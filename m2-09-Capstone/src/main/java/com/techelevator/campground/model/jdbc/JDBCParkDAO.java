package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.Site;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<Park>();
		
		String selectSql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park ORDER BY name";

		SqlRowSet results = jdbcTemplate.queryForRowSet(selectSql);

		while (results.next()) {
			Park park = mapRowToPark(results);
			allParks.add(park);
		}
		
		return allParks;
	}

	@Override
	public Park getParkInformation(String name) {

		String selectSql = "SELECT park_id, name, location, establish_date, area, visitors, description FROM park "
				+ "WHERE name = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(selectSql, name);

		if (results.next()) {
			return mapRowToPark(results);

		}
		return null;

	}
	
	@Override
	public Park getParkByParkId(Long parkId) {
		String sqlPark = "SELECT park_id, name, location, establish_date, area, visitors, description "
				+ "FROM park " 
				+ "WHERE park_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlPark, parkId);

		if (results.next()) {
			return mapRowToPark(results);

		}
		return null;
	}
	

	private Park mapRowToPark(SqlRowSet results) {
		Park park = new Park();
		park.setParkId(results.getLong("park_id"));
		park.setName(results.getString("name"));
		park.setLocation(results.getString("location"));
		park.setEstablishDate(results.getDate("establish_date").toLocalDate());
		park.setArea(results.getInt("area"));
		park.setVisitors(results.getInt("visitors"));
		park.setDescription(results.getString("description"));
		return park;
	}



	

}
