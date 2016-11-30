package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.wonhigh.retail.fas.common.enums.FasBillCodeEnums;
import cn.wonhigh.retail.fas.common.model.BillBuyBalance;
import cn.wonhigh.retail.fas.common.model.BillSaleBalance;
import cn.wonhigh.retail.fas.service.DataHqAreaExchangeService;
import cn.wonhigh.retail.gms.api.service.InventoryBookApi;
import cn.wonhigh.retail.gms.api.vo.InventoryBookDto;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

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
@Service("dataHqAreaExchangeManager")
class DataHqAreaExchangeManagerImpl extends BaseCrudManagerImpl implements DataHqAreaExchangeManager {
    @Resource
    private DataHqAreaExchangeService dataHqAreaExchangeService;
    
    @Resource
    private InventoryBookApi inventoryBookApi;
    
    @Override
    public BaseCrudService init() {
        return dataHqAreaExchangeService;
    }

	@Override
	public int selectSaleTransferBillCount(Map<String, Object> params) throws ManagerException {
		try {
			return dataHqAreaExchangeService.selectSaleTransferBillCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> selectSaleTransferBill(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return dataHqAreaExchangeService.selectSaleTransferBill(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	@Override
	public int selectBuyTransferBillCount(Map<String, Object> params) throws ManagerException {
		try {
			return dataHqAreaExchangeService.selectBuyTransferBillCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> selectBuyTransferBill(SimplePage page,
			String orderByField, String orderBy, Map<String, Object> params) throws ManagerException {
		try {
			return dataHqAreaExchangeService.selectBuyTransferBill(page, orderByField, orderBy, params);
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=ManagerException.class)
	public Map<String, Object> changeBillType(List<Object> billList,
			String changeBillType) throws ManagerException {
		
		Map<String, Object> reMap = new HashMap<String, Object>();
		
		List<BillSaleBalance> saleList = new ArrayList<BillSaleBalance>();
		List<InventoryBookDto> dtoList = new ArrayList<InventoryBookDto>();
		int accountType = 0;
		
		for(Object obj : billList) {
			BillBuyBalance billBuyBalance = (BillBuyBalance) obj;
			String fasBillCode = null;
			//总部至地区-冲抵
			if("-1".equals(changeBillType)) {
				if(FasBillCodeEnums.FG13710403.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710404.getCode();
				}
				else if(FasBillCodeEnums.FG13710503.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710504.getCode();
				}
				accountType = -1;
			}
			//地区至总部-正常
			else if ("0".equals(changeBillType)) {
				if(FasBillCodeEnums.FG13710404.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710403.getCode();
				}
				else if(FasBillCodeEnums.FG13710504.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710503.getCode();
				}
				accountType = 0;
			}
			//地区至总部-冲抵
			else if ("-2".equals(changeBillType)) {
				if(FasBillCodeEnums.FG13710401.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710405.getCode();
				}
				else if(FasBillCodeEnums.FG13710501.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710505.getCode();
				}
				accountType = -2;
			}
			//总部至地区-正常
			else if ("2".equals(changeBillType)) {
				if(FasBillCodeEnums.FG13710405.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710401.getCode();
				}
				else if(FasBillCodeEnums.FG13710505.getCode().equals(billBuyBalance.getFasBillCode())) {
					fasBillCode = FasBillCodeEnums.FG13710501.getCode();
				}
				accountType = 2;
			}
			if(StringUtils.isNotEmpty(fasBillCode)) {
				BillSaleBalance updateObj = new BillSaleBalance();
				updateObj.setId(billBuyBalance.getId());
				updateObj.setFasBillCode(fasBillCode);
				updateObj.setOriginalBillNo(billBuyBalance.getOriginalBillNo());
				updateObj.setItemNo(billBuyBalance.getItemNo());
				saleList.add(updateObj);
				
				InventoryBookDto inventoryBookDto = new InventoryBookDto();
				inventoryBookDto.setBillNo(billBuyBalance.getOriginalBillNo());
				inventoryBookDto.setItemNo(billBuyBalance.getItemNo());
				dtoList.add(inventoryBookDto);
			}
		}
		
		//调用GMS接口,通知其处理
		if(dtoList.size() != 0) {
			int total = dtoList.size();
			int pageSize = 100;
			int pageCount = total/pageSize + 1 ;
			for(int i = 1; i <= pageCount; i++) {
				
				int startIndex = (i - 1) * pageSize;
				int endIndex = i * pageSize - 1;
				
				List<String> gmsApiList = new ArrayList<String>();
				
				for(int j = startIndex; j <= endIndex && j<= total - 1; j++) {
					if(!gmsApiList.contains(dtoList.get(j).getBillNo())){
					    gmsApiList.add(dtoList.get(j).getBillNo());
					}
				}
				
				try {
					inventoryBookApi.updateAccountType(gmsApiList, accountType);
				} catch (RpcException e) {
					throw new ManagerException(e.getMessage(), e);
				}
			}
		}
		
		//调整出库单
		if(saleList.size() != 0) {
			for(BillSaleBalance billBalance : saleList) {
				this.updateChangeBill(billBalance, changeBillType);
			}
		}
		
		reMap.put("isSuccess", true);
		reMap.put("message", "操作成功！");
		
		return reMap;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=ManagerException.class)
	private void updateChangeBill(BillSaleBalance billBalance, String changeBillType) throws ManagerException {
		try {
			dataHqAreaExchangeService.updateSaleTransferBill(billBalance);
			//查询对应的入库单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("originalBillNo", billBalance.getOriginalBillNo());
			params.put("itemNo", billBalance.getItemNo());
			List<BillBuyBalance> buyTransferList = dataHqAreaExchangeService.selectBuyTransferBill(null, null, null, params);
			
			//查询差异部分
			Map<String, Object> paramsDiff = new HashMap<String, Object>();
			paramsDiff.put("refBillNo", billBalance.getOriginalBillNo());
			paramsDiff.put("itemNo", billBalance.getItemNo());
			buyTransferList.addAll(dataHqAreaExchangeService.selectBuyTransferBill(null, null, null, paramsDiff));
			
			for(BillBuyBalance buyTransfer : buyTransferList) {
				BillBuyBalance updateObj = new BillBuyBalance();
				String fasBillCode = null;
				//总部至地区-冲抵
				if("-1".equals(changeBillType)) {
					if(FasBillCodeEnums.FG13720403.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720404.getCode();
					}
					else if(FasBillCodeEnums.FG13720503.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720504.getCode();
					}
				}
				//地区至总部-正常
				else if ("0".equals(changeBillType)) {
					if(FasBillCodeEnums.FG13720404.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720403.getCode();
					}
					else if(FasBillCodeEnums.FG13720504.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720503.getCode();
					}
				}
				//地区至总部-冲抵
				else if ("-2".equals(changeBillType)) {
					if(FasBillCodeEnums.FG13720401.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720405.getCode();
					}
					else if(FasBillCodeEnums.FG13720501.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720505.getCode();
					}
				}
				//总部至地区-正常
				else if ("2".equals(changeBillType)) {
					if(FasBillCodeEnums.FG13720405.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720401.getCode();
					}
					else if(FasBillCodeEnums.FG13720505.getCode().equals(buyTransfer.getFasBillCode())) {
						fasBillCode = FasBillCodeEnums.FG13720501.getCode();
					}
				}
				updateObj.setId(buyTransfer.getId());
				updateObj.setFasBillCode(fasBillCode);
				updateObj.setOriginalBillNo(buyTransfer.getOriginalBillNo());
				updateObj.setItemNo(buyTransfer.getItemNo());
				dataHqAreaExchangeService.updateBuyTransferBill(updateObj);
			}
			
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}

	@Override
	public List<BillBuyBalance> findTotalRow(Map<String, Object> params) {
		return dataHqAreaExchangeService.findTotalRow(params);
	}

	@Override
	public List<BillBuyBalance> findBuyTotalRow(Map<String, Object> params) {
		return dataHqAreaExchangeService.findBuyTotalRow(params);
	}

}