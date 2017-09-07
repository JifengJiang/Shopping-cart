package com.cart.model;

import java.util.Date;

public class ChargeInfoModel {

	private String id;
	private String object;
	private int amount;
	
	
	public ChargeInfoModel(String id, String object, int amount)
	{
		this.id = id;
		this.object = object;
		this.amount = amount;
		
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
	
	
}
