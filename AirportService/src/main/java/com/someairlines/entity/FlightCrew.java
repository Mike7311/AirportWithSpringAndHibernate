package com.someairlines.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "flight_crew")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class FlightCrew {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "crew_id", nullable = false)
	private long id;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Employee pilot;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Employee navigator;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Employee operator;
	
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@Size(min=1, max=3)
	@JoinTable(
            name = "flight_attendants",
            joinColumns = @JoinColumn(name = "crew_id"),
            inverseJoinColumns = @JoinColumn(name = "attendant_id")
    )
	private List<Employee> flightAttendants;

	public List<Employee> getFlightAttendants() {
		return new ArrayList<Employee>(flightAttendants);
	}

}
