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
@Transactional(isolation = Isolation.READ_COMMITTED)
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
	public Employee find(long id) {
		return currentSession().get(Employee.class, id);
	}

	@Override
	public void delete(Employee emp) {
		currentSession().delete(emp);
	}

	@Override
	public void save(Employee emp) {
		currentSession().persist(emp);
	}
	
	@Override
	public void update(Employee emp) {
		currentSession().update(emp);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findPilots() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.PILOT);
		List<Employee> pilots = query.getResultList();
		return pilots;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findNavigators() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.NAVIGATOR);
		List<Employee> navigators = query.getResultList();
		return navigators;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findOperators() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.OPERATOR);
		List<Employee> operators = query.getResultList();
		return operators;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> findFlightAttendats() {
		Query query = currentSession().createQuery(hql);
		query.setParameter("job", Job.FLIGHT_ATTENDANT);
		List<Employee> flightAttendants = query.getResultList();
		return flightAttendants;
	}
	
	@Override
	public List<Employee> find(List<Long> ids) {
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
        List<Employee> employees = currentSession().createQuery(criteria).getResultList();
        return employees;
	}
}
