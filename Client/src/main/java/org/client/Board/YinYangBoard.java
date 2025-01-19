package org.client.Board;

import javafx.scene.paint.Color;

/**
 * Represents a YinYang game board with a specialized two-player zone setup.
 */
public class YinYangBoard extends AbstractBoard {
  /**
   * Constructs a YinYangBoard with the specified number of marbles per player.
   * Note: The YinYang variant is restricted to two players.
   *
   * @param marblesPerPlayer Number of marbles allocated to each player.
   */
  public YinYangBoard(int marblesPerPlayer) {
    super(marblesPerPlayer, 0);
  }

  /**
   * Assigns a pawn to a cell in the YinYang variant.
   * Configures the cell with the pawn's properties and sets the zone color.
   *
   * @param playerNum The number of the player owning the pawn.
   * @param color     The color associated with the pawn.
   * @param cell      The cell where the pawn is to be placed.
   */
  @Override
  public void setupPawn(int playerNum, Color color, Cell cell) {
    if (playerNum != 0) {
      assignPawnToCell(cell, playerNum, color);
    }
    cell.setZoneColor(color);
  }

  /**
   *
   * @param colorNum unnecessary argument only here because of the function prototype in AbstractBoard
   *
   * @return Color default zone color for unoccupied cells overriden because of setupPlayerZpnes in AbstractBoard
   */
  @Override
  protected Color getColor(int colorNum){
    return ColorManager.generateDefaultColor(0);
  }

  /**
   * Handles a command to create game elements such as pawns or zones on the board.
   *
   * Modified compared to the abstract method in such a way that player zone colors are assigned alongside pawn creation
   * whereas in AbstractBoard those were assigned with zone creation
   *
   * @param command The command string specifying creation instructions.
   */
  @Override
  public void handleCreate(String command) {
    int[] positions = decodeCommand(command);
    int row = positions[0];
    int col = positions[1];
    int player = positions[2];
    int numPlayers = positions[3];
    Color color = Color.valueOf(ColorManager.getDefaultColorString(numPlayers, player));
    System.out.println("color: " + color);
    cells[row][col].setZoneColor(color);
    setupPawn(player, color, cells[row][col]);
  }

}