package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Employee;
import com.example.postgresdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @PostMapping("/employee")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId,
                                   @Valid @RequestBody Employee employeeRequest) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    employee.setName(employeeRequest.getName());
                    employee.setAge(employeeRequest.getAge());
                    employee.setJob(employeeRequest.getJob());
                    employee.setAddresses(employeeRequest.getAddresses());
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
    }

    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    employeeRepository.delete(employee);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
    }

}
