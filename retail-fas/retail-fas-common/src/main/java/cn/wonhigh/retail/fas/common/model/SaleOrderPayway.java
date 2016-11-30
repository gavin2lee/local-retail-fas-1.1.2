package cn.wonhigh.retail.fas.common.model;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;

@ExportFormat(className=AbstractExportFormat.class)
public class SaleOrderPayway extends FasBaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String shopNo;
	private String shopName;
	private String companyNo;
	private String companyName;
	private String brandNo;
	private String brandName;
	private String brandUnitName;
	private Integer total;
	
	@JsonSerialize(using = JsonDateSerializer$10.class)
	private Date outDate;
	private BigDecimal   payAmount;
	private BigDecimal netAmount;
	private BigDecimal  orderAmount;
	private BigDecimal  returnAmount;
	
	private String month;
	private String year;
	
	private BigDecimal  p01;
	private BigDecimal  p03;
	private BigDecimal  p04;
	private BigDecimal  p05;
	private BigDecimal  p06;
	private BigDecimal  p08;
	private BigDecimal  p09;
	private BigDecimal  p10;
	private BigDecimal  p11;
	private BigDecimal  p12;
	private BigDecimal  p13;
	private BigDecimal  p14;
	private BigDecimal  p15;
	private BigDecimal  p16;
	private BigDecimal  p17;
	private BigDecimal  p18;
	private BigDecimal  p19;
	private BigDecimal  p20;
	private BigDecimal  p20151010;
	private BigDecimal  p21;
	private BigDecimal  p22;
	private BigDecimal  p23;
	private BigDecimal  p24;
	private BigDecimal  p25;
	private BigDecimal  p26;
	private BigDecimal  p27;
	private BigDecimal  p28;
	private BigDecimal  p29;
	private BigDecimal  p30;
	private BigDecimal  p31;
	private BigDecimal  p32;
	private BigDecimal  p33;
	private BigDecimal  p35;
	private BigDecimal  p36;
	private BigDecimal  p37;
	private BigDecimal  p38;
	private BigDecimal  p999;
	private BigDecimal totalAmount;//销售总额
	
	private BigDecimal  s01;
	private BigDecimal  s03;
	private BigDecimal  s04;
	private BigDecimal  s05;
	private BigDecimal  s06;
	private BigDecimal  s08;
	private BigDecimal  s09;
	private BigDecimal  s10;
	private BigDecimal  s11;
	private BigDecimal  s12;
	private BigDecimal  s13;
	private BigDecimal  s14;
	private BigDecimal  s15;
	private BigDecimal  s16;
	private BigDecimal  s17;
	private BigDecimal  s18;
	private BigDecimal  s19;
	private BigDecimal  s20;
	private BigDecimal  s20151010;
	private BigDecimal  s21;
	private BigDecimal  s22;
	private BigDecimal  s23;
	private BigDecimal  s24;
	private BigDecimal  s25;
	private BigDecimal  s26;
	private BigDecimal  s27;
	private BigDecimal  s28;
	private BigDecimal  s29;
	private BigDecimal  s30;
	private BigDecimal  s31;
	private BigDecimal  s32;
	private BigDecimal  s33;
	private BigDecimal  s35;
	private BigDecimal  s36;
	private BigDecimal  s37;
	private BigDecimal  s38;
	private BigDecimal  s999;
	private BigDecimal amount;//实收总额
	
	private BigDecimal  d01;
	private BigDecimal  d03;
	private BigDecimal  d04;
	private BigDecimal  d05;
	private BigDecimal  d06;
	private BigDecimal  d08;
	private BigDecimal  d09;
	private BigDecimal  d10;
	private BigDecimal  d11;
	private BigDecimal  d12;
	private BigDecimal  d13;
	private BigDecimal  d14;
	private BigDecimal  d15;
	private BigDecimal  d16;
	private BigDecimal  d17;
	private BigDecimal  d18;
	private BigDecimal  d19;
	private BigDecimal  d20;
	private BigDecimal  d20151010;
	private BigDecimal  d21;
	private BigDecimal  d22;
	private BigDecimal  d23;
	private BigDecimal  d24;
	private BigDecimal  d25;
	private BigDecimal  d26;
	private BigDecimal  d27;
	private BigDecimal  d28;
	private BigDecimal  d29;
	private BigDecimal  d30;
	private BigDecimal  d31;
	private BigDecimal  d32;
	private BigDecimal  d33;
	private BigDecimal  d35;
	private BigDecimal  d36;
	private BigDecimal  d37;
	private BigDecimal  d38;
	private BigDecimal  d999;
	private BigDecimal actualReturnAmount;
	private BigDecimal diffAmount;//销售实收差异
	private BigDecimal poundage;//手续费
	private BigDecimal sum;//进账合计
	public BigDecimal getS36() {
		return s36;
	}
	public void setS36(BigDecimal s36) {
		this.s36 = s36;
	}
	public BigDecimal getS37() {
		return s37;
	}
	public void setS37(BigDecimal s37) {
		this.s37 = s37;
	}
	public BigDecimal getS38() {
		return s38;
	}
	public void setS38(BigDecimal s38) {
		this.s38 = s38;
	}
	public BigDecimal getD20151010() {
		return d20151010;
	}
	public void setD20151010(BigDecimal d20151010) {
		this.d20151010 = d20151010;
	}
	public BigDecimal getD33() {
		return d33;
	}
	public void setD33(BigDecimal d33) {
		this.d33 = d33;
	}
	public BigDecimal getD36() {
		return d36;
	}
	public void setD36(BigDecimal d36) {
		this.d36 = d36;
	}
	public BigDecimal getD37() {
		return d37;
	}
	public void setD37(BigDecimal d37) {
		this.d37 = d37;
	}
	public BigDecimal getD38() {
		return d38;
	}
	public void setD38(BigDecimal d38) {
		this.d38 = d38;
	}
	
	
	public BigDecimal getD01() {
		return d01;
	}
	public void setD01(BigDecimal d01) {
		this.d01 = d01;
	}
	public BigDecimal getD03() {
		return d03;
	}
	public void setD03(BigDecimal d03) {
		this.d03 = d03;
	}
	public BigDecimal getD04() {
		return d04;
	}
	public void setD04(BigDecimal d04) {
		this.d04 = d04;
	}
	public BigDecimal getD05() {
		return d05;
	}
	public void setD05(BigDecimal d05) {
		this.d05 = d05;
	}
	public BigDecimal getD06() {
		return d06;
	}
	public void setD06(BigDecimal d06) {
		this.d06 = d06;
	}
	public BigDecimal getD08() {
		return d08;
	}
	public void setD08(BigDecimal d08) {
		this.d08 = d08;
	}
	public BigDecimal getD09() {
		return d09;
	}
	public void setD09(BigDecimal d09) {
		this.d09 = d09;
	}
	public BigDecimal getD10() {
		return d10;
	}
	public void setD10(BigDecimal d10) {
		this.d10 = d10;
	}
	public BigDecimal getD11() {
		return d11;
	}
	public void setD11(BigDecimal d11) {
		this.d11 = d11;
	}
	public BigDecimal getD12() {
		return d12;
	}
	public void setD12(BigDecimal d12) {
		this.d12 = d12;
	}
	public BigDecimal getD13() {
		return d13;
	}
	public void setD13(BigDecimal d13) {
		this.d13 = d13;
	}
	public BigDecimal getD14() {
		return d14;
	}
	public void setD14(BigDecimal d14) {
		this.d14 = d14;
	}
	public BigDecimal getD15() {
		return d15;
	}
	public void setD15(BigDecimal d15) {
		this.d15 = d15;
	}
	public BigDecimal getD16() {
		return d16;
	}
	public void setD16(BigDecimal d16) {
		this.d16 = d16;
	}
	public BigDecimal getD17() {
		return d17;
	}
	public void setD17(BigDecimal d17) {
		this.d17 = d17;
	}
	public BigDecimal getD18() {
		return d18;
	}
	public void setD18(BigDecimal d18) {
		this.d18 = d18;
	}
	public BigDecimal getD19() {
		return d19;
	}
	public void setD19(BigDecimal d19) {
		this.d19 = d19;
	}
	public BigDecimal getD20() {
		return d20;
	}
	public void setD20(BigDecimal d20) {
		this.d20 = d20;
	}
	public BigDecimal getD21() {
		return d21;
	}
	public void setD21(BigDecimal d21) {
		this.d21 = d21;
	}
	public BigDecimal getD22() {
		return d22;
	}
	public void setD22(BigDecimal d22) {
		this.d22 = d22;
	}
	public BigDecimal getD23() {
		return d23;
	}
	public void setD23(BigDecimal d23) {
		this.d23 = d23;
	}
	public BigDecimal getD24() {
		return d24;
	}
	public void setD24(BigDecimal d24) {
		this.d24 = d24;
	}
	public BigDecimal getD25() {
		return d25;
	}
	public void setD25(BigDecimal d25) {
		this.d25 = d25;
	}
	public BigDecimal getD26() {
		return d26;
	}
	public void setD26(BigDecimal d26) {
		this.d26 = d26;
	}
	public BigDecimal getD27() {
		return d27;
	}
	public void setD27(BigDecimal d27) {
		this.d27 = d27;
	}
	public BigDecimal getD28() {
		return d28;
	}
	public void setD28(BigDecimal d28) {
		this.d28 = d28;
	}
	public BigDecimal getD29() {
		return d29;
	}
	public void setD29(BigDecimal d29) {
		this.d29 = d29;
	}
	public BigDecimal getD30() {
		return d30;
	}
	public void setD30(BigDecimal d30) {
		this.d30 = d30;
	}
	public BigDecimal getD31() {
		return d31;
	}
	public void setD31(BigDecimal d31) {
		this.d31 = d31;
	}
	public BigDecimal getD32() {
		return d32;
	}
	public void setD32(BigDecimal d32) {
		this.d32 = d32;
	}
	public BigDecimal getD35() {
		return d35;
	}
	public void setD35(BigDecimal d35) {
		this.d35 = d35;
	}
	public BigDecimal getD999() {
		return d999;
	}
	public void setD999(BigDecimal d999) {
		this.d999 = d999;
	}
	public BigDecimal getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}
	public BigDecimal getS01() {
		return s01;
	}
	public void setS01(BigDecimal s01) {
		this.s01 = s01;
	}
	public BigDecimal getS03() {
		return s03;
	}
	public void setS03(BigDecimal s03) {
		this.s03 = s03;
	}
	public BigDecimal getS04() {
		return s04;
	}
	public void setS04(BigDecimal s04) {
		this.s04 = s04;
	}
	public BigDecimal getS05() {
		return s05;
	}
	public void setS05(BigDecimal s05) {
		this.s05 = s05;
	}
	public BigDecimal getS06() {
		return s06;
	}
	public void setS06(BigDecimal s06) {
		this.s06 = s06;
	}
	public BigDecimal getS08() {
		return s08;
	}
	public void setS08(BigDecimal s08) {
		this.s08 = s08;
	}
	public BigDecimal getS09() {
		return s09;
	}
	public void setS09(BigDecimal s09) {
		this.s09 = s09;
	}
	public BigDecimal getS10() {
		return s10;
	}
	public void setS10(BigDecimal s10) {
		this.s10 = s10;
	}
	public BigDecimal getS11() {
		return s11;
	}
	public void setS11(BigDecimal s11) {
		this.s11 = s11;
	}
	public BigDecimal getS12() {
		return s12;
	}
	public void setS12(BigDecimal s12) {
		this.s12 = s12;
	}
	public BigDecimal getS13() {
		return s13;
	}
	public void setS13(BigDecimal s13) {
		this.s13 = s13;
	}
	public BigDecimal getS14() {
		return s14;
	}
	public void setS14(BigDecimal s14) {
		this.s14 = s14;
	}
	public BigDecimal getS15() {
		return s15;
	}
	public void setS15(BigDecimal s15) {
		this.s15 = s15;
	}
	public BigDecimal getS16() {
		return s16;
	}
	public void setS16(BigDecimal s16) {
		this.s16 = s16;
	}
	public BigDecimal getS17() {
		return s17;
	}
	public void setS17(BigDecimal s17) {
		this.s17 = s17;
	}
	public BigDecimal getS18() {
		return s18;
	}
	public void setS18(BigDecimal s18) {
		this.s18 = s18;
	}
	public BigDecimal getS19() {
		return s19;
	}
	public void setS19(BigDecimal s19) {
		this.s19 = s19;
	}
	public BigDecimal getS20() {
		return s20;
	}
	public void setS20(BigDecimal s20) {
		this.s20 = s20;
	}
	public BigDecimal getS20151010() {
		return s20151010;
	}
	public void setS20151010(BigDecimal s20151010) {
		this.s20151010 = s20151010;
	}
	public BigDecimal getS21() {
		return s21;
	}
	public void setS21(BigDecimal s21) {
		this.s21 = s21;
	}
	public BigDecimal getS22() {
		return s22;
	}
	public void setS22(BigDecimal s22) {
		this.s22 = s22;
	}
	public BigDecimal getS23() {
		return s23;
	}
	public void setS23(BigDecimal s23) {
		this.s23 = s23;
	}
	public BigDecimal getS24() {
		return s24;
	}
	public void setS24(BigDecimal s24) {
		this.s24 = s24;
	}
	public BigDecimal getS25() {
		return s25;
	}
	public void setS25(BigDecimal s25) {
		this.s25 = s25;
	}
	public BigDecimal getS26() {
		return s26;
	}
	public void setS26(BigDecimal s26) {
		this.s26 = s26;
	}
	public BigDecimal getS27() {
		return s27;
	}
	public void setS27(BigDecimal s27) {
		this.s27 = s27;
	}
	public BigDecimal getS28() {
		return s28;
	}
	public void setS28(BigDecimal s28) {
		this.s28 = s28;
	}
	public BigDecimal getS29() {
		return s29;
	}
	public void setS29(BigDecimal s29) {
		this.s29 = s29;
	}
	public BigDecimal getS30() {
		return s30;
	}
	public void setS30(BigDecimal s30) {
		this.s30 = s30;
	}
	public BigDecimal getS31() {
		return s31;
	}
	public void setS31(BigDecimal s31) {
		this.s31 = s31;
	}
	public BigDecimal getS32() {
		return s32;
	}
	public void setS32(BigDecimal s32) {
		this.s32 = s32;
	}
	public BigDecimal getS33() {
		return s33;
	}
	public void setS33(BigDecimal s33) {
		this.s33 = s33;
	}
	public BigDecimal getS35() {
		return s35;
	}
	public void setS35(BigDecimal s35) {
		this.s35 = s35;
	}
	public BigDecimal getS999() {
		return s999;
	}
	public void setS999(BigDecimal s999) {
		this.s999 = s999;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}
	public BigDecimal getP01() {
		return p01;
	}
	public void setP01(BigDecimal p01) {
		this.p01 = p01;
	}
	public BigDecimal getP03() {
		return p03;
	}
	public void setP03(BigDecimal p03) {
		this.p03 = p03;
	}
	public BigDecimal getP04() {
		return p04;
	}
	public void setP04(BigDecimal p04) {
		this.p04 = p04;
	}
	public BigDecimal getP05() {
		return p05;
	}
	public void setP05(BigDecimal p05) {
		this.p05 = p05;
	}
	public BigDecimal getP06() {
		return p06;
	}
	public void setP06(BigDecimal p06) {
		this.p06 = p06;
	}
	public BigDecimal getP08() {
		return p08;
	}
	public void setP08(BigDecimal p08) {
		this.p08 = p08;
	}
	public BigDecimal getP09() {
		return p09;
	}
	public void setP09(BigDecimal p09) {
		this.p09 = p09;
	}
	public BigDecimal getP10() {
		return p10;
	}
	public void setP10(BigDecimal p10) {
		this.p10 = p10;
	}
	public BigDecimal getP11() {
		return p11;
	}
	public void setP11(BigDecimal p11) {
		this.p11 = p11;
	}
	public BigDecimal getP12() {
		return p12;
	}
	public void setP12(BigDecimal p12) {
		this.p12 = p12;
	}
	public BigDecimal getP13() {
		return p13;
	}
	public void setP13(BigDecimal p13) {
		this.p13 = p13;
	}
	public BigDecimal getP14() {
		return p14;
	}
	public void setP14(BigDecimal p14) {
		this.p14 = p14;
	}
	public BigDecimal getP15() {
		return p15;
	}
	public void setP15(BigDecimal p15) {
		this.p15 = p15;
	}
	public BigDecimal getP16() {
		return p16;
	}
	public void setP16(BigDecimal p16) {
		this.p16 = p16;
	}
	public BigDecimal getP17() {
		return p17;
	}
	public void setP17(BigDecimal p17) {
		this.p17 = p17;
	}
	public BigDecimal getP18() {
		return p18;
	}
	public void setP18(BigDecimal p18) {
		this.p18 = p18;
	}
	public BigDecimal getP19() {
		return p19;
	}
	public void setP19(BigDecimal p19) {
		this.p19 = p19;
	}
	public BigDecimal getP20() {
		return p20;
	}
	public void setP20(BigDecimal p20) {
		this.p20 = p20;
	}
	public BigDecimal getP20151010() {
		return p20151010;
	}
	public void setP20151010(BigDecimal p20151010) {
		this.p20151010 = p20151010;
	}
	public BigDecimal getP21() {
		return p21;
	}
	public void setP21(BigDecimal p21) {
		this.p21 = p21;
	}
	public BigDecimal getP22() {
		return p22;
	}
	public void setP22(BigDecimal p22) {
		this.p22 = p22;
	}
	public BigDecimal getP23() {
		return p23;
	}
	public void setP23(BigDecimal p23) {
		this.p23 = p23;
	}
	public BigDecimal getP24() {
		return p24;
	}
	public void setP24(BigDecimal p24) {
		this.p24 = p24;
	}
	public BigDecimal getP25() {
		return p25;
	}
	public void setP25(BigDecimal p25) {
		this.p25 = p25;
	}
	public BigDecimal getP26() {
		return p26;
	}
	public void setP26(BigDecimal p26) {
		this.p26 = p26;
	}
	public BigDecimal getP27() {
		return p27;
	}
	public void setP27(BigDecimal p27) {
		this.p27 = p27;
	}
	public BigDecimal getP28() {
		return p28;
	}
	public void setP28(BigDecimal p28) {
		this.p28 = p28;
	}
	public BigDecimal getP29() {
		return p29;
	}
	public void setP29(BigDecimal p29) {
		this.p29 = p29;
	}
	public BigDecimal getP30() {
		return p30;
	}
	public void setP30(BigDecimal p30) {
		this.p30 = p30;
	}
	public BigDecimal getP31() {
		return p31;
	}
	public void setP31(BigDecimal p31) {
		this.p31 = p31;
	}
	public BigDecimal getP32() {
		return p32;
	}
	public void setP32(BigDecimal p32) {
		this.p32 = p32;
	}
	public BigDecimal getP33() {
		return p33;
	}
	public void setP33(BigDecimal p33) {
		this.p33 = p33;
	}
	public BigDecimal getP35() {
		return p35;
	}
	public void setP35(BigDecimal p35) {
		this.p35 = p35;
	}
	public BigDecimal getP36() {
		return p36;
	}
	public void setP36(BigDecimal p36) {
		this.p36 = p36;
	}
	public BigDecimal getP999() {
		return p999;
	}
	public BigDecimal getP37() {
		return p37;
	}
	public void setP37(BigDecimal p37) {
		this.p37 = p37;
	}
	public BigDecimal getP38() {
		return p38;
	}
	public void setP38(BigDecimal p38) {
		this.p38 = p38;
	}
	public void setP999(BigDecimal p999) {
		this.p999 = p999;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
	public BigDecimal getPoundage() {
		return poundage;
	}
	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public BigDecimal getActualReturnAmount() {
		return actualReturnAmount;
	}
	public void setActualReturnAmount(BigDecimal actualReturnAmount) {
		this.actualReturnAmount = actualReturnAmount;
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
	public String getBrandUnitName() {
		return brandUnitName;
	}
	public void setBrandUnitName(String brandUnitName) {
		this.brandUnitName = brandUnitName;
	}
}
