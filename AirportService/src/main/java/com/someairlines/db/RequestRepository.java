package com.someairlines.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.someairlines.entity.Request;

public interface RequestRepository extends CrudRepository<Request, Long>{
	
	List<Request> findAllByUsernameIs(String username);
	
	@Query("from Request r where r.status='NEW'")
	List<Request> findAllNew();
}
