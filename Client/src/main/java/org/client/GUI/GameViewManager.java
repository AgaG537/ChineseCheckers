package org.client.GUI;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.client.Board.*;
import org.client.Client;

/**
 * Handles the layout and functionality of the game view during play.
 * Manages the central game board and the side pane for updates/messages.
 */
public class GameViewManager {

  private final Client client;
  private final int playerNum;

  private final VBox boardBox;
  private final VBox playerInfoBox;

  private final int cellRadius; //15
  private final int constraintSize;
  private final int vGapSize;

  private final Board board;

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
    this.client = client;
    this.playerNum = playerNum;
    this.boardBox = boardBox;
    this.playerInfoBox = playerInfoBox;
    board = new Board(10, playerNum, numOfPlayers, variant);

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
          Cell cell = board.getCell(i,j);

          if (cell.isInsideBoard()){
            Circle circ = new Circle(cellRadius);
            cell.setCircle(circ);

            circ.setStroke(cell.getZoneColor());
            circ.setFill(cell.getCurrentColor());

            circ.setOnMouseClicked(event -> {
              if (cell.isInsideBoard()) {
                String tmp = "%d %d %d";
                client.sendMessage(String.format(tmp,cell.getRow(),cell.getCol(),cell.getInitialPlayerNum()));
                System.out.printf("Clicked on cell: row=%d, col=%d, user=%d%n",
                    cell.getRow(), cell.getCol(), cell.getInitialPlayerNum());
              }
            });

            gridPane.add(circ, j, i);
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
    Label playerNumLabel = new Label("Your number: " + playerNum + "\nYour color: " + ColorManager.getDefaultColorString(playerNum));
    playerNumLabel.setTextFill(Color.BLACK);
    playerNumLabel.setFont(new Font("Verdana",20));
    playerNumLabel.setWrapText(true);

    Button skipButton = new Button("skip turn");
    skipButton.setMinWidth(125);
    skipButton.setCursor(Cursor.HAND);
    skipButton.setStyle("-fx-font-size : 15px;");
    skipButton.setOnMouseClicked(event -> {
      client.sendMessage("skip");
    });
    playerInfoBox.getChildren().addAll(playerNumLabel, skipButton);
  }
}
