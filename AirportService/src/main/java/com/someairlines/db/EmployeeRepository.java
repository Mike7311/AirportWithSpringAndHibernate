package com.someairlines.db;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.someairlines.entity.Employee;

public interface EmployeeRepository {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Employee emp);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void save(Employee emp);

	Employee find(long id);
	
	List<Employee> findAll();
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void update(Employee emp);
	
	List<Employee> findPilots();
	
	List<Employee> findNavigators();
	
	List<Employee> findOperators();
	
	List<Employee> findFlightAttendats();

	List<Employee> find(List<Long> ids);
}
