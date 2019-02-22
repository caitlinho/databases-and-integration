package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.campground.model.Reservation;

import com.techelevator.campground.model.jdbc.JDBCReservationDAO;

public class JDBCReservationDAOIntegrationTest {
	
	private Long insertedReservationId;
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
				+ "VALUES (1, 'testName', '2019-01-21', '2019-01-25', '2019-01-18') "
				+ "RETURNING reservation_id";
		
		insertedReservationId = jdbcTemplate.queryForObject(createReservationSql, Long.class);
	}

	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	
	// Ask why these two objects pass as not equals -- ie, they are no longer the same item in the stack?
	@Test
	public void reservation_test_getting_by_id_then_canceled() {
		
		Reservation compareReservation = getReservation(insertedReservationId, 1L, "testName", LocalDate.parse("2019-01-21"), LocalDate.parse("2019-01-25"), LocalDate.parse("2019-01-18"));
		Reservation reservation = dao.getReservationId(insertedReservationId);
		
		assertNotNull(reservation);
		assertEquals(compareReservation, reservation);
		
		dao.cancelReservation(insertedReservationId);
		
		assertNotEquals(insertedReservationId, reservation);

	}
	
	private void clearReservationTable() {
		String truncateReservationTableSql = "TRUNCATE Reservation CASCADE";
		jdbcTemplate.update(truncateReservationTableSql);
	}
	
	private Reservation getReservation(Long reservationId, Long siteId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate) {
		Reservation testReservation = new Reservation();
		testReservation.setReservationId(reservationId);
		testReservation.setName(name);
		testReservation.setSiteId(siteId);
		testReservation.setFromDate(fromDate);
		testReservation.setToDate(toDate);
		testReservation.setCreateDate(createDate);
		return testReservation;
	}



}

	
