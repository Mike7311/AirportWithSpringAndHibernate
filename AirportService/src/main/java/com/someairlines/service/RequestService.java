package com.someairlines.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.someairlines.db.RequestRepository;
import com.someairlines.entity.Request;

@Transactional
@Service
public class RequestService {

	private RequestRepository requestRepository;

	public RequestService(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	public List<Request> findAllNew() {
		return requestRepository.findAllNew();
	}

	public List<Request> findForUsername(String name) {
		return requestRepository.findAllByUsernameIs(name);
	}

	public Request find(long requestId) {
		return requestRepository.findOne(requestId);
	}

	public void save(Request request) {
		requestRepository.save(request);
	}
	
	
}
