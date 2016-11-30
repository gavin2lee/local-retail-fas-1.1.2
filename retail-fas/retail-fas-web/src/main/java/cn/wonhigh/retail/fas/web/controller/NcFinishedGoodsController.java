package cn.wonhigh.retail.fas.web.controller;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.wonhigh.retail.fas.common.model.BrandUnit;
import cn.wonhigh.retail.fas.common.model.Category;
import cn.wonhigh.retail.fas.common.model.Company;
import cn.wonhigh.retail.fas.common.model.NcFinishedGoods;
import cn.wonhigh.retail.fas.common.utils.ExcelImporterUtils;
import cn.wonhigh.retail.fas.manager.BrandUnitManager;
import cn.wonhigh.retail.fas.manager.CategoryManager;
import cn.wonhigh.retail.fas.manager.CompanyManager;
import cn.wonhigh.retail.fas.manager.NcFinishedGoodsManager;

import com.google.common.collect.Lists;
import com.yougou.logistics.base.common.contains.PublicContains;
import com.yougou.logistics.base.common.exception.ManagerException;
import com.yougou.logistics.base.common.model.SystemUser;
import com.yougou.logistics.base.web.controller.BaseCrudController;

/**
 * NC库存商品导入模板 
 * @author xu.j
 * @date  2016-05-23 16:36:42
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
@Controller
@RequestMapping("/nc_finished_goods")
public class NcFinishedGoodsController extends BaseCrudController<NcFinishedGoods> {
    @Resource
    private NcFinishedGoodsManager ncFinishedGoodsManager;
    
    @Resource
    private CompanyManager companyManager;
    
    @Resource
    private BrandUnitManager brandUnitManager;
    
    @Resource
    private CategoryManager categoryManager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("nc_finished_goods/",ncFinishedGoodsManager);
    }
    
    @RequestMapping("/ncFinishedGoodsList")
	public String ncFinishedGoods() throws ManagerException {
		return "nc_finished_goods/list";
	}
    
    @Override
	public Map<String, Object> builderParams(HttpServletRequest req, Model model) {
		Map<String, Object> map=super.builderParams(req, model);
		if(map.containsKey("companyNos") && StringUtils.isNotBlank(map.get("companyNos").toString())){
			String[] companyNos = map.get("companyNos").toString().split(",");
			map.put("companyNos", companyNos);
		}
		if(map.containsKey("brandUnitNos") && StringUtils.isNotBlank(map.get("brandUnitNos").toString())){
			String[] brandUnitNos = map.get("brandUnitNos").toString().split(",");
			map.put("brandUnitNos", brandUnitNos);
		}
		return map;
	}
    
    /**
	  * 导入
	  * @param file
	  * @param request
	  * @return
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/do_import")
	public ModelAndView doImport(HttpServletRequest request, NcFinishedGoods modelType)throws Exception{
		ModelAndView mv = new ModelAndView("/print/import");
		try {
			SystemUser loginUser = (SystemUser) request.getSession().getAttribute(PublicContains.SESSION_USER);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile excelFile = multipartRequest.getFile("fileData");
			Class<NcFinishedGoods> entityClass = (Class<NcFinishedGoods>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			// 忽略在excel有字段填写的值
			List<String> ignoreProperties = Lists.newArrayList();
			for (Entry<String, String> entry : ExcelImporterUtils.getExcelCells(entityClass,entityClass.getSimpleName()).entrySet()) {
				ignoreProperties.add(entry.getKey());
			}
			List<NcFinishedGoods> datas = ExcelImporterUtils.importSheet(excelFile.getInputStream(), NcFinishedGoods.class,entityClass.getSimpleName());
			// 数据校验
			List<Object> error = getDataValidators(datas, request);
			StringBuilder errorBuilder = new StringBuilder();
			if (null != error && error.size() > 0) {
				for (int i = 0; i < error.size(); i++) {
					errorBuilder.append(error.get(i) + "<br/>");
				}
			}
			if (errorBuilder.length() == 0) {
	    		int count = 0;
	    		for (NcFinishedGoods ncFinishedGoods : datas) {
	    			if (ncFinishedGoods.getDirection() == 0){
	    				//方向为平的数据不保存到数据库
	    				continue;
	    			}
	    			
	    			if (count == 0) {
	    				//先删除此公司此月或许已经存在在数据库的数据
	    				Map<String,Object> params = new HashMap<String,Object>();
	    				params.put("dCompanyNo", ncFinishedGoods.getCompanyNo());
	    				params.put("dYear", ncFinishedGoods.getYear());
	    				params.put("dMonth", ncFinishedGoods.getMonth());
	    				ncFinishedGoodsManager.deleteData(params);
	    			}
	    			
	    			ncFinishedGoods.setCreateUser(loginUser.getUsername());
	    			ncFinishedGoods.setCreateTime(new Date());
	    			ncFinishedGoods.setUpdateUser(loginUser.getUsername());
	    			ncFinishedGoods.setUpdateTime(new Date());
	    			
	    			//新增数据
	    			ResponseEntity<NcFinishedGoods> resEntiy = this.add(ncFinishedGoods);
	    			
	    			count ++;
	    		}
	    		
	    		if (count > 0) {
					mv.addObject("success", "成功导入" + count + "条记录");
				} else{
					mv.addObject("error", "没有要导入的行！");
				}
			} else {
				mv.addObject("error", errorBuilder);
			}
			return mv;
	    	
		}catch (Exception e) {
			mv.addObject("error", "导入数据发生其他异常，请联系管理员");
			return mv;
		}
	}
	
	/**
	 * 校验数据方法
	 * @param datas List<NcFinishedGoods>
	 * @param request HttpServletRequest
	 * @return 返回校验错误信息集合
	 */
	private List<Object> getDataValidators(List<NcFinishedGoods> datas, HttpServletRequest request) throws ManagerException{
		List<Object> errors = new ArrayList<Object>();
		if(datas != null && datas.size() > 0) {
			
			int lineNum = 2;
			String errorMessage = null;
			
			for(NcFinishedGoods ncFinishedGoods : datas) {
				
				//判断空数据
				if(StringUtils.isEmpty(ncFinishedGoods.getCompanyNo()) 
						|| StringUtils.isEmpty(ncFinishedGoods.getBrandUnitNo())
						|| StringUtils.isEmpty(ncFinishedGoods.getCategoryName())) {
					errorMessage = "第" + lineNum + "行数据有空记录";
					errors.add(errorMessage);
					lineNum ++;
					continue;
				}
				
				String organTypeNo = null;
				
				//判断店铺是否存在
				Map<String, Object> paramsCompany = new HashMap<String, Object>();
				paramsCompany.put("companyNo", ncFinishedGoods.getCompanyNo());
				List<Company> companyList = companyManager.findByBiz(null, paramsCompany);
				if (CollectionUtils.isEmpty(companyList)) {
					errorMessage = "第" + lineNum + "行数据的公司编号不存在";
					errors.add(errorMessage);
					lineNum ++;
					continue;
				}
				else {
					organTypeNo = companyList.get(0).getOrganTypeNo();
					ncFinishedGoods.setCompanyName(companyList.get(0).getName());
				}
				
				//判断品牌部是否存在
				Map<String, Object> paramsBrandUnit = new HashMap<String, Object>();
				paramsBrandUnit.put("brandUnitNo", ncFinishedGoods.getBrandUnitNo());
				List<BrandUnit> brandUnitList = brandUnitManager.findByBiz(null, paramsBrandUnit);
				if (CollectionUtils.isEmpty(brandUnitList)) {
					errorMessage = "第" + lineNum + "行数据的品牌部编号不存在";
					errors.add(errorMessage);
					lineNum ++;
					continue;
				}
				else {
					ncFinishedGoods.setBrandUnitName(brandUnitList.get(0).getName());
				}
				
				//判断大类是否存在
				Map<String, Object> paramsCategory = new HashMap<String, Object>();
				paramsCategory.put("categoryName", ncFinishedGoods.getCategoryName().equals("包") ? "包饰" : ncFinishedGoods.getCategoryName());
				paramsCategory.put("organTypeNo",organTypeNo);
				paramsCategory.put("levelid","1"); //找一级大类
				List<Category> categoryList = categoryManager.findByBiz(null, paramsCategory);
				if (CollectionUtils.isEmpty(categoryList)) {
					errorMessage = "第" + lineNum + "行数据的大类不存在";
					errors.add(errorMessage);
					lineNum ++;
					continue;
				}
				else {
					ncFinishedGoods.setCategoryNo(categoryList.get(0).getCategoryNo());
				}
				
				//方向（1：借，2：贷）
				if (StringUtils.isNotEmpty(ncFinishedGoods.getDirectionName())){
					if (ncFinishedGoods.getDirectionName().equals("借")){
						ncFinishedGoods.setDirection(1);
					}else if (ncFinishedGoods.getDirectionName().equals("贷")){
						//贷方保留负数
						ncFinishedGoods.setAmount((ncFinishedGoods.getAmount() != null && 
								ncFinishedGoods.getAmount().compareTo(BigDecimal.ZERO) == 1) ?
							    ncFinishedGoods.getAmount().negate() : ncFinishedGoods.getAmount());
						ncFinishedGoods.setDirection(2);
					}else{
						ncFinishedGoods.setDirection(0);
					}
				}
				
				lineNum ++;
			}
			
		}
		
		return errors;
	}
	
	@RequestMapping(value = "/deleteData")
	public ResponseEntity<Map<String, Boolean>> deleteData(HttpServletRequest request) throws ManagerException {
		Map<String, Boolean> flag = new HashMap<String, Boolean>();
		
		Map<String, Object> params = new HashMap<String, Object>();;
		String ids = request.getParameter("ids");
		if(StringUtils.isNotBlank(ids)) {
			String[] temps = ids.split(",");
			List<String> tempList = Arrays.asList(temps);
			params.put("ids", tempList);
			ncFinishedGoodsManager.deleteData(params);
			flag.put("result", true);
		}else{
			flag.put("result", false);
		}
		
		return new ResponseEntity<Map<String, Boolean>>(flag, HttpStatus.OK);
	}
}