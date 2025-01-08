# ChineseCheckers
Wroclaw University of Science and Technology group project for Programming Technologies.

## Project structure:
- Server folder - contains the server project.
- Client folder - contains the client project.
- Diagrams folder - contains diagrams related to the projects.

## User guide
### Requirements:
- Maven version 3.3.0+

### Start-up
1. Clone the repository.
2. Run commands in the terminal:
```
cd Server
mvn clean install exec:java
```
7. Information about server starting should appear.
8. In a separate terminal, run commands:
```
cd Client
mvn clean install javafx:run
```
9. The JavaFX window with game options should appear. As a first player - host, choose game variant, number of players and click the **APPLY** button.
10. Now you can run the Client program (commands for the Client folder given above) for the rest of the players, from separate terminals.

### Implemented rules
1. When all clients are connected, one of them will be selected at random and asked to make a move.
2. ... (_to be added_)

## Authors
- Agnieszka Głuszkiewicz (AgaG537)
- Jędrzej Sajnóg (JinerX)
