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

public class RegisterServlet extends HttpServlet {
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
		System.out.println("username"+username);
		String password1=req.getParameter("password1");
		String password2=req.getParameter("password2");
		PrintWriter pw=resp.getWriter();
		if(password1.equals(password2)){
			//两次密码输入一致
			if(!isExist(username)){
				//用户名已被注册
				
//				resp.sendRedirect("userexist.jsp");
				pw.println("userExist");
			}else{
				try {
					Connection con=ConnectionManager.getConnection();
					String sql="insert into user(username,password) values (?,?);";
					PreparedStatement pstmt=con.prepareStatement(sql);
					pstmt.setString(1, username);
					pstmt.setString(2, password1);
					pstmt.execute();
					//String filePath="D:\\struts\\RegisterAndLogin\\WebRoot\\";
					//String fileName="user.txt";
					
					// str1=new StringBuffer("用户名:"+username+"密码:"+password1);
					//str=str1.append(str);
					//System.out.println("11111"+str.toString());
					//File file=new File(fileName);
					//if (!file.exists()) {
					//file.createNewFile();
					//}
					//PrintWriter pfp=new PrintWriter(file);
					//pfp.println("");
					//pfp.println(str.toString());
					//pfp.println("");
					//pfp.close();
					pw.println("success");
//					resp.sendRedirect("registersuccess.jsp");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}else{
//			resp.sendRedirect("passworderror.jsp");
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	//用户名是否已经注册
	public boolean isExist(String username){
		try {
			Connection con=ConnectionManager.getConnection();
			Statement stmt=con.createStatement();
			String sql="select * from user;";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				String user=rs.getString("username");
				System.out.println(user);
				if(user.equals(username)){
					//说明用户名已被注册
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  true;
	}
}
