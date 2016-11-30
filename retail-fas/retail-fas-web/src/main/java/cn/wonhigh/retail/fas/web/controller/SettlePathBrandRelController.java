package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.SettlePathBrandRel;
import cn.wonhigh.retail.fas.manager.SettlePathBrandRelManager;

import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 结算路径与品牌关联
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/settle_path_brand_rel")
public class SettlePathBrandRelController extends BaseController<SettlePathBrandRel> {
   
	@Resource
    private SettlePathBrandRelManager settlePathBrandRelManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("settle_path_brand_rel/",settlePathBrandRelManager);
    }
    
    /**
     * 通过结算路径id，查询该结算路径关联的品牌
     * 
     * @param pathNo 结算路径编码
     * @param request HttpServletRequest
     * @return 结算路径明细列表
     * @throws ManagerException 异常
     */
    @RequestMapping("/query_settle_path")
    @ResponseBody
    public List<SettlePathBrandRel> querySettlePath(@RequestParam("pathNo")String pathNo, 
		HttpServletRequest request) throws Exception {
    	if(StringUtils.isEmpty(pathNo)) {
    		return new ArrayList<SettlePathBrandRel>(1);
    	}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pathNo", pathNo);
		List<SettlePathBrandRel> list = settlePathBrandRelManager.findByBiz(null, params);
		return list;
    }
    
    @RequestMapping(value = "/save")
	public ResponseEntity<Map<String, Boolean>> save(HttpServletRequest request) {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		String pathNo = request.getParameter("pathNo");
		if(StringUtils.isEmpty(pathNo)) {
			flag.put("success", false);
			return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
		}
		String inserted = StringUtils.isEmpty(request.getParameter("inserted")) ? "" : request.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		List<SettlePathBrandRel> oList = null;
		if (StringUtils.isNotEmpty(inserted)) {
			List<Map> list;
			try {
				list = mapper.readValue(inserted, new TypeReference<List<Map>>() {});
				oList = convertListWithTypeReference(mapper, list, request);
				if (oList != null && oList.size() > 0) {
					settlePathBrandRelManager.save(pathNo, oList);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
}