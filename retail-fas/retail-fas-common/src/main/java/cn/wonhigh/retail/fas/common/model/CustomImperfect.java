package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-08-20 16:24:24
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
public class CustomImperfect implements Serializable,SequenceId {
    /**
	 * 序列号ID
	 */
	private static final long serialVersionUID = 8319787961289538170L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 买方编码
     */
    private String buyerNo;

    /**
     * 买方名称
     */
    private String buyerName;

    /**
     * 卖方编码
     */
    private String salerNo;

    /**
     * 卖方名称
     */
    private String salerName;

    /**
     * 退货日期
     */
	@JsonSerialize(using = JsonDateSerializer$10.class)
	@JsonDeserialize(using = JsonDateDeserialize$10.class)
    private Date returnDate;

    /**
     * 商品ID
     */
    private String itemNo;

    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 尺码
     */
    private String sizeNo;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 原因
     */
    private String reason;

    /**
     * 意见
     */
    private String opinion;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 一级类别编码
     */
    private String oneLevelCategoryNo;

    /**
     * 一级类别名称
     */
    private String oneLevelCategoryName;
    
    /**
     * 二级类别编码
     */
    private String twoLevelCategoryNo;

    /**
     * 二级类别名称
     */
    private String twoLevelCategoryName;
    
    /**
     * 年份编码
     */
    private String years;

    /**
     * 年份名称
     */
    private String yearsName;

    /**
     * 季节编码
     */
    private String season;

    /**
     * 季节名称
     */
    private String seasonName;

    /**
     * 性别编码
     */
    private String gender;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 结算单号
     */
    private String balanceNo;

    /**
     * 结算单类型
     */
    private Integer balanceType;

    /**
     * 结算单状态
     */
    private Integer balanceStatus;
    
    /**
     * 创建时间
     */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
	@JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    public String getOneLevelCategoryNo() {
		return oneLevelCategoryNo;
	}

	public void setOneLevelCategoryNo(String oneLevelCategoryNo) {
		this.oneLevelCategoryNo = oneLevelCategoryNo;
	}

	public String getOneLevelCategoryName() {
		return oneLevelCategoryName;
	}

	public void setOneLevelCategoryName(String oneLevelCategoryName) {
		this.oneLevelCategoryName = oneLevelCategoryName;
	}

	public String getTwoLevelCategoryNo() {
		return twoLevelCategoryNo;
	}

	public void setTwoLevelCategoryNo(String twoLevelCategoryNo) {
		this.twoLevelCategoryNo = twoLevelCategoryNo;
	}

	public String getTwoLevelCategoryName() {
		return twoLevelCategoryName;
	}

	public void setTwoLevelCategoryName(String twoLevelCategoryName) {
		this.twoLevelCategoryName = twoLevelCategoryName;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public Integer getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(Integer balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of custom_imperfect.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for custom_imperfect.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of custom_imperfect.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for custom_imperfect.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of custom_imperfect.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for custom_imperfect.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #salerNo}
     *
     * @return the value of custom_imperfect.saler_no
     */
    public String getSalerNo() {
        return salerNo;
    }

    /**
     * 
     * {@linkplain #salerNo}
     * @param salerNo the value for custom_imperfect.saler_no
     */
    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of custom_imperfect.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for custom_imperfect.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #returnDate}
     *
     * @return the value of custom_imperfect.return_date
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * 
     * {@linkplain #returnDate}
     * @param returnDate the value for custom_imperfect.return_date
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of custom_imperfect.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for custom_imperfect.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of custom_imperfect.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for custom_imperfect.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of custom_imperfect.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for custom_imperfect.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of custom_imperfect.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for custom_imperfect.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of custom_imperfect.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for custom_imperfect.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     *
     * @return the value of custom_imperfect.purchase_price
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * 
     * {@linkplain #purchasePrice}
     * @param purchasePrice the value for custom_imperfect.purchase_price
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of custom_imperfect.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for custom_imperfect.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #reason}
     *
     * @return the value of custom_imperfect.reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * 
     * {@linkplain #reason}
     * @param reason the value for custom_imperfect.reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 
     * {@linkplain #opinion}
     *
     * @return the value of custom_imperfect.opinion
     */
    public String getOpinion() {
        return opinion;
    }

    /**
     * 
     * {@linkplain #opinion}
     * @param opinion the value for custom_imperfect.opinion
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of custom_imperfect.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for custom_imperfect.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of custom_imperfect.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for custom_imperfect.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of custom_imperfect.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for custom_imperfect.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of custom_imperfect.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for custom_imperfect.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #years}
     *
     * @return the value of custom_imperfect.years
     */
    public String getYears() {
        return years;
    }

    /**
     * 
     * {@linkplain #years}
     * @param years the value for custom_imperfect.years
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * 
     * {@linkplain #yearsName}
     *
     * @return the value of custom_imperfect.years_name
     */
    public String getYearsName() {
        return yearsName;
    }

    /**
     * 
     * {@linkplain #yearsName}
     * @param yearsName the value for custom_imperfect.years_name
     */
    public void setYearsName(String yearsName) {
        this.yearsName = yearsName;
    }

    /**
     * 
     * {@linkplain #season}
     *
     * @return the value of custom_imperfect.season
     */
    public String getSeason() {
        return season;
    }

    /**
     * 
     * {@linkplain #season}
     * @param season the value for custom_imperfect.season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * 
     * {@linkplain #seasonName}
     *
     * @return the value of custom_imperfect.season_name
     */
    public String getSeasonName() {
        return seasonName;
    }

    /**
     * 
     * {@linkplain #seasonName}
     * @param seasonName the value for custom_imperfect.season_name
     */
    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    /**
     * 
     * {@linkplain #gender}
     *
     * @return the value of custom_imperfect.gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * {@linkplain #gender}
     * @param gender the value for custom_imperfect.gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * {@linkplain #genderName}
     *
     * @return the value of custom_imperfect.gender_name
     */
    public String getGenderName() {
        return genderName;
    }

    /**
     * 
     * {@linkplain #genderName}
     * @param genderName the value for custom_imperfect.gender_name
     */
    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of custom_imperfect.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for custom_imperfect.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of custom_imperfect.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for custom_imperfect.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of custom_imperfect.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for custom_imperfect.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of custom_imperfect.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for custom_imperfect.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of custom_imperfect.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for custom_imperfect.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}