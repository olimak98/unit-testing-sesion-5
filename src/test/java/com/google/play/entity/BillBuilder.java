package com.google.play.entity;

public class BillBuilder {

	private Bill bill;
	
	private BillBuilder() {}
	
	
	public static BillBuilder getBill() {
		BillBuilder builder = new BillBuilder();
		builder.bill = new Bill();
		builder.bill.setNetPrice(10.0);
		builder.bill.setNumMovies(10);
		builder.bill.setNumBill(1005944);
		
		return builder;
	}
	
	
	public BillBuilder otherNumBill(double numBill ) {
		bill.setNumBill(numBill);
		return this;
	}
	
	public Bill now() {
		return bill;
	}
	
	
	
}
