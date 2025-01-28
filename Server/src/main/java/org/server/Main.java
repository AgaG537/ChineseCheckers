package org.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start the server application.
 */
@SpringBootApplication
public class Main {
  /**
   * Main method for running the server application.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
