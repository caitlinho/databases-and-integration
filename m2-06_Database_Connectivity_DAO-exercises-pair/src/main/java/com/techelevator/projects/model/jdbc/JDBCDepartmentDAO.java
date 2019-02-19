package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;
import com.techelevator.projects.model.Project;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Department> getAllDepartments() {
		List<Department> departments = new ArrayList<Department>();
		
		String selectSql = "SELECT department_id, name " 
				+ "FROM department";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql);
		
		while (results.next()) {
			Department p = mapRowToDepartment(results);
				departments.add(p);
			}
			return departments;
	}

	
	private Department mapRowToDepartment(SqlRowSet results) {
		Department department = new Department();
		department.setId(results.getLong("department_id"));
		department.setName(results.getString("name"));
				
		return department;

	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		List<Department> department = new ArrayList<Department>();
		
		String selectSql = "SELECT department_id, name " + 
				"FROM department " + "WHERE name LIKE ('%' || ? || '%')";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql, nameSearch);
		
		while (results.next()) {
			Department p = mapRowToDepartment(results);
				department.add(p);
			}
			return department;
	}

	@Override
	public void saveDepartment(Department updatedDepartment) {

		String insertSql = "UPDATE department SET name = ? " 
				+ "WHERE department_id = ?";
		
		jdbcTemplate.update(insertSql, updatedDepartment.getName(), updatedDepartment.getId());
	}

	@Override
	public Department createDepartment(Department newDepartment) {
		
		String insertSql = "INSERT INTO department(department_id, name)" +
				"VALUES (DEFAULT, ?) RETURNING department_id";
		SqlRowSet results =  jdbcTemplate.queryForRowSet(insertSql, newDepartment.getName());
		
		results.next();
		newDepartment.setId(results.getLong("department_id"));
		return newDepartment;

	}

	@Override
	public Department getDepartmentById(Long id) {

		String selectSql = "SELECT department_id, name " + 
				"FROM department " + "WHERE department id = ?";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql, id);
		
		Department departmentId = mapRowToDepartment(results);
		
		return departmentId;

	}

}
