package org.client;

import org.client.GUI.ClientGUIManager;

public class CommandHandler {
  public static void handleCommand(String message, ClientGUIManager guiManager) {
    if (message.startsWith("User number ") || message.equals("You just moved")) {
      guiManager.clearServerMessageBox();
      guiManager.addCurrPlayerInfo();
    } else if (message.startsWith("Turn skipped by user:") || message.equals("You just skipped")) {
      guiManager.clearServerMessageBox();
      guiManager.addCurrPlayerInfo();
    }
    else if (message.equals("Game options correct.")) {
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
    } else if (message.startsWith("[CMD]")) {

    } else if (message.startsWith("VARIANT")) {
      String[] tokens = message.split(" ");
      guiManager.setVariant(tokens[1]);
    }
    else {
      guiManager.addLabel(message);
    }
  }
}
