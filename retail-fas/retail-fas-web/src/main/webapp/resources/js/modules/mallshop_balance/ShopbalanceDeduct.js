function ShopBalanceDeductDialog() { 
	
	//当前用户
	var $this = this;	
	this.search = function() {
		var tab = $('#dtlTab').tabs("getSelected");
		var tabIndex = $('#dtlTab').tabs('getTabIndex',tab);
		if(tabIndex == 1) {
			$.fas.search({
				searchFormId : $this.options.searchFormId,
				dataGridId : "shopBalanceDeductAfterDataGrid",
				searchUrl : "/bill_shop_balance_deduct/list.json?costDeductType=2",
			});
		} else {
			$.fas.search({
				searchFormId : $this.options.searchFormId,
				dataGridId : "shopBalanceDeductBeforeDataGrid",
				searchUrl : "/bill_shop_balance_deduct/list.json?costDeductType=1",
			});
		}
	};
	
	/*this.exportExcel11 = function() {
		var tab = $('#dtlTab').tabs("getSelected");
		var tabIndex = $('#dtlTab').tabs('getTabIndex',tab);
		if(tabIndex == 1) {
			$.fas.exportExcel({
				searchFormId : $this.options.searchFormId,
				dataGridId : "shopBalanceDeductAfterDataGrid",
//				searchUrl : "/bill_shop_balance_deduct/list.json?costDeductType=2",
				exportTitle : "商场门店结算费用列表导出",
				exportUrl : "/do_fas_export"
			});
		} else {
			$.fas.exportExcel({
				searchFormId : $this.options.searchFormId,
				dataGridId : "shopBalanceDeductBeforeDataGrid",
//				searchUrl : "/bill_shop_balance_deduct/list.json?costDeductType=1",
				exportTitle : "商场门店结算费用列表导出",
				exportUrl : "/do_fas_export"
			});
		}
	};*/
	
	this.createBalanceDeduct = function() {
		ygDialog({
			title : '店铺费用生成',
			target : $('#createBalanceDeductPanel'),
			width : 560,
			height : 260,
			onOpen:function() {
				//绑定店铺通用查询
				$("#shortName_c").filterbuilder({
			        type:'organ',
			        organFlag: 2,
			        roleType:'bizCity', 
			        onSave : function(result) { 
			        	var value = $(this).filterbuilder('getValue');
			        	$("#shopNo_c").val(value);
			        }
			    });
			},
			buttons : [ {
				text : '确认',
				iconCls : 'icon-save',
				handler : function(dialog) {
					
					var fromObj = $('#createBalanceDeductForm');
					if(!fromObj.form('validate')) {
						return;
					}
					
					var url = BasePath + '/bill_shop_balance_deduct/saveBalanceDeduct';
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
							var obj = eval("("+data+")"); 
				        	$.messager.progress('close');
				        	return $this.showBatchResult(obj);
						},
						error : function() {
				        	$.messager.progress('close');
				            showError('新增失败,请联系管理员!');
				        }
					});
					
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			} ]
		});
	};
	
	this.showBatchResult = function(result) {
		var url = BasePath + '/bill_shop_balance_deduct/shopdeduct_generate_results';
		ygDialog({
			title : '批量生成费用处理结果',
			href : url,
			width : 1000,
			height : 'auto',
			isFrame : true,
			modal : true,
			showMask : true,
			buttons: [
			   {
			    text: '取消',
			    handler: function(dialog) {
			    dialog.close();
			   }
			  }],
			  onLoad : function(win, content) {
			  content.loadData(result);
			  //$("#btn-search").click();
			 }
		});
	};
	
	this.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/bill_shop_balance_deduct/do_import',
			'templateName' : '商场门店结算费用列表导入.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (isNotBlank(data.error)) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showSuc('数据导入成功');
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
	
}

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};

//检查费用管理系统扣费金额是否可以编辑
function checkSystemDeduAmountEdit(businessOrganNo,paramCode) {
//	var result=0;       
	var url = BasePath + '/base_setting/system_param_set/findSystemParamByParma';
//	var businessOrganNo = $('#shopNo').val();
//	var paramCode = $('#balanceEndDate').val();
	if(!businessOrganNo || $.trim(businessOrganNo) == '') {
		return false;
	}     
	var params = {
		 businessOrganNo : businessOrganNo,
		 paramCode : paramCode
	};
	return $.fas.ajaxRequestAsync(url, params ,function(data) {
 		return data; 
	});    
//	 ajaxRequest(url,params, function(data){
//		 alert("result---111 "+data);
//		 result=data;
//	 });
//	 return result;
};

function ShopBalanceDeductEditor() {
	var $this = this;
	this.insertRowCheck = function() {
		if($('#searchForm').form('validate')) {
			this.insertRow();
		}
	};
	
	this.saveCheck = function() {
		if($('#searchForm').form('validate')) {
			this.save();
		}
	};
	
	this.checkUpdate = function(options, rowIndex, rowData) {
		var tab = $('#dtlTab').tabs("getSelected");
		var tabIndex = $('#dtlTab').tabs('getTabIndex',tab);
		var status = rowData.balanceStatus;
		if(tabIndex == 0 && status == '2' ){
			showWarn("费用单已结算,不能修改");
			return false;
		}
		if(tabIndex == 0 && status == '3'){
			showWarn("费用单已开票,不能修改");
			return false;
		}
		if(tabIndex == 0 && rowData.balanceNo !=null){
			showWarn("已存在对应的结算单"+rowData.balanceNo+"，不能编辑,需要修改在结算单票前费用调整！");
			return false;
 	    }
		if(rowData.generateType == '0') {
			$("#deductionName").combogrid("disable");
			$("#costType").combobox("disable");
			$("#deductAmount").numberbox("disable");
		}
		$("#generateType").combobox("disable");
//		var processStatus = rowData.processStatus;
//		if(tabIndex == 0 && processStatus == '2' ){
//			showWarn("费用单已处理完成,不能修改编辑");
//			return false;
//		}
		return true;
	};
	
	// 列表页面点击删除按钮
	this.deleteRow = function() {
		var $this = this;
		var options = $this.options;
		var tab = $('#dtlTab').tabs("getSelected");
		var tabIndex = $('#dtlTab').tabs('getTabIndex',tab);
		/*var  dataGridId=null;
		if(tabIndex == 1) {
				dataGridId : "shopBalanceDeductAfterDataGrid"
		} else {
				dataGridId : "shopBalanceDeductBeforeDataGrid"
		}*/
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
		
	    if(checkedRows.length < 1){
	        showWarn("请选择要删除的记录!");
	        return;
	    }
		for(var i =0; i < checkedRows.length; i++){
			var row = checkedRows[i];
			if(row.id != null && row.id != '') {
				if(tabIndex == 0 && row.balanceStatus == '2'){
					showWarn("已结算，不能删除！");
					return;
		 	    }
				if(tabIndex == 0 && row.balanceStatus == '4') {
					showWarn("已开票，不能删除！");
					return;
			    }
				if(tabIndex == 0 && row.balanceNo !=null){
					showWarn("已存在对应的结算单"+row.balanceNo+"，不能删除！");
					return;
		 	    }
				var companyNo = row.companyNo;
				if((row.companyNo).substring(0,1) == 'K'){
				if(row.createUser != currentUser.username){
					showWarn("该单据非本人创建，不可删除！" );
			    	return;
				}
				}
			}
		}
		
		//删除未保存的行
	    for(var i=0; i<checkedRows.length; i++){
	    	var row = checkedRows[i];
	    	var index = $("#" + options.dataGridId).datagrid('getRowIndex', row);
	    	if(row.id == null) {
	    		$("#" + options.dataGridId).datagrid('deleteRow', index);
	    	}
		}
	    //重新获取选中行
	    checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
	    if(checkedRows.length < 1){
	        return;
	    }
		$.messager.confirm("确认","确定要删除已保存的数据?",function(r) {
			if (r) {
				var url = BasePath + '/bill_shop_balance_deduct/deleteBalanceDeduct';
				var idList = "";
				$.each(checkedRows, function(index,row) {
					
					idList+=row.id+','+row.rateId+";";
				});
				var params = {idList : idList.substring(0, idList.length-1)};
				ajaxRequestAsync(url,params,function(count){
					if(count){
						//删除已保存的行
						for(var i=0; i<checkedRows.length; i++){
					    	var row = checkedRows[i];
					    	var index = $("#" + options.dataGridId).datagrid('getRowIndex', row);
					    	$("#" + options.dataGridId).datagrid('deleteRow', index);
						}
						showSuc('删除成功');
					}else{
						showError('删除失败');
					}
				});
			}
		});
		
	};
	
	this.balanceStatus = function(value, rowData, rowIndex) {
	    var balanceStatus = [{'value':'1', 'text': '未结算'}, {'value':'2', 'text':'已结算'}, {'value':'3', 'text':'作废'}, {'value':'4', 'text':'已开票'}];
	    for(var i = 0; i < balanceStatus.length; i++) {
	        if(balanceStatus[i].value == value) {
	            return balanceStatus[i].text;
	        }
	    }
	    return "";
	};

	this.processStatus = function(value, rowData, rowIndex) {
	    var processStatus = [{'value':'1', 'text': '未完成'}, {'value':'2', 'text':'已完成'}];
	    for(var i = 0; i < processStatus.length; i++) {
	        if(processStatus[i].value == value) {
	            return processStatus[i].text;
	        }
	    }
	    return "";
	};
	
	this.dataCostType = function(value, rowData, rowIndex) {
	    var datacostType = [{'value':'1', 'text': '合同内'}, {'value':'2', 'text':'合同外'}];
	    for(var i = 0; i < datacostType.length; i++) {
	        if(datacostType[i].value == value) {
	            return datacostType[i].text;
	        }
	    }
	    return "";
	};

	this.generateType = function(value, rowData, rowIndex) {
	    var generateType = [{'value':'0', 'text': '系统生成'}, {'value':'1', 'text':'手工新增'}];
	    for(var i = 0; i < generateType.length; i++) {
	        if(generateType[i].value == value) {
	            return generateType[i].text;
	        }
	    }
	    return "";
	};
	
	this.dataCostDeductType = function(value, rowData, rowIndex) {
	    var datacostDeductType = [{'value':'1', 'text': '票前'}, {'value':'2', 'text':'票后'}];
	    for(var i = 0; i < datacostDeductType.length; i++) {
	        if(datacostDeductType[i].value == value) {
	            return datacostDeductType[i].text;
	        }
	    }
	    return "";
	};

	this.dataBasePayCode = function(value, rowData, rowIndex) {
	    var dataBasePayCode = [{'value':'01', 'text': '现金'}, {'value':'03', 'text':'现金券'}, {'value':'04', 'text':'银行卡'}
	    , {'value':'05', 'text':'商场卡'}, {'value':'06', 'text':'其它'}, {'value':'08', 'text':'商场券'}, {'value':'09', 'text':'预收款'}
	    , {'value':'20', 'text':'外卡'}];
	    for(var i = 0; i < dataBasePayCode.length; i++) {
	        if(dataBasePayCode[i].value == value) {
	            return dataBasePayCode[i].text;
	        }
	    }
	    return "";
	};
	
	this.dataBaseOther = function(value, rowData, rowIndex) {
	    var dataBaseOther = [{'value':'0', 'text': '商场VIP销售达成金额'}, {'value':'1', 'text':'商场VIP折让金额'}, {'value':'2', 'text':'销售金额'}];
	    for(var i = 0; i < dataBaseOther.length; i++) {
	        if(dataBaseOther[i].value == value) {
	            return dataBaseOther[i].text;
	        }
	    }
	    return "";
	};
	
	this.dataCostPayType = function(value, rowData, rowIndex) {
	    var datacostPayType = [{'value':'0', 'text': ''},{'value':'1', 'text': '帐扣'}, {'value':'2', 'text':'现金'}];
	    for(var i = 0; i < datacostPayType.length; i++) {
	        if(datacostPayType[i].value == value) {
	            return datacostPayType[i].text;
	        }
	    }
	    return "";
	};
//	this.changeCostDeductType = function(data) {
//		var ed = $('#' + $this.options.dataGridId).datagrid('getEditor', {index:$.fas.editIndex,field:'costPayType'});
//		if(data.value == '2') {
//			$(ed.target).combobox('enable');
//		} else {
//			$(ed.target).combobox('disable');
//			$(ed.target).combobox('clear');
//		} 
//	};
}

var deductDialog = null, deductDialogs = null,  currentUser=null; beforeeditor = null, aftereditor = null;	
$(function() {
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	//绑定店铺通用查询
	$("#shortName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
	
	// 初始化单据明细页签
	//initDtlTab();
	$.fas.extend(ShopBalanceDeductDialog, FasDialogController);
	$.fas.extend(ShopBalanceDeductEditor, FasEditorController);
	deductDialog = new ShopBalanceDeductDialog();
	deductDialog.init("/bill_shop_balance_deduct", {
		dataGridId : "shopBalanceDeductBeforeDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "商场门店结算费前列表导出",
		exportUrl : "/do_fas_export?costDeductType=1"
		
	});
	deductDialogs = new ShopBalanceDeductDialog();
	deductDialogs.init("/bill_shop_balance_deduct", {
		dataGridId : "shopBalanceDeductAfterDataGrid",
		exportTitle : "商场门店结算费后列表导出",
		exportUrl : "/do_fas_export?costDeductType=2"
		
	});
	
	beforeeditor = new ShopBalanceDeductEditor();
	beforeeditor.init("/bill_shop_balance_deduct", {
		dataGridDivId : 'beforeDataGridDiv',
		dataGridId : 'shopBalanceDeductBeforeDataGrid',
		saveUrl : "/bill_shop_balance_deduct/save",
		buildAddData : function(rowIndex) {
			//新增行时复制上一行部分字段
			var copyData = {};
			if(rowIndex > 0) {
				var copyRow = $("#shopBalanceDeductBeforeDataGrid").datagrid('getRows')[rowIndex-1];
				copyData = {
					shortName:copyRow.shortName,
					shopNo:copyRow.shopNo,
//					month:copyRow.month,
//					balanceStartDate:copyRow.balanceStartDate,
//					balanceEndDate:copyRow.balanceEndDate,
					companyNo:copyRow.companyNo,
					companyName:copyRow.companyName,
					bsgroupsNo:copyRow.bsgroupsNo,
					bsgroupsName:copyRow.bsgroupsName,
					mallNo:copyRow.mallNo,
					mallName:copyRow.mallName,
					organNo:copyRow.organNo,
					organName:copyRow.organName,
					brandNo:copyRow.brandNo,
					brandName:copyRow.brandName
				}
			}
			
			var row = $.extend({}, copyData);
			
			return row;
		},
		afterAdd : function(rowIndex) {		
			
			var deductAmountEditordis = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
			var companyNo = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'companyNo'});
			var dtlValue = checkSystemDeduAmountEdit($(companyNo.target).val(),'systemDeduAmountEdit');
			if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
				$(deductAmountEditordis.target).numberbox("disable");
			}
//			if( $.trim($(companyNo.target).val().substring(0,1)) == 'K'){
//			   $(deductAmountEditordis.target).numberbox("disable");
//			}
			var ed = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'costDeductType'});
			$(ed.target).combobox("setValue", "1");
			$(ed.target).combobox('disable');
			
			var balanceStatus = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'balanceStatus'});
			$(balanceStatus.target).combobox("setValue", "1");
			$(balanceStatus.target).combobox('disable');
			
			var generateType = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'generateType'});
			$(generateType.target).combobox("setValue", "1");
			$(generateType.target).combobox('disable');
			
			var deductType = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductType'});
			deductType.target.val(1);
			
//			改变"实际扣费金额"列的数据时，触发的函数  实际扣费金额-系统扣费金额=扣费差异金额
			var deductAmountEditor = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductAmount'});
			var actualAmountEditor = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:rowIndex,field:'actualAmount'});
			var diffAmountEditor = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:rowIndex,field:'diffAmount'});
			$(actualAmountEditor.target).bind('change',function(){
				var actualAmount = actualAmountEditor.target.val();	
				var deductAmount = deductAmountEditor.target.val();	
				if(!deductAmount) {
					deductAmount = 0;
				}
				diffAmountEditor.target.val(parseFloat(actualAmount- deductAmount).toFixed(2));
				
				var processStatusEditor = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
				var diffReason =  $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
				var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
				
				var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
				if(diffAmount1 == 0) {
					$(processStatusEditor.target).combobox("setValue", "2");
					$(processStatusEditor.target).combobox('disable');
				} else {
					$(processStatusEditor.target).combobox("setValue", "1");
					$(processStatusEditor.target).combobox('enable');

					if(diffReasonVal == null || $.trim(diffReasonVal) == '') {
						showWarn("请填写差异原因！");
						return;
					}
				}
			});	
		},
		afterUpdate : function(rowIndex, rowData) {
			if(rowData.generateType == '0') {
				$("#deductionName").combogrid("disable");
				$("#costType").combobox("disable");
				$("#deductAmount").numberbox("disable");
			}
			$("#generateType").combobox("disable");
			$.fas.setEditorVal({
				dataGridId : 'shopBalanceDeductBeforeDataGrid',
				rowIndex : rowIndex,
				field : 'month',
				value : rowData.month
			});
//			var ed = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'costPayType'});
//			if(rowData.costDeductType == '2') {
//				$(ed.target).combobox('enable');
//			} else {
//				$(ed.target).combobox('disable');
//			}
			
			var balanceNo = rowData.balanceNo;	
			if(balanceNo != null && $.trim(balanceNo) != ''){
				$("#shortName_").combogrid('disable');
			} else {
				$("#shortName_").combogrid('enable');
			}
			
//			$("#costDeductType").combobox("setValue", "1");
			
			if(balanceNo != null && $.trim(balanceNo) != ''){
				$("#month_").attr("readonly","readonly");
				$("#deductionName").combogrid('disable');
//				$("#brandName_").combogrid('disable');				
			} else {
				$("#month_").datebox('enable');
				$("#deductionName").combogrid('disable');
//				$("#brandName_").combogrid('disable');
			}
			
			var deductAmountEditordis = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
			var companyNo = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'companyNo'});
			var dtlValue = checkSystemDeduAmountEdit($(companyNo.target).val(),'systemDeduAmountEdit');
			if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
				$(deductAmountEditordis.target).numberbox("disable");
			}
//			if( $.trim($(companyNo.target).val().substring(0,1)) == 'K'){
//				$(deductAmountEditordis.target).numberbox("disable");
//			}
			var ed = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'costDeductType'});
			$(ed.target).combobox("setValue", "1");
			$(ed.target).combobox('disable');
			
			var ed = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'generateType'});
			$(ed.target).combobox('disable');
			
//			if(balanceNo !=null){
//				$(ed.target).combobox('disable');
//			} else {
//				$(ed.target).combobox('enable');
//			}
			
			var ed = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'costType'});
			if(balanceNo !=null){
				$(ed.target).combobox('disable');
			} else {
				$(ed.target).combobox('enable');
			}
			
//			var ed = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductAmount'});
//			if(balanceNo !=null){
//				$(ed.target).numberbox('disable');
//			} else {
//				$(ed.target).numberbox('enable');
//			}
			
//			改变"实际扣费金额"列的数据时，触发的函数  实际扣费金额-系统扣费金额=扣费差异金额
			var deductAmountEditor = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductAmount'});
			var actualAmountEditor = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:rowIndex,field:'actualAmount'});
			var diffAmountEditor = $("#shopBalanceDeductBeforeDataGrid").datagrid('getEditor',{index:rowIndex,field:'diffAmount'});
			$(actualAmountEditor.target).bind('change',function(){
				var actualAmount = actualAmountEditor.target.val();	
				var deductAmount = deductAmountEditor.target.val();	
				if(!deductAmount) {
					deductAmount = 0;
				}
				diffAmountEditor.target.val(parseFloat(actualAmount- deductAmount).toFixed(2));	
				
				var processStatusEditor = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
				var diffReason =  $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
				var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
				
				var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
				if(diffAmount1 == 0) {
					$(processStatusEditor.target).combobox("setValue", "2");
					$(processStatusEditor.target).combobox('disable');
				} else {
//					$(processStatusEditor.target).combobox("setValue", "1");
//					$(processStatusEditor.target).combobox('enable');

					if(diffReasonVal == null || $.trim(diffReasonVal) == '') {
						showWarn("请填写差异原因！");
						return;
					}
				}	
			});	
			
		},
		saveCallback : function() {
			$('#shopBalanceDeductBeforeDataGrid').datagrid('options').queryParams = $('#searchForm').form('getData');
		    $('#shopBalanceDeductBeforeDataGrid').datagrid('options').url = BasePath + '/bill_shop_balance_deduct/list.json?costDeductType=1';
			$('#shopBalanceDeductBeforeDataGrid').datagrid('load');
		},
		keyboard : false
	});
	 
	aftereditor = new ShopBalanceDeductEditor();
	aftereditor.init("/bill_shop_balance_deduct", {
		dataGridDivId : 'afterDataGridDiv',
		dataGridId : 'shopBalanceDeductAfterDataGrid',
		saveUrl : "/bill_shop_balance_deduct/save",
		buildAddData : function(rowIndex) {
			//新增行时复制上一行部分字段
			var copyData = {};
			if(rowIndex > 0) {
				var copyRow = $("#shopBalanceDeductAfterDataGrid").datagrid('getRows')[rowIndex-1];
				copyData = {
					shortName:copyRow.shortName,
					shopNo:copyRow.shopNo,
					 month:copyRow.month,
					 balanceStartDate:copyRow.balanceStartDate,
					 balanceEndDate:copyRow.balanceEndDate,
					companyNo:copyRow.companyNo,
					companyName:copyRow.companyName,
					bsgroupsNo:copyRow.bsgroupsNo,
					bsgroupsName:copyRow.bsgroupsName,
					mallNo:copyRow.mallNo,
					mallName:copyRow.mallName,
					organNo:copyRow.organNo,
					organName:copyRow.organName,
					brandNo:copyRow.brandNo,
					brandName:copyRow.brandName
				}
			}
			
			var row = $.extend({}, copyData);
			
			return row;
		},
		afterAdd : function(rowIndex) {
			
			var deductAmountEditordis = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
			var companyNo = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'companyNo'});
			var dtlValue = checkSystemDeduAmountEdit($(companyNo.target).val(),'systemDeduAmountEdit');
			if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
				$(deductAmountEditordis.target).numberbox("disable");
			}
//			if( $.trim($(companyNo.target).val().substring(0,1)) == 'K'){
//				$(deductAmountEditordis.target).numberbox("disable");
//			}
			var ed = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'costDeductType'});
			$(ed.target).combobox("setValue", "2");
			$(ed.target).combobox('disable');
			
			var ed = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'generateType'});
			$(ed.target).combobox("setValue", "1");
			$(ed.target).combobox('disable');
			
			var generateType = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'generateType'});
			generateType.target.val(1);
			
			var deductType = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductType'});
			deductType.target.val(1);
			
//			改变"实际扣费金额"列的数据时，触发的函数  实际扣费金额-系统扣费金额=扣费差异金额
			var deductAmountEditor = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductAmount'});
			var actualAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'actualAmount'});
			var diffAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'diffAmount'});
			$(actualAmountEditor.target).bind('change',function(){
				var actualAmount = actualAmountEditor.target.val();	
				var deductAmount = deductAmountEditor.target.val();	
				if(!deductAmount) {
					deductAmount = 0;
				}
				diffAmountEditor.target.val(parseFloat(actualAmount- deductAmount).toFixed(2));	
				var processStatusEditor = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
				var diffReason =  $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
				var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
				
				var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
				if(diffAmount1 == 0) {
					$(processStatusEditor.target).combobox("setValue", "2");
					$(processStatusEditor.target).combobox('disable');
				} else {
//					$(processStatusEditor.target).combobox("setValue", "1");
//					$(processStatusEditor.target).combobox('enable');

					if(diffReasonVal == null || $.trim(diffReasonVal) == '') {
						showWarn("请填写差异原因！");
						return;
					}
			   }
			});	
			
//			改变"实际扣费金额"列的数据时，触发的函数  实际扣费金额-发票金额=费用余额  actualAmount 
			var actualAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'actualAmount'});
			var invoiceAmountEditor = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'invoiceAmount'});
			var accountDeductAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'accountDeductAmount'});
			$(actualAmountEditor.target).bind('keyup',function(){
				var actualAmount = actualAmountEditor.target.val();	
				var invoiceAmount = invoiceAmountEditor.target.val();	
				if(!invoiceAmount) {
					invoiceAmount = 0;
				}
				accountDeductAmountEditor.target.val(parseFloat(actualAmount- invoiceAmount).toFixed(2));			
			});	
		},
		afterUpdate : function(rowIndex, rowData) {
			if(rowData.generateType == '0') {
				$("#deductionName").combogrid("disable");
				$("#costType").combobox("disable");
				$("#deductAmount").numberbox("disable");
			}
			$("#generateType").combobox("disable");
			$.fas.setEditorVal({
				dataGridId : 'shopBalanceDeductAfterDataGrid',
				rowIndex : rowIndex,
				field : 'month',
				value : rowData.month
			});
//			var ed = $('#shopBalanceDeductBeforeDataGrid').datagrid('getEditor', {index:rowIndex,field:'costPayType'});
//			if(rowData.costDeductType == '2') {
//				$(ed.target).combobox('enable');
//			} else {
//				$(ed.target).combobox('disable');
//			}
			
			var balanceNo = rowData.balanceNo;		
			if(balanceNo != null && $.trim(balanceNo) != ''){
				$("#shortName_").combogrid('disable');
			} else {
				$("#shortName_").combogrid('enable');
			}
			
			if(balanceNo != null && $.trim(balanceNo) != ''){
				$("#month_").attr("readonly","readonly");
				$("#deductionName").combogrid('disable');
//				$("#brandName_").combogrid('disable');				
			} else {
				$("#month_").datebox('enable');
				$("#deductionName").combogrid('disable');
//				$("#brandName_").combogrid('disable');
			}
			
			var ed = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'costDeductType'});
			$(ed.target).combobox("setValue", "2");
			$(ed.target).combobox('disable');
//			if(balanceNo !=null){
//				$(ed.target).combobox('disable');
//			} else {
//				$(ed.target).combobox('enable');
//			}
			
			var deductAmountEditordis = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
			var companyNo = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'companyNo'});
			var dtlValue = checkSystemDeduAmountEdit($(companyNo.target).val(),'systemDeduAmountEdit');
			if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
				$(deductAmountEditordis.target).numberbox("disable");
			}
//			if( $.trim($(companyNo.target).val().substring(0,1)) == 'K'){ 
//				$(deductAmountEditordis.target).numberbox("disable");
//			}
			var ed = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'generateType'});
			$(ed.target).combobox('disable');
			
			var ed = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'costType'});
			if(balanceNo !=null){
				$(ed.target).combobox('disable');
			} else {
				$(ed.target).combobox('enable');
			}
			
//			var ed = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductAmount'});
//			if(balanceNo !=null){
//				$(ed.target).numberbox('disable');
//			} else {
//				$(ed.target).numberbox('enable');
//			}
			
//			改变"实际扣费金额"列的数据时，触发的函数  实际扣费金额-系统扣费金额=扣费差异金额
			var deductAmountEditor = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'deductAmount'});
			var actualAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'actualAmount'});
			var diffAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'diffAmount'});
			$(actualAmountEditor.target).bind('change',function(){
				var actualAmount = actualAmountEditor.target.val();	
				var deductAmount = deductAmountEditor.target.val();	
				if(!deductAmount) {
					deductAmount = 0;
				}
				diffAmountEditor.target.val(parseFloat(actualAmount- deductAmount).toFixed(2));	
				var processStatusEditor = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
				var diffReason =  $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
				var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
				
				var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
				if(diffAmount1 == 0) {
					$(processStatusEditor.target).combobox("setValue", "2");
					$(processStatusEditor.target).combobox('disable');
				} else {
//					$(processStatusEditor.target).combobox("setValue", "1");
//					$(processStatusEditor.target).combobox('enable');

					if(diffReasonVal == null || $.trim(diffReasonVal) == '') {
						showWarn("请填写差异原因！");
						return;
					}
			   }
			});	
			
//			改变"实际扣费金额"列的数据时，触发的函数  实际扣费金额-发票金额=费用余额  actualAmount 
			var actualAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'actualAmount'});
			var invoiceAmountEditor = $('#shopBalanceDeductAfterDataGrid').datagrid('getEditor', {index:rowIndex,field:'invoiceAmount'});
			var accountDeductAmountEditor = $("#shopBalanceDeductAfterDataGrid").datagrid('getEditor',{index:rowIndex,field:'accountDeductAmount'});
			$(actualAmountEditor.target).bind('keyup',function(){
				var actualAmount = actualAmountEditor.target.val();	
				var invoiceAmount = invoiceAmountEditor.target.val();	
				if(!invoiceAmount) {
					invoiceAmount = 0;
				}
				accountDeductAmountEditor.target.val(parseFloat(actualAmount- invoiceAmount).toFixed(2));			
			});	
			
		},
		saveCallback : function() {
			$('#shopBalanceDeductAfterDataGrid').datagrid('options').queryParams = $('#searchForm').form('getData');
		    $('#shopBalanceDeductAfterDataGrid').datagrid('options').url = BasePath + '/bill_shop_balance_deduct/list.json?costDeductType=2';
			$('#shopBalanceDeductAfterDataGrid').datagrid('load');
		},
		keyboard : false 
	});
});