package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.common.model.SelfShopDepositAccount;
import cn.wonhigh.retail.fas.common.model.ShopCheckSet;
import cn.wonhigh.retail.fas.manager.ShopCheckSetManager;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-21 14:24:19
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
@RequestMapping("/shop_check_set")
public class ShopCheckSetController extends BaseController<ShopCheckSet> {
    @Resource
    private ShopCheckSetManager shopCheckSetManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/shop_check_set/",shopCheckSetManager);
    }
    
    @RequestMapping({ "/getSerialNo" })
    @ResponseBody
    public String getSerialNo(HttpServletRequest req){
    	List<ShopCheckSet> list = shopCheckSetManager.getSerialNo();
    	if(list.size()<=0){
    		return "000";
    	}
    	String serialNo = list.get(0).getRowId().toString();
    	int length = serialNo.length();
    	while(length<3){
    		serialNo = "0"+serialNo;
    		length++;
    	}
		return serialNo;
    }
    
    @RequestMapping({"/getInsertNo"})
    @ResponseBody
    public String getInsertNo(HttpServletRequest req){
    	String s1 = req.getParameter("serialNo");
    	Integer s2 = Integer.parseInt(s1) + 1;
    	String serialNo = s2.toString();
    	int length = serialNo.length();
    	while(length<3){
    		serialNo = "0"+serialNo;
    		length++;
    	}
    	return serialNo;
    }
}