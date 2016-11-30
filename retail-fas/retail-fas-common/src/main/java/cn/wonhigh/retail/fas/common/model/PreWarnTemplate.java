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
public class PreWarnTemplate {
    private Integer id;

    private String nodeName;

    private Integer code;
    
    private Integer type;
    
    private String tabTitle;
    
    private String moduleName;

    private String preTitle;

    private String sufTitle;

    private String balanceType;

    private String status;

    private String extendStatus;

    private String sqlStatement;
    
    private String sqlDeleteStatement;

    private String postUrl;

    private String showUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtendStatus() {
        return extendStatus;
    }

    public void setExtendStatus(String extendStatus) {
        this.extendStatus = extendStatus;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public String getSqlDeleteStatement() {
		return sqlDeleteStatement;
	}

	public void setSqlDeleteStatement(String sqlDeleteStatement) {
		this.sqlDeleteStatement = sqlDeleteStatement;
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