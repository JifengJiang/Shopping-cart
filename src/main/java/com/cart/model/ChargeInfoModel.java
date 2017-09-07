package com.cart.model;

import java.util.Date;

public class ChargeInfoModel {

	private String id;
	private String object;
	private int amount;
	private Date createDate;
	
	public ChargeInfoModel(String id, String object, int amount, Date createDate)
	{
		this.id = id;
		this.object = object;
		this.amount = amount;
		this.createDate = createDate;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
