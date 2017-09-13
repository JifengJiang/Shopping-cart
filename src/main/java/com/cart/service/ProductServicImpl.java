package com.cart.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cart.dao.ProductDAO;
import com.cart.entity.Product;
import com.cart.model.PaginationResult;
import com.cart.model.ProductInfo;

@Service
@Transactional(readOnly=true)
public class ProductServicImpl implements ProductService{

	@Autowired
	ProductDAO productDAO;
	
    @Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Product findProduct(String code) {
		
		return productDAO.findProduct(code);
	}

	@Override
	public ProductInfo findProductInfo(String code) {
		// TODO Auto-generated method stub
		return productDAO.findProductInfo(code);
	}

	@Override
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
		// TODO Auto-generated method stub
		return productDAO.queryProducts(page, maxResult, maxNavigationPage);
	}

	@Override
	public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
			String likeName) {
		// TODO Auto-generated method stub
		return productDAO.queryProducts(page, maxResult, maxNavigationPage, likeName);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void save(ProductInfo productInfo) {
//		productDAO.save(productInfo);
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try
		{
			
		  productDAO.save(productInfo);
		  tx.commit();
		}catch(Exception e)
		{
			tx.rollback();
		}
		
		
	}

}
