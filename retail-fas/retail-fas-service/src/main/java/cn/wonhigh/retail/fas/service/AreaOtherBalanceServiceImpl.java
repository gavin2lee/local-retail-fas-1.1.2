/**  
*   
* 项目名称：retail-fas-service  
* 类名称：AreaOtherBalanceServiceImpl  
* 类描述：地区其他出库结算
* 创建人：ouyang.zm  
* 创建时间：2014-12-10 下午1:39:09  
* @version       
*/ 
package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.BalanceTypeEnums;
import cn.wonhigh.retail.fas.common.model.BillBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.common.utils.FasUtil;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.dal.database.BillAreaBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillAreaOtherBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BillBalanceMapper;
import cn.wonhigh.retail.fas.dal.database.BrandUnitMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;
@Service("areaOtherBalanceService")
public class AreaOtherBalanceServiceImpl extends BaseCrudServiceImpl implements
		AreaOtherBalanceService {
	@Resource
	private BillAreaOtherBalanceMapper billAreaOtherBalanceMapper;
	@Resource
	private BillAreaBalanceMapper billAreaBalanceMapper;
	@Resource
	private BillBalanceMapper billBalanceMapper;
	@Resource
	private BrandUnitMapper brandUnitMapper;
	@Resource
	private CommonUtilService commonUtilService;
	
	@Override
	public BaseCrudMapper init() {
		return billAreaOtherBalanceMapper;
	}
	@Override
	public int modifyBalanceBillStatus(String[] ids, Map<String,Object> params) {
		int n = 0;
		for (int i = 0; i < ids.length; i++) {
			params.put("billNo", ids[i]);
			n = billAreaBalanceMapper.updateBillStatus(params);
		}
		return n;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int deleteBalanceBill(String[] ids) throws ServiceException {
		try {
			int iCount = 0;
			for (int i = 0; i < ids.length; i++) {
				// 回写bill_sale_balance结算单编号为空
				billAreaBalanceMapper.updateSaleBalanceNoToNull(ids[i]);
				// 删除结算单
				iCount = billAreaBalanceMapper.deleteBalanceBill(ids[i]);
			}
			return iCount;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 查询存在出库明细的结算单
	 */
	@Override
	public List<BillBalance> findBalanceBillList(BillBalance billBalance) {
		List<BillBalance> list = billAreaOtherBalanceMapper.selectAreaAmogBalanceBill(billBalance);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 单个生成结算单
	 * @throws Exception 
	 */
	@Override
	public void addBalanceBill(List<BillBalance> lstBillBalance,
			BillBalance billBalance) throws Exception {
		try {
			for (BillBalance bill : lstBillBalance) {
				initBillInfo(bill, billBalance); // 设置结算单信息
				validate(bill);
				bill.setBrandUnitNo(FasUtil.formatInCondition(bill.getBrandUnitNo()));
				billAreaOtherBalanceMapper.updateSaleBalanceNo(bill);// 回写结算单编号
				bill.setBrandNo(null);
				bill.setBrandUnitNo(FasUtil.splitStr(bill.getBrandUnitNo()));
				billAreaOtherBalanceMapper.insertSelective(bill);
			}
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 保存结算单(单个操作)
	 * @param bill
	 * @param billBalance
	 * @throws Exception 
	 */
	private void initBillInfo(BillBalance bill, BillBalance fromPage) throws Exception {
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.AREA_OTHER.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getSalerNo(),null,"RO"));// 生成结算单编码
		bill.setBillName(fromPage.getBillName());
		bill.setBuyerNo(fromPage.getBuyerNo());// 客户
		bill.setBuyerName(fromPage.getBuyerName());
		bill.setSalerNo(fromPage.getSalerNo());// 公司
		bill.setSalerName(fromPage.getSalerName());
		bill.setStatus(fromPage.getStatus());	//单据状态
		bill.setBrandNo(FasUtil.splitStr(fromPage.getBrandNo()));
		bill.setBrandName(fromPage.getBrandName());
		bill.setBrandUnitNo(fromPage.getBrandUnitNo());
		if(fromPage.getBrandUnitNo().equals("")){
			bill.setBrandUnitName("");
		}else{
			bill.setBrandUnitName(fromPage.getBrandUnitName()); 
		}
		bill.setBalanceStartDate(fromPage.getBalanceStartDate());	
		bill.setBalanceEndDate(fromPage.getBalanceEndDate());
		bill.setBalanceDate(fromPage.getBalanceDate());
		bill.setCurrency(fromPage.getCurrency());
		bill.setCreateUser(fromPage.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
		bill.setUpdateUser(fromPage.getCreateUser());
		bill.setUpdateTime(DateUtil.getCurrentDateTime());
		bill.setRemark(fromPage.getRemark());
	}

	/**
	 * 计算结算单合计
	 */
	@Override
	public List<BillBalance> findTotalRow(Map<String, Object> params) {
		return billAreaOtherBalanceMapper.selectTotalRow(params);
	}

	/**
	 * 查询匹配的结算单
	 */
	@Override
	public List<BillBalance> findMatchedBalanceBill(BillBalance billBalance) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("salerNo", billBalance.getSalerNo());
		params.put("buyerNo", billBalance.getBuyerNo());
		params.put("balanceStartDate", billBalance.getBalanceStartDate());
		params.put("balanceEndDate", billBalance.getBalanceEndDate());
		params.put("brandNo", billBalance.getBrandNo());
		params.put("brandUnitNo", FasUtil.formatInQueryCondition(billBalance.getBrandUnitNo()));
		List<BillBalance> list = billAreaOtherBalanceMapper.selectMatchedBalanceBill(params);
		if(list != null && list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 保存结算单(批量操作)
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addBalanceBillBatch(List<BillBalance> billList,BillBalance b) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		BillBalance bill = null;
		for (BillBalance fromSelect : billList) {
			bill = new BillBalance();
			initCreateBillInfo(bill, fromSelect, b);
			validate(bill); // 验证
			bill.setBrandUnitNo(FasUtil.formatInCondition(bill.getBrandUnitNo()));
			billAreaOtherBalanceMapper.updateSaleBalanceNo(bill);// 回写结算单编号到出库明细
			bill.setBrandNo(null);
			bill.setBrandUnitNo(FasUtil.splitStr(bill.getBrandUnitNo()));
			billAreaOtherBalanceMapper.insertSelective(bill); // 保存
			
			list.add(bill.getBillNo());
			resultMap.put("billNos",list);
			resultMap.put("bill", bill);//默认返回第一个
			resultMap.put("flag", true);
		}
		return resultMap;
	}

	/**
	 * 保存结算单(批量操作)
	 * @param bill
	 * @param billBalance
	 * @throws Exception 
	 */
	private void initCreateBillInfo(BillBalance bill, BillBalance fromSelect,BillBalance fromPage) throws Exception {
		bill.setId(UUIDGenerator.getUUID());
		bill.setBalanceType(BalanceTypeEnums.AREA_OTHER.getTypeNo()); // 结算类型
		bill.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromSelect.getSalerNo(),null,"RO"));// 生成结算单编码
		//将查到的数据插入结算单
		bill.setSalerNo(fromSelect.getSalerNo());
		bill.setSalerName(fromSelect.getSalerName());
		bill.setBuyerNo(fromSelect.getBuyerNo());
		bill.setBuyerName(fromSelect.getBuyerName());
		bill.setOutAmount(fromSelect.getOutAmount());	
		bill.setBalanceAmount(fromSelect.getBalanceAmount());
		bill.setOutQty(fromSelect.getOutQty());
		bill.setBalanceQty(fromSelect.getBalanceQty());
	
		//将前端传来的数据插入结算单
		bill.setStatus(new Integer("0"));
		bill.setBalanceStartDate(fromPage.getBalanceStartDate());
		bill.setBalanceEndDate(fromPage.getBalanceEndDate());
		bill.setBalanceDate(fromPage.getBalanceDate());
		bill.setCreateUser(fromPage.getCreateUser());
		bill.setCreateTime(DateUtil.getCurrentDateTime());
		bill.setUpdateUser(fromPage.getCreateUser());
		bill.setUpdateTime(DateUtil.getCurrentDateTime());
		
		String brandUnitNo = fromPage.getBrandUnitNo(); 
		String brandNos = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			params.put("brandUnitNo",FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitMapper.selectBrandNos(params); // 查询有权限品牌部下的有权限品牌
			bill.setBrandNo(brandNos);
			bill.setBrandUnitNo(fromPage.getBrandUnitNo());
			bill.setBrandUnitName(fromPage.getBrandUnitName());
		} else {
			params.put("brandUnitNo", null);
			brandNos = brandUnitMapper.selectBrandNos(params); // 查询有权限的品牌部下的品牌
			bill.setBrandNo(brandNos);
			bill.setBrandUnitName("");
		}
	}

	/**
	 * 生成地区其他出库结算单(选单操作)
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> addBalanceBillBySelect(List<Object> lstItem,
			BillBalance fromPage) throws Exception {
		BillBalance billBalance=new BillBalance();
		BeanUtils.copyProperties(fromPage, billBalance);//属性复制
		billBalance.setId(UUIDGenerator.getUUID());
		billBalance.setBalanceType(BalanceTypeEnums.AREA_OTHER.getTypeNo()); // 结算类型
		billBalance.setBillNo(commonUtilService.getNewBillNoCompanyNo(fromPage.getSalerNo(),null,"RO"));// 生成结算单编码
		billBalance.setStatus(new Integer("0")); // 单据状态
		billBalance.setCreateTime(DateUtil.getCurrentDateTime());

		BigDecimal outAmount = new BigDecimal(0);// 出库金额
		/*判断和设置品牌部*/
		String brandUnitNo = billBalance.getBrandUnitNo(); 
		String brandNos = "";
		Map<String, Object> brandParams = new HashMap<String, Object>();
		if (!brandUnitNo.equals("")) {
			brandParams.put("brandUnitNo",FasUtil.formatInQueryCondition(brandUnitNo));
			brandNos = brandUnitMapper.selectBrandNos(brandParams); // 查询有权限品牌部下的有权限品牌
			billBalance.setBrandNo(brandNos);
			billBalance.setBrandUnitNo(FasUtil.formatInQueryCondition(brandUnitNo));
			billBalance.setBrandUnitName(billBalance.getBrandUnitName());
		} else {
			brandParams.put("brandUnitNo", null);
			brandNos = brandUnitMapper.selectBrandNos(brandParams); // 查询有权限的品牌部下的品牌
			billBalance.setBrandNo(brandNos);
			billBalance.setBrandUnitName("");
		}
		
		Integer sendQty=new Integer("0");
		for (Object object : lstItem) {
			BillSaleBalance sale = (BillSaleBalance)object;
			outAmount = outAmount.add(sale.getCost().multiply(new BigDecimal(sale.getSendQty())));
			sendQty += sale.getSendQty();
		}
		billBalance.setBalanceAmount(outAmount);
		billBalance.setOutAmount(outAmount);
		billBalance.setOutQty(sendQty);
		billBalance.setBalanceQty(sendQty);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("balanceNo", billBalance.getBillNo());
		if (lstItem.size() > 0 && lstItem != null) {
			for (Object object : lstItem) {
				BillSaleBalance sale = (BillSaleBalance)object;
				params.put("id",sale.getId());
				billAreaOtherBalanceMapper.updateSaleBalanceNoBySelect(params);// 反写结算单号到bill_sale_balance
			}
			//多品牌属性值设置
			billBalance.setBrandNo(null);
			billBalance.setBrandUnitNo(FasUtil.parseInQueryCondition(billBalance.getBrandUnitNo()));
			billBalanceMapper.insertSelective(billBalance);
			resultMap.put("flag", true);
			resultMap.put("bill", billBalance);
		} else {
			resultMap.put("flag", false);
		}
		return resultMap;
	}
}
