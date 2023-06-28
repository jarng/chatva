package client;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginUI extends JFrame{
  private JTextField textField;
  private JButton button;
  private JLabel label;
  
  public LoginUI() {
    super("Chat");
    setSize(350, 200);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    Container contentPane = getContentPane();
    contentPane.setLayout(new FlowLayout());

    // Label
    label = new JLabel("Enter username");
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(new Font("Serief", Font.BOLD, 16));
    label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    contentPane.add(label);

    // Text field
    textField = new JTextField();
    textField.setColumns(10);
    textField.setFont(new Font("Serief", Font.PLAIN, 16));
    contentPane.add(textField);

    // Button
    button = new JButton("Proceed");
    button.setHorizontalAlignment(JButton.CENTER);
    button.setFont(new Font("Serief", Font.PLAIN, 16));
    contentPane.add(button);

    // Events
    textField.addKeyListener(new MessageFieldEventHandler());
    button.addActionListener(new ButtonEventHandler());
    this.addWindowListener(new WindowEventHandler());

    setVisible(true);
  }

  private void proceed() {
    try {
      if (textField.getText().length() <= 0) {
        JOptionPane.showMessageDialog(null, "Please enter username");
        return;
      }
  
      ClientUtil.getInstance().sendMessage("[CONNECT]-" + textField.getText());

      dispose();
      new ChatboxUI(textField.getText());

    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  class MessageFieldEventHandler implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        proceed();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
  }

  class ButtonEventHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      proceed();
    }
  }

  class WindowEventHandler extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
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


