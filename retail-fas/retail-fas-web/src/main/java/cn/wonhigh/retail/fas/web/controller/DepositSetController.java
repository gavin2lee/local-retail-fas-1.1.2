package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wonhigh.retail.fas.common.model.DepositCheck;
import cn.wonhigh.retail.fas.common.model.DepositSet;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.manager.DepositSetManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-10-10 16:14:40
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
@RequestMapping("/deposit_set")
@ModuleVerify("30170027")
public class DepositSetController extends ExcelImportController<DepositSet>{
    @Resource
    private DepositSetManager depositSetManager;
    @Resource
    private ShopManager shopManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/deposit_set/",depositSetManager);
    }

	@Override
	@RequestMapping(value = "/list.json")
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String orderByField = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String orderBy = StringUtils.isEmpty(req.getParameter("order")) ? "" : String
				.valueOf(req.getParameter("order"));

		Map<String, Object> params = builderParams(req, model);
		
		params = setParams(req, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		if(params == null ){
			obj.put("total", 0);
			obj.put("rows", new ArrayList<DepositSet>());
			return obj;
		}
		int total = this.depositSetManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<DepositSet> list = this.depositSetManager.findByPage(page, orderByField, orderBy,
				params);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
	
	/**
	 * 根据shop_no查询店铺存现设置信息
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/getDepositSet" })
    @ResponseBody
	public ResponseEntity<Map<String, Object>> getDepositSet(HttpServletRequest req){
		String shop_no = req.getParameter("shopNo");
		if(null == shop_no){
			return null;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", shop_no);
		DepositSet depositSet = depositSetManager.getDepositSet(params);
		
		Map<String,Object> respInfo = new HashMap<String,Object>();
		if(null!=depositSet){
			respInfo.put("beyondLastDepositDate", depositSet.getBeyondLastDepositDate());
			respInfo.put("depositDiff", depositSet.getDepositDiff());
		}
		
		
		return new ResponseEntity<Map<String, Object>>(respInfo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");

		List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), DepositSet.class, request);
		StringBuilder errorBuilder = new StringBuilder();
		if (msgList != null && msgList.size() > 0) {
			for (UploadMessageModel message : msgList) {
				errorBuilder.append(message.getMessage() + "<br/>");
			}
			mv.addObject("error", errorBuilder);
		} else {
			mv.addObject("success", "成功导入");
		}
		return mv;
	}
	
	@Override
	protected List<DepositSet> queryExportData(Map<String, Object> params) throws ManagerException {
		params = setParams(null, params);
		List<DepositSet> list = new ArrayList<DepositSet>();
		if (params == null) {
			return list;
		}
		int total = this.depositSetManager.findCount(params);
		SimplePage page = new SimplePage(1, total, (int) total);
		list = this.depositSetManager.findByPage(page, null, null,params);
		return list;
	}
	
    /**
	 * 新增保存
     * @throws Exception 
	 */
	public ResponseEntity<Map<String, Boolean>> save(HttpServletRequest req) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Map<CommonOperatorEnum, List<DepositSet>> params=null;
		try {
			params = this.convertToMap(req);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		if(params.size() > 0) {
			List<DepositSet> list = params.get(CommonOperatorEnum.INSERTED);
			if(list!=null &&list.size()>0){
				try {
					depositSetManager.addListDepositSet(list);
				} catch (ManagerException e) {
					logger.error(e.getMessage(), e);
				}
				map.put("success", true);
				return new ResponseEntity<Map<String, Boolean>>(map, HttpStatus.OK);
			}else{
				try {
					depositSetManager.save(params);
				} catch (ManagerException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
				}
			}
		}
		map.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(map, HttpStatus.OK);
	}
    
	private Map<String, Object> setParams(HttpServletRequest req, Map<String, Object> params) throws ManagerException {
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		String bizCityNo = params.get("bizCityNo") == null ? null : params.get("bizCityNo").toString();
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		Map<String, Object> shopParamMap = new HashMap<String, Object>();
		
		shopParamMap.put("companyNo", companyNo);//根据公司查询店铺
		shopParamMap.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		shopParamMap.put("bizCityNoLists", FasUtil.formatInQueryCondition(bizCityNo));
		
		List<String> shopList = shopManager.getShopBySelfCheckin(shopParamMap);
		if(shopList.size()<= 0){
			return null;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shopNoList", shopList);
		map.put("pageSize", params.get("pageSize"));
		map.put("pageIndex", params.get("pageIndex"));
		map.put("startOutDate", params.get("startOutDate"));
		map.put("rows", params.get("rows"));
		map.put("endOutDate", params.get("endOutDate"));
		map.put("page", params.get("page"));
		map.put("pageNumber", params.get("pageNumber"));
		return map;
	}

	@Override
	protected String[] getImportProperties() {
		return new String[] { "shopNo","prepareCash","startAmount","beyondLastDepositDate","amount","depositDiff"};
	}

	@Override
	protected boolean doBatchAdd(List<DepositSet> list) throws ManagerException {
		try {
			if(list == null || list.size() < 1){
				return false;
			}
			initDefaultInfo(list);//存现设置信息初始化
			
			depositSetManager.addListDepositSet(list);
		} catch (ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
		return true;
	}
	
	private void initDefaultInfo(List<DepositSet> list) {
		for(DepositSet depositSet : list){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("shopNo", depositSet.getShopNo());
			Shop shop = shopManager.initSubsiInfo(params);
			
			if(shop!=null){
				depositSet.setShopName(shop.getShortName());
			}
			
		}
		
	}
}