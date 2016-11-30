var voucherType = {};

$(function() {
});

var statusData = [ {
	"value" : "1",
	"text" : "已启用"
}, {
	"value" : "0",
	"text" : "已停用"
} ];	

/**
 * 操作
 */
var extra_operate = {
	// 新增初始化
	initAdd : function() {
		$("#vouchTypeCodeId").removeAttr("readonly").removeClass("disabled");
		$("#currencyCodeId").attr("value", "");
	},
		
	checkSave : function() {
		var repeatFlag = false;
		var $codeId = $("#vouchTypeCodeId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/voucher_type/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				vouchTypeCode : $codeId
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('编码不能重复，请重新输入！');
		}
		return !repeatFlag;
	},
	
	// 新增初始化
	loadedAdd : function() {
//		voucherType.initLoadSearchCompany();
	},	

	saveCallback:function(result, setting){
		$("#id").attr("value", "");
		$("#"+setting.searchBtn).click();
	},
	
	//修改之前的逻辑处理
	initUpdate : function() {
	},
	
	// 修改之前的逻辑处理
	loadedUpdate : function() {
		$("#vouchTypeCodeId").attr("readonly", true).addClass("disabled");
	},
	
	// 修改之后的处理
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#currencyCodeId").attr("value", "");
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