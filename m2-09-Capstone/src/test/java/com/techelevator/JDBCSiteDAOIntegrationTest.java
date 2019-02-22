package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;

public class JDBCSiteDAOIntegrationTest {

	private Long testSiteId;
	private int testCampgroundId = 1;
	private int testSiteNumber = 2;
	private int testMaxOccupancy = 3;
	private boolean testAccessible = false;
	private int testMaxRVLength = 0;
	private boolean testUtilities = false;
	

	private String testName = "testName";
	private LocalDate fromDateTest = LocalDate.parse("2019-01-21");
	private LocalDate toDateTest = LocalDate.parse("2019-01-25");
	private LocalDate createDateTest = LocalDate.parse("2019-01-18");

	
	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
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
		dao = new JDBCSiteDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
		clearSiteTable();
		String createSiteSql = "INSERT INTO Site (campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) "
				+ "VALUES (?, ?, ?, ?, ?, ?) "
				+ "RETURNING Site_id";
		
		testSiteId = jdbcTemplate.queryForObject(createSiteSql, Long.class, 
				testCampgroundId, testSiteNumber, testMaxOccupancy, testAccessible, testMaxRVLength, testUtilities);
	}

	@After
	public void cleanup() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void return_top_5_available_sites() {
		clearSiteTable();
		List <Reservation> reservations = new ArrayList<Reservation>();
		
		String sqlReservation = "SELECT reservation_id, site_id, name, from_date, to_date, create_date "
				+ "FROM reservation ";
		
		SqlRowSet reservationResults = jdbcTemplate.queryForRowSet(sqlReservation);
		
		while(reservationResults.next()) {
			Reservation r = mapRowToReservation(reservationResults);
			reservations.add(r);
		}	
	
		Reservation reservationOne = getReservation(testSiteId, testName, fromDateTest, toDateTest, createDateTest);
		Reservation reservationTwo = getReservation(testSiteId, "testName2", LocalDate.parse("2019-01-25"), LocalDate.parse("2019-01-28"), LocalDate.parse("2019-01-01"));
		Reservation reservationThree = getReservation(testSiteId, "testName3", LocalDate.parse("2019-01-28"), LocalDate.parse("2019-01-30"), LocalDate.parse("2019-01-02"));
		Reservation reservationFour = getReservation(testSiteId, "testName4", LocalDate.parse("2019-02-01"), LocalDate.parse("2019-02-02"), LocalDate.parse("2019-01-05"));
		Reservation reservationFive = getReservation(testSiteId, "testName5", LocalDate.parse("2019-03-01"), LocalDate.parse("2019-03-02"), LocalDate.parse("2019-01-09"));
		Reservation reservationSix = getReservation(testSiteId, "testName6", LocalDate.parse("2019-03-05"), LocalDate.parse("2019-03-02"), LocalDate.parse("2019-01-09"));
		
				
		List <Site> resultsReservation = dao.getTop5AvailableSitesByDate();


		
		assertEquals(count, results.size());
		
	}
	
	
	private void clearSiteTable() {
		String truncateSiteTableSql = "TRUNCATE Site CASCADE";
		jdbcTemplate.update(truncateSiteTableSql);
	}

	private Site getSite(Long siteId, int campgroundId, int siteNumber, int maxOccupancy, Boolean accessible, int maxRVLength, boolean utilities) {
		Site testSite = new Site();
		
		testSite.setSiteId(siteId);
		testSite.setCampgroundId(campgroundId);
		testSite.setSiteNumber(siteNumber);
		testSite.setMaxOccupancy(maxOccupancy);
		testSite.setAccessible(accessible);
		testSite.setMaxRVLength(maxRVLength);
		testSite.setUtilities(utilities);
		return testSite;
	}
		
	private Reservation getReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate) {
		Reservation testReservation = new Reservation();
		
		testReservation.setSiteId(siteId);
		testReservation.setName(name);
		testReservation.setFromDate(fromDate);
		testReservation.setToDate(toDate);
		testReservation.setCreateDate(createDate);
		return testReservation;
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation reservation = new Reservation();
		reservation.setReservationId(results.getLong("reservation_id"));
		reservation.setSiteId(results.getLong("site_id"));
		reservation.setName(results.getString("name"));
		reservation.setFromDate(results.getDate("from_date").toLocalDate());
		reservation.setToDate(results.getDate("to_date").toLocalDate());
		reservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return reservation;
	}
	
	private Site mapRowToSite(SqlRowSet results) {
		Site site = new Site();
		site.setSiteId(results.getLong("reservation_id"));
		site.setCampgroundId(results.getInt("campground_id"));
		site.setSiteNumber(results.getInt("site_number"));
		site.setMaxOccupancy(results.getInt("max_occupancy"));
		site.setAccessible(results.getBoolean("accessible"));
		site.setMaxRVLength(results.getInt("max_rv_length"));
		site.setUtilities(results.getBoolean("utilities"));
		return site;
	}	
}

	private void assertSitesAreEqual(Site expected, Site actual) {
		
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
		assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
		assertEquals(expected.isAccessible(), actual.isAccessible());
		assertEquals(expected.getMaxRVLength(), actual.getMaxRVLength());
		assertEquals(expected.isUtilities(), actual.isUtilities());
		
	}

	
}
