import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Main extends JFrame {
    private static JTextArea textArea;
    private static JTextField textField;
    private static int PORT = 8189;


    public static void Draw(JFrame frame){
        textArea = new JTextArea(400/19,50);
        textField = new JTextField();
        JButton button = new JButton("Send");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setLocation(0,0);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        button.addActionListener(e ->{

        });
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.getContentPane().add(BorderLayout.NORTH,scrollPane);
        frame.getContentPane().add(BorderLayout.CENTER,textField);
        frame.getContentPane().add(BorderLayout.EAST,button);
        button.addActionListener(e ->{
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(),PORT), 1000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.print(textField.getText());
            Scanner scanner = new Scanner(socket.getInputStream());
            while (scanner.hasNext()){
                textArea.append(scanner.nextLine());
                textArea.setCaretPosition(textArea.getText().length());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
            textField.setText("");
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Draw(new Main());
    }
}
