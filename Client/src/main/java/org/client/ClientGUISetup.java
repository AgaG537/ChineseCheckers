package org.client;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Sets up the GUI components for the client.
 */
public class ClientGUISetup {

  private final Client client;

  private final BorderPane mainPane;
  private final BorderPane boardPane;
  private final ScrollPane sidePane;
  private final VBox boardBox;
  private final VBox sideBox;
  private final int[] numsOfPlayers = {2,3,4,6};
  private final String[] possibleVariants = {"standard", "variant1", "variant2"};

  private String chosenVariant;
  private Integer numOfPlayers;

  /**
   * Constructs a GUI setup with the specified main pane and client.
   *
   * @param root   The main pane of the GUI.
   * @param client The client instance.
   */
  public ClientGUISetup(BorderPane root, Client client) {
    this.client = client;
    int userNum = client.getUserNumFromServer();

    mainPane = root;
    boardPane = new BorderPane();
    sidePane = new ScrollPane();
    boardBox = new VBox();
    sideBox = new VBox();

    setInitialBasicView();

    if (userNum == 0) {
      chosenVariant = "standard";
      numOfPlayers = 2;

      setFirstUserCenterPane();
      setFirstUserSidePane();
    } else {
      setWaitingCenterPane();
      setMessageSidePane();
    }
  }

  /**
   * Adds a label to the side pane.
   *
   * @param label The label to add.
   */
  public void addLabel(Label label) {
    sideBox.getChildren().add(label);
  }

  /**
   * Sets the initial view of the GUI.
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

    Label chineseCheckersLabel = new Label("Chinese Checkers");
    chineseCheckersLabel.setTextFill(Color.WHITE);
    chineseCheckersLabel.setFont(new Font("Verdana",50));
    chineseCheckersLabel.setStyle("-fx-font-weight: bold");

    boardBox.setSpacing(20);
    boardBox.getChildren().add(chineseCheckersLabel);
    boardBox.setPadding(new Insets(15));
    boardBox.setAlignment(Pos.CENTER);

    sideBox.setSpacing(20);
    sideBox.setPadding(new Insets(15));
  }

  /**
   * Sets up the center pane for the first
   * user to configure game options.
   */
  private void setFirstUserCenterPane() {
    Label infoLabel = new Label("Provide necessary game options on the left.");
    infoLabel.setTextFill(Color.WHITE);
    infoLabel.setFont(new Font("Verdana",23));

    boardBox.getChildren().add(infoLabel);
  }

  /**
   * Sets up the side pane for the first
   * user to configure game settings.
   */
  private void setFirstUserSidePane() {
    Label titleLabel = new Label("GAME SETUP");
    titleLabel.setFont(new Font("Verdana",35));
    titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black");

    Label chooseVariantLabel = new Label("Choose game variant:");
    chooseVariantLabel.setFont(new Font("Verdana",23));
    chooseVariantLabel.setStyle("-fx-text-fill: black");

    ChoiceBox<String> chooseVariantChoiceBox = new ChoiceBox<>();
    chooseVariantChoiceBox.setValue(chosenVariant);
    for (String variant : possibleVariants) {
      chooseVariantChoiceBox.getItems().add(variant);
    }
    chooseVariantChoiceBox.setOnAction(e -> chosenVariant = chooseVariantChoiceBox.getValue());
    chooseVariantChoiceBox.setMinWidth(250);
    chooseVariantChoiceBox.setCursor(Cursor.HAND);
    chooseVariantChoiceBox.setStyle("-fx-font-size : 23px;");

    Label choosePlayerNumLabel = new Label("Choose number of \nplayers:");
    choosePlayerNumLabel.setFont(new Font("Verdana",23));
    choosePlayerNumLabel.setStyle("-fx-text-fill: black");

    ChoiceBox<Integer> choosePlayerNumChoiceBox = new ChoiceBox<>();
    choosePlayerNumChoiceBox.setValue(numOfPlayers);
    for (int num : numsOfPlayers) {
      choosePlayerNumChoiceBox.getItems().add(num);
    }
    choosePlayerNumChoiceBox.setOnAction(e -> {
      numOfPlayers = choosePlayerNumChoiceBox.getValue();
    });
    choosePlayerNumChoiceBox.setMinWidth(250);
    choosePlayerNumChoiceBox.setCursor(Cursor.HAND);
    choosePlayerNumChoiceBox.setStyle("-fx-font-size : 23px;");

    Button applyButton = new Button("APPLY");
    applyButton.setMinWidth(250);
    applyButton.setCursor(Cursor.HAND);
    applyButton.setTextFill(Color.WHITE);
    applyButton.setFont(new Font("Verdana",23));
    applyButton.setStyle("-fx-background-color: #1C2541;");

    applyButton.setOnAction(e -> {
      System.out.println("Chosen variant: " + chooseVariantChoiceBox.getValue());
      System.out.println("Chosen number of players: " + choosePlayerNumChoiceBox.getValue());

      String message = choosePlayerNumChoiceBox.getValue().toString() + "," + chooseVariantChoiceBox.getValue();
      client.sendMessage(message);
      setMessageSidePane();
      setWaitingCenterPane();
    });

    sideBox.getChildren().addAll(titleLabel, chooseVariantLabel, chooseVariantChoiceBox, choosePlayerNumLabel, choosePlayerNumChoiceBox, applyButton);
  }

  /**
   * Updates the center pane with a waiting message
   * after the first user sets the game options.
   */
  private void setWaitingCenterPane() {
    Label infoLabel = new Label("Game all set up. Waiting for other players...");
    infoLabel.setTextFill(Color.WHITE);
    infoLabel.setFont(new Font("Verdana",23));

    boardBox.getChildren().add(infoLabel);
  }

  /**
   * Prepares the side pane for the
   * messages received from server.
   */
  private void setMessageSidePane() {
    sideBox.setSpacing(10);
    sideBox.getChildren().clear();

    sideBox.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        sidePane.setVvalue(newValue.doubleValue());
      }
    });
  }

  /**
   * Sets up the center pane for the
   * game board once the game starts.
   */
  public void setHexagonCentralPane() {
    Label infoLabel = new Label("Board");
    infoLabel.setTextFill(Color.WHITE);
    infoLabel.setFont(new Font("Verdana",23));

    boardBox.getChildren().clear();
    boardBox.getChildren().add(infoLabel);
  }

}
