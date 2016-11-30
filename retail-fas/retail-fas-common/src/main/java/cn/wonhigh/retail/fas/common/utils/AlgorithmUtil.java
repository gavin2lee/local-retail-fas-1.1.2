package cn.wonhigh.retail.fas.common.utils;

import java.math.BigDecimal;

import cn.wonhigh.retail.fas.common.enums.AlgorithmEnums;

public class AlgorithmUtil {
	
	public static BigDecimal algorithmAll(String algorithm, BigDecimal tagPrice, BigDecimal discount1, BigDecimal discount2){
		if(algorithm.equals(AlgorithmEnums.A.getTypeNo())){
			return algorithmA(tagPrice,discount1,discount2);
		}else if(algorithm.equals(AlgorithmEnums.B.getTypeNo())){
			return algorithmB(tagPrice,discount1,discount2);
		}else if(algorithm.equals(AlgorithmEnums.C.getTypeNo())){
			return algorithmC(tagPrice,discount1,discount2);
		}
		return null;
	}
	
	public static BigDecimal algorithmInverseAll(String algorithm, BigDecimal tagPrice, BigDecimal discount1, BigDecimal amount){
		if(algorithm.equals(AlgorithmEnums.A.getTypeNo())){
			return algorithmInverseA(tagPrice,discount1,amount);
		}else if(algorithm.equals(AlgorithmEnums.B.getTypeNo())){
			return algorithmInverseB(tagPrice,discount1,amount);
		}else if(algorithm.equals(AlgorithmEnums.C.getTypeNo())){
			return algorithmInverseC(tagPrice,discount1,amount);
		}
		return null;
	}
	
	/**
	 * 算法A
	 * @param tagPrice
	 * @param discount1
	 * @param discount2
	 * @return
	 */
	public static BigDecimal algorithmA(BigDecimal tagPrice, BigDecimal discount1, BigDecimal discount2){
		BigDecimal resultVal = new BigDecimal(0);
		resultVal = tagPrice.multiply(discount1).setScale(2,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.divide(new BigDecimal(1.17),2,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.multiply(discount2).multiply(new BigDecimal(1.17)).setScale(2,BigDecimal.ROUND_HALF_UP);
		return resultVal;
	}
	
	/**
	 * 反算算法A折扣2
	 * @param tagPrice
	 * @param discount1
	 * @param discount2
	 * @return
	 */
	public static BigDecimal algorithmInverseA(BigDecimal tagPrice, BigDecimal discount1, BigDecimal amount){
		BigDecimal resultVal = new BigDecimal(0);
		resultVal = tagPrice.multiply(discount1).setScale(2,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.divide(new BigDecimal(1.17),2,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.multiply(new BigDecimal(1.17)).setScale(2, BigDecimal.ROUND_HALF_UP);
		resultVal = amount.divide(resultVal,20,BigDecimal.ROUND_HALF_UP);
		return resultVal;
	}
	
	/**
	 * 算法B
	 * @param tagPrice
	 * @param discount1
	 * @param discount2
	 * @return
	 */
	public static BigDecimal algorithmB(BigDecimal tagPrice, BigDecimal discount1, BigDecimal discount2){
		BigDecimal resultVal = new BigDecimal(0);
		resultVal = tagPrice.multiply(discount1).setScale(0,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.divide(new BigDecimal(1.17),2,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.multiply(discount2).multiply(new BigDecimal(1.17)).setScale(2,BigDecimal.ROUND_HALF_UP);
		return resultVal;
	}
	
	/**
	 * 反算算法B折扣2
	 * @param tagPrice
	 * @param discount1
	 * @param discount2
	 * @return
	 */
	public static BigDecimal algorithmInverseB(BigDecimal tagPrice, BigDecimal discount1, BigDecimal amount){
		BigDecimal resultVal = new BigDecimal(0);
		resultVal = tagPrice.multiply(discount1).setScale(0,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.divide(new BigDecimal(1.17),2,BigDecimal.ROUND_HALF_UP);
		resultVal = resultVal.multiply(new BigDecimal(1.17)).setScale(2, BigDecimal.ROUND_HALF_UP);
		resultVal = amount.divide(resultVal,20,BigDecimal.ROUND_HALF_UP);
		return resultVal;
	}
	
	/**
	 * 算法C
	 * @param tagPrice
	 * @param discount1
	 * @param discount2
	 * @return
	 */
	public static BigDecimal algorithmC(BigDecimal tagPrice, BigDecimal discount1,BigDecimal discount2){
		BigDecimal resultVal = new BigDecimal(0);
		resultVal = tagPrice.multiply(discount1).multiply(discount2).setScale(2,BigDecimal.ROUND_HALF_UP);
		return resultVal;
	}
	
	/**
	 * 反算算法C折扣2
	 * @param tagPrice
	 * @param discount1
	 * @param discount2
	 * @return
	 */
	public static BigDecimal algorithmInverseC(BigDecimal tagPrice, BigDecimal discount1, BigDecimal amount){
		BigDecimal resultVal = new BigDecimal(0);
		resultVal = tagPrice.multiply(discount1).setScale(2,BigDecimal.ROUND_HALF_UP);
		resultVal = amount.divide(resultVal,20,BigDecimal.ROUND_HALF_UP);
		return resultVal;
	}
}
