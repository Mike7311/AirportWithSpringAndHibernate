package com.someairlines.web;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.someairlines.db.CrewUtil;
import com.someairlines.db.FlightRepository;
import com.someairlines.entity.Flight;
import com.someairlines.entity.util.FlightStatus;

@Controller
@RequestMapping("/flight*")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class FlightController {

	private final FlightRepository flightRepository;
	
	private final CrewUtil crewUtil;
	
	public static final String REDIRECT = "redirect:/flight";
	
	public static final String ADD = "admin/addFlight";
	
	public static final String CONFIGURE = "admin/configureFlight";
	
	public FlightController(FlightRepository flightRepository, CrewUtil crewUtil) {
		this.flightRepository = flightRepository;
		this.crewUtil = crewUtil;
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public String flights(Model model) {
		List<Flight> flights = flightRepository.findAll();
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
		flightRepository.save(flight);
		return REDIRECT;
	}
	
	@PostMapping(params = "remove")
	public String removeFlight(@RequestParam long flightId){
		Flight flightToDelete = flightRepository.findAndInitialize(flightId);
		if(flightToDelete.getFlightCrew() != null) {
			crewUtil.setCrewFree(flightToDelete.getFlightCrew(), true);
		}
		flightRepository.delete(flightToDelete);
		return REDIRECT;
	}
	
	@PostMapping(params = "changeFlightPage")
	public String changeFlightPage(Model model, @RequestParam long flightId) {
		Flight flightToChange = flightRepository.find(flightId);
		model.addAttribute("flight", flightToChange);
		model.addAttribute("flightStatuses", Arrays.asList(FlightStatus.values()));
		return CONFIGURE;
	}
	
	@PostMapping("/change")
	public String changeFlight(@Valid @ModelAttribute Flight flight, BindingResult result) {
		if(result.hasErrors()) {
			return CONFIGURE;
		}
		flightRepository.update(flight);
		return REDIRECT;
	}
}
