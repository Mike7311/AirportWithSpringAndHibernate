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

@Entity
@Table(name = "employee")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
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
	
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", job=" + job
				+ ", email=" + email + ", isFree=" + isFree + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isFree ? 1231 : 1237);
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
		Employee other = (Employee) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (isFree != other.isFree)
			return false;
		if (job != other.job)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
	
}
