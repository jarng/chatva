package week10_Networking.ex2.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatboxUI extends JFrame {

  private JTextField messageField;
  private JTextArea chatArea;

  public ChatboxUI(String username) {
    setTitle("Chatter - " + username);
    setSize(800, 500);
    setResizable(false);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setLocationRelativeTo(null);

    // create message input field
    messageField = new JTextField();
    messageField.setFont(new Font("Arial", Font.PLAIN, 16));
    messageField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    // create chat display area
    chatArea = new JTextArea();
    chatArea.setEditable(false);
    chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
    chatArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    chatArea.setAutoscrolls(true);

    JScrollPane scrollPane = new JScrollPane(chatArea);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    // add components to the frame
    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.add(messageField, BorderLayout.CENTER);
    inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(scrollPane, BorderLayout.CENTER);
    add(inputPanel, BorderLayout.SOUTH);

    // create the listener for the message input field
    ResponseListener listener = new ResponseListener(chatArea);
    new Thread(listener).start();

    // set background color
    getContentPane().setBackground(new Color(230, 230, 230));

    // events
    messageField.addKeyListener(new MessageFieldEventHandler());
    this.addWindowListener(new WindowEventHandler());

    setVisible(true);
  }

  public void appendText(String message) {
    chatArea.append(message + "\n");
  }

  class MessageFieldEventHandler implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER && !messageField.getText().equals("")) {
        try {
          String message = messageField.getText();
          messageField.setText("");
  
          // send message to server
          ClientUtil.getInstance().sendMessage("[MESSAGE]" + message);
          chatArea.append("[YOU]: " + message + "\n");

        } catch (Exception ex) {
          ex.printStackTrace();
        }    
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

  }

  class WindowEventHandler extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent windowEvent) {
      try {
        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
          // ClientUtil.getInstance().closeEverything();
          System.exit(0);
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
  }

}
