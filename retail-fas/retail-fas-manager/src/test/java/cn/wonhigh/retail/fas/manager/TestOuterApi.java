package cn.wonhigh.retail.fas.manager;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wonhigh.retail.fas.common.utils.DateUtil;
import cn.wonhigh.retail.gms.api.service.CalculateWeightedCostApi;
import cn.wonhigh.retail.gms.common.dto.InvCostGenerateDto;
import cn.wonhigh.retail.uc.common.api.model.AuthorityUserSettlementCompany;
import cn.wonhigh.retail.uc.common.api.service.AuthorityUserDataApi;

import com.yougou.logistics.base.common.exception.RpcException;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/spring-fas-manager-test.xml")
public class TestOuterApi  {
	
	@Resource
	private CalculateWeightedCostApi calculateWeightedCostApi;
	
//	@Autowired(required = false)
//	private SSOApi ssoApi;
//
//	@Autowired(required = false)
//	AuthorityUserApi userApi;
	
	@Autowired(required = false)
	AuthorityUserDataApi userDataApi;
	
	/**
	 * 生成月末一次加权成本
	 * @throws RpcException
	 */
	@Test
	public void testUCPermission() throws RpcException{
		
		List<AuthorityUserSettlementCompany> companies = userDataApi.userSettlementCompany(97);
		
		for (AuthorityUserSettlementCompany company : companies) {
			System.out.println(company.getSettlementCompanyNo());
		}
	}
	
	
	
	
	/**
	 * 校验是否存在异常单据
	 * @throws RpcException
	 */
	@Test
	public void testExceptionBillExist() throws RpcException{
		
		InvCostGenerateDto costGenerateDto = new InvCostGenerateDto();
		costGenerateDto.setCompanyNo("20140922000008");
		costGenerateDto.setStartDate(DateUtil.getFirstDayOfMonth(2014,10));
		costGenerateDto.setEndDate(DateUtil.getLastDayOfMonth(2014,10));
		
		boolean exceptionBillExist = calculateWeightedCostApi.checkHasBillException(costGenerateDto);
		System.out.println(exceptionBillExist);
	}
	
	/**
	 * 分页查询异常单据列表
	 * @throws RpcException
	 */
	@Test
	public void testQryFactoryPriceByPage() throws RpcException{
		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("page", 1);
//		params.put("pageSize", 20);
//		params.put("sortColumn", "");
//		params.put("sortOrder", "");
//		
//		cn.wonhigh.retail.pms.api.dto.PagingDto<PurchasePriceDTO> listDto = purchasePriceApi.qryFactoryPriceByPage(params);
//		
//		System.out.println(listDto);
	}
	
}
