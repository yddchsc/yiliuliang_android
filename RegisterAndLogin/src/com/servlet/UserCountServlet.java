package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dbutil.ConnectionManager;
import com.model.User;

public class UserCountServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  int  count;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sql="select * from user;";
		List<User> list=new ArrayList<User>();
		try {
			Connection con=ConnectionManager.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				String username=rs.getString("username");
				String password=rs.getString("password");
				User user=new User(username,password);
				list.add(user);
			}
			//获得所有用户的数量
			count=list.size();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		req.setAttribute("count", count);
//		req.getRequestDispatcher("usercount.jsp").forward(req, resp);
		PrintWriter pw=resp.getWriter();
		pw.println(count);
	}
}
