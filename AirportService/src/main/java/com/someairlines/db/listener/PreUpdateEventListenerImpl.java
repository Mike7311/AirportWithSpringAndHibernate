package com.someairlines.db.listener;

import java.util.List;

import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

import com.someairlines.beanutil.BeanUtil;
import com.someairlines.db.EmployeeRepository;
import com.someairlines.entity.Employee;
import com.someairlines.entity.Flight;
import com.someairlines.entity.FlightCrew;

public class PreUpdateEventListenerImpl implements PreUpdateEventListener {

	private static final long serialVersionUID = 3856784083824357709L;
	
	private EmployeeRepository employeeRepository;
	
	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		Object entity = event.getEntity();
		if(entity instanceof Flight) {
			Flight flight = (Flight) entity;
			if(flight.getFlightCrew() != null) {
				setCrewBusy(flight.getFlightCrew());
			}
		}
		return false;
	}
	
	private void setCrewBusy(FlightCrew crew) {
		Employee pilot = crew.getPilot();
		Employee navigator = crew.getNavigator();
		Employee operator = crew.getOperator();
		List<Employee> attendants = crew.getFlightAttendants();
		pilot.setFree(false);
		navigator.setFree(false);
		operator.setFree(false);
		attendants.forEach((attendant) -> attendant.setFree(false));
		if(employeeRepository == null) {
			employeeRepository = BeanUtil.getBean(EmployeeRepository.class);
		}
		employeeRepository.update(pilot);
		employeeRepository.update(navigator);
		employeeRepository.update(operator);
		attendants.forEach((attendant) -> employeeRepository.update(attendant));
	}
	
}
