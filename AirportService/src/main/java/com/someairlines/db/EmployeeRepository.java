package com.someairlines.db;

import java.util.List;

import com.someairlines.entity.Employee;

public interface EmployeeRepository {

	void delete(Employee emp);
	
	void save(Employee emp);

	Employee find(long id);
	
	List<Employee> findAll();
	
	void update(Employee emp);
	
	List<Employee> findPilots();
	
	List<Employee> findNavigators();
	
	List<Employee> findOperators();
	
	List<Employee> findFlightAttendats();

	List<Employee> find(List<Long> ids);

}
