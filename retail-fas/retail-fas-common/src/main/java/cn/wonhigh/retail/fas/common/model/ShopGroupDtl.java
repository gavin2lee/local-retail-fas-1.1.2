package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-22 11:41:25
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
public class ShopGroupDtl implements Serializable,SequenceId {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7151855152165398593L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 店铺分组编号
     */
    private String shopGroupNo;

    /**
     * 店铺编号
     */
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of shop_group_dtl.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for shop_group_dtl.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopGroupNo}
     *
     * @return the value of shop_group_dtl.shop_group_no
     */
    public String getShopGroupNo() {
        return shopGroupNo;
    }

    /**
     * 
     * {@linkplain #shopGroupNo}
     * @param shopGroupNo the value for shop_group_dtl.shop_group_no
     */
    public void setShopGroupNo(String shopGroupNo) {
        this.shopGroupNo = shopGroupNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of shop_group_dtl.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for shop_group_dtl.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of shop_group_dtl.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for shop_group_dtl.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}