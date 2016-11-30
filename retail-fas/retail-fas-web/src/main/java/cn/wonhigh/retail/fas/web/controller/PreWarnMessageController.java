package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.PreWarnMessage;
import cn.wonhigh.retail.fas.manager.PreWarnMessageManager;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;
import cn.wonhigh.retail.uc.common.api.dto.AuthorityUserModuleDto;
import cn.wonhigh.retail.uc.common.api.service.AuthorityUserApi;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-01 15:49:31
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
@Controller
@RequestMapping("/pre_warn_message")
public class PreWarnMessageController extends BaseCrudController<PreWarnMessage> {
	
	private Logger logger = Logger.getLogger(PreWarnMessageController.class);
	
    @Resource
    private PreWarnMessageManager preWarnMessageManager;
    
    @Resource
    private AuthorityUserApi authorityUserApi;

    @Override
    public CrudInfo init() {
        return new CrudInfo("pre_warn_message/",preWarnMessageManager);
    }
    
    @RequestMapping("/getCheckMessage")
    @ResponseBody
    public List<PreWarnMessage> checkMessage(HttpServletRequest req, Model model) throws ManagerException {
    	
    	List<PreWarnMessage> returnList = new ArrayList<PreWarnMessage>();
    	try {
    		String type = req.getParameter("type");
//        	SystemUser currentUser = CurrentUser.getCurrentUser(req);
        	returnList = preWarnMessageManager.selectWarnInfoByType(type);
//        	List<PreWarnMessage> list = preWarnMessageManager.selectWarnInfoByType(type);
//    		List<AuthorityUserModuleDto> dtoList = authorityUserApi.findAllUserHasModules(currentUser.getUserid(), 15);
//    		for (PreWarnMessage message : list) {
//    			for(AuthorityUserModuleDto moduleDto : dtoList) {
//    				if(message.getTabTitle().equals(moduleDto.getModuleName())){
//    					returnList.add(message);
//    					break;
//    				}
//    			}
//    		}
		} catch (Exception e) {
			logger.info(e.getMessage() + e);
		}
    	return returnList;
    }
    
}