package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-07-11 17:43:44
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
public class PricingRange implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * JSON字符串
     */
    private String jsonStr;

    /**
     * 查询条件
     */
    private String queryCondition;

    /**
     * 是否启用(0，不启用;1，启用)
     */
    private Byte isStart;

    /**
     * 备注
     */
    private String remark;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of pricing_range.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for pricing_range.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #jsonStr}
     *
     * @return the value of pricing_range.json_str
     */
    public String getJsonStr() {
        return jsonStr;
    }

    /**
     * 
     * {@linkplain #jsonStr}
     * @param jsonStr the value for pricing_range.json_str
     */
    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    /**
     * 
     * {@linkplain #queryCondition}
     *
     * @return the value of pricing_range.query_condition
     */
    public String getQueryCondition() {
        return queryCondition;
    }

    /**
     * 
     * {@linkplain #queryCondition}
     * @param queryCondition the value for pricing_range.query_condition
     */
    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    /**
     * 
     * {@linkplain #isStart}
     *
     * @return the value of pricing_range.is_start
     */
    public Byte getIsStart() {
        return isStart;
    }

    /**
     * 
     * {@linkplain #isStart}
     * @param isStart the value for pricing_range.is_start
     */
    public void setIsStart(Byte isStart) {
        this.isStart = isStart;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of pricing_range.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for pricing_range.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of pricing_range.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for pricing_range.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}