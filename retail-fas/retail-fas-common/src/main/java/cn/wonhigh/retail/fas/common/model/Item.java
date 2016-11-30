package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author ouyang.zm
 * @date  2014-10-22 15:09:15
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
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2583555430442015684L;

	/**
     * 
     */
    private Integer id;

    /**
     * 商品系统唯一编码(系统编码,对用户不可见)
     */
    private String itemNo;

    /**
     * 商品编码(出厂时的商品编码)
     */
    private String code;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品全称(默认与商品名称一致)
     */
    private String fullName;

    /**
     * 商品英文名
     */
    private String enName;

    /**
     * 所属业务单元
     */
    private String sysNo;

    /**
     * 商品款号(必须输入且长度必须6位)
     */
    private String styleNo;

    /**
     * 品牌编码
     */
    private String brandNo;
    
    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 款型(下拉框选择,值: 1.5, 2, 2.5, 无)
     */
    private String shoeModel;

    /**
     * 主料(下拉框选择,值: 1:皮 2:布 3:绒 4:PU 5:木 6:弹力 7:网 8:其它 9:不涉及)
     */
    private String ingredients;

    /**
     * 面料成份
     */
    private String mainqdb;

    /**
     * 内里(D:单里 M:毛里 R:绒里 F:仿毛里)
     */
    private String lining;

    /**
     * 颜色编码
     */
    private String colorNo;

    /**
     * 主色
     */
    private String mainColor;

    /**
     * 类别编码
     */
    private String categoryNo;
    
    /**
     * 类别名称
     */
    private String categoryName;
    
    /**
     * 商品大类编码
     */
    private String rootCategoryNo;

    /**
     * 重复上市(下拉框选择,值: 是, 否)
     */
    private String repeatlisting;

    /**
     * 性别(下拉框选择,值: 男, 女, 童, 无性别)
     */
    private String gender;

    /**
     * 跟型(下拉框选择,值: 高, 中, 低, 平, 不涉及)
     */
    private String heeltype;

    /**
     * 底型(下拉框选择,值: 成型底, 片底, 成型片, 不涉及)
     */
    private String bottomtype;

    /**
     * 尺寸分类
     */
    private String sizeKind;

    /**
     * 状态(0 = 禁用 1 = 正常 2 = 临时)
     */
    private Byte status;
    
    /**
     * 状态名(0 = 禁用 1 = 正常 2 = 临时)
     */
    private String statusName;

    /**
     * 牌价
     */
    private BigDecimal tagPrice;

    /**
     * 建议牌价
     */
    private BigDecimal suggestTagPrice;

    /**
     * 含税采购价
     */
    private BigDecimal purchaseTaxPrice;

    /**
     * 进项税率
     */
    private BigDecimal costtaxrate;

    /**
     * 销项税率
     */
    private BigDecimal saletaxrate;

    /**
     * 物料价
     */
    private BigDecimal materialPrice;

    /**
     * 供应商编码
     */
    private String supplierNo;

    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 供应商货号
     */
    private String supplierItemNo;

    /**
     * 供应商商品名称
     */
    private String supplierItemName;

    /**
     * 订货形式(下拉框选择,值: 1:自产 2:外购 3:地区自购)
     */
    private String orderfrom;

    /**
     * 年份(指上市的年份,下拉框选择,值: 2006~2026,默认当年)
     */
    private String years;
    
    private String yearsName;

    /**
     * 季节(下拉框选择,A:春 B:夏 C:秋 D:冬)
     */
    private String sellSeason;

    /**
     * 采购季节
     */
    private String purchaseSeason;

    /**
     * 建议上柜日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date saleDate;

    /**
     * 检索码
     */
    private String searchCode;

    /**
     * 风格
     */
    private String style;

    /**
     * 建档人
     */
    private String createUser;

    /**
     * 建档时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)
	@JsonDeserialize(using = JsonDateDeserialize$19.class)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 管理城市
     */
    private String organNo;
    
    /**
     * 店铺编号
     */
    private String shopNo;

    public String getRootCategoryNo() {
		return rootCategoryNo;
	}

	public void setRootCategoryNo(String rootCategoryNo) {
		this.rootCategoryNo = rootCategoryNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of item.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for item.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of item.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for item.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #code}
     *
     * @return the value of item.code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * {@linkplain #code}
     * @param code the value for item.code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * {@linkplain #name}
     *
     * @return the value of item.name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * {@linkplain #name}
     * @param name the value for item.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of item.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for item.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #enName}
     *
     * @return the value of item.en_name
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 
     * {@linkplain #enName}
     * @param enName the value for item.en_name
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 
     * {@linkplain #sysNo}
     *
     * @return the value of item.sys_no
     */
    public String getSysNo() {
        return sysNo;
    }

    /**
     * 
     * {@linkplain #sysNo}
     * @param sysNo the value for item.sys_no
     */
    public void setSysNo(String sysNo) {
        this.sysNo = sysNo;
    }

    /**
     * 
     * {@linkplain #styleNo}
     *
     * @return the value of item.style_no
     */
    public String getStyleNo() {
        return styleNo;
    }

    /**
     * 
     * {@linkplain #styleNo}
     * @param styleNo the value for item.style_no
     */
    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of item.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for item.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #shoeModel}
     *
     * @return the value of item.shoe_model
     */
    public String getShoeModel() {
        return shoeModel;
    }

    /**
     * 
     * {@linkplain #shoeModel}
     * @param shoeModel the value for item.shoe_model
     */
    public void setShoeModel(String shoeModel) {
        this.shoeModel = shoeModel;
    }

    /**
     * 
     * {@linkplain #ingredients}
     *
     * @return the value of item.ingredients
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * 
     * {@linkplain #ingredients}
     * @param ingredients the value for item.ingredients
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * 
     * {@linkplain #mainqdb}
     *
     * @return the value of item.mainqdb
     */
    public String getMainqdb() {
        return mainqdb;
    }

    /**
     * 
     * {@linkplain #mainqdb}
     * @param mainqdb the value for item.mainqdb
     */
    public void setMainqdb(String mainqdb) {
        this.mainqdb = mainqdb;
    }

    /**
     * 
     * {@linkplain #lining}
     *
     * @return the value of item.lining
     */
    public String getLining() {
        return lining;
    }

    /**
     * 
     * {@linkplain #lining}
     * @param lining the value for item.lining
     */
    public void setLining(String lining) {
        this.lining = lining;
    }

    /**
     * 
     * {@linkplain #colorNo}
     *
     * @return the value of item.color_no
     */
    public String getColorNo() {
        return colorNo;
    }

    /**
     * 
     * {@linkplain #colorNo}
     * @param colorNo the value for item.color_no
     */
    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

    /**
     * 
     * {@linkplain #mainColor}
     *
     * @return the value of item.main_color
     */
    public String getMainColor() {
        return mainColor;
    }

    /**
     * 
     * {@linkplain #mainColor}
     * @param mainColor the value for item.main_color
     */
    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of item.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for item.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
     * 
     * {@linkplain #repeatlisting}
     *
     * @return the value of item.repeatlisting
     */
    public String getRepeatlisting() {
        return repeatlisting;
    }

    /**
     * 
     * {@linkplain #repeatlisting}
     * @param repeatlisting the value for item.repeatlisting
     */
    public void setRepeatlisting(String repeatlisting) {
        this.repeatlisting = repeatlisting;
    }

    /**
     * 
     * {@linkplain #gender}
     *
     * @return the value of item.gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * {@linkplain #gender}
     * @param gender the value for item.gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * {@linkplain #heeltype}
     *
     * @return the value of item.heeltype
     */
    public String getHeeltype() {
        return heeltype;
    }

    /**
     * 
     * {@linkplain #heeltype}
     * @param heeltype the value for item.heeltype
     */
    public void setHeeltype(String heeltype) {
        this.heeltype = heeltype;
    }

    /**
     * 
     * {@linkplain #bottomtype}
     *
     * @return the value of item.bottomtype
     */
    public String getBottomtype() {
        return bottomtype;
    }

    /**
     * 
     * {@linkplain #bottomtype}
     * @param bottomtype the value for item.bottomtype
     */
    public void setBottomtype(String bottomtype) {
        this.bottomtype = bottomtype;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     *
     * @return the value of item.size_kind
     */
    public String getSizeKind() {
        return sizeKind;
    }

    /**
     * 
     * {@linkplain #sizeKind}
     * @param sizeKind the value for item.size_kind
     */
    public void setSizeKind(String sizeKind) {
        this.sizeKind = sizeKind;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of item.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for item.status
     */
    public void setStatus(Byte status) {
    	if(status!=null){
    		if(status.byteValue() == 0){
    			this.statusName = "禁用";
    		}else if(status.byteValue() == 1){
    			this.statusName = "正常";
    		}else if(status.byteValue() == 2){
    			this.statusName = "临时";
    		}
    	}
        this.status = status;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     *
     * @return the value of item.tag_price
     */
    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    /**
     * 
     * {@linkplain #tagPrice}
     * @param tagPrice the value for item.tag_price
     */
    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    /**
     * 
     * {@linkplain #suggestTagPrice}
     *
     * @return the value of item.suggest_tag_price
     */
    public BigDecimal getSuggestTagPrice() {
        return suggestTagPrice;
    }

    /**
     * 
     * {@linkplain #suggestTagPrice}
     * @param suggestTagPrice the value for item.suggest_tag_price
     */
    public void setSuggestTagPrice(BigDecimal suggestTagPrice) {
        this.suggestTagPrice = suggestTagPrice;
    }

    /**
     * 
     * {@linkplain #purchaseTaxPrice}
     *
     * @return the value of item.purchase_tax_price
     */
    public BigDecimal getPurchaseTaxPrice() {
        return purchaseTaxPrice;
    }

    /**
     * 
     * {@linkplain #purchaseTaxPrice}
     * @param purchaseTaxPrice the value for item.purchase_tax_price
     */
    public void setPurchaseTaxPrice(BigDecimal purchaseTaxPrice) {
        this.purchaseTaxPrice = purchaseTaxPrice;
    }

    /**
     * 
     * {@linkplain #costtaxrate}
     *
     * @return the value of item.costtaxrate
     */
    public BigDecimal getCosttaxrate() {
        return costtaxrate;
    }

    /**
     * 
     * {@linkplain #costtaxrate}
     * @param costtaxrate the value for item.costtaxrate
     */
    public void setCosttaxrate(BigDecimal costtaxrate) {
        this.costtaxrate = costtaxrate;
    }

    /**
     * 
     * {@linkplain #saletaxrate}
     *
     * @return the value of item.saletaxrate
     */
    public BigDecimal getSaletaxrate() {
        return saletaxrate;
    }

    /**
     * 
     * {@linkplain #saletaxrate}
     * @param saletaxrate the value for item.saletaxrate
     */
    public void setSaletaxrate(BigDecimal saletaxrate) {
        this.saletaxrate = saletaxrate;
    }

    /**
     * 
     * {@linkplain #materialPrice}
     *
     * @return the value of item.material_price
     */
    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    /**
     * 
     * {@linkplain #materialPrice}
     * @param materialPrice the value for item.material_price
     */
    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     *
     * @return the value of item.supplier_no
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierNo}
     * @param supplierNo the value for item.supplier_no
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 
     * {@linkplain #supplierItemNo}
     *
     * @return the value of item.supplier_item_no
     */
    public String getSupplierItemNo() {
        return supplierItemNo;
    }

    /**
     * 
     * {@linkplain #supplierItemNo}
     * @param supplierItemNo the value for item.supplier_item_no
     */
    public void setSupplierItemNo(String supplierItemNo) {
        this.supplierItemNo = supplierItemNo;
    }

    /**
     * 
     * {@linkplain #supplierItemName}
     *
     * @return the value of item.supplier_item_name
     */
    public String getSupplierItemName() {
        return supplierItemName;
    }

    /**
     * 
     * {@linkplain #supplierItemName}
     * @param supplierItemName the value for item.supplier_item_name
     */
    public void setSupplierItemName(String supplierItemName) {
        this.supplierItemName = supplierItemName;
    }

    /**
     * 
     * {@linkplain #orderfrom}
     *
     * @return the value of item.orderfrom
     */
    public String getOrderfrom() {
        return orderfrom;
    }

    /**
     * 
     * {@linkplain #orderfrom}
     * @param orderfrom the value for item.orderfrom
     */
    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom;
    }

    /**
     * 
     * {@linkplain #years}
     *
     * @return the value of item.years
     */
    public String getYears() {
        return years;
    }

    /**
     * 
     * {@linkplain #years}
     * @param years the value for item.years
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * 
     * {@linkplain #sellSeason}
     *
     * @return the value of item.sell_season
     */
    public String getSellSeason() {
        return sellSeason;
    }

    /**
     * 
     * {@linkplain #sellSeason}
     * @param sellSeason the value for item.sell_season
     */
    public void setSellSeason(String sellSeason) {
        this.sellSeason = sellSeason;
    }

    /**
     * 
     * {@linkplain #purchaseSeason}
     *
     * @return the value of item.purchase_season
     */
    public String getPurchaseSeason() {
        return purchaseSeason;
    }

    /**
     * 
     * {@linkplain #purchaseSeason}
     * @param purchaseSeason the value for item.purchase_season
     */
    public void setPurchaseSeason(String purchaseSeason) {
        this.purchaseSeason = purchaseSeason;
    }

    /**
     * 
     * {@linkplain #saleDate}
     *
     * @return the value of item.sale_date
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * 
     * {@linkplain #saleDate}
     * @param saleDate the value for item.sale_date
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * 
     * {@linkplain #searchCode}
     *
     * @return the value of item.search_code
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * 
     * {@linkplain #searchCode}
     * @param searchCode the value for item.search_code
     */
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    /**
     * 
     * {@linkplain #style}
     *
     * @return the value of item.style
     */
    public String getStyle() {
        return style;
    }

    /**
     * 
     * {@linkplain #style}
     * @param style the value for item.style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of item.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for item.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of item.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for item.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of item.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for item.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of item.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for item.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of item.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for item.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getYearsName() {
		return yearsName;
	}

	public void setYearsName(String yearsName) {
		this.yearsName = yearsName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}