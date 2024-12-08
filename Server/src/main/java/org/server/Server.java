package org.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main server class responsible for accepting client connections
 * and managing the overall lifecycle of the game. It listens for client connections,
 * creates client handlers, and starts the game once all players are connected.
 */
public class Server {
  private final ServerSocket serverSocket;
  private final GameManager gameManager;

  /**
   * Constructs a Server with the specified server socket.
   *
   * @param serverSocket The server socket.
   */
  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    this.gameManager = new GameManager();
  }

  /**
   * Starts the server to accept incoming client connections.
   */
  public void start() {
    try {
      System.out.println("Server is running...");
      while (!serverSocket.isClosed()) {
        if (gameManager.getClientHandlers().size() < gameManager.getMaxUsers() || gameManager.getMaxUsers() == 0) {
          Socket socket = serverSocket.accept();
          int userNum = gameManager.getClientHandlers().size();
          ClientHandler clientHandler = new ClientHandler(socket, gameManager, userNum);
          new Thread(clientHandler).start();

          if (gameManager.getClientHandlers().size() == gameManager.getMaxUsers()) {
            gameManager.startGame();
            gameManager.broadcastGameStarted();
          }
        }
      }
    } catch (IOException e) {
      closeServerSocket();
    }
  }

  /**
   * Closes the server socket.
   */
  public void closeServerSocket() {
    try {
      if (serverSocket != null) serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * The main method to start the server.
   *
   * @param args Command-line arguments.
   * @throws IOException If an I/O error occurs when creating the server socket.
   */
  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(1234);
    Server server = new Server(serverSocket);
    server.start();
  }
}
