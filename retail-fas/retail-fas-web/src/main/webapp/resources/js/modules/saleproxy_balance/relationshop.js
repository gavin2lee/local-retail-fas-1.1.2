var saleproxyBalanceRelationShop = new Object();

var date = new Date();

//当前用户
saleproxyBalanceRelationShop.currentUser;

//当前编辑行
saleproxyBalanceRelationShop.editRowIndex = -1;

// 主表模块路径
saleproxyBalanceRelationShop.modulePath = BasePath + '/saleproxy_balance_relationshop';

// 导出
saleproxyBalanceRelationShop.exportExcel = function() {
	$.fas.exportExcel({
		dataGridId : "dtlDataGrid",
		exportUrl : "/saleproxy_balance_relationshop/do_fas_export",
		exportTitle : "商场门店结算期列表导出"
	});
};

//清空
saleproxyBalanceRelationShop.clear = function() {
	$('#searchForm').find("input").val("");
	$('#searchForm').find("textarea").val("");
	$('input[type=checkbox]').removeAttr('checked');
};

//查询
saleproxyBalanceRelationShop.search = function() {
	var valid = $("#searchForm").form('validate');
	if(valid == false){
		return;
	}
	var params = $('#searchForm').form('getData');
	var url = saleproxyBalanceRelationShop.modulePath + '/list.json';
    $('#dtlDataGrid').datagrid('options').queryParams= params;
    $('#dtlDataGrid').datagrid('options').url= url;
    $('#dtlDataGrid').datagrid('load');
};

//新增
saleproxyBalanceRelationShop.add = function() {
	
	if(saleproxyBalanceRelationShop.endEdit()){
		
		saveToDB();
		
		$('#dtlDataGrid').datagrid('insertRow', {
			index : 0,
			row : {}
		});
	    $('#dtlDataGrid').datagrid('beginEdit', 0);
	    saleproxyBalanceRelationShop.editRowIndex = 0;
	    
//	    initializationDateField(firstDay,lastDay,0); 
		
	}
};

saleproxyBalanceRelationShop.check = function(rowIndex,data){
//	var getStatusValue=data.balanceFlag;
//	if(data.balanceFlag == 0){
//		 showWarn("已生成预估结算单，不能修改！");
//		 return true;
//	 }
//	if(data.balanceFlag == 2){
//		showWarn("结算单已生成,不能修改");
//		return true;
//	}
//	if(data.billalready == 2){
//		showWarn("已开票,不能修改");
//		return true;
//	}
	return false;
};

//修改
saleproxyBalanceRelationShop.edit = function(rowIndex,data) {
	if(saleproxyBalanceRelationShop.endEdit()){
		
		saveToDB();
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
		saleproxyBalanceRelationShop.editRowIndex = rowIndex;
		
		
	}
	var valueStartDate = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : saleproxyBalanceRelationShop.editRowIndex,
		'field' : 'balanceStartDate'
	});
	
	var valueEndDate = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : saleproxyBalanceRelationShop.editRowIndex,
		'field' : 'balanceEndDate'
	});
	
	check(valueStartDate.target.val(),valueEndDate.target.val());
};

//删除
saleproxyBalanceRelationShop.del = function() {
	
	var delFlag = false;
    var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
    var rowIndex;
    var deletedRows = [];
    var _index = 0;
    
    $.messager.confirm("确认","你确定要删除数据", function(r) {
        if(r) {
            
        	if(!saleproxyBalanceRelationShop.endEdit()){
        		return;
        	}
        	
        	$.each(checkedRows,function(index,row){
            	rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
            	if(row.balanceFlag == '0'){
             		 showWarn("已生成预估结算单，不能删除！");
             		 return;
             	 }
            	if(row.balanceFlag == '2'){
              		 showWarn("已生成结算单，不能删除！");
              		 return;
              	 }
            	index++;
            	deletedRows[index] = row;
            	$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
            	if(saleproxyBalanceRelationShop.editRowIndex == rowIndex){
            		saleproxyBalanceRelationShop.editRowIndex =  -1;
            	}
            	
            });
            
            if(deletedRows.length > 0){
            	
            	var url = saleproxyBalanceRelationShop.modulePath + '/validationRepeat';
            	var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
        	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
        	    var changeRows = {
        	    		deleted : JSON.stringify(deletedRows),
        	    		inserted : JSON.stringify(insertRows),
        	    		updated : JSON.stringify(updatedRows)
        	    };

        	    ajaxRequestAsync(url, changeRows, function(result){
        	    	if(typeof result.error == 'undefined' || result.error == null){
        	    		showSuc('删除成功');
        	    	}else{
        	    		alert(result.error);
        	    		showSuc('删除失败');
        	    	}
        	    });
            	
            }
        	
        }
    });
};

//保存
saleproxyBalanceRelationShop.save = function() {
	if(saleproxyBalanceRelationShop.endEdit()){
	    var url = saleproxyBalanceRelationShop.modulePath + '/validationRepeat';
//	    var url_Validation = saleproxyBalanceRelationShop.modulePath + '/validationRepeat';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    
	    if(insertRows.length == 0 && updatedRows.length == 0 && deletedRows.length == 0){
	    	alert("没有需要编辑的数据");
	    	return;
	    }
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };

//	    alert(changeRows);
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(typeof result.error == 'undefined' || result.error == null){
	    		showSuc('保存成功');
	    		saleproxyBalanceRelationShop.search(); 
	    	}else{
	    		alert(result.error);
	    		showSuc('保存失败');
	    		saleproxyBalanceRelationShop.search();
	    	}
	    });
	}
};

function saveToDB(){
	
		saleproxyBalanceRelationShop.endEdit();
	
	 	var url = saleproxyBalanceRelationShop.modulePath + '/validationRepeat';
	 	
	 	var rows = $('#dtlDataGrid').datagrid('getChanges');
	 	if(rows.length < 1){
	 		return;
	 	}
	 	
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
//	    alert(JSON.stringify(changeRows));
	    if(deletedRows.length < 1){
		    if(insertRows.length == 0 && updatedRows.length == 0){
		    	return false;
		    }
	    }
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(typeof result.error == 'undefined' || result.error == null){
	    		showSuc('保存成功');
	    		saleproxyBalanceRelationShop.search();
	    		
	    	}else{
	    		alert(result.error);
	    		showSuc('保存失败');
	    		saleproxyBalanceRelationShop.search();
	    	}
	    });
}


//结束编辑
saleproxyBalanceRelationShop.endEdit = function() {
//	if($('#dtlDataGrid').datagrid('validateRow',saleproxyBalanceRelationShop.editRowIndex)){
//		  $('#dtlDataGrid').datagrid('endEdit',saleproxyBalanceRelationShop.editRowIndex);
//		  return true;
//	}
//	return false;
	var tempObj = $('#dtlDataGrid');
	var rowArr = tempObj.datagrid('getRows');
	tempObj.datagrid("unselectAll");
    for(var i = 0; rowArr && i < rowArr.length; i++) {
    	if(tempObj.datagrid('validateRow', i)) {
    		tempObj.datagrid('endEdit', i);
    	} else {
    		tempObj.datagrid('selectRow', i);
    		return false;
    	}
    }
    return true;
};

// 初始化
$(function(){
	//绑定店铺通用查询
	$("#shopNames_").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNos_").val(value);
        }
    });
});

saleproxyBalanceRelationShop.importOperation = function(){
	$.importExcel.open({
		'submitUrl' : BasePath + '/shop_balance_date/do_import',
		'templateName' : '结算期管理导入.xlsx',
		success : function(result) {
			$.messager.progress('close');
			if (result) {
				if (isNotBlank(result.error)) {
					showError(result.error);
				} else {
					$.importExcel.colse();
					showSuc('数据导入成功');
					saleproxyBalanceRelationShop.search();
				}
			} else {
				showInfo('导入失败,请联系管理员!');
			}
		},
		error : function() {
			$.messager.progress('close');
			showWarn('数据导入失败，请联系管理员');
		}
	});
};

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};