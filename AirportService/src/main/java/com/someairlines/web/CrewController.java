package com.someairlines.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.someairlines.entity.Employee;
import com.someairlines.service.EmployeeService;
import com.someairlines.service.FlightService;

/**
 * @author Kotkov Mikhail
 *
 */
@Controller
@RequestMapping("/crew*")
@PreAuthorize("hasRole('ROLE_DISPATCHER')")
public class CrewController {

	private FlightService flightService;
	
	private EmployeeService employeeService;
	
	public CrewController(EmployeeService employeeService,
			FlightService flightService) {
		this.employeeService = employeeService;
		this.flightService = flightService;
	}
	
	@GetMapping
	public String crew(Model model,
			@RequestParam long flightId) {
		List<Employee> pilots = employeeService.findPilots();
		List<Employee> operators = employeeService.findOperators();
		List<Employee> navigators = employeeService.findNavigators();
		List<Employee> attendants = employeeService.findFlightAttendants();
		model.addAttribute("flightId", flightId);
		model.addAttribute("pilots", pilots);
		model.addAttribute("operators", operators);
		model.addAttribute("navigators", navigators);
		model.addAttribute("attendants", attendants);
		return "dispatcher/formCrew";
	}
	
	@PostMapping("/create")
	public String createCrew(@RequestParam long flightId,
			@RequestParam long pilotId,
			@RequestParam long navigatorId,
			@RequestParam long operatorId,
			@RequestParam List<Long> attendantIds) {
		flightService.createCrew(flightId, pilotId, navigatorId, operatorId
				,attendantIds);
		return FlightController.REDIRECT;
	}
	
	@PostMapping("/free")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String freeCrew(@RequestParam long flightId) {
		flightService.deleteCrew(flightId);
		return FlightController.REDIRECT;
	}
}
