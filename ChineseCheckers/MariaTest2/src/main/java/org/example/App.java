package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class App  implements CommandLineRunner
{
    private final GameRepository gameRepository;
    private Board board;
    private int gameNum;
    private int moveNum;

    public App(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        board = new Board(4,4,3);
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
      try {
        gameNum = gameRepository.currentGameNum() + 1;
      }
      catch (Exception e) {
        gameNum = 0;
      }
      moveNum = 0;
      gameFromBoard(board);
      board.singleTurn();
      moveNum++;
      gameFromBoard(board);
      board.singleTurn();
      moveNum++;
      gameFromBoard(board);

      List<Game> games = gameRepository.findGamesWithMaxMoveByGameNum(1);
      for (Game game : games) {
        System.out.println(game.getCellName());
        System.out.printf("gameNum: %d, moveNum: %d\n", game.getGameNum(), game.getMoveNum());
        System.out.printf("cellRow: %d, cellCol: %d\n", game.getCellRow(), game.getCellCol());
        System.out.printf("cellPlayer: %d, cellZone: %d\n", game.getCellPlayerNum(), game.getCellZoneNum());

      }
    }

    private void gameFromBoard(Board board) {
      int counter = 0;
      for (Cell[] cellRow : board.cells) {
        for (Cell cell : cellRow) {
          Game game = new Game();
          game.setGameNum(gameNum);
          game.setMoveNum(moveNum);
          game.setCellCol(cell.getCol());
          game.setCellRow(cell.getRow());
          game.setCellPlayerNum(cell.getPlayerNum());
          game.setCellZoneNum(cell.getZoneNum());
          game.setCellName(String.format("dupa %d",counter));
          gameRepository.save(game);
          counter++;
          System.out.println(counter);
        }
      }
      System.out.println("\n\n\n");
      System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
}
