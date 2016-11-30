package cn.wonhigh.retail.fas.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.backend.security.DataAccessEnum;
import cn.wonhigh.retail.fas.common.model.Organ;
import cn.wonhigh.retail.fas.manager.OrganManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-10-13 14:01:15
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
@RequestMapping("/organ")
public class OrganController extends BaseCrudController<Organ> {
    @Resource
    private OrganManager organManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("organ/",organManager);
    }
    
    @RequestMapping(value = "/get_biz")
	@ResponseBody
	public List<Organ> getBiz(Organ modelType,HttpServletRequest req,Model model)throws ManagerException{
		Map<String,Object> params=builderParams(req, model);
		String organLevel = req.getParameter("organLevel");
		if(StringUtils.isEmpty(organLevel)) {
			params.put("organLevel", 1);
		}
		return this.organManager.findByBiz(modelType, params);
	}
    
    /**
     * 通过多个值查询数据
     * @param modelType
     * @param req
     * @return
     * @throws ManagerException
     */
    @RequestMapping(value = "/get_mulit_biz")
  	@ResponseBody
  	public List<Organ> getMulitBiz(HttpServletRequest req,Model model) throws ManagerException{
  		Map<String,Object> params = builderParams(req, model);
  		String mulitParentNo = req.getParameter("multiParentNo");
  		if(StringUtils.isNotEmpty(mulitParentNo)) {
  			List<String> lstParentNo = Arrays.asList(mulitParentNo.split("\\,"));
  			params.put("multiParentNo", lstParentNo);
  		}
  		return this.organManager.findByBiz(null, params);
  	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value={"/list.json"})
    @ResponseBody
    public Map queryList(HttpServletRequest req, Model model)
        throws ManagerException
    {
        int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
        int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
        String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
        String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
        Map params = builderParams(req, model);
		SystemUser currUser = Authorization.getUser();
		params.put("zoneNos",  "");
		if(currUser != null){
			List<String> lstZone = Authorization.getAccessData(DataAccessEnum.ZONE);
			if(!CollectionUtils.isEmpty(lstZone)){
				StringBuffer sb = new StringBuffer();
				for (String zoneNo : lstZone) {
					sb.append("'").append(zoneNo).append("',");
				}
				params.put("zoneNos",  "(".concat(sb.deleteCharAt(sb.length()-1).toString()).concat(")"));
			}
		}
        int total = organManager.findCount(params);
        SimplePage page = new SimplePage(pageNo, pageSize, total);
        List list = organManager.findByPage(page, sortColumn, sortOrder, params);
        Map obj = new HashMap();
        obj.put("total", Integer.valueOf(total));
        obj.put("rows", list);
        return obj;
    }
}