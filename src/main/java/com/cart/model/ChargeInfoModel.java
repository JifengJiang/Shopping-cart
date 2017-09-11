package com.cart.model;

import java.util.Date;

public class ChargeInfoModel {

	private String id;
	private String object;
	private int amount;
	private int user_id;
	
	
	public ChargeInfoModel(String id, String object, int amount, int userId)
	{
		this.id = id;
		this.object = object;
		this.amount = amount;
		this.user_id = userId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
}
