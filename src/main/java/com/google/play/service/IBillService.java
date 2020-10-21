package com.google.play.service;

import com.google.play.entity.Bill;

public interface IBillService {

	public void refundMoney(Bill bill);
	
	public double refundMoney();
}
