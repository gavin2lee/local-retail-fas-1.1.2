package cn.wonhigh.retail.fas.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.api.dal.BrandApiMapper;
import cn.wonhigh.retail.fas.api.dal.SettleNewStyleDtlApiMapper;
import cn.wonhigh.retail.fas.api.dal.SettlePathApiMapper;
import cn.wonhigh.retail.fas.api.dal.SettlePathDtlApiMapper;
import cn.wonhigh.retail.fas.api.dal.SupplierGroupRelApiMapper;
import cn.wonhigh.retail.fas.api.model.BillSplitSettlePathDtlModel;
import cn.wonhigh.retail.fas.api.model.SettlePathDtlModel;
import cn.wonhigh.retail.fas.api.model.SettlePathModel;
import cn.wonhigh.retail.fas.api.model.SupplierGroupRelModel;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.SettlePathDtlQueryVo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 请写出类的用途
 * 
 * @author yang.y
 * @date 2014-08-27 14:16:31
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
@Service("settlePathApiService")
class SettlePathApiServiceImpl implements SettlePathApiService {
	private Logger logger = Logger.getLogger(SettlePathApiServiceImpl.class);

	@Resource
	private SettlePathApiMapper settlePathApiMapper;

	@Resource
	private SettlePathDtlApiMapper settlePathDtlApiMapper;

	@Resource
	private SettleNewStyleDtlApiMapper settleNewStyleDtlApiMapper;

	@Resource
	private SupplierGroupRelApiMapper supplierGroupRelApiMapper;

	@Resource
	private BrandApiMapper brandApiMapper;

	private int getKey(SettlePathDtlQueryVo vo) {
		return vo.toString().intern().hashCode();
	}

	Cache<Integer, BillSplitSettlePathDtlModel> pathCache;

	<K, V> Cache<K, V> callableCached() {
		return CacheBuilder.newBuilder().maximumSize(50000)
				.expireAfterWrite(5, TimeUnit.MINUTES).build();
	}

	public SettlePathApiServiceImpl() {
		pathCache = callableCached();
	}

	@Override
	public BillSplitSettlePathDtlModel querySettlePathDtl(
			final SettlePathDtlQueryVo vo) {
		Integer key = getKey(vo);
		BillSplitSettlePathDtlModel result;
		long start = System.currentTimeMillis();

		try {
			result = pathCache.get(key,
					new Callable<BillSplitSettlePathDtlModel>() {
						@Override
						public BillSplitSettlePathDtlModel call()
								throws Exception {
							BillSplitSettlePathDtlModel item = getSettlePath(vo);
							if ("001".equals(item.getErrorCode()))
								return null;
							return item;

						}
					});

		} catch (ExecutionException e) {
			result = getSettlePath(vo);
		}
		long takes = System.currentTimeMillis() - start;
		if( result == null ){
			result = new BillSplitSettlePathDtlModel();
			result.setErrorCode("001");
			result.setErrorMsg("找不到对应的结算路径");
			logger.warn("BillSplitSettlePathDtlModel.querySettlePathDtl error:" + vo.getPathNo());
		}
		
		logger.warn("BillSplitSettlePathDtlModel.querySettlePathDtl takes: "
				+ takes + "ms");
		return result;
	}

	private BillSplitSettlePathDtlModel getSettlePath(SettlePathDtlQueryVo vo) {

		BillSplitSettlePathDtlModel billSplitSettlePathDtl = new BillSplitSettlePathDtlModel();
		String pathNo = vo.getPathNo();

		// 如果没有指定结算路径编号，则通过条件查询出编号
		if (StringUtils.isEmpty(pathNo)) {
			if (!validateParams(vo)) {
				billSplitSettlePathDtl.setErrorCode("001");
				billSplitSettlePathDtl.setErrorMsg("找不到对应的结算路径");
				return billSplitSettlePathDtl;
			}
			// 查询供应商组编码
			List<SupplierGroupRelModel> supplierGroups = supplierGroupRelApiMapper
					.selectBySupplierNo(vo.getSupplierNo());
			if (supplierGroups == null || supplierGroups.size() == 0) {
				billSplitSettlePathDtl.setErrorCode("002");
				billSplitSettlePathDtl.setErrorMsg("当前供应商没有维护供应商组，请联系财务部门维护。");
				return billSplitSettlePathDtl;
			}
			if (supplierGroups.size() > 1) {
				billSplitSettlePathDtl.setErrorCode("003");
				billSplitSettlePathDtl.setErrorMsg("当前供应商不能存在多个供应商组里，请联系管理员。");
				return billSplitSettlePathDtl;
			}
			billSplitSettlePathDtl.setSupplierGroupNo(supplierGroups.get(0)
					.getGroupNo());
			// 查询品牌所属的品牌部
			List<Brand> lstBrand = brandApiMapper
					.findByBrandNo(vo.getBrandNo());
			if (lstBrand == null || lstBrand.size() == 0) {
				billSplitSettlePathDtl.setErrorCode("004");
				billSplitSettlePathDtl.setErrorMsg("品牌[" + vo.getBrandNo()
						+ "]没有维护品牌部，请联系管理员。");
				return billSplitSettlePathDtl;
			}
			// 通过单据依据、单据类型、品牌编码、大类编码、新旧款、审核状态、启用状态、有效时间、供应商编码确定路径的唯一性
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billBasis", vo.getBillBasis());
			if (vo.getBillType() != null) {
				params.put("billType", vo.getBillType() + "");
			}
			params.put("brandUnitNo", lstBrand.get(0).getSysNo());
			if (StringUtils.isNotEmpty(vo.getCategoryNo())
					&& vo.getCategoryNo().length() >= 2) {
				params.put("categoryNo", vo.getCategoryNo().substring(0, 2));
			} else if (StringUtils.isNotEmpty(vo.getCategoryNo())) {
				params.put("categoryNo", vo.getCategoryNo());
			}
			params.put("auditStatus",
					FasAduitStatusEnum.ADUIT_STATUS.getValue());
			// 屏蔽掉按状态查询
			// params.put("status",
			// FasLogoutStatusEnum.ENABLE_STATUS.getValue());
			params.put("queryDate", DateUtil.format1(new Date()));
			params.put("supplierNo", vo.getSupplierNo());
			params.put("returnOwnFlag", vo.getReturnOwnFlag());
			List<SettlePathModel> paths = settlePathApiMapper
					.selectList(params);
			if (paths == null || paths.size() == 0) {
				billSplitSettlePathDtl.setErrorCode("004");
				billSplitSettlePathDtl.setErrorMsg("找不到对应的结算路径");
				return billSplitSettlePathDtl;
			}

			// 根据新旧款过滤
			List<SettlePathModel> resultPaths = new ArrayList<SettlePathModel>();
			for (SettlePathModel path : paths) {
				// 判断新旧款
				if ("ALL".equalsIgnoreCase(path.getStyleNo())) {
					resultPaths.add(path);
				} else {
					Map<String, Object> newStyleMap = new HashMap<String, Object>();
					newStyleMap.put("styleNo", path.getStyleNo());
					newStyleMap.put("seasonNo", vo.getSeason());
					newStyleMap.put("yearCode", vo.getYears());
					int count = settleNewStyleDtlApiMapper
							.selectCount(newStyleMap);
					if (count > 0) {
						resultPaths.add(path);
					}
				}
			}

			// 根据明细最后一级数据的公司编号过滤
			List<SettlePathModel> settlePaths = this.convertSettlePath(
					resultPaths, vo);
			if (settlePaths == null || settlePaths.size() == 0) {
				billSplitSettlePathDtl.setErrorCode("004");
				billSplitSettlePathDtl.setErrorMsg("找不到对应的结算路径");
				return billSplitSettlePathDtl;
			}
			if (settlePaths != null && settlePaths.size() > 1) {
				billSplitSettlePathDtl.setErrorCode("005");
				billSplitSettlePathDtl.setErrorMsg("存在多条结算路径");
				return billSplitSettlePathDtl;
			}

			pathNo = settlePaths.get(0).getPathNo();
		}

		// 根据结算编号查询
		List<SettlePathDtlModel> list = settlePathDtlApiMapper
				.selectByPathNo(pathNo);
		if (list == null || list.size() == 0) {
			billSplitSettlePathDtl.setErrorCode("006");
			billSplitSettlePathDtl.setErrorMsg("结算路径" + pathNo + "不存在或没有维护明细");
			return billSplitSettlePathDtl;
		}

		int maxPathOrder = list.get(list.size() - 1).getPathOrder();
		List<SettlePathDtlModel> results = new ArrayList<SettlePathDtlModel>();
		for (SettlePathDtlModel dtl : list) {
			// 如果当前对象不是最后一级，则直接将对象添加到结果集合中
			if (dtl.getPathOrder() < maxPathOrder) {
				results.add(dtl);
				continue;
			}
			// 如果找到最后一级，将当前对象添加到结果集合中，并终止循环
			if (dtl.getCompanyNo().equals(vo.getCompanyNo())) {
				results.add(dtl);
				break;
			}
		}
		if (maxPathOrder != results.size()) {
			billSplitSettlePathDtl.setErrorCode("008");
			billSplitSettlePathDtl.setErrorMsg("结算路径" + pathNo + "信息维护不完整");
			return billSplitSettlePathDtl;
		}
		billSplitSettlePathDtl.setList(results);
		billSplitSettlePathDtl.setPathNo(pathNo);
		return billSplitSettlePathDtl;
	}

	/**
	 * 转换结算路径
	 * 
	 * @param lstSettlePath
	 * @param vo
	 * @return
	 */
	private List<SettlePathModel> convertSettlePath(
			List<SettlePathModel> lstSettlePath, SettlePathDtlQueryVo vo) {
		List<SettlePathModel> lstNewSettlePath = new ArrayList<SettlePathModel>();
		if (lstSettlePath != null && lstSettlePath.size() > 0) {
			for (SettlePathModel model : lstSettlePath) {
				List<SettlePathDtlModel> list = settlePathDtlApiMapper
						.selectByPathNo(model.getPathNo());
				if (list != null && list.size() > 0) {
					int maxPathOrder = list.get(list.size() - 1).getPathOrder();
					for (SettlePathDtlModel dtlModel : list) {
						if (dtlModel.getPathOrder().intValue() == maxPathOrder
								&& dtlModel.getCompanyNo().equals(
										vo.getCompanyNo())) {
							lstNewSettlePath.add(model);
						}
					}
				}
			}
		}
		return lstNewSettlePath;
	}

	private boolean validateParams(SettlePathDtlQueryVo vo) {
		return true;
	}

	@Override
	public Integer selectRefBizType(String refBillNo) {
		return settlePathApiMapper.selectRefBizType(refBillNo);
	}
}