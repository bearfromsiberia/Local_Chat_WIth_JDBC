import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(8189)){
                Socket socket = serverSocket.accept();
                new Thread(new Receiver(socket)).start();
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
        try(Scanner scanner = new Scanner(socket.getInputStream())){
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            while (scanner.hasNext()){
                out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
