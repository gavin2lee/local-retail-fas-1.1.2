package cn.wonhigh.retail.fas.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import cn.wonhigh.retail.fas.common.dto.POSOcGroupRootCategoryDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOcGroupPromationDto;
import cn.wonhigh.retail.fas.common.dto.POSOcOcGroupWildCardDto;
import cn.wonhigh.retail.fas.common.model.POSOcGroupRootCategory;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupPromation;
import cn.wonhigh.retail.fas.common.model.POSOcOcGroupWildCard;
import cn.wonhigh.retail.fas.common.model.OrderMain;
import cn.wonhigh.retail.fas.common.model.OrderPayway;
import cn.wonhigh.retail.fas.common.model.ReturnExchangeMain;
import cn.wonhigh.retail.pos.api.client.utils.CommonUtils;

/**
 * TODO: 汇总相关DataSet
 * 
 * @author zhang.lh
 * @date 2014-12-4 下午2:20:44
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
public class GroupOderDataSet {
	
 	/**
	 * DESC: OcGroupRootCategoryList 转化 OcGroupRootCategoryDtoList
	 * @param ocGroupRootCategory
	 * @return
	 * @throws Exception
	 */
	public static List<POSOcGroupRootCategoryDto> convertOcGroupRootCategoryList(
			List<POSOcGroupRootCategory> ocGroupRootCategory ) throws Exception{
 		if (!CommonUtils.hasValue(ocGroupRootCategory)) {
			return null;
		}
		try {
 			List<POSOcGroupRootCategoryDto> ocGroupOrderPaywayDtoList = new ArrayList<POSOcGroupRootCategoryDto>();
			for (int i = 0,size=ocGroupRootCategory.size(); i < size; i++) {
				POSOcGroupRootCategoryDto ocGroupRootCategoryDto = new POSOcGroupRootCategoryDto();
				PropertyUtils.copyProperties(ocGroupRootCategoryDto, ocGroupRootCategory.get(i));
				ocGroupOrderPaywayDtoList.add(ocGroupRootCategoryDto);
			}
			ocGroupRootCategory.clear();
 			return ocGroupOrderPaywayDtoList;
 		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
	}
	
	
 	/**
	 * DESC: ocOcGroupPromationList 转化 OcOcGroupPromationDto
	 * @param ocGroupRootCategory
	 * @return
	 * @throws Exception
	 */
	public static List<POSOcOcGroupPromationDto> convertOcOcGroupPromationList(
			List<POSOcOcGroupPromation> ocOcGroupPromationList ) throws Exception{
 		if (!CommonUtils.hasValue(ocOcGroupPromationList)) {
			return null;
		}
		try {
 			List<POSOcOcGroupPromationDto> ocOcGroupPromationDtoList = new ArrayList<POSOcOcGroupPromationDto>();
			for (int i = 0,size=ocOcGroupPromationList.size(); i < size; i++) {
				POSOcOcGroupPromationDto ocOcGroupPromationDto = new POSOcOcGroupPromationDto();
				PropertyUtils.copyProperties(ocOcGroupPromationDto, ocOcGroupPromationList.get(i));
				ocOcGroupPromationDtoList.add(ocOcGroupPromationDto);
			}
			ocOcGroupPromationList.clear();
 			return ocOcGroupPromationDtoList;
 		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
	}
	
	

	
	
 	/**
	 * DESC: ocOcGroupWildCardList 转化 OcOcGroupWildCardDto
	 * @param ocOcGroupWildCardList
	 * @return
	 * @throws Exception
	 */
	public static List<POSOcOcGroupWildCardDto> convertOcOcGroupWildCardList(
			List<POSOcOcGroupWildCard> ocOcGroupWildCardList ) throws Exception{
 		if (!CommonUtils.hasValue(ocOcGroupWildCardList)) {
			return null;
		}
		try {
 			List<POSOcOcGroupWildCardDto> ocOcGroupWildCardDtoList = new ArrayList<POSOcOcGroupWildCardDto>();
			for (int i = 0,size=ocOcGroupWildCardList.size(); i < size; i++) {
				POSOcOcGroupWildCardDto ocOcGroupWildCardDto = new POSOcOcGroupWildCardDto();
				PropertyUtils.copyProperties(ocOcGroupWildCardDto, ocOcGroupWildCardList.get(i));
				ocOcGroupWildCardDtoList.add(ocOcGroupWildCardDto);
			}
			ocOcGroupWildCardList.clear();
 			return ocOcGroupWildCardDtoList;
 		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
	}
	
}
