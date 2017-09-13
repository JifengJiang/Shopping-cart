package com.cart.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cart.dao.ChargeDAO;
import com.cart.entity.ChargeInfo;
import com.cart.model.ChargeInfoModel;


@Transactional
public class ChargeDAOImpl implements ChargeDAO{

	 @Autowired
	    private SessionFactory sessionFactory;

	@Override
	public ChargeInfo findCharge(String id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ChargeInfo.class);
		crit.add(Restrictions.eq("id", id));
		return (ChargeInfo)crit.uniqueResult();
	}

	@Override
	public void saveCharge(ChargeInfoModel charge) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String id = charge.getId();
		String object = charge.getObject();
		int amount = charge.getAmount();
		int user_id = charge.getUser_id();
		String sql = "insert into chargeInfo(id, object, amount, user_id) values ( '"+id+"' , '"+object+"' , "+ amount+", "+ user_id+")";
		SQLQuery sqlQuery = session.createSQLQuery(sql); 
		sqlQuery.executeUpdate();
//		sqlQuery.setResultTransformer(Transformers.aliasToBean(ChargeInfo.class));
//		 this.sessionFactory.getCurrentSession().persist(chargeInfo);
//		 this.sessionFactory.getCurrentSession().flush();
		 
		
	}
}
