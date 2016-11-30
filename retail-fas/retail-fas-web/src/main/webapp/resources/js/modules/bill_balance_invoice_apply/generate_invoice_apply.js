function GenerateInvoiceApplyDialog() { 
	var $this = this;

	this.balanceTypeData = [{'value' : '10','text' : '地区门店结算'},
	                        {'value' : '5','text' : '地区间结算'},{'value' : '11','text' : '地区其他出库结算'},
	                        {'value' : '7','text' : '地区批发结算'}];
	
	this.billTypeData = [
	                     	{'value' : '1','text' : '索赔单'},  //8 : 差异索赔单 10: 盘差索赔单
							{'value' : '2','text' : '报废单'},
							{'value' : '3','text' : '客残销售单'},
							{'value' : '5','text' : '团购出库单'},
							{'value' : '6','text' : '团购退货单'},
							{'value' : '7','text' : '领用出库-材料'},
	                     	{'value' : '4','text' : 'POS内购单'}
	                    ];
	
	this.initBalanceInvoiceTypeCombo = function() {
		$('#balanceType').combobox({
			data : this.balanceTypeData,
			valueField : 'value',
			textField : 'text',
			editable : false
		});
	};
	
	this.generateBalanceInvoice= function() {
		var checkedRows = $("#dataGridDiv").datagrid("getChecked");
	    if(checkedRows.length < 1){
	        showWarn("请选择需要生成开票申请的记录!");
	        return;
	    }
	    // 校验数据
	    var validResult = true;
	    var validUrl = BasePath + '/bill_invoice_apply_generate/check_balance_amount';
	    var effectRow = new Object();
	    effectRow["checkedRows"] = JSON.stringify(checkedRows);
	    
	    ajaxRequestAsync(validUrl, effectRow, function(data){
		       if(data && !data.success){
		    	   showWarn(data.message);
		    	   validResult = false;
		       	 }
	    });
	    
	    if(!validResult){
	    	return;
	    }
	    var url = BasePath + '/bill_invoice_apply_generate/save_balance_invoice';
	    $.messager.confirm("确认","你确定要对条这些数据生成开票申请？", function(r) {
	        if(r) {
                ajaxRequestAsync(url, effectRow, function(result){
                	if(result.success){
						showSuc('生成成功');
					}else{
						showError(result.error);
					}
                });
                $("#btn-search").click();
	        }
	    });
	};
	
	this.initBillInvoiceTypeCombo = function(){
		$('#billBalanceType').combobox({
			data : this.billTypeData,
			valueField : 'value',
			textField : 'text',
			editable : false
		});
	};
	
	this.billSearch = function(){
		var reqParam = $("#billSearchForm").form("getData");
        //组装请求参数
        $("#billDataGridDiv").datagrid('options').queryParams = reqParam;
        var queryMxURL = BasePath + '/bill_invoice_apply_generate/query_bill.json';
        $("#billDataGridDiv").datagrid('options').url = queryMxURL;
        $("#billDataGridDiv").datagrid('load');
	};
	
	this.billClear = function(){
		$("#billSearchForm").form("clear");
	};
	
	this.generateBillInvoice= function() {
		var checkedRows = $("#billDataGridDiv").datagrid("getChecked");
	    if(checkedRows.length < 1){
	        showWarn("请选择需要生成开票申请的记录!");
	        return;
	    }
	    // 校验数据
	    var url = BasePath + '/bill_invoice_apply_generate/save_bill_invoice';
	    $.messager.confirm("确认","你确定要对条这些数据生成开票申请？", function(r) {
	        if(r) {
	        	var effectRow = new Object();
                effectRow["deleted"] = JSON.stringify(deleteList);
                ajaxRequest(url, effectRow, function(result){
                    options.delCallback(options, result);
                });
	        }
	    });
	};
	
}

var dialog = null;

$(function() {
	$.fas.extend(GenerateInvoiceApplyDialog, FasDialogController);
	dialog = new GenerateInvoiceApplyDialog();
	dialog.init("/bill_invoice_apply_generate", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm"
	});
	
	dialog.initBalanceInvoiceTypeCombo();
	
	$('#mainTab').tabs('add', {
		title : '单据开票申请',
		selected : false,
		closable : false,
		href : BasePath + "/bill_invoice_apply_generate/bill_invoice_apply",
		onLoad : function(panel) {
			dialog.initBillInvoiceTypeCombo();
		}
	});
});
