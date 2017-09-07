package com.cart.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment_charge")
public class ChargeSample {

	@Id
	@Column(name = "id", length = 45, nullable = false)
	private String id;
	
	@Column(name = "object", length = 45, nullable = false)
	private String object;
	
	@Column(name = "amount", length = 11, nullable = false)
	private int amount;
	
	@Column(name = "amount_refunded", length = 11, nullable = false)
	private int amount_refunded;
	
	@Column(name = "application", length = 45, nullable = true)
	private String application;
	
	@Column(name = "application_fee", length = 45, nullable = true)
	private String application_fee;
	
	@Column(name = "balance_transaction", length = 45, nullable = true)
	private String balance_transaction;
	//1=ture 0= false
	
	@Column(name = "captured", length = 1, nullable = false)
	private int captured;
	
	@Column(name = "created", length = 6, nullable = false)
	private Timestamp created;
	
	@Column(name = "currency", length = 45, nullable = false)
	private String currency;
	
	@Column(name = "customer", length = 45, nullable = true)
	private String customer;
	
	@Column(name = "description", length = 45, nullable = true)
	private String description;
	
	@Column(name = "order", length = 45, nullable = true)
	private String order;
	
	@Column(name = "paid", length = 1, nullable = false)
	private int paid;
	
	@Column(name = "refundsId", length = 45, nullable = false)
	private String refundsId;
	
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
	public int getAmount_refunded() {
		return amount_refunded;
	}
	public void setAmount_refunded(int amount_refunded) {
		this.amount_refunded = amount_refunded;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getApplication_fee() {
		return application_fee;
	}
	public void setApplication_fee(String application_fee) {
		this.application_fee = application_fee;
	}
	public String getBalance_transaction() {
		return balance_transaction;
	}
	public void setBalance_transaction(String balance_transaction) {
		this.balance_transaction = balance_transaction;
	}
	public int getCaptured() {
		return captured;
	}
	public void setCaptured(int captured) {
		this.captured = captured;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getPaid() {
		return paid;
	}
	public void setPaid(int paid) {
		this.paid = paid;
	}
	public String getRefundsId() {
		return refundsId;
	}
	public void setRefundsId(String refundsId) {
		this.refundsId = refundsId;
	}
	
	
}
