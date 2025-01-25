package org.server.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRecordRepository extends JpaRepository<MoveRecord, Long> {
  @Query("SELECT MAX(m.gameNumber) FROM MoveRecord m")
  int currentGameNum();

  @Query(value = "SELECT * FROM move_record m1 WHERE m1.game_number=:gameNum AND m1.move_number=(SELECT MAX(m2.move_number) FROM move_record m2 WHERE m2.move_number=:gameNum)", nativeQuery = true)
  List<MoveRecord> findGamesWithMaxMoveByGameNum(@Param("gameNum") int gameNum);

  @Query(value = "SELECT m.variant FROM move_record m WHERE m.game_number = :gameNum LIMIT 1", nativeQuery = true)
  String getVariantByGameNum(@Param("gameNum") int gameNum);

  @Query(value = "SELECT m.num_of_players FROM move_record m WHERE m.game_number = :gameNum LIMIT 1", nativeQuery = true)
  int getMaxUsersByGameNum(@Param("gameNum")int gameNum);
}
