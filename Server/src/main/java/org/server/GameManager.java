package org.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.server.board.Board;
import org.server.board.Cell;
import org.server.board.MoveValidator;

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
  private MoveValidator moveValidator;
  private String variant;
  private final ArrayList<Integer> finishedPlayers;
  private int seed;
  private final Object setupLock = new Object();
  private int setupCount = 0;

  /**
   * Constructs a GameManager.
   */
  public GameManager() {
    this.clientHandlers = new ArrayList<>();
    this.maxUsers = 0;
    this.currTurn = 0;
    this.gameStarted = false;
    finishedPlayers = new ArrayList<>();
  }

  /**
   * Sets the current board for the game.
   *
   * @param board The board instance to set.
   */
  public void setBoard(Board board) {
    this.currentBoard = board;
  }

  public void setMoveValidator(Cell[][] cells) {
    this.moveValidator = new MoveValidator(cells);
  }

  /**
   * Adds a client handler to the user manager and notifies the client of the selected variant.
   *
   * @param clientHandler The client handler to add.
   */
  public synchronized void addUser(ClientHandler clientHandler) {
    clientHandlers.add(clientHandler);
    try {
      clientHandler.sendMessage("VARIANT " + variant);
      clientHandler.sendMessage("SEED " + seed);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the game variant for the current session.
   *
   * @param variant The variant to set (e.g., "standard", "order", "yinyang").
   */
  public void setVariant(String variant) {
    this.variant = variant;
  }

  /**
   * Retrieves the currently selected game variant.
   *
   * @return The selected game variant as a string.
   */
  public String getVariant() {
    return variant;
  }

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
    if (finishedPlayers.isEmpty()) {
      if (currTurn + 1 > playerCount) {
        currTurn = 1;
      } else {
        currTurn++;
      }
    } else {
      if (playerCount == finishedPlayers.size()) {
        broadcastGameFinished();
      } else {
        do {
          if (currTurn + 1 > playerCount) {
            currTurn = 1;
          } else {
            currTurn++;
          }
        } while (finishedPlayers.contains(currTurn));
      }
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
   * Broadcasts a message that the game has started.
   */
  public synchronized void broadcastGameFinished() {
    for (ClientHandler clientHandler : clientHandlers) {
      try {
        clientHandler.sendMessage("GAME FINISHED!");
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
    String move = moveValidator.makeMove(input);
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

  /**
   * Broadcasts a message that the turn has been skipped by a user.
   *
   * @param userNum The user number of the player who skipped their turn.
   */
  public synchronized void broadcastSkip(int userNum) {
    if (!gameStarted) {
      return;
    }

    for (ClientHandler clientHandler : clientHandlers) {
      try {
        if (!Objects.equals(clientHandler.getUserNum(), userNum)) {
          clientHandler.sendMessage("Turn skipped by user: " + userNum);
        } else {
          clientHandler.sendMessage("You just skipped");
        }
      } catch (Exception e) {
        clientHandler.closeEverything();
      }
    }
  }

  /**
   * Broadcasts a message that the player has won.
   */
  public synchronized void broadcastPlayerWon(int playerNum) {
    for (ClientHandler clientHandler : clientHandlers) {
      try {
        clientHandler.sendMessage("WIN." + playerNum);
      } catch (Exception e) {
        clientHandler.closeEverything();
      }
    }
  }

  /**
   * Validates a move made by a player based on the current game state.
   *
   * @param userNum The player's user number.
   * @param input   The move input provided by the player.
   * @return 0 if the move is valid, 1 if the move is invalid.
   */
  public int validateMove(int userNum, String input) {
    System.out.println(input);
    System.out.println(input.equals("SKIP"));
    if (input.equals("SKIP")) {
      System.out.println("returning 2");
      return 2;
    }
    boolean valid = moveValidator.validateMove(userNum, input);
    if (valid) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * Checks if there is a winning condition on the current board.
   *
   * @return The user number of the winning player, or 0 if no winner yet.
   */
  public int checkWin() {
    return currentBoard.checkWinCondition();
  }

  /**
   * Adds a player to the list of finished players.
   * If all players have finished, broadcasts a game-finished message to all clients.
   *
   * @param playerNum The player number to mark as finished.
   */
  public void addFinishedPlayer(int playerNum) {
    finishedPlayers.add(playerNum);
    if (finishedPlayers.size() == clientHandlers.size()) {
      broadcastGameFinished();
    }
  }

  /**
   * Sets the seed for the game, which may affect randomness in gameplay mechanics.
   *
   * @param seed The seed value to set.
   */
  public void setSeed(int seed) {
    this.seed = seed;
  }

  public synchronized void broadcastBoardCreate() {
    for (ClientHandler clientHandler : clientHandlers) {
      for (Cell[] cellRow : currentBoard.getCells()) {
        for (Cell cell : cellRow) {
          try {
            clientHandler.sendMessage(currentBoard.makeCreate(cell.getRow(), cell.getCol()));
          } catch (Exception e) {
            clientHandler.closeEverything();
          }
        }
      }
    }
  }

  public void waitForAllSetups() throws InterruptedException {
    synchronized (setupLock) {
      while (setupCount < maxUsers) {
        setupLock.wait();
      }
    }
  }

  public void clientSetupComplete() {
    synchronized (setupLock) {
      setupCount++;
      if (setupCount == maxUsers) {
        setupLock.notifyAll();
      }
    }
  }
}
