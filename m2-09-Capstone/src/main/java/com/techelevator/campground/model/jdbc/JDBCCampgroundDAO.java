package com.techelevator.campground.model.jdbc;

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
	public List<Campground> getAllCampgrounds(String name) {
		
		List<Campground> campgrounds = new ArrayList<Campground>();
		
		String selectSql = "SELECT campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee FROM campground";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(selectSql);
		
		while(results.next()) {
			Campground p = mapRowToCampground(results);
				campgrounds.add(p);
		}
		
		return campgrounds;
	}
	
	private Campground mapRowToCampground(SqlRowSet results) {
		Campground campground = new Campground();
		campground.setCampgroundId(results.getLong("campground_id"));
		campground.setParkId(results.getLong("park_id"));
		campground.setName(results.getString("name"));
		campground.setOpenFromMonth(results.getString("open_from_mm"));
		campground.setOpenToMonth(results.getString("open_to_mm"));
		campground.setDailyFee(results.getDouble("daily_fee"));
		return campground;
	}




}
