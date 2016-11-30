package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$2;
import cn.wonhigh.retail.fas.common.utils.BigDecimalSerializer$4;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-04-09 11:13:48
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
public class BillSalesSum extends FasBaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 片区编码
     */
    private String regionNo;

    /**
     * 片区名称
     */
    private String regionName;

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
    private String organNo;

    /**
     * 管理城市名称
     */
    private String organName;
    
    private String  organNo2;
    private String organName2;
    

    /**
     * 结算公司编码
     */
    private String companyNo;

    /**
     * 结算公司名称
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
     * 主营品类(门店必填, 1:男鞋 2:女鞋 3:童鞋 4:综合)
     */
    private String major;

    /**
     * 店铺细类 单品多品(门店必填,C:多品店 D:单品店)
     */
    private String multi;

    /**
     * 销售类别(1:零售 2:批发)
     */
    private String saleType;

    /**
     * 业务类型(1-调货、2-员购、3-团购、4-批发、5-门店、6-其他)
     */
    private String bizType;

    private String  bizTypeName;
    
    public String getBizTypeName() {
    	if(getBizType() == null) {
    		return bizTypeName;
    	}
		if("1".equals(getBizType())) {
			bizTypeName = "门店销售";
		} else if("2".equals(getBizType())) {
			bizTypeName = "地区批发";
		}else if("3".equals(getBizType())) {
			bizTypeName = "跨区调货";
		}else if("4".equals(getBizType())) {
			bizTypeName = "内购销售";
		}else if("5".equals(getBizType())) {
			bizTypeName = "其他出库";
		}
		return bizTypeName;
	}
    
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
    
    /**
     * 经营类型（男鞋，女鞋，综合）
     */
    private String categoryCode;

    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 品牌中文名称
     */
    private String brandName;

    /**
     * 品牌类别(D:代理品牌，S:自有品牌)
     */
    private String category;

    /**
     * 品牌归属(X:鞋，S:体)
     */
    private String belonger;
    
    /**
     * 品牌部编码
     */
    private String brandUnitNo;
    
    /**
     * 品牌部中文名称
     */
    private String brandUnitName;

    /**
     * 是否主营品牌(1是，0否)
     */
    private Byte brandFlag;

    /**
     * 年份
     */
    private String year;

    /**
     * 月份
     */
    private String month;

    /**
     * 销售月份
     */
    private String saleMonth;

    /**
     * 开票月份
     */
    private String billMonth;

    /**
     * 结算起始日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceStartDate;

    /**
     * 结算终止日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date balanceEndDate;

    /**
     * 开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billingDate;

    /**
     * 单据日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date billDate;

    /**
     * 商业集团编码
     */
    private String bsgroupsNo;

    /**
     * 商业集团名称
     */
    private String bsgroupsName;

    /**
     * 店铺编码
     */
    private String shopNo;
    
    private String shopNoReplace;

    /**
     * 店铺简称
     */
    private String shortName;

    /**
     * 店铺全称
     */
    private String fullName;

    /**
     * 店铺大类 批发零售(门店必填,1:零售；2:批发)
     */
    private String saleMode;

    /**
     * 店铺小类 (销售类型(门店必填, A0:商场店中店 A1:商场独立店 A2:商场特卖店 A3:商场寄卖店 BJ:独立街边店 BM:MALL B3:独立寄卖店, D0:批发加盟店 D1:批发团购店 D2:批发员购店 D3:批发调货店)
     */
    private String retailType;

    /**
     * 店铺类别
     */
    private String shopClassify;

    /**
     * 成立日期(店铺正式营业的日期)
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date openDate;

    /**
     * 店员配备数(门店必填,指标准的店员配备数量)
     */
    private Integer employeAmount;

    /**
     * 卖场面积
     */
    private BigDecimal area;

    /**
     * 仓库面积
     */
    private BigDecimal areaLeft;

    /**
     * 总面积
     */
    private BigDecimal areaTotal;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 门店级别( A、B、C、D、E)
     */
    private String shopLevel;

    /**
     * 上月结算期后销售量
     */
    private Integer lmaPeriodSalesnum;
    
    /**
     * 上月结算期后终端销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodRealamount;

    /**
     * 上月结算期后销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodSalesamount;

    /**
     * 上月结算期后牌价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodTagamount;

    /**
     * 上月结算期后销售成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal lmaPeriodSalescost;

    /**
     * 上月结算期后商场扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodSalesdeductions;
    
    /**
     * 上月结算期后合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodContractdeductions;
    
    /**
     * 上月结算期后折价加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodCutratedeductions;
    
    /**
     * 上月结算期后促销加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodPromotiondeductions;

    /**
     * 上月结算期后结算收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal lmaPeriodBalanceamount;

    /**
     * 本月结算期内销售数量
     */
 
    private Integer tmiPeriodSalesnum;
    
    /**
     * 本月结算期内终端销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodRealamount;

    /**
     * 本月结算期内销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodSalesamount;

    /**
     * 本月结算期内牌价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodTagamount;

    /**
     * 本月结算期内销售成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal tmiPeriodSalescost;

    /**
     * 本月结算期内商场扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodSalesdeductions;
    
    /**
     * 本月结算期内合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodContractdeductions;
    
    /**
     * 本月结算期内折价加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodCutratedeductions;
    
    /**
     * 本月结算期内促销加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodPromotiondeductions;

    /**
     * 本月结算期内结算收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmiPeriodBalanceamount;

    /**
     * 结算期内合计销售数量
     */

    private Integer biPeriodSalesnum;

    /**
     * 结算期内合计终端销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodRealamount;
    
    /**
     * 结算期内合计销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodSalesamount;

    /**
     * 结算期内合计牌价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodTagamount;

    /**
     * 结算期内合计销售成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal biPeriodSalescost;

    /**
     * 结算期内合计商场扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodSalesdeductions;
    
    /**
     * 结算期内合计合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodContractdeductions;
    
    /**
     * 结算期内合计折价加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodCutratedeductions;
    
    /**
     * 结算期内合计促销加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodPromotiondeductions;
    
    /**
     * 结算期内合计其他加扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodOtherdeductions;
    
    /**
     * 结算期内合计预估调整
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodPredictiondeductions;
    
    /**
     * 结算期内合计商场扣额（含调整）
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodMalldeductions;

    /**
     * 结算期内合计结算收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodBalanceamount;
    
    /**
     * 结算期内合计无税收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biPeriodNonTaxBalanceamount;

    /**
     * 开票数量
     */
    private Integer billQty;

    /**
     * 实际开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billAmount;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sendAmount;

    /**
     * 申请开票日期
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)
    private Date invoiceApplyDate;

    /**
     * 卖方名称
     */
    private String salerName;
    
    /**
     * 是否查询前期少估扣费
     */
    private Byte isSearchPrediction;

    /**
     * 客户编码(收票方)-买方
     */
    private String buyerNo;

    /**
     * 买方名称-开票单位
     */
    private String buyerName;

    /**
     * 本月结算期后销售量
     */
    private Integer tmaPeriodSalesnum;

    /**
     * 本月结算期后终端销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodRealamount;
    
    /**
     * 本月结算期后销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodSalesamount;

    /**
     * 本月结算期后合计牌价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodTagamount;

    /**
     * 本月结算期后销售成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal tmaPeriodSalescost;

    /**
     * 本月结算期后商场扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodSalesdeductions;
    
    /**
     * 本月结算期后合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodContractdeductions;
    
    /**
     * 本月结算期后折价加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodCutratedeductions;
    
    /**
     * 本月结算期后促销加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodPromotiondeductions;

    /**
     * 本月结算期后结算收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodBalanceamount;
    
    /**
     * 本月结算期后无税收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPeriodNonTaxBalanceamount;

    /**
     * 本月合计销售量
     */
   
    private Integer tmSalesnum;
    
    /**
     * 本月合计销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmRealamount;

    /**
     * 本月合计销售收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmSalesamount;

    /**
     * 本月合计牌价额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmTagamount;

    /**
     * 本月合计销售成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal tmSalescost;

    /**
     * 本月合计商场扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmSalesdeductions;
    
    /**
     * 本月合计合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmContractdeductions;
    
    /**
     * 本月合计折价加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmCutratedeductions;
    
    /**
     * 本月合计促销加扣扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmPromotiondeductions;
    
    /**
     * 本月合计其他加扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmOtherdeductions;
    
    /**
     * 本月合计预估调整
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmPredictiondeductions;

    /**
     * 本月合计结算收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmBalanceamount;

    /**
     * 调整前期扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sumChangebalanceamount;

    /**
     * 扣费合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal sumSalesdeductions;

    /**
     * 合同扣率
     */
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal contractRate;

    /**
     * 实际扣率
     */
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal actualRate;

    /**
     * 本月商场扣费合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmMallDeductions;

    /**
     * 本月商场正价折扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmSalesamountDiscount;

    /**
     * 本月商场促销折扣
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmSalesamountProdiscount;

    /**
     * 本月商场票前费用
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmBillbefDeducamount;

    /**
     * 本月结算差异
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDiffAmount;

    /**
     * 本月结算收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceSalesamount;

    /**
     * 本月无税收入
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal nonTaxSalesamount;

    /**
     * 本月含税成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal taxCost;

    /**
     * 本月无税成本
     */
    @JsonSerialize(using = BigDecimalSerializer$4.class)
    private BigDecimal noTaxCosts;

    /**
     * 地区成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal regionCost;
    
    /**
     * 总部成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal headquarterCost;
    
    /**
     * 采购成本
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal purchasePrice;

    /**
     * 本月累计未开票
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmNobillingSumamount;

    /**
     * 本月合计销售牌价
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tagPriceAmount;

    /**
     * 未开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal noBillingAmount;

    /**
     * 实物库存（正品库存量）
     */
    private Integer balanceQty;

    /**
     * 含税成本库存金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceQtyAmount;
    
    /**
     * 地区成本库存金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceQtyRegionAmount;
    
    /**
     * 总部成本库存金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceQtyHeadquarterAmount;

    /**
     * 销售渠道编码
     */
    private String channelNo;

    /**
     * 销售渠道名称
     */
    private String channelName;

    /**
     * 组织级别,1 管理城市,2 经营城市
     */
    private Integer organLevel;

    /**
     * 扣费金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal mallDeductAmount;

    /**
     * 开票金额
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal billingAmount;

    /**
     * 描述
     */
    private String strDesc;

    /**
     * 备注
     */
    private String strRemark;

    /**
     * 金额描述
     */
    private BigDecimal numDesc;

    /**
     * 金额备注
     */
    private BigDecimal numRemark;

    private BigDecimal saleAmount;
    private BigDecimal tagAmount;
    private BigDecimal saleCost;
    private BigDecimal deduAmount;
    private BigDecimal balanceAmount;
    
    private String statusName;
    private String brandCategory;
    private String brandAffiliation;
    private String levelName;
    
    private String  saleMode1;
    private String retailType1;
    private String multi1;
    private String levelName1;
    
    /**
     * 统计条数
     */
    private Integer count;
    
    /**
     * 票前费用扣款金额合计
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal balanceDeductAmount;
    
    /**
     * 系统扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal systemDeductAmount;
    
    /**
     * 合同正价扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal conpriceDeductAmount;

    /**
     * 促销扣费
     */
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal promDeductAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biMallNumberAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biSalesDiffamount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biExpenseOperateAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal biBillingAmount;
    
    @JsonSerialize(using = BigDecimalSerializer$2.class)
    private BigDecimal tmaPromDeductAmount;

    
    /*
     * *****************************华北专用start****************************************
     */
    
    /**
     * 店铺/客户名称
     */
    private String hb_shortName;
    
    /**
     * 店铺细类
     */
    private String hb_multi1;
    
    /*
     * *****************************华北专用end****************************************
     */
    
    /*
     * *****************************西北专用start****************************************
     */
    
    /**
     * 店铺/客户名称
     */
    private String xb_shortName;
    
    /*
     * *****************************西北专用end****************************************
     */
    
    /**
     * 
     * {@linkplain #regionNo}
     *
     * @return the value of bill_sales_sum.region_no
     */
    public String getRegionNo() {
        return regionNo;
    }

    /**
     * 
     * {@linkplain #regionNo}
     * @param regionNo the value for bill_sales_sum.region_no
     */
    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    /**
     * 
     * {@linkplain #regionName}
     *
     * @return the value of bill_sales_sum.region_name
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 
     * {@linkplain #regionName}
     * @param regionName the value for bill_sales_sum.region_name
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     *
     * @return the value of bill_sales_sum.province_no
     */
    public String getProvinceNo() {
        return provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceNo}
     * @param provinceNo the value for bill_sales_sum.province_no
     */
    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    /**
     * 
     * {@linkplain #provinceName}
     *
     * @return the value of bill_sales_sum.province_name
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 
     * {@linkplain #provinceName}
     * @param provinceName the value for bill_sales_sum.province_name
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 
     * {@linkplain #organNo}
     *
     * @return the value of bill_sales_sum.organ_no
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * 
     * {@linkplain #organNo}
     * @param organNo the value for bill_sales_sum.organ_no
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * 
     * {@linkplain #organName}
     *
     * @return the value of bill_sales_sum.organ_name
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 
     * {@linkplain #organName}
     * @param organName the value for bill_sales_sum.organ_name
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of bill_sales_sum.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for bill_sales_sum.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of bill_sales_sum.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for bill_sales_sum.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     *
     * @return the value of bill_sales_sum.zone_no
     */
    public String getZoneNo() {
        return zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneNo}
     * @param zoneNo the value for bill_sales_sum.zone_no
     */
    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    /**
     * 
     * {@linkplain #zoneName}
     *
     * @return the value of bill_sales_sum.zone_name
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * {@linkplain #zoneName}
     * @param zoneName the value for bill_sales_sum.zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 
     * {@linkplain #major}
     *
     * @return the value of bill_sales_sum.major
     */
    public String getMajor() {
        return major;
    }

    /**
     * 
     * {@linkplain #major}
     * @param major the value for bill_sales_sum.major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 
     * {@linkplain #multi}
     *
     * @return the value of bill_sales_sum.multi
     */
    public String getMulti() {
        return multi;
    }

    /**
     * 
     * {@linkplain #multi}
     * @param multi the value for bill_sales_sum.multi
     */
    public void setMulti(String multi) {
        this.multi = multi;
    }

    /**
     * 
     * {@linkplain #saleType}
     *
     * @return the value of bill_sales_sum.sale_type
     */
    public String getSaleType() {
        return saleType;
    }

    /**
     * 
     * {@linkplain #saleType}
     * @param saleType the value for bill_sales_sum.sale_type
     */
    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    /**
     * 
     * {@linkplain #bizType}
     *
     * @return the value of bill_sales_sum.biz_type
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 
     * {@linkplain #bizType}
     * @param bizType the value for bill_sales_sum.biz_type
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 
     * {@linkplain #categoryCode}
     *
     * @return the value of bill_sales_sum.category_code
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * 
     * {@linkplain #categoryCode}
     * @param categoryCode the value for bill_sales_sum.category_code
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * 
     * {@linkplain #brandNo}
     *
     * @return the value of bill_sales_sum.brand_no
     */
    public String getBrandNo() {
        return brandNo;
    }

    /**
     * 
     * {@linkplain #brandNo}
     * @param brandNo the value for bill_sales_sum.brand_no
     */
    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    /**
     * 
     * {@linkplain #brandName}
     *
     * @return the value of bill_sales_sum.brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 
     * {@linkplain #brandName}
     * @param brandName the value for bill_sales_sum.brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 
     * {@linkplain #category}
     *
     * @return the value of bill_sales_sum.category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * {@linkplain #category}
     * @param category the value for bill_sales_sum.category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * {@linkplain #belonger}
     *
     * @return the value of bill_sales_sum.belonger
     */
    public String getBelonger() {
        return belonger;
    }

    /**
     * 
     * {@linkplain #belonger}
     * @param belonger the value for bill_sales_sum.belonger
     */
    public void setBelonger(String belonger) {
        this.belonger = belonger;
    }

    /**
     * 
     * {@linkplain #brandFlag}
     *
     * @return the value of bill_sales_sum.brand_flag
     */
    public Byte getBrandFlag() {
        return brandFlag;
    }

    /**
     * 
     * {@linkplain #brandFlag}
     * @param brandFlag the value for bill_sales_sum.brand_flag
     */
    public void setBrandFlag(Byte brandFlag) {
        this.brandFlag = brandFlag;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of bill_sales_sum.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for bill_sales_sum.year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * {@linkplain #month}
     *
     * @return the value of bill_sales_sum.month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * {@linkplain #month}
     * @param month the value for bill_sales_sum.month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * {@linkplain #saleMonth}
     *
     * @return the value of bill_sales_sum.sale_month
     */
    public String getSaleMonth() {
        return saleMonth;
    }

    /**
     * 
     * {@linkplain #saleMonth}
     * @param saleMonth the value for bill_sales_sum.sale_month
     */
    public void setSaleMonth(String saleMonth) {
        this.saleMonth = saleMonth;
    }

    /**
     * 
     * {@linkplain #billMonth}
     *
     * @return the value of bill_sales_sum.bill_month
     */
    public String getBillMonth() {
        return billMonth;
    }

    /**
     * 
     * {@linkplain #billMonth}
     * @param billMonth the value for bill_sales_sum.bill_month
     */
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     *
     * @return the value of bill_sales_sum.balance_start_date
     */
    public Date getBalanceStartDate() {
        return balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceStartDate}
     * @param balanceStartDate the value for bill_sales_sum.balance_start_date
     */
    public void setBalanceStartDate(Date balanceStartDate) {
        this.balanceStartDate = balanceStartDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     *
     * @return the value of bill_sales_sum.balance_end_date
     */
    public Date getBalanceEndDate() {
        return balanceEndDate;
    }

    /**
     * 
     * {@linkplain #balanceEndDate}
     * @param balanceEndDate the value for bill_sales_sum.balance_end_date
     */
    public void setBalanceEndDate(Date balanceEndDate) {
        this.balanceEndDate = balanceEndDate;
    }

    /**
     * 
     * {@linkplain #billingDate}
     *
     * @return the value of bill_sales_sum.billing_date
     */
    public Date getBillingDate() {
        return billingDate;
    }

    /**
     * 
     * {@linkplain #billingDate}
     * @param billingDate the value for bill_sales_sum.billing_date
     */
    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     *
     * @return the value of bill_sales_sum.bill_date
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * 
     * {@linkplain #billDate}
     * @param billDate the value for bill_sales_sum.bill_date
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     *
     * @return the value of bill_sales_sum.bsgroups_no
     */
    public String getBsgroupsNo() {
        return bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsNo}
     * @param bsgroupsNo the value for bill_sales_sum.bsgroups_no
     */
    public void setBsgroupsNo(String bsgroupsNo) {
        this.bsgroupsNo = bsgroupsNo;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     *
     * @return the value of bill_sales_sum.bsgroups_name
     */
    public String getBsgroupsName() {
        return bsgroupsName;
    }

    /**
     * 
     * {@linkplain #bsgroupsName}
     * @param bsgroupsName the value for bill_sales_sum.bsgroups_name
     */
    public void setBsgroupsName(String bsgroupsName) {
        this.bsgroupsName = bsgroupsName;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of bill_sales_sum.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for bill_sales_sum.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shortName}
     *
     * @return the value of bill_sales_sum.short_name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * {@linkplain #shortName}
     * @param shortName the value for bill_sales_sum.short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * {@linkplain #fullName}
     *
     * @return the value of bill_sales_sum.full_name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * {@linkplain #fullName}
     * @param fullName the value for bill_sales_sum.full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * {@linkplain #saleMode}
     *
     * @return the value of bill_sales_sum.sale_mode
     */
    public String getSaleMode() {
        return saleMode;
    }

    /**
     * 
     * {@linkplain #saleMode}
     * @param saleMode the value for bill_sales_sum.sale_mode
     */
    public void setSaleMode(String saleMode) {
        this.saleMode = saleMode;
    }

    /**
     * 
     * {@linkplain #retailType}
     *
     * @return the value of bill_sales_sum.retail_type
     */
    public String getRetailType() {
        return retailType;
    }

    /**
     * 
     * {@linkplain #retailType}
     * @param retailType the value for bill_sales_sum.retail_type
     */
    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    /**
     * 
     * {@linkplain #shopClassify}
     *
     * @return the value of bill_sales_sum.shop_classify
     */
    public String getShopClassify() {
        return shopClassify;
    }

    /**
     * 
     * {@linkplain #shopClassify}
     * @param shopClassify the value for bill_sales_sum.shop_classify
     */
    public void setShopClassify(String shopClassify) {
        this.shopClassify = shopClassify;
    }

    /**
     * 
     * {@linkplain #openDate}
     *
     * @return the value of bill_sales_sum.open_date
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * 
     * {@linkplain #openDate}
     * @param openDate the value for bill_sales_sum.open_date
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * 
     * {@linkplain #employeAmount}
     *
     * @return the value of bill_sales_sum.employe_amount
     */
    public Integer getEmployeAmount() {
        return employeAmount;
    }

    /**
     * 
     * {@linkplain #employeAmount}
     * @param employeAmount the value for bill_sales_sum.employe_amount
     */
    public void setEmployeAmount(Integer employeAmount) {
        this.employeAmount = employeAmount;
    }

    /**
     * 
     * {@linkplain #area}
     *
     * @return the value of bill_sales_sum.area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * 
     * {@linkplain #area}
     * @param area the value for bill_sales_sum.area
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * 
     * {@linkplain #areaLeft}
     *
     * @return the value of bill_sales_sum.area_left
     */
    public BigDecimal getAreaLeft() {
        return areaLeft;
    }

    /**
     * 
     * {@linkplain #areaLeft}
     * @param areaLeft the value for bill_sales_sum.area_left
     */
    public void setAreaLeft(BigDecimal areaLeft) {
        this.areaLeft = areaLeft;
    }

    /**
     * 
     * {@linkplain #areaTotal}
     *
     * @return the value of bill_sales_sum.area_total
     */
    public BigDecimal getAreaTotal() {
        return areaTotal;
    }

    /**
     * 
     * {@linkplain #areaTotal}
     * @param areaTotal the value for bill_sales_sum.area_total
     */
    public void setAreaTotal(BigDecimal areaTotal) {
        this.areaTotal = areaTotal;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     *
     * @return the value of bill_sales_sum.category_no
     */
    public String getCategoryNo() {
        return categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryNo}
     * @param categoryNo the value for bill_sales_sum.category_no
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    /**
     * 
     * {@linkplain #categoryName}
     *
     * @return the value of bill_sales_sum.category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * {@linkplain #categoryName}
     * @param categoryName the value for bill_sales_sum.category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * {@linkplain #shopLevel}
     *
     * @return the value of bill_sales_sum.shop_level
     */
    public String getShopLevel() {
        return shopLevel;
    }

    /**
     * 
     * {@linkplain #shopLevel}
     * @param shopLevel the value for bill_sales_sum.shop_level
     */
    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalesnum}
     *
     * @return the value of bill_sales_sum.lma_period_salesnum
     */
    public Integer getLmaPeriodSalesnum() {
        return lmaPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalesnum}
     * @param lmaPeriodSalesnum the value for bill_sales_sum.lma_period_salesnum
     */
    public void setLmaPeriodSalesnum(Integer lmaPeriodSalesnum) {
        this.lmaPeriodSalesnum = lmaPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalesamount}
     *
     * @return the value of bill_sales_sum.lma_period_salesamount
     */
    public BigDecimal getLmaPeriodSalesamount() {
        return lmaPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalesamount}
     * @param lmaPeriodSalesamount the value for bill_sales_sum.lma_period_salesamount
     */
    public void setLmaPeriodSalesamount(BigDecimal lmaPeriodSalesamount) {
        this.lmaPeriodSalesamount = lmaPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #lmaPeriodTagamount}
     *
     * @return the value of bill_sales_sum.lma_period_tagamount
     */
    public BigDecimal getLmaPeriodTagamount() {
        return lmaPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #lmaPeriodTagamount}
     * @param lmaPeriodTagamount the value for bill_sales_sum.lma_period_tagamount
     */
    public void setLmaPeriodTagamount(BigDecimal lmaPeriodTagamount) {
        this.lmaPeriodTagamount = lmaPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalescost}
     *
     * @return the value of bill_sales_sum.lma_period_salescost
     */
    public BigDecimal getLmaPeriodSalescost() {
        return lmaPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalescost}
     * @param lmaPeriodSalescost the value for bill_sales_sum.lma_period_salescost
     */
    public void setLmaPeriodSalescost(BigDecimal lmaPeriodSalescost) {
        this.lmaPeriodSalescost = lmaPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalesdeductions}
     *
     * @return the value of bill_sales_sum.lma_period_salesdeductions
     */
    public BigDecimal getLmaPeriodSalesdeductions() {
        return lmaPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #lmaPeriodSalesdeductions}
     * @param lmaPeriodSalesdeductions the value for bill_sales_sum.lma_period_salesdeductions
     */
    public void setLmaPeriodSalesdeductions(BigDecimal lmaPeriodSalesdeductions) {
        this.lmaPeriodSalesdeductions = lmaPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #lmaPeriodBalanceamount}
     *
     * @return the value of bill_sales_sum.lma_period_balanceamount
     */
    public BigDecimal getLmaPeriodBalanceamount() {
        return lmaPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #lmaPeriodBalanceamount}
     * @param lmaPeriodBalanceamount the value for bill_sales_sum.lma_period_balanceamount
     */
    public void setLmaPeriodBalanceamount(BigDecimal lmaPeriodBalanceamount) {
        this.lmaPeriodBalanceamount = lmaPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalesnum}
     *
     * @return the value of bill_sales_sum.tmi_period_salesnum
     */
    public Integer getTmiPeriodSalesnum() {
        return tmiPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalesnum}
     * @param tmiPeriodSalesnum the value for bill_sales_sum.tmi_period_salesnum
     */
    public void setTmiPeriodSalesnum(Integer tmiPeriodSalesnum) {
        this.tmiPeriodSalesnum = tmiPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalesamount}
     *
     * @return the value of bill_sales_sum.tmi_period_salesamount
     */
    public BigDecimal getTmiPeriodSalesamount() {
        return tmiPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalesamount}
     * @param tmiPeriodSalesamount the value for bill_sales_sum.tmi_period_salesamount
     */
    public void setTmiPeriodSalesamount(BigDecimal tmiPeriodSalesamount) {
        this.tmiPeriodSalesamount = tmiPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #tmiPeriodTagamount}
     *
     * @return the value of bill_sales_sum.tmi_period_tagamount
     */
    public BigDecimal getTmiPeriodTagamount() {
        return tmiPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #tmiPeriodTagamount}
     * @param tmiPeriodTagamount the value for bill_sales_sum.tmi_period_tagamount
     */
    public void setTmiPeriodTagamount(BigDecimal tmiPeriodTagamount) {
        this.tmiPeriodTagamount = tmiPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalescost}
     *
     * @return the value of bill_sales_sum.tmi_period_salescost
     */
    public BigDecimal getTmiPeriodSalescost() {
        return tmiPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalescost}
     * @param tmiPeriodSalescost the value for bill_sales_sum.tmi_period_salescost
     */
    public void setTmiPeriodSalescost(BigDecimal tmiPeriodSalescost) {
        this.tmiPeriodSalescost = tmiPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalesdeductions}
     *
     * @return the value of bill_sales_sum.tmi_period_salesdeductions
     */
    public BigDecimal getTmiPeriodSalesdeductions() {
        return tmiPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #tmiPeriodSalesdeductions}
     * @param tmiPeriodSalesdeductions the value for bill_sales_sum.tmi_period_salesdeductions
     */
    public void setTmiPeriodSalesdeductions(BigDecimal tmiPeriodSalesdeductions) {
        this.tmiPeriodSalesdeductions = tmiPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #tmiPeriodBalanceamount}
     *
     * @return the value of bill_sales_sum.tmi_period_balanceamount
     */
    public BigDecimal getTmiPeriodBalanceamount() {
        return tmiPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #tmiPeriodBalanceamount}
     * @param tmiPeriodBalanceamount the value for bill_sales_sum.tmi_period_balanceamount
     */
    public void setTmiPeriodBalanceamount(BigDecimal tmiPeriodBalanceamount) {
        this.tmiPeriodBalanceamount = tmiPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #biPeriodSalesnum}
     *
     * @return the value of bill_sales_sum.bi_period_salesnum
     */
    public Integer getBiPeriodSalesnum() {
        return biPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #biPeriodSalesnum}
     * @param biPeriodSalesnum the value for bill_sales_sum.bi_period_salesnum
     */
    public void setBiPeriodSalesnum(Integer biPeriodSalesnum) {
        this.biPeriodSalesnum = biPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #biPeriodSalesamount}
     *
     * @return the value of bill_sales_sum.bi_period_salesamount
     */
    public BigDecimal getBiPeriodSalesamount() {
        return biPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #biPeriodSalesamount}
     * @param biPeriodSalesamount the value for bill_sales_sum.bi_period_salesamount
     */
    public void setBiPeriodSalesamount(BigDecimal biPeriodSalesamount) {
        this.biPeriodSalesamount = biPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #biPeriodTagamount}
     *
     * @return the value of bill_sales_sum.bi_period_tagamount
     */
    public BigDecimal getBiPeriodTagamount() {
        return biPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #biPeriodTagamount}
     * @param biPeriodTagamount the value for bill_sales_sum.bi_period_tagamount
     */
    public void setBiPeriodTagamount(BigDecimal biPeriodTagamount) {
        this.biPeriodTagamount = biPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #biPeriodSalescost}
     *
     * @return the value of bill_sales_sum.bi_period_salescost
     */
    public BigDecimal getBiPeriodSalescost() {
        return biPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #biPeriodSalescost}
     * @param biPeriodSalescost the value for bill_sales_sum.bi_period_salescost
     */
    public void setBiPeriodSalescost(BigDecimal biPeriodSalescost) {
        this.biPeriodSalescost = biPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #biPeriodSalesdeductions}
     *
     * @return the value of bill_sales_sum.bi_period_salesdeductions
     */
    public BigDecimal getBiPeriodSalesdeductions() {
        return biPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #biPeriodSalesdeductions}
     * @param biPeriodSalesdeductions the value for bill_sales_sum.bi_period_salesdeductions
     */
    public void setBiPeriodSalesdeductions(BigDecimal biPeriodSalesdeductions) {
        this.biPeriodSalesdeductions = biPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #biPeriodBalanceamount}
     *
     * @return the value of bill_sales_sum.bi_period_balanceamount
     */
    public BigDecimal getBiPeriodBalanceamount() {
        return biPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #biPeriodBalanceamount}
     * @param biPeriodBalanceamount the value for bill_sales_sum.bi_period_balanceamount
     */
    public void setBiPeriodBalanceamount(BigDecimal biPeriodBalanceamount) {
        this.biPeriodBalanceamount = biPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #billQty}
     *
     * @return the value of bill_sales_sum.bill_qty
     */
    public Integer getBillQty() {
        return billQty;
    }

    /**
     * 
     * {@linkplain #billQty}
     * @param billQty the value for bill_sales_sum.bill_qty
     */
    public void setBillQty(Integer billQty) {
        this.billQty = billQty;
    }

    /**
     * 
     * {@linkplain #billAmount}
     *
     * @return the value of bill_sales_sum.bill_amount
     */
    public BigDecimal getBillAmount() {
        return billAmount;
    }

    /**
     * 
     * {@linkplain #billAmount}
     * @param billAmount the value for bill_sales_sum.bill_amount
     */
    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of bill_sales_sum.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for bill_sales_sum.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #sendAmount}
     *
     * @return the value of bill_sales_sum.send_amount
     */
    public BigDecimal getSendAmount() {
        return sendAmount;
    }

    /**
     * 
     * {@linkplain #sendAmount}
     * @param sendAmount the value for bill_sales_sum.send_amount
     */
    public void setSendAmount(BigDecimal sendAmount) {
        this.sendAmount = sendAmount;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     *
     * @return the value of bill_sales_sum.invoice_apply_date
     */
    public Date getInvoiceApplyDate() {
        return invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #invoiceApplyDate}
     * @param invoiceApplyDate the value for bill_sales_sum.invoice_apply_date
     */
    public void setInvoiceApplyDate(Date invoiceApplyDate) {
        this.invoiceApplyDate = invoiceApplyDate;
    }

    /**
     * 
     * {@linkplain #salerName}
     *
     * @return the value of bill_sales_sum.saler_name
     */
    public String getSalerName() {
        return salerName;
    }

    /**
     * 
     * {@linkplain #salerName}
     * @param salerName the value for bill_sales_sum.saler_name
     */
    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     *
     * @return the value of bill_sales_sum.buyer_no
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerNo}
     * @param buyerNo the value for bill_sales_sum.buyer_no
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 
     * {@linkplain #buyerName}
     *
     * @return the value of bill_sales_sum.buyer_name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * 
     * {@linkplain #buyerName}
     * @param buyerName the value for bill_sales_sum.buyer_name
     */
    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalesnum}
     *
     * @return the value of bill_sales_sum.tma_period_salesnum
     */
    public Integer getTmaPeriodSalesnum() {
        return tmaPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalesnum}
     * @param tmaPeriodSalesnum the value for bill_sales_sum.tma_period_salesnum
     */
    public void setTmaPeriodSalesnum(Integer tmaPeriodSalesnum) {
        this.tmaPeriodSalesnum = tmaPeriodSalesnum;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalesamount}
     *
     * @return the value of bill_sales_sum.tma_period_salesamount
     */
    public BigDecimal getTmaPeriodSalesamount() {
        return tmaPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalesamount}
     * @param tmaPeriodSalesamount the value for bill_sales_sum.tma_period_salesamount
     */
    public void setTmaPeriodSalesamount(BigDecimal tmaPeriodSalesamount) {
        this.tmaPeriodSalesamount = tmaPeriodSalesamount;
    }

    /**
     * 
     * {@linkplain #tmaPeriodTagamount}
     *
     * @return the value of bill_sales_sum.tma_period_tagamount
     */
    public BigDecimal getTmaPeriodTagamount() {
        return tmaPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #tmaPeriodTagamount}
     * @param tmaPeriodTagamount the value for bill_sales_sum.tma_period_tagamount
     */
    public void setTmaPeriodTagamount(BigDecimal tmaPeriodTagamount) {
        this.tmaPeriodTagamount = tmaPeriodTagamount;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalescost}
     *
     * @return the value of bill_sales_sum.tma_period_salescost
     */
    public BigDecimal getTmaPeriodSalescost() {
        return tmaPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalescost}
     * @param tmaPeriodSalescost the value for bill_sales_sum.tma_period_salescost
     */
    public void setTmaPeriodSalescost(BigDecimal tmaPeriodSalescost) {
        this.tmaPeriodSalescost = tmaPeriodSalescost;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalesdeductions}
     *
     * @return the value of bill_sales_sum.tma_period_salesdeductions
     */
    public BigDecimal getTmaPeriodSalesdeductions() {
        return tmaPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #tmaPeriodSalesdeductions}
     * @param tmaPeriodSalesdeductions the value for bill_sales_sum.tma_period_salesdeductions
     */
    public void setTmaPeriodSalesdeductions(BigDecimal tmaPeriodSalesdeductions) {
        this.tmaPeriodSalesdeductions = tmaPeriodSalesdeductions;
    }

    /**
     * 
     * {@linkplain #tmaPeriodBalanceamount}
     *
     * @return the value of bill_sales_sum.tma_period_balanceamount
     */
    public BigDecimal getTmaPeriodBalanceamount() {
        return tmaPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #tmaPeriodBalanceamount}
     * @param tmaPeriodBalanceamount the value for bill_sales_sum.tma_period_balanceamount
     */
    public void setTmaPeriodBalanceamount(BigDecimal tmaPeriodBalanceamount) {
        this.tmaPeriodBalanceamount = tmaPeriodBalanceamount;
    }

    /**
     * 
     * {@linkplain #tmSalesnum}
     *
     * @return the value of bill_sales_sum.tm_salesnum
     */
    public Integer getTmSalesnum() {
        return tmSalesnum;
    }

    /**
     * 
     * {@linkplain #tmSalesnum}
     * @param tmSalesnum the value for bill_sales_sum.tm_salesnum
     */
    public void setTmSalesnum(Integer tmSalesnum) {
        this.tmSalesnum = tmSalesnum;
    }

    /**
     * 
     * {@linkplain #tmSalesamount}
     *
     * @return the value of bill_sales_sum.tm_salesamount
     */
    public BigDecimal getTmSalesamount() {
        return tmSalesamount;
    }

    /**
     * 
     * {@linkplain #tmSalesamount}
     * @param tmSalesamount the value for bill_sales_sum.tm_salesamount
     */
    public void setTmSalesamount(BigDecimal tmSalesamount) {
        this.tmSalesamount = tmSalesamount;
    }

    /**
     * 
     * {@linkplain #tmTagamount}
     *
     * @return the value of bill_sales_sum.tm_tagamount
     */
    public BigDecimal getTmTagamount() {
        return tmTagamount;
    }

    /**
     * 
     * {@linkplain #tmTagamount}
     * @param tmTagamount the value for bill_sales_sum.tm_tagamount
     */
    public void setTmTagamount(BigDecimal tmTagamount) {
        this.tmTagamount = tmTagamount;
    }

    /**
     * 
     * {@linkplain #tmSalescost}
     *
     * @return the value of bill_sales_sum.tm_salescost
     */
    public BigDecimal getTmSalescost() {
        return tmSalescost;
    }

    /**
     * 
     * {@linkplain #tmSalescost}
     * @param tmSalescost the value for bill_sales_sum.tm_salescost
     */
    public void setTmSalescost(BigDecimal tmSalescost) {
        this.tmSalescost = tmSalescost;
    }

    /**
     * 
     * {@linkplain #tmSalesdeductions}
     *
     * @return the value of bill_sales_sum.tm_salesdeductions
     */
    public BigDecimal getTmSalesdeductions() {
        return tmSalesdeductions;
    }

    /**
     * 
     * {@linkplain #tmSalesdeductions}
     * @param tmSalesdeductions the value for bill_sales_sum.tm_salesdeductions
     */
    public void setTmSalesdeductions(BigDecimal tmSalesdeductions) {
        this.tmSalesdeductions = tmSalesdeductions;
    }

    /**
     * 
     * {@linkplain #tmBalanceamount}
     *
     * @return the value of bill_sales_sum.tm_balanceamount
     */
    public BigDecimal getTmBalanceamount() {
        return tmBalanceamount;
    }

    /**
     * 
     * {@linkplain #tmBalanceamount}
     * @param tmBalanceamount the value for bill_sales_sum.tm_balanceamount
     */
    public void setTmBalanceamount(BigDecimal tmBalanceamount) {
        this.tmBalanceamount = tmBalanceamount;
    }

    /**
     * 
     * {@linkplain #sumChangebalanceamount}
     *
     * @return the value of bill_sales_sum.sum_changebalanceamount
     */
    public BigDecimal getSumChangebalanceamount() {
        return sumChangebalanceamount;
    }

    /**
     * 
     * {@linkplain #sumChangebalanceamount}
     * @param sumChangebalanceamount the value for bill_sales_sum.sum_changebalanceamount
     */
    public void setSumChangebalanceamount(BigDecimal sumChangebalanceamount) {
        this.sumChangebalanceamount = sumChangebalanceamount;
    }

    /**
     * 
     * {@linkplain #sumSalesdeductions}
     *
     * @return the value of bill_sales_sum.sum_salesdeductions
     */
    public BigDecimal getSumSalesdeductions() {
        return sumSalesdeductions;
    }

    /**
     * 
     * {@linkplain #sumSalesdeductions}
     * @param sumSalesdeductions the value for bill_sales_sum.sum_salesdeductions
     */
    public void setSumSalesdeductions(BigDecimal sumSalesdeductions) {
        this.sumSalesdeductions = sumSalesdeductions;
    }

    /**
     * 
     * {@linkplain #contractRate}
     *
     * @return the value of bill_sales_sum.contract_rate
     */
    public BigDecimal getContractRate() {
        return contractRate;
    }

    /**
     * 
     * {@linkplain #contractRate}
     * @param contractRate the value for bill_sales_sum.contract_rate
     */
    public void setContractRate(BigDecimal contractRate) {
        this.contractRate = contractRate;
    }

    /**
     * 
     * {@linkplain #actualRate}
     *
     * @return the value of bill_sales_sum.actual_rate
     */
    public BigDecimal getActualRate() {
        return actualRate;
    }

    /**
     * 
     * {@linkplain #actualRate}
     * @param actualRate the value for bill_sales_sum.actual_rate
     */
    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    /**
     * 
     * {@linkplain #tmMallDeductions}
     *
     * @return the value of bill_sales_sum.tm_mall_deductions
     */
    public BigDecimal getTmMallDeductions() {
        return tmMallDeductions;
    }

    /**
     * 
     * {@linkplain #tmMallDeductions}
     * @param tmMallDeductions the value for bill_sales_sum.tm_mall_deductions
     */
    public void setTmMallDeductions(BigDecimal tmMallDeductions) {
        this.tmMallDeductions = tmMallDeductions;
    }

    /**
     * 
     * {@linkplain #tmSalesamountDiscount}
     *
     * @return the value of bill_sales_sum.tm_salesamount_discount
     */
    public BigDecimal getTmSalesamountDiscount() {
        return tmSalesamountDiscount;
    }

    /**
     * 
     * {@linkplain #tmSalesamountDiscount}
     * @param tmSalesamountDiscount the value for bill_sales_sum.tm_salesamount_discount
     */
    public void setTmSalesamountDiscount(BigDecimal tmSalesamountDiscount) {
        this.tmSalesamountDiscount = tmSalesamountDiscount;
    }

    /**
     * 
     * {@linkplain #tmSalesamountProdiscount}
     *
     * @return the value of bill_sales_sum.tm_salesamount_prodiscount
     */
    public BigDecimal getTmSalesamountProdiscount() {
        return tmSalesamountProdiscount;
    }

    /**
     * 
     * {@linkplain #tmSalesamountProdiscount}
     * @param tmSalesamountProdiscount the value for bill_sales_sum.tm_salesamount_prodiscount
     */
    public void setTmSalesamountProdiscount(BigDecimal tmSalesamountProdiscount) {
        this.tmSalesamountProdiscount = tmSalesamountProdiscount;
    }

    /**
     * 
     * {@linkplain #tmBillbefDeducamount}
     *
     * @return the value of bill_sales_sum.tm_billbef_deducamount
     */
    public BigDecimal getTmBillbefDeducamount() {
        return tmBillbefDeducamount;
    }

    /**
     * 
     * {@linkplain #tmBillbefDeducamount}
     * @param tmBillbefDeducamount the value for bill_sales_sum.tm_billbef_deducamount
     */
    public void setTmBillbefDeducamount(BigDecimal tmBillbefDeducamount) {
        this.tmBillbefDeducamount = tmBillbefDeducamount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     *
     * @return the value of bill_sales_sum.balance_diff_amount
     */
    public BigDecimal getBalanceDiffAmount() {
        return balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #balanceDiffAmount}
     * @param balanceDiffAmount the value for bill_sales_sum.balance_diff_amount
     */
    public void setBalanceDiffAmount(BigDecimal balanceDiffAmount) {
        this.balanceDiffAmount = balanceDiffAmount;
    }

    /**
     * 
     * {@linkplain #balanceSalesamount}
     *
     * @return the value of bill_sales_sum.balance_salesamount
     */
    public BigDecimal getBalanceSalesamount() {
        return balanceSalesamount;
    }

    /**
     * 
     * {@linkplain #balanceSalesamount}
     * @param balanceSalesamount the value for bill_sales_sum.balance_salesamount
     */
    public void setBalanceSalesamount(BigDecimal balanceSalesamount) {
        this.balanceSalesamount = balanceSalesamount;
    }

    /**
     * 
     * {@linkplain #nonTaxSalesamount}
     *
     * @return the value of bill_sales_sum.non_tax_salesamount
     */
    public BigDecimal getNonTaxSalesamount() {
        return nonTaxSalesamount;
    }

    /**
     * 
     * {@linkplain #nonTaxSalesamount}
     * @param nonTaxSalesamount the value for bill_sales_sum.non_tax_salesamount
     */
    public void setNonTaxSalesamount(BigDecimal nonTaxSalesamount) {
        this.nonTaxSalesamount = nonTaxSalesamount;
    }

    /**
     * 
     * {@linkplain #taxCost}
     *
     * @return the value of bill_sales_sum.tax_cost
     */
    public BigDecimal getTaxCost() {
        return taxCost;
    }

    /**
     * 
     * {@linkplain #taxCost}
     * @param taxCost the value for bill_sales_sum.tax_cost
     */
    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    /**
     * 
     * {@linkplain #noTaxCosts}
     *
     * @return the value of bill_sales_sum.no_tax_costs
     */
    public BigDecimal getNoTaxCosts() {
        return noTaxCosts;
    }

    /**
     * 
     * {@linkplain #noTaxCosts}
     * @param noTaxCosts the value for bill_sales_sum.no_tax_costs
     */
    public void setNoTaxCosts(BigDecimal noTaxCosts) {
        this.noTaxCosts = noTaxCosts;
    }

    /**
     * 
     * {@linkplain #headquarterCost}
     *
     * @return the value of bill_sales_sum.headquarter_cost
     */
    public BigDecimal getHeadquarterCost() {
        return headquarterCost;
    }

    /**
     * 
     * {@linkplain #headquarterCost}
     * @param headquarterCost the value for bill_sales_sum.headquarter_cost
     */
    public void setHeadquarterCost(BigDecimal headquarterCost) {
        this.headquarterCost = headquarterCost;
    }

    /**
     * 
     * {@linkplain #tmNobillingSumamount}
     *
     * @return the value of bill_sales_sum.tm_nobilling_sumamount
     */
    public BigDecimal getTmNobillingSumamount() {
        return tmNobillingSumamount;
    }

    /**
     * 
     * {@linkplain #tmNobillingSumamount}
     * @param tmNobillingSumamount the value for bill_sales_sum.tm_nobilling_sumamount
     */
    public void setTmNobillingSumamount(BigDecimal tmNobillingSumamount) {
        this.tmNobillingSumamount = tmNobillingSumamount;
    }

    /**
     * 
     * {@linkplain #tagPriceAmount}
     *
     * @return the value of bill_sales_sum.tag_price_amount
     */
    public BigDecimal getTagPriceAmount() {
        return tagPriceAmount;
    }

    /**
     * 
     * {@linkplain #tagPriceAmount}
     * @param tagPriceAmount the value for bill_sales_sum.tag_price_amount
     */
    public void setTagPriceAmount(BigDecimal tagPriceAmount) {
        this.tagPriceAmount = tagPriceAmount;
    }

    /**
     * 
     * {@linkplain #noBillingAmount}
     *
     * @return the value of bill_sales_sum.no_billing_amount
     */
    public BigDecimal getNoBillingAmount() {
        return noBillingAmount;
    }

    /**
     * 
     * {@linkplain #noBillingAmount}
     * @param noBillingAmount the value for bill_sales_sum.no_billing_amount
     */
    public void setNoBillingAmount(BigDecimal noBillingAmount) {
        this.noBillingAmount = noBillingAmount;
    }

    /**
     * 
     * {@linkplain #balanceQty}
     *
     * @return the value of bill_sales_sum.balance_qty
     */
    public Integer getBalanceQty() {
        return balanceQty;
    }

    /**
     * 
     * {@linkplain #balanceQty}
     * @param balanceQty the value for bill_sales_sum.balance_qty
     */
    public void setBalanceQty(Integer balanceQty) {
        this.balanceQty = balanceQty;
    }

    /**
     * 
     * {@linkplain #balanceQtyAmount}
     *
     * @return the value of bill_sales_sum.balance_qty_amount
     */
    public BigDecimal getBalanceQtyAmount() {
        return balanceQtyAmount;
    }

    /**
     * 
     * {@linkplain #balanceQtyAmount}
     * @param balanceQtyAmount the value for bill_sales_sum.balance_qty_amount
     */
    public void setBalanceQtyAmount(BigDecimal balanceQtyAmount) {
        this.balanceQtyAmount = balanceQtyAmount;
    }

    /**
     * 
     * {@linkplain #channelNo}
     *
     * @return the value of bill_sales_sum.channel_no
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * 
     * {@linkplain #channelNo}
     * @param channelNo the value for bill_sales_sum.channel_no
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    /**
     * 
     * {@linkplain #channelName}
     *
     * @return the value of bill_sales_sum.channel_name
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 
     * {@linkplain #channelName}
     * @param channelName the value for bill_sales_sum.channel_name
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 
     * {@linkplain #organLevel}
     *
     * @return the value of bill_sales_sum.organ_level
     */
    public Integer getOrganLevel() {
        return organLevel;
    }

    /**
     * 
     * {@linkplain #organLevel}
     * @param organLevel the value for bill_sales_sum.organ_level
     */
    public void setOrganLevel(Integer organLevel) {
        this.organLevel = organLevel;
    }

    /**
     * 
     * {@linkplain #mallDeductAmount}
     *
     * @return the value of bill_sales_sum.mall_deduct_amount
     */
    public BigDecimal getMallDeductAmount() {
        return mallDeductAmount;
    }

    /**
     * 
     * {@linkplain #mallDeductAmount}
     * @param mallDeductAmount the value for bill_sales_sum.mall_deduct_amount
     */
    public void setMallDeductAmount(BigDecimal mallDeductAmount) {
        this.mallDeductAmount = mallDeductAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     *
     * @return the value of bill_sales_sum.billing_amount
     */
    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    /**
     * 
     * {@linkplain #billingAmount}
     * @param billingAmount the value for bill_sales_sum.billing_amount
     */
    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }

    /**
     * 
     * {@linkplain #strDesc}
     *
     * @return the value of bill_sales_sum.str_desc
     */
    public String getStrDesc() {
        return strDesc;
    }

    /**
     * 
     * {@linkplain #strDesc}
     * @param strDesc the value for bill_sales_sum.str_desc
     */
    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    /**
     * 
     * {@linkplain #strRemark}
     *
     * @return the value of bill_sales_sum.str_remark
     */
    public String getStrRemark() {
        return strRemark;
    }

    /**
     * 
     * {@linkplain #strRemark}
     * @param strRemark the value for bill_sales_sum.str_remark
     */
    public void setStrRemark(String strRemark) {
        this.strRemark = strRemark;
    }

    /**
     * 
     * {@linkplain #numDesc}
     *
     * @return the value of bill_sales_sum.num_desc
     */
    public BigDecimal getNumDesc() {
        return numDesc;
    }

    /**
     * 
     * {@linkplain #numDesc}
     * @param numDesc the value for bill_sales_sum.num_desc
     */
    public void setNumDesc(BigDecimal numDesc) {
        this.numDesc = numDesc;
    }

    /**
     * 
     * {@linkplain #numRemark}
     *
     * @return the value of bill_sales_sum.num_remark
     */
    public BigDecimal getNumRemark() {
        return numRemark;
    }

    /**
     * 
     * {@linkplain #numRemark}
     * @param numRemark the value for bill_sales_sum.num_remark
     */
    public void setNumRemark(BigDecimal numRemark) {
        this.numRemark = numRemark;
    }

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getDeduAmount() {
		return deduAmount;
	}

	public void setDeduAmount(BigDecimal deduAmount) {
		this.deduAmount = deduAmount;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public BigDecimal getTagAmount() {
		return tagAmount;
	}

	public void setTagAmount(BigDecimal tagAmount) {
		this.tagAmount = tagAmount;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getBalanceDeductAmount() {
		return balanceDeductAmount;
	}

	public void setBalanceDeductAmount(BigDecimal balanceDeductAmount) {
		this.balanceDeductAmount = balanceDeductAmount;
	}

	public BigDecimal getConpriceDeductAmount() {
		return conpriceDeductAmount;
	}

	public void setConpriceDeductAmount(BigDecimal conpriceDeductAmount) {
		this.conpriceDeductAmount = conpriceDeductAmount;
	}

	public BigDecimal getPromDeductAmount() {
		return promDeductAmount;
	}

	public void setPromDeductAmount(BigDecimal promDeductAmount) {
		this.promDeductAmount = promDeductAmount;
	}

	public String getOrganNo2() {
		return organNo2;
	}

	public void setOrganNo2(String organNo2) {
		this.organNo2 = organNo2;
	}

	public String getOrganName2() {
		return organName2;
	}

	public void setOrganName2(String organName2) {
		this.organName2 = organName2;
	}

	public String getBrandCategory() {
		return brandCategory;
	}

	public void setBrandCategory(String brandCategory) {
		this.brandCategory = brandCategory;
	}

	public String getBrandAffiliation() {
		return brandAffiliation;
	}

	public void setBrandAffiliation(String brandAffiliation) {
		this.brandAffiliation = brandAffiliation;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getSaleMode1() {
		return saleMode1;
	}

	public void setSaleMode1(String saleMode1) {
		this.saleMode1 = saleMode1;
	}

	public String getRetailType1() {
		return retailType1;
	}

	public void setRetailType1(String retailType1) {
		this.retailType1 = retailType1;
	}

	public String getMulti1() {
		return multi1;
	}

	public void setMulti1(String multi1) {
		this.multi1 = multi1;
	}

	public String getLevelName1() {
		return levelName1;
	}

	public void setLevelName1(String levelName1) {
		this.levelName1 = levelName1;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getBalanceQtyHeadquarterAmount() {
		return balanceQtyHeadquarterAmount;
	}

	public void setBalanceQtyHeadquarterAmount(
			BigDecimal balanceQtyHeadquarterAmount) {
		this.balanceQtyHeadquarterAmount = balanceQtyHeadquarterAmount;
	}

	public BigDecimal getSystemDeductAmount() {
		return systemDeductAmount;
	}

	public void setSystemDeductAmount(BigDecimal systemDeductAmount) {
		this.systemDeductAmount = systemDeductAmount;
	}

	public BigDecimal getRegionCost() {
		return regionCost;
	}

	public void setRegionCost(BigDecimal regionCost) {
		this.regionCost = regionCost;
	}

	public BigDecimal getBalanceQtyRegionAmount() {
		return balanceQtyRegionAmount;
	}

	public void setBalanceQtyRegionAmount(BigDecimal balanceQtyRegionAmount) {
		this.balanceQtyRegionAmount = balanceQtyRegionAmount;
	}

	public BigDecimal getLmaPeriodRealamount() {
		return lmaPeriodRealamount;
	}

	public void setLmaPeriodRealamount(BigDecimal lmaPeriodRealamount) {
		this.lmaPeriodRealamount = lmaPeriodRealamount;
	}

	public BigDecimal getTmiPeriodRealamount() {
		return tmiPeriodRealamount;
	}

	public void setTmiPeriodRealamount(BigDecimal tmiPeriodRealamount) {
		this.tmiPeriodRealamount = tmiPeriodRealamount;
	}

	public BigDecimal getBiPeriodRealamount() {
		return biPeriodRealamount;
	}

	public void setBiPeriodRealamount(BigDecimal biPeriodRealamount) {
		this.biPeriodRealamount = biPeriodRealamount;
	}

	public BigDecimal getTmaPeriodRealamount() {
		return tmaPeriodRealamount;
	}

	public void setTmaPeriodRealamount(BigDecimal tmaPeriodRealamount) {
		this.tmaPeriodRealamount = tmaPeriodRealamount;
	}

	public BigDecimal getTmRealamount() {
		return tmRealamount;
	}

	public void setTmRealamount(BigDecimal tmRealamount) {
		this.tmRealamount = tmRealamount;
	}

	public String getHb_shortName() {
		return hb_shortName;
	}

	public void setHb_shortName(String hb_shortName) {
		this.hb_shortName = hb_shortName;
	}

	public String getHb_multi1() {
		return hb_multi1;
	}

	public void setHb_multi1(String hb_multi1) {
		this.hb_multi1 = hb_multi1;
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

	public BigDecimal getLmaPeriodContractdeductions() {
		return lmaPeriodContractdeductions;
	}

	public void setLmaPeriodContractdeductions(
			BigDecimal lmaPeriodContractdeductions) {
		this.lmaPeriodContractdeductions = lmaPeriodContractdeductions;
	}

	public BigDecimal getLmaPeriodCutratedeductions() {
		return lmaPeriodCutratedeductions;
	}

	public void setLmaPeriodCutratedeductions(BigDecimal lmaPeriodCutratedeductions) {
		this.lmaPeriodCutratedeductions = lmaPeriodCutratedeductions;
	}

	public BigDecimal getLmaPeriodPromotiondeductions() {
		return lmaPeriodPromotiondeductions;
	}

	public void setLmaPeriodPromotiondeductions(
			BigDecimal lmaPeriodPromotiondeductions) {
		this.lmaPeriodPromotiondeductions = lmaPeriodPromotiondeductions;
	}

	public BigDecimal getTmiPeriodContractdeductions() {
		return tmiPeriodContractdeductions;
	}

	public void setTmiPeriodContractdeductions(
			BigDecimal tmiPeriodContractdeductions) {
		this.tmiPeriodContractdeductions = tmiPeriodContractdeductions;
	}

	public BigDecimal getTmiPeriodCutratedeductions() {
		return tmiPeriodCutratedeductions;
	}

	public void setTmiPeriodCutratedeductions(BigDecimal tmiPeriodCutratedeductions) {
		this.tmiPeriodCutratedeductions = tmiPeriodCutratedeductions;
	}

	public BigDecimal getTmiPeriodPromotiondeductions() {
		return tmiPeriodPromotiondeductions;
	}

	public void setTmiPeriodPromotiondeductions(
			BigDecimal tmiPeriodPromotiondeductions) {
		this.tmiPeriodPromotiondeductions = tmiPeriodPromotiondeductions;
	}

	public BigDecimal getBiPeriodContractdeductions() {
		return biPeriodContractdeductions;
	}

	public void setBiPeriodContractdeductions(BigDecimal biPeriodContractdeductions) {
		this.biPeriodContractdeductions = biPeriodContractdeductions;
	}

	public BigDecimal getBiPeriodCutratedeductions() {
		return biPeriodCutratedeductions;
	}

	public void setBiPeriodCutratedeductions(BigDecimal biPeriodCutratedeductions) {
		this.biPeriodCutratedeductions = biPeriodCutratedeductions;
	}

	public BigDecimal getBiPeriodPromotiondeductions() {
		return biPeriodPromotiondeductions;
	}

	public void setBiPeriodPromotiondeductions(
			BigDecimal biPeriodPromotiondeductions) {
		this.biPeriodPromotiondeductions = biPeriodPromotiondeductions;
	}

	public BigDecimal getTmaPeriodContractdeductions() {
		return tmaPeriodContractdeductions;
	}

	public void setTmaPeriodContractdeductions(
			BigDecimal tmaPeriodContractdeductions) {
		this.tmaPeriodContractdeductions = tmaPeriodContractdeductions;
	}

	public BigDecimal getTmaPeriodCutratedeductions() {
		return tmaPeriodCutratedeductions;
	}

	public void setTmaPeriodCutratedeductions(BigDecimal tmaPeriodCutratedeductions) {
		this.tmaPeriodCutratedeductions = tmaPeriodCutratedeductions;
	}

	public BigDecimal getTmaPeriodPromotiondeductions() {
		return tmaPeriodPromotiondeductions;
	}

	public void setTmaPeriodPromotiondeductions(
			BigDecimal tmaPeriodPromotiondeductions) {
		this.tmaPeriodPromotiondeductions = tmaPeriodPromotiondeductions;
	}

	public BigDecimal getTmContractdeductions() {
		return tmContractdeductions;
	}

	public void setTmContractdeductions(BigDecimal tmContractdeductions) {
		this.tmContractdeductions = tmContractdeductions;
	}

	public BigDecimal getTmCutratedeductions() {
		return tmCutratedeductions;
	}

	public void setTmCutratedeductions(BigDecimal tmCutratedeductions) {
		this.tmCutratedeductions = tmCutratedeductions;
	}

	public BigDecimal getTmPromotiondeductions() {
		return tmPromotiondeductions;
	}

	public void setTmPromotiondeductions(BigDecimal tmPromotiondeductions) {
		this.tmPromotiondeductions = tmPromotiondeductions;
	}

	public BigDecimal getBiPeriodOtherdeductions() {
		return biPeriodOtherdeductions;
	}

	public void setBiPeriodOtherdeductions(BigDecimal biPeriodOtherdeductions) {
		this.biPeriodOtherdeductions = biPeriodOtherdeductions;
	}

	public BigDecimal getTmOtherdeductions() {
		return tmOtherdeductions;
	}

	public void setTmOtherdeductions(BigDecimal tmOtherdeductions) {
		this.tmOtherdeductions = tmOtherdeductions;
	}

	public BigDecimal getBiPeriodPredictiondeductions() {
		return biPeriodPredictiondeductions;
	}

	public void setBiPeriodPredictiondeductions(
			BigDecimal biPeriodPredictiondeductions) {
		this.biPeriodPredictiondeductions = biPeriodPredictiondeductions;
	}

	public BigDecimal getBiPeriodMalldeductions() {
		return biPeriodMalldeductions;
	}

	public void setBiPeriodMalldeductions(BigDecimal biPeriodMalldeductions) {
		this.biPeriodMalldeductions = biPeriodMalldeductions;
	}

	public BigDecimal getTmPredictiondeductions() {
		return tmPredictiondeductions;
	}

	public void setTmPredictiondeductions(BigDecimal tmPredictiondeductions) {
		this.tmPredictiondeductions = tmPredictiondeductions;
	}

	public Byte getIsSearchPrediction() {
		return isSearchPrediction;
	}

	public void setIsSearchPrediction(Byte isSearchPrediction) {
		this.isSearchPrediction = isSearchPrediction;
	}

	public BigDecimal getBiPeriodNonTaxBalanceamount() {
		return biPeriodNonTaxBalanceamount;
	}

	public void setBiPeriodNonTaxBalanceamount(
			BigDecimal biPeriodNonTaxBalanceamount) {
		this.biPeriodNonTaxBalanceamount = biPeriodNonTaxBalanceamount;
	}

	public BigDecimal getTmaPeriodNonTaxBalanceamount() {
		return tmaPeriodNonTaxBalanceamount;
	}

	public void setTmaPeriodNonTaxBalanceamount(
			BigDecimal tmaPeriodNonTaxBalanceamount) {
		this.tmaPeriodNonTaxBalanceamount = tmaPeriodNonTaxBalanceamount;
	}

	public String getShopNoReplace() {
		return shopNoReplace;
	}

	public void setShopNoReplace(String shopNoReplace) {
		this.shopNoReplace = shopNoReplace;
	}

	public BigDecimal getBiMallNumberAmount() {
		return biMallNumberAmount;
	}

	public void setBiMallNumberAmount(BigDecimal biMallNumberAmount) {
		this.biMallNumberAmount = biMallNumberAmount;
	}

	public BigDecimal getBiSalesDiffamount() {
		return biSalesDiffamount;
	}

	public void setBiSalesDiffamount(BigDecimal biSalesDiffamount) {
		this.biSalesDiffamount = biSalesDiffamount;
	}

	public BigDecimal getBiExpenseOperateAmount() {
		return biExpenseOperateAmount;
	}

	public void setBiExpenseOperateAmount(BigDecimal biExpenseOperateAmount) {
		this.biExpenseOperateAmount = biExpenseOperateAmount;
	}

	public BigDecimal getBiBillingAmount() {
		return biBillingAmount;
	}

	public void setBiBillingAmount(BigDecimal biBillingAmount) {
		this.biBillingAmount = biBillingAmount;
	}

	public BigDecimal getTmaPromDeductAmount() {
		return tmaPromDeductAmount;
	}

	public void setTmaPromDeductAmount(BigDecimal tmaPromDeductAmount) {
		this.tmaPromDeductAmount = tmaPromDeductAmount;
	}

}