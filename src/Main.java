import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main extends JFrame {
    private static JTextArea textArea;
    private static JTextField textField;
    private static int PORT = 4040;
    private  Socket socket;
    private  BufferedWriter out;
    private static BufferedReader in;


    public void Draw() throws IOException {
        textArea = new JTextArea(400/19,50);
        textField = new JTextField();
        JButton button = new JButton("Send");
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setLocation(0,0);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
        setResizable(false);
        getContentPane().add(BorderLayout.NORTH,scrollPane);
        getContentPane().add(BorderLayout.CENTER,textField);
        getContentPane().add(BorderLayout.EAST,button);
        socket = new Socket("localhost",PORT);
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        button.addActionListener(e ->{
            try {
                out.write(textField.getText() + "\n");
                out.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setCaretPosition(textArea.getText().length());
            textField.setText("");
        });
        new Thread(this.nonstatic()).start();
        setVisible(true);
    }
    class Client_receiver implements Runnable{

        @Override
            public void run() {
                while (true){
                    try{
                    textArea.append(in.readLine()+"\n");
                    textArea.setCaretPosition(textArea.getText().length());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    public Client_receiver nonstatic(){
        return new Client_receiver();
    }
    public static void main(String[] args) throws IOException {
        Main client = new Main();
        client.Draw();
    }
}

