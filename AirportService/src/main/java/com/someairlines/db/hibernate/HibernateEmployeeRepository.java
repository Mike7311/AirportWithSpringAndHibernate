package com.someairlines.db.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.someairlines.db.EmployeeRepository;
import com.someairlines.entity.Employee;
import com.someairlines.entity.util.Job;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class HibernateEmployeeRepository implements EmployeeRepository{

	private SessionFactory sessionFactory;

	private final String hql = "FROM Employee WHERE job=:job AND isFree='1'";
	
	@Autowired
	public HibernateEmployeeRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Employee find(final long id) {
		return currentSession().get(Employee.class, id);
	}

	@Override
	public List<Employee> find(final List<Long> ids) {
		MultiIdentifierLoadAccess<Employee> multiLoadAccess = currentSession().byMultipleIds(Employee.class);
		List<Employee> employees = multiLoadAccess.multiLoad(ids);
		return employees;
	}
	
	@Override
	public List<Employee> findAll() {
		CriteriaBuilder builder = currentSession().getCriteriaBuilder();
		CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
		Root<Employee> rootEmployee = criteria.from(Employee.class);
        criteria.select(rootEmployee);
        List<Employee> employees = currentSession()
        		.createQuery(criteria)
        		.setHint("org.hibernate.cacheable", true)
        		.getResultList();
        return employees;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findPilots() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.PILOT);
		query.setHint("org.hibernate.cacheable", true);
		List<Employee> pilots = query.getResultList();
		return pilots;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findNavigators() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.NAVIGATOR);
		query.setHint("org.hibernate.cacheable", true);
		List<Employee> navigators = query.getResultList();
		return navigators;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findOperators() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.OPERATOR);
		query.setHint("org.hibernate.cacheable", true);
		List<Employee> operators = query.getResultList();
		return operators;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findFlightAttendats() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.FLIGHT_ATTENDANT);
		query.setHint("org.hibernate.cacheable", true);
		List<Employee> flightAttendants = query.getResultList();
		return flightAttendants;
	}
	
	@Transactional(readOnly = false)
	@Override
	public void delete(final Employee emp) {
		currentSession().delete(emp);
	}

	@Transactional(readOnly = false)
	@Override
	public void save(final Employee emp) {
		currentSession().persist(emp);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void update(final Employee emp) {
		currentSession().update(emp);
	}
	
}
