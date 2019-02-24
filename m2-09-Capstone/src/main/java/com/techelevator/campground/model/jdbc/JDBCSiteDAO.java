package com.techelevator.campground.model.jdbc;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}
	
	@Override
	public List<Site> getTop5AvailableSitesByDate(int campgroundId, LocalDate fromDate, LocalDate toDate) {
		
		List<Site> top5AvailableSites = new ArrayList<Site>();
		
		String top5SitesSql = "SELECT site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities " 
				+ "FROM site " 
				+ "JOIN campground ON site.campground_id = campground.campground_id "  
				+ "WHERE site.campground_id = ? AND site_id NOT IN (SELECT site.site_id FROM site " 
				+ "JOIN reservation ON reservation.site_id = site.site_id " 
				+ "WHERE (? > reservation.from_date AND ? < reservation.to_date) OR (? > reservation.from_date AND ? < reservation.to_date)) " 
				+ "ORDER BY daily_fee " 
				+ "LIMIT 5";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(top5SitesSql, campgroundId, fromDate, toDate, fromDate, toDate);
		
		while(results.next()) {
			Site site = mapRowToSite(results);
			top5AvailableSites.add(site);
		}
		return top5AvailableSites;
	}
	
	private Site mapRowToSite(SqlRowSet results) {
		Site site = new Site();
		site.setSiteId(results.getLong("site_id"));
		site.setCampgroundId(results.getInt("campground_id"));
		site.setSiteNumber(results.getInt("site_number"));
		site.setMaxOccupancy(results.getInt("max_occupancy"));
		site.setAccessible(results.getBoolean("accessible"));
		site.setMaxRVLength(results.getInt("max_rv_length"));
		site.setUtilities(results.getBoolean("utilities"));
		return site;
	}

	
	
}
