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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.someairlines.config.TestWebConfig;
import com.someairlines.entity.Flight;
import com.someairlines.entity.util.FlightStatus;
import com.someairlines.service.FlightService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
public class FlightControllerTest {
	
	MockMvc mockMvc;
	@Autowired
	FlightService mockFlightService;
	
	@Autowired
	private WebApplicationContext context;

	private LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
	
	private String dateTimeString = formatLocalDateTime(dateTime);
	
	@Before
	public void init() {
		reset(mockFlightService);
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
			.andExpect(view().name(FlightController.ADD))
			.andExpect(model().attribute("flight", instanceOf(Flight.class)))
			.andExpect(model().attribute("flightStatuses", is(flightStatuses)));
	}
	
	@Test
	public void testAddFlight() throws Exception {
		mockMvc.perform(post("/flight/add")
			.param("origin", "moon")
			.param("destination", "sun")
			.param("departureDate", dateTimeString))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(FlightController.REDIRECT));
	}
	
	@Test
	public void testAddFlightNonValid() throws Exception {
		mockMvc.perform(post("/flight/add")
			.param("origin", "moon")
			.param("destination", "")
			.param("departureDate", dateTimeString))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name(FlightController.ADD));
	}
	
	@Test
	public void testRemoveFlight() throws Exception {
		Flight flightToDelete = createFlight();
		when(mockFlightService.find(flightToDelete.getId())).thenReturn(flightToDelete);
		mockMvc.perform(post("/flight")
		.param("remove", "")
		.param("flightId", "1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(FlightController.REDIRECT));
	}
	
	@Test
	public void testChangeFlightPage() throws Exception{
		List<FlightStatus> flightStatuses = Arrays.asList(FlightStatus.values());
		Flight flightToChange = createFlight();
		when(mockFlightService.find(flightToChange.getId())).thenReturn(flightToChange);
		mockMvc.perform(post("/flight")
				.param("changeFlightPage", "")
				.param("flightId", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name(FlightController.CONFIGURE))
			.andExpect(model().attribute("flight", is(flightToChange)))
			.andExpect(model().attribute("flightStatuses", is(flightStatuses)));
	}
	
	@Test
	public void testChangeFlight() throws Exception {
		mockMvc.perform(post("/flight/change")
		.param("origin", "moon")
		.param("destination", "sun")
		.param("dateAndTime", dateTimeString)
		.param("flightStatus", FlightStatus.CANCELED.toString()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(FlightController.REDIRECT));
	}
	
	@Test
	public void testChangeFlightNonValid() throws Exception {
		mockMvc.perform(post("/flight/change")
				.param("origin", "moon")
				.param("destination", "")
				.param("dateAndTime", dateTimeString)
				.param("flightStatus", FlightStatus.CANCELED.toString()))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name(FlightController.CONFIGURE));
	}
	
	//Util
	
	public Flight createFlight() {
		Flight flight = new Flight();
		flight.setId(1);
		flight.setOrigin("Mars");
		flight.setDestination("Uranus");
		flight.setDepartureDate(dateTime);
		flight.setFlightStatus(FlightStatus.EN_ROUTE);
		return flight;
	}
	
	private String formatLocalDateTime(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dateTime.format(formatter);
	}
}
