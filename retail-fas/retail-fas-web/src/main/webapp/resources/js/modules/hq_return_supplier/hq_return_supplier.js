
var supplierCollet = new Object();

//查询
supplierCollet.search = function() {
	if($('#searchForm').form('validate')) {
		var reqParam = $("#searchForm" ).form("getData");
		var tab = $('#mainTab').tabs('getSelected');   
		var index = $('#mainTab').tabs('getTabIndex',tab);
		if (1 == index) {
			$("#returnSupplierDetailDataGrid").datagrid('options').queryParams = reqParam;
		    $("#returnSupplierDetailDataGrid").datagrid('options').url = BasePath + "/hq_return_supplier/find_recall_list";
		    $("#returnSupplierDetailDataGrid").datagrid('load');
		} else if (0 == index) {
	    	$("#returnSupplierCelletDataGrid").datagrid('options').queryParams = reqParam;
		    $("#returnSupplierCelletDataGrid").datagrid('options').url = BasePath + "/hq_return_supplier/find_return_list";
		    $("#returnSupplierCelletDataGrid").datagrid('load');
		}
	}
};

//清空
supplierCollet.clear = function() {
	$('#searchForm').form("clear");
//	$("#companyNoId").val("");
//	$("#organNoId").val("");
//	$("#brandNoId").val("");;
};

var _fasController = null;
//导出
supplierCollet.exportExcel = function() {
	var tab = $('#mainTab').tabs('getSelected');   
	var index = $('#mainTab').tabs('getTabIndex',tab);
	if (1 == index) {
		$.fas.exportExcel({
			dataGridId : "returnSupplierDetailDataGrid",
			exportUrl : "/hq_return_supplier/do_fas_export?exportType=2",
			exportTitle : "召回业务",
		});
	}else{
		$.fas.exportExcel({
			dataGridId : "returnSupplierCelletDataGrid",
			exportUrl : "/hq_return_supplier/do_fas_export?exportType=1",
			exportTitle : "退残业务",
		});
	}
};

// 初始化
$(function(){
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	$("#startDate").datebox("setValue", strDate + "-1"); 
	strDate += "-" + currTime.getDate();
	$("#endDate").datebox("setValue", strDate); 
	supplierCollet.billTypeformatter();
	$('#mainTab').tabs('add', {
		title : '召回业务',
		selected : true,
		closable : false,
		href : BasePath + "/hq_return_supplier/to_detail",
		onLoad : function(panel) {
			//加载预警列表
			setTimeout(loadWarnMessageList(),3000);
		}
	});
	
});

function loadWarnMessageList(){
	var warnPostUrl = $("#warnPostUrl").val();
	var detailType = $("#detailType").val();
	var dataGridId;
	if(warnPostUrl != null && warnPostUrl != ''){
		warnPostUrl = warnPostUrl.replaceAll(":", "&");
		if (1 == detailType) {
			dataGridId = "returnSupplierDetailDataGrid";
			
		} else if (0 == detailType) {
			dataGridId = "returnSupplierCelletDataGrid";
		}
		if($("#"+dataGridId).length>0){
			$('#mainTab').tabs('select', parseInt(detailType));
			var tempDataGrid = $("#"+dataGridId);
		    $("#"+dataGridId).datagrid('options').url = BasePath + warnPostUrl;
		    $("#"+dataGridId).datagrid('load');
		}else{
			setTimeout("loadWarnMessageList()", 3000);
		}
	}
}

supplierCollet.formatterDate = function(value){
	return new Date(value).format("yyyy-MM-dd");
}

var confirmStatus =[{'value':'', 'text': '全部'},{'value':'1', 'text': '已确认'},{'value':'0', 'text':'末确认'}];

supplierCollet.confirmStatus= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < confirmStatus.length; i++) {
        if (confirmStatus[i].value == value) {
            return confirmStatus[i].text;
        }
    }
};


var billTypeformatter =[{'value':'1', 'text': '召回出库'},{'value':'2', 'text':'退残'},{'value':'3', 'text':'退厂'},{'value':'4', 'text':'供应商索赔'}];

supplierCollet.billTypeformatter= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < billTypeformatter.length; i++) {
        if (billTypeformatter[i].value == value) {
            return billTypeformatter[i].text;
        }
    }
};

supplierCollet.billTypeformatter=function(){
	$('#businessType').combobox({
		width:130,
		data : billTypeformatter,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};
