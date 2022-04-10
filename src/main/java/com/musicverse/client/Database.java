package com.musicverse.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	Connection conn = null;
	
	public Database() {
		this.connect();
	}
	
	public void connect() {
		
		 String host = "ec2-34-247-72-29.eu-west-1.compute.amazonaws.com";
		 String dbname = "dftvu9stg74fnk";
	        try {
	            conn = DriverManager.getConnection("jdbc:postgresql://"+
	        host+":5432/"+dbname+"?sslmode=require&user=aennmtvxwmyctc&password=410e8a8234efce60eb823705730e4119c808beab6f86509b41cf827fdc45a6b3");
	            System.out.println("Connected to the PostgreSQL server successfully.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}
	
	public boolean registerUser(String email,String username,String password) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO users (nickname,email,password,access_level,access_status,online) "
					+ "VALUES ('"+username+"', '"+email+"', '"+password+"', 1,'user',0)";
	        stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean nicknameExists(String nickname) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "select id from users where nickname='"+nickname+"'";
	        ResultSet rs = stmt.executeQuery(sql);
	        if(rs.next())
	        	return true;
	        else
	        	return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean emailExists(String email) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "select id from users where email='"+email+"'";
	        ResultSet rs = stmt.executeQuery(sql);
	        if(rs.next())
	        	return true;
	        else
	        	return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public ResultSet authenticate(String email, String password) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from users where email='"+email+"' and password='"+password+"'";
	        ResultSet rs = stmt.executeQuery(sql);
	        if(rs.next())
	        	return rs;
	        else
	        	return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
