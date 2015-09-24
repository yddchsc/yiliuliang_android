package com.ym.listview_test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.dbutil.ConnectionManager;

public class SceneryService {
	private Connection conn ;
	private ResultSet res ;
	private String sql;
	private int page;
	private int countt;
	private int coun;
	private int count;
	public SceneryService(String sql,int page,int count){
		this.sql= sql;
		this.page= page;
		this.coun=count;
	}
	
	public List<Scenery> select_all(){
		List<Scenery> list = new ArrayList<Scenery>() ;
		Scenery scenery;
		if(page == 1){
			count = getCount();
		}
		else{
			count = coun;
		}
		try {
			conn=ConnectionManager.getConnection();
			Statement stmt=conn.createStatement();
			res=stmt.executeQuery(sql);
			while(res.next()){
				scenery = new Scenery() ;
				scenery.setId(res.getInt("id")) ;
				scenery.setUserName(res.getString("username")) ;
				scenery.setNumber(res.getInt("number")) ;
				scenery.setAddress(res.getString("address")) ;
				scenery.setCompany(res.getString("company")) ;
				scenery.setMoney(res.getInt("money")) ;
				scenery.setCount(count);
				list.add(scenery) ;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list ;
		
	}
	public int getCount(){
		countt = 0 ;
		String sq = "select count(*) from scenery" ;
		try {
			Connection conn=ConnectionManager.getConnection();
			Statement stmt=conn.createStatement();
			res=stmt.executeQuery(sq);
			if(res.next()){
				countt = (int) res.getLong(1) ;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countt ;
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
