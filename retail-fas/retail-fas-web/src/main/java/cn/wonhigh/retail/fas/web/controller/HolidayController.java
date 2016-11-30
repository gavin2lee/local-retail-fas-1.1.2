package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wonhigh.retail.fas.common.model.Holiday;
import cn.wonhigh.retail.fas.manager.HolidayManager;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-04-19 17:47:43
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
@RequestMapping("/holiday")
public class HolidayController extends BaseCrudController<Holiday> {
    @Resource
    private HolidayManager holidayManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("holiday/",holidayManager);
    }
    
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public Map<String, Object>  doImport(@RequestParam("file") MultipartFile file, HttpServletRequest req)throws Exception{
		String[] fieldNames= new String[]{"year","name","startDate","endDate","days","lastWorkDate"};
		List<Object> lstItem = ImportUtils.getDataList(file.getInputStream(), Holiday.class, fieldNames);
		holidayManager.batchAdd(lstItem);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		return map;
	}
}