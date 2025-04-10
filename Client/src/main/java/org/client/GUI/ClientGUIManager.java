package org.client.GUI;


import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
  private final ArrayList<Integer> finishedPlayers;
  private boolean didIFinish;
  private GameViewManager gameViewManager;

  private final BorderPane mainPane;
  private final BorderPane boardPane;
  private final ScrollPane sidePane;
  private final VBox boardBox;
  private final VBox sideBox;
  private final VBox infoBox;
  private final VBox serverMessageBox;
  private final VBox playerInfoBox;
  private final VBox currPlayerInfoBox;
  private String variant;

  /**
   * Constructs the GUI manager with the specified main pane and client instance.
   *
   * @param root   The main pane of the GUI.
   * @param client The client instance.
   */
  public ClientGUIManager(BorderPane root, Client client) {
    this.client = client;
    playerNum = client.getUserNumFromServer();
    finishedPlayers = new ArrayList<>();
    didIFinish = false;

    mainPane = root;
    boardPane = new BorderPane();
    sidePane = new ScrollPane();
    boardBox = new VBox();
    sideBox = new VBox();
    infoBox = new VBox();
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
    FirstPlayerViewManager manager = new FirstPlayerViewManager(client, boardBox, sideBox, serverMessageBox, this);
    manager.setFirstPlayerPanes();
  }

  /**
   * Sets the game variant (e.g., "standard", "order", "yinyang").
   * Used to customize gameplay based on the selected variant.
   *
   * @param variant The selected game variant.
   */
  public void setVariant(String variant) {
    this.variant = variant;
  }

  /**
   * Sets the GUI to the initial waiting state until the game starts.
   * Prepares the layout and displays messages from the server.
   */
  public void setWaitingPanes() {
    sideBox.getChildren().clear();
    sideBox.getChildren().add(serverMessageBox);
    sideBox.setPadding(new Insets(0));
    WaitingViewManager manager = new WaitingViewManager(boardBox, serverMessageBox);
    manager.setWaitingPanes();
  }

  /**
   * Configures the GUI to transition from the waiting phase to gameplay.
   * Parses game settings from the server message and initializes game components.
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
    gameViewManager = new GameViewManager(client, playerNum, boardBox, playerInfoBox, numOfPlayers, variant);
    gameViewManager.setGamePanes();

    infoBox.setStyle("-fx-border-color: black; -fx-border-insets: 5; "
        + "-fx-border-width: 3; -fx-border-style: dashed;");
    infoBox.getChildren().addAll(playerInfoBox, currPlayerInfoBox);
    serverMessageBox.setPadding(new Insets(0, 15, 0, 15));
    sideBox.getChildren().addAll(infoBox, serverMessageBox);
  }

  /**
   * Adds a message label to the server message box.
   *
   * @param message The message to display.
   */
  public void addLabel(String message) {
    Label messageLabel = new Label(message);
    messageLabel.setTextFill(Color.BLACK);
    messageLabel.setFont(new Font("Verdana", 18));
    messageLabel.setWrapText(true);
    serverMessageBox.getChildren().add(messageLabel);
  }

  /**
   * Updates the GUI to reflect the current player's turn. Adds relevant messages
   * and an option to skip the turn for the active player. Handles end-of-game scenarios
   * for finished players.
   */
  public void addCurrPlayerInfo() {
    currPlayerInfoBox.getChildren().clear();

    Label titleLabel;
    if (!didIFinish) {
      titleLabel = new Label("CURRENT TURN:");
    } else {
      titleLabel = new Label("YOU FINISHED!");
    }

    titleLabel.setStyle("-fx-font-family: Verdana; -fx-font-size: 25; "
        + "-fx-font-weight: bold; -fx-text-fill: black");

    Label messageLabel = new Label();
    messageLabel.setTextFill(Color.BLACK);
    messageLabel.setFont(new Font("Verdana", 20));
    messageLabel.setWrapText(true);
    messageLabel.setAlignment(Pos.CENTER);
    messageLabel.setPadding(new Insets(0, 0, 5, 0));

    currPlayerInfoBox.setPadding(new Insets(15));
    currPlayerInfoBox.setPrefHeight(130);

    if (!didIFinish) {
      if (currTurn == playerNum) {
        messageLabel.setText("It's your turn!");

        Button skipButton = new Button("SKIP TURN");
        skipButton.setMinWidth(125);
        skipButton.setCursor(Cursor.HAND);
        skipButton.setStyle("-fx-font-size : 15px;");
        skipButton.setOnMouseClicked(event -> client.sendMessage("SKIP"));

        currPlayerInfoBox.getChildren().addAll(titleLabel, messageLabel, skipButton);
      } else {
        messageLabel.setText("Current player: " + currTurn + "\nWait for your turn.");
        currPlayerInfoBox.getChildren().addAll(titleLabel, messageLabel);
      }

      do {
        if (currTurn + 1 > numOfPlayers) {
          currTurn = 1;
        } else {
          currTurn++;
        }
      } while (finishedPlayers.contains(currTurn));

    } else {
      String finisherNumString = getPlaceNumString();
      messageLabel.setText("You take " + finisherNumString + " place. Congratulations!");
      currPlayerInfoBox.getChildren().addAll(titleLabel, messageLabel);
    }

  }

  /**
   * Clears all messages from the server message box in the side pane.
   * This is typically used before updating the pane with new messages.
   */
  public void clearServerMessageBox() {
    serverMessageBox.getChildren().clear();
  }

  /**
   * Handles the display of a winning message when a player finishes the game.
   * Updates the finished players list and GUI to reflect the new game state.
   *
   * @param message A string message from the server indicating which player won.
   */
  public void showWin(String message) {
    int playerNum = Integer.parseInt(message.substring("WIN.".length()));
    finishedPlayers.add(playerNum);
    if (this.playerNum == playerNum) {
      gameViewManager.getBoard().setWin();
      didIFinish = true;
      addCurrPlayerInfo();
    } else {
      String finisherNumString = getPlaceNumString();
      addLabel("Player " + playerNum + " takes " + finisherNumString + " place!");
    }
  }

  /**
   * Determines the ordinal representation of a player's finishing position.
   * Converts numerical position (e.g., 1, 2, 3) into a string (e.g., "first", "second", "third").
   *
   * @return The ordinal string representation of the player's position.
   */
  private String getPlaceNumString() {
    int finisherNum = finishedPlayers.size();
    return switch (finisherNum) {
      case 1 -> "first";
      case 2 -> "second";
      case 3 -> "third";
      case 4 -> "fourth";
      case 5 -> "fifth";
      case 6 -> "sixth";
      default -> "";
    };
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

    sideBox.setSpacing(15);
    sideBox.setPadding(new Insets(15));
    sideBox.heightProperty().addListener((observable, oldValue, newValue) -> sidePane.setVvalue(newValue.doubleValue()));

    serverMessageBox.setStyle("-fx-font-size : 10px;");
  }

}
