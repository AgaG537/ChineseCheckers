package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Query("SELECT e FROM Employee e WHERE e.id BETWEEN :start AND :end")
  List<Employee> findIdBetween(@Param("start")long start, @Param("end") long end);


  @Query("SELECT MAX(e.gameNum) FROM Employee e")
  int currentGameNum();
}
