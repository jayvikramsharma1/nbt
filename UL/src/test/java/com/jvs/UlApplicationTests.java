package com.jvs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UlApplicationTests {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void contextLoads() {
		try {
			
			String pass1 = bCryptPasswordEncoder.encode("12345");
			String pass2 = bCryptPasswordEncoder.encode("12345");
			
			System.out.println("Pass 1 : " + pass1);
			System.out.println("Pass 1 : " + pass2);
			
			System.out.println(bCryptPasswordEncoder.matches("12345", pass2));
			
			
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("UPDATE user SET fullname="+ "'Jay Vikram Sharma'" +", password='"+bCryptPasswordEncoder.encode("12345")+"' where id="+1);
			statement.execute("select * from user");
			//statement.executeUpdate();
			ResultSet rs = statement.getResultSet();
			while(rs.next()) {
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("password"));
				System.out.println(rs.getString("fullname"));
				System.out.println(rs.getString("roles"));
			}
			statement.close();
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
