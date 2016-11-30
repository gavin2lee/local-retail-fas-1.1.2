package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.enums.SubjectTypeEnums;
import cn.wonhigh.retail.fas.common.model.AccountingSubject;
import cn.wonhigh.retail.fas.manager.AccountingSubjectManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;

/**
 * 会计科目设置
 * 
 * @author ouyang.zm
 * @date 2014-08-25 15:58:32
 * @version 1.0.0
 * @copyright (C) 2013 YouGou Information Technology Co.,Ltd All Rights
 *            Reserved.
 * 
 *            The software for the YouGou technology development, without the
 *            company's written consent, and any other individuals and
 *            organizations shall not be used, Copying, Modify or distribute the
 *            software.
 * 
 */
@Controller
@RequestMapping("/base_setting/accounting_subject")
@ModuleVerify("30100001")
public class AccountingSubjectController extends BaseController<AccountingSubject> {
	@Resource
	private AccountingSubjectManager accountingSubjectManager;

	@Override
	public CrudInfo init() {
		return new CrudInfo("base_setting/accounting_subject/", accountingSubjectManager);
	}

	/**
	 * 初始化科目类型
	 * @return
	 */
	@RequestMapping("/getSubjectType")
	@ResponseBody
	public List<Map<String, String>> initSubjectType() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		SubjectTypeEnums[] enumData = SubjectTypeEnums.values();
		for (SubjectTypeEnums x : enumData) {
			map = new HashMap<String, String>();
			map.put("id", x.getTypeNo().toString());
			map.put("name", x.getTypeName());
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
	@RequestMapping(value = "/check_Repect", method = RequestMethod.POST)
	public boolean checkRepeatData(@ModelAttribute("model") AccountingSubject model) throws ManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		String queryCondition = "AND ( SUBJECT_CODE = '" + model.getSubjectCode() + "'  OR SUBJECT_NAME = '"
				+ model.getSubjectName() + "')";
		params.put("queryCondition", queryCondition);
		List<AccountingSubject> list = accountingSubjectManager.findByBiz(model, params);
		if (list != null && list.size() > 0) {
			for (AccountingSubject m : list) {
				if (!m.getId().equals(model.getId())) {
					return true;
				}
			}
		}
		return false;
	}
}