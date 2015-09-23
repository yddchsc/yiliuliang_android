package com.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://sqld.duapp.com:4050/LMpplvEePGmOLZUFeOhh";
	private static final String DATABASE_USERNAME = "7a2f0aa875c94306b77ef58cd43fa88d";
	private static final String DATEBASE_PASSWORD = "e4fdd5b5aef74b608462f1b8706e5a7d";

	// »ñÈ¡Á¬½Ó
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection con = DriverManager.getConnection(DATABASE_URL,
				DATABASE_USERNAME, DATEBASE_PASSWORD);
		return con;
	}

	public static void main(String[] args) {
		try {
			Connection  con=getConnection();
			Statement stmt=con.createStatement();
//			String sql="create table user(id int auto_increment primary key not null,username varchar(15) not null,password varchar(15) not null);";
//			stmt.execute(sql);
			stmt.execute("insert user(username,password) values('zhangsan','123')");
			stmt.execute("insert user(username,password) values('lisi','123')");
			stmt.execute("insert user(username,password) values('wangwu','123')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
