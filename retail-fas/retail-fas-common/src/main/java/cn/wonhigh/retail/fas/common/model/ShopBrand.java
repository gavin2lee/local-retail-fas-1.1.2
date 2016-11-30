package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-01-22 10:14:42
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
public class ShopBrand implements Serializable {
    
	private static final long serialVersionUID = 5525881743986986737L;

	/**
     * 门店品牌ID
     */
    private Integer id;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 品牌编码
     */
    private String brandNo;
    
    /**
     * 品牌编码
     */
    private String brandName;
    
    /**
     * 品牌编号
     */
    private String brandUnitNo;

    /**
     * 品牌名称
     */
    private String brandUnitName;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private  String name;

    /**
     * 默认订货单位
     */
    private String orderUnitNo;

    /**
     * 是否主营品牌(1是，0否)
     */
    private Byte brandFlag;

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
     * 时间序列
     */
    private Long timeSeq;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of shop_brand.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for shop_brand.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of shop_brand.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for shop_brand.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of shop_brand.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for shop_brand.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     *
     * @return the value of shop_brand.order_unit_no
     */
    public String getOrderUnitNo() {
        return orderUnitNo;
    }

    /**
     * 
     * {@linkplain #orderUnitNo}
     * @param orderUnitNo the value for shop_brand.order_unit_no
     */
    public void setOrderUnitNo(String orderUnitNo) {
        this.orderUnitNo = orderUnitNo;
    }

    /**
     * 
     * {@linkplain #brandFlag}
     *
     * @return the value of shop_brand.brand_flag
     */
    public Byte getBrandFlag() {
        return brandFlag;
    }

    /**
     * 
     * {@linkplain #brandFlag}
     * @param brandFlag the value for shop_brand.brand_flag
     */
    public void setBrandFlag(Byte brandFlag) {
        this.brandFlag = brandFlag;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of shop_brand.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for shop_brand.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of shop_brand.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for shop_brand.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of shop_brand.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for shop_brand.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of shop_brand.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for shop_brand.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of shop_brand.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for shop_brand.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #timeSeq}
     *
     * @return the value of shop_brand.time_seq
     */
    public Long getTimeSeq() {
        return timeSeq;
    }

    /**
     * 
     * {@linkplain #timeSeq}
     * @param timeSeq the value for shop_brand.time_seq
     */
    public void setTimeSeq(Long timeSeq) {
        this.timeSeq = timeSeq;
    }

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
}