package com.ym.struts_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class TestAction implements Action,ServletRequestAware,ServletResponseAware{
	
	
	Map<String, Object> map  ;
	HttpServletRequest request ;
	
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public TestAction() {
		map = new HashMap<String, Object>() ;
	}

	public String login(){
		map.clear() ;
		
		String op = request.getParameter("op") ;
		String type = request.getParameter("scenery_list") ;
		
		if(op.equals("json")&&type.equals("scenerys")){
			List<Message> list = new ArrayList<Message>() ;
			//Map<String, Object> map = new HashMap<String, Object>() ;
			List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>() ;
			Map<String, Object> map2 = new HashMap<String, Object>() ;
			
			Message message = new Message() ;
			Message message2 = new Message() ;
			Message message3 = new Message() ;
			
			message.setId(2) ;
			message.setMsg("dd") ;
			message2.setId(3) ;
			message2.setMsg("ff") ;
			message3.setId(4) ;
			message3.setMsg("dd") ;
			
			list.add(message2) ;
			list.add(message3) ;
			
			map2.put("id", 4) ;
			map2.put("msg",list) ;
			list2.add(map2) ;
			
			
			
			map.put("msg", message) ;
			map.put("list", list) ;
			map.put("lsit2", list2) ;
			map.put("success", true) ;
			
			return Action.SUCCESS ;
		}else{
			map.put("error", false) ;
			return Action.SUCCESS ;
		}
		
		
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request ;
	}

}
