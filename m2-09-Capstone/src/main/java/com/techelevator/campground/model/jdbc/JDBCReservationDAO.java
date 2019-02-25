package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}
	
	@Override
	public int addReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate) {
		String returnIdSql = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "  
				+ "VALUES (?, ?, ?, ?, ?) " 
				+ "RETURNING reservation_id"; 
		
		int returnReservationId = jdbcTemplate.queryForObject(returnIdSql, Integer.class, siteId, name, fromDate, toDate, createDate);

		return returnReservationId;
	}
		

	@Override
	public Reservation getActiveReservation(int reservationId) {
		
		String sqlReservation = "SELECT reservation_id, site_id, name, from_date, to_date, create_date "
				+ "FROM reservation " 
				+ "WHERE reservation_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReservation, reservationId);

		if (results.next()) {
			return mapRowToReservation(results);

		}

		return null;

	}
	//DELETE
	@Override
	public void cancelReservation(int reservationId) {
		String sqlCancelReservation = "DELETE FROM reservation "
				+ "WHERE reservation_id = ?";
		
		jdbcTemplate.update(sqlCancelReservation, reservationId);
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation reservation = new Reservation();
		reservation.setReservationId(results.getInt("reservation_id"));
		reservation.setSiteId(results.getInt("site_id"));
		reservation.setName(results.getString("name"));
		reservation.setFromDate(results.getDate("from_date").toLocalDate());
		reservation.setToDate(results.getDate("to_date").toLocalDate());
		reservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return reservation;
	}
	
}
