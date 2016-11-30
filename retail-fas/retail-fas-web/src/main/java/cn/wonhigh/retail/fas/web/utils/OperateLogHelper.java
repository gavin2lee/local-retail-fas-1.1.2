package cn.wonhigh.retail.fas.web.utils;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import cn.wonhigh.retail.fas.common.constans.PublicConstans;
import cn.wonhigh.retail.fas.common.enums.AuditStatusEnums;
import cn.wonhigh.retail.fas.common.enums.OperateLogEnums;
import cn.wonhigh.retail.fas.common.model.OperateLog;
import cn.wonhigh.retail.fas.common.utils.UUIDGenerator;
import cn.wonhigh.retail.fas.manager.OperateLogManager;
import cn.wonhigh.retail.fas.web.controller.BaseController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;

/**
 * 操作日志的辅助类
 */
public class OperateLogHelper {
	protected static final XLogger logger = XLoggerFactory.getXLogger(BaseController.class);

	@Resource
	private OperateLogManager operateLogManager;
	
	/**
	 * 添加审核的操作日志(可批量操作)
	 * @param request HttpServletRequest
	 * @param moduleNo 模块编码
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerify(HttpServletRequest request, int moduleNo, String statusName, String operateStatusName) throws ManagerException {
		if(Integer.parseInt(request.getParameter("status")) == AuditStatusEnums.BACK.getTypeNo()) {
			return this.addVerifyBack(request, moduleNo ,statusName, operateStatusName);
		}
		return this.addVerifyPost(request, moduleNo, statusName, operateStatusName);
	}
	
	
	/**
	 * 添加审核的操作日志
	 * @param request HttpServletRequest
	 * @param moduleNo 模块编码
	 * @param operateStatusName 操作状态名称
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerify(HttpServletRequest request, int moduleNo, String operateStatusName) throws ManagerException {
		int status = 0;
		if(StringUtils.isNotEmpty(request.getParameter("status"))) {
			status = Integer.parseInt(request.getParameter("status"));
		}
		if(status == AuditStatusEnums.BACK.getTypeNo()) {
			return this.addVerifyBack(request, moduleNo, operateStatusName);
		}
		return this.addVerifyPost(request, moduleNo, operateStatusName);
	}
	
	/**
	 * 添加审核通过的操作日志
	 * @param request HttpServletRequest
	 * @param moduleNo 模块编码
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyPost(HttpServletRequest request, int moduleNo, String operateStatusName) throws ManagerException {
		try {
			String dataNo = request.getParameter("billNo");
			int status = 0;
			if(StringUtils.isNotEmpty(request.getParameter("status"))) {
				status = Integer.parseInt(request.getParameter("status"));
			}
			String statusName = request.getParameter("statusName");
			return this.addVerifyPost(request, dataNo, moduleNo, status, statusName, operateStatusName);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加审核通过的操作日志
	 * @param request HttpServletRequest
	 * @param moduleNo 模块编码
	 * @param statusName 状态名称
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyPost(HttpServletRequest request, int moduleNo, String statusName, String operateStatusName) throws ManagerException {
		try {
			String dataNo = request.getParameter("billNo");
			int status = 0;
			if(StringUtils.isNotEmpty(request.getParameter("status"))) {
				status = Integer.parseInt(request.getParameter("status"));
			}
			return this.addVerifyPost(request, dataNo, moduleNo, status, statusName, operateStatusName);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加审核通过的操作日志
	 * @param request HttpServletRequest
	 * @param moduleNo 模块编码
	 * @param status 状态编码
	 * @param statusName 状态名称
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyPost(HttpServletRequest request, int moduleNo, int status, String statusName, String operateStatusName) throws ManagerException {
		try {
			String dataNo = request.getParameter("billNo");
			return this.addVerifyPost(request, dataNo, moduleNo, status, statusName, operateStatusName);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加审核通过的操作日志
	 * @param request HttpServletRequest
	 * @param dataNo 数据编码
	 * @param moduleNo 模块编码
	 * @param status 状态编码
	 * @param statusName 状态名称
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyPost(HttpServletRequest request, String dataNo, int moduleNo, int status, 
			String statusName, String operateStatusName) throws ManagerException {
		try {
			String []arrDataNo = null;
			if(StringUtils.isNotEmpty(dataNo)) {
				arrDataNo = dataNo.split(",");
			}
			if(arrDataNo == null || arrDataNo.length == 0) {
				return 0;
			}
			int count = 0;
			for(String strNo : arrDataNo) {
				OperateLog operateLog = this.createOperateLog(request, strNo, moduleNo, status,
						statusName);
				operateLog.setOperateStatus(OperateLogEnums.OperateStatus.POST.getStatus());
				operateLog.setOperateStatusName(operateStatusName);
				count += operateLogManager.add(operateLog);
			}
			return count;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加审核打回的操作日志
	 * @param moduleNo 模块编码
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyBack(HttpServletRequest request, int moduleNo, String operateStatusName) throws ManagerException {
		try {
			String dataNo = request.getParameter("billNo");
			int status = 0;
			if(StringUtils.isNotEmpty(request.getParameter("status"))) {
				status = Integer.parseInt(request.getParameter("status"));
			}
			String statusName = request.getParameter("statusName");
			return this.addVerifyBack(request, dataNo, moduleNo, status, statusName, operateStatusName);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加审核打回的操作日志
	 * @param request HttpServletRequest
	 * @param moduleNo 模块编码
	 * @param statusName 状态名称
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyBack(HttpServletRequest request, int moduleNo, String statusName, String operateStatusName) throws ManagerException {
		try {
			String dataNo = request.getParameter("billNo");
			int status = 0;
			if(StringUtils.isNotEmpty(request.getParameter("status"))) {
				status = Integer.parseInt(request.getParameter("status"));
			}
			return this.addVerifyBack(request, dataNo, moduleNo, status, statusName, operateStatusName);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加审核打回的操作日志
	 * @param request HttpServletRequest
	 * @param moduleNo 模块编码
	 * @param status 状态编码
	 * @param statusName 状态名称
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyBack(HttpServletRequest request, int moduleNo, int status, 
			String statusName, String operateStatusName) throws ManagerException {
		try {
			String dataNo = request.getParameter("billNo");
			return this.addVerifyBack(request, dataNo, moduleNo, status, statusName, operateStatusName);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加审核打回的操作日志
	 * @param request HttpServletRequest
	 * @param dataNo 数据编码
	 * @param moduleNo 模块编码
	 * @param status 状态编码
	 * @param statusName 状态名称
	 * @return 新增的数量
	 * @throws ManagerException 
	 */
	public int addVerifyBack(HttpServletRequest request, String dataNo, int moduleNo, int status,
			String statusName, String operateStatusName) throws ManagerException {
		try {
			String []arrDataNo = null;
			if(StringUtils.isNotEmpty(dataNo)) {
				arrDataNo = dataNo.split(",");
			}
			if(arrDataNo == null || arrDataNo.length == 0) {
				return 0;
			}
			int count = 0;
			for(String strNo : arrDataNo) {
				OperateLog operateLog = this.createOperateLog(request, strNo, moduleNo, status, 
						statusName);
				operateLog.setOperateStatus(OperateLogEnums.OperateStatus.BACK.getStatus());
				operateLog.setOperateStatusName(operateStatusName);
				count += operateLogManager.add(operateLog);
			}
			return count;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ManagerException(e.getMessage(), e);
		}
	}
	
	/**
	 * 创建OperateLog对象
	 * @param request HttpServletRequest
	 * @param dataNo 数据编码
	 * @param moduleNo 模块编码
	 * @param status 状态编码
	 * @param statusName 状态名称
	 * @return OperateLog对象
	 */
	private OperateLog createOperateLog(HttpServletRequest request, String dataNo, int moduleNo, 
			int status, String statusName) {
		HttpSession session = request.getSession();
		SystemUser user = (SystemUser) session.getAttribute(PublicConstans.SESSION_USER);
		OperateLog operateLog = new OperateLog();
		operateLog.setId(UUIDGenerator.getUUID());
		operateLog.setDataNo(dataNo);
		operateLog.setOperateNo(OperateLogEnums.OperateAction.VERIFY.getOperateNo());
		operateLog.setModuleNo(moduleNo);
		operateLog.setStatus(status);
		operateLog.setStatusName(statusName);
		operateLog.setCreateUser(user.getUsername());
		operateLog.setCreateTime(new Date());
		return operateLog;
	}
}
