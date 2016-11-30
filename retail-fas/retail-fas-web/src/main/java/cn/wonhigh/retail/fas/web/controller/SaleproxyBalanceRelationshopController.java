package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;


import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SaleproxyBalanceRelationshop;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.manager.SaleproxyBalanceRelationshopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2016-08-16 11:55:08
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/saleproxy_balance_relationshop")
@ModuleVerify("3140000")
public class SaleproxyBalanceRelationshopController  extends ExcelImportController<SaleproxyBalanceRelationshop> {
    @Resource
    private SaleproxyBalanceRelationshopManager saleproxyBalanceRelationshopManager;

    private static final XLogger LOGGER = XLoggerFactory.getXLogger(SaleproxyBalanceRelationshopController.class);
    
    
    @RequestMapping("/list")
  	public String list() {
  		return "saleproxy_balance/relationshop";
  	}
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("saleproxy_balance_relationshop/",saleproxyBalanceRelationshopManager);
    }

	@Override
	protected String[] getImportProperties() {
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<SaleproxyBalanceRelationshop> list) throws ManagerException {
		return false;
	}
	
	@RequestMapping("/validationRepeat")
	@ResponseBody
	public ResponseEntity<Map<String, String>> validationRepeat(HttpServletRequest req) throws Exception{
		
		String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		
		Map<String,String> respInfo = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			if(StringUtils.isNotEmpty(deletedList)) {
				List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {});
				for(Map map : list){
					SaleproxyBalanceRelationshop saleproxyBalanceRelationshop = mapper.readValue(mapper.writeValueAsString(map), SaleproxyBalanceRelationshop.class);
					saleproxyBalanceRelationshopManager.deleteById(saleproxyBalanceRelationshop);
				}
			}
			if(StringUtils.isNotEmpty(upadtedList)) {
				List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {});
				
				for(Map map : list){
					SaleproxyBalanceRelationshop SaleproxyBalanceRelationshop = mapper.readValue(mapper.writeValueAsString(map), SaleproxyBalanceRelationshop.class);
					setDefaulValues(SaleproxyBalanceRelationshop,req);
					SaleproxyBalanceRelationshop.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(SaleproxyBalanceRelationshop.getSalerNo()));
//					Map<String,String> _errorInfo =null;// saleproxyBalanceRelationshopManager.validationSaleproxyBalanceRelationshop(SaleproxyBalanceRelationshop);
//					if(_errorInfo.get("error") != null){
//						respInfo.put("error", _errorInfo.get("error") + "<br />" );
//						continue;
//					}
					saleproxyBalanceRelationshopManager.modifyById(SaleproxyBalanceRelationshop);
				}
				
			}
			if(StringUtils.isNotEmpty(insertedList)) {
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {});
				for(Map map : list){
					SaleproxyBalanceRelationshop saleproxyBalanceRelationshop = mapper.readValue(mapper.writeValueAsString(map), SaleproxyBalanceRelationshop.class);
					setDefaulValues(saleproxyBalanceRelationshop,req);
					saleproxyBalanceRelationshop.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(saleproxyBalanceRelationshop.getSalerNo()));
//					Map<String,String> _errorInfo = null;// saleproxyBalanceRelationshopManager.validationSaleproxyBalanceRelationshop(SaleproxyBalanceRelationshop);
//					if( null != _errorInfo.get("error") ){
//						respInfo.put("error", _errorInfo.get("error") + "<br />" );
//						continue;
//					}
					saleproxyBalanceRelationshop.setId(UUIDGenerator.getUUID());
					saleproxyBalanceRelationshopManager.add(saleproxyBalanceRelationshop);
				}
				
			}
		} 

		catch (Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		return new ResponseEntity<Map<String, String>>(respInfo, HttpStatus.OK);
	}
}