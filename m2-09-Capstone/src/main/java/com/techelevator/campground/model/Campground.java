package com.techelevator.campground.model;

import java.math.BigDecimal;

public class Campground {
	
	private int campgroundId;
	private Long parkId;
	private String name;
	private String openFromMonth;
	private String openToMonth;
	private BigDecimal dailyFee;
	
	public int getCampgroundId() {
		return campgroundId;
	}
	
	public void setCampgroundId(int campgroundId) {
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
	
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
