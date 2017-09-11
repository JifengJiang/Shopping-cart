package com.cart.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cart.dao.UserDAO;
import com.cart.entity.UserInfo;
import com.cart.model.CustomerInfo;

public class UserDAOImpl implements UserDAO{

	
	  @Autowired
	    private SessionFactory sessionFactory;

	@Override
	public void saveInfomation(CustomerInfo userInfo) {
		

		UserInfo user = new UserInfo();
		user.setId(0);
		user.setUserName(userInfo.getName());
		user.setUserAddress(userInfo.getAddress());
		user.setUserEmail(userInfo.getEmail());
		user.setUserTelephone(userInfo.getPhone());
		 this.sessionFactory.getCurrentSession().persist(user);
		 this.sessionFactory.getCurrentSession().flush();
	}

	@Override
	public List getUserId(CustomerInfo userInfo) {
		
		String name = userInfo.getName();
		String email = userInfo.getEmail();
		String telephone = userInfo.getPhone();
		String address = userInfo.getAddress();
		Session session = sessionFactory.getCurrentSession();
		 Criteria crit = session.createCriteria(UserInfo.class);
		 crit.add(Restrictions.eq("userName", name));
		 crit.add(Restrictions.eq("userEmail", email));
		 crit.add(Restrictions.eq("userTelephone", telephone));
		 crit.add(Restrictions.eq("userAddress", address));
		return  crit.list();
	}

}
