package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wonhigh.retail.fas.common.model.OrderUnit;
import cn.wonhigh.retail.fas.manager.OrderUnitManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author huang.xb1
 * @date  2014-07-28 14:19:21
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
@RequestMapping("/order_unit")
@ModuleVerify("30100007")
public class OrderUnitController extends BaseCrudController<OrderUnit> {

	private static final XLogger logger = XLoggerFactory.getXLogger(OrderUnitController.class);
	@Resource
	private OrderUnitManager orderUnitManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("order_unit/", orderUnitManager);
	}

	@RequestMapping(value = "/listSelect")
	public String listSelect() {
		return "order_unit/listSelect";
	}

	/**
	 * 新增訂貨單元明细
	 * @param 
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value = "/add")
//	public ResponseEntity<Map<String, Boolean>> add(OrderBrandStoreRel orderBrandStoreRel, HttpServletRequest req)
//			throws ManagerException {
//		try {
//			Map<String, Boolean> flag = new HashMap<String, Boolean>();
//
//			String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
//			insertedList = URLDecoder.decode(insertedList, "UTF-8");
//			ObjectMapper mapper = new ObjectMapper();
//			List<OrderBrandStoreRel> orderBrandStoreRelList = new ArrayList<OrderBrandStoreRel>();
//			String dtl = req.getParameter("orderUnit");
//			OrderUnit orderUnit = null;
//
//			if (StringUtils.isNotEmpty(dtl)) {
//				orderUnit = mapper.readValue(dtl, new TypeReference<OrderUnit>() {
//				});
//				Date date = new Date();
//				String userName = Authorization.getUser().getLoginName();
//				orderUnit.setCreateUser(userName);
//				orderUnit.setUpdateUser(userName);
//				orderUnit.setCreateTime(date);
//				orderUnit.setUpdateTime(date);
//			}
//			if (StringUtils.isNotEmpty(insertedList)) {
//				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
//				});
//				orderBrandStoreRelList = convertListWithTypeReference1(mapper, list);
//			}
//			//attributeExtDtlManager.add(attributeExtDtls);
//			orderUnitManager.add(orderBrandStoreRelList, orderUnit);
//
//			flag.put("success", true);
//			return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
//		} catch (ManagerException e) {
//
//			logger.error(e.getLocalizedMessage(), e);
//			throw new ManagerException(e.getLocalizedMessage(), e);
//		} catch (Exception e) {
//
//			logger.error("新增订货单元明细出错！", e);
//			throw new ManagerException("新增订货单元明明细出错！", e);
//		}
//
//	}

	/**
	 * 新增分类属性明细
	 * @param AttributeExtDtl
	 * @param req
	 * @return
	 * @throws ManagerException
	 */
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value = "/save_all")
//	public ResponseEntity<Map<String, Boolean>> saveAll(OrderBrandStoreRel orderBrandStoreRel, HttpServletRequest req)
//			throws ManagerException {
//		try {
//
//			Map<String, Boolean> flag = new HashMap<String, Boolean>();
//			String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
//			String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
//			String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
//			String dtl = req.getParameter("orderUnit");
//			insertedList = URLDecoder.decode(insertedList, "UTF-8");
//			upadtedList = URLDecoder.decode(upadtedList, "UTF-8");
//			deletedList = URLDecoder.decode(deletedList, "UTF-8");
//
//			ObjectMapper mapper = new ObjectMapper();
//			Map<CommonOperatorEnum, List<OrderBrandStoreRel>> params = new HashMap<CommonOperatorEnum, List<OrderBrandStoreRel>>();
//			OrderUnit orderUnit = null;
//
//			if (StringUtils.isNotEmpty(dtl)) {
//				orderUnit = mapper.readValue(dtl, new TypeReference<OrderUnit>() {
//				});
//				Date date = new Date();
//				String userName = Authorization.getUser().getLoginName();
//				orderUnit.setUpdateUser(userName);
//				orderUnit.setUpdateTime(date);
//			}
//			if (StringUtils.isNotEmpty(deletedList) && !deletedList.equals("[]")) {
//				List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {
//				});
//				List<OrderBrandStoreRel> oList = convertListWithTypeReference1(mapper, list);
//				params.put(CommonOperatorEnum.DELETED, oList);
//			}
//			if (StringUtils.isNotEmpty(upadtedList) && !upadtedList.equals("[]")) {
//				List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {
//				});
//				List<OrderBrandStoreRel> oList = convertListWithTypeReference1(mapper, list);
//				params.put(CommonOperatorEnum.UPDATED, oList);
//			}
//			if (StringUtils.isNotEmpty(insertedList) && !insertedList.equals("[]")) {
//				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
//				});
//				List<OrderBrandStoreRel> oList = convertListWithTypeReference1(mapper, list);
//				params.put(CommonOperatorEnum.INSERTED, oList);
//			}
//
//			orderUnitManager.saveAll(params, orderUnit);
//			flag.put("success", true);
//			return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
//
//		} catch (ManagerException e) {
//			logger.error(e.getLocalizedMessage(), e);
//			throw new ManagerException(e.getLocalizedMessage(), e);
//		} catch (Exception e) {
//			logger.error("新增订货单位明细出错！", e);
//			throw new ManagerException("新增订货单位明细出错！", e);
//		}
//
//	}

	/**
	 * 数据类型转换1
	 * @param mapper
	 * @param list
	 * @return
	 * */
//	@SuppressWarnings({ "rawtypes" })
//	private List<OrderBrandStoreRel> convertListWithTypeReference1(ObjectMapper mapper, List<Map> list)
//			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
//		List<OrderBrandStoreRel> tl = new ArrayList<OrderBrandStoreRel>(list.size());
//		Date nowTime = new Date();
//		for (int i = 0; i < list.size(); i++) {
//			OrderBrandStoreRel type = mapper
//					.readValue(mapper.writeValueAsString(list.get(i)), OrderBrandStoreRel.class);
//			type.setCreateTime(nowTime);
//			type.setUpdateTime(nowTime);
//			tl.add(type);
//		}
//		return tl;
//	}

	/**
	 * 数据类型转换
	 * @param mapper
	 * @param list
	 * @return
	 * */
	@SuppressWarnings({ "rawtypes" })
	private List<OrderUnit> convertListWithTypeReference(ObjectMapper mapper, List<Map> list)
			throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
		List<OrderUnit> tl = new ArrayList<OrderUnit>(list.size());
		Date nowTime = new Date();
		for (int i = 0; i < list.size(); i++) {
			OrderUnit type = mapper.readValue(mapper.writeValueAsString(list.get(i)), OrderUnit.class);
			type.setCreateTime(nowTime);
			type.setUpdateTime(nowTime);
			tl.add(type);
		}
		return tl;
	}

	/**
	 * 删除订货单元
	 * @param  
	 * @param req 
	 * @return
	 * @throws ManagerException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/delOrderUnit")
	public ResponseEntity<Map<String, Boolean>> delOrderUnit(HttpServletRequest req) throws JsonParseException,
			JsonMappingException, IOException, ManagerException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();

		String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
		ObjectMapper mapper = new ObjectMapper();
		Map<CommonOperatorEnum, List<OrderUnit>> params = new HashMap<CommonOperatorEnum, List<OrderUnit>>();
		if (StringUtils.isNotEmpty(deletedList)) {
			List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {
			});
			List<OrderUnit> oList = convertListWithTypeReference(mapper, list);
			params.put(CommonOperatorEnum.DELETED, oList);
		}
		if (params.size() > 0) {
			orderUnitManager.delOrderUnit(params);
		}
		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
}