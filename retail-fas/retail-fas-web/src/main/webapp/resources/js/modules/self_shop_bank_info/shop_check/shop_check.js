var shopCheck = new Object();

shopCheck.editRowIndex = -1;

$(function() {
	$.extend($.fn.validatebox.defaults.rules, {
		onlyNumbers : {// 验证数字
			validator : function(value) {
				return /^[+]?[0-9]+\d*$/i.test(value);
			},
			message : '请输入数字'
		}
	});
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
	var date = new Date();
	var month = date.getMonth()+1;
	$("#year").val(date.getFullYear());
	$("#month").val(month<10?"0"+month:month);
	
	// 给查询按钮绑定函数事件
	$("#btn-search").on('click', function() {
		shopCheck.search();
	});
	// 给清空按钮绑定函数事件
	$("#btn-remove").on('click', function() {
		shopCheck.doSearchClear();
	});
	// 给保存按钮绑定函数事件
	$("#btn-save").on('click', function() {
		shopCheck.save();
	});
	// 给导入按钮绑定函数事件
	$("#btn-import").on('click', function() {
		shopCheck.importExcel();
	});
	// 给导出按钮绑定函数事件
	$("#btn-export").on('click', function() {
		shopCheck.exportExcel();
	});
});

// 查询
shopCheck.search = function() {
	var params = $('#searchForm').form('getData');
	var valid = $("#searchForm").form('validate');
	if (valid == false) {
		return;
	}
	
	ajaxRequestAsync(BasePath + '/shop_check_tmp/get_check', params,
			function(data) {
				// 根据公司编号获取公司检查列,然后在页面展示出来
				var cols = $('#dataGridDiv').datagrid('options').columns[0];// 获取原有固定列
				var col = new Array();
				for ( var i = 0; i < 6; i++) {
					col.push(cols[i]);
				}
				for ( var i = 0; i < data.length; i++) {
					var column = {};
					column["align"] = 'center';
					column["boxWidth"] = 72;
					column["title"] = data[i].checkName;
					column["field"] = 'check' + i;
					column["width"] = 80;
					column["editor"] = {type:'validatebox',options:{validType:['onlyNumbers','maxLength[20]']}};
					column["exportType"] = 'number';
					col.push(column);// 当需要formatter的时候自己添加就可以了,原理就是拼接字符串.
				}
				for ( var i = cols.length-4; i < cols.length ; i++) {
					col.push(cols[i]);
				}
				$('#dataGridDiv').datagrid({
					queryParams : params,
					url : BasePath + '/shop_check_tmp/list.json',
					columns : [ col ]
				});
			});
};

//清空
//2.清空查询区域
shopCheck.doSearchClear = function() {
	$('#searchForm').form("clear");
	$(':input','#searchForm').not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
};

//编辑
shopCheck.edit = function (rowIndex, data){
	if (shopCheck.endEdit()) {
		$('#dataGridDiv').datagrid('beginEdit', rowIndex);
		shopCheck.editRowIndex = rowIndex;
	}
}

//保存
shopCheck.save = function() {
	if (shopCheck.endEdit()) {
		var url = BasePath + '/shop_check_tmp/do_save';
		var updatedRows = $('#dataGridDiv').datagrid('getChanges',
				'updated');
		var changeRows = {
			updated : JSON.stringify(updatedRows)
		};
		shopCheck.saveToDB(url, changeRows);
	}
};

//导入
shopCheck.importExcel = function() {
	$.importExcel.open({
		'submitUrl' : BasePath + '/shop_check/do_import',
		'templateName' : '店铺检查登记导入.xlsx',
		success : function(data) {
			$.messager.progress('close');
			if (data) {
				if (isNotBlank(data.error)) {
					showError(data.error);
				} else {
					$.importExcel.colse();
					showSuc('数据导入成功');
				}
			} else {
				showInfo('导入失败,请联系管理员!');
			}
		},
		error : function() {
			$.messager.progress('close');
			showWarn('数据导入失败，请联系管理员');
		}
	});
}

//导出
shopCheck.exportExcel = function(){
	$.fas.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/shop_check_tmp/do_fas_export",
		exportTitle : "店铺检查登记导出"
	});
}

shopCheck.saveToDB = function(url, changeRows) {
	shopCheck.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
			shopCheck.search();
		} else {
			showSuc('保存失败');
		}
	});
};

shopCheck.endEdit = function() {
	if ($('#dataGridDiv').datagrid('validateRow',
			shopCheck.editRowIndex)) {
		$('#dataGridDiv').datagrid('endEdit',
				shopCheck.editRowIndex);
		return true;
	}
	return false;
};

//common ajax request 
shopCheck.commonAjaxRequest = function(url, reqParam, callback) {
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