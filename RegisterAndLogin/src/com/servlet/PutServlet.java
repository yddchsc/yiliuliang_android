package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dbutil.ConnectionManager;

public class PutServlet extends HttpServlet {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		StringBuffer str=new StringBuffer();
		StringBuffer str1=new StringBuffer();
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=gb2312");
		
		String username=req.getParameter("username");
		String number=req.getParameter("number");
		String address=req.getParameter("address");
		String company=req.getParameter("company");
		String money=req.getParameter("money");
		
		System.out.println("username"+username);
		try {
			Connection con=ConnectionManager.getConnection();
			String sql="insert into scenery(username,number,address,company,money) values (?,?,?,?,?);";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, number);
			pstmt.setString(3, address);
			pstmt.setString(4, company);
			pstmt.setString(5, money);
			pstmt.execute();

//			resp.sendRedirect("registersuccess.jsp");
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
