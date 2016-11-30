package cn.wonhigh.retail.fas.common.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * @系统用户查询
 * @author wei.hj
 * @Date 2013-7-29
 * @version 0.1.0
 * @copyright yougou.com
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SystemUser implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2888177309270602559L;

	private Long userid; // 主键ID

    private String username; // 中文姓名  管理员

    private String sex;

    private String loginName; // 登陆名称  admin 

    private String loginPassword; //  密码

    private String mobilePhone;

    private String telPhone;

    private String email;

    private String qqNum;

    private String msnNum;

    private String state;

    private String category;

    private String no;

    private String userlevel;

    private String organizName;
    
    private String driverName;

    // BigDecimal============================================
    private String organizNo; 

    private String supplierCode;

    private String warehouseCode;

    private String userGroupId;

    private Date pwdUpdateTime;

    private String giftCardPermission;
    
    private String storeNo;
    
    //tms系统新加的所属调度中心
    private String quartzcenterNo;
    
    
    //机构类型  50-调度中心    51-配送点  
    private String storeType;
    
    //配送点编号
    private String transportPointNo;
    
    public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public String getMsnNum() {
        return msnNum;
    }

    public void setMsnNum(String msnNum) {
        this.msnNum = msnNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    public String getOrganizName() {
        return organizName;
    }

    public void setOrganizName(String organizName) {
        this.organizName = organizName;
    }


    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Date getPwdUpdateTime() {
        return pwdUpdateTime;
    }

    public void setPwdUpdateTime(Date pwdUpdateTime) {
        this.pwdUpdateTime = pwdUpdateTime;
    }

    public String getGiftCardPermission() {
        return giftCardPermission;
    }

    public void setGiftCardPermission(String giftCardPermission) {
        this.giftCardPermission = giftCardPermission;
    }

	public String getQuartzcenterNo() {
		return quartzcenterNo;
	}

	public void setQuartzcenterNo(String quartzcenterNo) {
		this.quartzcenterNo = quartzcenterNo;
	}

	public String getOrganizNo() {
		return organizNo;
	}

	public void setOrganizNo(String organizNo) {
		this.organizNo = organizNo;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getTransportPointNo() {
		return transportPointNo;
	}

	public void setTransportPointNo(String transportPointNo) {
		this.transportPointNo = transportPointNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
    
    
}