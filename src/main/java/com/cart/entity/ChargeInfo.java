package com.cart.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "chargeInfo")
public class ChargeInfo {

	@Id
	@Column(name = "id", length = 45, nullable = false)
	private String id;
	
	@Column(name = "object", length = 45, nullable = false)
	private String object;
	
	@Column(name = "amount", length = 11, nullable = false)
	private int amount;

	 @ManyToOne(targetEntity = UserInfo.class)
	    @JoinColumn(name = "user_id", nullable = false //
//	    foreignKey = @ForeignKey(name = "User_id_FK") 
	    )
	private int user_id;
	

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
