package org.server;


import static java.lang.Thread.sleep;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import org.server.board.Board;
import org.server.board.BoardFactory;

/**
 * The main server class responsible for accepting client connections
 * and managing the overall lifecycle of the game. It listens for client connections,
 * creates client handlers, and starts the game once all players are connected.
 */
public class Server {
  private final ServerSocket serverSocket;
  private final GameManager gameManager;
  private final int seed;

  /**
   * Constructs a Server with the specified server socket.
   *
   * @param serverSocket The server socket.
   */
  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    this.gameManager = new GameManager();
    Random random = new Random();
    seed = random.nextInt(1000000);
    this.gameManager.setSeed(seed);
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
          int userNum = gameManager.getClientHandlers().size() + 1;
          ClientHandler clientHandler = new ClientHandler(socket, gameManager, userNum);
          new Thread(clientHandler).start();

          if (gameManager.getClientHandlers().size() == gameManager.getMaxUsers()) {
            gameManager.startGame();
            Board board = BoardFactory.createBoard(10, gameManager.getMaxUsers(), gameManager.getVariant(), seed);
            gameManager.setMoveValidator(board.getCells());
            gameManager.setBoard(board);
            gameManager.broadcastGameStarted();
            while (!allSetup(gameManager.getClientHandlers())) {
              sleep(10);
            }
            gameManager.broadcastBoardCreate();
          }
        }
      }
    } catch (IOException e) {
      closeServerSocket();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean allSetup(List<ClientHandler> clientHandlers) {
    for (ClientHandler clientHandler : clientHandlers) {
      if (!clientHandler.getSetup()) {
        System.out.println("ret false");
        return false;
      }
    }
    System.out.println("ret true");
    return true;
  }

  /**
   * Closes the server socket.
   */
  public void closeServerSocket() {
    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
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
