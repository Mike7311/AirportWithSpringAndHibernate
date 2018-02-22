package com.someairlines.db;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.someairlines.entity.Flight;

public interface FlightRepository {

	List<Flight> findAll();
	
	Flight find(long id);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Flight flight);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void save(Flight flight);

	void update(Flight flight);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void freeCrew(Flight flight);
}
