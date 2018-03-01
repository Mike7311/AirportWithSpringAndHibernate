package com.someairlines.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class CrewController {

	private FlightRepository flightRepository;
	
	private EmployeeRepository employeeRepository;
	
	private static final Logger logger = LogManager.getLogger(CrewController.class);
	
	@Autowired
	public CrewController(EmployeeRepository employeeRepository,
			FlightRepository flightRepository) {
		this.employeeRepository = employeeRepository;
		this.flightRepository = flightRepository;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public String crew(Model model,
			@RequestParam long flightId) {
		List<Employee> pilots = employeeRepository.findPilots();
		logger.debug("found pilots: " + pilots);
		List<Employee> operators = employeeRepository.findOperators();
		logger.debug("found operators: " + operators);
		List<Employee> navigators = employeeRepository.findNavigators();
		logger.debug("found navigators: " + navigators);
		List<Employee> attendants = employeeRepository.findFlightAttendats();
		logger.debug("found attendants: " + attendants);
		model.addAttribute("flightId", flightId);
		model.addAttribute("pilots", pilots);
		model.addAttribute("operators", operators);
		model.addAttribute("navigators", navigators);
		model.addAttribute("attendants", attendants);
		return "dispatcher/formCrew";
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String createCrew(@RequestParam long flightId,
			@RequestParam long pilotId,
			@RequestParam long navigatorId,
			@RequestParam long operatorId,
			@RequestParam List<Long> attendantIds, Model model) {
		Flight flight = flightRepository.find(flightId);
		logger.debug("Found flight: " + flight);
		Employee pilot = employeeRepository.find(pilotId);
		logger.debug("Found pilot: " + pilot);
		Employee navigator = employeeRepository.find(navigatorId);
		logger.debug("Found navigator: " + navigator);
		Employee operator = employeeRepository.find(operatorId);
		logger.debug("Found operator: " + operator);
		List<Employee> attendants = employeeRepository.find(attendantIds);
		logger.debug("Found attendants: " + attendants);
		FlightCrew crew = new FlightCrew();
		crew.setPilot(pilot);
		crew.setNavigator(navigator);
		crew.setOperator(operator);
		crew.setFlightAttendants(attendants);
		flight.setFlightCrew(crew);
		logger.debug("Created crew: " + crew);
		flightRepository.update(flight);
		return FlightController.REDIRECT;
	}
	
	@PostMapping("/free")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String freeCrew(@RequestParam long flightId) {
		Flight flight = flightRepository.find(flightId);
		logger.debug("Found flight: " + flight);
		flight.setFlightCrew(null);
		flightRepository.freeCrew(flight);
		flightRepository.update(flight);
		return FlightController.REDIRECT;
	}
}
