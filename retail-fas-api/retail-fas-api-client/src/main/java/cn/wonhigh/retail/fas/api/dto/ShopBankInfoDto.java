package cn.wonhigh.retail.fas.api.dto;

import java.io.Serializable;
import java.util.List;

import cn.wonhigh.retail.fas.api.model.ShopBankAccountModel;

public class ShopBankInfoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2810576819937943385L;

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
