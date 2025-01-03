package org.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientGUI extends Application {
  @Override
  public void start(Stage stage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1200, 700);
    new ClientGUISetup(root);

    stage.setTitle("CHINESE CHECKERS");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}