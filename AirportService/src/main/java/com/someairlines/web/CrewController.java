package com.someairlines.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.someairlines.db.CrewUtil;
import com.someairlines.db.EmployeeRepository;
import com.someairlines.db.FlightRepository;
import com.someairlines.entity.Employee;
import com.someairlines.entity.Flight;
import com.someairlines.entity.FlightCrew;

/**
 * @author Kotkov Mikhail
 *
 */
@Controller
@RequestMapping("/crew*")
@PreAuthorize("hasRole('ROLE_DISPATCHER')")
public class CrewController {

	private FlightRepository flightRepository;
	
	private EmployeeRepository employeeRepository;
	
	private CrewUtil crewUtil;
	
	public CrewController(EmployeeRepository employeeRepository,
			FlightRepository flightRepository, CrewUtil crewUtil) {
		this.employeeRepository = employeeRepository;
		this.flightRepository = flightRepository;
		this.crewUtil = crewUtil;
	}
	
	@GetMapping
	public String crew(Model model,
			@RequestParam long flightId) {
		List<Employee> pilots = employeeRepository.findPilots();
		List<Employee> operators = employeeRepository.findOperators();
		List<Employee> navigators = employeeRepository.findNavigators();
		List<Employee> attendants = employeeRepository.findFlightAttendats();
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
			@RequestParam List<Long> attendantIds, Model model) {
		Flight flight = flightRepository.find(flightId);
		Employee pilot = employeeRepository.find(pilotId);
		Employee navigator = employeeRepository.find(navigatorId);
		Employee operator = employeeRepository.find(operatorId);
		List<Employee> attendants = employeeRepository.find(attendantIds);
		FlightCrew crew = new FlightCrew();
		crew.setPilot(pilot);
		crew.setNavigator(navigator);
		crew.setOperator(operator);
		crew.setFlightAttendants(attendants);
		crewUtil.setCrewFree(crew, false);
		flight.setFlightCrew(crew);
		flightRepository.update(flight);
		return FlightController.REDIRECT;
	}
	
	@PostMapping("/free")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String freeCrew(@RequestParam long flightId) {
		Flight flight = flightRepository.findAndInitialize(flightId);
		if(flight.getFlightCrew() == null) {
			return FlightController.REDIRECT;
		}
		crewUtil.setCrewFree(flight.getFlightCrew(), true);
		flightRepository.deleteCrew(flight);
		return FlightController.REDIRECT;
	}
}
