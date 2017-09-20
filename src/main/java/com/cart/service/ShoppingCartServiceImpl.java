package com.cart.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cart.dao.BuyDAO;

@Transactional(readOnly=true)
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired 
	BuyDAO buyDAO;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public int getAvilableAmount(String code) {
		int product = 0;
//		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
		
		
			product=buyDAO.getAviableProductAmount(code);

		return product;
	}

	
}
