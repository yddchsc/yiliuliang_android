package com.ym.listview_test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SceneryAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private HttpServletRequest request ;
	private Map<String, Object> jsonMap ;
	private List<Scenery> list ;
	private SceneryService service ;
	private Divpage divpage ;
	HttpServletResponse response ;
	private int count;
	
	public SceneryAction() {
		jsonMap = new HashMap<String, Object>() ;
		//service = new SceneryService(null);
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request ;
	}
	
	public String sceneryList(){
		jsonMap.clear() ;
		String pageNo = request.getParameter("pageNo") ;
		String[] strs = pageNo.split("/");
		String sql;
		service = new SceneryService("select id,username,number,address,company,money from scenery ORDER BY id DESC",0,0);
		int currentPage = 0 ;
		if(strs[0] == null){
			currentPage = 1 ;
		}else{
			currentPage = Integer.parseInt(strs[0]) ;
		}
		if (currentPage==1 || service.getCount()==Integer.parseInt(strs[1])){
			sql="select id,username,number,address,company,money from scenery ORDER BY id DESC";
			count = service.getCount();
			service = new SceneryService(sql,currentPage,Integer.parseInt(strs[1]));
		}else{
			count = Integer.parseInt(strs[1]);
			sql="select id,username,number,address,company,money from scenery ORDER BY id DESC "+"LIMIT "+Integer.toString(service.getCount()-count-1)+","+strs[1];
			service = new SceneryService(sql,currentPage,count);
		}
		
		list = service.select_all();
		
		int flag = count%new Divpage().getPageSize() == 0?count/new Divpage().getPageSize():count/new Divpage().getPageSize()+1 ;
		if(currentPage > flag){
			jsonMap.put("list", "") ;
			jsonMap.put("statue", "error") ;
			//jsonMap.put("", ) ;
			return  Action.ERROR ;
			
		}else{
		//	list = service.select_all() ;
			divpage = Divpage.getPage(currentPage, list.size()) ;
			//System.out.println("=================>"+count);
			List<Scenery> us = list.subList(divpage.getFromIndex(), divpage.getToIndex()) ;
			jsonMap.put("list", us) ;
			jsonMap.put("statue","success") ;
			return Action.SUCCESS ;
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response ;
	}
	
	
	

}
