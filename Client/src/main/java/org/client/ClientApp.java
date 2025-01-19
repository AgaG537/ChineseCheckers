package org.client;


import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.client.GUI.ClientGUIManager;

/**
 * Represents the graphical user interface for the client application.
 * Handles the connection to the server, initialization of the GUI, and
 * processing of messages received from the server.
 */
public class ClientApp extends Application {

  private Client client;
  private ClientGUIManager guiManager;

  /**
   * Entry point for the JavaFX application. Initializes the client connection,
   * sets up the graphical user interface, and starts listening for server messages.
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
   * Handles messages received from the server. Updates the GUI based on the
   * message content by delegating the processing to the command handler.
   *
   * @param message The message received from the server.
   */
  public void handleMessageFromServer(String message) {

    Platform.runLater(() -> {
      try {
        CommandHandler.handleCommand(message, guiManager);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
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