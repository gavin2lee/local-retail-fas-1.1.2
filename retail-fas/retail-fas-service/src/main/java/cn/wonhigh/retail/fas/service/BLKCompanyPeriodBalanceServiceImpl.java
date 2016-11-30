package cn.wonhigh.retail.fas.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.dto.ItemCostConditionDto;
import cn.wonhigh.retail.fas.common.model.CompanyPeriodBalance;
import cn.wonhigh.retail.fas.dal.database.BLKCompanyPeriodBalanceMapper;

import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.common.utils.SimplePage;
import com.yougou.logistics.base.dal.database.BaseCrudMapper;
import com.yougou.logistics.base.service.BaseCrudServiceImpl;

@Service("blkCompanyPeriodBalanceService")
public class BLKCompanyPeriodBalanceServiceImpl extends BaseCrudServiceImpl implements BLKCompanyPeriodBalanceService {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(BLKCompanyPeriodBalanceServiceImpl.class);
	@Resource
	private BLKCompanyPeriodBalanceMapper blkCompanyPeriodBalanceMapper;
	
	@Override
	public BaseCrudMapper init() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyPeriodBalance> selectTheFinalCostByPage(Map<String, Object> params) throws ServiceException {
		return blkCompanyPeriodBalanceMapper.selectTheFinalCostByPage(params);
	}
	
	@Override
	public void deleteCompanyMonthPeriodBalance(ItemCostConditionDto dto) throws ServiceException {
		try {
			// 先删除本公司,本月的所有结存数据
	    	blkCompanyPeriodBalanceMapper.deleteCompanyMonthPeriodBalance(dto);
			LOGGER.info("################"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部删除公司结存成功 ###################");
		} catch (Exception e) {
			LOGGER.error(dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部删除公司结存失败", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void generateCompanyPeriodBalance(ItemCostConditionDto conditionDto) throws ServiceException {
		try {
	    	blkCompanyPeriodBalanceMapper.shopSummaryToCompany(conditionDto);
			LOGGER.info("################"+conditionDto.getCompanyNo()+"公司"+conditionDto.getCurrentYear()+"年"+conditionDto.getCurrentMonth()+"月"+conditionDto.getBrandUnitNo()+"品牌部生成公司结存成功 ###################");
		} catch (Exception e) {
			LOGGER.error(conditionDto.getCompanyNo()+"公司"+conditionDto.getCurrentYear()+"年"+conditionDto.getCurrentMonth()+"月"+conditionDto.getBrandUnitNo()+"品牌部生成公司结存失败", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void writeBackAmount(ItemCostConditionDto invCostGenerateDto) throws ServiceException {
		try{
			blkCompanyPeriodBalanceMapper.batchUpdateWriteBackAmount(invCostGenerateDto);
			LOGGER.info("################"+invCostGenerateDto.getCompanyNo()+"公司"+invCostGenerateDto.getCurrentYear()+"年"+invCostGenerateDto.getCurrentMonth()+"月"+invCostGenerateDto.getBrandUnitNo()+"品牌部入库数据已成功回写到结存    ###################");
		}catch(Exception e){
			LOGGER.error(invCostGenerateDto.getCompanyNo()+"公司"+invCostGenerateDto.getCurrentYear()+"年"+invCostGenerateDto.getCurrentMonth()+"月"+invCostGenerateDto.getBrandUnitNo()+"品牌部入库数据回写结存失败。", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void writeBackCostAdjustmentAmout(ItemCostConditionDto dto) throws ServiceException {
		try{
			blkCompanyPeriodBalanceMapper.batchHandleCostDjustmentAmount(dto);
			LOGGER.info("################"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部成本生成逻辑执行完毕  ###################");
		}catch(Exception e){
			LOGGER.error("################"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部生成成本失败。", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void itemCostFeedbackToPeriodBalance(ItemCostConditionDto dto) throws ServiceException {
		try{
			// 分批获取本期结存 已经汇总的入库类单据的数据
			int totalCount = blkCompanyPeriodBalanceMapper.queryPeriodBalanceJoinItemCostCount(dto);
			if (totalCount <= 0) {
				return;
			}
			int pageSize = 2000;// 每次查询2000条
			int pageNo = 1;// 当前页数
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}
	
			while (pageNo <= totalPage) {
				SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
				blkCompanyPeriodBalanceMapper.batchUpdateBalanceItemCostByPage(page, "", "", dto);
				pageNo++;
			}
			LOGGER.info("################"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部出库类金额已成功回写到公司结存    ###################");
		}catch(Exception e){
			LOGGER.error("################"+dto.getCompanyNo()+"公司"+dto.getCurrentYear()+"年"+dto.getCurrentMonth()+"月"+dto.getBrandUnitNo()+"品牌部成本回写公司结存失败。", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Map<String, BigDecimal> selectCompanyPeriodBalanceGroupByStyleNo(ItemCostConditionDto dto,List<String> itemNos) {
		List<CompanyPeriodBalance> list = blkCompanyPeriodBalanceMapper.selectCompanyPeriodBalanceGroupByStyleNo(dto, itemNos);
		Map<String, BigDecimal> styleInfo = new HashMap<String, BigDecimal>();
		
		for (CompanyPeriodBalance blkPeriodBalance : list) {
			styleInfo.put(blkPeriodBalance.getItemNo(), blkPeriodBalance.getUnitCost());
		}
		return styleInfo;
	}
	
	@Override
	public List<String> findCompanyPeriodBalanceList(ItemCostConditionDto conditionDto) throws ServiceException {
		try {
			return blkCompanyPeriodBalanceMapper.findCompanyPeriodBalanceList(conditionDto);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	
}
