package org.server;


import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private final Socket socket;
  private final BufferedReader bufferedReader;
  private final BufferedWriter bufferedWriter;
  private final String username;
  private final GameManager gameManager;
  private final UserManager userManager;
  private final int userNum;

  /**
   * Constructs a ClientHandler with the specified socket, game manager, user manager, and user number.
   *
   * @param socket       The socket connected to the client.
   * @param gameManager  The game manager managing the game state.
   * @param userManager  The user manager managing the connected users.
   * @param userNum      The user's position in the game.
   * @throws IOException If an I/O error occurs when creating the input or output streams.
   */
  public ClientHandler(Socket socket, GameManager gameManager, UserManager userManager, int userNum) throws IOException {
    this.socket = socket;
    this.gameManager = gameManager;
    this.userManager = userManager;
    this.userNum = userNum;

    this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    this.username = bufferedReader.readLine();

    System.out.println("User " + username + " has joined.");
    userManager.addUser(this);

    if (userNum == 0) {
      requestMaxUsers();
    }
  }

  /**
   * Requests the maximum number of users from the client.
   *
   * @throws IOException If an I/O error occurs when reading from the client.
   */
  private void requestMaxUsers() throws IOException {
    sendMessage("Enter max number of users: ");
    int maxUsers = Integer.parseInt(bufferedReader.readLine());
    userManager.setMaxUsers(maxUsers);
  }

  /**
   * The main logic for the client handler.
   * Reads messages from the client and processes them.
   */
  @Override
  public void run() {
    try {
      while (socket.isConnected()) {
        String message = bufferedReader.readLine();

        if (gameManager.isGameStarted()) {
          if (userNum == gameManager.getCurrTurn()) {
            gameManager.broadcastMove(userManager.getClientHandlers(), username, message);
            gameManager.advanceTurn(userManager.getClientHandlers().size());
          } else {
            sendMessage("Wait for your turn.");
          }
        } else {
          sendMessage("Game has not started yet.");
        }
      }
    } catch (IOException e) {
      closeEverything();
    }
  }

  /**
   * Sends a message to the client.
   *
   * @param message The message to send.
   * @throws IOException If an I/O error occurs when writing to the client.
   */
  public synchronized void sendMessage(String message) throws IOException {
    bufferedWriter.write(message);
    bufferedWriter.newLine();
    bufferedWriter.flush();
  }

  /**
   * Closes all the resources (socket, bufferedReader, and bufferedWriter).
   */
  public void closeEverything() {
    try {
      socket.close();
      bufferedReader.close();
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    userManager.removeUser(this);
  }
}