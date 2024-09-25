import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends JFrame {
  private JTextArea messageArea;
  private JTextField textField;
  private Client client;
  private JButton exitButton;

    public ChatClientGUI() {
        super("Chat Application");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String name = JOptionPane.showInputDialog(this, "Enter your name:", "Name Entry", JOptionPane.PLAIN_MESSAGE);
        this.setTitle("Chat Application - " + name);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        textField = new JTextField();
        textField.addActionListener(e -> {
            String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": " + textField.getText();
            client.sendMessage(message);
            textField.setText("");
            });
        add(textField, BorderLayout.SOUTH);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
        // Send a departure message to the server
        String departureMessage = name + " has left the chat.";
        client.sendMessage(departureMessage);
 
        // Delay to ensure the message is sent before exiting
        try {
            Thread.sleep(1000); // Wait for 1 second to ensure message is sent
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
 
        // Exit the application
        System.exit(0);
        });

        // Creating a bottom panel to hold the text field and exit button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST); // Add the exit button to the bottom panel
        add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the frame
        try {
            this.client = new Client("127.0.0.1", 4000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

  private void onMessageReceived(String message) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            messageArea.append(message + "\n");
        }
      });
  }

  public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new ChatClientGUI().setVisible(true);
        }
      });
  }
}