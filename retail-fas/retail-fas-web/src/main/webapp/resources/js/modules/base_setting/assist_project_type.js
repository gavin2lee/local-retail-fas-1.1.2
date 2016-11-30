var assistProjectType = {};

$(function() {
});

/**
 * 验证辅助项目编码是否重复
 */
var extra_operate = {
	checkSave : function() {
		var repeatFlag = false;
		var $id = $("#id").val();
		var $codeId = $("#typeCodeId").val();
		var $name = $("#typeNameId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/assist_project_type/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				id : $id,
				typeCode : $codeId,
				typeName : $name
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('类型编码或者名称不能重复，请重新输入!');
		}
		return !repeatFlag;
	},

	// 新增初始化
	initAdd : function() {
		$("#typeCodeId").removeAttr("readonly").removeClass("disabled");
	},

	// 新增初始化
	loadedAdd : function() {
		// assistProjectType.initLoadSearchCompany();
	},

	// 修改验证
	checkUpdate : function() {
		var repeatFlag = false;
		var $id = $("#id").val();
		var $name = $("#typeNameId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/base_setting/assist_project_type/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				id : $id,
				typeName : $name
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('名称不能重复，请重新输入!');
		}
		return !repeatFlag;
	},

	// 修改初始化
	initUpdate : function(rowData) {
	},

	// 修改之前的逻辑处理
	loadedUpdate : function(rowData) {
		$("#typeCodeId").attr("readonly", true).addClass("disabled");
	},

	// 保存成功之后
	saveCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},

	// 修改之后的处理
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},

	// 删除辅助项目
	checkDel : function() {
		var delFlag = false;
		// 是否已经启用
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for ( var i = 0; i < checkedRows.length; i++) {
			if (checkedRows[i].status == '1') {
				showWarn("包含启用记录，不能删除！");
				delFlag = true;
				return;
			}
		}
		return !delFlag;

		var code_datas = "";
		var deletedData = $("#dataGridJG").datagrid("getChecked");
		$.each(deletedData, function(index, item) {
			code_datas += item.id + ",";
		});
		// 拼接参数
		var params = {
			codeList : code_datas.substring(0, code_datas.length - 1)
		};

		// 先查，再判断
		var searchUrl = BasePath
				+ '/base_setting/assist_project_type/searchData';
		fas_common.ajaxRequest(searchUrl, params, function(result) {
			if (result > 0) {
				// 存在辅助项目
				showWarn("该辅助项目类型下存在辅助项目,不能删除");
				$('#dataGridJG').datagrid('reload');
				delFlag = false;
			} else {
				// 不存在辅助项目
				$.messager.confirm("提示", "你确定要删除这" + deletedData.length
						+ "条记录吗?", function(r) {
					if (r) {
						var delUrl = BasePath
								+ '/base_setting/assist_project_type/delData';
						fas_common.ajaxRequest(delUrl, params, function(data) {
							if (data > 0) {
								showSuc('保存成功');
								fas_common.search();
								delFlag = true;
							} else {
								showError('保存失败！');
							}
						});
					}
				});
			}
		});
		return delFlag;
	},

	checkEnable : function() {
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for ( var i = 0; i < checkedRows.length; i++) {
			if (checkedRows[i].status == '1') {
				showWarn("包含启用记录，不能重复操作！");
				return;
			}
		}
		return !Flag;
	},

	checkUnable : function() {
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for ( var i = 0; i < checkedRows.length; i++) {
			if (checkedRows[i].status == '0') {
				showWarn("包含停用记录，不能重复操作！");
				return;
			}
		}
		return !Flag;
	}
};

var statusData = [ {
	"value" : "1",
	"text" : "已启用"
}, {
	"value" : "0",
	"text" : "已停用"
} ];
