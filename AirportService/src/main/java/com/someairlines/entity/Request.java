package com.someairlines.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.someairlines.entity.util.RequestStatus;

import lombok.Data;

@Entity
@Table(name = "request")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Request {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	
	@NotEmpty
	@Column(name = "user_name")
	private String username;
	
	@NotEmpty
	private String header;
	
	@NotEmpty
	@Size(max = 200)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private RequestStatus status;
	
}
