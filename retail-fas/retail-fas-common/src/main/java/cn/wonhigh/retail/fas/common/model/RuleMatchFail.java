package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-09-15 17:42:50
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
public class RuleMatchFail implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6646650953262227015L;

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 商品编码
     */
    private String itemNo;
    
    /**
     * itemCode
     */
    private String itemCode;

    /**
     * 地区编码
     */
    private String zoneNo;
    
    /**
     * 品牌编码
     */
    private String brandNo;
    
    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 0-失败,1-成功
     */
    private Integer status;
    
    private String statusName;

    /**
     * 0-总部匹配，1-地区匹配
     */
    private Integer matchType;
    
    private String matchTypeName;

    /**
     * 1-厂商组未匹配，其他参考枚举对象
     */
    private Integer failReason;
    
    private String failReasonName;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of rule_match_fail.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for rule_match_fail.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of rule_match_fail.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for rule_match_fail.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of rule_match_fail.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for rule_match_fail.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
     * 
     * {@linkplain #status}
     *
     * @return the value of rule_match_fail.status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for rule_match_fail.status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #matchType}
     *
     * @return the value of rule_match_fail.match_type
     */
    public Integer getMatchType() {
        return matchType;
    }

    /**
     * 
     * {@linkplain #matchType}
     * @param matchType the value for rule_match_fail.match_type
     */
    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    /**
     * 
     * {@linkplain #failReason}
     *
     * @return the value of rule_match_fail.fail_reason
     */
    public Integer getFailReason() {
        return failReason;
    }

    /**
     * 
     * {@linkplain #failReason}
     * @param failReason the value for rule_match_fail.fail_reason
     */
    public void setFailReason(Integer failReason) {
        this.failReason = failReason;
    }

    public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getMatchTypeName() {
		return matchTypeName;
	}

	public void setMatchTypeName(String matchTypeName) {
		this.matchTypeName = matchTypeName;
	}

	public String getFailReasonName() {
		return failReasonName;
	}

	public void setFailReasonName(String failReasonName) {
		this.failReasonName = failReasonName;
	}

	/**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of rule_match_fail.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for rule_match_fail.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of rule_match_fail.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for rule_match_fail.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}