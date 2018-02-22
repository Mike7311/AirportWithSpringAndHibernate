package com.someairlines.db;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.someairlines.entity.Request;

public interface RequestRepository {

	void save(Request request);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	Request find(long id);
	
	List<Request> findAllForUser(String username);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void update(Request request);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	List<Request> findAllNew();
}
