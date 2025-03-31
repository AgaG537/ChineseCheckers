package org.server.playerHandlers;


import java.util.ArrayList;
import org.server.GameManager;
import org.server.board.moveManagement.BotMoveOptimizer;

import static java.lang.Thread.sleep;

/**
 * Represents a bot player in the game, with logic to make automated moves.
 */
public class BotHandler extends PlayerHandler {
  private final BotMoveOptimizer botMoveOptimizer;
  private final GameManager gameManager;
  private final int[] destinationPoint;
  private final int destinationZoneNum;
  private int numOfPlayers;
  private int nextTurn;
  private final ArrayList<Integer> finishedPlayers;
  private boolean didIFinish;

  /**
   * Constructs a BotHandler for automated gameplay.
   *
   * @param userNum          The bot's user number.
   * @param botMoveOptimizer The optimizer for deciding the bot's moves.
   * @param gameManager      The game manager handling the game logic.
   * @param destinationPoint The bot's destination point on the board.
   * @param destinationZoneNum The zone number representing the bot's destination.
   */
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

  /**
   * Sends a message to the player.
   *
   * @param message The message to be sent.
   * @throws InterruptedException If the thread is interrupted.
   */
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

  /**
   * Makes a move for the bot based on optimized strategies.
   */
  public synchronized void makeMove() {
    int[] bestMove = botMoveOptimizer.getBestMoveForBot(userNum, destinationPoint, destinationZoneNum);
    int startRow = bestMove[0];
    int startCol = bestMove[1];
    int endRow = bestMove[2];
    int endCol = bestMove[3];

    if (startRow != endRow || startCol != endCol) {
      String move = startRow + " " + startCol + " " + userNum + " " + endRow + " " + endCol + " 0";
      gameManager.advanceTurn(gameManager.getPlayerHandlers().size());
      System.out.println("bot turn: " + gameManager.getCurrTurn());
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

  /**
   * Advances the game turn for the bot.
   */
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
