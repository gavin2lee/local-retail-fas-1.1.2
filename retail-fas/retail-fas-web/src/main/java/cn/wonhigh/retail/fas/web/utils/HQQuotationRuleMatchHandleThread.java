package cn.wonhigh.retail.fas.web.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import cn.wonhigh.retail.fas.common.model.HeadquarterCostMaintain;
import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.fas.manager.CommonUtilManager;
import cn.wonhigh.retail.fas.manager.HeadquarterCostMaintainManager;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.utils.SimplePage;

/**
 * 批量匹配总部加价规则，生成总部价
 * 
 * @author wang.xy
 * @date 2014-10-9 上午10:31:42
 * @version 0.1.0
 * @copyright yougou.com
 */
public class HQQuotationRuleMatchHandleThread implements Runnable {

	private static final XLogger LOGGER = XLoggerFactory.getXLogger(HQQuotationRuleMatchHandleThread.class);
	
	private HeadquarterCostMaintainManager headquarterCostMaintainManager;

	private CommonUtilManager commonUtilManager;
	
	private String currentUser;
	
	private String brandNo;
	
	private Date effectiveTime;
	
	private String multiCategoryNo;

	public HQQuotationRuleMatchHandleThread(HeadquarterCostMaintainManager costMaintainManager, CommonUtilManager commonUtilManager, 
				String currentUser, String brandNo,String firstNew,Date effectiveTime,String multiCategoryNo) {
		this.headquarterCostMaintainManager = costMaintainManager;
		this.commonUtilManager = commonUtilManager;
		this.currentUser = currentUser;
		this.brandNo = brandNo;
		this.effectiveTime = effectiveTime;
		this.multiCategoryNo = multiCategoryNo;
	}

	@Override
	public void run() {
		try {
			LOGGER.debug("###################### 开始批量生成总部价：  ##############################");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("brandNo", this.brandNo);
			params.put("multiCategoryNo", this.multiCategoryNo);
			int totalCount = this.commonUtilManager.countQuotationRuleMatchItems(params);
			if (totalCount <= 0) {
				return;
			}
			int pageSize = 1000;// 每次查询1000条
			int pageNo = 1;// 当前页数
			int i=1;
			int totalPage = totalCount / pageSize;// 总页数
			if (totalCount % pageSize != 0 || totalPage == 0) {
				totalPage++;
			}
			while (i <= totalPage) {
				SimplePage page = new SimplePage(pageNo, pageSize, totalCount);
				List<HeadquarterCostMaintain> needMaintainItems = this.commonUtilManager.queryQuotationRuleMatchItems(page, "", "", params);
				// 某一个地区需要维护的商品进行价格维护
				for (HeadquarterCostMaintain headCostMaintain : needMaintainItems) {
					headCostMaintain.setEffectiveTime(this.effectiveTime);
					headCostMaintain.setCreateUser(this.currentUser);
					headCostMaintain.setCreateTime(DateUtil.getCurrentDateTime());
					
					try {
						this.headquarterCostMaintainManager.addHeadquarterCost(headCostMaintain, "2");
					} catch (ManagerException e) {
						LOGGER.info("匹配加价规则失败, 商品编码： " + headCostMaintain.getItemCode());
						throw new ManagerException(e.getMessage(), e);
					}
				}
				i++;
			}
			LOGGER.debug("###################### 成功完成批量生成总部价  ##############################");
		} catch (ManagerException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage(), e);
		}
	}
}
