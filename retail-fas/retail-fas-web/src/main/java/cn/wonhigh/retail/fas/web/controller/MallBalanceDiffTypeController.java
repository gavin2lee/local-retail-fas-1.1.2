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

import cn.wonhigh.retail.fas.common.model.MallBalanceDiffType;
import cn.wonhigh.retail.fas.manager.MallBalanceDiffTypeManager;
import cn.wonhigh.retail.fas.web.utils.ExportUtils;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-09-11 10:15:51
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
@RequestMapping("/mall_shopbalancedifftype")
@ModuleVerify("301400011")
public class MallBalanceDiffTypeController extends BaseController<MallBalanceDiffType> {
    @Resource
    private MallBalanceDiffTypeManager mallBalanceDiffTypeManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("mall_balance_diff_type/",mallBalanceDiffTypeManager);
    }
    
    @RequestMapping("/list")
   	public String list() {
   		return "mallshop_balance/mall_balance_difftype";
   	}
    
    /**
	 * 导出
	 * @param req
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest req,Model model, HttpServletResponse response) throws Exception{
		String fileName = req.getParameter("fileName");
		String exportColumns = req.getParameter("exportColumns");
		int pageNumber = Integer.parseInt(req.getParameter("pageNumber") == null? "0" : req.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize") == null? "0" : req.getParameter("pageSize"));
		Map<String, Object> params = builderParams(req, model);
		SimplePage page = new SimplePage(pageNumber, pageSize, pageSize);
		page = new SimplePage(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		List<MallBalanceDiffType> dataList = mallBalanceDiffTypeManager.findByPage(page, "", "", params);
		ExportUtils.doExport(fileName, exportColumns, dataList, response);
	}
    
	/**
   	 * 校验数据合法性
   	 * @param datas 待校验的数据集合
   	 * @return Map<String, Object>
   	 * @throws ManagerException 异常
   	 */
	@RequestMapping("/validate_data")
	@ResponseBody
   	public Map<String, Object> validateData(HttpServletRequest request) throws ManagerException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JsonUtil<MallBalanceDiffType> util = new JsonUtil<MallBalanceDiffType>();
			Map<CommonOperatorEnum, List<MallBalanceDiffType>> datas = util.convertToMap(request, 
					MallBalanceDiffType.class);
			if(datas != null && datas.size() > 0) {
				List<MallBalanceDiffType> lstDel = datas.get(CommonOperatorEnum.DELETED);
				List<MallBalanceDiffType> lstUpdate = datas.get(CommonOperatorEnum.UPDATED);
				if(null != lstUpdate && lstUpdate.size() > 0) {
					for(MallBalanceDiffType model : lstUpdate) {
						Map<String, Object> queryParams = new HashMap<String, Object>();
						queryParams.put("uniqueCompanyNo", model.getCompanyNo());
						queryParams.put("uniqueName", model.getName());
						List<MallBalanceDiffType> lstResult = mallBalanceDiffTypeManager.findByBiz(null, queryParams);
						if(lstResult != null && lstResult.size() > 0
								&& !lstResult.get(0).getId().equals(model.getId())
								&& !this.checkDelListCotainItem(lstDel, model)
								&& !this.checkUpdateListCotainItem(lstUpdate, model, lstResult.get(0))) {
							result.put("success", false);
							result.put("message", "有重复数据，请检查后再操作！");
							return result;
						}
					}
				}
				List<MallBalanceDiffType> lstInsert = datas.get(CommonOperatorEnum.INSERTED);
				if(null != lstInsert && lstInsert.size() > 0) {
					for(MallBalanceDiffType model : lstInsert) {
						Map<String, Object> queryParams = new HashMap<String, Object>();
						queryParams.put("uniqueCompanyNo", model.getCompanyNo());
						queryParams.put("uniqueName", model.getName());
						List<MallBalanceDiffType> lstResult = mallBalanceDiffTypeManager.findByBiz(null, queryParams);
						if(lstResult != null && lstResult.size() > 0 
								&& !this.checkDelListCotainItem(lstDel, model)
								&& !this.checkUpdateListCotainItem(lstUpdate, model, lstResult.get(0))) {
							result.put("success", false);
							result.put("message", "有重复数据，请检查后再操作！");
							return result;
						}
					}
				}
			}
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", "操作失败,请联系管理员!");
			result.put("success", true);
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return result;
   	}
   	
   	/**
   	 * 判断item是否在集合中
   	 * @param list 集合数据
   	 * @param item 单个数据
   	 * @return boolean
   	 */
   	private boolean checkDelListCotainItem(List<MallBalanceDiffType> list, MallBalanceDiffType item) {
   		if(list == null || list.size() == 0) {
   			return false;
   		}
   		for(MallBalanceDiffType model : list) {
   			if(model.getCompanyNo().equals(item.getCompanyNo())
   					&& model.getName().equals(item.getName())) {
   				return true;
   			}
   		}
   		return false;
   	}
   	
   	/**
   	 * 判断item是否在集合中
   	 * @param list 集合数据
   	 * @param item 单个数据
   	 * @return boolean
   	 */
   	private boolean checkUpdateListCotainItem(List<MallBalanceDiffType> list, MallBalanceDiffType item,
   			MallBalanceDiffType queryItem) {
   		if(list == null || list.size() == 0) {
   			return false;
   		}
   		for(MallBalanceDiffType model : list) {
   			if(queryItem.getId().equals(model.getId()) 
   					&& queryItem.getCompanyNo().equals(model.getCompanyNo())
   					&& queryItem.getName().equals(model.getName())) {
   				return false;
   			}
   			if(queryItem.getId().equals(model.getId()) 
   					&& (!queryItem.getCompanyNo().equals(model.getCompanyNo()) || !queryItem.getName().equals(model.getName()))) {
   				return true;
   			}
   		}
   		return false;
   	}
}