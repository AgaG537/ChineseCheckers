package org.client.GUI;


import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.client.Client;

import java.util.Objects;

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
  private final VBox serverMessageBox;

  private final String[] possibleVariants = {"standard", "order", "yinyang"};
  private String chosenVariant;
  private Integer numOfPlayers;
  private final ClientGUIManager guiManager;

  /**
   * Constructor for the FirstPlayerViewManager class.
   *
   * @param client The client responsible for server communication.
   * @param boardBox The VBox for displaying the central pane.
   * @param sideBox The VBox for displaying the side panel.
   * @param clientGUIManager The manager for displaying client GUI.
   */
  public FirstPlayerViewManager(Client client, VBox boardBox, VBox sideBox, VBox serverMessageBox, ClientGUIManager clientGUIManager) {
    this.client = client;
    this.boardBox = boardBox;
    this.sideBox = sideBox;
    this.serverMessageBox = serverMessageBox;
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
    chineseCheckersLabel.setFont(new Font("Verdana", 50));
    chineseCheckersLabel.setStyle("-fx-font-weight: bold");

    Label infoLabel = new Label("Provide necessary game options on the left.");
    infoLabel.setTextFill(Color.WHITE);
    infoLabel.setFont(new Font("Verdana", 23));

    boardBox.getChildren().addAll(chineseCheckersLabel, infoLabel);
  }

  /**
   * Sets up the side pane for game settings configuration.
   */
  private void setFirstPlayerSidePane() {
    Label titleLabel = new Label("GAME SETUP");
    titleLabel.setStyle("-fx-font-family: Verdana; -fx-font-size: 35; "
        + "-fx-font-weight: bold; -fx-text-fill: black");

    Label chooseVariantLabel = new Label("Choose game variant:");
    chooseVariantLabel.setFont(new Font("Verdana", 18));
    chooseVariantLabel.setStyle("-fx-text-fill: black");

    ChoiceBox<String> chooseVariantChoiceBox = new ChoiceBox<>();
    chooseVariantChoiceBox.setValue(chosenVariant);
    for (String variant : possibleVariants) {
      chooseVariantChoiceBox.getItems().add(variant);
    }
    chooseVariantChoiceBox.setOnAction(e -> chosenVariant = chooseVariantChoiceBox.getValue());
    chooseVariantChoiceBox.setMinWidth(250);
    chooseVariantChoiceBox.setCursor(Cursor.HAND);
    chooseVariantChoiceBox.setStyle("-fx-font-size : 18px;");

    Label chooseUserNumLabel = new Label("Choose number of users:");
    chooseUserNumLabel.setFont(new Font("Verdana", 18));
    chooseUserNumLabel.setStyle("-fx-text-fill: black");

    TextField userNumberTextField = new TextField();
    userNumberTextField.setMaxWidth(250);
    userNumberTextField.setStyle("-fx-font-size : 18px;");

    Label chooseBotNumLabel = new Label("Choose number of bots:");
    chooseBotNumLabel.setFont(new Font("Verdana", 18));
    chooseBotNumLabel.setStyle("-fx-text-fill: black");

    TextField botNumberTextField = new TextField();
    botNumberTextField.setMaxWidth(250);
    botNumberTextField.setStyle("-fx-font-size : 18px;");

    // Add a TextField for user input
    Label numberInputLabel = new Label("Enter a number:");
    numberInputLabel.setFont(new Font("Verdana", 18));
    numberInputLabel.setStyle("-fx-text-fill: black");

    TextField numberInputField = new TextField();
    numberInputField.setPromptText("Enter a number...");
    numberInputField.setStyle("-fx-font-size : 18px;");
    numberInputField.setMaxWidth(250);


    Button applyButton = new Button("APPLY");
    applyButton.setMinWidth(250);
    applyButton.setCursor(Cursor.HAND);
    applyButton.setTextFill(Color.WHITE);
    applyButton.setFont(new Font("Verdana", 18));
    applyButton.setStyle("-fx-background-color: #1C2541;");

    applyButton.setOnAction(e -> {
      System.out.println("Chosen variant: " + chooseVariantChoiceBox.getValue());
      guiManager.setVariant(chooseVariantChoiceBox.getValue());
      System.out.println("Chosen number of users: " + userNumberTextField.getText());
      System.out.println("Chosen number of bots: " + botNumberTextField.getText());

      String numOfUsers = userNumberTextField.getText();
      String numOfBots = botNumberTextField.getText();
      if (numOfUsers.isEmpty()) {
        numOfUsers = "0";
      }
      if (numOfBots.isEmpty()) {
        numOfBots = "0";
      }
      String message = numOfUsers + "," + numOfBots + "," + chooseVariantChoiceBox.getValue();

      // Check if the number input field is not empty and valid
      String numberInput = numberInputField.getText();
      if (!numberInput.isEmpty()) {
        try {
          int number = Integer.parseInt(numberInput);
          message = "DB" + "," +  number; // Override message with DB + number
        } catch (NumberFormatException ex) {
          System.err.println("Invalid number input: " + numberInput);
          return; // Stop further processing if input is invalid
        }
      }

      System.out.println("message sent first panes: " + message);
      client.sendMessage(message);
    });

    sideBox.getChildren().addAll(titleLabel, chooseVariantLabel, chooseVariantChoiceBox, chooseUserNumLabel, userNumberTextField,
        chooseBotNumLabel, botNumberTextField, numberInputLabel, numberInputField, applyButton, serverMessageBox);
  }
}
