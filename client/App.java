package client;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          ClientUtil.getInstance();     
          new LoginUI();

        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Something went wrong");
          e.printStackTrace();
        }
      }
    });
  }
}
