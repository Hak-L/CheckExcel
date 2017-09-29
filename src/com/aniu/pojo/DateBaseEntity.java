package com.aniu.pojo;

public class DateBaseEntity {
	
	//微信支付单号
	private String payment_no;
	//商户订单号
	private String order_no;
	//交易金额(元)
	private String amount;
	
	//错误信息反馈
	private String errmsg;
	
	
	
	
	
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getPayment_no() {
		return payment_no;
	}
	public void setPayment_no(String payment_no) {
		this.payment_no = payment_no;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	
}
