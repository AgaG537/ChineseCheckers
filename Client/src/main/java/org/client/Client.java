package org.client;


import org.client.Board.Board;
import org.client.Board.PlayerZoneFactory;
import java.io.*;
import java.net.Socket;

/**
 * Represents a client connecting to the server.
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

  public Socket getSocket() {
    return socket;
  }


  public void setBoard(Board board) {
    this.board = board;
  }


  /**
   * Retrieves the user's number from the server.
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
   * Listens for messages from the server.
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
              if (messageFromServer.startsWith("[CMD]")) {
                board.handleCommand(messageFromServer);
              } else if (messageFromServer.startsWith("[CREATE]")) {
                board.handleCreate(messageFromServer);
              } else if (messageFromServer.equals("[SETUP]")) {
                setup = true;
              }
              else if (messageFromServer.startsWith("SEED")) {
                String[] tokens = messageFromServer.split(" ");
                PlayerZoneFactory.setSeed(Integer.parseInt(tokens[1]));
              } else {
                clientApp.handleMessageFromServer(messageFromServer);
              }
            }
          } catch (IOException e) {
            closeEverything();
          }
        }
      }

    }).start();
  }


  /**
   * Closes the socket and associated resources.
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
