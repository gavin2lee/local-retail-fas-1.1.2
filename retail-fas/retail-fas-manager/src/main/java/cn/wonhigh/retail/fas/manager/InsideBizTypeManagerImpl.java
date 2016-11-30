package cn.wonhigh.retail.fas.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.wonhigh.retail.fas.common.model.InsideBizType;
import cn.wonhigh.retail.fas.common.model.InsideBizTypeDetail;
import cn.wonhigh.retail.fas.service.InsideBizTypeDetailService;
import cn.wonhigh.retail.fas.service.InsideBizTypeService;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.ServiceException;
import com.yougou.logistics.base.manager.BaseCrudManagerImpl;
import com.yougou.logistics.base.service.BaseCrudService;

/**
 * 请写出类的用途 
 * @author wangyj
 * @date  2015-04-02 13:45:59
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
@Service("insideBizTypeManager")
class InsideBizTypeManagerImpl extends BaseCrudManagerImpl implements InsideBizTypeManager {
    @Resource
    private InsideBizTypeService insideBizTypeService;
    
    @Resource
    private InsideBizTypeDetailService insideBizTypeDetailService;

    @Override
    public BaseCrudService init() {
        return insideBizTypeService;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public InsideBizType add(InsideBizType obj, List<Object> insertedList,List<Object> clientList,Map params) throws ManagerException{
		try {
			insideBizTypeService.add(obj);
			List<InsideBizType> list=insideBizTypeService.findByBiz(InsideBizType.class, params);
			for (Object dtl : insertedList) {
				InsideBizTypeDetail insideBizTypeDetail = (InsideBizTypeDetail)dtl;
				insideBizTypeDetail.setTypeId(Integer.valueOf(list.get(0).getId()));
				insideBizTypeDetail.setCreateTime(obj.getCreateTime());
				insideBizTypeDetail.setCreateUser(obj.getCreateUser());
				insideBizTypeDetail.setDtlType((byte)1);
				insideBizTypeDetailService.add(insideBizTypeDetail);
			}
			
			for (Object dtl : clientList) {
				InsideBizTypeDetail insideBizTypeDetail = (InsideBizTypeDetail)dtl;
				insideBizTypeDetail.setTypeId(Integer.valueOf(list.get(0).getId()));
				insideBizTypeDetail.setCreateTime(obj.getCreateTime());
				insideBizTypeDetail.setCreateUser(obj.getCreateUser());
				insideBizTypeDetail.setDtlType((byte)2);
				insideBizTypeDetailService.add(insideBizTypeDetail);
			}
			return obj;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
    @Override
	public InsideBizType update(InsideBizType obj, List<Object> insertedList,
			List<Object> updatedList, List<Object> deletedList,List<Object> clientInsList)
			throws ManagerException {
    	InsideBizType insideBizType =obj;
		try {
			insideBizTypeService.modifyById(obj);
			for (Object dtl : insertedList) {
				InsideBizTypeDetail insideBizTypeDetail = (InsideBizTypeDetail)dtl;
				insideBizTypeDetail.setTypeId(Integer.valueOf(obj.getId()));
				insideBizTypeDetail.setDtlType((byte)1);
				insideBizTypeDetail.setCreateTime(obj.getCreateTime());
				insideBizTypeDetail.setCreateUser(obj.getCreateUser());
				insideBizTypeDetailService.add(insideBizTypeDetail);
			}
			for (Object dtl : updatedList) {
				InsideBizTypeDetail insideBizTypeDetail = (InsideBizTypeDetail)dtl;
				insideBizTypeDetail.setTypeId(Integer.valueOf(obj.getId()));
				insideBizTypeDetail.setDtlType((byte)2);
				insideBizTypeDetailService.modifyById(insideBizTypeDetail);
			}
			for (Object dtl : deletedList) {
				InsideBizTypeDetail insideBizTypeDetail = (InsideBizTypeDetail)dtl;
				insideBizTypeDetail.setTypeId(Integer.valueOf(obj.getId()));
				insideBizTypeDetailService.deleteById(insideBizTypeDetail);
			}
			for (Object dtl : clientInsList) {
				InsideBizTypeDetail insideBizTypeDetail = (InsideBizTypeDetail)dtl;
				insideBizTypeDetail.setTypeId(Integer.valueOf(obj.getId()));
				insideBizTypeDetail.setDtlType((byte)2);
				insideBizTypeDetailService.add(insideBizTypeDetail);
			}
			return insideBizType;
		} catch (Exception e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
    
    @Override
	public Integer delete(List<Object> dtlList) throws ManagerException {
		try {
			int iCount =0 ;
			for (Object object : dtlList) {
				InsideBizType insideBizType = (InsideBizType)object;
				insideBizTypeService.deleteById(insideBizType);
				InsideBizTypeDetail insideBizTypeDetail = new InsideBizTypeDetail();
				insideBizTypeDetail.setTypeId(Integer.valueOf(insideBizType.getId()));
				insideBizTypeDetailService.deleteByTypeNo(insideBizTypeDetail);
				iCount ++;
			}
			return iCount;
		} catch (ServiceException e) {
			throw new ManagerException(e.getMessage(), e);
		}
	}
}