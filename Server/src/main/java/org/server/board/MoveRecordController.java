package org.server.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling HTTP requests related to MoveRecord entities.
 * Provides endpoints for retrieving and creating move records.
 */
@RestController
@RequestMapping("/MoveRecord")
public class MoveRecordController {

  @Autowired
  private MoveRecordRepository moveRecordRepository;

  /**
   * Returns a list of all move records.
   *
   * @return A list of all MoveRecord entities.
   */
  @GetMapping
  public List<MoveRecord> getMoveRecords() {
    return moveRecordRepository.findAll();
  }

  /**
   * Creates a new move record.
   *
   * @param moveRecord The move record to create.
   * @return The created move record.
   */
  @PostMapping
  public MoveRecord createMoveRecord(@RequestBody MoveRecord moveRecord) {
    return moveRecordRepository.save(moveRecord);
  }
}
