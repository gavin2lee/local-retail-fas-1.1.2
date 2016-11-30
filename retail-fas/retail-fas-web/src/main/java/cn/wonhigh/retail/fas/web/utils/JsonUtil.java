package cn.wonhigh.retail.fas.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.enums.FasLogoutStatusEnum;
import cn.wonhigh.retail.fas.common.model.FasBaseModel;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * TODO: 增加描述
 * 
 * @author 杨勇
 * @date 2014-9-5 上午10:31:42
 * @version 0.1.0
 * @copyright yougou.com
 */
@SuppressWarnings("rawtypes")
public class JsonUtil<ModelType extends FasBaseModel> {

	public Map<CommonOperatorEnum, List<ModelType>> convertToMap(HttpServletRequest req, Class<ModelType> entityClass)
			throws Exception {
		String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		Map<CommonOperatorEnum, List<ModelType>> params = new HashMap<CommonOperatorEnum, List<ModelType>>();
		if (StringUtils.isNotEmpty(deletedList)) {
			List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, req, entityClass);
			params.put(CommonOperatorEnum.DELETED, oList);
		}
		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, req, entityClass);
			params.put(CommonOperatorEnum.UPDATED, oList);
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>() {
			});
			List<ModelType> oList = convertListWithTypeReference(mapper, list, req, entityClass);
			params.put(CommonOperatorEnum.INSERTED, oList);
		}
		return params;
	}

	/**
	 * 转换成泛型列表
	 * 
	 * @param valueList
	 *            待转换的json格式数据
	 * @param request
	 *            HttpServletRequest
	 * @param entityClass
	 *            转换后的class
	 * @return List<ModelType>
	 * @throws Exception
	 */
	public List<ModelType> convertListWithTypeReference(String valueList, HttpServletRequest request,
			Class<ModelType> entityClass) throws Exception {
		if (StringUtils.isNotEmpty(valueList)) {
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = mapper.readValue(valueList, new TypeReference<List<Map>>() {
			});
			List<ModelType> tl = new ArrayList<ModelType>(list.size());
			for (int i = 0; i < list.size(); i++) {
				ModelType type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
				this.setDefaulValues(type, request);
				tl.add(type);
			}
			return tl;
		}
		return new ArrayList<ModelType>(1);
	}

	/**
	 * 转换成泛型列表
	 * 
	 * @param mapper
	 *            ObjectMapper
	 * @param list
	 *            List<Map>
	 * @param request
	 *            HttpServletRequest
	 * @param entityClass
	 *            转换后的class
	 * @return List<ModelType>
	 * @throws Exception
	 */
	protected List<ModelType> convertListWithTypeReference(ObjectMapper mapper, List<Map> list,
			HttpServletRequest request, Class<ModelType> entityClass) throws Exception {
		if (list != null && list.size() > 0) {
			List<ModelType> tl = new ArrayList<ModelType>(list.size());
			for (int i = 0; i < list.size(); i++) {
				ModelType type = mapper.readValue(mapper.writeValueAsString(list.get(i)), entityClass);
				this.setDefaulValues(type, request);
				tl.add(type);
			}
			return tl;
		}
		return new ArrayList<ModelType>(1);
	}

	/**
	 * 给实体对象的字段设置默认的值
	 * 
	 * @param model
	 *            实体对象
	 * @param request
	 *            HttpServletRequest
	 * @return 设置默认值之后的实体对象
	 * @throws Exception
	 */
	protected ModelType setDefaulValues(ModelType model, HttpServletRequest request) throws Exception {
		SystemUser loginUser = CurrentUser.getCurrentUser(request);
		if (StringUtils.isEmpty(model.getId())) {
			model.setId(null);
			model.setCreateUser(loginUser.getUsername());
			model.setCreateTime(DateUtil.getCurrentDateTime());
			model.setAuditStatus(FasAduitStatusEnum.NO_ADUIT_STATUS.getValue());
			if (model.getStatus() == null) {
				model.setStatus(FasLogoutStatusEnum.UNABLE_STATUS.getValue());
			}
		} else {
			model.setUpdateUser(loginUser.getUsername());
			model.setUpdateTime(DateUtil.getCurrentDateTime());
		}
		return model;
	}
}
