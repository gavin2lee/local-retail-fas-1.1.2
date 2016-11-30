var settleMethod = {};

$(function() {
	settleMethod.initSettleType();
	settleMethod.initBusinessType();
	settleMethod.initPayFeesFlag();
	settleMethod.initStopFlag();
});

/**
 * 格式化结算方式类型
 */
settleMethod.formatSettleType = function(v) {
	return fas_common.formatterCombox({
		"id" : 'settleTypeCondition',
		"formatVal" : v
	});
};
/**
 * 初始化结算方式下拉列表
 */
settleMethod.initSettleType = function() {
	$('#settleTypeCondition').combobox({
		url:BasePath+"/base_setting/settle_method/settleType",
		valueField : 'no',
		textField : 'name',
		editable : false,
		width : 130
	});  
};

/**
 * 业务类型
 */
settleMethod.initBusinessType=function(){
	$('#businessTypeId').combobox({
		data : businessType,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

/**
 * 是否支付手续费
 */
settleMethod.initPayFeesFlag=function(){
	$('#payFeesFlagId').combobox({
		data : payFeesFlag,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

/**
 * 是否启用
 */
settleMethod.initStopFlag=function(){
	$('#stopFlagId').combobox({
		data : stopFlag,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

/**
 * 初始化结算方式下拉列表(新增、修改)
 */
settleMethod.initLoadSettleType = function() {
	$('#settleTypeId').combobox({
		url:BasePath+"/base_setting/settle_method/settleType",
		valueField : 'no',
		textField : 'name',
		editable : false,
		width : 160
	});  
};

/**
 * 业务类型
 */
var businessType = [ {
	"value" : "1",
	"text" : "现金业务"
}, {
	"value" : "2",
	"text" : "银行业务"
}, {
	"value" : "3",
	"text" : "票据业务"
} ];

var payFeesFlag = [ {
	"value" : "1",
	"text" : "是"
}, {
	"value" : "2",
	"text" : "否"
} ];

var stopFlag = [ {
	"value" : "1",
	"text" : "是"
}, {
	"value" : "0",
	"text" : "否"
} ];

/**
 * 格式化业务类型
 */
settleMethod.businessTypeformatter = function(value) {
	for ( var i = 0; i < businessType.length; i++) {
		if (businessType[i].value == value) {
			return businessType[i].text;
		}
	}
};

/**
 * 格式化是否支付手续费
 */
settleMethod.payFeesFlagformatter = function(value) {
	for ( var i = 0; i < businessType.length; i++) {
		if (businessType[i].value == value) {
			return payFeesFlag[i].text;
		}
	}
};

/**
 * 保存
 */
var extra_operate = {
	//验证编码是否重复
	checkSave : function() {
		var repeatFlag = false;
		var $codeId = $("#settleCodeId").val();
		var $name = $("#settleNameId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/settle_method/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				settleCode : $codeId,
				settleName : $name
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('结算方式编码或名称不能重复，请重新输入！');
		}
		return !repeatFlag;
	},
	
	// 新增初始化
	initAdd : function() {
		settleMethod.initLoadSettleType();
		$("#settleCodeId").removeAttr("readonly").removeClass("disabled");
	},
	
	// 修改初始化
	initUpdate : function() {
	},
	
	// 修改之前的逻辑处理
	loadedUpdate : function(rowData) {
		$("#settleCodeId").attr("readonly", true).addClass("disabled");
		settleMethod.initLoadSettleType();
		$('#settleTypeId').combobox('setValue', rowData.settleType);
	},
	
	//修改验证
	checkUpdate:function(){
		var repeatFlag = false;
		var $id = $("#id").val();
		var $name = $("#settleNameId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/settle_method/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				id : $id,
				settleName : $name
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('结算方式名称不能重复，请重新输入！');
		}
		return !repeatFlag;
	},
	
	
	saveCallback:function(result, setting){
		$("#id").attr("value", "");
		$("#"+setting.searchBtn).click();
	},
	
	// 修改之后的处理
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#"+setting.searchBtn).click();
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
