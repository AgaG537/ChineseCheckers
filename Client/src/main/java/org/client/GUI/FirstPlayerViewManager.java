package org.client.GUI;


import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.client.Client;

/**
 * Manages the view for the first player
 * to configure game options.
 * Handles UI elements for selecting game
 * settings like variant and player count.
 */
public class FirstPlayerViewManager {

  private final Client client;

  private final VBox boardBox;
  private final VBox sideBox;

  private final int[] numsOfPlayers = {2,3,4,6};
  private final String[] possibleVariants = {"standard", "variant1", "variant2"};
  private String chosenVariant;
  private Integer numOfPlayers;
  private ClientGUIManager guiManager;

  /**
   * Constructor for the FirstPlayerViewManager class.
   *
   * @param client The client responsible for server communication.
   * @param boardBox The VBox for displaying the central pane.
   * @param sideBox The VBox for displaying the side panel.
   */
  public FirstPlayerViewManager(Client client, VBox boardBox, VBox sideBox, ClientGUIManager clientGUIManager) {
    this.client = client;
    this.boardBox = boardBox;
    this.sideBox = sideBox;
    this.guiManager = clientGUIManager;

    chosenVariant = "standard";
    numOfPlayers = 2;
  }

  /**
   * Initializes the panes for the first player setup screen.
   */
  public void setFirstPlayerPanes() {
    setFirstPlayerCenterPane();
    setFirstPlayerSidePane();
  }

  /**
   * Sets up the central pane with an informational label.
   */
  private void setFirstPlayerCenterPane() {
    Label chineseCheckersLabel = new Label("Chinese Checkers");
    chineseCheckersLabel.setTextFill(Color.WHITE);
    chineseCheckersLabel.setFont(new Font("Verdana",50));
    chineseCheckersLabel.setStyle("-fx-font-weight: bold");

    Label infoLabel = new Label("Provide necessary game options on the left.");
    infoLabel.setTextFill(Color.WHITE);
    infoLabel.setFont(new Font("Verdana",23));

    boardBox.getChildren().addAll(chineseCheckersLabel, infoLabel);
  }

  /**
   * Sets up the side pane for game settings configuration.
   */
  private void setFirstPlayerSidePane() {
    Label titleLabel = new Label("GAME SETUP");
    titleLabel.setStyle("-fx-font-family: Verdana; -fx-font-size: 35; " +
                        "-fx-font-weight: bold; -fx-text-fill: black");

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
      guiManager.setVariant(chooseVariantChoiceBox.getValue());
      System.out.println("Chosen number of players: " + choosePlayerNumChoiceBox.getValue());

      String message = choosePlayerNumChoiceBox.getValue().toString() + "," + chooseVariantChoiceBox.getValue();

      // Mamy tutaj variant
      client.sendMessage(message);
    });

    sideBox.getChildren().addAll(titleLabel, chooseVariantLabel, chooseVariantChoiceBox, choosePlayerNumLabel, choosePlayerNumChoiceBox, applyButton);
  }
}
