package org.server.playerHandlers;


import java.io.*;
import java.net.Socket;

import org.server.GameManager;
import org.server.board.utilityHandlers.MaxUserHandler;

/**
 * Handles communication with a single client in the game.
 * Each instance of this class manages one client connection,
 * including receiving input and sending messages.
 */
public class ClientHandler extends PlayerHandler implements Runnable {
  private final Socket socket;
  private final BufferedReader bufferedReader;
  private final BufferedWriter bufferedWriter;
  private final GameManager gameManager;
  //private final int userNum;
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
    setUserNum(userNum);
    this.userNum = userNum;
    this.setup = false;

    this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    sendMessage(String.valueOf(userNum));

    if (userNum == 1) {
      requestMaxPlayers();
      gameManager.setRandomTurn();
      sendMessage("Game options correct.");
    }

    gameManager.addPlayer(this);
    sendMessage("You have successfully joined.");

    if (gameManager.getMaxUsers() != gameManager.getPlayerHandlers().size()) {
      gameManager.broadcastNumOfUsers();
    }
  }


  /**
   * Requests the maximum number of users from the client.
   *
   * @throws IOException If an I/O error occurs when reading from the client.
   */
  void requestMaxPlayers() throws IOException {
    int maxUsers = 0;
    int maxBots = 0;
    String variant = "";

    try {
      String[] message = (bufferedReader.readLine()).split(",");
      if (message[0].equals("DB")) {
//          gameManager.initFromDatabase(Integer.parseInt(message[1]));
        gameManager.createFromDatabase(Integer.parseInt(message[1]));
        //maxUsers = 2;
      }
      else {
        while (isNumOfPlayersInvalid(maxUsers, maxBots)) {
          maxUsers = Integer.parseInt(message[0]);
          maxBots = Integer.parseInt(message[1]);
          variant = message[2];
          maxUsers = MaxUserHandler.handleMaxUsers(maxUsers, variant);
          if (isNumOfPlayersInvalid(maxUsers, maxBots)) {
            sendMessage("Wrong number of players!");
            message = (bufferedReader.readLine()).split(",");
          }
        }
        gameManager.setMaxUsers(maxUsers);
        gameManager.setMaxBots(maxBots);
        gameManager.setVariant(variant);
      }
    } catch (NumberFormatException e) {
      sendMessage("Wrong number of players!");
    }
  }

  private boolean isNumOfPlayersInvalid(int maxUsers, int maxBots) {
    return (maxUsers + maxBots != 2 && maxUsers + maxBots != 3 && maxUsers + maxBots != 4 && maxUsers + maxBots != 6) || maxUsers < 1;
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
          System.out.println(userNum + " " +  message);
          switch (flag) {
            case 0:
              System.out.println(message);
              System.out.println(userNum);
              System.out.println("Broadcasting move");
              gameManager.broadcastMove(userNum, message);
              System.out.println("advancing turn");
              gameManager.advanceTurn(gameManager.getPlayerHandlers().size());
              System.out.println(gameManager.getCurrTurn());



              // Zakomentowane chwilowo
//              int playerCheckedForWin = gameManager.checkWin();
//              if (playerCheckedForWin != 0) {
//                gameManager.broadcastPlayerWon(playerCheckedForWin);
//                gameManager.addFinishedPlayer(playerCheckedForWin);
//              }
//              break;

            case 1:
              sendMessage("Invalid move!");
              System.out.println("wrong move dupa");
              break;
            case 2:
              gameManager.advanceTurn(gameManager.getPlayerHandlers().size());
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
    gameManager.removePlayer(this);
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