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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.someairlines.entity.util.Job;

import lombok.Data;

@Entity
@Table(name = "employee")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@Data
public class Employee {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(updatable = false, nullable = false)
	private long id;
	
	@Column(name = "first_name", nullable = false, length = 20)
	@NotEmpty
	@Size(max = 20)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 20)
	@NotEmpty
	@Size(max = 20)
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private Job job;
	
	@Email(message = "Invalid email")
	@NotEmpty
	private String email;
	
	@NotNull
	@Column(name = "free")
	private boolean isFree;
	
}
