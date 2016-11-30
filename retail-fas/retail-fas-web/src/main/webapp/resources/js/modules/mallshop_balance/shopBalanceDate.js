
function ShopBalanceDateDialog() { 
	var $this = this;
	this.importOperation = function(){
		$.importExcel.open({
			'submitUrl' : BasePath + '/shop_balance_date/do_import',
			'templateName' : '结算期管理导入.xlsx',
			success : function(result) {
				$.messager.progress('close');
				if (result) {
					if ($this.isNotBlank(result.error)) {
						showError(result.error);
					} else {
						$.importExcel.colse();
						showSuc('数据导入成功');
						$this.search();
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
		
	};

	//检查对象是否为空
	this.isNotBlank = function(obj) {
		if (!obj || typeof obj == 'undefined' || obj == '') {
			if('0' == obj){
				return true;
			}
			return false;
		}
		return true;
	};
	
	this.batchGeneratorBalanceDate = function(){
		$(':input','#myFormPanel').not(
		':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
		
		ygDialog({
			title : '批量生成结算期',
			target : $('#myFormPanel'),
			width : 750,
			height : 180,
			onOpen:function() {
				//绑定店铺通用查询
				$("#shopNameTemp").filterbuilder({
			        type:'organ',
			        organFlag: 2,
			        roleType:'bizCity', 
			        onSave : function(result) { 
			        	var value = $(this).filterbuilder('getValue');
			        	$("#shopNoTemp").val(value);
			        }
			    });
			},
			buttons : [{
				text : '生成',
				iconCls : 'icon-save',
				handler : function(dialog) {
					var fromObj = $('#dataForm');
					if(!fromObj.form('validate')) {
						return;
					}
					
					var url = BasePath + "/shop_balance_date/generateBalanceDate";
					fromObj.form('submit', {
						url : url,
						dataType : 'json',
						onSubmit : function(param) {
							$.messager.progress({
				    			title:'请稍后',
				    			msg:'正在处理中...'
				    		});
						},
						success : function(data) {
				        	$.messager.progress('close');
				        	showSuc('执行成功');
							dialog.close();
							$this.search();
						},
						error : function(result) {
				        	$.messager.progress('close');
				        	showError(result.errorInfo);
				        }
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			}]
		});
	};
}

function ShopBalanceDateEditor() {
	var $this = this;
	
	//校验数据
	this.checkSave = function(options, data) {
		var url = BasePath + "/shop_balance_date/validationRepeat";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
	this.checkUpdate = function(options, rowIndex, rowData) {
//		if(rowData.status == '1'){
//			showWarn("开票规则已启用，不能修改！");
//			return false;
// 	    }
		return true;
	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
//		$.each(checkedRows, function(index,row) {
//			if(row.status == '1'){
//				showWarn("开票规则已启用，不能删除！");
//				delFlag = false;
//				return false;
//	 	    }
//		});
		return delFlag;
	};

	this.balanceFlagFormatter = function(value, rowData, rowIndex) {
		//是否已生成结算单
		var balanceFlagStatus = [{"value" : "-1","text" : "未生成"},{"value" : "0","text" : "已生成预估"},
		                          {"value" : "1","text" : "未生成"}, {"value" : "2","text" : "已生成"}];
	    for(var i = 0; i < balanceFlagStatus.length; i++) {
	        if(balanceFlagStatus[i].value == value) {
	            return balanceFlagStatus[i].text;
	        }
	    }
	    return "";
	};
	
	this.billFlagFormatter = function(value, rowData, rowIndex) {
		//是否已开票
		var billFlagStatus = [{"value" : "1","text" : "未开票"}, {"value" : "2","text" : "已开票"}];
	    for(var i = 0; i < billFlagStatus.length; i++) {
	        if(billFlagStatus[i].value == value) {
	            return billFlagStatus[i].text;
	        }
	    }
	    return "";
	};
	
	this.deductStatusFormatter = function(value, rowData, rowIndex) {
		//是否生成费用
		var deductStatus = [{"value" : "0","text" : "不生成"}, {"value" : "1","text" : "生成"}];
	    for(var i = 0; i < deductStatus.length; i++) {
	        if(deductStatus[i].value == value) {
	            return deductStatus[i].text;
	        }
	    }
	    return "";
	};
	
	this.balanceDateBoxOnBlur = function() {
	};
	
	this.dateBoxOnblur = function() {
	};
	
	this.copyOneLine = function(){
		var copyRows = $('#dtlDataGrid').datagrid('getChecked');
		if(copyRows.length < 1){
			showWarn("请选择要复制的结算期设置!");
			return;
		}
	    	
	};
}

var balanceDateDialog = null, editor = null;
$(function() {
	$.fas.extend(ShopBalanceDateDialog, FasDialogController);
	$.fas.extend(ShopBalanceDateEditor, FasEditorController);
	balanceDateDialog = new ShopBalanceDateDialog();
	balanceDateDialog.init("/shop_balance_date", {
		dataGridId : "dtlDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "结算期列表导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new ShopBalanceDateEditor();
	editor.init("/shop_balance_date", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'dtlDataGrid',
		saveUrl : "/shop_balance_date/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
//			var edBsgroups = $('#shopBalanceDateDataGrid').datagrid('getEditor', {index:rowIndex,field:'bsgroupsName'});
//			var edMalls = $('#shopBalanceDateDataGrid').datagrid('getEditor', {index:rowIndex,field:'mallName'});
//			
//			if(rowData.billingMethod == '1') {
//				$(edBsgroups.target).combobox('enable');
//				$(edMalls.target).combobox('disable');
//				$(edMalls.target).combobox('clear');
//				$("#mallNo_").val('');
//			} else if (rowData.billingMethod == '2' || rowData.billingMethod == '3' ) {
//				$(edMalls.target).combobox('enable');
//				$(edBsgroups.target).combobox('disable');
//				$(edBsgroups.target).combobox('clear');
//				$("#bsgroupsNo").val('');
//			} 
		},
		keyboard : true
	});
});