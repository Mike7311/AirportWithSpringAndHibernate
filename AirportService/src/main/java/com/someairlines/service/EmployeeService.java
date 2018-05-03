package com.someairlines.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.someairlines.db.EmployeeRepository;
import com.someairlines.entity.Employee;

@Transactional
@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public Iterable<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public Employee save(@Valid Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee find(long employeeId) {
		return employeeRepository.findOne(employeeId);
	}

	public void delete(Employee employeeToDelete) {
		employeeRepository.delete(employeeToDelete);
	}
	
	public List<Employee> findPilots() {
		return employeeRepository.findPilots();
	}
	
	public List<Employee> findNavigators() {
		return employeeRepository.findNavigators();
	}
	
	public List<Employee> findOperators() {
		return employeeRepository.findOperators();
	}
	
	public List<Employee> findFlightAttendants() {
		return employeeRepository.findFlightAttendants();
	}
}
