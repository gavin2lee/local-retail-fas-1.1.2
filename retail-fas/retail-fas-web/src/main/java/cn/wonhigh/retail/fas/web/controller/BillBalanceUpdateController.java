package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.dto.BalanceDetailDto;
import cn.wonhigh.retail.fas.common.enums.BalanceTableEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.OtherDeduction;
import cn.wonhigh.retail.fas.manager.BillBalanceUpdateManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 结算更新Controller
 * @author wang.m1
 * @date  2014-09-05 10:33:45
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 */
@Controller
@RequestMapping("/bill_balance/update")
public class BillBalanceUpdateController extends BaseCrudController<BillBalance> {
	
	@Resource
	private BillBalanceUpdateManager billBalanceUpdateManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("bill_balance/hq/", billBalanceUpdateManager);
	}

	/**
	 * 新增结算单
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add_balance")
	@ResponseBody
	public BillBalance addBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if(setTableName(obj)){
			Map<String, Object> params = builderParams(req, model);
			params.put("tableName", obj.getTableName());
			params.put("queryCondition", " AND (balance_no IS NULL OR balance_no = '')");
			return billBalanceUpdateManager.add(obj, params);
		}
		return null;
	}
	
	/**
	 * 修改结算单
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update_balance")
	@ResponseBody
	public BillBalance updateBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if(setTableName(obj)){
			return billBalanceUpdateManager.update(obj);
		}
		return null;
	}
	
	/**
	 * 删除结算单
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete_balance")
	@ResponseBody
	public Integer deleteBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if(setTableName(obj)){
			return billBalanceUpdateManager.delete(obj);
		}
		return null;
	}
	
	/**
	 * 自定义结算单
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/create_balance")
	public BillBalance createBalance(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if(StringUtils.isNotBlank(req.getParameter("checkedRows")) && setTableName(obj)){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> itemList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
			List<Object> lstItem =convertListWithTypeReference(mapper, itemList, BalanceDetailDto.class);
			return billBalanceUpdateManager.createBalance(obj, lstItem);
		}
		return null;
	}
	
	/**
	 * 结算调整
	 * @param obj
	 * @param req
	 * @param model
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/adjust_balance")
	@ResponseBody
	public BillBalance balanceAdjust(BillBalance obj, HttpServletRequest req, Model model) throws Exception {
		if(StringUtils.isNotBlank(req.getParameter("checkedRows"))){
			ObjectMapper mapper = new ObjectMapper();
			List<Map> deductionList = mapper.readValue(req.getParameter("checkedRows"), new TypeReference<List<Map>>() {});
			List<Object> lstItem =convertListWithTypeReference(mapper, deductionList, OtherDeduction.class);
			return billBalanceUpdateManager.balanceAdjust(obj, lstItem);
		}
		return null;
	}
	
	/**
	 * 设置结算表名
	 * @param params 
	 * @return
	 */
	private boolean setTableName(BillBalance obj){
		Integer balanceType = obj.getBalanceType();
		if(null !=balanceType){
			String tableName = BalanceTableEnums.getSendTableByNo(balanceType);
			if(StringUtils.isNotBlank(tableName)){
				obj.setTableName(tableName);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 转换成泛型列表
	 * @param mapper
	 * @param list
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> convertListWithTypeReference(ObjectMapper mapper, List<Map> list, Class clazz)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List<Object> tl = new ArrayList<Object>(list.size());
		if(!CollectionUtils.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object type = mapper.readValue(mapper.writeValueAsString(list.get(i)), clazz);
				tl.add(type);
			}
		}
		return tl;
	}
	
}