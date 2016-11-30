package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 类别信息
 * @author ouyang.zm
 * @date  2014-08-25 13:52:36
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
public class Category implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8599239896583385497L;

	/**
     * 货品分类ID
     */
    private Integer id;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别外码
     */
    private String code;

    /**
     * 类别级别(第一级为1)
     */
    private Integer levelid;

    /**
     * 类别特征码(1位,当类别级别为1大类时，必须输入且长度必须1位)
     */
    private String opcode;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 上级类别ID
     */
    private Integer parentId;

    /**
     * 分类路径
     */
    private String path;

    /**
     * 类别状态(0 = 禁用 1 = 正常)
     */
    private Byte status;

    /**
     * 检索码
     */
    private String searchCode;

    /**
     * 所属业务单元
     */
    private String sysNo;

    /**
     * 类型(0 普通节点,1 叶子节点)
     */
    private Byte type;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
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
     * 备注
     */
    private String remark;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of category.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for category.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of category.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for category.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of category.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for category.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #levelid}
     *
     * @return the value of category.levelid
     */
    public Integer getLevelid() {
        return levelid;
    }

    /**
     * 
     * {@linkplain #levelid}
     * @param levelid the value for category.levelid
     */
    public void setLevelid(Integer levelid) {
        this.levelid = levelid;
    }

    /**
     * 
     * {@linkplain #opcode}
     *
     * @return the value of category.opcode
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * 
     * {@linkplain #opcode}
     * @param opcode the value for category.opcode
     */
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of category.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for category.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #parentId}
     *
     * @return the value of category.parent_id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 
     * {@linkplain #parentId}
     * @param parentId the value for category.parent_id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 
     * {@linkplain #path}
     *
     * @return the value of category.path
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     * {@linkplain #path}
     * @param path the value for category.path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of category.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for category.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #searchCode}
     *
     * @return the value of category.search_code
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * 
     * {@linkplain #searchCode}
     * @param searchCode the value for category.search_code
     */
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of category.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for category.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #type}
     *
     * @return the value of category.type
     */
    public Byte getType() {
        return type;
    }

    /**
     * 
     * {@linkplain #type}
     * @param type the value for category.type
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of category.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for category.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of category.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for category.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of category.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for category.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of category.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for category.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of category.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for category.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}