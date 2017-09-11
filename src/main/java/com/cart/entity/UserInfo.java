package com.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userInfo")
public class UserInfo {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 
	@Column(name = "userName", length = 45, nullable = false) 
	private String userName;
	
	@Column(name = "userEmail", length = 45, nullable = false) 
	private String userEmail;
	
	@Column(name = "userTelephone", length = 45, nullable = false) 
	private String userTelephone;
	
	@Column(name = "userAddress", length = 45, nullable = false) 
	private String userAddress;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserTelephone() {
		return userTelephone;
	}
	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	
}
