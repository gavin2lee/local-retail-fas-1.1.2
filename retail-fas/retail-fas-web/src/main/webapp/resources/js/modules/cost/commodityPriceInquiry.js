
function CommodityPriceInquiryDialog() { 
	var $this = this;
}

function buildSubgridUrl(row){
	return "?itemNo="+row.itemNo+"&effectiveDate="+$("#effectiveDateEnd").datebox('getValue')+"&headquarterCost="+row.headquarterCost;
}


var dialog = null;
$(function() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1);
	var  day = new Date(year,month,0);
	$('#effectiveDateEnd').datebox('setValue', year + '-' + month + '-' + day.getDate());
	$.fas.extend(CommodityPriceInquiryDialog, FasDialogController);
	dialog = new CommodityPriceInquiryDialog();
	dialog.init("/commodity_price_inquiry", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "商品价格综合导出",
		exportUrl : "/do_exports",
		exportType : "common"
	});
	
	
});
