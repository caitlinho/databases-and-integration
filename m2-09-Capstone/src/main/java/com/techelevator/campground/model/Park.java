package com.techelevator.campground.model;

import java.sql.Date;
import java.time.LocalDate;

public class Park {

	private Long parkId;
	private String name;
	private String location;
	private LocalDate establishDate;
	private int area;
	private int visitors;
	private String description;
	
	
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
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public LocalDate getEstablishDate() {
		return establishDate;
	}
	
	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}
	
	public int getArea() {
		return area;
	}
	
	public void setArea(int area) {
		this.area = area;
	}
	
	public int getVisitors() {
		return visitors;
	}
	
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + area;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((establishDate == null) ? 0 : establishDate.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parkId == null) ? 0 : parkId.hashCode());
		result = prime * result + visitors;
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
		Park other = (Park) obj;
		if (area != other.area)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (establishDate == null) {
			if (other.establishDate != null)
				return false;
		} else if (!establishDate.equals(other.establishDate))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parkId == null) {
			if (other.parkId != null)
				return false;
		} else if (!parkId.equals(other.parkId))
			return false;
		if (visitors != other.visitors)
			return false;
		return true;
	}
	
	
}
