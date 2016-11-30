package cn.wonhigh.retail.fas.api.dto;

public class BillBalanceApiVo extends BillBalanceApiDto {

	private static final long serialVersionUID = 632921878292555297L;

	private String zoneNoFrom;
	
	private String zoneNameFrom;

	public String getZoneNoFrom() {
		return zoneNoFrom;
	}

	public void setZoneNoFrom(String zoneNoFrom) {
		this.zoneNoFrom = zoneNoFrom;
	}

	public String getZoneNameFrom() {
		return zoneNameFrom;
	}

	public void setZoneNameFrom(String zoneNameFrom) {
		this.zoneNameFrom = zoneNameFrom;
	}
}
