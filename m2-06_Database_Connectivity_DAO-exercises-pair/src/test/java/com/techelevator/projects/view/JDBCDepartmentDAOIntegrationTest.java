package com.techelevator.projects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.jdbc.JDBCDepartmentDAO;


public class JDBCDepartmentDAOIntegrationTest {
	
	private static final Long DEPARTMENT_ID = (long) 10;
	private static SingleConnectionDataSource dataSource;
	private JDBCDepartmentDAO dao;
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
		dao = new JDBCDepartmentDAO(dataSource);
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
	public void returns_all_departments() {
		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM department ", Integer.class);
		
//		System.out.println(count);
		
		List <Department> results = dao.getAllDepartments();
		assertEquals(count, results.size());
		
	}
	
	@Test
	public void returns_departments_searched_for_by_name() {
		Department newDepartment = getDepartment(0L, "AAA");
		dao.createDepartment(newDepartment);
		
		List <Department> results = dao.searchDepartmentsByName("AA");
		
		assertNotNull(results);
		Assert.assertTrue(results.size() > 0);
		assertEquals(results.get(0).getName(), newDepartment.getName());
	
	}
	
	@Test
	public void save_department() {
		Department testCreateDepartment = getDepartment(0L, "AAA");
		dao.createDepartment(testCreateDepartment);
		
		List<Department> departments = dao.searchDepartmentsByName("AAA");
		assertEquals(1L, departments.size());
		
		Department updatedDepartment = departments.get(0);
		updatedDepartment.setName("AAB");
		dao.saveDepartment(updatedDepartment);
		List<Department> changedDepartments = dao.searchDepartmentsByName("AAB");
		assertEquals(1L, changedDepartments.size());

	}
				
	@Test 
	public void create_new_department_and_read_it_back() {
		Department testCreateDepartment = getDepartment(0L, "AAA");
		dao.createDepartment(testCreateDepartment);
		
		Department createdDepartment = dao.getDepartmentById(testCreateDepartment.getId());
		
		assertNotEquals(null, testCreateDepartment.getId());
		assertDepartmentsAreEqual(testCreateDepartment, createdDepartment);
	
	}
	
	private Department getDepartment(Long id, String name) {
		Department returnDepartment = new Department();
		returnDepartment.setId(id);
		returnDepartment.setName(name);
		return returnDepartment;
	}
	
	private void assertDepartmentsAreEqual(Department expected, Department actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		
	}
	
	
	
}
