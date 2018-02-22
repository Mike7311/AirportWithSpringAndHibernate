package com.someairlines.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "flight_crew")
public class FlightCrew {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "crew_id", nullable = false)
	private long id;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Employee pilot;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Employee navigator;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Employee operator;
	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	@Size(min=1, max=3)
	@JoinTable(
            name = "flight_attendants",
            joinColumns = @JoinColumn(name = "crew_id"),
            inverseJoinColumns = @JoinColumn(name = "attendant_id")
    )
	private List<Employee> flightAttendants;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Employee getPilot() {
		return pilot;
	}

	public void setPilot(Employee pilot) {
		this.pilot = pilot;
	}

	public Employee getNavigator() {
		return navigator;
	}

	public void setNavigator(Employee navigator) {
		this.navigator = navigator;
	}

	public Employee getOperator() {
		return operator;
	}

	public void setOperator(Employee operator) {
		this.operator = operator;
	}

	public List<Employee> getFlightAttendants() {
		return flightAttendants;
	}

	public void setFlightAttendants(List<Employee> flightAttendants) {
		this.flightAttendants = flightAttendants;
	}

	@Override
	public String toString() {
		return "FlightCrew [id=" + id + ", pilot=" + pilot + ", navigator=" + navigator + ", operator=" + operator
				+ ", flightAttendants=" + flightAttendants + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightAttendants == null) ? 0 : flightAttendants.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((navigator == null) ? 0 : navigator.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((pilot == null) ? 0 : pilot.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightCrew other = (FlightCrew) obj;
		if (flightAttendants == null) {
			if (other.flightAttendants != null)
				return false;
		} else if (!flightAttendants.equals(other.flightAttendants))
			return false;
		if (id != other.id)
			return false;
		if (navigator == null) {
			if (other.navigator != null)
				return false;
		} else if (!navigator.equals(other.navigator))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (pilot == null) {
			if (other.pilot != null)
				return false;
		} else if (!pilot.equals(other.pilot))
			return false;
		return true;
	}

}
