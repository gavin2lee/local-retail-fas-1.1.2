// 弹出框
function CustomerReceRelDialog() {
	var $this = this;
	
	this.initAdd = function() {
		$('#dataForm').form("clear");
		$('#dataForm').find("input[type=hidden]").val("");
		$('#marginControlFlag').attr("checked",false);
		$('#bottombarMx').find('span').text("");
		/*deleteAllGridCommon('dtlDataGridDiv');
		$('#dtlDataGridDiv').datagrid('acceptChanges');*/
		$this.super.initAdd.call(this);
	};
	this.loadedAdd = function() {
		$("#marginAmount").addClass("readonly").attr("readonly",true);
	};
	//检查新增的数据是否正确
	this.checkAdd = function() {
		return $this.validateData();
	};
	
	this.checkInitUpdate = function(rowData) {
		if(rowData.status != '1'){
			$.fas.ajaxRequestAsync(BasePath + '/customer_rece_rel/check_update', {customerNo:rowData.customerNo, companyNo:rowData.companyNo, termNo:rowData.termNo}, function(result){
				if(result > 0){
					showInfo("已收款的客户条款不允许变更!");
					rowData.updateCheck = 'true';
				}
			});
		}else{
			showInfo("已启用的客户条款不允许变更!");
		}
		$("#companyNameView").settlecategorybox("disable");
		$("#termNameView").settlecategorybox("disable");
		$("#customerNameView").settlecategorybox("disable");
		$("#dataFormView input").addClass("readonly").attr("readonly", true);
		$("#returnOwnFlagView").attr("disabled", "disabled");
	    var linkbuttons = $("#dtltoolbarView").find("a");
    	for(var i = 0; i < linkbuttons.length; i++) {
    		$(linkbuttons[i]).linkbutton('disable');
    	}
    	if(rowData.marginControlFlag && rowData.marginControlFlag=='1'){
    		$('#returnOwnFlagView').attr("checked",true);
    	}else{
    		$('#returnOwnFlagView').attr("checked",false);
    	}
		$("#marginAmountView").addClass("readonly").attr("readonly",true);
		/*$( '#dtlDataGridDivView').datagrid( 'options' ).queryParams= {refId:rowData.id};
	    $( '#dtlDataGridDivView').datagrid( 'options' ).url = BasePath + '/customer_rece_rel_dtl/list.json';
	    $( '#dtlDataGridDivView').datagrid( 'load' );*/
		return true;
		
	};
	
	
	this.checkUpdate = function() {
		return $this.validateData();
	};
	
	this.loadedUpdate = function() {
		if($(":checkbox[id='marginControlFlag']").attr("checked")) {
			$("#marginAmount").removeClass("readonly").removeAttr("readonly");
		}else{
			$("#marginAmount").addClass("readonly").attr("readonly",true);
		}
		/*$( '#dtlDataGridDiv').datagrid( 'options' ).queryParams= {refId:$('#id').val()};
	    $( '#dtlDataGridDiv').datagrid( 'options' ).url = BasePath + '/customer_rece_rel_dtl/list.json';
	    $( '#dtlDataGridDiv').datagrid( 'load' );*/
	};
	
	//校验数据
	this.validateData = function() {
		var check_data = {companyNo : $("#companyNo").val(), customerNo : $("#customerNo").val(), id : $("#id").val()};
		/*if($.fas.endEditing('dtlDataGridDiv')){
			var rows = $('#dtlDataGridDiv').datagrid('getRows');
			if(rows.length >1){
				for(var i = 0;i < rows.length;i++){
					for(var j = 0;j < rows.length;j++){
						if(i!=j && rows[i].year == rows[j].year){
							showError("存在重复的返利设置!");
							return false;
						}
					}
				}
			}
		}else{
			return false;
		}*/
		var url = BasePath + "/customer_rece_rel/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
	this.buildEnableData = function(checkedRows) {
		return $this.buildEnableAndUnableData(checkedRows);
	};
	
	this.buildUnableData = function(checkedRows) {
		return $this.buildEnableAndUnableData(checkedRows);
	};
	
	this.buildEnableAndUnableData = function(checkedRows) {
		var list = [];
		$.each(checkedRows, function(index, item) {
			var obj = new Object();
			obj.id = item.id;
			obj.marginControlFlag = item.marginControlFlag;
			obj.marginAmount = item.marginAmount;
			list.push(obj);
		});     
	    return list;
	}
	
	this.initAddByTemplate = function(){
		$('#templateDataForm').form("clear");
		$('#templateDataForm').find("input[type=hidden]").val("");
		$('#marginControlFlag').attr("checked",false);
		$('#bottombarMx').find('span').text("");
	}
	
	this.toAddByTemplate = function(params) {
	    this.dataFormId = "templateDataForm";
	    this.dialogWidth = 700;
	    
	    this.dialogHeight = 300;

	    // 如果需要在新增操作时，进行一些其他的逻辑处理，可自定义extra_operate.initAdd函数
	    if(typeof(this.initAddByTemplate) == 'function'){
	    	this.initAddByTemplate();
	    }
	    var _this = this;
	    ygDialog({
	        title : '新增',
	        target : $('#templateFormPanel'),
	        width : _this.dialogWidth,
	        height : _this.dialogHeight,
	        buttons : [{
	            text : '保存',
	            iconCls : 'icon-save',
	            handler : function(dialog) {
	                _this.addTemplate(params);
	            }
	        }, {
	            text : '取消',
	            iconCls : 'icon-cancel',
	            handler : function(dialog) {
	                dialog.close();
	            }
	        }]
	    });
	    this.addDataFormClass("dataForm");
	    this.loadedAdd();
	};
	
	this.addTemplate = function() {
		this.saveUrl = BasePath + '/customer_rece_rel/insertByTemplate';
		this.formObj = $("#templateDataForm");
		this.searchBtn = '';
	    var _this = this;
	    if(!_this.validateForm(_this.formObj)) {
	        return;
	    }
	    _this.formObj.form('submit', {
	        url : _this.saveUrl,
	        dataType : 'json',
	        onSubmit : function(extraParams) {
	        	var submitParams = null;
	    	    if(submitParams != null && submitParams.length > 0) {
	    	        for(var i = 0; i < submitParams.length; i++) {
	    	            var paramName = submitParams[i].name;
	    	            extraParams[paramName] = submitParams[i].value;
	    	        }
	    	    }
	        },
	        success : function(result) {
	            _this.successAddTemplateFn(result, _this);
	        },
	        error : function() {
	            showError('新增失败,请联系管理员!');
	        }
	    });
	    
	};
	//新增成功的回调函数
	this.successAddTemplateFn = function(result, params) {
		if(result && (JSON.parse(result).success
	        || typeof JSON.parse(result).success == 'undefined')) {
			if((JSON.parse(result).message == '' || typeof JSON.parse(result).message == 'undefined')){
				showSuc('新增成功!');
				clearTemplateRef();
				$("#templateFormPanel").window('close');
			    $("#"+ params.searchBtn).click();
			}else{
				showError(JSON.parse(result).message);
			}
	    } else {
	        showError('新增失败,请联系管理员!');
	    }
	};
		
//	this.exportExcel = function(options) {
//		var defaults = {
//			searchFormId : "searchForm",
//			dataGridId : "dataGridDiv",
//			exportUrl : "/customer_rece_rel/do_fas_export",
//			exportTitle : "导出",
//			exportType : "common"
//        };
//		options = $.extend(defaults, options);
//		alert(BasePath+options.exportUrl);
//    	var $dg = $("#" + options.dataGridId);
//	    var queryParams = $dg.datagrid('options').queryParams;
//	    var grepColumns = $dg.datagrid('options').columns;
//	    var subGrepColumns = $dg.datagrid('options').subColumns;
//	    var Fcolumns= $dg.datagrid('options').frozenColumns; 
//	    //处理冻结列
////	    if(Fcolumns.length >0){
////	    	for(var i=Fcolumns[0].length-1;i>=0;i--){
////	    		grepColumns[0].unshift(Fcolumns[0][i]);
////	    	}
////	    }
//	    var columns = [], firstHeaderColumns = [];
//		if(grepColumns && grepColumns.length > 1) {
//			columns = $.grep(grepColumns[1], function(o, i) {
//				if ($(o).attr("notexport") == true) {
//					return true;
//				}
//				return false;
//			}, true);
//			firstHeaderColumns = $.grep(grepColumns[0], function(o, i) {
//				if ($(o).attr("notexport") == true) {
//					return true;
//				}
//				return false;
//			}, true);
//		} else {
//			columns = $.grep(grepColumns[0], function(o, i) {
//				if ($(o).attr("notexport") == true) {
//					return true;
//				}
//				return false;
//			}, true);
//		}
//	    // 获取排序字段，由于sortName只能获取field字段，所以需要转换
//	    var sortName = $dg.datagrid('options').sortName;
//	    var sortField = "", sortOrder = $dg.datagrid('options').sortOrder;
//	    if(sortName && columns) {
//	    	for(var i = 0; i < columns.length; i++) {
//	    		if(sortName == columns[i].field) {
//	    			sortField = columns[i].sortField;
//	    			break;
//	    		}
//	    	}
//	    }
//	    var subColumns = [];
//	    if(typeof subGrepColumns != 'undefined'
//	        && subGrepColumns != null
//	        && subGrepColumns != "") {
//	        subColumns = $.grep(subGrepColumns[0], function(o, i) {
//	            if ($(o).attr("notexport") == true) {
//	                return true;
//	            }
//	            return false;
//	        }, true);
//	    }
//	    var exportColumns = JSON.stringify(columns);
//	    var exportSubColumns = JSON.stringify(subColumns);
//	    var dataRow = $dg.datagrid('getRows');
//	    $("#exportExcelForm").remove();
//	    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
//	    if(dataRow.length > 0) {
//	    	$('#exportExcelForm').form('submit', {
//	            url : BasePath + options.exportUrl,
//	            onSubmit : function(params) {
//	            	params.exportColumns = exportColumns;
//	            	params.exportSubColumns = exportSubColumns;
//	            	params.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
//	            	params.fileName = options.exportTitle;
//	            	params.exportType = fas_common_setting.exportType;
//	            	params.orderByField = sortField;
//	            	params.orderBy = sortOrder;
//	                if(queryParams != null && queryParams != {}) {
//	                    $.each(queryParams, function(i) {
//	                    	params[i] = queryParams[i];
//	                    });
//	                }
//	            },
//	            success : function() {
//
//	            }
//	        });
//	    } else {
//	        showWarn('查询记录为空，不能导出!');
//	    }
//	};
	
}


var dialog = null;
var editor = null;
var yearData = null;

function buildSubgridUrl(row){
	return "?refId="+row.id;
}

function company_callback(row){
	var dg = $('#termName').combogrid('grid');
	if(row){
		$("#companyName").val(row.name);
		$("#companyNo").val(row.companyNo);
		$('#termName').combogrid('clear');
		$('#termName').val("");
		$('#termNo').val("");
	}else{
		$('#termName').combogrid('clear');
		$('#termName').val("");
		$('#termNo').val("");
	}
	
}
function initTerm(){// 初始化条款信息
	var _this = $('#termName');
	_this.combogrid({
        panelWidth:560,
	    panelHeight : 250,
	    width : 160,
        mode:'remote',
        idField:'name',
        textField:'name',
        url : '',
        columns:[[
	        {field:'termNo',title:'条款编码',width:120},
	        {field:'name',title:'条款名称',width:100},
	        {field:'createTime',title:'创建日期',width:100},
	        {field:'createUser',title:'创建人',width:100}
        ]],
        onHidePanel:function(){
        	var row = _this.combogrid('grid').datagrid('getSelected');
        	if(row){
        		$('#termName').val(row.name);
            	$('#termNo').val(row.termNo);
        	}else{
        		clearRef();
        	}
        },
        onShowPanel:function(){
        	loadRef();
        }
    });
};

function clearRef(){// 清空关联信息
	$('#termName').combogrid('clear');
	$('#termNo').val('');
};

function loadRef(){
	var isHq = $('#isHq').val();
	var salerNo = $('#dataForm').find("input[name=companyNo]").val();
	var dg = $('#termName').combogrid('grid');
	if(salerNo!= ''){
		var queryParams = {companyNo:salerNo};
		dg.datagrid( 'options' ).queryParams = queryParams;
		dg.datagrid( 'options' ).url = BasePath + '/wholesale_rece_term/list.json?status=1&isHq='+isHq;
		dg.datagrid( 'load' );
	}else{
		dg.datagrid('loadData',{total:0,rows:[]}); 
	}
}


function initTemplate() {	
	$('#customerNameTemplate').combogrid({
		width :200,
        panelWidth:700,
	    panelHeight : 250,
//        mode:'remote',
        idField:'billNo',
        textField:'billNo',
        url : '',
		fitColumns : true,
		pagination : true,
        columns:[[
        {field:'companyName',title:'公司',width:200},
        {field:'customerName',title:'客户',width:150},
        {field:'termName',title:'条款名称',width:150}
        ]],
        onHidePanel:function(){
        	var rows = $('#customerNameTemplate').combogrid('grid').datagrid('getSelected');
        	if(rows){
        		$("#templateFormPanel").find("input[name=customerNameTemplate]").val(rows.customerName);
        		$("#templateFormPanel").find("input[name=termNo]").val(rows.termNo);
        		$("#templateFormPanel").find("input[name=termName]").val(rows.termName);
        		$("#templateFormPanel").find("input[name=companyNo]").val(rows.companyNo);
        		$("#templateFormPanel").find("input[name=termName]").val(rows.termName);
        		$("#templateFormPanel").find("input[name=companyName]").val(rows.companyName);
        		$("#templateFormPanel").find("input[name=customerName]").val(rows.customerName);
        		$("#templateFormPanel").find("input[name=marginAmount]").val(rows.marginAmount);
        		$("#templateFormPanel").find("input[name=marginRemainderAmount]").val(rows.marginRemainderAmount);
        		$("#templateFormPanel").find("input[name=marginControlFlag]").val(rows.marginControlFlag);
        		if(rows.marginControlFlag == 1){
        			$("#templateFormPanel").find("input[name=marginControlFlagText]").attr("checked",true);
        		}else{
        			$("#templateFormPanel").find("input[name=marginControlFlagText]").attr("checked",false);
        		}
        		$("#templateFormPanel").find("input[name=marginControlFlagText]").attr("disabled",true);
        	}else{
        		clearTemplateRef();
        	}
        },
        onShowPanel:function(){
        	loadTemplateRef();
        },
        onChange:function(){
        	loadTemplateRef();
        }
    });
};

function clearTemplateRef(){// 清空关联信息
	$("#customerNameTemplate").combogrid('clear');
	$("#templateFormPanel").find("input[name=termNo]").val('');
	$("#templateFormPanel").find("input[name=termName]").val('');
	$("#templateFormPanel").find("input[name=companyNo]").val('');
	$("#templateFormPanel").find("input[name=termName]").val('');
	$("#templateFormPanel").find("input[name=companyName]").val('');
	$("#templateFormPanel").find("input[name=customerName]").val('');
	$("#templateFormPanel").find("input[name=marginAmount]").val('');
	$("#templateFormPanel").find("input[name=marginRemainderAmount]").val('');
	$("#templateFormPanel").find("input[name=marginControlFlag]").val('');
};

function loadTemplateRef(){
	var queryParams = {};
	var customerNameTemplate = $('#templateFormPanel').find("input[name=customerNameTemplate]").val();
	var dg = $('#customerNameTemplate').combogrid('grid');
	if(customerNameTemplate!= ''){
		queryParams = {customerName:customerNameTemplate};
	}
	dg.datagrid( 'options' ).queryParams = queryParams;
	dg.datagrid( 'options' ).url = BasePath + '/customer_rece_rel/list_customer';
	dg.datagrid( 'load' );
}


//行编辑
function CustomerReceRelEditor() {}

$(function() {
	$.fas.extend(CustomerReceRelDialog, FasDialogController);
	$.fas.extend(CustomerReceRelEditor, FasEditorController);
	dialog = new CustomerReceRelDialog();
	dialog.init("/customer_rece_rel", fas_common_setting);
	
	$("#marginControlFlag").on("click", function() {
		if($(this).attr("checked")) {
			$("#marginAmount").removeClass("readonly").removeAttr("readonly");
		}else{
			$("#marginAmount").addClass("readonly").attr("readonly",true);
		}
	});
	editor = new CustomerReceRelEditor();
	/*editor.init("/customer_rece_rel", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dtlDataGridDiv'
	});*/
	
	yearData = new Array();
	var currYear = new Date().getFullYear();
	for(var i = 0;i<10;i++){
		yearData.push({value:currYear+i,text:currYear+i});
	}
	initTerm();
	initTemplate();
});