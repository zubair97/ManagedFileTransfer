package com.osi.ftpapplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name= "user")
@Proxy(lazy=false)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userID;
	@Column(name = "username", nullable = false)
	private String userName;
	@Column(name = "password", nullable = false)
	private String password;
	
	public User() {
		
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + ", password=" + password + "]";
	}

}
