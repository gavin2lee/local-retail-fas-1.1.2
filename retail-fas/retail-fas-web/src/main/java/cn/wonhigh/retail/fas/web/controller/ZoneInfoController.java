package cn.wonhigh.retail.fas.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.BillPriceTypeEnums;
import cn.wonhigh.retail.fas.common.model.ZoneInfo;
import cn.wonhigh.retail.fas.manager.ZoneInfoManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-08-25 12:10:54
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
@RequestMapping("/zone_info")
public class ZoneInfoController extends BaseCrudController<ZoneInfo> {
	
    @Resource
    private ZoneInfoManager zoneInfoManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("zone_info/",zoneInfoManager);
    }
    
    /**
     * 查询默认价格区
     */
	@RequestMapping("/getPriceZone")
	@ResponseBody
	public List<ZoneInfo> initPriceZone() {
		List<ZoneInfo> list=zoneInfoManager.findPriceZones();
		return list;
	}
    
    /**
     * 查询结算路径中结算依据相关的地区数据
     * @param request HttpServletRequest
     * @param model Model
     * @return List<ZoneInfo>
     * @throws ManagerException 异常
     */
    @RequestMapping("/list_settle_path_zone")
    @ResponseBody
    public List<ZoneInfo> listSettlePathZone(HttpServletRequest request, Model model) 
    	throws Exception {
    	List<ZoneInfo> list = zoneInfoManager.findPriceZones();
//    	String suffix = request.getParameter("suffix");
//    	String suffixValue = "";
//    	if(StringUtils.isNotEmpty(suffix)) {
//    		suffixValue = new String(suffix.getBytes("iso-8859-1"), "UTF-8");  
//    	}
    	String suffixValue = "成本";
    	if(list != null && list.size() > 0) {
    		for(ZoneInfo zone : list) {
    			zone.setName(zone.getName() + suffixValue);
    		}
    	}
    	ZoneInfo zone = new ZoneInfo();
    	zone.setZoneNo(BillPriceTypeEnums.PURCHASE_PRICE.getTypeNo());
    	zone.setName(BillPriceTypeEnums.PURCHASE_PRICE.getTypeName());
    	list.add(zone);
    	return list;
    }
}