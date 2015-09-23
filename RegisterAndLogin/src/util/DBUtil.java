package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Config;

/**
 * 数据库工具类
 */
public class DBUtil {
	
	/*
	 * 关闭数据库连接
	 */
	public static void closeAll(Connection conn,PreparedStatement pst,ResultSet rs){
		
		try {
			if(conn!=null){
				conn.close();
			}
			if(pst!=null){
				pst.close();
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 打开数据库连接
	 */
	public static Connection openConnection() {
//		Properties prop = new Properties();
		Config config=new Config("ServerConfig.properties");
		String driver = null;
		String url = null;
		String username = null;
		String password = null;

		try {
			driver = config.getString("driver");
			url = config.getString("url");
			username = config.getString("username");
			password = config.getString("password");
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}
