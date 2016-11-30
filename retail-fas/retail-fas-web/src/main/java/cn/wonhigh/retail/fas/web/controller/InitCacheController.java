package cn.wonhigh.retail.fas.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.backend.security.Authorization;
import cn.wonhigh.retail.fas.common.model.Lookup;
import cn.wonhigh.retail.fas.common.model.LookupEntry;
import cn.wonhigh.retail.fas.common.utils.CommonUtil;
import cn.wonhigh.retail.fas.common.vo.LookupDtl;
import cn.wonhigh.retail.fas.manager.LookupEntryManager;
import cn.wonhigh.retail.fas.manager.LookupManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 1.tomcat容器初始化后加载些数据,如字典加载到缓存   2. 处理些公用的链接
 * @author wei.hj  weihaijin
 * @date 2013-07-20cc
 * @copyRight yougou.com
 */
@Controller
@RequestMapping("/initCache")
public class InitCacheController implements InitializingBean {
	private static final String QUERY_CONDITION = "queryCondition";
	protected static final XLogger LOG = XLoggerFactory.getXLogger(InitCacheController.class);
	@Value("${db.schema}")
	private String schema;

	public static Map<String, List<LookupDtl>> lookupdMap = new LinkedHashMap<String, List<LookupDtl>>();

	@Resource
	private LookupManager lookupManager;

	@Resource
	private LookupEntryManager lookupEntryManager;

	/**
	 * 初始化
	 */
	public void init() {
		LOG.info("★★★★★★★★★★★★★★★★初始化开始★★★★★★★★★★★★★★★★");
		LOG.info("★★★★★★★★★★★★★★★★初始化完成★★★★★★★★★★★★★★★★");
	}

	//页面获得字典
	@RequestMapping(value = "/getLookupDtlsList")
	@ResponseBody
	public List<LookupDtl> getLookupDtlsList(@RequestParam("lookupcode") String lookupcode, HttpServletRequest request)
			throws ManagerException {
		List<LookupDtl> listObj = new ArrayList<LookupDtl>();
		if (CommonUtil.hasValue(lookupcode)) {
			listObj = lookupdMap.get(lookupcode);
			if (CollectionUtils.isEmpty(listObj)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("lookupCode", lookupcode);
				List<Lookup> lookups = lookupManager.findByBiz(null, param);
				if (!CollectionUtils.isEmpty(lookups)) {
					param.clear();
					param.put("lookupId", lookups.get(0).getId());
					List<LookupEntry> lookupEntries = lookupEntryManager.findByBiz(null, param);
					listObj = new ArrayList<LookupDtl>();
					LookupDtl dtl = null;
					for (LookupEntry lookupEntry : lookupEntries) {
						dtl = new LookupDtl();
						dtl.setLookupcode(lookupcode);
						dtl.setItemname(lookupEntry.getName());
						dtl.setItemvalue(lookupEntry.getCode());
						dtl.setOrganTypeNo(lookupEntry.getOrganTypeNo());
						listObj.add(dtl);
					}
					lookupdMap.put(lookupcode, listObj);
				}
			}
			//是否添加全部的标志
			String addAllFlag = request.getParameter("addAllFlag");
			if ("true".equalsIgnoreCase(addAllFlag)) {
				List<LookupDtl> newlstObj = new ArrayList<LookupDtl>();
				LookupDtl dtl = new LookupDtl();
				dtl.setLookupcode("ALL");
				dtl.setItemname("全部");
				dtl.setItemvalue("ALL");
				newlstObj.add(dtl);
				newlstObj.addAll(listObj);
				return reload(newlstObj);
			}
			String currentYear = request.getParameter("currentYear");
			if (StringUtils.isNotEmpty(currentYear)) {
				int year = Integer.parseInt(currentYear);
				String beforeCurrentYear = String.valueOf(year - 1);
				String afterCurrentYear = String.valueOf(year + 1);
				List<LookupDtl> list = new ArrayList<LookupDtl>();
				for (LookupDtl dtl : listObj) {
					if (currentYear.equals(dtl.getItemname()) || beforeCurrentYear.equals(dtl.getItemname())
							|| afterCurrentYear.equals(dtl.getItemname())) {
						list.add(dtl);
					}
				}
				listObj = list;
			}
		}
		return reload(listObj);
	}

	
	private List<LookupDtl> reload(List<LookupDtl> lst){
		SystemUser user = Authorization.getUser();
		if( user == null || StringUtils.isEmpty(user.getOrganTypeNo())){
			return lst;
		}
		List<LookupDtl> result = new ArrayList<>();
		for (LookupDtl item : lst) {
			if( StringUtils.isEmpty(item.getOrganTypeNo())  
				|| user.getOrganTypeNo().equalsIgnoreCase(item.getOrganTypeNo())){
				result.add(item);
			}
		}
		return result;
	}
	
	/**
	 * 构建参数
	 * @param req 请求对象
	 * @param model 保存对象
	 * @return 返回构建的数组信息
	 * @throws ManagerException 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private Map<String, Object> builderParams(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, String[]> params = req.getParameterMap();
		Map<String, Object> result = new HashMap<String, Object>(params.size());
		if (null != params && params.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Entry<String, String[]> p : params.entrySet()) {
				if (null == p.getValue() || StringUtils.isEmpty(p.getValue().toString())) {
					continue;
				}
				// 只转换一个参数，多个参数不转换
				String[] values = p.getValue();
				String match = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
				if (values[0].matches(match)) {
					try {
						result.put(p.getKey(), sdf.parse(values[0]));
					} catch (ParseException e) {
						LOG.error(e.getMessage(), e);
						throw new ManagerException(e.getMessage(), e);
					}
				} else if (QUERY_CONDITION.equals(p.getKey()) && model.asMap().containsKey(QUERY_CONDITION)) {
					result.put(p.getKey(), model.asMap().get(QUERY_CONDITION));
				} else {
					result.put(p.getKey(), values[0]);
				}
			}
		}
		return result;
	}

	/**
	 * @return 数据库schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema 数据库schema
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}
