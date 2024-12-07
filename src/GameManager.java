package src;


import java.util.List;

public class GameManager {
  private int currTurn;
  private boolean gameStarted;

  /**
   * Constructs a GameManager.
   */
  public GameManager() {
    this.currTurn = 0;
    this.gameStarted = false;
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
    currTurn = (currTurn + 1) % playerCount;
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
   * Broadcasts a move made by a player to all clients.
   *
   * @param clientHandlers The list of all connected client handlers.
   * @param username       The username of the player making the move.
   * @param move           The move made by the player.
   */
  public void broadcastMove(List<ClientHandler> clientHandlers, String username, String move) {
    if (!gameStarted) return;

    for (ClientHandler clientHandler : clientHandlers) {
      try {
        clientHandler.sendMessage(username + ": " + move);
      } catch (Exception e) {
        clientHandler.closeEverything();
      }
    }
  }
}
