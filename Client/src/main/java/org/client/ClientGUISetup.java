package org.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ClientGUISetup {

  private final BorderPane mainPane;
  private final int[] numsOfPlayers = {2,3,4,6};
  private final String[] possibleVariants = {"standard", "variant1", "variant2"};

  private String chosenVariant;
  private Integer numOfPlayers;

  public ClientGUISetup(BorderPane root) {
    mainPane = root;

    chosenVariant = "standard";
    numOfPlayers = 2;

    setInitialCenterPane();
    setInitialSidePane();
  }


  public void setInitialCenterPane() {
    Label chineseCheckersLabel = new Label("Chinese Checkers");
    chineseCheckersLabel.setTextFill(Color.BLACK);
    chineseCheckersLabel.setFont(new Font("Verdana",50));
    chineseCheckersLabel.setStyle("-fx-font-weight: bold");

    Label infoLabel = new Label("Provide necessary game options on the left.");
    infoLabel.setTextFill(Color.BLACK);
    infoLabel.setFont(new Font("Verdana",23));

    VBox info = new VBox();
    info.setSpacing(20);
    info.getChildren().addAll(chineseCheckersLabel, infoLabel);
    info.setPadding(new Insets(15));
    info.setAlignment(Pos.CENTER);

    BorderPane boardPane = new BorderPane();
    boardPane.setCenter(info);
    mainPane.setCenter(boardPane);
  }


  public void setInitialSidePane() {
    Label titleLabel = new Label("GAME SETUP");
    titleLabel.setTextFill(Color.BLACK);
    titleLabel.setFont(new Font("Verdana",35));
    titleLabel.setStyle("-fx-font-weight: bold");

    Label chooseVariantLabel = new Label("Choose game variant:");
    chooseVariantLabel.setTextFill(Color.BLACK);
    chooseVariantLabel.setFont(new Font("Verdana",23));

    ChoiceBox<String> chooseVariantChoiceBox = new ChoiceBox<>();
    chooseVariantChoiceBox.setValue(chosenVariant);
    for (String variant : possibleVariants) {
      chooseVariantChoiceBox.getItems().add(variant);
    }
    chooseVariantChoiceBox.setOnAction(e -> chosenVariant = chooseVariantChoiceBox.getValue());
    chooseVariantChoiceBox.setMinWidth(250);
    chooseVariantChoiceBox.setCursor(Cursor.HAND);
    chooseVariantChoiceBox.setStyle("-fx-font-size : 23px;");

    Label choosePlayerNumLabel = new Label("Choose number \nof players:");
    choosePlayerNumLabel.setTextFill(Color.BLACK);
    choosePlayerNumLabel.setFont(new Font("Verdana",23));

    ChoiceBox<Integer> choosePlayerNumChoiceBox = new ChoiceBox<>();
    choosePlayerNumChoiceBox.setValue(numOfPlayers);
    for (int numsOfPlayer : numsOfPlayers) {
      choosePlayerNumChoiceBox.getItems().add(numsOfPlayer);
    }
    choosePlayerNumChoiceBox.setOnAction(e -> {
      numOfPlayers = choosePlayerNumChoiceBox.getValue();
    });
    choosePlayerNumChoiceBox.setMinWidth(250);
    choosePlayerNumChoiceBox.setCursor(Cursor.HAND);
    choosePlayerNumChoiceBox.setStyle("-fx-font-size : 23px;");

    Button startButton = new Button("START GAME");
    startButton.setMinWidth(250);
    startButton.setCursor(Cursor.HAND);
    startButton.setTextFill(Color.WHITE);
    startButton.setFont(new Font("Verdana",23));
    startButton.setStyle("-fx-background-color: #000000;");

    startButton.setOnAction(e -> {
      System.out.println("Chosen variant: " + chooseVariantChoiceBox.getValue());
      System.out.println("Chosen number of players: " + choosePlayerNumChoiceBox.getValue());

      setWaitingSidePane();
      setWaitingCenterPane();
    });

    VBox setupOptions = new VBox();
    setupOptions.setSpacing(20);
    setupOptions.getChildren().addAll(titleLabel, chooseVariantLabel, chooseVariantChoiceBox, choosePlayerNumLabel, choosePlayerNumChoiceBox, startButton);
    setupOptions.setPadding(new Insets(15));

    BorderPane sidePane = new BorderPane();
    sidePane.setStyle("-fx-background-color: #BEB7A4");
    sidePane.setPrefWidth(300);
    sidePane.setCenter(setupOptions);

    mainPane.setLeft(sidePane);
  }


  public void setWaitingCenterPane() {
    Label boardLabel = new Label("Board");
    boardLabel.setTextFill(Color.BLACK);
    boardLabel.setFont(new Font("Verdana",50));
    boardLabel.setStyle("-fx-font-weight: bold");

    BorderPane boardPane = new BorderPane();
    boardPane.setCenter(boardLabel);

    mainPane.setCenter(boardPane);
  }


  public void setWaitingSidePane() {

    BorderPane sidePane2 = new BorderPane();
    Label waitingLabel = new Label("Waiting for other \nplayers to join...");
    waitingLabel.setTextFill(Color.BLACK);
    waitingLabel.setFont(new Font("Verdana",23));

    sidePane2.setStyle("-fx-background-color: #BEB7A4");
    sidePane2.setPrefWidth(300);
    sidePane2.setCenter(waitingLabel);

    mainPane.setLeft(sidePane2);
  }

}
