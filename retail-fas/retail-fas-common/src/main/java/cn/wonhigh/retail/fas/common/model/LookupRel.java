package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-08-29 11:31:48
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
public class LookupRel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2176742371233072127L;

	/**
     * 关系ID
     */
    private Integer id;

    /**
     * 字典编号
     */
    private Integer lookupId;

    /**
     * 字典项编号
     */
    private String lookupEntryNo;

    /**
     * 子字典编号
     */
    private Integer subLookupId;

    /**
     * 子字典项编号
     */
    private String subLpEntryNo;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of lookup_rel.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for lookup_rel.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #lookupId}
     *
     * @return the value of lookup_rel.lookup_id
     */
    public Integer getLookupId() {
        return lookupId;
    }

    /**
     * 
     * {@linkplain #lookupId}
     * @param lookupId the value for lookup_rel.lookup_id
     */
    public void setLookupId(Integer lookupId) {
        this.lookupId = lookupId;
    }

    /**
     * 
     * {@linkplain #lookupEntryId}
     *
     * @return the value of lookup_rel.lookup_entry_id
     */
    public String getLookupEntryNo() {
        return lookupEntryNo;
    }

    /**
     * 
     * {@linkplain #lookupEntryId}
     * @param lookupEntryId the value for lookup_rel.lookup_entry_id
     */
    public void setLookupEntryNo(String lookupEntryNo) {
        this.lookupEntryNo = lookupEntryNo;
    }

    /**
     * 
     * {@linkplain #subLookupId}
     *
     * @return the value of lookup_rel.sub_lookup_id
     */
    public Integer getSubLookupId() {
        return subLookupId;
    }

    /**
     * 
     * {@linkplain #subLookupId}
     * @param subLookupId the value for lookup_rel.sub_lookup_id
     */
    public void setSubLookupId(Integer subLookupId) {
        this.subLookupId = subLookupId;
    }

    /**
     * 
     * {@linkplain #subLpEntryId}
     *
     * @return the value of lookup_rel.sub_lp_entry_id
     */
    public String getSubLpEntryNo() {
        return subLpEntryNo;
    }

    /**
     * 
     * {@linkplain #subLpEntryId}
     * @param subLpEntryId the value for lookup_rel.sub_lp_entry_id
     */
    public void setSubLpEntryNo(String subLpEntryNo) {
        this.subLpEntryNo = subLpEntryNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of lookup_rel.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for lookup_rel.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of lookup_rel.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for lookup_rel.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of lookup_rel.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for lookup_rel.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of lookup_rel.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for lookup_rel.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}