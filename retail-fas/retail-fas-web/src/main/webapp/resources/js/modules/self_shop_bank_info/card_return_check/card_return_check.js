var cardReturnCheck = new Object();

cardReturnCheck.editRowIndex = -1;
//当前用户
var currentUser;

$(function(){
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
	$("#s").hide();
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#startOutDate").val(firstDay.format("yyyy-MM-dd"));
	$("#endOutDate").val(lastDay.format("yyyy-MM-dd"));
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	// 给保存按钮绑定函数事件
	$("#btn-save").on('click',function(){
		cardReturnCheck.save();
	});
})

// 操作
cardReturnCheck.operate = function(value,row,index){
	var result = '';
	var id=row.id;
	if(row.id !=undefined){
		if(row.status == '0'){
			result = result + '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>确认</a>';
		}else if(row.status == '1'){
			result = result + '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>反确认</a>';
		}else{
			result = result + '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>提交银行</a>';
		}
	}
	
	return result;
	
}

// 进行提交银行、退款确认、反确认操作
function operate(id,i){
	var rows=$("#dataGridDiv").datagrid('getRows');
	var row=rows[i];
	
	if($("#"+id).text()=='提交银行'){
		$("#"+id).text("确认");
		row.status=0;
	}else if($("#"+id).text()=='确认'){
		$("#"+id).text("反确认");
		row.status=1;
		row.auditor=currentUser.loginName;
		row.actualReturnTime=new Date().format("yyyy-MM-dd hh:mm:ss");
	}else if($("#"+id).text()=='反确认'){
		$("#"+id).text("确认");
		row.status=0;
		row.auditor=null;
		row.actualReturnTime=null;
	}
	// 更新行数据
	$("#dataGridDiv").datagrid('updateRow',{index:i,row:row});
	var updatedRows=[row];
	var changeRows = {
			updated : JSON.stringify(updatedRows)
		};
	cardReturnCheck.saveToDB(BasePath + '/card_return_check/do_save', changeRows);
}

// 编辑
cardReturnCheck.edit = function(rowIndex,data){
	if(cardReturnCheck.endEdit()){
		$('#dataGridDiv').datagrid('beginEdit', rowIndex);
		cardReturnCheck.editRowIndex = rowIndex;
		$("#shopName_").combogrid("disable");
	}
}

// 保存
cardReturnCheck.save = function() {
	if (cardReturnCheck.endEdit()) {
		if (!cardReturnCheck.validationBeforeSave()) {
			return;
		}

		var url = BasePath + '/card_return_check/do_save';
		var updatedRows = $('#dataGridDiv').datagrid('getChanges', 'updated');
		var changeRows = {
			updated : JSON.stringify(updatedRows)
		};
		cardReturnCheck.saveToDB(url, changeRows);
	}

};

//导出
cardReturnCheck.exportExcel = function() {
	$.fas.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/card_return_check/do_fas_export",
		exportTitle : "银行卡退款核对导出"
	});
};


// end the current edit row
cardReturnCheck.endEdit = function() {
	if ($('#dataGridDiv').datagrid('validateRow', cardReturnCheck.editRowIndex)) {
		$('#dataGridDiv').datagrid('endEdit', cardReturnCheck.editRowIndex);
		return true;
	}
	return false;
};

cardReturnCheck.validationBeforeSave = function() {

	var flag = true;

	var updatedRows = $('#dataGridDiv').datagrid('getChanges', 'updated');

	var dataRows;
	if (updatedRows.length > 0) {
		dataRows = updatedRows;
		$.each(dataRows, function(i, row) {
			if (!flag) {
				$('#dataGridDiv').datagrid('beginEdit',
						cardReturnCheck.editRowIndex);
				cardReturnCheck.editRowIndex = $('#dataGridDiv').datagrid(
						'getRowIndex', row);
				return false;
			}
		});
	}
	return flag;
};

cardReturnCheck.saveToDB = function(url, changeRows) {
	cardReturnCheck.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
		} else {
			showSuc('保存失败');
		}
	});
};
// common ajax request
cardReturnCheck.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async : false,
		dataType : 'json',
		success : callback
	});
};