package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-03-12 10:13:46
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
public class ReturnExchangeMain implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2466408376589868441L;

	/**
     * 主键ID,主键
     */
    private String id;
    
    /**
	 * FAS单据类型
	 */
	private String fasBillCode;

    /**
     * 退换货单号,业务编号
     */
    private String businessNo;
    
    /**
     * 结算公司编码
     */
    private String companyNo;
    
    /**
     * 结算公司名称	
     */
    private String companyName;

    /**
     * 门店编号
     */
    private String shopNo;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 退换货日期
     */
    private Date outDate;

    /**
     * 原单号日期
     */
    private Date oldOutDate;

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
     * 外卡总折让金额 
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
     * 应收总金额
     */
    private BigDecimal allAmount;

    /**
     * 实收总金额,订单应收总金额+优惠券总金额+找零金额
     */
    private BigDecimal amount;

    /**
     * 订单找零金额
     */
    private BigDecimal remainAmount;

    /**
     * 营业员姓名,营业员姓名，多个营业员使用逗号隔开
     */
    private String assistantName;

    /**
     * 营业员主键ID
     */
    private String assistantId;

    /**
     * 营业员工号
     */
    private String assistantNo;

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
     * 外卡折数
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
     * 业务类别,1-换货 2-退货 默认为2
     */
    private Integer businessMode;

    /**
     * 业务类型,取原订单的业务类型
     */
    private Integer businessType;

    /**
     * 订单来源,0-pos,1-移动pos
     */
    private Boolean orderSource;

    /**
     * 日结标识,0-未日结 1-已日结 默认为0
     */
    private Boolean dailyFlag;

    /**
     * 日结时间
     */
    private Date dailyDatetime;

    /**
     * 结算单据编号
     */
    private String balanceNo;

    /**
     * 月结账标识,0-未月结 1-已月结 默认为0
     */
    private Boolean monthlyFlag;

    /**
     * 月结账时间
     */
    private Date monthlyDatetime;

    /**
     * 原订单号
     */
    private String oldOrderNo;

    /**
     * 新订单号(换货),业务类型是换货时，由换货产生的新订单单号
     */
    private String newOrderNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 退换货状态,0-已创建 10-已挂起 11-已取消 20-已审核 30-已收银 40-待配货41-已发货 50-已提货 99-已完结
     */
    private Boolean status;

    /**
     * 实收运费
     */
    private BigDecimal actualPostage;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 提货方式,0-自提 1-快递
     */
    private Boolean takeMode;

    /**
     * 商场小票号
     */
    private String marketTicketNo;

    /**
     * 锁单标识,0-未锁单 1-已锁单
     */
    private Boolean lockFlag;

    /**
     * 退换货次数,只有换货订单才有
     */
    private Byte returnExchangeTime;

    /**
     * 门店收银终端号
     */
    private String shopTerminalNo;

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
     * 创建人编号
     */
    private String createUserNo;

    /**
     * 创建人姓名
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人编号
     */
    private String updateUserNo;

    /**
     * 修改人姓名
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否改单，0-未改单，1-日结前改单， 2-日结后改单
     */
    private Boolean adjustFlag;

    /**
     * 改单人
     */
    private String adjustUser;

    /**
     * 改单日期
     */
    private Date adjustTime;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of return_exchange_main.id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for return_exchange_main.id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #businessNo}
     *
     * @return the value of return_exchange_main.business_no
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * 
     * {@linkplain #businessNo}
     * @param businessNo the value for return_exchange_main.business_no
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of return_exchange_main.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for return_exchange_main.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of return_exchange_main.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for return_exchange_main.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * {@linkplain #outDate}
     *
     * @return the value of return_exchange_main.out_date
     */
    public Date getOutDate() {
        return outDate;
    }

    /**
     * 
     * {@linkplain #outDate}
     * @param outDate the value for return_exchange_main.out_date
     */
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    /**
     * 
     * {@linkplain #oldOutDate}
     *
     * @return the value of return_exchange_main.old_out_date
     */
    public Date getOldOutDate() {
        return oldOutDate;
    }

    /**
     * 
     * {@linkplain #oldOutDate}
     * @param oldOutDate the value for return_exchange_main.old_out_date
     */
    public void setOldOutDate(Date oldOutDate) {
        this.oldOutDate = oldOutDate;
    }

    /**
     * 
     * {@linkplain #qty}
     *
     * @return the value of return_exchange_main.qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * {@linkplain #qty}
     * @param qty the value for return_exchange_main.qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 
     * {@linkplain #tagPriceAmount}
     *
     * @return the value of return_exchange_main.tag_price_amount
     */
    public BigDecimal getTagPriceAmount() {
        return tagPriceAmount;
    }

    /**
     * 
     * {@linkplain #tagPriceAmount}
     * @param tagPriceAmount the value for return_exchange_main.tag_price_amount
     */
    public void setTagPriceAmount(BigDecimal tagPriceAmount) {
        this.tagPriceAmount = tagPriceAmount;
    }

    /**
     * 
     * {@linkplain #salePriceAmount}
     *
     * @return the value of return_exchange_main.sale_price_amount
     */
    public BigDecimal getSalePriceAmount() {
        return salePriceAmount;
    }

    /**
     * 
     * {@linkplain #salePriceAmount}
     * @param salePriceAmount the value for return_exchange_main.sale_price_amount
     */
    public void setSalePriceAmount(BigDecimal salePriceAmount) {
        this.salePriceAmount = salePriceAmount;
    }

    /**
     * 
     * {@linkplain #discPriceAmount}
     *
     * @return the value of return_exchange_main.disc_price_amount
     */
    public BigDecimal getDiscPriceAmount() {
        return discPriceAmount;
    }

    /**
     * 
     * {@linkplain #discPriceAmount}
     * @param discPriceAmount the value for return_exchange_main.disc_price_amount
     */
    public void setDiscPriceAmount(BigDecimal discPriceAmount) {
        this.discPriceAmount = discPriceAmount;
    }

    /**
     * 
     * {@linkplain #settleAmount}
     *
     * @return the value of return_exchange_main.settle_amount
     */
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    /**
     * 
     * {@linkplain #settleAmount}
     * @param settleAmount the value for return_exchange_main.settle_amount
     */
    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    /**
     * 
     * {@linkplain #reduceAmount}
     *
     * @return the value of return_exchange_main.reduce_amount
     */
    public BigDecimal getReduceAmount() {
        return reduceAmount;
    }

    /**
     * 
     * {@linkplain #reduceAmount}
     * @param reduceAmount the value for return_exchange_main.reduce_amount
     */
    public void setReduceAmount(BigDecimal reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    /**
     * 
     * {@linkplain #totalRebateAmount}
     *
     * @return the value of return_exchange_main.total_rebate_amount
     */
    public BigDecimal getTotalRebateAmount() {
        return totalRebateAmount;
    }

    /**
     * 
     * {@linkplain #totalRebateAmount}
     * @param totalRebateAmount the value for return_exchange_main.total_rebate_amount
     */
    public void setTotalRebateAmount(BigDecimal totalRebateAmount) {
        this.totalRebateAmount = totalRebateAmount;
    }

    /**
     * 
     * {@linkplain #prefAmount}
     *
     * @return the value of return_exchange_main.pref_amount
     */
    public BigDecimal getPrefAmount() {
        return prefAmount;
    }

    /**
     * 
     * {@linkplain #prefAmount}
     * @param prefAmount the value for return_exchange_main.pref_amount
     */
    public void setPrefAmount(BigDecimal prefAmount) {
        this.prefAmount = prefAmount;
    }

    /**
     * 
     * {@linkplain #couponAmount}
     *
     * @return the value of return_exchange_main.coupon_amount
     */
    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    /**
     * 
     * {@linkplain #couponAmount}
     * @param couponAmount the value for return_exchange_main.coupon_amount
     */
    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    /**
     * 
     * {@linkplain #allAmount}
     *
     * @return the value of return_exchange_main.all_amount
     */
    public BigDecimal getAllAmount() {
        return allAmount;
    }

    /**
     * 
     * {@linkplain #allAmount}
     * @param allAmount the value for return_exchange_main.all_amount
     */
    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    /**
     * 
     * {@linkplain #amount}
     *
     * @return the value of return_exchange_main.amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 
     * {@linkplain #amount}
     * @param amount the value for return_exchange_main.amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 
     * {@linkplain #remainAmount}
     *
     * @return the value of return_exchange_main.remain_amount
     */
    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    /**
     * 
     * {@linkplain #remainAmount}
     * @param remainAmount the value for return_exchange_main.remain_amount
     */
    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    /**
     * 
     * {@linkplain #assistantName}
     *
     * @return the value of return_exchange_main.assistant_name
     */
    public String getAssistantName() {
        return assistantName;
    }

    /**
     * 
     * {@linkplain #assistantName}
     * @param assistantName the value for return_exchange_main.assistant_name
     */
    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    /**
     * 
     * {@linkplain #assistantId}
     *
     * @return the value of return_exchange_main.assistant_id
     */
    public String getAssistantId() {
        return assistantId;
    }

    /**
     * 
     * {@linkplain #assistantId}
     * @param assistantId the value for return_exchange_main.assistant_id
     */
    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     *
     * @return the value of return_exchange_main.assistant_no
     */
    public String getAssistantNo() {
        return assistantNo;
    }

    /**
     * 
     * {@linkplain #assistantNo}
     * @param assistantNo the value for return_exchange_main.assistant_no
     */
    public void setAssistantNo(String assistantNo) {
        this.assistantNo = assistantNo;
    }

    /**
     * 
     * {@linkplain #vipNo}
     *
     * @return the value of return_exchange_main.vip_no
     */
    public String getVipNo() {
        return vipNo;
    }

    /**
     * 
     * {@linkplain #vipNo}
     * @param vipNo the value for return_exchange_main.vip_no
     */
    public void setVipNo(String vipNo) {
        this.vipNo = vipNo;
    }

    /**
     * 
     * {@linkplain #vipName}
     *
     * @return the value of return_exchange_main.vip_name
     */
    public String getVipName() {
        return vipName;
    }

    /**
     * 
     * {@linkplain #vipName}
     * @param vipName the value for return_exchange_main.vip_name
     */
    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    /**
     * 
     * {@linkplain #wildcardNo}
     *
     * @return the value of return_exchange_main.wildcard_no
     */
    public String getWildcardNo() {
        return wildcardNo;
    }

    /**
     * 
     * {@linkplain #wildcardNo}
     * @param wildcardNo the value for return_exchange_main.wildcard_no
     */
    public void setWildcardNo(String wildcardNo) {
        this.wildcardNo = wildcardNo;
    }

    /**
     * 
     * {@linkplain #wildcardName}
     *
     * @return the value of return_exchange_main.wildcard_name
     */
    public String getWildcardName() {
        return wildcardName;
    }

    /**
     * 
     * {@linkplain #wildcardName}
     * @param wildcardName the value for return_exchange_main.wildcard_name
     */
    public void setWildcardName(String wildcardName) {
        this.wildcardName = wildcardName;
    }

    /**
     * 
     * {@linkplain #wildcardDiscount}
     *
     * @return the value of return_exchange_main.wildcard_discount
     */
    public BigDecimal getWildcardDiscount() {
        return wildcardDiscount;
    }

    /**
     * 
     * {@linkplain #wildcardDiscount}
     * @param wildcardDiscount the value for return_exchange_main.wildcard_discount
     */
    public void setWildcardDiscount(BigDecimal wildcardDiscount) {
        this.wildcardDiscount = wildcardDiscount;
    }

    /**
     * 
     * {@linkplain #baseScore}
     *
     * @return the value of return_exchange_main.base_score
     */
    public Integer getBaseScore() {
        return baseScore;
    }

    /**
     * 
     * {@linkplain #baseScore}
     * @param baseScore the value for return_exchange_main.base_score
     */
    public void setBaseScore(Integer baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     *
     * @return the value of return_exchange_main.pro_score
     */
    public Integer getProScore() {
        return proScore;
    }

    /**
     * 
     * {@linkplain #proScore}
     * @param proScore the value for return_exchange_main.pro_score
     */
    public void setProScore(Integer proScore) {
        this.proScore = proScore;
    }

    /**
     * 
     * {@linkplain #costScore}
     *
     * @return the value of return_exchange_main.cost_score
     */
    public Integer getCostScore() {
        return costScore;
    }

    /**
     * 
     * {@linkplain #costScore}
     * @param costScore the value for return_exchange_main.cost_score
     */
    public void setCostScore(Integer costScore) {
        this.costScore = costScore;
    }

    /**
     * 
     * {@linkplain #businessMode}
     *
     * @return the value of return_exchange_main.business_mode
     */
    public Integer getBusinessMode() {
        return businessMode;
    }

    /**
     * 
     * {@linkplain #businessMode}
     * @param businessMode the value for return_exchange_main.business_mode
     */
    public void setBusinessMode(Integer businessMode) {
        this.businessMode = businessMode;
    }

    /**
     * 
     * {@linkplain #businessType}
     *
     * @return the value of return_exchange_main.business_type
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 
     * {@linkplain #businessType}
     * @param businessType the value for return_exchange_main.business_type
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 
     * {@linkplain #orderSource}
     *
     * @return the value of return_exchange_main.order_source
     */
    public Boolean getOrderSource() {
        return orderSource;
    }

    /**
     * 
     * {@linkplain #orderSource}
     * @param orderSource the value for return_exchange_main.order_source
     */
    public void setOrderSource(Boolean orderSource) {
        this.orderSource = orderSource;
    }

    /**
     * 
     * {@linkplain #dailyFlag}
     *
     * @return the value of return_exchange_main.daily_flag
     */
    public Boolean getDailyFlag() {
        return dailyFlag;
    }

    /**
     * 
     * {@linkplain #dailyFlag}
     * @param dailyFlag the value for return_exchange_main.daily_flag
     */
    public void setDailyFlag(Boolean dailyFlag) {
        this.dailyFlag = dailyFlag;
    }

    /**
     * 
     * {@linkplain #dailyDatetime}
     *
     * @return the value of return_exchange_main.daily_datetime
     */
    public Date getDailyDatetime() {
        return dailyDatetime;
    }

    /**
     * 
     * {@linkplain #dailyDatetime}
     * @param dailyDatetime the value for return_exchange_main.daily_datetime
     */
    public void setDailyDatetime(Date dailyDatetime) {
        this.dailyDatetime = dailyDatetime;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     *
     * @return the value of return_exchange_main.balance_no
     */
    public String getBalanceNo() {
        return balanceNo;
    }

    /**
     * 
     * {@linkplain #balanceNo}
     * @param balanceNo the value for return_exchange_main.balance_no
     */
    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    /**
     * 
     * {@linkplain #monthlyFlag}
     *
     * @return the value of return_exchange_main.monthly_flag
     */
    public Boolean getMonthlyFlag() {
        return monthlyFlag;
    }

    /**
     * 
     * {@linkplain #monthlyFlag}
     * @param monthlyFlag the value for return_exchange_main.monthly_flag
     */
    public void setMonthlyFlag(Boolean monthlyFlag) {
        this.monthlyFlag = monthlyFlag;
    }

    /**
     * 
     * {@linkplain #monthlyDatetime}
     *
     * @return the value of return_exchange_main.monthly_datetime
     */
    public Date getMonthlyDatetime() {
        return monthlyDatetime;
    }

    /**
     * 
     * {@linkplain #monthlyDatetime}
     * @param monthlyDatetime the value for return_exchange_main.monthly_datetime
     */
    public void setMonthlyDatetime(Date monthlyDatetime) {
        this.monthlyDatetime = monthlyDatetime;
    }

    /**
     * 
     * {@linkplain #oldOrderNo}
     *
     * @return the value of return_exchange_main.old_order_no
     */
    public String getOldOrderNo() {
        return oldOrderNo;
    }

    /**
     * 
     * {@linkplain #oldOrderNo}
     * @param oldOrderNo the value for return_exchange_main.old_order_no
     */
    public void setOldOrderNo(String oldOrderNo) {
        this.oldOrderNo = oldOrderNo;
    }

    /**
     * 
     * {@linkplain #newOrderNo}
     *
     * @return the value of return_exchange_main.new_order_no
     */
    public String getNewOrderNo() {
        return newOrderNo;
    }

    /**
     * 
     * {@linkplain #newOrderNo}
     * @param newOrderNo the value for return_exchange_main.new_order_no
     */
    public void setNewOrderNo(String newOrderNo) {
        this.newOrderNo = newOrderNo;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of return_exchange_main.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for return_exchange_main.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of return_exchange_main.status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for return_exchange_main.status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 
     * {@linkplain #actualPostage}
     *
     * @return the value of return_exchange_main.actual_postage
     */
    public BigDecimal getActualPostage() {
        return actualPostage;
    }

    /**
     * 
     * {@linkplain #actualPostage}
     * @param actualPostage the value for return_exchange_main.actual_postage
     */
    public void setActualPostage(BigDecimal actualPostage) {
        this.actualPostage = actualPostage;
    }

    /**
     * 
     * {@linkplain #expressNo}
     *
     * @return the value of return_exchange_main.express_no
     */
    public String getExpressNo() {
        return expressNo;
    }

    /**
     * 
     * {@linkplain #expressNo}
     * @param expressNo the value for return_exchange_main.express_no
     */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    /**
     * 
     * {@linkplain #takeMode}
     *
     * @return the value of return_exchange_main.take_mode
     */
    public Boolean getTakeMode() {
        return takeMode;
    }

    /**
     * 
     * {@linkplain #takeMode}
     * @param takeMode the value for return_exchange_main.take_mode
     */
    public void setTakeMode(Boolean takeMode) {
        this.takeMode = takeMode;
    }

    /**
     * 
     * {@linkplain #marketTicketNo}
     *
     * @return the value of return_exchange_main.market_ticket_no
     */
    public String getMarketTicketNo() {
        return marketTicketNo;
    }

    /**
     * 
     * {@linkplain #marketTicketNo}
     * @param marketTicketNo the value for return_exchange_main.market_ticket_no
     */
    public void setMarketTicketNo(String marketTicketNo) {
        this.marketTicketNo = marketTicketNo;
    }

    /**
     * 
     * {@linkplain #lockFlag}
     *
     * @return the value of return_exchange_main.lock_flag
     */
    public Boolean getLockFlag() {
        return lockFlag;
    }

    /**
     * 
     * {@linkplain #lockFlag}
     * @param lockFlag the value for return_exchange_main.lock_flag
     */
    public void setLockFlag(Boolean lockFlag) {
        this.lockFlag = lockFlag;
    }

    /**
     * 
     * {@linkplain #returnExchangeTime}
     *
     * @return the value of return_exchange_main.return_exchange_time
     */
    public Byte getReturnExchangeTime() {
        return returnExchangeTime;
    }

    /**
     * 
     * {@linkplain #returnExchangeTime}
     * @param returnExchangeTime the value for return_exchange_main.return_exchange_time
     */
    public void setReturnExchangeTime(Byte returnExchangeTime) {
        this.returnExchangeTime = returnExchangeTime;
    }

    /**
     * 
     * {@linkplain #shopTerminalNo}
     *
     * @return the value of return_exchange_main.shop_terminal_no
     */
    public String getShopTerminalNo() {
        return shopTerminalNo;
    }

    /**
     * 
     * {@linkplain #shopTerminalNo}
     * @param shopTerminalNo the value for return_exchange_main.shop_terminal_no
     */
    public void setShopTerminalNo(String shopTerminalNo) {
        this.shopTerminalNo = shopTerminalNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     *
     * @return the value of return_exchange_main.invoice_no
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceNo}
     * @param invoiceNo the value for return_exchange_main.invoice_no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     *
     * @return the value of return_exchange_main.invoice_amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceAmount}
     * @param invoiceAmount the value for return_exchange_main.invoice_amount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     *
     * @return the value of return_exchange_main.invoice_date
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * 
     * {@linkplain #invoiceDate}
     * @param invoiceDate the value for return_exchange_main.invoice_date
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     *
     * @return the value of return_exchange_main.create_user_no
     */
    public String getCreateUserNo() {
        return createUserNo;
    }

    /**
     * 
     * {@linkplain #createUserNo}
     * @param createUserNo the value for return_exchange_main.create_user_no
     */
    public void setCreateUserNo(String createUserNo) {
        this.createUserNo = createUserNo;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of return_exchange_main.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for return_exchange_main.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of return_exchange_main.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for return_exchange_main.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     *
     * @return the value of return_exchange_main.update_user_no
     */
    public String getUpdateUserNo() {
        return updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUserNo}
     * @param updateUserNo the value for return_exchange_main.update_user_no
     */
    public void setUpdateUserNo(String updateUserNo) {
        this.updateUserNo = updateUserNo;
    }

    /**
     * 
     * {@linkplain #updateUser}
     *
     * @return the value of return_exchange_main.update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * {@linkplain #updateUser}
     * @param updateUser the value for return_exchange_main.update_user
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     * {@linkplain #updateTime}
     *
     * @return the value of return_exchange_main.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * {@linkplain #updateTime}
     * @param updateTime the value for return_exchange_main.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * {@linkplain #adjustFlag}
     *
     * @return the value of return_exchange_main.adjust_flag
     */
    public Boolean getAdjustFlag() {
        return adjustFlag;
    }

    /**
     * 
     * {@linkplain #adjustFlag}
     * @param adjustFlag the value for return_exchange_main.adjust_flag
     */
    public void setAdjustFlag(Boolean adjustFlag) {
        this.adjustFlag = adjustFlag;
    }

    /**
     * 
     * {@linkplain #adjustUser}
     *
     * @return the value of return_exchange_main.adjust_user
     */
    public String getAdjustUser() {
        return adjustUser;
    }

    /**
     * 
     * {@linkplain #adjustUser}
     * @param adjustUser the value for return_exchange_main.adjust_user
     */
    public void setAdjustUser(String adjustUser) {
        this.adjustUser = adjustUser;
    }

    /**
     * 
     * {@linkplain #adjustTime}
     *
     * @return the value of return_exchange_main.adjust_time
     */
    public Date getAdjustTime() {
        return adjustTime;
    }

    /**
     * 
     * {@linkplain #adjustTime}
     * @param adjustTime the value for return_exchange_main.adjust_time
     */
    public void setAdjustTime(Date adjustTime) {
        this.adjustTime = adjustTime;
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

	public String getFasBillCode() {
		return fasBillCode;
	}

	public void setFasBillCode(String fasBillCode) {
		this.fasBillCode = fasBillCode;
	}
}