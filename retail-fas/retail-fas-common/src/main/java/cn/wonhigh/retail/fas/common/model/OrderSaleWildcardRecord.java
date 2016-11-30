package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
public class OrderSaleWildcardRecord implements Serializable {
    /**
     * 主键ID,主键
     */
    private String id;

    /**
     * 外卡编码
     */
    private String wildcardNo;

    /**
     * 外卡名称
     */
    private String wildcardName;

    /**
     * 创建人编号
     */
    private String createUserNo;

    /**
     * 创建人姓名
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人编号
     */
    private String updateUserNo;

    /**
     * 修改人姓名
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
     * @return the value of order_sale_wildcard_record.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for order_sale_wildcard_record.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #wildcardNo}
     *
     * @return the value of order_sale_wildcard_record.wildcard_no
     */
    public String getWildcardNo() {
        return wildcardNo;
    }

    /**
     * 
     * {@linkplain #wildcardNo}
     * @param wildcardNo the value for order_sale_wildcard_record.wildcard_no
     */
    public void setWildcardNo(String wildcardNo) {
        this.wildcardNo = wildcardNo;
    }

    /**
     * 
     * {@linkplain #wildcardName}
     *
     * @return the value of order_sale_wildcard_record.wildcard_name
     */
    public String getWildcardName() {
        return wildcardName;
    }

    /**
     * 
     * {@linkplain #wildcardName}
     * @param wildcardName the value for order_sale_wildcard_record.wildcard_name
     */
    public void setWildcardName(String wildcardName) {
        this.wildcardName = wildcardName;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of order_sale_wildcard_record.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for order_sale_wildcard_record.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of order_sale_wildcard_record.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for order_sale_wildcard_record.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of order_sale_wildcard_record.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for order_sale_wildcard_record.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of order_sale_wildcard_record.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for order_sale_wildcard_record.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of order_sale_wildcard_record.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for order_sale_wildcard_record.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of order_sale_wildcard_record.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for order_sale_wildcard_record.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}