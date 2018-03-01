package com.someairlines.entity;

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

import com.someairlines.entity.util.RequestStatus;

@Entity
@Table(name = "request")
public class Request {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
	
	public long getId() {
		return id;
	}
	public void setId(long requestId) {
		this.id = requestId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RequestStatus getStatus() {
		return status;
	}
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", username=" + username + ", header=" + header 
				+ ", status=" + status + "]";
	}
	
}
