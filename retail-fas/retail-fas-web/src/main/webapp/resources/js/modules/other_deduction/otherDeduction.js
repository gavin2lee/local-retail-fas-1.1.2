var otherDeduction = new Object();

var currentUser;

otherDeduction.modulePath = BasePath + '/other_deduction';

//查询
otherDeduction.search = function() {
	var isHq = $('#isHq').val();
	var params = $('#searchForm').form('getData');
	var url = otherDeduction.modulePath + '/list.json?isHq='+isHq;
	$('#dtlDataGrid').datagrid('options').queryParams= params;
	$('#dtlDataGrid').datagrid('options').url= url;
	$('#dtlDataGrid').datagrid('load');
};

//清空
otherDeduction.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
};

//新增
otherDeduction.add = function() {
	if(otherDeduction.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {currencyNo: 0}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
	}
};

//修改
otherDeduction.edit = function(rowIndex, rowData) {
	if(rowData.orderNo || rowData.status==1){
		showInfo('不能操作已更新客户余额扣项!');
		return false;
	} 
	if(rowData.balanceNo){
		showInfo('不能操作已结算的扣项!');
		return ;
	}
	if(otherDeduction.endEdit()){
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
	}
};

//删除
otherDeduction.del = function() {
  var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
  var rowIndex;
  var errMessage = '';
  if(checkedRows.length > 0){
	  $.each(checkedRows,function(index,row){
		    if(row.orderNo || row.status==1){
				errMessage = '不能操作已更新客户余额扣项!';
				return false;
			} 
			if(row.balanceNo ){
				errMessage = '不能操作已结算的扣项!';
				return false;
			}  
		  });
		  if(errMessage!=''){
			 showInfo(errMessage); 
			 return ;
		  }
		  $.each(checkedRows,function(index,row){
		  	rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
		  	$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
		  });
  }else{
	  showInfo('请选中需要删除的记录!');  
  }

};


function importCallBack(data){
	if(data.indexOf('"success":true')!=-1){
		var jsonData = JSON.parse(data);
		if(jsonData.success){
			var lstData = jsonData.rows;
			if(lstData.length > 0){
				$('#importDataGrid').datagrid({
					data: lstData,
					rowStyler: function(index,row){
						if (row.pass == 0){
							return 'background-color:#D4E6E7;';
						}
					}
				});
				ygDialog({
					title : '导入结果',
					target : $('#myFormPanel'),
					width :  850,
					height : 'auto',
					buttons : [{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(dialog) {
							dialog.close();
						}
					}]
				});
				otherDeduction.search(); 
				showSuc("操作成功!");
				return ;
			}
		}
	}
	showError("数据不合法,导入失败!");
}

//结束编辑
otherDeduction.endEdit = function() {
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#dtlDataGrid').datagrid('validateRow',editRowIndex)){
			  $('#dtlDataGrid').datagrid('endEdit',editRowIndex);
			  return true;
		}
		return false;
	}
	return true;
};

function getDueDate(item) {
	var dueDate = null;
	ajaxRequestAsync(otherDeduction.modulePath + "/getDueDate.json", item, function(result){
		dueDate = result.dueDate;
	});
	return dueDate;
}

// 保存
otherDeduction.save = function(){
	if(otherDeduction.endEdit()){
	    var url = otherDeduction.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var balanceType = $('#balanceType').val();
	    $.each(insertRows,function(index, item){
	    	item.balanceType = balanceType;
	    	item.createUser = currentUser.username;
	    	item.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    	item.deductionAmount = item.fineAmount;
	    	if(item.balanceType == 16) {
	    		if(item.dueDate == null || item.dueDate.trim() =='') {
		    		item.dueDate = getDueDate(item);
		    	}
	    		item.noPayAmount = item.deductionAmount;
	    	}
	    	if(item.currencyNo == 1){
	    		item.currencyNo = "001";
	    	}
	    });
	    $.each(updatedRows,function(index, item){
	    	item.updateUser = currentUser.username;
	    	item.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    	var returnAmount = 0;
	    	if(item.returnAmount){
	    		returnAmount = item.returnAmount;
	    	}
	    	item.deductionAmount = parseFloat(item.fineAmount) +  parseFloat(returnAmount);
	    	if(item.balanceType == 16) {
		    	if(item.dueDate == null || item.dueDate.trim() =='') {
		    		item.dueDate = getDueDate(item);
		    	}
	    		item.noPayAmount = item.deductionAmount;
	    	}
	    });
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');
	    		otherDeduction.search(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};
// 设置大类名称
otherDeduction.setCategoryName = function(rec) {
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var nameEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : editIndex,
		'field' : 'categoryName'
	});
	nameEd.target.val(rec.name);
};

//巴洛克隐藏列
otherDeduction.operate = function(){
	if(currentUser.organTypeNo.indexOf("U010105")!= -1){
		$('#dtlDataGrid').datagrid('showColumn','targetCurrencyName');
		$('#dtlDataGrid').datagrid('showColumn','conversionFactor');
		$('#dtlDataGrid').datagrid('showColumn','targetCurrencyAmount');
	}
};

//更新客户余额
otherDeduction.distributeDeduction = function(){
	var updatedRows = $('#dtlDataGrid').datagrid('getChecked');
	  var rowIndex;
	  var errMessage = '';
	  if(updatedRows.length > 0){
		  $.each(updatedRows,function(index,row){
				if(row.orderNo || row.status==1){
					errMessage = '不能操作已更新客户余额扣项!';
					return false;
				}  
			  });
			  if(errMessage!=''){
				 showInfo(errMessage); 
				 return ;
			  }	
			  var changeRows = {
					  updated : JSON.stringify(updatedRows)
			  };
			  var url = otherDeduction.modulePath + '/distribute_deduction';
			  ajaxRequestAsync(url, changeRows, function(result){
			    	if(result){
			    		showSuc('分配成功');
			    		otherDeduction.search(); 
			    	}else{
			    		showSuc('分配失败');
			    	}
			    });
	  }else{
		  showInfo('请选中需要分配的记录!');  
	  }
};

// 初始化
$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
});
