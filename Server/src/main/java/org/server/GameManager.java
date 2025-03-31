package org.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.server.board.*;
import org.server.board.boardManagement.Board;
import org.server.board.boardManagement.BoardFactory;
import org.server.board.boardObjects.Cell;
import org.server.board.boardObjects.Pawn;
import org.server.board.moveManagement.BotMoveOptimizer;
import org.server.board.moveManagement.MoveValidator;
import org.server.playerHandlers.BotHandler;
import org.server.playerHandlers.ClientHandler;
import org.server.playerHandlers.PlayerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Manages the state of the game, including player turns, broadcasting messages,
 * and ensuring synchronization between connected clients.
 * Acts as a central hub for game-related logic.
 */
@Component
public class GameManager {
  private final List<PlayerHandler> playerHandlers;
  private int maxUsers;
  private int maxBots;
  private int currTurn;
  private boolean gameStarted;
  private Board currentBoard;
  private MoveValidator moveValidator;
  private String variant;
  private final ArrayList<Integer> finishedPlayers;
  private Server server;
  private int moveNum;
  private int gameNum;
  private boolean fromDatabase;
  public int gameNumCpy;

  @Autowired
  private MoveRecordRepository moveRecordRepository;


  /**
   * Constructs a GameManager.
   */
  public GameManager() {
    this.playerHandlers = new ArrayList<>();
    this.maxUsers = 0;
    this.currTurn = 0;
    this.gameStarted = false;
    finishedPlayers = new ArrayList<>();
    moveNum = 0;
    currentBoard = null;
    fromDatabase = false;
    maxBots = 0;
//    moveRecordRepository = null;
  }

  /**
   * Sets the current server for the game.
   *
   * @param server The server instance to set.
   */
  public void setServer(Server server) {
    this.server = server;
  }

  /**
   * Sets the current game number.
   *
   * @param gameNum The number of the current game.
   */
  public void setGameNum(int gameNum) {
    this.gameNum = gameNum;
  }

//  public void setMoveRecordRepository(MoveRecordRepository moveRecordRepository) {
//    this.moveRecordRepository = moveRecordRepository;
//  }

  /**
   * Sets the current board for the game.
   *
   * @param board The board instance to set.
   */
  public void setBoard(Board board) {
    this.currentBoard = board;
  }

  public Board getBoard() {
    return currentBoard;
  }

  /**
   * Sets the move validator for the game.
   *
   * @param cells A 2D array of cells representing the board's current state.
   */
  public void setMoveValidator(Cell[][] cells) {
    this.moveValidator = new MoveValidator(cells);
  }

  public MoveValidator getMoveValidator() {
    return moveValidator;
  }

  /**
   * Adds a player handler to the ArrayList and notifies the client of the selected variant.
   *
   * @param playerHandler The player handler to add.
   */
  public synchronized void addPlayer(PlayerHandler playerHandler) {
    playerHandlers.add(playerHandler);
    try {
      playerHandler.sendMessage("VARIANT " + variant);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the game variant for the current session.
   *
   * @param variant The variant to set (e.g., "standard", "order", "yinyang").
   */
  public void setVariant(String variant) {
    this.variant = variant;
  }

  /**
   * Retrieves the currently selected game variant.
   *
   * @return The selected game variant as a string.
   */
  public String getVariant() {
    return variant;
  }

  /**
   * Removes a player handler from the ArrayList.
   *
   * @param playerHandler The player handler to remove.
   */
  public synchronized void removePlayer(PlayerHandler playerHandler) {
    playerHandlers.remove(playerHandler);
  }

  /**
   * Returns the list of all player handlers.
   *
   * @return A list of player handlers.
   */
  public synchronized List<PlayerHandler> getPlayerHandlers() {
    return new ArrayList<>(playerHandlers);
  }

  /**
   * Sets the maximum number of users allowed.
   *
   * @param maxUsers The maximum number of users.
   */
  public synchronized void setMaxUsers(int maxUsers) {
    this.maxUsers = maxUsers;
  }

  /**
   * Returns the maximum number of users allowed.
   *
   * @return The maximum number of users.
   */
  public synchronized int getMaxUsers() {
    if (fromDatabase) {
      maxUsers = moveRecordRepository.getMaxUsersByGameNum(gameNumCpy);
    }
    return maxUsers;
  }

  /**
   * Returns the maximum number of bots allowed.
   *
   * @return The maximum number of bots.
   */
  public synchronized int getMaxBots() {
    return maxBots;
  }

  /**
   * Sets the maximum number of bots allowed.
   *
   * @param maxBots The maximum number of bots.
   */
  public synchronized void setMaxBots(int maxBots) {
    this.maxBots = maxBots;
  }

  /**
   * Starts the game.
   * Sets the game state to started.
   */
  public synchronized void startGame() {
    this.gameStarted = true;
  }

  /**
   * Returns whether the game has started.
   *
   * @return true if the game is started, false otherwise.
   */
  public synchronized boolean isGameStarted() {
    return gameStarted;
  }

  /**
   * Advances the turn to the next player.
   *
   * @param playerCount The total number of players in the game.
   */
  public synchronized void advanceTurn(int playerCount) {
    if (finishedPlayers.isEmpty()) {
      if (currTurn + 1 > playerCount) {
        currTurn = 1;
      } else {
        currTurn++;
      }
    } else {
      if (playerCount == finishedPlayers.size()) {
        broadcastGameFinished();
      } else {
        do {
          if (currTurn + 1 > playerCount) {
            currTurn = 1;
          } else {
            currTurn++;
          }
        } while (finishedPlayers.contains(currTurn));
      }
    }
  }

  /**
   * Sets the current player's turn index to random one.
   *
   */
  public synchronized void setRandomTurn() {
    currTurn = (int) ((Math.random() * getMaxUsers()) + 1);
  }

  /**
   * Returns the current player's turn index.
   *
   * @return The index of the current player's turn.
   */
  public synchronized int getCurrTurn() {
    return currTurn;
  }


  /**
   * Broadcasts a message about the number of users still needed.
   */
  public synchronized void broadcastNumOfUsers() {
    for (PlayerHandler playerHandler : playerHandlers) {
      try {
        int missingNumOfUsers = maxUsers - playerHandlers.size();
        playerHandler.sendMessage("Waiting for " + missingNumOfUsers + " more player(s).");
      } catch (Exception e) {
        if (playerHandler instanceof ClientHandler) {
          ((ClientHandler) playerHandler).closeEverything();
        }
      }
    }
  }

  /**
   * Broadcasts a message that the game has started.
   */
  public synchronized void broadcastGameStarted() {
    int numOfPlayers = getMaxUsers() + getMaxBots();
    for (PlayerHandler playerHandler : playerHandlers) {
      try {
        playerHandler.sendMessage("START." + numOfPlayers + "," + "currTurn" + "," + getCurrTurn());
      } catch (Exception e) {
        if (playerHandler instanceof ClientHandler) {
          ((ClientHandler) playerHandler).closeEverything();
        }
      }
    }
  }

  /**
   * Broadcasts a message that the game has finished.
   */
  public synchronized void broadcastGameFinished() {
    for (PlayerHandler playerHandler : playerHandlers) {
      try {
        playerHandler.sendMessage("GAME FINISHED!");
      } catch (Exception e) {
        if (playerHandler instanceof ClientHandler) {
          ((ClientHandler) playerHandler).closeEverything();
        }
      }
    }
  }

  /**
   * Checks if the game is initialized from the database.
   *
   * @return true if the game is initialized from the database, false otherwise.
   */
  public boolean isFromDatabase() {
    System.out.println("fromDatabase: " + fromDatabase);
    return fromDatabase;
  }

  /**
   * Broadcasts a move made by a player to all clients.
   *
   * @param userNum The username of the player making the move.
   * @param input The input made by the player.
   */
  public synchronized void broadcastMove(int userNum, String input) {
    if (!gameStarted) {
      return;
    }
    if (moveRecordRepository == null) {
      System.out.println("MoveRecordRepository is null!");
    } else {
      System.out.println("MoveRecordRepository is ready!");
    }


    String move = moveValidator.makeMove(input);
    for (PlayerHandler playerHandler : playerHandlers) {
      try {
        if (!Objects.equals(playerHandler.getUserNum(), userNum)) {
          playerHandler.sendMessage("User number " + userNum + " moved: " + move);
        } else {
          if (playerHandler instanceof ClientHandler) {
            playerHandler.sendMessage("You just moved");
          }
        }
        playerHandler.sendMessage(move);

      } catch (Exception e) {
        if (playerHandler instanceof ClientHandler) {
          ((ClientHandler) playerHandler).closeEverything();
        }
      }
    }
    saveRecords();
  }

  /**
   * Saves the current game records to the database.
   */
  public void saveRecords() {
    for (Cell[] cellRow : currentBoard.getCells()) {
      for (Cell cell : cellRow) {
        MoveRecord mr = new MoveRecord();
        try {
          mr.setGameNumber(gameNum);
        } catch (Exception e) {
          mr.setGameNumber(0);
        }
        mr.setMoveNumber(moveNum);
        mr.setCellZoneNumber(cell.getZoneNum());
        mr.setCellColumnNumber(cell.getCol());
        mr.setCellRowNumber(cell.getRow());
        mr.setVariant(variant);
        mr.setNumOfPlayers(maxUsers);
        mr.setNumOfBots(maxBots);
        try {
          mr.setCellPlayerNumber(cell.getPawn().getPlayerNum());
        } catch (NullPointerException e) {
          mr.setCellPlayerNumber(0);
        }
        moveRecordRepository.save(mr);
      }
    }
    moveNum++;
  }

  /**
   * Prints the saved game records to the console.
   */
  public void printRecords() {
    List<MoveRecord> records = moveRecordRepository.findGamesWithMaxMoveByGameNum(1);
    System.out.println("Records size: " + records.size());
    for (MoveRecord record : records) {
      System.out.printf("gameNum: %d, moveNum: %d\n", record.getGameNumber(), record.getMoveNumber());
      System.out.printf("cellRow: %d, cellCol: %d\n", record.getCellRowNumber(), record.getCellColumnNumber());
      System.out.printf("cellZoneNumber: %d\n", record.getCellZoneNumber());
      System.out.printf("cellPlayerNumber: %d\n", record.getCellPlayerNumber());
    }
  }

  /**
   * Broadcasts a message that the turn has been skipped by a user.
   *
   * @param userNum The user number of the player who skipped their turn.
   */
  public synchronized void broadcastSkip(int userNum) {
    if (!gameStarted) {
      return;
    }

    for (PlayerHandler playerHandler : playerHandlers) {
      try {
        if (!Objects.equals(playerHandler.getUserNum(), userNum)) {
          playerHandler.sendMessage("Turn skipped by user: " + userNum);
        } else {
          if (playerHandler instanceof ClientHandler) {
            playerHandler.sendMessage("You just skipped");
          }
        }
      } catch (Exception e) {
        if (playerHandler instanceof ClientHandler) {
          ((ClientHandler) playerHandler).closeEverything();
        }
      }
    }
  }

  /**
   * Broadcasts a message that a player has won.
   *
   * @param playerNum The user number of the player who won.
   */
  public synchronized void broadcastPlayerWon(int playerNum) {
    for (PlayerHandler playerHandler : playerHandlers) {
      try {
        playerHandler.sendMessage("WIN." + playerNum);
      } catch (Exception e) {
        if (playerHandler instanceof ClientHandler) {
          ((ClientHandler) playerHandler).closeEverything();
        }
      }
    }
  }

  /**
   * Validates a move made by a player based on the current game state.
   *
   * @param userNum The player's user number.
   * @param input   The move input provided by the player.
   * @return 0 if the move is valid, 1 if the move is invalid.
   */
  public int validateMove(int userNum, String input) {
    System.out.println("received move");
    System.out.println(input);
    System.out.println(input.equals("SKIP"));
    if (input.equals("SKIP")) {
      System.out.println("returning 2");
      return 2;
    }
    boolean valid = moveValidator.validateMove(userNum, input);
    System.out.println("you've been validated");
    if (valid) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * Checks if there is a winning condition on the current board.
   *
   * @return The user number of the winning player, or 0 if no winner yet.
   */
  public int checkWin() {
    return currentBoard.checkWinCondition();
  }

  /**
   * Adds a player to the list of finished players.
   * If all players have finished, broadcasts a game-finished message to all clients.
   *
   * @param playerNum The player number to mark as finished.
   */
  public void addFinishedPlayer(int playerNum) {
    finishedPlayers.add(playerNum);
    if (finishedPlayers.size() == playerHandlers.size()) {
      broadcastGameFinished();
    }
  }

  /**
   * Broadcasts a message about the cell to be created in clients' GUI.
   */
  public synchronized void broadcastBoardCreate() {
    for (PlayerHandler playerHandler : playerHandlers) {
      for (Cell[] cellRow : currentBoard.getCells()) {
        for (Cell cell : cellRow) {
          try {
            if (playerHandler instanceof ClientHandler) {
              playerHandler.sendMessage(currentBoard.makeCreate(cell.getRow(), cell.getCol()));
            }
          } catch (Exception e) {
            if (playerHandler instanceof ClientHandler) {
              ((ClientHandler) playerHandler).closeEverything();
            }
          }
        }
      }
    }
  }

  /**
   * Initializes the game state from the database.
   *
   * @param gameNum The game number to initialize.
   */
  public void initFromDatabase(int gameNum) {
    System.out.println("Init from database, gameNum: " + gameNum);
    fromDatabase = true;
    gameNumCpy = gameNum;
  }

  /**
   * Creates the game state from the database.
   *
   * @param gameNumCpy The game number used to fetch data for recreation.
   */
  public void createFromDatabase(int gameNumCpy) {
    Random random = new Random();
    System.out.println("creating from database");
    System.out.println("gameNum: " + gameNumCpy);
    System.out.println("getting variant");
    variant = moveRecordRepository.getVariantByGameNum(gameNumCpy);
    System.out.println("variant: " + variant);
    System.out.println("getting max users");
    maxUsers = moveRecordRepository.getMaxUsersByGameNum(gameNumCpy);
    System.out.println("maxUsers: " + maxUsers);
    System.out.println("creating board");
    try {
      maxBots = moveRecordRepository.getMaxBotsByGameNum(gameNumCpy);
    } catch (Exception e) {
      maxBots = 0;
    }
    System.out.println(
        "-------------Creating from database----------------"
            + "\ngameNum: " + gameNumCpy
            + "\nvariant: " + variant
            + "\nmaxUsers: " + maxUsers
            + "\nmaxBots: " + maxBots
    );

    Board board = BoardFactory.createBoard(10, maxUsers + maxBots, variant, random.nextInt(1000000));
    System.out.println("board created ");
    board.removePawns();
    Cell[][] cells = board.getCells();

    System.out.println("gameNumCpy: " + gameNumCpy);
    List<MoveRecord> moveRecords = moveRecordRepository.findGamesWithMaxMoveByGameNum(gameNumCpy);

    System.out.printf("length of moveRecords: %d\n", moveRecords.size());
    for (MoveRecord moveRecord : moveRecords) {
      System.out.println("working...");
      int playerNum = moveRecord.getCellPlayerNumber();
      int cellRowNum = moveRecord.getCellRowNumber();
      int cellColNum = moveRecord.getCellColumnNumber();
      int cellZoneNum = moveRecord.getCellZoneNumber();
      if (playerNum != 0) {
        Pawn pawn = new Pawn(playerNum, cells[cellRowNum][cellColNum]);
        cells[cellRowNum][cellColNum].pawnMoveIn(pawn);
      }
      cells[cellRowNum][cellColNum].setZoneNum(cellZoneNum);
    }
    board.setCells(cells);
    System.out.println("setting board");
    currentBoard = board;
    System.out.println("board set");
  }

  /**
   * Initializes bots for the game to match the required number of players.
   */
  public void initializeBots() {
    while (getPlayerHandlers().size() < maxUsers + maxBots) {
      int botNum = getPlayerHandlers().size() + 1;
      int[] destinationPoint = getBoard().getDestinationPoint(botNum);
      int destinationZoneNum = getBoard().getDestinationZoneNum(botNum);
      BotMoveOptimizer botMoveOptimizer = new BotMoveOptimizer(getBoard().getCells());
      BotHandler botHandler = new BotHandler(botNum, botMoveOptimizer, this, destinationPoint, destinationZoneNum);
      addPlayer(botHandler);
    }
  }
}
