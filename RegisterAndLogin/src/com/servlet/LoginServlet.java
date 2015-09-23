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
import javax.servlet.http.HttpSession;

import com.dbutil.ConnectionManager;
import com.model.User;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=gb2312");
		// ȡ�������û�������û���
		String username = request.getParameter("username");
		System.out.println("=================>"+username);
		// ȡ�������û����������
		System.out.println("u�ܴ�������?"+username);
		String password = request.getParameter("password");
		System.out.println("p�ܴ�������?"+password);
		PrintWriter pw=resp.getWriter();
//		OutputStream out=resp.getOutputStream();
		// �������û�
		User user = new User(username, password);
		if (isExist(user)) {
			// Ϊ�� ˵���û�����
			request.setAttribute("username", username);
			HttpSession session = request.getSession(true);
			session.setAttribute("username",username);
//			resp.sendRedirect("success.jsp");
			
//			req.setAttribute("lab", "true");
			
			pw.println(true);
//			out.write(1);
		} else {
//			resp.sendRedirect("error.jsp");
			pw.println(false);
//			out.write(2);
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter pw=resp.getWriter();
		String username = req.getParameter("username");
		System.out.println("u�ܴ�������?"+username);
		String password = req.getParameter("password");
		System.out.println("p�ܴ�������?"+password);
		User user = new User(username, password);
		if (isExist(user)) {
			// Ϊ�� ˵���û�����
			req.setAttribute("username", username);
//			resp.sendRedirect("success.jsp");
//			req.getRequestDispatcher("success.jsp").forward(req, resp);
			String sql="select * from user;";
			int count=0;
			List<User> list=new ArrayList<User>();
			try {
				Connection con=ConnectionManager.getConnection();
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next()){
					String username1=rs.getString("username");
					String password1=rs.getString("password");
					User user1=new User(username1,password1);
					list.add(user1);
				}
				//��������û�������
				 count=list.size();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println(count);
			pw.println(true);
			System.out.println("===pw==="+pw);
		} else {
//			resp.sendRedirect("error.jsp");
			pw.println(false);
		}
	}

	// �ж��û�������û��������Ƿ�����ȷ
	private boolean isExist(User user) {
		// �û�������
		try {
			Connection con = ConnectionManager.getConnection();
			String sql = "select * from user";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				if (user.getUsername().equals(username)
						&& user.getPassword().equals(password)) {
					// �û���¼�û������� һ��
					System.out.println(username);
					System.out.println(password);
					return true;
				} 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
