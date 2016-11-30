package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.OfficialItem;
import cn.wonhigh.retail.fas.manager.OfficialItemManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-03-04 15:25:06
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
@RequestMapping("/official_item")
public class OfficialItemController extends BaseCrudController<OfficialItem> {
    @Resource
    private OfficialItemManager officialItemManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("official_item/",officialItemManager);
    }
    
    @ResponseBody
    @RequestMapping(value = "/update_data")
    public Map<String, Object> updateOfficeItem(HttpServletRequest req) throws ManagerException{
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("updateTimeStart", req.getParameter("updateTimeStart"));
    	return officialItemManager.updateOfficialItem(params);
    }
    
    @ResponseBody
    @RequestMapping(value = "/update_all_data")
    public Map<String, Object> updateAllOfficeItem(HttpServletRequest req) throws ManagerException{
    	return officialItemManager.updateAllOfficeItem();
    }
    
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export_data")
	public void exportData(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		List<Map> ColumnsList =  ExportUtils.getColumnList(req.getParameter("exportColumns"));
		Map<String, Object> params = builderParams(req, model);
		List<Map> dataMapList = ExportUtils.getDataList(this.officialItemManager.findByBiz(null, params));
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
		
	}
    
}