package com.someairlines.web;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import com.someairlines.db.EmployeeRepository;
import com.someairlines.entity.Employee;
import com.someairlines.entity.util.Job;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
public class EmployeeControllerTest {

	private final String REDIRECT = "redirect:/employee";
	
	MockMvc mockMvc;
	@Autowired
	EmployeeRepository mockEmployeeRepository;
	
	@Autowired
	EmployeeController employeeController;
	
	@Autowired
	private WebApplicationContext context;

	@Before
	public void init() {
		reset(mockEmployeeRepository);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
	
	@Test
	public void testEmployees() throws Exception {
		mockMvc.perform(get("/employee")).andExpect(status().isOk())
                .andExpect(view().name("admin/employees"));
	}
	
	@Test
	public void testAddEmployeePage() throws Exception{
		List<Job> jobs = Arrays.asList(Job.values());
		mockMvc.perform(post("/employee").param("addEmployeePage", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/addEmployee"))
			.andExpect(model().attribute("employee", samePropertyValuesAs(new Employee())))
			.andExpect(model().attribute("jobs", is(jobs)));
	}
	
	@Test
	public void testAddEmployee() throws Exception {
		mockMvc.perform(post("/employee/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("firstName", "Petr")
			.param("lastName", "Perviy")
			.param("email", "Petya1672@mail.ru")
			.param("job", "NAVIGATOR"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(REDIRECT));
	}
	
	@Test
	public void testRemoveEmployee() throws Exception {
		Employee employeeToDelete = createEmployee();
		when(mockEmployeeRepository.find(employeeToDelete.getId())).thenReturn(employeeToDelete);
		mockMvc.perform(post("/employee")
		.param("removeEmployeeId", "1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(REDIRECT));
	}
	
	@Test
	public void testChangeEmployeePage() throws Exception{
		List<Job> jobs = Arrays.asList(Job.values());
		Employee employeeToChange = createEmployee();
		when(mockEmployeeRepository.find(employeeToChange.getId())).thenReturn(employeeToChange);
		mockMvc.perform(post("/employee")
				.param("configureEmployeeId", "1"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/configureEmployee"))
			.andExpect(model().attribute("employee", is(employeeToChange)))
			.andExpect(model().attribute("jobs", is(jobs)));
	}
	
	@Test
	public void testChangeEmployee() throws Exception {
		mockMvc.perform(post("/employee/change")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.param("id", "1")
		.param("firstName", "Petr")
		.param("lastName", "Perviy")
		.param("email", "Petya1672@mail.ru")
		.param("job", Job.OPERATOR.toString()))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(REDIRECT));
	}
	
	public Employee createEmployee() {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setFirstName("Petr");
		employee.setLastName("Perviy");
		employee.setEmail("Petya1672@mail.ru");
		employee.setJob(Job.NAVIGATOR);
		employee.setFree(true);
		return employee;
	}
}
