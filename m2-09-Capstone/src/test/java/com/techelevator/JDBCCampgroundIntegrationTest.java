package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;

public class JDBCCampgroundIntegrationTest {
	

	private Long insertedCampgroundId;
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
		clearCampgroundTable();
		String createCampgroundSql = "INSERT INTO Campground (park_id, name, open_from_mm, open_to_mm, daily_fee) "
				+ "VALUES (3L,'testName','01', '08', 30.00) "
				+ "RETURNING Campground_id";
		
		insertedCampgroundId = jdbcTemplate.queryForObject(createCampgroundSql, Long.class);
	}

	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	
	private void clearCampgroundTable() {
		String truncateCampgroundTableSql = "TRUNCATE Campground CASCADE";
		jdbcTemplate.update(truncateCampgroundTableSql);
	}
	
	private Campground getCampground(Long CampgroundId, Long parkId, String name, String openFromMonth, String openToMonth, double dailyFee) {
		Campground testCampground = new Campground();
		testCampground.setCampgroundId(CampgroundId);
		testCampground.setParkId(parkId);
		testCampground.setName(name);
		testCampground.setOpenFromMonth(openFromMonth);
		testCampground.setOpenToMonth(openToMonth);
		testCampground.setDailyFee(dailyFee);
		return testCampground;
	}
}



