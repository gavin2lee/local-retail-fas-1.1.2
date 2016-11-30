var payTermSupplier = {};

var currentUser;

payTermSupplier.modulePath = BasePath + '/pay_term_supplier';

payTermSupplier.termTypeData = [{value:'1', text:'每月固定日'},
                        {value:'2', text:'发货日XX天'}, 
                        {value:'3', text:'发票日XX天'},
                        {value:'4', text:'月结XX天'}, 
                        {value:'5', text:'供应商发货日XX天'}];

payTermSupplier.termTypeFormat = function(value,row,index){
	for(var i=0;i<payTermSupplier.termTypeData.length;i++){
		if(value == payTermSupplier.termTypeData[i].value){
			return payTermSupplier.termTypeData[i].text;
		}
	}
	return '';
};

payTermSupplier.selectIsPrepay = function(value){
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var edForward = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'forwardProportion'});
	var edSpot = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'spotProportion'});
	if(value && value=='1'){
		$(edForward.target).numberbox('enable');
		$(edSpot.target).numberbox('enable');
		$(edForward.target).numberbox({
			required : true
		});
		$(edSpot.target).numberbox({
			required : true
		});
	}else{
		$(edForward.target).numberbox('setValue',0);
		$(edSpot.target).numberbox('setValue',0);
		$(edForward.target).numberbox('disable');
		$(edSpot.target).numberbox('disable');
		$(edForward.target).numberbox({
			required : false
		});
		$(edSpot.target).numberbox({
			required : false
		});
	}
	$(edForward.target).numberbox('validate');
	$(edSpot.target).numberbox('validate');
}

payTermSupplier.isPrepayFormat = function(value,row,index){
	if(value && value=='1'){
		return '是';
	}
	return '否';
};

//页面初始化
payTermSupplier.initPage = function(){
	payTermSupplier.initEditor();
	payTermSupplier.initFormatFunction();
}

//初始化格式化方法
payTermSupplier.initFormatFunction = function(){
	var isPrepay = $('#dtlDataGrid').datagrid('getColumnOption','isPrepay');
	isPrepay.formatter = payTermSupplier.isPrepayFormat;
}

// 初始化编辑器
payTermSupplier.initEditor = function(){
	$("#dtlDataGrid").datagrid("addEditor", 
		[
		 {field : "companyNo", editor : {type:'readonlytext'}},
		 {field : "supplierNo", editor : {type:'readonlytext'}},
		 {field : "termName", editor : {type:'readonlytext'}},
		 {field : "termType", editor : {type:'readonlytext'}},
		 {field : "termTypeName", editor : {type:'readonlytext'}},
		 {field : "fixedDay", editor : {type:'readonlytext'}},
		 {field : "days", editor : {type:'readonlytext'}},
		 {field : "termNo", editor : {type:'test_combogrid',
				 options:{
					 type:'payTerm',
					 required:true,
					 callback:payTermSupplier.termCallBack
				 }
		 }},
		 {field : "companyName", editor : {type:'test_combogrid',
				options:{
					type:'company',
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'companyNo'
				}
		 }},
		 {field : "supplierName", editor : {type:'test_combogrid',
				options:{
					type:'supplier',
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'supplierNo'
				}
		 }},
		 {field : "isPrepay", editor : {type:'combobox',
				options:{
					data:[{value:'0', text:'否'}, {value:'1', text:'是'}],
					valueField:'value',
					textField:'text',
					required:true,
					onSelect:function(data){
						payTermSupplier.selectIsPrepay(data.value);
					}
				}
		 }},
		 {field : "forwardProportion", editor : {type:'numberbox',options:{max:1,min:0,precision:3}}},
		 {field : "spotProportion", editor : {type:'numberbox',options:{max:1,min:0,precision:3}}},
		 ]
	);
}

//新增
payTermSupplier.add = function() {
	if(payTermSupplier.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {isPrepay:0}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
		payTermSupplier.selectIsPrepay(0);
	}
};
 
//修改
payTermSupplier.edit = function(rowIndex, rowData) {
	if(payTermSupplier.endEdit()){
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
		payTermSupplier.selectIsPrepay(rowData.isPrepay);
	}
};

//删除
payTermSupplier.del = function() {
  var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
  var rowIndex;
  var errMessage = '';
  if(checkedRows.length > 0){
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
payTermSupplier.endEdit = function() {
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
payTermSupplier.getCheckObj = function(companyNo, supplierNo){
	var obj = null;
	ajaxRequestAsync(BasePath + '/pay_term_supplier/get_biz.json',{companyNo:companyNo,supplierNo:supplierNo},function(data){
		if(data && data.length >0){
			obj = data[0];
		}
	});
	return obj;
}

// 保存
payTermSupplier.save = function(){
	if(payTermSupplier.endEdit()){
	    var url = payTermSupplier.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var flag = false;
	    $.each(insertRows,function(index, item){
	    	var checkObj = payTermSupplier.getCheckObj(item.companyNo,item.supplierNo);
	    	if(checkObj !=null){
	    		flag = true;
	    		return false;
	    	}
	    	item.createUser = currentUser.username;
	    	item.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    if(flag){
	    	showError("存在重复数据，不允许保存！");
	    	return ;
	    }
	    $.each(updatedRows,function(index, item){
	    	var checkObj = payTermSupplier.getCheckObj(item.companyNo,item.supplierNo);
	    	if(checkObj !=null && checkObj.id != item.id){
	    		flag = true;
	    		return false;
	    	}
	    	item.updateUser = currentUser.username;
	    	item.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    if(flag){
	    	showError("存在重复数据，不允许保存！");
	    	return ;
	    }
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');
	    		payTermSupplier.searchData(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};

//查询
payTermSupplier.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = 'list.json';
	$("#dtlDataGrid").datagrid('load');
};

//清空
payTermSupplier.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
};

//条款信息回调
payTermSupplier.termCallBack = function(row){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0 && row){
		var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
    	var edNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'termNo'});
    	var edName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'termName'});
    	var edType =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'termType'});
    	var edTypeName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'termTypeName'});
    	var edFixedDay =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'fixedDay'});
    	var edDays =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'days'});
    	edNo.target.val(row.code);
    	edName.target.val(row.name);
    	edType.target.val(row.extendField1);
    	edTypeName.target.val(payTermSupplier.termTypeFormat(row.extendField1));
		edFixedDay.target.val(row.extendField2);
		edDays.target.val(row.extendField3);
	}
}

$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	setTimeout(payTermSupplier.initPage,500);
});

