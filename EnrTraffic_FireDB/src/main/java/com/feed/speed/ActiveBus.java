package com.feed.speed;

public class ActiveBus {
	//From bus
	private String vehicleID;
	private long timestamp;
	private double longitude;
	private double latitude;
	private String tripId;
	private String routeId;
	private double distance;
	private double speed;
	
	//additional
	private double avgSpeed;
	private long time;
	private double tripDistance;
	private int overspeedCount;
	private double instantScore;
	private double cumScore;
	private long startTime;
	private long endTime;
	private int counter;
	private int stagCounter;
	
	
	
	
	
	
	public ActiveBus(String vehicleID, long timestamp, double longitude, double latitude, String tripId, String routeId,
			double distance, double speed, double avgSpeed,long time, double tripDistance, int overspeedCount, double instantScore,
			double cumScore, long startTime, long endTime, int counter, int stagCounter) {
		super();
		this.vehicleID = vehicleID;
		this.timestamp = timestamp;
		this.longitude = longitude;
		this.latitude = latitude;
		this.tripId = tripId;
		this.routeId = routeId;
		this.distance = distance;
		this.speed = speed;
		this.time = time;
		this.tripDistance = tripDistance;
		this.overspeedCount = overspeedCount;
		this.instantScore = instantScore;
		this.cumScore = cumScore;
		this.startTime = startTime;
		this.endTime = endTime;
		this.counter = counter;
		this.stagCounter = stagCounter;
		
	}
	
	
	public ActiveBus() {
		// TODO Auto-generated constructor stub
		super();
	}

	

	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public double getAvgSpeed() {
		return avgSpeed;
	}


	public void setAvgSpeed(double avgSpeed) {
		this.avgSpeed = avgSpeed;
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
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
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
	public double getInstantScore() {
		return instantScore;
	}
	public void setInstantScore(double instantScore) {
		this.instantScore = instantScore;
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


	public int getStagCounter() {
		return stagCounter;
	}


	public void setStagCounter(int stagCounter) {
		this.stagCounter = stagCounter;
	}
	
	
	
	
	

}
