package cn.wonhigh.retail.fas.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillSplitData;
import cn.wonhigh.retail.fas.common.model.BillSplitLog;
import cn.wonhigh.retail.fas.manager.BillSplitDataManager;
import cn.wonhigh.retail.fas.manager.BillSplitLogManager;

import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-29 13:20:36
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
@RequestMapping("/bill_split_log")
@ModuleVerify("30115001")
public class BillSplitLogController extends BaseController<BillSplitLog> {
   
	@Resource
    private BillSplitLogManager billSplitLogManager;
	
	@Resource
    private BillSplitDataManager billSplitDataManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_split_log/",billSplitLogManager);
    }
    
    @RequestMapping("/manual_split")
    @ResponseBody
    public Boolean split(HttpServletRequest request) throws Exception {
    	String startDate = request.getParameter("startDate");
    	String endDate = request.getParameter("endDate");
    	String brandNo = request.getParameter("brandNo");
    	String billNo = request.getParameter("billNo");
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		if(StringUtils.isNotEmpty(brandNo)) {
			params.put("brandNo",brandNo);
		}
		if(StringUtils.isNotEmpty(billNo)) {
			params.put("billNo", billNo);
		}
		// 将拆单失败的单据对应的该字段清空
//		params.put("splitFlag", 1);
//    	billSplitLogManager.clearSplitFlag(params);
    	//拆单
    	this.split(params);
    	return true;
    }
    
    /**
     * 真正的拆单逻辑处理
     * 
     * @param params 过滤参数
     * @throws ManagerException 
     * @throws Exception 异常
     */
    public void split(Map<String, Object> params) throws ManagerException {
		try {
			int count = billSplitDataManager.findCount(params);
			if(count > 0) {
				for(int i = 0; i < (count / 50) + 1; i++) {
					SimplePage page = new SimplePage(i+1, 50, count);
					List<BillSplitData> list = billSplitDataManager.findByPage(page, null, null, params);
					for(int j = 0; j < list.size(); j++) {
						BillSplitData billSplitData = list.get(j);
						String brandNo = params.get("brandNo") != null ? params.get("brandNo").toString() : null;
						billSplitData.setBrandNo(brandNo);
						billSplitDataManager.billSplit(billSplitData);
					}
				}
			}
//			int returnCount = billSplitDataManager.selectBillReturnCount(params);
//			if(returnCount > 0) {
//				for(int i = 0; i < (returnCount / 50) + 1; i++) {
//					SimplePage page = new SimplePage(i+1, 50, returnCount);
//					List<BillSplitData> list = billSplitDataManager.findBillReturn(page, null, null, params);
//					for(int j = 0; j < list.size(); j++) {
//						BillSplitData billSplitData = list.get(j);
//						String brandNo = params.get("brandNo") != null ? params.get("brandNo").toString() : null;
//						billSplitData.setBrandNo(brandNo);
//						billSplitDataManager.billSplit(billSplitData);
//					}
//				}
//			}
		} catch(ManagerException e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
    }
//    public void split(Map<String, Object> params) {
//		boolean splitFlag = true;
//		while(splitFlag) {
//			BillSplitData data = null;
//			try {
//    			int asnCount = billSplitDataManager.selectBillAsnCount(params);
//    			int returnCount = billSplitDataManager.selectBillReturnCount(params);
//    			int logCount = billSplitLogManager.findCount(params);
//    			if(logCount >= (asnCount + returnCount)) {
//    				splitFlag = false;
//    			}
//    			if(splitFlag) {
//    				List<BillSplitData> list = billSplitDataManager.findByBiz(null, params);
//    				if(list == null || list.size() == 0) {
//    					splitFlag = false;
//    				}
//    				for(int i = 0; i < list.size(); i++) {
//    					data = list.get(i);
//    					data.setBrandNo(params.get("brandNo") == null ? null : params.get("brandNo").toString());
//    					billSplitDataManager.billSplit(data, params);
//    				}
//				} 
//			} catch(ManagerException e) {
//				e.printStackTrace();
//				if(data != null) {
//					try {
//						BillSplitLog log = billSplitDataManager.findLog(data);
//						billSplitDataManager.processLog(log, data, 1, e.getMessage());
//					} catch(ManagerException e1) {
//						
//					}
//				}
//			}
//		}
//    }
    
//    @RequestMapping(value="/show_split_log", produces={"application/json;charset=UTF-8"})
//    @ResponseBody
//    public String showSplitLog(HttpServletRequest request) throws Exception {
//    	String startDate = request.getParameter("startDate");
//    	String endDate = request.getParameter("endDate");
//    	Map<String, Object> params = new HashMap<String, Object>();
//    	params.put("startDate", startDate);
//    	params.put("endDate", endDate);
//    	//查询日志表示使用
//    	params.put("status", "0");
//		int asnCount = billSplitDataManager.selectBillAsnCount(params);
//		int returnCount = billSplitDataManager.selectBillReturnCount(params);
//		int logCount = billSplitLogManager.findCount(params);
//		if(asnCount + returnCount == logCount) {
//			return "拆单全部成功";
//		}
//		params.put("billType", 1);
//		params.put("status", "1");
//		int logFailAsnCount = billSplitLogManager.findCount(params);
//		String message = "";
//		if(logFailAsnCount == 0) {
//			message += "到货单全部拆分成功/n";
//		} else {
//			message += "到货单有"+logFailAsnCount+"张未拆分成功/n";
//		}
//		//YANGYONGTODO 需算出退厂单是否全拆成功
//		return message;
//    }
}