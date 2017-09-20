package com.cart.dao.impl;



import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cart.dao.BuyDAO;
import com.cart.entity.Product;
import com.cart.model.ProductInfo;
@Repository("BuyDAO")
@Transactional
public class BuyDAOImpl implements BuyDAO{

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public void buyProduct(ProductInfo product) {//update stock for selected product
		String code = product.getCode();
		int quantity = product.getQuantity();
		Session session = sessionFactory.getCurrentSession();
		
		String sql = "update Products set Stock = Stock- "+quantity+" where Code = '"+code+" ';";
		SQLQuery sqlQuery = session.createSQLQuery(sql); 
//		sqlQuery.setLockMode(code, LockMode.UPGRADE);
		sqlQuery.executeUpdate();
		
	}

	@Override
	public int getAviableProductAmount(String code) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "selec Stock from Products where Code = '"+code+"' ;";
		SQLQuery sqlQuery = session.createSQLQuery(sql); 
		 Integer value = (Integer) sqlQuery.uniqueResult();
		 return value;
	}

	@Override
	public int getAmountByLock(String code) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select Stock from Products where Code = '"+code+"' ;";
		SQLQuery query = session.createSQLQuery(sql);
		query.setLockMode(code, LockMode.UPGRADE);
		Integer value = (Integer) query.uniqueResult();
		
		return value;
	}

	@Override
	public Product getProductByLock(String code) {
		 Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(Product.class);
	        crit.setLockMode(code, LockMode.UPGRADE);
	        crit.add(Restrictions.eq("code", code));
	        return (Product) crit.uniqueResult();
	}

}
