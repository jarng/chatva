package week10_Networking.ex2.client;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ResponseListener implements Runnable {

  private BufferedReader in;
  private JTextArea textArea;

  public ResponseListener(JTextArea textArea) {
    try {
      this.textArea = textArea;
      this.in = ClientUtil.getInstance().getIn();
      System.out.println(this.in);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      String response = null;
      while ((response = in.readLine()) != null) {
        System.out.println("Response from server: " + response);
        String finalResponse = response;
        SwingUtilities.invokeLater(() -> textArea.append(finalResponse + "\n"));
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
