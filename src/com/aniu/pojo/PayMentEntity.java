package com.aniu.pojo;

public class PayMentEntity {
	
	private static final String PAYMENT_NO_ZW = "微信支付单号";
	private static final String ORDER_NO_ZW = "商户订单号";
	private static final String AMOUNT_ZW = "交易金额(元)";
	private static final String TRADE_SCENE_ZW = "交易场景";
	
	
	//微信支付单号
	private String payment_no;
	//商户订单号
	private String order_no;
	//交易金额(元)
	private String amount;
	//交易场景
	private String trade_scene;
	//错误信息反馈
	private String errmsg;
	
	
	
	
	
	public static String getAmountZw() {
		return AMOUNT_ZW;
	}
	public static String getPaymentNoZw() {
		return PAYMENT_NO_ZW;
	}
	public static String getOrderNoZw() {
		return ORDER_NO_ZW;
	}
	public static String getTradeSceneZw() {
		return TRADE_SCENE_ZW;
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
	public String getTrade_scene() {
		return trade_scene;
	}
	public void setTrade_scene(String trade_scene) {
		this.trade_scene = trade_scene;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	
	
}	
