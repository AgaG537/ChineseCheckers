# ChineseCheckers
Wroclaw University of Science and Technology group project for Programming Technologies.

## Introduction
Chinese Checkers is a strategic board game for 2–6 players, where the goal is to move all of one's pieces to the opposite corner of the board by making single-step moves or jumping over other pieces. The game comes in various variants, including a two-player version or variations with additional rules for jumping and blocking opponents' moves.

Game Settings | Start of the game
:-------------------------:|:-------------------------:
![chinese_checkers_start](https://github.com/user-attachments/assets/122d684c-14f8-4a93-b543-3d64b481076f) | ![chinese_checkers_board](https://github.com/user-attachments/assets/9a0d1908-723c-44db-a0cd-9e72ffd0ebe7)

## Project structure:
- Server folder - contains the server project.
- Client folder - contains the client project.
- Diagrams folder - contains diagrams related to the projects.

## User guide
### Requirements:
- Maven version 3.3.0+

### Start-up
1. Clone the repository:
```
git clone https://github.com/JinerX/ChineseCheckers.git
```
2. Go to the project folder
```
cd ChineseCheckers
```
3. Make sure you have MariaDB setup:
- database called "mytestdatabase"
- user called "myuser"
- password "password"
- These default values can be changed by changing Server/resources/application.properties

3. To start the Server, run commands in the terminal:
```
cd Server
mvn clean install exec:java
```
&emsp;Information about server starting should appear.

4. To run the Client, in a separate terminal, run commands:
```
cd Client
mvn clean install javafx:run
```
&emsp;The JavaFX window with game options should appear. As a host, choose game variant, number of users (human players), number of bots and click the **APPLY** button.

5. Now you can run the Client program (commands for the Client folder given above) for the rest of the players, from separate terminals.

## Implemented rules
1. When all clients are connected, one of them will be selected at random and asked to make a move. Then, the order of players' moves is determined clockwise.
2. A player's movement can consist of individual moves or jumps over other pawns (their own or other players'). A player can also choose not to make a move by pressing the **SKIP TURN** button.
3. When one of the players wins the game, an announcement is displayed. The remaining players can continue the game, competing for the remaining places (second, third, etc.).
4. To load data from the database enter a number corresponding to the number of the game you want to load. By default the starting value is 1 and is updated every game (every server restart)
### Standard version
All players start the game with their pawns in assigned zones. The task of each player is to move all their pawns to the opposite zone.
### Order Out Of Chaos
Players' pawns are randomly assigned in the center of the board. The task of each player is to bring all the pawns to their zone.
### Yin and Yang
The game involves two players, but their zones do not have to be opposite each other. The task is to reach the zone initially occupied by the opponent.

## Authors
- Agnieszka Głuszkiewicz (AgaG537)
- Jędrzej Sajnóg (JinerX)
