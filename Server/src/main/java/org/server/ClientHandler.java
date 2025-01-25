package org.server;


import java.io.*;
import java.net.Socket;
import org.server.board.MaxUserHandler;

/**
 * Handles communication with a single client in the game.
 * Each instance of this class manages one client connection,
 * including receiving input and sending messages.
 */
public class ClientHandler implements Runnable{
  private final Socket socket;
  private final BufferedReader bufferedReader;
  private final BufferedWriter bufferedWriter;
  private final GameManager gameManager;
  private final int userNum;
  private boolean setup;

  /**
   * Constructs a ClientHandler with the specified socket, game manager and user number.
   *
   * @param socket       The socket connected to the client.
   * @param gameManager  The game manager managing the game state.
   * @param userNum      The user's position in the game.
   * @throws IOException If an I/O error occurs when creating the input or output streams.
   */
  public ClientHandler(Socket socket, GameManager gameManager, int userNum) throws IOException {
    this.socket = socket;
    this.gameManager = gameManager;
    this.userNum = userNum;
    this.setup = false;

    this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    sendMessage(String.valueOf(userNum));

    if (userNum == 1) {
      requestMaxUsers();
      gameManager.setRandomTurn();
      sendMessage("Game options correct.");
    }

    gameManager.addUser(this);
    sendMessage("You have successfully joined.");

    if (gameManager.getMaxUsers() != gameManager.getClientHandlers().size()) {
      gameManager.broadcastNumOfUsers();
    }
  }


  /**
   * Requests the maximum number of users from the client.
   *
   * @throws IOException If an I/O error occurs when reading from the client.
   */
  void requestMaxUsers() throws IOException {
    int maxUsers = 0;
    String variant;
    do {
      try {
        String[] message = (bufferedReader.readLine()).split(",");
        maxUsers = Integer.parseInt(message[0]);
        variant = message[1];
        maxUsers = MaxUserHandler.handleMaxUsers(maxUsers, variant);

        gameManager.setMaxUsers(maxUsers);
        gameManager.setVariant(variant);
      } catch (NumberFormatException e) {
        sendMessage("Wrong number of users!");
      }
    } while (maxUsers != 2 && maxUsers != 3 && maxUsers != 4 && maxUsers != 6);

  }

  /**
   * The main logic for the client handler.
   * Reads messages from the client and processes them.
   */
  @Override
  public void run() {
    try {
      while (socket.isConnected()) {
        String message = bufferedReader.readLine();
        if (message.startsWith("SETUP")) {
          if (Integer.parseInt(message.split(" ")[1]) == userNum) {
            this.setup = true;
            sendMessage("[SETUP]");
          }
          continue;
        }
        if (gameManager.isGameStarted() && userNum == gameManager.getCurrTurn()) {
          int flag = gameManager.validateMove(userNum, message);
          switch (flag) {
            case 0:
              System.out.println(message);
              System.out.println(userNum);
              System.out.println("Broadcasting move");
              gameManager.broadcastMove(userNum, message);
              System.out.println("advancing turn");
              gameManager.advanceTurn(gameManager.getClientHandlers().size());
              int playerCheckedForWin = gameManager.checkWin();
              if (playerCheckedForWin != 0) {
                gameManager.broadcastPlayerWon(playerCheckedForWin);
                gameManager.addFinishedPlayer(playerCheckedForWin);
              }
              break;
            case 1:
              sendMessage("Invalid move!");
              System.out.println("wrong move");
              break;
            case 2:
              gameManager.advanceTurn(gameManager.getClientHandlers().size());
              gameManager.broadcastSkip(userNum);
              break;
            default:
              System.out.println("Something went wrong");
          }
        } else {
          if (bufferedReader.ready()) {
            bufferedReader.readLine();
          }
        }
      }
    } catch (IOException e) {
      closeEverything();
    }
  }

  /**
   * Sends a message to the client.
   *
   * @param message The message to send.
   * @throws IOException If an I/O error occurs when writing to the client.
   */
  public synchronized void sendMessage(String message) throws IOException {
    bufferedWriter.write(message);
    bufferedWriter.newLine();
    bufferedWriter.flush();
  }

  /**
   * Returns the user number of the client.
   *
   * @return The user number.
   */
  public int getUserNum() {
    return userNum;
  }

  /**
   * Closes all the resources (socket, bufferedReader, and bufferedWriter).
   */
  public void closeEverything() {
    try {
      socket.close();
      bufferedReader.close();
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    gameManager.removeUser(this);
  }

  /**
   * Returns whether the client has completed its setup phase.
   *
   * @return true if the client has completed setup; false otherwise.
   */
  public boolean getSetup() {
    return setup;
  }

}