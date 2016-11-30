function PeriodBalanceDialog() { 
	var $this = this;
	
	this.monthData = [{"value":"1","text":"1"},{"value":"2","text":"2"},
	                  {"value":"3","text":"3"},{"value":"4","text":"4"},
	                  {"value":"5","text":"5"},{"value":"6","text":"6"},
	                  {"value":"7","text":"7"},{"value":"8","text":"8"},
	                  {"value":"9","text":"9"},{"value":"10","text":"10"},
	                  {"value":"11","text":"11"},{"value":"12","text":"12"}];
	
	this.generateOwerGuest = function() {
		//组装请求参数
		var valid = $("#searchForm").form('validate');
		if(valid == false){
			return false;
		}
        
        $.messager.confirm("确认", "确认要生成公司的累计欠客?", function (r) {  
	        if (r) {  
	        	saveFn(); 
	        }else{
	        	return false;
	        }
		});
		
		// 3.保存
		var saveFn = function() {
			showProcess(true, '正在生成欠客销售数据，请稍后......');
			var url = BasePath + '/period_balance/generate_ower_guest.json';
			$("#searchForm").form('submit', {
				url : url,
				dataType : 'json',
				success : function(result) {
					if(result && JSON.parse(result).success){
						 showProcess(false);
						 showSuc('正在生成累计欠客，请稍后查看结果!');
						 return;
					 } else if(result){
						 showProcess(false);
						 showError(JSON.parse(result).message);
					 }
				},
				error : function() {
					showProcess(false);
					showError('生成累计欠客失败,请联系管理员!');
				}
			});
		};
	};
	
}

function selectChange(id) {
	$("#searchForm").find(':checkbox').each(function () {
        if(this.id != id ){
        	$(this).attr("checked",false);
        }
	});
}

var dialog = null;
$(function() {
	$.fas.extend(PeriodBalanceDialog, FasDialogController);
	dialog = new PeriodBalanceDialog();
	dialog.init("/company_period_balance", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/company_period_balance.json",
		searchFormId : "searchForm",
		exportTitle : "期间结存导出",
		exportUrl : "/do_fas_export",
		exportType : "common"
	});
	
	var currentDate = new Date();
	var currentYear = currentDate.getFullYear();
	var currentMonth = currentDate.getMonth() + 1;
	$("#monthCondition").initCombox({
		data:dialog.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		editable:false,
		required:true,
		value: currentMonth
	});
	$('#yearCondition').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 130,
		editable:false,
		required:true,
		value: currentYear
	});
	
});
