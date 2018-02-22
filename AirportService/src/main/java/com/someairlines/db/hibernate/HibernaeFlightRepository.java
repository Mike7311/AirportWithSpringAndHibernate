package com.someairlines.db.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.someairlines.db.FlightRepository;
import com.someairlines.entity.Flight;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Repository
public class HibernaeFlightRepository implements FlightRepository{

	private final String DELETE_CREW = "DELETE FlightCrew WHERE crew_id = ?";
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public HibernaeFlightRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Flight> findAll() {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
		CriteriaQuery<Flight> criteria = builder.createQuery(Flight.class);
		Root<Flight> rootFlight = criteria.from(Flight.class);
        criteria.select(rootFlight);
        List<Flight> flights = currentSession().createQuery(criteria).getResultList();
		return flights;
	}

	@Override
	public Flight find(long id) {
		return currentSession().get(Flight.class, id);
	}

	@Override
	public void delete(Flight flight) {
		currentSession().delete(flight);
	}

	@Override
	public void save(Flight flight) {
		currentSession().persist(flight);
	}
	
	@Override
	public void update(Flight flight) {
		currentSession().update(flight);
	}
	
	@Override
	public void freeCrew(Flight flight) {
		Query query = currentSession().createQuery(DELETE_CREW);
		query.setParameter(0, flight.getId());
		query.executeUpdate();
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
}
