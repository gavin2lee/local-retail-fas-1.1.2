package cn.wonhigh.retail.fas.web.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.wonhigh.retail.fas.common.model.OrderPayway;
import cn.wonhigh.retail.fas.common.model.PaySaleCheck;

import com.yougou.logistics.base.common.exception.ManagerException;

public abstract class DayReportControllerHelper {
	private static Logger logger = Logger.getLogger(DayReportControllerHelper.class);
	/**
	 * 不同业务查询使用接口实现
	 * 
	 * @author wangshimiao
	 * 
	 */
	public abstract List<Map<String, Object>> getList(Map<String, Object> params) throws ManagerException;
	
	public abstract List<Map<String, Object>> getFooterList(Map<String, Object> params) throws ManagerException;

	public abstract List<Map<String, Object>> getExportList(Map<String, Object> params) throws ManagerException;
	
	public List<Map<String, Object>> getPaidinAmountForBrandByOutDate(List<Map<String, Object>> list, Map<String, BigDecimal> params) {
		for (Map<String, Object> map : list) {
			String shopNo = (String) map.get("shop_no");
			Date outDate = (Date) map.get("out_date");
			BigDecimal bg =  params.get(shopNo+outDate);
			if(bg != null){
				map.put("S01", bg);
			}else{
				map.put("S01", BigDecimal.valueOf(0d));
			}
		}
		return list;
	}
	
	public List<Map<String, Object>> dailyReportListByShopNo(List<Map<String, Object>> list,List<OrderPayway> payWays) {
		Map<String, Object> temp = null;
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for(Map<String, Object> m:list){
			String shopNo = (String) m.get("shop_no");
			Date outDate = (Date) m.get("out_date");
			if(null == map.get(shopNo+outDate)){
				temp = new HashMap<String, Object>();
				temp = this.initMap(temp, payWays);
				temp.put("shop_no", shopNo);
				temp.put("out_date", outDate);
				map.put(shopNo+outDate, temp);
			}
			this.sumToMap(map.get(shopNo+outDate),m);
		}
		
		//汇总到列表底部
		for(String key:map.keySet()){
			Map<String, Object> object = map.get(key);
			for(String k : object.keySet()){
				if(k.matches("P\\d+")){
					BigDecimal val = (BigDecimal) object.get(k);
					if(val!=null){
						object.put(k, val.setScale(2,BigDecimal.ROUND_HALF_UP));
					}
				}
			}
			String shopNo = (String) object.get("shop_no");
			Date outDate = (Date) object.get("out_date");
			int index = -1;
			for(Map<String, Object> m : list){
				if(shopNo.equals(m.get("shop_no")) && outDate.equals(m.get("out_date"))){
					index = list.indexOf(m);
				}
			}
			if(index > -1){
				object.put("shop_no", "小计");
				list.add(index+1, object);
			}
		}
		return list;
		   
	}
	
	private Map<String, Object> initMap(Map<String, Object> temp, List<OrderPayway> payWays) {
		for(OrderPayway payWay:payWays){
			temp.put("P"+payWay.getPayCode(), BigDecimal.valueOf(0d));
		}
		return temp;
	}

	private void sumToMap(Map<String, Object> map, Map<String, Object> m) {
		for(String key:map.keySet()){
			if(key.matches("P\\d+") || key.equals("totalAmount")
					|| key.matches("S\\d+") || key.equals("amount")
					|| key.matches("D\\d+") || key.equals("diffAmount")
					|| key.equals("returnAmount") || key.equals("actualReturnAmount")
					|| key.equals("poundage") || key.equals("sum")){
				BigDecimal mainVal = (BigDecimal) map.get(key);
				BigDecimal addVal = (BigDecimal) m.get(key);
				if(mainVal!=null && addVal !=null){
					map.put(key, mainVal.add(addVal));
				}
			}
		}
		
	}

	private List<Map<String, Object>> calculate(List<Map<String, Object>> dailyReportList, List<Map<String, Object>> t,
			Map<String, Map<String, Object>> map,Integer payWays) {
		if (t == null || t.size() <= 0)
			return dailyReportList;
		int index = dailyReportList.indexOf(t.get(0));// 第一个位置
		BigDecimal[] s = new BigDecimal[payWays];
		// 初始化数组
		for (int k = 0; k < s.length; k++) {
			s[k] = BigDecimal.valueOf(0d);
		}
		for (int i = 0; i < t.size(); i++) {
			Map<String, Object> temp = t.get(i);
			int j = 0;
			for(String key:temp.keySet()){
				if(key.equals("P\\d+")){
					BigDecimal val = (BigDecimal) temp.get(key);
					temp.put(key, val.setScale(2, BigDecimal.ROUND_HALF_UP));
					s[j] = s[j].add(val.setScale(2, BigDecimal.ROUND_HALF_UP));
					j++;
				}
			}
		}
		
		String shopName = (String) t.get(0).get("shop_name");
		Date outDate = (Date) t.get(0).get("out_date");
		Map<String, Object> ss = map.get(shopName+outDate);
		for (int m = 0; m < s.length; m++) {
			for(String key:ss.keySet()){
				if(key.equals("P\\d+")){
					if(s[m].compareTo((BigDecimal) ss.get(key)) != 0){
						int n = 0;
						s[m] = (BigDecimal) ss.get(key);
						while(n <t.size()-1){
							s[m] = s[m].subtract((BigDecimal) t.get(n).get(key));
							n++;
						}
						t.get(t.size()-1).put(key, s[m]);
					}
				}
			}
		}
		
		for(Map<String, Object> mm:t){
			dailyReportList.set(index, mm);
			index++;
		}

		return dailyReportList;
	}

	public List<Map<String, Object>> dailyReportListByBrand(List<Map<String, Object>> listMap) {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> obj:listMap){
			String shopNo = (String) obj.get("shop_no");
			Date outDate = (Date) obj.get("out_date");
			if(null == map.get(shopNo+outDate)){
				getMapList(listMap, list);
				list = new ArrayList<Map<String, Object>>();
				map.put(shopNo+outDate, obj);
			}
			if(!"小计".equals(obj.get("shop_no"))){
				list.add(obj);
			}
		}
		getMapList(listMap, list);
		return listMap;
		
	}

	private void getMapList(List<Map<String, Object>> listMap, List<Map<String, Object>> list) {
		if (list == null || list.size() <= 0)
			return;
		// 计算店铺某天所有品牌销售额
		BigDecimal totalAmount = BigDecimal.valueOf(0d);
		for(Map<String, Object> map:list){
			BigDecimal totalAmount1 = (BigDecimal) map.get("totalAmount");
			totalAmount = totalAmount.add(totalAmount1);
		}
		
		BigDecimal oldVal = BigDecimal.ZERO,totalVal = BigDecimal.ZERO,newVal = BigDecimal.ZERO;
		for(Map<String, Object> map:list){
			for(String key:map.keySet()){
				if(totalAmount.compareTo(BigDecimal.ZERO) != 0){
					if(key.matches("S\\d+") || key.equals("poundage") || key.equals("returnAmount")
							|| key.equals("actualReturnAmount")){
						oldVal = (BigDecimal) map.get(key);
						totalVal = (BigDecimal) map.get("totalAmount");
						newVal = oldVal.multiply(totalVal).divide(totalAmount,2,BigDecimal.ROUND_HALF_DOWN);
						map.put(key, newVal);
					}
				}
			}
			
			if(totalAmount.compareTo(BigDecimal.ZERO) != 0){
				oldVal = (BigDecimal) map.get("amount");
				totalVal = (BigDecimal) map.get("totalAmount");
				newVal = oldVal.multiply(totalVal).divide(totalAmount,2,BigDecimal.ROUND_HALF_DOWN);
				if(map.get("S01")!=null){
					newVal = newVal.add((BigDecimal) map.get("S01"));
				}
				map.put("amount", newVal);
			}
		}
		
		//计算差异金额
		for(Map<String, Object> map:list){
			Map<String, Object> temp = new HashMap<String, Object>();
			for(String key:map.keySet()){
				if(key.matches("P\\d+")){
					String str = key.substring(1);
					BigDecimal sVal = (BigDecimal) map.get("S"+str);
					BigDecimal pVal = (BigDecimal) map.get(key);
					if(sVal != null){
						temp.put("D"+str, sVal.subtract(pVal));
					}else{
						temp.put("D"+str, pVal.negate());
					}
				}else if(key.equals("totalAmount")){
					BigDecimal sVal = (BigDecimal) (map.get("amount")==null?BigDecimal.ZERO:map.get("amount"));
					BigDecimal pVal = (BigDecimal) map.get(key);
					BigDecimal returnAmount = (BigDecimal) map.get("returnAmount");
					BigDecimal actualReturnAmount = (BigDecimal) map.get("actualReturnAmount");
					temp.put("diffAmount", sVal.subtract(pVal).add(returnAmount).add(actualReturnAmount));
				}
			}
			BigDecimal amount = (BigDecimal) (map.get("amount")==null?BigDecimal.ZERO:map.get("amount"));
			BigDecimal poundage = (BigDecimal) map.get("poundage");
			BigDecimal actualReturnAmount = (BigDecimal) map.get("actualReturnAmount");
			temp.put("sum", amount.subtract(poundage).add(actualReturnAmount));
			map.putAll(temp);
		}
		
		
//		Map<String, Object> t = new HashMap<String, Object>();
//		int count = 0;
//		for(Map<String, Object> map:list){
//			if (count == list.size() - 1)
//				break;
//			int i = listMap.indexOf(map);
//			if (totalAmount.compareTo(BigDecimal.ZERO) != 0) {// 除数为正
//				Map<String, Object> temp = listMap.get(i);
//				for(String key:temp.keySet()){
//					if(key.matches("S\\d+") || key.equals("amount") || key.equals("returnAmount")
//						|| key.equals("actualReturnAmount")){
//						BigDecimal v1 = (BigDecimal) map.get(key);
//						BigDecimal v2 = (BigDecimal) map.get("totalAmount");
//						temp.put(key, v1.multiply(v2).divide(totalAmount, 2, BigDecimal.ROUND_HALF_DOWN));
//						
//						BigDecimal v = (BigDecimal) (t.get(key) !=null?t.get(key):BigDecimal.valueOf(0d));
//						t.put(key, v.add((BigDecimal) temp.get(key)));
//					}
//					
//				}
//				count++;
//			}
//			
//		}
		
	}
	
	/**
	 * 销售保留2位小数，及误差处理
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> getBrandReportListBySaleError(List<Map<String, Object>> list,List<OrderPayway> payWays) {
		List<Map<String, Object>> tempList = new ArrayList<>();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			for(String key:map.keySet()){
				if(key.matches("P\\d+")){
					BigDecimal val = (BigDecimal) map.get(key);
					if(null != val){
						map.put(key, val.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
			}
			String shopNo = (String) map.get("shop_no");
			if(!"小计".equals(shopNo)){
				tempList.add(map);
			}else{
				this.checkError(list,tempList,map,payWays);
				tempList = new ArrayList<Map<String, Object>>();
			}
		}
		for (Map<String, Object> map : list) {
			String shopNo = (String) map.get("shop_no");
			if(!"小计".equals(shopNo)){
				retList.add(map);
			}
		}
		
		return retList;
	}

	private void checkError(List<Map<String, Object>> list,List<Map<String, Object>> tempList, Map<String, Object> map,List<OrderPayway> payWays) {
		Map<String, Object> sumMap = new HashMap<String, Object>();
		sumMap = this.initMap(sumMap, payWays);
		
		int count = tempList.size();
		int index = count-1;
		ok:
		for(int i=0;i<count;i++){
			Map<String, Object> m = tempList.get(i);
			for(String key:m.keySet()){
				if(key.matches("P\\d+")){
					BigDecimal n = (BigDecimal) m.get(key);
					BigDecimal number = n.subtract(n.setScale(0, BigDecimal.ROUND_HALF_UP));//小数部分的数
					BigDecimal min = BigDecimal.valueOf(-0.01d);
					BigDecimal max = BigDecimal.valueOf(0.01d);
					if(BigDecimal.ZERO.compareTo(number.subtract(min)) != 0 && BigDecimal.ZERO.compareTo(number.subtract(max))!=0){
						index = i;
						break ok;
					}
				}
			}
		}
		//前n-1位求和
		for(int i=0;i<count;i++){
			if(i!=index){
				this.sumToMap(sumMap, tempList.get(i));
			}
		}
		
		Map<String, Object> last = tempList.get(index);
		int index1 = list.indexOf(last);
		for(String key:last.keySet()){
			if(key.matches("P\\d+")){
				BigDecimal all = (BigDecimal) map.get(key);
				BigDecimal sum = (BigDecimal) sumMap.get(key);
				BigDecimal val = (BigDecimal) last.get(key);
				if(null != all && null != val && val.compareTo(all.subtract(sum))!=0){
					last.put(key, all.subtract(sum));
				}
			}
		}
		list.set(index1, last);
		
	}
	
	public List<Map<String, Object>> getFooter(Map<String, Object> params) throws ManagerException {
		List<Map<String, Object>> list = this.getList(params);
		Map<String, Object> all = new HashMap<String, Object>();//合计对象
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();;
		all.put("shop_no", "合计");
		for (Map<String, Object> map : list) {
			for(String key:map.keySet()){
				if(key.matches("P\\d+") || key.equals("totalAmount")
					|| key.matches("S\\d+") || key.equals("amount")
					|| key.matches("D\\d+") || key.equals("diffAmount")
					|| key.equals("returnAmount") || key.equals("actualReturnAmount")
					|| key.equals("poundage") || key.equals("sum")){
					BigDecimal val = (BigDecimal) map.get(key);
					if(all.containsKey(key)){
						BigDecimal v = (BigDecimal) all.get(key);
						all.put(key, v.add(val));
					}else{
						all.put(key, val);
					}
					
				}
			}
		}
		ret.add(all);
		return ret;
	}
	
}
