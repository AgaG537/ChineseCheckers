package org.client.GUI;


import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.client.Client;

public class FirstPlayerViewManager {

  private final Client client;

  private final VBox boardBox;
  private final VBox sideBox;

  private final int[] numsOfPlayers = {2,3,4,6};
  private final String[] possibleVariants = {"standard", "variant1", "variant2"};
  private String chosenVariant;
  private Integer numOfPlayers;

  public FirstPlayerViewManager(Client client, VBox boardBox, VBox sideBox) {
    this.client = client;
    this.boardBox = boardBox;
    this.sideBox = sideBox;

    chosenVariant = "standard";
    numOfPlayers = 2;
  }

  public void setFirstPlayerPanes() {
    setFirstPlayerCenterPane();
    setFirstPlayerSidePane();
  }

  /**
   * Sets up the center pane for the first
   * user to configure game options.
   */
  private void setFirstPlayerCenterPane() {
    Label infoLabel = new Label("Provide necessary game options on the left.");
    infoLabel.setTextFill(Color.WHITE);
    infoLabel.setFont(new Font("Verdana",23));

    boardBox.getChildren().add(infoLabel);
  }


  /**
   * Sets up the side pane for the first
   * user to configure game settings.
   */
  private void setFirstPlayerSidePane() {
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
    });

    sideBox.getChildren().addAll(titleLabel, chooseVariantLabel, chooseVariantChoiceBox, choosePlayerNumLabel, choosePlayerNumChoiceBox, applyButton);
  }
}
