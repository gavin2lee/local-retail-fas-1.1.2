package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.dto.SettlePathQueryExportDto;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.common.model.SupplierGroup;
import cn.wonhigh.retail.fas.manager.SettlePathDtlManager;
import cn.wonhigh.retail.fas.manager.SettlePathManager;
import cn.wonhigh.retail.fas.manager.SupplierGroupManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;


/**
 * 结算路径查询
 * @author yang.y
 * @date  2014-07-02 15:10:04
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
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/settle_path_query")
@ModuleVerify("30114001")
public class SettlePathQueryController extends BaseController<SettlePathDto> {
    
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(SettlePathQueryController.class);
	
	@Resource
    private SettlePathManager settlePathManager;

	@Resource
	private SettlePathDtlManager settlePathDtlManager;
	
	@Resource
	private SupplierGroupManager supplierGroupManager;
	
    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_path_query/",settlePathManager);
    }
    
    /**
     * 进入结算大类查询页面
     * 
     * @return String
     */
    @RequestMapping("/list")
    public String list() {
    	return "settle_path/query_page";
    }
    
    /**
     * 分页查询结算大类数据，需关联明细数据
     * 
     * @param model Model
     * @param request HttpServletRequest
     * @return 存放total和row数据的map
     * @throws ManagerException 
     */
    @RequestMapping("/query.json")
    @ResponseBody
    public Map<String, Object> query(Model model, HttpServletRequest request) throws ManagerException {
    	try {
	    	int pageNo = StringUtils.isEmpty(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));
			int pageSize = StringUtils.isEmpty(request.getParameter("rows")) ? 10 : Integer.parseInt(request.getParameter("rows"));
			String sortColumn = StringUtils.isEmpty(request.getParameter("sort")) ? "" : String.valueOf(request.getParameter("sort"));
			String sortOrder = StringUtils.isEmpty(request.getParameter("order")) ? "" : String.valueOf(request.getParameter("order"));
			Map<String, Object> params = builderParams(request, model);
			int total = settlePathManager.findRelationCount(params);
			
			SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
			List<SettlePathQueryExportDto> list = settlePathManager.findRelationByPage(page, sortColumn, sortOrder, params);
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("total", total);
			obj.put("rows", list);
			return obj;
    	} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
    }
    
    /**
	 * 查询符合表头的数据
	 * @param vo 查询参数
	 * @return List<Map>
     * @throws ManagerException 
	 */
	@Override
	protected List<Map> findComplexHeaderData(SettlePathDto vo) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pathNo", vo.getPathNo());
		List<SettlePathDtl> list = null;
		try {
			list = settlePathDtlManager.findByBiz(null, params);
			if(list != null && list.size() > 0) {
				SupplierGroup supplierGroup = new SupplierGroup();
				supplierGroup.setGroupNo(list.get(0).getCompanyNo());
				SupplierGroup result = supplierGroupManager.findById(supplierGroup);
				if(result != null) {
					list.get(0).setCompanyName(result.getGroupName());
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			List<Map> map = mapper.readValue(json, List.class);
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
}