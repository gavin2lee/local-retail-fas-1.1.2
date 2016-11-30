package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.SaleproxyBalanceSettleprice;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.manager.SaleproxyBalanceSettlepriceManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;
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
@RequestMapping("/saleproxy_balance_settleprice")
public class SaleproxyBalanceSettlepriceController  extends ExcelImportController<SaleproxyBalanceSettleprice> {
    @Resource
    private SaleproxyBalanceSettlepriceManager saleproxyBalanceSettlepriceManager;
    
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(SaleproxyBalanceSettlepriceController.class);

    @Override
    public CrudInfo init() {
        return new CrudInfo("saleproxy_balance_settleprice/",saleproxyBalanceSettlepriceManager);
    }
    
    @RequestMapping("/list")
  	public String list() {
  		return "saleproxy_balance/settleprice";
  	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<SaleproxyBalanceSettleprice> list) throws ManagerException {
		// TODO Auto-generated method stub
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
					SaleproxyBalanceSettleprice saleproxyBalanceSettleprice = mapper.readValue(mapper.writeValueAsString(map), SaleproxyBalanceSettleprice.class);
					saleproxyBalanceSettlepriceManager.deleteById(saleproxyBalanceSettleprice);
				}
			}
			if(StringUtils.isNotEmpty(upadtedList)) {
				List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {});
				
				for(Map map : list){
					SaleproxyBalanceSettleprice saleproxyBalanceSettleprice = mapper.readValue(mapper.writeValueAsString(map), SaleproxyBalanceSettleprice.class);
					setDefaulValues(saleproxyBalanceSettleprice,req);
//					Map<String,String> _errorInfo = null ;//= saleproxyBalanceSettlepriceManager.validationSaleproxyBalanceSettleprice(SaleproxyBalanceSettleprice);
//					if(_errorInfo.get("error") != null){
//						respInfo.put("error", _errorInfo.get("error") + "<br />" );
//						continue;
//					}
					saleproxyBalanceSettlepriceManager.modifyById(saleproxyBalanceSettleprice);
				}
				
			}
			if(StringUtils.isNotEmpty(insertedList)) {
				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {});
				for(Map map : list){
					SaleproxyBalanceSettleprice saleproxyBalanceSettleprice = mapper.readValue(mapper.writeValueAsString(map), SaleproxyBalanceSettleprice.class);
					setDefaulValues(saleproxyBalanceSettleprice,req);
					saleproxyBalanceSettleprice.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(saleproxyBalanceSettleprice.getSalerNo()));
//					Map<String,String> _errorInfo = null ;;// SaleproxyBalanceSettlepriceManager.validationSaleproxyBalanceSettleprice(SaleproxyBalanceSettleprice);
//					if(_errorInfo.get("error") != null){
//						respInfo.put("error", _errorInfo.get("error") + "<br />" );
//						continue;
//					}
					saleproxyBalanceSettleprice.setId(UUIDGenerator.getUUID());
					saleproxyBalanceSettlepriceManager.add(saleproxyBalanceSettleprice);
				}
				
			}
		} 

		catch (Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		return new ResponseEntity<Map<String, String>>(respInfo, HttpStatus.OK);
	}
}