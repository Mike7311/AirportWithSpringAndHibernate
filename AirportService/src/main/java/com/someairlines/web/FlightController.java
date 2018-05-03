package com.someairlines.web;

import java.util.Arrays;

import javax.servlet.http.HttpSession;
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

import com.someairlines.entity.Flight;
import com.someairlines.entity.FlightCrew;
import com.someairlines.entity.util.FlightStatus;
import com.someairlines.service.FlightService;

@Controller
@RequestMapping("/flight*")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class FlightController {

	private final FlightService flightService;
	
	
	public static final String REDIRECT = "redirect:/flight";
	
	public static final String ADD = "admin/addFlight";
	
	public static final String CONFIGURE = "admin/configureFlight";
	
	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public String flights(Model model) {
		Iterable<Flight> flights = flightService.findAll();
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
	public String addFlight(@Valid @ModelAttribute Flight flight, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return ADD;
		}
		flight.setFlightStatus(FlightStatus.SCHEDULED);
		flightService.save(flight);
		return REDIRECT;
	}
	
	@PostMapping(params = "remove")
	public String removeFlight(@RequestParam long flightId){
		flightService.delete(flightId);
		return REDIRECT;
	}
	
	@PostMapping(params = "changeFlightPage")
	public String changeFlightPage(Model model, HttpSession session,@RequestParam long flightId) {
		Flight flightToChange = flightService.findAndInitialize(flightId);
		model.addAttribute("flight", flightToChange);
		session.setAttribute("flightCrew", flightToChange.getFlightCrew());
		model.addAttribute("flightStatuses", Arrays.asList(FlightStatus.values()));
		return CONFIGURE;
	}
	
	@PostMapping("/change")
	public String changeFlight(@Valid @ModelAttribute Flight flight, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return CONFIGURE;
		}
		flight.setFlightCrew((FlightCrew) session.getAttribute("flightCrew"));
		flightService.save(flight);
		return REDIRECT;
	}
}
