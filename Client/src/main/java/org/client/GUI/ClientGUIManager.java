package org.client.GUI;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.client.Client;

/**
 * Manages the setup and functionality of the client GUI components.
 * Includes views for the initial setup, waiting phase, and gameplay.
 */
public class ClientGUIManager {

  private final Client client;
  private final int playerNum;
  private int currTurn;
  private int numOfPlayers;

  private final BorderPane mainPane;
  private final BorderPane boardPane;
  private final ScrollPane sidePane;
  private final VBox boardBox;
  private final VBox sideBox;
  private final VBox serverMessageBox;
  private final VBox playerInfoBox;
  private final VBox currPlayerInfoBox;

  /**
   * Constructs the GUI manager with the specified main pane and client instance.
   *
   * @param root   The main pane of the GUI.
   * @param client The client instance.
   */
  public ClientGUIManager(BorderPane root, Client client) {
    this.client = client;
    playerNum = client.getUserNumFromServer();

    mainPane = root;
    boardPane = new BorderPane();
    sidePane = new ScrollPane();
    boardBox = new VBox();
    sideBox = new VBox();
    playerInfoBox = new VBox();
    currPlayerInfoBox = new VBox();
    serverMessageBox = new VBox();
    currTurn = numOfPlayers = 0;

    setInitialBasicView();

    if (playerNum == 1) {
      setFirstPlayerPanes();
    } else {
      setWaitingPanes();
    }
  }

  /**
   * Sets up the GUI for the first player, including options to configure the game.
   */
  public void setFirstPlayerPanes() {
    FirstPlayerViewManager manager = new FirstPlayerViewManager(client, boardBox, sideBox);
    manager.setFirstPlayerPanes();
  }

  /**
   * Sets the GUI to a waiting state until the game starts.
   */
  public void setWaitingPanes() {
    sideBox.getChildren().clear();
    sideBox.getChildren().add(serverMessageBox);
    WaitingViewManager manager = new WaitingViewManager(boardBox, serverMessageBox);
    manager.setWaitingPanes();
  }

  /**
   * Configures the GUI for the gameplay phase.
   *
   * @param message A message containing game configuration details from the server.
   */
  public void setGamePanes(String message) {
    sideBox.getChildren().clear();
    serverMessageBox.getChildren().clear();
    String options = message.substring("START.".length());
    String[] optionsTable = options.split(",");
    currTurn = Integer.parseInt(optionsTable[2]);
    numOfPlayers = Integer.parseInt(optionsTable[0]);
    String variant = optionsTable[1];
    GameViewManager manager = new GameViewManager(client, playerNum, boardBox, playerInfoBox, numOfPlayers, variant);
    manager.setGamePanes();

    sideBox.getChildren().addAll(playerInfoBox, currPlayerInfoBox, serverMessageBox);
  }

  /**
   * Adds a message label to the server message box.
   *
   * @param message The message to display.
   */
  public void addLabel(String message) {
    Label messageLabel = new Label(message);
    messageLabel.setTextFill(Color.BLACK);
    messageLabel.setFont(new Font("Verdana",15));
    messageLabel.setWrapText(true);
    serverMessageBox.getChildren().add(messageLabel);
  }

  /**
   * Updates the current player information box with the turn status.
   */
  public void addCurrPlayerInfo() {
    currPlayerInfoBox.getChildren().clear();

    Label messageLabel;
    if (currTurn == playerNum) {
      messageLabel = new Label("It's your turn!");
    } else {
      messageLabel = new Label("Current player number: " + currTurn + "\nWait for your turn.");
    }
    messageLabel.setTextFill(Color.BLACK);
    messageLabel.setFont(new Font("Verdana",20));
    messageLabel.setWrapText(true);
    currPlayerInfoBox.getChildren().add(messageLabel);

    if(currTurn + 1 > numOfPlayers) {
      currTurn = 1;
    } else {
      currTurn++;
    }
  }

  /**
   * Sets the initial view of the GUI, including basic layout and styling.
   */
  private void setInitialBasicView() {
    sidePane.setStyle("-fx-background: #6081A9; -fx-background-color: #6081A9");
    sidePane.setPrefWidth(300);
    sidePane.fitToWidthProperty().set(true);
    sidePane.setContent(sideBox);

    boardPane.setCenter(boardBox);
    boardPane.setStyle("-fx-background-color: #1C2541");

    mainPane.setCenter(boardPane);
    mainPane.setLeft(sidePane);

    boardBox.setSpacing(20);
    boardBox.setPadding(new Insets(15));
    boardBox.setAlignment(Pos.CENTER);

    sideBox.setSpacing(20);
    sideBox.setPadding(new Insets(15));
    sideBox.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        sidePane.setVvalue(newValue.doubleValue());
      }
    });

    serverMessageBox.setSpacing(10);
  }

}
