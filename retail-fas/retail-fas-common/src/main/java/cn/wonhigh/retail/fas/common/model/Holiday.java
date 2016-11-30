package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2016-04-19 17:47:43
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
public class Holiday implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 假日名
     */
    private String name;

    /**
     * 假日开始时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    @JsonDeserialize(using = JsonDateDeserialize$10.class)  
    private Date startDate;

    /**
     * 假日结束时间
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    @JsonDeserialize(using = JsonDateDeserialize$10.class)  
    private Date endDate;

    /**
     * 天数
     */
    private Integer days;

    /**
     * 假日前最近一个工作日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
    @JsonDeserialize(using = JsonDateDeserialize$10.class)  
    private Date lastWorkDate;

    /**
     * 更新时间
     */
    private Date updateTime;

    private boolean isWeekend;
    
    public boolean isWeekend() {
		return isWeekend;
	}

	public void setWeekend(boolean isWeekend) {
		this.isWeekend = isWeekend;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of holiday.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for holiday.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of holiday.year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for holiday.year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of holiday.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for holiday.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #startDate}
     *
     * @return the value of holiday.start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 
     * {@linkplain #startDate}
     * @param startDate the value for holiday.start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     *
     * @return the value of holiday.end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 
     * {@linkplain #endDate}
     * @param endDate the value for holiday.end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * {@linkplain #days}
     *
     * @return the value of holiday.days
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 
     * {@linkplain #days}
     * @param days the value for holiday.days
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 
     * {@linkplain #lastWorkDate}
     *
     * @return the value of holiday.last_work_date
     */
    public Date getLastWorkDate() {
        return lastWorkDate;
    }

    /**
     * 
     * {@linkplain #lastWorkDate}
     * @param lastWorkDate the value for holiday.last_work_date
     */
    public void setLastWorkDate(Date lastWorkDate) {
        this.lastWorkDate = lastWorkDate;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of holiday.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for holiday.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}