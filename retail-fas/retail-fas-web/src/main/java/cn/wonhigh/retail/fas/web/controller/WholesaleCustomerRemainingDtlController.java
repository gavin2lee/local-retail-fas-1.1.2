package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.WholesaleCustomerRemainingDtl;
import cn.wonhigh.retail.fas.dal.database.WholesaleCustomerRemainingDtlMapper;
import cn.wonhigh.retail.fas.manager.WholesaleCustomerRemainingDtlManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-07-06 12:15:59
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
@RequestMapping("/wholesale_customer_remaining_dtl")
public class WholesaleCustomerRemainingDtlController extends BaseCrudController<WholesaleCustomerRemainingDtl> {
	
    @Resource
    private WholesaleCustomerRemainingDtlManager wholesaleCustomerRemainingDtlManager;

    @Resource
    private WholesaleCustomerRemainingDtlMapper wholesaleCustomerRemainingDtlMapper; 
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("wholesale_customer_remaining_dtl/",wholesaleCustomerRemainingDtlManager);
    }
    
    @RequestMapping({"/list.json"})
    @ResponseBody
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
      int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
      int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
//      String sortColumn = (StringUtils.isEmpty(req.getParameter("sort"))) ? "" : String.valueOf(req.getParameter("sort"));
//      String sortOrder = (StringUtils.isEmpty(req.getParameter("order"))) ? "" : String.valueOf(req.getParameter("order"));

      Map<String, Object> params = builderParams(req, model);
      int total = wholesaleCustomerRemainingDtlManager.findCount(params);
      SimplePage page = new SimplePage(pageNo, pageSize, total);
      List<WholesaleCustomerRemainingDtl> list = wholesaleCustomerRemainingDtlManager.selectDtlByPage(page, params);
      Map<String, Object> obj = new HashMap<String, Object>();
      obj.put("total", Integer.valueOf(total));
      obj.put("rows", list);
      return obj;
    }
    
    @RequestMapping({"/selectRemainingAmount.json"})
    @ResponseBody
    public WholesaleCustomerRemainingDtl selectCustomerRemainingTotalByDate(HttpServletRequest req,Model model) throws ManagerException{
    	Map<String, Object> params = builderParams(req, model);
    	WholesaleCustomerRemainingDtl wholesaleCustomerRemainingDtl = wholesaleCustomerRemainingDtlManager.selectCustomerRemainingTotalByDate(params);
    	return wholesaleCustomerRemainingDtl;
    }
    
    /**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/export")
	public void exportSaleDtl(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		List dataList = null;
		List<Map> ColumnsList =  ExportUtils.getColumnList(req.getParameter("exportColumns"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		dataList = wholesaleCustomerRemainingDtlManager.selectDtlByPage(page, params);
		List<Map> dataMapList = ExportUtils.getDataList(dataList);
		ExportUtils.ExportData(req.getParameter("fileName"), ColumnsList, dataMapList, response);
	}
	
	@RequestMapping(value = "/update_position.json")
	@ResponseBody
	public Map<String, Object> updatePosition(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{		
		
		List<WholesaleCustomerRemainingDtl> tempDtlList = null;
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		try{
			
			//查出数据
			List<WholesaleCustomerRemainingDtl> dtlList = wholesaleCustomerRemainingDtlMapper.selectPositionTwo();
			//SELECT main_id,create_time FROM wholesale_customer_remaining_dtl GROUP BY create_time,main_id HAVING COUNT(create_time) >1;
			//然后遍历结果集
			if(dtlList != null && dtlList.size() > 0){
				for (WholesaleCustomerRemainingDtl tempDtl : dtlList) {
					params.put("mainId", tempDtl.getMainId());
					params.put("createTime", tempDtl.getCreateTime());
					//循环查出结果集
					//SELECT id FROM wholesale_customer_remaining_dtl WHERE main_id=mainId AND create_time=createTime ORDER BY id;
					tempDtlList = wholesaleCustomerRemainingDtlMapper.selectPositionThree(params);
					if(tempDtlList != null && tempDtlList.size() > 0) {
						int num = 0; 
						for (WholesaleCustomerRemainingDtl dtl : tempDtlList) {
							if(num > 0){
								if(dtl.getPosition()!=null){
									dtl.setPosition(dtl.getPosition()+num);
								}
								//更新序号
								wholesaleCustomerRemainingDtlMapper.updateByPrimaryKey(dtl);
							}
							num++;
						}
					}
				}
			}
			//首先根据最新规格全表刷新数据      改为position为空得数据
			wholesaleCustomerRemainingDtlMapper.updatePositionOne();
			//UPDATE wholesale_customer_remaining_dtl SET POSITION=CONCAT(DATE_FORMAT(create_time,'%Y%m%d%H%i%s'),'0001') WHERE 1=1 and position is null;
		}catch(Exception e){
			result.put("success", false);
			result.put("errorMessage", e.getMessage()+" --- " + e);
		}
		result.put("success", true);
		return result;
	}
	
}