package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.wonhigh.retail.fas.common.annotation.excel.ExcelCell;
import cn.wonhigh.retail.fas.common.exportformat.AbstractExportFormat;
import cn.wonhigh.retail.fas.common.exportformat.ExportFormat;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateDeserialize$19;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$10;
import cn.wonhigh.retail.fas.common.utils.JsonDateSerializer$19;

/**
 * 请写出类的用途 
 * @author wangm
 * @date  2015-01-23 15:42:05
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
public class ShopGroup implements Serializable,SequenceId {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1055273320225154375L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 店铺分组编号
     */
    private String shopGroupNo;

    /**
     * 店铺分组名称
     */
	@ExcelCell("A")
    private String shopGroupName;

    /**
     * 公司编码
     */
	@ExcelCell("B")
    private String companyNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 生效日
     */
    @JsonSerialize(using = JsonDateSerializer$10.class)  
	@JsonDeserialize(using = JsonDateDeserialize$10.class) 
    private Date valueDate;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateSerializer$19.class)  
	@JsonDeserialize(using = JsonDateDeserialize$19.class) 
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0-停用;1-启用
     */
    private Byte status;

    /**
     * 发票模板编号
     */
    @ExcelCell("E")
    private String templateNo;
    
    /**
     * 发票模板名称
     */
    private String templateName;
    
    /**
     * 客户编号
     */
    @ExcelCell("C")
    private String clientNo;
    
    /**
     * 客户名称
     */
    private String clientName;
    
    @ExcelCell("D")
    private String clientTypeStr;
    
    /**
     * 开票名称
     */
    private String invoiceName;

    /**
     * 查询精灵
     */
    private String searchCode;

    /**
     * 查询精灵
     */
    private String searchName;
    /**
     * 店铺编号
     */
    @ExcelCell("F")
    private String shopNo;

    /**
     * 店铺名称
     */
    private String shopName;
    
	private String statusStr;
	
    public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

    /**
     * 
     * {@linkplain #shopNo}
     *
     * @return the value of shop_group_dtl.shop_no
     */
    public String getShopNo() {
        return shopNo;
    }

    /**
     * 
     * {@linkplain #shopNo}
     * @param shopNo the value for shop_group_dtl.shop_no
     */
    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * 
     * {@linkplain #shopName}
     *
     * @return the value of shop_group_dtl.shop_name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * {@linkplain #shopName}
     * @param shopName the value for shop_group_dtl.shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getTemplateNo() {
		return templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientTypeStr() {
		return clientTypeStr;
	}

	public void setClientTypeStr(String clientTypeStr) {
		this.clientTypeStr = clientTypeStr;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	/**
     * 
     * {@linkplain #id}
     *
     * @return the value of shop_group.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for shop_group.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #shopGroupNo}
     *
     * @return the value of shop_group.shop_group_no
     */
    public String getShopGroupNo() {
        return shopGroupNo;
    }

    /**
     * 
     * {@linkplain #shopGroupNo}
     * @param shopGroupNo the value for shop_group.shop_group_no
     */
    public void setShopGroupNo(String shopGroupNo) {
        this.shopGroupNo = shopGroupNo;
    }

    /**
     * 
     * {@linkplain #shopGroupName}
     *
     * @return the value of shop_group.shop_group_name
     */
    public String getShopGroupName() {
        return shopGroupName;
    }

    /**
     * 
     * {@linkplain #shopGroupName}
     * @param shopGroupName the value for shop_group.shop_group_name
     */
    public void setShopGroupName(String shopGroupName) {
        this.shopGroupName = shopGroupName;
    }

    /**
     * 
     * {@linkplain #companyNo}
     *
     * @return the value of shop_group.company_no
     */
    public String getCompanyNo() {
        return companyNo;
    }

    /**
     * 
     * {@linkplain #companyNo}
     * @param companyNo the value for shop_group.company_no
     */
    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    /**
     * 
     * {@linkplain #companyName}
     *
     * @return the value of shop_group.company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * {@linkplain #companyName}
     * @param companyName the value for shop_group.company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * {@linkplain #valueDate}
     *
     * @return the value of shop_group.value_date
     */
    public Date getValueDate() {
        return valueDate;
    }

    /**
     * 
     * {@linkplain #valueDate}
     * @param valueDate the value for shop_group.value_date
     */
    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    /**
     * 
     * {@linkplain #createTime}
     *
     * @return the value of shop_group.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * {@linkplain #createTime}
     * @param createTime the value for shop_group.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * {@linkplain #createUser}
     *
     * @return the value of shop_group.create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 
     * {@linkplain #createUser}
     * @param createUser the value for shop_group.create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * {@linkplain #remark}
     *
     * @return the value of shop_group.remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * {@linkplain #remark}
     * @param remark the value for shop_group.remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     * {@linkplain #status}
     *
     * @return the value of shop_group.status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 
     * {@linkplain #status}
     * @param status the value for shop_group.status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}