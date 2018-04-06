package com.someairlines.web;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.someairlines.db.FlightRepository;
import com.someairlines.entity.Flight;
import com.someairlines.entity.util.FlightStatus;

@Controller
@RequestMapping("/flight*")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class FlightController {

	private static final Logger logger = LogManager.getLogger(FlightController.class);
	
	private final FlightRepository flightRepository;
	
	public static final String REDIRECT = "redirect:/flight";
	
	public static final String ADD = "admin/addFlight";
	
	public static final String CONFIGURE = "admin/configureFlight";
	
	@Autowired
	public FlightController(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public String flights(Model model) {
		List<Flight> flights = flightRepository.findAll();
		logger.debug("Found flights: " + flights);
		model.addAttribute("flights", flights);
		return "flights";
	}
	
	@PostMapping(params = "addFlightPage")
	public String addFlightPage(Model model) {
		model.addAttribute("flight", new Flight());
		model.addAttribute("flightStatuses", Arrays.asList(FlightStatus.values()));
		return ADD;
	}
	
	@PostMapping("/add")
	public String addFlight(@Valid @ModelAttribute Flight flight, BindingResult result) {
		if(result.hasErrors()) {
			return ADD;
		}
		flight.setFlightStatus(FlightStatus.SCHEDULED);
		logger.debug("Flight to add: " + flight);
		flightRepository.save(flight);
		return REDIRECT;
	}
	
	@PostMapping(params = "remove")
	public String removeFlight(@RequestParam("flightId") long id){
		logger.debug("got Id: " + id);
		Flight flightToDelete = flightRepository.find(id);
		flightRepository.delete(flightToDelete);
		logger.debug("removed Flight: " + flightToDelete);
		return REDIRECT;
	}
	
	@PostMapping(params = "changeFlightPage")
	public String changeFlightPage(Model model, @RequestParam("flightId") long id) {
		logger.debug("got Id: " + id);
		Flight flightToChange = flightRepository.find(id);
		logger.debug("Flight to change: " + flightToChange);
		model.addAttribute("flight", flightToChange);
		model.addAttribute("flightStatuses", Arrays.asList(FlightStatus.values()));
		return CONFIGURE;
	}
	
	@PostMapping("/change")
	public String changeFlight(@Valid @ModelAttribute Flight flight, BindingResult result) {
		if(result.hasErrors()) {
			return CONFIGURE;
		}
		logger.debug("Got changed flight: " + flight);
		flightRepository.update(flight);
		logger.debug("Updated flight: " + flight);
		return REDIRECT;
	}
}
