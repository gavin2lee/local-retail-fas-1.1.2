package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
/**
 * 销售分类汇总
 * @author ouyang.zm
 * @date  2015-01-04 11:46:00
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
@ExportFormat(className=AbstractExportFormat.class)
public class SalesSummary   extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int total ;


    /**
     * 公司编码
     */
    private String companyNo;
    /**
     * 公司名称
     */
    private String companyName;
    private String organNo;
    private String organName;
    
	/**
     * 店铺编号
     */
    private String shopNo1;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 业务类型
     */
    private String bizType;
    private String brandUnitNo;
    private String brandUnitName;
    private String shopNoReplace;
    
    public String getShopNoReplace() {
		return shopNoReplace;
	}

	public void setShopNoReplace(String shopNoReplace) {
		this.shopNoReplace = shopNoReplace;
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

	 public String getOrganNo() {
			return organNo;
	}


	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}


	public String getOrganName() {
		return organName;
	}


	public void setOrganName(String organName) {
		this.organName = organName;
	}

	/**
     * 商品编码
     */
    private String brandNo1;


    /**
     * 商品名称
     */
    private String brandName;
    /**
     * 商品内码
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
     * 发货总数量
     */
    private Integer totalQty;

    /**
     * 汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount;
    /**
     * 汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice;
    /**
     * 鞋汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice01;
    /**
     * 童鞋汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice02;
    /**
     * 服汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice03;
    /**
     * 包饰汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice04;
    /**
     * 护鞋用品汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice05;
    /**
     * 物料汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice06;
    /**
     * 其他汇总牌价金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalTagPrice07;


	/**
     * 汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost;

    /**
     * 地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost;


    /**
     * 总部成本总和
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost;

    /**
     * 鞋汇总数量
     */
    private Integer totalQty01;

    /**
     * 鞋汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount01;

    /**
     * 鞋汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost01;

    /**
     * 鞋地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost01;


    /**
     * 鞋总部成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost01;
    /**
     * 童鞋汇总数量
     */
    private Integer totalQty02;

    /**
     * 童鞋汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount02;

    /**
     * 童鞋汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost02;

    /**
     * 童鞋地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost02;


    /**
     * 童鞋总部成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost02;

    /**
     * 服汇总数量
     */
    private Integer totalQty03;

    /**
     * 服汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount03;

    /**
     * 服汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost03;

    /**
     * 服地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost03;


    /**
     * 服总部成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost03;

    /**
     * 包饰汇总数量
     */
    private Integer totalQty04;

    /**
     * 包饰汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount04;

    /**
     * 包饰汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost04;

    /**
     * 包饰地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost04;


    /**
     * 包饰总部成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost04;

    /**
     *护鞋用品汇总数量
     */
    private Integer totalQty05;

    /**
     * 护鞋用品汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount05;

    /**
     * 护鞋用品汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost05;

    /**
     * 护鞋用品地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost05;


    /**
     * 护鞋用品总部成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost05;
    /**
     * 物料汇总数量
     */
    private Integer totalQty06;

    /**
     * 物料汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount06;

    /**
     * 物料汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost06;

    /**
     * 物料地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost06;


    /**
     * 物料总部成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost06;
    /**
     * 其他
     */
    private Integer totalQty07;

    /**
     * 其他汇总金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmount07;

    /**
     * 其他汇总金额单价成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal totalAmountUnitCost07;

    /**
     * 其他地区成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountRegionCost07;


    /**
     * 其他总部成本总额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal totalAmountHeadquarterCost07;


	public String getCompanyNo() {
		return companyNo;
	}


	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getShopNo1() {
		return shopNo1;
	}


	public void setShopNo1(String shopNo1) {
		this.shopNo1 = shopNo1;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getBizType() {
		return bizType;
	}


	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	
	public String getBrandNo1() {
		return brandNo1;
	}


	public void setBrandNo1(String brandNo1) {
		this.brandNo1 = brandNo1;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getItemNo() {
		return itemNo;
	}


	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}


	public String getItemCode() {
		return itemCode;
	}


	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public Integer getTotalQty() {
		return totalQty;
	}


	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}


	public BigDecimal getTotalTagPrice() {
		return totalTagPrice;
	}


	public void setTotalTagPrice(BigDecimal totalTagPrice) {
		this.totalTagPrice = totalTagPrice;
	}


	public BigDecimal getTotalTagPrice01() {
		return totalTagPrice01;
	}


	public void setTotalTagPrice01(BigDecimal totalTagPrice01) {
		this.totalTagPrice01 = totalTagPrice01;
	}


	public BigDecimal getTotalTagPrice02() {
		return totalTagPrice02;
	}


	public void setTotalTagPrice02(BigDecimal totalTagPrice02) {
		this.totalTagPrice02 = totalTagPrice02;
	}


	public BigDecimal getTotalTagPrice03() {
		return totalTagPrice03;
	}


	public void setTotalTagPrice03(BigDecimal totalTagPrice03) {
		this.totalTagPrice03 = totalTagPrice03;
	}


	public BigDecimal getTotalTagPrice04() {
		return totalTagPrice04;
	}


	public void setTotalTagPrice04(BigDecimal totalTagPrice04) {
		this.totalTagPrice04 = totalTagPrice04;
	}


	public BigDecimal getTotalTagPrice05() {
		return totalTagPrice05;
	}


	public void setTotalTagPrice05(BigDecimal totalTagPrice05) {
		this.totalTagPrice05 = totalTagPrice05;
	}


	public BigDecimal getTotalTagPrice06() {
		return totalTagPrice06;
	}


	public void setTotalTagPrice06(BigDecimal totalTagPrice06) {
		this.totalTagPrice06 = totalTagPrice06;
	}


	public BigDecimal getTotalTagPrice07() {
		return totalTagPrice07;
	}


	public void setTotalTagPrice07(BigDecimal totalTagPrice07) {
		this.totalTagPrice07 = totalTagPrice07;
	}


	public BigDecimal getTotalAmountUnitCost() {
		return totalAmountUnitCost;
	}


	public void setTotalAmountUnitCost(BigDecimal totalAmountUnitCost) {
		this.totalAmountUnitCost = totalAmountUnitCost;
	}


	public BigDecimal getTotalAmountRegionCost() {
		return totalAmountRegionCost;
	}


	public void setTotalAmountRegionCost(BigDecimal totalAmountRegionCost) {
		this.totalAmountRegionCost = totalAmountRegionCost;
	}


	public BigDecimal getTotalAmountHeadquarterCost() {
		return totalAmountHeadquarterCost;
	}


	public void setTotalAmountHeadquarterCost(BigDecimal totalAmountHeadquarterCost) {
		this.totalAmountHeadquarterCost = totalAmountHeadquarterCost;
	}


	public Integer getTotalQty01() {
		return totalQty01;
	}


	public void setTotalQty01(Integer totalQty01) {
		this.totalQty01 = totalQty01;
	}


	public BigDecimal getTotalAmount01() {
		return totalAmount01;
	}


	public void setTotalAmount01(BigDecimal totalAmount01) {
		this.totalAmount01 = totalAmount01;
	}


	public BigDecimal getTotalAmountUnitCost01() {
		return totalAmountUnitCost01;
	}


	public void setTotalAmountUnitCost01(BigDecimal totalAmountUnitCost01) {
		this.totalAmountUnitCost01 = totalAmountUnitCost01;
	}


	public BigDecimal getTotalAmountRegionCost01() {
		return totalAmountRegionCost01;
	}


	public void setTotalAmountRegionCost01(BigDecimal totalAmountRegionCost01) {
		this.totalAmountRegionCost01 = totalAmountRegionCost01;
	}


	public BigDecimal getTotalAmountHeadquarterCost01() {
		return totalAmountHeadquarterCost01;
	}


	public void setTotalAmountHeadquarterCost01(
			BigDecimal totalAmountHeadquarterCost01) {
		this.totalAmountHeadquarterCost01 = totalAmountHeadquarterCost01;
	}


	public Integer getTotalQty02() {
		return totalQty02;
	}


	public void setTotalQty02(Integer totalQty02) {
		this.totalQty02 = totalQty02;
	}


	public BigDecimal getTotalAmount02() {
		return totalAmount02;
	}


	public void setTotalAmount02(BigDecimal totalAmount02) {
		this.totalAmount02 = totalAmount02;
	}


	public BigDecimal getTotalAmountUnitCost02() {
		return totalAmountUnitCost02;
	}


	public void setTotalAmountUnitCost02(BigDecimal totalAmountUnitCost02) {
		this.totalAmountUnitCost02 = totalAmountUnitCost02;
	}


	public BigDecimal getTotalAmountRegionCost02() {
		return totalAmountRegionCost02;
	}


	public void setTotalAmountRegionCost02(BigDecimal totalAmountRegionCost02) {
		this.totalAmountRegionCost02 = totalAmountRegionCost02;
	}


	public BigDecimal getTotalAmountHeadquarterCost02() {
		return totalAmountHeadquarterCost02;
	}


	public void setTotalAmountHeadquarterCost02(
			BigDecimal totalAmountHeadquarterCost02) {
		this.totalAmountHeadquarterCost02 = totalAmountHeadquarterCost02;
	}


	public Integer getTotalQty03() {
		return totalQty03;
	}


	public void setTotalQty03(Integer totalQty03) {
		this.totalQty03 = totalQty03;
	}


	public BigDecimal getTotalAmount03() {
		return totalAmount03;
	}


	public void setTotalAmount03(BigDecimal totalAmount03) {
		this.totalAmount03 = totalAmount03;
	}


	public BigDecimal getTotalAmountUnitCost03() {
		return totalAmountUnitCost03;
	}


	public void setTotalAmountUnitCost03(BigDecimal totalAmountUnitCost03) {
		this.totalAmountUnitCost03 = totalAmountUnitCost03;
	}


	public BigDecimal getTotalAmountRegionCost03() {
		return totalAmountRegionCost03;
	}


	public void setTotalAmountRegionCost03(BigDecimal totalAmountRegionCost03) {
		this.totalAmountRegionCost03 = totalAmountRegionCost03;
	}


	public BigDecimal getTotalAmountHeadquarterCost03() {
		return totalAmountHeadquarterCost03;
	}


	public void setTotalAmountHeadquarterCost03(
			BigDecimal totalAmountHeadquarterCost03) {
		this.totalAmountHeadquarterCost03 = totalAmountHeadquarterCost03;
	}


	public Integer getTotalQty04() {
		return totalQty04;
	}


	public void setTotalQty04(Integer totalQty04) {
		this.totalQty04 = totalQty04;
	}


	public BigDecimal getTotalAmount04() {
		return totalAmount04;
	}


	public void setTotalAmount04(BigDecimal totalAmount04) {
		this.totalAmount04 = totalAmount04;
	}


	public BigDecimal getTotalAmountUnitCost04() {
		return totalAmountUnitCost04;
	}


	public void setTotalAmountUnitCost04(BigDecimal totalAmountUnitCost04) {
		this.totalAmountUnitCost04 = totalAmountUnitCost04;
	}


	public BigDecimal getTotalAmountRegionCost04() {
		return totalAmountRegionCost04;
	}


	public void setTotalAmountRegionCost04(BigDecimal totalAmountRegionCost04) {
		this.totalAmountRegionCost04 = totalAmountRegionCost04;
	}


	public BigDecimal getTotalAmountHeadquarterCost04() {
		return totalAmountHeadquarterCost04;
	}


	public void setTotalAmountHeadquarterCost04(
			BigDecimal totalAmountHeadquarterCost04) {
		this.totalAmountHeadquarterCost04 = totalAmountHeadquarterCost04;
	}


	public Integer getTotalQty05() {
		return totalQty05;
	}


	public void setTotalQty05(Integer totalQty05) {
		this.totalQty05 = totalQty05;
	}


	public BigDecimal getTotalAmount05() {
		return totalAmount05;
	}


	public void setTotalAmount05(BigDecimal totalAmount05) {
		this.totalAmount05 = totalAmount05;
	}


	public BigDecimal getTotalAmountUnitCost05() {
		return totalAmountUnitCost05;
	}


	public void setTotalAmountUnitCost05(BigDecimal totalAmountUnitCost05) {
		this.totalAmountUnitCost05 = totalAmountUnitCost05;
	}


	public BigDecimal getTotalAmountRegionCost05() {
		return totalAmountRegionCost05;
	}


	public void setTotalAmountRegionCost05(BigDecimal totalAmountRegionCost05) {
		this.totalAmountRegionCost05 = totalAmountRegionCost05;
	}


	public BigDecimal getTotalAmountHeadquarterCost05() {
		return totalAmountHeadquarterCost05;
	}


	public void setTotalAmountHeadquarterCost05(
			BigDecimal totalAmountHeadquarterCost05) {
		this.totalAmountHeadquarterCost05 = totalAmountHeadquarterCost05;
	}


	public Integer getTotalQty06() {
		return totalQty06;
	}


	public void setTotalQty06(Integer totalQty06) {
		this.totalQty06 = totalQty06;
	}


	public BigDecimal getTotalAmount06() {
		return totalAmount06;
	}


	public void setTotalAmount06(BigDecimal totalAmount06) {
		this.totalAmount06 = totalAmount06;
	}


	public BigDecimal getTotalAmountUnitCost06() {
		return totalAmountUnitCost06;
	}


	public void setTotalAmountUnitCost06(BigDecimal totalAmountUnitCost06) {
		this.totalAmountUnitCost06 = totalAmountUnitCost06;
	}


	public BigDecimal getTotalAmountRegionCost06() {
		return totalAmountRegionCost06;
	}


	public void setTotalAmountRegionCost06(BigDecimal totalAmountRegionCost06) {
		this.totalAmountRegionCost06 = totalAmountRegionCost06;
	}


	public BigDecimal getTotalAmountHeadquarterCost06() {
		return totalAmountHeadquarterCost06;
	}


	public void setTotalAmountHeadquarterCost06(
			BigDecimal totalAmountHeadquarterCost06) {
		this.totalAmountHeadquarterCost06 = totalAmountHeadquarterCost06;
	}


	public Integer getTotalQty07() {
		return totalQty07;
	}


	public void setTotalQty07(Integer totalQty07) {
		this.totalQty07 = totalQty07;
	}


	public BigDecimal getTotalAmount07() {
		return totalAmount07;
	}


	public void setTotalAmount07(BigDecimal totalAmount07) {
		this.totalAmount07 = totalAmount07;
	}


	public BigDecimal getTotalAmountUnitCost07() {
		return totalAmountUnitCost07;
	}


	public void setTotalAmountUnitCost07(BigDecimal totalAmountUnitCost07) {
		this.totalAmountUnitCost07 = totalAmountUnitCost07;
	}


	public BigDecimal getTotalAmountRegionCost07() {
		return totalAmountRegionCost07;
	}


	public void setTotalAmountRegionCost07(BigDecimal totalAmountRegionCost07) {
		this.totalAmountRegionCost07 = totalAmountRegionCost07;
	}


	public BigDecimal getTotalAmountHeadquarterCost07() {
		return totalAmountHeadquarterCost07;
	}


	public void setTotalAmountHeadquarterCost07(
			BigDecimal totalAmountHeadquarterCost07) {
		this.totalAmountHeadquarterCost07 = totalAmountHeadquarterCost07;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

    
    

}