package com.someairlines.web;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collections;
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
import com.someairlines.entity.Employee;
import com.someairlines.entity.Flight;
import com.someairlines.entity.FlightCrew;
import com.someairlines.entity.util.Job;
import com.someairlines.service.EmployeeService;
import com.someairlines.service.FlightService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
public class CrewControllerTest {

	MockMvc mockMvc;
	@Autowired
	EmployeeService mockEmployeeService;
	
	@Autowired
	FlightService mockFlightService;
	
	@Autowired
	CrewController crewController;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void init() {
		reset(mockFlightService);
		reset(mockEmployeeService);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCrews() throws Exception {
		when(mockEmployeeService.findPilots()).thenReturn(getTestPilots());
		when(mockEmployeeService.findOperators()).thenReturn(getTestOperators());
		when(mockEmployeeService.findNavigators()).thenReturn(getTestNavigators());
		when(mockEmployeeService.findFlightAttendants()).thenReturn(getTestAttendants());
		mockMvc.perform(get("/crew").param("flightId", "1"))
		.andExpect(view().name("dispatcher/formCrew")).andExpect(status().isOk())
		.andExpect(model().attribute("flightId", is(1L)))
		.andExpect(model().attribute("pilots", hasItem(
        		allOf(
        				hasProperty("id", is(1L)),
                        hasProperty("firstName", is("Pilot")),
                        hasProperty("lastName", is("First")),
                        hasProperty("email", is("pilotFirst@gmail.com")),
                        hasProperty("job", is(Job.PILOT)),
                        hasProperty("free", is(true))
                     )
        		)))
		.andExpect(model().attribute("navigators", hasItem(
        		allOf(
        				hasProperty("id", is(2L)),
                        hasProperty("firstName", is("Navigator")),
                        hasProperty("lastName", is("First")),
                        hasProperty("email", is("navigatorFirst@gmail.com")),
                        hasProperty("job", is(Job.NAVIGATOR)),
                        hasProperty("free", is(true))
                     )
        		)))
		.andExpect(model().attribute("operators", hasItem(
        		allOf(
        				hasProperty("id", is(3L)),
                        hasProperty("firstName", is("Operator")),
                        hasProperty("lastName", is("First")),
                        hasProperty("email", is("operatorFirst@gmail.com")),
                        hasProperty("job", is(Job.OPERATOR)),
                        hasProperty("free", is(true))
                     )
        		)))
		.andExpect(model().attribute("attendants", hasItems(
        		allOf(
        				hasProperty("id", is(4L)),
                        hasProperty("firstName", is("Attendant")),
                        hasProperty("lastName", is("First")),
                        hasProperty("email", is("attendantFirst@gmail.com")),
                        hasProperty("job", is(Job.FLIGHT_ATTENDANT)),
                        hasProperty("free", is(true))
                     ),
        		allOf(
        				hasProperty("id", is(5L)),
                        hasProperty("firstName", is("Attendant")),
                        hasProperty("lastName", is("Second")),
                        hasProperty("email", is("attendantSecond@gmail.com")),
                        hasProperty("job", is(Job.FLIGHT_ATTENDANT)),
                        hasProperty("free", is(true))
        			)))
				);
	}

	@Test
	public void testCreateCrew() throws Exception {
		Flight flight = getTestFlight();
		Employee pilot = getTestPilots().get(0);
		Employee navigator = getTestNavigators().get(0);
		Employee operator = getTestOperators().get(0);
		List<Employee> attendants = getTestAttendants();
		when(mockFlightService.find(flight.getId())).thenReturn(flight);
		when(mockEmployeeService.find(pilot.getId())).thenReturn(pilot);
		when(mockEmployeeService.find(navigator.getId())).thenReturn(navigator);
		when(mockEmployeeService.find(operator.getId())).thenReturn(operator);
		attendants.forEach(attendant 
				-> when(mockEmployeeService.find(attendant.getId())).thenReturn(attendant));
		mockMvc.perform(post("/crew/create")
				.param("flightId", "1")
				.param("pilotId", "1")
				.param("navigatorId", "2")
				.param("operatorId", "3")
				.param("attendantIds", "4")
				.param("attendantIds", "5"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(FlightController.REDIRECT));
	}
	
	@Test
	public void testFreeCrew() throws Exception {
		Flight flight = getTestFlight();
		flight.setFlightCrew(getTestCrew());
		when(mockFlightService.find(flight.getId())).thenReturn(flight);
		mockMvc.perform(post("/crew/free").param("flightId", "1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(FlightController.REDIRECT));
	}
	
	//Utility
	private Flight getTestFlight() {
		Flight flight = new Flight();
		flight.setId(1);
		return flight;
	}
	
	private List<Employee> getTestPilots() {
		Employee pilot = new Employee();
		pilot.setId(1);
		pilot.setFirstName("Pilot");
		pilot.setLastName("First");
		pilot.setEmail("pilotFirst@gmail.com");
		pilot.setJob(Job.PILOT);
		pilot.setFree(true);
		return Collections.singletonList(pilot);
	}
	
	private List<Employee> getTestNavigators() {
		Employee navigator = new Employee();
		navigator.setId(2);
		navigator.setFirstName("Navigator");
		navigator.setLastName("First");
		navigator.setEmail("navigatorFirst@gmail.com");
		navigator.setJob(Job.NAVIGATOR);
		navigator.setFree(true);
		return Collections.singletonList(navigator);
	}
	
	private List<Employee> getTestOperators() {
		Employee operator = new Employee();
		operator.setId(3);
		operator.setFirstName("Operator");
		operator.setLastName("First");
		operator.setEmail("operatorFirst@gmail.com");
		operator.setJob(Job.OPERATOR);
		operator.setFree(true);
		return Collections.singletonList(operator);
	}
	
	private List<Employee> getTestAttendants() {
		Employee attendant = new Employee();
		attendant.setId(4);
		attendant.setFirstName("Attendant");
		attendant.setLastName("First");
		attendant.setEmail("attendantFirst@gmail.com");
		attendant.setJob(Job.FLIGHT_ATTENDANT);
		attendant.setFree(true);
		Employee attendant2 = new Employee();
		attendant2.setId(5);
		attendant2.setFirstName("Attendant");
		attendant2.setLastName("Second");
		attendant2.setEmail("attendantSecond@gmail.com");
		attendant2.setJob(Job.FLIGHT_ATTENDANT);
		attendant2.setFree(true);
		List<Employee> attendants = Arrays.asList(new Employee[]{attendant, attendant2});
		return attendants;
	}
	
	private FlightCrew getTestCrew() {
		FlightCrew crew = new FlightCrew();
		Employee pilot = getTestPilots().get(0);
		Employee navigator = getTestNavigators().get(0);
		Employee operator = getTestOperators().get(0);
		List<Employee> attendants = getTestAttendants();
		pilot.setFree(false);
		navigator.setFree(false);
		operator.setFree(false);
		for(Employee e : attendants) {
			e.setFree(false);
		}
		crew.setNavigator(navigator);
		crew.setPilot(pilot);
		crew.setOperator(operator);
		crew.setFlightAttendants(attendants);
		return crew;
	}
	
}
