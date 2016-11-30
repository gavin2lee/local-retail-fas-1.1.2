function BillDtlDialog() { 
	var $this = this;
	
	this.bizTypeData = [{"value":"21","text":"批发销售"},{"value":"22","text":"过季退货"},{"value":"29","text":"批发销售(店出)"},{"value":"30","text":"客残退货"},{"value":"43","text":"召回退货"}];
	this.unfrozenStatusData = [{"value":"9","text":"冻结"},{"value":"11","text":"解冻"}];
	
}

var dialog = null;

function extendClear(){
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
}

$(function() {
	var isHq = $('#isHq').val();
	$.fas.extend(BillDtlDialog, FasDialogController);
	dialog = new BillDtlDialog();
	dialog.init("/bill_balance_zone", {
		dataGridId : "dataGridDiv",
		searchFormId : "searchForm",
		searchUrl : "/enter_list.json?isHq="+isHq,
		exportUrl : "/export_sale_dtl?isHq="+isHq,
		exportTitle : "地区批发销售明细"
	});
	
	$("#bizType").initCombox({
		data:dialog.bizTypeData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		editable:true,
		required:false
	});
	$("#unfrozenStatus").initCombox({
		data:dialog.unfrozenStatusData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		editable:true,
		required:false
	});
	
});