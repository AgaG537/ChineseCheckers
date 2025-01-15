package org.client.Board;

import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Factory class for generating and managing player zones on the game board.
 */
public class PlayerZoneFactory {
  private static int numPlayers;
  private static int boardWidth;
  private static int boardHeight;
  private static int playerZoneHeight;
  private static int numOfCellsPerZone;
  private static int[][] playerZonesStartPoints;
  private static int seed;
  private static int[] activeZoneNums;

  /**
   * Initializes the factory with the game settings.
   *
   * @param numOfPlayers       Number of players in the game.
   * @param boardWidthValue    Width of the game board.
   * @param boardHeightValue   Height of the game board.
   * @param playerZoneHeightValue Height of a player's zone.
   */
  public PlayerZoneFactory(int numOfPlayers, int boardWidthValue, int boardHeightValue, int playerZoneHeightValue) {
    numPlayers = numOfPlayers;
    playerZoneHeight = playerZoneHeightValue;
    boardWidth = boardWidthValue;
    boardHeight = boardHeightValue;
    numOfCellsPerZone = getNumOfCellsPerZone(playerZoneHeight);
    playerZonesStartPoints = new int[][]{
        {0, (boardWidth / 2)}, // Upper zone [0]
        {playerZoneHeight * 2 - 1, boardWidth - playerZoneHeight},  // Right upper zone xxx [1]
        {boardHeight - playerZoneHeight * 2, boardWidth - playerZoneHeight}, // Right bottom zone [2]
        {boardHeight - 1, (boardWidth / 2)}, // Bottom zone xxx [3]
        {boardHeight - playerZoneHeight * 2, playerZoneHeight - 1}, // Left bottom zone [4]
        {playerZoneHeight * 2 - 1, playerZoneHeight - 1} // Left upper zone xxx [5]
    };
    activeZoneNums = new int[6];
    markActiveZones();
  }

  /**
   * Marks the zones as active or inactive based on the number of players.
   */
  private void markActiveZones() {
    for (int i = 0; i < activeZoneNums.length; i++) {
      activeZoneNums[0] = 0;
    }
    switch (numPlayers) {
      case 2:
        activeZoneNums[0] = activeZoneNums[3] = 1;
        break;
      case 3:
        activeZoneNums[0] = activeZoneNums[2] = activeZoneNums[4] = 1;
        break;
      case 4:
        activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[4] = activeZoneNums[5] = 1;
        break;
      default:
        activeZoneNums[0] = activeZoneNums[1] = activeZoneNums[2] = activeZoneNums[3] = activeZoneNums[4] = activeZoneNums[5] = 1;
    }
  }

  /**
   * Adds standard player zones to the board.
   *
   * @param cells The current state of the board's cells.
   * @return The updated board cells with player zones added.
   */
  public static Cell[][] addPlayerZones(Cell[][] cells) {
    int defaultPlayerNum = 1;
    int playerNum = 0;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      }

      if (playerNum == defaultPlayerNum) {
        defaultPlayerNum++;
      }

    }
    return cells;
  }

  /**
   * Adds player zones for an order out of chaos variant to the
   * board and distributes pawns randomly in the middle of the board.
   *
   * @param cells The current state of the board's cells.
   * @return The updated board cells with ordered player zones added.
   */
  public static Cell[][] addOrderPlayerZones(Cell[][] cells) {
    int marbles = numOfCellsPerZone;

    int defaultPlayerNum = 1;
    int playerNum = 0;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setOrderCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            Color color = ColorManager.generateDefaultColor(i + 1);
            if (activeZoneNums[i] == 1) {
              playerNum = defaultPlayerNum;
            } else {
              playerNum = 0;
            }
            setOrderCellZone(color, playerNum, cells[row][col]);
          }
          k++;
        }
      }

      if (playerNum == defaultPlayerNum) {
        defaultPlayerNum++;
      }
    }

    // Randomly distribute pawns in the middle of the board.
    Random random = new Random(seed);
    int player = 0;
    int currPlayer = 0;
    for (int j : activeZoneNums) {
      player++;
      if (j != 0) {
        currPlayer++;
        int i = 0;
        while (i < marbles) {
          int row = random.nextInt(boardHeight);
          int col = random.nextInt(boardWidth);

          if (cells[row][col].getPawn() == null && cells[row][col].getZoneColor().toString().equals("0xffa64dff")) {
            Color pawnColor = ColorManager.generateDefaultColor(player);
            Pawn pawn = new Pawn(currPlayer, pawnColor, cells[row][col]);
            cells[row][col].pawnMoveIn(pawn);
            i++;
          }
        }
      }
    }

    return cells;
  }

  /**
   * Adds Yin-Yang player zones for a specific game mode.
   *
   * @param cells The current state of the board's cells.
   * @return The updated board cells with Yin-Yang player zones added.
   */
  public static Cell[][] addYinYangZones(Cell[][] cells) {
    Random random = new Random(seed);

    int[] activeZoneNums = new int[6];
    for (int i = 0; i < activeZoneNums.length; i++) {
      activeZoneNums[i] = 0;
    }

    int player1ZoneNum = random.nextInt(6);
    int player2ZoneNum = random.nextInt(6);
    while (player1ZoneNum == player2ZoneNum) {
      player2ZoneNum = random.nextInt(6);
    }

    activeZoneNums[player1ZoneNum] = activeZoneNums[player2ZoneNum] = 1;
    int playerNum;

    for (int i = 0; i < playerZonesStartPoints.length; i++) {
      int[] zoneStartPoint = playerZonesStartPoints[i];
      int rowStart = zoneStartPoint[0];
      int colStart = zoneStartPoint[1];

      int k = 0;
      if (i % 2 == 0) {
        for (int row = rowStart; row < rowStart + playerZoneHeight; row++) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            if (activeZoneNums[i] == 1) {
              if (i == player1ZoneNum) {
                playerNum = 1;
              } else {
                playerNum = 2;
              }
              setYinYangCellZone(playerNum, cells[row][col]);
            }
          }
          k++;
        }
      } else {
        for (int row = rowStart; row > rowStart - playerZoneHeight; row--) {
          for (int col = colStart - k; col <= colStart + k; col += 2) {
            if (activeZoneNums[i] == 1) {
              if (i == player1ZoneNum) {
                playerNum = 1;
              } else {
                playerNum = 2;
              }
              setYinYangCellZone(playerNum, cells[row][col]);
            }
          }
          k++;
        }
      }
    }

    return cells;
  }

  /**
   * Configures a cell with a player number and color, and optionally creates a pawn for the player.
   *
   * @param color       The color to set for the cell.
   * @param playerNum   The player's number (0 for no player).
   * @param currentCell The cell to update.
   */
  public static void setCellZone(Color color, int playerNum, Cell currentCell) {
    currentCell.setFlag(5);
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneColor(color);
    if (playerNum != 0) {
      Pawn pawn = new Pawn(playerNum, color, currentCell);
      currentCell.pawnMoveIn(pawn);
    }
  }

  /**
   * Configures a cell for the Yin-Yang game mode, setting the appropriate player and color.
   *
   * @param playerNum   The player's number (1 or 2).
   * @param currentCell The cell to update.
   */
  public static void setYinYangCellZone(int playerNum, Cell currentCell) {
    currentCell.setFlag(5);
    currentCell.setInitialPlayerNum(playerNum);
    Color color;
    if (playerNum != 0) {
      if (playerNum == 1) {
        color = Color.BLACK;
      } else {
        color = Color.WHITE;
      }
      currentCell.setZoneColor(color);
      Pawn pawn = new Pawn(playerNum, color, currentCell);
      currentCell.pawnMoveIn(pawn);
    }
  }

  /**
   * Configures a cell for an order out of chaos variant, for zones without creating pawns.
   *
   * @param color       The color to set for the cell.
   * @param playerNum   The player's number.
   * @param currentCell The cell to update.
   */
  private static void setOrderCellZone(Color color, int playerNum, Cell currentCell) {
    currentCell.setFlag(5);
    currentCell.setInitialPlayerNum(playerNum);
    currentCell.setZoneColor(color);
  }

  /**
   * Calculates the number of cells in a triangular player zone.
   *
   * @param playerZoneHeight The height of the player's zone.
   * @return The total number of cells in the zone.
   */
  private static int getNumOfCellsPerZone(int playerZoneHeight) {
    int counter = playerZoneHeight;
    int numOfCells = 0;
    while (counter != 0) {
      numOfCells += counter;
      counter--;
    }
    return numOfCells;
  }

  /**
   * Sets the seed value for randomization, ensuring reproducibility.
   *
   * @param seed The seed value.
   */
  public static void setSeed(int seed) {
    PlayerZoneFactory.seed = seed;
  }
}
