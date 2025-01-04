package org.client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents the graphical user interface for the client.
 */
public class ClientGUI extends Application {

  private Client client;
  private ClientGUISetup setup;

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
    setup = new ClientGUISetup(root, client);

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

        if (messageFromServer.getText().startsWith("Starting the game")) {
          setup.setHexagonCentralPane();
        }
        setup.addLabel(messageFromServer);

      }
    });
  }

  public static void main(String[] args) {
    launch();
  }
}