package cn.wonhigh.retail.fas.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;


/**
 * 对象转化类 
 * @author tang.yc
 * @date 2014-9-28 上午10:28:57
 * @version 0.1.0 
 * @copyright wonhigh.cn 
 */
public class CommonConvertModelWithDto<ModelType, ModelTypeDto>{
	/**
	 * DESC: model 转化 dto
	 * @param ModelType
	 * @param classType ModelTypeDto.class
	 * @return ModelTypeDto
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelTypeDto convertDto(ModelType modelType, Class<?> classType) throws Exception{
		try {
			if (null == modelType){
				return null;
			}
			
			ModelTypeDto modelTypeDto = (ModelTypeDto) classType.newInstance();
			PropertyUtils.copyProperties(modelTypeDto, modelType);
			return modelTypeDto;
		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
	}
	
	/**
	 * DESC:  dto 转化 model
	 * @param ModelTypeDto
	 * @param classType ModelType.class
	 * @return ModelType
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelType convertModel(ModelTypeDto modelTypeDto, Class<?> classType) throws Exception{
		try {
			if (null == modelTypeDto){
				return null;
			}
			
			ModelType modelType = (ModelType) classType.newInstance();
			PropertyUtils.copyProperties(modelType, modelTypeDto);
			return modelType;
		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
	}
	
	/**
	 * DESC: 检验list是否有值 
	 * @param list
	 * @return true = 有值
	 */
	public static <T> boolean hasValue(List<T> list) {
		return ((null != list) && (list.size() > 0));
	}
	
	/**
	 * DESC: List<ModelType> 转化  List<ModelTypeDto>
	 * @param List<ModelType>
	 * @param classType ModelTypeDto.class
	 * @return List<ModelTypeDto>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ModelTypeDto> convertListDto(List<ModelType> modelTypeList, Class<?> classType) throws Exception{
		try {
			if (!hasValue(modelTypeList)) {
				return null;
			}
			
			List<ModelTypeDto> modelTypeDtoList = new ArrayList<ModelTypeDto>();
			for (ModelType modelType: modelTypeList) {
				ModelTypeDto modelTypeDto = (ModelTypeDto) classType.newInstance();
				PropertyUtils.copyProperties(modelTypeDto, modelType);
				modelTypeDtoList.add(modelTypeDto);
			}
			return modelTypeDtoList;
		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
	}
	
	/**
	 * DESC:  List<ModelTypeDto> 转化  List<ModelType>
	 * @param List<ModelTypeDto>
	 * @param classType ModelType.class
	 * @return List<ModelType>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ModelType> convertListModel(List<ModelTypeDto> modelTypeDtoList, Class<?> classType) throws Exception{
		try {
			if (!hasValue(modelTypeDtoList)) {
				return null;
			}
			
			List<ModelType> modelTypeList = new ArrayList<ModelType>();
			for (ModelTypeDto mdelTypeDto: modelTypeDtoList) {
				ModelType modelType = (ModelType) classType.newInstance();
				PropertyUtils.copyProperties(modelType, mdelTypeDto);
				modelTypeList.add(modelType);
			}
			return modelTypeList;
		} catch (Exception e) {
			throw new Exception("转化对象失败", e);
		}
	}
}