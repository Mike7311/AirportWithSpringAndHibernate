package com.someairlines.entity.util;

import java.util.Arrays;

public enum FlightStatus {

	DELAYED, ARRIVED, EN_ROUTE, SCHEDULED, CANCELED;
	
	public static String getStatusName(int id) {
		return FlightStatus.values()[id].getName();
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
	public static String[] names() {
	    return Arrays.toString(FlightStatus.values()).replaceAll("^.|.$", "").split(", ");
	}
	
}
