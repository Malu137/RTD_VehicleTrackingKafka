package com.feed.speed;

public class CompTrip {
	
	private String vehicleID;
	private String tripId;
	private String routeId;
	private long time;
	private double tripDistance;
	private int overspeedCount;
	private double cumScore;
	private long startTime;
	private long endTime;
	private double avgSpeed;
	
	public CompTrip(String vehicleID, String tripId, String routeId, long time, double tripDistance, int overspeedCount,
			double cumScore, long startTime, long endTime, double avgSpeed) {
		super();
		this.vehicleID = vehicleID;
		this.tripId = tripId;
		this.routeId = routeId;
		this.time = time;
		this.tripDistance = tripDistance;
		this.overspeedCount = overspeedCount;
		this.cumScore = cumScore;
		this.startTime = startTime;
		this.endTime = endTime;
		this.avgSpeed = avgSpeed;
	}
	public CompTrip() {
		// TODO Auto-generated constructor stub
		super();
	}
	public String getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getTripDistance() {
		return tripDistance;
	}
	public void setTripDistance(double tripDistance) {
		this.tripDistance = tripDistance;
	}
	public int getOverspeedCount() {
		return overspeedCount;
	}
	public void setOverspeedCount(int overspeedCount) {
		this.overspeedCount = overspeedCount;
	}
	public double getCumScore() {
		return cumScore;
	}
	public void setCumScore(double cumScore) {
		this.cumScore = cumScore;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public double getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
		

}
