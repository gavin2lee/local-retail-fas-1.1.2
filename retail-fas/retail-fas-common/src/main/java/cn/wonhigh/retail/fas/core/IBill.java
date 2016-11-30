package cn.wonhigh.retail.fas.core;

public interface IBill {

	public abstract String getBillNo();

	public abstract void setBillNo(String billNo);

	public abstract Integer getBizType();

	public abstract void setBizType(Integer bizType);

	public abstract Integer getBillType();

	public abstract void setBillType(Integer billType);

	public abstract Integer getStatus();

	public abstract void setStatus(Integer status);

}