package com.aniu.checkpayment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aniu.pojo.PayMentEntity;

public class CsvUtil {

	public static List<PayMentEntity> getCvsList(String one,String two) {
		List<PayMentEntity> list = new ArrayList<>();
		
		if(one!=null && !"".equals(one)){
			
			List<PayMentEntity> list_one = getCvsListByFile(one);
			if(list_one != null && !list_one.isEmpty()){
				list.addAll(list_one);
			}
			
		}
		if(two!=null && !"".equals(two)){
			
			List<PayMentEntity> list_two = getCvsListByFile(two);
			if(list_two != null && !list_two.isEmpty()){
				list.addAll(list_two);
			}
			
		}
		return list;
	}
	
	private static List<PayMentEntity> getCvsListByFile(String name){
		FileInputStream fs = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		List<PayMentEntity> paymentlist = null;

		try {

			// 读CSV文件
			fs = new FileInputStream(name);
			// 设置输出内容格式，防止乱码
			isr = new InputStreamReader(fs, "GBK");
			br = new BufferedReader(isr);

			String line = "";
			int payment_index = 0;
			int order_index = 0;
			int amount_index = 0;
			int trade_index = 0;
			paymentlist = new ArrayList<>();
			
			while ((line = br.readLine()) != null) {
				//取台头字段名
				if (line.contains(PayMentEntity.getPaymentNoZw()) 
						&& line.contains(PayMentEntity.getOrderNoZw())
						&& line.contains(PayMentEntity.getAmountZw())
						&& line.contains(PayMentEntity.getTradeSceneZw())) {
					/*System.out.println(line);*/
					String[] title_array = line.split(",");
					List<String> title_list = new ArrayList<>(title_array.length);
					Collections.addAll(title_list, title_array);

					for (int i = 0; i < title_list.size(); i++) {
						String title = title_list.get(i);

						if (PayMentEntity.getPaymentNoZw().equals(title)) {
							payment_index = i;
						} else if (PayMentEntity.getOrderNoZw().equals(title)) {
							order_index = i;
						} else if (PayMentEntity.getAmountZw().equals(title)) {
							amount_index = i;
						} else if (PayMentEntity.getTradeSceneZw().equals(title)) {
							trade_index = i;
						}
					}
				}
				/*System.out.println(line);*/
				if(line != "" && !line.isEmpty() && line.charAt(0) == '2'){
					
					String[] title_array = line.split(",");
					List<String> title_list = new ArrayList<>(title_array.length);
					Collections.addAll(title_list, title_array);
					
					if(title_list != null && !title_list.isEmpty()){
						PayMentEntity payment = new PayMentEntity();
						payment.setPayment_no(title_list.get(payment_index).substring(1, title_list.get(payment_index).length()));
						payment.setOrder_no(title_list.get(order_index).substring(1, title_list.get(order_index).length()));
						String amount = title_list.get(amount_index);
						if(amount.contains(".")){
							payment.setAmount(amount);
			                }else{
			                payment.setAmount(amount+".00");
			            }
						
						payment.setTrade_scene(title_list.get(trade_index));
						paymentlist.add(payment);
					}
					
				}
			}
			/*System.out.println(paymentlist.size());*/

		} catch (IOException e) {
			System.out.println("文件读取错误");

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return paymentlist;
	}
}
