package org.client.GUI;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.client.Board.Board;
import org.client.Board.Cell;
import org.client.Client;

/**
 * Handles the layout and functionality
 * of the game view during play.
 * Manages the central game board and
 * the side pane for updates/messages.
 */
public class GameViewManager {

  private final Client client;

  private final VBox boardBox;
  private final VBox sideBox;

  private final int cellRadius; //15
  private final int constraintSize;
  private final int vGapSize;

  private final Board board;

  /**
   * Constructor for the GameViewManager class.
   *
   * @param client The client responsible for server communication.
   * @param boardBox The VBox for displaying the central game board.
   * @param sideBox The VBox for displaying the side panel.
   */
  public GameViewManager(Client client, VBox boardBox, VBox sideBox) {
    this.client = client;
    this.boardBox = boardBox;
    this.sideBox = sideBox;
    board = new Board(10);

    constraintSize = board.getConstraintSize();
    cellRadius = (constraintSize * 3) / 4;
    vGapSize = (constraintSize * 2) / 3;
  }

  /**
   * Initializes the game panes (central board and side panel).
   */
  public void setGamePanes() {
    setGameCentralPane();
    setGameSidePane();
  }

  /**
   * Sets up the central pane to display the game board.
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
            circ.setStyle("-fx-stroke-width: 1");
            circ.setCursor(Cursor.HAND);

            circ.setStroke(cell.getInitialColor());
            circ.setFill(cell.getCurrentColor());

            circ.setOnMouseClicked(event -> {
              if (cell.isInsideBoard()) {
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
   * Sets up the side pane to display
   * messages received from the server.
   */
  private void setGameSidePane() {
    sideBox.setSpacing(10);
    sideBox.getChildren().clear();
  }
}
