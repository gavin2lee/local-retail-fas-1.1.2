var assist_project={};

$(function(){
	assist_project.initSearchAssistProjectType();
});

/**
 * 初始化下拉列表
 */
assist_project.initSearchAssistProjectType=function(){
	$('#typeCodeCondition').combobox({    
	    url:BasePath+'/base_setting/assist_project_type/get_biz?status=1',    
	    valueField:'id',    
	    textField:'typeName',
	    editable:false
	});  
};

/**
 * 格式化辅助项目类型
 */
assist_project.formatAssistProjectType = function(v) {
	return fas_common.formatterCombox ({
		"id" : 'typeCodeCondition',
		"formatVal" : v
	});
};

assist_project.initAssistProjectType=function(){
	$('#typeId').combobox({    
	    url:BasePath+'/base_setting/assist_project_type/get_biz?status=1',    
	    valueField:'id',    
	    textField:'typeName',
	    editable:false
	});  
};

/**
 * 校验
 */
var extra_operate = {
		// 新增初始化
		initAdd : function() {
			assist_project.initAssistProjectType();
			$("#codeId").removeAttr("readonly").removeClass("disabled");
		},
		
		// 新增初始化
		loadedAdd : function() {
//			assist_project.initLoadSearchCompany();
		},	
		
		checkSave : function() {
			var repeatFlag = false;
			var $id = $("#id").val();
			var $codeId = $("#codeId").val();
			var $name = $("#nameId").val();
			$.ajax({
				type : "POST",
				url : BasePath + "/base_setting/assist_project/check_Repect",
				dataType : 'json',
				async : false,
				data : {
					id : $id,
					code : $codeId,
					name : $name
				},
				success : function(msg) {
					if (msg == true) {
						repeatFlag = true;
					}
				}
			});
			// 如果存在，则提示
			if (repeatFlag) {
				showWarn('编码或名称不能重复，请重新输入!');
			}
			return !repeatFlag;
		},
		
		// 修改初始化
		initUpdate : function(rowData) {
		},
		
		//修改验证
		checkUpdate:function(){
			var repeatFlag = false;
			var $id = $("#id").val();
			var $name = $("#nameId").val();
			$.ajax({
				type : "POST",
				url : BasePath + "/base_setting/assist_project/check_Repect",
				dataType : 'json',
				async : false,
				data : {
					id : $id,
					name : $name
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
		
		// 修改之前的逻辑处理
		loadedUpdate : function(rowData) {
			$("#codeId").attr("readonly", true).addClass("disabled");
			assist_project.initAssistProjectType();
			$('#typeId').combobox('setValue', rowData.type);
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
			//是否已经启用
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
	
