package com.someairlines.web;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.someairlines.config.TestWebConfig;
import com.someairlines.db.FlightRepository;
import com.someairlines.entity.Flight;
import com.someairlines.entity.util.FlightStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
public class FlightControllerTest {
	
	private final String REDIRECT = "redirect:/flight";
	
	MockMvc mockMvc;
	@Autowired
	FlightRepository mockFlightRepository;
	
	@Autowired
	FlightController flightController;
	
	@Autowired
	private WebApplicationContext context;

	@Before
	public void init() {
		reset(mockFlightRepository);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
	
	@Test
	public void testFlights() throws Exception {
		mockMvc.perform(get("/flight")).andExpect(status().isOk())
                .andExpect(view().name("flights"));
	}
	
	@Test
	public void testAddFlightPage() throws Exception{
		List<FlightStatus> flightStatuses = Arrays.asList(FlightStatus.values());
		mockMvc.perform(post("/flight").param("addFlightPage", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/addFlight"))
			.andExpect(model().attribute("flight", instanceOf(Flight.class)))
			.andExpect(model().attribute("flightStatuses", is(flightStatuses)));
	}
	
	@Test
	public void testAddFlight() throws Exception {
		mockMvc.perform(post("/flight/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("origin", "moon")
			.param("destination", "sun")
			.param("dateAndTime", "2017-12-27 16:26"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(REDIRECT));
	}
	
	@Test
	public void testRemoveFlight() throws Exception {
		Flight flightToDelete = createFlight();
		when(mockFlightRepository.find(flightToDelete.getId())).thenReturn(flightToDelete);
		mockMvc.perform(post("/flight")
		.param("remove", "")
		.param("flightId", "1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(REDIRECT));
	}
	
	@Test
	public void testChangeFlightPage() throws Exception{
		List<FlightStatus> flightStatuses = Arrays.asList(FlightStatus.values());
		Flight flightToChange = createFlight();
		when(mockFlightRepository.find(flightToChange.getId())).thenReturn(flightToChange);
		mockMvc.perform(post("/flight")
				.param("changeFlightPage", "")
				.param("flightId", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/configureFlight"))
			.andExpect(model().attribute("flight", is(flightToChange)))
			.andExpect(model().attribute("flightStatuses", is(flightStatuses)));
	}
	
	@Test
	public void testChangeFlight() throws Exception {
		mockMvc.perform(post("/flight/change")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.param("origin", "moon")
		.param("destination", "sun")
		.param("dateAndTime", "2017-12-27 16:26")
		.param("flightStatus", FlightStatus.CANCELED.toString()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(REDIRECT));
	}
	
	public Flight createFlight() {
		Flight flight = new Flight();
		flight.setId(1);
		flight.setOrigin("Mars");
		flight.setDestination("Uranus");
		flight.setDepartureDate(LocalDateTime.of(2017, 12, 28, 12, 33));
		flight.setFlightStatus(FlightStatus.EN_ROUTE);
		return flight;
	}
	
}
