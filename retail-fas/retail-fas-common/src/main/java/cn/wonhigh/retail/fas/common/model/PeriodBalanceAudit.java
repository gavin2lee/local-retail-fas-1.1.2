package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-12-28 17:10:06
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
public class PeriodBalanceAudit implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1921336466177909840L;

	/**
     * id
     */
    private int id;
    
    /**
     * 公司No
     */
    private String companyNo;

    /**
     * 公司列表
     */
    private List<String> companyNos;

    /**
     * 公司名称
     */
    private String companyName;
    
    /**
     * 年份
     */
    private String year;

    /**
     * 月份
     */
    private String month;
    
    /**
     * 品牌部编码
     */
    private String brandUnitNo;

    /**
     * 品牌部名称
     */
    private String brandUnitName;

    /**
     * 状态(0 = 运行中 1 = 正常 2 = 失败)
     */
    private Byte status;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of period_balance_audit.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for period_balance_audit.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of period_balance_audit.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for period_balance_audit.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
     * 
     * {@linkplain #year}
     *
     * @return the value of period_balance_audit.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for period_balance_audit.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of period_balance_audit.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for period_balance_audit.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public String getBrandUnitName() {
		return brandUnitName;
	}

	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}

	/**
     * 
     * {@linkplain #status}
     *
     * @return the value of period_balance_audit.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for period_balance_audit.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of period_balance_audit.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for period_balance_audit.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of period_balance_audit.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for period_balance_audit.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of period_balance_audit.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for period_balance_audit.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of period_balance_audit.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for period_balance_audit.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public List<String> getCompanyNos() {
		return companyNos;
	}

	public void setCompanyNos(List<String> companyNos) {
		this.companyNos = companyNos;
	}
}