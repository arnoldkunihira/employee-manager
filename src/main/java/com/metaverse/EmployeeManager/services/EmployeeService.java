package com.metaverse.EmployeeManager.service;

import com.metaverse.EmployeeManager.exception.UserNotFoundException;
import com.metaverse.EmployeeManager.model.Employee;
import com.metaverse.EmployeeManager.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee createEmployee(Employee employee) {
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

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteEmployeeById(id);
    }
}
