package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
  @Query("SELECT MAX(g.gameNum) FROM Game g")
  int currentGameNum();

  @Query("SELECT g FROM Game g WHERE g.gameNum = :gameNum AND g.moveNum = (SELECT MAX(g2.moveNum) FROM Game g2 WHERE g2.gameNum = :gameNum)")
  List<Game> findGamesWithMaxMoveByGameNum(@Param("gameNum") int gameNum);
}
