package cn.wonhigh.retail.fas.common.model;

/**
 * 请写出类的用途 
 * @author Administrator
 * @date  2015-09-01 15:49:31
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
public class PreWarnMessage {
    
	private Integer id;

    private Integer templateCode;

    private Integer type;
    
    private String companyNo;
    
    private String brandNo;

    private String brandUnitNo;
    
    private String billNo;
    
    // 扩展字段
    private Integer num;
    
    // PreWarnTemplate
    private String nodeName;
    
    private String tabTitle;
    
    private String moduleName;
    
    private String preTitle;

    private String sufTitle;

    private String postUrl;

    private String showUrl;
    
    public PreWarnMessage() { }
    
	public PreWarnMessage(Integer templateCode, Integer type, String companyNo,String brandNo, String brandUnitNo, String billNo) {
		super();
		this.templateCode = templateCode;
		this.type = type;
		this.companyNo = companyNo;
		this.brandNo = brandNo;
		this.brandUnitNo = brandUnitNo;
		this.billNo = billNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(Integer templateCode) {
		this.templateCode = templateCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	
	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandUnitNo() {
		return brandUnitNo;
	}

	public void setBrandUnitNo(String brandUnitNo) {
		this.brandUnitNo = brandUnitNo;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPreTitle() {
		return preTitle;
	}

	public void setPreTitle(String preTitle) {
		this.preTitle = preTitle;
	}

	public String getSufTitle() {
		return sufTitle;
	}

	public void setSufTitle(String sufTitle) {
		this.sufTitle = sufTitle;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	
}