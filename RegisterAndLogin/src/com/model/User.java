package com.model;

public class User {
	//�û���¼��
	private String username;
	//�û���¼����
	private String password;
	//�û�ע��ʱ�ظ�����
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
