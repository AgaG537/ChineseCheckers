package org.server;


import org.server.board.Board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manages the state of the game, including player turns, broadcasting messages,
 * and ensuring synchronization between connected clients.
 * Acts as a central hub for game-related logic.
 */
public class GameManager {
  private final List<ClientHandler> clientHandlers;
  private int maxUsers;
  private int currTurn;
  private boolean gameStarted;
  private Board currentBoard;
  private String variant;

  /**
   * Constructs a GameManager.
   */
  public GameManager() {
    this.clientHandlers = new ArrayList<>();
    this.maxUsers = 0;
    this.currTurn = 0;
    this.gameStarted = false;
  }

  public void setBoard(Board board) {
    this.currentBoard = board;
  }

  /**
   * Adds a client handler to the user manager.
   *
   * @param clientHandler The client handler to add.
   */
  public synchronized void addUser(ClientHandler clientHandler) {
    clientHandlers.add(clientHandler);
    try {
      clientHandler.sendMessage("VARIANT " + variant);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setVariant(String variant) {
    this.variant=variant;
  };

  /**
   * Removes a client handler from the user manager.
   *
   * @param clientHandler The client handler to remove.
   */
  public synchronized void removeUser(ClientHandler clientHandler) {
    clientHandlers.remove(clientHandler);
  }

  /**
   * Returns the list of all connected client handlers.
   *
   * @return A list of client handlers.
   */
  public synchronized List<ClientHandler> getClientHandlers() {
    return new ArrayList<>(clientHandlers);
  }

  /**
   * Sets the maximum number of users allowed.
   *
   * @param maxUsers The maximum number of users.
   */
  public synchronized void setMaxUsers(int maxUsers) {
    this.maxUsers = maxUsers;
  }

  /**
   * Returns the maximum number of users allowed.
   *
   * @return The maximum number of users.
   */
  public synchronized int getMaxUsers() {
    return maxUsers;
  }

  /**
   * Starts the game.
   * Sets the game state to started.
   */
  public synchronized void startGame() {
    this.gameStarted = true;
  }

  /**
   * Returns whether the game has started.
   *
   * @return true if the game is started, false otherwise.
   */
  public synchronized boolean isGameStarted() {
    return gameStarted;
  }

  /**
   * Advances the turn to the next player.
   *
   * @param playerCount The total number of players in the game.
   */
  public synchronized void advanceTurn(int playerCount) {
    if (currTurn + 1 > playerCount) {
      currTurn = 1;
    } else {
      currTurn++;
    }
  }

  /**
   * Sets the current player's turn index to random one.
   *
   */
  public synchronized void setRandomTurn() {
    currTurn = (int) ((Math.random() * getMaxUsers()) + 1);
  }

  /**
   * Returns the current player's turn index.
   *
   * @return The index of the current player's turn.
   */
  public synchronized int getCurrTurn() {
    return currTurn;
  }


  /**
   * Broadcasts a message about the number of users still needed.
   */
  public synchronized void broadcastNumOfUsers() {
    for (ClientHandler clientHandler : clientHandlers) {
      try {
        int missingNumOfUsers = maxUsers - clientHandlers.size();
        clientHandler.sendMessage("Waiting for " + missingNumOfUsers + " more player(s).");
      } catch (Exception e) {
        clientHandler.closeEverything();
      }
    }
  }

  /**
   * Broadcasts a message that the game has started.
   */
  public synchronized void broadcastGameStarted() {
    for (ClientHandler clientHandler : clientHandlers) {
      try {
        clientHandler.sendMessage("START." + maxUsers + "," + "variant" + "," + getCurrTurn());
      } catch (Exception e) {
        clientHandler.closeEverything();
      }
    }
  }


  /**
   * Broadcasts a move made by a player to all clients.
   *
   * @param userNum       The username of the player making the move.
   * @param input           The input made by the player.
   */
  public synchronized void broadcastMove(int userNum, String input) {
    if (!gameStarted) {
      return;
    }
    String move = currentBoard.makeMove(input);
    for (ClientHandler clientHandler : clientHandlers) {
      try {
        if (!Objects.equals(clientHandler.getUserNum(), userNum)) {
        clientHandler.sendMessage("User number " + userNum + " moved: " + move);
        } else {
          clientHandler.sendMessage("You just moved");
        }
        clientHandler.sendMessage(move);
      } catch (Exception e) {
        clientHandler.closeEverything();
      }
    }
  }

  public int validateMove(int userNum, String input) {
    boolean valid = currentBoard.validateMove(userNum, input);
    if (valid) {
      return 0;
    }
    else {
      return 1;
    }
  }
}
