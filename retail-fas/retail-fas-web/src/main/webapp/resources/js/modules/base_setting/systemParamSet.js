
function paramSetDialog() { 
	var $this = this;
	
}

function paramSetEditor() {
	var $this = this;
	
	this.checkUpdate = function(options, rowIndex, rowData) {
		var status = rowData.status;
		if(status == '1' ){
			showWarn("已启用,不能修改！");
			return false;
		}
		return true;
	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		if(checkedRows.length<1){
			showInfo("请先选择要删除的记录！");
			return;
		}
		var delFlag = true;
		$.each(checkedRows, function(index, row) {
			if (row.status == '1') {
				showWarn("存在已启用数据，不能删除。");
				delFlag = false;
				return false;
			}
		});
		return delFlag;
	};
	
	this.BizTypeFormatter = function(value, rowData, rowIndex) {
		for(var i = 0; i < BizTypeData.length; i++) {
			if(BizTypeData[i].typeNo == value) {
				return BizTypeData[i].typeName;
			}
		}
		return "";
	};
	
	this.statusformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':'0', 'text': '停用'}, {'value':'1', 'text':'启用'}];
	    for(var i = 0; i < dataStatusType.length; i++) {
	        if(dataStatusType[i].value == value) {
	            return dataStatusType[i].text;
	        }
	    }
	    return "";
	};
}

var paramSet = null, editor = null;
var BizTypeData=new Object();
var paramNameData=new Object();
$(function() {
	$.fas.extend(paramSetDialog, FasDialogController);
	$.fas.extend(paramSetEditor, FasEditorController);
	paramSet = new paramSetDialog();
	paramSet.init("/base_setting/system_param_set", {
		dataGridId : "paramSetDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json?paramFlag=exclusive",
		searchFormId : "searchForm",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable"
	});
	
	editor = new paramSetEditor();
	editor.init("/base_setting/system_param_set", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'paramSetDataGrid',
		saveUrl : "/base_setting/system_param_set/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			$('#businessOrganNameId').combogrid("disable");
			$('#businessOrganTypeId').combobox("disable");
		},
		keyboard : true
	});

	// 加载客户类型
	$('#businessOrganTypeCond').combobox({
		url : BasePath + '/base_setting/system_param_set/getControlType',
		valueField : 'typeNo',
		textField : 'typeName',
		onLoadSuccess : function(data) {
			BizTypeData = data;
		}
	}); 
	
	$('#paramCodeCond').combobox({
		url : BasePath + '/base_setting/system_param_set/getSystemParamList',
		valueField : 'paramCode',
		textField : 'paramName',
		width:200,
		onLoadSuccess : function(data) {
			paramNameData = data;
		}
	}); 
});

function controlTypeChange(newValue) {
	var ed = $('#paramSetDataGrid').datagrid('getEditor', {
		index : $.fas.editIndex,
		field : 'paramName'
	});
	var organEd = $('#paramSetDataGrid').datagrid('getEditor', {
		index : $.fas.editIndex,
		field : 'businessOrganName'
	});
	//根据控制级别查询参数
	if(newValue != '') {
		$(ed.target).combogrid({
			panelWidth : 300,
			panelHeight : 200,
			url : BasePath + "/base_setting/digital_dict/list.json?controlLevel="+newValue,
			idField : "paramName",
			textField : "paramName",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
               {field : 'paramCode',title : '参数编码',width : 100,align:'left',halign:'center'},
          	   {field : 'paramName',title : '参数名称',width : 150,align:'left',halign:'center'}
            ]],
            onHidePanel:function(data){
            	var data = $(ed.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
  					$('#paramCodeId').val(data.paramCode);
  					paramNameChange();
  				}
  			}
		});
	}
	//根据控制级别查询业务组织
	if(newValue==1) {
		$(organEd.target).combogrid({
			panelWidth : 300,
			panelHeight : 200,
			url : BasePath + '/zone_info/get_biz?status=1',
			idField : "name",
			textField : "name",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
               {field : 'zoneCode',title : '大区编码',width : 80,align:'left',halign:'center'},
          	   {field : 'name',title : '大区名称',width : 100,align:'left',halign:'center'}
            ]],
            onHidePanel:function(data){
            	var data = $(organEd.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#businessOrganNoId').val(data.zoneCode);
      				$('#businessOrganNameId').val(data.name);
  				}
  			}
		});
	}else if(newValue==2){
		$(organEd.target).combogrid({
			panelWidth : 300,
			panelHeight : 200,
			url : BasePath + "/base_setting/company/list.json?dataAccess=1",
			idField : "name",
			textField : "name",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
               {field : 'companyNo',title : '公司编码',width : 80,align:'left',halign:'center'},
          	   {field : 'name',title : '公司名称',width : 150,align:'left',halign:'center'}
            ]],
            onHidePanel:function(data){
            	var data = $(organEd.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#businessOrganNoId').val(data.companyNo);
      				$('#businessOrganNameId').val(data.name);
  				}
  			}
		});
	}else if(newValue==3){
		$(organEd.target).combogrid({
			panelWidth : 300,
			panelHeight : 200,
			url : BasePath + '/shop/list.json?status=1',
			idField : "shortName",
			textField : "shortName",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
		        {field : 'shopNo',title : '店铺编码',width : 100,align:'left',halign:'center'},
          		{field : 'shortName',title : '店铺简称',width : 200,align:'left',halign:'center'}
            ]],
            onHidePanel:function(data){
            	var data = $(organEd.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#businessOrganNoId').val(data.shopNo);
      				$('#businessOrganNameId').val(data.shortName);
  				}
  			}
		});
	}
};

function paramNameChange(){
	var newValueEd = $('#paramSetDataGrid').datagrid('getEditor', {
		index : $.fas.editIndex,
		field : 'paramCode'
	});
	newValue = newValueEd.target.val();
	var ed = $('#paramSetDataGrid').datagrid('getEditor', {
		index : $.fas.editIndex,
		field : 'dtlName'
	});
	if(newValue != '') {
		$(ed.target).combogrid({
			panelWidth : 300,
			panelHeight : 200,
			url : '',
			idField : "dtlName",
			textField : "dtlName",
			mode : "remote",
			pagination : true,
			multiple : false,
			fitColumns : true,
			columns:[[
               {field : 'dtlValue',title : '参数值',width : 80,align:'left',halign:'center'},
          	   {field : 'dtlName',title : '参数值名称',width : 100,align:'left',halign:'center'}
            ]],
            onHidePanel:function(data){
            	var data = $(ed.target).combogrid('grid').datagrid('getSelected'); 
  				if(data){
      				$('#dtlValueId').val(data.dtlValue);
      				$('#dtlNameId').val(data.dtlName);
  				}
  			},
  			onShowPanel:function(){
  				if(newValue != ''){
  					var dg = $(ed.target).combogrid('grid');
  	  				dg.datagrid( 'options' ).url = BasePath + "/param_dtl/list.json?paramCode="+newValue;
  	  				dg.datagrid( 'load' );
  				}
			}
		});
	}
}