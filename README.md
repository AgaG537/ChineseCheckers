# ChineseCheckers
Wroclaw University of Science and Technology group project for Programming Technologies

## Project structure:
1. Server folder - contains the server project
2. Client folder - contains the client project
3. Diagrams folder - contains diagrams related to the projects

## User guide
### Requirements:
- Maven version 3.3.0+

### Start-up
1. Clone the repository
2. Navigate to Server folder
3. Open the folder in command line
4. Run commands
  - mvn clean
  - mvn install
6. Navigate to the newly created target folder and run the created .jar file by typing "java -jar <file-name>.jar"
7. Information about server starting should appear
8. Navigate to the Client folder
9. Run commands
  - mvn clean
  - mvn install
10. Navigate to the newly created target folder and run the created .jar file by typing "java -jar <file-name>.jar"
11. Information about client connecting to the server should appear
12. You can run the .jar file in the Client/target folder from multiple command lines in order to create multiple clients

### Implemented rules
1. After connecting the first client will be asked to specify the number of players (2,3,4,6)
2. After that the server will wait for the other clients to connect
3. After connection the client that moves first will be decided randomly and prompted to make a move
4. For now any instruction typed by the client during his turn will send a message to other clients
5. Another client will be prompted to make a move, inputs will be handled in an analogous way to the way that the first user's move was handled
6. Each user will be asked to make a move before coming back to the first user where the cycle repeats

## Authors
- Agnieszka Głuszkiewicz (AgaG537)
- Jędrzej Sajnóg (JinerX)
