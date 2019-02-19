package com.techelevator.projects.model.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class ProjectDAODemo {
	
	
	public static void main(String[] args) {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
	
		ProjectDAO projectDao = new JDBCProjectDAO(dataSource);
		
	}
}
