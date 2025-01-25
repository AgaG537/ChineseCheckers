package org.server.playerHandlers;

import java.io.IOException;

public abstract class PlayerHandler {
  protected int userNum;

  public int getUserNum() {
    return userNum;
  }

  public void setUserNum(int userNum) {
    this.userNum = userNum;
  }

  public abstract void sendMessage(String message) throws IOException, InterruptedException;
}
