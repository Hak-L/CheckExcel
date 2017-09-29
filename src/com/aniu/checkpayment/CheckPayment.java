package com.aniu.checkpayment;

import java.util.ArrayList;
import java.util.List;

import com.aniu.pojo.DateBaseEntity;
import com.aniu.pojo.PayMentEntity;


public class CheckPayment {
	
	 
	public static void main(String[] args) {
		//问股票点掌财经微信公众号
		String csv_one = "C:\\Users\\Administrator\\Desktop\\问股票点掌财经微信公众号支付明细20170926.csv";
		//问股票APP微信公众号支付明细
		String csv_two = "C:\\Users\\Administrator\\Desktop\\问股票APP微信公众号支付明细20170926.csv";
		
		//公众号官方下载下来的交易记录
		List<PayMentEntity> paymentlist = CsvUtil.getCvsList(csv_one,csv_two);
		
		String xlsx = "C:\\Users\\Administrator\\Desktop\\20170926.xlsx";
		
		//数据库里的问股票和订单记录列表
		List<DateBaseEntity> dblist =  XlsxUtil.getXlsxList(xlsx);
		
		//以微信为主去比较数据库
		checkMethod(dblist, paymentlist,1);
		//以数据库为主去比较微信公众号
		checkMethod(dblist, paymentlist,2);
		
		
	}
	
	private static void checkMethod(List<DateBaseEntity> dblist,List<PayMentEntity> paymentlist,int type){
		//比较后有问题的记录列表
		List<PayMentEntity> errorlist_one = new ArrayList<>();
		List<DateBaseEntity> errorlist_two = new ArrayList<>();
		
		if(paymentlist != null && !paymentlist.isEmpty() && dblist != null && !dblist.isEmpty() ){
			//以微信为主去比较数据库
			if(type == 1){
				System.out.println("-----------------------------以微信公众号为主去比较数据库--------------------------------");
				for(PayMentEntity p : paymentlist){
					int i = 0;
					for(DateBaseEntity d : dblist){
						/*if(p.getPayment_no().equals(d.getPayment_no())){
							System.out.println(p.getPayment_no() +"----"+d.getPayment_no());
						}
						if(p.getOrder_no().equals(d.getOrder_no())){
							System.out.println(p.getOrder_no() +"----"+d.getOrder_no());
						}*/
						if(p.getPayment_no().equals(d.getPayment_no()) && p.getOrder_no().equals(d.getOrder_no()) && p.getAmount().equals(d.getAmount())){
							i++;
							/*System.out.println(p.getAmount() +"----"+d.getAmount());*/
						}
					}
					/*System.out.println(i);*/
					if(i == 0){
						p.setErrmsg("错误微信支付单号:"+ p.getPayment_no());
						errorlist_one.add(p);
					}
				}
				System.out.println("微信公众号当天产生了："+paymentlist.size()+"条订单数据");
				System.out.println("数据库当天产生了："+dblist.size()+"条订单数据");
				
				if(errorlist_one != null && !errorlist_one.isEmpty()){
					System.out.println("今天共："+ errorlist_one.size()+"条异常订单，请人工核对一下。");
					for(PayMentEntity e : errorlist_one){
						System.out.println(e.getErrmsg());
					}
				}else{
					System.out.println("今天订单核对完毕，没有异常订单");
				}
			//以数据库为主去比较微信公众号	
			}else if(type == 2){
				System.out.println("-----------------------------以数据库为主去比较微信公众号----------------------------------");
				for(DateBaseEntity d : dblist){
					int i = 0;
					for(PayMentEntity p : paymentlist){
						
						if(d.getPayment_no().equals(p.getPayment_no()) && d.getOrder_no().equals(p.getOrder_no()) && d.getAmount().equals(p.getAmount())){
							i++;
						}
					}
					/*System.out.println(i);*/
					if(i == 0){
						d.setErrmsg("错误微信支付单号:"+ d.getPayment_no());
						errorlist_two.add(d);
					}
				}
				System.out.println("微信公众号当天产生了："+paymentlist.size()+"条订单数据");
				System.out.println("数据库当天产生了："+dblist.size()+"条订单数据");
				
				if(errorlist_two != null && !errorlist_two.isEmpty()){
					System.out.println("今天共："+errorlist_two.size()+"条异常订单，请人工核对一下。");
					for(DateBaseEntity e : errorlist_two){
						System.out.println(e.getErrmsg());
					}
				}else{
					System.out.println("今天订单核对完毕，没有异常订单");
				}
			}
				
		}else{
			System.out.println("数据库订单列表为空，或微信公众号列表为空，无法校对，请检查");
		}
	}
	
	
	
	
	
}
