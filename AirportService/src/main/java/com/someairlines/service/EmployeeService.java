package com.someairlines.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.someairlines.db.EmployeeRepository;
import com.someairlines.entity.Employee;

@Transactional(readOnly = true)
@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public Iterable<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Transactional
	public Employee save(@Valid Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee find(long employeeId) {
		return employeeRepository.findOne(employeeId);
	}

	@Transactional
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
