package org.server.board;

public interface Board {
  public String makeMove(String input);
  public boolean validateMove(int userId,String input);
}
