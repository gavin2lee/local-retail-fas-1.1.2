var balanceDiffBack = {};

var setting = {
		datagridId : "balanceDiffBackDG",
		primaryKey : "id",
		saveUrl : "/bill_shop_balance_back/save",
		saveCallback : function(){
			
		},
		initRow : function(){
			return {};
		},
		initData : {
			combobox : {
				
			}
		}
	};

balanceDiffBack.modulePath = BasePath + '/bill_shop_balance_back';

/**
 * 查询店铺
 */
balanceDiffBack.dobalanceDiffBack = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/bill_shop_balance_back/list.json";          //"/common_util/getShop";
	$("#balanceDiffBackDG").datagrid('options').queryParams = reqParam;
	$("#balanceDiffBackDG").datagrid('options').url = queryMxURL;
	$("#balanceDiffBackDG").datagrid('load');
};

/**
 * 清空查询条件
 */
balanceDiffBack.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
});

balanceDiffBack.save = function(){
	if($('#mainDataForm').form('validate')){
		if(balanceDiffBack.doValidate()){
			balanceDiffBack.savebalanceDiffBack();
//			var pkVal = $('#id').val();
//			if(pkVal !=''){
//				balanceDiffBack.doPut();
//			}else{
//				balanceDiffBack.doPost();
//			}
		}
	};
};

balanceDiffBack.doValidate = function(){ // 验证
	if($('#mainDataForm').form('validate')){
		if(balanceDiffBack.endEdit()){
			return true;
		}
	}
	return false;
};

balanceDiffBack.endEdit = function(){// 结束明细行编辑
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#balanceDiffBackDG').datagrid('validateRow', editRowIndex)){
			$('#balanceDiffBackDG').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

// 新增保存
balanceDiffBack.doPost = function(){
	$('#mainDataForm').form('submit',{
		url : balanceDiffBack.modulePath + '/post',
		onSubmit:function(param){
			param.status = 0;
		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
			}else{
				showError('保存失败');
			}
		}
	});
};

// 修改保存
balanceDiffBack.doPut = function(){
	$('#mainDataForm').form('submit',{
		url : balanceDiffBack.modulePath + '/put',
		onSubmit:function(param){

		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
			}else{
				showError('保存失败');
			}
		}
	});
};

balanceDiffBack.savebalanceDiffBack = function(){// 保存明细 
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return;
	}
	if(balanceDiffBack.endEdit()){
	var insertedData = $('#balanceDiffBackDG').datagrid('getChanges','inserted');
	var updatedData = $('#balanceDiffBackDG').datagrid('getChanges','updated');
	var deletedData = $('#balanceDiffBackDG').datagrid('getChanges','deleted');
	var diffBillNo = $('#diffBillNo').val();
	var balanceNo = $('#balanceNo').val();
	
	var url = balanceDiffBack.modulePath + '/save';
	
	$.each(insertedData, function(index, row) {
		row.diffBillNo = diffBillNo;
		row.balanceNo = balanceNo;
	});
	
	var data = {
		inserted : JSON.stringify(insertedData),
		updated : JSON.stringify(updatedData),
		deleted : JSON.stringify(deletedData),
	};
	 ajaxRequestAsync(url, data, function(result){
	    	if(result){
//	ajaxRequestAsync(balanceDiffBack.modulePath + '/insert',data,function(){
//		if(data){
		  	$('#balanceDiffBackDG').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
	}
};

function getCheckedRows(){
	return $('#balanceDiffBackDG').datagrid('getChecked');
}

balanceDiffBack.getRowNo = function(field){
	var noEd = $('#balanceDiffBackDG').datagrid('getEditor', {
		'index' : balanceDiffBack.editRowIndex,
		'field' : field
	});
	return noEd.target.val();
};

//新增
balanceDiffBack.insertRow = function() {
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return;
	}
	if(balanceDiffBack.endEdit()){
		var diffBillNo = $('#diffBillNo').val();
		var balanceNo = $('#balanceNo').val();
		var billNo = $('#billNo').val();		
	    $('#balanceDiffBackDG').datagrid('appendRow', {diffBillNo:diffBillNo,balanceNo:balanceNo,billNo:billNo});
	    var length = $('#balanceDiffBackDG').datagrid('getRows').length;
		$('#balanceDiffBackDG').datagrid('beginEdit',length-1);
	    shopBalanceDiff.editRowIndex = length-1;
	
	}
};