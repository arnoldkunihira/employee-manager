package com.metaverse.EmployeeManager.services;

import com.metaverse.EmployeeManager.exceptions.UserNotFoundException;
import com.metaverse.EmployeeManager.models.Employee;
import com.metaverse.EmployeeManager.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee createEmployee(Employee employee) {
        Optional<Employee> employeeByEmail = employeeRepo.findEmployeeByEmail(employee.getEmail());

        if (employeeByEmail.isPresent()) {
            throw new IllegalStateException("This email is taken");
        }

        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee showEmployee(Long id) {
        return employeeRepo.findEmployeeById(id).orElseThrow(
                () -> new UserNotFoundException("User by Id " + id + " was not found"));
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepo.findById(id).orElseThrow(
                () -> new IllegalStateException("Employee with id " + id + " does not exist")
        );

        employee.setEmail(employeeDetails.getEmail());
        employee.setImageUrl(employeeDetails.getImageUrl());
        employee.setJobTitle(employeeDetails.getJobTitle());
        employee.setName(employeeDetails.getName());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());

        return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        boolean exists = employeeRepo.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Student with id " + id + " does not exist");
        }

        employeeRepo.deleteById(id);
    }
}
