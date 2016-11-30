
var innerBuyDetail = new Object();

//查询
innerBuyDetail.search = function() {
	if($('#searchForm').form('validate')) {
		var reqParam = $("#searchForm" ).form("getData");
		var categoryNoStr = $("#categoryNoId").combobox("getValues");
		if(categoryNoStr){
			reqParam.categoryNo = categoryNoStr.join();
		}
		var tab = $('#mainTab').tabs('getSelected');   
		var index = $('#mainTab').tabs('getTabIndex',tab);
		if (1 == index) {
			$("#posDtlDataGrid").datagrid('options').queryParams = reqParam;
		    $("#posDtlDataGrid").datagrid('options').url = BasePath + "/inner_buy_detail/pos_detail.json";
		    $("#posDtlDataGrid").datagrid('load');
		} else if (0 == index) {
	    	$("#gmsDtlDataGrid").datagrid('options').queryParams = reqParam;
		    $("#gmsDtlDataGrid").datagrid('options').url = BasePath + "/inner_buy_detail/gms_detail.json";
		    $("#gmsDtlDataGrid").datagrid('load');
		}
	}
};

//清空
innerBuyDetail.clear = function() {
	$('#searchForm').form("clear");
	$("#companyNoId").val("");
	$("#organNoId").val("");
	$("#brandNoId").val("");;
};

var _fasController = null;
//导出
innerBuyDetail.exportExcel = function() {
	var reqParam = $("#searchForm" ).form("getData");
	var categoryNoStr = $("#categoryNoId").combobox("getValues");
	if(categoryNoStr){
		reqParam.categoryNo = categoryNoStr.join();
	}
	var tab = $('#mainTab').tabs('getSelected');   
	var index = $('#mainTab').tabs('getTabIndex',tab);
	if (1 == index) {
		$.fas.exportExcel({
			dataGridId : "posDtlDataGrid",
			exportUrl : "/inner_buy_detail/do_fas_export?exportType=1",
			exportTitle : "POS内购明细",
		});
	}else{
		$.fas.exportExcel({
			dataGridId : "gmsDtlDataGrid",
			exportUrl : "/inner_buy_detail/do_fas_export?exportType=2",
			exportTitle : "GMS内购明细",
		});
	}
};

//财务确认
innerBuyDetail.financeConfirm = function() {
	var tab = $('#mainTab').tabs('getSelected');   
	var index = $('#mainTab').tabs('getTabIndex',tab);
	var checkedRows = [];
	if (1 == index) {
		checkedRows = $("#posDtlDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	}else if(0 == index){
		checkedRows = $("#gmsDtlDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	}
	
	if(checkedRows.length > 0){
		for(var i = 0; i < checkedRows.length; i++) {
			var item = checkedRows[i];
			if(item.financeConfirmFlag == 1){
				showWarn(item.orderNo+"单据已经确认,不能再次确认!");
				return false;
			}
		}
		
		var params = {datas : JSON.stringify(checkedRows)};
		ajaxRequest(BasePath + "/inner_buy_detail/finance_confirm", params, function(result){
	    	if(result) {
	            showSuc("财务确认成功!");
	            innerBuyDetail.search();
	        } else {
	            showError("财务确认失败,请联系管理员!");
	        }
	    });
	}else{
		howWarn("请选择需要确认的记录!");
	}
};

//财务反确认
innerBuyDetail.financeAntiConfirm = function() {
	var tab = $('#mainTab').tabs('getSelected');   
	var index = $('#mainTab').tabs('getTabIndex',tab);
	var checkedRows = [];
	if (1 == index) {
		checkedRows = $("#posDtlDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	}else if(0 == index){
		checkedRows = $("#gmsDtlDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	}
    
    if(checkedRows.length > 0){
    	for(var i = 0; i < checkedRows.length; i++) {
    		var item = checkedRows[i];
    		if(item.orderNo){
	    		if(item.financeConfirmFlag == 0 || item.financeConfirmFlag == null){
	    			showWarn(item.orderNo+"单据未确认,不能反确认!");
	    			return false;
	    		}
	    		if(item.invoiceNo != '' && item.invoiceNo){
	    			showWarn(item.orderNo+"已开票，不能反确认!");
	    			return false;
	    		}
    		}
    	}
    	
 	    var params = {datas : JSON.stringify(checkedRows)};
	    ajaxRequest(BasePath + "/inner_buy_detail/finance_anti_confirm", params, function(result){
	    	if(result) {
	            showSuc("财务反确认成功!");
	            innerBuyDetail.search();
	        } else {
	            showError("财务反确认失败,请联系管理员!");
	        }
	    });
    }else{
    	showWarn("请选择需要反确认的记录!");
    }
};


// 初始化
$(function(){
//	$.fas.extend(FasController, FasController);
//	_fasController = new FasController();
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	$("#startDate").datebox("setValue", strDate + "-1"); 
	strDate += "-" + currTime.getDate();
	$("#endDate").datebox("setValue", strDate); 
	
	$('#confirmStatus').combobox({
		width:120,
		data : confirmStatus,
		valueField : 'value',
		textField : 'text',
		editable : false,
	});  
	$('#mainTab').tabs('add', {
		title : 'POS内购明细',
		selected : true,
		closable : false,
		href : BasePath + "/inner_buy_detail/to_pos_detail",
		onLoad : function(panel) {
			//加载预警列表
			setTimeout(loadWarnMessageList(),3000);
		}
	});
	
});

function loadWarnMessageList(){
	var warnPostUrl = $("#warnPostUrl").val();
	var detailType = $("#detailType").val();
	var dataGridId = null;
	if(warnPostUrl != null && warnPostUrl != ''){
		warnPostUrl = warnPostUrl.replaceAll(":", "&");
		if (1 == detailType) {
			dataGridId = "posDtlDataGrid";
			
		} else if (0 == detailType) {
			dataGridId = "gmsDtlDataGrid";
		}
		if($("#"+dataGridId).length>0){
			$('#mainTab').tabs('select', parseInt(detailType));
		    $("#"+dataGridId).datagrid('options').url = BasePath + warnPostUrl;
		    $("#"+dataGridId).datagrid('load');
		}else{
			setTimeout("loadWarnMessageList()", 3000);
		}
	}
}

innerBuyDetail.formatterDate = function(value){
	return new Date(value).format("yyyy-MM-dd");
}

var confirmStatus =[{'value':'', 'text': '全部'},{'value':'1', 'text': '已确认'},{'value':'0', 'text':'末确认'}];

innerBuyDetail.confirmStatus= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < confirmStatus.length; i++) {
        if (confirmStatus[i].value == value) {
            return confirmStatus[i].text;
        }
    }
};

