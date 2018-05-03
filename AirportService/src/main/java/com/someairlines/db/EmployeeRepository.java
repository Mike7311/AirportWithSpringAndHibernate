package com.someairlines.db;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.someairlines.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	@QueryHints(@QueryHint(name="org.hibernate.cacheable", value="true"))
	Iterable<Employee> findAll();
	
	@QueryHints(@QueryHint(name="org.hibernate.cacheable", value="true"))
	@Query("from Employee e where e.job = 'PILOT'")
	List<Employee> findPilots();
	
	@QueryHints(@QueryHint(name="org.hibernate.cacheable", value="true"))
	@Query("from Employee e where e.job = 'NAVIGATOR'")
	List<Employee> findNavigators();
	
	@QueryHints(@QueryHint(name="org.hibernate.cacheable", value="true"))
	@Query("from Employee e where e.job = 'OPERATOR'")
	List<Employee> findOperators();
	
	@QueryHints(@QueryHint(name="org.hibernate.cacheable", value="true"))
	@Query("from Employee e where e.job = 'FLIGHT_ATTENDANT'")
	List<Employee> findFlightAttendants();
	
	@Query("update Employee e set e.isFree=:status where e.id in(:ids)")
	@Modifying
	void setStatuses(@Param("ids") List<Long> ids, @Param("status") boolean status);

}
