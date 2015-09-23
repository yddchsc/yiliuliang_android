package com.model;

public class User {
	//用户登录名
	private String username;
	//用户登录密码
	private String password;
	//用户注册时重复密码
	private String pwd;
	public User() {
		
	}
	public User(String username,String password){
		this.username=username;
		this.password=password;
	}
	public User(String username,String password,String pwd){
		this.username=username;
		this.password=password;
		this.pwd=pwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
