package com.someairlines.db;

import java.util.List;

import com.someairlines.entity.Flight;

public interface FlightRepository {

	List<Flight> findAll();
	
	Flight find(long id);
	
	void delete(Flight flight);
	
	void save(Flight flight);

	void update(Flight flight);
	
	void freeCrew(Flight flight);
}
