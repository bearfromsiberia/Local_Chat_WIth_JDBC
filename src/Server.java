import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(4040)){
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new Receiver(socket)).start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
class Receiver implements Runnable{
    private Socket socket;
    public Receiver(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try(Scanner in = new Scanner(socket.getInputStream())){
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            out.write(("Your message: " + in.nextLine()).getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
