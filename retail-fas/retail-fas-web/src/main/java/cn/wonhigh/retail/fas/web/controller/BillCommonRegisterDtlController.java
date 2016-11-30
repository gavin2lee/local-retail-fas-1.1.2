package cn.wonhigh.retail.fas.web.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.BillCommonRegisterDtl;
import cn.wonhigh.retail.fas.manager.BillCommonRegisterDtlManager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author chen.mj
 * @date  2014-11-10 14:40:44
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
@RequestMapping("/bill_common_register_dtl")
public class BillCommonRegisterDtlController extends BaseController<BillCommonRegisterDtl> {
    @Resource
    private BillCommonRegisterDtlManager billCommonRegisterDtlManager;
    
    
   

    @Override
    public CrudInfo init() {
        return new CrudInfo("bill_common_register_dtl/",billCommonRegisterDtlManager);
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
	private List<BillCommonRegisterDtl> convertListWithTypeReference(ObjectMapper mapper,List<Map> list) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException{
		Class<BillCommonRegisterDtl> entityClass = (Class<BillCommonRegisterDtl>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		List<BillCommonRegisterDtl> tl=new ArrayList<BillCommonRegisterDtl>(list.size());
		for (int i = 0; i < list.size(); i++) {
			BillCommonRegisterDtl type=mapper.readValue(mapper.writeValueAsString(list.get(i)),entityClass);
			tl.add(type);
		}
		return tl;
	}
	
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/save")
	public ResponseEntity<Map<String, Boolean>> save(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException,
			ManagerException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();

		String deletedList = StringUtils.isEmpty(req.getParameter("deleted")) ? "" : req.getParameter("deleted");
		String upadtedList = StringUtils.isEmpty(req.getParameter("updated")) ? "" : req.getParameter("updated");
		String insertedList = StringUtils.isEmpty(req.getParameter("inserted")) ? "" : req.getParameter("inserted");
		ObjectMapper mapper = new ObjectMapper();
		Map<CommonOperatorEnum, List<BillCommonRegisterDtl>> params = new HashMap<CommonOperatorEnum, List<BillCommonRegisterDtl>>();
		if (StringUtils.isNotEmpty(deletedList)) {
			List<Map> list = mapper.readValue(deletedList, new TypeReference<List<Map>>(){});
			List<BillCommonRegisterDtl> oList=convertListWithTypeReference(mapper,list);
			params.put(CommonOperatorEnum.DELETED, oList);
		}
		if (StringUtils.isNotEmpty(upadtedList)) {
			List<Map> list = mapper.readValue(upadtedList, new TypeReference<List<Map>>(){});
			List<BillCommonRegisterDtl> oList=convertListWithTypeReference(mapper,list);
			params.put(CommonOperatorEnum.UPDATED, oList);
		}
		if (StringUtils.isNotEmpty(insertedList)) {
			List<Map> list = mapper.readValue(insertedList, new TypeReference<List<Map>>(){});
			List<BillCommonRegisterDtl> oList=convertListWithTypeReference(mapper,list);
			params.put(CommonOperatorEnum.INSERTED, oList);
		}
		
		if (params.size() > 0) {
			billCommonRegisterDtlManager.saveAll(params);
		}
		flag.put("success", true);
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
   
	/**
     * 根据发票单据号查询发票明细的记录数
     * @param req
     * @return 记录数 total
     * @throws ManagerException
     */
    @RequestMapping(value = "/getDtlTotalByBillNo")
	public ResponseEntity<Integer> getDtlTotalByBillNo(HttpServletRequest req) throws ManagerException {
		String billNo = req.getParameter("billNo") == null ? "" : req.getParameter("billNo");
		int total = 0;
		if(StringUtils.isNotBlank(billNo)){
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("billNo", billNo);
			total = this.billCommonRegisterDtlManager.findCount(params);
		}
		return new ResponseEntity<Integer>(total, HttpStatus.OK);
	}

    @RequestMapping(value = "/find_list")
	@ResponseBody
	public Map<String, Object> findDltList(HttpServletRequest req, Model model)
			throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		int total = this.billCommonRegisterDtlManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, total);
		List<BillCommonRegisterDtl>  list = billCommonRegisterDtlManager.findByPage(page, null, null, params);
		BillCommonRegisterDtl dtl = new BillCommonRegisterDtl();

		List<BillCommonRegisterDtl> footer = new ArrayList<BillCommonRegisterDtl>();

		BigDecimal amount = new BigDecimal(0);
		BigDecimal noTaxAmount = new BigDecimal(0);
		BigDecimal taxAmount = new BigDecimal(0);
		int qty = 0;
		if(CollectionUtils.isNotEmpty(list)){
			for (BillCommonRegisterDtl billCommonRegisterDtl : list) {
				if (billCommonRegisterDtl.getQty() != null) {
					qty += billCommonRegisterDtl.getQty();
				}
				amount = amount.add(null == billCommonRegisterDtl.getInvoiceAmount() ? new BigDecimal(0)
						: billCommonRegisterDtl.getInvoiceAmount());
				noTaxAmount = noTaxAmount.add(null == billCommonRegisterDtl.getNoTaxAmount() ? new BigDecimal(0)
				: billCommonRegisterDtl.getNoTaxAmount());
				taxAmount = taxAmount.add(null == billCommonRegisterDtl.getTaxAmount() ? new BigDecimal(0)
				: billCommonRegisterDtl.getTaxAmount());
			}
			dtl.setInvoiceNo("合计");
			dtl.setInvoiceAmount(amount);
			dtl.setNoTaxAmount(noTaxAmount);
			dtl.setTaxAmount(taxAmount);
			dtl.setQty(qty);
			footer.add(dtl);
		}
    	obj.put("footer", footer);
		obj.put("total", total);
		obj.put("rows", list);
		return obj;
	}
    
}