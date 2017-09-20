package com.cart.service;

import com.cart.model.ProductInfo;

public interface CheckoutService {
	public int getAvalibleNumber(ProductInfo product);
	public int buyProduct(ProductInfo product,  int flag);
}
