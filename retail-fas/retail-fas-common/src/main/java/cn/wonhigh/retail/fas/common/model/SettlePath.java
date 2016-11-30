package cn.wonhigh.retail.fas.common.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 结算路径
 * @author yang.y
 * @date  2014-08-27 14:16:31
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
public class SettlePath extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = 5441129422487612666L;

	/**
     * 结算路径编码
     */
    private String pathNo;

    /**
     * 结算路径名称
     */
    private String name;

    /**
     * 单据依据
     */
    private Integer billBasis;

    /**
     * 单据类型(可多选)
     */
    private String billType;

    private String[] billTypes;
    
    /**
     * 大类编码
     */
    private String settleCategoryNo;

    /**
     * 新旧款编码
     */
    private String styleNo;

    /**
     * 启用日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date startDate;

    /**
     * 终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date endDate;
    
    /**
     * 是否原厂退残
     */
    private Integer returnOwnFlag;

    private String organTypeNo;
    
    public Integer getReturnOwnFlag() {
		return returnOwnFlag;
	}

	public void setReturnOwnFlag(Integer returnOwnFlag) {
		this.returnOwnFlag = returnOwnFlag;
	}

	/**
     * 
     * {@linkplain #pathNo}
     *
     * @return the value of settle_path.path_no
     */
    public String getPathNo() {
        return pathNo;
    }

    /**
     * 
     * {@linkplain #pathNo}
     * @param pathNo the value for settle_path.path_no
     */
    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of settle_path.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for settle_path.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #billBasis}
     *
     * @return the value of settle_path.bill_basis
     */
    public Integer getBillBasis() {
        return billBasis;
    }

    /**
     * 
     * {@linkplain #billBasis}
     * @param billBasis the value for settle_path.bill_basis
     */
    public void setBillBasis(Integer billBasis) {
        this.billBasis = billBasis;
    }

    public String getBillType() {
    	if(billTypes != null && billTypes.length > 0) {
    		return StringUtils.join(billTypes, ",");
    	}
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String[] getBillTypes() {
		if(StringUtils.isNotEmpty(billType)) {
			return StringUtils.split(billType, ",");
		}
		return new String[0];
	}

	public void setBillTypes(String[] billTypes) {
		this.billTypes = billTypes;
	}

	/**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of settle_path.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * {@linkplain #startDate}
     * @param startDate the value for settle_path.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of settle_path.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     * @param endDate the value for settle_path.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public String getSettleCategoryNo() {
		return settleCategoryNo;
	}

	public void setSettleCategoryNo(String settleCategoryNo) {
		this.settleCategoryNo = settleCategoryNo;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getOrganTypeNo() {
		return organTypeNo;
	}

	public void setOrganTypeNo(String organTypeNo) {
		this.organTypeNo = organTypeNo;
	}
}