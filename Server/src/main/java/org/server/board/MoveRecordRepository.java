package org.server.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for handling CRUD operations on MoveRecord entities.
 * Provides custom queries for specific game-related operations.
 */
@Repository
public interface MoveRecordRepository extends JpaRepository<MoveRecord, Long> {

  /**
   * Retrieves the highest game number currently in the database.
   *
   * @return The highest game number.
   */
  @Query("SELECT MAX(m.gameNumber) FROM MoveRecord m")
  int currentGameNum();

  /**
   * Retrieves all move records for a given game number, filtering by the highest move number.
   *
   * @param gameNum The game number to filter by.
   * @return A list of move records with the highest move number for the specified game.
   */
  @Query(value = "SELECT * FROM move_record m1 WHERE m1.game_number=:gameNum AND m1.move_number=(SELECT MAX(m2.move_number) FROM move_record m2 WHERE m2.game_number=:gameNum)", nativeQuery = true)
  List<MoveRecord> findGamesWithMaxMoveByGameNum(@Param("gameNum") int gameNum);

  /**
   * Retrieves the variant of the game for a given game number.
   *
   * @param gameNum The game number to retrieve the variant for.
   * @return The game variant.
   */
  @Query(value = "SELECT m.variant FROM move_record m WHERE m.game_number = :gameNum LIMIT 1", nativeQuery = true)
  String getVariantByGameNum(@Param("gameNum") int gameNum);

  /**
   * Retrieves the number of players for a given game number.
   *
   * @param gameNum The game number to retrieve the number of players for.
   * @return The number of players for the specified game.
   */
  @Query(value = "SELECT m.num_of_players FROM move_record m WHERE m.game_number = :gameNum LIMIT 1", nativeQuery = true)
  int getMaxUsersByGameNum(@Param("gameNum") int gameNum);

  /**
   * Retrieves the number of bots for a given game number.
   *
   * @param gameNumCpy The game number to retrieve the number of bots for.
   * @return The number of bots for the specified game.
   */
  @Query(value = "SELECT m.num_of_bots FROM move_record m WHERE m.game_number = :gameNum LIMIT 1", nativeQuery = true)
  int getMaxBotsByGameNum(@Param("gameNum") int gameNumCpy);
}
