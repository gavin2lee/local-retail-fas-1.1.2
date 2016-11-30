package cn.wonhigh.retail.fas.common.model;

/**
 * 新旧款明细
 * @author yang.y
 * @date  2014-08-26 15:42:01
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
public class SettleNewStyleDtl extends FasBaseModel implements SequenceStrId {

	private static final long serialVersionUID = -2741584374522195888L;

    /**
     * 分类明细编码
     */
    private String styleNo;

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

    
	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	/**
     * 
     * {@linkplain #styleNo}
     *
     * @return the value of settle_new_style_dtl.style_no
     */
    public String getStyleNo() {
        return styleNo;
    }

    /**
     * 
     * {@linkplain #styleNo}
     * @param styleNo the value for settle_new_style_dtl.style_no
     */
    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

    /**
     * 
     * {@linkplain #seasonNo}
     *
     * @return the value of settle_new_style_dtl.season_no
     */
    public String getSeasonNo() {
        return seasonNo;
    }

    /**
     * 
     * {@linkplain #seasonNo}
     * @param seasonNo the value for settle_new_style_dtl.season_no
     */
    public void setSeasonNo(String seasonNo) {
        this.seasonNo = seasonNo;
    }

    /**
     * 
     * {@linkplain #seasonName}
     *
     * @return the value of settle_new_style_dtl.season_name
     */
    public String getSeasonName() {
        return seasonName;
    }

    /**
     * 
     * {@linkplain #seasonName}
     * @param seasonName the value for settle_new_style_dtl.season_name
     */
    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    /**
     * 
     * {@linkplain #year}
     *
     * @return the value of settle_new_style_dtl.year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * {@linkplain #year}
     * @param year the value for settle_new_style_dtl.year
     */
    public void setYear(String year) {
        this.year = year;
    }
}