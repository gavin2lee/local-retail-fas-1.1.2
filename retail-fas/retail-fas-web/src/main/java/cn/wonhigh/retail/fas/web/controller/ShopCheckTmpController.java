package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.SelfShopDepositAccount;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.ShopCheck;
import cn.wonhigh.retail.fas.common.model.ShopCheckSet;
import cn.wonhigh.retail.fas.common.model.ShopCheckTmp;
import cn.wonhigh.retail.fas.common.model.UploadMessageModel;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.manager.ShopCheckManager;
import cn.wonhigh.retail.fas.manager.ShopCheckSetManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

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
@RequestMapping("/shop_check_tmp")
@ModuleVerify("30170026")
public class ShopCheckTmpController extends ExcelImportController<ShopCheckTmp> {
    @Resource
    private ShopCheckManager shopCheckManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("IndepShop_management/shop_check/",shopCheckManager);
    }
    
    @Override
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
    	Map<String, Object> map = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = StringUtils.isEmpty(req.getParameter("sort")) ? "" : String.valueOf(req
				.getParameter("sort"));
		String sortOrder = StringUtils.isEmpty(req.getParameter("order")) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		String organNo = params.get("organNo") == null ? null : params.get("organNo").toString();
		params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		params.put("organNoLists", FasUtil.formatInQueryCondition(organNo));
		
		//根据组合条件（公司、店铺、店铺小类、品牌、管理城市）求交集获取店铺列表
    	List<String> shopList = shopCheckManager.findShops(params);
    	if(shopList.size()>0){
    		params.put("shopNoList", shopList);
    		params.put("shopNo", null);
    	}
    	
		int total = shopCheckManager.findShopCheckByPageCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		if(total<1){
			map.put("rows", new ArrayList<ShopCheck>());
			map.put("total", 0);
			return map;
		}
    	List<ShopCheck> list = shopCheckManager.findShopCheckByPage(page, sortColumn, sortOrder, params);
    	List<ShopCheckTmp> tmpList = new ArrayList<ShopCheckTmp>();
    	for(ShopCheck shopCheck:list){
    		Map<String, Object> maps = new HashMap<String, Object>();
    		maps.put("shopNo", shopCheck.getShopNo());
    		maps.put("year", shopCheck.getYear());
    		maps.put("month", shopCheck.getMonth());
    		ShopCheckTmp tmp = new ShopCheckTmp();
    		tmp.setShopNo(shopCheck.getShopNo());
			tmp.setShopName(shopCheck.getShopName());
			tmp.setYear(shopCheck.getYear());
			tmp.setMonth(shopCheck.getMonth());
    		List<ShopCheckSet> checkNoList = shopCheckManager.findShopCheckNos(params);//检查编号列表
    		Date date = new Date(0);
     		for(int i=0;i<checkNoList.size();i++){
    			maps.put("checkNo", checkNoList.get(i).getCheckNo());
    			ShopCheck sc = shopCheckManager.findShopCheck(maps);
				if(null!=sc){
					if(null != sc.getCreateUser() && null != sc.getCreateTime()){
						tmp.setCreateUser(sc.getCreateUser());
						tmp.setCreateTime(sc.getCreateTime());
					}
	    			if(null != sc.getUpdateTime() && date.before(sc.getUpdateTime())){
	    				date = sc.getUpdateTime();
	    				tmp.setUpdateUser(sc.getUpdateUser());
	    				tmp.setUpdateTime(sc.getUpdateTime());
	    			}
					tmp = this.initCheck(tmp,i,sc);
				}
    		}
    		tmpList.add(tmp);
    	}
    	map.put("rows", tmpList);
		map.put("total", total);
		return map;
    }
    
    @RequestMapping("/get_check")
    @ResponseBody
    public List<ShopCheckSet> getCheck(HttpServletRequest req){
    	String companyNo = req.getParameter("companyNo");
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("companyNo", companyNo);
    	List<ShopCheckSet> list = shopCheckManager.findShopCheckNos(params);
    	Map<String, Object> obj = new HashMap<String, Object>();
    	obj.put("list", list);
    	return list;
    }
    
    @RequestMapping({ "/do_save" })
    public ResponseEntity<Map<String, Boolean>> do_save(HttpServletRequest req, Model model) throws Exception {
    	int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
    	Map<String, Boolean> flag = new HashMap<String, Boolean>();
    	Map<CommonOperatorEnum, List<ShopCheckTmp>> params = this.convertToMap(req);
		List<ShopCheckTmp> shopCheckTmpList = new ArrayList<ShopCheckTmp>();

		for (Entry<CommonOperatorEnum, List<ShopCheckTmp>> param : params.entrySet()) {
			if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
				shopCheckTmpList = params.get(CommonOperatorEnum.UPDATED);
			}
		}
		for(ShopCheckTmp shopCheckTmp:shopCheckTmpList){
			ShopCheck shopCheck = new ShopCheck();
			shopCheck.setShopNo(shopCheckTmp.getShopNo());
			shopCheck.setShopName(shopCheckTmp.getShopName());
			shopCheck.setYear(shopCheckTmp.getYear());
			shopCheck.setMonth(shopCheckTmp.getMonth());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopNoLists", FasUtil.formatInQueryCondition(shopCheckTmp.getShopNo()));
			List<ShopCheckSet> checkNoList = shopCheckManager.findShopCheckNos(map);//检查编号列表
			for(int i=0;i<checkNoList.size();i++){
				shopCheck.setCheckNo(checkNoList.get(i).getCheckNo());
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("shopNo", shopCheck.getShopNo());
				m.put("companyNo", params.get("companyNo"));
				m.put("checkNo", shopCheck.getCheckNo());
				SimplePage page = new SimplePage(pageNo, pageSize, 1);
				shopCheck = this.getShopCheck(shopCheck,i,shopCheckTmp);
				List<ShopCheck> entity = shopCheckManager.findShopCheckByPage(page, null, null, m);
				if(entity.size()>0){//修改
					shopCheckManager.updateData(shopCheck);
				}else{//新增
					shopCheck.setId(UUIDGenerator.getUUID());
					shopCheckManager.add(shopCheck);
				}
			}
		}

		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
    }
    
    @Override
    protected List<ShopCheckTmp> queryExportData(Map<String, Object> params) throws ManagerException {
		String sortColumn = params.get("sort") == null ? "" : String.valueOf(params.get("sort"));
		String sortOrder = params.get("order") == null ? "" : String.valueOf(params.get("order"));
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		String organNo = params.get("organNo") == null ? null : params.get("organNo").toString();
		params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
		params.put("organNoLists", FasUtil.formatInQueryCondition(organNo));
		
		//根据组合条件（公司、店铺、店铺小类、品牌、管理城市）求交集获取店铺列表
    	List<String> shopList = shopCheckManager.findShops(params);
    	params.put("shopNoList", shopList);
		int total = shopCheckManager.findShopCheckByPageCount(params);
		SimplePage page = new SimplePage(1, total, total);
    	List<ShopCheck> list = shopCheckManager.findShopCheckByPage(page, sortColumn, sortOrder, params);
    	List<ShopCheckTmp> tmpList = new ArrayList<ShopCheckTmp>();
    	for(ShopCheck shopCheck:list){
    		Map<String, Object> maps = new HashMap<String, Object>();
    		maps.put("shopNo", shopCheck.getShopNo());
    		maps.put("year", shopCheck.getYear());
    		maps.put("month", shopCheck.getMonth());
    		ShopCheckTmp tmp = new ShopCheckTmp();
    		tmp.setShopNo(shopCheck.getShopNo());
			tmp.setShopName(shopCheck.getShopName());
			tmp.setYear(shopCheck.getYear());
			tmp.setMonth(shopCheck.getMonth());
			Date date = new Date(0);
    		List<ShopCheckSet> checkNoList = shopCheckManager.findShopCheckNos(params);//检查编号列表
    		for(int i=0;i<checkNoList.size();i++){
    			maps.put("checkNo", checkNoList.get(i).getCheckNo());
    			ShopCheck sc = shopCheckManager.findShopCheck(maps);
				if(null!=sc){
					if(null != sc.getCreateUser() && null != sc.getCreateTime()){
						tmp.setCreateUser(sc.getCreateUser());
						tmp.setCreateTime(sc.getCreateTime());
					}
	    			if(null != sc.getUpdateTime() && date.before(sc.getUpdateTime())){
	    				date = sc.getUpdateTime();
	    				tmp.setUpdateUser(sc.getUpdateUser());
	    				tmp.setUpdateTime(sc.getUpdateTime());
	    			}
					tmp = this.initCheck(tmp,i,sc);
				}
    		}
    		tmpList.add(tmp);
    	}
    	return tmpList;
    }

    private ShopCheck getShopCheck(ShopCheck shopCheck, int i, ShopCheckTmp shopCheckTmp) {
    	switch (i) {
		case 0:
			shopCheck.setCheckResult(shopCheckTmp.getCheck0());break;
		case 1:
			shopCheck.setCheckResult(shopCheckTmp.getCheck1());break;
		case 2:
			shopCheck.setCheckResult(shopCheckTmp.getCheck2());break;
		case 3:
			shopCheck.setCheckResult(shopCheckTmp.getCheck3());break;
		case 4:
			shopCheck.setCheckResult(shopCheckTmp.getCheck4());break;
		case 5:
			shopCheck.setCheckResult(shopCheckTmp.getCheck5());break;
		case 6:
			shopCheck.setCheckResult(shopCheckTmp.getCheck6());break;
		case 7:
			shopCheck.setCheckResult(shopCheckTmp.getCheck7());break;
		case 8:
			shopCheck.setCheckResult(shopCheckTmp.getCheck8());break;
		case 9:
			shopCheck.setCheckResult(shopCheckTmp.getCheck9());break;
		case 10:
			shopCheck.setCheckResult(shopCheckTmp.getCheck10());break;
		case 11:
			shopCheck.setCheckResult(shopCheckTmp.getCheck11());break;
		case 12:
			shopCheck.setCheckResult(shopCheckTmp.getCheck12());break;
		case 13:
			shopCheck.setCheckResult(shopCheckTmp.getCheck13());break;
		case 14:
			shopCheck.setCheckResult(shopCheckTmp.getCheck14());break;
		case 15:
			shopCheck.setCheckResult(shopCheckTmp.getCheck15());break;
		case 16:
			shopCheck.setCheckResult(shopCheckTmp.getCheck16());break;
		case 17:
			shopCheck.setCheckResult(shopCheckTmp.getCheck17());break;
		case 18:
			shopCheck.setCheckResult(shopCheckTmp.getCheck18());break;
		case 19:
			shopCheck.setCheckResult(shopCheckTmp.getCheck19());break;
		case 20:
			shopCheck.setCheckResult(shopCheckTmp.getCheck20());break;
		case 21:
			shopCheck.setCheckResult(shopCheckTmp.getCheck21());break;
		case 22:
			shopCheck.setCheckResult(shopCheckTmp.getCheck22());break;
		case 23:
			shopCheck.setCheckResult(shopCheckTmp.getCheck23());break;
		case 24:
			shopCheck.setCheckResult(shopCheckTmp.getCheck24());break;
		case 25:
			shopCheck.setCheckResult(shopCheckTmp.getCheck25());break;
		case 26:
			shopCheck.setCheckResult(shopCheckTmp.getCheck26());break;
		case 27:
			shopCheck.setCheckResult(shopCheckTmp.getCheck27());break;
		case 28:
			shopCheck.setCheckResult(shopCheckTmp.getCheck28());break;
		case 29:
			shopCheck.setCheckResult(shopCheckTmp.getCheck29());break;
		case 30:
			shopCheck.setCheckResult(shopCheckTmp.getCheck30());break;
		case 31:
			shopCheck.setCheckResult(shopCheckTmp.getCheck31());break;
		case 32:
			shopCheck.setCheckResult(shopCheckTmp.getCheck32());break;
		case 33:
			shopCheck.setCheckResult(shopCheckTmp.getCheck33());break;
		case 34:
			shopCheck.setCheckResult(shopCheckTmp.getCheck34());break;
		case 35:
			shopCheck.setCheckResult(shopCheckTmp.getCheck35());break;
		case 36:
			shopCheck.setCheckResult(shopCheckTmp.getCheck36());break;
		case 37:
			shopCheck.setCheckResult(shopCheckTmp.getCheck37());break;
		case 38:
			shopCheck.setCheckResult(shopCheckTmp.getCheck38());break;
		case 39:
			shopCheck.setCheckResult(shopCheckTmp.getCheck39());break;
		case 40:
			shopCheck.setCheckResult(shopCheckTmp.getCheck40());break;
		case 41:
			shopCheck.setCheckResult(shopCheckTmp.getCheck41());break;
		case 42:
			shopCheck.setCheckResult(shopCheckTmp.getCheck42());break;
		case 43:
			shopCheck.setCheckResult(shopCheckTmp.getCheck43());break;
		case 44:
			shopCheck.setCheckResult(shopCheckTmp.getCheck44());break;
		case 45:
			shopCheck.setCheckResult(shopCheckTmp.getCheck45());break;
		case 46:
			shopCheck.setCheckResult(shopCheckTmp.getCheck46());break;
		case 47:
			shopCheck.setCheckResult(shopCheckTmp.getCheck47());break;
		case 48:
			shopCheck.setCheckResult(shopCheckTmp.getCheck48());break;
		case 49:
			shopCheck.setCheckResult(shopCheckTmp.getCheck49());break;
		case 50:
			shopCheck.setCheckResult(shopCheckTmp.getCheck50());break;
		case 51:
			shopCheck.setCheckResult(shopCheckTmp.getCheck51());break;
		case 52:
			shopCheck.setCheckResult(shopCheckTmp.getCheck52());break;
		case 53:
			shopCheck.setCheckResult(shopCheckTmp.getCheck53());break;
		case 54:
			shopCheck.setCheckResult(shopCheckTmp.getCheck54());break;
		case 55:
			shopCheck.setCheckResult(shopCheckTmp.getCheck55());break;
		case 56:
			shopCheck.setCheckResult(shopCheckTmp.getCheck56());break;
		case 57:
			shopCheck.setCheckResult(shopCheckTmp.getCheck57());break;
		case 58:
			shopCheck.setCheckResult(shopCheckTmp.getCheck58());break;
		case 59:
			shopCheck.setCheckResult(shopCheckTmp.getCheck59());break;
		case 60:
			shopCheck.setCheckResult(shopCheckTmp.getCheck60());break;
		case 61:
			shopCheck.setCheckResult(shopCheckTmp.getCheck61());break;
		case 62:
			shopCheck.setCheckResult(shopCheckTmp.getCheck62());break;
		case 63:
			shopCheck.setCheckResult(shopCheckTmp.getCheck63());break;
		case 64:
			shopCheck.setCheckResult(shopCheckTmp.getCheck64());break;
		case 65:
			shopCheck.setCheckResult(shopCheckTmp.getCheck65());break;
		case 66:
			shopCheck.setCheckResult(shopCheckTmp.getCheck66());break;
		case 67:
			shopCheck.setCheckResult(shopCheckTmp.getCheck67());break;
		case 68:
			shopCheck.setCheckResult(shopCheckTmp.getCheck68());break;
		case 69:
			shopCheck.setCheckResult(shopCheckTmp.getCheck69());break;
		case 70:
			shopCheck.setCheckResult(shopCheckTmp.getCheck70());break;
		case 71:
			shopCheck.setCheckResult(shopCheckTmp.getCheck71());break;
		case 72:
			shopCheck.setCheckResult(shopCheckTmp.getCheck72());break;
		case 73:
			shopCheck.setCheckResult(shopCheckTmp.getCheck73());break;
		case 74:
			shopCheck.setCheckResult(shopCheckTmp.getCheck74());break;
		case 75:
			shopCheck.setCheckResult(shopCheckTmp.getCheck75());break;
		case 76:
			shopCheck.setCheckResult(shopCheckTmp.getCheck76());break;
		case 77:
			shopCheck.setCheckResult(shopCheckTmp.getCheck77());break;
		case 78:
			shopCheck.setCheckResult(shopCheckTmp.getCheck78());break;
		case 79:
			shopCheck.setCheckResult(shopCheckTmp.getCheck79());break;
		case 80:
			shopCheck.setCheckResult(shopCheckTmp.getCheck80());break;
		case 81:
			shopCheck.setCheckResult(shopCheckTmp.getCheck81());break;
		case 82:
			shopCheck.setCheckResult(shopCheckTmp.getCheck82());break;
		case 83:
			shopCheck.setCheckResult(shopCheckTmp.getCheck83());break;
		case 84:
			shopCheck.setCheckResult(shopCheckTmp.getCheck84());break;
		case 85:
			shopCheck.setCheckResult(shopCheckTmp.getCheck85());break;
		case 86:
			shopCheck.setCheckResult(shopCheckTmp.getCheck86());break;
		case 87:
			shopCheck.setCheckResult(shopCheckTmp.getCheck87());break;
		case 88:
			shopCheck.setCheckResult(shopCheckTmp.getCheck88());break;
		case 89:
			shopCheck.setCheckResult(shopCheckTmp.getCheck89());break;
		case 90:
			shopCheck.setCheckResult(shopCheckTmp.getCheck90());break;
		case 91:
			shopCheck.setCheckResult(shopCheckTmp.getCheck91());break;
		case 92:
			shopCheck.setCheckResult(shopCheckTmp.getCheck92());break;
		case 93:
			shopCheck.setCheckResult(shopCheckTmp.getCheck93());break;
		case 94:
			shopCheck.setCheckResult(shopCheckTmp.getCheck94());break;
		case 95:
			shopCheck.setCheckResult(shopCheckTmp.getCheck95());break;
		case 96:
			shopCheck.setCheckResult(shopCheckTmp.getCheck96());break;
		case 97:
			shopCheck.setCheckResult(shopCheckTmp.getCheck97());break;
		case 98:
			shopCheck.setCheckResult(shopCheckTmp.getCheck98());break;
		case 99:
			shopCheck.setCheckResult(shopCheckTmp.getCheck99());break;
		default:
			break;
		}
		return shopCheck;
	}

	private ShopCheckTmp initCheck(ShopCheckTmp tmp,int i,ShopCheck sc) {
		switch (i) {
		case 0:
			tmp.setCheck0(sc.getCheckResult());break;
		case 1:
			tmp.setCheck1(sc.getCheckResult());break;	
		case 2:
			tmp.setCheck2(sc.getCheckResult());break;
		case 3:
			tmp.setCheck3(sc.getCheckResult());break;
		case 4:
			tmp.setCheck4(sc.getCheckResult());break;
		case 5:
			tmp.setCheck5(sc.getCheckResult());break;
		case 6:
			tmp.setCheck6(sc.getCheckResult());break;
		case 7:
			tmp.setCheck7(sc.getCheckResult());break;
		case 8:
			tmp.setCheck8(sc.getCheckResult());break;
		case 9:
			tmp.setCheck9(sc.getCheckResult());break;
		case 10:
			tmp.setCheck10(sc.getCheckResult());break;
		case 11:
			tmp.setCheck11(sc.getCheckResult());break;
		case 12:
			tmp.setCheck12(sc.getCheckResult());break;
		case 13:
			tmp.setCheck13(sc.getCheckResult());break;
		case 14:
			tmp.setCheck14(sc.getCheckResult());break;
		case 15:
			tmp.setCheck15(sc.getCheckResult());break;
		case 16:
			tmp.setCheck16(sc.getCheckResult());break;
		case 17:
			tmp.setCheck17(sc.getCheckResult());break;
		case 18:
			tmp.setCheck18(sc.getCheckResult());break;
		case 19:
			tmp.setCheck19(sc.getCheckResult());break;
		case 20:
			tmp.setCheck20(sc.getCheckResult());break;
		case 21:
			tmp.setCheck21(sc.getCheckResult());break;
		case 22:
			tmp.setCheck22(sc.getCheckResult());break;
		case 23:
			tmp.setCheck23(sc.getCheckResult());break;
		case 24:
			tmp.setCheck24(sc.getCheckResult());break;
		case 25:
			tmp.setCheck25(sc.getCheckResult());break;
		case 26:
			tmp.setCheck26(sc.getCheckResult());break;
		case 27:
			tmp.setCheck27(sc.getCheckResult());break;
		case 28:
			tmp.setCheck28(sc.getCheckResult());break;
		case 29:
			tmp.setCheck29(sc.getCheckResult());break;
		case 30:
			tmp.setCheck30(sc.getCheckResult());break;
		case 31:
			tmp.setCheck31(sc.getCheckResult());break;
		case 32:
			tmp.setCheck32(sc.getCheckResult());break;
		case 33:
			tmp.setCheck33(sc.getCheckResult());break;
		case 34:
			tmp.setCheck34(sc.getCheckResult());break;
		case 35:
			tmp.setCheck35(sc.getCheckResult());break;
		case 36:
			tmp.setCheck36(sc.getCheckResult());break;
		case 37:
			tmp.setCheck37(sc.getCheckResult());break;
		case 38:
			tmp.setCheck38(sc.getCheckResult());break;
		case 39:
			tmp.setCheck39(sc.getCheckResult());break;
		case 40:
			tmp.setCheck40(sc.getCheckResult());break;
		case 41:
			tmp.setCheck41(sc.getCheckResult());break;
		case 42:
			tmp.setCheck42(sc.getCheckResult());break;
		case 43:
			tmp.setCheck43(sc.getCheckResult());break;
		case 44:
			tmp.setCheck44(sc.getCheckResult());break;
		case 45:
			tmp.setCheck45(sc.getCheckResult());break;
		case 46:
			tmp.setCheck46(sc.getCheckResult());break;
		case 47:
			tmp.setCheck47(sc.getCheckResult());break;
		case 48:
			tmp.setCheck48(sc.getCheckResult());break;
		case 49:
			tmp.setCheck49(sc.getCheckResult());break;
		case 50:
			tmp.setCheck50(sc.getCheckResult());break;
		case 51:
			tmp.setCheck51(sc.getCheckResult());break;
		case 52:
			tmp.setCheck52(sc.getCheckResult());break;
		case 53:
			tmp.setCheck53(sc.getCheckResult());break;
		case 54:
			tmp.setCheck54(sc.getCheckResult());break;
		case 55:
			tmp.setCheck55(sc.getCheckResult());break;
		case 56:
			tmp.setCheck56(sc.getCheckResult());break;
		case 57:
			tmp.setCheck57(sc.getCheckResult());break;
		case 58:
			tmp.setCheck58(sc.getCheckResult());break;
		case 59:
			tmp.setCheck59(sc.getCheckResult());break;
		case 60:
			tmp.setCheck60(sc.getCheckResult());break;
		case 61:
			tmp.setCheck61(sc.getCheckResult());break;
		case 62:
			tmp.setCheck62(sc.getCheckResult());break;
		case 63:
			tmp.setCheck63(sc.getCheckResult());break;
		case 64:
			tmp.setCheck64(sc.getCheckResult());break;
		case 65:
			tmp.setCheck65(sc.getCheckResult());break;
		case 66:
			tmp.setCheck66(sc.getCheckResult());break;
		case 67:
			tmp.setCheck67(sc.getCheckResult());break;
		case 68:
			tmp.setCheck68(sc.getCheckResult());break;
		case 69:
			tmp.setCheck69(sc.getCheckResult());break;
		case 70:
			tmp.setCheck70(sc.getCheckResult());break;
		case 71:
			tmp.setCheck71(sc.getCheckResult());break;
		case 72:
			tmp.setCheck72(sc.getCheckResult());break;
		case 73:
			tmp.setCheck73(sc.getCheckResult());break;
		case 74:
			tmp.setCheck74(sc.getCheckResult());break;
		case 75:
			tmp.setCheck75(sc.getCheckResult());break;
		case 76:
			tmp.setCheck76(sc.getCheckResult());break;
		case 77:
			tmp.setCheck77(sc.getCheckResult());break;
		case 78:
			tmp.setCheck78(sc.getCheckResult());break;
		case 79:
			tmp.setCheck79(sc.getCheckResult());break;
		case 80:
			tmp.setCheck80(sc.getCheckResult());break;
		case 81:
			tmp.setCheck81(sc.getCheckResult());break;
		case 82:
			tmp.setCheck82(sc.getCheckResult());break;
		case 83:
			tmp.setCheck83(sc.getCheckResult());break;
		case 84:
			tmp.setCheck84(sc.getCheckResult());break;
		case 85:
			tmp.setCheck85(sc.getCheckResult());break;
		case 86:
			tmp.setCheck86(sc.getCheckResult());break;
		case 87:
			tmp.setCheck87(sc.getCheckResult());break;
		case 88:
			tmp.setCheck88(sc.getCheckResult());break;
		case 89:
			tmp.setCheck89(sc.getCheckResult());break;
		case 90:
			tmp.setCheck90(sc.getCheckResult());break;
		case 91:
			tmp.setCheck91(sc.getCheckResult());break;
		case 92:
			tmp.setCheck92(sc.getCheckResult());break;
		case 93:
			tmp.setCheck93(sc.getCheckResult());break;
		case 94:
			tmp.setCheck94(sc.getCheckResult());break;
		case 95:
			tmp.setCheck95(sc.getCheckResult());break;
		case 96:
			tmp.setCheck96(sc.getCheckResult());break;
		case 97:
			tmp.setCheck97(sc.getCheckResult());break;
		case 98:
			tmp.setCheck98(sc.getCheckResult());break;
		case 99:
			tmp.setCheck99(sc.getCheckResult());break;
		default:
			break;
		}
		return tmp;
	}

	@Override
	protected String[] getImportProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<ShopCheckTmp> list) {
		// TODO Auto-generated method stub
		return false;
	}
}