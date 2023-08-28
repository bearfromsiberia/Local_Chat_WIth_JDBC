import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private static JTextArea textArea;
    private static JTextField textField;

    private class Server extends Thread{
        public Server(Receiver receiver){
                super(new Receiver());
        }
    }

    private class Receiver implements Runnable{

        @Override
        public void run() {

        }
    }

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
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Draw(new Main());
    }
}