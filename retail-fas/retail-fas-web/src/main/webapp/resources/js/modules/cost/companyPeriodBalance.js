function CompanyPeriodBalanceDialog() { 
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
			var url = BasePath + '/company_period_balance/generate_ower_guest.json';
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

function showDialog(){
	ygDialog({
		title : '对账差异生成条件设置',
		target : $('#generateDiffFormPanel'),
		width :  320,
		height : 320,
		buttons : [{
			text : '确认',
			iconCls : 'icon-save',
			handler : function(dialog) {
				var fromObj = $('#generateDiffForm');
	    		var validateForm = fromObj.form('validate');
	    		if (validateForm == false) {
	    			return;
	    		}
	    		$.messager.progress({
    				title:'请稍后',
    				msg:'正在生成对账差异...',
    				interval:1000
    			}); 
	    		fromObj.form('submit',{
    				url : BasePath + '/print_balance/export_balance_diff_data',
    				success:function(data){
    					if(data && data.indexOf('"success":true')!=-1){
    						if(JSON.parse(result).errorMessage){
    							showError(JSON.parse(result).errorMessage);
    						}
    						showSuc('操作成功');
    					}else{
    						showError('系统异常！');
    					}
    					$.messager.progress('close');
    				}
	    		});
	    		dialog.close();
	    		var interval = setInterval(function(){
	    			ajaxRequestAsync(BasePath + '/print_balance/getExportFlag',null,function(data){
	    				console.info(JSON.stringify(data));
	    				if(data && data[currentUser.userid] && data[currentUser.userid] == 'true'){
	    					$.messager.progress('close');
	    					window.clearInterval(interval);
	    				}
	    			});
	    		},3000);
	    		setTimeout(function(){
	    			if(interval){
	    				window.clearInterval(interval);
	    				$.messager.progress('close');
	    			}
	    		},300000); 
	    		
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
};

var dialog = null;
$(function() {
	$.fas.extend(CompanyPeriodBalanceDialog, FasDialogController);
	dialog = new CompanyPeriodBalanceDialog();
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
	$("#monthCondition,#monthDiffCondition").initCombox({
		data:dialog.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		editable:false,
		required:true,
		value: currentMonth
	});
	$('#yearCondition,#yearDiffCondition').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 160,
		editable:false,
		required:true,
		value: currentYear
	});
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
});
