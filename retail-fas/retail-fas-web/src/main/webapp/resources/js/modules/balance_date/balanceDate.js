var balanceDate = new Object();

//当前用户
balanceDate.currentUser;

// 主表模块路径
balanceDate.modulePath = BasePath + '/balance_date';

//清空
balanceDate.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
};

//查询
balanceDate.search = function() {
	$('#dtlDataGrid').datagrid('acceptChanges');
	var params = $('#searchForm').form('getData');
	var url = balanceDate.modulePath + '/list.json';
    $('#dtlDataGrid').datagrid('options').queryParams= params;
    $('#dtlDataGrid').datagrid('options').url= url;
    $('#dtlDataGrid').datagrid('load');
};


//新增
balanceDate.add = function() {
	if(balanceDate.endEdit()){
	    $('#dtlDataGrid').datagrid('appendRow',{});
	    var length = $('#dtlDataGrid').datagrid('getRows').length;
		$('#dtlDataGrid').datagrid('beginEdit',length-1);
	}
};

//修改
balanceDate.edit = function(rowIndex) {
	if(balanceDate.endEdit()){
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
	}
};

//删除
balanceDate.del = function() {
    var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
    var rowIndex;
    if(checkedRows.length > 0){
        $.each(checkedRows,function(index,row){
        	rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
        	$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
        });
    }else{
    	showWarn("请选中需要删除的记录!");
    }

};


//结束编辑
balanceDate.endEdit = function() {
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length == 0){
		return true;
	}
	var editRowIndex = editTr.attr("datagrid-row-index");
	if($('#dtlDataGrid').datagrid('validateRow',editRowIndex)){
		  $('#dtlDataGrid').datagrid('endEdit',editRowIndex);
		  return true;
	}
	return false;
};

// 校验数据有效性

balanceDate.checkData = function(rows){
	var flag = true;
	$.each(rows,function(index,item){
		if(item.companyNo== ''){// 公司
			flag = false;
			return false;
		}
		if(item.supplierName && item.supplierName!= '' && item.supplierNo ==''){// 供应商
			flag = false;
			return false;
		}
		if(item.customerName && item.customerName!= '' && item.customerNo ==''){// 客户
			flag = false;
			return false;
		}
	});
}
//保存
balanceDate.save = function() {
	if(balanceDate.endEdit()){
	    var url = balanceDate.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var balanceType = $("#balanceType").val();
	    $.each(insertRows,function(index,row){
	    	row.balanceType = balanceType;
	    });
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');
	    		balanceDate.search(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};

//批量填充
balanceDate.doPadding = function() {
	ygDialog({
		title : '批量填充',
		target : $('#myFormPanel'),
		width :  420,
		height : 220,
		buttons : [{
			text : '确认',
			iconCls : 'icon-save',
			handler : function(dialog) {
				balanceDate.paddingRow(dialog);
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

balanceDate.paddingRow = function(dialog) {
	//1.校验必填项
	if (!$('#dataForm').form('validate')) {
		return;
	}
	dialog.close();
	var jsonData = $('#dataForm').form('getData');
	var companyNo = jsonData.companyNo;
	var companyName = jsonData.companyName;
	var arrCompanyNo = companyNo.split(",");
	var arrCompanyName = companyName.split(",");
	var balanceDate = jsonData.balanceDate;
	$.each(arrCompanyNo,function(i,companyStr){
		if(jsonData.supplierNo){
			var arrSupplierNo = jsonData.supplierNo.split(",");
			var arrSupplierName = jsonData.supplierName.split(",");
			$.each(arrSupplierNo,function(j,supplierStr){
				$('#dtlDataGrid').datagrid('insertRow',{
					index: 0,	
					row: {
						companyNo: companyStr,
						companyName: arrCompanyName[i],
						supplierNo: supplierStr,
						supplierName: arrSupplierName[j],
						balanceDate: balanceDate
					}
				});
			});
		}else{
			var arrCustomerNo = jsonData.customerNo.split(",");
			var arrCustomerName = jsonData.customerName.split(",");
			$.each(arrCustomerNo,function(j,customerStr){
				$('#dtlDataGrid').datagrid('insertRow',{
					index: 0,	
					row: {
						companyNo: companyStr,
						companyName: arrCompanyName[i],
						customerNo: customerStr,
						customerName: arrCustomerName[j],
						balanceDate: balanceDate
					}
				});
			});
		}
	});
};

// 初始化
$(function(){
});
