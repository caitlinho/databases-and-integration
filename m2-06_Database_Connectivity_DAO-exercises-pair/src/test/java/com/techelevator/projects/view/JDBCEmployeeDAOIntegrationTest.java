package com.techelevator.projects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;


public class JDBCEmployeeDAOIntegrationTest {

	private static SingleConnectionDataSource dataSource;
	private JDBCEmployeeDAO dao;
	private JdbcTemplate jdbcTemplate;

	
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
		dao = new JDBCEmployeeDAO(dataSource);

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
	public void returns_all_employees() {
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM employee ", Integer.class);
		
		List <Employee> results = dao.getAllEmployees();
		assertEquals(count, results.size());
		
	}
	
	@Test
	public void returns_employees_searched_for_by_name() {
		List <Employee> results = dao.getAllEmployees();
		List <Employee> searchResults = dao.searchEmployeesByName("Fredrick", "Keppard");
		
		assertNotNull(searchResults);
		Assert.assertTrue(searchResults.size() > 0);
		assertEquals(searchResults.get(0).getFirstName(), results.get(0).getFirstName());
	
	}
	
	@Test
	public void returns_employees_searched_for_by_department_id() {
		
		List <Employee> searchResults = dao.getEmployeesByDepartmentId(4L);
		
		assertNotNull(searchResults);
		Assert.assertTrue(searchResults.size() > 0);
		assertEquals(2L, searchResults.size());
	
	}

	@Test
	public void returns_employees_without_projects() {
		List <Employee> searchResults = dao.getEmployeesWithoutProjects();
		
		assertEquals(6L, searchResults.size());
	
	}

	@Test
	public void change_employee_department() {
		List <Employee> results = dao.getEmployeesByDepartmentId(2L);
		int count = results.size();

		dao.changeEmployeeDepartment(3L, 3L);
		List<Employee> changedDepartments = dao.getEmployeesByDepartmentId(2L);
		assertEquals(count - 1, changedDepartments.size());

	}


	private Employee getEmployee(Long id, Long departmentId, String firstName, String lastName, LocalDate birthDay, char gender, LocalDate hireDate) {
		Employee returnEmployee = new Employee();
		returnEmployee.setId(id);
		returnEmployee.setDepartmentId(departmentId);
		returnEmployee.setFirstName(firstName);
		returnEmployee.setLastName(lastName);
		returnEmployee.setBirthDay(birthDay);
		returnEmployee.setGender(gender);
		returnEmployee.setHireDate(hireDate);
		return returnEmployee;
	}
	
	private void assertEmployeesAreEqual(Employee expected, Employee actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getDepartmentId(), actual.getDepartmentId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getBirthDay(), actual.getBirthDay());
		assertEquals(expected.getGender(), actual.getGender());
		assertEquals(expected.getHireDate(), actual.getHireDate());

	}

	
}
