var payTerm = {};

var currentUser;

payTerm.modulePath = BasePath + '/pay_term';

payTerm.termTypeData = [{value:'1', text:'每月固定日'},
                        {value:'2', text:'发货日XX天'}, 
                        {value:'3', text:'发票日XX天'},
                        {value:'4', text:'月结XX天'}, 
                        {value:'5', text:'供应商发货日XX天'}];

payTerm.selectTermType = function(value){
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var edFixedDay = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'fixedDay'});
	var edDays = $('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'days'});
	if(value && value==1){
		$(edFixedDay.target).numberbox('enable');
		$(edDays.target).numberbox('disable');
		$(edFixedDay.target).numberbox({
			required : true
		});
		$(edDays.target).numberbox({
			required : false
		});
	}else{
		$(edDays.target).numberbox('enable');
		$(edFixedDay.target).numberbox('disable');
		$(edDays.target).numberbox({
			required : true
		});
		$(edFixedDay.target).numberbox({
			required : false
		});
	}
	$(edFixedDay.target).numberbox('validate');
	$(edDays.target).numberbox('validate');
}

payTerm.termTypeFormat = function(value,row,index){
	for(var i=0;i<payTerm.termTypeData.length;i++){
		if(value == payTerm.termTypeData[i].value){
			return payTerm.termTypeData[i].text;
		}
	}
	return '';
};

//页面初始化
payTerm.initPage = function(){
	payTerm.initEditor();
	payTerm.initFormatFunction();
}

//初始化格式化方法
payTerm.initFormatFunction = function(){
	var termType = $('#dtlDataGrid').datagrid('getColumnOption','termType');
	termType.formatter = payTerm.termTypeFormat;
}

// 初始化编辑器
payTerm.initEditor = function(){
	$("#dtlDataGrid").datagrid("addEditor", 
		[
		 {field : "termNo", editor : {type:'readonlytext'}},
		 {field : "termName", editor : {type:'validatebox',options:{validType:'maxLength[100]'}}},
		 {field : "termType", editor : {type:'combobox', 
   			options:{
				data:payTerm.termTypeData,
				valueField:'value',
				textField:'text',
				required:true,
				onSelect:function(data){
					payTerm.selectTermType(data.value);
				}
			}
		 }},
		 {field : "fixedDay", editor : {type:'numberbox',options:{max:28,min:1,precision:0}}},
		 {field : "days", editor : {type:'numberbox',options:{max:9999,min:1,precision:0}}},
		 ]
	);
}

//新增
payTerm.add = function() {
	if(payTerm.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {termType:1,termTypeName:'每月固定日'}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
		payTerm.selectTermType(1);
	}
};

// 校验是否只读
payTerm.checkIsReadonly = function(termNo){
	var result = false;
	if(termNo && termNo!=''){
		ajaxRequestAsync( BasePath + '/pay_term/checkIsReadonly',{termNo:termNo},function(data){
			if(data){
				result = true;
			}
		});
	}
	return result;
}

//修改
payTerm.edit = function(rowIndex, rowData) {
	if(payTerm.endEdit()){
		if(payTerm.checkIsReadonly(rowData.termNo)){
			showError("该条款已被引用，不允许修改！");
			return;
		}
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
		payTerm.selectTermType(rowData.termType);
	}
};

//删除
payTerm.del = function() {
var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
var rowIndex;
var errMessage = '';
if(checkedRows.length > 0){
	  $.each(checkedRows,function(index,row){
		  if(payTerm.checkIsReadonly(row.termNo)){
			  errMessage = "勾选的条款已被引用，不允许修改！"
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
payTerm.endEdit = function() {
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

//保存
payTerm.save = function(){
	if(payTerm.endEdit()){
	    var url = payTerm.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var balanceType = $('#balanceType').val();
	    $.each(insertRows,function(index, item){
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
	    		payTerm.searchData(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};

//查询
payTerm.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = 'list.json';
	$("#dtlDataGrid").datagrid('load');
};

//清空
payTerm.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
};

$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	setTimeout(payTerm.initPage,500);
});

