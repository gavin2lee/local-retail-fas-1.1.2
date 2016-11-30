package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceApply;
import cn.wonhigh.retail.fas.common.model.BillBalanceInvoiceDtl;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceApplyService;
import cn.wonhigh.retail.fas.service.BillBalanceInvoiceDtlService;

import com.yougou.logistics.base.common.enums.CommonOperatorEnum;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-01-07 16:30:58
 * @version 1.0.0
 * @copyright (C) 2013 WonHigh Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the WonHigh technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */
@Service("billBalanceInvoiceDtlManager")
class BillBalanceInvoiceDtlManagerImpl extends BaseCrudManagerImpl implements BillBalanceInvoiceDtlManager {
	
    @Resource
    private BillBalanceInvoiceDtlService billBalanceInvoiceDtlService;
    
    @Resource
    private BillBalanceInvoiceApplyService billBalanceInvoiceApplyService;

    @Override
    public BaseCrudService init() {
        return billBalanceInvoiceDtlService;
    }
    
    /**
     * 新增/修改操作
     * @param params Map<CommonOperatorEnum, List<BillBalanceInvoiceDtl>>
     * @return 影响的记录数
     * @throws ManagerException 异常
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ManagerException.class)
	public int saveAll(Map<CommonOperatorEnum, List<BillBalanceInvoiceDtl>> dataMap)
			throws ManagerException {
		try {
			int count = 0;
			List<BillBalanceInvoiceDtl> delList = dataMap.get(CommonOperatorEnum.DELETED);
			if(null != delList && delList.size() > 0) {
				for(BillBalanceInvoiceDtl modelType : delList) {
					count += this.billBalanceInvoiceDtlService.deleteById(modelType);
				}
			}
			List<BillBalanceInvoiceDtl> updateList = dataMap.get(CommonOperatorEnum.UPDATED);
			if(null != updateList && updateList.size() > 0) {
				for(BillBalanceInvoiceDtl modelType : updateList) {
					//count += this.billBalanceInvoiceDtlService.modifyById(modelType);
/*					if(StringUtils.isNotEmpty(modelType.getBalanceType()) && Integer.parseInt(modelType.getBalanceType()) == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){
						modelType.setBalanceNo("");
						count += this.billBalanceInvoiceDtlService.modifyById(modelType);
					}else{
						count += this.billBalanceInvoiceDtlService.modifyById(modelType);
					}*/
					modelType.setBalanceNo("");
					count += this.billBalanceInvoiceDtlService.modifyById(modelType);
				}
			}
			List<BillBalanceInvoiceDtl> insertList = dataMap.get(CommonOperatorEnum.INSERTED);
			if(null != insertList && insertList.size() > 0) {
				BillBalanceInvoiceApply apply = new BillBalanceInvoiceApply();
				apply.setBillNo(insertList.get(0).getBillNo());
				BillBalanceInvoiceApply billBalanceInvoiceApply = billBalanceInvoiceApplyService.findById(apply);
				for(BillBalanceInvoiceDtl modelType : insertList) {
/*					if(StringUtils.isNotEmpty(modelType.getBalanceType()) && Integer.parseInt(modelType.getBalanceType()) == BalanceTypeEnums.AREA_GROUP_BUY.getTypeNo()){
						modelType.setBalanceNo("");
						this.billBalanceInvoiceDtlService.add(modelType);
					}else{
						this.billBalanceInvoiceDtlService.add(modelType);
					}*/
					modelType.setShardingFlag(billBalanceInvoiceApply.getShardingFlag());
					modelType.setBalanceNo("");
					modelType.setId(UUIDGenerator.getUUID());
					this.billBalanceInvoiceDtlService.add(modelType);
				}
			}
			return count;
		} catch(ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBalanceInvoiceDtl> selectByGroupByParams(Map<String, Object> params) {
		return billBalanceInvoiceDtlService.selectByGroupByParams(params);
	}

	@Override
	public List<BillBalanceInvoiceDtl> selectByGroupByParamExport(
			Map<String, Object> params) {
		//实际扣率=（终端收入金额-金额）/终端收入金额*100
		List<BillBalanceInvoiceDtl>  BillBalanceInvoiceDtlList = billBalanceInvoiceDtlService.selectByGroupByParamExport(params);
		/*for(BillBalanceInvoiceDtl billBalanceInvoiceDtl:BillBalanceInvoiceDtlList){
			BigDecimal posEarningAmount=billBalanceInvoiceDtl.getPosEarningAmount();
			BigDecimal sendAmount=billBalanceInvoiceDtl.getSendAmount();
			BigDecimal actualRate=(posEarningAmount.subtract(sendAmount)).divide(posEarningAmount).multiply(new BigDecimal(100));
			billBalanceInvoiceDtl.setActualRate(actualRate);
		}*/
		return BillBalanceInvoiceDtlList;
	}
}