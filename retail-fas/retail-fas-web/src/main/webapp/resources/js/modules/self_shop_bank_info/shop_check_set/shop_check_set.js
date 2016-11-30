var shopCheckSet = new Object();

shopCheckSet.editRowIndex = -1;
//当前用户
var currentUser;

$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	//给新增按钮绑定函数事件
	$("#btn-insert").on('click',function(){
		shopCheckSet.add();
	});
	//给删除按钮绑定函数事件
	$("#btn-delete").on('click',function(){
		shopCheckSet.del();
	});
	// 给保存按钮绑定函数事件
	$("#btn-save").on('click',function(){
		shopCheckSet.save();
	});
	//给启用按钮绑定函数事件
	$("#top-btn-aduit").on('click',function(){
		shopCheckSet.batchOperate(0)
	});
	//给禁用按钮绑定函数事件
	$("#top-btn-cancel").on('click',function(){
		shopCheckSet.batchOperate(1)
	});
})

//新增
shopCheckSet.add = function() {

	var paramTemp = {
		companyNo : 'companyNo',
		companyName : 'companyName',
		checkNo : 'checkNo',
		checkName : 'checkName'
	};

	$("#dataGridDiv").datagrid("removeEditor", "companyName");
	$("#dataGridDiv").datagrid(
			"addEditor",
			{
				field : "companyName",
				editor : {
					type : 'company',
					options : {
						id : 'companyName_',
						name : 'companyName_',
						required : true,
						relationData : true,
						callback : function(row) {
							var valueEd = $('#dataGridDiv').datagrid('getEditor', {
								'index' : shopCheckSet.editRowIndex,
								'field' : paramTemp.companyNo
							});
							valueEd.target.val(row[paramTemp.companyNo]);
							//设置检查项编码(公司编码前两位+店铺编码后四位)
							var valueEd = $('#dataGridDiv').datagrid('getEditor', {
								'index' : shopCheckSet.editRowIndex,
								'field' : 'checkNo'
							});
							//获取三位流水号
							var serialNo = null;
							ajaxRequestAsync( BasePath + '/shop_check_set/getSerialNo',null,function(data){
								serialNo = data;
							});
							var checkNo = row[paramTemp.companyNo].substring(0,2)+serialNo;
							var insertRows = $('#dataGridDiv').datagrid('getChanges', 'inserted');
							if(insertRows.length>1){
								if(checkNo == insertRows[0].checkNo){
									var temp = insertRows[insertRows.length-2].checkNo;
									var params = {"serialNo" : temp.substring(2,temp.length)};
									ajaxRequestAsync( BasePath + '/shop_check_set/getInsertNo',params,function(data){
										serialNo = data;
									});
								}
							}
							valueEd.target.val(row[paramTemp.companyNo].substring(0,2)+serialNo);
						}
					}
				}
			});

	if (shopCheckSet.endEdit()) {

		$('#dataGridDiv').datagrid('insertRow', {
			index : 0,
			row : {}
		});
		$('#dataGridDiv').datagrid('beginEdit', 0);
		shopCheckSet.editRowIndex = 0;

	}

};

//删除
shopCheckSet.del = function() {
	var delFlag = false;
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');

	if (checkedRows.length == 0) {
		showWarn('请选择要删除的行..');
		return;
	}

	var rowIndex;
	$.each(checkedRows, function(index, row) {
		if(row.status == 0){
			showWarn('不能删除启用状态记录!');
			return;
		}
		rowIndex = $('#dataGridDiv').datagrid('getRowIndex', row);
		$('#dataGridDiv').datagrid('deleteRow', rowIndex);
		if (shopCheckSet.editRowIndex == rowIndex) {
			shopCheckSet.editRowIndex = -1;
		}
	});
}

//编辑
shopCheckSet.edit = function(rowIndex, data) {
	if (shopCheckSet.endEdit()) {
		$('#dataGridDiv').datagrid('beginEdit', rowIndex);
		shopCheckSet.editRowIndex = rowIndex;
	}
}

//保存
shopCheckSet.save = function() {

	if (shopCheckSet.endEdit()) {
		if (!shopCheckSet.validationBeforeSave()) {
			return;
		}

		var url = BasePath + '/shop_check_set/save';
		var insertRows = $('#dataGridDiv').datagrid('getChanges',
				'inserted');
		var updatedRows = $('#dataGridDiv').datagrid('getChanges',
				'updated');
		$.each(updatedRows, function(index, row) {
			row.id = row.rowId;
		});
		var deletedRows = $('#dataGridDiv').datagrid('getChanges',
				'deleted');
		$.each(deletedRows, function(index, row) {
			row.id = row.rowId;
		});
		var changeRows = {
			inserted : JSON.stringify(insertRows),
			updated : JSON.stringify(updatedRows),
			deleted : JSON.stringify(deletedRows)
		};
		shopCheckSet.saveToDB(url, changeRows);
		$('#dataGridDiv').datagrid('reload');//刷新
	}

};

//启用禁用
shopCheckSet.batchOperate = function(status){
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');
	if(checkedRows.length == 0){
		showWarn("请选择操作的记录");
		return ;
	}
	var message = "";
	for(var i=0;i<checkedRows.length;i++){
		var row=checkedRows[i];
		if(status==row.status&&status==0){
			showWarn("已启用项不能再启用!!");
			return ;
		}else if(status==row.status&&status==1){
			showWarn("已禁用项不能再禁用!!");
			return ;
		}
	}
	var tips = "";
	if(status == 0){
		tips = "启用"
	}else if(status == 1){
		tips = "禁用";
	}
	$.messager.confirm("确认","确定要"+tips+"选中记录吗?",function(r) {
		if (r) {
			$.each(checkedRows, function(index, row) {
				row.status = status;
			});
			var data = {
				updated : JSON.stringify(checkedRows),
			};
			ajaxRequestAsync(BasePath + '/shop_check_set/save',data,function(result){
				if(result){
					showSuc('操作成功');
					$('#dataGridDiv').datagrid('reload');
				}else{
					showError('操作失败');
				}
			});
		}
	});
}

shopCheckSet.validationBeforeSave = function() {

	var flag = true;

	var insertRows = $('#dataGridDiv').datagrid('getChanges',
			'inserted');
	var updatedRows = $('#dataGridDiv').datagrid('getChanges',
			'updated');

	var dataRows;
	if (insertRows.length > 0 || updatedRows.length > 0) {
		if (insertRows.length > 0) {
			dataRows = insertRows;
		} else {
			dataRows = updatedRows;
		}

		$.each(dataRows, function(i, row) {
			if (row.companyNo == null || row.companyNo == "") {
				flag = false;
				showWarn('当前添加的数据中,公司数据不完整,请完善后 重试.');
			}
			if (!flag) {
				$('#dataGridDiv').datagrid('beginEdit',
						shopCheckSet.editRowIndex);
				shopCheckSet.editRowIndex = $('#dataGridDiv')
						.datagrid('getRowIndex', row);
				return false;
			}
		});
	}
	return flag;
};

//end the current edit row
shopCheckSet.endEdit = function() {
	if ($('#dataGridDiv').datagrid('validateRow',
			shopCheckSet.editRowIndex)) {
		$('#dataGridDiv').datagrid('endEdit',
				shopCheckSet.editRowIndex);
		return true;
	}
	return false;
};

shopCheckSet.saveToDB = function(url, changeRows) {
	shopCheckSet.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
		} else {
			showSuc('保存失败');
		}
	});
};

//common ajax request 
shopCheckSet.commonAjaxRequest = function(url, reqParam, callback) {
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