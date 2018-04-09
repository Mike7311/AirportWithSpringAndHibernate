package com.someairlines.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.someairlines.entity.FlightCrew;

@Component
public class CrewUtil {

	private EmployeeRepository employeeRepository;
	
	@Autowired
	public CrewUtil(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public void setCrewFree(FlightCrew crew, boolean isFree) {
		employeeRepository.setStatuses(getIds(crew), isFree);
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
