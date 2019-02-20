package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		
		String selectSql = "SELECT employee_id, department_id, first_name, last_name, birth_date, "
				+ "gender, hire_date FROM employee";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql);
		
		while (results.next()) {
			Employee p = mapRowToEmployee(results);
				employees.add(p);
			}
			return employees;

	}

	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee employees = new Employee();
		employees.setId(results.getLong("employee_id"));
		employees.setDepartmentId(results.getLong("department_id"));
		employees.setFirstName(results.getString("first_name"));
		employees.setLastName(results.getString("last_name"));
		employees.setBirthDay(results.getDate("birth_date").toLocalDate());
		employees.setGender(results.getString("gender").charAt(0));
		employees.setHireDate(results.getDate("hire_date").toLocalDate());
		
		return employees;

	}

	
	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> employees = new ArrayList<Employee>();
		
		String selectSql = "SELECT employee_id, department_id, first_name, last_name, birth_date, "
				+ "gender, hire_date FROM employee " 
				+ "WHERE first_name LIKE ('%' || ? || '%') "
				+ "AND last_name LIKE ('%' || ? || '%') ";

		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql, firstNameSearch, lastNameSearch);
		
		while (results.next()) {
			Employee p = mapRowToEmployee(results);
				employees.add(p);
			}
			return employees;

	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		List<Employee> employees = new ArrayList<Employee>();
		String selectSql = "SELECT employee_id, department_id, first_name, last_name, birth_date, "
				+ "gender, hire_date "
				+ "FROM employee " 
				+ "WHERE department_id = ?";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql, id);
		
		while (results.next()) {
			Employee p = mapRowToEmployee(results);
				employees.add(p);
			}
			return employees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> employees = new ArrayList<Employee>();
		
		String selectSql = "SELECT employee_id, department_id, first_name, last_name, birth_date, "  
				+ "gender, hire_date "
				+ "FROM employee LEFT JOIN project ON employee.employee_id = project.project_id "
				+ "WHERE project.project_id IS NULL";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql);
		
		while (results.next()) {
			Employee p = mapRowToEmployee(results);
				employees.add(p);
			}
			return employees;	
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> employees = new ArrayList<Employee>();
		
		String selectSql = "SELECT employee.employee_id, employee.department_id, employee.first_name, employee.last_name, employee.birth_date, "  
				+ "employee.gender, employee.hire_date "
				+ "FROM employee LEFT JOIN project_employee ON employee.employee_id = project_employee.project_id "
				+ "WHERE project_employee.project_id = ?";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql, projectId);
		
		while (results.next()) {
			Employee p = mapRowToEmployee(results);
				employees.add(p);
			}
			return employees;	

	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		String updateSql = "UPDATE employee SET department_id = ? " 
				+ "WHERE employee_id = ?";

		jdbcTemplate.update(updateSql, departmentId, employeeId);
	}

}
