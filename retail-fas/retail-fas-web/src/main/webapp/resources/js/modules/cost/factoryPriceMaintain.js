
function PurchasePriceDialog() { 
	var $this = this;
	this.statusformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':0, 'text': '未生效'}, {'value':99, 'text': '禁用'},{'value':100, 'text':'生效'}];
	    for(var i = 0; i < dataStatusType.length; i++) {
	        if(dataStatusType[i].value == value) {
	            return dataStatusType[i].text;
	        }
	    }
	    return "";
	};
}

var dialog = null;
$(function() {
	$.fas.extend(PurchasePriceDialog, FasDialogController);
	dialog = new PurchasePriceDialog();
	dialog.init("/factory_price_maintain", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "采购价导出",
		exportUrl : "/do_exports",
		exportType : "common"
	});
	
});
