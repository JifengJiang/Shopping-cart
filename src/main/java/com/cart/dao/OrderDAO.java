package com.cart.dao;

import java.util.List;

import com.cart.model.CartInfo;
import com.cart.model.OrderDetailInfo;
import com.cart.model.OrderInfo;
import com.cart.model.PaginationResult;

public interface OrderDAO {

    public void saveOrder(CartInfo cartInfo);

    public PaginationResult<OrderInfo> listOrderInfo(int page,
                                                     int maxResult, int maxNavigationPage);

    public OrderInfo getOrderInfo(String orderId);

    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);

}