var contractDiscountSupplier = {};

var currentUser;

contractDiscountSupplier.modulePath = BasePath + '/contract_discount';

// 订货类型
contractDiscountSupplier.orderTypeData = 
			[{value:'1', text:'现货'},
		     {value:'2', text:'期货'}, 
	         {value:'3', text:'期货;现货'}];

//定价依据
contractDiscountSupplier.priceBasisData = 
	[{value:'1', text:'牌价'},
    {value:'2', text:'终端销售金额'}];

//算法(含税单价)
contractDiscountSupplier.algorithmData = 
	[{value:'A', text:'1：牌价*折扣1四舍五入取2位小数保留到分，2：/1.17四舍五入取2位小数保留到分，3：*折扣2*1.17  四舍五入取2位小数保留到分'},
    {value:'B', text:'1：牌价*折扣1四舍五入保留到元，2：/1.17四舍五入取2位小数保留到分，3：*折扣2*1.17  四舍五入取2位小数保留到分'},
    {value:'C', text:'牌价*折扣1*折扣2'}];

//算法(不含税金额)
contractDiscountSupplier.notTaxAlgorithmData = 
	[{value:'A', text:'1：牌价*折扣1四舍五入取2位小数保留到分，2：/1.17 四舍五入取2位小数保留到分，3：*折扣2四舍五入取2位小数保留到分，4： *数量四舍五入取2位小数保留到分'},
    {value:'B', text:'1：牌价*折扣1四舍五入保留到元 ，2：/1.17四舍五入取2位小数保留到分，3：*数量*折扣2  四舍五入取2位小数保留到分'},
    {value:'C', text:'1：牌价*折扣1*折扣2*/1.17*数量四舍五入取2位小数保留到分'}];


//类型描述格式化
contractDiscountSupplier.typeNameFormat = function(value,data){
	for(var i=0;i<data.length;i++){
		if(value == data[i].value){
			return data[i].text;
		}
	}
}

// 页面初始化
contractDiscountSupplier.initPage = function(){
	contractDiscountSupplier.initEditor();
	contractDiscountSupplier.initFormatFunction();
}

//初始化格式化方法
contractDiscountSupplier.initFormatFunction = function(){
	var orderType = $('#dtlDataGrid').datagrid('getColumnOption','orderType');
	var priceBasis = $('#dtlDataGrid').datagrid('getColumnOption','priceBasis');
	var algorithmDesc = $('#dtlDataGrid').datagrid('getColumnOption','algorithmDesc');
	var notTaxAlgorithmDesc = $('#dtlDataGrid').datagrid('getColumnOption','notTaxAlgorithmDesc');
	orderType.formatter = function(value,row,index){
		return contractDiscountSupplier.typeNameFormat(value,contractDiscountSupplier.orderTypeData);
	};
	priceBasis.formatter = function(value,row,index){
		return contractDiscountSupplier.typeNameFormat(value,contractDiscountSupplier.priceBasisData);
	};
	algorithmDesc.formatter = function(value,row,index){
		return '<span title="' + value + '">' + value + '</span>';
	};
	notTaxAlgorithmDesc.formatter = function(value,row,index){
		return '<span title="' + value + '">' + value + '</span>';
	};
}

// 初始化编辑器
contractDiscountSupplier.initEditor = function(){
	$("#dtlDataGrid").datagrid("addEditor", 
		[
		 {field : "buyerNo", editor : {type:'readonlytext'}},
		 {field : "salerNo", editor : {type:'readonlytext'}},
		 {field : "brandNo", editor : {type:'readonlytext'}},
		 {field : "categoryNo", editor : {type:'readonlytext'}},
		 {field : "algorithmDesc", editor : {type:'readonlytext'}},
		 {field : "notTaxAlgorithmDesc", editor : {type:'readonlytext'}},
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
		 {field : "brandName", editor : {type:'test_combogrid',
				options:{
					type:'brand',
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'brandNo'
				}
		 }},
		 {field : "categoryName", editor : {type:'test_combogrid',
				options:{
					type:'category',
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'categoryNo'
				}
		 }},
		 {field : "orderType", editor : {type:'combobox',
				options:{
					data:contractDiscountSupplier.orderTypeData,
					valueField:'value',
					textField:'text',
					required:true
				}
		 }},
		 {field : "priceBasis", editor : {type:'combobox',
				options:{
					data:contractDiscountSupplier.priceBasisData,
					valueField:'value',
					textField:'text',
					required:true
				}
		 }},
		 {field : "algorithm", editor : {type:'combobox',
				options:{
					data:contractDiscountSupplier.algorithmData,
					valueField:'value',
					textField:'value',
					required:true,
					onSelect:function(data){
						var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
						var edAlgorithmDesc = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'algorithmDesc'});
						edAlgorithmDesc.target.val(contractDiscountSupplier.typeNameFormat(data.value,contractDiscountSupplier.algorithmData));
					
					}
				}
		  }},
		  {field : "notTaxAlgorithm", editor : {type:'combobox',
				options:{
					data:contractDiscountSupplier.notTaxAlgorithmData,
					valueField:'value',
					textField:'value',
					onSelect:function(data){
						var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
						var edNotTaxAlgorithmDesc = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'notTaxAlgorithmDesc'});
						edNotTaxAlgorithmDesc.target.val(contractDiscountSupplier.typeNameFormat(data.value,contractDiscountSupplier.notTaxAlgorithmData));
					
					}
				}
		  }},
		  {field : "discount1", editor : {type:'numberbox',
				options:{
					max:1,
		  			min:0,
		  			precision:5,
		  			required:true
				}
		  }},
		  {field : "discount2", editor : {type:'numberbox',
				options:{
					max:1,
		  			min:0,
		  			precision:5,
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
contractDiscountSupplier.checkIsReadonly = function(contractDiscountNo){
	var result = false;
	if(contractDiscountNo && contractDiscountNo!=''){
		ajaxRequestAsync(BasePath + '/contract_discount/checkIsReadonly',{contractDiscountNo:contractDiscountNo,contractDiscountType:1},function(data){
			if(data){
				result = true;
			}
		});
	}
	return result;
}

//新增
contractDiscountSupplier.add = function() {
	if(contractDiscountSupplier.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
	}
};
 
//修改
contractDiscountSupplier.edit = function(rowIndex, rowData) {
	if(contractDiscountSupplier.endEdit()){
		if(contractDiscountSupplier.checkIsReadonly(rowData.contractDiscountNo)){
			showError("该折扣已被引用，不允许修改！");
			return;
		}
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
	}
};

//删除
contractDiscountSupplier.del = function() {
  var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
  var rowIndex;
  var errMessage = '';
  if(checkedRows.length > 0){
	  $.each(checkedRows,function(index,row){
		  if(contractDiscountSupplier.checkIsReadonly(row.contractDiscountNo)){
			  errMessage = "勾选的折扣已被引用，不允许修改！"
			  return false;
		  }
	  });
	  if(errMessage !=''){
		  showError(errMessage);
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

//结束编辑
contractDiscountSupplier.endEdit = function() {
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
contractDiscountSupplier.save = function(){
	if(contractDiscountSupplier.endEdit()){
	    var url = contractDiscountSupplier.modulePath + '/save';
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
	    		contractDiscountSupplier.searchData(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};

//查询
contractDiscountSupplier.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = 'list.json';
	$("#dtlDataGrid").datagrid('load');
};

//清空
contractDiscountSupplier.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
	$('#contractDiscountType').val('1');
};

$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
    setTimeout(contractDiscountSupplier.initPage,500);
});

