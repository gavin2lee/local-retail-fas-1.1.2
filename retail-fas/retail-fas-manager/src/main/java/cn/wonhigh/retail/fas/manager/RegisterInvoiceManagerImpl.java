package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.dto.POSOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSRegisterInvoiceDto;
import cn.wonhigh.retail.fas.common.model.RegisterInvoice;
import cn.wonhigh.retail.fas.common.model.SelfShopBankInfo;
import cn.wonhigh.retail.fas.common.model.SelfShopTerminalAccount;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.service.RegisterInvoiceService;
import cn.wonhigh.retail.fas.service.SelfShopBankInfoService;
import cn.wonhigh.retail.fas.service.SelfShopTerminalAccountService;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.pos.api.client.dto.sales.OrderParameterDto;
import cn.wonhigh.retail.pos.api.client.dto.sales.RegisterInvoiceDto;
import cn.wonhigh.retail.pos.api.client.dto.util.PagingDto;
import cn.wonhigh.retail.pos.api.client.dto.util.SimplePageDto;
import cn.wonhigh.retail.pos.api.client.service.sales.OrderApi;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途
 * 
 * @author chen.mj
 * @date 2014-10-22 13:51:56
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Service("registerInvoiceManagerImpl")
class RegisterInvoiceManagerImpl extends BaseCrudManagerImpl implements
		RegisterInvoiceManager {
	@Resource
	private RegisterInvoiceService registerInvoiceService;
	
	@Resource
	private SelfShopBankInfoService selfShopBankInfoService;
	
	@Resource
	private SelfShopTerminalAccountService selfShopTerminalAccountService;

	@Resource
	private OrderApi orderApi;
	
	@Resource
    private ShopService shopService;
	
	@Resource
	private OrderMainManager orderMainManager;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(RegisterInvoiceManagerImpl.class);
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public BaseCrudService init() {
		return registerInvoiceService;
	}

	@Override
	public Map<String, Object> queryRegisterInvoiceListRemote(Map<String, Object> params) throws ManagerException {
		POSOcSimplePageDto pageDto = new POSOcSimplePageDto();
		String pageSizeTemp = params.get("pageSize").toString();
		String pageNumberTemp = params.get("pageNumber").toString();
		if(pageSizeTemp != null && !"".equals(pageSizeTemp)){
			pageDto.setPageSize(Integer.valueOf(pageSizeTemp));
		}
		if(pageNumberTemp != null && !"".equals(pageNumberTemp)){
			pageDto.setPageNo(Integer.valueOf(pageNumberTemp));
		}
		POSOrderParameterDto orderParameterDto = new POSOrderParameterDto();
		List<String> shopNos = new ArrayList<String>();
		Map<String, Object> obj = new HashMap<String,Object>();
		try {
			String companyNo = params.get("companyNo").toString();
	    	String startTime = params.get("createTimeStart").toString();
	    	String endTime = params.get("createTimeEnd").toString();
	    	
	    	if(companyNo == null || "".equals(companyNo) || startTime == null || "".equals(companyNo) || endTime == null || "".equals(endTime)){
	    		return null;
	    	}
	    	List<Shop> shopList = getAllShopByComanyNo(companyNo);
	    	if(shopList == null || shopList.size() < 1){
	    		return null;
	    	}
	    	for(Shop shop : shopList){
    			if(!shopNos.contains(shop.getShopNo())){
    				shopNos.add(shop.getShopNo());
    			}
    		}
	    	orderParameterDto.setStartDate(startTime);
	    	orderParameterDto.setEndDate(endTime);
			
	    	POSOcPagingDto<POSRegisterInvoiceDto> pagingDtoList = orderMainManager.findList4OrderRegisterInvoiceDto(pageDto,shopNos,orderParameterDto);
//	    	PagingDto<RegisterInvoiceDto> pagingDtoList = getData();
	    	if(pagingDtoList != null && pagingDtoList.getResult() != null && pagingDtoList.getResult().size() > 0){
	    		obj.put("total", pagingDtoList.getTotal());
	    		List<RegisterInvoice> registerInvoiceList = dataConvert(pagingDtoList);
	    		obj.put("rows", registerInvoiceList);
	    	}
			return obj;
		} catch (Exception e) {
			// TODO Auto-generated catch block0
			LOGGER.error("Get Register Invoice Failed ....");
			throw new ManagerException(e.getMessage(), e);

		}
	}
	
	public List<RegisterInvoice> dataConvert(POSOcPagingDto<POSRegisterInvoiceDto> pingDto) throws ManagerException{
		
		List<RegisterInvoice> registerInvoiceList = null;
		List<String> shopNos = null;
		if(pingDto != null && pingDto.getResult() != null && pingDto.getResult().size() > 0){
			
			shopNos = new ArrayList<String>();
			for(POSRegisterInvoiceDto registerInvoiceDto : pingDto.getResult()){
				if(!shopNos.contains(registerInvoiceDto.getShopNo())){
					shopNos.add(registerInvoiceDto.getShopNo());
				}
			}
			Map<String,Object> maps = new HashMap<String,Object>();
			maps.put("listShopNos", shopNos);
			List<SelfShopTerminalAccount> selfShopTerminalAccountList = null;
			try {
				selfShopTerminalAccountList = selfShopTerminalAccountService.queryListByShopNos(maps);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				throw new ManagerException(e.getMessage(), e);

			}
			Map<String,SelfShopTerminalAccount> selfShopTerminalAccountMap = new HashMap<String,SelfShopTerminalAccount>();
			for(SelfShopTerminalAccount selfShopTerminalAccount : selfShopTerminalAccountList){
				if(!selfShopTerminalAccountMap.containsKey(selfShopTerminalAccount.getShopNo())){
					selfShopTerminalAccountMap.put(selfShopTerminalAccount.getShopNo(), selfShopTerminalAccount);
				}
			}
			
			registerInvoiceList = new ArrayList<RegisterInvoice>();
			for(POSRegisterInvoiceDto invoiceDto : pingDto.getResult()){
				RegisterInvoice invoice = new RegisterInvoice();
//				BeanUtils.copyProperties(invoiceDto, invoice);
				invoice.setShopNo(invoiceDto.getShopNo());
				invoice.setShopName(invoiceDto.getShopName());
				invoice.setBillNo(invoiceDto.getBillNo());
				invoice.setShouldAmount(invoiceDto.getShouldAmount());
				invoice.setActualAmount(invoiceDto.getActualAmount());
				invoice.setOutDate(invoiceDto.getOutDate());
				invoice.setRemark(invoiceDto.getRemark());
				invoice.setOrderNo(invoiceDto.getOrderNo());
				if(invoice.getShouldAmount()!=null){
					invoice.setDiffAmount(invoice.getShouldAmount().subtract(invoice.getActualAmount()));
				}
				SelfShopTerminalAccount selfShopTerminalAccount = selfShopTerminalAccountMap.get(invoice.getShopNo());
				if(selfShopTerminalAccount != null){
					invoice.setTerminalNumber(selfShopTerminalAccount.getTerminalNumber());
				}
				
				registerInvoiceList.add(invoice);
			}
		}
		return registerInvoiceList;
	}
	
	 public List<Shop> getAllShopByComanyNo(String companyNo) throws ServiceException{
	    	Map<String,Object> params = new HashMap<String,Object>();
	    	params.put("companyNo", companyNo);
	    	List<Shop> shopList = shopService.findByBiz(new Shop(), params);
	    	if(shopList == null || shopList.size() < 1){
	    		return null;
	    	}
	    	return shopList;
	 }
	
	public PagingDto<RegisterInvoiceDto> getData(){
		
		PagingDto<RegisterInvoiceDto> pagingDto = new PagingDto<RegisterInvoiceDto>();
		
		List<RegisterInvoiceDto> registerInvoiceDtoList = new ArrayList<RegisterInvoiceDto>();
		
		pagingDto.setTotal(10);
		pagingDto.setResult(registerInvoiceDtoList);
		
		for(int i = 0 ; i < 10;i++){
			
			RegisterInvoiceDto registerInvoiceDto = new RegisterInvoiceDto();
			registerInvoiceDto.setShopName("shopName"+i);
			registerInvoiceDto.setShopNo("BLCT3"+i);
			registerInvoiceDto.setOutDate(new Date());
			registerInvoiceDto.setShouldAmount(new BigDecimal(580).multiply(new BigDecimal(i)) );
			registerInvoiceDto.setActualAmount(new BigDecimal(380).multiply(new BigDecimal(i)) );
			registerInvoiceDto.setDiffAmount(new BigDecimal(123).multiply(new BigDecimal(i)) );
			registerInvoiceDto.setRemark("counted wroung , maybe ...");
			
			registerInvoiceDtoList.add(registerInvoiceDto);
		}
		
		return pagingDto;
	}

	@Override
	public List<RegisterInvoice> findByBillNo(Map<String, Object> params) {
		return registerInvoiceService.findByBillNo(params);
	}

}