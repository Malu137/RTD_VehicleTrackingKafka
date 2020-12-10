package com.feed.speed;

public class TrafficParam {
	private double avgSP;
	private double minSP;
	private double maxSP;
	
	private double avgSU;
	private double minSU;
	private double maxSU;
	
	private double avgFF;
	private double minFF;
	private double maxFF;
	
	private double avgJF;
	private double minJF;
	private double maxJF;
	
	private double avgCN;
	private double minCN;
	private double maxCN;
	
	
	
	
	@Override
	public String toString() {
		return "TrafficParam [avgSP=" + avgSP + ", minSP=" + minSP + ", maxSP=" + maxSP + ", avgSU=" + avgSU
				+ ", minSU=" + minSU + ", maxSU=" + maxSU + ", avgFF=" + avgFF + ", minFF=" + minFF + ", maxFF=" + maxFF
				+ ", avgJF=" + avgJF + ", minJF=" + minJF + ", maxJF=" + maxJF + ", avgCN=" + avgCN + ", minCN=" + minCN
				+ ", maxCN=" + maxCN + "]";
	}
	public TrafficParam() {
		super();
		// TODO Auto-generated constructor stub
		this.minCN = 999999;
		this.minSU = 999999;
		this.minSP = 999999;
		this.minFF = 999999;
		this.minJF = 999999;
		
		this.maxCN = -999999;
		this.maxSU = -999999;
		this.maxSP = -999999;
		this.maxFF = -999999;
		this.maxJF = -999999;
		
		this.avgCN =0;
		this.avgSU =0;
		this.avgSP =0;
		this.avgFF =0;
		this.avgJF =0;
	}
	public double getAvgSP() {
		return avgSP;
	}
	public void setAvgSP(double SP ,int n) {
		this.avgSP = ((this.avgSP * (n-1)) +SP)/n;
	}
	public double getMinSP() {
		return minSP;
	}
	public void setMinSP(double minSP) {
		this.minSP = Math.min(minSP , this.minSP);
	}
	public double getMaxSP() {
		return maxSP;
	}
	public void setMaxSP(double maxSP) {
		this.maxSP = Math.max(maxSP,this.maxSP);
	}
	public double getAvgSU() {
		return avgSU;
	}
	public void setAvgSU(double SU,int n) {
		this.avgSU = ((this.avgSU *(n-1)) +SU)/n;
	}
	public double getMinSU() {
		return minSU;
	}
	public void setMinSU(double minSU) {
		this.minSU = Math.min(minSU,this.minSU);
	}
	public double getMaxSU() {
		return maxSU;
	}
	public void setMaxSU(double maxSU) {
		this.maxSU = Math.max(maxSU,this.maxSU);
	}
	public double getAvgFF() {
		return avgFF;
	}
	public void setAvgFF(double FF,int n) {
		this.avgFF = ((this.avgFF * (n-1)) +FF)/n;
	}
	public double getMinFF() {
		return minFF;
	}
	public void setMinFF(double minFF) {
		this.minFF = Math.min(minFF,this.minFF);
	}
	public double getMaxFF() {
		return maxFF;
	}
	public void setMaxFF(double maxFF) {
		this.maxFF = Math.max(maxFF,this.maxFF);
	}
	public double getAvgJF() {
		return avgJF;
	}
	public void setAvgJF(double JF, int n) {
		this.avgJF = ((this.avgJF * (n-1)) +JF)/n;
	}
	public double getMinJF() {
		return minJF;
	}
	public void setMinJF(double minJF) {
		this.minJF = Math.min(minJF,this.minJF);
	}
	public double getMaxJF() {
		return maxJF;
	}
	public void setMaxJF(double maxJF) {
		this.maxJF = Math.max(maxJF,this.maxJF);
	}
	public double getAvgCN() {
		return avgCN;
	}
	public void setAvgCN(double CN,int n) {
		this.avgCN = ((this.avgCN * (n-1)) +CN)/n;
	}
	public double getMinCN() {
		return minCN;
	}
	public void setMinCN(double minCN) {
		this.minCN = Math.min(minCN,this.minCN);
	}
	public double getMaxCN() {
		return maxCN;
	}
	public void setMaxCN(double maxCN) {
		this.maxCN = Math.max(maxCN,this.maxCN);
	}
	
	

}
