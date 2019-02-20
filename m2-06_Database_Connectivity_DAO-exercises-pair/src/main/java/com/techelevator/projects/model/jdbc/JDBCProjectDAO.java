package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		project.setId(results.getLong("project_id"));
		project.setName(results.getString("name"));
		
		if (results.getDate("from_date") != null) {
			project.setStartDate(results.getDate("from_date").toLocalDate());
		}
		
		if (results.getDate("to_date") != null) {
			project.setStartDate(results.getDate("to_date").toLocalDate());
		}
		
		return project;

	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		List<Project> projects = new ArrayList<Project>();
		
		String selectSql = "SELECT project_id, name, from_date, to_date " + 
				"FROM project";
		
		SqlRowSet results =  jdbcTemplate.queryForRowSet(selectSql);
		
		while (results.next()) {
			Project p = mapRowToProject(results);
				projects.add(p);
			}
			return projects;
	}
	
	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String deleteSql = "DELETE FROM project_employee "
				+ "WHERE employee_id = ? "
				+ "AND project_id = ?";
		
		jdbcTemplate.update(deleteSql, employeeId, projectId);
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String insertSql = "INSERT INTO project_employee (project_id, employee_id) "
				+ "VALUES (?, ?) RETURNING project_id, employee_id";
		
		jdbcTemplate.queryForRowSet(insertSql, projectId, employeeId);

	}

}
