package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
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
import cn.wonhigh.retail.fas.common.model.Mall;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.vo.CurrentUser;
import cn.wonhigh.retail.fas.manager.MallManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 10:59:29
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
@RequestMapping("/mall")
public class MallController extends BaseCrudController<Mall> {
    @Resource
    private MallManager mallManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("mall/",mallManager);
    }
    
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
        
        List<String> zoneNoList = new ArrayList<String>();
		//页面选择了公司和管理城市，根据提交来查询店铺
		
		SystemUser currUser = Authorization.getUser();
		if(currUser != null){
			String currZone = Authorization.getCurrentZone();
			params.put("zoneNos",  FasUtil.formatInQueryCondition(currZone));
		}
        int total = mallManager.findCount(params);
        SimplePage page = new SimplePage(pageNo, pageSize, total);
        List list = mallManager.findByPage(page, sortColumn, sortOrder, params);
        Map obj = new HashMap();
        obj.put("total", Integer.valueOf(total));
        obj.put("rows", list);
        return obj;
    }
}