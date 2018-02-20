package com.jvs.config;


import java.sql.Connection;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBInitConfig {
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void initialize() {
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS user(" +
					"id INTEGER Primary key, " +
					"username varchar(30) not null, " +
					"password varchar(30) not null, " +
					"fullname varchar(30) not null, " +
					"roles varhar(30) not null )"
					);
			statement.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
