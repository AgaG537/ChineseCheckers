package org.server.playerHandlers;


import org.server.board.moveManagement.BotMoveOptimizer;
import org.server.GameManager;

import java.util.ArrayList;
import static java.lang.Thread.sleep;

public class BotHandler extends PlayerHandler {
  private final BotMoveOptimizer botMoveOptimizer;
  private final GameManager gameManager;
  private final int[] destinationPoint;
  private final int destinationZoneNum;
  private int numOfPlayers;
  private int nextTurn;
  private final ArrayList<Integer> finishedPlayers;
  private boolean didIFinish;

  public BotHandler(int userNum, BotMoveOptimizer botMoveOptimizer, GameManager gameManager, int[] destinationPoint, int destinationZoneNum) {
    setUserNum(userNum);
    this.botMoveOptimizer = botMoveOptimizer;
    this.gameManager = gameManager;
    this.destinationPoint = destinationPoint;
    this.destinationZoneNum = destinationZoneNum;
    finishedPlayers = new ArrayList<>();
    didIFinish = false;
    System.out.println(destinationPoint[0] + " " + destinationPoint[1]);
  }

  @Override
  public synchronized void sendMessage(String message) throws InterruptedException {
    if (message.startsWith("User number ") || message.startsWith("Turn skipped by user:")) {
      if (!didIFinish) {
        int currTurn = nextTurn;
        advanceTurn();
        if (currTurn == userNum) {
          sleep(1500);
          makeMove();
          advanceTurn();
        }
      }
    } else if (message.startsWith("START")) {
      String options = message.substring("START.".length());
      String[] optionsTable = options.split(",");
      int currTurn = Integer.parseInt(optionsTable[2]);
      numOfPlayers = Integer.parseInt(optionsTable[0]);
      if (!didIFinish) {
        nextTurn = currTurn;
        advanceTurn();
        if (currTurn == userNum) {
          sleep(1500);
          makeMove();
          advanceTurn();
        }
      }
    } else if (message.startsWith("WIN")) {
      int playerNum = Integer.parseInt(message.substring("WIN.".length()));
      finishedPlayers.add(playerNum);
      if (userNum == playerNum) {
        didIFinish = true;
      }
    }
  }

  public synchronized void makeMove() {
    int[] bestMove = botMoveOptimizer.getBestMoveForBot(userNum, destinationPoint, destinationZoneNum);
    int startRow = bestMove[0];
    int startCol = bestMove[1];
    int endRow = bestMove[2];
    int endCol = bestMove[3];

    if (startRow != endRow || startCol != endCol) {
      String move = startRow + " " + startCol + " " + userNum + " " + endRow + " " + endCol + " 0";
      gameManager.advanceTurn(gameManager.getPlayerHandlers().size());
      gameManager.broadcastMove(userNum, move);

      int playerCheckedForWin = gameManager.checkWin();
      if (playerCheckedForWin != 0) {
        gameManager.broadcastPlayerWon(playerCheckedForWin);
        gameManager.addFinishedPlayer(playerCheckedForWin);
      }
    } else {
      gameManager.advanceTurn(gameManager.getPlayerHandlers().size());
      gameManager.broadcastSkip(userNum);
    }
  }

  private void advanceTurn() {
    do {
      if (nextTurn + 1 > numOfPlayers) {
        nextTurn = 1;
      } else {
        nextTurn++;
      }
    } while (finishedPlayers.contains(nextTurn));
  }
}
