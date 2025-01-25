package org.server.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MoveRecord")
public class MoveRecordController {
  @Autowired
  private MoveRecordRepository moveRecordRepository;

  @GetMapping
  public List<MoveRecord> getMoveRecords() {return moveRecordRepository.findAll();}

  @PostMapping
  public MoveRecord createMoveRecord(@RequestBody MoveRecord moveRecord) {return moveRecordRepository.save(moveRecord);}
}
