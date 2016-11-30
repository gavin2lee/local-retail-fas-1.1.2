function ShopBalanceDiffSearchDialog() { 
	var $this = this;

	this.balanceFlagformatter = function(value) {
		var balanceFlag_status = [ {"value" : "0","text" : "未完成"}, {"value" : "1","text" : "已完成"} ];
		for ( var i = 0; i < balanceFlag_status.length; i++) {
			if (balanceFlag_status[i].value == value) {
				return balanceFlag_status[i].text;
			}
		}
	};
	
	this.generateType = function(value, rowData, rowIndex) {
	    var generateType = [{'value':'0', 'text': '系统生成'}, {'value':'1', 'text':'手工新增'}];
	    for(var i = 0; i < generateType.length; i++) {
	        if(generateType[i].value == value) {
	            return generateType[i].text;
	        }
	    }
	    return "";
	};
}

var dialog = null;
$(function() {
	$.fas.extend(ShopBalanceDiffSearchDialog, FasDialogController);
	dialog = new ShopBalanceDiffSearchDialog();
	dialog.init("/bill_shop_balance_diff", {
		dataGridId : "searchBillDiffdataGrid",
		searchBtn : "btn-search",
		searchUrl : "/querylist.json",
		searchFormId : "searchForm",
		exportTitle : "结算差异导出",
		exportUrl : "/do_fas_export?billStatus=all"
	});
	
});