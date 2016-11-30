
var supplierGroupType = {};

/*add by wang.m*/
/**
 * 引入未分组供应商
 */
supplierGroupType.importNoGroup = function(){
	var groupNo = $('#groupNo').val();
	if(groupNo==''){
		alert("请先保存表头信息后操作！");
		return ;
	}
	ygDialog({
		title : '未分组供应商列表',
		href : BasePath + '/supplier_group/list_no_group',
		width : 500,
		height : 'auto',
		isFrame : true,
		modal : true,
		showMask : true,
		buttons: [{
			id:'sure',
            text: '确认',
            handler: function(dialog) {
            	var checkedRows = supplierGroupType.getChecked();
            	var params = {checkedRows:JSON.stringify(checkedRows),groupNo:groupNo};
            	ajaxRequestAsync(BasePath + '/supplier_group/importNoGroup', params, function(data){
            		if(data.success){
            			dialog.close();
						showSuc('保存成功');
						supplierGroupType.refreshDtl();
					}else{
						 dialog.close();
						showError('保存失败');
					}
            	});
            }
        },
        {
            text: '取消',
            handler: function(dialog) {
                dialog.close();
            }
        }],
		onLoad : function(win, content) {
			supplierGroupType.getChecked = content.getChecked;
		}
	});
}

supplierGroupType.statusApproved = [{"value":"0","text":"未审核"},
                      {"value":"1","text":"已审核"}];

supplierGroupType.statusApproveFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < supplierGroupType.statusApproved.length; i++) {
		if (supplierGroupType.statusApproved[i].value == value) {
			return supplierGroupType.statusApproved[i].text;
		}
	}
};

var setting = {
		datagridId : "dtlDataGridDiv",
		primaryKey : "id",
		saveUrl : "/supplier_group_rel/save",
		saveCallback : function(){
			
		},
		initRow : function(){
			return {groupNo : $("#groupNo").val()};
		},
		initData : {
			combobox : {
				
			}
		}
	};

supplierGroupType.refreshDtl = function() {
	var queryMxURL = BasePath + "/supplier_group_rel/get_biz?sort=update_time&order=asc&groupNo="+$("#groupNo").val();
	$("#dtlDataGridDiv").datagrid('options').url = queryMxURL;
	$("#dtlDataGridDiv").datagrid('load');
	$(":checkbox:eq(0)").attr("checked", false);
};
	
supplierGroupType.IsSupplierAlreadyBelongTo = function(data) {
	var checkParams = {"supplierNo" : data.supplierNo};
	var checkAlreadyBelongTo = false;
 	$.ajax({
		  type: 'POST',
		  url: BasePath + "/supplier_group_rel/get_count.json",
		  data: checkParams,
		  cache: true,
		  async:false, // 一定要
		  dataType: 'text', 
		  success: function(totalData){
			  totalData = parseInt(totalData,10);
			  if(totalData>0){
				  checkAlreadyBelongTo = true;
			  }
		  }
     });
	
 	if(checkAlreadyBelongTo){
 		showError('该供应商已经属于某个供应商组,不能重复!');
		return;
 	}else{
 		$("#supplierNo").val(data.supplierNo);
 		$("#supplierName").val(data.fullName);
 	}
};

//检查行数据是否有重复
supplierGroupType.checkRowUnique = function() {
	var rows = $("#dtlDataGridDiv").datagrid("getRows");
	if(rows.length == 0) {
		 return true;
	}
	var supplierNos = [];
	$.each(rows, function(index, row){
		supplierNos.push(row.supplierNo);
    });
	var supplierLen = supplierNos.length;
	$.unique(supplierNos);
	var uniqueSupplierLen = supplierNos.length;
	if(supplierLen != uniqueSupplierLen) {
		showWarn("行数据不能重复!");
		return false;
	}
	return true;
};

supplierGroupType.checkExistFun = function(url,checkColumnJsonData){
	var checkExist=false;
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkColumnJsonData,
		  cache: true,
		  async:false, // 一定要
		  dataType: 'text', 
		  success: function(totalData){
			  totalData = parseInt(totalData,10);
			  if(totalData>0){
				  checkExist=true;
			  }
		  }
     });
 	return checkExist;
};

//公共ajax请求（不是异步，ajax方法执行完之后，再执行后面的js的代码）
supplierGroupType.checkSupplierGroupUsed = function(param) {
	var ret = "";
	$.ajax({
		  type: 'POST',
		  url: BasePath + "/supplier_group/check_supplier_group_used.json",
		  data: param,
		  cache: true,
		  async : false,
		  dataType : 'json',
		  success: function(result) {
			  if(result && !result.success) {
				  ret = result.message;
				}
		  }
	});
	return ret;
};

var extra_operate = {		
	//检查新增的数据是否正确
	checkSave : function() {
		
		if($("#id").val() != ''){
			return true;
		}
		
		var check_data = {"groupNo" : $("#groupNo").val()};
		var checkUrl = BasePath + "/supplier_group/get_count.json";
		if (supplierGroupType.checkExistFun(checkUrl, check_data)) {
			showError('供应商组编码已存在,不能重复!');
			return false;
		}
		
		var checkGroupName = {"groupNameUnique" : $("#groupName").val()};
		var checkNameUrl = BasePath + "/supplier_group/get_count.json";
		if (supplierGroupType.checkExistFun(checkNameUrl, checkGroupName)) {
			showError('供应商组名称已存在,不能重复!');
			return false;
		}
		
		if(fas_common_editor.endEditing()) {
			var rowUnique = supplierGroupType.checkRowUnique();
			return rowUnique;
		}
		return true;
	},
	checkUpdate : function(){
		if(fas_common_editor.endEditing()) {
			var rowUnique = supplierGroupType.checkRowUnique();
			return rowUnique;
		}
		
	},
	initAdd : function() {
		var  organTypeNo =  currentUser.organTypeNo;
		$("#id").val('');
		$("#groupNo").removeClass("readonly").removeAttr("readonly", true);
		$("#groupName").removeClass("readonly").removeAttr("readonly", true);
		$('#dtlDataGridDiv').datagrid('loadData', { total: 0, rows: [] }); 
		$("#organTypeNo").val(organTypeNo);	
	},
	initUpdate : function(rowData) {
//		var resultData = supplierGroupType.checkSupplierGroupUsed(rowData);
//		if(resultData != ''){
//			showError(resultData);
//			return false;
//		};
		if(rowData.auditStatus == 1){
			showError('该供应商组已审核,不能编辑!');
			return false;
		};
		
		return true;
	},
	loadedUpdate : function(rowData) {
		if(rowData.auditStatus == 1) {
//			showWarn("数据已启用，不允许修改!");
			$("#dataFormView input").addClass("readonly").attr("readonly", true);
			var linkbuttons = $("#dtltoolbarView").find("a");
	    	for(var i = 0; i < linkbuttons.length; i++) {
	    		$(linkbuttons[i]).linkbutton('disable');
	    	}
			$.fas.search({
				dataGridId : "dtlDataGridDivView",
				searchUrl: "/supplier_group_rel/get_biz?sort=update_time&order=asc&groupNo="+$("#groupNos").val()
			});
		}else{
			$("#groupNo").addClass("readonly").attr("readonly", true);
			$("#groupName").addClass("readonly").attr("readonly", true);
			fas_common.search({
				dataGridId : "dtlDataGridDiv",
				url : "/supplier_group_rel/get_biz?sort=update_time&order=asc&groupNo="+$("#groupNo").val()
			});
			fas_common_editor.editIndex = undefined;
		}
		
	},
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},
	saveCallback : function(result, setting) {
			$("#id").attr("value", "");
			$("#" + setting.searchBtn).click();
	},
	checkAudit : function(checkedRows) {
		var resultCheck = true;
		$.each(checkedRows, function(index, item){
			
//			var resultData = supplierGroupType.checkSupplierGroupUsed(item);
//			if(resultData != ''){
//				showError(resultData);
//				resultCheck = false;
//				return false;
//			};
			
			if(item.auditStatus == 1){
				showError('有已审核状态的记录！,请重新选择！');
				resultCheck = false;
				return false;
			};
		});     
		return resultCheck;
	},
	checkAntiAudit : function(checkedRows) {
		var resultCheck = true;
		$.each(checkedRows, function(index, item){

//			var resultData = supplierGroupType.checkSupplierGroupUsed(item);
//			if(resultData != ''){
//				showError(resultData);
//				resultCheck = false;
//				return false;
//			};
			
			if(item.auditStatus == 0){
				showError('有未审核状态的记录！,请重新选择！');
				resultCheck = false;
				return false;
			};
		});     
		return resultCheck;
	},
	initSubmitParams : function() {
		var params = [];
		var effectRow = getChangeTableDataCommon("dtlDataGridDiv");
		var deleted = "";
		var deletedData = $("#dtlDataGridDiv").datagrid('getChanges','deleted');
		var deleteList = [];
    	$.each(deletedData, function(index, item){
    		var obj = new Object();
    		obj.id = item.id;
    		deleteList.push(obj);
    	});
    	if(deleteList.length > 0) {
            deleted = JSON.stringify(deleteList);
    	}
		params.push({name : 'inserted', value : effectRow.inserted});
		params.push({name : 'updated', value : effectRow.updated});
		params.push({name : 'deleted', value : deleted});
		return params;
	}
};

//结算大类列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "groupNo=" + rowData.groupNo;
};

supplierGroupType.searchDtl = function(rowData, hasParam) {
	var fromDtlSearchData = convertArray($("#dtlSearchForm").serializeArray());
	var queryDtlURL = BasePath +'/supplier_group_query/query.json';
	$("#dtlSearchDataGrid").datagrid('options').queryParams = eval("("
			+ fromDtlSearchData + ")");
	$("#dtlSearchDataGrid").datagrid('options').url = queryDtlURL;
	$("#dtlSearchDataGrid").datagrid('load');
};

supplierGroupType.clearDtl = function(rowData, hasParam) {
	$('#dtlSearchForm').form("clear");
};

supplierGroupType.initStatusCondition = function(comboboxId) {
	$('#' + comboboxId).combobox({
		data:supplierGroupType.statusApproved,
	    valueField:'value',    
	    textField:'text',
	    panelHeight:100
	});
};

//导出
supplierGroupType.exportDtl = function() {
	var $dg = $("#dtlSearchDataGrid");
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	
	var subGrepColumns = $dg.datagrid('options').subColumns;
	
	var columns = $.grep(grepColumns[0], function(o, i) {
		if ($(o).attr("notexport") == true) {
			return true;
		}
		return false;
	}, true);
	
	var subColumns = [];
	if(typeof subGrepColumns != 'undefined' 
			&& subGrepColumns != null
			&& subGrepColumns != "") {
		subColumns = $.grep(subGrepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}
	
	var exportColumns = JSON.stringify(columns);
	var exportSubColumns = JSON.stringify(subColumns);
	var url = BasePath + "/supplier_group_query/do_fas_export";
	var dataRow = $dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.exportColumns = exportColumns;
				param.exportSubColumns = exportSubColumns;
				param.fileName = "供应商组明细信息导出";
				param.exportType = "common";
				if(params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
					});
				}
			},
			success : function() {

			}
		});
	} else {
		showWarn('查询记录为空，不能导出!');
	}
};

supplierGroupType.doImport = function() {
	var validateForm = $('#dataForm').form('validate');
	//校验必填项
	if (validateForm == false) {
		return;
	}
	if($("#id").val() == ''){
		var check_data = {"groupNo" : $("#groupNo").val()};
		var checkUrl = BasePath + "/supplier_group/get_count.json";
		if (supplierGroupType.checkExistFun(checkUrl, check_data)) {
			showError('供应商组编码已存在,不能重复!');
			return false;
		}
		
		var checkGroupName = {"groupNameUnique" : $("#groupName").val()};
		var checkNameUrl = BasePath + "/supplier_group/get_count.json";
		if (supplierGroupType.checkExistFun(checkNameUrl, checkGroupName)) {
			showError('供应商组名称已存在,不能重复!');
			return false;
		}
		
		$('#dataForm').form('submit', {
			url : BasePath + '/supplier_group/save_all',
			dataType : 'json',
			onSubmit : function(params) {
			},
			success : function(result) {
				if(result && (JSON.parse(result).success 
						|| typeof JSON.parse(result).success == 'undefined')) {
					$("#groupNo").addClass("readonly").attr("readonly", true);
					$("#groupName").addClass("readonly").attr("readonly", true);
					
					var paramsMap = {"groupNo" : $("#groupNo").val()};
					$.ajax({
						  type: 'POST',
						  url: BasePath + "/supplier_group/get_unique_module",
						  data: paramsMap,
						  cache: true,
						  async:false, // 一定要
						  dataType: 'json', 
						  success: function(resultData){
							  if(resultData){
								  $("#id").val(resultData.id);
							  }else{
								  showError('查询供应商组失败!');
								  return false;
							  }			 
						  }
				     });
					
					$.importExcel.open({
						'submitUrl' : BasePath + "/supplier_group_rel/do_import?groupNo="+$("#groupNo").val(),
						'templateName' : '供应商组导入.xlsx',
						success : function(data) {
							$.messager.progress('close');
							if (data) {
								if (isNotBlank(data.error)) {
									showError(data.error);
								}else{
									$.importExcel.colse();
									showSuc('数据导入成功');
									supplierGroupType.refreshDtl();
								}
							}else{
								showInfo('导入失败,请联系管理员!');
							}
						},
						error : function() {
							$.messager.progress('close');
							showWarn('数据导入失败，请联系管理员');
						}
					});
				} else {
					showError('保存主档和明细失败,请联系管理员!');
				}
			},
			error : function() {
				showError('保存主档和明细失败,请联系管理员!');
			}
		});
	}else{
		
		$('#dataForm').form('submit', {
			url : BasePath + '/supplier_group/save_all',
			dataType : 'json',
			onSubmit : function(params) {
			},
			success : function(result) {
				if(result && (JSON.parse(result).success 
						|| typeof JSON.parse(result).success == 'undefined')) {
					$.importExcel.open({
						'submitUrl' : BasePath + "/supplier_group_rel/do_import?groupNo="+$("#groupNo").val(),
						'templateName' : '供应商组导入.xlsx',
						success : function(data) {
							$.messager.progress('close');
							if (data) {
								if (isNotBlank(data.error)) {
									showError(data.error);
								} else {
									$.importExcel.colse();
									showSuc('数据导入成功');
									supplierGroupType.refreshDtl();
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
				} else {
					showError('新增失败,请联系管理员!');
				}
			},
			error : function() {
				showError('新增失败,请联系管理员!');
			}
		});
	}
	
};

supplierGroupType.showSupplierNoGroupDialog = function(){
	ygDialog({
		title : '未分组供应商',
		target : $('#supplierNoGroup'),
		width :  660,
		height : 500,
		});
	$("#supplierNoGroupDataGridDiv").datagrid('options').url = BasePath + '/supplier_group/supplierNoGroup.json';
	$("#supplierNoGroupDataGridDiv").datagrid('load');//加载数据
}

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};

$(document).ready(function(){
	window.onload=supplierGroupType.showSupplierNoGroupDialog;
	$('#mainTab').tabs('add', {
		title : '供应商组查询',
		selected : false,
		closable : false,
		href : BasePath + "/supplier_group/list_tabMain",
		onLoad : function(panel) {
			// 这里需要在重写在加载完后做对应的事件
			supplierGroupType.initStatusCondition("statusCondition");
		}
	});
	
	ajaxRequestAsync(BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
});




