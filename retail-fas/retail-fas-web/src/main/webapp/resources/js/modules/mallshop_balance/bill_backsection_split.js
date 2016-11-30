var billBacksectionSplit = new Object();

var setting = {
		datagridId : "backSplitDtlDataGrid",
		primaryKey : "id",
		saveUrl : "/bill_backsection_split/save",
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

//当前用户
billBacksectionSplit.currentUser;

// 模块路径
billBacksectionSplit.modulePath = BasePath + '/bill_backsection_split';

billBacksectionSplit.dtlmodulePath = BasePath + '/bill_backsection_split_dtl';

billBacksectionSplit.readonlyInfo = function() {  
};

//查询界面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "backsectionNo=" + rowData.backsectionNo;
};

function BackSplitEditDataEditor() {
	this.insertRowCheck = function() {
		if($('#searchForm').form('validate')) {
			this.insertRow();
		}
	};
	
	this.saveCheck = function() {
		if($('#searchForm').form('validate')) {
			this.save();
		}
	};
	
	this.del = function() {
//		var $this = this;
//		var options = $this.options;
//		var tab = $('#dtlTab').tabs("getSelected");
//		var tabIndex = $('#dtlTab').tabs('getTabIndex',tab);
		var checkedRows = $("#backSplitEditDataGrid").datagrid("getChecked");
	    if(checkedRows.length < 1){
	        showWarn("请选择要删除的记录!");
	        return;
	    }
		for(var i =0; i < checkedRows.length; i++){
			var row = checkedRows[i];
			if(row.id != null && row.id != '') {				
				if(row.balanceNo !=null){
					showWarn("已存在对应的结算单"+row.balanceNo+"，不能删除！");
					return;
		 	    }
			}
		}
		
		//删除未保存的行
	    for(var i=0; i<checkedRows.length; i++){
	    	var row = checkedRows[i];
	    	var index =  $("#backSplitEditDataGrid").datagrid('getRowIndex', row);
	    	if(row.id == null) {
	    		 $("#backSplitEditDataGrid").datagrid('deleteRow', index);
	    	}
		}
	    //重新获取选中行
	    checkedRows =  $("#backSplitEditDataGrid").datagrid("getChecked");
	    if(checkedRows.length < 1){
	        return;
	    }
		$.messager.confirm("确认","确定要删除吗？",function(r) {
			if (r) {
				var url = BasePath + '/bill_backsection_split/deleteBacksectionSplit';
//				var idList = $('#id').val() + ',' + $('#backsectionNo').val() + ";";
				var idList = "";
				$.each(checkedRows, function(index,row) {
					idList+=row.id+','+row.backsectionNo+";";
				});
				var params = {idList : idList};
				ajaxRequestAsync(url,params,function(count){
					if(count) {
						showSuc('删除成功');
//						returnTab('mainTab', '单据查询');
//						$('#mainTab').tabs('close','单据明细');
						$("#backSplitEditDataGrid").datagrid('load');
					} else {
						showError('删除失败');
					}
				});
			}
		});
	}
	
	// 列表页面点击删除按钮
	this.deleteRow = function() {
		var $this = this;
		var options = $this.options;
		var tab = $('#dtlTab').tabs("getSelected");
		var tabIndex = $('#dtlTab').tabs('getTabIndex',tab);
		/*var  dataGridId=null;
		if(tabIndex == 1) {
				dataGridId : "shopBalanceDeductAfterDataGrid"
		} else {
				dataGridId : "shopBalanceDeductBeforeDataGrid"
		}*/
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
		
	    if(checkedRows.length < 1){
	        showWarn("请选择要删除的记录!");
	        return;
	    }
		for(var i =0; i < checkedRows.length; i++){
			var row = checkedRows[i];
			if(row.id != null && row.id != '') {				
				if(tabIndex == 0 && row.balanceNo !=null){
					showWarn("已存在对应的结算单"+row.balanceNo+"，不能删除！");
					return;
		 	    }
			}
		}
		
		//删除未保存的行
	    for(var i=0; i<checkedRows.length; i++){
	    	var row = checkedRows[i];
	    	var index = $("#" + options.dataGridId).datagrid('getRowIndex', row);
	    	if(row.id == null) {
	    		$("#" + options.dataGridId).datagrid('deleteRow', index);
	    	}
		}
	    //重新获取选中行
	    checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
	    if(checkedRows.length < 1){
	        return;
	    }
		$.messager.confirm("确认","确定要删除已保存的数据?",function(r) {
			if (r) {
				var url = BasePath + '/bill_backsection_split_dtl/delete';
				var idList = "";
				$.each(checkedRows, function(index,row) {
					
					idList+=row.id+','+row.rateId+";";
				});
				var params = {idList : idList.substring(0, idList.length-1)};
				ajaxRequestAsync(url,params,function(count){
					if(count){
						//删除已保存的行
						for(var i=0; i<checkedRows.length; i++){
					    	var row = checkedRows[i];
					    	var index = $("#" + options.dataGridId).datagrid('getRowIndex', row);
					    	$("#" + options.dataGridId).datagrid('deleteRow', index);
						}
						showSuc('删除成功');
					}else{
						showError('删除失败');
					}
				});
			}
		});
		
	};
}
var backSplitEditDataEditor=null;
/**
 * 初始化
 */
$(function() {
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	$.fas.extend(BackSplitEditDataEditor, FasEditorController);
	
	backSplitEditDataEditor = new BackSplitEditDataEditor();
	backSplitEditDataEditor.init("/bill_backsection_split", {
		dataGridDivId : 'backSplitEditDataDiv',
		dataGridId : 'backSplitEditDataGrid',
		saveUrl : "/bill_backsection_split_dtl/save",
		afterAdd : backSplitEditDataEditor.afterAdd,
		afterUpdate : backSplitEditDataEditor.afterUpdate,
		buildAddData : backSplitEditDataEditor.buildAddData
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
	
	//公司-回款方级联
	$("#companyName_").combogrid({
		onChange:function(newValue, oldValue) {
			var row = $("#companyName_").combogrid('grid').datagrid('getSelected');
			if(row) {
				var url = BasePath + '/base_setting/invoice_info_set/list.json?status=1&clientTypes=3,4' + '&companyNo=' + row.companyNo;
				$("#backNameId_").combogrid("grid").datagrid("options").url = url;
			}
			else {
				var url = BasePath + '/base_setting/invoice_info_set/list.json?status=1&clientTypes=3,4';
				$("#backNameId_").combogrid("grid").datagrid("options").url = url;
			}
		}
	});
	
});

/**
 * 查询界面--查询按钮
 */
billBacksectionSplit.search = function() {
	
	var tab = $('#subTab').tabs("getSelected");
	var tabIndex = $('#subTab').tabs('getTabIndex',tab);
	//回款单查询
	if(tabIndex == 0) {
		var fromObjStr = convertArray($('#searchForm').serializeArray());
		var queryMxURL = BasePath + '/bill_backsection_split/list.json';
		$("#backSplitDataGrid").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
		$("#backSplitDataGrid").datagrid('options').url = queryMxURL;
		$("#backSplitDataGrid").datagrid('load');
	}
	//回款单明细  店
	else if(tabIndex == 1) {
		var params = $('#searchForm').form('getData');
		// 店铺多选
		if($("#shopNo").val() != null && $("#shopNo").val() != '') {
			params.shopNos = '\'' + $("#shopNo").val().split(',').join('\',\'') + '\'';
		}
		params.month = $("#month").val();
		params.balanceNo = $("#balanceNo").val();
		
		var url = BasePath + '/bill_backsection_split_dtl/list.json';
	    $('#backSplitDataDtlGrid').datagrid('options').queryParams= params;
	    $('#backSplitDataDtlGrid').datagrid('options').url= url;
	    $("#backSplitDataDtlGrid").datagrid('load');
	}
	//回款单明细  店+品牌
	else if(tabIndex == 2) {
		var params = $('#searchForm').form('getData');
		// 店铺多选
		if($("#shopNo").val() != null && $("#shopNo").val() != '') {
			params.shopNos = '\'' + $("#shopNo").val().split(',').join('\',\'') + '\'';
		}
		params.month = $("#month").val();
		params.balanceNo = $("#balanceNo").val();
		
		var url = BasePath + '/bill_backsection_split_dtl/list.json';
	    $('#backSplitShopBrandDataDtlGrid').datagrid('options').queryParams= params;
	    $('#backSplitShopBrandDataDtlGrid').datagrid('options').url= url;
	    $("#backSplitShopBrandDataDtlGrid").datagrid('load');
	}
	//回款单 
	else if(tabIndex == 3) {
		var params = $('#searchForm').form('getData');
		// 店铺多选
		if($("#shopNo").val() != null && $("#shopNo").val() != '') {
			params.shopNos = '\'' + $("#shopNo").val().split(',').join('\',\'') + '\'';
		}
		params.month = $("#month").val();
		params.balanceNo = $("#balanceNo").val();
		
		var url = BasePath + '/bill_backsection_split_dtl/list.json';
	    $('#backSplitEditDataGrid').datagrid('options').queryParams= params;
	    $('#backSplitEditDataGrid').datagrid('options').url= url;
	    $("#backSplitEditDataGrid").datagrid('load');
	}
};

/**
 * 查询界面--清空按钮
 */
billBacksectionSplit.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
	$('#tb').find("input[name!=type]").val('');
	var url = BasePath + '/base_setting/invoice_info_set/list.json?status=1&clientTypes=3,4';
	$("#backNameId_").combogrid("grid").datagrid("options").url = url;
};

/**
 * 查询界面--新增按钮
 */
billBacksectionSplit.add = function(){
	billBacksectionSplit.addMainTab();
};

/**
 * 查询界面--双击修改
 */
billBacksectionSplit.loadDetail = function(rowIndex, rowData) {
	billBacksectionSplit.addMainTab(rowData);
};

/**
 * 初始化单据编辑tab
 */
billBacksectionSplit.addMainTab = function(rowData) {
	if($('#mainTab') != 'undefined' && $('#mainTab') !== null) {
		$('#mainTab').tabs('add', {
			title : '单据明细',
			href : billBacksectionSplit.modulePath + '/bill_backsection_split_dtl',
			onLoad : function(panel) {
				//fas_util.initIptFormSearch('mainDataForm');
				$("#backsectionNo").attr('readonly',true).addClass("readonly");
				
				//公司-回款方级联
				$("#companyName").combogrid({
					onChange:function(newValue, oldValue) {
						var row = $("#companyName").combogrid('grid').datagrid('getSelected');
						if(row) {
							var url = BasePath + '/base_setting/invoice_info_set/list.json?status=1&clientTypes=3,4' + '&companyNo=' + row.companyNo;
							$("#backNameId").combogrid("grid").datagrid("options").url = url;
						}
						else {
							var url = BasePath + '/base_setting/invoice_info_set/list.json?status=1&clientTypes=3,4';
							$("#backNameId").combogrid("grid").datagrid("options").url = url;
						}
					}
				});
				
				//修改时加载数据
				if(rowData != null) {
					
					var url = billBacksectionSplit.modulePath + '/get';
					var params = {id : rowData.id};
					
					$.getJSON(url, params, function(resultData) {// resultData-服务器回传的数据
						//填充主表
						billBacksectionSplit.loadData(resultData); 
						//填充明细
						billBacksectionSplit.loadBacksectionSplitDtl(rowData.backsectionNo);
					});
				}
			}
		});
	};
};

/**
 * 编辑界面--保存按钮
 */
billBacksectionSplit.save = function(){
	if($('#mainDataForm').form('validate') && billBacksectionSplit.endEdit()){
		var pkVal = $('#id').val();
		//修改
		if(pkVal !=''){
			billBacksectionSplit.doPut();
		}
		//新增
		else{
			billBacksectionSplit.doPost();
		}
	};
};

/**
 * 编辑界面--删除按钮
 */
billBacksectionSplit.del = function() {
	var pkVal = $('#id').val();
	//未保存表头
	if(pkVal == null || pkVal == ''){
		showWarn("单据未保存，不能删除！");
        return;
	}
	
	$.messager.confirm("确认","确定要删除吗？",function(r) {
		if (r) {
			var url = BasePath + '/bill_backsection_split/deleteBacksectionSplit';
			var idList = $('#id').val() + ',' + $('#backsectionNo').val() + ";";
			var params = {idList : idList};
			ajaxRequestAsync(url,params,function(count){
				if(count) {
					showSuc('删除成功');
					returnTab('mainTab', '单据查询');
					$('#mainTab').tabs('close','单据明细');
					$("#backSplitDataGrid").datagrid('load');
				} else {
					showError('删除失败');
				}
			});
		}
	});
}

/**
 * 编辑界面--返回按钮
 */
billBacksectionSplit.back = function() {
	var changeDatas = $('#backSplitDtlDataGrid').datagrid('getChanges');
	if(changeDatas != null && changeDatas != '') {
		$.messager.confirm("确认","修改尚未保存，确定要返回吗？",function(r) {
			if (r) {
				returnTab('mainTab', '单据查询');
				$('#mainTab').tabs('close','单据明细');
				$("#backSplitDataGrid").datagrid('load');
			}
		});
	}
	else {
		returnTab('mainTab', '单据查询');
		$('#mainTab').tabs('close','单据明细');
		$("#backSplitDataGrid").datagrid('load');
	}
	
};

/**
 * 查询界面--删除按钮
 */
billBacksectionSplit.batchDelBill= function(){
	var checkedRows = $('#backSplitDataGrid').datagrid('getChecked');
    if(checkedRows.length < 1){
        showWarn("请选择要删除的记录!");
        return;
    }
	$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
		if (r) {
			var url = BasePath + '/bill_backsection_split/deleteBacksectionSplit';
			var idList = "";
			$.each(checkedRows, function(index, row) {
				idList+=row.id+','+row.backsectionNo+";";
			});
			var params = {idList : idList}; //{id:idList};//
			ajaxRequestAsync(url,params,function(count){
				if(count) {
					showSuc('删除成功');
					billBacksectionSplit.search();
				} else {
					showError('删除失败');
				}
			});
		}
	});

};

/**
 * 查询界面--导出按钮
 */
billBacksectionSplit.exportExcel = function() {
	var tab = $('#subTab').tabs("getSelected");
	var tabIndex = $('#subTab').tabs('getTabIndex',tab);
	//回款单查询
	if(tabIndex == 0) {
		$.fas.exportExcel({
			dataGridId : "backSplitDataGrid",
			exportUrl : "/bill_backsection_split/do_fas_export",
			exportTitle : "收款回款拆分单列表导出"
		});
	}
	//回款单明细
	else if(tabIndex == 1) {
		$.fas.exportExcel({
			dataGridId : "backSplitDataDtlGrid",
			exportUrl : "/bill_backsection_split_dtl/do_fas_export",
			exportTitle : "收款回款拆分单明细列表导出"
		});
	}
	//
	//回款单明细
	else if(tabIndex == 2) {
		$.fas.exportExcel({
			dataGridId : "backSplitShopBrandDataDtlGrid",
			exportUrl : "/bill_backsection_split_dtl/do_fas_export",
			exportTitle : "收款回款拆分单明细列表导出"
		});
	}
	//
	//回款单明细
	else if(tabIndex == 3) {
		$.fas.exportExcel({
			dataGridId : "backSplitEditDataGrid",
			exportUrl : "/bill_backsection_split_dtl/do_fas_export",
			exportTitle : "收款回款拆分单明细列表导出"
		});
	}
}

/**
 * 新增保存表头
 */
billBacksectionSplit.doPost = function(){
//	var companyNo = $('#companyNoMain_').val();
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理中...'
	});
	$('#mainDataForm').form('submit',{
		url : billBacksectionSplit.modulePath + '/post',
		onSubmit:function(param){
			param.status = 0;
			param.createUser = currentUser.loginName;
			param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
//			param.companyNo = companyNo;
		},
		success:function(data){
			if(data){
				var jsonData = JSON.parse(data);
				//填充主表
				billBacksectionSplit.loadData(jsonData); 
				//生成明细
				billBacksectionSplit.createDtl(jsonData.id, jsonData.backsectionNo);
				$.messager.progress('close');
				showSuc('保存成功');
			}else{
				$.messager.progress('close');
				showError('保存失败');
			}
		}
	});
};

/**
 * 修改保存表头及明细
 */ 
billBacksectionSplit.doPut = function(){
//	var companyNo = $('#companyNo').val();  // companyNoMain_
	$('#mainDataForm').form('submit',{
		url : billBacksectionSplit.modulePath + '/put',
		onSubmit:function(param){
			param.status = 1;
			param.updateUser = currentUser.loginName;
			param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
//			param.companyNo = companyNo;
		},
		success:function(data){
			if(data){
				var jsonData = JSON.parse(data);
				
				var insertedData = $('#backSplitDtlDataGrid').datagrid('getChanges','inserted');
				var updatedData = $('#backSplitDtlDataGrid').datagrid('getChanges','updated');
				//var deletedData = $('#backSplitDtlDataGrid').datagrid('getChanges','deleted');
				var backsectionNo = $('#backsectionNo').val();
				$.each(insertedData, function(index, row) {
					row.backsectionNo = backsectionNo;
				});
				
				var data = {
					inserted : JSON.stringify(insertedData),
					updated : JSON.stringify(updatedData),
					//deleted : JSON.stringify(deletedData),
				};
				ajaxRequestAsync(billBacksectionSplit.dtlmodulePath + '/save',data,function(){
					if(data){
					  	$('#backSplitDtlDataGrid').datagrid('acceptChanges');
					  	//填充主表
						billBacksectionSplit.loadData(jsonData); 
						//填充明细
						billBacksectionSplit.loadBacksectionSplitDtl(backsectionNo);
						showSuc('保存成功');
					}else{
						showError('保存失败');
					}
				});
			}else{
				showError('保存失败');
			}
		}
	});
};

/**
 * 编辑明细行
 */
billBacksectionSplit.editRow = function(rowIndex,data) {
	
	console.log('started ...');
	if(billBacksectionSplit.endEdit()){
		$('#backSplitDtlDataGrid').datagrid('beginEdit',rowIndex);
		billBacksectionSplit.editRowIndex = rowIndex;
	}
	billBacksectionSplit.bindGridEvent(rowIndex);
};

/**
 * 编辑明细行
 */
billBacksectionSplit.editBckSplitRow = function(rowIndex,data) {
	
	console.log('started ...');
	if(billBacksectionSplit.endBckSplitEdit()){
		$('#backSplitEditDataGrid').datagrid('beginEdit',rowIndex);
		billBacksectionSplit.editRowIndex = rowIndex;
	}
	billBacksectionSplit.bindGridEvent(rowIndex);
};

/**
 * 结束明细行编辑
 */
billBacksectionSplit.endBckSplitEdit = function(){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = billBacksectionSplit.editRowIndex;
		if($('#backSplitEditDataGrid').datagrid('validateRow', editRowIndex)){
			$('#backSplitEditDataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

/**
 * 结束明细行编辑
 */
billBacksectionSplit.endEdit = function(){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = billBacksectionSplit.editRowIndex;
		if($('#backSplitDtlDataGrid').datagrid('validateRow', editRowIndex)){
			$('#backSplitDtlDataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

/**
 * 加载单据信息
 */
billBacksectionSplit.loadData = function(resultData){
	$("#companyName").combogrid('disable');
	$("#backNameId").combogrid('disable');
	$('#mainDataForm').form('load', resultData);
};

/**
 * 加载明细行
 */
billBacksectionSplit.loadBacksectionSplitDtl = function(backsectionNo){
	var params = {backsectionNo : backsectionNo};
	var url = billBacksectionSplit.dtlmodulePath + '/get_biz';
	$('#backSplitDtlDataGrid').datagrid({
		url : url,
		queryParams : params
	});
};

/**
 * 编辑界面--新增明细按钮
 */
billBacksectionSplit.insertRow = function() {
	if(!$('#mainDataForm').form('validate')) {
		return;
	}
	
	var pkVal = $('#id').val();
	//新增
	if(pkVal == null || pkVal == ''){
		$('#mainDataForm').form('submit',{
			url : billBacksectionSplit.modulePath + '/post',
			onSubmit:function(param){
				param.status = 0;
				param.createUser = currentUser.loginName;
				param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			},
			success:function(data){
				if(data){
					var jsonData = JSON.parse(data);
					//填充主表
					billBacksectionSplit.loadData(jsonData); 
					showSuc('保存成功');
				}else{
					showError('保存失败');
				}
			}
		});
	}
	
	fas_common_editor.insertRow();
	var rowLen = $("#"+setting.datagridId).datagrid('getRows').length-1;
	if(typeof rowLen == 'undefined' || rowLen < 0) {
		rowLen = 0;
	}
	billBacksectionSplit.editRowIndex = rowLen;
	billBacksectionSplit.bindGridEvent(rowLen);
	
};

billBacksectionSplit.insertRowEditDtl = function() {
	ygDialog({
		title : '批量新增',
		target : $('#myFormPanel'),
		width : 330,
		height : 350,
		buttons : [ {
			text : '确定',
			iconCls : 'icon-save',
			handler : function(dialog) {
				this.saveBills();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
				$('#dataForm').form('clear');
			}
		} ]
	});
};

//新增数据
this.saveBills = function() {
    var fromObj = $('#dataForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理中...'
	});
	$("#dataForm").form('submit', {
        url : BasePath + '/bill_backsection_split_dtl/batchAdds', //batchAdd 
        dataType : 'json',
        onSubmit : function(params) {
        },
        success : function(data) {
    		if (data > 0) {
    			showInfo('批量操作成功！');
    			$.messager.progress('close');
    			dialog.search();
//    					billBacksectionSplit.loadBacksectionSplitDtl(data.backsectionNo);
    			$('#myFormPanel').dialog('close');
    		} else {
    			$.messager.progress('close');
    			showWarn('保存失败，没有对应的业务数据');
    		}
    	}
//        success : function(data) {
////        	var obj = eval("("+data+")"); 
//        	showInfo('批量操作成功！');
//        	$.messager.progress('close');
//        	$('#myFormPanel').dialog('close');
//        },
//        error : function() {
//        	$.messager.progress('close');
//            showError('新增失败,请联系管理员!');
//            showWarn('保存失败，没有对应的业务数据');
//        }
    });
	return true;
};


// 批量生成结算单后的回调函数  \ftl\pages\mallshop_balance\shopBalance_BatchAdd_ResultList.ftl
this.generateBillBatchAdd = function(result){
//	   showWarn("<font color='red'>温馨提示：</font>结算单数据不准确："+result);//.get("errorMsg")+result.get("shopNo"));
		var url = BasePath + '/mall_shopbalance/shopbalance_batchadd_resultlist';
		ygDialog({
			title : '批量生成门店结算回款单处理结果',
			href : url,
			width : 1000,
			height : 'auto',
			isFrame : true,
			modal : true,
			showMask : true,
			buttons: [
			   {
			    text: '取消',
			    handler: function(dialog) {
			    dialog.close();
			   }
			  }],
			  onLoad : function(win, content) {
			  content.loadData(result);
//			  $('#searchForm').form('load', data);
			  $("#btn-search1").click();
			 }
		});
};


billBacksectionSplit.insertRowDtl = function() {
	//新增
	if(pkVal == null || pkVal == ''){
		$('#mainDataForm').form('submit',{
			url : billBacksectionSplit.modulePath + '/post',
			onSubmit:function(param){
				param.status = 0;
				param.createUser = currentUser.loginName;
				param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			},
			success:function(data){
				if(data){
					var jsonData = JSON.parse(data);
					//填充主表
					billBacksectionSplit.loadData(jsonData); 
					showSuc('保存成功');
				}else{
					showError('保存失败');
				}
			}
		});
	}
	
	fas_common_editor.insertRow();
	var rowLen = $("#"+setting.datagridId).datagrid('getRows').length-1;
	if(typeof rowLen == 'undefined' || rowLen < 0) {
		rowLen = 0;
	}
	billBacksectionSplit.editRowIndex = rowLen;
	billBacksectionSplit.bindGridEvent(rowLen);
	
};
billBacksectionSplit.insertRowEditDtl = function() {
	ygDialog({
		title : '批量新增',
		target : $('#myFormPanel'),
		width : 330,
		height : 350,
		buttons : [ {
			text : '确定',
			iconCls : 'icon-save',
			handler : function(dialog) {
				this.saveBills();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
				$('#dataForm').form('clear');
			}
		} ]
	});
};

//新增数据
this.saveBills = function() {
    var fromObj = $('#dataForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理中...'
	});
	$("#dataForm").form('submit', {
        url : BasePath + '/bill_backsection_split_dtl/batchAdds', //batchAdd 
        dataType : 'json',
        onSubmit : function(params) {
        },
        success : function(data) {
    		if (data > 0) {
    			showInfo('批量操作成功！');
    			$.messager.progress('close');
    			dialog.search();
//    					billBacksectionSplit.loadBacksectionSplitDtl(data.backsectionNo);
    			$('#myFormPanel').dialog('close');
    		} else {
    			$.messager.progress('close');
    			showWarn('保存失败，没有对应的业务数据');
    		}
    	}
//        success : function(data) {
//        	var obj = eval("("+data+")"); 
//        	$.messager.progress('close');
//        	return $this.generateBillBatchAdd(obj);
//        },
//        error : function() {
//        	$.messager.progress('close');
//            showError('新增失败,请联系管理员!');
//        }
    });
	return true;
};


// 批量生成结算单后的回调函数  \ftl\pages\mallshop_balance\shopBalance_BatchAdd_ResultList.ftl
this.generateBillBatchAdd = function(result){
//	   showWarn("<font color='red'>温馨提示：</font>结算单数据不准确："+result);//.get("errorMsg")+result.get("shopNo"));
		var url = BasePath + '/mall_shopbalance/shopbalance_batchadd_resultlist';
		ygDialog({
			title : '批量生成门店结算回款单处理结果',
			href : url,
			width : 1000,
			height : 'auto',
			isFrame : true,
			modal : true,
			showMask : true,
			buttons: [
			   {
			    text: '取消',
			    handler: function(dialog) {
			    dialog.close();
			   }
			  }],
			  onLoad : function(win, content) {
			  content.loadData(result);
//			  $('#searchForm').form('load', data);
			  $("#btn-search1").click();
			 }
		});
};


billBacksectionSplit.insertRowDtl = function() {
	//新增
	if(pkVal == null || pkVal == ''){
		$('#mainDataForm').form('submit',{
			url : billBacksectionSplit.modulePath + '/post',
			onSubmit:function(param){
				param.status = 0;
				param.createUser = currentUser.loginName;
				param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			},
			success:function(data){
				if(data){
					var jsonData = JSON.parse(data);
					//填充主表
					billBacksectionSplit.loadData(jsonData); 
					showSuc('保存成功');
				}else{
					showError('保存失败');
				}
			}
		});
	}
	
	fas_common_editor.insertRow();
	var rowLen = $("#"+setting.datagridId).datagrid('getRows').length-1;
	if(typeof rowLen == 'undefined' || rowLen < 0) {
		rowLen = 0;
	}
	billBacksectionSplit.editRowIndex = rowLen;
	billBacksectionSplit.bindGridEvent(rowLen);
	
};
/**
 * 编辑界面--生成明细按钮
 */
billBacksectionSplit.insertRows = function() {
	if(!$('#mainDataForm').form('validate')) {
		return;
	}
	
	var isContinue = false;
	
	var datas = $('#backSplitDtlDataGrid').datagrid('getRows');
	if(datas != null && datas != '') {
		$.messager.confirm("确认","将删除已有的明细记录，确定要重新生成?",function(r) {
			if (r) {
				billBacksectionSplit.freshDtl();
			}
		});
	}
	else {
		billBacksectionSplit.freshDtl();
	}
	
};

//生成明细
billBacksectionSplit.freshDtl = function() {
	var pkVal = $('#id').val();
	var url;
	//新增
	if(pkVal == null || pkVal == ''){
		url = billBacksectionSplit.modulePath + '/post';
	}
	//修改
	else {
		url = billBacksectionSplit.modulePath + '/put';
	}
	
	//先保存表头
	$('#mainDataForm').form('submit',{
		url : url,
		onSubmit:function(param){
			//新增
			if(pkVal == null || pkVal == ''){
				param.status = 0;
				param.createUser = currentUser.loginName;
				param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			}
			//修改
			else {
				param.status = 1;
				param.updateUser = currentUser.loginName;
				param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			}
		},
		success:function(data){
			if(data){
				var jsonData = JSON.parse(data);
				//填充主表
				billBacksectionSplit.loadData(jsonData); 
				//生成明细
				billBacksectionSplit.createDtl(jsonData.id, jsonData.backsectionNo);
				showSuc('保存成功');
			}else{
				showError('保存失败');
			}
		}
	});
}

/*
 * 根据回款单编号生成明细
 */
billBacksectionSplit.createDtl = function(id, backsectionNo) {
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理中...'
	});
	$.ajax({
	   type: "POST",
	   url: billBacksectionSplit.dtlmodulePath + '/add_insert_dtl.json',
	   data: "id=" + id,
	   success: function(data){
		   billBacksectionSplit.loadBacksectionSplitDtl(backsectionNo);
		   if(data.count == 0) {
			   showWarn('明细生成失败，应收余额为0，回款完毕。请检查需要回款的结算单是否已做开票申请！');
		   }
		   $.messager.progress('close');
	   }
	});
}

/**
 * 编辑明细界面--删除明细按钮
 */
billBacksectionSplit.deleteRows = function() {
	
	//获取选中行
	var checkedRows = $('#backSplitDtlDataGrid').datagrid('getChecked');
    if(checkedRows.length < 1){
        showWarn("请选择要删除的记录!");
        return;
    }
    //删除未保存的行
    for(var i=0; i<checkedRows.length; i++){
    	var row = checkedRows[i];
    	var index = $('#backSplitDtlDataGrid').datagrid('getRowIndex', row);
    	if(row.id == null) {
    		$('#backSplitDtlDataGrid').datagrid('deleteRow', index);
    		if(index < billBacksectionSplit.editRowIndex) {
    			billBacksectionSplit.editRowIndex--;
    		}
    		else if(index <= billBacksectionSplit.editRowIndex) {
    			billBacksectionSplit.editRowIndex = -1;
    		}
    	}
	}
    //重新获取选中行
    checkedRows = $('#backSplitDtlDataGrid').datagrid('getChecked');
    if(checkedRows.length < 1){
        return;
    }
	$.messager.confirm("确认","确定要删除已保存的明细?",function(r) {
		if (r) {
			var data = {
					deleted : JSON.stringify(checkedRows),
			};
			ajaxRequestAsync(billBacksectionSplit.dtlmodulePath + '/save',data,function(){
				if(data){
					//删除已保存的行
					for(var i=0; i<checkedRows.length; i++){
				    	var row = checkedRows[i];
				    	var index = $('#backSplitDtlDataGrid').datagrid('getRowIndex', row);
			    		$('#backSplitDtlDataGrid').datagrid('deleteRow', index);
			    		if(index < billBacksectionSplit.editRowIndex) {
			    			billBacksectionSplit.editRowIndex--;
			    		}
			    		else if(index <= billBacksectionSplit.editRowIndex) {
			    			billBacksectionSplit.editRowIndex = -1;
			    		}
					}
				  	//$('#backSplitDtlDataGrid').datagrid('acceptChanges');
					showSuc('删除成功');
				}else{
					showError('删除失败');
				}
			});
		}
	});
}

//当前编辑行
billBacksectionSplit.editRowIndex = -1;

billBacksectionSplit.getRowNo = function(field){
	var noEd = $('#backSplitDtlDataGrid').datagrid('getEditor', {
		'index' : billBacksectionSplit.editRowIndex,
		'field' : field
	});
	return noEd.target.val();
};

/**
 * 绑定表格事件
 */
billBacksectionSplit.bindGridEvent = function(rowIndex){
	try{
		var objGrid = $("#backSplitDtlDataGrid"); //表格对象
		
		var rAmoutEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'receiveAmount'}); //应收款
		var arAmoutEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'alreadyReceiveAmount'}); //前期累计回款
		var tpAmoutEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'thePaymentAmount'}); //本次回款金额
		
		var dfAmoutEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'diffAmount'}); //回款差异
		
		//本次回款金额 改变绑定事件
		$(tpAmoutEdt.target).bind("change",function(){
			var rAmoutValue = $(rAmoutEdt.target).val();
			var arAmoutValue = $(arAmoutEdt.target).val();
			var tpAmoutValue = $(tpAmoutEdt.target).val();
			$(dfAmoutEdt.target).val((rAmoutValue - arAmoutValue - tpAmoutValue).toFixed(2));
		});
		
	}
	catch(e){
		console.log(e);
	}
	
};

/**
 * 导入按钮
 */
billBacksectionSplit.doImport = function() {
	$.importExcel.open({
		'submitUrl' : BasePath + '/bill_backsection_split/do_import',
		'templateName' : '收款回款拆分导入.xlsx',
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
};

/**
 * 导入按钮(品牌)
 */
billBacksectionSplit.doImportBrand = function() {
	$.importExcel.open({
		'submitUrl' : BasePath + '/bill_backsection_split/do_import',
		'templateName' : '收款回款拆分(店+品牌部)导入.xlsx',
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
};

