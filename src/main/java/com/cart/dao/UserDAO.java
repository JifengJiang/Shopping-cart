package com.cart.dao;

import java.util.List;

import com.cart.entity.UserInfo;
import com.cart.model.CustomerInfo;

public interface UserDAO {
	public void saveInfomation(CustomerInfo userInfo);
	public List getUserId(CustomerInfo userInfo);
}
