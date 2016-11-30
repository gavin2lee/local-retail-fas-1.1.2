package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author yu.y
 * @date  2014-11-13 15:44:38
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
public class TicketCollectDtl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5143426735751778036L;

	/**
     * 主键ID,UUID
     */
    private String id;

    /**
     * 收款单号
     */
    private String collectNo;

    /**
     * 券生成编号
     */
    private String ticketDefineNo;

    /**
     * 券号
     */
    private String ticketNo;

    /**
     * 券验证码
     */
    private String ticketCode;

    /**
     * 建档人
     */
    private String createUserNo;

    /**
     * 建档人姓名
     */
    private String createUser;

    /**
     * 建档时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private String updateUserNo;

    /**
     * 最后修改人姓名
     */
    private String updateUser;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of ticket_collect_dtl.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for ticket_collect_dtl.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #collectNo}
     *
     * @return the value of ticket_collect_dtl.collect_no
     */
    public String getCollectNo() {
        return collectNo;
    }

    /**
     * 
     * {@linkplain #collectNo}
     * @param collectNo the value for ticket_collect_dtl.collect_no
     */
    public void setCollectNo(String collectNo) {
        this.collectNo = collectNo;
    }

    /**
     * 
     * {@linkplain #ticketDefineNo}
     *
     * @return the value of ticket_collect_dtl.ticket_define_no
     */
    public String getTicketDefineNo() {
        return ticketDefineNo;
    }

    /**
     * 
     * {@linkplain #ticketDefineNo}
     * @param ticketDefineNo the value for ticket_collect_dtl.ticket_define_no
     */
    public void setTicketDefineNo(String ticketDefineNo) {
        this.ticketDefineNo = ticketDefineNo;
    }

    /**
     * 
     * {@linkplain #ticketNo}
     *
     * @return the value of ticket_collect_dtl.ticket_no
     */
    public String getTicketNo() {
        return ticketNo;
    }

    /**
     * 
     * {@linkplain #ticketNo}
     * @param ticketNo the value for ticket_collect_dtl.ticket_no
     */
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * 
     * {@linkplain #ticketCode}
     *
     * @return the value of ticket_collect_dtl.ticket_code
     */
    public String getTicketCode() {
        return ticketCode;
    }

    /**
     * 
     * {@linkplain #ticketCode}
     * @param ticketCode the value for ticket_collect_dtl.ticket_code
     */
    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of ticket_collect_dtl.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for ticket_collect_dtl.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of ticket_collect_dtl.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for ticket_collect_dtl.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of ticket_collect_dtl.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for ticket_collect_dtl.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of ticket_collect_dtl.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for ticket_collect_dtl.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of ticket_collect_dtl.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for ticket_collect_dtl.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of ticket_collect_dtl.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for ticket_collect_dtl.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}