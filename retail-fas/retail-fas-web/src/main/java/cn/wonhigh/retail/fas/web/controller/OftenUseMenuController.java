package cn.wonhigh.retail.fas.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wonhigh.retail.fas.common.model.OftenUseMenu;
import cn.wonhigh.retail.fas.manager.OftenUseMenuManager;
import cn.wonhigh.retail.fas.web.vo.CurrentUser;
import cn.wonhigh.retail.uc.common.api.dto.AuthorityUserModuleDto;
import cn.wonhigh.retail.uc.common.api.service.AuthorityUserApi;

import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.exception.RpcException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-28 15:16:03
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
@RequestMapping("/often_use_menu")
public class OftenUseMenuController extends BaseCrudController<OftenUseMenu> {
    
	private Logger logger = Logger.getLogger(OftenUseMenuController.class);
	
	@Resource
    private OftenUseMenuManager oftenUseMenuManager;
    
    @Resource
    private AuthorityUserApi authorityUserApi;
    

    @Override
    public CrudInfo init() {
        return new CrudInfo("often_use_menu/",oftenUseMenuManager);
    }
    
    @RequestMapping(value = "/findItemsByUserId.json")
	@ResponseBody
	public List<OftenUseMenu> findItemsByUserId(HttpServletRequest req, Model model) throws ManagerException {
		List<OftenUseMenu> returnList = new ArrayList<OftenUseMenu>();
    	try {
    		SystemUser currentUser = CurrentUser.getCurrentUser(req);
			List<OftenUseMenu> list = oftenUseMenuManager.findByBiz(null, null);
			List<AuthorityUserModuleDto> dtoList = authorityUserApi.findAllUserHasModules(currentUser.getUserid(), 15);
			for (OftenUseMenu oftenUseMenu : list) {
				for(AuthorityUserModuleDto moduleDto : dtoList) {
					if(oftenUseMenu.getName().equals(moduleDto.getModuleName())){
						returnList.add(oftenUseMenu);
						break;
					}
				}
			}
		} catch (RpcException e) {
			logger.info(e.getMessage(), e);
		}
		return returnList;
	}
    
    @RequestMapping(value = "/getAuthorityModel.json")
	@ResponseBody
	public List<AuthorityUserModuleDto> findUserHasMenus(HttpServletRequest req, Model model) throws ManagerException {
		
    	List<AuthorityUserModuleDto> list = null;
    	List<AuthorityUserModuleDto> resultList = new ArrayList<AuthorityUserModuleDto>();
		try {
			SystemUser currentUser = CurrentUser.getCurrentUser(req);
			list = authorityUserApi.findAllUserHasModules(currentUser.getUserid(), 15);
			Map<String, AuthorityUserModuleDto> map = new HashMap<String, AuthorityUserModuleDto>();
			for (AuthorityUserModuleDto dto : list) {
				if(map.get(dto.getModuleName())==null){
					map.put(dto.getModuleName(), dto);
				}
			}
			for(AuthorityUserModuleDto dto : map.values()){
				resultList.add(dto);
			}
		} catch (RpcException e) {
			logger.info(e.getMessage(), e);
		}
		return resultList;
	}
    
    
}