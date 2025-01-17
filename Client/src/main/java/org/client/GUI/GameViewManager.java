package org.client.GUI;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.client.Board.*;
import org.client.Client;

import java.util.ArrayList;

/**
 * Handles the layout and functionality of the game view during play.
 * Manages the central game board and the side pane for updates/messages.
 */
public class GameViewManager {

  private final Client client;
  private final int playerNum;
  private final int numOfPlayers;

  private final VBox boardBox;
  private final VBox playerInfoBox;

  private final int cellRadius; //15
  private final int constraintSize;
  private final int vGapSize;

  private final Board board;
  private final ArrayList<String> rawCommand;

  /**
   * Constructor for the GameViewManager class.
   *
   * @param client The client responsible for server communication.
   * @param playerNum The player's number.
   * @param boardBox The VBox for displaying the central game board.
   * @param playerInfoBox The VBox for displaying the side panel player info.
   * @param numOfPlayers The total number of players in the game.
   * @param variant The variant of the game being played.
   */
  public GameViewManager(Client client, int playerNum, VBox boardBox, VBox playerInfoBox, int numOfPlayers, String variant) {
    System.out.println("GameViewManager constructor " + playerNum);
    this.client = client;
    this.playerNum = playerNum;
    this.numOfPlayers = numOfPlayers;
    this.boardBox = boardBox;
    this.playerInfoBox = playerInfoBox;
    this.board = BoardFactory.createBoard(variant, 10, numOfPlayers);
    client.setBoard(board);
    while (!client.getSetupStatus()) {
      client.sendMessage("SETUP " + playerNum);
    }
    rawCommand = new ArrayList<>();

    constraintSize = board.getConstraintSize();
    cellRadius = (constraintSize * 3) / 4;
    vGapSize = (constraintSize * 2) / 3;
  }

  /**
   * Initializes the game panes (central board and side panel).
   * Configures and displays both the central game board and the side panel.
   */
  public void setGamePanes() {
    setGameCentralPane();
    setGameSidePane();
  }

  /**
   * Sets up the central pane to display the game board.
   * Configures a grid layout and populates it with clickable cells representing the board.
   */
  private void setGameCentralPane() {
    GridPane gridPane = new GridPane();
    gridPane.setVgap(vGapSize);
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setStyle("-fx-background-color: #1C2541");
    gridPane.setAlignment(Pos.CENTER);

    for (int i = 0; i < board.getBoardHeight(); i++) {
      gridPane.getRowConstraints().add(new RowConstraints(constraintSize));
      for (int j = 0; j < board.getBoardWidth(); j++) {
        if (i == 0) {
          gridPane.getColumnConstraints().add(new ColumnConstraints(constraintSize));
        }
        Cell cell = board.getCell(i, j);

        if (cell.isInsideBoard()) {
          cell.setRadius(cellRadius);
          cell.setStyle("-fx-stroke-width: 2");
          cell.setCursor(Cursor.HAND);

          cell.setStroke(cell.getZoneColor());
          cell.setFill(cell.getCurrentColor());

          cell.setOnMouseClicked(event -> {
            if (cell.isInsideBoard()) {
              String tmp;
              try {
                tmp = String.format("%d %d %d", cell.getRow(), cell.getCol(), cell.getPawn().getPlayerNum());
              } catch (NullPointerException e) {
                tmp = String.format("%d %d %d", cell.getRow(), cell.getCol(), 0);
              }
              rawCommand.add(tmp);
              if (rawCommand.size() == 2) {
                String message = rawCommand.get(0) + " " + rawCommand.get(1);
                client.sendMessage(message);
                System.out.println("Message sent: " + message);
                rawCommand.clear();
              }
              System.out.printf("Clicked on cell: row=%d, col=%d, player=%d\n", cell.getRow(), cell.getCol(), cell.getInitialPlayerNum());
            }
          });

          gridPane.add(cell, j, i);
        }
      }
    }

    boardBox.getChildren().clear();
    boardBox.getChildren().add(gridPane);
  }

  /**
   * Sets up the side pane to display messages and actions for the player.
   * Displays the player's information and provides options like skipping a turn.
   */
  private void setGameSidePane() {
    Label titleLabel = new Label("PLAYER INFO:");
    titleLabel.setStyle("-fx-font-family: Verdana; -fx-font-size: 25; "
        + "-fx-font-weight: bold; -fx-text-fill: black");

    String colorName = ColorManager.getDefaultColorString(numOfPlayers, playerNum);
    Label playerNumLabel = new Label("number: " + playerNum + "\ncolor: " + colorName);
    playerNumLabel.setTextFill(Color.BLACK);
    playerNumLabel.setFont(new Font("Verdana", 20));
    playerNumLabel.setAlignment(Pos.CENTER);
    playerNumLabel.setWrapText(true);
    playerInfoBox.setPadding(new Insets(15));
    playerInfoBox.getChildren().addAll(titleLabel, playerNumLabel);
  }

  /**
   * Function to get current board.
   *
   * @return board for the game.
   */
  public Board getBoard() {
    return board;
  }
}
