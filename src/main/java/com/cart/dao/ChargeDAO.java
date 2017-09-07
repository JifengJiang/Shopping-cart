package com.cart.dao;

import com.cart.entity.ChargeInfo;
import com.cart.entity.ChargeSample;
import com.cart.model.ChargeInfoModel;

public interface ChargeDAO {
	public ChargeInfo findCharge(String id);
	public void saveCharge(ChargeInfoModel charge);
}
