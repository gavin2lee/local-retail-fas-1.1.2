package cn.wonhigh.retail.fas.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.enums.ValidateTypeEnums;
import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.InitialAmount;
import cn.wonhigh.retail.fas.common.vo.ValidateResultVo;
import cn.wonhigh.retail.fas.common.vo.ValidateVo;
import cn.wonhigh.retail.fas.manager.InitialAmountManager;
import cn.wonhigh.retail.fas.web.utils.ImportUtils;

import com.alibaba.dubbo.common.json.ParseException;
import com.yougou.logistics.base.common.annotation.ModuleVerify;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
/**
 * 期初应付金额
 * @author ouyang.zm
 * @date  2015-02-03 11:06:43
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
@RequestMapping("/hq_period_meet_money")
@ModuleVerify("40001019")
public class HqPeriodMeetMoneyController extends BaseController<InitialAmount> {
    @Resource
    private InitialAmountManager initialAmountManager;
    
    @Override
    public CrudInfo init() {
        return new CrudInfo("hq_period_meet_money/",initialAmountManager);
    }
    
//    /**
//	 * 修改开票申请付款状态
//	 */
//	@Override
//	@RequestMapping(value = "/put")
//	public ResponseEntity<InitialAmount> moditfy(InitialAmount type) throws ManagerException {
//		InitialAmount initialAmount = super.get(type).getBody();
//		initialAmount.setPayStatus(1);
//		initialAmount.setPayDate(new Date());
//		super.moditfy(initialAmount);
//		return new ResponseEntity<InitialAmount>(initialAmount, HttpStatus.OK);
//	}

	@Override
	public Map<String, Object> queryList(HttpServletRequest req, Model model) throws ManagerException {
		Map<String, Object> obj = new HashMap<String, Object>();
		int pageNo = StringUtils.isEmpty(req.getParameter("page")) ? 1 : Integer.parseInt(req.getParameter("page"));
		int pageSize = StringUtils.isEmpty(req.getParameter("rows")) ? 10 : Integer.parseInt(req.getParameter("rows"));
		Map<String, Object> params = builderParams(req, model);
		int count = initialAmountManager.findCount(params);
		SimplePage page = new SimplePage(pageNo, pageSize, count);
		List<InitialAmount> initialAmountList= initialAmountManager.findByPage(page, null, null, params);
		List<InitialAmount> footer = new ArrayList<InitialAmount>();
		
		InitialAmount countObj = new InitialAmount();
		countObj.setCompanyNo("合计");
		int qty = 0;
		BigDecimal cost = new BigDecimal(0);
		BigDecimal amount = new BigDecimal(0);
		for (InitialAmount initialAmount : initialAmountList) {
			qty += initialAmount.getQty();
			cost = cost.add(initialAmount.getCost());
			amount = amount.add(initialAmount.getAmount());
			
		}
		countObj.setAmount(amount);
		countObj.setCost(cost);
		countObj.setQty(qty);
		countObj.setPayStatusStr("");
		countObj.setPayStatus(-1);
		footer.add(countObj);
		obj.put("total", count);
		obj.put("rows", initialAmountList);
		obj.put("footer", footer);
		return obj;
	}

	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest req,InitialAmount modelType) throws Exception {
		ModelAndView mv = new ModelAndView("/print/import");
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			SystemUser loginUser = (SystemUser) req.getSession().getAttribute(PublicContains.SESSION_USER);
			String[] fieldNames = new String[] { "companyNo", "companyName", "supplierNo", "supplierName",
					"initialDate", "qty", "amount", "cost", "dueDate", "payStatusStr" };
			List<Object> lstItem = ImportUtils.getDataList(excelFile.getInputStream(), InitialAmount.class, fieldNames);
			List<ValidateVo> lstValidate = new ArrayList<ValidateVo>();
			lstValidate.add(new ValidateVo(-1, "companyNo", "", "公司编号", true));
			lstValidate.add(new ValidateVo(-1, "companyName", "", "公司名称", true));
			lstValidate.add(new ValidateVo(-1, "supplierNo", "", "供应商编码", true));
			lstValidate.add(new ValidateVo(-1, "supplierName", "", "供应商", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "initialDate", "", "日期", false));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "qty", "", "期初应付数量", true));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "amount", "", "期初应付金额", false));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.NUMBER.getTypeNo(), "cost", "", "期初牌价额", false));
			lstValidate.add(new ValidateVo(ValidateTypeEnums.DATE.getTypeNo(), "dueDate", "", "到期日", false));
			List<ValidateResultVo> listValidate = doValidateDetail(lstItem, lstValidate, InitialAmount.class);
			int count = 0;
			Date date = new Date();
			for (Object object : listValidate) {
				ValidateResultVo vo = (ValidateResultVo) object;
				if (vo.getPass() == YesNoEnum.YES.getValue()) {
					InitialAmount ninitialAmount = (InitialAmount) vo.getValidateObj();
					if("已付款".equals(ninitialAmount.getPayStatusStr())){
						ninitialAmount.setPayStatus(1);
					}else{
						ninitialAmount.setPayStatus(0);
					}
					ninitialAmount.setCreateTime(date);
					ninitialAmount.setCreateUser(loginUser.getUsername());
					this.add(ninitialAmount);
					count++;
				}
			}
			if (count > 0) {
				mv.addObject("success", "成功导入" + count + "条记录");
			} else {
				mv.addObject("error", "没有要导入的行！");
			}
			return mv;

		} catch (NumberFormatException e) {
			mv.addObject("error", "导入数据的开票申请不是数字格式！");
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} catch (ParseException e) {
			mv.addObject("error", "导入数据的生效时间不正确，格式需为yyyy-MM-dd或yyyy/MM/dd");
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		} catch (Exception e) {
			mv.addObject("error", "导入数据发生其他异常，请联系管理员");
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

	 /**
	 * 导入校验
	 * @param fileName
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	private List<ValidateResultVo> doValidateDetail(List<Object> lstItem, List<ValidateVo> lstValidate, Class clazz) throws Exception {
		List<ValidateResultVo> resultList = new ArrayList<ValidateResultVo>();
		if(!CollectionUtils.isEmpty(lstItem) && !CollectionUtils.isEmpty(lstValidate) ){
			for (int i = 0,iSize = lstItem.size(); i<iSize; i++) {
				ValidateResultVo resultVo = new ValidateResultVo();
				Object obj = lstItem.get(i);
				String errorInfo = "";
				String errorCode = resultVo.getErrorCode();
				String emptyCode = resultVo.getEmptyCode();
				if(StringUtils.isNotBlank(errorCode) || StringUtils.isNotBlank(emptyCode) ){
					resultVo.setPass(YesNoEnum.NO.getValue());
					if(StringUtils.isNotBlank(errorCode)){
						errorInfo = errorInfo.concat("无效编码：").concat(errorCode.substring(0, errorCode.length()-1).concat(";"));
					}
					if(StringUtils.isNotBlank(emptyCode)){
						errorInfo = errorInfo.concat("必填字段：").concat(emptyCode.substring(0, emptyCode.length()-1));
					}
				}
				if(StringUtils.isBlank(errorInfo)){
					resultVo.setPass(YesNoEnum.YES.getValue());
				}else{
					resultVo.setErrorInfo(errorInfo);
				}
				resultVo.setIndex(i+3);
				resultVo.setValidateObj(obj);
				resultList.add(resultVo);
			}
		}
		return resultList;
	}
}