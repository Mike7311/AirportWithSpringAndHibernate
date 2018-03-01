package com.someairlines.web;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.someairlines.config.TestSecurityConfig;
import com.someairlines.config.TestWebConfig;
import com.someairlines.db.RequestRepository;
import com.someairlines.entity.Request;
import com.someairlines.entity.util.RequestStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestWebConfig.class, TestSecurityConfig.class})
@WebAppConfiguration
public class RequestControllerTest {

	MockMvc mockMvc;
	
	@Autowired
	private RequestRepository mockRequestRepository;
	
	@Autowired
	private WebApplicationContext context;

	private final String TESTUSER = "user1";
	
	private final String TESTHEADER = "test header";
	
	private final String TESTDESCRIPTION = "test description";
	
	@Before
	public void init() {
		reset(mockRequestRepository);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@Test
	@WithMockUser(username = "admin", roles= "ADMIN")
	public void testAdminRequestsPage() throws Exception {
		Request testRequest = getNewTestRequest();
		List<Request> testRequestList = Collections.singletonList(testRequest);
		when(mockRequestRepository.findAllNew()).thenReturn(testRequestList);
		mockMvc.perform(get("/request"))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/requests"))
		.andExpect(model().attribute("requests", is(testRequestList)));
		verify(mockRequestRepository, times(1)).findAllNew();
		verifyNoMoreInteractions(mockRequestRepository);
	}
	
	@Test
	@WithMockUser(username = TESTUSER, roles= "USER")
	public void testDispatcherRequestsPage() throws Exception {
		Request testRequest = getNewTestRequest();
		List<Request> testRequestList = Collections.singletonList(testRequest);
		when(mockRequestRepository.findAllForUser(TESTUSER)).thenReturn(testRequestList);
		mockMvc.perform(get("/request"))
		.andExpect(status().isOk())
		.andExpect(view().name("dispatcher/requests"))
		.andExpect(model().attribute("requests", is(testRequestList)));
		verify(mockRequestRepository, times(1)).findAllForUser(TESTUSER);
		verifyNoMoreInteractions(mockRequestRepository);
	}
	
	@Test
	public void testOpenRequest() throws Exception {
		Request testRequest = getNewTestRequest();
		when(mockRequestRepository.find(1)).thenReturn(testRequest);
		mockMvc.perform(get("/request/open")
				.param("requestId", "1"))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/requestInfo"))
		.andExpect(model().attribute("request", is(testRequest)));
	}
	
	@Test
	public void testWriteRequestForm() throws Exception {
		mockMvc.perform(get("/request/write"))
		.andExpect(status().isOk())
		.andExpect(view().name(RequestController.DISPATCHERFORM))
		.andExpect(model().attribute("request", instanceOf(Request.class)));
	}
	
	@Test
	@WithMockUser(username = TESTUSER, roles={ "USER" })
	public void testWriteRequest() throws Exception {
		mockMvc.perform(post("/request/write")
				.param("username", TESTUSER)
				.param("header", TESTHEADER)
				.param("description", TESTDESCRIPTION))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(RequestController.REDIRECT));
	}
	
	@Test
	@WithMockUser(username = TESTUSER, roles={ "USER" })
	public void testWriteRequestNonValid() throws Exception {
		mockMvc.perform(post("/request/write")
				.param("username", TESTUSER)
				.param("header", "")
				.param("description", TESTDESCRIPTION))
		.andExpect(status().is2xxSuccessful())
		.andExpect(view().name(RequestController.DISPATCHERFORM));
	}
	
	@Test
	public void testProcessRequest () throws Exception {
		Request testRequest = getNewTestRequest();
		when(mockRequestRepository.find(1)).thenReturn(testRequest);
		mockMvc.perform(post("/request/update")
				.param("requestId", "1")
				.param("status", "APPROVED"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name(RequestController.REDIRECT));
	}
	
	private Request getNewTestRequest() {
		Request request = new Request();
		request.setId(1);
		request.setHeader(TESTHEADER);
		request.setDescription(TESTDESCRIPTION);
		request.setUsername(TESTUSER);
		request.setStatus(RequestStatus.NEW);
		return request;
	}
}
