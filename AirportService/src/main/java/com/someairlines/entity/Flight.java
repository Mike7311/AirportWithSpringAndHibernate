package com.someairlines.entity;

import java.time.LocalDateTime;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.someairlines.entity.util.FlightStatus;

import lombok.Data;

@Entity
@Table(name = "flight")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@Data
public class Flight {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
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
	
}
