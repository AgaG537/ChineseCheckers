package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
  @Autowired
  private GameRepository employeeRepository;

  @GetMapping
  public List<Game> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @PostMapping
  public Game createEmployee(@RequestBody Game employee) {
    return employeeRepository.save(employee);
  }
}
