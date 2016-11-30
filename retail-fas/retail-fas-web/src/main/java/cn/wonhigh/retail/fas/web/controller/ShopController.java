package cn.wonhigh.retail.fas.web.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopGroup;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.ShopGroupManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-22 11:02:24
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
@RequestMapping("/shop")
public class ShopController extends BaseCrudController<Shop> {
	
    @Resource
    private ShopManager shopManager;
    
    @Resource
    private ShopGroupManager shopGroupManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("shop/",shopManager);
    }
    
    @RequestMapping(value = "/initSubInfo")
	public ResponseEntity<Shop> initSubsiInfo(HttpServletRequest request){
    	String shopNo = request.getParameter("shopNo");
		String payType = request.getParameter("payType");
		String month = request.getParameter("month");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("shopNo", shopNo);
		params.put("payType", payType);
		params.put("month", month);
		Shop shop = shopManager.initSubsiInfo(params);
		return new ResponseEntity<Shop>(shop, HttpStatus.OK);
	}
    
    /**
     * 根据店铺查询店铺开票模板
     * @param request
     * @return
     * @throws ManagerException 
     */
    @RequestMapping(value = "/findShopGroup")
   	public ResponseEntity<ShopGroup> findShopGroup(HttpServletRequest request) throws ManagerException{
       	String shopNo = request.getParameter("shopNo");
    	String companyNo = request.getParameter("companyNo");
   		
   		Map<String,Object> params = new HashMap<String,Object>();
   		params.put("shopNo", shopNo);
   		params.put("companyNo", companyNo);
   		params.put("validDate", new Date());
   		List<ShopGroup> shopGroups = shopGroupManager.getShopGroupNoByShopNo(shopNo);
		
   		ShopGroup shopGroup = new ShopGroup();
   		if(CollectionUtils.isNotEmpty(shopGroups)){
   			shopGroup = shopGroups.get(0);
   		}
   		return new ResponseEntity<ShopGroup>(shopGroup, HttpStatus.OK);
   		
   	}
   
    @RequestMapping(value = "/list_multi_organNo")
	@ResponseBody
	public Map<String, Object> queryListByMultiOrganNo(HttpServletRequest req, Model model) 
			throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 500 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String multiOrganNo = req.getParameter("multiOrganNo");
		Map<String, Object> obj = new HashMap<String, Object>();
		params.put("multiCompanyNo", FasUtil.formatInQueryCondition(req.getParameter("multiCompanyNo")));
		params.put("multiBsgroupsNo", FasUtil.formatInQueryCondition(req.getParameter("multiBsgroupsNo")));
		params.put("multiMallNo", FasUtil.formatInQueryCondition(req.getParameter("multiMallNo")));
		params.put("multiBrandNo", FasUtil.formatInQueryCondition(req.getParameter("multiBrandNo")));
		if(StringUtils.isNotEmpty(multiOrganNo)) {
			List<String> lstOrganNo = Arrays.asList(multiOrganNo.split("\\,"));
			params.put("multiOrganNo", lstOrganNo);
		}
		int total = this.shopManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<Shop> list = this.shopManager.findByPage(page, sortColumn, sortOrder, params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
}