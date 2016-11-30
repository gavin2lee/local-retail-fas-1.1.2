package cn.wonhigh.retail.fas.api.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dto.MallDeductionSettingDto;
import cn.wonhigh.retail.fas.api.dto.ShopBalanceDateDto;
import cn.wonhigh.retail.fas.api.dto.ShopBankInfoDto;
import cn.wonhigh.retail.fas.api.dto.ShopCardInfoDto;
import cn.wonhigh.retail.fas.api.model.ShopBankAccountModel;
import cn.wonhigh.retail.fas.api.model.ShopBankInfoDtlModel;
import cn.wonhigh.retail.fas.api.service.SelfShopBankInfoService;
import cn.wonhigh.retail.fas.api.service.ShopSettlementApi;
import cn.wonhigh.retail.fas.api.service.ShopSettlementService;
import cn.wonhigh.retail.fas.common.model.MallDeductionSetting;
import cn.wonhigh.retail.fas.common.model.ShopBalanceDate;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("shopSettlementManagerImplApi")
public class ShopSettlementManagerImpl implements ShopSettlementApi {

	@Resource
	private ShopSettlementService shopSettlementServiceImpl;
	
	@Resource
	private SelfShopBankInfoService selfShopBankInfoServiceImpl;
	
	private Logger log = Logger.getLogger(getClass());
	
	@Override
	public ShopBalanceDateDto getAllShopBalanceDateInfo(String shopNo) throws RpcException {
		try {
			if(StringUtils.isEmpty(shopNo)){
				throw new RpcException("shopNo 参数不合法","财务辅助dubbo服务");
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			//获取店铺所有已生成结算单的结算期
			List<ShopBalanceDate> shopBalanceDateList =  shopSettlementServiceImpl.getAllShopBalanceDateInfo(shopNo);
			
			Date bigestDate = null;
			ShopBalanceDate shopBalanceDate = null;
			ShopBalanceDateDto shopBalanceDateDto = null;
			if(!CollectionUtils.isEmpty(shopBalanceDateList)){
				for(ShopBalanceDate shopBalanceDateTemp : shopBalanceDateList){
					Date yearAndMonth = dateFormat.parse(shopBalanceDateTemp.getMonth());
					if(bigestDate == null){
						bigestDate = yearAndMonth;
						shopBalanceDate = shopBalanceDateTemp;
					}else{
						if(yearAndMonth.getTime() > bigestDate.getTime()){
							bigestDate = yearAndMonth;
							shopBalanceDate = shopBalanceDateTemp;
						}
					}
				}
			}
			if(shopBalanceDate != null){
				shopBalanceDateDto = new ShopBalanceDateDto();
				shopBalanceDateDto.setShopNo(shopBalanceDate.getShopNo());
				shopBalanceDateDto.setMonth(shopBalanceDate.getMonth());
				shopBalanceDateDto.setBalanceStartDate(shopBalanceDate.getBalanceStartDate());
				shopBalanceDateDto.setBalanceEndDate(shopBalanceDate.getBalanceEndDate());
			}
			return shopBalanceDateDto; 
		} catch (ServiceException e) {
			log.error("获取商场扣费名目设置失败");
			throw new RpcException("获取商场扣费名目设置失败", "财务辅助dubbo服务",e);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);		
			throw new RpcException(e.getMessage(), e);
		}
	}

	@Override
	public List<MallDeductionSettingDto> getAllMallDeductionSettingInfo(String shopNo) throws RpcException {
		List<MallDeductionSettingDto> mallDeductionSettingDtoList = null;
		try {
			List<MallDeductionSetting> mallDeductionSettingList = shopSettlementServiceImpl.getAllMallDeductionSettingInfo(shopNo);

			if(mallDeductionSettingList != null && mallDeductionSettingList.size() > 0){
				mallDeductionSettingDtoList = new ArrayList<MallDeductionSettingDto>();
			
				for(MallDeductionSetting MallDeductionSetting :mallDeductionSettingList){
				
					MallDeductionSettingDto mallDeductionSettingDto = new MallDeductionSettingDto();
					BeanUtils.copyProperties(MallDeductionSetting, mallDeductionSettingDto);
					mallDeductionSettingDtoList.add(mallDeductionSettingDto);
				}
			}
			return mallDeductionSettingDtoList;
		} catch (ServiceException e) {
			log.debug("获取费用类别失败");
			throw new RpcException("获取费用类别失败", "财务辅助dubbo服务",e);
		}
	}

	@Override
	public ShopCardInfoDto getShopBankInfo(String shopNo) throws RpcException {
		ShopCardInfoDto shopCardInfoDto = null;
		try {
			if(shopNo == null || "".equals(shopNo)){
				throw new RpcException("shopNo 参数不合法","财务辅助dubbo服务");
			}
			
			ShopBankAccountModel selfShopBankInfo = selfShopBankInfoServiceImpl.getShopBankInfo(shopNo);
			if(selfShopBankInfo != null){
				shopCardInfoDto = new ShopCardInfoDto();
				BeanUtils.copyProperties(selfShopBankInfo, shopCardInfoDto);
			}
			return shopCardInfoDto;
		} catch (ServiceException e) {
			log.debug("获取店铺存入账号及终端号失败");
			throw new RpcException("获取店铺存入账号及终端号失败", "财务辅助dubbo服务",e);
		}
	}
	
	@Override
	public ShopBankInfoDto getShopBankInfoDtl(String shopNo)
			throws RpcException {
		ShopBankInfoDto shopBankInfoDto = null;
		try {
			if(shopNo == null || "".equals(shopNo)){
				throw new RpcException("shopNo 参数不合法","财务辅助dubbo服务");
			}
			ShopBankInfoDtlModel shopBankInfoDtl = new ShopBankInfoDtlModel();
			shopBankInfoDtl.setDepositAccountList(selfShopBankInfoServiceImpl.getShopDepositAccountInfo(shopNo));
			shopBankInfoDtl.setTerminalAccountList(selfShopBankInfoServiceImpl.getShopTerminalAccountInfo(shopNo));
			if(shopBankInfoDtl != null){
				shopBankInfoDto = new ShopBankInfoDto();
				BeanUtils.copyProperties(shopBankInfoDtl, shopBankInfoDto);
			}
			return shopBankInfoDto;
		} catch (Exception e) {
			log.debug("获取店铺存入账号及终端号失败");
			throw new RpcException("获取店铺存入账号及终端号失败", "财务辅助dubbo服务",e);
		}
	}

	@Override
	public Date getShopBalanceEndDate(String shopNo) throws RpcException {
		try {
			return shopSettlementServiceImpl.getShopBalanceEndDate(shopNo);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);		
			throw new RpcException("getShopBalanceEndDate 获取店铺失败", "财务辅助dubbo服务",e);
		}
	}

	@Override
	public Date getInsidePurchaseBalanceEndDate(String shopNo)
			throws RpcException {
		try {
			return shopSettlementServiceImpl.getInsidePurchaseBalanceEndDate(shopNo);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);		
			throw new RpcException("getInsidePurchaseBalanceEndDate 获取店铺失败", "财务辅助dubbo服务",e);
		}
	}



}
