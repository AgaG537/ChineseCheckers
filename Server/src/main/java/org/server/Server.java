package org.server;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import org.server.board.boardManagement.Board;
import org.server.board.boardManagement.BoardFactory;
import org.server.board.MoveRecord;
import org.server.board.MoveRecordRepository;
import org.server.playerHandlers.ClientHandler;
import org.server.playerHandlers.PlayerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

/**
 * The main server class responsible for accepting client connections
 * and managing the overall lifecycle of the game. It listens for client connections,
 * creates client handlers, and starts the game once all players are connected.
 */
@Component
public class Server {

  private final ServerSocket serverSocket;
  private final GameManager gameManager;
  private final MoveRecordRepository moveRecordRepository;
  private final int seed;

  /**
   * Constructs a Server with the specified server socket.
   *
   * @param gameManager         The game manager instance.
   * @param moveRecordRepository The repository for saving move records.
   */
  @Autowired
  public Server(GameManager gameManager, MoveRecordRepository moveRecordRepository) throws IOException {
    this.serverSocket = new ServerSocket(1234); // Define the server socket port
    this.gameManager = gameManager;
    gameManager.setServer(this);
    this.moveRecordRepository = moveRecordRepository;
    Random random = new Random();
    this.seed = random.nextInt(1000000);
  }

  /**
   * Starts the server to accept incoming client connections.
   */
  @PostConstruct
  public void start() {
    try {
      System.out.println("Server is running...");
      MoveRecord mr = new MoveRecord(-1);
      moveRecordRepository.save(mr);

      gameManager.setGameNum(moveRecordRepository.currentGameNum() + 1);

      while (!serverSocket.isClosed()) {
        if (gameManager.getPlayerHandlers().size() < gameManager.getMaxUsers() || gameManager.getMaxUsers() == 0) {
          Socket socket = serverSocket.accept();
          int userNum = gameManager.getPlayerHandlers().size() + 1;
          ClientHandler clientHandler = new ClientHandler(socket, gameManager, userNum);
          new Thread(clientHandler).start();
//          System.out.println("max players: " + moveRecordRepository.getMaxUsersByGameNum(1));

          if (gameManager.getPlayerHandlers().size() == gameManager.getMaxUsers()) {
            if (gameManager.getBoard() == null) {
              int numOfPlayers = gameManager.getMaxUsers() + gameManager.getMaxBots();
              Board board = BoardFactory.createBoard(10, numOfPlayers, gameManager.getVariant(), seed);
              gameManager.setBoard(board);
            }
            gameManager.setMoveValidator(gameManager.getBoard().getCells());
            if (gameManager.getMaxBots() > 0) {
              gameManager.initializeBots();
            }
            gameManager.startGame();
            gameManager.broadcastGameStarted();
            while (!allSetup(gameManager.getPlayerHandlers())) {
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


  /**
   * Checks if all connected clients have completed their setup.
   *
   * @param playerHandlers The list of player handlers.
   * @return true if all clients have completed their setup; false otherwise.
   */
  public boolean allSetup(List<PlayerHandler> playerHandlers) {
    for (PlayerHandler playerHandler : playerHandlers) {
      if (playerHandler instanceof ClientHandler) {
        if (!((ClientHandler) playerHandler).getSetup()) {
          return false;
        }
      }
    }
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
   * Method for saving move record.
   *
   * @param mr move record.
   */
  public void saveMoveRecord(MoveRecord mr) {
    System.out.println("saving move record Server");
    moveRecordRepository.save(mr);
    System.out.println("saved move record server");
  }
}
