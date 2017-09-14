package com.cart.service;

import com.cart.entity.Order;
import com.cart.entity.OrderDetail;

public interface JsonWebTokenService {
    public Order orderId(String id);
    public Order Payload(String id);
}
