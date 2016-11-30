package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.SettleTypeEnum;
import cn.wonhigh.retail.fas.common.model.SettleMethod;
import cn.wonhigh.retail.fas.manager.SettleMethodManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 结算方式设置 
 * @author ouyang.zm
 * @date  2014-09-01 15:17:41
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
@RequestMapping("/base_setting/settle_method")
@ModuleVerify("30100005")
public class SettleMethodController extends BaseController<SettleMethod> {
    @Resource
    private SettleMethodManager settleMethodManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("base_setting/settle_method/",settleMethodManager);
    }
    
    
    
    /**
     * 加载结算方式类型
     * @return
     */
	@RequestMapping("/settleType")
	@ResponseBody
	public List<Map<String,String>> initSubjectType(){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String,String> map=null;
		SettleTypeEnum[] allLight = SettleTypeEnum.values();
		for (SettleTypeEnum aLight : allLight) {
			map=new HashMap<String, String>();
			map.put("no", aLight.getNo());
			map.put("name", aLight.getName());
			list.add(map);
		}
		return list;
	} 
	
	/**
     * 校验是否包含重复数据
     * @param financialAccount
     * @return
     * @throws ManagerException 
     */
    @ResponseBody
    @RequestMapping(value="/check_Repect", method=RequestMethod.POST)
    public boolean checkrepeatData(@ModelAttribute("model") SettleMethod model) throws ManagerException{
    	Map<String,Object> params=new HashMap<String, Object>();
    	String queryCondition = "AND ( SETTLE_CODE = '"+model.getSettleCode()+"'  OR SETTLE_NAME = '"+model.getSettleName()+"')";
    	params.put("queryCondition", queryCondition);
    	List<SettleMethod> list=settleMethodManager.findByBiz(model, params);
    	if (list != null && list.size() > 0) {
			for (SettleMethod m : list) {
				if (!m.getId().equals(model.getId())) {
					return true;
				}
			}
		}
		return false;
    }
	
    @RequestMapping("/getJsonData")
    @ResponseBody
    public List<SettleMethod> getJsonData(HttpServletRequest req) throws ManagerException {
    	return settleMethodManager.findByBiz(null, null);
    }
}