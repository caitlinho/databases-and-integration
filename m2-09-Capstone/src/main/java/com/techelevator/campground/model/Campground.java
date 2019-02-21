package com.techelevator.campground.model;

public class Campground {
	
	private Long campgroundId;
	private Long parkId;
	private String name;
	private String openFromMonth;
	private String openToMonth;
	private double dailyFee;
	
	public Long getCampgroundId() {
		return campgroundId;
	}
	
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public Long getParkId() {
		return parkId;
	}
	
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOpenFromMonth() {
		return openFromMonth;
	}
	
	public void setOpenFromMonth(String openFromMonth) {
		this.openFromMonth = openFromMonth;
	}
	
	public String getOpenToMonth() {
		return openToMonth;
	}
	
	public void setOpenToMonth(String openToMonth) {
		this.openToMonth = openToMonth;
	}
	
	public double getDailyFee() {
		return dailyFee;
	}
	
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
}
