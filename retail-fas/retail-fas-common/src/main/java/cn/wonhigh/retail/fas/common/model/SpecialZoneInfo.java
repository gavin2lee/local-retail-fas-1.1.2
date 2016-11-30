package cn.wonhigh.retail.fas.common.model;

import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.SpecialZoneInfoExportFormat;


/**
 * 请写出类的用途 
 * @author wangxy
 * @date  2015-04-07 18:18:17
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
@ExportFormat(className=SpecialZoneInfoExportFormat.class)
public class SpecialZoneInfo extends FasBaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 695860787264698414L;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 区域编码(有且必须只能输入一位)
     */
    private String zoneCode;

    /**
     * 经营区域名称
     */
    private String name;

    /**
     * 品牌库编码(00-通用定义 其他则为相应品牌库)
     */
    private String sysNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 时间序列
     */
    private Long timeSeq;
    
   /* *//** 状态 *//*
    private Integer status = FasAduitStatusEnum.ADUIT_STATUS.getValue();

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}*/

	/**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of special_zone_info.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for special_zone_info.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneCode}
     *
     * @return the value of special_zone_info.zone_code
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * 
     * {@linkplain #zoneCode}
     * @param zoneCode the value for special_zone_info.zone_code
     */
    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of special_zone_info.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for special_zone_info.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of special_zone_info.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for special_zone_info.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of special_zone_info.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for special_zone_info.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #timeSeq}
     *
     * @return the value of special_zone_info.time_seq
     */
    public Long getTimeSeq() {
        return timeSeq;
    }

    /**
     * 
     * {@linkplain #timeSeq}
     * @param timeSeq the value for special_zone_info.time_seq
     */
    public void setTimeSeq(Long timeSeq) {
        this.timeSeq = timeSeq;
    }
}