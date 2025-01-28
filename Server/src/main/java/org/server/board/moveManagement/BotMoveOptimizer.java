package org.server.board.moveManagement;

import java.util.ArrayList;
import java.util.Arrays;
import org.server.board.boardObjects.Cell;

/**
 * Optimizer for calculating the best moves for bot players based on the current game board.
 */
public class BotMoveOptimizer {

  private final Cell[][] cells;
  int[][] directions;

  /**
   * Constructs a BotMoveOptimizer with the provided game board cells.
   *
   * @param cells The game board cells.
   */
  public BotMoveOptimizer(Cell[][] cells) {
    this.cells = cells;
    directions = new int[][]{
        {-1, -1}, {0, -2}, {1, -1},  // Upper left, left, bottom left
        {1, 1}, {0, 2}, {-1, 1}      // Bottom right, right, upper right
    };
  }

  /**
   * Calculates the best move for a bot based on its destination and the game board state.
   *
   * @param botNum            The bot's number.
   * @param destinationPoint  The destination point for the bot.
   * @param destinationZoneNum The destination zone for the bot.
   * @return An array representing the best move for the bot.
   */
  public synchronized int[] getBestMoveForBot(int botNum, int[] destinationPoint, int destinationZoneNum) {
    double maxDistanceFromEdgeChange = -(cells.length + cells[0].length);
    double maxMoveDistance = 0;
    int[] bestMovePositions = destinationPoint;

    for (int row = 0; row < cells.length; row++) {
      for (int col = 0; col < cells[row].length; col++) {
        if (cells[row][col].isInsideBoard() && cells[row][col].isOccupied()) {
          if (cells[row][col].getPawn().getPlayerNum() == botNum) {
            int[] bestMovePositionForPawn = getBestMovePositionForPawn(row, col, destinationPoint[0], destinationPoint[1], destinationZoneNum, botNum);

            if (!(bestMovePositionForPawn[0] == row && bestMovePositionForPawn[1] == col)) {
              double startDistanceFromEdge = getDistanceBetweenPoints(new int[]{row, col}, destinationPoint);
              double distanceFromEdgeAfterMove = getDistanceBetweenPoints(bestMovePositionForPawn, destinationPoint);
              double distanceFromEdgeChange = startDistanceFromEdge - distanceFromEdgeAfterMove;
              double moveDistance = getDistanceBetweenPoints(new int[]{row, col}, bestMovePositionForPawn);

              if (distanceFromEdgeChange > maxDistanceFromEdgeChange) {
                maxDistanceFromEdgeChange = distanceFromEdgeChange;
                maxMoveDistance = moveDistance;
                bestMovePositions = new int[]{row, col, bestMovePositionForPawn[0], bestMovePositionForPawn[1]};
              } else if (distanceFromEdgeChange == maxDistanceFromEdgeChange) {
                if (moveDistance > maxMoveDistance && canBotMoveHere(bestMovePositionForPawn, destinationZoneNum, botNum)) {
                  bestMovePositions = new int[]{row, col, bestMovePositionForPawn[0], bestMovePositionForPawn[1]};
                  maxMoveDistance = moveDistance;
                }
              }
            }
          }
        }
      }
    }
    return bestMovePositions;
  }

  /**
   * Determines whether the bot can move to a specific position.
   *
   * @param position           The position to check as an array [row, col].
   * @param destinationZoneNum The zone number of the bot's destination.
   * @param botNum             The bot's number.
   * @return true if the bot can move to the position, otherwise false.
   */
  private boolean canBotMoveHere(int[] position, int destinationZoneNum, int botNum) {
    return cells[position[0]][position[1]].getZoneNum() == 0
        || cells[position[0]][position[1]].getZoneNum() == destinationZoneNum
        || cells[position[0]][position[1]].getInitialPlayerNum() == botNum;
  }

  /**
   * Checks if a specified position is within the bounds of the game board.
   *
   * @param row The row index to check.
   * @param col The column index to check.
   * @return true if the position is inside the board, otherwise false.
   */
  private boolean isInsideBoard(int row, int col) {
    return (row >= 0 && row < cells.length && col >= 0 && col < cells[0].length)
        && cells[row][col].isInsideBoard();
  }

  /**
   * Determines the best move position for a pawn, based on the starting position,
   * the destination, and the bot's specific logic.
   *
   * @param startRow          The starting row of the pawn.
   * @param startCol          The starting column of the pawn.
   * @param destinationRow    The destination row.
   * @param destinationCol    The destination column.
   * @param destinationZoneNum The destination zone number.
   * @param botNum            The bot's number.
   * @return An array representing the best move position [row, col].
   */
  private int[] getBestMovePositionForPawn(int startRow, int startCol, int destinationRow, int destinationCol, int destinationZoneNum, int botNum) {
    ArrayList<int[]> possibleMovePositions = new ArrayList<>();
    getValidSingleSteps(startRow, startCol, possibleMovePositions, destinationZoneNum, botNum);

    ArrayList<int[]> visited = new ArrayList<>();
    getValidJumps(startRow, startCol, visited, destinationZoneNum, botNum);

    if (!visited.isEmpty()) {
      for (int[] validJump : visited) {
        if (!(validJump[0] == startRow && validJump[1] == startCol)) {
          possibleMovePositions.add(validJump);
        }
      }
    }
    if (!possibleMovePositions.isEmpty()) {

      double startDistanceFromEdge = getDistanceBetweenPoints(new int[]{startRow, startCol}, new int[]{destinationRow, destinationCol});
      double maxDistanceFromEdgeChange = startDistanceFromEdge - getDistanceBetweenPoints(possibleMovePositions.get(0), new int[]{destinationRow, destinationCol});
      double maxMoveDistance = getDistanceBetweenPoints(new int[]{startRow, startCol}, possibleMovePositions.get(0));
      int[] bestMovePositionForPawn = possibleMovePositions.get(0);

      for (int[] possibleMovePosition : possibleMovePositions) {
        double distanceFromEdgeAfterMove = getDistanceBetweenPoints(possibleMovePosition, new int[]{destinationRow, destinationCol});
        double distanceFromEdgeChange = startDistanceFromEdge - distanceFromEdgeAfterMove;
        double moveDistance = getDistanceBetweenPoints(new int[]{startRow, startCol}, possibleMovePosition);

        if (distanceFromEdgeChange > maxDistanceFromEdgeChange) {
          maxDistanceFromEdgeChange = distanceFromEdgeChange;
          bestMovePositionForPawn = possibleMovePosition;
          maxMoveDistance = moveDistance;

        } else if (distanceFromEdgeChange == maxDistanceFromEdgeChange) {
          if (moveDistance > maxMoveDistance
              && canBotMoveHere(bestMovePositionForPawn, destinationZoneNum, botNum)) {
            bestMovePositionForPawn = possibleMovePosition;
            maxMoveDistance = moveDistance;
          }
        }
      }
      return new int[]{bestMovePositionForPawn[0], bestMovePositionForPawn[1]};
    }

    return new int[]{startRow, startCol};
  }

  /**
   * Retrieves all valid single-step moves for a pawn and stores them in the provided list.
   *
   * @param startRow          The starting row of the pawn.
   * @param startCol          The starting column of the pawn.
   * @param possibleMoves     The list to store valid single-step moves.
   * @param destinationZoneNum The destination zone number.
   * @param botNum            The bot's number.
   */
  private void getValidSingleSteps(int startRow, int startCol, ArrayList<int[]> possibleMoves, int destinationZoneNum, int botNum) {
    for (int[] direction : directions) {
      int targetRow = startRow + direction[0];
      int targetCol = startCol + direction[1];
      if (isInsideBoard(targetRow, targetCol) && !cells[targetRow][targetCol].isOccupied()
          && canBotMoveHere(new int[]{targetRow, targetCol}, destinationZoneNum, botNum)) {
        possibleMoves.add(new int[]{targetRow, targetCol});
      }
    }
  }

  /**
   * Retrieves all valid jump moves for a pawn and stores them in the provided list.
   *
   * @param startRow          The starting row of the pawn.
   * @param startCol          The starting column of the pawn.
   * @param visited           The list to store visited positions during jumps.
   * @param destinationZoneNum The destination zone number.
   * @param botNum            The bot's number.
   */
  private void getValidJumps(int startRow, int startCol, ArrayList<int[]> visited, int destinationZoneNum, int botNum) {
    visited.add(new int[]{startRow, startCol});
    for (int[] direction : directions) {
      int jumpOverRow = startRow + direction[0];
      int jumpOverCol = startCol + direction[1];

      if (isInsideBoard(jumpOverRow, jumpOverCol) && cells[jumpOverRow][jumpOverCol].isOccupied()) {
        int landingRow = jumpOverRow + direction[0];
        int landingCol = jumpOverCol + direction[1];

        if (isInsideBoard(landingRow, landingCol) && !cells[landingRow][landingCol].isOccupied()
            && canBotMoveHere(new int[]{landingRow, landingCol}, destinationZoneNum, botNum)) {
          getValidMovesRecursively(landingRow, landingCol, visited, destinationZoneNum, botNum);
        }

      }
    }
  }

  /**
   * Recursively retrieves valid jump moves for a pawn and stores them in the visited list.
   *
   * @param currentRow        The current row of the pawn.
   * @param currentCol        The current column of the pawn.
   * @param visited           The list to store visited positions during recursive jumps.
   * @param destinationZoneNum The destination zone number.
   * @param botNum            The bot's number.
   */
  private void getValidMovesRecursively(int currentRow, int currentCol, ArrayList<int[]> visited, int destinationZoneNum, int botNum) {
    if (isInsideBoard(currentRow, currentCol) && !cells[currentRow][currentCol].isOccupied()
        && visited.stream().noneMatch(cell -> Arrays.equals(cell, new int[]{currentRow, currentCol}))
        && canBotMoveHere(new int[]{currentRow, currentCol}, destinationZoneNum, botNum)) {
      visited.add(new int[]{currentRow, currentCol});

      for (int[] direction : directions) {
        int jumpOverRow = currentRow + direction[0];
        int jumpOverCol = currentCol + direction[1];

        if (isInsideBoard(jumpOverRow, jumpOverCol) && cells[jumpOverRow][jumpOverCol].isOccupied()) {
          int landingRow = jumpOverRow + direction[0];
          int landingCol = jumpOverCol + direction[1];

          if (isInsideBoard(landingRow, landingCol) && !cells[landingRow][landingCol].isOccupied()
              && canBotMoveHere(new int[]{landingRow, landingCol}, destinationZoneNum, botNum)) {
            getValidMovesRecursively(landingRow, landingCol, visited, destinationZoneNum, botNum);
          }
        }
      }
    }
  }

  /**
   * Calculates the Euclidean distance between two points.
   *
   * @param startingPosition The starting position as an array [row, col].
   * @param endingPosition   The ending position as an array [row, col].
   * @return The Euclidean distance between the two points.
   */
  private double getDistanceBetweenPoints(int[] startingPosition, int[] endingPosition) {
    return Math.sqrt(Math.pow((startingPosition[0] - endingPosition[0]), 2) + Math.pow((startingPosition[1] - endingPosition[1]), 2));
  }
}
