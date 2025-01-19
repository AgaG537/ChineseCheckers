package org.client;


import java.io.*;
import java.net.Socket;
import org.client.Board.Board;

/**
 * Represents a client connecting to the server. Manages communication between
 * the client and server, including message handling and game board updates.
 */
public class Client {
  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private ClientApp clientApp;
  private Board board;
  private boolean setup =  false;

  /**
   * Constructs a Client with the specified socket and GUI.
   *
   * @param socket    The socket to connect to the server.
   * @param clientApp The GUI associated with this client.
   */
  public Client(Socket socket, ClientApp clientApp) {
    try {
      this.socket = socket;
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      this.clientApp = clientApp;
    } catch (IOException e) {
      closeEverything();
    }
  }

  /**
   * Sends a message from the client to the server.
   *
   * @param message The message to send.
   */
  public void sendMessage(String message) {
    try {
      if (socket.isConnected()) {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
      closeEverything();
    }
  }

  /**
   * Sets the current board.
   *
   * @param board The current board.
   */
  public void setBoard(Board board) {
    this.board = board;
  }


  /**
   * Retrieves the user's number assigned by the server upon connection.
   * This number uniquely identifies the player within the game.
   *
   * @return The user number assigned by the server.
   */
  public int getUserNumFromServer() {
    int userNum = -1;
    if (socket.isConnected()) {
      try {
        String userNumString = bufferedReader.readLine();
        userNum = Integer.parseInt(userNumString);
      } catch (IOException e) {
        closeEverything();
      }
    }
    return userNum;
  }


  /**
   * Listens for messages from the server on a separate thread. Processes these
   * messages based on their content and updates the game state or GUI as needed.
   */
  public void listenForMessages() {
    new Thread(new Runnable() {

      @Override
      public void run() {
        String messageFromServer;

        while (socket.isConnected()) {
          try {
            messageFromServer = bufferedReader.readLine();
            synchronized (this) {
              handleLogicCommad(messageFromServer);
            }
          } catch (IOException e) {
            closeEverything();
          }
        }
      }

    }).start();
  }



  private void handleLogicCommad(String messageFromServer) {
    if (messageFromServer.startsWith("[CMD]")) {
      board.handleCommand(messageFromServer);
    } else if (messageFromServer.startsWith("[CREATE]")) {
      board.handleCreate(messageFromServer);
    } else if (messageFromServer.equals("[SETUP]")) {
      setup = true;
    } else {
      clientApp.handleMessageFromServer(messageFromServer);
    }
  }


  /**
   * Closes the socket and associated resources to terminate the connection
   * with the server safely.
   */
  public void closeEverything() {
    try {
      socket.close();
      bufferedReader.close();
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean getSetupStatus() {
    return setup;
  }
}
