package com.someairlines.db;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.someairlines.entity.Flight;

public interface FlightRepository extends CrudRepository<Flight, Long> {

	@QueryHints(@QueryHint(name="org.hibernate.cacheable", value="true"))
	Iterable<Flight> findAll();
	
	@EntityGraph(value = "flight.flightCrew", type = EntityGraphType.LOAD)
	Flight findById(long id);
	
	@Query("delete FlightCrew where crew_id=:#{#flight.flightCrew.id}")
	@Modifying
	@Transactional
	void deleteCrew(@Param("flight") Flight flight);
}
