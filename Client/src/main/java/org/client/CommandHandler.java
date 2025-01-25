package org.client;

import org.client.GUI.ClientGUIManager;

/**
 * Handles commands received from the server and updates the GUI state accordingly.
 * Commands include game state updates, player actions, and system notifications.
 */
public class CommandHandler {

  /**
   * Processes a server command and updates the client GUI based on the message content.
   *
   * @param message    The message received from the server.
   * @param guiManager The GUI manager to update the user interface.
   * @throws InterruptedException If the thread is interrupted during message handling.
   */
  public static void handleCommand(String message, ClientGUIManager guiManager) throws InterruptedException {
    if (message.startsWith("User number ") || message.equals("You just moved")) {
      guiManager.clearServerMessageBox();
      guiManager.addCurrPlayerInfo();
    } else if (message.startsWith("Turn skipped by user:") || message.equals("You just skipped")) {
      guiManager.clearServerMessageBox();
      guiManager.addLabel(message);
      guiManager.addCurrPlayerInfo();
    } else if (message.equals("Wrong number of players!")) {
      guiManager.addLabel(message);
    } else if (message.equals("Game options correct.")) {
      guiManager.setWaitingPanes();
      guiManager.addLabel(message);
    } else if (message.startsWith("START")) {
      guiManager.setGamePanes(message);
      guiManager.addCurrPlayerInfo();
    } else if (message.equals("Invalid move!")) {
      guiManager.addLabel(message);
    } else if (message.startsWith("WIN")) {
      guiManager.showWin(message);
    } else if (message.equals("GAME FINISHED!")) {
      guiManager.addLabel(message);
    } else if (message.startsWith("VARIANT")) {
      String[] tokens = message.split(" ");
      guiManager.setVariant(tokens[1]);
    } else {
      guiManager.addLabel(message);
    }
  }
}
