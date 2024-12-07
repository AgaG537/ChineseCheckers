package src;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
  private final List<ClientHandler> clientHandlers;
  private int maxUsers;

  /**
   * Constructs a UserManager.
   */
  public UserManager() {
    this.clientHandlers = new ArrayList<>();
    this.maxUsers = 0;
  }

  /**
   * Adds a client handler to the user manager.
   *
   * @param clientHandler The client handler to add.
   */
  public synchronized void addUser(ClientHandler clientHandler) {
    clientHandlers.add(clientHandler);
  }

  /**
   * Removes a client handler from the user manager.
   *
   * @param clientHandler The client handler to remove.
   */
  public synchronized void removeUser(ClientHandler clientHandler) {
    clientHandlers.remove(clientHandler);
  }

  /**
   * Returns the list of all connected client handlers.
   *
   * @return A list of client handlers.
   */
  public synchronized List<ClientHandler> getClientHandlers() {
    return new ArrayList<>(clientHandlers);
  }

  /**
   * Sets the maximum number of users allowed.
   *
   * @param maxUsers The maximum number of users.
   */
  public synchronized void setMaxUsers(int maxUsers) {
    this.maxUsers = maxUsers;
  }

  /**
   * Returns the maximum number of users allowed.
   *
   * @return The maximum number of users.
   */
  public synchronized int getMaxUsers() {
    return maxUsers;
  }
}
