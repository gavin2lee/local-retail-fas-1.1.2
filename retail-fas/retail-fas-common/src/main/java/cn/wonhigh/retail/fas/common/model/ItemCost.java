package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

/**
 * 请写出类的用途 
 * @author wang.xy1
 * @date  2014-10-24 12:07:00
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
public class ItemCost extends FasBaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3375847112315549575L;

    /**
     * 分库字段
     */
    private String shardingFlag;
    
    /**
     * 公司编号
     */
    private String companyNo;

    /**
     * 货品编号
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
     * 商品款号
     */
    private String styleNo;
    
    /**
     * 品牌编号
     */
    private String brandNo;
    
    /**
     * 商品名称
     */
    private String brandName;
    
    /**
     * 仓库编号(预留)
     */
    private String storeNo;

    /**
     * 库位(预留)
     */
    private String location;

    /**
     * 批次(预留)
     */
    private String lot;

    /**
     * 成本方法
     */
    private String costMethod;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 单位成本
     */
    private BigDecimal regionCost;
    
    /**
     * 计量单位
     */
    private String unit;

    /**
     * 年份
     */
    private String year;

    /**
     * 月份
     */
    private String month;

    /**
     * 多选
     */
    private String brandNos;
    
    /**
     * 多选
     */
    private String brandNames;

    /**
     * 多选
     */
    private String itemNames;

    /**
     * 多选
     */
    private String itemNos;
    
    /**
     * 多选
     */
    private String itemCodes;
    
    /**
     * 多选
     */
    private String styleNos;
    
    /**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 是否按公司维度生成
	 */
	private boolean onlyCompanyDM;
	
	/**
     * 品牌部编号
     */
    private String brandUnitNo;
    
    /**
     * 品牌部名称
     */
    private String brandUnitName;
    
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

	public boolean getOnlyCompanyDM() {
		return onlyCompanyDM;
	}

	public void setOnlyCompanyDM(boolean onlyCompanyDM) {
		this.onlyCompanyDM = onlyCompanyDM;
	}

	public String getShardingFlag() {
		return shardingFlag;
	}

	public void setShardingFlag(String shardingFlag) {
		this.shardingFlag = shardingFlag;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of item_cost.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for item_cost.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
//        setShardingFlag(ShardingUtil.getShardingFlagByCompanyNo(companyNo));
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of item_cost.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for item_cost.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of item_cost.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for item_cost.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

	/**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of item_cost.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for item_cost.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
     * 
     * {@linkplain #storeNo}
     *
     * @return the value of item_cost.store_no
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 
     * {@linkplain #storeNo}
     * @param storeNo the value for item_cost.store_no
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    /**
     * 
     * {@linkplain #location}
     *
     * @return the value of item_cost.location
     */
    public String getLocation() {
        return location;
    }

    /**
     * 
     * {@linkplain #location}
     * @param location the value for item_cost.location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 
     * {@linkplain #lot}
     *
     * @return the value of item_cost.lot
     */
    public String getLot() {
        return lot;
    }

    /**
     * 
     * {@linkplain #lot}
     * @param lot the value for item_cost.lot
     */
    public void setLot(String lot) {
        this.lot = lot;
    }

    /**
     * 
     * {@linkplain #costMethod}
     *
     * @return the value of item_cost.cost_method
     */
    public String getCostMethod() {
        return costMethod;
    }

    /**
     * 
     * {@linkplain #costMethod}
     * @param costMethod the value for item_cost.cost_method
     */
    public void setCostMethod(String costMethod) {
        this.costMethod = costMethod;
    }

    /**
     * 
     * {@linkplain #unitCost}
     *
     * @return the value of item_cost.unit_cost
     */
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    /**
     * 
     * {@linkplain #unitCost}
     * @param unitCost the value for item_cost.unit_cost
     */
    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getRegionCost() {
		return regionCost;
	}

	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}

	/**
     * 
     * {@linkplain #unit}
     *
     * @return the value of item_cost.unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * {@linkplain #unit}
     * @param unit the value for item_cost.unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of item_cost.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for item_cost.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of item_cost.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for item_cost.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

	public String getBrandNos() {
		return brandNos;
	}

	public void setBrandNos(String brandNos) {
		this.brandNos = brandNos;
	}

	public String getBrandNames() {
		return brandNames;
	}

	public void setBrandNames(String brandNames) {
		this.brandNames = brandNames;
	}

	public String getItemNames() {
		return itemNames;
	}

	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}

	public String getItemNos() {
		return itemNos;
	}

	public void setItemNos(String itemNos) {
		this.itemNos = itemNos;
	}

	public String getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(String itemCodes) {
		this.itemCodes = itemCodes;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getStyleNos() {
		return styleNos;
	}

	public void setStyleNos(String styleNos) {
		this.styleNos = styleNos;
	}
	
}