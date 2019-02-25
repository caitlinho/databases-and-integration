package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.bind.ParseConversionEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Reservation;

import com.techelevator.campground.model.jdbc.JDBCReservationDAO;

public class JDBCReservationDAOIntegrationTest {
	
	private int insertedReservationId;
	private int testSiteId = 1;
	private String testName = "testName";
	private LocalDate fromDateTest = LocalDate.parse("2019-01-21");
	private LocalDate toDateTest = LocalDate.parse("2019-01-25");
	private LocalDate createDateTest = LocalDate.parse("2019-01-18");
	private static SingleConnectionDataSource dataSource;
	private JDBCReservationDAO dao;
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
		dao = new JDBCReservationDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
		clearReservationTable();
		String createReservationSql = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "
				+ "VALUES (?, ?, ?, ?, ?) "
				+ "RETURNING reservation_id";
		
		insertedReservationId = jdbcTemplate.queryForObject(createReservationSql, Integer.class, testSiteId, 
				testName, fromDateTest, toDateTest, createDateTest);
	}

	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
//	@Test
//	public void adds_reservation() {
//		Reservation testReservation = getReservation();
//		
//		int confirmationId = dao.addReservation(testReservation);
//		assertNotNull(confirmationId);
//		Reservation retrievedReservation = dao.getActiveReservation(confirmationId);
//		assertReservationsAreEqual(testReservation, retrievedReservation);
//	}
	
	@Test
	public void reservation_test_getting_by_id_then_canceled() {
		
		Reservation compareReservation = getReservation();
		Reservation reservation = dao.getActiveReservation(insertedReservationId);
		
		assertNotNull(reservation);
		assertReservationsAreEqual(compareReservation, reservation);
		
		dao.cancelReservation(insertedReservationId);
		
		assertNull(null, dao.getActiveReservation(insertedReservationId));

	}
	
	private void clearReservationTable() {
		String truncateReservationTableSql = "TRUNCATE Reservation CASCADE";
		jdbcTemplate.update(truncateReservationTableSql);
	}
	
	private Reservation getReservation() {
		Reservation testReservation = new Reservation();
		
		testReservation.setName(testName);
		testReservation.setSiteId(testSiteId);
		testReservation.setFromDate(fromDateTest);
		testReservation.setToDate(toDateTest);
		testReservation.setCreateDate(createDateTest);
		return testReservation;
	}
	
	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
		
	}



}

	
