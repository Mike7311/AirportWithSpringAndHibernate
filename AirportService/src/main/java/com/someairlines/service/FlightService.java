package com.someairlines.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.someairlines.db.EmployeeRepository;
import com.someairlines.db.FlightRepository;
import com.someairlines.entity.Employee;
import com.someairlines.entity.Flight;
import com.someairlines.entity.FlightCrew;

@Transactional
@Service
public class FlightService {

	private FlightRepository flightRepository;
	
	private EmployeeRepository employeeRepository;
	
	public FlightService(FlightRepository flightRepository, EmployeeRepository employeeRepository) {
		this.flightRepository = flightRepository;
		this.employeeRepository = employeeRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Flight> findAll() {
		return flightRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Flight find(long flightId) {
		return flightRepository.findOne(flightId);
	}
	
	public Flight save(@Valid Flight flight) {
		return flightRepository.save(flight);
	}

	@Transactional(readOnly = true)
	public Flight findAndInitialize(long flightId) {
		return flightRepository.findById(flightId);
	}

	public void createCrew(long flightId, long pilotId, long navigatorId, 
			long operatorId,
			List<Long> attendantIds) {
		Flight flight = flightRepository.findOne(flightId);
		Employee pilot = employeeRepository.findOne(pilotId);
		Employee navigator = employeeRepository.findOne(navigatorId);
		Employee operator = employeeRepository.findOne(operatorId);
		Iterable<Employee> attendants = employeeRepository.findAll(attendantIds);
		FlightCrew crew = new FlightCrew();
		crew.setPilot(pilot);
		crew.setNavigator(navigator);
		crew.setOperator(operator);
		ArrayList<Employee> attendantsList = new ArrayList<Employee>();
		attendants.forEach(attendantsList::add);
		crew.setFlightAttendants(attendantsList);
		List<Long> ids = new ArrayList<>();
		ids.add(pilotId);
		ids.add(navigatorId);
		ids.add(operatorId);
		attendantIds.forEach(ids::add);
		employeeRepository.setStatuses(ids, false);
		flight.setFlightCrew(crew);
		flightRepository.save(flight);
	}
	
	public void deleteCrew(final Long flightId) {
		Flight flightToClean = flightRepository.findOne(flightId);
		List<Long> employeeIds = getIds(flightToClean.getFlightCrew());
		flightRepository.deleteCrew(flightToClean);
		employeeRepository.setStatuses(employeeIds, true);
		flightToClean.setFlightCrew(null);
	}
	
	public void delete(Long flightId) {
		Flight flightToDelete = flightRepository.findOne(flightId);
		if(flightToDelete.getFlightCrew() != null) {
			List<Long> employeeIds = getIds(flightToDelete.getFlightCrew());
			flightRepository.deleteCrew(flightToDelete);
			employeeRepository.setStatuses(employeeIds, true);
		}
		flightRepository.delete(flightToDelete);
	}

	private List<Long> getIds(FlightCrew crew) {
		List<Long> ids = new ArrayList<>();
		ids.add(crew.getPilot().getId());
		ids.add(crew.getNavigator().getId());
		ids.add(crew.getOperator().getId());
		crew.getFlightAttendants().forEach(
				(attendant) -> {
					ids.add(attendant.getId());
				});
		return ids;
	}

}
