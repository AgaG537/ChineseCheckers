package org.client.GUI;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.client.Client;

/**
 * Sets up the GUI components for the client.
 */
public class ClientGUIManager {

  private final Client client;

  private final BorderPane mainPane;
  private final BorderPane boardPane;
  private final ScrollPane sidePane;
  private final VBox boardBox;
  private final VBox sideBox;

  /**
   * Constructs a GUI setup with the specified main pane and client.
   *
   * @param root   The main pane of the GUI.
   * @param client The client instance.
   */
  public ClientGUIManager(BorderPane root, Client client) {
    this.client = client;
    int userNum = client.getUserNumFromServer();

    mainPane = root;
    boardPane = new BorderPane();
    sidePane = new ScrollPane();
    boardBox = new VBox();
    sideBox = new VBox();

    setInitialBasicView();

    if (userNum == 0) {
      setFirstPlayerPanes();
    } else {
      setGamePanes();
    }
  }

  public void setFirstPlayerPanes() {
    FirstPlayerViewManager manager = new FirstPlayerViewManager(client, boardBox, sideBox);
    manager.setFirstPlayerPanes();
  }

  public void setGamePanes() {
    GameViewManager manager = new GameViewManager(client, boardBox, sideBox);
    manager.setGamePanes();
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
    sideBox.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        sidePane.setVvalue(newValue.doubleValue());
      }
    });
  }

}
