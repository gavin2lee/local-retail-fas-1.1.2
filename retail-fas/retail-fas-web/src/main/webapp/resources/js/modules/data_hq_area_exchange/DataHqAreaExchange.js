var dataHqArea={};

$(function() {
	dataHqArea.addMainTab();
	$('#sendDateStart').datebox('setValue', getStartDate());
	$('#sendDateEnd').datebox('setValue', getEndDate());
});

dataHqArea.addMainTab = function() {
	$('#mainTab').tabs('add',
		{
			title : '调货入库单',
			selected : false,
			closable : false,
			href : BasePath + '/data_hq_area_exchange/list_tabMain.htm?',
			onLoad : function(panel) {
				$('#sendDateStartId').datebox('setValue', getStartDate());
				$('#sendDateEndId').datebox('setValue', getEndDate());
			}
		}
	);
};

dataHqArea.search = function() {
	if(!$('#querySearchForm').form('validate')) {
		return;
	}
	var params = $('#querySearchForm').form('getData');
	var url = BasePath + '/data_hq_area_exchange/selectTransferData';
    $('#queryDataGridDiv').datagrid('options').queryParams= params;
    $('#queryDataGridDiv').datagrid('options').url= url;
    $('#queryDataGridDiv').datagrid('load');
};

dataHqArea.clear = function() {
	$.fas.clear("querySearchForm");
	$('#sendDateStart').datebox('setValue', getStartDate());
	$('#sendDateEnd').datebox('setValue', getEndDate());
};

dataHqArea.searchEntry = function() {
	if(!$('#searchForm').form('validate')) {
		return;
	}
	var params = $('#searchForm').form('getData');
	var url = BasePath + '/data_hq_area_exchange/selectEntryData';
    $('#mainDataGrid').datagrid('options').queryParams= params;
    $('#mainDataGrid').datagrid('options').url= url;
    $('#mainDataGrid').datagrid('load');
};

dataHqArea.clearEntry = function() {
	$.fas.clear("searchForm");
	$('#sendDateStartId').datebox('setValue', getStartDate());
	$('#sendDateEndId').datebox('setValue', getEndDate());
};

dataHqArea.showSettlePath = function() {
	var checkedRows = $('#queryDataGridDiv').datagrid('getChecked');
	if(checkedRows.length > 0){
		ygDialog({
			title : '转换类型选择',
			width : 400,
    		height : 150,
    		target : $('#billTypeChoosePanel'),
			buttons: [{
				id:'sure',
				iconCls:"icon-save",
	            text: '确认',
	            handler: function(dialog) {
	            	$.messager.progress({
	        			title:'请稍后',
	        			msg:'正在处理中...'
	        		});
	            	$.ajax({
	          		  type: 'POST',
	          		  url: BasePath + "/data_hq_area_exchange/changeBillType",
	          		  data: {checkedRows:JSON.stringify(checkedRows),changeBillType:$('#billTypeChooseForm').form('getData').changeBillType},
	          		  cache: true,
	          		  //async:false,
	          		  dataType: 'json', 
	          		  success: function(data){
	          			  if(data.isSuccess) {
	          				  showInfo(data.message);
	          			  }
	          			  else {
	          				  showError(data.message);
	          			  }
	          			  $.messager.progress('close');
	          			  $('#queryDataGridDiv').datagrid('load');
	          		  }
	            	});
	            	dialog.close();
	            }
	        },
	        {
	        	iconCls:"icon-cancel",
	        	text: '取消',
	            handler: function(dialog) {
	                dialog.close();
	            }
	        }],
			onLoad : function(win, content) {
			}
		});
	}
	else{
		showInfo("请选中需要调整的记录！");
	}
};