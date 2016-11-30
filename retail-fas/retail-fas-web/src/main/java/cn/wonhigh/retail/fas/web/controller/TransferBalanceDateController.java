package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.TransferBalanceDate;
import cn.wonhigh.retail.fas.manager.TransferBalanceDateManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 地区调货结算期
 * @author user
 * @date  2016-07-05 14:55:50
 * @version 1.0.8
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Controller
@RequestMapping("/transfer_balance_date")
@ModuleVerify("30133007")
public class TransferBalanceDateController extends BaseController<TransferBalanceDate> {
    @Resource
    private TransferBalanceDateManager transferBalanceDateManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("transfer_balance_date/",transferBalanceDateManager);
    }
    
    /**
   	 * 根据公司编号及结算月，判断是否已存在记录
   	 * @param request
   	 * @return
        * @throws IOException 
   	 */
   	@RequestMapping(value = "/validate_data", method = RequestMethod.POST)
   	@ResponseBody
   	public Map<String, Object> checkIsExist(HttpServletRequest request) throws IOException {
   		Map<String, Object> map = new HashMap<String, Object>();
   		try {
   			String insertedList = StringUtils.isEmpty(request.getParameter("inserted")) ? "" : request.getParameter("inserted");
   			String upadtedList = StringUtils.isEmpty(request.getParameter("updated")) ? "" : request.getParameter("updated");
   			ObjectMapper mapper = new ObjectMapper();
   			
   			//新增
   			if (StringUtils.isNotEmpty(insertedList)) {
   				List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
				List<TransferBalanceDate> oList = convertListWithTypeReference(mapper, list);
				if (CollectionUtils.isNotEmpty(oList)) {
					for (int i = 0; i < oList.size(); i++) {
						TransferBalanceDate model = oList.get(i);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("salerNo", model.getSalerNo());
						params.put("buyerNo", model.getBuyerNo());
						params.put("balanceMonth", model.getBalanceMonth());
	   					List<TransferBalanceDate> ipbdlist = transferBalanceDateManager.findByBiz(null, params);
						if (CollectionUtils.isNotEmpty(ipbdlist)) {
	   						map.put("success", true);
							if (StringUtils.isBlank(model.getBuyerNo())) {
	   							map.put("message", "已存在调出公司为："+model.getSalerName()+" 结算月为："+model.getBalanceMonth()+" 的记录！");
							}else{
								map.put("message", "已存在调出公司为："+model.getSalerName()+" 调入公司为："+model.getBuyerName()+" 结算月为："+model.getBalanceMonth()+" 的记录！");
							}
						}else{
							map.put("success", false);
						}
	   				}
				}
			} 
   			
   			//修改
			if (StringUtils.isNotEmpty(upadtedList)) {
				List<Map> lists = mapper.readValue(upadtedList, new TypeReference<List<Map>>(){});
				List<TransferBalanceDate> oLists= convertListWithTypeReference(mapper,lists);
				if (oLists != null && oLists.size() > 0) {
					for (int i = 0; i < oLists.size(); i++) {
						TransferBalanceDate model = oLists.get(i);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("salerNo", model.getSalerNo());
						params.put("buyerNo", model.getBuyerNo());
						params.put("balanceMonth", model.getBalanceMonth());
						List<TransferBalanceDate> ipbdlist = transferBalanceDateManager.findByBiz(null, params);
					    if(CollectionUtils.isNotEmpty(ipbdlist) && ipbdlist.size() > 0){
							for (int j = 0; j < ipbdlist.size(); j++) {
								String id = (String) ipbdlist.get(j).getId();
								String salerNo = (String) ipbdlist.get(j).getSalerNo();
								String buyerNo = (String) ipbdlist.get(j).getBuyerNo();
								String balanceMonth = (String) ipbdlist.get(j).getBalanceMonth();
							    if (id.equals(model.getId()) && balanceMonth.equals(model.getBalanceMonth())) { 
										map.put("success", false);
							    } else {
								    if (salerNo.equals(model.getSalerNo()) && buyerNo.equals(model.getBuyerNo())
										&& balanceMonth.equals(model.getBalanceMonth()) && ipbdlist.size() > 1) {
										map.put("success", false);
									}else{
										map.put("success", true);
										if (StringUtils.isBlank(model.getBuyerNo())) {
				   							map.put("message", "已存在调出公司为："+model.getSalerName()+" 结算月为："+model.getBalanceMonth()+" 的记录！");
										}else{
											map.put("message", "已存在调出公司为："+model.getSalerName()+" 调入公司为："+model.getBuyerName()+" 结算月为："+model.getBalanceMonth()+" 的记录！");
										}
									}
								}
								
							}
						}
   					
					}
				} 
   			}
   		} catch(Exception e) {
   			map.put("success", false);
   			logger.error(e.getMessage(), e);
   			throw new IOException(e.getMessage(), e);
   		}
   		return map;
   	}
   	
   	/**
   	 * 批量生成
   	 * @param req
   	 * @param fromPage
   	 * @return
   	 * @throws ManagerException
   	 * @throws Exception
   	 */
	@RequestMapping(value = "/batch_create")
	@ResponseBody
	public Map<String, Object> batchSave(HttpServletRequest req,TransferBalanceDate fromPage)throws ManagerException, Exception {
		SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);// 获取登录用户
		String salerNo = fromPage.getSalerNo();
		String buyerNo = fromPage.getBuyerNo();
		
		TransferBalanceDate tbd = new TransferBalanceDate();
		tbd.setCreateUser(loginUser.getUsername());
		tbd.setSalerNo(salerNo); 
		tbd.setBuyerNo(buyerNo);
		tbd.setBalanceStartDate(fromPage.getBalanceStartDate());
		tbd.setBalanceEndDate(fromPage.getBalanceEndDate());
		tbd.setBalanceMonth(fromPage.getBalanceMonth());
		Map<String, Object>	resultMap = transferBalanceDateManager.addBalanceDateByBatch(tbd);
		return resultMap;
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
   	@SuppressWarnings({ "unchecked", "rawtypes" })
   	private List<TransferBalanceDate> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
   		Class<TransferBalanceDate> entityClass = (Class<TransferBalanceDate>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
   		List<TransferBalanceDate> tl=new ArrayList<TransferBalanceDate>(list.size());
   		for (int i = 0; i < list.size(); i++) {
   			TransferBalanceDate type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
   			tl.add(type);
   		}
   		return tl;
   	}
}