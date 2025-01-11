package org.client.GUI;


import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Handles the layout and functionality of the view during the time
 * when players are waiting for others to join. Manages the central
 * game board and the side pane for updates/messages.
 */
public class WaitingViewManager {


  private final VBox boardBox;
  private final VBox serverMessageBox;

  /**
   * Constructor for the WaitingViewManager class.
   *
   * @param boardBox The VBox for displaying the central game board.
   * @param serverMessageBox The VBox for displaying the side panel.
   */
  public WaitingViewManager(VBox boardBox, VBox serverMessageBox) {
    this.boardBox = boardBox;
    this.serverMessageBox = serverMessageBox;
  }

  /**
   * Initializes the waiting panes (central board and side panel).
   */
  public void setWaitingPanes() {
    setWaitingCenterPane();
    setWaitingSidePane();
  }

  /**
   * Sets up the central pane with an informational label.
   */
  private void setWaitingCenterPane() {
    boardBox.getChildren().clear();

    Label chineseCheckersLabel = new Label("Chinese Checkers");
    chineseCheckersLabel.setTextFill(Color.WHITE);
    chineseCheckersLabel.setFont(new Font("Verdana",50));
    chineseCheckersLabel.setStyle("-fx-font-weight: bold");

    Label infoLabel = new Label("Game all set up. Waiting for other players to join...");
    infoLabel.setTextFill(Color.WHITE);
    infoLabel.setFont(new Font("Verdana",23));

    boardBox.getChildren().addAll(chineseCheckersLabel, infoLabel);
  }

  /**
   * Sets up the side pane to display messages received from the server.
   */
  private void setWaitingSidePane() {
    serverMessageBox.setSpacing(10);
    serverMessageBox.getChildren().clear();
  }
}
