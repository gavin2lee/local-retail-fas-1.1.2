var contractDiscountItem = {};

var currentUser;

contractDiscountItem.modulePath = BasePath + '/contract_discount';

// 页面初始化
contractDiscountItem.initPage = function(){
	contractDiscountItem.initEditor();
}

// 初始化编辑器
contractDiscountItem.initEditor = function(){
	$("#dtlDataGrid").datagrid("addEditor", 
		[
		 {field : "buyerNo", editor : {type:'readonlytext'}},
		 {field : "salerNo", editor : {type:'readonlytext'}},
		 {field : "brandNo", editor : {type:'readonlytext'}},
		 {field : "categoryNo", editor : {type:'readonlytext'}},
		 {field : "brandName", editor : {type:'readonlytext'}},
		 {field : "categoryName", editor : {type:'readonlytext'}},
		 {field : "itemNo", editor : {type:'readonlytext'}},
		 {field : "itemName", editor : {type:'readonlytext'}},
		 {field : "buyerName", editor : {type:'test_combogrid',
				options:{
					type:'company',
					notGroupLeadRole : true,
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'buyerNo'
				}
		 }},
		 {field : "salerName", editor : {type:'test_combogrid',
				options:{
					type:'supplier',
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'salerNo'
				}
		  }},
		  {field : "itemCode", editor : {type:'test_combogrid',
				options:{
					type:'item',
					required:true,
					datagridId:'dtlDataGrid',
					callback:function(row){
						var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
						var edItemNo = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemNo'});
						var edItemName = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemName'});
						var edBrandNo = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'brandNo'});
						var edBrandName = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'brandName'});
						var edCategoryNo = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'categoryNo'});
						var edCategoryName = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'categoryName'});
						edItemNo.target.val(row.id);
						edItemName.target.val(row.name);
						edBrandNo.target.val(row.extendField1);
						edCategoryNo.target.val(row.extendField2);
						ajaxRequestAsync( BasePath + '/brand/get_biz',{brandNo:row.extendField1},function(data){
							if(data && data.length >0){
								edBrandName.target.val(data[0].name);
							}
						});
						ajaxRequestAsync( BasePath + '/category/get_biz',{categoryNo:row.extendField2},function(data){
							if(data && data.length >0){
								edCategoryName.target.val(data[0].name);
							}
						});
						
					}
				}
		 }},
		  {field : "purchasePrice", editor : {type:'numberbox',
				options:{
		  			min:0,
		  			max:999999,
		  			precision:2,
		  			required:true
				}
		  }},
		  {field : "effectiveDate", editor : {type:'datebox',
				options:{
		  			required:true
				}
		  }}
		]	
	);
}

//校验是否只读
contractDiscountItem.checkIsReadonly = function(row){
	var result = false;
	if(row.contractDiscountNo && row.contractDiscountNo!=''){
		ajaxRequestAsync(BasePath + '/contract_discount/checkIsReadonly',row,function(data){
			if(data){
				result = true;
			}
		});
	}
	return result;
}

//新增
contractDiscountItem.add = function() {
	if(contractDiscountItem.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
	}
};
 
//修改
contractDiscountItem.edit = function(rowIndex, rowData) {
	if(contractDiscountItem.endEdit()){
		if(contractDiscountItem.checkIsReadonly(rowData)){
			showError("该折扣已被引用，不允许修改！");
			return;
		}
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
	}
};

//删除
contractDiscountItem.del = function() {
  var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
  if(checkedRows.length > 0){
	  var errMessage = '';
	  $.each(checkedRows,function(index,row){
		  if(contractDiscountItem.checkIsReadonly(row)){
			  errMessage = "勾选的折扣已被引用，不允许修改！"
			  return false;
		  }
	  });
	  if(errMessage !=''){
		  showError(errMessage);
		  return ;
	  }
	  $.each(checkedRows,function(index,row){
		var rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
	  	$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
	  });
  }else{
	  showInfo('请选中需要删除的记录!');  
  }

};

//结束编辑
contractDiscountItem.endEdit = function() {
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

// 保存
contractDiscountItem.save = function(){
	if(contractDiscountItem.endEdit()){
	    var url = contractDiscountItem.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    $.each(insertRows,function(index, item){
	    	item.contractDiscountType = $('#contractDiscountType').val();
	    	item.createUser = currentUser.username;
	    	item.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    $.each(updatedRows,function(index, item){
	    	item.updateUser = currentUser.username;
	    	item.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');
	    		contractDiscountItem.searchData(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};

//查询
contractDiscountItem.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = 'list.json';
	$("#dtlDataGrid").datagrid('load');
};

//清空
contractDiscountItem.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
	$('#contractDiscountType').val('3');
};

$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
    setTimeout(contractDiscountItem.initPage,500);
});

