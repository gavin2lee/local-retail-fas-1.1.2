package cn.wonhigh.retail.fas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.SettlePathDto;
import cn.wonhigh.retail.fas.common.dto.SettlePathQueryExportDto;
import cn.wonhigh.retail.fas.common.enums.FasAduitStatusEnum;
import cn.wonhigh.retail.fas.common.model.BillSplitSettlePathDtl;
import cn.wonhigh.retail.fas.common.model.SettlePath;
import cn.wonhigh.retail.fas.common.model.SettlePathDtl;
import cn.wonhigh.retail.fas.common.model.SettlePathDtlQueryVo;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.dal.database.SettleNewStyleDtlMapper;
import cn.wonhigh.retail.fas.dal.database.SettlePathDtlMapper;
import cn.wonhigh.retail.fas.dal.database.SettlePathMapper;
import cn.wonhigh.retail.fas.dal.database.SupplierGroupRelMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;

/**
 * 请写出类的用途 
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
@Service("settlePathService")
class SettlePathServiceImpl extends BaseServiceImpl implements SettlePathService {
   
	@Resource
    private SettlePathMapper settlePathMapper;
	
	@Resource
	private SettlePathDtlMapper settlePathDtlMapper;
	
	@Resource
	private SettleNewStyleDtlMapper settleNewStyleDtlMapper;
	
	@Resource
	private SupplierGroupRelMapper supplierGroupRelMapper;

    @Override
    public BaseCrudMapper init() {
        return settlePathMapper;
    }

	@Override
	public BillSplitSettlePathDtl querySettlePathDtl(SettlePathDtlQueryVo vo) {
		BillSplitSettlePathDtl billSplitSettlePathDtl = new BillSplitSettlePathDtl();
		if(!validateParams(vo)) {
			billSplitSettlePathDtl.setErrorCode("001");
			billSplitSettlePathDtl.setErrorMsg("找不到对应的结算路径");
			return billSplitSettlePathDtl;
		}
		//查询供应商组编码
//		Map<String, Object> supplierGroupParams = new HashMap<String, Object>();
//		supplierGroupParams.put("supplierNo", vo.getSupplierNo());
//		List<SupplierGroupRel> supplierGroups = supplierGroupRelMapper.selectByParams(null, supplierGroupParams);
//		if(supplierGroups == null || supplierGroups.size() == 0) {
//			billSplitSettlePathDtl.setErrorCode("002");
//			billSplitSettlePathDtl.setErrorMsg("供应商不在任何供应商组里");
//			return billSplitSettlePathDtl;
//		}
//		if(supplierGroups.size() > 1) {
//			billSplitSettlePathDtl.setErrorCode("003");
//			billSplitSettlePathDtl.setErrorMsg("供应商存在于供应商组里");
//			return billSplitSettlePathDtl;
//		}
//		billSplitSettlePathDtl.setSupplierGroupNo(supplierGroups.get(0).getGroupNo());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("billBasis", vo.getBillBasis());
		params.put("billType", vo.getBillType());
//		params.put("brandNo", vo.getBrandNo());
//		params.put("categoryNo", vo.getCategoryNo());
		params.put("auditStatus", FasAduitStatusEnum.ADUIT_STATUS.getValue());
		params.put("queryDate", DateUtil.format1(new Date()));
		List<SettlePath> paths = settlePathMapper.selectByQueryVo(params);
		if(paths == null || paths.size() == 0) {
			billSplitSettlePathDtl.setErrorCode("004");
			billSplitSettlePathDtl.setErrorMsg("找不到对应的结算路径");
			return billSplitSettlePathDtl;
		}
		List<SettlePath> resultPaths = new ArrayList<SettlePath>();
		for(SettlePath path : paths) {
			if("All".equals(path.getStyleNo())) {
				resultPaths.add(path);
			} else {
				Map<String, Object> styleParams = new HashMap<String, Object>();
				styleParams.put("queryYear", vo.getYears());
				styleParams.put("querySeason", vo.getSeason());
				int count = settleNewStyleDtlMapper.selectCount(styleParams, null);
				if(count  == 1) {
					resultPaths.add(path);
				} else {
					//YANGYONGTODO
				}
			}
		}
		if(resultPaths == null || resultPaths.size() == 0) {
			billSplitSettlePathDtl.setErrorCode("004");
			billSplitSettlePathDtl.setErrorMsg("找不到对应的结算路径");
			return billSplitSettlePathDtl;
		}
//		if(resultPaths.size() > 1) {
//			billSplitSettlePathDtl.setErrorCode("005");
//			billSplitSettlePathDtl.setErrorMsg("存在多个结算路径");
//			return billSplitSettlePathDtl;
//		}
		Map<String, Object> pathDtlParams = new HashMap<String, Object>();
		pathDtlParams.put("pathNo", resultPaths.get(0).getPathNo());
		List<SettlePathDtl> list = settlePathDtlMapper.selectByParams(null, pathDtlParams);
		if(list == null || list.size() == 0) {
			billSplitSettlePathDtl.setErrorCode("006");
			billSplitSettlePathDtl.setErrorMsg("结算路径没有明细数据");
			return billSplitSettlePathDtl;
		}
		//判断单据头中的供应商是否在结算路径明细数据的供应商组里
//		if(!supplierGroups.get(0).getGroupNo().equals(list.get(0).getCompanyNo())) {
//			billSplitSettlePathDtl.setErrorCode("007");
//			billSplitSettlePathDtl.setErrorMsg("供应商不存在于供应商组[" + list.get(0).getCompanyNo() + "]里");
//			return billSplitSettlePathDtl;
//		}
		
		int maxPathOrder = list.get(list.size() - 1).getPathOrder();
		List<SettlePathDtl> results = new ArrayList<SettlePathDtl>();
		for(SettlePathDtl dtl : list) {
			//如果当前对象不是最后一级，则直接将对象添加到结果集合中
			if(dtl.getPathOrder() < maxPathOrder) {
				results.add(dtl);
				continue;
			}
			//如果找到最后一级，将当前对象添加到结果集合中，并终止循环
			if(dtl.getCompanyNo().equals(vo.getCompanyNo())) {
				results.add(dtl);
				break;
			}
		}
		if(maxPathOrder != results.size()) {
			billSplitSettlePathDtl.setErrorCode("008");
			billSplitSettlePathDtl.setErrorMsg("结算路径信息维护不完整");
			return billSplitSettlePathDtl;
		}
		billSplitSettlePathDtl.setList(results);
		billSplitSettlePathDtl.setPathNo(resultPaths.get(0).getPathNo());
		return billSplitSettlePathDtl;
	}
	
	private boolean validateParams(SettlePathDtlQueryVo vo) {
		return true;
	}

	/**
	 * 定时修改结算路径的状态
	 * @param params 参数
	 * @return 修改的数量
	 * @throws ServiceException 异常
	 */
	@Override
	public int updateStatus(Map<String, Object> params) throws ServiceException {
		try {
			return settlePathMapper.updateStatus(params);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int doAudit(SettlePath model) throws ServiceException {
		try {
			return settlePathMapper.doAudit(model);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int doStatus(SettlePathDto model) throws ServiceException {
		try {
			return settlePathMapper.doStatus(model);
		} catch(Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
   	public int findRelationCount(Map<String, Object> params) throws ServiceException {
		return settlePathMapper.selectRelationCount(params, null);
   	}

   	@Override
   	public List<SettlePathQueryExportDto> findRelationByPage(SimplePage page, String sortColumn, String sortOrder,
   			Map<String, Object> params) throws ServiceException {
		return settlePathMapper.selectRelationByPage(page, sortColumn, sortOrder, params, null);
   	}
}