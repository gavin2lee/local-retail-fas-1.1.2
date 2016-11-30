package cn.wonhigh.retail.fas.api.model;

import java.util.List;

public class ShopBankInfoDtlModel {
	
	private List<ShopBankAccountModel> depositAccountList;
	
	private List<ShopBankAccountModel> terminalAccountList;

	public List<ShopBankAccountModel> getDepositAccountList() {
		return depositAccountList;
	}

	public void setDepositAccountList(List<ShopBankAccountModel> depositAccountList) {
		this.depositAccountList = depositAccountList;
	}

	public List<ShopBankAccountModel> getTerminalAccountList() {
		return terminalAccountList;
	}

	public void setTerminalAccountList(List<ShopBankAccountModel> terminalAccountList) {
		this.terminalAccountList = terminalAccountList;
	}

}
