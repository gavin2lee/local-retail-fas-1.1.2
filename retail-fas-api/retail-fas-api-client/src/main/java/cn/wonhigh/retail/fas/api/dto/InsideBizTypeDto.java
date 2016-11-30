package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;

public class InsideBizTypeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 145069414257650242L;

	/**
     * 结算公司编号
     */
    private String companyNo;

    /**
     * 结算公司名称
     */
    private String companyName;

    /**
     * 业务类型编号
     */
    private String bizTypeCode;

    /**
     * 业务类型名称
     */
    private String bizTypeName;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 结转成本
     */
    private Integer carryOverCost;
    
    /**
     * 客户编号
     */
    private String clientNo;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 备注，在销售明细成本有为0时，需要增加备注说明
     */
    private String remark;


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

    public String getBizTypeCode() {
        return bizTypeCode;
    }

    public void setBizTypeCode(String bizTypeCode) {
        this.bizTypeCode = bizTypeCode;
    }

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getCarryOverCost() {
        return carryOverCost;
    }

    public void setCarryOverCost(Integer carryOverCost) {
        this.carryOverCost = carryOverCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
	public String toString() {
		return "InsideBizTypeDto [companyNo=" + companyNo + ", companyName="
				+ companyName + ", bizTypeCode=" + bizTypeCode + ", bizTypeName="
				+ bizTypeName + ", status=" + status + ", carryOverCost=" + carryOverCost
				+ ", remark=" + remark +", clientNo=" + clientNo +", clientName=" + clientName + "]";
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
