package com.cart.dao;

import com.cart.entity.Product;
import com.cart.model.ProductInfo;

public interface BuyDAO {

	public void buyProduct(Product product);
	public int getAviableProductAmount(String code);
	public int getAmountByLock(String code);
	public Product getProductByLock(String code); 
}
