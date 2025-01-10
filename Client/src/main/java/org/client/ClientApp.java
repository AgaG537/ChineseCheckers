package org.client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.client.GUI.ClientGUIManager;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents the graphical user interface for the client.
 */
public class ClientApp extends Application {

  private Client client;
  private ClientGUIManager guiManager;

  @Override
  public void start(Stage stage) {

    try {
      Socket socket = new Socket("localhost", 1234);
      client = new Client(socket, this);
    } catch (IOException e) {
      client.closeEverything();
    }

    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1000, 600);
    guiManager = new ClientGUIManager(root, client);

    stage.setTitle("CHINESE CHECKERS");
    stage.setScene(scene);
    stage.show();
    client.listenForMessages();
  }

  /**
   * Displays a message received from the server on the GUI.
   *
   * @param message The message to display.
   */
  public void showMessageFromServer(String message) {

    Label messageFromServer = new Label(message);
    messageFromServer.setTextFill(Color.BLACK);
    messageFromServer.setFont(new Font("Verdana",15));
    messageFromServer.setWrapText(true);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {

        if (message.equals("Game options correct.")) {
          guiManager.setWaitingPanes();
        } else if (message.startsWith("Starting the game!")) {
          String options = message.substring("Starting the game! Wait for an announcement about your turn.".length());
          String[] optionsTable = options.split(",");
          guiManager.setGamePanes(Integer.parseInt(optionsTable[0]), optionsTable[1]);
          messageFromServer.setText("Starting the game! Wait for an announcement about your turn.");
        }

        guiManager.addLabel(messageFromServer);

      }
    });
  }

  public static void main(String[] args) {
    launch();
  }
}