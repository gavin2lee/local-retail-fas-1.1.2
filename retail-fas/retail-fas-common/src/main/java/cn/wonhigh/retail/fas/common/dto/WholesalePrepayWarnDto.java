package cn.wonhigh.retail.fas.common.dto;

import cn.wonhigh.retail.fas.common.enums.YesNoEnum;
import cn.wonhigh.retail.fas.common.model.WholesalePrepayWarn;

public class WholesalePrepayWarnDto extends WholesalePrepayWarn {

	private static final long serialVersionUID = -8751312016946234737L;

	private String marginFullText;
	
	private String preOrderFullText;
	
	private String preSendOutFullText;

	public String getMarginFullText() {
		for(YesNoEnum s : YesNoEnum.values()) {
			if(getMarginFull() != null && 
					s.getValue() == getMarginFull().intValue()) {
				return s.getText();
			}
		}
		return marginFullText;
	}

	public void setMarginFullText(String marginFullText) {
		this.marginFullText = marginFullText;
	}

	public String getPreOrderFullText() {
		for(YesNoEnum s : YesNoEnum.values()) {
			if(getPreOrderFull() != null && 
					s.getValue() == getPreOrderFull().intValue()) {
				return s.getText();
			}
		}
		return preOrderFullText;
	}

	public void setPreOrderFullText(String preOrderFullText) {
		this.preOrderFullText = preOrderFullText;
	}

	public String getPreSendOutFullText() {
		for(YesNoEnum s : YesNoEnum.values()) {
			if(getPreSendOutFull() != null && 
					s.getValue() == getPreSendOutFull().intValue()) {
				return s.getText();
			}
		}
		return preSendOutFullText;
	}

	public void setPreSendOutFullText(String preSendOutFullText) {
		this.preSendOutFullText = preSendOutFullText;
	}
}
