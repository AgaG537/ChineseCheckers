package org.server.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRecordRepository extends JpaRepository<MoveRecord, Long> {}
