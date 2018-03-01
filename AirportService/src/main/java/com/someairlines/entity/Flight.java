package com.someairlines.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.someairlines.entity.util.FlightStatus;

@Entity
@Table(name = "flight")
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private long id;
	
	@NotEmpty
	@Size(max = 20)
	private String origin;
	
	@NotEmpty
	@Size(max = 20)
	private String destination;
	
	@Column(name = "departure_date")
	@Future(message = "departure date must be in future")
	private LocalDateTime departureDate;
	
	@Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
	private FlightStatus flightStatus;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "flight_crew_id")
	private FlightCrew flightCrew;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public LocalDateTime getDepartureDate() {
		return departureDate;
	}
	
	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}
	public FlightStatus getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(FlightStatus flightStatus) {
		this.flightStatus = flightStatus;
	}
	
	public FlightCrew getFlightCrew() {
		return flightCrew;
	}
	public void setFlightCrew(FlightCrew flightCrew) {
		this.flightCrew = flightCrew;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departureDate == null) ? 0 : departureDate.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((flightCrew == null) ? 0 : flightCrew.hashCode());
		result = prime * result + ((flightStatus == null) ? 0 : flightStatus.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
		Flight other = (Flight) obj;
		if (departureDate == null) {
			if (other.departureDate != null)
				return false;
		} else if (!departureDate.equals(other.departureDate))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (flightCrew == null) {
			if (other.flightCrew != null)
				return false;
		} else if (!flightCrew.equals(other.flightCrew))
			return false;
		if (flightStatus != other.flightStatus)
			return false;
		if (id != other.id)
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Flight [id=" + id + ", origin=" + origin + ", destination=" + destination + ", departureDate="
				+ departureDate + ", flightStatus=" + flightStatus + "]";
	}
}
