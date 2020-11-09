package com.example.demo.jsonmessage;

import java.lang.String;

public class BusPos {

	
	private String vehicleID;
	private long timestamp;
	private double longitude;
	private double latitude;
	double speed;
	
	
	public BusPos() {
		super();

	}

	public BusPos( String vehicleID, long timestamp, double longitude, double latitude, double speed) {
	 
		super();
		this.vehicleID = vehicleID;
		this.timestamp = timestamp;
		this.longitude = longitude;
		this.latitude = latitude;
		this.speed = speed;
	}
	
	public String getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	@Override
	public String toString() {
		return "BusPos [vehicleID=" + vehicleID + ", timestamp=" + timestamp + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", speed=" + speed + "]";
	}
	
}
