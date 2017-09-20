package com.cart.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cart.dao.BuyDAO;
import com.cart.dao.ProductDAO;
import com.cart.entity.Product;
import com.cart.model.ProductInfo;
@Service("CheckoutService")
//@Transactional
public class CheckoutServiceImpl implements CheckoutService{
	@Autowired
	BuyDAO buyDAO;
	
	
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Transactional(readOnly=true)
	@Override
	public int getAvalibleNumber(ProductInfo product) {
		int amount =0;
		String code = product.getCode();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			amount = buyDAO.getAmountByLock(code);
			tx.commit();
	} catch (Exception e) {
		tx.rollback();
	}
		
		
		return amount;
	}

	@Transactional
	@Override
	public int buyProduct(ProductInfo product,int flag) {//valuate the quantities and amounts see wheather quantities > amount
		int result=0;//init is 0, 1 is ok, 2 is faile
		int amount = 0;
		int selectedNumber = product.getQuantity();
		double price = 0.0;
		int proPrice=0;
		int totalPrice=0;
		String code = product.getCode();
		Session session = sessionFactory.getCurrentSession();
		if (!session.getTransaction().isActive()) {
			Transaction tx = session.beginTransaction();
			try {
				amount = buyDAO.getAmountByLock(code);
				if (selectedNumber<=amount) {
					//这里需要有两部操作，1要更改数据库的库存2.需要算当前ID商品的总价
					if (flag==1) {
						
						buyDAO.buyProduct(product);
					}else
					{
						throw new Exception();
					}
					
//					pro = buyDAO.getProductByLock(code);
//					price = pro.getPrice();
//					proPrice= (int)((Math.ceil(price))*100);
//					totalPrice = proPrice*selectedNumber;
					if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
						tx.commit();
					}
					
					result=1;
				}
			} catch (Exception e) {
				tx.rollback();
				result=-1;
			}
		}else
		{
			Transaction tx = session.getTransaction();
			try {
				if (selectedNumber<=amount) {
					//这里需要有两部操作，1要更改数据库的库存2.需要算当前ID商品的总价
					buyDAO.buyProduct(product);
					
//					pro = buyDAO.getProductByLock(code);
//					price = pro.getPrice();
//					proPrice= (int)((Math.ceil(price))*100);
//					totalPrice = proPrice*selectedNumber;
					if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
						tx.commit();
					}
//					session.close();
					result=1;
				}
			} catch (Exception e) {
				tx.rollback();
				result=-1;
			}
		}
		
//		Product pro = new Product();
	
		return result;
		
	}

}
