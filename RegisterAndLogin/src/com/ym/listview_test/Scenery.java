package com.ym.listview_test;

public class Scenery {
	
	private String username ;
	private int id ;
	private String address ;
	private String company ;
	private int number ;
	private int money ;
	private int count;
	
	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	

	public Scenery(String name, int id, String address,int number,String company,int money,int count) {
		this.username = username;
		this.id = id;
		this.address = address;
		this.number = number;
		this.company = company;
		this.money = money;
		this.count=count;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public Scenery() {
	}
	
	

}
