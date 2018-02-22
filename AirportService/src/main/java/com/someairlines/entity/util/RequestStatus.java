package com.someairlines.entity.util;

public enum RequestStatus {
	NEW, APPROVED, DECLINED;
	
	public String getName() {
		return name().toLowerCase();
	}
}
