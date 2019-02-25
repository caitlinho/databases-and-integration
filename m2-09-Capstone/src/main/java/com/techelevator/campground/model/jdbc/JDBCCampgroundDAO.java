package com.techelevator.campground.model.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}

	@Override
	public List<Campground> getAllCampgrounds() {
		
		List<Campground> campgrounds = new ArrayList<Campground>();
		
		String selectSql = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee FROM campground";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(selectSql);
		
		while(results.next()) {
			Campground p = mapRowToCampground(results);
				campgrounds.add(p);
		}
		
		return campgrounds;
	}
	@Override
	public List<Campground> getAllCampgroundsByParkId(int parkId) {

		List<Campground> campgroundsByParkId = new ArrayList<Campground>();
		
		String selectSql = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee FROM campground "
						+ "WHERE park_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(selectSql, parkId);
		
		while(results.next()) {
			Campground p = mapRowToCampground(results);
				campgroundsByParkId.add(p);
		}
		
		return campgroundsByParkId;
	}


	
	@Override
	public BigDecimal getTotalCostOfReservation(long campgroundId, long lengthOfStay) {
		BigDecimal totalCost = null;
		String getPriceSql = "SELECT daily_fee * ? AS total_cost" 
				+ "FROM campground "
				+ "WHERE campground_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getPriceSql, lengthOfStay, campgroundId);
		
		if(results.next()) {
			totalCost = mapRowToTotalCost(results);
		}
		return totalCost;
	}	
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground campground = new Campground();
		campground.setCampgroundId(results.getInt("campground_id"));
		campground.setParkId(results.getLong("park_id"));
		campground.setName(results.getString("name"));
		campground.setOpenFromMonth(results.getString("open_from_mm"));
		campground.setOpenToMonth(results.getString("open_to_mm"));
		campground.setDailyFee(results.getBigDecimal("daily_fee"));
		return campground;
	}

	private BigDecimal mapRowToTotalCost(SqlRowSet results) {
		BigDecimal totalCost = results.getBigDecimal("total_cost");
		return totalCost;
		}

	
}
