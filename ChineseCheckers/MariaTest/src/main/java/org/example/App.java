package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;

    // Constructor for dependency injection
    public App(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Create and save two employee records
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setDepartment("HR");
        emp1.setId(1L);  // Assigning ID explicitly (just for illustration)
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setDepartment("IT");
        emp2.setId(2L);  // Assigning ID explicitly (just for illustration)

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);

        // Access and print employees with IDs between 5 and 10
        List<Employee> employees = employeeRepository.findIdBetween(5L, 10L);
        employees.forEach(employee -> {
            System.out.println("Employee ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println();
        });
    }
}
