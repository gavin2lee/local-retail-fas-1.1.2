package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;

/**
 * 结算路径查询导出相关的dto类
 * 
 * @author yang.y
 */
@ExportFormat(className=AbstractExportFormat.class)
public class SettlePathQueryExportDto extends SettlePathDto {

	private static final long serialVersionUID = -5629753275572546601L;

	private Integer pathOrder;
	
	private String companyNo;
	
	private String companyName;
	
	private String financialBasis;
	
	private String financialBasisText;

	public Integer getPathOrder() {
		return pathOrder;
	}

	public void setPathOrder(Integer pathOrder) {
		this.pathOrder = pathOrder;
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
