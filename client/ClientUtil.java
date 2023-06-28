package week10_Networking.ex2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientUtil{

  private final int PORT = 8080;
  private final String HOST = "localhost";

  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  private static ClientUtil instance;

  private ClientUtil() throws IOException {
    this.socket = new Socket(HOST, PORT);
    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.out = new PrintWriter(socket.getOutputStream(), true);
  }

  public static ClientUtil getInstance() throws IOException{
    if (instance == null) {
      instance = new ClientUtil();
    }
    return instance;
  }

  public Socket getSocket() {
    return socket;
  }
  public BufferedReader getIn() {
    return in;
  }

  public void sendMessage(String message) {
    out.println(message);
  }

  public void closeEverything() {
    try {
      in.close();
      out.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
