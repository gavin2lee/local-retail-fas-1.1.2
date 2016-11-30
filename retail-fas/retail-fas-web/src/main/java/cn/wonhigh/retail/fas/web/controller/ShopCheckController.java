package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopCheck;
import cn.wonhigh.retail.fas.common.model.ShopCheckTmp;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.manager.ShopCheckManager;
import cn.wonhigh.retail.fas.manager.ShopCheckSetManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController.CrudInfo;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-09-22 14:01:10
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
@RequestMapping("/shop_check")
@ModuleVerify("30170026")
public class ShopCheckController extends ExcelImportController<ShopCheck> {
	@Resource
    private ShopCheckManager shopCheckManager;
	@Resource
	private ShopManager shopManager;
	@Resource
	private ShopCheckSetManager shopCheckSetManager;
	
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShopCheck.class);

	@Override
	protected CrudInfo init() {
		return new CrudInfo("IndepShop_management/shop_check/",shopCheckManager);
	}
	
	@RequestMapping(value = "/do_import")
	@ResponseBody
	public ModelAndView doImport(@RequestParam("fileData") MultipartFile file, HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");

		List<UploadMessageModel> msgList = this.doUpload(file.getInputStream(), ShopCheck.class, request);
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
	protected UploadMessageModel validateModel(ShopCheck shopCheck, int rowIndex) {
		UploadMessageModel uploadMessageModel = null;
		try {
			if(null!=shopCheck){
				//判断店铺是否存在
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("shopNo", shopCheck.getShopNo());
				Shop shop = shopManager.initSubsiInfo(params);
				if(shop == null){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 店铺不存在！");
					return uploadMessageModel;
				}
				//根据店铺检查项和店铺编码查询是否存在
				params.put("year", shopCheck.getYear());
				params.put("month", shopCheck.getMonth());
				params.put("checkNo", shopCheck.getCheckNo());
				ShopCheck sc = shopCheckManager.findShopCheck(params);
				if(null!=sc){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 "+shopCheck.getYear()+"年"+shopCheck.getMonth()+"月"+shopCheck.getCheckNo()+"检查项检查结果已生成，如需修改请在界面直接操作。");
					return uploadMessageModel;
				}
				List<String> list = shopCheckSetManager.getCheckNo(shopCheck.getShopNo());
				if(!list.contains(shopCheck.getCheckNo())){
					uploadMessageModel = getErrorMessageObject("第"+(rowIndex+1)+"行 检查项编码未启用或未设置，请联系财务主管！");
					return uploadMessageModel;
				}
				return uploadMessageModel;
			}else{
				return super.validateModel(shopCheck, rowIndex);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			LOGGER.error(" validate the model values : "+shopCheck.toString() + " , index : "+(rowIndex+1));
			uploadMessageModel = getErrorMessageObject(" validation ShopCheckTmp failed , "+shopCheck.toString());
			return uploadMessageModel;
		}
	}
	
	public UploadMessageModel getErrorMessageObject(String message){
		UploadMessageModel uploadMessageModel = new UploadMessageModel();
		uploadMessageModel.setFlag(false);
		uploadMessageModel.setMessage(message);
		return uploadMessageModel;
	}
	
	@Override
	protected String[] getImportProperties() {
		return new String[] { "shopNo","year","month","checkNo","checkResult","remark"};
	}
	
	@Override
	protected boolean doBatchAdd(List<ShopCheck> list) throws ManagerException {
		if (list == null || list.size() < 1) {
			return false;
		}
		initDefaultInfo(list);//店铺信息初始化

		shopCheckManager.addShopCheck(list);
		return true;
	}

	private void initDefaultInfo(List<ShopCheck> list) {
		for (ShopCheck shopCheck : list) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", shopCheck.getShopNo());
			Shop shop = shopManager.initSubsiInfo(params);

			shopCheck.setShopName(shop.getShortName());
		}
	}

}
