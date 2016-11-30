package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.ShardingUtil;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-02-27 10:37:34
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
@ExportFormat(className=AbstractExportFormat.class)
public class BillBacksectionSplit extends FasBaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 回款单据编号
     */
    private String backsectionNo;

    /**
     * 结算公司编码
     */
    @ExcelCell("A")
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 回款方类型(1-商场，2-商业集团)
     */
    private Byte backType;

    /**
     * 回款方编码
     */
    @ExcelCell("B")
    private String backNo;

    /**
     * 回款方名称
     */
    private String backName;

    /**
     * 回款金额
     */
    @ExcelCell("D")
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal backAmount;

    /**
     * 回款日期
     */
    @ExcelCell("C")
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date backDate;

    /**
     * 应回金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal receiveAmount;

    /**
     * 未收金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal notreceiveAmount;

    /**
     * 回款差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal diffAmount;

    /**
     * 差异原因
     */
    private String diffReason;

    /**
     * 备注
     */
    @ExcelCell("E")
    private String remark;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	/**
     * 
     * {@linkplain #backsectionNo}
     *
     * @return the value of bill_backsection_split.backsection_no
     */
    public String getBacksectionNo() {
        return backsectionNo;
    }

    /**
     * 
     * {@linkplain #backsectionNo}
     * @param backsectionNo the value for bill_backsection_split.backsection_no
     */
    public void setBacksectionNo(String backsectionNo) {
        this.backsectionNo = backsectionNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_backsection_split.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_backsection_split.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));    
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_backsection_split.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_backsection_split.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #backType}
     *
     * @return the value of bill_backsection_split.balance_type
     */
    public Byte getbackType() {
        return backType;
    }

    /**
     * 
     * {@linkplain #backType}
     * @param backType the value for bill_backsection_split.balance_type
     */
    public void setbackType(Byte backType) {
        this.backType = backType;
    }

    /**
     * 
     * {@linkplain #backNo}
     *
     * @return the value of bill_backsection_split.back_no
     */
    public String getBackNo() {
        return backNo;
    }

    /**
     * 
     * {@linkplain #backNo}
     * @param backNo the value for bill_backsection_split.back_no
     */
    public void setBackNo(String backNo) {
        this.backNo = backNo;
    }

    /**
     * 
     * {@linkplain #backName}
     *
     * @return the value of bill_backsection_split.back_name
     */
    public String getBackName() {
        return backName;
    }

    /**
     * 
     * {@linkplain #backName}
     * @param backName the value for bill_backsection_split.back_name
     */
    public void setBackName(String backName) {
        this.backName = backName;
    }

    /**
     * 
     * {@linkplain #backAmount}
     *
     * @return the value of bill_backsection_split.back_amount
     */
    public BigDecimal getBackAmount() {
        return backAmount;
    }

    /**
     * 
     * {@linkplain #backAmount}
     * @param backAmount the value for bill_backsection_split.back_amount
     */
    public void setBackAmount(BigDecimal backAmount) {
        this.backAmount = backAmount;
    }

    /**
     * 
     * {@linkplain #backDate}
     *
     * @return the value of bill_backsection_split.back_date
     */
    public Date getBackDate() {
        return backDate;
    }

    /**
     * 
     * {@linkplain #backDate}
     * @param backDate the value for bill_backsection_split.back_date
     */
    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    /**
     * 
     * {@linkplain #receiveAmount}
     *
     * @return the value of bill_backsection_split.receive_amount
     */
    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    /**
     * 
     * {@linkplain #receiveAmount}
     * @param receiveAmount the value for bill_backsection_split.receive_amount
     */
    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**
     * 
     * {@linkplain #notreceiveAmount}
     *
     * @return the value of bill_backsection_split.notreceive_amount
     */
    public BigDecimal getNotreceiveAmount() {
        return notreceiveAmount;
    }

    /**
     * 
     * {@linkplain #notreceiveAmount}
     * @param notreceiveAmount the value for bill_backsection_split.notreceive_amount
     */
    public void setNotreceiveAmount(BigDecimal notreceiveAmount) {
        this.notreceiveAmount = notreceiveAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     *
     * @return the value of bill_backsection_split.diff_amount
     */
    public BigDecimal getDiffAmount() {
        return diffAmount;
    }

    /**
     * 
     * {@linkplain #diffAmount}
     * @param diffAmount the value for bill_backsection_split.diff_amount
     */
    public void setDiffAmount(BigDecimal diffAmount) {
        this.diffAmount = diffAmount;
    }

    /**
     * 
     * {@linkplain #diffReason}
     *
     * @return the value of bill_backsection_split.diff_reason
     */
    public String getDiffReason() {
        return diffReason;
    }

    /**
     * 
     * {@linkplain #diffReason}
     * @param diffReason the value for bill_backsection_split.diff_reason
     */
    public void setDiffReason(String diffReason) {
        this.diffReason = diffReason;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of bill_backsection_split.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for bill_backsection_split.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}