package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDeductDto;
import cn.wonhigh.retail.fas.common.dto.GatherBillShopBalanceDiffDto;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupOrderPaywayDto;
import cn.wonhigh.retail.fas.common.dto.POSOcGroupRootCategoryDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOcGroupPromationDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOcGroupWildCardDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOrderParameterDto;
import cn.wonhigh.retail.fas.common.dto.POSOcPagingDto;
import cn.wonhigh.retail.fas.common.dto.POSOcSimplePageDto;
import cn.wonhigh.retail.fas.common.enums.ShopMallEnums;
import cn.wonhigh.retail.fas.common.model.BillShopBalance;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceBrand;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCateSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceCodeSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDaysaleSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDeduct;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceDiff;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceOperatelog;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceProSum;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceSet;
import cn.wonhigh.retail.fas.common.model.BillShopBalanceWildsaleSum;
import cn.wonhigh.retail.fas.common.model.CostCategorySetting;
import cn.wonhigh.retail.fas.common.model.ExpectCash;
import cn.wonhigh.retail.fas.common.model.MallDeductionSet;
import cn.wonhigh.retail.fas.common.model.RateBasic;
import cn.wonhigh.retail.fas.common.model.RateExpenseOperate;
import cn.wonhigh.retail.fas.common.model.RateExpenseRemind;
import cn.wonhigh.retail.fas.common.model.RateExpenseSporadic;
import cn.wonhigh.retail.fas.common.model.RatePro;
import cn.wonhigh.retail.fas.common.model.Shop;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;
import cn.wonhigh.retail.fas.common.model.ShopBrand;
import cn.wonhigh.retail.fas.common.model.SystemParamSet;
import cn.wonhigh.retail.fas.common.utils.BigDecimalUtil;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.ReflectUtil;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceBackMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceBrandMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceCateSumMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceCodeSumMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceDaysaleSumMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceDeductMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceDiffMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceProSumMapper;
import cn.wonhigh.retail.fas.dal.database.BillShopBalanceWildsaleSumMapper;
import cn.wonhigh.retail.fas.dal.database.RateExpenseOperateMapper;
import cn.wonhigh.retail.fas.dal.database.RateExpenseRemindMapper;
import cn.wonhigh.retail.fas.dal.database.RateExpenseSporadicMapper;
import cn.wonhigh.retail.fas.service.BillBacksectionSplitDtlService;
import cn.wonhigh.retail.fas.service.BillShopBalanceBrandService;
import cn.wonhigh.retail.fas.service.BillShopBalanceCateSumService;
import cn.wonhigh.retail.fas.service.BillShopBalanceCodeSumService;
import cn.wonhigh.retail.fas.service.BillShopBalanceDaysaleSumService;
import cn.wonhigh.retail.fas.service.BillShopBalanceDeductService;
import cn.wonhigh.retail.fas.service.BillShopBalanceDiffService;
import cn.wonhigh.retail.fas.service.BillShopBalanceOperatelogService;
import cn.wonhigh.retail.fas.service.BillShopBalanceProSumService;
import cn.wonhigh.retail.fas.service.BillShopBalanceService;
import cn.wonhigh.retail.fas.service.BillShopBalanceSetService;
import cn.wonhigh.retail.fas.service.BillShopBalanceWildsaleSumService;
import cn.wonhigh.retail.fas.service.CommonUtilService;
import cn.wonhigh.retail.fas.service.CostCategorySettingService;
import cn.wonhigh.retail.fas.service.ExpectCashService;
import cn.wonhigh.retail.fas.service.MallDeductionSetService;
import cn.wonhigh.retail.fas.service.RateBasicService;
import cn.wonhigh.retail.fas.service.RateExpenseOperateService;
import cn.wonhigh.retail.fas.service.RateExpenseRemindService;
import cn.wonhigh.retail.fas.service.RateExpenseSporadicService;
import cn.wonhigh.retail.fas.service.RateProService;
import cn.wonhigh.retail.fas.service.ShopBalanceDateService;
import cn.wonhigh.retail.fas.service.ShopService;
import cn.wonhigh.retail.fas.service.SystemParamSetService;
import cn.wonhigh.retail.mps.api.client.dto.finance.BalanceCallBackDto;
import cn.wonhigh.retail.mps.api.client.dto.finance.BalanceParamDto;
import cn.wonhigh.retail.mps.api.client.dto.finance.RateBasicDto;
import cn.wonhigh.retail.mps.api.client.dto.finance.RateExpenseOperateDto;
import cn.wonhigh.retail.mps.api.client.dto.finance.RateExpenseRemindDto;
import cn.wonhigh.retail.mps.api.client.dto.finance.RateExpenseSporadicDto;
import cn.wonhigh.retail.mps.api.client.dto.finance.RateProDto;
import cn.wonhigh.retail.mps.api.client.service.finance.BalanceRateApi;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderParameterDto;
import cn.wonhigh.retail.oc.common.api.dto.OcOrderParameterParentDto;
import cn.wonhigh.retail.oc.common.api.service.OrderMainApi;
import cn.wonhigh.retail.pos.api.client.dto.finance.ExpectCashDto;
import cn.wonhigh.retail.pos.api.client.dto.finance.ExpectCashParamsDto;
import cn.wonhigh.retail.pos.api.client.dto.util.PagingDto;
import cn.wonhigh.retail.pos.api.client.dto.util.SimplePageDto;
import cn.wonhigh.retail.pos.api.client.service.finance.ExpectCashApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;
import com.yougou.logistics.base.service.CodingRuleService;

/**
 * 请写出类的用途 与销售额、扣率有关的费用计算在生成结算单是动态生成；MPS 扣率有关的查询保存 获取POS销售数据维度汇总 预收款处理 差异处理
 * 更新状态反写
 * 
 * @author chen.mj
 * @date 2014-09-04 17:20:02
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
@Service("billShopBalanceManager")
class BillShopBalanceManagerImpl extends BaseCrudManagerImpl implements BillShopBalanceManager {

	Logger logger = Logger.getLogger(BillShopBalanceManagerImpl.class);

	@Resource
	private BillShopBalanceService billShopBalanceService;

	@Resource
	private ShopBalanceDateService shopBalanceDateService;

	@Resource
	private CodingRuleService codingRuleService;

	@Resource
	private ExpectCashService expectCashService;

	@Resource
	private OrderMainApi orderMainApi;

	@Resource
	private MallDeductionSetService mallDeductionSetService;

	@Resource
	private CostCategorySettingService costCategorySettingService;

	@Resource
	private ExpectCashApi expectCashApi;

	@Resource
	private BalanceRateApi balanceRateApi;

	@Resource
	private RateExpenseRemindService rateExpenseRemindService;

	@Resource
	private RateBasicService rateBasicService;

	@Resource
	private RateExpenseOperateService rateExpenseOperateService;

	@Resource
	private RateExpenseSporadicService rateExpenseSporadicService;

	@Resource
	private RateProService rateProService;

	@Resource
	private BillShopBalanceDiffService billShopBalanceDiffService;

	@Resource
	private BillShopBalanceDaysaleSumService billShopBalanceDaysaleSumService;

	@Resource
	private BillShopBalanceCateSumService billShopBalanceCateSumService;

	@Resource
	private BillShopBalanceWildsaleSumService billShopBalanceWildsaleSumService;

	@Resource
	private BillShopBalanceProSumService billShopBalanceProSumService;

	@Resource
	private BillShopBalanceCodeSumService billShopBalanceCodeSumService;

	@Resource
	private BillShopBalanceDeductService billShopBalanceDeductService;

	@Resource
	private BillShopBalanceBrandService billShopBalanceBrandService;

	@Resource
	private SystemParamSetService systemParamSetService;

	@Resource
	private OrderMainManager orderMainManager;

	@Resource
	private ShopService shopService;

	@Resource
	private BillShopBalanceSetService billShopBalanceSetService;

	@Resource
	private BillShopBalanceMapper billShopBalanceMapper;

	@Resource
	private BillShopBalanceCateSumMapper billShopBalanceCateSumMapper;

	@Resource
	private BillShopBalanceDaysaleSumMapper billShopBalanceDaysaleSumMapper;

	@Resource
	private BillShopBalanceDiffMapper billShopBalanceDiffMapper;

	@Resource
	private BillShopBalanceProSumMapper billShopBalanceProSumMapper;

	@Resource
	private BillShopBalanceDeductMapper billShopBalanceDeductMapper;

	@Resource
	private BillShopBalanceBackMapper billShopBalanceBackMapper;

	@Resource
	private RateExpenseOperateMapper rateExpenseOperateMapper;

	@Resource
	private BillShopBalanceWildsaleSumMapper billShopBalanceWildsaleSumMapper;

	@Resource
	private BillShopBalanceCodeSumMapper billShopBalanceCodeSumMapper;

	@Resource
	private BillShopBalanceBrandMapper billShopBalanceBrandMapper;

	@Resource
	private RateExpenseSporadicMapper rateExpenseSporadicMapper;

	@Resource
	private RateExpenseRemindMapper rateExpenseRemindMapper;

	@Resource
	private CommonUtilService commonUtilService;

	@Resource
	private BillBacksectionSplitDtlService billBacksectionSplitDtlService;

	@Resource
	private BillShopBalanceOperatelogService billShopBalanceOperatelogService;

	@Override
	public BaseCrudService init() {
		return billShopBalanceService;
	}

	public List<Map<String, String>> batchAdd(List<BillShopBalance> list) throws ManagerException {
		List<Map<String, String>> resuleList = new ArrayList<Map<String, String>>();
		StringBuffer billMsg = new StringBuffer();
		Map<String, String> msgMap = null;
		Integer count = 1;
		for (BillShopBalance billShopBalance : list) {
			msgMap = new HashMap<String, String>();
			BillShopBalance shopBalance = this.save(billShopBalance, null, null, null, null);
			// billMsg.append(shopBalance.getShopNo()+"-"+shopBalance.getShortName()+",结算月："+shopBalance.getMonth()+",单据编码："+shopBalance.getBalanceNo()+"; ");
			// billMsg.append(shopBalance.getErrorType()+shopBalance.getErrorInfo());
			msgMap.put("shopNo", shopBalance.getShopNo());
			msgMap.put("shortName", shopBalance.getShortName());
			msgMap.put("month", shopBalance.getMonth());
			msgMap.put("balanceNo", shopBalance.getBalanceNo());
			msgMap.put("errorType", shopBalance.getErrorType().toString());
			if (StringUtils.isNotEmpty(shopBalance.getErrorInfo())) {
				msgMap.put("errorInfo", shopBalance.getErrorInfo());
				// if(billShopBalance.getErrorInfo() != null &&
				// !"".equals(billShopBalance.getErrorInfo())){
				resuleList.add(msgMap);
			} else if (StringUtils.isEmpty(shopBalance.getErrorInfo())) {
				count = shopBalance.getErrorType();
				count++;
				msgMap.put("errorInfo", "恭喜你，成功生成" + shopBalance.getShopNo() + "," + shopBalance.getShortName() + ","
						+ shopBalance.getMonth() + "的结算单！");
				resuleList.add(msgMap);
			}
		}
		// msgMap.put("errorInfo", "恭喜你，成功生成"+count+"条结算单！");
		// resuleList.add(msgMap);
		return resuleList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillShopBalance save(BillShopBalance billShopBalance, List<BillShopBalanceDiff> lstDiff,
			List<BillShopBalanceDeduct> lstDeduct, List<BillShopBalanceBrand> lstBrand,
			List<BillShopBalanceDeduct> lstDeductAfter) throws ManagerException {
		try {
			int iCount = 0;
			// 初始化结算单对象基础数据
			billShopBalance = this.initBill(billShopBalance);
			// 校验结算单是否可生成，同时设置店铺等其他数据
			billShopBalance = this.checkBillAndInitOtherInfo(billShopBalance);
			if (StringUtils.isNotEmpty(billShopBalance.getErrorInfo())) {
				return billShopBalance;
			}
			// 提示保底计划的字符串
			StringBuffer sb = new StringBuffer();

			StringBuffer showRateExpenseRemind = new StringBuffer();
			// 处理接口数据
			billShopBalance = this.processApiData(billShopBalance);

			// 默认初始化结算差异 从相应的促销活动表写入结算差异 `bill_shop_balance_pro_sum` - >
			// `bill_shop_balance_diff`
			// 默认把上月未完成的费用、未处理的差异写入本次结算单
			this.processBalanceDiff(billShopBalance);

			// 处理页面新增的数据
			if (lstDiff != null && lstDiff.size() > 0) {
				for (BillShopBalanceDiff diff : lstDiff) {
					diff.setId(UUIDGenerator.getUUID());
					diff.setBalanceNo(billShopBalance.getBalanceNo());
					diff.setBillNo(codingRuleService.getSerialNo(BillShopBalanceDiff.class.getSimpleName()));
					billShopBalanceDiffService.add(diff);
				}
			}
			if (lstDeduct != null && lstDeduct.size() > 0) {
				for (BillShopBalanceDeduct deduct : lstDeduct) {
					deduct.setId(UUIDGenerator.getUUID());
					deduct.setBalanceNo(billShopBalance.getBalanceNo());
					deduct.setCompanyNo(billShopBalance.getCompanyNo());
					deduct.setCompanyName(billShopBalance.getCompanyName());
					deduct.setShopNo(billShopBalance.getShopNo());
					deduct.setShortName(billShopBalance.getShortName());
					deduct.setOrganNo(billShopBalance.getOrganNo());
					deduct.setOrganName(billShopBalance.getOrganName());
					deduct.setBsgroupsNo(billShopBalance.getBsgroupsNo());
					deduct.setBsgroupsName(billShopBalance.getBsgroupsName());
					deduct.setMallNo(billShopBalance.getMallNo());
					deduct.setMallName(billShopBalance.getMallName());
					deduct.setBalanceStartDate(billShopBalance.getBalanceStartDate());
					deduct.setBalanceEndDate(billShopBalance.getBalanceEndDate());
					deduct.setCostDeductType(ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId().byteValue());
					deduct.setGenerateType(ShopMallEnums.CostDeductGenerateType.PAGE_GENERATE.getRequestId().intValue());
					billShopBalanceDeductService.add(deduct);
				}
			}

			if (lstDeductAfter != null && lstDeductAfter.size() > 0) {
				for (BillShopBalanceDeduct deduct : lstDeductAfter) {
					deduct.setId(UUIDGenerator.getUUID());
					deduct.setBalanceNo(billShopBalance.getBalanceNo());
					deduct.setCompanyNo(billShopBalance.getCompanyNo());
					deduct.setCompanyName(billShopBalance.getCompanyName());
					deduct.setShopNo(billShopBalance.getShopNo());
					deduct.setShortName(billShopBalance.getShortName());
					deduct.setOrganNo(billShopBalance.getOrganNo());
					deduct.setOrganName(billShopBalance.getOrganName());
					deduct.setBsgroupsNo(billShopBalance.getBsgroupsNo());
					deduct.setBsgroupsName(billShopBalance.getBsgroupsName());
					deduct.setMallNo(billShopBalance.getMallNo());
					deduct.setMallName(billShopBalance.getMallName());
					deduct.setBalanceStartDate(billShopBalance.getBalanceStartDate());
					deduct.setBalanceEndDate(billShopBalance.getBalanceEndDate());
					deduct.setCostDeductType(ShopMallEnums.CostDeductType.AFTER_INVOICE.getRequestId().byteValue());
					deduct.setGenerateType(ShopMallEnums.CostDeductGenerateType.PAGE_GENERATE.getRequestId().intValue());
					billShopBalanceDeductService.add(deduct);
				}
			}

			if (lstBrand != null && lstBrand.size() > 0) {
				for (BillShopBalanceBrand brand : lstBrand) {
					brand.setId(UUIDGenerator.getUUID());
					brand.setBalanceNo(billShopBalance.getBalanceNo());
					brand.setBillNo(codingRuleService.getSerialNo(BillShopBalanceDiff.class.getSimpleName()));
					billShopBalanceBrandService.add(brand);
				}
			}

			// 处理预收款
			BigDecimal prepaymentAmount = new BigDecimal("0");
			BigDecimal usedPrepaymentAmount = new BigDecimal("0");
			billShopBalance.setPrepaymentAmount(prepaymentAmount);
			billShopBalance.setUsedPrepaymentAmount(usedPrepaymentAmount);

			Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
			systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
			systemParamSetParams.put("paramCode", "MS_ExpectCash_Pro");
			systemParamSetParams.put("status", 1);

			String value = getSystemParamSetValue(systemParamSetParams);

			if ("0".equals(value)) {// 参与结算 处理预收款的业务流程
				processExpectCash(billShopBalance);
			} else if ("1".equals(value)) {
				// 1表示不参与结算 不处理预收款的业务流程
			} else {
				// 没有配置参数，需要处理预收款的业务流程
				processExpectCash(billShopBalance);
			}

			prepaymentAmount = billShopBalance.getPrepaymentAmount();
			if (prepaymentAmount == null) {
				prepaymentAmount = BigDecimal.ZERO;
			}
			usedPrepaymentAmount = billShopBalance.getUsedPrepaymentAmount();
			if (usedPrepaymentAmount == null) {
				usedPrepaymentAmount = BigDecimal.ZERO;
			}

			BigDecimal mallNumberAmountLog = new BigDecimal("0");
			BigDecimal mallBillingAmountLog = new BigDecimal("0");

			// 重新计算刷新单据头的销售收入 系统收入 + 预收款金额-预收款当期冲销金额 预收款当期冲销金额 为负数所以+
			billShopBalance.setSystemSalesAmount(billShopBalance.getSystemSalesAmount().add(prepaymentAmount)
					.add(usedPrepaymentAmount));

			// 删除记录商场报数 开票金额 生成读取
			Map<String, Object> operatelogParams = new HashMap<String, Object>();
			operatelogParams.put("shopNo", billShopBalance.getShopNo());
			operatelogParams.put("month", billShopBalance.getMonth());
			operatelogParams.put("startDate", billShopBalance.getBalanceStartDate());
			operatelogParams.put("endDate", billShopBalance.getBalanceEndDate());
			operatelogParams.put("balanceNo", "balanceNo");
			List<BillShopBalanceOperatelog> billShopBalanceOperatelogList = billShopBalanceOperatelogService.findByBiz(
					null, operatelogParams);
			if (!CollectionUtils.isEmpty(billShopBalanceOperatelogList)) {
				mallNumberAmountLog = billShopBalanceOperatelogList.get(0).getMallNumberAmount();
				mallBillingAmountLog = billShopBalanceOperatelogList.get(0).getMallBillingAmount();
			}

			BigDecimal mallNumberAmount = billShopBalance.getMallNumberAmount();
			if (mallNumberAmount == null) {
				billShopBalance.setMallNumberAmount(mallNumberAmountLog);
			}

			BigDecimal mallBillingAmount = billShopBalance.getMallBillingAmount();
			if (mallBillingAmount == null) {
				billShopBalance.setMallBillingAmount(mallBillingAmountLog);
			}

			// billShopBalance.setMallNumberAmount(mallNumberAmount);
			// billShopBalance.setMallBillingAmount(mallBillingAmount);

			// 门店结算差异商场报数重置单头的商场报数 0-不计算重置,默认 1-计算重置
			Map<String, Object> reMallNumberDiffParams = new HashMap<String, Object>();
			reMallNumberDiffParams.put("businessOrganNo", billShopBalance.getCompanyNo());
			reMallNumberDiffParams.put("paramCode", "MS_ReMall_Number_DiffNum");
			reMallNumberDiffParams.put("status", 1);

			String reMallNumberDiff = getSystemParamSetValue(reMallNumberDiffParams);

			if ("1".equals(reMallNumberDiff)) { // 1-计算重置
				processReMallNumberDiff(billShopBalance);
			}

			// 数据计算规则逻辑
			this.getNumDataCalc(billShopBalance);

			// 预估的商场结算单的商场报数和商场开票金额默认等于系统收入和系统开票金额 报数差异 结算差异总额 就是0
			// 扣费总额 mallDeductAmount
			if (ShopMallEnums.BalanceType.ESTIMATE_BALANCE.getRequestId().intValue() == billShopBalance
					.getBalanceType().intValue()) {
				billShopBalance.setMallNumberAmount(billShopBalance.getSystemSalesAmount());
				billShopBalance.setMallBillingAmount(billShopBalance.getSystemBillingAmount());
				billShopBalance.setSalesDiffamount(billShopBalance.getSystemSalesAmount().subtract(
						billShopBalance.getMallNumberAmount())); // 报数差异=系统收入-商场报数
				billShopBalance.setBalanceDiffAmount(billShopBalance.getSystemBillingAmount().subtract(
						billShopBalance.getMallBillingAmount()));
				billShopBalance.setMallDeductAmount(billShopBalance.getSystemSalesAmount().subtract(
						billShopBalance.getMallBillingAmount()));
			}

			billShopBalance.setStatus(1);

			// 门店品牌从 pos销售单取 bill_shop_balance_cate_sum
			Map<String, Object> cateSumParams = new HashMap<String, Object>();
			cateSumParams.put("balanceNo", billShopBalance.getBalanceNo());
			List<BillShopBalanceCateSum> cateSum = billShopBalanceCateSumService.findBrandShopCate(cateSumParams);// .findByBiz(null,
																													// cateSumParams);
			if (cateSum != null && cateSum.size() > 0) {
				for (BillShopBalanceCateSum model : cateSum) {
					if (model != null) {
						String brandNo = model.getBrandNo();
						if (null != brandNo && brandNo.length() > 100) {
							billShopBalance.setBrandNo(brandNo.substring(0, 100));
						} else
							billShopBalance.setBrandNo(brandNo);

						if (null != model.getBrandName() && model.getBrandName().length() > 250) {
							billShopBalance.setBrandName(model.getBrandName().substring(0, 250));
						} else
							billShopBalance.setBrandName(model.getBrandName());
					}
				}
			} else {
				// 设置店铺相关数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("shopNo", billShopBalance.getShopNo());
				ShopBrand shopBrand = null;
				try {
					shopBrand = getShopBrandInfo(params);
				} catch (ServiceException e) {
					throw new Exception(e.getMessage(), e);
				}
				if (shopBrand != null && !"".equals(shopBrand)) {
					billShopBalance.setBrandNo(shopBrand.getBrandNo());
					billShopBalance.setBrandName(shopBrand.getName());
				}
			}

			// 处理综合店结算 品牌MAP扣费
			this.processBalanceBrandData(billShopBalance);

			// 大类分摊
			Map<String, Object> systemParamSetParamsIas = new HashMap<String, Object>();
			systemParamSetParamsIas.put("businessOrganNo", billShopBalance.getCompanyNo());
			systemParamSetParamsIas.put("paramCode", "MS_InvoiceAmount_Share");
			systemParamSetParamsIas.put("status", 1);

			String valueIas = getSystemParamSetValue(systemParamSetParamsIas);

			if ("0".equals(valueIas)) {// 0不分摊,从综合店读取
				procssShopBalanceBrandCate(billShopBalance, cateSumParams);
			} else if ("1".equals(valueIas)) {
				// 1分摊,从单据头上比例分摊
				updateShopBalanceBrandCateShare(billShopBalance, cateSumParams);
			} else if ("2".equals(valueIas)) {
				// 2分摊,根据公式,大类销售金额 *（1-实际扣率）
				updateShopBalanceBrandCateCalc(billShopBalance, cateSumParams);
			} else {
				// 没有配置参数，需要处理0不分摊,从综合店读取
				procssShopBalanceBrandCate(billShopBalance, cateSumParams);
			}
     
			// ADD  20161020  处理0.01差异
			billShopBalanceCateSumService.modifyCateSumAmountDiff(cateSumParams);
			
			// 保存结算单
			iCount = billShopBalanceService.add(billShopBalance);

			if (iCount <= 0) {
				billShopBalance.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
				billShopBalance.setErrorInfo("保存失败，没有结算单生成，请检查数据的有效性完整性；");
				return billShopBalance;
			}

			// 更新接口数据
			this.modifyApiData(billShopBalance);

			if (StringUtils.isNotEmpty(billShopBalance.getErrorInfo())) {
				sb.append(billShopBalance.getErrorInfo() + "\n");
			}

			// 校验数据
			Map<String, String> msgMap = this.validateDataBalance(billShopBalance);
			if (msgMap != null && msgMap.size() > 0) {
				sb.append(msgMap.get("errorMsg"));
				showRateExpenseRemind.append(msgMap.get("showRateExpenseRemind"));
				// billShopBalance.setErrorInfo(msgMap.get("errorMsg"));
				// throw new ManagerException(msgMap.get("errorMsg"));
			}
			billShopBalance.setErrorType(ShopMallEnums.ErrorType.SUCCESS.getRequestId());
			billShopBalance.setErrorInfo(sb.toString());
			billShopBalance.setShowRateExpenseRemind(showRateExpenseRemind.toString());
			// 设置收款差异金额
			BigDecimal thePaymentAmount = billBacksectionSplitDtlService.sumPaymentAmount(billShopBalance
					.getBalanceNo());
			BillShopBalanceDeduct shopBalanceDeduct = getCostDeductTypeAmount(billShopBalance, null);
			BigDecimal deductAmount = new BigDecimal("0");
			BigDecimal actualAmount = new BigDecimal("0");
			if (shopBalanceDeduct != null) {
				deductAmount = shopBalanceDeduct.getDeductAmount();
				actualAmount = shopBalanceDeduct.getActualAmount();
			}
			// 收款差异=商场开票金额-票后账扣-回款金额
			BigDecimal differenceAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(),
					thePaymentAmount).subtract(actualAmount);
			billShopBalance.setDifferenceAmount(differenceAmount);

			// 应返款金额 =商场开票金额-票后账扣
			BigDecimal returnedAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(), actualAmount);
			billShopBalance.setReturnedAmount(returnedAmount);

			return billShopBalance;
		} catch (Exception e) {
			logger.error("保存结算单失败。", e);
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 修改结算单
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @param deductList
	 *            票前费用页签对应的数据
	 * @param diffDataMap
	 *            结算差异页签对应的数据
	 * @return 修改后的结算单对象
	 * @throws ManagerException
	 *             异常
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public BillShopBalance updateBill(BillShopBalance billShopBalance,
			Map<CommonOperatorEnum, List<BillShopBalanceDeduct>> deductDataMap,
			Map<CommonOperatorEnum, List<BillShopBalanceDiff>> diffDataMap,
			Map<CommonOperatorEnum, List<BillShopBalanceBrand>> brandDataMap,
			Map<CommonOperatorEnum, List<BillShopBalanceDeduct>> deductAfterDataMap,
			Map<CommonOperatorEnum, List<BillShopBalanceCateSum>> categoryDataMap) throws ManagerException {
		try {
			// 处理票前费用页面操作的数据
			List<BillShopBalanceDeduct> lstDeductInsert = deductDataMap.get(CommonOperatorEnum.INSERTED);
			if (lstDeductInsert != null && lstDeductInsert.size() > 0) {
				BillShopBalance billBalance = billShopBalanceService.findById(billShopBalance);
				if (billBalance != null) {
					for (BillShopBalanceDeduct deduct : lstDeductInsert) {
						deduct.setId(UUIDGenerator.getUUID());
						deduct.setBalanceNo(billBalance.getBalanceNo());
						deduct.setCompanyNo(billBalance.getCompanyNo());
						deduct.setCompanyName(billBalance.getCompanyName());
						deduct.setShopNo(billBalance.getShopNo());
						deduct.setShortName(billBalance.getShortName());
						deduct.setOrganNo(billBalance.getOrganNo());
						deduct.setOrganName(billBalance.getOrganName());
						deduct.setBsgroupsNo(billBalance.getBsgroupsNo());
						deduct.setBsgroupsName(billBalance.getBsgroupsName());
						deduct.setMallNo(billBalance.getMallNo());
						deduct.setMallName(billBalance.getMallName());
						deduct.setBalanceStartDate(billBalance.getBalanceStartDate());
						deduct.setBalanceEndDate(billBalance.getBalanceEndDate());
						deduct.setCostDeductType(ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId().byteValue());
						deduct.setDeductType(ShopMallEnums.DeductType.GD.getRequestId().byteValue());
						deduct.setGenerateType(ShopMallEnums.CostDeductGenerateType.PAGE_GENERATE.getRequestId()
								.intValue());
						// 品牌
						// Map<String, Object> params = new HashMap<String,
						// Object>();
						// params.put("shopNo", billBalance.getShopNo());
						// ShopBrand shopBrand = null;
						// try {
						// shopBrand = getShopBrandInfo(params);
						// } catch (ServiceException e) {
						// throw new ManagerException(e.getMessage(), e);
						// }
						// if(shopBrand != null && !"".equals(shopBrand)){
						// deduct.setBrandNo(shopBrand.getBrandNo());
						// deduct.setBrandName(shopBrand.getName());
						// }
						// 总账 会计科目
						Map<String, Object> costSetParams = new HashMap<String, Object>();
						costSetParams.put("companyNo", billShopBalance.getCompanyNo());
						costSetParams.put("code", lstDeductInsert.get(0).getDeductionCode());
						List<CostCategorySetting> costCategorySettingList = costCategorySettingService.findByBiz(null,
								costSetParams);
						if (costCategorySettingList != null && costCategorySettingList.size() > 0) {
							deduct.setCostCateCode(costCategorySettingList.get(0).getCode());
							deduct.setCostCateName(costCategorySettingList.get(0).getName());
							deduct.setAccountsNo(costCategorySettingList.get(0).getAccountsNo());
						}
						deduct.setDeductAmount(deduct.getDeductAmount());// getDeductAmount(params,rateExpenseSporadic,billShopBalanceDeduct));
																			// //扣费金额
																			// 要处理计算
						deduct.setActualAmount(deduct.getActualAmount());// getDeductAmount(params,rateExpenseSporadic,billShopBalanceDeduct));

						BigDecimal deductAmount = deduct.getDeductAmount();
						BigDecimal actualAmount = deduct.getActualAmount();
						if (deductAmount == null) {
							deductAmount = BigDecimal.valueOf(0);
						}
						if (actualAmount == null) {
							actualAmount = BigDecimal.valueOf(0);
						}

						deduct.setDiffAmount(actualAmount.subtract(deductAmount));
						if (deduct.getDiffAmount().compareTo(BigDecimal.ZERO) == 0) {
							deduct.setProcessStatus((byte) 2);
						} else {
							deduct.setProcessStatus((byte) 1);
						}
					}
				}
			}
			billShopBalanceDeductService.save(deductDataMap);

			List<BillShopBalanceDeduct> lstDeductAfterInsert = deductAfterDataMap.get(CommonOperatorEnum.INSERTED);
			if (lstDeductAfterInsert != null && lstDeductAfterInsert.size() > 0) {
				BillShopBalance billBalance = billShopBalanceService.findById(billShopBalance);
				if (billBalance != null) {
					for (BillShopBalanceDeduct deduct : lstDeductAfterInsert) {
						deduct.setId(UUIDGenerator.getUUID());
						deduct.setBalanceNo(billBalance.getBalanceNo());
						deduct.setCompanyNo(billBalance.getCompanyNo());
						deduct.setCompanyName(billBalance.getCompanyName());
						deduct.setShopNo(billBalance.getShopNo());
						deduct.setShortName(billBalance.getShortName());
						deduct.setOrganNo(billBalance.getOrganNo());
						deduct.setOrganName(billBalance.getOrganName());
						deduct.setBsgroupsNo(billBalance.getBsgroupsNo());
						deduct.setBsgroupsName(billBalance.getBsgroupsName());
						deduct.setMallNo(billBalance.getMallNo());
						deduct.setMallName(billBalance.getMallName());
						deduct.setBalanceStartDate(billBalance.getBalanceStartDate());
						deduct.setBalanceEndDate(billBalance.getBalanceEndDate());
						deduct.setCostDeductType(ShopMallEnums.CostDeductType.AFTER_INVOICE.getRequestId().byteValue());
						deduct.setDeductType(ShopMallEnums.DeductType.GD.getRequestId().byteValue());
						deduct.setGenerateType(ShopMallEnums.CostDeductGenerateType.PAGE_GENERATE.getRequestId()
								.intValue());
						// 品牌
						// Map<String, Object> params = new HashMap<String,
						// Object>();
						// params.put("shopNo", billBalance.getShopNo());
						// ShopBrand shopBrand = null;
						// try {
						// shopBrand = getShopBrandInfo(params);
						// } catch (ServiceException e) {
						// throw new ManagerException(e.getMessage(), e);
						// }
						// if(shopBrand != null && !"".equals(shopBrand)){
						// deduct.setBrandNo(shopBrand.getBrandNo());
						// deduct.setBrandName(shopBrand.getName());
						// }
						// 总账 会计科目
						Map<String, Object> costSetParams = new HashMap<String, Object>();
						costSetParams.put("companyNo", billShopBalance.getCompanyNo());
						costSetParams.put("code", lstDeductAfterInsert.get(0).getDeductionCode());
						List<CostCategorySetting> costCategorySettingList = costCategorySettingService.findByBiz(null,
								costSetParams);
						if (costCategorySettingList != null && costCategorySettingList.size() > 0) {
							deduct.setCostCateCode(costCategorySettingList.get(0).getCode());
							deduct.setCostCateName(costCategorySettingList.get(0).getName());
							deduct.setAccountsNo(costCategorySettingList.get(0).getAccountsNo());
						}
						deduct.setDeductAmount(deduct.getDeductAmount());// getDeductAmount(params,rateExpenseSporadic,billShopBalanceDeduct));
																			// //扣费金额
																			// 要处理计算
						deduct.setActualAmount(deduct.getActualAmount());// getDeductAmount(params,rateExpenseSporadic,billShopBalanceDeduct));

						BigDecimal deductAmount = deduct.getDeductAmount();
						BigDecimal actualAmount = deduct.getActualAmount();
						if (deductAmount == null) {
							deductAmount = BigDecimal.valueOf(0);
						}
						if (actualAmount == null) {
							actualAmount = BigDecimal.valueOf(0);
						}

						deduct.setDiffAmount(actualAmount.subtract(deductAmount));
						if (deduct.getDiffAmount().compareTo(BigDecimal.ZERO) == 0) {
							deduct.setProcessStatus((byte) 2);
						} else {
							deduct.setProcessStatus((byte) 1);
						}
					}
				}
			}
			billShopBalanceDeductService.save(deductAfterDataMap);

			List<BillShopBalanceDiff> lstInsert = diffDataMap.get(CommonOperatorEnum.INSERTED);
			if (lstInsert != null && lstInsert.size() > 0) {
				for (BillShopBalanceDiff diff : lstInsert) {
					diff.setId(UUIDGenerator.getUUID());
					diff.setBillNo(codingRuleService.getSerialNo(BillShopBalanceDiff.class.getSimpleName()));
				}
			}
			// 处理结算差异页签的数据
			billShopBalanceDiffService.save(diffDataMap);

			List<BillShopBalanceBrand> lstBrandInsert = brandDataMap.get(CommonOperatorEnum.INSERTED);
			if (lstBrandInsert != null && lstBrandInsert.size() > 0) {
				for (BillShopBalanceBrand brand : lstBrandInsert) {
					brand.setId(UUIDGenerator.getUUID());
					brand.setBillNo(codingRuleService.getSerialNo(BillShopBalanceDiff.class.getSimpleName()));
					brand.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(billShopBalance.getCompanyNo()));
					brand.setBalanceNo(billShopBalance.getBalanceNo());
				}
			}

			billShopBalanceBrandService.save(brandDataMap);

			billShopBalanceCateSumService.save(categoryDataMap);

			BillShopBalance billBalanceShop = billShopBalanceService.findById(billShopBalance);
			billShopBalance.setBalanceType(billBalanceShop.getBalanceType());

			// 门店结算差异商场报数重置单头的商场报数 0-不计算重置,默认 1-计算重置
			Map<String, Object> reMallNumberDiffParams = new HashMap<String, Object>();
			reMallNumberDiffParams.put("businessOrganNo", billShopBalance.getCompanyNo());
			reMallNumberDiffParams.put("paramCode", "MS_ReMall_Number_DiffNum");
			reMallNumberDiffParams.put("status", 1);

			String reMallNumberDiff = getSystemParamSetValue(reMallNumberDiffParams);

			if ("1".equals(reMallNumberDiff)) { // 1-计算重置
				processReMallNumberDiff(billShopBalance);
			}

			billShopBalance = this.getNumDataCalc(billShopBalance);

			int iCount = billShopBalanceService.modifyById(billShopBalance);
			try {
				if (iCount < 1) {
					return billShopBalance;
				}
			} catch (Exception e) {
				logger.error("shopBalance getNumDataCalc", e);
				throw new ManagerException(e.getMessage(), e);
			}

			// 由于录入了结算差异及费用 要重新刷新综合店结算模块 重新刷新大类的计算
			// 处理综合店结算 品牌MAP扣费
			billShopBalance = billShopBalanceService.findById(billShopBalance);
			this.updateBalanceBrandData(billShopBalance);

			// 大类分摊
			Map<String, Object> cateSumParams = new HashMap<String, Object>();
			cateSumParams.put("balanceNo", billShopBalance.getBalanceNo());

			Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
			systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
			systemParamSetParams.put("paramCode", "MS_InvoiceAmount_Share");
			systemParamSetParams.put("status", 1);

			String value = getSystemParamSetValue(systemParamSetParams);

			if ("0".equals(value)) {// 0不分摊,从综合店读取
				updateShopBalanceBrandCate(billShopBalance, cateSumParams);
			} else if ("1".equals(value)) {
				// 1分摊,从单据头上比例分摊
				updateShopBalanceBrandCateShareBill(billShopBalance, cateSumParams);
			} else if ("2".equals(value)) {
				// 2分摊,根据公式,大类销售金额 *（1-实际扣率）
				updateShopBalanceBrandCateCalc(billShopBalance, cateSumParams);
			} else {
				// 没有配置参数，需要处理0不分摊,从综合店读取
				updateShopBalanceBrandCate(billShopBalance, cateSumParams);
			}

			// ADD  20161020  处理0.01差异
			billShopBalanceCateSumService.modifyCateSumAmountDiff(cateSumParams);
			
			BillShopBalance billShopBalancediff = billShopBalanceService.findById(billShopBalance);

			// 设置收款差异金额

			BigDecimal thePaymentAmount = billBacksectionSplitDtlService.sumPaymentAmount(billShopBalance.getBalanceNo());
			if (thePaymentAmount == null) {
				thePaymentAmount = BigDecimal.valueOf(0);
			}
			BillShopBalanceDeduct shopBalanceDeduct = getCostDeductTypeAmount(billShopBalance, null);
			BigDecimal deductAmount = new BigDecimal("0");
			BigDecimal actualAmount = new BigDecimal("0");
			if (shopBalanceDeduct != null) {
				deductAmount = shopBalanceDeduct.getDeductAmount();
				actualAmount = shopBalanceDeduct.getActualAmount();
			}
			// 收款差异=商场开票金额-票后账扣-回款金额
			BigDecimal differenceAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(),
					thePaymentAmount).subtract(actualAmount);
			billShopBalance.setDifferenceAmount(differenceAmount);

			// 应返款金额 =商场开票金额-票后账扣
			BigDecimal returnedAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(), actualAmount);
			billShopBalance.setReturnedAmount(returnedAmount);
			// BigDecimal differenceAmount =
			// BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(),
			// thePaymentAmount);
			// billShopBalance.setDifferenceAmount(differenceAmount);

			// 门店品牌从 pos销售单取 bill_shop_balance_cate_sum
			// Map<String, Object> cateSumParams = new HashMap<String,
			// Object>();
			// cateSumParams.put("balanceNo", billShopBalance.getBalanceNo());
			// 重新刷新明细大类汇总
			// procssShopBalanceBrandCate(billShopBalance,cateSumParams);

			// List<BillShopBalanceCateSum> cateSum =
			// billShopBalanceCateSumService.findByBiz(null, cateSumParams);
			// if(cateSum != null && cateSum.size() > 0) {
			// //for(BillShopBalanceCateSum model : cateSum) {
			// // billShopBalance.setBrandNo(model.getBrandNo());
			// // billShopBalance.setBrandName(model.getBrandName());
			//
			// }
			// }

			// billShopBalance =
			// billShopBalanceService.findById(billShopBalance);
			// 校验数据
			Map<String, String> msgMap = this.validateDataBalance(billShopBalance);
			if (msgMap != null && msgMap.size() > 0) {
				billShopBalance.setErrorInfo(msgMap.get("errorMsg"));
				billShopBalance.setShowRateExpenseRemind(msgMap.get("showRateExpenseRemind"));
				billShopBalance.setErrorType(ShopMallEnums.ErrorType.SUCCESS.getRequestId());
			}
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
		return billShopBalance;
	}

	private int updateShopBalanceBrandCate(BillShopBalance billShopBalance, Map<String, Object> cateSumParams)
			throws Exception {
		int count = 0;
		List<BillShopBalanceCateSum> brandCateSum = billShopBalanceCateSumService.findByBiz(null, cateSumParams);
		if (brandCateSum != null && brandCateSum.size() > 0) {
			// for(BillShopBalanceCateSum model : brandCateSum) {
			// cateSumParams.put("brandNo",model.getBrandNo());
			// List<BillShopBalanceBrand> billShopBalanceBrandList =
			// billShopBalanceBrandService.findByBiz(null, cateSumParams);
			// for(BillShopBalanceBrand billShopBalanceBrand :
			// billShopBalanceBrandList) {
			BigDecimal systemDeductAmount = new BigDecimal("0");
			BigDecimal systemDeductAmountDc = new BigDecimal("0");
			BigDecimal saleAmount = new BigDecimal("0");
			BigDecimal zeanAmount = new BigDecimal("0");
			for (int i = 0; i < brandCateSum.size(); i++) {
				BillShopBalanceCateSum bill = brandCateSum.get(i);
				bill.setId(brandCateSum.get(i).getId());
				systemDeductAmount = brandCateSum.get(i).getDeductAmount();
				systemDeductAmountDc = brandCateSum.get(i).getSystemDeductAmount();
				saleAmount = brandCateSum.get(i).getSaleAmount();
				bill.setSaleAmount(saleAmount);
				bill.setSystemDeductAmount(systemDeductAmountDc);
				bill.setUsedPrepaymentAmount(brandCateSum.get(i).getUsedPrepaymentAmount());
				bill.setDiffAmount(brandCateSum.get(i).getDiffAmount());
				bill.setUsedPrepaymentDeductAmount(brandCateSum.get(i).getUsedPrepaymentDeductAmount());
				Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
				systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
				systemParamSetParams.put("paramCode", "MS_ExpectCash_Pro");
				systemParamSetParams.put("status", 1);

				String value = getSystemParamSetValue(systemParamSetParams);
				if ("0".equals(value)) {// 参与结算 处理预收款的业务流程
					bill = processExpectCashCateBrand(billShopBalance, bill);
				} else if ("1".equals(value)) {
				} else
					// 默认参与计算
					bill = processExpectCashCateBrand(billShopBalance, bill);
				bill.setPrepaymentAmount(brandCateSum.get(i).getPrepaymentAmount());
				bill.setPrepaymentDeductAmount(brandCateSum.get(i).getPrepaymentDeductAmount());
				// bill.setDeductAmount(billShopBalanceBrand.getDeductDiffAmount());
				this.getNumDataCalcBrandCate(bill, billShopBalance, cateSumParams);
				billShopBalanceCateSumService.modifySubInvoiceAmountByRound(cateSumParams);
				count = billShopBalanceCateSumService.modifyById(bill);
				if (billShopBalance.getMallBillingAmount() != null
						&& billShopBalance.getMallBillingAmount() != zeanAmount) {
					count += billShopBalanceCateSumService.modifySubInvoiceAmountByBrand(cateSumParams);
				}
				bill.setSystemDeductAmount(zeanAmount);
			}
			// }
			// }
		}
		return count;
	}

	// 新增调用
	private int updateShopBalanceBrandCateShare(BillShopBalance billShopBalance, Map<String, Object> cateSumParams)
			throws Exception {
		int count = 0;
		BigDecimal zeanAmount = new BigDecimal(0);
		// 要先处理是否有预收款的业务，先更新预收款、冲销预收款
		procssShopBalanceBrandCateShare(billShopBalance, cateSumParams);
		billShopBalanceCateSumService.modifySubInvoiceAmountByRound(cateSumParams);
		count = billShopBalanceCateSumService.modifyInvoiceAmountShareByNo(cateSumParams);
		if (billShopBalance.getMallBillingAmount() != null && billShopBalance.getMallBillingAmount() != zeanAmount) {
			count += billShopBalanceCateSumService.modifySubInvoiceAmountByBrand(cateSumParams);
		}
		return count;
	}

	// 修改调用
	private int updateShopBalanceBrandCateShareBill(BillShopBalance billShopBalance, Map<String, Object> cateSumParams)
			throws Exception {
		int count = 0;
		BigDecimal zeanAmount = new BigDecimal(0);
		billShopBalanceCateSumService.modifySubInvoiceAmountByRound(cateSumParams);
		count = billShopBalanceCateSumService.modifyInvoiceAmountShareByNo(cateSumParams);
		if (billShopBalance.getMallBillingAmount() != null && billShopBalance.getMallBillingAmount() != zeanAmount) {
			count += billShopBalanceCateSumService.modifySubInvoiceAmountByBrand(cateSumParams);
		}
		return count;
	}

	private int updateShopBalanceBrandCateCalc(BillShopBalance billShopBalance, Map<String, Object> cateSumParams)
			throws Exception {
		int count = 0;
		BigDecimal rateNum = new BigDecimal("100");
		BigDecimal num = new BigDecimal("1");
		BigDecimal zeanAmount = new BigDecimal(0);
		BigDecimal actualRate = new BigDecimal(0);
		cateSumParams.put("balanceNo", billShopBalance.getBalanceNo());
		List<BillShopBalanceCateSum> cateSum = billShopBalanceCateSumService.findByBiz(null, cateSumParams);
		if (cateSum != null && cateSum.size() > 0) {
			for (int i = 0; i < cateSum.size(); i++) {
				BillShopBalanceCateSum model = cateSum.get(i);
				// bill.setId(brandCateSum.get(i).getId());
				// for(BillShopBalanceCateSum model : cateSum) {
				if (model != null) {
					if (billShopBalance.getSystemSalesAmount().compareTo(new BigDecimal("0")) != 0) {
						// 实际扣率： （系统收入-商场开票金额）/系统收入 | 实际扣率要计算 实际扣率 =
						// （系统销售-开票金额）/系统销售
						actualRate = (billShopBalance.getSystemSalesAmount().subtract(billShopBalance
								.getMallBillingAmount())).divide(billShopBalance.getSystemSalesAmount(), 8,
								BigDecimal.ROUND_HALF_UP);
					}
					// model.setBillingAmount(model.getSaleAmount().multiply(num.subtract(billShopBalance.getActualRate().divide(rateNum))));
					model.setBillingAmount(BigDecimalUtil.format(
							model.getSaleAmount().multiply(num.subtract(actualRate)), BigDecimalUtil.POINT_2_PATTERN));
				}
				count = billShopBalanceCateSumService.modifyById(model);

				billShopBalanceCateSumService.modifySubInvoiceAmountByRound(cateSumParams);
				if (billShopBalance.getMallBillingAmount() != null
						&& billShopBalance.getMallBillingAmount() != zeanAmount) {
					count += billShopBalanceCateSumService.modifySubInvoiceAmountByBrand(cateSumParams);
				}
			}
		}
		return count;
	}

	// 重新刷新大类页签的 预收金额 冲销预收款
	private int procssShopBalanceBrandCateShare(BillShopBalance billShopBalance, Map<String, Object> cateSumParams)
			throws Exception {
		int count = 0;
		List<BillShopBalanceCateSum> brandCateSum = billShopBalanceCateSumService.findByBiz(null, cateSumParams);
		if (brandCateSum != null && brandCateSum.size() > 0) {
			for (int i = 0; i < brandCateSum.size(); i++) {
				BillShopBalanceCateSum bill = brandCateSum.get(i);
				bill.setId(brandCateSum.get(i).getId());
				Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
				systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
				systemParamSetParams.put("paramCode", "MS_ExpectCash_Pro");
				systemParamSetParams.put("status", 1);

				String value = getSystemParamSetValue(systemParamSetParams);
				if ("0".equals(value)) {// 参与结算 处理预收款的业务流程
					bill = processExpectCashCateBrandShare(billShopBalance, bill);
				} else if ("1".equals(value)) {
				} else
					// 默认参与计算
					bill = processExpectCashCateBrandShare(billShopBalance, bill);

				BigDecimal prepaymentAmount = bill.getPrepaymentAmount();
				if (prepaymentAmount == null) {
					prepaymentAmount = BigDecimal.ZERO;
				}
				BigDecimal usedDeductAmount1 = bill.getDiffAmount();
				if (usedDeductAmount1 == null) {
					usedDeductAmount1 = BigDecimal.ZERO;
				}

				bill.setPrepaymentAmount(prepaymentAmount);
				bill.setUsedPrepaymentAmount(usedDeductAmount1);
				bill.setDiffAmount(usedDeductAmount1);

				// this.getNumDataCalcBrandCate(bill, cateSumParams);
				count = billShopBalanceCateSumService.modifyById(bill);
			}
			// }
		}
		return count;
	}

	// 获取商场开票金额 重新刷新大类页签的 预收金额 冲销预收款 系统扣费 票前费用 结算差异 扣费总额 开票金额
	private int procssShopBalanceBrandCate(BillShopBalance billShopBalance, Map<String, Object> cateSumParams)
			throws Exception {
		int count = 0;
		BigDecimal zeanAmount = new BigDecimal(0);
		List<BillShopBalanceCateSum> brandCateSum = billShopBalanceCateSumService.findByBiz(null, cateSumParams);
		if (brandCateSum != null && brandCateSum.size() > 0) {
			// for(BillShopBalanceCateSum model : brandCateSum) {
			for (int i = 0; i < brandCateSum.size(); i++) {
				BillShopBalanceCateSum bill = brandCateSum.get(i);
				bill.setId(brandCateSum.get(i).getId());
				BigDecimal systemDeductAmount = brandCateSum.get(i).getDeductAmount();
				BigDecimal systemDeductAmountDu = brandCateSum.get(i).getSystemDeductAmount();
				BigDecimal saleAmount = brandCateSum.get(i).getSaleAmount();
				bill.setSaleAmount(saleAmount);
				// bill.setSystemDeductAmount(systemDeductAmount);
				bill.setUsedPrepaymentAmount(brandCateSum.get(i).getUsedPrepaymentAmount());
				bill.setDiffAmount(brandCateSum.get(i).getDiffAmount());
				bill.setUsedPrepaymentDeductAmount(brandCateSum.get(i).getUsedPrepaymentDeductAmount());
				bill.setPrepaymentDeductAmount(brandCateSum.get(i).getPrepaymentDeductAmount());
				Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
				systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
				systemParamSetParams.put("paramCode", "MS_ExpectCash_Pro");
				systemParamSetParams.put("status", 1);

				String value = getSystemParamSetValue(systemParamSetParams);
				if ("0".equals(value)) {// 参与结算 处理预收款的业务流程
					bill = processExpectCashCateBrand(billShopBalance, bill);
				} else if ("1".equals(value)) {
				} else
					// 默认参与计算
					bill = processExpectCashCateBrand(billShopBalance, bill);

				BigDecimal prepaymentAmountDeduct = bill.getPrepaymentDeductAmount();
				if (prepaymentAmountDeduct == null) {
					prepaymentAmountDeduct = BigDecimal.ZERO;
				}
				BigDecimal usedDeductAmount1 = bill.getUsedPrepaymentDeductAmount();
				if (usedDeductAmount1 == null) {
					usedDeductAmount1 = BigDecimal.ZERO;
				}

				bill.setSystemDeductAmount(systemDeductAmount.add(prepaymentAmountDeduct).add(usedDeductAmount1));

				this.getNumDataCalcBrandCate(bill, billShopBalance, cateSumParams);
				count = billShopBalanceCateSumService.modifyById(bill);
				if (billShopBalance.getMallBillingAmount() != null
						&& billShopBalance.getMallBillingAmount() != zeanAmount) {
					billShopBalanceCateSumService.modifySubInvoiceAmountByRound(cateSumParams);
					count += billShopBalanceCateSumService.modifySubInvoiceAmountByBrand(cateSumParams);
				}
			}
			// }
		}
		return count;
	}

	public BillShopBalanceCateSum getNumDataCalcBrandCate(BillShopBalanceCateSum bill, BillShopBalance shopBalance,
			Map<String, Object> cateSumParams) throws ManagerException, ServiceException {
		BigDecimal brandSalesAmount = new BigDecimal("0");
		BigDecimal brandBalanceDeductAmount = new BigDecimal("0");
		BigDecimal brandBalanceDiffAmount = new BigDecimal("0");
		BigDecimal deductDiffAmountsy = new BigDecimal("0");
		BigDecimal prepaymentAmountBc = bill.getPrepaymentAmount();

		BigDecimal mallNumberAmount = new BigDecimal("0");
		BigDecimal salesDiffamount = new BigDecimal("0");
		BigDecimal expenseOperateAmount = new BigDecimal("0");

		if (prepaymentAmountBc == null) {
			prepaymentAmountBc = BigDecimal.ZERO;
		}
		BigDecimal prepaymentAmountDeduct = bill.getPrepaymentDeductAmount();
		if (prepaymentAmountDeduct == null) {
			prepaymentAmountDeduct = BigDecimal.ZERO;
		}
		BigDecimal usedDeductAmount = bill.getDiffAmount();// .getUsedPrepaymentAmount();//.getUsedDeductAmount();
		if (usedDeductAmount == null) {
			usedDeductAmount = BigDecimal.ZERO;
		}
		BigDecimal usedDeductAmount1 = bill.getUsedPrepaymentDeductAmount();// .getUsedPrepaymentAmount();//.getUsedDeductAmount();
		if (usedDeductAmount1 == null) {
			usedDeductAmount1 = BigDecimal.ZERO;
		}

		// 根据品牌 综合店结算页签的对应品牌的票前费用金额，按照大类的销售金额占品牌的销售金额的比重分摊
		cateSumParams.put("brandNo", bill.getBrandNo());
		List<BillShopBalanceBrand> billShopBalanceBrand = billShopBalanceBrandService.findByBiz(null, cateSumParams);
		if (billShopBalanceBrand != null && billShopBalanceBrand.size() > 0) {
			for (BillShopBalanceBrand brand : billShopBalanceBrand) {
				brandSalesAmount = brand.getSalesAmount();
				brandBalanceDeductAmount = brand.getBalanceDeductAmount();
				brandBalanceDiffAmount = brand.getBalanceDiffAmount();
				deductDiffAmountsy = brand.getDeductDiffAmount();
			}
		} // saleAmount.add(prepaymentAmountBc)).divide(brandSalesAmount)
		BigDecimal systemDeductAmount = bill.getSystemDeductAmount();
		if (systemDeductAmount == null) {
			systemDeductAmount = BigDecimal.ZERO;
		}
		if (deductDiffAmountsy == null) {
			deductDiffAmountsy = BigDecimal.ZERO;
		}
		// bill.setSystemDeductAmount(deductDiffAmountsy);
		if (brandSalesAmount.compareTo(BigDecimal.ZERO) != 0) {
			bill.setBalanceDeductAmount(BigDecimalUtil.divisionScale(
					bill.getSaleAmount().add(prepaymentAmountBc.add(usedDeductAmount)), 4, brandSalesAmount).multiply(
					brandBalanceDeductAmount));
			bill.setBalanceDiffAmount(BigDecimalUtil.divisionScale(
					bill.getSaleAmount().add(prepaymentAmountBc.add(usedDeductAmount)), 4, brandSalesAmount).multiply(
					brandBalanceDiffAmount));
			// bill.setBalanceDeductAmount(BigDecimalUtil.format(BigDecimalUtil.division(bill.getSaleAmount().add(prepaymentAmountBc.add(usedDeductAmount)),brandSalesAmount).multiply(brandBalanceDeductAmount),BigDecimalUtil.POINT_4_PATTERN));
			// bill.setBalanceDiffAmount(BigDecimalUtil.format(BigDecimalUtil.division(bill.getSaleAmount().add(prepaymentAmountBc.add(usedDeductAmount)),brandSalesAmount).multiply(brandBalanceDiffAmount),BigDecimalUtil.POINT_4_PATTERN));
		}
		// 等于系统扣费+票前费用+结算差异
		BigDecimal balanceDeductAmount = bill.getBalanceDeductAmount();
		if (balanceDeductAmount == null) {
			balanceDeductAmount = BigDecimal.ZERO;
		}

		BigDecimal balanceDiffAmount = bill.getBalanceDiffAmount();// .getDeductAmount();
		if (balanceDiffAmount == null) {
			balanceDiffAmount = BigDecimal.ZERO;
		}

		BigDecimal deductDiffAmount = bill.getSystemDeductAmount();
		if (deductDiffAmount == null) {
			deductDiffAmount = BigDecimal.ZERO;
		}

		// deductDiffAmount 系统扣费 balanceDeductAmount 票前费用 balanceDiffAmount 结算差异
		bill.setDeductAmount(deductDiffAmount.add(balanceDeductAmount).add(balanceDiffAmount));
		BigDecimal deductAmount = bill.getDeductAmount();
		if (deductAmount == null) {
			deductAmount = BigDecimal.ZERO;
		}
		bill.setBillingAmount(bill.getSaleAmount().add(prepaymentAmountBc).add(usedDeductAmount).subtract(deductAmount));

		Map<String, Object> deductParams = new HashMap<String, Object>();
		deductParams.put("balanceNo", bill.getBalanceNo());
		deductParams.put("shopNo", bill.getShopNo());
		deductParams.put("month", bill.getMonth());
		// deductParams.put("costDeductType",1);
		deductParams.put("rateType", 1);
		deductParams.put("brandNo", bill.getBrandNo());

		BillShopBalanceDeduct billShopBalanceDeduct = billShopBalanceDeductService.getSumAmount(deductParams);
		if (billShopBalanceDeduct != null) {
			expenseOperateAmount = billShopBalanceDeduct.getDeductAmount();
		}
		if (shopBalance.getSystemSalesAmount().compareTo(BigDecimal.ZERO) != 0) {
			bill.setMallNumberAmount(BigDecimalUtil.divisionScale(bill.getSaleAmount(), 4,
					shopBalance.getSystemSalesAmount()).multiply(shopBalance.getMallNumberAmount()));
			bill.setSalesDiffamount(BigDecimalUtil.divisionScale(bill.getSaleAmount(), 4,
					shopBalance.getSystemSalesAmount()).multiply(shopBalance.getSalesDiffamount()));
			bill.setExpenseOperateAmount(BigDecimalUtil.divisionScale(bill.getSaleAmount(), 8,
					shopBalance.getSystemSalesAmount()).multiply(expenseOperateAmount));
		}
		return bill;
	}

	private BillShopBalanceCateSum processExpectCashCateBrand(BillShopBalance billShopBalance,
			BillShopBalanceCateSum billShopBalanceCateSum) throws Exception {
		BillShopBalanceCateSum bill = new BillShopBalanceCateSum();
		ReflectUtil.copyProperties(bill, billShopBalanceCateSum);

		BigDecimal prepaymentAmountBc = BigDecimal.ZERO;
		BigDecimal prepaymentAmountDeduct = BigDecimal.ZERO;
		BigDecimal usedDeductAmount = BigDecimal.ZERO;
		BigDecimal systemDeductAmount = BigDecimal.ZERO;
		// prepaymentAmountBc 预收款 品牌 大类
		ExpectCash expectCashCate = this.getExpectCashAmountBrandCate(billShopBalance, bill);// 收到的预收款--按品牌
		if (expectCashCate != null) {
			prepaymentAmountBc = expectCashCate.getExpectCashAmount();
			prepaymentAmountDeduct = expectCashCate.getDeductDiffAmount();
		}

		bill.setPrepaymentAmount(prepaymentAmountBc);
		bill.setPrepaymentDeductAmount(prepaymentAmountDeduct);

		// 冲销预收款 要关联预收款信息维护了结算月 取扣款算扣费
		Map<String, Object> useparams = new HashMap<String, Object>();
		useparams.put("shopNo", billShopBalance.getShopNo());
		useparams.put("balanceStartDate", billShopBalance.getBalanceStartDate());
		useparams.put("balanceEndDate", billShopBalance.getBalanceEndDate());
		useparams.put("payCode", "09");
		useparams.put("brandNo", bill.getBrandNo());
		useparams.put("categoryNo", bill.getCategoryNo());
		useparams.put("month", billShopBalance.getMonth());
		List<ExpectCash> expectCashList1 = expectCashService.processUseExpectCashBalanceDiff(useparams);
		if (expectCashList1 != null && expectCashList1.size() > 0) {
			for (ExpectCash expectCash : expectCashList1) {
				BigDecimal usedAmount = new BigDecimal("0");
				if (expectCash != null) {
					usedAmount = expectCash.getExpectCashAmount();
					usedDeductAmount = expectCash.getDeductDiffAmount();
				}
				BigDecimal zean = new BigDecimal("0");
				if (null == usedAmount) {
					usedAmount = new BigDecimal("0");
				}
				usedDeductAmount = zean.subtract(usedDeductAmount);
				bill.setDiffAmount(zean.subtract(usedAmount)); // 0-X = 负数
				bill.setUsedPrepaymentDeductAmount(usedDeductAmount);
				bill.setUsedDeductAmount(usedDeductAmount);
			}
		}
		return bill;
	}

	private BillShopBalanceCateSum processExpectCashCateBrandShare(BillShopBalance billShopBalance,
			BillShopBalanceCateSum billShopBalanceCateSum) throws Exception {
		BillShopBalanceCateSum bill = new BillShopBalanceCateSum();
		ReflectUtil.copyProperties(bill, billShopBalanceCateSum);

		BigDecimal prepaymentAmountBc = BigDecimal.ZERO;
		BigDecimal prepaymentAmountDeduct = BigDecimal.ZERO;
		BigDecimal usedDeductAmount = BigDecimal.ZERO;
		BigDecimal systemDeductAmount = BigDecimal.ZERO;
		// prepaymentAmountBc 预收款 品牌 大类
		ExpectCash expectCashCate = this.getExpectCashAmountBrandCateShare(billShopBalance, bill);// 收到的预收款--按品牌
		if (expectCashCate != null) {
			prepaymentAmountBc = expectCashCate.getExpectCashAmount();
			prepaymentAmountDeduct = expectCashCate.getDeductDiffAmount();
		}

		bill.setPrepaymentAmount(prepaymentAmountBc);
		bill.setPrepaymentDeductAmount(prepaymentAmountDeduct);

		// 冲销预收款 要关联预收款信息维护了结算月 取扣款算扣费
		Map<String, Object> useparams = new HashMap<String, Object>();
		useparams.put("shopNo", billShopBalance.getShopNo());
		useparams.put("balanceStartDate", billShopBalance.getBalanceStartDate());
		useparams.put("balanceEndDate", billShopBalance.getBalanceEndDate());
		useparams.put("payCode", "09");
		useparams.put("brandNo", bill.getBrandNo());
		useparams.put("categoryNo", bill.getCategoryNo());
		useparams.put("month", billShopBalance.getMonth());
		List<ExpectCash> expectCashList1 = expectCashService.processUseExpectCashBalanceDiff(useparams);
		if (expectCashList1 != null && expectCashList1.size() > 0) {
			for (ExpectCash expectCash : expectCashList1) {
				BigDecimal usedAmount = new BigDecimal("0");
				if (expectCash != null) {
					usedAmount = expectCash.getExpectCashAmount();
					usedDeductAmount = expectCash.getDeductDiffAmount();
				}
				BigDecimal zean = new BigDecimal("0");
				if (null == usedAmount) {
					usedAmount = new BigDecimal("0");
				}
				usedDeductAmount = zean.subtract(usedDeductAmount);
				bill.setDiffAmount(zean.subtract(usedAmount)); // 0-X = 负数
				bill.setUsedPrepaymentDeductAmount(usedDeductAmount);
				bill.setUsedDeductAmount(usedDeductAmount);
			}
		}
		return bill;
	}

	private BillShopBalance processReMallNumberDiff(BillShopBalance billShopBalance) throws Exception {
		BillShopBalance bill = new BillShopBalance();
		ReflectUtil.copyProperties(bill, billShopBalance);

		Map<String, Object> diffparams = new HashMap<String, Object>();
		diffparams.put("shopNo", billShopBalance.getShopNo());
		diffparams.put("balanceStartDate", billShopBalance.getBalanceStartDate());
		diffparams.put("balanceEndDate", billShopBalance.getBalanceEndDate());
		diffparams.put("balanceNo", billShopBalance.getBalanceNo());
		diffparams.put("month", billShopBalance.getMonth());
		diffparams.put("status", "1");
		// BillShopBalanceDiff billShopBalanceDiff=
		// billShopBalanceDiffService.getSumAmount(diffparams);
		// if(billShopBalanceDiff != null){
		// mallNumber = billShopBalanceDiff.getMallNumber();
		// }
		List<BillShopBalanceDiff> billShopBalanceDiff = billShopBalanceDiffService.getSumAmount(diffparams);
		if (billShopBalanceDiff != null && billShopBalanceDiff.size() > 0) {
			BigDecimal mallNumber = new BigDecimal("0");
			for (BillShopBalanceDiff shopBalanceDiff : billShopBalanceDiff) {
				if (shopBalanceDiff != null) {
					mallNumber = shopBalanceDiff.getMallNumber();
				}
			}
			billShopBalance.setMallNumberAmount(mallNumber);
		}
		return bill;
	}

	private BillShopBalance processExpectCash(BillShopBalance billShopBalance) throws Exception {
		BillShopBalance bill = new BillShopBalance();
		ReflectUtil.copyProperties(bill, billShopBalance);
		BigDecimal prepaymentAmount = new BigDecimal("0");
		BigDecimal usedPrepaymentAmount = new BigDecimal("0");
		prepaymentAmount = this.getExpectCashAmount(billShopBalance);// 收到的预收款

		if (prepaymentAmount != null) {
			billShopBalance.setPrepaymentAmount(prepaymentAmount);
		}

		// billShopBalance.setPayCode("09");//按照预收款支付方式汇总
		// BillShopBalanceDaysaleSum
		// billShopBalanceDaysaleSum=getSystemSalesAmount(billShopBalance);
		//
		// billShopBalance.setUsedPrepaymentAmount(usedPrepaymentAmount);
		// // 如果用到预收款的 记为 - 0-？
		// if(billShopBalanceDaysaleSum != null){
		// billShopBalance.setUsedPrepaymentAmount(usedPrepaymentAmount.subtract(billShopBalanceDaysaleSum.getAmount()));
		// //
		// billShopBalance.setUsedPrepaymentAmount(billShopBalanceDaysaleSum.getAmount());
		// }
		// 冲销预收款 要关联预收款信息维护了结算月 取扣款算扣费
		Map<String, Object> useparams = new HashMap<String, Object>();
		useparams.put("shopNo", billShopBalance.getShopNo());
		// useparams.put("month", billShopBalance.getMonth());
		// params.put("status", "2");
		// params.put("confirmFlag", "2");
		// params.put("balanceStatus", "1");
		useparams.put("balanceStartDate", billShopBalance.getBalanceStartDate());
		useparams.put("balanceEndDate", billShopBalance.getBalanceEndDate());
		useparams.put("payCode", "09");
		useparams.put("month", billShopBalance.getMonth());
		List<ExpectCash> expectCashList1 = expectCashService.processUseExpectCashBalanceDiff(useparams);
		if (expectCashList1 != null && expectCashList1.size() > 0) {
			BigDecimal usedAmount = new BigDecimal("0");
			BigDecimal zean = new BigDecimal("0");
			for (ExpectCash expectCash : expectCashList1) {
				if (expectCash != null) {
					BigDecimal usedAmountSum = usedAmount.add(expectCash.getExpectCashAmount());
					usedAmount = usedAmountSum;
				}
				// if(null == usedAmount){
				// usedAmount = new BigDecimal("0");
				// }
			}
			billShopBalance.setUsedPrepaymentAmount(zean.subtract(usedAmount)); // 0-X
																				// =
																				// 负数
		}

		// 预收款处理 产生一条预收款收款总金额 一条已使用金额(冲销)
		this.expectCashBalanceDiff(billShopBalance);
		return bill;
	}

	private BillShopBalance processExpectCashBrand(BillShopBalance billShopBalance,
			BillShopBalanceBrand billShopBalanceBrand) throws Exception {
		BillShopBalance bill = new BillShopBalance();
		ReflectUtil.copyProperties(bill, billShopBalance);
		BigDecimal prepaymentAmountBrand = new BigDecimal("0");
		BigDecimal usedPrepaymentAmountBrand = new BigDecimal("0");
		prepaymentAmountBrand = this.getExpectCashAmountBrand(billShopBalance, billShopBalanceBrand);// 收到的预收款--按品牌
		if (prepaymentAmountBrand != null) {
			billShopBalance.setPrepaymentAmountBrand(prepaymentAmountBrand);
			billShopBalanceBrand.setPrepaymentAmount(prepaymentAmountBrand);
		}

		// 冲销预收款 要关联预收款信息维护了结算月 取扣款算扣费
		Map<String, Object> useparams = new HashMap<String, Object>();
		useparams.put("shopNo", billShopBalance.getShopNo());
		// useparams.put("month", billShopBalance.getMonth());
		// params.put("status", "2");
		// params.put("confirmFlag", "2");
		// params.put("balanceStatus", "1");
		useparams.put("balanceStartDate", billShopBalance.getBalanceStartDate());
		useparams.put("balanceEndDate", billShopBalance.getBalanceEndDate());
		useparams.put("payCode", "09");
		useparams.put("brandNo", billShopBalanceBrand.getBrandNo());
		useparams.put("month", billShopBalance.getMonth());
		List<ExpectCash> expectCashList1 = expectCashService.processUseExpectCashBalanceDiff(useparams);
		if (expectCashList1 != null && expectCashList1.size() > 0) {
			for (ExpectCash expectCash : expectCashList1) {
				BigDecimal usedAmount = new BigDecimal("0");
				if (expectCash != null) {
					usedAmount = expectCash.getExpectCashAmount();
				}
				BigDecimal zean = new BigDecimal("0");
				if (null == usedAmount) {
					usedAmount = new BigDecimal("0");
				}
				billShopBalanceBrand.setUsedPrepaymentAmount(zean.subtract(usedAmount));
				billShopBalance.setUsedPrepaymentAmountBrand(zean.subtract(usedAmount)); // 0-X
																							// =
																							// 负数
			}
		}
		return bill;
	}

	/**
	 * 保存时校验数据是否相同
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @return 保存错误消息的Map
	 */
	public Map<String, String> validateDataBalance(BillShopBalance billShopBalance) {
		BigDecimal thePaymentAmount = billBacksectionSplitDtlService.sumPaymentAmount(billShopBalance.getBalanceNo());
		BillShopBalanceDeduct shopBalanceDeduct = null;
		try {
			shopBalanceDeduct = getCostDeductTypeAmount(billShopBalance, null);
		} catch (ServiceException e1) {
			logger.error(e1.getMessage(), e1);
		}
		BigDecimal deductAmount = new BigDecimal("0");
		BigDecimal actualAmount = new BigDecimal("0");
		if (shopBalanceDeduct != null) {
			deductAmount = shopBalanceDeduct.getDeductAmount();
			actualAmount = shopBalanceDeduct.getActualAmount();
		}
		// 收款差异=商场开票金额-票后账扣-回款金额
		BigDecimal differenceAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(), thePaymentAmount)
				.subtract(actualAmount);
		billShopBalance.setDifferenceAmount(differenceAmount);

		// 应返款金额 =商场开票金额-票后账扣
		BigDecimal returnedAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(), actualAmount);
		billShopBalance.setReturnedAmount(returnedAmount);

		Map<String, String> msgMap = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", billShopBalance.getBalanceNo());
		params.put("parBalanceNo", "");
		// 查询结算差异汇总数据
		BigDecimal totalDiffSystemAmount = null; // 结算差异-系统扣费总额
		BigDecimal totalDiffAmount = null; // 结算差异-扣费差异总额
		GatherBillShopBalanceDiffDto diffDto = billShopBalanceDiffService.gatherBalanceDiff(params);
		if (diffDto != null) {
			totalDiffSystemAmount = diffDto.getTotalDeductDiffAmount();
			totalDiffAmount = diffDto.getTotalDiffAmount();
		}
		if (totalDiffSystemAmount == null) {
			totalDiffSystemAmount = BigDecimal.ZERO;
		}
		if (totalDiffAmount == null) {
			totalDiffAmount = BigDecimal.ZERO;
		}
		// 查询票前费用汇总数据
		params.put("costDeductType", ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId());
		BigDecimal totalDeductActualAmount = null; // 票前费用-实际扣费总额
		BigDecimal totalDeductDiffAmount = null; // 票前费用-扣费差异总额
		GatherBillShopBalanceDeductDto deductDto = billShopBalanceDeductService.gatherBalanceDeduct(params);
		if (deductDto != null) {
			totalDeductActualAmount = deductDto.getTotalActualAmount();
			totalDeductDiffAmount = deductDto.getTotalDiffAmount();
		}
		if (totalDeductActualAmount == null) {
			totalDeductActualAmount = BigDecimal.ZERO;
		}
		if (totalDeductDiffAmount == null) {
			totalDeductDiffAmount = BigDecimal.ZERO;
		}
		// 判断校验：1、系统收入 - 商场开票金额 = 票前费用页签.票前实际扣费 + 结算差异.系统扣费 + 结算差异.扣费差异汇总
		// 2、系统开票金额 - 商场开票 = 单据体结算差异页签的扣费差异金额汇总+票前费用.扣费差异
		// 系统收入 - 商场开票金额
		BigDecimal systemSalesAmount = billShopBalance.getSystemSalesAmount();
		if (systemSalesAmount == null) {
			systemSalesAmount = BigDecimal.ZERO;
		}
		BigDecimal mallBillingAmount = billShopBalance.getMallBillingAmount();
		if (mallBillingAmount == null) {
			mallBillingAmount = BigDecimal.ZERO;
		}
		BigDecimal systemBillingAmount = billShopBalance.getSystemBillingAmount();
		if (systemBillingAmount == null) {
			systemBillingAmount = BigDecimal.ZERO;
		}

		StringBuffer sbMsg = new StringBuffer();
		StringBuffer showRateExpenseRemind = new StringBuffer();

		// 处理结算提醒
		params.put("shopNo", billShopBalance.getShopNo());
		params.put("endMonth", billShopBalance.getMonth());
		List<RateExpenseRemind> rateExpenseRemindList = null;
		try {
			rateExpenseRemindList = rateExpenseRemindService.findByBiz(null, params);
		} catch (ServiceException e) {
		}
		if (rateExpenseRemindList != null && rateExpenseRemindList.size() > 0) {
			BigDecimal totalGuaranteeAmount = rateExpenseRemindList.get(0).getTotalGuaranteeAmount();// 总保底计划
			showRateExpenseRemind.append(rateExpenseRemindList.get(0).getShopName() + "在"
					+ rateExpenseRemindList.get(0).getStartMonth() + " ~ " + rateExpenseRemindList.get(0).getEndMonth()
					+ "存在总保底计划，额度为：" + totalGuaranteeAmount + ";</br>");
			// 结算单系统销售金额汇总
		}

		BigDecimal value0 = systemSalesAmount.subtract(mallBillingAmount);// BigDecimalUtil.subtract(systemSalesAmount,
																			// mallBillingAmount);
		// 票前费用页签.票前实际扣费 + 结算差异.系统扣费 + 结算差异.扣费差异汇总
		BigDecimal value1 = BigDecimalUtil.add(totalDeductActualAmount, totalDiffSystemAmount, totalDiffAmount);

		// if(value0.compareTo(value1) != 0) {
		// 华南20151103要求去掉该验证
		// if(value0.subtract(value1).doubleValue() > 0.02 ||
		// value0.subtract(value1).doubleValue() < -0.02) {
		// sbMsg.append("扣费总额!= 票前费用.实际扣费("+totalDeductActualAmount+")+结算差异.系统扣费("+totalDiffSystemAmount+")+结算差异.扣费差异("+totalDiffAmount+")<br/>");
		// }
		// 系统开票金额 - 商场开票
		value0 = systemBillingAmount.subtract(mallBillingAmount);// BigDecimalUtil.subtract(systemBillingAmount,
																	// mallBillingAmount);
		// 单据体结算差异页签的扣费差异金额汇总+票前费用.扣费差异
		value1 = BigDecimalUtil.add(totalDiffAmount, totalDeductDiffAmount);
		// if(value0.compareTo(value1) != 0) {
		// 结算差异额 结算差异总额 - 结算差异.扣费差异 +票前费用.扣费差异
		BigDecimal diffValue = value0.subtract(value1);
		// if(!"I".equals(billShopBalance.getCompanyNo().substring(0,1))){
		if (value0.subtract(value1).doubleValue() > 0.02 || value0.subtract(value1).doubleValue() < -0.02) {
			sbMsg.append("结算差异总额!=结算差异/扣费差异(" + totalDiffAmount + ")+票前费用/扣费差异(" + totalDeductDiffAmount + ")，差异额为："
					+ diffValue + "<br/>");
		}
		// }
		msgMap.put("differenceAmount", differenceAmount.toString());
		msgMap.put("returnedAmount", String.valueOf(returnedAmount));
		msgMap.put("errorMsg", sbMsg.toString());
		msgMap.put("showRateExpenseRemind", showRateExpenseRemind.toString());
		return msgMap;
	}

	/**
	 * 初始化结算单对象
	 * 
	 * @param shopBalances
	 *            页面传递的结算单数据
	 * @param i
	 *            店铺编码序号
	 * @param billShopBalance
	 *            待初始化的BillShopBalance对象
	 * @return 初始化后的BillShopBalance对象
	 * @throws Exception
	 */
	private BillShopBalance initBill(BillShopBalance billShopBalance) throws Exception {
		BillShopBalance bill = new BillShopBalance();
		ReflectUtil.copyProperties(bill, billShopBalance);
		String shopNo = bill.getShopNo();

		// String balanceNo =
		// codingRuleService.getSerialNo(BillShopBalance.class.getSimpleName());
		String requestId = BillShopBalance.class.getSimpleName();
		// 调用单据编号拼接处理方法，返回最终的单据编号
		String balanceNo = commonUtilService.getNewBillNoCompanyNo(null, shopNo, requestId);

		bill.setId(UUIDGenerator.getUUID());
		bill.setStatus(1); // 设置单据状态
		bill.setBalanceNo(balanceNo);
		bill.setBillDate(new Date());
		bill.setBillName(billShopBalance.getShortName() + billShopBalance.getMonth() + "的结算单");
		bill.setCreateUser(billShopBalance.getCreateUser());
		bill.setCreateTime(billShopBalance.getCreateTime());
		return bill;
	}

	/**
	 * 校验指定公司、店铺、结算期是否已生成过结算单,同时初始化店铺、基础扣率等信息
	 * 
	 * @param billShopBalance
	 *            封装结算单数据的model
	 * @return BillShopBalance 组装后的BillShopBalance对象
	 * @throws Exception
	 *             异常
	 */
	private BillShopBalance checkBillAndInitOtherInfo(BillShopBalance billShopBalance) throws ServiceException {
		billShopBalance.setErrorInfo("");
		// 根据条件取结算期起止时间
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", billShopBalance.getCompanyNo());
		params.put("shopNo", billShopBalance.getShopNo());
		params.put("month", billShopBalance.getMonth());
		// 否生成结算单(0-已生成预估 1-未生成，2-已生成)
		if (1 == billShopBalance.getBalanceType().intValue()) { // 正式 !2 否生成结算单
			params.put("notEqualBalanceFlag", 2);// 未结算 不等于2 已结算 不等于0 已生成预估
		} else if (2 == billShopBalance.getBalanceType().intValue()) { // 预估
			params.put("balanceFlag", 1); // 1 2
		}

		List<ShopBalanceDate> shopBalanceDateList = shopBalanceDateService.findByBiz(null, params);
		if (CollectionUtils.isEmpty(shopBalanceDateList)) {
			billShopBalance.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
			billShopBalance.setErrorInfo("保存失败，" + billShopBalance.getShopNo() + " " + billShopBalance.getShortName()
					+ " " + billShopBalance.getMonth() + " 请检查结算期设置，是否生成结算单状态是否为未生成；");
			return billShopBalance;
		}
		billShopBalance.setShopBalanceDateId(shopBalanceDateList.get(0).getId());
		billShopBalance.setBalanceStartDate(shopBalanceDateList.get(0).getBalanceStartDate());
		billShopBalance.setBalanceEndDate(shopBalanceDateList.get(0).getBalanceEndDate());

		// 正式
		if (ShopMallEnums.BalanceType.FORMAL_BALANCE.getRequestId().intValue() == billShopBalance.getBalanceType()
				.intValue()) {
			params.put("balanceFlag", ShopMallEnums.BalanceType.FORMAL_BALANCE.getRequestId().intValue());// 未结算
		}

		// 预估
		if (ShopMallEnums.BalanceType.ESTIMATE_BALANCE.getRequestId().intValue() == billShopBalance.getBalanceType()
				.intValue()) {
			params.put("balanceFlag", ShopMallEnums.BalanceType.FORMAL_BALANCE.getRequestId().intValue());// 未结算
		}

		String companyNo = billShopBalance.getCompanyNo() == null ? null : billShopBalance.getCompanyNo().toString();
		if (companyNo != null && !"".equals(companyNo)) {
			params.put("companyNo", FasUtil.formatInQueryCondition(companyNo));
		}
		params.put("status", "2");
		params.put("startDate", billShopBalance.getBalanceStartDate());
		params.put("endDate", billShopBalance.getBalanceEndDate());
		int iCount = billShopBalanceService.findCount(params);
		if (iCount > 0) {
			billShopBalance.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
			billShopBalance.setErrorInfo("保存失败，" + billShopBalance.getShopNo() + " " + billShopBalance.getShortName()
					+ " " + billShopBalance.getMonth() + " 已存在数据，请检查数据是否重复操作；");
			return billShopBalance;
		}
		params.put("status", "1");
		params.put("startDate", billShopBalance.getBalanceStartDate());
		params.put("endDate", billShopBalance.getBalanceEndDate());
		iCount = billShopBalanceService.findCount(params);
		if (iCount > 0) {
			billShopBalance.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
			billShopBalance.setErrorInfo("保存失败，" + billShopBalance.getShopNo() + " " + billShopBalance.getShortName()
					+ " " + billShopBalance.getMonth() + " 请检查数据是否有效，已存在重复数据；");
			return billShopBalance;
		}
		// 检查预估 如果存在预估，提示，是否复制还是新增结算单
		// params.put("balanceType", "2");
		// params.put("startDate",
		// DateUtil.format1(billShopBalance.getBalanceStartDate()));
		// params.put("endDate",
		// DateUtil.format1(billShopBalance.getBalanceEndDate()));
		// iCount = billShopBalanceService.findCount(params);
		// if(iCount > 0) {
		// billShopBalance.setErrorType(ShopMallEnums.ErrorType.FAIL.getRequestId());
		// billShopBalance.setErrorInfo("存在预估，"+billShopBalance.getShopNo()+" "+billShopBalance.getShortName()+" "+billShopBalance.getMonth()+" 是否复制还是新增结算单；");
		// return billShopBalance;
		// }

		Map<String, Object> shopParams = new HashMap<String, Object>();
		shopParams.put("shopNo", billShopBalance.getShopNo());
		Shop shop = shopService.selectSubsiInfo(shopParams);
		if (shop != null) {
			billShopBalance.setZoneNo(shop.getZoneNo());
			billShopBalance.setZoneName(shop.getZoneName());
			billShopBalance.setOrganNo(shop.getOrganNo());
			billShopBalance.setOrganName(shop.getOrganName());
			billShopBalance.setBsgroupsNo(shop.getBsgroupsNo());
			billShopBalance.setBsgroupsName(shop.getBsgroupsName());
			billShopBalance.setRegionNo(shop.getRegionNo());
			billShopBalance.setRegionName(shop.getRegionName());
			billShopBalance.setMallNo(shop.getMallNo());
			billShopBalance.setMallName(shop.getMallName());
			billShopBalance.setRetailType(shop.getRetailType());
		}
		// ShopBrand shopBrand = getShopBrandInfo(shopParams);
		// if(shopBrand != null) {
		// billShopBalance.setBrandNo(shopBrand.getBrandNo());
		// billShopBalance.setBrandName(shopBrand.getName());
		// }
		return billShopBalance;
	}

	/**
	 * 处理pos、mps接口方法
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @return 设置值之后的结算单对象
	 * @throws Exception
	 *             异常
	 */
	private BillShopBalance processApiData(BillShopBalance billShopBalance) throws Exception {
		// 处理基础扣率、场地经营费用、其他零星费用、结算提醒
		// 修改为同步
		// billShopBalance = this.processRateApiData(billShopBalance);
		// 传参取POS数据 写每日销售 大类汇总 促销 支付方式
		this.processBalanceSumData(billShopBalance);

		// 设置结算单中需要结算的数据
		this.initBillCalculateInfo(billShopBalance);

		// 处理商场扣费
		this.processBillDeductData(billShopBalance);

		return billShopBalance;
	}

	/**
	 * 处理基础扣率、场地经营费用、其他零星费用、结算提醒
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @return BillShopBalance 组装后的BillShopBalance对象
	 * @throws Exception
	 *             异常
	 */
	private BillShopBalance processRateApiData(BillShopBalance billShopBalance) throws ServiceException, RpcException {
		// 传参取MPS数据 扣率、费用数据
		List<BalanceParamDto> balanceParams = new ArrayList<BalanceParamDto>();
		BalanceParamDto balanceParamDto = new BalanceParamDto();
		balanceParamDto.setShopNo(billShopBalance.getShopNo());
		balanceParamDto.setBalanceMonth(billShopBalance.getMonth());
		balanceParamDto.setStartDate(billShopBalance.getBalanceStartDate());
		balanceParamDto.setEndDate(billShopBalance.getBalanceEndDate());
		balanceParamDto.setQueryType(0);
		balanceParams.add(balanceParamDto);

		// 将从MPS读取到的基础扣率、场地经营费用、其他零星费用、结算提醒数据保存到fas数据库
		// 于20150626修改为同步表结构
		// this.saveRateBasic(balanceParams);
		// this.saveRateExpenseOperat(balanceParams);
		// this.saveRateExpenseSporadic(balanceParams);
		// this.saveRateExpenseRemind(balanceParams);
		// this.saveRatePro(balanceParams);

		// 处理结算提醒
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("shopNo", billShopBalance.getShopNo());
		// params.put("endMonth", billShopBalance.getMonth());
		// List<RateExpenseRemind> rateExpenseRemindList =
		// rateExpenseRemindService.findByBiz(null, params);
		// if(rateExpenseRemindList != null && rateExpenseRemindList.size() > 0)
		// {
		// BigDecimal totalGuaranteeAmount =
		// rateExpenseRemindList.get(0).getTotalGuaranteeAmount();// 总保底计划
		// billShopBalance.setErrorInfo(rateExpenseRemindList.get(0).getShopName()+"在"+rateExpenseRemindList.get(0).getStartMonth()+" ~ "
		// +rateExpenseRemindList.get(0).getEndMonth()+"存在总保底计划，额度为：" +
		// totalGuaranteeAmount+"；</br>");
		// // 结算单系统销售金额汇总
		// }
		return billShopBalance;
	}

	/**
	 * 传参取POS数据 写每日销售 大类汇总 促销 支付方式
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @throws ManagerException
	 * @throws Exception
	 *             异常
	 */
	private void processBalanceSumData(BillShopBalance billShopBalance) throws ServiceException, ManagerException {
		// 传参取POS数据写 每日销售 大类汇总 促销 支付方式
		POSOcOrderParameterDto ocOrderParameterDto = new POSOcOrderParameterDto();
		ocOrderParameterDto.setShopNo(billShopBalance.getShopNo());
		ocOrderParameterDto.setStartOutDate(billShopBalance.getBalanceStartDate());
		ocOrderParameterDto.setEndOutDate(billShopBalance.getBalanceEndDate());

		// 订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-内购 4-员购 5-专柜团购 6-特卖销售 默认为0
		List<Integer> businessTypeList = new ArrayList<Integer>();
		businessTypeList.add(0);
		businessTypeList.add(1);
		businessTypeList.add(2);
		businessTypeList.add(5);
		businessTypeList.add(6);
		ocOrderParameterDto.setBusinessTypeList(businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(30);
		statusList.add(41);
		ocOrderParameterDto.setStatusList(statusList);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", billShopBalance.getCompanyNo());
		params.put("companyName", billShopBalance.getCompanyName());
		params.put("balanceNo", billShopBalance.getBalanceNo());
		params.put("month", billShopBalance.getMonth());
		// 保存销售汇总数据
		this.saveBalanceDaysaleSum(ocOrderParameterDto, params);
		this.saveBalanceCateSum(ocOrderParameterDto, params);
		params.put("startDate", DateUtil.format1(billShopBalance.getBalanceStartDate()));
		params.put("endDate", DateUtil.format1(billShopBalance.getBalanceEndDate()));

		// 商场促销活动生成方式 扣率代码+扣率 活动编号+扣率 结算码+扣率
		Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
		systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
		systemParamSetParams.put("paramCode", "MS_Promotion_Pro");
		systemParamSetParams.put("status", 1);
		String value = getSystemParamSetValue(systemParamSetParams);
		ocOrderParameterDto.setProType(value);

		// 生成促销活动及结算差异汇总方式
		Map<String, Object> promotionSumMethodParams = new HashMap<String, Object>();
		promotionSumMethodParams.put("businessOrganNo", billShopBalance.getCompanyNo());
		promotionSumMethodParams.put("paramCode", "MS_Promotion_SumMethod");
		promotionSumMethodParams.put("status", 1);
		String promotionSumMethodValue = getSystemParamSetValue(promotionSumMethodParams);
		if ("1".equals(promotionSumMethodValue)) { // 1 -分，商场活动与品牌活动
			this.saveBalanceSalePromationSum(ocOrderParameterDto, params);
		} else if ("2".equals(promotionSumMethodValue)) { // 2-不分，按照促销活动生成方式参数设置
			this.saveBalanceSalePromationSumH(ocOrderParameterDto, params);
		} else {
			// 没有配置参数，分，商场活动与品牌活动
			this.saveBalanceSalePromationSum(ocOrderParameterDto, params);
		}
		this.saveBalanceWildSaleSum(ocOrderParameterDto, params);
		this.saveBalanceCodeSum(ocOrderParameterDto, params);
	}

	/**
	 * MAP
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @throws Exception
	 *             异常
	 */
	private void processBalanceBrandData(BillShopBalance billShopBalance) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", billShopBalance.getCompanyNo());
		params.put("balanceNo", billShopBalance.getBalanceNo());
		// 保存综合店结算数据
		this.saveBalanceBrandSum(billShopBalance, params);
		billShopBalanceCateSumService.modifyBrandBillingAmountByRound(params);
		billShopBalanceCateSumService.modifyBrandBillingAmountByBrand(params);
	}

	// 处理数据 从结算差异数据 按品牌汇总 综合店结算处理
	public int saveBalanceBrandSum(BillShopBalance billShopBalance, Map<String, Object> params) throws Exception {
		Map<String, Object> diffParams = new HashMap<String, Object>();
		diffParams.put("balanceNo", billShopBalance.getBalanceNo());
		diffParams.put("shopNo", billShopBalance.getShopNo());
		diffParams.put("month", billShopBalance.getMonth());
		diffParams.put("groupBrand", "groupBrand");
		List<BillShopBalanceDiff> billShopBalanceDiffList = billShopBalanceDiffService.getSumAmount(diffParams);// .findByBiz(null,
																												// diffParams);
		int count = 0;
		if (billShopBalanceDiffList != null && billShopBalanceDiffList.size() > 0) {
			for (BillShopBalanceDiff billShopBalanceDiff : billShopBalanceDiffList) {
				BillShopBalanceBrand billShopBalanceBrand = new BillShopBalanceBrand();
				Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
				systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
				systemParamSetParams.put("paramCode", "MS_ExpectCash_Pro");
				systemParamSetParams.put("status", 1);

				billShopBalanceBrand.setBrandNo(billShopBalanceDiff.getBrandNo());
				billShopBalanceBrand.setBrandName(billShopBalanceDiff.getBrandName());

				String value = getSystemParamSetValue(systemParamSetParams);
				if ("0".equals(value)) {// 参与结算 处理预收款的业务流程
					processExpectCashBrand(billShopBalance, billShopBalanceBrand);
				} else if ("1".equals(value)) {
				} else
					// 默认参与计算
					processExpectCashBrand(billShopBalance, billShopBalanceBrand);

				BigDecimal prepaymentAmountBrand = billShopBalanceBrand.getPrepaymentAmount();
				if (prepaymentAmountBrand == null) {
					prepaymentAmountBrand = BigDecimal.ZERO;
				}
				BigDecimal usedPrepaymentAmountBrand = billShopBalanceBrand.getUsedPrepaymentAmount();
				if (usedPrepaymentAmountBrand == null) {
					usedPrepaymentAmountBrand = BigDecimal.ZERO;
				}
				billShopBalanceBrand.setPrepaymentAmount(prepaymentAmountBrand);
				billShopBalanceBrand.setUsedPrepaymentAmount(usedPrepaymentAmountBrand);

				billShopBalanceBrand.setId(UUIDGenerator.getUUID());
				billShopBalanceBrand.setShopNo(billShopBalance.getShopNo());
				billShopBalanceBrand.setShortName(billShopBalance.getShortName());
				billShopBalanceBrand.setCompanyNo(billShopBalance.getCompanyNo());
				billShopBalanceBrand.setCompanyName(billShopBalance.getCompanyName());
				billShopBalanceBrand.setMonth(billShopBalance.getMonth());
				billShopBalanceBrand.setBalanceStartDate(billShopBalance.getBalanceStartDate());
				billShopBalanceBrand.setBalanceEndDate(billShopBalance.getBalanceEndDate());
				diffParams.put("generateType", "0");// 系统扣费只取 0：系统自动生成
				diffParams.put("brandNo", billShopBalanceDiff.getBrandNo());
				List<BillShopBalanceDiff> billShopBalanceDiffList1 = billShopBalanceDiffService
						.getSumAmount(diffParams);// .findByBiz(null,
													// diffParams);
				if (billShopBalanceDiffList1 != null && billShopBalanceDiffList1.size() > 0) {
					for (BillShopBalanceDiff billShopBalanceDiff1 : billShopBalanceDiffList1) {
						billShopBalanceBrand.setSalesAmount(billShopBalanceDiff1.getSalesAmount());
						billShopBalanceBrand.setMallNumber(billShopBalanceDiff1.getMallNumber());
					}
				}
				BigDecimal mallNumber = billShopBalanceBrand.getMallNumber();
				if (mallNumber == null) {
					mallNumber = BigDecimal.ZERO;
				}
				billShopBalanceBrand.setReportDiffAmount(billShopBalanceBrand.getSalesAmount().subtract(mallNumber));
				billShopBalanceBrand.setSalesDiffamount(billShopBalanceDiff.getSalesDiffamount());
				billShopBalanceBrand.setBalanceNo((String) params.get("balanceNo"));

				// 处理预收款 计算 按品牌的 预收款金额 冲销金额
				// BigDecimal prepaymentAmount=new BigDecimal("0");
				// BigDecimal usedPrepaymentAmount=new BigDecimal("0");
				// billShopBalance.setPrepaymentAmount(prepaymentAmount);
				// billShopBalance.setUsedPrepaymentAmount(usedPrepaymentAmount);
				billShopBalanceBrand.setMallBillingAmount(billShopBalanceBrand.getSystemBillingAmount());
				// billShopBalanceBrand.setBalanceDiffAmount(billShopBalanceBrand.getSystemBillingAmount().subtract(billShopBalanceBrand.getMallBillingAmount()));
				Map<String, Object> deductParams = new HashMap<String, Object>();
				deductParams.put("balanceNo", billShopBalanceBrand.getBalanceNo());
				deductParams.put("shopNo", billShopBalanceDiff.getShopNo());
				deductParams.put("month", billShopBalanceDiff.getMonth());
				deductParams.put("costDeductType", 1);
				deductParams.put("brandNo", billShopBalanceDiff.getBrandNo());
				BigDecimal diffAmount1 = new BigDecimal("0");

				BigDecimal balanceDeductAmount = new BigDecimal("0");

				BillShopBalanceDeduct billShopBalanceDeduct = billShopBalanceDeductService.getSumAmount(deductParams);
				if (billShopBalanceDeduct != null) {
					diffAmount1 = billShopBalanceDeduct.getDiffAmount();
					balanceDeductAmount = billShopBalanceDeduct.getDeductAmount();
				}
				if (diffAmount1 == null) {
					diffAmount1 = BigDecimal.ZERO;
				}
				if (balanceDeductAmount == null) {
					balanceDeductAmount = BigDecimal.ZERO;
				}
				// 综合店结算差异 = 结算差异页签的扣费差异 + 票前费用的扣费差异金额
				billShopBalanceBrand.setBalanceDiffAmount(billShopBalanceDiff.getDiffAmount().add(diffAmount1));
				billShopBalanceBrand.setDiffAmount(billShopBalanceDiff.getDiffAmount());
				billShopBalanceBrand.setDeductDiffAmount(billShopBalanceDiff.getDeductDiffAmount());// .add(balanceDeductAmount)
				billShopBalanceBrand.setBalanceDeductAmount(balanceDeductAmount);
				this.getNumDataCalcBrandSum(billShopBalanceDiff, billShopBalanceBrand);

				// 扣费总额 = 系统扣费 + 扣费差异 + 实际扣费金额
				// deduct_allamount = deduct_diff_amount + diff_amount +
				// actual_amount
				BigDecimal deductDiffAmount = billShopBalanceBrand.getDeductDiffAmount();
				if (deductDiffAmount == null) {
					deductDiffAmount = BigDecimal.ZERO;
				}

				BigDecimal diffAmount = billShopBalanceBrand.getBalanceDiffAmount();
				if (diffAmount == null) {
					diffAmount = BigDecimal.ZERO;
				}

				BigDecimal actualAmount = billShopBalanceBrand.getBalanceDeductAmount();
				if (actualAmount == null) {
					actualAmount = BigDecimal.ZERO;
				}

				billShopBalanceBrand.setDeductAllamount(deductDiffAmount.add(diffAmount).add(actualAmount));

				count += billShopBalanceBrandService.add(billShopBalanceBrand);
			}
		}
		return count;
	}

	/**
	 * 数据计算
	 * 
	 * @throws ManagerException
	 *             ,ServiceException
	 */
	public BillShopBalanceBrand getNumDataCalcBrandSum(BillShopBalanceDiff billShopBalanceDiff,
			BillShopBalanceBrand billShopBalanceBrand) throws ManagerException, ServiceException {
		// 通过店 结算月 品牌 取票前费用的实际扣费总额 赋值actual_amount

		Map<String, Object> deductParams = new HashMap<String, Object>();
		deductParams.put("balanceNo", billShopBalanceDiff.getBalanceNo());
		deductParams.put("shopNo", billShopBalanceDiff.getShopNo());
		deductParams.put("month", billShopBalanceDiff.getMonth());
		deductParams.put("costDeductType", 1);
		deductParams.put("brandNo", billShopBalanceDiff.getBrandNo());
		BigDecimal actualAmount = new BigDecimal("0");
		if (actualAmount == null) {
			actualAmount = BigDecimal.ZERO;
		}
		BillShopBalanceDeduct billShopBalanceDeduct = billShopBalanceDeductService.getSumAmount(deductParams);
		if (billShopBalanceDeduct != null) {
			billShopBalanceBrand.setActualAmount(billShopBalanceDeduct.getDeductAmount());
			billShopBalanceBrand.setBalanceDeductAmount(billShopBalanceDeduct.getDeductAmount());
			actualAmount = billShopBalanceDeduct.getDeductAmount();
		}

		// 系统收入-系统扣费-票前费用
		BigDecimal deductDiffAmount = billShopBalanceBrand.getDeductDiffAmount();
		if (deductDiffAmount == null) {
			deductDiffAmount = BigDecimal.ZERO;
		}

		BigDecimal balanceDeductAmount = billShopBalanceBrand.getBalanceDeductAmount();
		if (balanceDeductAmount == null) {
			balanceDeductAmount = BigDecimal.ZERO;
		}
		// 扣费总额 = 系统扣费 + 扣费差异 + 实际扣费金额
		// deduct_allamount = deduct_diff_amount + diff_amount + actual_amount

		BigDecimal diffAmount = billShopBalanceBrand.getBalanceDiffAmount();// .getDiffAmount();
		if (diffAmount == null) {
			diffAmount = BigDecimal.ZERO;
		}

		// BigDecimal actualAmount = billShopBalanceBrand.getActualAmount();
		// if(actualAmount == null) {
		// actualAmount = BigDecimal.ZERO;
		// }

		billShopBalanceBrand.setDeductAllamount(deductDiffAmount.add(diffAmount).add(balanceDeductAmount));

		// 系统开票金额 =系统收入-系统扣费-票前费用
		// 商场开票金额=系统收入-扣费总额（结算差异 +系统扣费+票前费用）
		// 结算差异 balanceDiffAmount = 结算差异.扣费差异 按品牌汇总

		billShopBalanceBrand.setSystemBillingAmount(billShopBalanceBrand.getSalesAmount().subtract(deductDiffAmount)
				.subtract(balanceDeductAmount));
		billShopBalanceBrand.setMallBillingAmount(billShopBalanceBrand.getSalesAmount().subtract(
				billShopBalanceBrand.getDeductAllamount()));
		return billShopBalanceBrand;
	}

	/**
	 * 通过结算单对象初始化费用对象
	 * 
	 * @param billShopBalance结算单对象
	 * @return 初始化后的费用对象
	 * @throws Exception
	 */
	private BillShopBalanceDeduct initBillDeduct(BillShopBalance billShopBalance) throws Exception {
		BillShopBalanceDeduct billShopBalanceDeduct = new BillShopBalanceDeduct();
		// 保存 按结算月 到费用表
		billShopBalanceDeduct.setSystemSalesAmount(billShopBalance.getSystemSalesAmount());
		billShopBalanceDeduct.setDeductDate(billShopBalance.getBalanceEndDate());
		billShopBalanceDeduct.setCreateUser(billShopBalance.getCreateUser());
		billShopBalanceDeduct.setCreateTime(DateUtil.getCurrentDateTime());
		billShopBalanceDeduct.setUpdateUser(billShopBalance.getUpdateUser());
		billShopBalanceDeduct.setUpdateTime(DateUtil.getCurrentDateTime());
		billShopBalanceDeduct.setMonth(billShopBalance.getMonth());
		billShopBalanceDeduct.setCompanyNo(billShopBalance.getCompanyNo());
		billShopBalanceDeduct.setCompanyName(billShopBalance.getCompanyName());
		billShopBalanceDeduct.setShopNo(billShopBalance.getShopNo());
		billShopBalanceDeduct.setShortName(billShopBalance.getShortName());
		// billShopBalanceDeduct.setBalanceNo(billShopBalance.getBalanceNo());
		billShopBalanceDeduct.setBalanceStartDate(billShopBalance.getBalanceStartDate());
		billShopBalanceDeduct.setBalanceEndDate(billShopBalance.getBalanceEndDate());
		billShopBalanceDeduct.setBillDate(new Date());
		billShopBalanceDeduct.setStatus(1);
		// 设置店铺相关数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalanceDeduct.getShopNo());
		Shop shop = shopService.selectSubsiInfo(params);
		if (shop != null) {
			billShopBalanceDeduct.setOrganNo(shop.getOrganNo());
			billShopBalanceDeduct.setOrganName(shop.getOrganName());
			billShopBalanceDeduct.setBsgroupsNo(shop.getBsgroupsNo());
			billShopBalanceDeduct.setBsgroupsName(shop.getBsgroupsName());
			billShopBalanceDeduct.setMallNo(shop.getMallNo());
			billShopBalanceDeduct.setMallName(shop.getMallName());
		}
		ShopBrand shopBrand = null;
		try {
			shopBrand = getShopBrandInfo(params);
		} catch (ServiceException e) {
			throw new Exception(e.getMessage(), e);
		}
		if (shopBrand != null && !"".equals(shopBrand)) {
			billShopBalanceDeduct.setBrandNo(shopBrand.getBrandNo());
			billShopBalanceDeduct.setBrandName(shopBrand.getName());
		}
		return billShopBalanceDeduct;
	}

	/**
	 * 处理商场费用
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @throws Exception
	 *             异常
	 */
	private void processBillDeductData(BillShopBalance billShopBalance) throws Exception {
		// 设置查询条件，取结算期 费用生成处理状态 0-不生成、1-生成
		Map<String, Object> balanceDateParams = new HashMap<String, Object>();
		balanceDateParams.put("shopNo", billShopBalance.getShopNo());
		balanceDateParams.put("month", billShopBalance.getMonth());
		balanceDateParams.put("notEqualBalanceFlag", 2);// 不是已生成状态
		// balanceDateParams.put("deductStatus", 1); // 0-不生成费用 1 - 生成费用
		List<ShopBalanceDate> shopBalanceDateList = shopBalanceDateService.findByBiz(null, balanceDateParams);
		if (CollectionUtils.isEmpty(shopBalanceDateList)) {
			return;
		}
		// 处理场地运营费用
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalance.getShopNo());
//		params.put("settlementPeriod", billShopBalance.getMonth());
		//调整成区间
		params.put("month", billShopBalance.getMonth());
//		params.put("settlementPeriod", billShopBalance.getMonth());
		params.put("settlementDate", billShopBalance.getBalanceStartDate());
		params.put("status", "0");
		// BillShopBalanceDeduct billShopBalanceDeduct = new
		// BillShopBalanceDeduct() ;
		List<RateExpenseOperate> rateExpenseOperateList = rateExpenseOperateService.findByBiz(null, params);
		if (rateExpenseOperateList != null && rateExpenseOperateList.size() > 0) {
			// 每条费用金额
			BigDecimal deductAmount = BigDecimal.ZERO;
			// 汇总后的票签费用对象(金额汇总)
			BillShopBalanceDeduct billShopBalanceDeduct = null;
			for (RateExpenseOperate rateExpenseOperate : rateExpenseOperateList) {
				// 查询费用表中是否已存在当前店铺、结算月、扣费类别的费用，如果存在，则只更新结算单号，否则插入费用数据
				Map<String, Object> deductParams = new HashMap<String, Object>();
				deductParams.put("shopNo", rateExpenseOperate.getShopNo());
				// deductParams.put("deductionCode","1001");//rateExpenseOperate.getDebitedType()
				// 扣费类别 1000 默认为场地经营费用

				// 获取场地经营费用
				Map<String, Object> deductSetParams = new HashMap<String, Object>();
				deductSetParams.put("companyNo", billShopBalance.getCompanyNo());
				deductSetParams.put("debitedRental", "1");
				List<MallDeductionSet> mallDeductionSetList = mallDeductionSetService.findByBiz(null, deductSetParams);
				// if(mallDeductionSetList != null &&
				// mallDeductionSetList.size() > 0) {
				// deductParams.put("deductionCode",mallDeductionSetList.get(0).getCode());
				// }
				deductParams.put("generateType", 0);
				deductParams.put("month", billShopBalance.getMonth());
				deductParams.put("balanceNo", null);
				// deductParams.put("balanceNoNull","isNull");
				// deductParams.put("costDeductType",1);
				// List<BillShopBalanceDeduct> lstbillShopBalanceDeduct =
				// billShopBalanceDeductService.findByBiz(null, deductParams);
				// // 更新费用表中的结算单号
				// if(lstbillShopBalanceDeduct != null &&
				// lstbillShopBalanceDeduct.size() > 0) {
				// for(BillShopBalanceDeduct deduct : lstbillShopBalanceDeduct)
				// {
				// // if(1==rateExpenseOperate.getDebitedMode()){ // 只处理票前费用
				// deduct.setBalanceNo(billShopBalance.getBalanceNo());
				// deduct.setBalanceStatus((byte) 2);
				// billShopBalanceDeductService.modifyById(deduct);
				// // }
				// }
				// } else {
				billShopBalanceDeduct = this.buildBillDeductByExpenseOperate(billShopBalance, rateExpenseOperate);
				if (billShopBalanceDeduct.getDeductAmount() == null
						|| billShopBalanceDeduct.getDeductAmount().compareTo(BigDecimal.ZERO) == 0) {
					billShopBalanceDeduct.setDeductAmount(deductAmount);
					billShopBalanceDeduct.setActualAmount(deductAmount);
					continue;
				}
				deductAmount = BigDecimalUtil.add(deductAmount, billShopBalanceDeduct.getDeductAmount());
				billShopBalanceDeduct.setDeductAmount(deductAmount);
				billShopBalanceDeduct.setActualAmount(deductAmount);
				billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
				billShopBalanceDeduct.setDiffAmount(billShopBalanceDeduct.getActualAmount().subtract(
						billShopBalanceDeduct.getDeductAmount()));
				if (billShopBalanceDeduct.getDiffAmount().compareTo(BigDecimal.ZERO) == 0) {
					billShopBalanceDeduct.setProcessStatus((byte) 2);
				} else {
					billShopBalanceDeduct.setProcessStatus((byte) 1);
				}
				if (rateExpenseOperate.getDebitedMode() == 1) {
					billShopBalanceDeduct.setBalanceStatus((byte) 2);
				}

				// }
			}
			if (billShopBalanceDeduct != null
					&& (billShopBalanceDeduct.getDeductAmount() != null && billShopBalanceDeduct.getDeductAmount()
							.compareTo(BigDecimal.ZERO) != 0)) {
				billShopBalanceDeductService.add(billShopBalanceDeduct);
			}
		}

		// 处理其他零星费用
		params.put("startDate", billShopBalance.getBalanceStartDate());
		params.put("endDate", billShopBalance.getBalanceEndDate());
		params.put("settlementDate", billShopBalance.getBalanceEndDate());
		List<RateExpenseSporadic> rateExpenseSporadicList = rateExpenseSporadicService.findByBiz(null, params);

		if (rateExpenseSporadicList != null && rateExpenseSporadicList.size() > 0) {
			for (RateExpenseSporadic rateExpenseSporadic : rateExpenseSporadicList) {
				// 查询费用表中是否已存在当前店铺、结算月、扣费类别的费用，如果存在，则只更新结算单号，否则插入费用数据
				Map<String, Object> deductParams = new HashMap<String, Object>();
				deductParams.put("shopNo", rateExpenseSporadic.getShopNo());
				// 取掉 deductionCode 参数控制 主要是为了手工新增的费用也可被引用到结算单
				// deductParams.put("deductionCode",rateExpenseSporadic.getDebitedType());//rateExpenseOperate.getDebitedType()
				// 扣费类别 1000 默认为场地经营费用
				deductParams.put("balanceNo", null);
				// deductParams.put("balanceNoNull","isNull");
				deductParams.put("month", billShopBalance.getMonth());
				// deductParams.put("generateType",1);
				// deductParams.put("costDeductType",1);
				// List<BillShopBalanceDeduct> lstbillShopBalanceDeduct =
				// billShopBalanceDeductService.findByBiz(null, deductParams);
				// // 更新费用表中的结算单号
				// if(lstbillShopBalanceDeduct != null &&
				// lstbillShopBalanceDeduct.size() > 0) {
				// for(BillShopBalanceDeduct deduct : lstbillShopBalanceDeduct)
				// {
				// // if(1==rateExpenseSporadic.getDebitedMode()){ //只处理票前费用
				// deduct.setBalanceNo(billShopBalance.getBalanceNo());
				// deduct.setBalanceStatus((byte) 2);
				// billShopBalanceDeductService.modifyById(deduct);
				// // }
				// }
				// } else {
				BillShopBalanceDeduct billShopBalanceDeductsp = this.buildBillDeductByExpenseSporadic(billShopBalance,
						rateExpenseSporadic);
				String sporadicBrandNo = rateExpenseSporadic.getBrandNo();
				String sporadicBrandName = rateExpenseSporadic.getBrandName();
				if (sporadicBrandNo != null && !"".equals(sporadicBrandNo)) {
					billShopBalanceDeductsp.setBrandNo(sporadicBrandNo);
				}
				if (sporadicBrandName != null && !"".equals(sporadicBrandName)) {
					billShopBalanceDeductsp.setBrandName(sporadicBrandName);
				}
				if (billShopBalanceDeductsp.getDeductAmount() != null
						&& billShopBalanceDeductsp.getDeductAmount().compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				billShopBalanceDeductService.add(billShopBalanceDeductsp);
				// }

				// 根据ID来更新零星费用
				List<String> sporadicIds = new ArrayList<String>();
				sporadicIds.add(rateExpenseSporadic.getId());

				List<BalanceCallBackDto> balanceCallBack = getBalanceCallBack(billShopBalance.getShopNo(),
						billShopBalance.getMonth(), billShopBalance.getBalanceStartDate(),
						billShopBalance.getBalanceEndDate(), sporadicIds);
				try {
					balanceRateApi.updateBalanceDate(balanceCallBack);
				} catch (RpcException e) {
					throw new ManagerException(e.getMessage(), e);
				}
			}
		}
		// 修改fas系统自己新增的费用 或者在费用生成时产生的数据记录
		Map<String, Object> deductParams = new HashMap<String, Object>();
		deductParams.put("shopNo", billShopBalance.getShopNo());
		deductParams.put("balanceNo", null);
		deductParams.put("month", billShopBalance.getMonth());
		deductParams.put("startDate", billShopBalance.getBalanceStartDate());
		deductParams.put("endDate", billShopBalance.getBalanceEndDate());
		// deductParams.put("generateType", 1); //generate_type
		// 生成方式（0：系统自动生成，1：在界面上新增）
		// deductParams.put("costDeductType",1);//只处理票前费用
		List<BillShopBalanceDeduct> lstbillShopBalanceDeduct = billShopBalanceDeductService.findByBiz(null,
				deductParams);
		if (lstbillShopBalanceDeduct != null && lstbillShopBalanceDeduct.size() > 0) {
			for (BillShopBalanceDeduct deduct : lstbillShopBalanceDeduct) {
				deduct.setBalanceNo(billShopBalance.getBalanceNo());
				deduct.setBalanceStatus((byte) 2);
				deduct.setProcessStatus((byte) 2);
				billShopBalanceDeductService.modifyById(deduct);
			}
		}
	}

	/**
	 * 通过场地经营费用对象和结算单对象组件商场扣费对象
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @param rateExpenseOperate
	 *            场地经营费用对象
	 * @return 商场扣费对象
	 * @throws Exception
	 *             异常
	 */
	private BillShopBalanceDeduct buildBillDeductByExpenseOperate(BillShopBalance billShopBalance,
			RateExpenseOperate rateExpenseOperate) throws Exception {
		BillShopBalanceDeduct billShopBalanceDeduct = this.initBillDeduct(billShopBalance);
		billShopBalanceDeduct.setMallNo(rateExpenseOperate.getMallNo());
		billShopBalanceDeduct.setMallName(rateExpenseOperate.getMallName());
		// billShopBalanceDeduct.setBrandNo(rateExpenseOperate.getBrandNo());
		// billShopBalanceDeduct.setBrandName(rateExpenseOperate.getBrandName());
		billShopBalanceDeduct.setConBaseDeductAmount(billShopBalance.getConBaseDeductAmount());
		billShopBalanceDeduct.setCostType((byte) 1);
		// billShopBalanceDeduct.setCostCateCode("1000");//rateExpenseOperate.getDebitedType()
		// if(StringUtils.isNotEmpty(rateExpenseOperate.getDebitedType())) {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("code","1001" );// rateExpenseOperate.getDebitedType()
		// 扣费类别 1000 默认为场地经营费用
		params.put("companyNo", billShopBalance.getCompanyNo());
		params.put("debitedRental", "1");
		List<MallDeductionSet> mallDeductionSetList = mallDeductionSetService.findByBiz(null, params);
		if (mallDeductionSetList != null && mallDeductionSetList.size() > 0) {
			billShopBalanceDeduct.setDeductionCode(mallDeductionSetList.get(0).getCode());
			billShopBalanceDeduct.setDeductionName(mallDeductionSetList.get(0).getName());
			billShopBalanceDeduct.setCostCateName(mallDeductionSetList.get(0).getCostName());
			billShopBalanceDeduct.setCostCateCode(mallDeductionSetList.get(0).getCostCode());
		}
		// }
		// 获取会计科目
		Map<String, Object> costSetParams = new HashMap<String, Object>();
		costSetParams.put("companyNo", billShopBalance.getCompanyNo());
		costSetParams.put("code", billShopBalanceDeduct.getCostCateCode());
		List<CostCategorySetting> costCategorySettingList = costCategorySettingService.findByBiz(null, costSetParams);
		if (costCategorySettingList != null && costCategorySettingList.size() > 0) {
			billShopBalanceDeduct.setAccountsNo(costCategorySettingList.get(0).getAccountsNo());
		}

		BillShopBalanceProSum billShopBalanceProSum = getProSumSalesAmount(billShopBalance);// billShopBalanceDeduct.getProSumSalesAmount();
		BigDecimal proSumSalesAmount = null;
		if (billShopBalanceProSum != null) {
			proSumSalesAmount = billShopBalanceProSum.getSaleAmount();
		}
		if (proSumSalesAmount == null) {
			proSumSalesAmount = BigDecimal.ZERO;
		}
		billShopBalanceDeduct.setProSumSalesAmount(proSumSalesAmount);

		BigDecimal deductAmount = getDeductAmount(rateExpenseOperate, billShopBalanceDeduct);
		if (deductAmount == null) {
			deductAmount = BigDecimal.ZERO;
		}

		if (2 == rateExpenseOperate.getSettlementType()) {
			billShopBalanceDeduct.setDeductType((byte) 1);
		} else
			billShopBalanceDeduct.setDeductType((byte) 2);

		billShopBalanceDeduct.setCostDeductType(rateExpenseOperate.getDebitedMode());
		billShopBalanceDeduct.setCostPayType(rateExpenseOperate.getPaymentMode());
		billShopBalanceDeduct.setDeductAmount(deductAmount); // 扣费金额 要处理计算
		billShopBalanceDeduct.setActualAmount(deductAmount);
		billShopBalanceDeduct.setDiffAmount(billShopBalanceDeduct.getActualAmount().subtract(
				billShopBalanceDeduct.getDeductAmount()));
		if (billShopBalanceDeduct.getDiffAmount().compareTo(BigDecimal.ZERO) == 0) {
			billShopBalanceDeduct.setProcessStatus((byte) 2);
		} else {
			billShopBalanceDeduct.setProcessStatus((byte) 1);
		}
		billShopBalanceDeduct.setDeductDate(billShopBalanceDeduct.getDeductDate());
		billShopBalanceDeduct.setRateType(1);
		billShopBalanceDeduct.setGenerateType(0);
		billShopBalanceDeduct.setId(UUIDGenerator.getUUID());

		// 票前费用才赋值
		// if(1==rateExpenseOperate.getDebitedMode()){
		billShopBalanceDeduct.setBalanceNo(billShopBalance.getBalanceNo());
		billShopBalanceDeduct.setBalanceStatus((byte) 2);
		// billShopBalanceDeduct.setProcessStatus((byte) 2);
		// }
		return billShopBalanceDeduct;
	}

	/**
	 * 通过其他零星费用对象和结算单对象组件商场扣费对象
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @param rateExpenseSporadic
	 *            其他零星费用对象
	 * @return 商场扣费对象
	 * @throws Exception
	 *             异常
	 */
	private BillShopBalanceDeduct buildBillDeductByExpenseSporadic(BillShopBalance billShopBalance,
			RateExpenseSporadic rateExpenseSporadic) throws Exception {
		BillShopBalanceDeduct billShopBalanceDeduct = this.initBillDeduct(billShopBalance);
		// billShopBalanceDeduct.setCostCateCode(rateExpenseSporadic.getDebitedType());
		billShopBalanceDeduct.setCostDeductType(rateExpenseSporadic.getDebitedMode());
		billShopBalanceDeduct.setCostPayType(rateExpenseSporadic.getPaymentMode());
		// billShopBalanceDeduct.setBrandNo(rateExpenseSporadic.getBrandNo());
		// billShopBalanceDeduct.setBrandName(rateExpenseSporadic.getBrandName());
		billShopBalanceDeduct.setCostType((byte) 1);
		billShopBalanceDeduct.setBasePayCode(rateExpenseSporadic.getBasePayCode());
		billShopBalanceDeduct.setBaseOther(rateExpenseSporadic.getBaseOther());
		billShopBalanceDeduct.setRate(rateExpenseSporadic.getRate());
		billShopBalanceDeduct.setBalanceRate(rateExpenseSporadic.getBalanceRate());
		billShopBalanceDeduct.setMallDeductionName(rateExpenseSporadic.getMallDeductionName());
		if (StringUtils.isNotEmpty(rateExpenseSporadic.getDebitedType())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", billShopBalance.getCompanyNo());
			params.put("code", rateExpenseSporadic.getDebitedType());
			List<MallDeductionSet> mallDeductionSetList = mallDeductionSetService.findByBiz(null, params);
			if (mallDeductionSetList != null && mallDeductionSetList.size() > 0) {
				billShopBalanceDeduct.setDeductionCode(mallDeductionSetList.get(0).getCode());
				billShopBalanceDeduct.setDeductionName(mallDeductionSetList.get(0).getName());
				billShopBalanceDeduct.setCostCateName(mallDeductionSetList.get(0).getCostName());
				billShopBalanceDeduct.setCostCateCode(mallDeductionSetList.get(0).getCostCode());
			}
			// 获取会计科目
			Map<String, Object> costSetParams = new HashMap<String, Object>();
			costSetParams.put("companyNo", billShopBalance.getCompanyNo());
			costSetParams.put("code", billShopBalanceDeduct.getCostCateCode());
			List<CostCategorySetting> costCategorySettingList = costCategorySettingService.findByBiz(null,
					costSetParams);
			if (costCategorySettingList != null && costCategorySettingList.size() > 0) {
				billShopBalanceDeduct.setAccountsNo(costCategorySettingList.get(0).getAccountsNo());
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", billShopBalance.getCompanyNo());
		params.put("shopNo", billShopBalance.getShopNo());
		params.put("month", billShopBalance.getMonth());
		params.put("startDate", billShopBalance.getBalanceStartDate());
		params.put("endDate", billShopBalance.getBalanceEndDate());
		params.put("balanceNo", billShopBalance.getBalanceNo());
		BigDecimal deductAmount = getDeductAmount(params, rateExpenseSporadic, billShopBalanceDeduct);
		if (deductAmount == null) {
			deductAmount = BigDecimal.ZERO;
		}

		if (1 == rateExpenseSporadic.getDebitedRule()) {
			billShopBalanceDeduct.setDeductType((byte) 1);
		} else
			billShopBalanceDeduct.setDeductType((byte) 2);

		billShopBalanceDeduct.setDeductAmount(deductAmount); // 扣费金额 要处理计算
		billShopBalanceDeduct.setActualAmount(deductAmount);
		billShopBalanceDeduct.setDiffAmount(billShopBalanceDeduct.getActualAmount().subtract(
				billShopBalanceDeduct.getDeductAmount()));
		if (billShopBalanceDeduct.getDiffAmount().compareTo(BigDecimal.ZERO) == 0) {
			billShopBalanceDeduct.setProcessStatus((byte) 2);
		} else {
			billShopBalanceDeduct.setProcessStatus((byte) 1);
		}
		billShopBalanceDeduct.setDeductDate(billShopBalanceDeduct.getDeductDate());
		if (rateExpenseSporadic.getDebitedMode() == 1) {
			billShopBalanceDeduct.setBalanceStatus((byte) 2);
		}
		billShopBalanceDeduct.setRateType(2);
		billShopBalanceDeduct.setRateId(rateExpenseSporadic.getId());
		billShopBalanceDeduct.setGenerateType(0);
		billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
		// 票前费用才赋值
		// if(1==rateExpenseSporadic.getDebitedMode()){
		billShopBalanceDeduct.setBalanceNo(billShopBalance.getBalanceNo());
		billShopBalanceDeduct.setBalanceStatus((byte) 2);
		// billShopBalanceDeduct.setProcessStatus((byte) 2);
		// }
		return billShopBalanceDeduct;
	}

	/**
	 * 设置结算单中需要结算的数据
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @return 设置后的结算单对象
	 * @throws Exception
	 *             异常
	 */
	private BillShopBalance initBillCalculateInfo(BillShopBalance billShopBalance) throws ServiceException {
		// 设置合同扣率 优先取合同正价扣 如果没有维护合同正价扣 就取阶梯扣最大的扣率
		Map<String, Object> rateParams = new HashMap<String, Object>();
		rateParams.put("shopNo", billShopBalance.getShopNo());
		rateParams.put("startDate", billShopBalance.getBalanceStartDate());
		rateParams.put("endDate", billShopBalance.getBalanceEndDate());
		rateParams.put("rateType", 1);
		List<RateBasic> rateBasicList = rateBasicService.findByBiz(null, rateParams);
		if (rateBasicList != null && rateBasicList.size() > 0) {
			billShopBalance.setContractRate(rateBasicList.get(0).getRate());// 合同扣率从mps设置读取
		} else {
			rateParams.put("rateType", 2);
			List<RateBasic> stairRateBasicList = rateBasicService.findStairRateBasicList(rateParams);
			if (!CollectionUtils.isEmpty(stairRateBasicList)) {
				// if(stairRateBasicList != null && stairRateBasicList.size() >
				// 0) {
				billShopBalance.setContractRate(stairRateBasicList.get(0).getRate());// 合同扣率从mps设置读取
			}
		}
		// 汇总结算单 系统销售金额
		// BillShopBalanceDaysaleSum billShopBalanceDaysaleSum =
		// billShopBalanceService.getSystemSalesAmount(
		// billShopBalance);

		// 2015-08-17 从大类汇总金额获取系统收入
		BillShopBalanceCateSum billShopBalanceCateSum = billShopBalanceService.getSalesAmount(billShopBalance);

		BigDecimal systemSalesAmount = BigDecimal.ZERO; // 销售总数
		if (billShopBalanceCateSum != null && billShopBalanceCateSum.getSaleAmount() != null) {
			systemSalesAmount = billShopBalanceCateSum.getSaleAmount();
		}
		// 获取合同基础扣销售额

		// BillShopBalanceDaysaleSum billShopBalanceDaysaleSumCb =
		// billShopBalanceService.getConBaseDeductAmount(rateParams);
		// 从bill_shop_balance_pro_sum 表取 根据pro_no 为空不为空来取数 不为空就是 促销期间的销售 此处取 为空的
		BillShopBalanceProSum notProSum = this.getIsNotProSumSalesAmount(billShopBalance);
		BigDecimal conBaseDeductAmount = BigDecimal.ZERO; // 合同基础扣销售总数
		BigDecimal conBaseDeduct = BigDecimal.ZERO; // 合同基础扣扣费总数
		// if(billShopBalanceDaysaleSumCb != null &&
		// billShopBalanceDaysaleSumCb.getAmount() != null) {
		// conBaseDeductAmount = billShopBalanceDaysaleSumCb.getAmount();
		// conBaseDeduct = billShopBalanceDaysaleSumCb.getSaleAmount();
		// }
		if (notProSum != null && notProSum.getSaleAmount() != null) {
			conBaseDeductAmount = notProSum.getSaleAmount();
			conBaseDeduct = notProSum.getDeductAmount();
		}
		billShopBalance.setSystemSalesAmount(systemSalesAmount);
		billShopBalance.setConBaseDeductAmount(conBaseDeductAmount);
		billShopBalance.setConBaseDeduct(conBaseDeduct);
		return billShopBalance;
	}

	private void modifyExpectCash(BillShopBalance billShopBalance) throws ServiceException {
		// ExpectCash expectCash = new ExpectCash();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalance.getShopNo());
		params.put("month", billShopBalance.getMonth());
		// params.put("status", "2");
		params.put("confirmFlag", "2");
		params.put("balanceStatus", "1");
		List<ExpectCash> expectCashList = expectCashService.findByBiz(null, params);
		if (expectCashList != null && expectCashList.size() > 0) {
			for (ExpectCash expectCash : expectCashList) {
				expectCash.setId(expectCashList.get(0).getId());
				expectCash.setBalanceNo(billShopBalance.getBalanceNo());
				expectCash.setBalanceStatus((byte) 2);
				expectCashService.modifyById(expectCash);
			}
		}
	}

	/**
	 * 更新接口数据
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @throws ManagerException
	 * @throws Exception
	 *             异常
	 */
	private void modifyApiData(BillShopBalance billShopBalance) throws ServiceException, RpcException, ManagerException {
		ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
		shopBalanceDatePar.setShopNo(billShopBalance.getShopNo());
		shopBalanceDatePar.setMonth(billShopBalance.getMonth());
		shopBalanceDatePar.setId(billShopBalance.getShopBalanceDateId());
		// 更新结算期 已结算标示 正式的才去更新 保存有数据的 || iCount > 0
		if (billShopBalance.getBalanceType().intValue() == ShopMallEnums.BalanceType.FORMAL_BALANCE.getRequestId()
				.intValue()) {
			// 正式结算单
			shopBalanceDatePar.setBalanceFlag((byte) 2);
			// 更新MPS
			List<BalanceCallBackDto> balanceCallBack = getBalanceCallBack(billShopBalance.getShopNo(),
					billShopBalance.getMonth(), billShopBalance.getBalanceStartDate(),
					billShopBalance.getBalanceEndDate(), null);
			balanceRateApi.updateBalanceDate(balanceCallBack);

			// 更新POS
			List<String> shopNoList = new ArrayList<String>();
			shopNoList.add(billShopBalance.getShopNo());
			OcOrderParameterParentDto ocOrderParameterParentDto = getOcOrderParameterParentDto(
					billShopBalance.getShopNo(), billShopBalance.getBalanceStartDate(),
					billShopBalance.getBalanceEndDate());
			orderMainApi.financeConfirm(shopNoList, billShopBalance.getBalanceNo(), ocOrderParameterParentDto);

			Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
			systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
			systemParamSetParams.put("paramCode", "MS_ExpectCash_Pro");
			systemParamSetParams.put("status", 1);

			String value = getSystemParamSetValue(systemParamSetParams);
			if ("0".equals(value)) {// 参与结算 处理预收款的业务流程
				updateExpectCash(billShopBalance);
			} else if ("1".equals(value)) {
			} else
				// 默认参与计算
				updateExpectCash(billShopBalance);

		} else {
			// 预估结算单
			shopBalanceDatePar.setBalanceFlag((byte) 0);
		}
		shopBalanceDateService.modifyById(shopBalanceDatePar);
	}

	public int updateExpectCash(BillShopBalance billShopBalance) throws ServiceException, RpcException {
		// 更新预收款的 balance_no balance_status month
		// 更新pos预收款
		List<ExpectCashDto> expectCashDtos = new ArrayList();
		// ExpectCashDto expectCashDto = new ExpectCashDto();
		// ExpectCash expectCash = new ExpectCash();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalance.getShopNo());
		params.put("month", billShopBalance.getMonth());
		// params.put("status", "2");
		params.put("balanceStatus", "1");
		params.put("confirmFlag", "2");
		List<ExpectCash> expectCashList = expectCashService.findByBiz(null, params);
		if (expectCashList != null && expectCashList.size() > 0) {
			for (ExpectCash expectCash : expectCashList) {
				ExpectCashDto expectCashDto = new ExpectCashDto();
				// expectCash.setBalanceNo(expectCashList.get(0).getBalanceNo());
				// expectCash.setBalanceStatus((byte) 2);
				expectCashDto.setShopNo(billShopBalance.getShopNo());
				expectCashDto.setId(expectCash.getId());
				expectCashDto.setBalanceNo(billShopBalance.getBalanceNo());
				expectCashDto.setBalanceStatus(2);
				expectCashDto.setConfirmFlag(expectCash.getConfirmFlag());
				expectCashDto.setMonth(expectCash.getMonth());
				expectCashDto.setUpdateUser(billShopBalance.getCreateUser());
				expectCashDto.setUpdateTime(billShopBalance.getCreateTime());
				expectCashDtos.add(expectCashDto);
			}
			expectCashApi.updateExpectCashList(expectCashDtos);

			// FAS本库就不在更新
			// this.modifyExpectCash(billShopBalance);
		}
		return 0;
	}

	/**
	 * 根据shopno获取商业集团 商场等信息
	 */
	protected Shop getShopInfo(Map<String, Object> params) throws ServiceException {
		Shop shop;
		try {
			shop = shopService.selectSubsiInfo(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return shop;
	}

	/**
	 * 根据shopno获取品牌等信息
	 */
	protected ShopBrand getShopBrandInfo(Map<String, Object> params) throws ServiceException {
		ShopBrand shopBrand;
		try {
			shopBrand = shopService.selectShopBrandInfo(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return shopBrand;
	}

	/**
	 * 检查是否存在 确认 单据状态(1-制单、2-确认、3-作废)
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int getShopBalanceAudit(Map<String, Object> params) throws ServiceException {
		int count = 0;
		try {
			params.put("status", 2);
			count = billShopBalanceService.findCount(params);
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return count;
	}

	/**
	 * 检查是否存在 制单
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int getShopBalanceCreate(Map<String, Object> params) throws ServiceException {
		int count = 0;
		try {
			params.put("status", 1);
			count = billShopBalanceService.findCount(params);
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return count;
	}

	public <ModelType> List<ModelType> getBalanceDate(ModelType modelType, Map<String, Object> params)
			throws ServiceException {
		try {
			return shopBalanceDateService.findByBiz(modelType, params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	/*
	 * 按支付方式取汇总数
	 */
	public List<BillShopBalanceDaysaleSum> getPaymentMethodSum(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		List<BillShopBalanceDaysaleSum> billShopBalanceDaysaleSum = new ArrayList<BillShopBalanceDaysaleSum>();
		billShopBalanceDaysaleSum = billShopBalanceService.getPaymentMethodSum(page, orderByField, orderBy, params);
		return billShopBalanceDaysaleSum;
	}

	/*
	 * 向billShopBalance赋值
	 */
	public BillShopBalance getBillShopBalance(Map<String, Object> params) throws ServiceException {
		BillShopBalance billShopBalance = new BillShopBalance();
		billShopBalance.setCompanyNo(String.valueOf(params.get("companyNo")));
		billShopBalance.setShopNo(String.valueOf(params.get("shopNo")));
		billShopBalance.setMonth(String.valueOf(params.get("month")));
		billShopBalance.setBalanceStartDate((Date) params.get("startDate"));
		billShopBalance.setBalanceEndDate((Date) params.get("endDate"));
		billShopBalance.setBalanceNo(String.valueOf(params.get("balanceNo")));
		return billShopBalance;
	}

	/*
	 * 保存从pos获取预收款
	 */
	public int saveExpectCash(Map<String, Object> params, ExpectCashParamsDto expectCashParamsDto) throws RpcException,
			ServiceException {
		SimplePageDto pageDto = new SimplePageDto();
		String pageSizeTemp = params.get("pageSize").toString();
		String pageNumberTemp = params.get("pageNumber").toString();
		if (pageSizeTemp != null && !"".equals(pageSizeTemp)) {
			pageDto.setPageSize(Integer.valueOf(pageSizeTemp));
		}
		if (pageNumberTemp != null && !"".equals(pageNumberTemp)) {
			pageDto.setPageNo(Integer.valueOf(pageNumberTemp));
		}
		PagingDto<ExpectCashDto> expectCashDtoList = expectCashApi.findList4ExpectCash(pageDto, expectCashParamsDto);
		int count = 0;
		if (!CollectionUtils.isEmpty((expectCashDtoList.getResult()))) {
			for (ExpectCashDto expectCashDto : expectCashDtoList.getResult()) {
				ExpectCash expectCash = new ExpectCash();
				expectCash.setId(expectCashDto.getId());
				expectCash = expectCashService.findById(expectCash);
				if (expectCash != null && !"".equals(expectCash)) {
					expectCashService.deleteById(expectCash);
				}
				count = expectCashService.add(expectCashDto);
			}
		}
		return count;
	}

	/*
	 * 获取预收款参数组装
	 */
	public ExpectCashParamsDto getExpectCashParams(String shopNo, String month, Date startDate, Date endDate) {
		ExpectCashParamsDto expectCashParamsDto = new ExpectCashParamsDto();
		expectCashParamsDto.setShopNo(shopNo);
		expectCashParamsDto.setStartDate(startDate);
		expectCashParamsDto.setEndDate(endDate);
		expectCashParamsDto.setStatus("0");
		expectCashParamsDto.setIsValid(true);
		return expectCashParamsDto;
	}

	/**
	 * 从每日销售汇总获取系统销售总金额
	 */
	protected BillShopBalanceDaysaleSum getSystemSalesAmount(BillShopBalance billShopBalance) throws ServiceException {
		BillShopBalanceDaysaleSum billShopBalanceDaysaleSum;
		try {
			// BigDecimal systemSalesnum = billShopBalance.getSystemSalesnum();
			// 查询汇总金额
			billShopBalanceDaysaleSum = billShopBalanceService.getSystemSalesAmount(billShopBalance);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return billShopBalanceDaysaleSum;
	}

	/**
	 * 实现按活动进行汇总
	 * 
	 * @param billShopBalance
	 * @return
	 * @throws ServiceException
	 */
	protected BillShopBalanceProSum getProSumSalesAmount(BillShopBalance billShopBalance) throws ServiceException {
		BillShopBalanceProSum billShopBalanceProSum;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("balanceNo", billShopBalance.getBalanceNo());
			if (billShopBalance.getBalanceStartDate() != null) {
				params.put("balanceStartDate", DateUtil.format1(billShopBalance.getBalanceStartDate()));
			}
			if (billShopBalance.getBalanceEndDate() != null) {
				params.put("balanceEndDate", DateUtil.format1(billShopBalance.getBalanceEndDate()));
			}
			billShopBalanceProSum = billShopBalanceService.getProSumSalesAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return billShopBalanceProSum;
	}

	/**
	 * 获取系统扣费
	 */
	public BigDecimal getDeductDiffAmount(BillShopBalance billShopBalance) throws ServiceException {
		BigDecimal deductDiffAmount = new BigDecimal("0");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("balanceNo", billShopBalance.getBalanceNo());
			if (billShopBalance.getBalanceStartDate() != null) {
				params.put("balanceStartDate", DateUtil.format1(billShopBalance.getBalanceStartDate()));
			}
			if (billShopBalance.getBalanceEndDate() != null) {
				params.put("balanceEndDate", DateUtil.format1(billShopBalance.getBalanceEndDate()));
			}
			deductDiffAmount = billShopBalanceService.getBalanceDeductDiffAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return deductDiffAmount;
	}

	/**
	 * 实现按活动进行汇总 取促销的
	 * 
	 * @param billShopBalance
	 * @return
	 * @throws ServiceException
	 */
	protected BillShopBalanceProSum getIsProSumSalesAmount(BillShopBalance billShopBalance) throws ServiceException {
		BillShopBalanceProSum billShopBalanceProSum;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("balanceNo", billShopBalance.getBalanceNo());
			params.put("proNoNotNull", "1");
			if (billShopBalance.getBalanceStartDate() != null) {
				params.put("balanceStartDate", DateUtil.format1(billShopBalance.getBalanceStartDate()));
			}
			if (billShopBalance.getBalanceEndDate() != null) {
				params.put("balanceEndDate", DateUtil.format1(billShopBalance.getBalanceEndDate()));
			}
			billShopBalanceProSum = billShopBalanceService.getProSumSalesAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return billShopBalanceProSum;
	}

	protected BillShopBalanceProSum getIsNotProSumSalesAmount(BillShopBalance billShopBalance) throws ServiceException {
		BillShopBalanceProSum billShopBalanceProSum;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("balanceNo", billShopBalance.getBalanceNo());
			params.put("proNoNull", "1");
			if (billShopBalance.getBalanceStartDate() != null) {
				params.put("balanceStartDate", DateUtil.format1(billShopBalance.getBalanceStartDate()));
			}
			if (billShopBalance.getBalanceEndDate() != null) {
				params.put("balanceEndDate", DateUtil.format1(billShopBalance.getBalanceEndDate()));
			}
			billShopBalanceProSum = billShopBalanceService.getProSumSalesAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return billShopBalanceProSum;
	}

	/**
	 * 预收款金额合计
	 */
	protected BigDecimal getExpectCashAmount(BillShopBalance billShopBalance) throws ServiceException {
		BigDecimal amount = new BigDecimal("0");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("month", billShopBalance.getMonth());
			// params.put("status", "2");
			params.put("balanceStatus", "1");
			params.put("confirmFlag", "2");
			// billShopBalance.setStatus(2);
			// billShopBalance.setBalanceStatus(1);
			amount = billShopBalanceService.getExpectCashAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return amount;
	}

	protected BigDecimal getExpectCashAmountBrand(BillShopBalance billShopBalance,
			BillShopBalanceBrand billShopBalanceBrand) throws ServiceException {
		BigDecimal amount = new BigDecimal("0");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("month", billShopBalance.getMonth());
			// params.put("status", "2");
			params.put("balanceStatus", "1");
			params.put("confirmFlag", "2");
			params.put("brandNo", billShopBalanceBrand.getBrandNo());
			// billShopBalance.setStatus(2);
			// billShopBalance.setBalanceStatus(1);
			amount = billShopBalanceService.getExpectCashAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return amount;
	}

	protected ExpectCash getExpectCashAmountBrandCate(BillShopBalance billShopBalance,
			BillShopBalanceCateSum billShopBalanceCateSum) throws ServiceException {
		BigDecimal amount = new BigDecimal("0");
		ExpectCash expectCash = new ExpectCash();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("month", billShopBalance.getMonth());
			// params.put("status", "2");
			params.put("balanceStatus", "1");
			params.put("confirmFlag", "2");
			params.put("brandNo", billShopBalanceCateSum.getBrandNo());
			params.put("categoryNo", billShopBalanceCateSum.getCategoryNo());
			// billShopBalance.setStatus(2);
			// billShopBalance.setBalanceStatus(1);
			expectCash = expectCashService.getExpectCashAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return expectCash;
	}

	protected ExpectCash getExpectCashAmountBrandCateShare(BillShopBalance billShopBalance,
			BillShopBalanceCateSum billShopBalanceCateSum) throws ServiceException {
		BigDecimal amount = new BigDecimal("0");
		ExpectCash expectCash = new ExpectCash();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("month", billShopBalance.getMonth());
			// params.put("status", "2");
			params.put("balanceStatus", "1");
			params.put("confirmFlag", "2");
			params.put("brandNo", billShopBalanceCateSum.getBrandNo());
			params.put("categoryNo", billShopBalanceCateSum.getCategoryNo());
			// billShopBalance.setStatus(2);
			// billShopBalance.setBalanceStatus(1);
			expectCash = expectCashService.getExpectCashAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return expectCash;
	}

	/**
	 * 根据结算单汇总该结算单对应的票前费用扣款金额合计
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @return 票前费用合计
	 */
	public BigDecimal getBalanceDeductAmount(BillShopBalance billShopBalance) {
		if (StringUtils.isEmpty(billShopBalance.getBalanceNo())) {
			return BigDecimal.ZERO;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", billShopBalance.getBalanceNo());
		params.put("costDeductType", ShopMallEnums.CostDeductType.BEFORE_INVOICE.getRequestId());
		// params.put("status", 2); // 已结
		BigDecimal amount = billShopBalanceService.getBalanceDeductAmount(params);
		if (amount == null) {
			return BigDecimal.ZERO;
		}
		return amount;
	}

	/**
	 * 通过结算单汇总结算差异金额
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @return 汇总的结算差异金额
	 */
	public BigDecimal getBalanceDiffAmount(BillShopBalance billShopBalance) {
		if (StringUtils.isEmpty(billShopBalance.getBalanceNo())) {
			return BigDecimal.ZERO;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", billShopBalance.getBalanceNo());
		// params.put("status", 1); // 已完成
		BigDecimal amount = billShopBalanceService.getBalanceDiffAmount(params);
		if (amount == null) {
			return BigDecimal.ZERO;
		}
		return amount;
	}

	/**
	 * 扣费金额
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @throws ServiceException
	 *             异常
	 */
	public BigDecimal getBalanceDeductDiffAmount(BillShopBalance billShopBalance) throws ServiceException {
		if (StringUtils.isEmpty(billShopBalance.getBalanceNo())) {
			return BigDecimal.ZERO;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", billShopBalance.getBalanceNo());
		List<BillShopBalanceProSum> billShopBalanceProSum = billShopBalanceProSumService.getSumAmount(params);
		BigDecimal amount = null;
		if (billShopBalanceProSum != null && billShopBalanceProSum.size() > 0) {
			for (BillShopBalanceProSum shopBalanceProSum : billShopBalanceProSum) {
				amount = shopBalanceProSum.getDeductAmount();
			}
		}
		if (amount == null) {
			return BigDecimal.ZERO;
		}
		return amount;
		// params.put("status", 1); // 已完成
		// BigDecimal amount =
		// billShopBalanceService.getBalanceDeductDiffAmount(params);
		// if(amount == null) {
		// return BigDecimal.ZERO;
		// }
		// return amount;
	}

	/**
	 * 回款金额
	 */
	public BigDecimal getPaymentAmount(BillShopBalance billShopBalance) throws ManagerException {
		BigDecimal amount = new BigDecimal("0");
		try {
			amount = billShopBalanceService.getPaymentAmount(billShopBalance);
		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		}
		return amount;
	}

	/**
	 * 数据计算
	 * 
	 * @throws ManagerException
	 *             ,ServiceException
	 */
	public BillShopBalance getNumDataCalc(BillShopBalance billShopBalance) throws ManagerException, ServiceException {
		/**
		 * 计算公式 实际扣率 = （系统销售-开票金额）/系统销售 billingAmount开票金额 = 系统销售-其他合计，是否是商场报数？
		 * otherTotalAmount其他合计 =
		 * -商场预收款本次结算金额合计expectCashAmount+票前费用实际扣款金额合计balanceDeductAmount
		 * +结算差异的差异金额合计balanceDiffAmount differenceAmount收款差异 = 收款信息的收款合计-开票金额
		 * 扣费金额 = 扣费差异 + 差异金额 mallDeductAmount = deductDiffAmount + diffAmount
		 **/

		/**
		 * add 实际扣率 = （系统收入-开票金额）/系统收入 actualRate systemSalesAmount 扣费金额 = 系统扣费
		 * +结算差异的差异金额合计 其他合计 = 票前费用实际扣款金额合计+扣费金额 开票金额 = 系统收入-其他合计 收款差异 =
		 * 收款信息的收款合计-开票金额
		 * 
		 * 销售差异=系统收入-商场报数 系统扣费=系统收入 * 扣率 差异金额 = (商场报数-系统收入)*扣率
		 * 差异余额”=“差异金额”+“差异回款”+“调整”
		 * 
		 * “差异金额”系统自动计算，为差异金额=（商场报数-系统收入）*对应活动扣率；修改“商场报数”，“差异金额”列根据公式计算更新；修改“
		 * 差异金额”，
		 * 
		 * “商场报数”列不更新； “差异余额”应系统自动计算，为“差异余额”=“差异金额”+“差异回款”+“调整”）；
		 */

		/**
		 * 目前系统调整的计算公式 系统开票金额：系统收入-扣费金额-票前费用金额 扣费金额=系统收入*扣率 票前费用=票前费用实际扣费之和
		 * 结算差异=结算差异页前的结算差异汇总 系统校验：系统开票金额-商场开票金额=结算差异，提示差异额有多少 其他合计去掉
		 */
		try {
			BigDecimal amount = new BigDecimal("0");
			// BigDecimal expectCashAmount=new BigDecimal("0");
			// 票前费用扣费金额合计
			BigDecimal balanceDeductAmount = this.getBalanceDeductAmount(billShopBalance); // 票前费用金额

			BigDecimal paymentAmount = this.getPaymentAmount(billShopBalance);
			BigDecimal billingAmount = new BigDecimal("0"); // 开票金额
			BigDecimal systemBillingAmount = new BigDecimal("0"); // 系统开票金额
			BigDecimal otherTotalAmount = new BigDecimal("0"); // 其他合计

			BigDecimal zeanAmount = new BigDecimal("0");

			// BigDecimal balanceDiffAmount =
			// this.getBalanceDiffAmount(billShopBalance); //结算差异金额
			// BigDecimal mallDeductAmount =
			// this.getBalanceDeductDiffAmount(billShopBalance); //扣费金额
			if (null == paymentAmount) {
				paymentAmount = new BigDecimal("0");
			}

			if (null == billShopBalance.getPrepaymentAmount()) {
				billShopBalance.setPrepaymentAmount(zeanAmount);
			}

			if (null == billShopBalance.getUsedPrepaymentAmount()) {
				billShopBalance.setUsedPrepaymentAmount(zeanAmount);
			}

			// 重新计算刷新单据头的销售收入
			billShopBalance.setSystemSalesAmount(billShopBalance.getSystemSalesAmount());

			BigDecimal systemSalesAmount = billShopBalance.getSystemSalesAmount();
			if (null == systemSalesAmount) {
				systemSalesAmount = BigDecimal.valueOf(0);
			}

			BigDecimal mallBillingAmount = billShopBalance.getMallBillingAmount();
			if (null == mallBillingAmount) {
				mallBillingAmount = BigDecimal.valueOf(0);
			}
			// 扣费总额 = 系统收入 - 商场开票金额
			BigDecimal mallDeductAmount = systemSalesAmount.subtract(mallBillingAmount);// BigDecimalUtil.subtract(systemSalesAmount,
																						// mallBillingAmount);

			BigDecimal mallNumberAmount = billShopBalance.getMallNumberAmount();// 商场报数
			if (null == mallNumberAmount) {
				mallNumberAmount = BigDecimal.valueOf(0);
			}

			// otherTotalAmount=
			// amount.subtract(expectCashAmount).add(balanceDeductAmount).add(balanceDiffAmount);
			otherTotalAmount = balanceDeductAmount.add(mallDeductAmount);
			billingAmount = billShopBalance.getSystemSalesAmount().subtract(otherTotalAmount);

			// 按活动汇总的数据
			BillShopBalanceProSum billShopBalanceProSum = this.getProSumSalesAmount(billShopBalance);
			BigDecimal proSumSalesAmount = null;
			BigDecimal promDeductAmount = null;
			if (billShopBalanceProSum != null) {
				proSumSalesAmount = billShopBalanceProSum.getSaleAmount();
				// promDeductAmount = billShopBalanceProSum.getDeductAmount();
			}

			// 扣费金额从结算差异中取数
			promDeductAmount = this.getDeductDiffAmount(billShopBalance);

			// 按活动汇总的销售金额收入
			if (proSumSalesAmount == null) {
				proSumSalesAmount = BigDecimal.ZERO;
			}
			// 按活动汇总的扣费金额
			if (promDeductAmount == null) {
				promDeductAmount = BigDecimal.ZERO;
			}
			// 系统开票金额=系统收入-销售单查询页签中按活动汇总的扣费合计-票前费用系统扣费金额合计
			// systemBillingAmount =
			// systemSalesAmount.subtract(promDeductAmount).subtract(balanceDeductAmount);
			systemBillingAmount = BigDecimalUtil.format(systemSalesAmount, BigDecimalUtil.POINT_2_PATTERN)
					.subtract(BigDecimalUtil.format(promDeductAmount, BigDecimalUtil.POINT_2_PATTERN))
					.subtract(BigDecimalUtil.format(balanceDeductAmount, BigDecimalUtil.POINT_2_PATTERN));
			if (systemSalesAmount.compareTo(new BigDecimal("0")) != 0) {
				// 实际扣率： （系统收入-商场开票金额）/系统收入
				billShopBalance.setActualRate(((systemSalesAmount.subtract(mallBillingAmount)).divide(
						systemSalesAmount, 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100)));// 实际扣率要计算
																										// 实际扣率
																										// =
																										// （系统销售-开票金额）/系统销售
			}

			// 结算差异总额 = 系统开票金额 - 商场开票
			BigDecimal balanceDiffAmount = BigDecimalUtil.format(systemBillingAmount, BigDecimalUtil.POINT_2_PATTERN)
					.subtract(BigDecimalUtil.format(mallBillingAmount, BigDecimalUtil.POINT_2_PATTERN)); // BigDecimalUtil.subtract(systemBillingAmount,
																											// mallBillingAmount);

			billShopBalance.setOtherTotalAmount(otherTotalAmount);
			// billShopBalance.setDifferenceAmount(mallBillingAmount.subtract(paymentAmount));
			BillShopBalanceDeduct shopBalanceDeduct = getCostDeductTypeAmount(billShopBalance, null);
			BigDecimal deductAmount1 = new BigDecimal("0");
			BigDecimal actualAmount = new BigDecimal("0");
			if (shopBalanceDeduct != null) {
				deductAmount1 = shopBalanceDeduct.getDeductAmount();
				actualAmount = shopBalanceDeduct.getActualAmount();
			}
			// 收款差异=商场开票金额-票后账扣-回款金额
			BigDecimal differenceAmount = BigDecimalUtil
					.subtract(billShopBalance.getMallBillingAmount(), paymentAmount).subtract(actualAmount);
			billShopBalance.setDifferenceAmount(differenceAmount);

			// 应返款金额 =商场开票金额-票后账扣
			BigDecimal returnedAmount = BigDecimalUtil.subtract(billShopBalance.getMallBillingAmount(), actualAmount);
			billShopBalance.setReturnedAmount(returnedAmount);
			BigDecimal amountType2 = new BigDecimal("0");
			// 预估差异 = 正式结算单商场开票金额-预估结算的商场开票金额
			// balance_type 结算单类型(1-正式，2-预估)
			// System.out.println("billShopBalance.getBalanceType: "+billShopBalance.getBalanceType());
			if (1 == billShopBalance.getBalanceType().intValue()) { // 正式
				// BillShopBalance billShopBalanceT2 = new BillShopBalance();
				// billShopBalanceT2.setShopNo(billShopBalance.getShopNo());
				// billShopBalanceT2.setMonth(billShopBalance.getMonth());
				// billShopBalanceT2.setBalanceStartDate(billShopBalance.getBalanceStartDate());
				// billShopBalanceT2.setBalanceEndDate(billShopBalance.getBalanceEndDate());
				// billShopBalanceT2.setBalanceType((byte) 2);
				Map<String, Object> billShopBalanceTPparams = new HashMap<String, Object>();
				billShopBalanceTPparams.put("shopNo", billShopBalance.getShopNo());
				billShopBalanceTPparams.put("month", billShopBalance.getMonth());
				billShopBalanceTPparams.put("startDate",
						DateUtil.format(billShopBalance.getBalanceStartDate(), "yyyy-MM-dd"));
				// DateUtil.parseToDate(String.valueOf(billShopBalance.getBalanceStartDate()),
				// "yyyy-MM-dd"));
				billShopBalanceTPparams.put("endDate",
						DateUtil.format(billShopBalance.getBalanceEndDate(), "yyyy-MM-dd"));
				// DateUtil.parseToDate(String.valueOf(billShopBalance.getBalanceEndDate()),
				// "yyyy-MM-dd"));
				billShopBalanceTPparams.put("balanceType", "2");
				List<BillShopBalance> billShopBalanceType2List = billShopBalanceService.findByBiz(null,
						billShopBalanceTPparams);
				if (billShopBalanceType2List != null && billShopBalanceType2List.size() > 0) {
					for (BillShopBalance billShopBalanceType2 : billShopBalanceType2List) {
						amountType2 = billShopBalanceType2.getMallBillingAmount();
						amount = billShopBalance.getMallBillingAmount().subtract(amountType2);
					}
				}
			} else if (2 == billShopBalance.getBalanceType().intValue()) { // 预估
				amount = amount.subtract(billShopBalance.getMallBillingAmount());
			}

			billShopBalance.setEstimateAmount(amount);
			billShopBalance.setBillingAmount(billingAmount);
			// billShopBalance.setExpectCashAmount(expectCashAmount);
			billShopBalance.setBalanceDeductAmount(balanceDeductAmount);
			billShopBalance.setBalanceDiffAmount(balanceDiffAmount);
			billShopBalance.setPaymentAmount(paymentAmount);
			billShopBalance.setMallDeductAmount(mallDeductAmount);
			billShopBalance.setSystemBillingAmount(systemBillingAmount);
			billShopBalance.setSalesDiffamount(systemSalesAmount.subtract(mallNumberAmount)); // 报数差异=系统收入-商场报数

			BigDecimal rateNum = new BigDecimal("100");
			BigDecimal conpriceDeductAmount = billShopBalance.getConBaseDeduct();
			billShopBalance.setConpriceDeductAmount(conpriceDeductAmount);

			// 按活动汇总的数据 取促销扣费
			BillShopBalanceProSum proSum = this.getIsProSumSalesAmount(billShopBalance);
			BigDecimal promAmount = null;
			if (proSum != null) {
				promAmount = proSum.getDeductAmount();
			}
			// promAmount 为null赋0
			if (promAmount == null) {
				promAmount = BigDecimal.ZERO;
			}
			billShopBalance.setPromDeductAmount(promAmount);

			BigDecimal contractRate = billShopBalance.getContractRate();
			if (null == contractRate) {
				contractRate = BigDecimal.valueOf(0);
			}
			billShopBalance.setPromPlusbuckleAmount(promDeductAmount.subtract(proSumSalesAmount.multiply(contractRate)
					.divide(rateNum)));// 促销加扣 = 促销扣费-（促销销售*合同扣率）

			BigDecimal adjustDeductAmount = null;
			BigDecimal adjustDiffAmount = null;
			billShopBalance.setAdjustDeductAmount(adjustDeductAmount);
			billShopBalance.setAdjustDiffAmount(adjustDiffAmount);

			// 获取扣费差异
			BigDecimal deductAmount = getDeductDiffAmount(billShopBalance);
			if (deductAmount == null) {
				deductAmount = BigDecimal.ZERO;
			}
			// 其他扣费= 系统收入-开票金额-（扣费差异）
			BigDecimal otherDeduct = systemSalesAmount.subtract(billingAmount).subtract(deductAmount);
			billShopBalance.setOtherDeduct(otherDeduct);
		} catch (Exception e) {
			logger.error("shopBalance getNumDataCalc", e);
			throw new ManagerException(e.getMessage(), e);
		}
		return billShopBalance;
	}

	/**
	 * 实现按活动进行汇总
	 * 
	 * @param billShopBalance
	 * @return
	 * @throws ServiceException
	 */
	protected ExpectCash getExpectCashAmountOld(BillShopBalance billShopBalance) throws ServiceException {
		ExpectCash expectCash = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopNo", billShopBalance.getShopNo());
			params.put("month", billShopBalance.getMonth());

			// expectCash = billShopBalanceService.getProSumSalesAmount(params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return expectCash;
	}

	/*
	 * mps参数组装
	 */
	public List<BalanceParamDto> getBalanceParams(String shopNo, String month, Date startDate, Date endDate) {
		List<BalanceParamDto> balanceParams = new ArrayList<BalanceParamDto>();
		BalanceParamDto balanceParamDto = new BalanceParamDto();
		balanceParamDto.setShopNo(shopNo);
		balanceParamDto.setBalanceMonth(month);
		balanceParamDto.setStartDate(startDate);// dateFormat.parse(strDate));
		balanceParamDto.setEndDate(endDate);// dateFormat.parse(endDate1));
		balanceParamDto.setQueryType(0);
		balanceParams.add(balanceParamDto);
		return balanceParams;
	}

	/*
	 * 反写mps参数组装
	 */
	public List<BalanceCallBackDto> getBalanceCallBack(String shopNo, String month, Date startDate, Date endDate,
			List<String> sporadicIds) {
		List<BalanceCallBackDto> balanceCallBacks = new ArrayList<BalanceCallBackDto>();
		BalanceCallBackDto balanceCallBackDto = new BalanceCallBackDto();
		balanceCallBackDto.setShopNo(shopNo);
		balanceCallBackDto.setBalanceMonth(month);
		balanceCallBackDto.setBalanceDate(endDate);
		balanceCallBackDto.setBalanceType(1);
		balanceCallBackDto.setQueryType(0);
		balanceCallBackDto.setSiteFeeStatus(2);
		if (!CollectionUtils.isEmpty(sporadicIds)) {
			balanceCallBackDto.setSporadicIds(sporadicIds);
		}
		balanceCallBacks.add(balanceCallBackDto);
		return balanceCallBacks;

	}

	/*
	 * 反写mps参数组装
	 */
	public List<BalanceCallBackDto> getBalanceCallBackDel(String shopNo, String month, Date startDate, Date endDate,
			List<String> sporadicIds, Integer siteFeeStatus) {
		List<BalanceCallBackDto> balanceCallBacks = new ArrayList<BalanceCallBackDto>();
		BalanceCallBackDto balanceCallBackDto = new BalanceCallBackDto();
		balanceCallBackDto.setShopNo(shopNo);
		balanceCallBackDto.setBalanceMonth(month);
		balanceCallBackDto.setBalanceDate(endDate);
		balanceCallBackDto.setBalanceType(0);
		balanceCallBackDto.setQueryType(1);
		if (null != siteFeeStatus) {
			balanceCallBackDto.setSiteFeeStatus(siteFeeStatus);
		}
		if (!CollectionUtils.isEmpty(sporadicIds)) {
			balanceCallBackDto.setSporadicIds(sporadicIds);
		}
		balanceCallBacks.add(balanceCallBackDto);
		return balanceCallBacks;
	}

	@Override
	public List<BillShopBalanceDaysaleSum> findPaymentMethodSum(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ManagerException {
		List<BillShopBalanceDaysaleSum> billShopBalanceDaysaleSum = new ArrayList<BillShopBalanceDaysaleSum>();
		billShopBalanceDaysaleSum = billShopBalanceService.getPaymentMethodSum(page, orderByField, orderBy, params);
		return billShopBalanceDaysaleSum;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int remove(String[] ids, String createUser, Date createTime) throws ManagerException {
		int iCount = 0;
		try {
			for (String str : ids) {
				iCount = removeDetail(createUser, createTime, str);
			}
		} catch (Exception e) {
			logger.error("删除店铺结算单失败：" + ids, e);
			throw new ManagerException(e.getMessage(), e);  
		}
		return iCount;
	}

	private int removeDetail(String createUser, Date createTime, String str) throws Exception {
		int delCount = 9999;
		int iCount;
		String balanceNo = str.split(",")[1];
		String shopNoStr = str.split(",")[2];
		String balanceEndDateStr = str.split(",")[4];

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", shopNoStr);
		params.put("balanceEndDate", balanceEndDateStr);
		iCount = billShopBalanceMapper.hasNextBalanceDate(params);
		if (iCount > 0)
		{
			logger.warn("没有找到对应的单据信息." + params.toString());
			return delCount;
		}

		if (iCount <= 0) {
			Map<String, Object> balanceParams = new HashMap<String, Object>();
			balanceParams.put("balanceNo", balanceNo);
			List<BillShopBalance> billShopBalanceList = billShopBalanceMapper.selectByParams(null, balanceParams);
			if (CollectionUtils.isEmpty(billShopBalanceList)) {
				return iCount;
			}

			BillShopBalance billShopBalance = billShopBalanceList.get(0);
			String shopNo = billShopBalance.getShopNo();
			String shortName = billShopBalance.getShortName();
			String month = billShopBalance.getMonth();
			Date startDate = billShopBalance.getBalanceStartDate();
			Date endDate = billShopBalance.getBalanceEndDate();

			// 依据结算单和店铺删除其他关联数据
			// balanceParams.put("shopNo", shopNo);

			removeBillBalance(balanceNo);

			updateExpectCashList(createUser, createTime, balanceParams);

			// 更新结算期 未结算标示
			ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
			shopBalanceDatePar.setShopNo(shopNo);
			shopBalanceDatePar.setBalanceFlag((byte) 1);
			shopBalanceDatePar.setMonth(month);
			shopBalanceDatePar.setBalanceStartDate(startDate);
			shopBalanceDatePar.setBalanceEndDate(endDate);
			shopBalanceDateService.updateBalanceDateSet(shopBalanceDatePar); 
			
			// 获取已结算的最大结束日期赋值给endDate
			String remarkDesc = updateShopBlanceStatus(balanceNo, shopNo, month, startDate, endDate);

			iCount = writeOperationLog(createUser, createTime, balanceNo, billShopBalance, shopNo, shortName, month,
					startDate, endDate, remarkDesc);
			
			logger.info("删除店铺结算单,Id:" + str);
		}
		return iCount;
	}

	private int writeOperationLog(String createUser, Date createTime, String balanceNo,
			BillShopBalance billShopBalance, String shopNo, String shortName, String month, Date startDate,
			Date endDate, String remarkDesc) throws ServiceException {
		int iCount;
		// 删结算单的时候写log

		BillShopBalanceOperatelog operateLog = new BillShopBalanceOperatelog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setBalanceNo(balanceNo);
		operateLog.setCompanyNo(billShopBalance.getCompanyNo());
		operateLog.setCompanyName(billShopBalance.getCompanyName());
		operateLog.setShopNo(shopNo);
		operateLog.setShortName(shortName);
		operateLog.setMonth(month);
		operateLog.setOperateNo((byte) 3);
		operateLog.setCreateUser(createUser);
		operateLog.setCreateTime(createTime);
		operateLog.setBalanceStartDate(startDate);
		operateLog.setBalanceEndDate(endDate);
		operateLog.setRemarkDesc(remarkDesc);
		operateLog.setMallNumberAmount(billShopBalance.getMallNumberAmount());
		operateLog.setMallBillingAmount(billShopBalance.getMallBillingAmount());
		operateLog.setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(billShopBalance.getCompanyNo()));
		billShopBalanceOperatelogService.add(operateLog);

		iCount = billShopBalanceMapper.deleteByPrimarayKeyForModel(billShopBalance);
		
		logger.info("插入单据日志:" + remarkDesc);
		return iCount;
	}

	private String updateShopBlanceStatus(String balanceNo, String shopNo, String month, Date startDate, Date endDate)
			throws ServiceException, ParseException, RpcException {
		Date endDatebef = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Map<String, Object> shopBalanceDateMps = new HashMap<String, Object>();
		shopBalanceDateMps.put("shopNo", shopNo);
		shopBalanceDateMps.put("getBefMonth", 1);
		List<ShopBalanceDate> shopBalanceDateList = shopBalanceDateService.findByBiz(null, shopBalanceDateMps);

		if (!CollectionUtils.isEmpty(shopBalanceDateList)) {
			endDatebef = shopBalanceDateList.get(0).getBalanceEndDate();
		} else {
			endDatebef = formatter.parse("2000-01-01");
		}

		// 查询结算单的扣率费用 处理零星费用
		Map<String, Object> paramsRateDeduct = new HashMap<String, Object>();
		paramsRateDeduct.put("balanceNo", balanceNo);
		paramsRateDeduct.put("deductType", 2);
		String remarkDesc = "";
		List<BillShopBalanceDeduct> ratebillShopBalanceDeductList = billShopBalanceDeductMapper.selectByParams(
				null, paramsRateDeduct);
		List<BalanceCallBackDto> balanceCallBack  = new ArrayList<>();
		balanceCallBack = getBalanceCallBackDel(shopNo, month, startDate, endDatebef,
					null, 0);
		if (!CollectionUtils.isEmpty(ratebillShopBalanceDeductList)) {
			List<String> sporadicIds = new ArrayList<String>(); 
			for (BillShopBalanceDeduct billShopBalanceDeduct : ratebillShopBalanceDeductList) {
				if (StringUtils.isNotBlank(billShopBalanceDeduct.getRateId())) {
					sporadicIds.add(billShopBalanceDeduct.getRateId());
					remarkDesc += billShopBalanceDeduct.getRateId().substring(27, 32) + "|";
				}
				
				//更新本地库的
				RateExpenseSporadic rateExpenseSporadic = new RateExpenseSporadic();
				rateExpenseSporadic.setId(billShopBalanceDeduct.getRateId());
				rateExpenseSporadic = rateExpenseSporadicService.findById(rateExpenseSporadic);
				if (rateExpenseSporadic != null) {
					rateExpenseSporadic.setShopNo(shopNo);
					rateExpenseSporadic.setSettlementDate(endDatebef);
					if (!CollectionUtils.isEmpty(sporadicIds)) {
						rateExpenseSporadic.setId(billShopBalanceDeduct.getRateId());
					}
					rateExpenseSporadicMapper.updateByPrimaryKey(rateExpenseSporadic);	
				}
				
			}
			// 更新MPS
			balanceCallBack = getBalanceCallBackDel(shopNo, month, startDate, endDatebef,
					sporadicIds, 0);
			for (BalanceCallBackDto balanceCallBackDto : balanceCallBack) {
				logger.info("删结算单更新mps零星费用：shopNo" + balanceCallBackDto.getShopNo());
			}
			logger.info("删结算单更新mps零星费用完成balanceCallBack:" + JSON.toJSONString(balanceCallBack,SerializerFeature.WriteDateUseDateFormat));
			balanceRateApi.updateBalanceDate(balanceCallBack);
			
			logger.info("删结算单更新mps零星费用完成:" + balanceNo);
			
			// 删扣率类型的费用
			billShopBalanceDeductMapper.deleteBalanceNoForModel(balanceNo);
		}

		balanceRateApi.updateBalanceDate(balanceCallBack);
		
		balanceCallBack = getBalanceCallBackDel(shopNo, month, startDate, endDatebef,
				null, 0);
		logger.info("删结算单更新mps零星费用完成balanceCallBack--------:" + JSON.toJSONString(balanceCallBack,SerializerFeature.WriteDateUseDateFormat));
		balanceRateApi.updateBalanceDate(balanceCallBack);

		// 更新固定类型的费用，固定费用的单结算单号清空
		billShopBalanceDeductMapper.updateBalanceNoForModel(balanceNo);

		balanceRateApi.updateBalanceDate(balanceCallBack);
		
		// 更新POS
		List<String> shopNoList = new ArrayList<String>();
		shopNoList.add(shopNo);
		OcOrderParameterParentDto ocOrderParameterParentDto = getOcOrderParameterParentDto(shopNo, startDate,
				endDate);
		orderMainApi.financeConfirm(shopNoList, "", ocOrderParameterParentDto);
		return remarkDesc;
	}

	/**
	 * 删结算单要把预收款的结算状态修改成未结算状态处理
	 * 
	 * @param createUser
	 * @param createTime
	 * @param balanceParams
	 * @throws ServiceException
	 * @throws RpcException
	 */
	private void updateExpectCashList(String createUser, Date createTime, Map<String, Object> balanceParams)
			throws ServiceException, RpcException {
		List<ExpectCash> expectCashList = expectCashService.findByBiz(null, balanceParams);

		// 更新pos预收款 fas本库不做更新
		List<ExpectCashDto> expectCashDtos = new ArrayList<>();

		if (expectCashList != null && expectCashList.size() > 0) {
			for (ExpectCash expectCash : expectCashList) {
				ExpectCashDto expectCashDto = new ExpectCashDto();
				// expectCash.setBalanceNo(expectCashList.get(0).getBalanceNo());
				// expectCash.setBalanceStatus((byte) 2);
				expectCashDto.setShopNo(expectCash.getShopNo());
				expectCashDto.setId(expectCash.getId());
				expectCashDto.setBalanceNo("");
				expectCashDto.setBalanceStatus((int) 1);
				expectCashDto.setConfirmFlag(expectCash.getConfirmFlag());
				expectCashDto.setMonth(expectCash.getMonth());
				expectCashDto.setUpdateUser(createUser);
				expectCashDto.setUpdateTime(createTime);
				expectCashDtos.add(expectCashDto);
			}
			expectCashApi.updateExpectCashList(expectCashDtos);
		}
	}

	/**
	 * 依据结算单删除大类汇总， 日销售汇总， 结算差异， 扣率汇总， 销售回款， 外卡汇总，结算码
	 * 
	 * @param balanceNo
	 */
	private void removeBillBalance(String balanceNo) {
		billShopBalanceCateSumMapper.deleteBalanceNoForModel(balanceNo);

		billShopBalanceDaysaleSumMapper.deleteBalanceNoForModel(balanceNo);

		billShopBalanceDiffMapper.deleteBalanceNoForModel(balanceNo);

		billShopBalanceProSumMapper.deleteBalanceNoForModel(balanceNo);

		billShopBalanceBackMapper.deleteBalanceNoForModel(balanceNo);

		billShopBalanceWildsaleSumMapper.deleteBalanceNoForModel(balanceNo);

		billShopBalanceCodeSumMapper.deleteBalanceNoForModel(balanceNo);

		billShopBalanceBrandMapper.deleteBalanceNoForModel(balanceNo);
	}

	/*
	 * 保存 按结算月 到费用表
	 */
	public int saveOperateToBalanceDeduct(Map<String, Object> params, BillShopBalanceDeduct billShopBalanceDeduct)
			throws RpcException, ServiceException {
		List<RateExpenseOperate> rateExpenseOperateList = getRateExpenseOperate(null, params);
		int count = 0;
		if (!CollectionUtils.isEmpty(rateExpenseOperateList)) {
			for (RateExpenseOperate rateExpenseOperate : rateExpenseOperateList) {
				// BillShopBalanceDeduct billShopBalanceDeduct = new
				// BillShopBalanceDeduct();
				// if(rateExpenseOperate.getSettlementType()==2 &&
				// params.get("queryType").equals(2)){
				// continue;
				// }
				setBillInfo(billShopBalanceDeduct, params);
				billShopBalanceDeduct.setMallNo(rateExpenseOperate.getMallNo());
				billShopBalanceDeduct.setMallName(rateExpenseOperate.getMallName());
				billShopBalanceDeduct.setBrandNo(rateExpenseOperate.getBrandNo());
				billShopBalanceDeduct.setBrandName(rateExpenseOperate.getBrandName());
				billShopBalanceDeduct.setCostCateCode(rateExpenseOperate.getDebitedType());
				// billShopBalanceDeduct.setDeductionCode(rateExpenseOperate.getDebitedType());
				if (rateExpenseOperate != null && StringUtils.isNotEmpty(rateExpenseOperate.getDebitedType())) {
					params.put("code", rateExpenseOperate.getDebitedType());
					params.put("createUser", null);
					List<MallDeductionSet> mallDeductionSetList = mallDeductionSetService.findByBiz(null, params);
					// if(mallDeductionSetList != null &&
					// !"".equals(mallDeductionSetList) &&
					// mallDeductionSetList.get(0) != null){
					if (mallDeductionSetList != null && mallDeductionSetList.size() > 0) {
						billShopBalanceDeduct.setDeductionCode(mallDeductionSetList.get(0).getCode());
						billShopBalanceDeduct.setDeductionName(mallDeductionSetList.get(0).getName());
						billShopBalanceDeduct.setCostCateName(mallDeductionSetList.get(0).getCostName());
						billShopBalanceDeduct.setCostCateCode(mallDeductionSetList.get(0).getCostCode());
					}
				}
				BigDecimal deductAmount = getDeductAmount(rateExpenseOperate, billShopBalanceDeduct);
				if (deductAmount == null) {
					deductAmount = BigDecimal.ZERO;
				}
				// billShopBalanceDeduct.setCostType(costType);
				billShopBalanceDeduct.setCostDeductType(rateExpenseOperate.getDebitedMode());
				billShopBalanceDeduct.setCostPayType(rateExpenseOperate.getPaymentMode());
				billShopBalanceDeduct.setDeductAmount(deductAmount); // 扣费金额
																		// 要处理计算
				billShopBalanceDeduct.setActualAmount(billShopBalanceDeduct.getDeductAmount());
				billShopBalanceDeduct.setDeductDate(billShopBalanceDeduct.getDeductDate());

				if (deductAmount.compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				count += billShopBalanceDeductService.add(billShopBalanceDeduct);
			}
		}
		return count;
	}

	public BigDecimal getNoProSalesSumAmount(RateExpenseOperate rateExpenseOperate,
			BillShopBalanceDeduct billShopBalanceDeduct) throws ServiceException {
		BigDecimal noProSalesSumAmount = new BigDecimal("0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalanceDeduct.getShopNo());
		params.put("startDate", billShopBalanceDeduct.getBalanceStartDate());
		params.put("endDate", billShopBalanceDeduct.getBalanceEndDate());
		List<Integer> businessTypeList = new ArrayList<Integer>();
		businessTypeList.add(0);
		businessTypeList.add(1);
		businessTypeList.add(2);
		businessTypeList.add(6);
		params.put("businessTypeList", businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(30);
		statusList.add(41);
		params.put("statusList", statusList);
		noProSalesSumAmount = billShopBalanceService.getNoProSalesSumAmount(params);
		return noProSalesSumAmount;
	}

	public BigDecimal getSalesAmount(RateExpenseOperate rateExpenseOperate,
			BillShopBalanceDeduct billShopBalanceDeduct) throws ServiceException {
		BigDecimal salesAmount = new BigDecimal("0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalanceDeduct.getShopNo());
		params.put("startDate", billShopBalanceDeduct.getBalanceStartDate());
		params.put("endDate", billShopBalanceDeduct.getBalanceEndDate());
		List<Integer> businessTypeList = new ArrayList<Integer>();
		businessTypeList.add(0);
		businessTypeList.add(1);
		businessTypeList.add(2);
		businessTypeList.add(6);
		params.put("businessTypeList", businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(30);
		statusList.add(41);
		params.put("statusList", statusList);
		
		params.put("startAmount", rateExpenseOperate.getStartAmount());
		params.put("endAmount", rateExpenseOperate.getEndAmount());
		
		salesAmount = billShopBalanceService.getSalesAmount(params);
		return salesAmount;
	}
	
	/*
	 * 保存 按结算月 到费用表 获取计算扣费金额
	 */
	// 结算类型, 0-保底扣率,1-阶段扣率 2-纯租金 3-最大值(租金、扣率) 4-和值(租金+扣率)',    
	public BigDecimal getDeductAmount(RateExpenseOperate rateExpenseOperate, BillShopBalanceDeduct billShopBalanceDeduct)
			throws ServiceException {
		BigDecimal deductAmount = null;// new BigDecimal("0");
		BigDecimal rateAmount = null;
		BigDecimal salesRateAmount = null;
		BigDecimal rateNum = new BigDecimal("100");
		BigDecimal startAmount = rateExpenseOperate.getStartAmount();
		BigDecimal endAmount = rateExpenseOperate.getEndAmount();

		// if(rateExpenseOperate != null &&
		// StringUtils.isNotEmpty(rateExpenseOperate.getDebitedType())
		// || billShopBalanceDeduct != null &&
		// StringUtils.isNotEmpty(billShopBalanceDeduct.getDeductionCode())) {
		// if(rateExpenseOperate.getDebitedType().equals(billShopBalanceDeduct.getDeductionCode())){
		// return deductAmount;
		// }
		// }

		BigDecimal systemSalesAmount = billShopBalanceDeduct.getSystemSalesAmount();
		if (null == systemSalesAmount) {
			systemSalesAmount = BigDecimal.valueOf(0);
		}

		BigDecimal proSumSalesAmount = billShopBalanceDeduct.getProSumSalesAmount();
		if (null == proSumSalesAmount) {
			proSumSalesAmount = BigDecimal.valueOf(0);
		}

		BigDecimal rate = rateExpenseOperate.getRate();
		if (null == rate) {
			rate = BigDecimal.valueOf(0);
		}

		BigDecimal notProSumSalesAmount = systemSalesAmount.subtract(proSumSalesAmount);
		if (null == notProSumSalesAmount) {
			notProSumSalesAmount = BigDecimal.valueOf(0);
		}

		BigDecimal conBaseDeductAmount = billShopBalanceDeduct.getConBaseDeductAmount();
		if (null == conBaseDeductAmount) {
			conBaseDeductAmount = BigDecimal.valueOf(0);
		}
		if (0 == rateExpenseOperate.getSettlementType()) {
			// 促销计保底 0-计保底 1-不计保底(用于保底扣率类型)
			// 促销计入保底
			// 计算规则：总销售额>=月保底，保底扣费=0
			// 总销售额<月保底，保底扣费=（月保底-总销售额）*保底扣率
			if (0 == rateExpenseOperate.getMinimumFlag()) {
				if (startAmount != null && systemSalesAmount.subtract(startAmount).doubleValue() >= 0) {
					deductAmount = BigDecimal.valueOf(0);
				} else {
					deductAmount = (startAmount.subtract(systemSalesAmount)).multiply(rate.divide(rateNum));
				}
			}
			// 促销不计入保底
			// 合同基础扣销售额>=月保底，保底扣费=0
			// 合同基础扣销售额<月保底，保底扣费=（月保底-合同基础扣销售额）*保底扣率
			else if (1 == rateExpenseOperate.getMinimumFlag()) {
				if (startAmount != null && conBaseDeductAmount.subtract(startAmount).doubleValue() >= 0) {
					deductAmount = BigDecimal.valueOf(0);
				} else {
					deductAmount = (startAmount.subtract(conBaseDeductAmount)).multiply(rate.divide(rateNum));
				}
			}

			// if(startAmount !=null &&
			// startAmount.subtract(notProSumSalesAmount).doubleValue() >= 0){
			// deductAmount=(startAmount.multiply(rate.divide(rateNum)));
			// }else
			// deductAmount=(notProSumSalesAmount.multiply(rate.divide(rateNum)));
		}
		// 结算类型,1-阶段保底+扣率
		else if (1 == rateExpenseOperate.getSettlementType()) {
			// 超额统一扣率 0-统一 1-不统一
			// 销售额 - 促销期间的销售
			BigDecimal proSalesSumAmount = systemSalesAmount.subtract(conBaseDeductAmount);
			if (null == proSalesSumAmount) {
				proSalesSumAmount = BigDecimal.valueOf(0);
			}
			// 销售额 - 促销期间的销售=没有促销
			// BigDecimal noProSalesSumAmount
			// =systemSalesAmount.subtract(proSalesSumAmount);

			// 阶段扣率类型的保底费用算法为 （保底金额 — 非促销实际销售）*扣率
			// 目前
			// 按销售明细中没有促销活动编号的当作【非促销实际销售】，不合理，将会调整为按销售明细中扣率代码以A开头的或者扣率代码为空的作为【非促销实际销售】。
			// 举例：扣率代码为A、A1、A2、空，均为【非促销实际销售】
			// 调整为从销售明细取扣率
			BigDecimal noProSalesSumAmount = getNoProSalesSumAmount(rateExpenseOperate, billShopBalanceDeduct);
			if (noProSalesSumAmount == null) {
				noProSalesSumAmount = BigDecimal.ZERO;
			}
			if (0 == rateExpenseOperate.getUnityRateFlag()) {
				// if(null == startAmount){//只设置最大值 <=最大值
				// if(systemSalesAmount.subtract(endAmount).doubleValue() <= 0
				// ){
				// deductAmount=(systemSalesAmount.multiply(rate.divide(rateNum)));
				// }
				// }
				// if(null == endAmount){//只设置最小值 无穷大 >=最小值
				// if(systemSalesAmount.subtract(startAmount).doubleValue() >= 0
				// ){
				// deductAmount=(systemSalesAmount.multiply(rate.divide(rateNum)));
				// }
				// }

				if (startAmount != null && endAmount != null
						&& noProSalesSumAmount.subtract(startAmount).doubleValue() > 0
						&& noProSalesSumAmount.subtract(endAmount).doubleValue() <= 0) {
					deductAmount = noProSalesSumAmount.multiply(rate.divide(rateNum));
				}
				/**
				 * // 如果是超额扣率，判断销售额是否大于当前设置多条中的最大终止额度，如果大于就是销售额*扣率，如果小于在按阶段区间扣处理
				 * Map<String, Object> params = new HashMap<String, Object>();
				 * params.put("shopNo", rateExpenseOperate.getShopNo());
				 * params.put("settlementPeriod",
				 * rateExpenseOperate.getSettlementPeriod());
				 * params.put("getEndAmount", 1); List<RateExpenseOperate>
				 * rateExpenseOperateList =
				 * rateExpenseOperatService.findByBiz(rateExpenseOperate,
				 * params); if
				 * (!CollectionUtils.isEmpty(rateExpenseOperateList)) {
				 * for(RateExpenseOperate rateExpenseOperate1 :
				 * rateExpenseOperateList){ startAmount =
				 * rateExpenseOperate1.getStartAmount(); maxEndAmount =
				 * rateExpenseOperate1.getEndAmount(); // --maxEndAmount =
				 * rateExpenseOperateList.get(0).getEndAmount(); } } //
				 * if(rateExpenseOperate != null &&
				 * !"".equals(rateExpenseOperate)){ // // }
				 * 
				 * // 销售额大于最大终止额度 是统一扣率 销售额 * 扣率
				 * if(systemSalesAmount.subtract(maxEndAmount).doubleValue() > 0
				 * ){ deductAmount =
				 * systemSalesAmount.multiply(rate.divide(rateNum)); }else
				 * if(systemSalesAmount.subtract(startAmount).doubleValue() > 0
				 * ){ deductAmount =
				 * systemSalesAmount.multiply(rate.divide(rateNum)); } //
				 * 小于的话，就要按照结算区间来处理 else
				 * if(systemSalesAmount.subtract(startAmount).doubleValue() > 0
				 * && systemSalesAmount.subtract(maxEndAmount).doubleValue() <=
				 * 0){ deductAmount=(systemSalesAmount.multiply(rate.divide(
				 * rateNum))); } else
				 * deductAmount=(systemSalesAmount.multiply(rate
				 * .divide(rateNum)));
				 **/
			} else if (1 == rateExpenseOperate.getUnityRateFlag()) { // 1-不是统一扣率
																		// 取到区间
																		// 销售额*扣率
																		// +
																		// 销售额-终止额度*扣率
				// 根据销售额去查询该销售额位于那个区间段
				// if(systemSalesAmount.subtract(startAmount).doubleValue() > 0
				// &&
				// systemSalesAmount.subtract(endAmount).doubleValue() <= 0){
				// deductAmount=(systemSalesAmount.multiply(rate.divide(rateNum)).add(
				// (systemSalesAmount.subtract(endAmount)).multiply(rate.divide(rateNum))));
				// }
				// if(null == startAmount){//只设置最大值 <=最大值
				// if(systemSalesAmount.subtract(endAmount).doubleValue() <= 0
				// ){
				// deductAmount=(endAmount.multiply(rate.divide(rateNum)));
				// }
				// }
				// if(null == endAmount){//只设置最小值 无穷大 >=最小值
				// if(systemSalesAmount.subtract(startAmount).doubleValue() >= 0
				// ){
				// deductAmount=((systemSalesAmount.subtract(startAmount)).multiply(rate.divide(rateNum)));
				// }
				// }
				// if(startAmount !=null && endAmount !=null &&
				// systemSalesAmount.subtract(startAmount).doubleValue() >= 0 &&
				// systemSalesAmount.subtract(endAmount).doubleValue() <= 0){
				// deductAmount=(systemSalesAmount.multiply(rate.divide(rateNum)).add(
				// (systemSalesAmount.subtract(endAmount)).multiply(rate.divide(rateNum))));
				// }
				if (startAmount != null && endAmount != null
						&& noProSalesSumAmount.subtract(endAmount).doubleValue() >= 0) {
					// 终止额度*扣率 + 销售额-终止额度*扣率
					// deductAmount=endAmount.multiply(rate.divide(rateNum)).add((systemSalesAmount.subtract(endAmount)).multiply(rate.divide(rateNum)));
					deductAmount = (endAmount.subtract(startAmount)).multiply(rate.divide(rateNum));

				} else if (startAmount != null && endAmount != null
						&& noProSalesSumAmount.subtract(startAmount).doubleValue() >= 0
						&& noProSalesSumAmount.subtract(endAmount).doubleValue() <= 0) {
					// deductAmount=systemSalesAmount.multiply(rate.divide(rateNum));
					deductAmount = (noProSalesSumAmount.subtract(startAmount)).multiply(rate.divide(rateNum));
				}
				// deductAmount=minAmount.add(maxAmount);
				// else if(systemSalesAmount.subtract(startAmount).doubleValue()
				// > 0 &&
				// systemSalesAmount.subtract(endAmount).doubleValue() <= 0){
				// deductAmount=(endAmount.subtract(startAmount)).multiply(rate.divide(rateNum));
				// }
				// else if(systemSalesAmount.subtract(endAmount).doubleValue()
				// <= 0){
				// deductAmount=(systemSalesAmount.subtract(startAmount)).multiply(rate.divide(rateNum));
				// }
			}
		} else if (2 == rateExpenseOperate.getSettlementType()) { // 纯租金
																	// 在生成费用的时候已经产生了
																	// ，在此就不在处理
			// 先检查是否在费用表中已经存在，存在就返回不处理，没有就保存
			Map<String, Object> brparams = new HashMap<String, Object>();
			brparams.put("shopNo", rateExpenseOperate.getShopNo());
			brparams.put("deductionCode", "1001");// rateExpenseOperate.getDebitedType()
													// costCateCode
			brparams.put("month", billShopBalanceDeduct.getMonth());
			List<BillShopBalanceDeduct> billShopBalanceDeductList = billShopBalanceDeductService.findByBiz(null,
					brparams);
			if (!CollectionUtils.isEmpty(billShopBalanceDeductList)) {
				return deductAmount;
			} else {
				if (null != rateExpenseOperate.getRental()) {
					deductAmount = rateExpenseOperate.getRental();
				}
			}
		} else if (3 == rateExpenseOperate.getSettlementType()) {
			if (null != rateExpenseOperate.getRental()) {
				rateAmount = rateExpenseOperate.getRental();
			}
			if (null != billShopBalanceDeduct.getSystemSalesAmount() && null != rateExpenseOperate.getRate()) {
				salesRateAmount = billShopBalanceDeduct.getSystemSalesAmount().multiply(
						(rateExpenseOperate.getRate().divide(rateNum)));
			}
			// 根据比较两个值大小来进行处理
			if (rateAmount.subtract(salesRateAmount).intValue() > 0) {
				deductAmount = rateAmount;
			} else {
				deductAmount = salesRateAmount;
			}
		} else if (4 == rateExpenseOperate.getSettlementType()) {
			if (null != rateExpenseOperate.getRental() && null != billShopBalanceDeduct.getSystemSalesAmount()
					&& null != rateExpenseOperate.getRate()) {
				deductAmount = rateExpenseOperate.getRental().add(
						billShopBalanceDeduct.getSystemSalesAmount().multiply(
								(rateExpenseOperate.getRate().divide(rateNum))));
			}
		}
		//5  基础扣率
		else if (5 == rateExpenseOperate.getSettlementType()) {					 
				if (startAmount != null && endAmount != null){
					BigDecimal salesAmount = getSalesAmount(rateExpenseOperate, billShopBalanceDeduct);
					if (salesAmount == null) {
						salesAmount = BigDecimal.ZERO;
					}	
					deductAmount = salesAmount.multiply(rate.divide(rateNum));
				}		 
			}
		return deductAmount;
	}

	/**
	 * 
	 * @param params
	 * @param billShopBalanceDeduct
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 */
	/*
	 * 保存 按起止时期 到费用表
	 */
	public int saveSporadicToBalanceDeduct(Map<String, Object> params, BillShopBalanceDeduct billShopBalanceDeduct)
			throws RpcException, ServiceException {
		List<RateExpenseSporadic> rateExpenseSporadicList = rateExpenseSporadicService.findByBiz(null, params);
		int count = 0;
		if (!CollectionUtils.isEmpty(rateExpenseSporadicList)) {
			for (RateExpenseSporadic rateExpenseSporadic : rateExpenseSporadicList) {
				// if(rateExpenseSporadic.getDebitedRule()!=2 &&
				// params.get("queryType").equals(2)){
				// continue;
				// }
				setBillInfo(billShopBalanceDeduct, params);
				billShopBalanceDeduct.setCostCateCode(rateExpenseSporadic.getDebitedType());
				// billShopBalanceDeduct.setDeductionCode(rateExpenseSporadic.getDebitedType());
				// billShopBalanceDeduct.setCostType(costType);
				billShopBalanceDeduct.setCostDeductType(rateExpenseSporadic.getDebitedMode());
				billShopBalanceDeduct.setCostPayType(rateExpenseSporadic.getPaymentMode());
				billShopBalanceDeduct.setBrandNo(rateExpenseSporadic.getBrandNo());
				billShopBalanceDeduct.setBrandName(rateExpenseSporadic.getBrandName());
				if (rateExpenseSporadic != null && StringUtils.isNotEmpty(rateExpenseSporadic.getDebitedType())) {
					params.put("code", rateExpenseSporadic.getDebitedType());
					params.put("createUser", null);
					List<MallDeductionSet> mallDeductionSetList = mallDeductionSetService.findByBiz(null, params);
					if (mallDeductionSetList != null && mallDeductionSetList.size() > 0) {
						billShopBalanceDeduct.setDeductionCode(mallDeductionSetList.get(0).getCode());
						billShopBalanceDeduct.setDeductionName(mallDeductionSetList.get(0).getName());
						billShopBalanceDeduct.setCostCateName(mallDeductionSetList.get(0).getCostName());
						billShopBalanceDeduct.setCostCateCode(mallDeductionSetList.get(0).getCostCode());
					}
				}
				BigDecimal deductAmount = getDeductAmount(params, rateExpenseSporadic, billShopBalanceDeduct);
				if (deductAmount == null) {
					deductAmount = BigDecimal.ZERO;
				}
				billShopBalanceDeduct.setDeductAmount(deductAmount); // 扣费金额
																		// 要处理计算
				billShopBalanceDeduct.setActualAmount(billShopBalanceDeduct.getDeductAmount());
				billShopBalanceDeduct.setDeductDate(billShopBalanceDeduct.getDeductDate());
				if (deductAmount.compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				count += billShopBalanceDeductService.add(billShopBalanceDeduct);
			}
		}
		return count;
	}

	/**
	 * 保存 按起止时期 到费用表 获取计算扣费金额
	 * 
	 * @param rateExpenseOperate
	 * @return
	 * @throws ServiceException
	 */
	public BigDecimal getDeductAmount(Map<String, Object> params, RateExpenseSporadic rateExpenseSporadic,
			BillShopBalanceDeduct billShopBalanceDeduct) throws ServiceException {
		BigDecimal deductAmount = new BigDecimal("0");
		BigDecimal rateNum = new BigDecimal("100");

		// if(rateExpenseSporadic != null &&
		// StringUtils.isNotEmpty(rateExpenseSporadic.getDebitedType())
		// || billShopBalanceDeduct != null &&
		// StringUtils.isNotEmpty(billShopBalanceDeduct.getDeductionCode())) {
		// if(rateExpenseSporadic.getDebitedType().equals(billShopBalanceDeduct.getDeductionCode())){
		// return deductAmount;
		// }
		// }

		BigDecimal balanceRate = rateExpenseSporadic.getBalanceRate();
		if (null == balanceRate) {
			balanceRate = BigDecimal.valueOf(0);
		}

		BigDecimal systemSalesAmount = billShopBalanceDeduct.getSystemSalesAmount();
		if (null == systemSalesAmount) {
			systemSalesAmount = BigDecimal.valueOf(0);
		}

		BigDecimal rate = rateExpenseSporadic.getRate();
		if (null == rate) {
			rate = BigDecimal.valueOf(0);
		}

		// debited_rule 扣费规则 1-月定额 2-费率
		if (1 == rateExpenseSporadic.getDebitedRule()) { // 月定额在费用生成的时候已经处理，在此不在处理
			// 先检查是否在费用表中已经存在，存在就返回不处理，没有就保存
			Map<String, Object> brparams = new HashMap<String, Object>();
			brparams.put("shopNo", rateExpenseSporadic.getShopNo());
			brparams.put("deductionCode", rateExpenseSporadic.getDebitedType());// costCateCode
			brparams.put("startDate", params.get("startDate"));
			brparams.put("endDate", params.get("endDate"));
			brparams.put("rateId", rateExpenseSporadic.getId());
			List<BillShopBalanceDeduct> billShopBalanceDeductList = billShopBalanceDeductService.findByBiz(null,
					brparams);
			if (!CollectionUtils.isEmpty(billShopBalanceDeductList)) {
				return deductAmount;
			} else {
				if (null != rateExpenseSporadic.getAmount()) {
					deductAmount = rateExpenseSporadic.getAmount();
				}
			}
		}
		// 处理费率
		// `debited_rule` '扣费规则 1-月定额 2-费率',
		else if (2 == rateExpenseSporadic.getDebitedRule()) { // 支付方式 其他
			// 按照支付方式
			// 其他 销售额 促销费

			// 按支付汇总
			// params.put("companyNo", params.get("companyNo"));
			// params.put("shopNo", params.get("shopNo"));
			// params.put("startDate", params.get("startDate"));
			// params.put("endDate", params.get("endDate"));
			// params.put("payCode",rateExpenseSporadic.getBasePayCode());

			// `amount` '金额',
			// `base_pay_code` '支付方式',
			// `base_other` '其他',
			// `rate` '费率',
			// `balance_rate` '补差费率',
			// `rate_amount` '费率金额',
			// `balance_rate_amount` '补差金额',
			if (StringUtils.isNotEmpty(rateExpenseSporadic.getBasePayCode())) { // 支付方式
				BillShopBalance billShopBalance = getBillShopBalance(params);
				billShopBalance.setPayCode(rateExpenseSporadic.getBasePayCode());// 按照支付方式汇总
				// 时间交叉处理
				Date balanceStartDate = billShopBalance.getBalanceStartDate();
				Date balanceEndDate = billShopBalance.getBalanceEndDate();
				Date startDate = rateExpenseSporadic.getStartDate();
				Date endDate = rateExpenseSporadic.getEndDate();
				Date bStartDate = compareStartDate(balanceStartDate, startDate);
				Date bEndDate = compareEndDate(balanceEndDate, endDate);
				billShopBalance.setBalanceStartDate(bStartDate);
				billShopBalance.setBalanceEndDate(bEndDate);
				BillShopBalanceDaysaleSum billShopBalanceDaysaleSum = getSystemSalesAmount(billShopBalance);
				BigDecimal amount = new BigDecimal("0");
				BigDecimal saleAmount = new BigDecimal("0");

				if (billShopBalanceDaysaleSum != null) {
					amount = billShopBalanceDaysaleSum.getAmount();
					saleAmount = billShopBalanceDaysaleSum.getSaleAmount();
				}
				if (null == amount) {
					amount = BigDecimal.valueOf(0);
				}

				// 银行卡 * 扣率 如果是商场卡？其他？
				deductAmount = (amount.multiply(rate.divide(rateNum))).add((saleAmount.subtract(amount))
						.multiply(balanceRate.divide(rateNum)));
				billShopBalanceDeduct.setRateAmount(amount.multiply(rate.divide(rateNum)));
				// 支付方式销售订单总金额 - 支付方式金额*扣率
				billShopBalanceDeduct.setBalanceRateAmount((saleAmount.subtract(amount)).multiply(balanceRate
						.divide(rateNum)));
			}
			if (rateExpenseSporadic.getBaseOther() != null && !"".equals(rateExpenseSporadic.getBaseOther())) {
				BillShopBalanceWildsaleSum billShopBalanceWildsaleSum = new BillShopBalanceWildsaleSum();
				billShopBalanceWildsaleSum.setShopNo(String.valueOf(params.get("shopNo")));
				billShopBalanceWildsaleSum.setBalanceStartDate((Date) params.get("startDate"));
				billShopBalanceWildsaleSum.setBalanceEndDate((Date) params.get("endDate"));
				billShopBalanceWildsaleSum.setBalanceNo(String.valueOf(params.get("balanceNo")));

				/** 把费用计算处理 测试用 **/
				// deductAmount=systemSalesAmount.multiply(rate.divide(rateNum));
				// billShopBalanceDeduct.setRateAmount(systemSalesAmount.multiply(rate.divide(rateNum)));
				// billShopBalanceDeduct.setBalanceRateAmount(systemSalesAmount.multiply(rate.divide(rateNum)));
				// VIP 其他要处理

				billShopBalanceWildsaleSum = billShopBalanceWildsaleSumService.getSumAmount(billShopBalanceWildsaleSum);
				if (billShopBalanceWildsaleSum != null) {
					if (rateExpenseSporadic.getBaseOther().equals("0")) {// 商场VIP销售达成金额
						if (null != billShopBalanceWildsaleSum.getSettleAmount()
								&& null != rateExpenseSporadic.getRate()) {
							deductAmount = billShopBalanceWildsaleSum.getSettleAmount().multiply(
									rateExpenseSporadic.getRate().divide(rateNum));
							billShopBalanceDeduct.setRateAmount(billShopBalanceWildsaleSum.getSettleAmount().multiply(
									rateExpenseSporadic.getRate().divide(rateNum)));
						}
					} else if (rateExpenseSporadic.getBaseOther().equals("1")) {// 商场VIP折让金额
						if (null != billShopBalanceWildsaleSum.getDiscAmount() && null != rateExpenseSporadic.getRate()) {
							deductAmount = billShopBalanceWildsaleSum.getDiscAmount().multiply(
									rateExpenseSporadic.getRate().divide(rateNum));
							billShopBalanceDeduct.setRateAmount(billShopBalanceWildsaleSum.getDiscAmount().multiply(
									rateExpenseSporadic.getRate().divide(rateNum)));
						}
					}
				}
				if (rateExpenseSporadic.getBaseOther().equals("2")) {// 销售金额
					deductAmount = systemSalesAmount.multiply(rate.divide(rateNum));
					billShopBalanceDeduct.setRateAmount(systemSalesAmount.multiply(rate.divide(rateNum)));
					billShopBalanceDeduct.setBalanceRateAmount(systemSalesAmount.multiply(rate.divide(rateNum)));
				}
			}
		}
		return deductAmount;
	}

	// return 前者大于后者返回true 反之false
	public Date compareStartDate(Date balanceDate, Date setDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(balanceDate);
		c2.setTime(setDate);
		int result = c1.compareTo(c2);
		if (result >= 0)
			return balanceDate;
		else
			return setDate;
	}

	public Date compareEndDate(Date balanceDate, Date setDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(balanceDate);
		c2.setTime(setDate);
		int result = c1.compareTo(c2);
		if (result >= 0)
			return setDate;
		else
			return balanceDate;
	}

	public List<RateExpenseOperate> getRateExpenseOperate(RateExpenseOperate rateExpenseOperate,
			Map<String, Object> params) throws ServiceException {
		try {
			return rateExpenseOperateService.findByBiz(null, params);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	/**
	 * 向BillShopBalanceDeduct对象赋值
	 * 
	 * @param params
	 * @throws ServiceException
	 */
	private void setBillInfo(BillShopBalanceDeduct billShopBalanceDeduct, Map<String, Object> params)
			throws ServiceException {
		billShopBalanceDeduct.setCompanyNo(String.valueOf(params.get("companyNo")));
		billShopBalanceDeduct.setCompanyName(String.valueOf(params.get("companyName")));
		billShopBalanceDeduct.setShopNo(String.valueOf(params.get("shopNo")));
		billShopBalanceDeduct.setShortName(String.valueOf(params.get("shopName")));
		billShopBalanceDeduct.setMonth(String.valueOf(params.get("month")));
		billShopBalanceDeduct.setBalanceNo(String.valueOf(params.get("balanceNo")));
		billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
		// billShopBalanceDeduct.setId(UUIDGenerator.getUUID());
		billShopBalanceDeduct.setBillDate(new Date());
		billShopBalanceDeduct.setStatus(1);
		Shop shop = this.getShopInfo(params);
		if (shop != null) {
			billShopBalanceDeduct.setOrganNo(shop.getOrganNo());
			billShopBalanceDeduct.setOrganName(shop.getOrganName());
			billShopBalanceDeduct.setBsgroupsNo(shop.getBsgroupsNo());
			billShopBalanceDeduct.setBsgroupsName(shop.getBsgroupsName());
			billShopBalanceDeduct.setMallNo(shop.getMallNo());
			billShopBalanceDeduct.setMallName(shop.getMallName());
		}
	}

	/**
	 * 得到给结算月的上月month
	 * 
	 * @param params
	 * @return
	 * @throws ParseException
	 */
	public String getBefMonth(Map<String, Object> params) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String str = String.valueOf(params.get("month"));
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		// rightNow.add(Calendar.YEAR,-1);//日期减1年
		rightNow.add(Calendar.MONTH, -1);// 日期加3个月
		// rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	public String getBefMonBalanceDate(Map<String, Object> params) throws ServiceException {
		Map<String, Object> befMonparams = new HashMap<String, Object>();
		befMonparams.put("companyNo", params.get("companyNo"));
		befMonparams.put("shopNo", params.get("shopNo"));
		// befMonparams.put("balanceFlag",2);//已结算
		befMonparams.put("getBefMonth", "getBefMonth");
		try {
			List<ShopBalanceDate> shopBalanceDateList = this.getBalanceDate(null, befMonparams);
			if (CollectionUtils.isEmpty(shopBalanceDateList)) {
				return null;
			}
			return shopBalanceDateList.get(0).getMonth();
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 保存每日销售按支付方式汇总 bill_shop_balance_daysale_sum
	 * 
	 * @param orderParameterDtoList
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 * @throws ManagerException
	 */

	public int saveBalanceDaysaleSum(POSOcOrderParameterDto orderParameterDtoList, Map<String, Object> params)
			throws ServiceException, ManagerException {
		POSOcSimplePageDto simple = new POSOcSimplePageDto();
		simple.setPageNo(1);
		simple.setPageSize(Integer.MAX_VALUE);
		POSOcPagingDto<POSOcGroupOrderPaywayDto> ocGroupOrderPaywayDtoList = orderMainManager
				.findList4OcGroupOrderPayway(simple, null, orderParameterDtoList);// .findList4OcGroupOrderPaywayDto(orderParameterDtoList);
		int count = 0;
		if (ocGroupOrderPaywayDtoList != null && ocGroupOrderPaywayDtoList.getResult() != null
				&& ocGroupOrderPaywayDtoList.getResult().size() > 0) {
			for (POSOcGroupOrderPaywayDto ocGroupOrderPaywayDto : ocGroupOrderPaywayDtoList.getResult()) {
				BillShopBalanceDaysaleSum billShopBalanceDaysaleSum = new BillShopBalanceDaysaleSum();
				billShopBalanceDaysaleSum.setShopNo(ocGroupOrderPaywayDto.getShopNo());
				billShopBalanceDaysaleSum.setShortName(ocGroupOrderPaywayDto.getShopName());
				billShopBalanceDaysaleSum.setCompanyNo((String) params.get("companyNo"));
				billShopBalanceDaysaleSum.setOutDate(ocGroupOrderPaywayDto.getOutDate());
				billShopBalanceDaysaleSum.setPayCode(ocGroupOrderPaywayDto.getPayCode());
				billShopBalanceDaysaleSum.setPayName(ocGroupOrderPaywayDto.getPayName());
				billShopBalanceDaysaleSum.setPaywayNum(ocGroupOrderPaywayDto.getPaywayNum());
				billShopBalanceDaysaleSum.setSaleAmount(ocGroupOrderPaywayDto.getAllAmount());
				billShopBalanceDaysaleSum.setAmount(ocGroupOrderPaywayDto.getGroupAmount());
				billShopBalanceDaysaleSum.setBalanceNo((String) params.get("balanceNo"));
				billShopBalanceDaysaleSum.setId(UUIDGenerator.getUUID());
				// billNo =
				// codingRuleService.getSerialNo(BillBacksectionSplit.class.getSimpleName());
				// billShopBalanceDaysaleSum.setBillNo(ocGroupOrderPaywayDto.getOrderNo());
				// Map<String, Object> params = new HashMap<String, Object>();
				params.put("shopNo", ocGroupOrderPaywayDto.getShopNo());
				Shop shop = this.getShopInfo(params);
				billShopBalanceDaysaleSum.setMallNo(shop.getMallNo());
				billShopBalanceDaysaleSum.setMallName(shop.getMallName());
				count += billShopBalanceDaysaleSumService.add(billShopBalanceDaysaleSum);
			}
		}
		return count;
	}

	/**
	 * 保存结算期大类汇总数据 bill_shop_balance_cate_sum
	 * 
	 * @param orderParameterDtoList
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 *             findList4OcOrderGroupRootCategory
	 * @throws ManagerException
	 */
	public int saveBalanceCateSum(POSOcOrderParameterDto OcOrderParameterDto, Map<String, Object> params)
			throws ServiceException, ManagerException {
		List<POSOcGroupRootCategoryDto> ocGroupRootCategoryDtoList = orderMainManager
				.findList4OcOrderGroupRootCategory(OcOrderParameterDto);// .findList4OcGroupOrderPaywayDto(orderParameterDtoList);
		int count = 0;
		if (ocGroupRootCategoryDtoList != null && ocGroupRootCategoryDtoList.size() > 0) {
			for (POSOcGroupRootCategoryDto ocGroupRootCategoryDto : ocGroupRootCategoryDtoList) {
				BillShopBalanceCateSum billShopBalanceCateSum = new BillShopBalanceCateSum();
				billShopBalanceCateSum.setId(UUIDGenerator.getUUID());
				billShopBalanceCateSum.setShopNo(ocGroupRootCategoryDto.getShopNo());
				billShopBalanceCateSum.setShortName(ocGroupRootCategoryDto.getShopName());
				// billShopBalanceCateSum.setCompanyNo(ocGroupRootCategoryDto.getCompanyNo());
				billShopBalanceCateSum.setMonth((String) params.get("month"));
				billShopBalanceCateSum.setBalanceStartDate(OcOrderParameterDto.getStartOutDate());
				billShopBalanceCateSum.setBalanceEndDate(OcOrderParameterDto.getEndOutDate());
				billShopBalanceCateSum.setBrandNo(ocGroupRootCategoryDto.getBrandNo());
				billShopBalanceCateSum.setBrandName(ocGroupRootCategoryDto.getBrandName());
				billShopBalanceCateSum.setCategoryNo(ocGroupRootCategoryDto.getRootCategoryNo());
				billShopBalanceCateSum.setCategoryName(ocGroupRootCategoryDto.getRootCategoryName());
				billShopBalanceCateSum.setSaleQty(ocGroupRootCategoryDto.getGroupQty());
				billShopBalanceCateSum.setSaleAmount(ocGroupRootCategoryDto.getGroupAmount());
				billShopBalanceCateSum.setDeductAmount(ocGroupRootCategoryDto.getDeductAmount());
				billShopBalanceCateSum.setBalanceNo((String) params.get("balanceNo"));
				// billShopBalanceCateSum.setId(UUIDGenerator.getUUID());
				// billShopBalanceCateSum.setBillNo(ocGroupRootCategoryDto.get);
				// Map<String, Object> params = new HashMap<String, Object>();
				params.put("shopNo", ocGroupRootCategoryDto.getShopNo());
				Shop shop = this.getShopInfo(params);
				billShopBalanceCateSum.setCompanyNo(shop.getCompanyNo());
				billShopBalanceCateSum.setCompanyName(shop.getCompanyName());
				billShopBalanceCateSum.setMallNo(shop.getMallNo());
				billShopBalanceCateSum.setMallName(shop.getMallName());
				count += billShopBalanceCateSumService.add(billShopBalanceCateSum);
			}
		}
		return count;
	}

	/**
	 * 保存按商场结算码汇总数据 bill_shop_balance_code_sum
	 * 
	 * @param orderParameterDtoList
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 * @throws ManagerException
	 */
	public int saveBalanceCodeSum(POSOcOrderParameterDto OcOrderParameterDto, Map<String, Object> params)
			throws ServiceException, ManagerException {
		List<BillShopBalanceCodeSum> billShopBalanceCodeSumList = orderMainManager
				.findListOrderGroupCodeSum(OcOrderParameterDto);// .findList4OcGroupOrderPaywayDto(orderParameterDtoList);
		int count = 0;
		if (billShopBalanceCodeSumList != null && billShopBalanceCodeSumList.size() > 0) {
			for (BillShopBalanceCodeSum billShopBalanceCodeSumDto : billShopBalanceCodeSumList) {
				BillShopBalanceCodeSum billShopBalanceCodeSum = new BillShopBalanceCodeSum();
				billShopBalanceCodeSum.setShopNo(billShopBalanceCodeSumDto.getShopNo());
				billShopBalanceCodeSum.setShortName(billShopBalanceCodeSumDto.getShortName());
				billShopBalanceCodeSum.setMonth(String.valueOf(params.get("month")));
				billShopBalanceCodeSum.setBalanceStartDate(OcOrderParameterDto.getStartOutDate());
				billShopBalanceCodeSum.setBalanceEndDate(OcOrderParameterDto.getEndOutDate());
				billShopBalanceCodeSum.setSaleAmount(billShopBalanceCodeSumDto.getSaleAmount());
				billShopBalanceCodeSum.setDeductAmount(billShopBalanceCodeSumDto.getDeductAmount());
				billShopBalanceCodeSum.setDiscount(billShopBalanceCodeSumDto.getDiscount());
				billShopBalanceCodeSum.setDiscountName(billShopBalanceCodeSumDto.getDiscountName());
				billShopBalanceCodeSum.setBillingCode(billShopBalanceCodeSumDto.getBillingCode());
				billShopBalanceCodeSum.setBalanceNo((String) params.get("balanceNo"));
				params.put("shopNo", billShopBalanceCodeSumDto.getShopNo());
				Shop shop = this.getShopInfo(params);
				billShopBalanceCodeSum.setCompanyNo(shop.getCompanyNo());
				billShopBalanceCodeSum.setCompanyName(shop.getCompanyName());
				billShopBalanceCodeSum.setMallNo(shop.getMallNo());
				billShopBalanceCodeSum.setMallName(shop.getMallName());
				billShopBalanceCodeSum.setId(UUIDGenerator.getUUID());
				count += billShopBalanceCodeSumService.add(billShopBalanceCodeSum);
			}
		}
		return count;
	}

	// 处理数据 从结算差异数据 按品牌汇总 综合店结算处理
	public int updateBalanceBrandData(BillShopBalance billShopBalance) throws Exception {
		Map<String, Object> diffParams = new HashMap<String, Object>();
		diffParams.put("balanceNo", billShopBalance.getBalanceNo());
		diffParams.put("shopNo", billShopBalance.getShopNo());
		diffParams.put("month", billShopBalance.getMonth());
		diffParams.put("groupBrand", "groupBrand");
		List<BillShopBalanceDiff> billShopBalanceDiffList = billShopBalanceDiffService.getSumAmount(diffParams);// .findByBiz(null,
																												// diffParams);
		int count = 0;
		if (billShopBalanceDiffList != null && billShopBalanceDiffList.size() > 0) {
			for (BillShopBalanceDiff billShopBalanceDiff : billShopBalanceDiffList) {
				diffParams.put("brandNo", billShopBalanceDiff.getBrandNo());
				List<BillShopBalanceBrand> billShopBalanceBrandList = billShopBalanceBrandService.findByBiz(null,
						diffParams);
				for (BillShopBalanceBrand billShopBalanceBrand : billShopBalanceBrandList) {
					Map<String, Object> systemParamSetParams = new HashMap<String, Object>();
					systemParamSetParams.put("businessOrganNo", billShopBalance.getCompanyNo());
					systemParamSetParams.put("paramCode", "MS_ExpectCash_Pro");
					systemParamSetParams.put("status", 1);

					// String value =
					// getSystemParamSetValue(systemParamSetParams);
					// if("0".equals(value)){//参与结算 处理预收款的业务流程
					// processExpectCashBrand(billShopBalance,billShopBalanceBrand);
					// } else if("1".equals(value)){
					// }else //默认参与计算
					// processExpectCashBrand(billShopBalance,billShopBalanceBrand);
					// BigDecimal prepaymentAmountBrand =
					// billShopBalance.getPrepaymentAmountBrand();
					// if(prepaymentAmountBrand == null) {
					// prepaymentAmountBrand = BigDecimal.ZERO;
					// }
					// BigDecimal usedPrepaymentAmountBrand =
					// billShopBalance.getUsedPrepaymentAmountBrand();
					// if(usedPrepaymentAmountBrand == null) {
					// usedPrepaymentAmountBrand = BigDecimal.ZERO;
					// }
					// billShopBalanceBrand.setPrepaymentAmount(prepaymentAmountBrand);
					// billShopBalanceBrand.setUsedPrepaymentAmount(usedPrepaymentAmountBrand);

					// billShopBalanceBrand.setId(UUIDGenerator.getUUID());
					// billShopBalanceBrand.setShopNo(billShopBalance.getShopNo());
					// billShopBalanceBrand.setShortName(billShopBalance.getShortName());
					// billShopBalanceBrand.setCompanyNo(billShopBalance.getCompanyNo());
					// billShopBalanceBrand.setCompanyName(billShopBalance.getCompanyName());
					// billShopBalanceBrand.setMonth(billShopBalance.getMonth());
					// billShopBalanceBrand.setBalanceStartDate(billShopBalance.getBalanceStartDate());
					// billShopBalanceBrand.setBalanceEndDate(billShopBalance.getBalanceEndDate());
					Map<String, Object> deductParams = new HashMap<String, Object>();
					deductParams.put("balanceNo", billShopBalance.getBalanceNo());
					deductParams.put("shopNo", billShopBalanceDiff.getShopNo());
					deductParams.put("month", billShopBalanceDiff.getMonth());
					deductParams.put("costDeductType", 1);
					deductParams.put("brandNo", billShopBalanceDiff.getBrandNo());
					BigDecimal diffAmount = new BigDecimal("0");

					BigDecimal balanceDeductAmount = new BigDecimal("0");

					BillShopBalanceDeduct billShopBalanceDeduct = billShopBalanceDeductService
							.getSumAmount(deductParams);
					if (billShopBalanceDeduct != null) {
						diffAmount = billShopBalanceDeduct.getDiffAmount();
						balanceDeductAmount = billShopBalanceDeduct.getDeductAmount();
					}
					if (diffAmount == null) {
						diffAmount = BigDecimal.ZERO;
					}
					if (balanceDeductAmount == null) {
						balanceDeductAmount = BigDecimal.ZERO;
					}
					// 综合店结算差异 = 结算差异页签的扣费差异 + 票前费用的扣费差异金额
					billShopBalanceBrand.setBalanceDiffAmount(billShopBalanceDiff.getDiffAmount().add(diffAmount));
					billShopBalanceBrand.setBalanceDeductAmount(balanceDeductAmount);
					// billShopBalanceBrand.setBrandNo(billShopBalanceDiff.getBrandNo());
					// billShopBalanceBrand.setBrandName(billShopBalanceDiff.getBrandName());
					billShopBalanceBrand.setDeductDiffAmount(billShopBalanceDiff.getDeductDiffAmount());// .add(balanceDeductAmount)
					billShopBalanceBrand.setBalanceNo(billShopBalance.getBalanceNo());
					diffParams.put("generateType", "0");// 系统扣费只取 0：系统自动生成
					List<BillShopBalanceDiff> billShopBalanceDiffList1 = billShopBalanceDiffService
							.getSumAmount(diffParams);// .findByBiz(null,
														// diffParams);
					if (billShopBalanceDiffList1 != null && billShopBalanceDiffList1.size() > 0) {
						for (BillShopBalanceDiff billShopBalanceDiff1 : billShopBalanceDiffList1) {
							billShopBalanceBrand.setSalesAmount(billShopBalanceDiff1.getSalesAmount());
							billShopBalanceBrand.setMallNumber(billShopBalanceDiff1.getMallNumber());
						}
					}
					BigDecimal mallNumber = billShopBalanceBrand.getMallNumber();
					if (mallNumber == null) {
						mallNumber = BigDecimal.ZERO;
					}
					billShopBalanceBrand
							.setReportDiffAmount(billShopBalanceBrand.getSalesAmount().subtract(mallNumber));
					billShopBalanceBrand.setSalesDiffamount(billShopBalanceDiff.getSalesDiffamount());
					billShopBalanceBrand.setDiffAmount(billShopBalanceDiff.getDiffAmount());
					this.getNumDataCalcBrandSum(billShopBalanceDiff, billShopBalanceBrand);

					// 处理预收款 计算 按品牌的 预收款金额 冲销金额
					// BigDecimal prepaymentAmount=new BigDecimal("0");
					// BigDecimal usedPrepaymentAmount=new BigDecimal("0");
					// billShopBalance.setPrepaymentAmount(prepaymentAmount);
					// billShopBalance.setUsedPrepaymentAmount(usedPrepaymentAmount);
					count += billShopBalanceBrandService.modifyById(billShopBalanceBrand);
					billShopBalanceCateSumService.modifyBrandBillingAmountByRound(diffParams);
					billShopBalanceCateSumService.modifyBrandBillingAmountByBrand(diffParams);
				}
			}
		}
		return count;
	}

	/**
	 * 结算期按活动方式汇总数据 bill_shop_balance_pro_sum List<OcOrderSalePromationDto>
	 * findList4OrderSalePromation
	 * 
	 * @param OcOrderParameterDto
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 *             OcOrderSalePromationDto findList4OrderSalePromation
	 * @throws ManagerException
	 */
	public int saveBalanceSalePromationSum(POSOcOrderParameterDto OcOrderParameterDto, Map<String, Object> params)
			throws ServiceException, ManagerException {
		List<POSOcOcGroupPromationDto> ocOcGroupPromationDtoList = orderMainManager
				.findList4OcOrderGroupPromation(OcOrderParameterDto);// .findList4OcGroupOrderPaywayDto(orderParameterDtoList);
		int count = 0;
		if (ocOcGroupPromationDtoList != null && ocOcGroupPromationDtoList.size() > 0) {
			for (POSOcOcGroupPromationDto ocOcGroupPromationDto : ocOcGroupPromationDtoList) {
				BillShopBalanceProSum billShopBalanceProSum = new BillShopBalanceProSum();
				billShopBalanceProSum.setShopNo(OcOrderParameterDto.getShopNo());
				billShopBalanceProSum.setShortName(ocOcGroupPromationDto.getShopName());
				billShopBalanceProSum.setCompanyNo(String.valueOf(params.get("companyNo")));
				billShopBalanceProSum.setCompanyName(String.valueOf(params.get("companyName")));
				billShopBalanceProSum.setMonth(String.valueOf(params.get("month")));
				billShopBalanceProSum.setBalanceStartDate(OcOrderParameterDto.getStartOutDate());
				billShopBalanceProSum.setBalanceEndDate(OcOrderParameterDto.getEndOutDate());

				billShopBalanceProSum.setSaleAmount(ocOcGroupPromationDto.getAmount());
				billShopBalanceProSum.setDiscountName(ocOcGroupPromationDto.getDiscountName());
				billShopBalanceProSum.setDiscount(ocOcGroupPromationDto.getDiscount());
				billShopBalanceProSum.setDiscountCode(ocOcGroupPromationDto.getDiscountCode());
				billShopBalanceProSum.setDeductAmount(ocOcGroupPromationDto.getDiscountAmount());
				billShopBalanceProSum.setBrandNo(ocOcGroupPromationDto.getBrandNo());
				billShopBalanceProSum.setBrandName(ocOcGroupPromationDto.getBrandName());
				billShopBalanceProSum.setBillingCode(ocOcGroupPromationDto.getBillingCode());

				BigDecimal amount = ocOcGroupPromationDto.getAmount();
				if (amount == null) {
					amount = BigDecimal.ZERO;
				}
				BigDecimal discountAmount = ocOcGroupPromationDto.getDiscountAmount();
				if (discountAmount == null) {
					discountAmount = BigDecimal.ZERO;
				}
				// billShopBalanceProSum.setBillingAmount(amount.subtract(discountAmount));
				billShopBalanceProSum.setBalanceNo((String) params.get("balanceNo"));
				params.put("shopNo", OcOrderParameterDto.getShopNo());
				billShopBalanceProSum.setSystemBillingAmount(amount.subtract(discountAmount));
				Shop shop = this.getShopInfo(params);
				billShopBalanceProSum.setMallNo(shop.getMallNo());
				billShopBalanceProSum.setMallName(shop.getMallName());
				String proNo = ocOcGroupPromationDto.getProNo();
				if (StringUtils.isNotEmpty(proNo)) {
					if (proNo.length() > 350) {
						billShopBalanceProSum.setProNo(proNo.substring(0, 350));
					} else
						billShopBalanceProSum.setProNo(proNo);
				}

				// launch_type 活动来源,1-公司活动 2-商场活动
				// discount_type 扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率)
				String discountType = ocOcGroupPromationDto.getDiscountType();
				String discountTypeName = "";
				if ("1".equals(discountType)) {
					discountTypeName = "合同正价扣";
				}
				if ("2".equals(discountType)) {
					discountTypeName = "合同阶梯扣";
				}
				if ("3".equals(discountType)) {
					discountTypeName = "促销扣率";
				} else
					discountTypeName = "正价/品牌活动";

				String launchType = ocOcGroupPromationDto.getLaunchType();
				if (launchType != null && StringUtils.isNotEmpty(String.valueOf(launchType))) {
					if ("1".equals(launchType)) {
						if (StringUtils.isNotEmpty(proNo)) {
							if (proNo.length() > 350) {
								billShopBalanceProSum.setProNo(proNo.substring(0, 350));
							} else
								billShopBalanceProSum.setProNo(proNo);
						}
						billShopBalanceProSum.setProName("品牌活动-" + discountTypeName);
						billShopBalanceProSum.setProStartDate(DateUtil.parseToDate(
								String.valueOf(params.get("startDate")), "yyyy-MM-dd"));
						billShopBalanceProSum.setProEndDate(DateUtil.parseToDate(String.valueOf(params.get("endDate")),
								"yyyy-MM-dd"));
					} else {
						if (StringUtils.isNotEmpty(proNo)) {
							if (proNo.length() > 350) {
								billShopBalanceProSum.setProNo(proNo.substring(0, 350));
							} else
								billShopBalanceProSum.setProNo(proNo);

							billShopBalanceProSum.setProName(discountTypeName + "-"
									+ ocOcGroupPromationDto.getProName());
							billShopBalanceProSum.setProStartDate(ocOcGroupPromationDto.getStartDate());
							billShopBalanceProSum.setProEndDate(ocOcGroupPromationDto.getEndDate());
						} else {
							billShopBalanceProSum.setProName(discountTypeName + "-"
									+ ocOcGroupPromationDto.getProName());
							billShopBalanceProSum.setProStartDate(DateUtil.parseToDate(
									String.valueOf(params.get("startDate")), "yyyy-MM-dd"));
							billShopBalanceProSum.setProEndDate(DateUtil.parseToDate(
									String.valueOf(params.get("endDate")), "yyyy-MM-dd"));
						}
					}
				} else {
					billShopBalanceProSum.setProName(discountTypeName + "-" + ocOcGroupPromationDto.getProName());
					billShopBalanceProSum.setProStartDate(DateUtil.parseToDate(String.valueOf(params.get("startDate")),
							"yyyy-MM-dd"));
					billShopBalanceProSum.setProEndDate(DateUtil.parseToDate(String.valueOf(params.get("endDate")),
							"yyyy-MM-dd"));
				}
				// 来源 促销扣率设置 rate_pro
				// if(ocOcGroupPromationDto != null &&
				// StringUtils.isNotEmpty(ocOcGroupPromationDto.getProNo())) {
				// Map<String, Object> rateparams = new HashMap<String,
				// Object>();
				// rateparams.put("shopNo", OcOrderParameterDto.getShopNo());
				// rateparams.put("proNo", ocOcGroupPromationDto.getProNo());
				//
				// RatePro ratePro = rateProService.findByRatePro(rateparams);
				// if(ratePro != null) {
				// billShopBalanceProSum.setProStartDate(ratePro.getStartDate());
				// billShopBalanceProSum.setProEndDate(ratePro.getEndDate());
				// billShopBalanceProSum.setCostDeductType(ratePro.getDebitedMode());
				// billShopBalanceProSum.setCostPayType(ratePro.getPaymentMode());
				// }
				// }else{
				// billShopBalanceProSum.setProStartDate(DateUtil.parseToDate(String.valueOf(params.get("startDate")),
				// "yyyy-MM-dd"));
				// billShopBalanceProSum.setProEndDate(DateUtil.parseToDate(String.valueOf(params.get("endDate")),
				// "yyyy-MM-dd"));
				// billShopBalanceProSum.setProName("合同阶梯扣");
				// }
				billShopBalanceProSum.setDiscountCode(ocOcGroupPromationDto.getDiscountCode());
				billShopBalanceProSum.setId(UUIDGenerator.getUUID());
				count += billShopBalanceProSumService.add(billShopBalanceProSum);
			}
		}
		return count;
	}

	/**
	 * 结算期按活动方式汇总数据 bill_shop_balance_pro_sum List<OcOrderSalePromationDto>
	 * findList4OrderSalePromation
	 * 
	 * @param OcOrderParameterDto
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 *             OcOrderSalePromationDto findList4OrderSalePromation
	 * @throws ManagerException
	 */
	public int saveBalanceSalePromationSumH(POSOcOrderParameterDto OcOrderParameterDto, Map<String, Object> params)
			throws ServiceException, ManagerException {
		List<POSOcOcGroupPromationDto> ocOcGroupPromationDtoList = orderMainManager
				.findList4OcOrderGroupPromationH(OcOrderParameterDto);// .findList4OcGroupOrderPaywayDto(orderParameterDtoList);
		int count = 0;
		if (ocOcGroupPromationDtoList != null && ocOcGroupPromationDtoList.size() > 0) {
			for (POSOcOcGroupPromationDto ocOcGroupPromationDto : ocOcGroupPromationDtoList) {
				BillShopBalanceProSum billShopBalanceProSum = new BillShopBalanceProSum();
				billShopBalanceProSum.setShopNo(OcOrderParameterDto.getShopNo());
				billShopBalanceProSum.setShortName(ocOcGroupPromationDto.getShopName());
				billShopBalanceProSum.setCompanyNo(String.valueOf(params.get("companyNo")));
				billShopBalanceProSum.setCompanyName(String.valueOf(params.get("companyName")));
				billShopBalanceProSum.setMonth(String.valueOf(params.get("month")));
				billShopBalanceProSum.setBalanceStartDate(OcOrderParameterDto.getStartOutDate());
				billShopBalanceProSum.setBalanceEndDate(OcOrderParameterDto.getEndOutDate());

				billShopBalanceProSum.setSaleAmount(ocOcGroupPromationDto.getAmount());
				billShopBalanceProSum.setDiscountName(ocOcGroupPromationDto.getDiscountName());
				billShopBalanceProSum.setDiscount(ocOcGroupPromationDto.getDiscount());
				billShopBalanceProSum.setDiscountCode(ocOcGroupPromationDto.getDiscountCode());
				billShopBalanceProSum.setDeductAmount(ocOcGroupPromationDto.getDiscountAmount());
				billShopBalanceProSum.setBrandNo(ocOcGroupPromationDto.getBrandNo());
				billShopBalanceProSum.setBrandName(ocOcGroupPromationDto.getBrandName());
				billShopBalanceProSum.setBillingCode(ocOcGroupPromationDto.getBillingCode());

				BigDecimal amount = ocOcGroupPromationDto.getAmount();
				if (amount == null) {
					amount = BigDecimal.ZERO;
				}
				BigDecimal discountAmount = ocOcGroupPromationDto.getDiscountAmount();
				if (discountAmount == null) {
					discountAmount = BigDecimal.ZERO;
				}
				// billShopBalanceProSum.setBillingAmount(amount.subtract(discountAmount));
				billShopBalanceProSum.setBalanceNo((String) params.get("balanceNo"));
				params.put("shopNo", OcOrderParameterDto.getShopNo());
				billShopBalanceProSum.setSystemBillingAmount(amount.subtract(discountAmount));
				Shop shop = this.getShopInfo(params);
				billShopBalanceProSum.setMallNo(shop.getMallNo());
				billShopBalanceProSum.setMallName(shop.getMallName());
				String proNo = ocOcGroupPromationDto.getProNo();
				if (StringUtils.isNotEmpty(proNo)) {
					if (proNo.length() > 350) {
						billShopBalanceProSum.setProNo(proNo.substring(0, 350));
					} else
						billShopBalanceProSum.setProNo(proNo);
				}

				// launch_type 活动来源,1-公司活动 2-商场活动
				// discount_type 扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率)
				String discountType = ocOcGroupPromationDto.getDiscountType();
				String discountTypeName = "";
				if ("1".equals(discountType)) {
					discountTypeName = "合同正价扣";
				}
				if ("2".equals(discountType)) {
					discountTypeName = "合同阶梯扣";
				}
				if ("3".equals(discountType)) {
					discountTypeName = "促销扣率";
				} else
					discountTypeName = "正价/品牌活动";

				String launchType = ocOcGroupPromationDto.getLaunchType();
				if (launchType != null && StringUtils.isNotEmpty(String.valueOf(launchType))) {
					if ("1".equals(launchType)) {
						if (StringUtils.isNotEmpty(proNo)) {
							if (proNo.length() > 350) {
								billShopBalanceProSum.setProNo(proNo.substring(0, 350));
							} else
								billShopBalanceProSum.setProNo(proNo);
						}
						billShopBalanceProSum.setProName("品牌活动-" + discountTypeName);
						billShopBalanceProSum.setProStartDate(DateUtil.parseToDate(
								String.valueOf(params.get("startDate")), "yyyy-MM-dd"));
						billShopBalanceProSum.setProEndDate(DateUtil.parseToDate(String.valueOf(params.get("endDate")),
								"yyyy-MM-dd"));
					} else {
						if (StringUtils.isNotEmpty(proNo)) {
							if (proNo.length() > 350) {
								billShopBalanceProSum.setProNo(proNo.substring(0, 350));
							} else
								billShopBalanceProSum.setProNo(proNo);

							billShopBalanceProSum.setProName(discountTypeName + "-"
									+ ocOcGroupPromationDto.getProName());
							billShopBalanceProSum.setProStartDate(ocOcGroupPromationDto.getStartDate());
							billShopBalanceProSum.setProEndDate(ocOcGroupPromationDto.getEndDate());
						} else {
							billShopBalanceProSum.setProName(discountTypeName + "-"
									+ ocOcGroupPromationDto.getProName());
							billShopBalanceProSum.setProStartDate(DateUtil.parseToDate(
									String.valueOf(params.get("startDate")), "yyyy-MM-dd"));
							billShopBalanceProSum.setProEndDate(DateUtil.parseToDate(
									String.valueOf(params.get("endDate")), "yyyy-MM-dd"));
						}
					}
				} else {
					billShopBalanceProSum.setProName(discountTypeName + "-" + ocOcGroupPromationDto.getProName());
					billShopBalanceProSum.setProStartDate(DateUtil.parseToDate(String.valueOf(params.get("startDate")),
							"yyyy-MM-dd"));
					billShopBalanceProSum.setProEndDate(DateUtil.parseToDate(String.valueOf(params.get("endDate")),
							"yyyy-MM-dd"));
				}
				billShopBalanceProSum.setDiscountCode(ocOcGroupPromationDto.getDiscountCode());
				billShopBalanceProSum.setId(UUIDGenerator.getUUID());
				count += billShopBalanceProSumService.add(billShopBalanceProSum);
			}
		}
		return count;
	}

	/**
	 * 结算外卡每日结算汇总数据 bill_shop_balance_wildsale_sum
	 * 
	 * @param orderParameterDtoList
	 * @return
	 * @throws RpcException
	 * @throws ServiceException
	 *             findList4OcOrderGroupRootCategory
	 * @throws ManagerException
	 */
	public int saveBalanceWildSaleSum(POSOcOrderParameterDto OcOrderParameterDto, Map<String, Object> params)
			throws ServiceException, ManagerException {
		// //.findList4OcGroupOrderPaywayDto(orderParameterDtoList);
		List<POSOcOcGroupWildCardDto> ocOcGroupWildCardDtoList = orderMainManager.findList4OcOrderGroupWildCard(
				OcOrderParameterDto, null);
		int count = 0;
		if (ocOcGroupWildCardDtoList != null && ocOcGroupWildCardDtoList.size() > 0) {
			for (POSOcOcGroupWildCardDto ocOcGroupWildCardDto : ocOcGroupWildCardDtoList) {
				BillShopBalanceWildsaleSum billShopBalanceWildsaleSum = new BillShopBalanceWildsaleSum();
				billShopBalanceWildsaleSum.setId(UUIDGenerator.getUUID());
				billShopBalanceWildsaleSum.setShopNo(ocOcGroupWildCardDto.getShopNo());
				billShopBalanceWildsaleSum.setBalanceNo((String) params.get("balanceNo"));
				billShopBalanceWildsaleSum.setOutDate(ocOcGroupWildCardDto.getOutDate());
				billShopBalanceWildsaleSum.setSaleAmount(ocOcGroupWildCardDto.getSetAmount());// ocOcGroupWildCardDto.getSaleAmount());
				billShopBalanceWildsaleSum.setSettleAmount(ocOcGroupWildCardDto.getAmount());// ocOcGroupWildCardDto.getSettleAmount());
				billShopBalanceWildsaleSum.setDiscAmount(ocOcGroupWildCardDto.getDiscAmount());

				params.put("shopNo", ocOcGroupWildCardDto.getShopNo());
				Shop shop = this.getShopInfo(params);
				if (shop != null) {
					billShopBalanceWildsaleSum.setShortName(shop.getShortName());
					billShopBalanceWildsaleSum.setCompanyNo(shop.getCompanyNo());
					billShopBalanceWildsaleSum.setCompanyName(shop.getCompanyName());
					billShopBalanceWildsaleSum.setMallNo(shop.getMallNo());
					billShopBalanceWildsaleSum.setMallName(shop.getMallName());
				}
				count += billShopBalanceWildsaleSumService.add(billShopBalanceWildsaleSum);
			}
		}
		return count;
	}

	/*
	 * pos查询参数组装
	 */
	public OcOrderParameterDto getOrderParams(String shopNo, Date startDate, Date endDate) {
		OcOrderParameterDto orderParamDto = new OcOrderParameterDto();
		orderParamDto.setShopNo(shopNo);
		orderParamDto.setStartOutDate(startDate);
		orderParamDto.setEndOutDate(endDate);
		List<Integer> businessTypeList = new ArrayList<Integer>();
		businessTypeList.add(0);
		businessTypeList.add(1);
		businessTypeList.add(2);
		businessTypeList.add(5);
		businessTypeList.add(6);
		orderParamDto.setBusinessTypeList(businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(30);
		statusList.add(41);
		orderParamDto.setStatusList(statusList);
		return orderParamDto;
	}

	/*
	 * pos反写参数组装
	 */
	public OcOrderParameterParentDto getOcOrderParameterParentDto(String shopNo, Date startDate, Date endDate) {
		OcOrderParameterParentDto ocOrderParameterParentDto = new OcOrderParameterParentDto();
		ocOrderParameterParentDto.setStartOutDate(startDate);
		ocOrderParameterParentDto.setEndOutDate(endDate);
		List<Integer> businessTypeList = new ArrayList<Integer>();
		businessTypeList.add(0);
		businessTypeList.add(1);
		businessTypeList.add(2);
		businessTypeList.add(5);
		businessTypeList.add(6);
		ocOrderParameterParentDto.setBusinessTypeList(businessTypeList);

		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(30);
		statusList.add(41);
		ocOrderParameterParentDto.setStatusList(statusList);
		return ocOrderParameterParentDto;
	}

	/*
	 * 保存基础扣率设置
	 */
	public int saveRateBasic(List<BalanceParamDto> params) throws RpcException, ServiceException {
		List<RateBasicDto> rateBasicDtoList = balanceRateApi.getBasicRatesByParam(params);
		int count = 0;
		if (rateBasicDtoList != null && rateBasicDtoList.size() > 0) {
			for (RateBasicDto rateBasicDto : rateBasicDtoList) {
				RateBasic rateBasic = new RateBasic();
				rateBasic.setId(rateBasicDto.getId());
				rateBasic = rateBasicService.findById(rateBasic);
				if (rateBasic != null) {
					rateBasicService.deleteById(rateBasic);
				}
				count += rateBasicService.add(rateBasicDto);
			}
		}
		return count;
	}

	/*
	 * 保存合同场地租赁费用 结算期扣费设置
	 */
	public int saveRateExpenseOperat(List<BalanceParamDto> params) throws RpcException, ServiceException {
		List<RateExpenseOperateDto> rateExpenseOperateDtoList = balanceRateApi.getRateExpenseOperatesByParam(params);
		int count = 0;
		if (rateExpenseOperateDtoList != null && rateExpenseOperateDtoList.size() > 0) {
			for (RateExpenseOperateDto rateExpenseOperateDto : rateExpenseOperateDtoList) {
				RateExpenseOperate rateExpenseOperate = new RateExpenseOperate();
				rateExpenseOperate.setId(rateExpenseOperateDto.getId());
				rateExpenseOperate = rateExpenseOperateService.findById(rateExpenseOperate);
				if (rateExpenseOperate != null) {
					rateExpenseOperateService.deleteById(rateExpenseOperate);
				}
				count += rateExpenseOperateService.add(rateExpenseOperateDto);
			}
		}
		return count;
	}

	/*
	 * 保存零星费用
	 */
	public int saveRateExpenseSporadic(List<BalanceParamDto> params) throws RpcException, ServiceException {
		List<RateExpenseSporadicDto> rateExpenseSporadicDtoList = balanceRateApi.getRateExpenseSporadicsByParam(params);
		int count = 0;
		if (rateExpenseSporadicDtoList != null && rateExpenseSporadicDtoList.size() > 0) {
			for (RateExpenseSporadicDto rateExpenseSporadicDto : rateExpenseSporadicDtoList) {
				RateExpenseSporadic rateExpenseSporadic = new RateExpenseSporadic();
				rateExpenseSporadic.setId(rateExpenseSporadicDto.getId());
				rateExpenseSporadic = rateExpenseSporadicService.findById(rateExpenseSporadic);
				if (rateExpenseSporadic != null) {
					rateExpenseSporadicService.deleteById(rateExpenseSporadic);
				}
				count += rateExpenseSporadicService.add(rateExpenseSporadicDto);
			}
		}
		return count;
	}

	/*
	 * 保存结算提醒
	 */
	public int saveRateExpenseRemind(List<BalanceParamDto> params) throws RpcException, ServiceException {
		List<RateExpenseRemindDto> rateExpenseRemindDtoList = balanceRateApi.getRateExpenseRemindsByParam(params);
		int count = 0;
		if (rateExpenseRemindDtoList != null && rateExpenseRemindDtoList.size() > 0) {
			for (RateExpenseRemindDto rateExpenseRemindDto : rateExpenseRemindDtoList) {
				RateExpenseRemind rateExpenseRemind = new RateExpenseRemind();
				rateExpenseRemind.setId(rateExpenseRemindDto.getId());
				rateExpenseRemind = rateExpenseRemindService.findById(rateExpenseRemind);
				if (rateExpenseRemind != null) {
					rateExpenseRemindService.deleteById(rateExpenseRemind);
				}
				count += rateExpenseRemindService.add(rateExpenseRemindDto);
			}
		}
		return count;
	}

	/*
	 * 保存活动扣率设置
	 */
	public int saveRatePro(List<BalanceParamDto> params) throws RpcException, ServiceException {
		List<RateProDto> rateProDtoDtoList = balanceRateApi.getRateProByParam(params);
		int count = 0;
		if (rateProDtoDtoList != null && rateProDtoDtoList.size() > 0) {
			for (RateProDto rateProDto : rateProDtoDtoList) {
				RatePro ratePro = new RatePro();
				ratePro.setId(rateProDto.getId()); // id为返回
				ratePro = rateProService.findById(ratePro);
				if (ratePro != null) {
					rateProService.deleteById(ratePro);
				}
				count += rateProService.add(rateProDto);
			}
		}
		return count;
	}

	@Override
	public BigDecimal getSumAmount(Map<String, Object> params) {
		return billShopBalanceService.getSumAmount(params);
	}

	@Override
	public String getMaxMonth(BillShopBalance billShopBalance) throws ManagerException, ServiceException {
		return billShopBalanceService.getMaxMonth(billShopBalance);
	}

	/**
	 * 通过店铺和结算单中的终止结算日期,查询是否已生成下期结算单
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 是否已生成下期结算单的标志
	 * @throws ManagerException
	 *             异常
	 */
	@Override
	public int hasNextBalanceDate(Map<String, Object> params) throws ManagerException {
		try {
			return billShopBalanceService.hasNextBalanceDate(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 处理结算差异
	 * 
	 * @param billShopBalance
	 *            本期结算单对象
	 * @return 处理的差异数量
	 * @throws ServiceException
	 *             异常
	 */
	private int processBalanceDiff(BillShopBalance billShopBalance) throws Exception {
		// 处理上期未完成的差异：票前费用和结算差异
		int iCount = this.processPreBalanceDiff(billShopBalance, this.initShopBalanceDiffBill(billShopBalance));
		// 处理本期的结算差异
		iCount += this.processCurrentBalanceDiff(billShopBalance, this.initShopBalanceDiffBill(billShopBalance));
		return iCount;
	}

	private int expectCashBalanceDiff(BillShopBalance billShopBalance) throws Exception {
		// 处理预收款金额 结算差异要按店 扣率 结算月来分组汇总
		int iCount = this.processExpectCashBalanceDiff(billShopBalance, this.initShopBalanceDiffBill(billShopBalance));
		// 处理已使用金额(冲销)
		iCount += this.processUseExpectCashBalanceDiff(billShopBalance, this.initShopBalanceDiffBill(billShopBalance));
		return iCount;
	}

	private int processExpectCashBalanceDiff(BillShopBalance billShopBalance, BillShopBalanceDiff billShopBalanceDiff)
			throws ServiceException {
		int count = 0;
		// 设置结算单号
		billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
		billShopBalanceDiff.setDiffTypeCode("10001");
		billShopBalanceDiff.setDiffTypeName("预收款金额");
		BigDecimal prepaymentAmount = new BigDecimal("0");

		// prepaymentAmount = this.getExpectCashAmount(billShopBalance);
		// billShopBalance.setPrepaymentAmount(prepaymentAmount);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalance.getShopNo());
		params.put("month", billShopBalance.getMonth());
		// params.put("status", "2");
		params.put("confirmFlag", "2");
		params.put("balanceStatus", "1");
		params.put("groupBrand", "groupBrand");
		List<ExpectCash> expectCashList = expectCashService.processExpectCashBalanceDiff(params);
		if (expectCashList != null && expectCashList.size() > 0) {
			for (ExpectCash expectCash : expectCashList) {
				BigDecimal rate = new BigDecimal("0");
				BigDecimal expectCashAmount = new BigDecimal("0");
				if (expectCash != null) {
					rate = expectCash.getRate();
					expectCashAmount = expectCash.getExpectCashAmount();
				}
				if (null == rate) {
					rate = new BigDecimal("0");
				}
				if (null == expectCashAmount) {
					expectCashAmount = new BigDecimal("0");
				}
				billShopBalanceDiff.setId(UUIDGenerator.getUUID());
				billShopBalanceDiff.setMallNumber(expectCashAmount);
				billShopBalanceDiff.setSalesAmount(expectCashAmount);
				billShopBalanceDiff.setDiscount(rate);
				billShopBalanceDiff.setMonth(billShopBalance.getMonth());
				billShopBalanceDiff.setDeductDiffAmount(expectCashAmount.multiply(rate).divide(new BigDecimal(100)));
				billShopBalanceDiff.setProNo(expectCash.getProNo());
				billShopBalanceDiff.setProName(expectCash.getProName());
				billShopBalanceDiff.setDiscountCode(expectCash.getRateCode());
				billShopBalanceDiff.setBrandNo(expectCash.getBrandNo());
				billShopBalanceDiff.setBrandName(expectCash.getBrandName());
				count += billShopBalanceDiffService.add(billShopBalanceDiff);
			}
		}
		return count;
	}

	private int processUseExpectCashBalanceDiff(BillShopBalance billShopBalance, BillShopBalanceDiff billShopBalanceDiff)
			throws ServiceException {
		int count = 0;
		// 设置结算单号
		billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
		billShopBalanceDiff.setDiffTypeCode("10002");
		billShopBalanceDiff.setDiffTypeName("预收款冲销金额");
		billShopBalance.setPayCode("09");// 按照预收款支付方式汇总
		BigDecimal zero = new BigDecimal("0");
		// BillShopBalanceDaysaleSum
		// billShopBalanceDaysaleSum=getSystemSalesAmount(billShopBalance);

		// 预收款冲销金额要处理为负数
		// if(billShopBalanceDaysaleSum != null){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalance.getShopNo());
		// params.put("month", billShopBalance.getMonth());
		// params.put("status", "2");
		// params.put("confirmFlag", "2");
		// params.put("balanceStatus", "1");
		params.put("balanceStartDate", billShopBalance.getBalanceStartDate());
		params.put("balanceEndDate", billShopBalance.getBalanceEndDate());
		params.put("payCode", "09");
		// params.put("groupBy", "groupBy");
		params.put("groupBrand", "groupBrand");
		params.put("month", billShopBalance.getMonth());
		List<ExpectCash> expectCashList = expectCashService.processUseExpectCashBalanceDiff(params);
		if (expectCashList != null && expectCashList.size() > 0) {
			for (ExpectCash expectCash : expectCashList) {
				BigDecimal rate = new BigDecimal("0");
				BigDecimal usedPrepaymentAmount = new BigDecimal("0");
				if (expectCash != null) {
					rate = expectCash.getRate();
					usedPrepaymentAmount = expectCash.getExpectCashAmount();
					billShopBalanceDiff.setProNo(expectCash.getProNo());
					billShopBalanceDiff.setProName(expectCash.getProName());
					billShopBalanceDiff.setDiscountCode(expectCash.getRateCode());
					billShopBalanceDiff.setBrandNo(expectCash.getBrandNo());
					billShopBalanceDiff.setBrandName(expectCash.getBrandName());
				}
				if (null == rate) {
					rate = new BigDecimal("0");
				}
				if (null == usedPrepaymentAmount) {
					usedPrepaymentAmount = new BigDecimal("0");
				}
				billShopBalanceDiff.setSalesAmount(zero.subtract(usedPrepaymentAmount));
				billShopBalanceDiff.setMallNumber(zero.subtract(usedPrepaymentAmount));
				billShopBalanceDiff.setId(UUIDGenerator.getUUID());
				billShopBalanceDiff.setDiscount(rate);
				billShopBalanceDiff.setMonth(billShopBalance.getMonth());
				billShopBalanceDiff.setDeductDiffAmount(billShopBalanceDiff.getSalesAmount().multiply(rate)
						.divide(new BigDecimal(100)));

				count += billShopBalanceDiffService.add(billShopBalanceDiff);
			}
			// }
			// billShopBalance.setUsedPrepaymentAmount(usedPrepaymentAmount.subtract(billShopBalanceDaysaleSum.getAmount()));
			// billShopBalanceDiff.setSalesAmount(usedPrepaymentAmount.subtract(billShopBalanceDaysaleSum.getAmount()));
		}
		return count;
	}

	/**
	 * 处理上期未完成的结算差异
	 * 
	 * @param billShopBalance
	 *            本期结算单对象
	 * @param billShopBalanceDiff
	 *            初始化的结算差异对象
	 * @return 处理的差异数量
	 * @throws ServiceException
	 *             异常
	 */
	private int processPreBalanceDiff(BillShopBalance billShopBalance, BillShopBalanceDiff billShopBalanceDiff)
			throws ServiceException {
		int count = 0;
		// 获取上期结算单号
		Map<String, Object> perBillMap = new HashMap<String, Object>();
		perBillMap.put("companyNo", billShopBalance.getCompanyNo());
		perBillMap.put("shopNo", billShopBalance.getShopNo());
		perBillMap.put("balanceStartDate", DateUtil.format(billShopBalance.getBalanceStartDate(), "yyyy-MM-dd"));
		perBillMap.put("balanceFlag", 1); 
		// 获取上期的结算单对象
		BillShopBalance preBillShopBalance = billShopBalanceService.getPerBillShopBalance(perBillMap);
		if (preBillShopBalance != null) {
			// 设置结算期为上个结算单的结算期
			billShopBalanceDiff.setMonth(preBillShopBalance.getMonth());
			// 查询上期未结算完成的票前费用数据
			Map<String, Object> preDeductParams = new HashMap<String, Object>();
			preDeductParams.put("balanceNo", preBillShopBalance.getBalanceNo());
			preDeductParams.put("costDeductType", "1");
			preDeductParams.put("processStatus", "1");
			List<BillShopBalanceDeduct> billShopBalanceDeductList = billShopBalanceDeductService.findByBiz(null,
					preDeductParams);
			if (billShopBalanceDeductList != null && billShopBalanceDeductList.size() > 0) {
				for (BillShopBalanceDeduct billShopBalanceDeduct : billShopBalanceDeductList) {
					// 设置结算单号
					billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
					// 设置上期结算单号
					billShopBalanceDiff.setParBalanceNo(billShopBalanceDeduct.getBalanceNo());
					billShopBalanceDiff.setMarkId(billShopBalanceDeduct.getId());
					if (billShopBalanceDeduct.getDeductAmount() != null
							&& billShopBalanceDeduct.getActualAmount() != null) {
						// billShopBalanceDiff.setDiffBalance(billShopBalanceDeduct.getDeductAmount().subtract(
						// billShopBalanceDeduct.getActualAmount()));
						billShopBalanceDiff.setDiffBalance(billShopBalanceDeduct.getDiffAmount());
					}
					billShopBalanceDiff.setMonth(preBillShopBalance.getMonth());
					billShopBalanceDiff.setBalanceStartDate(preBillShopBalance.getBalanceStartDate());
					billShopBalanceDiff.setBalanceEndDate(preBillShopBalance.getBalanceEndDate());
					billShopBalanceDiff.setDiffDate(billShopBalanceDeduct.getBalanceEndDate());
					billShopBalanceDiff.setPreDiffBalance(billShopBalanceDiff.getDiffBalance());
					billShopBalanceDiff.setDiffReason(billShopBalanceDeduct.getDiffReason());// 上期费用未完成差异
					billShopBalanceDiff.setDiffTypeCode("1001");
					billShopBalanceDiff.setId(UUIDGenerator.getUUID());
					billShopBalanceDiff.setDiffTypeName("费用差异");
					count += billShopBalanceDiffService.add(billShopBalanceDiff);
				}
			}
			// 处理上期未完成的差异数据
			Map<String, Object> preDiffParams = new HashMap<String, Object>();
			preDiffParams.put("status", "0");
			preDiffParams.put("balanceNo", preBillShopBalance.getBalanceNo());
			List<BillShopBalanceDiff> billShopBalanceDiffList = billShopBalanceDiffService.findByBiz(null,
					preDiffParams);
			if (billShopBalanceDiffList != null && billShopBalanceDiffList.size() > 0) {
				for (BillShopBalanceDiff billShopBalanceDiffBef : billShopBalanceDiffList) {
					// 设置结算单号
					billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
					// 设置上期结算单号
					billShopBalanceDiff.setParBalanceNo(billShopBalanceDiffBef.getBalanceNo());
					billShopBalanceDiff.setMarkId(billShopBalanceDiffBef.getId());
					billShopBalanceDiff.setDiffTypeCode(billShopBalanceDiffBef.getDiffTypeCode());
					billShopBalanceDiff.setDiffTypeName(billShopBalanceDiffBef.getDiffTypeName());

					billShopBalanceDiff.setProNo(billShopBalanceDiffBef.getProNo());
					billShopBalanceDiff.setProName(billShopBalanceDiffBef.getProName());
					billShopBalanceDiff.setProStartDate(billShopBalanceDiffBef.getProStartDate());
					billShopBalanceDiff.setProEndDate(billShopBalanceDiffBef.getProEndDate());
					billShopBalanceDiff.setDiscount(billShopBalanceDiffBef.getDiscount());

					BillShopBalance billShopBalance2 = new BillShopBalance();
					// Map<String, Object> preParams = new HashMap<String,
					// Object>();
					// preParams.put("balanceNo",
					// preBillShopBalance.getBalanceNo());
					billShopBalance2.setBalanceNo(preBillShopBalance.getBalanceNo());
					BillShopBalance billShopBalance1 = billShopBalanceMapper.selectByPrimaryKey(billShopBalance2);
					billShopBalanceDiff.setDiffDate(billShopBalanceDiffBef.getDiffDate());

					// billShopBalanceDiff.setDiffTypeCode("1002");
					// billShopBalanceDiff.setDiffTypeName("活动差异");
					billShopBalanceDiff.setDiffTypeCode(billShopBalanceDiffBef.getDiffTypeCode());
					billShopBalanceDiff.setDiffTypeName(billShopBalanceDiffBef.getDiffTypeName());
					billShopBalanceDiff.setDiffReason(billShopBalanceDiffBef.getDiffReason());// 上期差异未完成
					billShopBalanceDiff.setMonth(preBillShopBalance.getMonth());
					billShopBalanceDiff.setBalanceStartDate(preBillShopBalance.getBalanceStartDate());
					billShopBalanceDiff.setBalanceEndDate(preBillShopBalance.getBalanceEndDate());
					// billShopBalanceDiff.setMallNumber(billShopBalanceDiffBef.getMallNumber());
					// billShopBalanceDiff.setSalesAmount(billShopBalanceDiffBef.getSalesAmount());
					// 扣费差异
					// billShopBalanceDiff.setDiffAmount(billShopBalanceDiffBef.getDiffAmount());

					// billShopBalanceDiff.setBalanceAmount(billShopBalanceDiffBef.getBalanceAmount());
					// billShopBalanceDiff.setChangeAmount(billShopBalanceDiffBef.getChangeAmount());
					// billShopBalanceDiff.setDeductDiffAmount(billShopBalanceDiffBef.getDeductDiffAmount());
					// billShopBalanceDiff.setSalesDiffamount(billShopBalanceDiffBef.getSalesDiffamount());
					billShopBalanceDiff.setDiffBalance(billShopBalanceDiffBef.getDiffBalance());

					billShopBalanceDiff.setBrandNo(billShopBalanceDiffBef.getBrandNo());
					billShopBalanceDiff.setBrandName(billShopBalanceDiffBef.getBrandName());
					billShopBalanceDiff.setBillingCode(billShopBalanceDiffBef.getBillingCode());
					billShopBalanceDiff.setId(UUIDGenerator.getUUID());
					// 本期差异余额=上期差异余额
					billShopBalanceDiff.setPreDiffBalance(billShopBalanceDiffBef.getDiffBalance());
					// 设置初始的差异编码
					if (StringUtils.isNotEmpty(billShopBalanceDiffBef.getRootDiffId())) {
						billShopBalanceDiff.setRootDiffId(billShopBalanceDiffBef.getRootDiffId());
					} else {
						billShopBalanceDiff.setRootDiffId(billShopBalanceDiffBef.getId());
					}
					count += billShopBalanceDiffService.add(billShopBalanceDiff);
				}
			}
		}
		return count;
	}

	/**
	 * 处理本期的结算差异
	 * 
	 * @param billShopBalance
	 *            本期的结算单对象
	 * @param billShopBalanceDiff
	 *            初始化的结算差异对象
	 * @return 处理的差异数量
	 * @throws ServiceException
	 *             异常
	 */
	private int processCurrentBalanceDiff(BillShopBalance billShopBalance, BillShopBalanceDiff billShopBalanceDiff)
			throws ServiceException {
		int iCount = 0;
		// 查询结算差异方式设置
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("companyNo", billShopBalance.getCompanyNo());
		params.put("shopNo", billShopBalance.getShopNo());
		List<BillShopBalanceSet> billShopBalanceSetList = billShopBalanceSetService.findByBiz(null, params);
		if (billShopBalanceSetList != null && billShopBalanceSetList.size() > 0) {
			for (BillShopBalanceSet billShopBalanceSet : billShopBalanceSetList) {
				// 2-按促销活动:将本期结算单的促销活动数据带入差异表中
				if (billShopBalanceSet.getBalanceDiffType().intValue() == ShopMallEnums.BalanceDiffType.SALES_PROMOTION
						.getRequestId().intValue()) {
					iCount += this.processCurBySalesPromotion(billShopBalance, billShopBalanceDiff);
				} else if (billShopBalanceSet.getBalanceDiffType() == ShopMallEnums.BalanceDiffType.SALE.getRequestId()
						.intValue()) { // 3-按销售:汇总一条数据
					iCount += this.processCurBySale(billShopBalance, billShopBalanceDiff);
				} else if (billShopBalanceSet.getBalanceDiffType() == ShopMallEnums.BalanceDiffType.CODE.getRequestId()
						.intValue()) { // 4-按商场结算码:汇总一条数据
					iCount += this.processCurByCode(billShopBalance, billShopBalanceDiff);
				} else if (billShopBalanceSet.getBalanceDiffType() == ShopMallEnums.BalanceDiffType.DETAIL
						.getRequestId().intValue()) { // 1-按明细 销售:汇总一条数据
					iCount += this.processCurBySale(billShopBalance, billShopBalanceDiff);
				}
			}
		} else { // 如果没设置结算差异方式，默认按促销活动
			iCount += this.processCurBySalesPromotion(billShopBalance, billShopBalanceDiff);
		}
		return iCount;
	}

	private int processCurByCode(BillShopBalance billShopBalance, BillShopBalanceDiff billShopBalanceDiff)
			throws ServiceException {
		int iCount = 0;
		BigDecimal rateNum = new BigDecimal("100");

		Map<String, Object> proSumParams = new HashMap<String, Object>();
		proSumParams.put("shopNo", billShopBalance.getShopNo());
		proSumParams.put("month", billShopBalance.getMonth());
		proSumParams.put("startDate", DateUtil.format(billShopBalance.getBalanceStartDate(), "yyyy-MM-dd"));
		proSumParams.put("endDate", DateUtil.format(billShopBalance.getBalanceEndDate(), "yyyy-MM-dd"));
		proSumParams.put("balanceNo", billShopBalance.getBalanceNo()); // add
																		// 201503121800
		// 查询本期按促销活动生成的汇总数据
		List<BillShopBalanceCodeSum> billShopBalanceCodeSumList = billShopBalanceCodeSumService.findByBiz(null,
				proSumParams);
		if (billShopBalanceCodeSumList != null && billShopBalanceCodeSumList.size() > 0) {
			for (BillShopBalanceCodeSum billShopBalanceCodeSum : billShopBalanceCodeSumList) {
				// 设置结算单号
				billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
				billShopBalanceDiff.setMonth(billShopBalance.getMonth());
				billShopBalanceDiff.setBalanceStartDate(billShopBalance.getBalanceStartDate());
				billShopBalanceDiff.setBalanceEndDate(billShopBalance.getBalanceEndDate());
				billShopBalanceDiff.setBillingCode(billShopBalanceCodeSum.getBillingCode());
				// billShopBalanceDiff.setProName(billShopBalanceCodeSum.getProName());
				// billShopBalanceDiff.setProStartDate(billShopBalanceCodeSum.getProStartDate());
				// billShopBalanceDiff.setProEndDate(billShopBalanceCodeSum.getProEndDate());
				billShopBalanceDiff.setDiscount(billShopBalanceCodeSum.getDiscount());
				billShopBalanceDiff.setSalesAmount(billShopBalanceCodeSum.getSaleAmount());
				billShopBalanceDiff.setDeductDiffAmount(billShopBalanceCodeSum.getDeductAmount());
				billShopBalanceDiff.setDiffTypeCode("1004");
				billShopBalanceDiff.setDiffTypeName("活动-商场结算码");
				billShopBalanceDiff.setId(UUIDGenerator.getUUID());
				billShopBalanceDiff.setDiffReason("");// 促销活动差异
				billShopBalanceDiff.setDiffDate(billShopBalance.getBalanceEndDate());
				billShopBalanceDiff.setStatus(0);
				// billShopBalanceDiff.setBrandNo(billShopBalanceCodeSum.getb);
				// billShopBalanceDiff.setBrandName(brandName);
				iCount += billShopBalanceDiffService.add(billShopBalanceDiff);
			}
		}
		return iCount;
	}

	/**
	 * 处理本期按促销活动方式的结算差异
	 * 
	 * @param billShopBalance
	 *            本期的结算单对象
	 * @param billShopBalanceDiff
	 *            初始化的差异对象
	 * @return 按促销活动生成的差异数量
	 * @throws ServiceException
	 *             异常
	 */
	private int processCurBySalesPromotion(BillShopBalance billShopBalance, BillShopBalanceDiff billShopBalanceDiff)
			throws ServiceException {
		int iCount = 0;
		BigDecimal rateNum = new BigDecimal("100");

		Map<String, Object> proSumParams = new HashMap<String, Object>();
		proSumParams.put("shopNo", billShopBalance.getShopNo());
		proSumParams.put("month", billShopBalance.getMonth());
		proSumParams.put("startDate", DateUtil.format(billShopBalance.getBalanceStartDate(), "yyyy-MM-dd"));
		proSumParams.put("endDate", DateUtil.format(billShopBalance.getBalanceEndDate(), "yyyy-MM-dd"));
		proSumParams.put("balanceNo", billShopBalance.getBalanceNo()); // add
																		// 201503121800
		// 查询本期按促销活动生成的汇总数据
		List<BillShopBalanceProSum> billShopBalanceProSumList = billShopBalanceProSumService.findByBiz(null,
				proSumParams);
		if (billShopBalanceProSumList != null && billShopBalanceProSumList.size() > 0) {
			for (BillShopBalanceProSum billShopBalanceProSum : billShopBalanceProSumList) {
				// 设置结算单号
				billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
				billShopBalanceDiff.setMonth(billShopBalance.getMonth());
				billShopBalanceDiff.setBalanceStartDate(billShopBalance.getBalanceStartDate());
				billShopBalanceDiff.setBalanceEndDate(billShopBalance.getBalanceEndDate());
				String proNo = billShopBalanceProSum.getProNo();
				if (StringUtils.isNotEmpty(proNo)) {
					if (proNo.length() > 350) {
						billShopBalanceDiff.setProNo(proNo.substring(0, 350));
					} else
						billShopBalanceDiff.setProNo(proNo);
				}
				billShopBalanceDiff.setProName(billShopBalanceProSum.getProName());
				billShopBalanceDiff.setProStartDate(billShopBalanceProSum.getProStartDate());
				billShopBalanceDiff.setProEndDate(billShopBalanceProSum.getProEndDate());
				billShopBalanceDiff.setDiscount(billShopBalanceProSum.getDiscount());
				billShopBalanceDiff.setSalesAmount(billShopBalanceProSum.getSaleAmount());
				billShopBalanceDiff.setMallNumber(billShopBalanceDiff.getSalesAmount());
				billShopBalanceDiff.setBrandNo(billShopBalanceProSum.getBrandNo());
				billShopBalanceDiff.setBrandName(billShopBalanceProSum.getBrandName());
				billShopBalanceDiff.setBillingCode(billShopBalanceProSum.getBillingCode());
				BigDecimal systemSalesAmount = billShopBalanceDiff.getSalesAmount();
				if (systemSalesAmount == null) {
					systemSalesAmount = BigDecimal.valueOf(0);
				}
				BigDecimal discount = billShopBalanceDiff.getDiscount();
				if (discount == null) {
					discount = BigDecimal.valueOf(0);
				}
				// 商场报数
				BigDecimal mallNumberAmount = billShopBalanceDiff.getMallNumber();
				if (mallNumberAmount == null) {
					mallNumberAmount = BigDecimal.ZERO;
				}
				
				//华北调整为    系统收入 - 商场报数	salesDiffamount
				if("D".equals(billShopBalance.getCompanyNo().substring(0, 1))){
					if (discount.longValue() > 0) {
						billShopBalanceDiff.setDiffAmount((systemSalesAmount.subtract(mallNumberAmount).multiply(discount
								.divide(rateNum))));
					} else {
						billShopBalanceDiff.setDiffAmount(systemSalesAmount.subtract(mallNumberAmount));
					}
					billShopBalanceDiff.setSalesDiffamount(systemSalesAmount.subtract(mallNumberAmount));
				}else 
					// 差异金额 = (商场报数-系统收入)*扣率 如果扣率为0 就是 商场报数 - 系统收入
					if (discount.longValue() > 0) {
						billShopBalanceDiff.setDiffAmount((mallNumberAmount.subtract(systemSalesAmount).multiply(discount
								.divide(rateNum))));
					} else {
						billShopBalanceDiff.setDiffAmount(mallNumberAmount.subtract(systemSalesAmount));
					}
					billShopBalanceDiff.setSalesDiffamount(mallNumberAmount.subtract(systemSalesAmount));
				
				billShopBalanceDiff.setDeductDiffAmount(billShopBalanceProSum.getDeductAmount());
				billShopBalanceDiff.setDiffTypeCode("1002");
				billShopBalanceDiff.setDiffTypeName("活动差异");
				billShopBalanceDiff.setId(UUIDGenerator.getUUID());
				billShopBalanceDiff.setDiffReason("");// 促销活动差异
				billShopBalanceDiff.setDiffDate(billShopBalance.getBalanceEndDate());
				billShopBalanceDiff.setDiscountCode(billShopBalanceProSum.getDiscountCode());
				if (billShopBalanceDiff.getDiffAmount().compareTo(BigDecimal.ZERO) == 0) {
					billShopBalanceDiff.setStatus(1);
				} else {
					billShopBalanceDiff.setStatus(0);
				}
				iCount += billShopBalanceDiffService.add(billShopBalanceDiff);
			}
		}
		return iCount;
	}

	/**
	 * 处理本期按销售汇总方式的结算差异
	 * 
	 * @param billShopBalance
	 *            本期结算单对象
	 * @param billShopBalanceDiff
	 *            初始化的差异对象
	 * @return 按销售汇总生成的差异数量
	 * @throws ServiceException
	 *             异常
	 */
	private int processCurBySale(BillShopBalance billShopBalance, BillShopBalanceDiff billShopBalanceDiff)
			throws ServiceException {
		int iCount = 0;
		BigDecimal rateNum = new BigDecimal("100");
		// 设置结算单号
		billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
		billShopBalanceDiff.setMonth(billShopBalance.getMonth());
		billShopBalanceDiff.setBalanceStartDate(billShopBalance.getBalanceStartDate());
		billShopBalanceDiff.setBalanceEndDate(billShopBalance.getBalanceEndDate());
		billShopBalanceDiff.setDiffTypeCode("1003");
		billShopBalanceDiff.setDiffTypeName("报数差异");
		billShopBalanceDiff.setDiffDate(billShopBalance.getBalanceEndDate());

		// 获取结算单按销售汇总的扣费金额汇总
		Map<String, Object> proSumParams = new HashMap<String, Object>();
		proSumParams.put("shopNo", billShopBalance.getShopNo());
		proSumParams.put("month", billShopBalance.getMonth());
		proSumParams.put("startDate", DateUtil.format(billShopBalance.getBalanceStartDate(), "yyyy-MM-dd"));
		proSumParams.put("endDate", DateUtil.format(billShopBalance.getBalanceEndDate(), "yyyy-MM-dd"));
		proSumParams.put("balanceNo", billShopBalance.getBalanceNo());
		proSumParams.put("groupByBrand", "groupByBrand");
		List<BillShopBalanceProSum> billShopBalanceProSumList = billShopBalanceProSumService.getSumAmount(proSumParams);
		if (billShopBalanceProSumList != null && billShopBalanceProSumList.size() > 0) {
			for (BillShopBalanceProSum billShopBalanceProSum : billShopBalanceProSumList) {
				billShopBalanceDiff.setId(UUIDGenerator.getUUID());
				billShopBalanceDiff.setDeductDiffAmount(billShopBalanceProSum.getDeductAmount());
				billShopBalanceDiff.setBrandNo(billShopBalanceProSum.getBrandNo());
				billShopBalanceDiff.setBrandName(billShopBalanceProSum.getBrandName());
				billShopBalanceDiff.setBillingCode(billShopBalanceProSum.getBillingCode());
				billShopBalanceDiff.setDiscountCode(billShopBalanceProSum.getDiscountCode());
				billShopBalanceDiff.setSalesAmount(billShopBalanceProSum.getSaleAmount());
				billShopBalanceDiff.setMallNumber(billShopBalanceDiff.getSalesAmount());

				// 扣费差异 = (商场报数-系统收入)*扣率 如果扣率为0 就是 商场报数 - 系统收入
				// 扣费差异 = (系统收入-商场报数)
				BigDecimal systemSalesAmount = billShopBalanceDiff.getSalesAmount();
				if (systemSalesAmount == null) {
					systemSalesAmount = BigDecimal.valueOf(0);
				}
				BigDecimal discount = billShopBalance.getContractRate();// .getDiscount();
				if (discount == null) {
					discount = BigDecimal.valueOf(0);
				}
				// 商场报数
				BigDecimal mallNumberAmount = billShopBalanceDiff.getMallNumber();
				if (mallNumberAmount == null) {
					mallNumberAmount = BigDecimal.ZERO;
				}
				if (discount.longValue() > 0) {
					billShopBalanceDiff.setDiffAmount((systemSalesAmount.subtract(mallNumberAmount).multiply(discount
							.divide(rateNum))));
				} else {
					billShopBalanceDiff.setDiffAmount(systemSalesAmount.subtract(mallNumberAmount));
				}

				if (billShopBalanceDiff.getDiffAmount().compareTo(BigDecimal.ZERO) == 0) {
					billShopBalanceDiff.setStatus(1);
				} else {
					billShopBalanceDiff.setStatus(0);
				}

				iCount += billShopBalanceDiffService.add(billShopBalanceDiff);
			}
		}
		return iCount;
	}

	/**
	 * 初始化结算差异对象
	 * 
	 * @param billShopBalance
	 *            当前结算单对象
	 * @throws Exception
	 */
	private BillShopBalanceDiff initShopBalanceDiffBill(BillShopBalance billShopBalance) throws Exception {
		BillShopBalanceDiff billShopBalanceDiff = new BillShopBalanceDiff();
		billShopBalanceDiff.setBillDate(new Date());
		String billNo = codingRuleService.getSerialNo(BillShopBalanceDiff.class.getSimpleName());
		billShopBalanceDiff.setBillNo(billNo);
		billShopBalanceDiff.setBalanceNo(billShopBalance.getBalanceNo());
		billShopBalanceDiff.setCompanyNo(billShopBalance.getCompanyNo());
		billShopBalanceDiff.setCompanyName(billShopBalance.getCompanyName());
		billShopBalanceDiff.setShopNo(billShopBalance.getShopNo());
		billShopBalanceDiff.setShortName(billShopBalance.getShortName());
		billShopBalanceDiff.setId(UUIDGenerator.getUUID());
		billShopBalanceDiff.setDiffDate(billShopBalance.getBalanceEndDate());
		billShopBalanceDiff.setSalesDiffamount(BigDecimal.ZERO);

		billShopBalanceDiff.setCreateUser(billShopBalance.getCreateUser());
		billShopBalanceDiff.setCreateTime(DateUtil.getCurrentDateTime());
		billShopBalanceDiff.setUpdateUser(billShopBalance.getUpdateUser());
		billShopBalanceDiff.setUpdateTime(DateUtil.getCurrentDateTime());

		// 根据店铺编码查询店铺所属的地区等扩展信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopNo", billShopBalance.getShopNo());
		Shop shop = shopService.selectSubsiInfo(params);
		if (shop != null) {
			billShopBalanceDiff.setZoneNo(shop.getZoneNo());
			billShopBalanceDiff.setZoneName(shop.getZoneName());
			billShopBalanceDiff.setOrganNo(shop.getOrganNo());
			billShopBalanceDiff.setOrganName(shop.getOrganName());
		}
		ShopBrand shopBrand = shopService.selectShopBrandInfo(params);
		if (shopBrand != null) {
			billShopBalanceDiff.setBrandNo(shopBrand.getBrandNo());
			billShopBalanceDiff.setBrandName(shopBrand.getName());
		}
		return billShopBalanceDiff;
	}

	@Override
	public BillShopBalance getBacksectionSplitDeduct(Map<String, Object> params) throws ManagerException {
		return billShopBalanceService.getBacksectionSplitDeduct(params);
	}

	@Override
	public List<BillShopBalance> findShopBalanceDeductAfter(Map<String, Object> params) throws ManagerException {
		if (params.get("shopNos") != null && StringUtils.isNotEmpty(String.valueOf(params.get("shopNos")))) {
			List<String> shopNos = Arrays.asList(String.valueOf(params.get("shopNos")).split(","));
			params.put("shopNos", shopNos);
		}
		params.put("status", 4);

		return billShopBalanceService.findShopBalanceDeductAfter(params);
	}

	/**
	 * 复制结算单
	 * 
	 * @param copyBill
	 *            待复制的结算单对象
	 * @param systemUser
	 *            系统登录用户
	 * @return 复制后的结算单对象
	 * @throws ManagerException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	@Override
	public BillShopBalance copyBill(BillShopBalance copyBill, SystemUser systemUser) throws ManagerException {
		try {
			BillShopBalance bill = billShopBalanceService.findById(copyBill);
			ShopBalanceDate shopBalanceDatePar = new ShopBalanceDate();
			if (bill == null) {
				return null;
			}
			// 设置新的结算单号
			String requestId = BillShopBalance.class.getSimpleName();
			// 调用单据编号拼接处理方法，返回最终的单据编号
			String balanceNo = commonUtilService.getNewBillNoCompanyNo(null, bill.getShopNo(), requestId);
			bill.setId(UUIDGenerator.getUUID());
			bill.setBalanceNo(balanceNo);
			// 初始化单据对象中某些属性字段的值
			bill.setBalanceType((byte) ShopMallEnums.BalanceType.FORMAL_BALANCE.getRequestId().intValue());
			bill.setStatus(1);
			bill.setAuditor(null);
			bill.setAuditTime(null);
			bill.setUpdateTime(null);
			bill.setUpdateUser(null);
			bill.setCreateUser(systemUser.getUsername());
			bill.setCreateTime(new Date());
			bill.setInvoiceApplyNo(null);
			bill.setInvoiceRegisterNo(null);
			bill.setInvoiceApplyDate(null);
			bill.setInvoiceNo(null);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("balanceNo", copyBill.getBalanceNo());
			// 复制bill_shop_balance_cate_sum--商场门店结算期大类汇总数据
			List<BillShopBalanceCateSum> lstCateSum = billShopBalanceCateSumService.findByBiz(null, params);
			if (lstCateSum != null && lstCateSum.size() > 0) {
				for (BillShopBalanceCateSum model : lstCateSum) {
					model.setId(UUIDGenerator.getUUID());
					model.setBalanceNo(balanceNo);
					billShopBalanceCateSumService.add(model);
				}
			}
			// 复制bill_shop_balance_daysale_sum--日销售汇总数据
			List<BillShopBalanceDaysaleSum> lstDaysaleSum = billShopBalanceDaysaleSumService.findByBiz(null, params);
			if (lstDaysaleSum != null && lstDaysaleSum.size() > 0) {
				for (BillShopBalanceDaysaleSum model : lstDaysaleSum) {
					model.setBalanceNo(balanceNo);
					model.setId(UUIDGenerator.getUUID());
					billShopBalanceDaysaleSumService.add(model);
				}
			}
			// 复制bill_shop_balance_pro_sum--按活动方式汇总数据
			List<BillShopBalanceProSum> lstProSum = billShopBalanceProSumService.findByBiz(null, params);
			if (lstProSum != null && lstProSum.size() > 0) {
				for (BillShopBalanceProSum model : lstProSum) {
					model.setBalanceNo(balanceNo);
					model.setId(UUIDGenerator.getUUID());
					billShopBalanceProSumService.add(model);
				}
			}
			// 复制bill_shop_balance_deduct--费用
			List<BillShopBalanceDeduct> lstDeduct = billShopBalanceDeductService.findByBiz(null, params);
			if (lstDeduct != null && lstDeduct.size() > 0) {
				for (BillShopBalanceDeduct model : lstDeduct) {
					model.setId(UUIDGenerator.getUUID());
					model.setBalanceNo(balanceNo);
					billShopBalanceDeductService.add(model);
				}
			}
			// 复制bill_shop_balance_diff--结算差异
			List<BillShopBalanceDiff> lstDiff = billShopBalanceDiffService.findByBiz(null, params);
			if (lstDiff != null && lstDiff.size() > 0) {
				for (BillShopBalanceDiff model : lstDiff) {
					model.setId(UUIDGenerator.getUUID());
					model.setBillNo(codingRuleService.getSerialNo(BillShopBalanceDiff.class.getSimpleName()));
					model.setBalanceNo(balanceNo);
					billShopBalanceDiffService.add(model);
				}
			}
			// 插入复制的结算单
			billShopBalanceService.add(bill);
			// 校验数据
			Map<String, String> msgMap = this.validateDataBalance(bill);
			if (msgMap != null && msgMap.size() > 0) {
				bill.setErrorInfo(msgMap.get("errorMsg"));
				bill.setShowRateExpenseRemind(msgMap.get("showRateExpenseRemind"));
				bill.setErrorType(ShopMallEnums.ErrorType.SUCCESS.getRequestId());
			}
			
			shopBalanceDatePar.setShopNo(copyBill.getShopNo());
			shopBalanceDatePar.setMonth(copyBill.getMonth());
			shopBalanceDatePar.setId(copyBill.getShopBalanceDateId());
			shopBalanceDatePar.setBalanceFlag((byte) 2);
			shopBalanceDateService.modifyById(shopBalanceDatePar);
			
			return bill;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public BillShopBalanceDaysaleSum getConBaseDeductAmount(Map<String, Object> params) throws ManagerException {
		return billShopBalanceService.getConBaseDeductAmount(params);
	}

	/**
	 * 审核操作：审核时，将结算差异中，本期差异余额为0的数据，默认置为已完成状态
	 * 
	 * @param billShopBalance
	 *            结算单对象
	 * @return 影响的行数
	 * @throws ManagerException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int confirm(BillShopBalance billShopBalance) throws ManagerException {
		try {
			// 先修改结算单
			int count = billShopBalanceService.modifyById(billShopBalance);
			// 若是审核操作，将结算差异中，本期差异余额为0的数据，置为已完成状态
			if (count > 0 && billShopBalance.getStatus() == 2) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("balanceNo", billShopBalance.getBalanceNo());
				params.put("status", "1");
				params.put("diffBalanceIsZero", "true");
				params.put("statusNotComplete", "true");
				billShopBalanceDiffService.modifyStatus(params);
			}
			return count;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	/**
	 * 批量审核操作：审核时，将结算差异中，本期差异余额为0的数据，默认置为已完成状态
	 * 
	 * @param list
	 *            结算单集合
	 * @return 影响的行数
	 * @throws ManagerException
	 *             异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	@Override
	public int batchConfirm(List<BillShopBalance> list) throws ManagerException {
		int count = 0;
		for (BillShopBalance billShopBalance : list) {
			// 先修改结算单
			count += confirm(billShopBalance);
		}
		return count;
	}

	@Override
	public List<BillShopBalance> selectBlanceList(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billShopBalanceService.selectBlanceList(page, orderByField, orderBy, params);
	}

	@Override
	public int selectBlanceCount(Map<String, Object> params) {
		return billShopBalanceService.selectBlanceCount(params);
	}

	@Override
	public BillShopBalanceCateSum getSalesAmount(BillShopBalance billShopBalance) throws ServiceException {
		BillShopBalanceCateSum billShopBalanceCateSum;
		try {
			// 查询汇总金额
			billShopBalanceCateSum = billShopBalanceService.getSalesAmount(billShopBalance);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return billShopBalanceCateSum;
	}

	public String getSystemParamSetValue(Map<String, Object> params) throws ManagerException, ServiceException {
		List<SystemParamSet> systemParamSetList = systemParamSetService.findByBiz(null, params);
		String value = null;
		if (systemParamSetList != null && systemParamSetList.size() > 0) {
			// for(SystemParamSet systemParamSet : systemParamSetList) {
			// value = systemParamSet.getDtlValue();
			// }
			value = systemParamSetList.get(0).getDtlValue();
		}
		return value;
	}

	@Override
	public BillShopBalanceDeduct getCostDeductTypeAmount(BillShopBalance billShopBalance,
			BillShopBalanceDeduct billShopBalanceDeduct) throws ServiceException {
		Map<String, Object> deductParams = new HashMap<String, Object>();
		deductParams.put("shopNo", billShopBalance.getShopNo());
		deductParams.put("month", billShopBalance.getMonth());
		deductParams.put("startDate", billShopBalance.getBalanceStartDate());
		deductParams.put("endDate", billShopBalance.getBalanceEndDate());
		deductParams.put("costDeductType", 2);
		deductParams.put("costPayType", 1);
		deductParams.put("balanceNo", "br");

		BigDecimal deductAmount = new BigDecimal("0");
		BillShopBalanceDeduct billShopBalanceDeduct2 = billShopBalanceDeductService.getSumAmount(deductParams);
		if (billShopBalanceDeduct2 != null) {
			deductAmount = billShopBalanceDeduct2.getDeductAmount();
		}
		return billShopBalanceDeduct2;
	}

	@Override
	public List<BillShopBalance> checkBackPayTimeoutList(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billShopBalanceService.checkBackPayTimeoutList(page, orderByField, orderBy, params);
	}

	@Override
	public int checkBackPayTimeoutCount(Map<String, Object> params) {
		return billShopBalanceService.checkBackPayTimeoutCount(params);
	}

	@Override
	public List<BillShopBalance> checkMakeInvoiceTimeoutList(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billShopBalanceService.checkMakeInvoiceTimeoutList(page, orderByField, orderBy, params);
	}

	@Override
	public int checkMakeInvoiceTimeoutCount(Map<String, Object> params) {
		return billShopBalanceService.checkMakeInvoiceTimeoutCount(params);
	}

	@Override
	public List<BillShopBalance> selectSalesResultList(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billShopBalanceService.selectSalesResultList(page, orderByField, orderBy, params);
	}

	@Override
	public int selectSalesResultCount(Map<String, Object> params) {
		return billShopBalanceService.selectSalesResultCount(params);
	}

	@Override
	public List<BillShopBalance> selectSalesBackSectionSplitList(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) {
		return billShopBalanceService.selectSalesBackSectionSplitList(page, orderByField, orderBy, params);
	}

	@Override
	public int selectSalesBackSectionSplitCount(Map<String, Object> params) {
		return billShopBalanceService.selectSalesBackSectionSplitCount(params);
	}
}