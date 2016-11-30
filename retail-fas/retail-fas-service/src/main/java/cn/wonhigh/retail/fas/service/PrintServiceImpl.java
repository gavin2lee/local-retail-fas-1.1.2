package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.dto.PrintBalanceDiffDto;
import cn.wonhigh.retail.fas.common.dto.PrintBalanceDto;
import cn.wonhigh.retail.fas.common.dto.PrintBalanceEntry;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.dal.database.FinancialAccountMapper;
import cn.wonhigh.retail.fas.dal.database.PrintMapper;

import com.yougou.logistics.base.common.exception.ServiceException;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * orderUnitizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("printService")
class PrintServiceImpl implements PrintService {
    @Resource
    private PrintMapper printMapper;
    
    @Resource
    private FinancialAccountMapper financialAccountMapper;
	@Override
	public Map<String, Object> getPrintListByBalanceNo(String balanceNo)
			throws ServiceException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap = setColumnMapList(balanceNo,resultMap);
			resultMap = setRowMapList(balanceNo,resultMap);
    		return resultMap;
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBalanceGatherListByBalanceNo(
			String balanceNo, Date balanceEndDate) throws ServiceException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(balanceEndDate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) +1;
			Map<String, Object> brandsMap = new HashMap<String, Object>();
			balanceNo = FasUtil.formatInQueryCondition(balanceNo);
    		List<PrintBalanceDto> lstItem = printMapper.selectBalanceGatherList(balanceNo);
    		// 行记录
    		for (PrintBalanceDto balanceDto : lstItem) {
    			String brandUnitNo = balanceDto.getBrandUnitNo();
    			String categoryNo = balanceDto.getCategoryNo();
    			String groupName = balanceDto.getGroupName();
    			String categoryName = balanceDto.getCategoryName();
    			String genderName = balanceDto.getGenderName();
    			String key = categoryNo.equals("01") ? groupName+categoryName+genderName : categoryName ;
    			if(null == brandsMap.get(brandUnitNo)){
    				Map<String, Object> map = new HashMap<String, Object>();
    				List<Map<String, Object>> columnList = getColumnList();
    				List<Map<String, Object>> yearColumnList = getYearColumnList();
    				Map<String, Object> rowMap = new HashMap<String, Object>();
    				List<PrintBalanceDto> footerList = new ArrayList<PrintBalanceDto>();
    				List<PrintBalanceDto> yearFooterList = new ArrayList<PrintBalanceDto>();
    				Map<String, Object> notShoesMap = new HashMap<String, Object>();
    				List<PrintBalanceDto> list = new ArrayList<PrintBalanceDto>();
    				list.add(balanceDto);
    				rowMap.put(key, list);
    				map.put("columnList", columnList);
    				map.put("rowMap", rowMap);
    				map.put("footerList", footerList);
    				map.put("yearFooterList", yearFooterList);
    				map.put("yearColumnList", yearColumnList);
    				map.put("notShoesMap", notShoesMap);
    				brandsMap.put(brandUnitNo, map);
    			}else{
    				Map<String, Object> map = (Map<String, Object>) brandsMap.get(brandUnitNo);
    				Map<String, Object> rowMap = (Map<String, Object>) map.get("rowMap");
    				if(null==rowMap.get(key)){
    					List<PrintBalanceDto> list = new ArrayList<PrintBalanceDto>();
    					list.add(balanceDto);
    					rowMap.put(key, list);
    				}else{
    					List<PrintBalanceDto> list = (List<PrintBalanceDto>) rowMap.get(key);
    					list.add(balanceDto);
    				}
    			}
    			if(!"01".equals(balanceDto.getCategoryNo())){
    				balanceDto.setGenderName(balanceDto.getGroupName());
    			}
			}
    		// 工鞋计算
			String strMonth = month <10 ? "0"+month : String.valueOf(month);
			Map<String, Object> params = new HashMap<>();
			params.put("brandUnitNo", lstItem.get(0).getBrandUnitNo());
			params.put("yearMonthStart", year+"-"+strMonth);
			params.put("yearMonthEnd", year+"-"+strMonth);
			String lstBalanceNo =  printMapper.selectWorkShoesBalanceNo(params);
			List<PrintBalanceDto> lstWorkShoes = new ArrayList<>();
			if(StringUtils.isNotBlank(lstBalanceNo)){
				lstWorkShoes = printMapper.selectBalanceGatherList(lstBalanceNo);
			}
    		// 合计行
    		List<PrintBalanceDto> lstFooter = printMapper.selectBalanceGatherFooter(balanceNo);
    		for (PrintBalanceDto footerDto : lstFooter) {
    			String brandUnitNo = footerDto.getBrandUnitNo();
    			String categoryNo = footerDto.getCategoryNo();
    			String groupName = footerDto.getGroupName();
    			String categoryName = footerDto.getCategoryName();
    			String genderName = footerDto.getGenderName();
    			String key = categoryNo.equals("01") ? groupName+categoryName+genderName : categoryName ;
    			Map<String, Object> map = (Map<String, Object>) brandsMap.get(brandUnitNo);
    			Map<String, Object> rowMap = (Map<String, Object>) map.get("rowMap");
    			List<PrintBalanceDto> footerList = (List<PrintBalanceDto>) map.get("footerList");
    			Map<String, Object> notShoesMap = (Map<String, Object>) map.get("notShoesMap");
    			List<PrintBalanceDto> list = (List<PrintBalanceDto>) rowMap.get(key);
    			if("合计".equals(footerDto.getSalerName()) && categoryNo.equals("01")){// 鞋类合计
    				// 工鞋计算
    				for (PrintBalanceDto shoes : lstWorkShoes) {
						if(groupName.equals(shoes.getGroupName())){
							footerDto.setSendAmount(shoes.getSendAmount().add(footerDto.getSendAmount()));
							footerDto.setReturnAmount(shoes.getReturnAmount().add(footerDto.getReturnAmount()));
							footerDto.setCustomReturnAmount(shoes.getCustomReturnAmount().add(footerDto.getCustomReturnAmount()));
							footerDto.setDeductionAmount(shoes.getDeductionAmount().add(footerDto.getDeductionAmount()));
							footerDto.setBalanceAmount(shoes.getBalanceAmount().add(footerDto.getBalanceAmount()));
							footerDto.setSendQty(shoes.getSendQty()+footerDto.getSendQty());
							footerDto.setReturnQty(shoes.getReturnQty()+footerDto.getReturnQty());
							footerDto.setCustomReturnQty(shoes.getCustomReturnQty()+footerDto.getCustomReturnQty());
							footerDto.setBalanceQty(shoes.getBalanceQty()+footerDto.getBalanceQty());
						}
					}
    				footerList.add(footerDto);
    			}else if("合计".equals(footerDto.getSalerName()) && !categoryNo.equals("01")){// 非鞋类合计
    				if(null == notShoesMap.get(key)){
    					notShoesMap.put(key, footerDto);
    					footerList.add(footerDto);
    				}else{
    					PrintBalanceDto dto = (PrintBalanceDto) notShoesMap.get(key);
    					dto.setSendAmount(dto.getSendAmount().add(footerDto.getSendAmount()));
    					dto.setReturnAmount(dto.getReturnAmount().add(footerDto.getReturnAmount()));
    					dto.setCustomReturnAmount(dto.getCustomReturnAmount().add(footerDto.getCustomReturnAmount()));
    					dto.setDeductionAmount(dto.getDeductionAmount().add(footerDto.getDeductionAmount()));
    					dto.setBalanceAmount(dto.getBalanceAmount().add(footerDto.getBalanceAmount()));
    					dto.setSendQty(dto.getSendQty()+footerDto.getSendQty());
    					dto.setReturnQty(dto.getReturnQty()+footerDto.getReturnQty());
    					dto.setCustomReturnQty(dto.getCustomReturnQty()+footerDto.getCustomReturnQty());
    					dto.setBalanceQty(dto.getBalanceQty()+footerDto.getBalanceQty());
    				}
    				PrintBalanceDto balanceDto = (PrintBalanceDto) notShoesMap.get(key);
    				balanceDto.setCost(balanceDto.getSendQty().intValue() == 0?new BigDecimal(0):balanceDto.getSendAmount().divide(new BigDecimal(balanceDto.getSendQty()), 2,BigDecimal.ROUND_UP));
    			}else if("小计".equals(footerDto.getSalerName()) && categoryNo.equals("01")){// 分类小计
    				// 工鞋计算
    				for (PrintBalanceDto shoes : lstWorkShoes) {
						if(groupName.equals(shoes.getGroupName()) 
								&& genderName.equals(shoes.getGenderName())){
							list.add(shoes);
							shoes.setSalerName(shoes.getSalerName().concat("(").concat("工作鞋").concat(")"));
							footerDto.setSendAmount(shoes.getSendAmount().add(footerDto.getSendAmount()));
							footerDto.setReturnAmount(shoes.getReturnAmount().add(footerDto.getReturnAmount()));
							footerDto.setCustomReturnAmount(shoes.getCustomReturnAmount().add(footerDto.getCustomReturnAmount()));
							footerDto.setDeductionAmount(shoes.getDeductionAmount().add(footerDto.getDeductionAmount()));
							footerDto.setBalanceAmount(shoes.getBalanceAmount().add(footerDto.getBalanceAmount()));
							footerDto.setSendQty(shoes.getSendQty()+footerDto.getSendQty());
							footerDto.setReturnQty(shoes.getReturnQty()+footerDto.getReturnQty());
							footerDto.setCustomReturnQty(shoes.getCustomReturnQty()+footerDto.getCustomReturnQty());
							footerDto.setBalanceQty(shoes.getBalanceQty()+footerDto.getBalanceQty());
						}
					}
    				if(list.size() >1){
    					footerDto.setiCount(list.size());
    					list.add(footerDto);
    				}
    			}
			}
    		// 数据拼装
    		for (Entry<String, Object> entry : brandsMap.entrySet()) {
    			List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
    			String brandUnitNo = entry.getKey();
				Map<String, Object> map = (Map<String, Object>) entry.getValue();
				Map<String, Object> rowMap = (Map<String, Object>) map.get("rowMap");
				List<PrintBalanceDto> footerList = (List<PrintBalanceDto>) map.get("footerList");
				for (PrintBalanceDto footerDto : footerList) {
					Map<String, Object> allMap = new HashMap<String, Object>();
					List<PrintBalanceDto> rowList = new ArrayList<PrintBalanceDto>();
					String groupKey = footerDto.getCategoryNo().equals("01") ? footerDto.getGroupName()+footerDto.getCategoryName() : footerDto.getCategoryName() ;
					for (Entry<String, Object> rowEntry : rowMap.entrySet()) {
						String rowKey = rowEntry.getKey();
						List<PrintBalanceDto> list = (List<PrintBalanceDto>) rowEntry.getValue();
						if(rowKey.indexOf(groupKey)!=-1){
							rowList.addAll(list);
						}
					}
					rowList.add(footerDto);
					allMap.put("title", groupKey +"进货汇总");
					allMap.put("rowList", rowList);
					allList.add(allMap);
				}
				allList.add(getYearFooterMap(brandUnitNo,year,month));
				map.remove("rowMap");
				map.remove("footerMap");
				String title = year+"年"+month+"月（全月）"+brandUnitNo+"品牌对账单";
				map.put("title", title);
				map.put("rowList", allList);
			}
    		
    		return brandsMap;
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}
	
	
	private Map<String, Object> getYearFooterMap(String brandUnitNo,int year,int month){
			List<PrintBalanceDto> newYearFooterList = new ArrayList<PrintBalanceDto>();
			Map<String, Object> yearFooterMap = new HashMap<String, Object>();
			Map<String, Object> notShoesMap = new HashMap<String, Object>();
			Map<String, Object> shoesMap = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			String strMonth = month <10 ? "0"+month : String.valueOf(month);
			params.put("brandUnitNo", brandUnitNo);
			params.put("yearMonthStart", year+"-"+"01");
			params.put("yearMonthEnd", year+"-"+strMonth);
			List<PrintBalanceDto> lstItem = printMapper.selectYearBalanceGatherFooter(params);
			for (PrintBalanceDto dto : lstItem) {
				if("01".equals(dto.getCategoryNo())){
					PrintBalanceDto newDto = new PrintBalanceDto();
					newDto.setSendAmount(dto.getSendAmount());
					newDto.setReturnAmount(dto.getReturnAmount());
					newDto.setCustomReturnAmount(dto.getCustomReturnAmount());
					newDto.setBalanceAmount(dto.getBalanceAmount());
					newDto.setSendQty(dto.getSendQty());
					newDto.setReturnQty(dto.getReturnQty());
					newDto.setCustomReturnQty(dto.getCustomReturnQty());
					newDto.setBalanceQty(dto.getBalanceQty());
					newDto.setTitle(dto.getGroupName()+dto.getGenderName()+"鞋");
					newDto.setCost(newDto.getSendQty().intValue() == 0?new BigDecimal(0):newDto.getSendAmount().divide(new BigDecimal(newDto.getSendQty()), 2,BigDecimal.ROUND_UP));
					newYearFooterList.add(newDto);
				}else if(!"06".equals(dto.getCategoryNo())){
					String key = dto.getCategoryNo();
					if(null == notShoesMap.get(key)){
						notShoesMap.put(key, dto);
						dto.setTitle(dto.getCategoryName());
						newYearFooterList.add(dto);
					}else{
						PrintBalanceDto newDto  = (PrintBalanceDto) notShoesMap.get(key);
						newDto.setSendAmount(newDto.getSendAmount().add(dto.getSendAmount()));
						newDto.setReturnAmount(newDto.getReturnAmount().add(dto.getReturnAmount()));
						newDto.setCustomReturnAmount(newDto.getCustomReturnAmount().add(dto.getCustomReturnAmount()));
						newDto.setBalanceAmount(newDto.getBalanceAmount().add(dto.getBalanceAmount()));
						newDto.setSendQty(newDto.getSendQty()+dto.getSendQty());
						newDto.setReturnQty(newDto.getReturnQty()+dto.getReturnQty());
						newDto.setCustomReturnQty(newDto.getCustomReturnQty()+dto.getCustomReturnQty());
						newDto.setBalanceQty(newDto.getBalanceQty()+dto.getBalanceQty());
					}
				}
			}
			for (PrintBalanceDto dto : lstItem) {// 供应商分类统计
				if("01".equals(dto.getCategoryNo()) ){
					String key = dto.getGroupNo();
					if(null == shoesMap.get(key)){
						PrintBalanceDto newDto = new PrintBalanceDto();
						newDto.setSendAmount(dto.getSendAmount());
						newDto.setReturnAmount(dto.getReturnAmount());
						newDto.setCustomReturnAmount(dto.getCustomReturnAmount());
						newDto.setBalanceAmount(dto.getBalanceAmount());
						newDto.setSendQty(dto.getSendQty());
						newDto.setReturnQty(dto.getReturnQty());
						newDto.setCustomReturnQty(dto.getCustomReturnQty());
						newDto.setBalanceQty(dto.getBalanceQty());
						newDto.setTitle(dto.getGroupName()+"鞋合计");
						newDto.setCost(newDto.getSendQty().intValue() == 0?new BigDecimal(0):newDto.getSendAmount().divide(new BigDecimal(newDto.getSendQty()), 2,BigDecimal.ROUND_UP));
						shoesMap.put(key, newDto);
						newYearFooterList.add(newDto);
					}else{
						PrintBalanceDto newDto  = (PrintBalanceDto) shoesMap.get(key);
						newDto.setSendAmount(newDto.getSendAmount().add(dto.getSendAmount()));
						newDto.setReturnAmount(newDto.getReturnAmount().add(dto.getReturnAmount()));
						newDto.setCustomReturnAmount(newDto.getCustomReturnAmount().add(dto.getCustomReturnAmount()));
						newDto.setBalanceAmount(newDto.getBalanceAmount().add(dto.getBalanceAmount()));
						newDto.setSendQty(newDto.getSendQty()+dto.getSendQty());
						newDto.setReturnQty(newDto.getReturnQty()+dto.getReturnQty());
						newDto.setCustomReturnQty(newDto.getCustomReturnQty()+dto.getCustomReturnQty());
						newDto.setBalanceQty(newDto.getBalanceQty()+dto.getBalanceQty());
						newDto.setCost(newDto.getSendQty().intValue() == 0?new BigDecimal(0):newDto.getSendAmount().divide(new BigDecimal(newDto.getSendQty()), 2,BigDecimal.ROUND_UP));
					}
				}
			}
			PrintBalanceDto shoesFooterDto = new PrintBalanceDto();
			for (PrintBalanceDto dto : lstItem) {// 性别分类统计
				if("01".equals(dto.getCategoryNo())){
					String key = dto.getGender();
					if(null == shoesMap.get(key)){
						PrintBalanceDto newDto = new PrintBalanceDto();
						newDto.setSendAmount(dto.getSendAmount());
						newDto.setReturnAmount(dto.getReturnAmount());
						newDto.setCustomReturnAmount(dto.getCustomReturnAmount());
						newDto.setBalanceAmount(dto.getBalanceAmount());
						newDto.setSendQty(dto.getSendQty());
						newDto.setReturnQty(dto.getReturnQty());
						newDto.setCustomReturnQty(dto.getCustomReturnQty());
						newDto.setBalanceQty(dto.getBalanceQty());
						newDto.setCost(newDto.getSendQty().intValue() == 0?new BigDecimal(0):newDto.getSendAmount().divide(new BigDecimal(newDto.getSendQty()), 2,BigDecimal.ROUND_UP));
						newDto.setTitle(dto.getGenderName()+"鞋合计");
						shoesMap.put(key, newDto);
						newYearFooterList.add(newDto);
					}else{
						PrintBalanceDto newDto  = (PrintBalanceDto) shoesMap.get(key);
						newDto.setSendAmount(newDto.getSendAmount().add(dto.getSendAmount()));
						newDto.setReturnAmount(newDto.getReturnAmount().add(dto.getReturnAmount()));
						newDto.setCustomReturnAmount(newDto.getCustomReturnAmount().add(dto.getCustomReturnAmount()));
						newDto.setBalanceAmount(newDto.getBalanceAmount().add(dto.getBalanceAmount()));
						newDto.setSendQty(newDto.getSendQty()+dto.getSendQty());
						newDto.setReturnQty(newDto.getReturnQty()+dto.getReturnQty());
						newDto.setCustomReturnQty(newDto.getCustomReturnQty()+dto.getCustomReturnQty());
						newDto.setBalanceQty(newDto.getBalanceQty()+dto.getBalanceQty());
						newDto.setCost(newDto.getSendQty().intValue() == 0?new BigDecimal(0):newDto.getSendAmount().divide(new BigDecimal(newDto.getSendQty()), 2,BigDecimal.ROUND_UP));
					}
					shoesFooterDto.setSendAmount(shoesFooterDto.getSendAmount().add(dto.getSendAmount()));
					shoesFooterDto.setReturnAmount(shoesFooterDto.getReturnAmount().add(dto.getReturnAmount()));
					shoesFooterDto.setCustomReturnAmount(shoesFooterDto.getCustomReturnAmount().add(dto.getCustomReturnAmount()));
					shoesFooterDto.setBalanceAmount(shoesFooterDto.getBalanceAmount().add(dto.getBalanceAmount()));
					shoesFooterDto.setSendQty(shoesFooterDto.getSendQty()+dto.getSendQty());
					shoesFooterDto.setReturnQty(shoesFooterDto.getReturnQty()+dto.getReturnQty());
					shoesFooterDto.setCustomReturnQty(shoesFooterDto.getCustomReturnQty()+dto.getCustomReturnQty());
					shoesFooterDto.setBalanceQty(shoesFooterDto.getBalanceQty()+dto.getBalanceQty());
				}
			}
			shoesFooterDto.setCost(shoesFooterDto.getSendQty().intValue() == 0?new BigDecimal(0):shoesFooterDto.getSendAmount().divide(new BigDecimal(shoesFooterDto.getSendQty()), 2,BigDecimal.ROUND_UP));
			shoesFooterDto.setTitle("鞋合计");
			newYearFooterList.add(shoesFooterDto);
			// 工作鞋
			String lstBalanceNo =  printMapper.selectWorkShoesBalanceNo(params);
			if(StringUtils.isNotBlank(lstBalanceNo)){
				params.put("balanceNo", lstBalanceNo);
				List<PrintBalanceDto> lstYearWorkShoes = printMapper.selectYearBalanceGatherFooter(params);
				Map<String, Object> workShoesMap = new HashMap<>();
				for (PrintBalanceDto workShoes : lstYearWorkShoes) {
					String groupKey = workShoes.getGroupName();
					if(null == workShoesMap.get(groupKey)){
						PrintBalanceDto newDto = new PrintBalanceDto();
						newDto.setSendAmount(workShoes.getSendAmount());
						newDto.setReturnAmount(workShoes.getReturnAmount());
						newDto.setCustomReturnAmount(workShoes.getCustomReturnAmount());
						newDto.setBalanceAmount(workShoes.getBalanceAmount());
						newDto.setSendQty(workShoes.getSendQty());
						newDto.setReturnQty(workShoes.getReturnQty());
						newDto.setCustomReturnQty(workShoes.getCustomReturnQty());
						newDto.setBalanceQty(workShoes.getBalanceQty());
						newDto.setCost(newDto.getSendQty().intValue() == 0?new BigDecimal(0):newDto.getSendAmount().divide(new BigDecimal(newDto.getSendQty()), 2,BigDecimal.ROUND_UP));
						newDto.setTitle(groupKey+"工鞋(物料)");
						workShoesMap.put(groupKey, newDto);
						newYearFooterList.add(newDto);
					}else{
						PrintBalanceDto oldDto = (PrintBalanceDto) workShoesMap.get(groupKey);
						oldDto.setSendAmount(oldDto.getSendAmount().add(workShoes.getSendAmount()));
						oldDto.setReturnAmount(oldDto.getReturnAmount().add(workShoes.getReturnAmount()));
						oldDto.setCustomReturnAmount(oldDto.getCustomReturnAmount().add(workShoes.getCustomReturnAmount()));
						oldDto.setBalanceAmount(oldDto.getBalanceAmount().add(workShoes.getBalanceAmount()));
						oldDto.setSendQty(oldDto.getSendQty()+workShoes.getSendQty());
						oldDto.setReturnQty(oldDto.getReturnQty()+workShoes.getReturnQty());
						oldDto.setCustomReturnQty(oldDto.getCustomReturnQty()+workShoes.getCustomReturnQty());
						oldDto.setBalanceQty(oldDto.getBalanceQty()+workShoes.getBalanceQty());
						oldDto.setCost(oldDto.getSendQty().intValue() == 0?new BigDecimal(0):oldDto.getSendAmount().divide(new BigDecimal(oldDto.getSendQty()), 2,BigDecimal.ROUND_UP));
					}
				}
				
			}
			
			yearFooterMap.put("title", year +"年度截至当月累计");
			yearFooterMap.put("rowList", newYearFooterList);
		return yearFooterMap;
	}
	
	private Map<String, Object> setColumnMapList(String balanceNo,Map<String, Object> resultMap) {
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
		List<PrintBalanceDto> orderUnitList = printMapper.selectSendColumnByBalanceNo(balanceNo);
		Map<String, Object> indexMap = new HashMap<String, Object>();
		indexMap.put("field", "ck");
		indexMap.put("title", "序号");
		indexMap.put("width", 30);
		columnList.add(indexMap);
		Map<String, Object> codeMap = new HashMap<String, Object>();
		codeMap.put("field", "itemCode");
		codeMap.put("title", "货号");
		codeMap.put("width", 80);
		columnList.add(codeMap);
		Map<String, Object> nameMap = new HashMap<String, Object>();
		nameMap.put("field", "itemName");
		nameMap.put("title", "中文名称");
		nameMap.put("width", 100);
		columnList.add(nameMap);
		for (PrintBalanceDto balance : orderUnitList) {
			Map<String, Object> orderUnitMap = new HashMap<String, Object>();
			orderUnitMap.put("field", balance.getOrderUnitNo());
			orderUnitMap.put("title", balance.getOrderUnitName());
			orderUnitMap.put("width", 40);
			columnList.add(orderUnitMap);
		}
		Map<String, Object> sendQtyMap = new HashMap<String, Object>();
		sendQtyMap.put("field", "sendQty");
		sendQtyMap.put("title", "合计");
		sendQtyMap.put("width", 40);
		columnList.add(sendQtyMap);
		Map<String, Object> purchasePriceMap = new HashMap<String, Object>();
		purchasePriceMap.put("field", "purchasePrice");
		purchasePriceMap.put("title", "采购价");
		purchasePriceMap.put("width", 40);
		columnList.add(purchasePriceMap);
		Map<String, Object> materialPriceMap = new HashMap<String, Object>();
		materialPriceMap.put("field", "materialPrice");
		materialPriceMap.put("title", "物料价");
		materialPriceMap.put("width", 40);
		columnList.add(materialPriceMap);
		Map<String, Object> sendAmountMap = new HashMap<String, Object>();
		sendAmountMap.put("field", "sendAmount");
		sendAmountMap.put("title", "出货金额");
		sendAmountMap.put("width", 50);
		columnList.add(sendAmountMap);
		// 残鞋
		Map<String, Object> codeMap1 = new HashMap<String, Object>();
		codeMap1.put("field", "itemCode1");
		codeMap1.put("title", "货号");
		codeMap1.put("width", 80);
		columnList.add(codeMap1);
		Map<String, Object> sendQtyMap1 = new HashMap<String, Object>();
		sendQtyMap1.put("field", "sendQty1");
		sendQtyMap1.put("title", "数量");
		sendQtyMap1.put("width", 40);
		columnList.add(sendQtyMap1);
		Map<String, Object> purchasePriceMap1 = new HashMap<String, Object>();
		purchasePriceMap1.put("field", "purchasePrice1");
		purchasePriceMap1.put("title", "采购价");
		purchasePriceMap1.put("width", 40);
		columnList.add(purchasePriceMap1);
		Map<String, Object> sendAmountMap1 = new HashMap<String, Object>();
		sendAmountMap1.put("field", "sendAmount1");
		sendAmountMap1.put("title", "金额");
		sendAmountMap1.put("width", 50);
		columnList.add(sendAmountMap1);
		// 退货
		Map<String, Object> codeMap2 = new HashMap<String, Object>();
		codeMap2.put("field", "itemCode2");
		codeMap2.put("title", "货号");
		codeMap2.put("width", 80);
		columnList.add(codeMap2);
		//modify
		Map<String, Object> itemCodeNameMap = new HashMap<String, Object>();
		itemCodeNameMap.put("field", "itemCodeName");
		itemCodeNameMap.put("title", "货号名称");
		itemCodeNameMap.put("width", 80);
		columnList.add(itemCodeNameMap);
		
		Map<String, Object> organNameMap = new HashMap<String, Object>();
		organNameMap.put("field", "organName");
		organNameMap.put("title", "退货城市");
		organNameMap.put("width", 40);
		columnList.add(organNameMap);
		
		Map<String, Object> sendQtyMap2 = new HashMap<String, Object>();
		sendQtyMap2.put("field", "sendQty2");
		sendQtyMap2.put("title", "数量");
		sendQtyMap2.put("width", 40);
		columnList.add(sendQtyMap2);
		Map<String, Object> purchasePriceMap2 = new HashMap<String, Object>();
		purchasePriceMap2.put("field", "purchasePrice2");
		purchasePriceMap2.put("title", "采购价");
		purchasePriceMap2.put("width", 40);
		columnList.add(purchasePriceMap2);
		
		//modify
		Map<String, Object> materialPrice1 = new HashMap<String, Object>();
		materialPrice1.put("field", "materialPrice1");
		materialPrice1.put("title", "物料价");
		materialPrice1.put("width", 40);
		columnList.add(materialPrice1);
		
		Map<String, Object> sendAmountMap2 = new HashMap<String, Object>();
		sendAmountMap2.put("field", "sendAmount2");
		sendAmountMap2.put("title", "金额");
		sendAmountMap2.put("width", 50);
		columnList.add(sendAmountMap2);
		resultMap.put("columnList", columnList);
		return resultMap;
	}
	
	private Map<String, Object> setRowMapList(String balanceNo,Map<String, Object> resultMap) {
		List<Map<String, Object>> rowMapList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> footerMapList = new ArrayList<Map<String, Object>>();
		List<PrintBalanceDto> rowSendList = printMapper.selectSendRowByBalanceNo(balanceNo);
		List<PrintBalanceDto> qtySendList = printMapper.selectSendQtyByBalanceNo(balanceNo);
		List<PrintBalanceDto> customReturnList = printMapper.selectCustomReturnQtyByBalanceNo(balanceNo);
		List<PrintBalanceDto> returnList = printMapper.selectReturnQtyByBalanceNo(balanceNo);
		int[] size = {rowSendList.size(),customReturnList.size(),returnList.size()};
		Arrays.sort(size);
		int maxSize = size[size.length-1];
		int allFooterQty = 0 ;
		BigDecimal allFooterAmount = new BigDecimal(0);
		int allFooterQty1 = 0 ;
		BigDecimal allFooterAmount1 = new BigDecimal(0);
		int allFooterQty2 = 0 ;
		BigDecimal allFooterAmount2 = new BigDecimal(0);
		Map<String, Object> footerMap = new HashMap<String, Object>();
		for(int i =0;i<maxSize;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			if(rowSendList.size() > i){
				Integer allQty = new Integer(0);
				BigDecimal allAmount = new BigDecimal(0);
				BigDecimal purchasePrice = new BigDecimal(0);
				BigDecimal materialPrice = new BigDecimal(0);
				PrintBalanceDto balance = rowSendList.get(i);
				map.put("itemCode", balance.getItemCode());
				map.put("itemName", balance.getItemName());
				for (PrintBalanceDto balanceQty : qtySendList) {
					if(i==0){
						String footerOrderUnitNo = balanceQty.getOrderUnitNo();
						Integer footerSendQty = balanceQty.getSendQty();
						allFooterQty += balanceQty.getSendQty();
						allFooterAmount = allFooterAmount.add(balanceQty.getSendAmount());
						if(null == footerMap.get(footerOrderUnitNo)){
							footerMap.put(footerOrderUnitNo, footerSendQty);
						}else{
							Integer footerQty = (Integer) footerMap.get(balanceQty.getOrderUnitNo());
							footerMap.put(footerOrderUnitNo, footerSendQty + footerQty);
						}
					}
					if(balanceQty.getItemNo().equals(balance.getItemNo())){
						String orderUnitNo = balanceQty.getOrderUnitNo();
						Integer sendQty = balanceQty.getSendQty();
						BigDecimal sendAmount = balanceQty.getSendAmount();
						if(null == map.get(orderUnitNo)){
							purchasePrice = balanceQty.getPurchasePrice();
							materialPrice = balanceQty.getMaterialPrice();
							map.put(orderUnitNo, sendQty);
						}else{
							Integer orderUnitQty = (Integer) map.get(orderUnitNo);
							map.put(orderUnitNo, orderUnitQty + sendQty);
						}
						allQty += sendQty;
						allAmount = allAmount.add(sendAmount); 
					}
				}
				map.put("sendQty", allQty);
				map.put("purchasePrice", purchasePrice);
				map.put("materialPrice", materialPrice);
				map.put("sendAmount", allAmount);
			}
			if(customReturnList.size() > i){
				PrintBalanceDto balance = customReturnList.get(i);
				allFooterQty1 += balance.getSendQty();
				allFooterAmount1 = allFooterAmount1.add(balance.getSendAmount());
				map.put("itemCode1", balance.getItemCode());
				map.put("sendQty1", balance.getSendQty());
				map.put("purchasePrice1", balance.getPurchasePrice());
				map.put("sendAmount1", balance.getSendAmount());
			}
			if(returnList.size() > i){
				PrintBalanceDto balance = returnList.get(i);
				allFooterQty2 += balance.getSendQty();
				allFooterAmount2 = allFooterAmount2.add(balance.getSendAmount());
				map.put("itemCode2", balance.getItemCode());
				map.put("itemCodeName", balance.getItemName());
				map.put("organName",balance.getOrganName());
				map.put("sendQty2", balance.getSendQty());
				map.put("purchasePrice2", balance.getPurchasePrice());
				map.put("materialPrice1", balance.getMaterialPrice());
				map.put("sendAmount2", balance.getSendAmount());
			}
			rowMapList.add(map);
		}
		footerMap.put("sendQty", allFooterQty);
		footerMap.put("sendAmount", allFooterAmount);
		footerMap.put("sendQty1", allFooterQty1);
		footerMap.put("sendAmount1", allFooterAmount1);
		footerMap.put("sendQty2", allFooterQty2);
		footerMap.put("sendAmount2", allFooterAmount2);
		BigDecimal deductionAmount = printMapper.selectDeductionAmountByBalanceNo(balanceNo);
		footerMap.put("deductionAmount", deductionAmount);
		footerMapList.add(footerMap);
		Map<String, Object> lastFooterMap = new HashMap<String, Object>();
		BigDecimal balanceAmount = allFooterAmount.subtract(allFooterAmount1).subtract(deductionAmount).add(allFooterAmount2);
		lastFooterMap.put("balanceAmount", balanceAmount);
		footerMapList.add(lastFooterMap);
		resultMap.put("rowList", rowMapList);
		resultMap.put("footerList", footerMapList);
		return resultMap;
	}

	private List<Map<String, Object>> getYearColumnList(){
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> genderMap = new HashMap<String, Object>();
		genderMap.put("field", "title");
		genderMap.put("title", "类别");
		genderMap.put("width", 65);
		columnList.add(genderMap);
		Map<String, Object> sendQtyMap = new HashMap<String, Object>();
		sendQtyMap.put("field", "sendQty");
		sendQtyMap.put("title", "累计进货数量");
		sendQtyMap.put("width", 80);
		columnList.add(sendQtyMap);
		Map<String, Object> sendAmountMap = new HashMap<String, Object>();
		sendAmountMap.put("field", "sendAmount");
		sendAmountMap.put("title", "累计进货金额");
		sendAmountMap.put("width", 80);
		columnList.add(sendAmountMap);
		Map<String, Object> costMap = new HashMap<String, Object>();
		costMap.put("field", "cost");
		costMap.put("title", "累计平均单价");
		costMap.put("width", 150);
		columnList.add(costMap);
		Map<String, Object> customReturnQtyMap = new HashMap<String, Object>();
		customReturnQtyMap.put("field", "customReturnQty");
		customReturnQtyMap.put("title", "累计客残数量");
		customReturnQtyMap.put("width", 70);
		columnList.add(customReturnQtyMap);
		Map<String, Object> customReturnAmountMap = new HashMap<String, Object>();
		customReturnAmountMap.put("field", "customReturnAmount");
		customReturnAmountMap.put("title", "累计客残金额");
		customReturnAmountMap.put("width", 70);
		columnList.add(customReturnAmountMap);
		Map<String, Object> balanceQtyMap = new HashMap<String, Object>();
		balanceQtyMap.put("field", "balanceQty");
		balanceQtyMap.put("title", "累计应付数量");
		balanceQtyMap.put("width", 80);
		columnList.add(balanceQtyMap);
		Map<String, Object> balanceAmountMap = new HashMap<String, Object>();
		balanceAmountMap.put("field", "balanceAmount");
		balanceAmountMap.put("title", "累计应付金额");
		balanceAmountMap.put("width", 80);
		columnList.add(balanceAmountMap);
		return columnList;
	}
	
	private List<Map<String, Object>> getColumnList(){
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> genderMap = new HashMap<String, Object>();
		genderMap.put("field", "genderName");
		genderMap.put("title", "类别");
		genderMap.put("width", 50);
		columnList.add(genderMap);
		Map<String, Object> indexMap = new HashMap<String, Object>();
		indexMap.put("field", "ck");
		indexMap.put("title", "序号");
		indexMap.put("width", 30);
		columnList.add(indexMap);
		Map<String, Object> codeMap = new HashMap<String, Object>();
		codeMap.put("field", "salerNo");
		codeMap.put("title", "厂家代号");
		codeMap.put("width", 70);
		columnList.add(codeMap);
		Map<String, Object> nameMap = new HashMap<String, Object>();
		nameMap.put("field", "salerName");
		nameMap.put("title", "厂家名称");
		nameMap.put("width", 150);
		columnList.add(nameMap);
		Map<String, Object> sendQtyMap = new HashMap<String, Object>();
		sendQtyMap.put("field", "sendQty");
		sendQtyMap.put("title", "进货数量");
		sendQtyMap.put("width", 50);
		columnList.add(sendQtyMap);
		Map<String, Object> sendAmountMap = new HashMap<String, Object>();
		sendAmountMap.put("field", "sendAmount");
		sendAmountMap.put("title", "进货金额");
		sendAmountMap.put("width", 70);
		columnList.add(sendAmountMap);
		Map<String, Object> costMap = new HashMap<String, Object>();
		costMap.put("field", "cost");
		costMap.put("title", "平均单价");
		costMap.put("width", 50);
		columnList.add(costMap);
		Map<String, Object> customReturnQtyMap = new HashMap<String, Object>();
		customReturnQtyMap.put("field", "customReturnQty");
		customReturnQtyMap.put("title", "客残数量");
		customReturnQtyMap.put("width", 50);
		columnList.add(customReturnQtyMap);
		Map<String, Object> customReturnAmountMap = new HashMap<String, Object>();
		customReturnAmountMap.put("field", "customReturnAmount");
		customReturnAmountMap.put("title", "客残金额");
		customReturnAmountMap.put("width", 60);
		columnList.add(customReturnAmountMap);
		Map<String, Object> deductionAmountMap = new HashMap<String, Object>();
		deductionAmountMap.put("field", "deductionAmount");
		deductionAmountMap.put("title", "其他扣项");
		deductionAmountMap.put("width", 60);
		columnList.add(deductionAmountMap);
		Map<String, Object> balanceQtyMap = new HashMap<String, Object>();
		balanceQtyMap.put("field", "balanceQty");
		balanceQtyMap.put("title", "应付数量");
		balanceQtyMap.put("width", 80);
		columnList.add(balanceQtyMap);
		Map<String, Object> balanceAmountMap = new HashMap<String, Object>();
		balanceAmountMap.put("field", "balanceAmount");
		balanceAmountMap.put("title", "应付金额");
		balanceAmountMap.put("width", 80);
		columnList.add(balanceAmountMap);
		return columnList;
	}

	@Override
	public Map<String, Map<String, Object>> getBalanceDiffData(String companyNo,
			String brandUnitNo, String year, String month) throws ServiceException {
			String hqCompanyNo = financialAccountMapper.selectLeadRoleCompanyNos(Authorization.getUser().getOrganTypeNo());
			Map<String, Object> params = new HashMap<>();
			params.put("hqCompanyNo", "(".concat(hqCompanyNo).concat(")"));
			params.put("companyNo", companyNo);
			params.put("brandUnitNo", brandUnitNo);
			params.put("year", year);
			params.put("month", month);
			params.put("startDate", year.concat("-").concat(month).concat("-1"));
			params.put("endDate", DateUtil.getLastDayOfMonthStr(Integer.parseInt(year), Integer.parseInt(month)));
			params.put("isPE", String.valueOf(ShardingUtil.isPE()));
			List<PrintBalanceDiffDto> periodInList = printMapper.selectPeriodInList(params);
			List<PrintBalanceDiffDto> periodOutList = printMapper.selectPeriodOutList(params);
			List<PrintBalanceDiffDto> periodBalanceList = printMapper.selectPeriodBalanceList(params);
			List<PrintBalanceDiffDto> oweOffList = printMapper.selectOweOffListByBill(params);
			params.put("month", Integer.parseInt(month)-1);
			List<PrintBalanceDiffDto> beginningOweOffList = printMapper.selectOweOffList(params);
			params.put("month", month);
			List<PrintBalanceDiffDto> endOweOffList = printMapper.selectOweOffList(params);
			Map<String, Map<String, Object>> resultMap = new HashMap<>();
			this.setPeriodInData(resultMap,periodInList,year,month);
			this.setPeriodOutData(resultMap,periodOutList,year,month);
			this.setPeriodBalanceData(resultMap,periodBalanceList,year,month);
			this.setOweOffData(resultMap,oweOffList,year,month);
			this.setBeginningOweOffData(resultMap,beginningOweOffList,year,month);
			this.setEndOweOffData(resultMap,endOweOffList,year,month);
		return resultMap;
	}

 	@SuppressWarnings("unchecked")
	private void setEndOweOffData(Map<String, Map<String, Object>> resultMap,
			List<PrintBalanceDiffDto> beginningOweOffList,String year,String month) {
		for (PrintBalanceDiffDto oweOffDto : beginningOweOffList) {
			String key = oweOffDto.getCompanyNo().concat("_").concat(oweOffDto.getBrandUnitNo());
			Map<String, Object> brandUnitMap = resultMap.get(key);
			if(brandUnitMap == null){
				brandUnitMap = this.initBrandUnitMap(oweOffDto,"oweOff",year,month);
				resultMap.put(key, brandUnitMap);
			}else{
				List<PrintBalanceEntry> lstOweOffItem = (List<PrintBalanceEntry>) brandUnitMap.get("oweOff");
				PrintBalanceEntry oweOffDiffEntry = lstOweOffItem.get(lstOweOffItem.size()-1); 
				for (PrintBalanceEntry entry : lstOweOffItem) {
					if(entry.getTitle().equals("期末欠客")){
						entry.setQty(oweOffDto.getThisOweOffQty());
						entry.setCost(oweOffDto.getThisOweOffCost());
						oweOffDiffEntry.setQty(oweOffDiffEntry.getQty() - oweOffDto.getThisOweOffQty());
						oweOffDiffEntry.setCost(oweOffDiffEntry.getCost().subtract(oweOffDto.getThisOweOffCost()));
					}
				}
			}
		}
	}

 	@SuppressWarnings("unchecked")
	private void setBeginningOweOffData(
			Map<String, Map<String, Object>> resultMap,
			List<PrintBalanceDiffDto> beginningOweOffList,String year,String month) {
		for (PrintBalanceDiffDto oweOffDto : beginningOweOffList) {
			String key = oweOffDto.getCompanyNo().concat("_").concat(oweOffDto.getBrandUnitNo());
			Map<String, Object> brandUnitMap = resultMap.get(key);
			if(brandUnitMap == null){
				brandUnitMap = this.initBrandUnitMap(oweOffDto,"oweOff",year,month);
				resultMap.put(key, brandUnitMap);
			}else{
				List<PrintBalanceEntry> lstOweOffItem = (List<PrintBalanceEntry>) brandUnitMap.get("oweOff");
				PrintBalanceEntry oweOffDiffEntry = lstOweOffItem.get(lstOweOffItem.size()-1); 
				for (PrintBalanceEntry entry : lstOweOffItem) {
					if(entry.getTitle().equals("期初欠客")){
						entry.setQty(oweOffDto.getThisOweOffQty());
						entry.setCost(oweOffDto.getThisOweOffCost());
						oweOffDiffEntry.setQty(oweOffDiffEntry.getQty()+ oweOffDto.getThisOweOffQty());
						oweOffDiffEntry.setCost(oweOffDiffEntry.getCost().add(oweOffDto.getThisOweOffCost()));
					}
				}
			}
		}
	}

 	@SuppressWarnings("unchecked")
	private void setOweOffData(Map<String, Map<String, Object>> resultMap,
			List<PrintBalanceDiffDto> oweOffList,String year,String month) {
		for (PrintBalanceDiffDto oweOffDto : oweOffList) {
			String key = oweOffDto.getCompanyNo().concat("_").concat(oweOffDto.getBrandUnitNo());
			Map<String, Object> brandUnitMap = resultMap.get(key);
			if(brandUnitMap == null){
				brandUnitMap = this.initBrandUnitMap(oweOffDto,"oweOff",year,month);
				resultMap.put(key, brandUnitMap);
			}else{
				List<PrintBalanceEntry> lstSalesItem = (List<PrintBalanceEntry>) brandUnitMap.get("sales");
				PrintBalanceEntry salesDiffEntry = lstSalesItem.get(lstSalesItem.size()-1); 
				for (PrintBalanceEntry entry : lstSalesItem) {
					if(entry.getTitle().equals("前期欠客本期发出")){
						entry.setQty(oweOffDto.getPreOweOffOutQty());
						entry.setCost(oweOffDto.getPreOweOffOutCost());
						salesDiffEntry.setQty(salesDiffEntry.getQty()- oweOffDto.getPreOweOffOutQty());
						salesDiffEntry.setCost(salesDiffEntry.getCost().subtract(oweOffDto.getPreOweOffOutCost()));
					}else if(entry.getTitle().equals("本期欠客")){
						entry.setQty(oweOffDto.getThisOweOffQty());
						entry.setCost(oweOffDto.getThisOweOffCost());
						salesDiffEntry.setQty(salesDiffEntry.getQty()+ oweOffDto.getThisOweOffQty());
						salesDiffEntry.setCost(salesDiffEntry.getCost().add(oweOffDto.getThisOweOffCost()));
					}
				}
				List<PrintBalanceEntry> lstOweOffItem = (List<PrintBalanceEntry>) brandUnitMap.get("oweOff");
				PrintBalanceEntry oweOffDiffEntry = lstOweOffItem.get(lstOweOffItem.size()-1); 
				for (PrintBalanceEntry entry : lstOweOffItem) {
					if(entry.getTitle().equals("前期欠客本期发出")){
						entry.setQty(oweOffDto.getPreOweOffOutQty());
						entry.setCost(oweOffDto.getPreOweOffOutCost());
						oweOffDiffEntry.setQty(oweOffDiffEntry.getQty()- oweOffDto.getPreOweOffOutQty());
						oweOffDiffEntry.setCost(oweOffDiffEntry.getCost().subtract(oweOffDto.getPreOweOffOutCost()));
					}else if(entry.getTitle().equals("本期欠客")){
						entry.setQty(oweOffDto.getThisOweOffQty());
						entry.setCost(oweOffDto.getThisOweOffCost());
						oweOffDiffEntry.setQty(oweOffDiffEntry.getQty()+ oweOffDto.getThisOweOffQty());
						oweOffDiffEntry.setCost(oweOffDiffEntry.getCost().add(oweOffDto.getThisOweOffCost()));
					}
				}
			}
		}
	}

 	@SuppressWarnings("unchecked")
	private void setPeriodBalanceData(
			Map<String, Map<String, Object>> resultMap,
			List<PrintBalanceDiffDto> periodBalanceList,String year,String month) {
		for (PrintBalanceDiffDto periodBalanceDto : periodBalanceList) {
			String key = periodBalanceDto.getCompanyNo().concat("_").concat(periodBalanceDto.getBrandUnitNo());
			Map<String, Object> brandUnitMap = resultMap.get(key);
			if(brandUnitMap == null){
				brandUnitMap = this.initBrandUnitMap(periodBalanceDto,"periodBalance",year,month);
				resultMap.put(key, brandUnitMap);
			}else{
				List<PrintBalanceEntry> lstPurchaseItem = (List<PrintBalanceEntry>) brandUnitMap.get("purchase");
				PrintBalanceEntry purchaseDiffEntry = lstPurchaseItem.get(lstPurchaseItem.size()-1); 
				for (PrintBalanceEntry entry : lstPurchaseItem) {
					if(entry.getTitle().equals("采购入库")){
						entry.setQty(periodBalanceDto.getPurchaseInQty());
						entry.setAmount(periodBalanceDto.getPurchaseInAmount());
						purchaseDiffEntry.setQty(periodBalanceDto.getPurchaseInQty()+purchaseDiffEntry.getQty());
						purchaseDiffEntry.setAmount(periodBalanceDto.getPurchaseInAmount().add(purchaseDiffEntry.getAmount()));
					}else if(entry.getTitle().equals("采购退回")){
						entry.setQty(periodBalanceDto.getPurchaseReturnQty());
						entry.setAmount(periodBalanceDto.getPurchaseReturnAmount());
						purchaseDiffEntry.setQty(periodBalanceDto.getPurchaseReturnQty()+purchaseDiffEntry.getQty());
						purchaseDiffEntry.setAmount(periodBalanceDto.getPurchaseReturnAmount().add(purchaseDiffEntry.getAmount()));
					}else if(entry.getTitle().equals("外区调入")){
						entry.setQty(periodBalanceDto.getOuterTransferInQty());
						entry.setAmount(periodBalanceDto.getOuterTransferInAmount());
						purchaseDiffEntry.setQty(periodBalanceDto.getOuterTransferInQty()+purchaseDiffEntry.getQty());
						purchaseDiffEntry.setAmount(periodBalanceDto.getOuterTransferInAmount().add(purchaseDiffEntry.getAmount()));
					}
				}
				List<PrintBalanceEntry> lstSalesItem = (List<PrintBalanceEntry>) brandUnitMap.get("sales");
				PrintBalanceEntry salesDiffEntry = lstSalesItem.get(lstSalesItem.size()-1); 
				for (PrintBalanceEntry entry : lstSalesItem) {
					if(entry.getTitle().equals("销售出库")){
						entry.setQty(periodBalanceDto.getSalesOutQty());
						entry.setCost(periodBalanceDto.getSalesOutCost());
						salesDiffEntry.setQty(salesDiffEntry.getQty()+ periodBalanceDto.getSalesOutQty());
						salesDiffEntry.setCost(salesDiffEntry.getCost().add(periodBalanceDto.getSalesOutCost()));
					}else if(entry.getTitle().equals("其他出库(期间结存)")){
						entry.setQty(periodBalanceDto.getOuterOtherOutQty());
						entry.setCost(periodBalanceDto.getOuterOtherOutCost());
						salesDiffEntry.setQty(salesDiffEntry.getQty()+ periodBalanceDto.getOuterOtherOutQty());
						salesDiffEntry.setCost(salesDiffEntry.getCost().add(periodBalanceDto.getOuterOtherOutCost()));
					}else if(entry.getTitle().equals("外区调出")){
						entry.setQty(periodBalanceDto.getOuterTransferOutQty());
						entry.setCost(periodBalanceDto.getOuterTransferOutCost());
						salesDiffEntry.setQty(salesDiffEntry.getQty()+ periodBalanceDto.getOuterTransferOutQty());
						salesDiffEntry.setCost(salesDiffEntry.getCost().add(periodBalanceDto.getOuterTransferOutCost()));
					}
				}
				List<PrintBalanceEntry> lstTransferItem = (List<PrintBalanceEntry>) brandUnitMap.get("transfer");
				PrintBalanceEntry transferInDiffEntry = lstTransferItem.get(lstTransferItem.size()-2); 
				PrintBalanceEntry transferOutDiffEntry = lstTransferItem.get(lstTransferItem.size()-1); 
				for (PrintBalanceEntry entry : lstTransferItem) {
					if(entry.getTitle().equals("外区调出")){
						entry.setQty(periodBalanceDto.getOuterTransferOutQty());
						entry.setCost(periodBalanceDto.getOuterTransferOutCost());
						transferOutDiffEntry.setQty(transferOutDiffEntry.getQty()+ periodBalanceDto.getOuterTransferOutQty());
						transferOutDiffEntry.setCost(transferOutDiffEntry.getCost().add(periodBalanceDto.getOuterTransferOutCost()));
					}else if(entry.getTitle().equals("外区调入")){
						entry.setQty(periodBalanceDto.getOuterTransferInQty());
						entry.setAmount(periodBalanceDto.getOuterTransferInAmount());
						transferInDiffEntry.setQty(transferInDiffEntry.getQty()+ periodBalanceDto.getOuterTransferInQty());
						transferInDiffEntry.setAmount(transferInDiffEntry.getAmount().add(periodBalanceDto.getOuterTransferInAmount()));
					}
				}
			}
		}
		
	}

 	@SuppressWarnings("unchecked")
	private void setPeriodOutData(Map<String, Map<String, Object>> resultMap,
			List<PrintBalanceDiffDto> periodOutList,String year,String month) {
		for (PrintBalanceDiffDto periodOutDto : periodOutList) {
			String key = periodOutDto.getCompanyNo().concat("_").concat(periodOutDto.getBrandUnitNo());
			Map<String, Object> brandUnitMap = resultMap.get(key);
			if(brandUnitMap == null){
				brandUnitMap = this.initBrandUnitMap(periodOutDto,"periodOut",year,month);
				resultMap.put(key, brandUnitMap);
			}else{
				List<PrintBalanceEntry> lstSalesItem = (List<PrintBalanceEntry>) brandUnitMap.get("sales");
				PrintBalanceEntry salesDiffEntry = lstSalesItem.get(lstSalesItem.size()-1); 
				Integer diffQty = salesDiffEntry.getQty();
				BigDecimal diffCost = salesDiffEntry.getCost();
				for (PrintBalanceEntry entry : lstSalesItem) {
					if(entry.getTitle().equals("门店销售")){
						entry.setQty(periodOutDto.getOuterPosOutQty());
						entry.setAmount(periodOutDto.getOuterPosOutAmount());
						entry.setCost(periodOutDto.getOuterPosOutCost());
						diffQty -= periodOutDto.getOuterPosOutQty();
						diffCost = diffCost.subtract(periodOutDto.getOuterPosOutCost());
					}else if(entry.getTitle().equals("批发销售")){
						entry.setQty(periodOutDto.getOuterWholesaleOutQty());
						entry.setAmount(periodOutDto.getOuterWholesaleOutAmount());
						entry.setCost(periodOutDto.getOuterWholesaleOutCost());
						diffQty -= periodOutDto.getOuterWholesaleOutQty();
						diffCost = diffCost.subtract(periodOutDto.getOuterWholesaleOutCost());
					}else if(entry.getTitle().equals("内购销售")){
						entry.setQty(periodOutDto.getOuterWithinOutQty());
						entry.setAmount(periodOutDto.getOuterWithinOutAmount());
						entry.setCost(periodOutDto.getOuterWithinOutCost());
						diffQty -= periodOutDto.getOuterWithinOutQty();
						diffCost = diffCost.subtract(periodOutDto.getOuterWithinOutCost());
					}else if(entry.getTitle().equals("跨区调出")){
						entry.setQty(periodOutDto.getOuterTransferOutQty());
						entry.setAmount(periodOutDto.getOuterTransferOutAmount());
						entry.setCost(periodOutDto.getOuterTransferOutCost());
						diffQty -= periodOutDto.getOuterTransferOutQty();
						diffCost = diffCost.subtract(periodOutDto.getOuterTransferOutCost());
					}else if(entry.getTitle().equals("其他出库(销售)")){
						entry.setQty(periodOutDto.getOuterOtherOutQty());
						entry.setAmount(periodOutDto.getOuterOtherOutAmount());
						entry.setCost(periodOutDto.getOuterOtherOutCost());
						diffQty -= periodOutDto.getOuterOtherOutQty();
						diffCost = diffCost.subtract(periodOutDto.getOuterOtherOutCost());

					}
				}
				salesDiffEntry.setQty(diffQty); 
				salesDiffEntry.setCost(diffCost); 
				List<PrintBalanceEntry> lstTransferItem = (List<PrintBalanceEntry>) brandUnitMap.get("transfer");
				PrintBalanceEntry transferDiffEntry = lstTransferItem.get(lstTransferItem.size()-1); 
				for (PrintBalanceEntry entry : lstTransferItem) {
					if(entry.getTitle().equals("跨区调出")){
						entry.setQty(periodOutDto.getOuterTransferOutQty());
						entry.setAmount(periodOutDto.getOuterTransferOutAmount());
						entry.setCost(periodOutDto.getOuterTransferOutCost());
						transferDiffEntry.setQty(transferDiffEntry.getQty() - periodOutDto.getOuterTransferOutQty());
						transferDiffEntry.setCost(transferDiffEntry.getCost().subtract(periodOutDto.getOuterTransferOutCost()));
					}
				}
			}
		}
		
	}

 	@SuppressWarnings("unchecked")
	private void setPeriodInData(Map<String, Map<String, Object>> resultMap,
			List<PrintBalanceDiffDto> periodInList,String year,String month) {
		for (PrintBalanceDiffDto periodInDto : periodInList) {
			String key = periodInDto.getCompanyNo().concat("_").concat(periodInDto.getBrandUnitNo());
			Map<String, Object> brandUnitMap = resultMap.get(key);
			if(brandUnitMap == null){
				brandUnitMap = this.initBrandUnitMap(periodInDto,"periodIn",year,month);
				resultMap.put(key, brandUnitMap);
			}else{
				List<PrintBalanceEntry> lstPurchaseItem = (List<PrintBalanceEntry>) brandUnitMap.get("purchase");
				PrintBalanceEntry purchaseDiffEntry = lstPurchaseItem.get(lstPurchaseItem.size()-1); 
				Integer diffQty = purchaseDiffEntry.getQty();
				BigDecimal diffAmount = purchaseDiffEntry.getAmount();
				for (PrintBalanceEntry entry : lstPurchaseItem) {
					if(entry.getTitle().equals("地区采购")){
						entry.setQty(periodInDto.getAreaPurchaseInQty());
						entry.setAmount(periodInDto.getAreaPurchaseInAmount());
						diffQty -= periodInDto.getAreaPurchaseInQty();
						diffAmount = diffAmount.subtract(periodInDto.getAreaPurchaseInAmount());
					}else if(entry.getTitle().equals("总部收购") || entry.getTitle().equals("总部代采")){
						entry.setQty(periodInDto.getHqPurchaseInQty());
						entry.setAmount(periodInDto.getHqPurchaseInAmount());
						diffQty -= periodInDto.getHqPurchaseInQty();
						diffAmount = diffAmount.subtract(periodInDto.getHqPurchaseInAmount());
					}else if(entry.getTitle().equals("地区自购")){
						entry.setQty(periodInDto.getAreaBuyInQty());
						entry.setAmount(periodInDto.getAreaBuyInAmount());
						diffQty -= periodInDto.getAreaBuyInQty();
						diffAmount = diffAmount.subtract(periodInDto.getAreaBuyInAmount());
					}else if(entry.getTitle().equals("跨区调入")){
						entry.setQty(periodInDto.getOuterTransferInQty());
						entry.setAmount(periodInDto.getOuterTransferInAmount());
						diffQty -= periodInDto.getOuterTransferInQty();
						diffAmount = diffAmount.subtract(periodInDto.getOuterTransferInAmount());
					}
				}
				purchaseDiffEntry.setQty(diffQty); 
				purchaseDiffEntry.setAmount(diffAmount); 
				List<PrintBalanceEntry> lstTransferItem = (List<PrintBalanceEntry>) brandUnitMap.get("transfer");
				PrintBalanceEntry transferDiffEntry = lstTransferItem.get(lstTransferItem.size()-2); 
				for (PrintBalanceEntry entry : lstTransferItem) {
					if(entry.getTitle().equals("跨区调入")){
						entry.setQty(periodInDto.getOuterTransferInQty());
						entry.setAmount(periodInDto.getOuterTransferInAmount());
						transferDiffEntry.setQty(transferDiffEntry.getQty() - periodInDto.getOuterTransferInQty());
						transferDiffEntry.setAmount(transferDiffEntry.getAmount().subtract(periodInDto.getOuterTransferInAmount()));
					}
				}
			}
		}
	}

	private Map<String, Object> initBrandUnitMap(PrintBalanceDiffDto dto,String type,String year, String month) {
		Map<String, Object> brandUnitMap = new HashMap<>();
		List<PrintBalanceEntry> lstPurchaseItem = this.initPurchaseList(dto,type);
		List<PrintBalanceEntry> lstSalesItem = this.initSalesList(dto,type);
		List<PrintBalanceEntry> lstTransferItem = this.initTransferList(dto,type);
		List<PrintBalanceEntry> lstOweOffItem = this.initOweOffList(dto,type);
		brandUnitMap.put("purchase", lstPurchaseItem);
		brandUnitMap.put("sales", lstSalesItem);
		brandUnitMap.put("transfer", lstTransferItem);
		brandUnitMap.put("oweOff", lstOweOffItem);
		brandUnitMap.put("title", year.concat("年").concat(month).concat("月").concat("结账核对"));
		brandUnitMap.put("companyName", dto.getCompanyName());
		brandUnitMap.put("brandUnitName", dto.getBrandUnitName());
		brandUnitMap.put("companyNo", dto.getCompanyNo());
		brandUnitMap.put("brandUnitNo", dto.getBrandUnitNo());
		return brandUnitMap;
	}

	private List<PrintBalanceEntry> initPurchaseList(PrintBalanceDiffDto dto,String type) {
		List<PrintBalanceEntry> newList = new ArrayList<>();
		PrintBalanceEntry entry0 = new PrintBalanceEntry();
		entry0.setSourse("采购");
		entry0.setTitle("地区采购");
		PrintBalanceEntry entry1 = new PrintBalanceEntry();
		if(ShardingUtil.isPE()){
			entry1.setTitle("总部收购");
		}else{
			entry1.setTitle("总部代采");
		}
		PrintBalanceEntry entry2 = new PrintBalanceEntry();
		entry2.setTitle("地区自购");
		PrintBalanceEntry entry3 = new PrintBalanceEntry();
		entry3.setTitle("跨区调入");
		PrintBalanceEntry entry4 = new PrintBalanceEntry();
		entry4.setSourse("期间结存");
		entry4.setTitle("采购入库");
		PrintBalanceEntry entry5 = new PrintBalanceEntry();
		entry5.setTitle("外区调入");
		PrintBalanceEntry entry6 = new PrintBalanceEntry();
		entry6.setTitle("采购退回");
		newList.add(entry4);
		newList.add(entry6);
		newList.add(entry5);
		newList.add(entry0);
		newList.add(entry1);
		newList.add(entry2);
		newList.add(entry3);
		PrintBalanceEntry diffEntry = new PrintBalanceEntry();
		diffEntry.setTitle("差异");
		newList.add(diffEntry);
		if(type.equals("periodIn")){
			entry0.setQty(dto.getAreaPurchaseInQty());
			entry0.setAmount(dto.getAreaPurchaseInAmount());
			entry1.setQty(dto.getHqPurchaseInQty());
			entry1.setAmount(dto.getHqPurchaseInAmount());
			entry2.setQty(dto.getAreaBuyInQty());
			entry2.setAmount(dto.getAreaBuyInAmount());
			entry3.setQty(dto.getOuterTransferInQty());
			entry3.setAmount(dto.getOuterTransferInAmount());
		}else if(type.equals("periodBalance")){
			entry4.setQty(dto.getPurchaseInQty());
			entry4.setAmount(dto.getPurchaseInAmount());
			entry5.setQty(dto.getOuterTransferInQty());
			entry5.setAmount(dto.getOuterTransferInAmount());
			entry6.setQty(dto.getPurchaseReturnQty());
			entry6.setAmount(dto.getPurchaseReturnAmount());

		}
		diffEntry.setQty(entry4.getQty() + entry5.getQty() + entry6.getQty()  - entry0.getQty()- entry1.getQty() - entry2.getQty()  - entry3.getQty());
		diffEntry.setAmount(entry4.getAmount().add(entry5.getAmount()).add(entry6.getAmount()).subtract(entry0.getAmount()).subtract(entry1.getAmount()).subtract(entry2.getAmount()).subtract(entry3.getAmount()));
		return newList;
	}

	private List<PrintBalanceEntry> initSalesList(PrintBalanceDiffDto dto,String type) {
		List<PrintBalanceEntry> newList = new ArrayList<>();
		PrintBalanceEntry entry0 = new PrintBalanceEntry();
		entry0.setSourse("销售");
		entry0.setTitle("门店销售");
		PrintBalanceEntry entry1 = new PrintBalanceEntry();
		entry1.setTitle("批发销售");
		PrintBalanceEntry entry2 = new PrintBalanceEntry();
		entry2.setTitle("内购销售");
		PrintBalanceEntry entry3 = new PrintBalanceEntry();
		entry3.setTitle("跨区调出");
		PrintBalanceEntry entry4 = new PrintBalanceEntry();
		entry4.setTitle("其他出库(销售)");
		PrintBalanceEntry entry5 = new PrintBalanceEntry();
		entry5.setSourse("期间结存");
		entry5.setTitle("销售出库");
		PrintBalanceEntry entry9 = new PrintBalanceEntry();
		entry9.setTitle("其他出库(期间结存)");
		PrintBalanceEntry entry6 = new PrintBalanceEntry();
		entry6.setTitle("外区调出");
		PrintBalanceEntry entry7 = new PrintBalanceEntry();
		entry7.setTitle("前期欠客本期发出");
		PrintBalanceEntry entry8 = new PrintBalanceEntry();
		entry8.setTitle("本期欠客");
		newList.add(entry5);
		newList.add(entry9);
		newList.add(entry6);
		newList.add(entry7);
		newList.add(entry8);
		newList.add(entry0);
		newList.add(entry1);
		newList.add(entry2);
		newList.add(entry3);
		newList.add(entry4);
		PrintBalanceEntry diffEntry = new PrintBalanceEntry();
		diffEntry.setTitle("差异");
		newList.add(diffEntry);
		if(type.equals("periodOut")){
			entry0.setQty(dto.getOuterPosOutQty());
			entry0.setAmount(dto.getOuterPosOutAmount());
			entry0.setCost(dto.getOuterPosOutCost());
			entry1.setQty(dto.getOuterWholesaleOutQty());
			entry1.setAmount(dto.getOuterWholesaleOutAmount());
			entry1.setCost(dto.getOuterWholesaleOutCost());
			entry2.setQty(dto.getOuterWithinOutQty());
			entry2.setAmount(dto.getOuterWithinOutAmount());
			entry2.setCost(dto.getOuterWithinOutCost());
			entry3.setQty(dto.getOuterTransferOutQty());
			entry3.setAmount(dto.getOuterTransferOutAmount());
			entry3.setCost(dto.getOuterTransferOutCost());
			entry4.setQty(dto.getOuterOtherOutQty());
			entry4.setAmount(dto.getOuterOtherOutAmount());
			entry4.setCost(dto.getOuterOtherOutCost());
		}else if(type.equals("periodBalance")){
			entry5.setQty(dto.getSalesOutQty());
			entry5.setAmount(dto.getSalesOutAmount());
			entry5.setCost(dto.getSalesOutCost());
			entry6.setQty(dto.getOuterTransferOutQty());
			entry6.setAmount(dto.getOuterTransferOutAmount());	
			entry6.setCost(dto.getOuterTransferOutCost());
		}else if(type.equals("oweOff")){
			entry7.setQty(dto.getPreOweOffOutQty());
			entry7.setCost(dto.getPreOweOffOutCost());	
			entry8.setQty(dto.getThisOweOffQty());
			entry8.setCost(dto.getThisOweOffCost());
		}
		//差异 = 销售出库+跨区调出 +其他出库 -前期欠客本期发出 +本期欠客  - 门店销售 - 批发销售 - 内购销售 - 跨区调出 - 其他出库
		diffEntry.setQty(entry5.getQty() + entry6.getQty() + entry9.getQty() - entry7.getQty() + entry8.getQty() 
				- entry0.getQty() - entry1.getQty() - entry2.getQty()  - entry3.getQty() + entry4.getQty());
		diffEntry.setCost(entry5.getCost().add(entry6.getCost()).add(entry9.getCost()).subtract(entry7.getCost()).add(entry8.getCost())
				.subtract(entry0.getCost()).subtract(entry1.getCost()).subtract(entry2.getCost()).subtract(entry3.getCost()).subtract(entry4.getCost()));
		return newList;
	}
	
	private List<PrintBalanceEntry> initTransferList(PrintBalanceDiffDto dto,String type) {
		List<PrintBalanceEntry> newList = new ArrayList<>();
		PrintBalanceEntry entry0 = new PrintBalanceEntry();
		entry0.setSourse("采购");
		entry0.setTitle("跨区调入");
		PrintBalanceEntry entry1 = new PrintBalanceEntry();
		entry1.setSourse("期间结存");
		entry1.setTitle("外区调入");
		PrintBalanceEntry entry2 = new PrintBalanceEntry();
		entry2.setSourse("销售");
		entry2.setTitle("跨区调出");
		PrintBalanceEntry entry3 = new PrintBalanceEntry();
		entry3.setSourse("期间结存");
		entry3.setTitle("外区调出");
		newList.add(entry1);
		newList.add(entry0);
		newList.add(entry3);
		newList.add(entry2);
		PrintBalanceEntry diffEntry1 = new PrintBalanceEntry();
		diffEntry1.setTitle("调入差异");
		newList.add(diffEntry1);
		PrintBalanceEntry diffEntry2 = new PrintBalanceEntry();
		diffEntry2.setTitle("调出差异");
		newList.add(diffEntry2);
		if(type.equals("periodIn")){
			entry0.setQty(dto.getOuterTransferInQty());
			entry0.setAmount(dto.getOuterTransferInAmount());
		}else if(type.equals("periodOut")){
			entry2.setQty(dto.getOuterTransferOutQty());
			entry2.setAmount(dto.getOuterTransferOutAmount());
			entry2.setCost(dto.getOuterTransferOutCost());
		}else if(type.equals("periodBalance")){
			entry1.setQty(dto.getOuterTransferInQty());
			entry1.setAmount(dto.getOuterTransferInAmount());
			entry3.setQty(dto.getOuterTransferOutQty());
			entry3.setAmount(dto.getOuterTransferOutAmount());
			entry3.setCost(dto.getOuterTransferOutCost());
		}
		// 差异1 = 跨区调入 - 外区调入  差异2 = 跨区调出 - 外区调处 
		diffEntry1.setQty(entry1.getQty() - entry0.getQty());
		diffEntry2.setQty(entry3.getQty() - entry2.getQty());
		diffEntry1.setAmount(entry1.getAmount().subtract(entry0.getAmount()));
		diffEntry2.setCost(entry3.getCost().subtract(entry2.getCost()));
		return newList;
	}
	
	private List<PrintBalanceEntry> initOweOffList(PrintBalanceDiffDto dto,String type) {
		List<PrintBalanceEntry> newList = new ArrayList<>();
		PrintBalanceEntry entry0 = new PrintBalanceEntry();
		entry0.setSourse("欠客");
		entry0.setTitle("前期欠客本期发出");
		PrintBalanceEntry entry1 = new PrintBalanceEntry();
		entry1.setTitle("本期欠客");
		PrintBalanceEntry entry2 = new PrintBalanceEntry();
		entry2.setSourse("期间结存");
		entry2.setTitle("期初欠客");
		PrintBalanceEntry entry3 = new PrintBalanceEntry();
		entry3.setTitle("期末欠客");
		newList.add(entry0);
		newList.add(entry1);
		newList.add(entry2);
		newList.add(entry3);
		PrintBalanceEntry diffEntry = new PrintBalanceEntry();
		diffEntry.setTitle("差异");
		newList.add(diffEntry);
		if(type.equals("oweOff")){
			entry0.setQty(dto.getThisOweOffQty());
			entry0.setCost(dto.getThisOweOffCost());
			entry1.setQty(dto.getPreOweOffOutQty());
			entry1.setCost(dto.getPreOweOffOutCost());
		}else if(type.equals("beginningOweOff")){
			entry2.setQty(dto.getThisOweOffQty());
			entry2.setCost(dto.getThisOweOffCost());
		}else if(type.equals("endOweOff")){
			entry3.setQty(dto.getThisOweOffQty());
			entry3.setCost(dto.getThisOweOffCost());
		}
		// 差异 = 本期欠客  - 前期欠客本期发出 + 期初欠客  - 期末欠客
		diffEntry.setQty(entry0.getQty() - entry1.getQty() + entry2.getQty() - entry3.getQty());
		diffEntry.setCost(entry0.getCost().subtract(entry1.getCost()).add(entry2.getCost()).add(entry3.getCost()));
		return newList;
	}
	
}