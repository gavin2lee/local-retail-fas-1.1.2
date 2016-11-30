package cn.wonhigh.retail.fas.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ReportDto;
import cn.wonhigh.retail.fas.common.dto.ReportFinanceDto;
import cn.wonhigh.retail.fas.common.dto.ReportGatherDto;
import cn.wonhigh.retail.fas.common.dto.ReportInventoryDto;
import cn.wonhigh.retail.fas.common.dto.ReportTransferDto;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.service.ReportService;

import com.google.common.base.Function;
import com.yougou.logistics.base.common.exception.ManagerException;
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
@Service("reportManager")
class ReportManagerImpl implements ReportManager {
    @Resource
    private ReportService reportService;

	@Override
	public int findReportCount(Map<String, Object> params) throws Exception {
		try {
			if(null != params.get("isTotal") && "true".equals(String.valueOf(params.get("isTotal")))){
				return reportService.findReportTotalCount(params);
			}
			return reportService.findReportCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findReportByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			if(null != params.get("isTotal") && "true".equals(String.valueOf(params.get("isTotal")))){
				return reportService.findReportTotalByPage(page, sortColumn, sortOrder, params);
			}
			List<ReportDto> rowList = reportService.findRowByPage(page, params);
			List<ReportDto> qtyList = reportService.findQtyByPage(page, params);
			List<Map<String, Object>> resuList = new ArrayList<Map<String,Object>>();
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
					if(rowDto.getSalerNo().equals(qtyDto.getSalerNo())
						&& rowDto.getItemNo().equals(qtyDto.getItemNo())
						&&(null == rowDto.getSendDate() || rowDto.getSendDate().equals(qtyDto.getSendDate()))
							){
						map.put(qtyDto.getOrderUnitNo(),qtyDto.getSendQty());
						if(null != map.get("sendAmount")){
							BigDecimal sendAmount = (BigDecimal)map.get("sendAmount");
							map.put("sendAmount", sendAmount.add(qtyDto.getSendAmount()));
						}else{
							map.put("purchasePrice", qtyDto.getPurchasePrice());
							map.put("factoryPrice", qtyDto.getFactoryPrice());
							map.put("sendAmount", qtyDto.getSendAmount());
						}
						if(null != map.get("totalQty")){
							Integer totalQty = (Integer)map.get("totalQty");
							map.put("totalQty", totalQty+qtyDto.getSendQty());
						}else{
							map.put("totalQty", qtyDto.getSendQty());
						}
					}
				}
				resuList.add(map);
			}
			return resuList;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Map<String, Object>> findReportFooter(SimplePage page, Map<String, Object> params)
			throws Exception {
		try {
			if(null != params.get("isTotal") && "true".equals(String.valueOf(params.get("isTotal")))){
				return reportService.findReportToalFooter(params);
			}
			List<ReportDto> footerDto = reportService.findReportFooter(page, params);
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
			List<Map<String, Object>> resuList = new ArrayList<Map<String,Object>>();
			resuList.add(map);
			return resuList;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportDto> findColumn(SimplePage page,
			Map<String, Object> params) throws Exception {
		try {
			return reportService.findColumnByPage(page, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportGatherCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportGatherCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportGatherDto> findReportGatherByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportGatherByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportDetailCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportDetailCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportDto> findReportDetailByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportDetailByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportGatherAreaCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportGatherAreaCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportGatherDto> findReportGatherAreaByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportGatherAreaByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportDto> findReportDetailFooter(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportDetailFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public int findReportBusinessCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportBusinessCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportDto> findReportBusinessByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			List<ReportDto> listBusiness1 = reportService.findReportBusinessByPage(page, sortColumn, sortOrder, params);
			page.setPageSize(page.getTotalCount());
			List<ReportDto> listBusiness2 = reportService.findReportBusinessByPage(page, sortColumn, sortOrder, params);
			ReportDto footerDto = new ReportDto();
			int sendQty = 0;
			int sendAmount=0;
			int returnQty=0;
			int returnAmount=0;
			int totalSendQty=0;
			int totalSendAmount=0;
			int totalReturnQty=0;
			int totalReturnAmount=0;
			int totalBalanceQty=0;
			int totalBalanceAmount=0;
			int balanceQty=0;
			int balanceAmount=0;
			for (ReportDto reportDto : listBusiness2) {
				sendQty += reportDto.getSendQty();
				sendAmount+=reportDto.getSendAmount().intValue();
				returnQty +=reportDto.getReturnQty();
				returnAmount+=reportDto.getReturnAmount().intValue();
				totalSendQty+=reportDto.getTotalSendQty();
				totalSendAmount+=reportDto.getTotalSendAmount().intValue();
				totalReturnQty+=reportDto.getTotalReturnQty();
				totalReturnAmount+=reportDto.getTotalReturnAmount().intValue();
				totalBalanceQty+=reportDto.getTotalBalanceQty();
				totalBalanceAmount+=reportDto.getTotalBalanceAmount().intValue();
				balanceQty+=reportDto.getBalanceQty();
				balanceAmount+=reportDto.getBalanceAmount().intValue();
			}
			footerDto.setOrderfromName("合计");
			footerDto.setSendQty(sendQty);
			footerDto.setSendAmount(new BigDecimal(sendAmount));
			footerDto.setReturnQty(returnQty);
			footerDto.setReturnAmount(new BigDecimal(returnAmount));
			footerDto.setTotalSendQty(totalSendQty);
			footerDto.setTotalSendAmount(new BigDecimal(totalSendAmount));
			footerDto.setTotalReturnQty(totalReturnQty);
			footerDto.setTotalReturnAmount(new BigDecimal(totalReturnAmount));
			footerDto.setTotalBalanceQty(totalBalanceQty);
			footerDto.setTotalBalanceAmount(new BigDecimal(totalBalanceAmount));
			footerDto.setBalanceQty(balanceQty);
			footerDto.setBalanceAmount(new BigDecimal(balanceAmount));
			listBusiness1.add(footerDto);
			return listBusiness1;
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportFinanceCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportFinanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportDto> findReportFinanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportFinanceByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportDto> findReportFinanceFooter(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportFinanceFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Object> queryDtlList(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.queryDtlList(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportInventoryCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportInventoryCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportInventoryDto> findReportInventoryByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportInventoryByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportInventoryDto> findReportInventoryFooter(
			Map<String, Object> params) throws Exception {
		try {
			return reportService.findReportInventoryFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportAllFinanceCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportAllFinanceCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportFinanceDto> findReportAllFinanceByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportAllFinanceByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportFinanceDto> findReportAllFinanceFooter(
			Map<String, Object> params) throws Exception {
		try {
			return reportService.findReportAllFinanceFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findReportTransferCount(Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportTransferCount(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportTransferDto> findReportTransferByPage(SimplePage page,
			String sortColumn, String sortOrder, Map<String, Object> params)
			throws Exception {
		try {
			return reportService.findReportTransferByPage(page, sortColumn, sortOrder, params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<ReportTransferDto> findReportTransferFooter(
			Map<String, Object> params) throws Exception {
		try {
			return reportService.findReportTransferFooter(params);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void findReportExport(SimplePage page, Map<String, Object> params,
			Function<Object, Boolean> handler) throws Exception {
		try {
			 reportService.findReportExport(page,params,handler);
		} catch (ServiceException e) {
			throw new ManagerException(e);
		}
	}

}