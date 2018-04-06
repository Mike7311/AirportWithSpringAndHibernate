package com.someairlines.db.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.someairlines.db.RequestRepository;
import com.someairlines.entity.Request;
import com.someairlines.entity.util.RequestStatus;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Repository
public class HibernateRequestRepository implements RequestRepository {

	private final String findAllNew = "FROM Request WHERE status=:status";
	
	private final String findByUser = "FROM Request WHERE user_name=:name";
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public HibernateRequestRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void save(final Request request) {
		currentSession().persist(request);
	}

	@Transactional(readOnly = true)
	@Override
	public Request find(final long id) {
		return currentSession().get(Request.class, id);
	}

	@Override
	public void update(final Request request) {
		currentSession().update(request);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Request> findAllNew() {
		Query query = currentSession().createQuery(findAllNew);
		query.setParameter("status", RequestStatus.NEW);
		query.setHint("org.hibernate.cacheable", true);
		List<Request> requests = query.getResultList();
		return requests;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Request> findAllForUser(final String username) {
		Query query = currentSession().createQuery(findByUser);
		query.setParameter("name", username);
		query.setHint("org.hibernate.cacheable", true);
		List<Request> requests = query.getResultList();
		return requests;
	}

}
