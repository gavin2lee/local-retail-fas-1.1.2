package cn.wonhigh.retail.fas.common.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 * 字典表
 * @author wei.hj
 * @date 2013-07-19
 * @version 0.1.0
 * @copyright yougou.com
 *
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.NONE)
public class LookupDtl implements Serializable  {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4616043461769020830L;
	private String systemid; // 系统ID
    private String lookupcode;// 字典分类编号
    private String itemvalue;// 字典编号
    private String itemname;// 字典名称
    public String getSystemid() {
		return systemid;
		
	}
    
    /**
     * 本部
     */
    private String organTypeNo;
    
	public String getOrganTypeNo() {
		return organTypeNo;
	}
	public void setOrganTypeNo(String organTypeNo) {
		this.organTypeNo = organTypeNo;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getLookupcode() {
		return lookupcode;
	}
	public void setLookupcode(String lookupcode) {
		this.lookupcode = lookupcode;
	}
	public String getItemvalue() {
		return itemvalue;
	}
	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
		
	}
	public String getItemnamedetail() {
		return itemvalue+"→"+itemname;
	}
	public void setItemnamedetail(String itemnamedetail) {
	}
	
	
    
    
    
}
