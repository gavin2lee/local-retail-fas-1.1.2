package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BillShopBalanceSet;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.BillShopBalanceSetManager;
import cn.wonhigh.retail.fas.manager.ShopManager;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.fas.web.utils.JsonUtil;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 门店结算差异生成方式配置表
 * @author yang.y
 * @date  2015-01-05 14:11:37
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/bill_shop_balance_set")
@ModuleVerify("30140016")
public class BillShopBalanceSetController extends ExcelImportController<BillShopBalanceSet> {
    
	@Resource
    private BillShopBalanceSetManager billShopBalanceSetManager;
	
	@Resource
    private ShopService shopService;
	
	@Resource
    private ShopManager shopManager;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BillShopBalanceSetController.class);

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_shop_balance_set/",billShopBalanceSetManager);
    }
    
    /**
   	 * 新增/修改
   	 * @param request HttpServletRequest
   	 * @return Map<String, Boolean>
     * @throws ManagerException 
   	 */
   	@RequestMapping(value = "/save_all")
   	@ResponseBody
   	public Map<String, Object> saveAll(HttpServletRequest request) throws ManagerException {
   		Map<String, Object> map = new HashMap<String, Object>();
   		try {
   			JsonUtil<BillShopBalanceSet> util = new JsonUtil<BillShopBalanceSet>();
   			Map<CommonOperatorEnum, List<BillShopBalanceSet>> dataMap = util.convertToMap(request, 
   					BillShopBalanceSet.class);
   			// 校验数据合法性
   			map = this.validateData(dataMap);
   			if(map != null && map.size() > 0) {
   				return map;
   			}
   			billShopBalanceSetManager.saveAll(dataMap);
   			map.put("success", true);
   		} catch(Exception e) {
   			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
   		}
   		return map;
   	}
   	
   	
   	//Excel导入功能
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)throws Exception{
    	ModelAndView mv = new ModelAndView("/print/import");
    	
    	List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), BillShopBalanceSet.class, request);
    	StringBuilder errorBuilder = new StringBuilder();
    	if(msgList != null && msgList.size() > 0){
	    	for(UploadMessageModel message : msgList){
	    		errorBuilder.append(message.getMessage() + "<br/>");
	    	}
	    	mv.addObject("error", errorBuilder);
    	}else{
    		mv.addObject("success","成功导入");
    	}
    	return mv;
	}
	
	// 批量添加
	@Override
	protected boolean doBatchAdd(List<BillShopBalanceSet> list) throws ManagerException {
		try {
			Map<CommonOperatorEnum, List<BillShopBalanceSet>> dataMap = new HashMap<CommonOperatorEnum, List<BillShopBalanceSet>>();
			if( list != null && list.size() > 0) {
				initializationInfo(list);
				dataMap.put(CommonOperatorEnum.INSERTED, list);
				billShopBalanceSetManager.saveAll(dataMap);
			}else {
				return false;
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
   		}	
		return true;
	}
	
	//Excel导入模块中对应的字段
	@Override
	protected String[] getImportProperties() {
		return new String[]{"shopNo","balanceDiffType"};
	}
	
	//封装集合数据项
	public void initializationInfo(List<BillShopBalanceSet> billShopBalanceSetList) throws ManagerException {
		StringBuffer sbf = new StringBuffer(" AND shop_no in  ( ");
		List<String> shopNoList = new ArrayList<String>();
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			for( BillShopBalanceSet bss : billShopBalanceSetList){
				if(!shopNoList.contains(bss.getShopNo())) {
					shopNoList.add(bss.getShopNo());
				}
			}
			for( int i = 0; i < shopNoList.size();i++) {
				if( i == (shopNoList.size() - 1)) {
					sbf.append("'").append(shopNoList.get(i)).append("'");
				}else {
					sbf.append("'").append(shopNoList.get(i)).append("'").append(",");
				}
			}
			sbf.append(" ) ");
			params.put("queryCondition", sbf.toString());
			List<Shop> shopList = shopService.selectShopInfoListByShopNos(params);
			if( shopList != null){	
				for( BillShopBalanceSet  bss : billShopBalanceSetList){
					for( Shop shop : shopList){						
						if( bss.getShopNo().equals(shop.getShopNo())) {
							bss.setShopNo(shop.getShopNo());
							bss.setShortName(shop.getShortName());
							bss.setFullName(shop.getFullName());
							bss.setCompanyNo(shop.getCompanyNo());
							bss.setCompanyName(shop.getCompanyName());
							bss.setRemark(shop.getRemark());
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	//校验导入数据有效性
	@Override
	protected UploadMessageModel validateModel(BillShopBalanceSet billShopBalanceSet, int rowIndex) {
		UploadMessageModel uploadMessageModel = null;
		try{
			if(billShopBalanceSet != null) {
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("shopNo", billShopBalanceSet.getShopNo());
				int count = shopManager.findCount(params);
				if(count < 1) {
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 店铺编号有误或无权限操作");
					return uploadMessageModel;
				}
				int type = billShopBalanceSet.getBalanceDiffType();
				if (type != 1 && type != 2 && type != 3 && type != 4) {
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 结算差异方式无效");
					return uploadMessageModel;
				}
				List<BillShopBalanceSet> list = billShopBalanceSetManager.selectByParams(params);	
				if(list != null && list.size() > 0) {
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 店铺已存在");
					return uploadMessageModel;
				}
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			LOGGER.error(" validate the model values : "+billShopBalanceSet.toString() + " , index : "+(rowIndex+1));
			uploadMessageModel = getErrorMessageObject(" validation ShopBalanceDate failed , "+billShopBalanceSet.toString());
			return uploadMessageModel;
		}
		return uploadMessageModel;
	}
	
	//验证是否存在重复数据
	@Override
	public boolean validateRepeart(BillShopBalanceSet billShopBalanceSet, List<BillShopBalanceSet> list) {
		if(billShopBalanceSet == null || (list == null || list.size() == 0)) {
			return false;
		}
		for (BillShopBalanceSet bss : list) {
			if(billShopBalanceSet.getShopNo().equals(bss.getShopNo())) {
				return true;
			}
		}
		return false;
	}
	
	//封装校验提示信息
	public UploadMessageModel getErrorMessageObject(String message){
		UploadMessageModel uploadMessageModel = new UploadMessageModel();
		uploadMessageModel.setFlag(false);
		uploadMessageModel.setMessage(message);
		return uploadMessageModel;
	}
	
	// 对象设置默认值
	protected BillShopBalanceSet setDefaulValues(BillShopBalanceSet billShopBalanceSet, HttpServletRequest request) throws Exception {
		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		if(StringUtils.isEmpty(billShopBalanceSet.getId())) {
			billShopBalanceSet.setId(null);
			billShopBalanceSet.setCreateUser(loginUser.getUsername());
			billShopBalanceSet.setCreateTime(DateUtil.getCurrentDateTime());
			billShopBalanceSet.setUpdateTime(DateUtil.getCurrentDateTime());
		} else {
			billShopBalanceSet.setUpdateUser(loginUser.getUsername());
			billShopBalanceSet.setUpdateTime(DateUtil.getCurrentDateTime());
		}
		return billShopBalanceSet;
	}
	
   	
	
   	/**
   	 * 校验数据合法性
   	 * @param datas 待校验的数据集合
   	 * @return Map<String, Object>
   	 * @throws ManagerException 异常
   	 */
   	private Map<String, Object> validateData(Map<CommonOperatorEnum, List<BillShopBalanceSet>> datas) 
   		throws ManagerException {
   		Map<String, Object> result = new HashMap<String, Object>();
   		if(datas != null && datas.size() > 0) {
   			List<BillShopBalanceSet> lstDel = datas.get(CommonOperatorEnum.DELETED);
			List<BillShopBalanceSet> lstUpdate = datas.get(CommonOperatorEnum.UPDATED);
			if(null != lstUpdate && lstUpdate.size() > 0) {
				for(BillShopBalanceSet model : lstUpdate) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("shopNo", model.getShopNo());
					List<BillShopBalanceSet> lstResult = billShopBalanceSetManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0
							&& !lstResult.get(0).getId().equals(model.getId())
							&& !this.checkDelListCotainItem(lstDel, model)
							&& !this.checkUpdateListCotainItem(lstUpdate, model, lstResult.get(0))) {
						result.put("success", false);
						result.put("message", "店铺["+model.getShopNo()+"]已存在，请重新操作！");
						return result;
					}
				}
			}
			List<BillShopBalanceSet> lstInsert = datas.get(CommonOperatorEnum.INSERTED);
			if(null != lstInsert && lstInsert.size() > 0) {
				for(BillShopBalanceSet model : lstInsert) {
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("shopNo", model.getShopNo());
					List<BillShopBalanceSet> lstResult = billShopBalanceSetManager.findByBiz(null, queryParams);
					if(lstResult != null && lstResult.size() > 0 
							&& !this.checkDelListCotainItem(lstDel, model)
							&& !this.checkUpdateListCotainItem(lstUpdate, model, lstResult.get(0))) {
						result.put("success", false);
						result.put("message", "店铺["+model.getShopNo()+"]已存在，请重新操作！");
						return result;
					}
				}
			}
		}
   		return result;
   	}
   	
    @Override
    public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	// TODO Auto-generated method stub
    	
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		List<String> shopNoList = new ArrayList<String>();
		if(shopNo != null && !"".equals(shopNo)){
			if(shopNo.contains(",")){
				String[] shopNos = shopNo.split(",");
				for (String shopNoTemp : shopNos) {
					shopNoList.add(shopNoTemp);
				}
			}else{
				shopNoList.add(shopNo);
			}
			params.put("shopNos",shopNoList );
			params.put("shopNo", null);
		}else{
			params.put("shopNos",null);
		}
		
		int total = this.billShopBalanceSetManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, (int) total);
		List<ShopBalanceDate> list = this.billShopBalanceSetManager.findByPage(page, sortColumn, sortOrder, params);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
    }
    
    
   	/**
   	 * 判断item是否在集合中
   	 * @param list 集合数据
   	 * @param item 单个数据
   	 * @return boolean
   	 */
   	private boolean checkDelListCotainItem(List<BillShopBalanceSet> list, BillShopBalanceSet item) {
   		if(list == null || list.size() == 0) {
   			return false;
   		}
   		for(BillShopBalanceSet model : list) {
   			if(model.getShopNo().equals(item.getShopNo())) {
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
   	private boolean checkUpdateListCotainItem(List<BillShopBalanceSet> list, BillShopBalanceSet item,
   			BillShopBalanceSet queryItem) {
   		if(list == null || list.size() == 0) {
   			return false;
   		}
   		for(BillShopBalanceSet model : list) {
   			if(queryItem.getId().equals(model.getId()) 
   					&& queryItem.getShopNo().equals(model.getShopNo())) {
   				return false;
   			}
   			if(queryItem.getId().equals(model.getId()) 
   					&& !queryItem.getShopNo().equals(model.getShopNo())) {
   				return true;
   			}
   		}
   		return false;
   	}
}