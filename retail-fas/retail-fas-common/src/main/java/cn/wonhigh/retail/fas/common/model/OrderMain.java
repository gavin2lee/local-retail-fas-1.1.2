package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:10:28
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
public class OrderMain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -926555509088651811L;

	/**
     * 主键ID,主键
     */
    private String id;
    
    /**
	 * FAS单据类型
	 */
	private String fasBillCode;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 销售门店编号
     */
    private String shopNo;

    /**
     * 销售门店名称
     */
    private String shopName;

    /**
     * 销售日期
     */
    private Date outDate;

    /**
     * 总商品数量
     */
    private Integer qty;

    /**
     * 本单牌价总金额
     */
    private BigDecimal tagPriceAmount;

    /**
     * 本单现价总金额
     */
    private BigDecimal salePriceAmount;

    /**
     * 本单折后价总金额
     */
    private BigDecimal discPriceAmount;

    /**
     * 本单结算总金额
     */
    private BigDecimal settleAmount;

    /**
     * 本单减价总金额,(单商品减价*数量)之和
     */
    private BigDecimal reduceAmount;
    
    /** 
     * 总的外卡折让金额 
     */
    private BigDecimal totalRebateAmount;

    /**
     * 促销优惠总金额
     */
    private BigDecimal prefAmount;

    /**
     * 优惠券总金额
     */
    private BigDecimal couponAmount;
    
    /**
     * 找零金额
     */
    private BigDecimal remainAmount;

    /**
     * 订单应收总金额
     */
    private BigDecimal allAmount;

    /**
     * 订单实收总金额,订单应收总金额+优惠券总金额+找零金额
     */
    private BigDecimal amount;

    /**
     * 会员卡号
     */
    private String vipNo;

    /**
     * 会员姓名
     */
    private String vipName;

    /**
     * 外卡编号
     */
    private String wildcardNo;

    /**
     * 外卡名称
     */
    private String wildcardName;

    /**
     * 
     */
    private BigDecimal wildcardDiscount;
    
    /**
     * 基本总积分
     */
    private Integer baseScore;
    
    /**
     * 整单分摊总积分
     */
    private Integer proScore;
    
    /**
     * 消费总积分
     */
    private Integer costScore;

    /**
     * 是否换货的订单
     */
    private Short orderType;

    /**
     * 订单业务类型,0-正常销售 1-跨店销售 2-商场团购 3-公司团购 4-员购 5-专柜团购  默认为0
     */
    private Integer businessType;

    /**
     * 订单来源,销售渠道
     */
    private Short orderSource;

    /**
     * 日结标识,0-未日结 1-已日结 默认为0
     */
    private Short dailyFlag;

    /**
     * 日结时间
     */
    private Date dailyDatetime;

    /**
     * 月结账标识,0-未月结 1-已月结 默认为0
     */
    private Short monthlyFlag;

    /**
     * 月结账时间
     */
    private Date monthlyDatetime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单状态,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银 40-待配货41-已发货 50-已提货 99-已完结
     */
    private Short status;

    /**
     * 实收运费
     */
    private BigDecimal actualPostage;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 创建时间,订单创建
     */
    private Date createTime;

    /**
     * 创建人编号
     */
    private String createUserNo;

    /**
     * 创建人姓名
     */
    private String createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人编号
     */
    private String updateUserNo;

    /**
     * 修改人姓名
     */
    private String updateUser;

    /**
     * 营业员姓名
     */
    private String assistantName;

    /**
     * 0-本店自提 1-快递 2-跨店自提
     */
    private Short takeMode;

    /**
     * 营业员ID
     */
    private String assistantId;

    /**
     * 营业员工号
     */
    private String assistantNo;

    /**
     * 商场小票号
     */
    private String marketTicketNo;

    /**
     * 锁单标识
     */
    private Short lockFlag;
    
    /**
     * 快递公司
     */
    private String expressCompany;
    
    /**
     * 短信验证码
     */
    private String messageCode;
    
    /**
     * 转货单号
     */
    private String billTransferNo;
    
    /**
     * 发货门店编号
     */
    private String shopNoFrom;
    
    /**
     * 发货门店名称
     */
    private String shopNameFrom;
    
    /**
     * 提货经办人编号
     */
    private String checkUserNo;
    
    /**
     * 提货经办人姓名
     */
    private String checkUser;
    
    /**
     * 发票号
     */
    private String invoiceNo;
    
    /**
     * 发票金额
     */
    private BigDecimal invoiceAmount;
    
    /**
     * 开票日期
     */
    private Date invoiceDate;
    
    /**
     * 提货日期
     */
    private Date pickupDate;
    
    /**
     * 促销流水号
     */
    private String serialNo;
    
    /**
     * 是否改单，0-未改单，1-改单
     */
    private Integer adjustFlag;
    
    /**
     * 改单人
     */
    private String adjustUser;
    
    /**
     * 改单时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date adjustTime;
    
    /**
     * 门店收银终端号
     */
    private String shopTerminalNo;

    /**
     * 支付方式
     */
    private List<OrderPayway> orderPaywayList;
    
    /**
     * 销售订单明细列表
     */
    private List<OrderDtl> orderDtls;
    
    /**
     * 销售订单明细列表
     */
    private List<POSOrderAndReturnExMainDtl> orderAndReturnExMainDtlList;
    
    /*
     * 商场编码	
     */
    private String mallNo;
    
    /**
     * 结算公司编码
     */
    private String companyNo;
    
    /*
     * 结算公司名称	
     */
    private String companyName;
    
    /*
     * 订单业务类型		
     */
    private Integer orderBillType;
    
    /*
     * 原单号	
     */
    private String oldOrderNo; 
    
    /**
     * 退换货字段
     * 原订单号
     */
    private Integer returnExchangeTime;
    
    /**
     * 结算单据编号
     */
    private String balanceNo;
    
    /**
     * 开票申请单号,类型为团购才有值
     */
    private String invoiceApplyNo;
    
    /**
     * 开票申请单号,类型为团购才有值
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String invoiceApplyDate;
    
   	/**
   	 * 冗余字段，用于日结后改单
   	 */
   	private String billAdjustNo;
   	
   	
    //加入的新的字段
    /**
     * 订单状态
     */
    private String statusStr;
    
    /**
     * 销售类别
     */
    private String orderTypeStr;
    
    /**
     * 业务类型
     */
    private String businessTypeStr;
    
    /**
     * 提货方式
     */
    private String takeModeStr;
    
    /**
     * 是否已日结
     */
    private String dailyFlagStr;
    
    /**
     * 是否已改单
     */
    private String adjustFlagStr;
    
    /**
     * 订单来源字符串
     */
    private String orderSourceStr;
    
    /**
     * 订单类型转义
     */
   	private String orderBillTypeStr;
   	
   	//以下两个字段仅用于移动pos
    /**
     * 所有订单总商品数量
     */
    private int totalAccount;
    
    /**
     * 所有订单总金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of order_main.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for order_main.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #orderNo}
     *
     * @return the value of order_main.order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 
     * {@linkplain #orderNo}
     * @param orderNo the value for order_main.order_no
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of order_main.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for order_main.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of order_main.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for order_main.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #outDate}
     *
     * @return the value of order_main.out_date
     */
    public Date getOutDate() {
        return outDate;
    }

    /**
     * 
     * {@linkplain #outDate}
     * @param outDate the value for order_main.out_date
     */
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of order_main.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for order_main.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #tagPriceAmount}
     *
     * @return the value of order_main.tag_price_amount
     */
    public BigDecimal getTagPriceAmount() {
        return tagPriceAmount;
    }

    /**
     * 
     * {@linkplain #tagPriceAmount}
     * @param tagPriceAmount the value for order_main.tag_price_amount
     */
    public void setTagPriceAmount(BigDecimal tagPriceAmount) {
        this.tagPriceAmount = tagPriceAmount;
    }

    /**
     * 
     * {@linkplain #salePriceAmount}
     *
     * @return the value of order_main.sale_price_amount
     */
    public BigDecimal getSalePriceAmount() {
        return salePriceAmount;
    }

    /**
     * 
     * {@linkplain #salePriceAmount}
     * @param salePriceAmount the value for order_main.sale_price_amount
     */
    public void setSalePriceAmount(BigDecimal salePriceAmount) {
        this.salePriceAmount = salePriceAmount;
    }

    /**
     * 
     * {@linkplain #discPriceAmount}
     *
     * @return the value of order_main.disc_price_amount
     */
    public BigDecimal getDiscPriceAmount() {
        return discPriceAmount;
    }

    /**
     * 
     * {@linkplain #discPriceAmount}
     * @param discPriceAmount the value for order_main.disc_price_amount
     */
    public void setDiscPriceAmount(BigDecimal discPriceAmount) {
        this.discPriceAmount = discPriceAmount;
    }

    /**
     * 
     * {@linkplain #settleAmount}
     *
     * @return the value of order_main.settle_amount
     */
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    /**
     * 
     * {@linkplain #settleAmount}
     * @param settleAmount the value for order_main.settle_amount
     */
    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    /**
     * 
     * {@linkplain #reduceAmount}
     *
     * @return the value of order_main.reduce_amount
     */
    public BigDecimal getReduceAmount() {
        return reduceAmount;
    }

    /**
     * 
     * {@linkplain #reduceAmount}
     * @param reduceAmount the value for order_main.reduce_amount
     */
    public void setReduceAmount(BigDecimal reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    /**
     * 
     * {@linkplain #prefAmount}
     *
     * @return the value of order_main.pref_amount
     */
    public BigDecimal getPrefAmount() {
        return prefAmount;
    }

    /**
     * 
     * {@linkplain #prefAmount}
     * @param prefAmount the value for order_main.pref_amount
     */
    public void setPrefAmount(BigDecimal prefAmount) {
        this.prefAmount = prefAmount;
    }

    /**
     * 
     * {@linkplain #couponAmount}
     *
     * @return the value of order_main.coupon_amount
     */
    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    /**
     * 
     * {@linkplain #couponAmount}
     * @param couponAmount the value for order_main.coupon_amount
     */
    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

	/**
     * 
     * {@linkplain #allAmount}
     *
     * @return the value of order_main.all_amount
     */
    public BigDecimal getAllAmount() {
        return allAmount;
    }

    /**
     * 
     * {@linkplain #allAmount}
     * @param allAmount the value for order_main.all_amount
     */
    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of order_main.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for order_main.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #vipNo}
     *
     * @return the value of order_main.vip_no
     */
    public String getVipNo() {
        return vipNo;
    }

    /**
     * 
     * {@linkplain #vipNo}
     * @param vipNo the value for order_main.vip_no
     */
    public void setVipNo(String vipNo) {
        this.vipNo = vipNo;
    }

    /**
     * 
     * {@linkplain #vipName}
     *
     * @return the value of order_main.vip_name
     */
    public String getVipName() {
        return vipName;
    }

    /**
     * 
     * {@linkplain #vipName}
     * @param vipName the value for order_main.vip_name
     */
    public void setVipName(String vipName) {
        this.vipName = vipName;
    }
    
    public String getWildcardNo() {
		return wildcardNo;
	}

	public void setWildcardNo(String wildcardNo) {
		this.wildcardNo = wildcardNo;
	}

	/**
     * 
     * {@linkplain #wildcardName}
     *
     * @return the value of order_main.wildcard_name
     */
    public String getWildcardName() {
        return wildcardName;
    }

    /**
     * 
     * {@linkplain #wildcardName}
     * @param wildcardName the value for order_main.wildcard_name
     */
    public void setWildcardName(String wildcardName) {
        this.wildcardName = wildcardName;
    }

    /**
     * 
     * {@linkplain #wildcardDiscount}
     *
     * @return the value of order_main.wildcard_discount
     */
    public BigDecimal getWildcardDiscount() {
        return wildcardDiscount;
    }

    /**
     * 
     * {@linkplain #wildcardDiscount}
     * @param wildcardDiscount the value for order_main.wildcard_discount
     */
    public void setWildcardDiscount(BigDecimal wildcardDiscount) {
        this.wildcardDiscount = wildcardDiscount;
    }

    /**
     * 
     * {@linkplain #orderType}
     *
     * @return the value of order_main.order_type
     */
    public Short getOrderType() {
        return orderType;
    }

    /**
     * 
     * {@linkplain #orderType}
     * @param orderType the value for order_main.order_type
     */
    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    /**
     * 
     * {@linkplain #businessType}
     *
     * @return the value of order_main.business_type
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 
     * {@linkplain #businessType}
     * @param businessType the value for order_main.business_type
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 
     * {@linkplain #orderSource}
     *
     * @return the value of order_main.order_source
     */
    public Short getOrderSource() {
        return orderSource;
    }

    /**
     * 
     * {@linkplain #orderSource}
     * @param orderSource the value for order_main.order_source
     */
    public void setOrderSource(Short orderSource) {
        this.orderSource = orderSource;
    }

    /**
     * 
     * {@linkplain #dailyFlag}
     *
     * @return the value of order_main.daily_flag
     */
    public Short getDailyFlag() {
        return dailyFlag;
    }

    /**
     * 
     * {@linkplain #dailyFlag}
     * @param dailyFlag the value for order_main.daily_flag
     */
    public void setDailyFlag(Short dailyFlag) {
        this.dailyFlag = dailyFlag;
    }

    /**
     * 
     * {@linkplain #dailyDatetime}
     *
     * @return the value of order_main.daily_datetime
     */
    public Date getDailyDatetime() {
        return dailyDatetime;
    }

    /**
     * 
     * {@linkplain #dailyDatetime}
     * @param dailyDatetime the value for order_main.daily_datetime
     */
    public void setDailyDatetime(Date dailyDatetime) {
        this.dailyDatetime = dailyDatetime;
    }

    /**
     * 
     * {@linkplain #monthlyFlag}
     *
     * @return the value of order_main.monthly_flag
     */
    public Short getMonthlyFlag() {
        return monthlyFlag;
    }

    /**
     * 
     * {@linkplain #monthlyFlag}
     * @param monthlyFlag the value for order_main.monthly_flag
     */
    public void setMonthlyFlag(Short monthlyFlag) {
        this.monthlyFlag = monthlyFlag;
    }

    /**
     * 
     * {@linkplain #monthlyDatetime}
     *
     * @return the value of order_main.monthly_datetime
     */
    public Date getMonthlyDatetime() {
        return monthlyDatetime;
    }

    /**
     * 
     * {@linkplain #monthlyDatetime}
     * @param monthlyDatetime the value for order_main.monthly_datetime
     */
    public void setMonthlyDatetime(Date monthlyDatetime) {
        this.monthlyDatetime = monthlyDatetime;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of order_main.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for order_main.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of order_main.status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for order_main.status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #actualPostage}
     *
     * @return the value of order_main.actual_postage
     */
    public BigDecimal getActualPostage() {
        return actualPostage;
    }

    /**
     * 
     * {@linkplain #actualPostage}
     * @param actualPostage the value for order_main.actual_postage
     */
    public void setActualPostage(BigDecimal actualPostage) {
        this.actualPostage = actualPostage;
    }

    /**
     * 
     * {@linkplain #expressNo}
     *
     * @return the value of order_main.express_no
     */
    public String getExpressNo() {
        return expressNo;
    }

    /**
     * 
     * {@linkplain #expressNo}
     * @param expressNo the value for order_main.express_no
     */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of order_main.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for order_main.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of order_main.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for order_main.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of order_main.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for order_main.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserNo() {
		return updateUserNo;
	}

	public void setUpdateUserNo(String updateUserNo) {
		this.updateUserNo = updateUserNo;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of order_main.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for order_main.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    /**
     * 
     * {@linkplain #takeMode}
     *
     * @return the value of order_main.take_mode
     */
    public Short getTakeMode() {
        return takeMode;
    }

    /**
     * 
     * {@linkplain #takeMode}
     * @param takeMode the value for order_main.take_mode
     */
    public void setTakeMode(Short takeMode) {
        this.takeMode = takeMode;
    }

    /**
     * 
     * {@linkplain #assistantId}
     *
     * @return the value of order_main.assistant_id
     */
    public String getAssistantId() {
        return assistantId;
    }

    /**
     * 
     * {@linkplain #assistantId}
     * @param assistantId the value for order_main.assistant_id
     */
    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of order_main.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for order_main.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #marketTicketNo}
     *
     * @return the value of order_main.market_ticket_no
     */
    public String getMarketTicketNo() {
        return marketTicketNo;
    }

    /**
     * 
     * {@linkplain #marketTicketNo}
     * @param marketTicketNo the value for order_main.market_ticket_no
     */
    public void setMarketTicketNo(String marketTicketNo) {
        this.marketTicketNo = marketTicketNo;
    }

    /**
     * 
     * {@linkplain #lockFlag}
     *
     * @return the value of order_main.lock_flag
     */
    public Short getLockFlag() {
        return lockFlag;
    }

    /**
     * 
     * {@linkplain #lockFlag}
     * @param lockFlag the value for order_main.lock_flag
     */
    public void setLockFlag(Short lockFlag) {
        this.lockFlag = lockFlag;
    }

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getBillTransferNo() {
		return billTransferNo;
	}

	public void setBillTransferNo(String billTransferNo) {
		this.billTransferNo = billTransferNo;
	}

	public String getShopNoFrom() {
		return shopNoFrom;
	}

	public void setShopNoFrom(String shopNoFrom) {
		this.shopNoFrom = shopNoFrom;
	}

	public String getShopNameFrom() {
		return shopNameFrom;
	}

	public void setShopNameFrom(String shopNameFrom) {
		this.shopNameFrom = shopNameFrom;
	}

	public String getCheckUserNo() {
		return checkUserNo;
	}

	public void setCheckUserNo(String checkUserNo) {
		this.checkUserNo = checkUserNo;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getShopTerminalNo() {
		return shopTerminalNo;
	}

	public void setShopTerminalNo(String shopTerminalNo) {
		this.shopTerminalNo = shopTerminalNo;
	}
  
 	public List<POSOrderAndReturnExMainDtl> getOrderAndReturnExMainDtlList() {
		return orderAndReturnExMainDtlList;
	}

	public void setOrderAndReturnExMainDtlList(List<POSOrderAndReturnExMainDtl> orderAndReturnExMainDtlList) {
		this.orderAndReturnExMainDtlList = orderAndReturnExMainDtlList;
	}

	public String getMallNo() {
		return mallNo;
	}

	public void setMallNo(String mallNo) {
		this.mallNo = mallNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getOrderBillType() {
		return orderBillType;
	}

	public void setOrderBillType(Integer orderBillType) {
		this.orderBillType = orderBillType;
	}

	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}

	public Integer getReturnExchangeTime() {
		return returnExchangeTime;
	}

	public void setReturnExchangeTime(Integer returnExchangeTime) {
		this.returnExchangeTime = returnExchangeTime;
	}

	public Integer getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(Integer baseScore) {
		this.baseScore = baseScore;
	}

	public Integer getProScore() {
		return proScore;
	}

	public void setProScore(Integer proScore) {
		this.proScore = proScore;
	}

	public Integer getCostScore() {
		return costScore;
	}

	public void setCostScore(Integer costScore) {
		this.costScore = costScore;
	}

	public Integer getAdjustFlag() {
		return adjustFlag;
	}

	public void setAdjustFlag(Integer adjustFlag) {
		this.adjustFlag = adjustFlag;
	}

	public String getAdjustUser() {
		return adjustUser;
	}

	public void setAdjustUser(String adjustUser) {
		this.adjustUser = adjustUser;
	}

	public Date getAdjustTime() {
		return adjustTime;
	}

	public void setAdjustTime(Date adjustTime) {
		this.adjustTime = adjustTime;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}

	public String getInvoiceApplyNo() {
		return invoiceApplyNo;
	}

	public void setInvoiceApplyNo(String invoiceApplyNo) {
		this.invoiceApplyNo = invoiceApplyNo;
	}

	public String getInvoiceApplyDate() {
		return invoiceApplyDate;
	}

	public void setInvoiceApplyDate(String invoiceApplyDate) {
		this.invoiceApplyDate = invoiceApplyDate;
	}

	public String getBillAdjustNo() {
		return billAdjustNo;
	}

	public void setBillAdjustNo(String billAdjustNo) {
		this.billAdjustNo = billAdjustNo;
	}

	public BigDecimal getTotalRebateAmount() {
		return totalRebateAmount;
	}

	public void setTotalRebateAmount(BigDecimal totalRebateAmount) {
		this.totalRebateAmount = totalRebateAmount;
	}

	public int getTotalAccount() {
		return totalAccount;
	}

	public void setTotalAccount(int totalAccount) {
		this.totalAccount = totalAccount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderPayway> getOrderPaywayList() {
		return orderPaywayList;
	}

	public void setOrderPaywayList(List<OrderPayway> orderPaywayList) {
		this.orderPaywayList = orderPaywayList;
	}

	public List<OrderDtl> getOrderDtls() {
		return orderDtls;
	}

	public void setOrderDtls(List<OrderDtl> orderDtls) {
		this.orderDtls = orderDtls;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getOrderTypeStr() {
		return orderTypeStr;
	}

	public void setOrderTypeStr(String orderTypeStr) {
		this.orderTypeStr = orderTypeStr;
	}

	public String getBusinessTypeStr() {
		return businessTypeStr;
	}

	public void setBusinessTypeStr(String businessTypeStr) {
		this.businessTypeStr = businessTypeStr;
	}

	public String getTakeModeStr() {
		return takeModeStr;
	}

	public void setTakeModeStr(String takeModeStr) {
		this.takeModeStr = takeModeStr;
	}

	public String getDailyFlagStr() {
		return dailyFlagStr;
	}

	public void setDailyFlagStr(String dailyFlagStr) {
		this.dailyFlagStr = dailyFlagStr;
	}

	public String getAdjustFlagStr() {
		return adjustFlagStr;
	}

	public void setAdjustFlagStr(String adjustFlagStr) {
		this.adjustFlagStr = adjustFlagStr;
	}

	public String getOrderSourceStr() {
		return orderSourceStr;
	}

	public void setOrderSourceStr(String orderSourceStr) {
		this.orderSourceStr = orderSourceStr;
	}

	public String getOrderBillTypeStr() {
		return orderBillTypeStr;
	}

	public void setOrderBillTypeStr(String orderBillTypeStr) {
		this.orderBillTypeStr = orderBillTypeStr;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getFasBillCode() {
		return fasBillCode;
	}

	public void setFasBillCode(String fasBillCode) {
		this.fasBillCode = fasBillCode;
	}
	
}