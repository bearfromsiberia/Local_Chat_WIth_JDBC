import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Server {
    private static ArrayList<Receiver> receivers = new ArrayList<>();
    public static ArrayList<Receiver> sockets(){
        return receivers;
    }
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(4040)){
            while (true){
                Socket socket = serverSocket.accept();
                receivers.add(new Receiver(socket));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
class Receiver extends Thread{
    private BufferedWriter out;
    private BufferedReader in;
    public Receiver(Socket socket) throws IOException {
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }
    @Override
    public void run() {
        String message = null;
        while (true){
            try {
                message = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for(Receiver socket : Server.sockets()) {
                try {
                    socket.out.write(Thread.currentThread().getName() + message+"\n");
                    socket.out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
