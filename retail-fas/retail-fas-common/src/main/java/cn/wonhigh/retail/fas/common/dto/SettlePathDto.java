package cn.wonhigh.retail.fas.common.dto;

import org.apache.commons.lang.StringUtils;

import cn.wonhigh.retail.fas.common.enums.SettlePathBillTypeEnums;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.SettlePathExportFormat;
import cn.wonhigh.retail.fas.common.model.SettlePath;

/**
 * 结算路径页面展示相关的dto
 * 
 * @author 杨勇
 * @date 2014-8-6 上午10:46:07
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@ExportFormat(className=SettlePathExportFormat.class)
public class SettlePathDto extends SettlePath {

	private static final long serialVersionUID = -84143873046206030L;

	/**
	 * 品牌名称
	 */
	private String brandUnitName;
	
	/**
	 * 结算分类名称
	 */
	private String settleCategoryName;
	
	/**
	 * 新旧款名称
	 */
	private String styleName;
	
	/**
	 * 单据类型名称
	 */
	private String billTypeName;
	
	/**
	 * 供应商组编号
	 */
	private String supplierGroupNo;
	
	/**
	 * 供应商组名称
	 */
	private String supplierGroupName;
	
	/**
	 * 中间结算公司编号
	 */
	private String middleCompanyNo;
	
	/**
	 * 中间结算公司名称
	 */
	private String middleCompanyName;
	
	/**
	 * 中间结算公司结算依据 
	 */
	private String middleFinancialBasis;
	
	/**
	 * 中间结算公司结算依据文本
	 */
	private String middleFinancialBasisText;
	
	/**
	 * 采购公司编号
	 */
	private String companyNo;
	
	/**
	 * 采购公司名称
	 */
	private String companyName;
	
	
	/**
	 * 采购公司结算依据 
	 */
	private String financialBasis;
	
	/**
	 * 采购公司结算依据文本
	 */
	private String financialBasisText;

	public String getBillTypeName() {
		String retVal = "";
		if(StringUtils.isNotEmpty(getBillType())) {
			String []values = getBillType().split(",");
			for(int i = 0; i < values.length; i++) {
				if(SettlePathBillTypeEnums.ORDERING.getValue().equals(values[i])) {
					retVal += SettlePathBillTypeEnums.ORDERING.getText();
				} else if(SettlePathBillTypeEnums.RESTOCK.getValue().equals(values[i])) {
					retVal += SettlePathBillTypeEnums.RESTOCK.getText();;
				}else if(SettlePathBillTypeEnums.HQ_BUY.getValue().equals(values[i])) {
					retVal += SettlePathBillTypeEnums.HQ_BUY.getText();;
				}
				if(i < (values.length - 1)) {
					retVal += "\\";
				}
			}
		}
		if(getReturnOwnFlag() != null && getReturnOwnFlag().intValue() == 1) {
			if(StringUtils.isNotEmpty(retVal)) {
				retVal += "\\";
			}
			retVal += SettlePathBillTypeEnums.ORIGINAL_RESIDUES.getText();
		}
		billTypeName = retVal;
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	public String getSettleCategoryName() {
		return settleCategoryName;
	}

	public void setSettleCategoryName(String settleCategoryName) {
		this.settleCategoryName = settleCategoryName;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getSupplierGroupNo() {
		return supplierGroupNo;
	}

	public void setSupplierGroupNo(String supplierGroupNo) {
		this.supplierGroupNo = supplierGroupNo;
	}

	public String getSupplierGroupName() {
		return supplierGroupName;
	}

	public void setSupplierGroupName(String supplierGroupName) {
		this.supplierGroupName = supplierGroupName;
	}

	public String getMiddleCompanyNo() {
		return middleCompanyNo;
	}

	public void setMiddleCompanyNo(String middleCompanyNo) {
		this.middleCompanyNo = middleCompanyNo;
	}

	public String getMiddleCompanyName() {
		return middleCompanyName;
	}

	public void setMiddleCompanyName(String middleCompanyName) {
		this.middleCompanyName = middleCompanyName;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMiddleFinancialBasis() {
		return middleFinancialBasis;
	}

	public void setMiddleFinancialBasis(String middleFinancialBasis) {
		this.middleFinancialBasis = middleFinancialBasis;
	}

	public String getMiddleFinancialBasisText() {
		return middleFinancialBasisText;
	}

	public void setMiddleFinancialBasisText(String middleFinancialBasisText) {
		this.middleFinancialBasisText = middleFinancialBasisText;
	}

	public String getFinancialBasis() {
		return financialBasis;
	}

	public void setFinancialBasis(String financialBasis) {
		this.financialBasis = financialBasis;
	}

	public String getFinancialBasisText() {
		return financialBasisText;
	}

	public void setFinancialBasisText(String financialBasisText) {
		this.financialBasisText = financialBasisText;
	}
}
