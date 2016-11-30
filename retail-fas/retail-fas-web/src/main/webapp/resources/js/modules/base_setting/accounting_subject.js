var accountingSubject = {};

$(function() {
	accountingSubject.initSubjectType('subjectTypeCondition');
	accountingSubject.initStopFlag();
});

var statusData = [ {
	"value" : "1",
	"text" : "已启用"
}, {
	"value" : "0",
	"text" : "已停用"
} ];

accountingSubject.initStopFlag=function(){
	$('#statusCondition').combobox({
		data : statusData,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

/**
 * 加载科目类型
 */
accountingSubject.initSubjectType = function(unique_id) {
	$('#'+unique_id).combobox({
		url:BasePath+"/base_setting/accounting_subject/getSubjectType",
		valueField : 'id',
		textField : 'name',
		editable : false,
		width : 130
	});  
};

/**
 * 格式化datagrid科目类型
 */
accountingSubject.formatSubjectType = function(v) {
	return fas_common.formatterCombox({
		"id" : 'subjectTypeCondition',
		"formatVal" : v
	});
};

/**
* 验证编码是否重复
*/
var extra_operate = {
	// 新增初始化
	initAdd : function() {
		accountingSubject.initSubjectType('subjectTypeId');
		$("#subjectCodeId").removeAttr("readonly").removeClass("disabled");
		$("#subjectCodeId").numberbox("clear");
		$("#subjectLevelId").numberbox("clear");
	},
	
	// 新增初始化
	loadedAdd : function() {
//		accountingSubject.initLoadSearchCompany();
	},	

	// 修改初始化
	initUpdate : function(rowData) {
	},

	// 修改之前的逻辑处理
	loadedUpdate : function(rowData) {
		$("#subjectCodeId").attr("readonly", true).addClass("disabled");
		accountingSubject.initSubjectType('subjectTypeId');
		$('#subjectTypeId').combobox('setValue', rowData.subjectType);
	},
	
	checkSave : function() {
		var repeatFlag = false;
		var $id = $("#id").val();
		var $code = $("#subjectCodeId").val();
		var $name = $("#subjectNameId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/accounting_subject/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				id : $id,
				subjectCode : $code,
				subjectName : $name
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('编码或者名称不能重复，请重新输入！');
		}
		return !repeatFlag;
	},

	// 保存成功之后
	saveCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},
	
	//修改验证
	checkUpdate:function(){
		var repeatFlag = false;
		var $id = $("#id").val();
		var $name = $("#subjectNameId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/accounting_subject/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				id : $id,
				subjectName : $name
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('名称不能重复，请重新输入！');
		}
		return !repeatFlag;
	},
	
	// 修改之后的处理
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
		$("#subjectCodeId").numberbox("clear");
		$("#subjectLevelId").numberbox("clear");
	},

	// 删除之前的逻辑处理
	checkDel : function() {
		var delFlag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1'){
				 showWarn("包含启用记录，不能删除！");
				 return;
			 }
		}
		return !delFlag;
	},
	
	checkEnable:function(){
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1'){
				 showWarn("包含启用记录，不能重复操作！");
				 return;
			 }
		}
		return !Flag;
	},
	
	checkUnable:function(){
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '0'){
				 showWarn("包含停用记录，不能重复操作！");
				 return;
			 }
		}
		return !Flag;
	}
};
