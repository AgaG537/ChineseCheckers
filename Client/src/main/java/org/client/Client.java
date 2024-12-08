package org.client;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Represents a client connecting to the server.
 */
public class Client {
  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;

  /**
   * Constructs a Client with the specified socket.
   *
   * @param socket The socket to connect to the server.
   */
  public Client(Socket socket) {
    try {
      this.socket = socket;
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    } catch (IOException e) {
      closeEverything();
    }
  }

  /**
   * Sends messages from the client to the server.
   */
  public void sendMessage() {
    try {
      Scanner scanner = new Scanner(System.in);
      while (socket.isConnected()) {
        String messageToSend = scanner.nextLine();
        bufferedWriter.write(messageToSend);
        bufferedWriter.newLine();
        bufferedWriter.flush();
      }
    } catch (IOException e) {
      closeEverything();
    }
  }

  /**
   * Listens for messages from the server.
   */
  public void listenForMessages() {
    new Thread(() -> {
      String messageFromServer;

      while (socket.isConnected()) {
        try {
          messageFromServer = bufferedReader.readLine();
          System.out.println(messageFromServer);
        } catch (IOException e) {
          closeEverything();
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

  /**
   * The entry point of the application to start the client.
   *
   * @param args Command-line arguments.
   * @throws IOException If an I/O error occurs when creating the client socket.
   */
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 1234);
    Client client = new Client(socket);
    client.listenForMessages();
    client.sendMessage();
  }
}
