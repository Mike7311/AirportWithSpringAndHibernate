package com.someairlines.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.someairlines.entity.Request;
import com.someairlines.entity.util.RequestStatus;
import com.someairlines.service.RequestService;

@RequestMapping("/request*")
@Controller
public class RequestController {

	private RequestService requestService;
	
	public static final String REDIRECT = "redirect:/request";
	
	public static final String DISPATCHERFORM = "dispatcher/requestForm";

	public RequestController(RequestService requestService) {
		this.requestService = requestService;
	}
	
	@GetMapping
	public String requestsPage(Model model) {
		String view;
		List<Request> requests;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
		if(auth.getAuthorities().contains(adminAuthority)) {
			view = "admin/requests";
			requests = requestService.findAllNew();
		} else {
			view = "dispatcher/requests";
			requests = requestService.findForUsername(auth.getName());
		}
		model.addAttribute("requests", requests);
		return view;
	}
	
	@GetMapping("/open")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String openRequest(@RequestParam long requestId, Model model) {
		Request request = requestService.find(requestId);
		model.addAttribute("request", request);
		return "admin/requestInfo";
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String processRequest(@RequestParam long requestId, @RequestParam String status) {
		Request request = requestService.find(requestId);
		RequestStatus requestStatus = RequestStatus.valueOf(status);
		request.setStatus(requestStatus);
		requestService.save(request);
		return REDIRECT;
	}
	
	@GetMapping("/write")
	@PreAuthorize("hasRole('ROLE_DISPATCHER')")
	public String writeRequest(Model model) {
		model.addAttribute("request", new Request());
		return DISPATCHERFORM;
	}
	
	@PostMapping("/write")
	@PreAuthorize("hasRole('ROLE_DISPATCHER')")
	public String writeRequest(@Valid @ModelAttribute Request request, BindingResult result) {
		if(result.hasErrors()) {
			return DISPATCHERFORM;
		}
		request.setStatus(RequestStatus.NEW);
		requestService.save(request);
		return REDIRECT;
	}
}
