package com.techelevator.projects.view;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;
import com.techelevator.projects.model.jdbc.JDBCProjectDAO;



public class JDBCProjectDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCProjectDAO dao;
	private JDBCEmployeeDAO daoemp;
	private JdbcTemplate jdbcTemplate;
	private static final Long TEST_EMPLOYEE = 15L;

	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JDBCProjectDAO(dataSource);
		daoemp = new JDBCEmployeeDAO(dataSource);
		String insertEmployeeSql = "INSERT INTO employee (employee_id, department_id, first_name, last_name, birth_date, "
				+ "gender, hire_date) "
				+ "VALUES (?, 1, 'Kelsey', 'Smith', '1990-01-08', 'F', '2019-02-18')";
		jdbcTemplate.update(insertEmployeeSql, TEST_EMPLOYEE);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@AfterClass
	public static void closeDateSource() {
		dataSource.destroy();
	}

	@Test
	public void returns_all_projects() {
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM project ", Integer.class);
		
		List <Project> results = dao.getAllActiveProjects();
		assertEquals(count, results.size());
	}
	
	@Test
	public void adds_and_removes_an_employee_from_project() {
		List<Employee> employees = daoemp.getEmployeesByProjectId(1L);
		int count = employees.size();
		dao.addEmployeeToProject(1L, TEST_EMPLOYEE);
		employees = daoemp.getEmployeesByProjectId(1L);
		
		assertEquals(count + 1, employees.size());
		
		dao.removeEmployeeFromProject(1L, TEST_EMPLOYEE);
		employees = daoemp.getEmployeesByProjectId(1L);
		assertEquals(count, employees.size());
	}
	
	
	

	
	
	
	
	
	
	
}
