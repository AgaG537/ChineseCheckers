package org.client.Board;

public interface Board {
  public void handleCommand(String command);
  public int getConstraintSize();
  public int getBoardHeight();
  public int getBoardWidth();
  public Cell getCell(int x, int y);
  public void setWin();
}
