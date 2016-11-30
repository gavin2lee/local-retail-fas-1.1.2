package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import cn.wonhigh.retail.fas.common.dto.POSDepositCashDto;
import cn.wonhigh.retail.fas.common.dto.POSRegisterInvoiceDto;
import cn.wonhigh.retail.fas.common.model.DepositCash;
import cn.wonhigh.retail.fas.common.model.RegisterInvoice;
import cn.wonhigh.retail.pos.api.client.dto.sales.DepositCashDto;
import cn.wonhigh.retail.pos.api.client.dto.sales.RegisterInvoiceDto;
import cn.wonhigh.retail.pos.api.client.utils.CommonUtils;

/**
 * TODO: 增加描述
 * 
 * @author zhang.lh
 * @date 2014-10-16 下午4:51:16
 * @version 0.1.0 
 * @copyright Wonhigh Information Technology (Shenzhen) Co.,Ltd.
 */
public class OrderMainDataSet {
	 
	
	/**
	 * DESC: DepositCash 转化 DepositCashDto
	 * @param depositCashList
	 * @return
	 * @throws Exception
	 */
	public static List<POSDepositCashDto> convertDepositCashDto(
			List<DepositCash> depositCashList) throws Exception{
 		if (!CommonUtils.hasValue(depositCashList)) {
			return null;
		}
		try {
 			List<POSDepositCashDto> depositCashDtoList = new ArrayList<POSDepositCashDto>();
			for (int i = 0,size=depositCashList.size(); i < size; i++) {
				POSDepositCashDto depositCashDto = new POSDepositCashDto();
				PropertyUtils.copyProperties(depositCashDto, depositCashList.get(i));
 				depositCashDtoList.add(depositCashDto);
			}
			depositCashList.clear();
 			return depositCashDtoList;
 		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
 	}
	
	/**
	 * DESC: registerInvoiceList 转化 RegisterInvoiceDto
	 * @param depositCashList
	 * @return
	 * @throws Exception
	 */
	public static List<POSRegisterInvoiceDto> convertRegisterInvoiceDto(
			List<RegisterInvoice> registerInvoiceList) throws Exception{
 		if (!CommonUtils.hasValue(registerInvoiceList)) {
			return null;
		}
		try {
 			List<POSRegisterInvoiceDto> depositCashDtoList = new ArrayList<POSRegisterInvoiceDto>();
			for (int i = 0,size=registerInvoiceList.size(); i < size; i++) {
				POSRegisterInvoiceDto registerInvoiceDto = new POSRegisterInvoiceDto();
				PropertyUtils.copyProperties(registerInvoiceDto, registerInvoiceList.get(i));
 				depositCashDtoList.add(registerInvoiceDto);
			}
			registerInvoiceList.clear();
 			return depositCashDtoList;
 		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
 	}
	
}
