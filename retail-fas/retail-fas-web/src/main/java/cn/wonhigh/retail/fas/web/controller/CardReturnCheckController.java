package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.wonhigh.retail.fas.common.model.CardReturnCheck;
import cn.wonhigh.retail.fas.common.model.PaySaleCheck;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeMain;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.manager.CardReturnCheckManager;
import cn.wonhigh.retail.fas.manager.ReturnExchangeMainManager;
import cn.wonhigh.retail.fas.manager.ShopManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.web.controller.BaseCrudController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请写出类的用途 
 * @author user
 * @date  2015-08-19 20:33:19
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
@RequestMapping("/card_return_check")
@ModuleVerify("30170023")
public class CardReturnCheckController extends ExcelImportController<CardReturnCheck> {
	@Resource
	private CardReturnCheckManager cardReturnCheckManager;
	@Resource
	private ShopManager shopManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("IndepShop_management/card_return_check/", cardReturnCheckManager);
	}

	@RequestMapping({ "/list.json" })
	@ResponseBody
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = (StringUtils.isEmpty(req.getParameter("page"))) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = (StringUtils.isEmpty(req.getParameter("rows"))) ? 10 : Integer.parseInt(req.getParameter("rows"));
		String sortColumn = (StringUtils.isEmpty(req.getParameter("sort"))) ? "" : String.valueOf(req
				.getParameter("sort"));

		String sortOrder = (StringUtils.isEmpty(req.getParameter("order"))) ? "" : String.valueOf(req
				.getParameter("order"));
		Map<String, Object> params = builderParams(req, model);
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));

		List<Integer> businessTypeList = new ArrayList<Integer>();
		//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		params.put("businessTypeList", businessTypeList);
		
		params.put("isCashRegister", String.valueOf(ShardingUtil.isPE()));//modify by wang.m 添加自收银店铺过滤条件
		//查询总记录对象
		CardReturnCheck cardReturnCheck = cardReturnCheckManager.findCardReturnCheckCount(params);
		List<CardReturnCheck> cardReturnCheckList = new ArrayList<CardReturnCheck>();
		//合计对象
		List<CardReturnCheck> footer = new ArrayList<CardReturnCheck>();
		if (cardReturnCheck != null && cardReturnCheck.getTotal() > 0) {
			SimplePage page = new SimplePage(pageNo, pageSize, cardReturnCheck.getTotal());
			cardReturnCheckList = cardReturnCheckManager.findCardReturnCheckList(page, sortColumn, sortOrder, params);
			cardReturnCheck.setCompanyNo("合计");
			footer.add(cardReturnCheck);
			//按终端号显示小计金额
			cardReturnCheckList = this.cardReturnCheckListByTerminalNumber(cardReturnCheckList);
		}
		obj.put("total", cardReturnCheck.getTotal());
		obj.put("rows", cardReturnCheckList);
		obj.put("footer", footer);
		return obj;
	}

	@RequestMapping({ "/do_save" })
	public ResponseEntity<Map<String, Boolean>> do_save(HttpServletRequest req) throws Exception {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		Map<CommonOperatorEnum, List<CardReturnCheck>> params = this.convertToMap(req);
		List<CardReturnCheck> cardReturnCheckList = new ArrayList<CardReturnCheck>();

		for (Entry<CommonOperatorEnum, List<CardReturnCheck>> param : params.entrySet()) {
			if (param.getKey().equals(CommonOperatorEnum.UPDATED)) {
				cardReturnCheckList = params.get(CommonOperatorEnum.UPDATED);
			}
		}
		if (params.size() > 0) {
			cardReturnCheckManager.updatePoundage(cardReturnCheckList);
		}

		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
	
	@Override
	protected List<CardReturnCheck> queryExportData(Map<String, Object> params) throws ManagerException {
		String shopNo = params.get("shopNo") == null ? null : params.get("shopNo").toString();
		String companyNo = params.get("companyNo") == null ? null : params.get("companyNo").toString();
		String mallNo = params.get("mallNo") == null ? null : params.get("mallNo").toString();
		//页面选择了特定的店铺
		if (StringUtils.isNotEmpty(shopNo)) {
			params.put("shopNoLists", FasUtil.formatInQueryCondition(shopNo));
			params.put("companyNo", null);//公司置空
		}else{
			Map<String, Object> shopParamMap = new HashMap<String, Object>();
			shopParamMap.put("companyNo", companyNo);//根据公司查询店铺
			if(StringUtils.isNotEmpty(mallNo)){
				shopParamMap.put("mallNo", mallNo);//根据商场查询店铺
			}
			List<String> shopNoList = new ArrayList<String>();
			List<Shop> shopList = shopManager.findByBiz(null, shopParamMap);
			if(!CollectionUtils.isEmpty(shopList)){
				for(Shop shop:shopList){
					shopNoList.add(shop.getShopNo());
				}
				params.put("shopNoList", shopNoList);
			}else{
				params.put("shopNo", "01");
			}
		}
		List<Integer> businessTypeList = new ArrayList<Integer>();
		//0-正常销售 1-跨店销售
		businessTypeList.add(Integer.valueOf(0));
		businessTypeList.add(Integer.valueOf(1));
		businessTypeList.add(Integer.valueOf(2));
		businessTypeList.add(Integer.valueOf(6));
		params.put("businessTypeList", businessTypeList);
		params.put("isCashRegister", String.valueOf(ShardingUtil.isPE()));//modify by wang.m 添加自收银店铺过滤条件
		//查询总记录对象
		CardReturnCheck cardReturnCheck = cardReturnCheckManager.findCardReturnCheckCount(params);
		List<CardReturnCheck> cardReturnCheckList = new ArrayList<CardReturnCheck>();
		//合计对象
		List<CardReturnCheck> footer = new ArrayList<CardReturnCheck>();
		if (cardReturnCheck != null && cardReturnCheck.getTotal() > 0) {
			SimplePage page = new SimplePage(1, cardReturnCheck.getTotal(), cardReturnCheck.getTotal());
			cardReturnCheckList = cardReturnCheckManager.findCardReturnCheckList(page, null, null, params);
			cardReturnCheck.setCompanyNo("合计");
			cardReturnCheck.setStatus(-1);
			//按终端号显示小计金额
			cardReturnCheckList = this.cardReturnCheckListByTerminalNumber(cardReturnCheckList);
			cardReturnCheckList.add(cardReturnCheck);
		}
		return cardReturnCheckList;
	}

	private List<CardReturnCheck> cardReturnCheckListByTerminalNumber(List<CardReturnCheck> cardReturnCheckList) {
		CardReturnCheck temp = null;
		Map<String, CardReturnCheck> map = new LinkedHashMap<String, CardReturnCheck>();
		for (CardReturnCheck cardReturnCheck : cardReturnCheckList) {
			if (null == map.get(cardReturnCheck.getTerminalNumber())) {
				temp = new CardReturnCheck();
				this.initCardReturnCheck(temp);
				temp.setTerminalNumber(cardReturnCheck.getTerminalNumber());//设置终端号
				temp.setCreditCardRate(cardReturnCheck.getCreditCardRate());
				map.put(cardReturnCheck.getTerminalNumber(), temp);
			}
			this.sumToCardReturnCheck(map.get(cardReturnCheck.getTerminalNumber()), cardReturnCheck);
		}
		//按终端账号汇总到列表底部
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			CardReturnCheck cardReturnCheckObject = map.get(key);
			String terminalNumber = cardReturnCheckObject.getTerminalNumber();
			int index = -1;//某品牌最后一个下标
			for (CardReturnCheck c : cardReturnCheckList) {
				if (c.getTerminalNumber() != null && terminalNumber != null && terminalNumber.equals(c.getTerminalNumber())) {
					index = cardReturnCheckList.indexOf(c);
				}
			}
			if (index > -1) {
				cardReturnCheckObject.setCompanyNo("小计");
				cardReturnCheckObject.setTerminalNumber(null);
				cardReturnCheckObject.setCreditCardRate(null);
				cardReturnCheckObject.setStatus(-1);
				cardReturnCheckList.add(index + 1, cardReturnCheckObject);
			}
		}
		return cardReturnCheckList;
	}

	private void sumToCardReturnCheck(CardReturnCheck mainCardReturnCheck, CardReturnCheck addCardReturnCheck) {
		if (null != mainCardReturnCheck.getAmount() && null != addCardReturnCheck.getAmount()) {
			mainCardReturnCheck.setAmount(mainCardReturnCheck.getAmount().add(addCardReturnCheck.getAmount()));
		}
		if (null != mainCardReturnCheck.getPoundage() && null != addCardReturnCheck.getPoundage()) {
			mainCardReturnCheck.setPoundage(mainCardReturnCheck.getPoundage().add(addCardReturnCheck.getPoundage()));
		}
		if (null != mainCardReturnCheck.getPaidinAmount() && null != addCardReturnCheck.getPaidinAmount()) {
			mainCardReturnCheck.setPaidinAmount(mainCardReturnCheck.getPaidinAmount().add(
					addCardReturnCheck.getPaidinAmount()));
		}

	}

	private void initCardReturnCheck(CardReturnCheck temp) {
		temp.setAmount(new BigDecimal(0d));
		temp.setPoundage(new BigDecimal(0d));
		temp.setCreditCardRate(new BigDecimal(0d));
		temp.setPaidinAmount(new BigDecimal(0d));
	}

	@Override
	protected String[] getImportProperties() {
		return null;
	}

	@Override
	protected boolean doBatchAdd(List<CardReturnCheck> list) {
		return false;
	}
}