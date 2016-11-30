package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-13 16:42:47
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
public class BillSalesOutstandingAnalysis extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	/**
//     * 主键
//     */
//    private String id;
    
	/**
	 * 明细流水号
	 */
	private String dtlId;
	
    /**
     * 总记录数
     */
    private Integer total;
    
    /**
     * 单据编号
     */
    private String orderNo;
    
    /**
     * 公司编号
     */
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 经营区域编号
     */
    private String zoneNo;

    /**
     * 经营区域名称
     */
    private String zoneName;

    /**
     * 行政省编码
     */
    private String provinceNo;

    /**
     * 行政省名称
     */
    private String provinceName;

    /**
     * 管理城市编号
     */
    private String organNo1;

    /**
     * 管理城市名称
     */
    private String organName1;

    /**
     * 经营城市编号
     */
    private String organNo2;

    /**
     * 经营城市名称
     */
    private String organName2;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String bsgroupsName;

    /**
     * 商场编码
     */
    private String mallNo;

    /**
     * 商场名称
     */
    private String mallName;

    /**
     * 商圈编码
     */
    private String cmcdistNo;

    /**
     * 商圈名称
     */
    private String cmcdistName;

    /**
     * 片区编码
     */
    private String regionNo;

    /**
     * 片区名称
     */
    private String regionName;

    /**
     * 销售类别(1:零售 2:批发)
     */
    private String saleType;

    /**
     * 业务类型(1-调货、2-员购、3-团购、4-批发、5-门店、6-其他)
     */
    private String bizType;

    /**
     * 门店级别( A、B、C、D、E)
     */
    private String shopLevel;

    /**
     * 店铺大类 批发零售(门店必填,1:零售；2:批发)
     */
    private String saleMode;

    /**
     * 店铺小类 (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场

特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

    /**
     * 主营品类(门店必填, 1:男鞋 2:女鞋 3:童鞋 4:综合)
     */
    private String major;

    /**
     * 店铺细类 单品多品(门店必填,C:多品店 D:单品店)
     */
    private String multi;

    /**
     * 店铺编码
     */
    private String shopNo;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 营业员工号
     */
    private String assistantNo;

    /**
     * 营业员姓名
     */
    private String assistantName;

    /**
     * 商场(品牌)活动单号
     */
    private String activityNo;

    /**
     * 活动类型,1-买换 2-打折 3-其他
     */
    private Byte activityType;
    
    /**
	 * 活动类型字符串
	 */
	private String activityTypeStr;

    /**
     * 促销活动编号
     */
    private String proNo;

    /**
     * 促销活动名称
     */
    private String proName;

    /**
     * 活动起始日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date proStartDate;

    /**
     * 活动终止日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date proEndDate;

    /**
     * 力度
     */
    private BigDecimal strength;

    /**
     * 虚实,0-虚数 1-实数
     */
    private Byte virtualFlag;
    
    /**
     * 虚实字符串
     */
    private String virtualFlagStr;

    /**
     * 属性,1-满送 2-满减 3-折扣 4-其他
     */
    private Byte properties;
    
    private String propertiesStr;

    /**
     * 扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal rate;

    /**
     * 扣率代码,A B...
     */
    private String rateCode;

    /**
     * 扣率名称
     */
    private String rateName;

    /**
     * 商场结算码
     */
    private String billingCode;

    /**
     * 商品SKU
     */
    private String skuNo;

    /**
     * 商品内码
     */
    private String itemNo;

    /**
     * 商品尺码
     */
    private String sizeNo;

    /**
     * 商品编号
     */
    private String itemCode;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别中文名称      大类
     */
    private String categoryName;

    /**
     * 商品编码
     */
    private String rootCategoryNo;

    /**
     * 商品名称           中类
     */
    private String rootCategoryName;

    /**
     * 商品名称             小类
     */
    private String subCategoruName;
    
    /**
     * 结算基数,0-销售金额 1-牌价金额
     */
    private Boolean balanceBase;

    /**
     * 季节编码
     */
    private String seasonNo;

    /**
     * 季节名称
     */
    private String seasonName;

    /**
     * 年份编码
     */
    private String yearCode;

    /**
     * 年份
     */
    private String year;

    /**
     * 季节(下拉框选择,A:春 B:夏 C:秋 D:冬)
     */
    private String sellSeason;

    /**
     * 分类编码
     */
    private String styleNo;

    /**
     * 分类名称-新旧款
     */
    private String styleName;

    /**
     * 性别(下拉框选择,值: 男, 女, 童, 无性别)
     */
    private String gender;

    /**
     * 订货形式(下拉框选择,值: 1:自产 2:外购 3:地区自购)
     */
    private String orderfrom;

    /**
     * 风格
     */
    private String style;

    /**
     * 颜色编码
     */
    private String colorNo;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 销售日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date outDate;

    
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date outDateStart;
    
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date outDateEnd;
    /**
     * 数量
     */
    private Integer qty;

    /**
     * 牌价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagAmount;

    /**
     * 商品折扣价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal discPrice;

    /**
     * 商品总金额,(结算价-减价)*数量
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal amount;

    /**
     * 终端销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesAmount;

    /**
     * 成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal cost;
    
    /**
     * 单位成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal unitCost;
    
    /**
     * 地区成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal regionCost;

    /**
     * 扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal deductAmount;

    /**
     * 合同扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal contractRate;

    /**
     * 合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal conpriceDeductAmount;

    /**
     * 合同阶梯加扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal conpriceLadderAmount;

    /**
     * 促销活动加扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal promPlusbuckleAmount;

    /**
     * 回款额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal backAmount;

    /**
     * 折扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal discountRate;

    /**
     * 毛利率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal grossMarginRate;

    /**
     * 扣费率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal deductionRate;

    /**
     * 合同正价扣费率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal conpriceDeductRate;

    /**
     * 合同阶梯扣加扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal conpriceLadderRate;

    /**
     * 促销活动加扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal promPlusbuckleRate;

    /**
     * 净折扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal theDiscountRate;

    /**
     * 净毛利率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal theMarginRate;

    /**
     * 回款率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal reimbursementRate;

    /**
     * 合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesamountDiscount;

    /**
     * 促销折扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal salesamountProdiscount;
    
    
    /**
	 * 发起方式字符串
	 */
	private String launchTypeStr;
	
	/**
     * 扣率类型 ( 1-合同正价扣 2-合同阶梯扣 3-促销扣率 4-退货原扣率)
     */
    private Short rateType;
    
    private String rateTypeStr;
    
    /**
     * 区分记录属于哪个表:0 = gms ;1 = pos
     */
    private Integer sourceType;
    
    /**
     * 结算基数名称
     */
    private String balanceBaseName;
    
    /**
     * 结合回款扣率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal synthesizeReturnRate;
    
    /**
     * 外卡编号
     */
    private String wildcardNo;
    
    /**
     * 外卡名称
     */
    private String wildcardName;
    
    /**
     * 现价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal saleAmount;
    
    /**
     * 活动折扣
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal rateDiscount;
    
    /**
     * 虚数活动实际金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal virtualAmount;
    
    
    /**
     * 品牌部编号
     */
    private String brandUnitNo;
    
    /**
     * 品牌部名称
     */
    private String brandUnitName;
    
    /**
     * 结算单差异金额平摊后金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal differenceAmount;
    
    /**
     * 西北店铺名称
     */
    private String xb_shortName;
    
    /**
     * 采购成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal purchasePrice;
    
    /**
     * 森达毛利率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal sdGrossMarginRate;
    
    /**
     * 森达净毛利率
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal sdTheMarginRate;
    
    /**
     * 店铺编号替换
     */
    private String shopNoReplace;
    
    /**
     * 季节
     */
    private String purchaseSeason;
    
    /**
     * 商品标志
     */
    private String itemFlag;
    

//    /**
//     * 
//     * {@linkplain #id}
//     *
//     * @return the value of bill_sales_outstanding_analysis.id
//     */
//    public String getId() {
//        return id;
//    }
//
//    /**
//     * 
//     * {@linkplain #id}
//     * @param id the value for bill_sales_outstanding_analysis.id
//     */
//    public void setId(String id) {
//        this.id = id;
//    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_sales_outstanding_analysis.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_sales_outstanding_analysis.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of bill_sales_outstanding_analysis.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for bill_sales_outstanding_analysis.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     *
     * @return the value of bill_sales_outstanding_analysis.province_no
     */
    public String getProvinceNo() {
        return provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     * @param provinceNo the value for bill_sales_outstanding_analysis.province_no
     */
    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceName}
     *
     * @return the value of bill_sales_outstanding_analysis.province_name
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 
     * {@linkplain #provinceName}
     * @param provinceName the value for bill_sales_outstanding_analysis.province_name
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 
     * {@linkplain #organNo1}
     *
     * @return the value of bill_sales_outstanding_analysis.organ_no1
     */
    public String getOrganNo1() {
        return organNo1;
    }

    /**
     * 
     * {@linkplain #organNo1}
     * @param organNo1 the value for bill_sales_outstanding_analysis.organ_no1
     */
    public void setOrganNo1(String organNo1) {
        this.organNo1 = organNo1;
    }

    /**
     * 
     * {@linkplain #organName1}
     *
     * @return the value of bill_sales_outstanding_analysis.organ_name1
     */
    public String getOrganName1() {
        return organName1;
    }

    /**
     * 
     * {@linkplain #organName1}
     * @param organName1 the value for bill_sales_outstanding_analysis.organ_name1
     */
    public void setOrganName1(String organName1) {
        this.organName1 = organName1;
    }

    /**
     * 
     * {@linkplain #organNo2}
     *
     * @return the value of bill_sales_outstanding_analysis.organ_no2
     */
    public String getOrganNo2() {
        return organNo2;
    }

    /**
     * 
     * {@linkplain #organNo2}
     * @param organNo2 the value for bill_sales_outstanding_analysis.organ_no2
     */
    public void setOrganNo2(String organNo2) {
        this.organNo2 = organNo2;
    }

    /**
     * 
     * {@linkplain #organName2}
     *
     * @return the value of bill_sales_outstanding_analysis.organ_name2
     */
    public String getOrganName2() {
        return organName2;
    }

    /**
     * 
     * {@linkplain #organName2}
     * @param organName2 the value for bill_sales_outstanding_analysis.organ_name2
     */
    public void setOrganName2(String organName2) {
        this.organName2 = organName2;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_sales_outstanding_analysis.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_sales_outstanding_analysis.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of bill_sales_outstanding_analysis.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for bill_sales_outstanding_analysis.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #mallNo}
     *
     * @return the value of bill_sales_outstanding_analysis.mall_no
     */
    public String getMallNo() {
        return mallNo;
    }

    /**
     * 
     * {@linkplain #mallNo}
     * @param mallNo the value for bill_sales_outstanding_analysis.mall_no
     */
    public void setMallNo(String mallNo) {
        this.mallNo = mallNo;
    }

    /**
     * 
     * {@linkplain #mallName}
     *
     * @return the value of bill_sales_outstanding_analysis.mall_name
     */
    public String getMallName() {
        return mallName;
    }

    /**
     * 
     * {@linkplain #mallName}
     * @param mallName the value for bill_sales_outstanding_analysis.mall_name
     */
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    /**
     * 
     * {@linkplain #cmcdistNo}
     *
     * @return the value of bill_sales_outstanding_analysis.cmcdist_no
     */
    public String getCmcdistNo() {
        return cmcdistNo;
    }

    /**
     * 
     * {@linkplain #cmcdistNo}
     * @param cmcdistNo the value for bill_sales_outstanding_analysis.cmcdist_no
     */
    public void setCmcdistNo(String cmcdistNo) {
        this.cmcdistNo = cmcdistNo;
    }

    /**
     * 
     * {@linkplain #cmcdistName}
     *
     * @return the value of bill_sales_outstanding_analysis.cmcdist_name
     */
    public String getCmcdistName() {
        return cmcdistName;
    }

    /**
     * 
     * {@linkplain #cmcdistName}
     * @param cmcdistName the value for bill_sales_outstanding_analysis.cmcdist_name
     */
    public void setCmcdistName(String cmcdistName) {
        this.cmcdistName = cmcdistName;
    }

    /**
     * 
     * {@linkplain #regionNo}
     *
     * @return the value of bill_sales_outstanding_analysis.region_no
     */
    public String getRegionNo() {
        return regionNo;
    }

    /**
     * 
     * {@linkplain #regionNo}
     * @param regionNo the value for bill_sales_outstanding_analysis.region_no
     */
    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    /**
     * 
     * {@linkplain #regionName}
     *
     * @return the value of bill_sales_outstanding_analysis.region_name
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 
     * {@linkplain #regionName}
     * @param regionName the value for bill_sales_outstanding_analysis.region_name
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 
     * {@linkplain #saleType}
     *
     * @return the value of bill_sales_outstanding_analysis.sale_type
     */
    public String getSaleType() {
        return saleType;
    }

    /**
     * 
     * {@linkplain #saleType}
     * @param saleType the value for bill_sales_outstanding_analysis.sale_type
     */
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    /**
     * 
     * {@linkplain #bizType}
     *
     * @return the value of bill_sales_outstanding_analysis.biz_type
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 
     * {@linkplain #bizType}
     * @param bizType the value for bill_sales_outstanding_analysis.biz_type
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 
     * {@linkplain #shopLevel}
     *
     * @return the value of bill_sales_outstanding_analysis.shop_level
     */
    public String getShopLevel() {
        return shopLevel;
    }

    /**
     * 
     * {@linkplain #shopLevel}
     * @param shopLevel the value for bill_sales_outstanding_analysis.shop_level
     */
    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    /**
     * 
     * {@linkplain #saleMode}
     *
     * @return the value of bill_sales_outstanding_analysis.sale_mode
     */
    public String getSaleMode() {
        return saleMode;
    }

    /**
     * 
     * {@linkplain #saleMode}
     * @param saleMode the value for bill_sales_outstanding_analysis.sale_mode
     */
    public void setSaleMode(String saleMode) {
        this.saleMode = saleMode;
    }

    /**
     * 
     * {@linkplain #retailType}
     *
     * @return the value of bill_sales_outstanding_analysis.retail_type
     */
    public String getRetailType() {
        return retailType;
    }

    /**
     * 
     * {@linkplain #retailType}
     * @param retailType the value for bill_sales_outstanding_analysis.retail_type
     */
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    /**
     * 
     * {@linkplain #major}
     *
     * @return the value of bill_sales_outstanding_analysis.major
     */
    public String getMajor() {
        return major;
    }

    /**
     * 
     * {@linkplain #major}
     * @param major the value for bill_sales_outstanding_analysis.major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 
     * {@linkplain #multi}
     *
     * @return the value of bill_sales_outstanding_analysis.multi
     */
    public String getMulti() {
        return multi;
    }

    /**
     * 
     * {@linkplain #multi}
     * @param multi the value for bill_sales_outstanding_analysis.multi
     */
    public void setMulti(String multi) {
        this.multi = multi;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_sales_outstanding_analysis.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_sales_outstanding_analysis.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_sales_outstanding_analysis.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_sales_outstanding_analysis.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_sales_outstanding_analysis.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_sales_outstanding_analysis.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of bill_sales_outstanding_analysis.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for bill_sales_outstanding_analysis.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of bill_sales_outstanding_analysis.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for bill_sales_outstanding_analysis.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    /**
     * 
     * {@linkplain #activityNo}
     *
     * @return the value of bill_sales_outstanding_analysis.activity_no
     */
    public String getActivityNo() {
        return activityNo;
    }

    /**
     * 
     * {@linkplain #activityNo}
     * @param activityNo the value for bill_sales_outstanding_analysis.activity_no
     */
    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
    }

    /**
     * 
     * {@linkplain #activityType}
     *
     * @return the value of bill_sales_outstanding_analysis.activity_type
     */
    public Byte getActivityType() {
        return activityType;
    }

    /**
     * 
     * {@linkplain #activityType}
     * @param activityType the value for bill_sales_outstanding_analysis.activity_type
     */
    public void setActivityType(Byte activityType) {
        this.activityType = activityType;
    }

    /**
     * 
     * {@linkplain #proNo}
     *
     * @return the value of bill_sales_outstanding_analysis.pro_no
     */
    public String getProNo() {
        return proNo;
    }

    /**
     * 
     * {@linkplain #proNo}
     * @param proNo the value for bill_sales_outstanding_analysis.pro_no
     */
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    /**
     * 
     * {@linkplain #proName}
     *
     * @return the value of bill_sales_outstanding_analysis.pro_name
     */
    public String getProName() {
        return proName;
    }

    /**
     * 
     * {@linkplain #proName}
     * @param proName the value for bill_sales_outstanding_analysis.pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * 
     * {@linkplain #proStartDate}
     *
     * @return the value of bill_sales_outstanding_analysis.pro_start_date
     */
    public Date getProStartDate() {
        return proStartDate;
    }

    /**
     * 
     * {@linkplain #proStartDate}
     * @param proStartDate the value for bill_sales_outstanding_analysis.pro_start_date
     */
    public void setProStartDate(Date proStartDate) {
        this.proStartDate = proStartDate;
    }

    /**
     * 
     * {@linkplain #proEndDate}
     *
     * @return the value of bill_sales_outstanding_analysis.pro_end_date
     */
    public Date getProEndDate() {
        return proEndDate;
    }

    /**
     * 
     * {@linkplain #proEndDate}
     * @param proEndDate the value for bill_sales_outstanding_analysis.pro_end_date
     */
    public void setProEndDate(Date proEndDate) {
        this.proEndDate = proEndDate;
    }

    /**
     * 
     * {@linkplain #strength}
     *
     * @return the value of bill_sales_outstanding_analysis.strength
     */
    public BigDecimal getStrength() {
        return strength;
    }

    /**
     * 
     * {@linkplain #strength}
     * @param strength the value for bill_sales_outstanding_analysis.strength
     */
    public void setStrength(BigDecimal strength) {
        this.strength = strength;
    }

    /**
     * 
     * {@linkplain #virtualFlag}
     *
     * @return the value of bill_sales_outstanding_analysis.virtual_flag
     */
    public Byte getVirtualFlag() {
        return virtualFlag;
    }

    /**
     * 
     * {@linkplain #virtualFlag}
     * @param virtualFlag the value for bill_sales_outstanding_analysis.virtual_flag
     */
    public void setVirtualFlag(Byte virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    /**
     * 
     * {@linkplain #properties}
     *
     * @return the value of bill_sales_outstanding_analysis.properties
     */
    public Byte getProperties() {
        return properties;
    }

    /**
     * 
     * {@linkplain #properties}
     * @param properties the value for bill_sales_outstanding_analysis.properties
     */
    public void setProperties(Byte properties) {
        this.properties = properties;
    }

    /**
     * 
     * {@linkplain #rate}
     *
     * @return the value of bill_sales_outstanding_analysis.rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 
     * {@linkplain #rate}
     * @param rate the value for bill_sales_outstanding_analysis.rate
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 
     * {@linkplain #rateCode}
     *
     * @return the value of bill_sales_outstanding_analysis.rate_code
     */
    public String getRateCode() {
        return rateCode;
    }

    /**
     * 
     * {@linkplain #rateCode}
     * @param rateCode the value for bill_sales_outstanding_analysis.rate_code
     */
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    /**
     * 
     * {@linkplain #rateName}
     *
     * @return the value of bill_sales_outstanding_analysis.rate_name
     */
    public String getRateName() {
        return rateName;
    }

    /**
     * 
     * {@linkplain #rateName}
     * @param rateName the value for bill_sales_outstanding_analysis.rate_name
     */
    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    /**
     * 
     * {@linkplain #billingCode}
     *
     * @return the value of bill_sales_outstanding_analysis.billing_code
     */
    public String getBillingCode() {
        return billingCode;
    }

    /**
     * 
     * {@linkplain #billingCode}
     * @param billingCode the value for bill_sales_outstanding_analysis.billing_code
     */
    public void setBillingCode(String billingCode) {
        this.billingCode = billingCode;
    }

    /**
     * 
     * {@linkplain #skuNo}
     *
     * @return the value of bill_sales_outstanding_analysis.sku_no
     */
    public String getSkuNo() {
        return skuNo;
    }

    /**
     * 
     * {@linkplain #skuNo}
     * @param skuNo the value for bill_sales_outstanding_analysis.sku_no
     */
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     *
     * @return the value of bill_sales_outstanding_analysis.item_no
     */
    public String getItemNo() {
        return itemNo;
    }

    /**
     * 
     * {@linkplain #itemNo}
     * @param itemNo the value for bill_sales_outstanding_analysis.item_no
     */
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     *
     * @return the value of bill_sales_outstanding_analysis.size_no
     */
    public String getSizeNo() {
        return sizeNo;
    }

    /**
     * 
     * {@linkplain #sizeNo}
     * @param sizeNo the value for bill_sales_outstanding_analysis.size_no
     */
    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    /**
     * 
     * {@linkplain #itemCode}
     *
     * @return the value of bill_sales_outstanding_analysis.item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 
     * {@linkplain #itemCode}
     * @param itemCode the value for bill_sales_outstanding_analysis.item_code
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 
     * {@linkplain #itemName}
     *
     * @return the value of bill_sales_outstanding_analysis.item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * {@linkplain #itemName}
     * @param itemName the value for bill_sales_outstanding_analysis.item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * {@linkplain #barcode}
     *
     * @return the value of bill_sales_outstanding_analysis.barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 
     * {@linkplain #barcode}
     * @param barcode the value for bill_sales_outstanding_analysis.barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_sales_outstanding_analysis.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_sales_outstanding_analysis.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_sales_outstanding_analysis.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_sales_outstanding_analysis.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_sales_outstanding_analysis.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_sales_outstanding_analysis.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_sales_outstanding_analysis.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_sales_outstanding_analysis.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #rootCategoryNo}
     *
     * @return the value of bill_sales_outstanding_analysis.root_category_no
     */
    public String getRootCategoryNo() {
        return rootCategoryNo;
    }

    /**
     * 
     * {@linkplain #rootCategoryNo}
     * @param rootCategoryNo the value for bill_sales_outstanding_analysis.root_category_no
     */
    public void setRootCategoryNo(String rootCategoryNo) {
        this.rootCategoryNo = rootCategoryNo;
    }

    /**
     * 
     * {@linkplain #rootCategoryName}
     *
     * @return the value of bill_sales_outstanding_analysis.root_category_name
     */
    public String getRootCategoryName() {
        return rootCategoryName;
    }

    /**
     * 
     * {@linkplain #rootCategoryName}
     * @param rootCategoryName the value for bill_sales_outstanding_analysis.root_category_name
     */
    public void setRootCategoryName(String rootCategoryName) {
        this.rootCategoryName = rootCategoryName;
    }

    /**
     * 
     * {@linkplain #balanceBase}
     *
     * @return the value of bill_sales_outstanding_analysis.balance_base
     */
    public Boolean getBalanceBase() {
        return balanceBase;
    }

    /**
     * 
     * {@linkplain #balanceBase}
     * @param balanceBase the value for bill_sales_outstanding_analysis.balance_base
     */
    public void setBalanceBase(Boolean balanceBase) {
        this.balanceBase = balanceBase;
    }

    /**
     * 
     * {@linkplain #seasonNo}
     *
     * @return the value of bill_sales_outstanding_analysis.season_no
     */
    public String getSeasonNo() {
        return seasonNo;
    }

    /**
     * 
     * {@linkplain #seasonNo}
     * @param seasonNo the value for bill_sales_outstanding_analysis.season_no
     */
    public void setSeasonNo(String seasonNo) {
        this.seasonNo = seasonNo;
    }

    /**
     * 
     * {@linkplain #seasonName}
     *
     * @return the value of bill_sales_outstanding_analysis.season_name
     */
    public String getSeasonName() {
        return seasonName;
    }

    /**
     * 
     * {@linkplain #seasonName}
     * @param seasonName the value for bill_sales_outstanding_analysis.season_name
     */
    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    /**
     * 
     * {@linkplain #yearCode}
     *
     * @return the value of bill_sales_outstanding_analysis.year_code
     */
    public String getYearCode() {
        return yearCode;
    }

    /**
     * 
     * {@linkplain #yearCode}
     * @param yearCode the value for bill_sales_outstanding_analysis.year_code
     */
    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of bill_sales_outstanding_analysis.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for bill_sales_outstanding_analysis.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #sellSeason}
     *
     * @return the value of bill_sales_outstanding_analysis.sell_season
     */
    public String getSellSeason() {
        return sellSeason;
    }

    /**
     * 
     * {@linkplain #sellSeason}
     * @param sellSeason the value for bill_sales_outstanding_analysis.sell_season
     */
    public void setSellSeason(String sellSeason) {
        this.sellSeason = sellSeason;
    }

    /**
     * 
     * {@linkplain #styleNo}
     *
     * @return the value of bill_sales_outstanding_analysis.style_no
     */
    public String getStyleNo() {
        return styleNo;
    }

    /**
     * 
     * {@linkplain #styleNo}
     * @param styleNo the value for bill_sales_outstanding_analysis.style_no
     */
    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

    /**
     * 
     * {@linkplain #styleName}
     *
     * @return the value of bill_sales_outstanding_analysis.style_name
     */
    public String getStyleName() {
        return styleName;
    }

    /**
     * 
     * {@linkplain #styleName}
     * @param styleName the value for bill_sales_outstanding_analysis.style_name
     */
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    /**
     * 
     * {@linkplain #gender}
     *
     * @return the value of bill_sales_outstanding_analysis.gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * {@linkplain #gender}
     * @param gender the value for bill_sales_outstanding_analysis.gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * {@linkplain #orderfrom}
     *
     * @return the value of bill_sales_outstanding_analysis.orderfrom
     */
    public String getOrderfrom() {
        return orderfrom;
    }

    /**
     * 
     * {@linkplain #orderfrom}
     * @param orderfrom the value for bill_sales_outstanding_analysis.orderfrom
     */
    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom;
    }

    /**
     * 
     * {@linkplain #style}
     *
     * @return the value of bill_sales_outstanding_analysis.style
     */
    public String getStyle() {
        return style;
    }

    /**
     * 
     * {@linkplain #style}
     * @param style the value for bill_sales_outstanding_analysis.style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * 
     * {@linkplain #colorNo}
     *
     * @return the value of bill_sales_outstanding_analysis.color_no
     */
    public String getColorNo() {
        return colorNo;
    }

    /**
     * 
     * {@linkplain #colorNo}
     * @param colorNo the value for bill_sales_outstanding_analysis.color_no
     */
    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

    /**
     * 
     * {@linkplain #colorName}
     *
     * @return the value of bill_sales_outstanding_analysis.color_name
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * 
     * {@linkplain #colorName}
     * @param colorName the value for bill_sales_outstanding_analysis.color_name
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    /**
     * 
     * {@linkplain #outDate}
     *
     * @return the value of bill_sales_outstanding_analysis.out_date
     */
    public Date getOutDate() {
        return outDate;
    }

    /**
     * 
     * {@linkplain #outDate}
     * @param outDate the value for bill_sales_outstanding_analysis.out_date
     */
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of bill_sales_outstanding_analysis.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for bill_sales_outstanding_analysis.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #tagAmount}
     *
     * @return the value of bill_sales_outstanding_analysis.tag_amount
     */
    public BigDecimal getTagAmount() {
        return tagAmount;
    }

    /**
     * 
     * {@linkplain #tagAmount}
     * @param tagAmount the value for bill_sales_outstanding_analysis.tag_amount
     */
    public void setTagAmount(BigDecimal tagAmount) {
        this.tagAmount = tagAmount;
    }

    /**
     * 
     * {@linkplain #discPrice}
     *
     * @return the value of bill_sales_outstanding_analysis.disc_price
     */
    public BigDecimal getDiscPrice() {
        return discPrice;
    }

    /**
     * 
     * {@linkplain #discPrice}
     * @param discPrice the value for bill_sales_outstanding_analysis.disc_price
     */
    public void setDiscPrice(BigDecimal discPrice) {
        this.discPrice = discPrice;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of bill_sales_outstanding_analysis.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for bill_sales_outstanding_analysis.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #salesAmount}
     *
     * @return the value of bill_sales_outstanding_analysis.sales_amount
     */
    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    /**
     * 
     * {@linkplain #salesAmount}
     * @param salesAmount the value for bill_sales_outstanding_analysis.sales_amount
     */
    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    /**
     * 
     * {@linkplain #cost}
     *
     * @return the value of bill_sales_outstanding_analysis.cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * {@linkplain #cost}
     * @param cost the value for bill_sales_outstanding_analysis.cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     *
     * @return the value of bill_sales_outstanding_analysis.deduct_amount
     */
    public BigDecimal getDeductAmount() {
        return deductAmount;
    }

    /**
     * 
     * {@linkplain #deductAmount}
     * @param deductAmount the value for bill_sales_outstanding_analysis.deduct_amount
     */
    public void setDeductAmount(BigDecimal deductAmount) {
        this.deductAmount = deductAmount;
    }

    /**
     * 
     * {@linkplain #contractRate}
     *
     * @return the value of bill_sales_outstanding_analysis.contract_rate
     */
    public BigDecimal getContractRate() {
        return contractRate;
    }

    /**
     * 
     * {@linkplain #contractRate}
     * @param contractRate the value for bill_sales_outstanding_analysis.contract_rate
     */
    public void setContractRate(BigDecimal contractRate) {
        this.contractRate = contractRate;
    }

    /**
     * 
     * {@linkplain #conpriceDeductAmount}
     *
     * @return the value of bill_sales_outstanding_analysis.conprice_deduct_amount
     */
    public BigDecimal getConpriceDeductAmount() {
        return conpriceDeductAmount;
    }

    /**
     * 
     * {@linkplain #conpriceDeductAmount}
     * @param conpriceDeductAmount the value for bill_sales_outstanding_analysis.conprice_deduct_amount
     */
    public void setConpriceDeductAmount(BigDecimal conpriceDeductAmount) {
        this.conpriceDeductAmount = conpriceDeductAmount;
    }

    /**
     * 
     * {@linkplain #conpriceLadderAmount}
     *
     * @return the value of bill_sales_outstanding_analysis.conprice_ladder_amount
     */
    public BigDecimal getConpriceLadderAmount() {
        return conpriceLadderAmount;
    }

    /**
     * 
     * {@linkplain #conpriceLadderAmount}
     * @param conpriceLadderAmount the value for bill_sales_outstanding_analysis.conprice_ladder_amount
     */
    public void setConpriceLadderAmount(BigDecimal conpriceLadderAmount) {
        this.conpriceLadderAmount = conpriceLadderAmount;
    }

    /**
     * 
     * {@linkplain #promPlusbuckleAmount}
     *
     * @return the value of bill_sales_outstanding_analysis.prom_plusbuckle_amount
     */
    public BigDecimal getPromPlusbuckleAmount() {
        return promPlusbuckleAmount;
    }

    /**
     * 
     * {@linkplain #promPlusbuckleAmount}
     * @param promPlusbuckleAmount the value for bill_sales_outstanding_analysis.prom_plusbuckle_amount
     */
    public void setPromPlusbuckleAmount(BigDecimal promPlusbuckleAmount) {
        this.promPlusbuckleAmount = promPlusbuckleAmount;
    }

    /**
     * 
     * {@linkplain #backAmount}
     *
     * @return the value of bill_sales_outstanding_analysis.back_amount
     */
    public BigDecimal getBackAmount() {
        return backAmount;
    }

    /**
     * 
     * {@linkplain #backAmount}
     * @param backAmount the value for bill_sales_outstanding_analysis.back_amount
     */
    public void setBackAmount(BigDecimal backAmount) {
        this.backAmount = backAmount;
    }

    /**
     * 
     * {@linkplain #discountRate}
     *
     * @return the value of bill_sales_outstanding_analysis.discount_rate
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * 
     * {@linkplain #discountRate}
     * @param discountRate the value for bill_sales_outstanding_analysis.discount_rate
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * 
     * {@linkplain #grossMarginRate}
     *
     * @return the value of bill_sales_outstanding_analysis.gross_margin_rate
     */
    public BigDecimal getGrossMarginRate() {
        return grossMarginRate;
    }

    /**
     * 
     * {@linkplain #grossMarginRate}
     * @param grossMarginRate the value for bill_sales_outstanding_analysis.gross_margin_rate
     */
    public void setGrossMarginRate(BigDecimal grossMarginRate) {
        this.grossMarginRate = grossMarginRate;
    }

    /**
     * 
     * {@linkplain #deductionRate}
     *
     * @return the value of bill_sales_outstanding_analysis.deduction_rate
     */
    public BigDecimal getDeductionRate() {
        return deductionRate;
    }

    /**
     * 
     * {@linkplain #deductionRate}
     * @param deductionRate the value for bill_sales_outstanding_analysis.deduction_rate
     */
    public void setDeductionRate(BigDecimal deductionRate) {
        this.deductionRate = deductionRate;
    }

    /**
     * 
     * {@linkplain #conpriceDeductRate}
     *
     * @return the value of bill_sales_outstanding_analysis.conprice_deduct_rate
     */
    public BigDecimal getConpriceDeductRate() {
        return conpriceDeductRate;
    }

    /**
     * 
     * {@linkplain #conpriceDeductRate}
     * @param conpriceDeductRate the value for bill_sales_outstanding_analysis.conprice_deduct_rate
     */
    public void setConpriceDeductRate(BigDecimal conpriceDeductRate) {
        this.conpriceDeductRate = conpriceDeductRate;
    }

    /**
     * 
     * {@linkplain #conpriceLadderRate}
     *
     * @return the value of bill_sales_outstanding_analysis.conprice_ladder_rate
     */
    public BigDecimal getConpriceLadderRate() {
        return conpriceLadderRate;
    }

    /**
     * 
     * {@linkplain #conpriceLadderRate}
     * @param conpriceLadderRate the value for bill_sales_outstanding_analysis.conprice_ladder_rate
     */
    public void setConpriceLadderRate(BigDecimal conpriceLadderRate) {
        this.conpriceLadderRate = conpriceLadderRate;
    }

    /**
     * 
     * {@linkplain #promPlusbuckleRate}
     *
     * @return the value of bill_sales_outstanding_analysis.prom_plusbuckle_rate
     */
    public BigDecimal getPromPlusbuckleRate() {
        return promPlusbuckleRate;
    }

    /**
     * 
     * {@linkplain #promPlusbuckleRate}
     * @param promPlusbuckleRate the value for bill_sales_outstanding_analysis.prom_plusbuckle_rate
     */
    public void setPromPlusbuckleRate(BigDecimal promPlusbuckleRate) {
        this.promPlusbuckleRate = promPlusbuckleRate;
    }

    /**
     * 
     * {@linkplain #theDiscountRate}
     *
     * @return the value of bill_sales_outstanding_analysis.the_discount_rate
     */
    public BigDecimal getTheDiscountRate() {
        return theDiscountRate;
    }

    /**
     * 
     * {@linkplain #theDiscountRate}
     * @param theDiscountRate the value for bill_sales_outstanding_analysis.the_discount_rate
     */
    public void setTheDiscountRate(BigDecimal theDiscountRate) {
        this.theDiscountRate = theDiscountRate;
    }

    /**
     * 
     * {@linkplain #theMarginRate}
     *
     * @return the value of bill_sales_outstanding_analysis.the_margin_rate
     */
    public BigDecimal getTheMarginRate() {
        return theMarginRate;
    }

    /**
     * 
     * {@linkplain #theMarginRate}
     * @param theMarginRate the value for bill_sales_outstanding_analysis.the_margin_rate
     */
    public void setTheMarginRate(BigDecimal theMarginRate) {
        this.theMarginRate = theMarginRate;
    }

    /**
     * 
     * {@linkplain #reimbursementRate}
     *
     * @return the value of bill_sales_outstanding_analysis.reimbursement_rate
     */
    public BigDecimal getReimbursementRate() {
        return reimbursementRate;
    }

    /**
     * 
     * {@linkplain #reimbursementRate}
     * @param reimbursementRate the value for bill_sales_outstanding_analysis.reimbursement_rate
     */
    public void setReimbursementRate(BigDecimal reimbursementRate) {
        this.reimbursementRate = reimbursementRate;
    }

    /**
     * 
     * {@linkplain #salesamountDiscount}
     *
     * @return the value of bill_sales_outstanding_analysis.salesamount_discount
     */
    public BigDecimal getSalesamountDiscount() {
        return salesamountDiscount;
    }

    /**
     * 
     * {@linkplain #salesamountDiscount}
     * @param salesamountDiscount the value for bill_sales_outstanding_analysis.salesamount_discount
     */
    public void setSalesamountDiscount(BigDecimal salesamountDiscount) {
        this.salesamountDiscount = salesamountDiscount;
    }

    /**
     * 
     * {@linkplain #salesamountProdiscount}
     *
     * @return the value of bill_sales_outstanding_analysis.salesamount_prodiscount
     */
    public BigDecimal getSalesamountProdiscount() {
        return salesamountProdiscount;
    }

    /**
     * 
     * {@linkplain #salesamountProdiscount}
     * @param salesamountProdiscount the value for bill_sales_outstanding_analysis.salesamount_prodiscount
     */
    public void setSalesamountProdiscount(BigDecimal salesamountProdiscount) {
        this.salesamountProdiscount = salesamountProdiscount;
    }

	public Date getOutDateStart() {
		return outDateStart;
	}

	public void setOutDateStart(Date outDateStart) {
		this.outDateStart = outDateStart;
	}

	public Date getOutDateEnd() {
		return outDateEnd;
	}

	public void setOutDateEnd(Date outDateEnd) {
		this.outDateEnd = outDateEnd;
	}

	public String getSubCategoruName() {
		return subCategoruName;
	}

	public void setSubCategoruName(String subCategoruName) {
		this.subCategoruName = subCategoruName;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getActivityTypeStr() {
		return activityTypeStr;
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}

	public String getVirtualFlagStr() {
		return virtualFlagStr;
	}

	public void setVirtualFlagStr(String virtualFlagStr) {
		this.virtualFlagStr = virtualFlagStr;
	}

	public String getPropertiesStr() {
		return propertiesStr;
	}

	public void setPropertiesStr(String propertiesStr) {
		this.propertiesStr = propertiesStr;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getLaunchTypeStr() {
		return launchTypeStr;
	}

	public void setLaunchTypeStr(String launchTypeStr) {
		this.launchTypeStr = launchTypeStr;
	}

	public Short getRateType() {
		return rateType;
	}

	public void setRateType(Short rateType) {
		this.rateType = rateType;
	}

	public String getRateTypeStr() {
		return rateTypeStr;
	}

	public void setRateTypeStr(String rateTypeStr) {
		this.rateTypeStr = rateTypeStr;
	}

	public BigDecimal getRegionCost() {
		return regionCost;
	}

	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}

	public String getBalanceBaseName() {
		return balanceBaseName;
	}

	public void setBalanceBaseName(String balanceBaseName) {
		this.balanceBaseName = balanceBaseName;
	}

	public String getDtlId() {
		return dtlId;
	}

	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}

	public BigDecimal getSynthesizeReturnRate() {
		return synthesizeReturnRate;
	}

	public void setSynthesizeReturnRate(BigDecimal synthesizeReturnRate) {
		this.synthesizeReturnRate = synthesizeReturnRate;
	}

	public String getWildcardNo() {
		return wildcardNo;
	}

	public void setWildcardNo(String wildcardNo) {
		this.wildcardNo = wildcardNo;
	}

	public String getWildcardName() {
		return wildcardName;
	}

	public void setWildcardName(String wildcardName) {
		this.wildcardName = wildcardName;
	}

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

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getRateDiscount() {
		return rateDiscount;
	}

	public void setRateDiscount(BigDecimal rateDiscount) {
		this.rateDiscount = rateDiscount;
	}

	public BigDecimal getVirtualAmount() {
		return virtualAmount;
	}

	public void setVirtualAmount(BigDecimal virtualAmount) {
		this.virtualAmount = virtualAmount;
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

	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	public String getXb_shortName() {
		return xb_shortName;
	}

	public void setXb_shortName(String xb_shortName) {
		this.xb_shortName = xb_shortName;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSdGrossMarginRate() {
		return sdGrossMarginRate;
	}

	public void setSdGrossMarginRate(BigDecimal sdGrossMarginRate) {
		this.sdGrossMarginRate = sdGrossMarginRate;
	}

	public BigDecimal getSdTheMarginRate() {
		return sdTheMarginRate;
	}

	public void setSdTheMarginRate(BigDecimal sdTheMarginRate) {
		this.sdTheMarginRate = sdTheMarginRate;
	}

	public String getShopNoReplace() {
		return shopNoReplace;
	}

	public void setShopNoReplace(String shopNoReplace) {
		this.shopNoReplace = shopNoReplace;
	}

	public String getPurchaseSeason() {
		return purchaseSeason;
	}

	public void setPurchaseSeason(String purchaseSeason) {
		this.purchaseSeason = purchaseSeason;
	}

	public String getItemFlag() {
		return itemFlag;
	}

	public void setItemFlag(String itemFlag) {
		this.itemFlag = itemFlag;
	}

	
}