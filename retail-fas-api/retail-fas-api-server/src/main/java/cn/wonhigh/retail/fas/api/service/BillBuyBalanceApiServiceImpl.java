package cn.wonhigh.retail.fas.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dal.BillBuyBalanceApiMapper;
import cn.wonhigh.retail.fas.api.dal.BillSaleBalanceApiMapper;
import cn.wonhigh.retail.fas.api.dal.BrandApiMapper;
import cn.wonhigh.retail.fas.api.dal.FinancialAccountApiMapper;
import cn.wonhigh.retail.fas.api.dal.HeadquarterCostAccountingMapper;
import cn.wonhigh.retail.fas.api.dal.ItemApiMapper;
import cn.wonhigh.retail.fas.api.dal.ItemCostMapper;
import cn.wonhigh.retail.fas.api.dal.OrganApiMapper;
import cn.wonhigh.retail.fas.api.dal.PriceQuotationListMapper;
import cn.wonhigh.retail.fas.api.dal.RegionCostAccountingMapper;
import cn.wonhigh.retail.fas.api.dto.BillBalanceApiDto;
import cn.wonhigh.retail.fas.api.dto.BillHeaderApiDto;
import cn.wonhigh.retail.fas.api.utils.CommonUtils;
import cn.wonhigh.retail.fas.common.dto.ItemDto;
import cn.wonhigh.retail.fas.common.dto.OrganDto;
import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.enums.BillTypeEnums;
import cn.wonhigh.retail.fas.common.enums.BizTypeEnums;
import cn.wonhigh.retail.fas.common.enums.FasBillCodeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.model.Brand;
import cn.wonhigh.retail.fas.common.model.FinancialAccount;
import cn.wonhigh.retail.fas.common.model.ItemCost;
import cn.wonhigh.retail.fas.common.model.PriceQuotationList;
import cn.wonhigh.retail.fas.common.model.PurchasePrice;
import cn.wonhigh.retail.fas.common.model.RegionCostMaintain;
import cn.wonhigh.retail.fas.common.utils.BalanceTypeConvert;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasBillCodeConvert;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.mdm.common.model.Company;
import cn.wonhigh.retail.mps.api.client.dto.price.PriceVo;
import cn.wonhigh.retail.mps.api.client.service.price.SalePriceApi;
import cn.wonhigh.retail.pms.api.service.BillPurchaseOrderApi;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

@Service("billBuyBalanceApiService")
public class BillBuyBalanceApiServiceImpl implements BillBuyBalanceApiService {

	private Logger logger = Logger.getLogger(BillBuyBalanceApiServiceImpl.class);

	@Resource
	private BillBuyBalanceApiMapper billBuyBalanceApiMapper;

	@Resource
	private BillSaleBalanceApiMapper billSaleBalanceApiMapper;

	@Resource
	private OrganApiMapper organApiMapper;

	@Resource
	private ItemApiMapper itemApiMapper;

	@Resource
	private ItemCostMapper itemCostMapper;

	@Resource
	private RegionCostAccountingMapper regionCostAccountingMapper;

	@Resource
	private HeadquarterCostAccountingMapper headquarterCostAccountingMapper;

	@Resource
	private BrandApiMapper brandApiMapper;

	@Resource
	private PurchasePriceApiService purchasePriceApiService;

	@Resource
	private FinancialAccountApiMapper financialAccountApiMapper;

	@Resource
	private SalePriceApi salePriceApi;

	@Resource
	private PriceQuotationListMapper priceQuotationListMapper;
	
	@Resource
	private PayRelationshipApiService payRelationshipApiService;

	@Resource 
	private BillPurchaseOrderApi billPurchaseOrderApiManager;
	
	/**
	 * 采购类单据插入单据池
	 * 
	 * @param balance
	 *            BillBalanceApiDto
	 * @return int
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int insert(BillBalanceApiDto bill) throws ServiceException {
		try {
			BillBuyBalance billBuyBalance = new BillBuyBalance();
			PropertyUtils.copyProperties(billBuyBalance, bill);
			billBuyBalance.setId(UUIDGenerator.getUUID());
			billBuyBalance = this.convertBill(billBuyBalance, CommonUtils.isPEByShardingFlag(bill.getShardingFlag()));
			billBuyBalance.setActualCost(billBuyBalance.getCost());
			int count = billBuyBalanceApiMapper.insert(billBuyBalance);
			return count;
		} catch (Exception e) {
			logger.error("插入单据出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private void deleteBuyBill(List<BillBuyBalance> lstBuyBill) throws Exception{
		for (BillBuyBalance billBuyBalance : lstBuyBill) {
			billBuyBalanceApiMapper.deleteByBillNoAndItemNo(billBuyBalance.getOriginalBillNo(), billBuyBalance.getItemNo());
		}
	}
	
	private void deleteSaleBill(List<BillSaleBalance> lstSaleBill) throws Exception{
		for (BillSaleBalance billSaleBalance : lstSaleBill) {
			billSaleBalanceApiMapper.deleteByBillNoAndItemNo(billSaleBalance.getOriginalBillNo(), billSaleBalance.getItemNo());
		}
	}
	
	/**
	 * 批量插入数据
	 * 
	 * @param list
	 *            待插入的数据集合
	 * @return 插入的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int batchInsert(List<BillBalanceApiDto> list) throws ServiceException {
		cn.wonhigh.retail.mdm.common.model.Company company = null;
		
		String companyNo=null;
		try {
			List<BillBuyBalance> lstBill = new ArrayList<BillBuyBalance>();
			List<BillSaleBalance> lstSaleBill = new ArrayList<>();
			// 查询总部职能公司编号集
			String leadRoleCompanyNos = financialAccountApiMapper.selectLeadRoleCompanyNos();
			if (null == leadRoleCompanyNos) {
				leadRoleCompanyNos = "";
			}

			for (int i = 0; i < list.size(); i++) { 
				BillBalanceApiDto dto = list.get(i);
				companyNo = dto.getBuyerNo();
				
				BillBuyBalance billBuyBalance = new BillBuyBalance();
				PropertyUtils.copyProperties(billBuyBalance, dto);
				billBuyBalance.setId(UUIDGenerator.getUUID());
				// 设置默认值
				if (billBuyBalance.getCost() == null) {
					billBuyBalance.setCost(new BigDecimal(0d));
				}
				if (billBuyBalance.getBillCost() == null) {
					billBuyBalance.setBillCost(new BigDecimal(0d));
				}
				if (billBuyBalance.getBuyerNo() == null) {
					billBuyBalance.setBuyerNo("9999");
					billBuyBalance.setBuyerName("9999");
				}
				if (billBuyBalance.getSalerNo() == null) {
					billBuyBalance.setSalerNo("9999");
					billBuyBalance.setSalerName("9999");
				}
				billBuyBalance.setActualCost(billBuyBalance.getCost());
				// 跨区调货：1320 1327 1322 1328 调货入库单
				if (this.isTransferBillType(dto)) {
					billBuyBalance.setBillType(BillTypeEnums.TRANSFER_IN.getRequestId());
				}
				if (StringUtils.isNotEmpty(dto.getSendStoreNo()) && StringUtils.isEmpty(dto.getReceiveStoreNo())) {
					billBuyBalance.setReceiveStoreNo(dto.getSendStoreNo());
				}
				if (StringUtils.isNotEmpty(dto.getSendStoreName()) && StringUtils.isEmpty(dto.getReceiveStoreName())) {
					billBuyBalance.setReceiveStoreName(dto.getSendStoreName());
				}
				
				// 卖方是否为总部
				boolean salerIsHq = false;
				if (null != billBuyBalance.getSalerNo()) {
					salerIsHq = leadRoleCompanyNos.contains(billBuyBalance.getSalerNo());
				}
				// 买方是否为总部
				boolean buyerIsHq = false;
				if (null != billBuyBalance.getBuyerNo()) {
					buyerIsHq = leadRoleCompanyNos.contains(billBuyBalance.getBuyerNo());
				}

				// 处理调货入库单 1372
				billBuyBalance = this.converTransferBill(billBuyBalance, salerIsHq, buyerIsHq, CommonUtils.isPEByShardingFlag(dto.getShardingFlag()));
				// 反冲单数量取反
				if (BalanceTypeConvert.isReturnBill(billBuyBalance.getBillType(), billBuyBalance.getBizType())) {
					billBuyBalance.setSendQty(-billBuyBalance.getSendQty());
					billBuyBalance.setReceiveQty(-billBuyBalance.getReceiveQty());
					billBuyBalance.setReceiveDate(billBuyBalance.getSendDate());
				}

				// 盘差单将ref_bill_no设为null（数据库长度限制）
				if (BillTypeEnums.CHECKDIFF.getRequestId().equals(billBuyBalance.getBillType())) {
					billBuyBalance.setRefBillNo(null);
				}
				// 根据订货单位查询管理城市、大区信息
				billBuyBalance = this
						.convertBill(billBuyBalance, CommonUtils.isPEByShardingFlag(dto.getShardingFlag()));
				if (billBuyBalance == null)
					throw new ServiceException("信息不能为空");
				// 卖方是否为总部
				if (null != billBuyBalance.getSalerNo()) {
					salerIsHq = leadRoleCompanyNos.contains(billBuyBalance.getSalerNo());
				}
				// 买方是否为总部
				if (null != billBuyBalance.getBuyerNo()) {
					buyerIsHq = leadRoleCompanyNos.contains(billBuyBalance.getBuyerNo());
				}

				// 设置结算类型
				billBuyBalance.setBalanceType(BalanceTypeConvert
						.chooseBalanceType(billBuyBalance, salerIsHq, buyerIsHq));

				// 设置FAS单据类型
				if (StringUtils.isEmpty(billBuyBalance.getFasBillCode())) {
					billBuyBalance.setFasBillCode(FasBillCodeConvert.chooseFasBillCode(billBuyBalance, salerIsHq,
							buyerIsHq));
				}
				if(null != billBuyBalance.getIsSwap() && billBuyBalance.getIsSwap()){// 调换的调货出库单特殊处理
					BillSaleBalance newSaleBalance = new BillSaleBalance();
					PropertyUtils.copyProperties(newSaleBalance, billBuyBalance);
					lstSaleBill.add(newSaleBalance);
				}
				lstBill.add(billBuyBalance);
			}
			if (lstBill.size() == 0 && lstSaleBill.size() == 0)
				throw new ServiceException("没有数据");
			Company c =new Company();
			c.setCompanyNo(companyNo);
			company =  companyApi.findById(c); //CacheContext.current().getCompany(companyNo);
			if( company ==  null )
				throw new NullArgumentException("找不到买方结算公司");
			String organTypeNo = company.getOrganTypeNo();
			int count = 0;
			if(null != lstBill.get(0).getIsSwap() && lstBill.get(0).getIsSwap()){
				if (lstSaleBill != null && lstSaleBill.size() > 0) {
					this.deleteSaleBill(lstSaleBill);
					count = billSaleBalanceApiMapper.batchInsert(company.getOrganTypeNo(), lstSaleBill);
					billBuyBalanceApiMapper.updateBuyTransferSendDate(lstSaleBill.get(0).getOriginalBillNo()
							,lstSaleBill.get(0).getSendDate());
				}
			}else{
				this.deleteBuyBill(lstBill);
				count = billBuyBalanceApiMapper.batchInsert(organTypeNo,lstBill);
				if (lstSaleBill != null && lstSaleBill.size() > 0) {
					this.deleteSaleBill(lstSaleBill);
					count = billSaleBalanceApiMapper.batchInsert(company.getOrganTypeNo(), lstSaleBill);
				}
			}
			if(count > 0){
				BillBalanceApiDto lastDto = list.get(list.size()-1);
				if(null == lastDto.getIsSplit()){
					String hqCompanyNo = financialAccountApiMapper.selectLeadRoleCompanyNos();
					if(CommonUtils.isNeedHanderPEValence(lastDto.getBuyerNo(), hqCompanyNo, lastDto)){
						payRelationshipApiService.handerPEValence(lastDto);
					}
				}
			}
			return count;
		} catch (Exception e) {
			logger.error("批量插入单据出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Resource
	private cn.wonhigh.retail.mdm.api.CompanyApi companyApi;
 
	/**
	 * 判断单据是否是跨区调货类型
	 * 
	 * @param billBalanceApiDto
	 *            BillBalanceApiDto对象
	 * @return true or false
	 */
	private boolean isTransferBillType(BillBalanceApiDto billBalanceApiDto) {
		if (billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_SHOP_SHOP.getRequestId().intValue()
				|| billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_STORE_STORE.getRequestId()
						.intValue()
				|| billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_SHOP_STORE.getRequestId()
						.intValue()
				|| billBalanceApiDto.getBillType().intValue() == BillTypeEnums.REGION_STORE_SHOP.getRequestId()
						.intValue()) {
			return true;
		}
		return false;
	}

	/**
	 * 调货入库单处理
	 * 
	 * @param billBalance
	 * @return
	 * @throws ServiceException
	 */
	private BillBuyBalance converTransferBill(BillBuyBalance billBalance, boolean salerIsHq, boolean buyerIsHq,boolean isPE)
			throws ServiceException {

		boolean flag = false;

		// 调货出库单 1371 地区至总部
		if (BillTypeEnums.TRANSFER_IN.getRequestId().equals(billBalance.getBillType())
				&& (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType()) 
						|| BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType()) 
						|| BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType()) 
						|| BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())
					) && !salerIsHq && buyerIsHq) {
			flag = true;

			if (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType()) 
					|| BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType()) 
					|| BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())
				) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13720404.getCode());
			} else if (BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13720504.getCode());
			}

			try {
				// 根据单号查询对应的出库单
				BillSaleBalance billSaleBalance = null;
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("originalBillNo", billBalance.getOriginalBillNo());
				params.put("itemNo", billBalance.getItemNo());

				billSaleBalance = billSaleBalanceApiMapper.selectRefBill(params);

				// 单号未查出则根据源单号查询对应的出库单
				if (billSaleBalance == null) {
					Map<String, Object> paramsRef = new HashMap<String, Object>();
					paramsRef.put("refBillNo", billBalance.getRefBillNo());
					paramsRef.put("itemNo", billBalance.getItemNo());
					billSaleBalance = billSaleBalanceApiMapper.selectRefBill(paramsRef);
				}

				// 如果有查询到出库单,并且卖方一致,则不对调买卖双方
				if (billSaleBalance != null && billSaleBalance.getSalerNo().equals(billBalance.getSalerNo())) {
					flag = false;
					if (FasBillCodeEnums.FG13710403.getCode().equals(billSaleBalance.getFasBillCode())) {
						billBalance.setFasBillCode(FasBillCodeEnums.FG13720403.getCode());
					} else if (FasBillCodeEnums.FG13710503.getCode().equals(billSaleBalance.getFasBillCode())) {
						billBalance.setFasBillCode(FasBillCodeEnums.FG13720503.getCode());
					}
				}

			} catch (Exception e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}
		if (isPE) {
			if (BillTypeEnums.TRANSFER_IN.getRequestId().equals(billBalance.getBillType())
					&& (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType()) || BizTypeEnums.DIFFERENCE
							.getStatus().equals(billBalance.getBizType())) && !salerIsHq && buyerIsHq) {
				if (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())) {
					billBalance.setFasBillCode(FasBillCodeEnums.FG13720403.getCode());
				} else if (BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
					billBalance.setFasBillCode(FasBillCodeEnums.FG13720503.getCode());
				}
			}
			return billBalance;
		}
		if (flag) {
			String buyerNo = billBalance.getBuyerNo();
			String buyerName = billBalance.getBuyerName();
			String receiveStoreNo = billBalance.getReceiveStoreNo();
			String receiveStoreName = billBalance.getReceiveStoreName();
			String orderUnitNo = billBalance.getOrderUnitNo();
			String orderUnitName = billBalance.getOrderUnitName();

			// 设置买方
			billBalance.setBuyerNo(billBalance.getSalerNo());
			billBalance.setBuyerName(billBalance.getSalerName());
			billBalance.setReceiveStoreNo(billBalance.getSendStoreNo());
			billBalance.setReceiveStoreName(billBalance.getSendStoreName());
			billBalance.setOrderUnitNo(billBalance.getOrderUnitNoFrom());
			billBalance.setOrderUnitName(billBalance.getOrderUnitNameFrom());

			// 设置卖方
			billBalance.setSalerNo(buyerNo);
			billBalance.setSalerName(buyerName);
			billBalance.setSendStoreNo(receiveStoreNo);
			billBalance.setSendStoreName(receiveStoreName);
			billBalance.setOrderUnitNoFrom(orderUnitNo);
			billBalance.setOrderUnitNameFrom(orderUnitName);

			// 数量取反
			billBalance.setSendQty(-billBalance.getSendQty());
			billBalance.setReceiveQty(-billBalance.getReceiveQty());
			
			// 对调的调货入库单特殊处理
			Date sendDate = billBalance.getSendDate();
			Date receiveDate = billBalance.getReceiveDate();
			Integer sendQty = billBalance.getSendQty();
			Integer receiveQty = billBalance.getReceiveQty();
			billBalance.setSendDate(receiveDate);
			billBalance.setReceiveDate(sendDate);
			billBalance.setSendQty(receiveQty);
			billBalance.setReceiveQty(sendQty);
			billBalance.setBillType(BillTypeEnums.TRANSFER_OUT.getRequestId());
			billBalance.setIsSwap(true);
			if (BizTypeEnums.TRANSFERCARGO.getStatus().equals(billBalance.getBizType())
					|| BizTypeEnums.TRANSFERETURN.getStatus().equals(billBalance.getBizType()) 
					|| BizTypeEnums.TRANSFERETURNDIFF.getStatus().equals(billBalance.getBizType())) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13710404.getCode());
			} else if (BizTypeEnums.DIFFERENCE.getStatus().equals(billBalance.getBizType())) {
				billBalance.setFasBillCode(FasBillCodeEnums.FG13710504.getCode());
			}
		}

		return billBalance;
	}

	/**
	 * 组装单据对象(批量插入)
	 * 
	 * @param billBuyBalance
	 *            BillBuyBalance对象
	 * @param firstBill
	 *            第一个单据对象
	 * @param isFirst
	 *            当前对象是否是第一个对象 （如果是，则查询，如果不是，则复制第一个对象查询出的结果）
	 * @return 组装后的单据对象
	 * @throws Exception
	 *             异常
	 */
	private BillBuyBalance convertBill(BillBuyBalance billBuyBalance, boolean isPE) throws Exception {

		// 冗余商品相关信息
		List<ItemDto> lstItem = itemApiMapper.findByItemNo(billBuyBalance.getItemNo());
		if (lstItem != null && lstItem.size() > 0) {
			ItemDto itemDto = lstItem.get(0);
			if (StringUtils.isEmpty(billBuyBalance.getSupplierNo())) {
				billBuyBalance.setSupplierNo(itemDto.getSupplierNo());
				billBuyBalance.setSupplierName(itemDto.getSupplierName());
			}
			billBuyBalance.setOrderfrom(itemDto.getOrderfrom());
			billBuyBalance.setGender(itemDto.getGender());
			billBuyBalance.setYears(itemDto.getYears());
			billBuyBalance.setSeason(itemDto.getSellSeason());
			billBuyBalance.setTagPrice(itemDto.getTagPrice());
			billBuyBalance.setBrandNo(itemDto.getBrandNo());
			billBuyBalance.setBrandName(itemDto.getBrandName());
			billBuyBalance.setCategoryNo(itemDto.getCategoryNo());
			billBuyBalance.setCategoryName(itemDto.getCategoryName());

		}

		// 设置品牌部
		List<Brand> lstBrand = brandApiMapper.findByBrandNo(billBuyBalance.getBrandNo());
		if (lstBrand != null && lstBrand.size() > 0) {
			billBuyBalance.setBrandUnitNo(lstBrand.get(0).getSysNo());
			billBuyBalance.setBrandUnitName(lstBrand.get(0).getBrandUnitName());
		}

		// 冗余管理城市、大区
		if (StringUtils.isNotEmpty(billBuyBalance.getOrderUnitNo())) {
			List<OrganDto> list = organApiMapper.selectByOrderUnitNo(billBuyBalance.getOrderUnitNo());
			if (list != null && list.size() > 0) {
				billBuyBalance.setOrganNo(list.get(0).getOrganNo());
				billBuyBalance.setOrganName(list.get(0).getName());
				billBuyBalance.setZoneNo(list.get(0).getZoneNo());
				billBuyBalance.setZoneName(list.get(0).getZoneName());
			}
		}
		if (StringUtils.isNotEmpty(billBuyBalance.getOrderUnitNoFrom())) {
			List<OrganDto> lstFrom = organApiMapper.selectByOrderUnitNo(billBuyBalance.getOrderUnitNoFrom());
			if (lstFrom != null && lstFrom.size() > 0) {
				billBuyBalance.setOrganNoFrom(lstFrom.get(0).getOrganNo());
				billBuyBalance.setOrganNameFrom(lstFrom.get(0).getName());
				billBuyBalance.setZoneNoFrom(lstFrom.get(0).getZoneNo());
				billBuyBalance.setZoneNameFrom(lstFrom.get(0).getZoneName());
			}
		}

		// 查询采购价、物料价、厂进价
		PurchasePrice purchasePrice = purchasePriceApiService.findBillPurchasePrice(billBuyBalance.getItemNo(),
				billBuyBalance.getSupplierNo(), billBuyBalance.getSendDate());
		if (purchasePrice != null && purchasePrice.getPurchasePrice() != null) {
			billBuyBalance.setPurchasePrice(purchasePrice.getPurchasePrice());
		} else {
			billBuyBalance.setPurchasePrice(BigDecimal.ZERO);
		}
		if (purchasePrice != null && purchasePrice.getMaterialPrice() != null) {
			billBuyBalance.setMaterialPrice(purchasePrice.getMaterialPrice());
		} else {
			billBuyBalance.setMaterialPrice(BigDecimal.ZERO);
		}
		if (purchasePrice != null && purchasePrice.getFactoryPrice() != null) {
			billBuyBalance.setFactoryPrice(purchasePrice.getFactoryPrice());
		} else {
			billBuyBalance.setFactoryPrice(BigDecimal.ZERO);
		}

		if (isPE) {
			return billBuyBalance;
		}

		// 查询牌价
		try {
			String organNoParam = billBuyBalance.getOrganNo();
			// 如果收方管理城市为空,则取发方
			if (StringUtils.isEmpty(organNoParam)) {
				organNoParam = billBuyBalance.getOrganNoFrom();
			}
			// PriceVo price = salePriceApi.getItemTagPriceByParam(2,
			// organNoParam, new
			// cn.wonhigh.retail.mps.api.client.dto.price.ItemDto(billBuyBalance.getItemNo()));
			PriceVo price = salePriceApi.getItemTagPriceByParam(organNoParam,
					new cn.wonhigh.retail.mps.api.client.dto.price.ItemDto(billBuyBalance.getItemNo()));
			if (price != null && null != price.getTagPrice()) {
				billBuyBalance.setTagPrice(price.getTagPrice());
			} else {
				billBuyBalance.setTagPrice(BigDecimal.ZERO);
			}
		} catch (Exception e) {
			logger.error("从MPS获取牌价出错:" + e.getMessage(), e);
			billBuyBalance.setTagPrice(BigDecimal.ZERO);
			throw new Exception(e.getMessage(), e);
		}

		// 查询全国统一牌价
		Map<String, Object> priceQuotationListParams = new HashMap<String, Object>();
		priceQuotationListParams.put("organId", "0000");
		priceQuotationListParams.put("itemNo", billBuyBalance.getItemNo());
		PriceQuotationList priceQuotationList = priceQuotationListMapper
				.getPriceQuotationList(priceQuotationListParams);
		if (priceQuotationList != null && priceQuotationList.getTagPrice() != null) {
			billBuyBalance.setTagPriceNation(priceQuotationList.getTagPrice());
		} else {
			billBuyBalance.setTagPriceNation(BigDecimal.ZERO);
		}

		// 查询买方地区价
		if (billBuyBalance.getZoneNo() != null) {
			String priceZoneNo = null;
			// 查询价格大区
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", billBuyBalance.getBuyerNo());
			params.put("status", 1);
			List<FinancialAccount> financialAccounts = financialAccountApiMapper.selectByParams(params);
			if (!CollectionUtils.isEmpty(financialAccounts)) {
				priceZoneNo = financialAccounts.get(0).getPriceZone();
			}
			if (StringUtils.isEmpty(priceZoneNo)) {
				priceZoneNo = billBuyBalance.getZoneNo();
			}

			Map<String, Object> buyerRegionCostparams = new HashMap<String, Object>();
			buyerRegionCostparams.put("zoneNo", priceZoneNo);
			buyerRegionCostparams.put("itemNo", billBuyBalance.getItemNo());
			buyerRegionCostparams.put("effectiveDate", billBuyBalance.getSendDate());
			RegionCostMaintain buyerRegionCost = regionCostAccountingMapper.findRegionCost(buyerRegionCostparams);
			if (buyerRegionCost != null && buyerRegionCost.getRegionCost() != null) {
				billBuyBalance.setRegionCost(buyerRegionCost.getRegionCost());
			} else {
				billBuyBalance.setRegionCost(BigDecimal.ZERO);
			}
		} else {
			billBuyBalance.setRegionCost(BigDecimal.ZERO);
		}

		// 查询卖方地区价
		if (billBuyBalance.getZoneNoFrom() != null) {
			String priceZoneNo = null;
			// 查询价格大区
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyNo", billBuyBalance.getSalerNo());
			params.put("status", 1);
			List<FinancialAccount> financialAccounts = financialAccountApiMapper.selectByParams(params);
			if (!CollectionUtils.isEmpty(financialAccounts)) {
				priceZoneNo = financialAccounts.get(0).getPriceZone();
			}
			if (StringUtils.isEmpty(priceZoneNo)) {
				priceZoneNo = billBuyBalance.getZoneNoFrom();
			}

			Map<String, Object> salerRegionCostparams = new HashMap<String, Object>();
			salerRegionCostparams.put("zoneNo", priceZoneNo);
			salerRegionCostparams.put("itemNo", billBuyBalance.getItemNo());
			salerRegionCostparams.put("effectiveDate", billBuyBalance.getSendDate());
			RegionCostMaintain salerRegionCost = regionCostAccountingMapper.findRegionCost(salerRegionCostparams);
			if (salerRegionCost != null && salerRegionCost.getRegionCost() != null) {
				billBuyBalance.setRegionCostSecond(salerRegionCost.getRegionCost());
			} else {
				billBuyBalance.setRegionCostSecond(BigDecimal.ZERO);
			}
		} else {
			billBuyBalance.setRegionCostSecond(BigDecimal.ZERO);
		}

		// 查询买方单位成本
		Map<String, Object> buyerItemCostParams = new HashMap<String, Object>();
		buyerItemCostParams.put("companyNo", billBuyBalance.getBuyerNo());
		buyerItemCostParams.put("itemNo", billBuyBalance.getItemNo());
		buyerItemCostParams.put("date", billBuyBalance.getSendDate());
		ItemCost buyerItemCost = itemCostMapper.getItemCost(buyerItemCostParams);
		if (buyerItemCost != null && buyerItemCost.getUnitCost() != null) {
			billBuyBalance.setUnitCost(buyerItemCost.getUnitCost());
		} else {
			// 未取到单位成本取地区价
			billBuyBalance.setUnitCost(billBuyBalance.getUnitCost());
		}

		// 查询卖方单位成本
		Map<String, Object> salerItemCostParams = new HashMap<String, Object>();
		salerItemCostParams.put("companyNo", billBuyBalance.getSalerNo());
		salerItemCostParams.put("itemNo", billBuyBalance.getItemNo());
		salerItemCostParams.put("date", billBuyBalance.getSendDate());
		ItemCost salerItemCost = itemCostMapper.getItemCost(salerItemCostParams);
		if (salerItemCost != null && salerItemCost.getUnitCost() != null) {
			billBuyBalance.setUnitCostSecond(salerItemCost.getUnitCost());
		} else {
			// 未取到单位成本取地区价
			billBuyBalance.setUnitCostSecond(billBuyBalance.getUnitCostSecond());
		}

		// 查询总部价
		Map<String, Object> headquarterCostParams = new HashMap<String, Object>();
		headquarterCostParams.put("itemNo", billBuyBalance.getItemNo());
		headquarterCostParams.put("effectiveDate", billBuyBalance.getSendDate());
		BigDecimal headquarterCost = headquarterCostAccountingMapper.findHeadquarterCost(headquarterCostParams);
		if (headquarterCost != null) {
			billBuyBalance.setHeadquarterCost(headquarterCost);
		} else {
			billBuyBalance.setHeadquarterCost(BigDecimal.ZERO);
		}

		return billBuyBalance;
	}

	/**
	 * 修改接受数量
	 * 
	 * @param billBalanceApiDto
	 *            BillBalanceApiDto
	 * @return 修改的记录数
	 * @throws Exception
	 *             异常
	 */
	/*
	 * private int modifyReceiveQty(BillBalanceApiDto billBalanceApiDto) throws
	 * Exception { if(StringUtils.isEmpty(billBalanceApiDto.getBillNo()) ||
	 * billBalanceApiDto.getBillType() == null) { return 0; } Map<String,
	 * Object> params = new HashMap<String, Object>(); params.put("billNo",
	 * billBalanceApiDto.getOriginalBillNo()); params.put("itemNo",
	 * billBalanceApiDto.getItemNo()); // 验收单
	 * if(BillTypeEnums.RECEIPT.getRequestId().intValue() ==
	 * billBalanceApiDto.getBillType().intValue()) { params.put("refBillNo",
	 * billBalanceApiDto.getRefBillNo()); params.put("refBillType",
	 * BillTypeEnums.ASN.getRequestId()); params.put("billType",
	 * BillTypeEnums.RECEIPT.getRequestId());
	 * billBuyBalanceApiMapper.modifyBillAsnReceiveQty(params); return
	 * billSaleBalanceApiMapper.modifyBillAsnReceiveQty(params); } // 跨区调货入库单
	 * if(this.isTransferBillType(billBalanceApiDto)) {
	 * params.put("refBillType", BillTypeEnums.TRANSFER_OUT.getRequestId());
	 * params.put("billType", BillTypeEnums.TRANSFER_IN.getRequestId()); return
	 * billBuyBalanceApiMapper.modifyBillTransferReceiveQty(params); } return 0;
	 * }
	 */
	/**
	 * 通过原单据编码查询单据池中相关单据的记录数
	 * 
	 * @param refBillNo
	 *            原单据编码
	 * @return 记录数
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int selectCountByRefBillNo(String refBillNo) throws ServiceException {
		try {
			int count = billBuyBalanceApiMapper.selectCountByRefBillNo(refBillNo);
			return count;
		} catch (Exception e) {
			logger.error("通过单据编码查询拆单的单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过单据编码删除为结算的数据
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 删除的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int deleteByBillNo(String billNo) throws ServiceException {
		try {
			int count = billBuyBalanceApiMapper.deleteByBillNo(billNo);
			return count;
		} catch (Exception e) {
			logger.error("通过单据编码删除单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过单据编码删除为结算的数据
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 删除的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int deleteByBillNoAndItemNo(String billNo, String itemNo) throws ServiceException {
		try {
			int count = billBuyBalanceApiMapper.deleteByBillNoAndItemNo(billNo, itemNo);
			return count;
		} catch (Exception e) {
			logger.error("通过单据编码及货号删除单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过单据编码查询已结算单据的数量
	 * 
	 * @param billNo
	 *            单据编码
	 * @return 数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int selectCountByBillNo(String billNo) throws ServiceException {
		try {
			return billBuyBalanceApiMapper.selectCountByBillNo(billNo);
		} catch (Exception e) {
			logger.error("通过单据编码查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 作废单据
	 * 
	 * @param billHeaderApiDto
	 *            参数条件
	 * @return 作废的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int invalid(BillHeaderApiDto billHeaderApiDto) throws ServiceException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("billNo", billHeaderApiDto.getBillNo());
			params.put("billType", billHeaderApiDto.getBillType());
			params.put("bizType", billHeaderApiDto.getBizType());
			int count = billBuyBalanceApiMapper.invalid(params);
			return count;
		} catch (Exception e) {
			logger.error("作废单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 通过map参数，查询数量
	 * 
	 * @param params
	 *            参训参数
	 * @return 查询到的数量
	 * @throws ServiceException
	 *             异常
	 */
	@Override
	public int selectCountByParams(Map<String, Object> params) throws ServiceException {
		try {
			int count = billBuyBalanceApiMapper.selectCountByParams(params);
			return count;
		} catch (Exception e) {
			logger.error("通过selectCountByParams方法查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectBillBuyBalanceCount(Map<String, Object> params) throws ServiceException {
		try {
			int count = billBuyBalanceApiMapper.selectBillBuyBalanceCount(params);
			return count;
		} catch (Exception e) {
			logger.error("通过selectBillBuyBalanceCount方法查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> selectBillBuyBalanceInfo(SimplePage page, String orderByField, String orderBy,
			Map<String, Object> params) throws ServiceException {
		try {
			List<BillBuyBalance> list = billBuyBalanceApiMapper.selectBillBuyBalanceInfo(page, orderByField, orderBy,
					params);
			return list;
		} catch (Exception e) {
			logger.error("通过selectBillBuyBalanceCount方法查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> selectBillBuyBalanceFooter(Map<String, Object> params) throws ServiceException {
		try {
			List<BillBuyBalance> list = billBuyBalanceApiMapper.selectBillBuyBalanceFooter(params);
			return list;
		} catch (Exception e) {
			logger.error("通过selectBillBuyBalanceFooter方法查询单据数量时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public String selectRefPathNo(Map<String, Object> params) throws ServiceException {
		try {
			String result = billBuyBalanceApiMapper.selectRefPathNo(params);
			return result;
		} catch (Exception e) {
			logger.error("根据源单编号和货号查询源单的结算路径编码:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int queryReportCount(Map<String, Object> params) throws ServiceException {
		try {
			return billBuyBalanceApiMapper.findReportCount(params);
		} catch (Exception e) {
			logger.error(":" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryReportByPage(Map<String, Object> params, SimplePage page)
			throws ServiceException {
		try {
			List<ReportDto> rowList = billBuyBalanceApiMapper.findRowByPage(page, params);
			List<ReportDto> qtyList = billBuyBalanceApiMapper.findQtyByPage(page, params);
			List<Map<String, Object>> resuList = new ArrayList<Map<String, Object>>();
			for (ReportDto rowDto : rowList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("salerNo", rowDto.getSalerNo());
				map.put("salerName", rowDto.getSalerName());
				map.put("brandNo", rowDto.getBrandNo());
				map.put("brandName", rowDto.getBrandName());
				map.put("oneLevelCategoryNo", rowDto.getOneLevelCategoryNo());
				map.put("oneLevelCategoryName", rowDto.getOneLevelCategoryName());
				map.put("itemNo", rowDto.getItemNo());
				map.put("itemCode", rowDto.getItemCode());
				map.put("itemName", rowDto.getItemName());

				map.put("orderfromName", rowDto.getOrderfromName());
				map.put("yeasName", rowDto.getYearsName());
				map.put("purchaseSeasonName", rowDto.getPurchaseSeasonName());
				map.put("seasonName", rowDto.getSeasonName());
				map.put("genderName", rowDto.getGenderName());
				map.put("twoLevelCategoryName", rowDto.getTwoLevelCategoryName());
				map.put("threeLevelCategoryName", rowDto.getThreeLevelCategoryName());
				map.put("sendDate", DateUtil.format1(rowDto.getSendDate()));

				for (ReportDto qtyDto : qtyList) {
					if (rowDto.getSalerNo().equals(qtyDto.getSalerNo())
							&& rowDto.getItemNo().equals(qtyDto.getItemNo())
							&& (null == rowDto.getSendDate() || rowDto.getSendDate().equals(qtyDto.getSendDate()))) {
						map.put(qtyDto.getOrderUnitNo(), qtyDto.getSendQty());
						if (null != map.get("sendAmount")) {
							BigDecimal sendAmount = (BigDecimal) map.get("sendAmount");
							map.put("sendAmount", sendAmount.add(qtyDto.getSendAmount()));
						} else {
							map.put("purchasePrice", qtyDto.getPurchasePrice());
							map.put("factoryPrice", qtyDto.getFactoryPrice());
							map.put("sendAmount", qtyDto.getSendAmount());
						}
						if (null != map.get("totalQty")) {
							Integer totalQty = (Integer) map.get("totalQty");
							map.put("totalQty", totalQty + qtyDto.getSendQty());
						} else {
							map.put("totalQty", qtyDto.getSendQty());
						}
					}
				}
				resuList.add(map);
			}
			return resuList;
		} catch (Exception e) {
			logger.error(":" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryReportFooter(Map<String, Object> params) throws ServiceException {
		try {
			List<ReportDto> footerDto = billBuyBalanceApiMapper.findReportFooter(params);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("buyerName", "合计");
			int totalQty = 0;
			BigDecimal totalAmount = new BigDecimal(0);
			for (ReportDto reportDto : footerDto) {
				map.put(reportDto.getOrderUnitNo(), reportDto.getSendQty());
				totalQty += reportDto.getSendQty();
				totalAmount = totalAmount.add(reportDto.getSendAmount());
			}
			map.put("totalQty", totalQty);
			map.put("sendAmount", totalAmount);
			List<Map<String, Object>> resuList = new ArrayList<Map<String, Object>>();
			resuList.add(map);
			return resuList;
		} catch (Exception e) {
			logger.error(":" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> queryReportColumn(Map<String, Object> params) throws ServiceException {
		try {
			List<ReportDto> lstItem = billBuyBalanceApiMapper.findColumnByParams(params);
			if (lstItem.size() > 0) {
				Map<String, Object> obj = new HashMap<String, Object>();
				Map<String, Integer> zoneData = new HashMap<String, Integer>();
				Map<String, Integer> organData = new HashMap<String, Integer>();
				List<ReportDto> lstZone = new ArrayList<ReportDto>();
				List<ReportDto> lstOrgan = new ArrayList<ReportDto>();
				for (ReportDto reportDto : lstItem) {
					if (null != zoneData.get(reportDto.getZoneNo())) {
						Integer index = zoneData.get(reportDto.getZoneNo());
						zoneData.put(reportDto.getZoneNo(), index + 1);
					} else {
						zoneData.put(reportDto.getZoneNo(), 1);
						ReportDto zoneDto = new ReportDto();
						zoneDto.setZoneNo(reportDto.getZoneNo());
						zoneDto.setZoneName(reportDto.getZoneName());
						lstZone.add(zoneDto);
					}
					if (null != organData.get(reportDto.getOrganNo())) {
						Integer index = organData.get(reportDto.getOrganNo());
						organData.put(reportDto.getOrganNo(), index + 1);
					} else {
						organData.put(reportDto.getOrganNo(), 1);
						ReportDto organDto = new ReportDto();
						organDto.setOrganNo(reportDto.getOrganNo());
						organDto.setOrganName(reportDto.getOrganName());
						lstOrgan.add(organDto);
					}
				}
				for (ReportDto zoneDto : lstZone) {
					zoneDto.setIndex(zoneData.get(zoneDto.getZoneNo()));
				}
				for (ReportDto organDto : lstOrgan) {
					organDto.setIndex(organData.get(organDto.getOrganNo()));
				}
				obj.put("orderUnitColumn", lstItem);
				obj.put("organColumn", lstOrgan);
				obj.put("zoneColumn", lstZone);
				return obj;
			}
			return null;
		} catch (Exception e) {
			logger.error(":" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> selectByRefBillNo(String refBillNo) throws ServiceException {
		try {
			List<BillBuyBalance> list = billBuyBalanceApiMapper.selectByRefBillNo(refBillNo);
			return list;
		} catch (Exception e) {
			logger.error("根据源单编号查询单据时出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BigDecimal findRegionCost(BillBalanceApiDto dto) throws ServiceException {
		try {
			return billBuyBalanceApiMapper.findRegionCost(dto);
		} catch (Exception e) {
			logger.error("根据BillBalanceApiDto计算地区价出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int selectIsSplitCount(String refBillNo) throws ServiceException {
		try {
			return billBuyBalanceApiMapper.selectIsSplitCount(refBillNo);
		} catch (Exception e) {
			logger.error("查询拆单数量出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Integer selectRefBizType(String refBillNo) throws ServiceException {
		try {
			return billBuyBalanceApiMapper.selectRefBizType(refBillNo);
		} catch (Exception e) {
			logger.error("查询关联单据类型出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Integer selectIsWholesaleCount(String orderUnitNo)
			throws ServiceException {
		try {
			return billBuyBalanceApiMapper.selectIsWholesaleCount(orderUnitNo);
		} catch (Exception e) {
			logger.error("查询批发单据出错:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
}
