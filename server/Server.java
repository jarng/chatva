package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
// import server.ClientHandler;

public class Server {
  private final int PORT = 8080;

  private ServerSocket serverSocket;
  private Socket clientSocket;
  private Set<ClientHandler> clients;

  public Server(InetAddress address) throws IOException  {
    serverSocket = new ServerSocket(PORT, 50, address);
    clients = new HashSet<>();
  }

  public void run() throws IOException {
    System.out.println("Server is running on " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() + "...");

    while (!serverSocket.isClosed()) {
      clientSocket = serverSocket.accept();
      System.out.println("Client connected: " + clientSocket.getInetAddress());

      ClientHandler clientHandler = new ClientHandler(clientSocket, this);
      clientHandler.start();
    }
  }

  public void broadcast(String message, ClientHandler exclude) {
    for (ClientHandler cl : clients) {
      if (cl != exclude) {
        cl.sendMessage(message);
      }
    }
  }

  public int getNumberOfUsers() {
    return clients.size();
  }

  public void addUser(ClientHandler clientHandler) {
    clients.add(clientHandler);
  }

  public void removeUser(ClientHandler clientHandler) {
    clients.remove(clientHandler);
    // System.out.println("User: " + username + " disconnected");
  }

  public static void main(String[] args) {
    try {
      InetAddress address = InetAddress.getByName("localhost");
      Server server = new Server(address);
      server.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
