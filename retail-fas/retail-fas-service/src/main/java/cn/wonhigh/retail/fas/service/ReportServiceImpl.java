package cn.wonhigh.retail.fas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.backend.data.core.DbHelper;
import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.dto.ReportFinanceDto;
import cn.wonhigh.retail.fas.common.dto.ReportGatherDto;
import cn.wonhigh.retail.fas.common.dto.ReportInventoryDto;
import cn.wonhigh.retail.fas.common.dto.ReportTransferDto;
import cn.wonhigh.retail.fas.common.model.BillPayment;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.dal.database.BillPaymentMapper;
import cn.wonhigh.retail.fas.dal.database.OtherDeductionMapper;
import cn.wonhigh.retail.fas.dal.database.ReportFinanceMapper;
import cn.wonhigh.retail.fas.dal.database.ReportMapper;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 请写出类的用途 
 * @author wang.m1
 * @date  2014-08-25 11:34:16
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
@Service("reportService")
class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private ReportFinanceMapper reportFinanceMapper;
    
    @Resource
    private BillPaymentMapper billPaymentMapper;
    
    @Resource
    private OtherDeductionMapper otherDeductionMapper;
    
	@Override
	public int findReportCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findReportFooter(SimplePage page, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportFooter(page, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findColumnByPage(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		try {
			return reportMapper.findColumnByPage(page, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findRowByPage(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		try {
			return reportMapper.findRowByPage(page, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findQtyByPage(SimplePage page,
			Map<String, Object> params) throws ServiceException {
		try {
			return reportMapper.findQtyByPage(page, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findReportGatherCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportGatherFinanceCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportGatherDto> findReportGatherByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportGatherFinanceByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findReportDetailCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportDetailCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findReportDetailByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportDetailByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findReportGatherAreaCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportGatherAreaCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportGatherDto> findReportGatherAreaByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportGatherAreaByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findReportDetailFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportDetailFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}
	
	@Override
	public int findReportBusinessCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportBusinessCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findReportBusinessByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportBusinessByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findReportFinanceCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportFinanceCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findReportFinanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportFinanceByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportDto> findReportFinanceFooter(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportFinanceFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findReportTotalCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportTotalCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Map<String, Object>> findReportTotalByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportMapper.findReportTotalByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Map<String, Object>> findReportToalFooter(
			Map<String, Object> params) throws ServiceException {
		try {
			return reportMapper.findReportToalFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<Object> queryDtlList(Map<String, Object> params)
			throws ServiceException {
		try {
			String panelId = String.valueOf(params.get("panelId"));
			if("beforeBalancePanel".equals(panelId)){
				return this.getBeforeBalanceList(params);
			}else if("currentPaymentPanel".equals(panelId)){
				params.put("billDateStart", params.get("sendDateStart"));
				params.put("billDateEnd", params.get("sendDateEnd"));
				params.put("status", 5);
				List<Object> lstItem = billPaymentMapper.selectByParams(null, params);
				if(lstItem.size() > 0){
					List<BillPayment> lstFooter = billPaymentMapper.selectFooter(params);
					BillPayment obj = lstFooter.get(0);
					obj.setBuyerName("合计");
					obj.setBillNo("");
					lstItem.add(obj);
				}
				return lstItem;
			}else if("currentDeductionPanel".equals(panelId)){
				params.put("queryCondition", " AND balance_type IN (1,13)");
				params.put("deductionDateStart", params.get("sendDateStart"));
				params.put("deductionDateEnd", params.get("sendDateEnd"));
				List<Object> lstItem = otherDeductionMapper.selectByParams(null, params);
				if(lstItem.size() > 0){
					lstItem.addAll(otherDeductionMapper.findFooter(params));
				}
				return lstItem;
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	private List<Object> getBeforeBalanceList(Map<String, Object> params) throws Exception{
		String sendStartDate = String.valueOf(params.get("sendDateStart"));
		Date firstDate = DateUtil.parseToDate(sendStartDate, "yyyy-MM-dd");
		List<Object> lstAll = new ArrayList<Object>();
		for(int i = 1; i < 12; i++){
			Date startDate = DateUtil.addMonth(firstDate, -i);
			Date endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 1), -1);
			Object dto = this.getBalanceDto(params,startDate,endDate);
			if(null != dto){
				lstAll.add(dto);
			}
		}
		Date startDate = DateUtil.addMonth(firstDate, -12);
		Date endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 1), -1);
		Object dto = this.getBalanceDto(params,null,endDate);
		if(null != dto){
			lstAll.add(dto);
		}
		// 合计行
		if(lstAll.size() > 0){
			params.put("sendDateStart", null);
			params.put("sendDateEnd", firstDate);
			List<ReportGatherDto> lstItem = reportMapper.findSumBalanceFooter(params);
			if(lstItem.size() > 0){
				lstAll.add(lstItem.get(0));
			}
		}
		return lstAll;
	}

	private ReportGatherDto getBalanceDto(Map<String, Object> params,
			Date startDate, Date endDate) {
		params.put("sendDateStart", startDate);
		params.put("sendDateEnd", endDate);
		List<ReportGatherDto> lstItem = reportMapper.findSumBalanceList(params);
		if(lstItem.size() > 0){
			ReportGatherDto dto = lstItem.get(0);
			dto.setSendDateStart(startDate);
			dto.setSendDateEnd(endDate);
			return dto;
		}
		return null;
	}

	@Override
	public int findReportInventoryCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportFinanceMapper.findReportInventoryCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportInventoryDto> findReportInventoryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportFinanceMapper.findReportInventoryByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportInventoryDto> findReportInventoryFooter(
			Map<String, Object> params) throws ServiceException {
		try {
			return reportFinanceMapper.findReportInventoryFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findReportAllFinanceCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportFinanceMapper.findReportAllFinanceCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportFinanceDto> findReportAllFinanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportFinanceMapper.findReportAllFinanceByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportFinanceDto> findReportAllFinanceFooter(
			Map<String, Object> params) throws ServiceException {
		try {
			return reportFinanceMapper.findReportAllFinanceFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public int findReportTransferCount(Map<String, Object> params)
			throws ServiceException {
		try {
			return reportFinanceMapper.findReportTransferCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportTransferDto> findReportTransferByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws ServiceException {
		try {
			return reportFinanceMapper.findReportTransferByPage(page, sortColumn, sortOrder, params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public List<ReportTransferDto> findReportTransferFooter(
			Map<String, Object> params) throws ServiceException {
		try {
			return reportFinanceMapper.findReportTransferFooter(params);
		} catch (Exception e) {
			throw new ServiceException("",e);
		}
	}

	@Override
	public void findReportExport(SimplePage page, Map<String, Object> params,
			Function<Object, Boolean> handler) throws ServiceException {
		String statement = "cn.wonhigh.retail.fas.dal.database.ReportFinanceMapper.findReportAllFinanceByPage";
		Map<String,Object> ps = new HashMap<String, Object>();
		ps.put("page", page);
		ps.put("params", params);
		try {
			DbHelper.FastExcute(statement, ps, handler);	
		} catch (Exception e) {
			 throw new ServiceException(e);
		}
	}

}