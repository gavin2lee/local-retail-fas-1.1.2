//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if ('0' == obj) {
			return true;
		}
		return false;
	}
	return true;
};

function InvoiceInfoSetDialog() { 
	var $this = this;
	
	// 首选
	this.enable = function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
		if(checkedRows.length < 1) {
			showWarn("请选择要首选的记录!");
			return;
		}
		var url = BasePath + options.url, enableFlag = true, enableLen = "";
		// 校验数据是否可删除
	    if(typeof options.checkEnable === 'function') {
	    	enableFlag = options.checkEnable(checkedRows);
	    }
		 // 校验数据是否可激活
	    if(!enableFlag) {
	        return;
	    }
		if(typeof checkedRows != "undefined" && checkedRows != null) {
			enableLen = checkedRows.length;
		}
		$.messager.confirm("确认","你确定要首选这"+enableLen+"条数据", function(r) {  
	        if(r) {
	        	var enableList = options.buildEnableData(checkedRows);
	        	if(enableList.length > 0) {
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(enableList);
		            ajaxRequest(url, effectRow, function(result) {
		        		options.enableCallback(options, result);
		        	}); 
	        	} else {
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	};
	// 备选
	this.unable = function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
		if(checkedRows.length < 1) {
			showWarn("请选择要备选的记录!");
			return;
		}
		var url = BasePath + options.url, unableFlag = true, unableLen = "";
		// 校验数据是否可删除
	    if(typeof options.checkUnable === 'function') {
	    	unableFlag = options.checkUnable(checkedRows);
	    }
		 // 校验数据是否可注销
	    if(!unableFlag) {
	        return;
	    }
		if(typeof checkedRows != "undefined" && checkedRows != null) {
			unableLen = checkedRows.length;
		}
		$.messager.confirm("确认","你确定要备选这"+unableLen+"条数据", function(r) {  
	        if(r) {
	        	var unableList = options.buildUnableData(checkedRows);
	        	if(unableList.length > 0) {
		            var effectRow = new Object();
		            effectRow["deleted"] = JSON.stringify(unableList);
		            ajaxRequest(url, effectRow, function(result) {
		            	options.unableCallback(result, options);
		        	}); 
	        	} else {
	        		showError("操作失败!");
	        	}
	        }  
	    });  
	};

	this.checkEnable = function(checkedRows) {
		var enableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				showWarn("存在类别状态为首选的记录，请重新选择！");
				enableFlag = false;
				return false;
	 	    }
		});
		return enableFlag;
	};
	
	this.checkUnable = function(checkedRows) {
		var unableFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '0'){
				showWarn("存在类别状态为备选的记录，请重新选择！");
				unableFlag = false;
				return false;
	 	    }
		});
		return unableFlag;
	};
	
	this.doEnable = function() {	
		var $this = this;
	    $this.enable({
	    	dataGridId : "invoiceRuleSetDataGrid",
	        url : "/base_setting/invoice_info_set/do_enableone",
	        searchBtn : "btn-search",
	        checkEnable : $this.checkEnable,
	        buildEnableData : $this.buildEnableData,
	        enableCallback : $this.enableCallback
	    });
	};
	
	this.buildEnableData = function(checkedRows) {
	    var enableList = [];
	    $.each(checkedRows, function(index, item) {
	        var obj = new Object();
	        obj.id = item.id;
	        obj.companyNo = item.companyNo;
	        obj.clientNo = item.clientNo;
	        enableList.push(obj);
	    });
	    return enableList;
	};
	
	//组装激活操作后的回调函数
	this.enableCallback = function(options, result) {
	    if(result) {
	    	if(result.message!=null&&result.message!=""){
				showWarn(result.message);
			}else{
				showInfo("首选操作成功!");
			}
	    	$("#btn-search").click();
	    } else {
	        showError("首选操作失败,请联系管理员!");
	    }
	};
	
	this.doUnable = function() {
	    var $this = this;
	    $this.unable({
	        dataGridId : "invoiceRuleSetDataGrid",
	        url : "/base_setting/invoice_info_set/do_unableone",
	        searchBtn : "btn-search",
	        checkUnable : $this.checkUnable,
	        buildUnableData : $this.buildUnableData,
	        unableCallback : $this.unableCallback
	    });
	};

	this.buildUnableData = function(checkedRows) {
	    var unableList = [];
	    $.each(checkedRows, function(index, item){
	        var obj = new Object();
	        obj.id = item.id;
	        obj.companyNo = item.companyNo;
	        obj.clientNo = item.clientNo;
	        unableList.push(obj);
	    });
	    return unableList;
	};
	
	//组装激活操作后的回调函数
	FasDialogController.prototype.unableCallback = function(result, options) {
	    if(result) {
	    	if(result.message!=null&&result.message!=""){
				showWarn(result.message);
			}else{
				showInfo("备选操作成功!");
			}
	        $("#btn-search").click();
	    } else {
	        showError("备选操作失败,请联系管理员!");
	    }
	};

}


function InvoiceInfoSetEditor() {
	var $this = this;
	
	this.endEditing = function(comboboxData){
		var tempObj = $('#invoiceRuleSetDataGrid');
		var rowArr = tempObj.datagrid('getRows');
	    for (var i = 0; i < rowArr.length; i++) {
	    	if(tempObj.datagrid('validateRow', i)){
	    		tempObj.datagrid('endEdit', i);
	    	}else{
	    		return false;
	    	}
	    }
	    return true;
	};
	
	
	this.save =function(win){
		if(this.endEditing()) {
			var insertedData = $("#invoiceRuleSetDataGrid").datagrid('getChanges','inserted');
			var updatedData = $("#invoiceRuleSetDataGrid").datagrid('getChanges','updated');
			var deletedData = $("#invoiceRuleSetDataGrid").datagrid('getChanges','deleted');
			var data = {
				inserted : JSON.stringify(insertedData),
				updated : JSON.stringify(updatedData)
			};
			var deleteList = [];
	    	$.each(deletedData, function(index, item){
	    		var obj = new Object();
	    		obj["id"] = item["id"];
	    		obj["companyNo"] = item["companyNo"];
	    		obj["clientNo"] = item["clientNo"];
	    		obj["status"] = item["status"];
	    		deleteList.push(obj);
	    	});
	    	if(deleteList.length > 0) {
	            data.deleted = JSON.stringify(deleteList);
	    	}
			ajaxRequest(BasePath+"/base_setting/invoice_info_set/save", data, function(result){
				if(result) {
					if(result.message!=null&&result.message!=""){
						showWarn(result.message);
					}else{
						showInfo("操作成功!");
					}
					invoiceSet.search();
					//如果是弹出页面，则关闭页面
					if(win) {
						win.close();
					}
					return;
				} else {
					showError("操作失败,请联系管理员!");
				}
			});
		}
	}
	
	/*this.checkUpdate = function(options, rowIndex, rowData) {
		var status = rowData.status;
		if(status == '1' ){
		}
		return true;
	};*/
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.status == '1'){
				delFlag = false;
				return false;
	 	    }
		});
		return delFlag;
	};
	
	this.deleteRow = function() {
	    var $this = this;
	    if(!$this.checkDel($this.options)) {
	    	$.messager.confirm("确认","选中的记录中包含首选数据确认要删除?",function(r) {
				if (r) {
					$.fas.deleteEditorRow({
						dataGridId : $this.options.dataGridId || 'dtlDataGridDiv',
						checkDel : $this.options.checkDel
					});
				}
			});
	    }else{
	    	$.messager.confirm("确认","你确定要删除选中的记录?",function(r) {
				if (r) {
					$.fas.deleteEditorRow({
						dataGridId : $this.options.dataGridId || 'dtlDataGridDiv',
						checkDel : $this.options.checkDel
					});
				}
			});
	    }
	};

	this.clientTypeFormatter = function(value, rowData, rowIndex) {
		for(var i = 0; i < clientData.length; i++) {
			if(clientData[i].typeNo == value) {
				return clientData[i].typeName;
			}
		}
		return "";
	};
	
	this.statusformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':'0', 'text': '备选'}, {'value':'1', 'text':'首选'}];
	    for(var i = 0; i < dataStatusType.length; i++) {
	        if(dataStatusType[i].value == value) {
	            return dataStatusType[i].text;
	        }
	    }
	    return "";
	};
	
	this.invoiceTypeformatter = function(value, rowData, rowIndex) {
		var dataStatusType = [{'value':'0', 'text': '普通发票'}, {'value':'1', 'text':'增值票'}];
		for(var i = 0; i < dataStatusType.length; i++) {
			if(dataStatusType[i].value == value) {
				return dataStatusType[i].text;
			}
		}
		return "";
	};
	
	
}

var invoiceSet = null, editor = null;
var clientData=new Object();
$(function() {
	$.fas.extend(InvoiceInfoSetDialog, FasDialogController);
	$.fas.extend(InvoiceInfoSetEditor, FasEditorController);
	invoiceSet = new InvoiceInfoSetDialog();
	invoiceSet.init("/base_setting/invoice_info_set", {
		dataGridId : "invoiceRuleSetDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		enableUrl : "/do_enableone",
		unableUrl : "/do_unableone",
		exportTitle:'开票信息导出',
		exportUrl:"/do_fas_export"
	});
	
	editor = new InvoiceInfoSetEditor();
	editor.init("/base_setting/invoice_info_set", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'invoiceRuleSetDataGrid',
		saveUrl : "/base_setting/invoice_info_set/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			var status = rowData.status;
			if(status == '1' ){
				$('#companyName_,#clientName_').combogrid("disable");
				$('#clientTypeId').combobox("disable");
			}
		},
		keyboard : true
	});
	

	// 加载客户类型
	$('#clientTypeCond').combobox({
		url : BasePath + '/base_setting/invoice_info_set/getClientType',
		valueField : 'typeNo',
		textField : 'typeName',
		onLoadSuccess : function(data) {
			clientData = data;
		}
	}); 
	
	invoiceSet.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/base_setting/invoice_info_set/do_import',
			'templateName' : '开票信息导入模板.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (isNotBlank(data.error)) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showInfo('数据导入成功');
						invoiceSet.search();
					}
				} else {
					showWarn('导入失败,请联系管理员!');
				}
			},
			error : function() {
				$.messager.progress('close');
				showWarn('数据导入失败，请联系管理员');
			}
		});
	};
});

function testChange(newValue, oldValue) {
	var ed = $('#invoiceRuleSetDataGrid').datagrid('getEditor', {
		index : $.fas.editIndex,
		field : 'clientName'
	});
	if(newValue == '2') {
		$(ed.target).combogrid({
			delay: 700,
			panelWidth : 400,
			panelHeight : 300,
			url : BasePath + "/base_setting/customer/list.json",
			idField : "fullName",
			textField : "fullName",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
               {field : 'customerNo',title : '客户编码',width : 100,align:'left'},
               {field : 'fullName',title : '客户全称',width : 150,align:'left'},
          	   {field : 'shortName',title : '客户简称',width : 150,align:'left'}
            ]],
            onHidePanel:function(data){
            	var data = $(ed.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#clientNo_').val(data.customerNo);
      				$('#clientName_').val(data.fullName);
      				$('#clientShortName_').val(data.shortName);
  				}
  			}
		});
	}else if(newValue == '1'){
		$(ed.target).combogrid({
			delay: 700,
			panelWidth : 400,
			panelHeight : 300,
			url : BasePath + "/base_setting/company/list.json?dataAccess=0",
			idField : "name",
			textField : "name",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
               {field : 'companyNo',title : '公司编码',width : 80,align:'left'},
          	   {field : 'name',title : '公司名称',width : 150,align:'left'}
            ]],
            onHidePanel:function(data){
            	var data = $(ed.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#clientNo_').val(data.companyNo);
      				$('#clientName_').val(data.name);
  				}
  			}
		});
	}else if(newValue == '3'){
		$(ed.target).combogrid({
			delay: 700,
			panelWidth : 400,
			panelHeight : 300,
			url : BasePath + '/mall/list.json',
			idField : "name",
			textField : "name",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
               {field : 'mallNo',title : '商场编码',width : 100,align:'left'},
          	   {field : 'name',title : '商场名称',width : 150,align:'left'}
            ]],
            onHidePanel:function(data){
            	var data = $(ed.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#clientNo_').val(data.mallNo);
      				$('#clientName_').val(data.name);
  				}
  			}
		});
	}else if(newValue == '4'){
		$(ed.target).combogrid({
			delay: 700,
			panelWidth : 400,
			panelHeight : 300,
			url : BasePath + '/bsgroups/list.json',
			idField : "name",
			textField : "name",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
			     {field : 'bsgroupsNo',title : '商业集团编码',width : 120, halign : 'center', align : 'left'},
		    	 {field : 'name',title : '商业集团名称',width : 260, halign : 'center', align : 'left'}
            ]],
            onHidePanel:function(data){
            	var data = $(ed.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#clientNo_').val(data.bsgroupsNo);
      				$('#clientName_').val(data.name);
  				}
  			}
		});
	}else if(newValue == '5'){
		$(ed.target).combogrid({
			delay: 700,
			panelWidth : 400,
			panelHeight : 300,
			url : BasePath + '/base_setting/supplier/list.json',
			idField : "fullName",
			textField : "fullName",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
		          {field : 'supplierNo',title : '供应商编码',width : 100,align:'left'},
		          {field : 'fullName',title : '供应商全称',width : 200,align:'left'},
	          	  {field : 'shortName',title : '供应商简称',width : 150,align:'left'}
            ]],
            onHidePanel:function(data){
            	var data = $(ed.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#clientNo_').val(data.supplierNo);
      				$('#clientName_').val(data.fullName);
      				$('#clientShortName_').val(data.shortName);
  				}
  			}
		});
	}
}
