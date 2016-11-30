/**  
*   
* 项目名称：retail-fas-web  
* 类名称：AreaBalanceCommonController  
* 类描述：处理地区结算的公共请求
* 创建人：ouyang.zm  
* 创建时间：2015-1-12 上午10:12:11  
* @version       
*/ 
package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.AreaAmongBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.AreaAmongBalanceStatusPayEnums;
import cn.wonhigh.retail.fas.common.enums.AreaOtherInBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.AreaOtherOutBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.AreaPurchaseBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.HqInsteadBuyBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.HqOtherInBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.enums.HqOtherOutBalanceStatusEnums;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.CurrencyManagement;
import cn.wonhigh.retail.fas.common.vo.LookupVo;
import cn.wonhigh.retail.fas.manager.BrandManager;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.CategoryManager;
import cn.wonhigh.retail.fas.manager.CurrencyManagementManager;

import com.yougou.logistics.base.common.exception.ManagerException;

@RequestMapping("/area_balance_common")
@Controller
public class AreaBalanceCommonController {
	@Resource
	private BrandManager brandManager;
	@Resource
	private BrandUnitManager brandUnitManager;
	@Resource
	private CurrencyManagementManager currencyManagementManager;
	@Resource
	private CategoryManager categoryManager;
	public static Map<String, List<LookupVo>> lookupMap = new HashMap<String, List<LookupVo>>();
	
	/**
	 * 查询品牌
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBrand")
	@ResponseBody
	public List<Brand> getBrandData() throws ManagerException {
		return brandManager.findByBiz(null, null);
	}
	
	/**
	 * 查询品牌部
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBrandUnit")
	@ResponseBody
	public List<BrandUnit> getBrandUnitData() throws ManagerException {
		return brandUnitManager.findByBiz(null, null);
	}
    
	/**
	 * 查询大类
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCategory")
	@ResponseBody
	public List<LookupVo> getCategory() throws ManagerException {
		List<LookupVo> lstVo = lookupMap.get("category");
		if (CollectionUtils.isEmpty(lstVo)) {
			lstVo = new LinkedList<LookupVo>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("levelid", 1);
			List<Category> lstItem = categoryManager.findByBiz(null, params);
			for (Category item : lstItem) {
				LookupVo vo = new LookupVo();
				vo.setCode(item.getCategoryNo());
				vo.setName(item.getName());
				lstVo.add(vo);
			}
			lookupMap.put("category", lstVo);
		}
		return lstVo;
	}
	
	/**
	 * 查询币种
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getCurrency")
	@ResponseBody
	public List<CurrencyManagement> getCurrencyData() throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", 1);
		return currencyManagementManager.findByBiz(null, params);
	}
	
	/**
	 * 结算单单据状态(地区间应收)
	 * @return
	 */
	@RequestMapping("/getAABalanceBillStatus")
	@ResponseBody
	public List<Map<String, String>> getAreaAmongBalanceStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		AreaAmongBalanceStatusEnums[] enumData = AreaAmongBalanceStatusEnums.values();
		for (AreaAmongBalanceStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 结算单单据状态(地区间应付)
	 * @return
	 */
	@RequestMapping("/getAPBalanceBillStatus")
	@ResponseBody
	public List<Map<String, String>> getAreaAmongBalancePayStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		AreaAmongBalanceStatusPayEnums[] enumData = AreaAmongBalanceStatusPayEnums.values();
		for (AreaAmongBalanceStatusPayEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 结算单单据状态(地区自购)
	 * @return
	 */
	@RequestMapping("/getASBalanceBillStatus")
	@ResponseBody
	public List<Map<String, String>> getAreaToSupplierBalanceStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		AreaPurchaseBalanceStatusEnums[] enumData = AreaPurchaseBalanceStatusEnums.values();
		for (AreaPurchaseBalanceStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 结算单单据状态(总部代采)
	 * @return
	 */
	@RequestMapping("/getHSBalanceBillStatus")
	@ResponseBody
	public List<Map<String, String>> getHqBalanceStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		HqInsteadBuyBalanceStatusEnums[] enumData = HqInsteadBuyBalanceStatusEnums.values();
		for (HqInsteadBuyBalanceStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}

	/**
	 * 获取单据类型
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
	@RequestMapping("/getBillType")
	@ResponseBody
	public List<LookupVo> getBillType(HttpServletRequest req) throws ManagerException {
		List<LookupVo> lstEntry = new ArrayList<LookupVo>();
		String showType = req.getParameter("showType");
		if (StringUtils.isNotBlank(showType)) {
			String type[] = showType.split(",");
			for (String str : type) {
				for (BillTypeEnums billType : BillTypeEnums.values()) {
					if (str.equals(billType.name())) {
						LookupVo entry = new LookupVo();
						entry.setCode(String.valueOf(billType.getRequestId()));
						entry.setName(String.valueOf(billType.getDesc()));
						lstEntry.add(entry);
						break;
					}
				}
			}
			return lstEntry;
		}
		for (BillTypeEnums type : BillTypeEnums.values()) {
			LookupVo entry = new LookupVo();
			entry.setCode(String.valueOf(type.getRequestId()));
			entry.setName(String.valueOf(type.getDesc()));
			lstEntry.add(entry);
		}
		return lstEntry;
	}
	
	/**
	 * 结算单单据状态(地区其他出库)
	 * @return
	 */
	@RequestMapping("/getAHQBalanceBillStatus")
	@ResponseBody
	public List<Map<String, String>> initAreaAmongBalanceStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		AreaOtherOutBalanceStatusEnums[] enumData = AreaOtherOutBalanceStatusEnums.values();
		for (AreaOtherOutBalanceStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 结算单单据状态(总部其他入库结算)
	 * @return
	 */
	@RequestMapping("/getHqOtherBalanceStatus")
	@ResponseBody
	public List<Map<String, String>> initHqBalanceStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		HqOtherInBalanceStatusEnums[] enumData = HqOtherInBalanceStatusEnums.values();
		for (HqOtherInBalanceStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 结算单单据状态(地区其他入库)
	 * @return
	 */
	@RequestMapping("/getAreaOtherInStatus")
	@ResponseBody
	public List<Map<String, String>> initAreaOtherStockInBalanceStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		AreaOtherInBalanceStatusEnums[] enumData = AreaOtherInBalanceStatusEnums.values();
		for (AreaOtherInBalanceStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 结算单单据状态(总部其他出库结算)
	 * @return
	 */
	@RequestMapping("/getHqOtherOutBalanceBillStatus")
	@ResponseBody
	public List<Map<String, String>> initHqOtherOutBalanceStatus() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		HqOtherOutBalanceStatusEnums[] enumData = HqOtherOutBalanceStatusEnums.values();
		for (HqOtherOutBalanceStatusEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("statusNo", x.getStatusNo().toString());
			map.put("statusName", x.getStatusName());
			list.add(map);
		}
		return list;
	}
}
