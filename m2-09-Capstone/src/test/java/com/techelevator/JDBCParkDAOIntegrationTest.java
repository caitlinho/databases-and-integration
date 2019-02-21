package com.techelevator;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;

import junit.framework.Assert;

public class JDBCParkDAOIntegrationTest {
	
	private Long insertedParkId;
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO dao;
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
		dao = new JDBCParkDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
		clearParkTable();
		String createParkSql = "INSERT INTO park (name, location, establish_date, area, visitors, description) "
				+ "VALUES ('testName','testLocation', '2019-02-21', 300, 4, 'The description of the park selected.') "
				+ "RETURNING park_id";
		
		insertedParkId = jdbcTemplate.queryForObject(createParkSql, Long.class);
	}

	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void information_of_park_is_being_listed() {
		
		Park comparePark = getPark(insertedParkId, "testName", "testLocation", LocalDate.parse("2019-02-21"), 300, 4, "The description of the park selected.");
		Park parks = dao.getParkInformation("testName");
		
		assertNotNull(parks);
		assertEquals(comparePark, parks);

	}
	
	private void clearParkTable() {
		String truncateParkTableSql = "TRUNCATE park CASCADE";
		jdbcTemplate.update(truncateParkTableSql);
	}
	
	private Park getPark(Long parkId, String name, String location, LocalDate establishDate, int area, int visitors, String description) {
		Park testPark = new Park();
		testPark.setParkId(parkId);
		testPark.setName(name);
		testPark.setLocation(location);
		testPark.setEstablishDate(establishDate);
		testPark.setArea(area);
		testPark.setVisitors(visitors);
		testPark.setDescription(description);
		return testPark;
	}
}
