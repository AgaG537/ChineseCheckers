package org.client.GUI;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.client.Board.Board;
import org.client.Board.Cell;
import org.client.Client;

public class GameViewManager {

  private final Client client;

  private final VBox boardBox;
  private final VBox sideBox;

  private final int cellRadius; //15
  private final int constraintSize;
  private final Board board;

  public GameViewManager(Client client, VBox boardBox, VBox sideBox) {
    this.client = client;
    this.boardBox = boardBox;
    this.sideBox = sideBox;
    board = new Board(10);
    cellRadius = 15;
    constraintSize = 20;
  }

  public void setGamePanes() {
    setGameCentralPane();
    setGameSidePane();
  }

  /**
   * Sets up the center pane for the
   * game board once the game starts.
   */
  private void setGameCentralPane() {
      GridPane gridPane = new GridPane();
      gridPane.setVgap(10);
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
            circ.setFill(Color.TRANSPARENT);
            circ.setStyle("-fx-stroke-width: 2");

            switch (cell.getUserNum()) {
              case 0: circ.setStroke(Color.web("#ffa64d")); break;
              case 1: circ.setStroke(Color.RED); break;
              case 2: circ.setStroke(Color.BLACK); break;
              case 3: circ.setStroke(Color.BLUE); break;
              case 4: circ.setStroke(Color.GREEN); break;
              case 5: circ.setStroke(Color.WHITE); break;
              case 6: circ.setStroke(Color.YELLOW); break;
              default: circ.setStroke(Color.TRANSPARENT);
            }

            circ.setOnMouseClicked(event -> {
              if (cell.isInsideBoard()) {
                System.out.printf("Clicked on cell: row=%d, col=%d, user=%d%n",
                    cell.getRow(), cell.getCol(), cell.getUserNum());
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
   * Prepares the side pane for the
   * messages received from server.
   */
  private void setGameSidePane() {
    sideBox.setSpacing(10);
    sideBox.getChildren().clear();
  }
}
