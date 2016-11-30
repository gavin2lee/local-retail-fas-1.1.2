package cn.wonhigh.retail.fas.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.InvoiceTemplateSet;
import cn.wonhigh.retail.fas.common.model.Memorandum;
import cn.wonhigh.retail.fas.manager.MemorandumManager;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;
/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-06 15:58:08
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
@RequestMapping("/memorandum")
public class MemorandumController extends BaseCrudController<Memorandum> {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

    @Resource
    private MemorandumManager memorandumManager;
    @Override
    public CrudInfo init() {
        return new CrudInfo("memorandum/",memorandumManager);
    }
    //查询方法
    @RequestMapping(value = "/getMemorandum")
	@ResponseBody
	public List<Memorandum> getMemorandum(HttpServletRequest req, Memorandum model) throws ManagerException {
		List<Memorandum> list = new ArrayList<Memorandum>();
		SystemUser currentUser = CurrentUser.getCurrentUser(req);
		Date d = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    String dateNowStr = sdf.format(d);  
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("createNo", currentUser.getUserid());
		params.put("createName", currentUser.getUsername());
		params.put("executionTime",dateNowStr );
		try {
			list=memorandumManager.findByBiz(null,params);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return list;
	}
    
    /**
   	 * 新增
   	 * @param request HttpServletRequest
   	 * @return Map<String, Boolean>
     * @throws ManagerException 
   	 */
   	@RequestMapping(value = "/saves")
   	@ResponseBody
   	public Map<String, Object> saveAll(HttpServletRequest req, Memorandum model) throws ManagerException {
   		Map<String, Object> map = new HashMap<String, Object>();
   		SystemUser currentUser = CurrentUser.getCurrentUser(req);
   		model.setCreaeTime(new Date());
   		model.setCreateUser(currentUser.getUsername());
   		model.setCreateNo(currentUser.getUserid());
		try {
			memorandumManager.add(model);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		map.put("success", true);
		return map;
   	}
    /**
   	 * 修改
   	 * @param request HttpServletRequest
   	 * @return Map<String, Boolean>
     * @throws ManagerException 
   	 */
   	@RequestMapping(value = "/updateId")
   	@ResponseBody
   	public Map<String, Object> updateId(Memorandum obj, HttpServletRequest req, Model model) throws ManagerException {
   		Map<String, Object> map = new HashMap<String, Object>();
   		obj.setCreaeTime(new Date());
		try {
			memorandumManager.modifyById(obj);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		map.put("success", true);
		return map;
   	}
}