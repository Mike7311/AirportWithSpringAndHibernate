package com.someairlines.db;

import java.util.List;

import com.someairlines.entity.Request;

public interface RequestRepository {

	void save(Request request);
	
	Request find(long id);
	
	List<Request> findAllForUser(String username);
	
	void update(Request request);

	List<Request> findAllNew();
}
