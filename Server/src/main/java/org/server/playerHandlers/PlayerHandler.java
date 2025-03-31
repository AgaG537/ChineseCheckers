package org.server.playerHandlers;

import java.io.IOException;

/**
 * Abstract class representing a player in the game.
 */
public abstract class PlayerHandler {
  protected int userNum;

  /**
   * Retrieves the user number of the player.
   *
   * @return The user number.
   */
  public int getUserNum() {
    return userNum;
  }

  /**
   * Sets the user number of the player.
   *
   * @param userNum The user number to be set.
   */
  public void setUserNum(int userNum) {
    this.userNum = userNum;
  }

  /**
   * Sends a message to the player.
   *
   * @param message The message to be sent.
   * @throws IOException          If an I/O error occurs.
   * @throws InterruptedException If the thread is interrupted.
   */
  public abstract void sendMessage(String message) throws IOException, InterruptedException;
}
