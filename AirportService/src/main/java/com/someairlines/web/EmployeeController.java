package com.someairlines.web;

import java.util.Arrays;

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

import com.someairlines.entity.Employee;
import com.someairlines.entity.util.Job;
import com.someairlines.service.EmployeeService;

@Controller
@RequestMapping("/employee*")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EmployeeController {

	private EmployeeService employeeService;
	
	public static final String REDIRECT = "redirect:/employee";
	
	public static final String ADD = "admin/addEmployee";
	
	public static final String CONFIGURE = "admin/configureEmployee";
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String employees(Model model) {
		Iterable<Employee> employees = employeeService.findAll();
		model.addAttribute("employees", employees);
		return "admin/employees";
	}
	
	@PostMapping(params = "addEmployeePage")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addEmployeePage(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("jobs", Arrays.asList(Job.values()));
		return ADD;
	}
	
	@PostMapping("/add")
	public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result) {
		if(result.hasErrors()) {
			return ADD;
		}
		employee.setFree(true);
		employeeService.save(employee);
		return REDIRECT;
	}
	
	@PostMapping(params = "removeEmployeeId")
	public String removeEmployee(@RequestParam("removeEmployeeId") long Id) {
		Employee employeeToDelete = employeeService.find(Id);
		employeeService.delete(employeeToDelete);
		return REDIRECT;
	}
	
	@PostMapping(params = "configureEmployeeId")
	public String changeEmployeePage(Model model, @RequestParam("configureEmployeeId") long Id) {
		Employee employeeToChange = employeeService.find(Id);
		model.addAttribute("employee", employeeToChange);
		model.addAttribute("jobs", Arrays.asList(Job.values()));
		return CONFIGURE;
	}
	
	@PostMapping("/change")
	public String changeEmployee(@Valid @ModelAttribute Employee employee, BindingResult result) {
		if(result.hasErrors()) {
			return CONFIGURE;
		}
		employeeService.save(employee);
		return REDIRECT;
	}
}
