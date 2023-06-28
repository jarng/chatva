package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private Thread thread;
  private Socket clientSocket;
  private Server server;
  private BufferedReader in;
  private PrintWriter out;
  private String username;

  public ClientHandler(Socket clientSocket, Server server) throws IOException {
    this.thread = new Thread(this);
    this.clientSocket = clientSocket;
    this.server = server;
    this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  @Override
  public void run() {
    while (!clientSocket.isClosed()) {
      try {
        String message = in.readLine();
        if (message == null) {
          closeEverything();
          break;
        }

        System.out.println("Message from " + clientSocket.getInetAddress() + ": " + message);

        if (message.contains("[CONNECT]")) {
          this.username = message.split("-")[1];
          server.addUser(this);

          sendMessage("[SERVER]: " + "Hello " + this.username + ", welcome to the chat room!");
          sendMessage("[SERVER]: " + "Number of users: " + server.getNumberOfUsers());

          server.broadcast("[SERVER]: " + message.split("-")[1] + " has joined the chat room!", this);
          server.broadcast("[SERVER]: " + "Number of users: " + server.getNumberOfUsers(), this);
        }

        if (message.contains("[MESSAGE]")) {
          server.broadcast("[" + username + "]: " + message.substring(9), this);
        }
        
      } catch (IOException e)  {
        closeEverything();
        e.printStackTrace();
      }
    }

    server.removeUser(this);
    System.out.println("Client " + clientSocket.getInetAddress() + " disconnected");

    server.broadcast("[SERVER] - " + this.username + " has left the chat room!", this);
    server.broadcast("[SERVER] - " + "Number of users: " + server.getNumberOfUsers(), this);

  }

  public void sendMessage(String message) {
    out.println(message);
  }

  public void start() {
    thread.start();
  }

  private void closeEverything() {
    try {
      in.close();
      out.close();
      clientSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
