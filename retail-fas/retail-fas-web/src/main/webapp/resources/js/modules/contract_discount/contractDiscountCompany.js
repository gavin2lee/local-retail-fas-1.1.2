var contractDiscountCompany = {};

var currentUser;

contractDiscountCompany.modulePath = BasePath + '/contract_discount';

//算法(含税单价)
contractDiscountCompany.algorithmData = 
	[{value:'A', text:'1：牌价*折扣1四舍五入取2位小数保留到分，2：/1.17四舍五入取2位小数保留到分，3：*折扣2*1.17  四舍五入取2位小数保留到分'},
    {value:'B', text:'1：牌价*折扣1四舍五入保留到元，2：/1.17四舍五入取2位小数保留到分，3：*折扣2*1.17  四舍五入取2位小数保留到分'},
    {value:'C', text:'牌价*折扣1*折扣2'}];

// 订货类型
contractDiscountCompany.orderTypeData = 
			[{value:'1', text:'现货'},
	        {value:'2', text:'期货'}, 
	        {value:'3', text:'期货;现货'}];

//定价依据
contractDiscountCompany.priceBasisData = 
	[{value:'1', text:'牌价'},
    {value:'2', text:'终端销售金额'},
    {value:'3', text:'供应商结算价'}];

//类型描述格式化
contractDiscountCompany.typeNameFormat = function(value,data){
	for(var i=0;i<data.length;i++){
		if(value == data[i].value){
			return data[i].text;
		}
	}
	return '';
}

// 页面初始化
contractDiscountCompany.initPage = function(){
	contractDiscountCompany.initEditor();
	contractDiscountCompany.initFormatFunction();
}

//初始化格式化方法
contractDiscountCompany.initFormatFunction = function(){
	var orderType = $('#dtlDataGrid').datagrid('getColumnOption','orderType');
	var priceBasis = $('#dtlDataGrid').datagrid('getColumnOption','priceBasis');
	var algorithmDesc = $('#dtlDataGrid').datagrid('getColumnOption','algorithmDesc');
	orderType.formatter = function(value,row,index){
		return contractDiscountCompany.typeNameFormat(value,contractDiscountCompany.orderTypeData);
	};
	priceBasis.formatter = function(value,row,index){
		return contractDiscountCompany.typeNameFormat(value,contractDiscountCompany.priceBasisData);
	};
	algorithmDesc.formatter = function(value,row,index){
		return '<span title="' + value + '">' + value + '</span>';
	};
}

contractDiscountCompany.selectPriceBasis = function(value){
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var edDiscount1 = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'discount1'});
	var edDiscount2 = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'discount2'});
	var edAlgorithm = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'algorithm'});
	var valAlgorithm = $(edAlgorithm.target).combobox('getValue');
	var edAddPrice = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'addPrice'});
	var edAlgorithmDesc = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'algorithmDesc'});
	if(value && value==1){// 牌价
		$(edAddPrice.target).numberbox('disable');
		$(edAddPrice.target).numberbox('setValue','');
		$(edDiscount1.target).numberbox({
			required : true,
			disabled : false
		});
		$(edDiscount2.target).numberbox({
			required : true,
			disabled : false
		});
		$(edAlgorithm.target).combobox({
			required : true,
			disabled : false,
			value : valAlgorithm
		});
	}else{
		$(edAddPrice.target).numberbox('enable');
		$(edDiscount1.target).numberbox({
			required : false,
			disabled : false
		});
		$(edDiscount2.target).numberbox({
			required : false,
			disabled : true,
			value : ''
		});
		$(edAlgorithm.target).combobox({
			required : false,
			disabled : true,
			value : ''
		});
		edAlgorithmDesc.target.val('');
	}
	$(edDiscount1.target).numberbox('validate');
	$(edDiscount2.target).numberbox('validate');
	$(edAlgorithm.target).combo('validate');
	$(edAddPrice.target).numberbox('validate');
}

// 初始化编辑器
contractDiscountCompany.initEditor = function(){
	$("#dtlDataGrid").datagrid("addEditor", 
		[
		 {field : "buyerNo", editor : {type:'readonlytext'}},
		 {field : "salerNo", editor : {type:'readonlytext'}},
		 {field : "brandNo", editor : {type:'readonlytext'}},
		 {field : "categoryNo", editor : {type:'readonlytext'}},
		 {field : "algorithmDesc", editor : {type:'readonlytext'}},
		 {field : "buyerName", editor : {type:'test_combogrid',
				options:{
					type:'company',
					groupLeadRole:true,
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'buyerNo'
				}
		 }},
		 {field : "salerName", editor : {type:'test_combogrid',
				options:{
					type:'company',
					notGroupLeadRole : true,
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
					data:contractDiscountCompany.orderTypeData,
					valueField:'value',
					textField:'text',
					required:true
				}
		 }},
		 {field : "priceBasis", editor : {type:'combobox',
				options:{
					data:contractDiscountCompany.priceBasisData,
					valueField:'value',
					textField:'text',
					required:true,
					onSelect:function(data){
						contractDiscountCompany.selectPriceBasis(data.value);
					}
				}
		 }},
		  {field : "discount1", editor : {type:'numberbox',
				options:{
					max:999999,
		  			min:0,
		  			precision:5,
		  			disabled:true
				}
		  }},
		  {field : "discount2", editor : {type:'numberbox',
				options:{
					max:999999,
		  			min:0,
		  			precision:5,
		  			disabled:true
				}
		  }},
		  {field : "referDiscount1", editor : {type:'numberbox',
				options:{
					max:999999,
		  			min:0,
		  			precision:5
				}
		  }},
		  {field : "referDiscount2", editor : {type:'numberbox',
				options:{
					max:999999,
		  			min:0,
		  			precision:5
				}
		  }},
		  {field : "algorithm", editor : {type:'combobox',
				options:{
					data:contractDiscountCompany.algorithmData,
					valueField:'value',
					textField:'value',
					disabled:true,
					onSelect:function(data){
						var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
						var edAlgorithmDesc = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'algorithmDesc'});
						edAlgorithmDesc.target.val(contractDiscountCompany.typeNameFormat(data.value,contractDiscountCompany.algorithmData));
					
					}
				}
		  }},
		  {field : "addPrice", editor : {type:'numberbox',
				options:{
		  			max:999999,
		  			min:-999999,
		  			precision:2,
		  			disabled:true
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
contractDiscountCompany.checkIsReadonly = function(contractDiscountNo){
	var result = false;
	if(contractDiscountNo && contractDiscountNo!=''){
		ajaxRequestAsync(BasePath + '/contract_discount/checkIsReadonly',{contractDiscountNo:contractDiscountNo,contractDiscountType:2},function(data){
			if(data){
				result = true;
			}
		});
	}
	return result;
}

//新增
contractDiscountCompany.add = function() {
	if(contractDiscountCompany.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
	}
};
 
//修改
contractDiscountCompany.edit = function(rowIndex, rowData) {
	if(contractDiscountCompany.endEdit()){
		if(contractDiscountCompany.checkIsReadonly(rowData.contractDiscountNo)){
			showError("该折扣已被引用，不允许修改！");
			return;
		}
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
		contractDiscountCompany.selectPriceBasis(rowData.priceBasis);
	}
};

//删除
contractDiscountCompany.del = function() {
  var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
  var rowIndex;
  var errMessage = '';
  if(checkedRows.length > 0){
	  $.each(checkedRows,function(index,row){
		  if(contractDiscountCompany.checkIsReadonly(row.contractDiscountNo)){
			  errMessage = "勾选的折扣已被引用，不允许修改！"
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

//结束编辑
contractDiscountCompany.endEdit = function() {
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

// 保存校验
contractDiscountCompany.checkSave = function(row,type){
	if(type =='add' || type=='update'){
		if(row.discount1 && row.discount1 !='' && row.addPrice && row.addPrice!=''){
			showError("加价,折扣只允许设置一个！");
			return false;
		}
		if(row.discount1 ==='' && row.addPrice===''){
			showError("加价,折扣必须设置一个！");
			return false;
		}
	}
	return true;
};

contractDiscountCompany.saveValidate = true;

// 保存
contractDiscountCompany.save = function(){
	if(contractDiscountCompany.endEdit()){
		contractDiscountCompany.saveValidate = true;
	    var url = contractDiscountCompany.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
    	$.each(insertRows,function(index, item){
    		if(!contractDiscountCompany.checkSave(item,'add')){
    			contractDiscountCompany.saveValidate = false;
    			return false;
    		}
	    	item.contractDiscountType = $('#contractDiscountType').val();
	    	item.createUser = currentUser.username;
	    	item.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
    	if(!contractDiscountCompany.saveValidate){
	    	return;
	    }
	    $.each(updatedRows,function(index, item){
	    	if(!contractDiscountCompany.checkSave(item,'update')){
	    		contractDiscountCompany.saveValidate = false;
	    		return false;
    		}
	    	item.updateUser = currentUser.username;
	    	item.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    if(!contractDiscountCompany.saveValidate){
	    	return;
	    }
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');
	    		contractDiscountCompany.searchData(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};

//查询
contractDiscountCompany.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = 'list.json';
	$("#dtlDataGrid").datagrid('load');
};

//清空
contractDiscountCompany.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
	$('#contractDiscountType').val('2');
};

$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
    setTimeout(contractDiscountCompany.initPage,500);
});

