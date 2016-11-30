var financialAccount = {};

$(function() {
	financialAccount.initPriceZone ('priceZone');
});

var ifSubCompany = [ {
	"value" : "1",
	"text" : "是"
}, {
	"value" : "0",
	"text" : "否"
} ];

var ifLeadRole = [ {
	"value" : "1",
	"text" : "是"
}, {
	"value" : "0",
	"text" : "否"
} ];

/**
 * 加载默认价格区
 */
financialAccount.initPriceZone = function(unique_id) {
	$('#'+unique_id).combobox({
		url:BasePath+"/zone_info/getPriceZone",
		valueField : 'zoneNo',
		textField : 'name',
		editable : false,
		width : 130
	});  
};

var extra_operate = {
	checkSave : function() {
		var repeatFlag = false;
		var $codeId = $("#companyNoId").val();
		var $ncId = $("#ncId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/financial_account/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				companyNo : $codeId,
				ncId:$ncId
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('公司编码或者NC账套编码重复，请重新输入!');
		}
		return !repeatFlag;
	},
		
	// 新增初始化
	loadedAdd : function() {
		$("#companyNoId").attr("readonly", true).addClass("disabled");
		$("#parentCompanyNameId").combogrid("disable");
		//如果是子公司
		$('#childCompanyId').combobox({
			onSelect : function(param) {
				if (param.value == 1) {
					$("#parentCompanyNameId").combogrid("enable");
				}else{
					$("#parentCompanyNameId").combogrid("disable");
					$("#parentCompanyId").val("");
					$("#parentCompanyNameId").combogrid("clear");
				}
			}
		});
	},	
	
	//修改之前的逻辑处理
	initUpdate : function(rowData) {
	},
	
	// 修改之前的逻辑处理
	loadedUpdate : function(rowData) {
		$("#companyNoId").attr("readonly", true).addClass("disabled");
		if (rowData.childCompany == 1) {
			$("#parentCompanyNameId").combogrid("enable");
		} else {
			$("#parentCompanyNameId").combogrid("disable");
			$("#parentCompanyId").val("");
			$("#parentCompanyNameId").combogrid("clear");
			// 如果是子公司
			$('#childCompanyId').combobox({
				onSelect : function(param) {
					if (param.value == 1) {
						$("#parentCompanyNameId").combogrid("enable");
					} else {
						$("#parentCompanyNameId").combogrid("disable");
						$("#parentCompanyId").val("");
						$("#parentCompanyNameId").combogrid("clear");
					}
				}
			});
		}
	},	
	
	//保存成功之后的处理
	saveCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},
	
	//修改验证
	checkUpdate:function(){
		var repeatFlag = false;
		var $source=$("#companyNoId").val();
		var $target=$("#parentCompanyId").val();
		if($source===$target){
			showInfo("公司编码和父项公司编码不能一样，请重新输入!");
			return;
		}
		
		var $id = $("#id").val();
		var $ncId = $("#ncId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/financial_account/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				ncId : $ncId,
				id : $id
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('NC账套编码重复，请重新输入!');
		}
		return !repeatFlag;
	},

	// 修改之后的处理
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
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
