package com.someairlines.db.listener;

import java.util.List;

import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.springframework.stereotype.Component;

import com.someairlines.beanutil.BeanUtil;
import com.someairlines.db.EmployeeRepository;
import com.someairlines.entity.Employee;
import com.someairlines.entity.Flight;
import com.someairlines.entity.FlightCrew;

@Component
public class PreDeleteEventListenerImpl implements PreDeleteEventListener {

	private static final long serialVersionUID = 8608023442583109505L;
	
	private EmployeeRepository employeeRepository; 
	
	@Override
	public boolean onPreDelete(PreDeleteEvent event) {
		Object entity = event.getEntity();
		if(entity instanceof Flight) {
			Flight flight = (Flight) entity;
			if(flight.getFlightCrew() != null) {
				setCrewFree(flight.getFlightCrew());
			}
		} else if(entity instanceof FlightCrew) {
			setCrewFree((FlightCrew) entity);
		}
		return false;
	}

	private void setCrewFree(FlightCrew crew) {
		Employee pilot = crew.getPilot();
		Employee navigator = crew.getNavigator();
		Employee operator = crew.getOperator();
		List<Employee> attendants = crew.getFlightAttendants();
		pilot.setFree(true);
		navigator.setFree(true);
		operator.setFree(true);
		attendants.forEach((attendant) -> attendant.setFree(true));
		if(employeeRepository == null) {
			employeeRepository = BeanUtil.getBean(EmployeeRepository.class);
		}
		employeeRepository.update(pilot);
		employeeRepository.update(navigator);
		employeeRepository.update(operator);
		attendants.forEach((attendant) -> employeeRepository.update(attendant));
	}
}
