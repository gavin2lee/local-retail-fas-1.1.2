package cn.wonhigh.retail.fas.api.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.wonhigh.retail.fas.api.dto.InsideBizTypeDto;
import cn.wonhigh.retail.fas.api.dto.InsideBizTypeParamDto;
import cn.wonhigh.retail.fas.api.dto.PagingDto;
import cn.wonhigh.retail.fas.api.model.InsideBizTypeModel;
import cn.wonhigh.retail.fas.api.service.InsideBizTypeApi;
import cn.wonhigh.retail.fas.api.service.InsideBizTypeService;

import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.exception.ServiceException;

@Service("insideBizTypeManagerImplApi")
public class InsideBizTypeManagerImpl implements InsideBizTypeApi {

	private Logger log = Logger.getLogger(getClass());
	
	@Resource
	private InsideBizTypeService insideBizTypeService;
	
	@Override
	public PagingDto<InsideBizTypeDto> queryInsideBizTypeList(InsideBizTypeParamDto insideBizTypeParam) throws RpcException{
		
		Map<String,Object> params = new HashMap<String,Object>();
		List<InsideBizTypeDto> insideBizTypeDtoList = null;
		List<InsideBizTypeModel> insideBizTypeList = null;
		PagingDto<InsideBizTypeDto> pagingDto = null;
		try{
		
			if(validationParams(insideBizTypeParam)){
				params.put("companyNo", insideBizTypeParam.getCompanyNo());
				params.put("bizTypeCode", insideBizTypeParam.getBizTypeCode());
				params.put("status", insideBizTypeParam.getStatus());
				params.put("shopNo", insideBizTypeParam.getShopNo());
				
				//如果按店铺查不到业务类型
				insideBizTypeList = insideBizTypeService.findInsideBizTypeByCodition(params);
				//
				if(CollectionUtils.isEmpty(insideBizTypeList)){
					params.put("shopNo", null);//则把店铺的查询条件清空,只按公司查询
					insideBizTypeList = insideBizTypeService.findInsideBizTypeByCodition(params);
				}
				if(!CollectionUtils.isEmpty(insideBizTypeList) ){
					
					insideBizTypeDtoList = new ArrayList<InsideBizTypeDto>();
					
					for(InsideBizTypeModel insideBizTypeModel : insideBizTypeList){
						InsideBizTypeDto insideBizTypeDto = new InsideBizTypeDto();
						BeanUtils.copyProperties(insideBizTypeModel, insideBizTypeDto);
						insideBizTypeDtoList.add(insideBizTypeDto);
					}
					
					pagingDto = new PagingDto<InsideBizTypeDto>();
					pagingDto.setTotals(insideBizTypeList.size());
					pagingDto.setResults(insideBizTypeDtoList);
				}
				
			}
		}catch(RpcException e){
			log.error(e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		return pagingDto;
	}
	
	@Override
	public PagingDto<InsideBizTypeDto> queryClientList(InsideBizTypeParamDto insideBizTypeParam) throws RpcException{
		
		Map<String,Object> params = new HashMap<String,Object>();
		List<InsideBizTypeDto> insideBizTypeDtoList = null;
		List<InsideBizTypeModel> insideBizTypeList = null;
		PagingDto<InsideBizTypeDto> pagingDto = null;
		try{
		
			if(validationParams(insideBizTypeParam)){
				params.put("companyNo", insideBizTypeParam.getCompanyNo());
				if(StringUtils.isNotBlank(insideBizTypeParam.getBizTypeCode())){
					params.put("bizTypeCode", insideBizTypeParam.getBizTypeCode());
				}
				if(null != insideBizTypeParam.getStatus()){
					params.put("status", insideBizTypeParam.getStatus());
				}
				
				insideBizTypeList = insideBizTypeService.findClientListByCodition(params);
				if(!CollectionUtils.isEmpty(insideBizTypeList) ){
					
					insideBizTypeDtoList = new ArrayList<InsideBizTypeDto>();
					
					for(InsideBizTypeModel insideBizTypeModel : insideBizTypeList){
						InsideBizTypeDto insideBizTypeDto = new InsideBizTypeDto();
						BeanUtils.copyProperties(insideBizTypeModel, insideBizTypeDto);
						insideBizTypeDtoList.add(insideBizTypeDto);
					}
					
					pagingDto = new PagingDto<InsideBizTypeDto>();
					pagingDto.setTotals(insideBizTypeList.size());
					pagingDto.setResults(insideBizTypeDtoList);
				}
				
			}
		}catch(RpcException e){
			log.error(e.getMessage(), e);
			throw new RpcException(e.getMessage(), e);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		return pagingDto;
	}
	
	public boolean validationParams(InsideBizTypeParamDto params) throws RpcException{
		
		boolean flag = false;
		
		if(params != null){
			String companyNo = params.getCompanyNo();
//			String bizTypeCode = params.getBizTypeCode();
//			Integer status = params.getStatus();
			
//			if(StringUtils.isEmpty(bizTypeCode)){
//				log.error("bizTypeCode参数不合法,请检查");
//				throw new RpcException("财务辅助dubbo服务","bizTypeCode参数不合法,请检查");
//			}
			if(StringUtils.isEmpty(companyNo)){
				log.error("companyNo参数不能为空,请检查");
				throw new RpcException( "财务辅助dubbo服务","companyNo参数不能为空,请检查");
			}
//			if(StringUtils.isEmpty(params.getShopNo())){
//				log.error("shopNo参数不能为空,请检查");
//				throw new RpcException("财务辅助dubbo服务","shopNo参数不能为空,请检查");
//			}
//			if(status == null){
//				log.error("status参数不合法,请检查");
//				throw new RpcException("财务辅助dubbo服务","status参数不合法,请检查");
//			}
			flag = true;
		}else{
			log.error("[InsideBizTypeParamDto == null] 参数不合法,请检查");
			throw new RpcException("财务辅助dubbo服务","获取发票登记失败");
		}
		return flag;
	}

}
