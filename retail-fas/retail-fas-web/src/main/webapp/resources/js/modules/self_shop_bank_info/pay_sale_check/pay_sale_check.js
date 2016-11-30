var paySaleCheck = new Object();
//当前用户
var currentUser;

paySaleCheck.editRowIndex = -1;

$(function() {
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
	
	$("#startOutStart").val(firstDay.format("yyyy-MM-dd"));
	$("#endOutEnd").val(lastDay.format("yyyy-MM-dd"));
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
		onlyNumbers : {// 验证数字
			validator : function(value) {
				return /^[+]?[0-9]+\d*$/i.test(value);
			},
			message : '请输入数字'
		},
		poundage : {//手续费验证
			validator : function(value) {
				return /^(-)?(?:0\.\d*[1-9]|(?!0)\d+(?:\.\d[1-9])?)|0$/i.test(value);
			},
			message : '输入非法!'
		}
	});

	// 给保存按钮绑定函数事件
	$("#btn-save").on('click', function() {
		paySaleCheck.save();
	});

});

// 根据支付方式销售核对状态显示操作信息，实现处理
paySaleCheck.operate = function(value, row, index) {
	var result = '';
	if(row.rowId != undefined){
		if (row.status == '1') {
			result = result
					+ '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>反确认</a>';
		} else {
			result = result
					+ '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>确认</a>';
		}
	}
	return result;
}

// 跳转处理进行确认反确认操作
function operate(id,i) {
	var rows=$("#dataGridDiv").datagrid('getRows');
	var row=rows[i];
	var currentUser=paySaleCheck.getCurrentUser();
	if($("#"+id).text() == '确认'){
		$("#"+id).text("反确认");
		row.status=1;
		row.auditor=currentUser.loginName;
		row.auditorTime=new Date().format("yyyy-MM-dd hh:mm:ss");
	}else if($("#"+id).text() == '反确认'){
		$("#"+id).text("确认");
		row.status=0;
		row.auditor=null;
		row.auditorTime=null;
	}
	//更新行数据
	$("#dataGridDiv").datagrid('updateRow',{index:i,row:row});
	var updateRows=[row];
	var changeRows = {
			updated : JSON.stringify(updateRows)
	};
	//保存到数据库
	paySaleCheck.saveToDB(BasePath + '/pay_sale_check/do_save?flag=1', changeRows);
}

// 编辑
paySaleCheck.edit = function(rowIndex, data) {
	if (paySaleCheck.endEdit()) {
		$('#dataGridDiv').datagrid('beginEdit', rowIndex);
		paySaleCheck.editRowIndex = rowIndex;
		$("#shopName_").combogrid("disable");
	}
}

// 保存
paySaleCheck.save = function() {

	if (paySaleCheck.endEdit()) {
		if (!paySaleCheck.validationBeforeSave()) {
			return;
		}

		var url = BasePath + '/pay_sale_check/do_save';
		var updatedRows = $('#dataGridDiv').datagrid('getChanges', 'updated');
		var changeRows = {
			updated : JSON.stringify(updatedRows)
		};
		paySaleCheck.saveToDB(url, changeRows);
	}

};

// 批量进行确认或反确认操作
paySaleCheck.batchOperate = function(status){
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');
	if(checkedRows.length == 0){
		showWarn("请选择操作的记录");
		return ;
	}
	var message = "";
	var changeRows = [];
	for(var i=0;i<checkedRows.length;i++){
		var row=checkedRows[i];
		if(status==row.status&&status==1){
			showWarn("不能确认财务已确认记录!!");
			return ;
		}else if(status==row.status&&status==0){
			showWarn("不能反确认财务未确认记录!!");
			return ;
		}
	}
	for(var i=0;i<checkedRows.length;i++){
		var row = checkedRows[i];
		if(row.rowId){
			changeRows.push(row);
		}
	}
	var tips = "";
	if(status == 1){
		tips = "确认"
	}else if(status == 0){
		tips = "反确认";
	}
	$.messager.confirm("确认","确定要"+tips+"选中记录吗?",function(r) {
		if (r) {
			$.each(changeRows, function(index, row) {
				row.status = status;
			});
			var data = {
				updated : JSON.stringify(changeRows),
			};
			ajaxRequestAsync(BasePath + '/pay_sale_check/do_save',data,function(result){
				if(result){
					showSuc('操作成功');
					$('#dataGridDiv').datagrid('reload');
				}else{
					showError('操作失败');
				}
			});
		}
	});
};
//导出
paySaleCheck.exportExcel = function() {
	$.fas.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/pay_sale_check/do_fas_export",
		exportTitle : "支付方式销售核对导出"
	});
};

// end the current edit row
paySaleCheck.endEdit = function() {
	if ($('#dataGridDiv').datagrid('validateRow', paySaleCheck.editRowIndex)) {
		$('#dataGridDiv').datagrid('endEdit', paySaleCheck.editRowIndex);
		return true;
	}
	return false;
};

paySaleCheck.validationBeforeSave = function() {

	var flag = true;

	var updatedRows = $('#dataGridDiv').datagrid('getChanges', 'updated');

	var dataRows;
	if (updatedRows.length > 0) {
		dataRows = updatedRows;
		$.each(dataRows, function(i, row) {
			if (!flag) {
				$('#dataGridDiv').datagrid('beginEdit',
						paySaleCheck.editRowIndex);
				paySaleCheck.editRowIndex = $('#dataGridDiv').datagrid(
						'getRowIndex', row);
				return false;
			}
		});
	}
	return flag;
};

paySaleCheck.saveToDB = function(url, changeRows) {
	paySaleCheck.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
		} else {
			showSuc('保存失败');
		}
	});
};
// common ajax request
paySaleCheck.commonAjaxRequest = function(url, reqParam, callback) {
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

//获取当前用户
paySaleCheck.getCurrentUser = function(){
	var currentUser = null;
 	$.ajax({
		  type: 'POST',
		  url: BasePath+'/common_util/getCurrentUser',
		  data: {},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  currentUser = resultData;
		  }
     });
 	return currentUser;
};

/**
 ** 减法函数，用来得到精确的减法结果
 ** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
 ** 调用：sub(arg1,arg2)
 ** 返回值：arg1减去arg2的精确结果
 **/
function sub(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

paySaleCheck.removeCheckbox = function(data) {
	var r = data.rows;
	for(var i=r.length-1;i>=0;i--){
		if(!r[i].rowId){
			$(".datagrid-btable input:checkbox").eq(i).remove();
		}
	}
}
