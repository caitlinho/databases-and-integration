package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;

public class JDBCCampgroundIntegrationTest {
	

	private int insertedCampgroundId;
	private static SingleConnectionDataSource dataSource;
	private JDBCCampgroundDAO dao;
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void createDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void destroyDataSource() {
		dataSource.destroy();
	}

	@Before
	public void setupTest() {
		dao = new JDBCCampgroundDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void get_multiple_campgrounds_from_chosen_parks() {
		clearCampgroundTable();
		int count = jdbcTemplate.queryForObject("SELECT count (*) FROM campground", Integer.class);
		
		List <Campground> results = dao.getAllCampgrounds();
		assertEquals(count, results.size());
	}
	
	@Test
	public void get_total_cost_of_stay() {
		
	}
	
	
	private void clearCampgroundTable() {
		String truncateCampgroundTableSql = "TRUNCATE Campground CASCADE";
		jdbcTemplate.update(truncateCampgroundTableSql);
	}
	
	private Campground getCampground(int CampgroundId, Long parkId, String name, String openFromMonth, String openToMonth, BigDecimal dailyFee) {
		Campground testCampground = new Campground();
		testCampground.setCampgroundId(CampgroundId);
		testCampground.setParkId(parkId);
		testCampground.setName(name);
		testCampground.setOpenFromMonth(openFromMonth);
		testCampground.setOpenToMonth(openToMonth);
		testCampground.setDailyFee(dailyFee);
		return testCampground;
	}
	
	private int insertingCampground (Long CampgroundId, Long parkId, String name, String openFromMonth, String openToMonth, BigDecimal dailyFee) {
		
		String campgroundSql = "INSERT INTO Campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) "
				+ "VALUES (DEFAULT, ?, ?, ?, ?, ?) "
				+ "RETURNING campground_id";
		SqlRowSet results = jdbcTemplate.queryForRowSet(campgroundSql, CampgroundId, parkId, name, openFromMonth, openToMonth, dailyFee);
		
		results.next();
		return results.getInt(1);
	}
}



