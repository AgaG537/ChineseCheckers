package org.client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.client.GUI.ClientGUIManager;
import java.io.IOException;
import java.net.Socket;

/**
 * Represents the graphical user interface for the client application.
 * Handles the connection to the server and the initialization of the GUI.
 */
public class ClientApp extends Application {

  private Client client;
  private ClientGUIManager guiManager;

  /**
   * Entry point for JavaFX application.
   *
   * @param stage The primary stage for the application.
   */
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
   * Handles messages received from the server and updates the GUI accordingly.
   *
   * @param message The message received from the server.
   */
  public void handleMessageFromServer(String message) {

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          CommandHandler.handleCommand(message, guiManager);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  /**
   * The main method to launch the JavaFX application.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    launch();
  }
}