function ItemCostDialog() { 
	var $this = this;
	this.generateCost = function(){
		$('#genarateDataForm').form('clear');
		$("input[type='hidden']", '#genarateFormPanel').val('');
		//新增时默认是新款
		$("#radio1").attr("checked","checked");
		
		var currentDate = new Date();
		var currentMonth = currentDate.getMonth()+1;
		var currentYear = currentMonth==0 ? currentDate.getFullYear()-1:currentDate.getFullYear();
		currentMonth = currentMonth==0 ? 12 :currentMonth;
		currentDate.setMonth(currentDate.getMonth());
		$("#genarateYear").combobox({
			url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR&currentYear=' + currentYear,
			valueField : 'itemname',    
			textField : 'itemname',
			panelHeight:"auto",
			width : 160,
			required:true,
			editable:false,
			value:currentYear
		});
		$("#genarateMonth").initCombox({
			data:dialog.monthData,
			valueField:"value",
			textField:"text",
			panelHeight:"auto",
			width : 160,
			required:true,
			editable:false,
			value:currentMonth
		});
		
		ygDialog({
			title : '生成单位成本',
			target : $('#genarateFormPanel'),
			width : 680,
			height : 300,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					$this.generateSave(dialog);
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
	
	this.updateCost = function(){
		$('#updateDataForm').form('clear');
		$("#radio3").attr("checked","checked");
		$("input[type='hidden']", '#updateDataForm').val('');
		
		ygDialog({
			title : '分配单据成本',
			target : $('#updateFormPanel'),
			width : 520,
			height : 400,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					$this.updateSave(dialog);
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

	this.generateSave = function(dialog){
		var fromObj = $('#genarateDataForm');
		var validateForm = fromObj.form('validate');
		// 1.校验必填项
		if (validateForm == false) {
			return;
		}
		if (!$this.checkItemBrandData()) {
			showError('所选的商品与品牌不匹配!');
			return false;
		}
		if (!$this.checkCompanySettleDate()) {
			showError('公司结账日未设置或已关账!');
			return false;
		}
		if(!$this.checkCompanyBrandUnitSettleDate()){
			showError('公司品牌部结账日未设置或已关账!');
			return false;
		}

		var async = true; //同步异步开关，true使用异步方式，false采用原方式
		var url = BasePath + "/item_cost/check_generate_condition";
		if(!async) {
			fromObj.form('submit', {
				url: url,
				dataType: 'json',
				success: function (result) {
					if (result && JSON.parse(result).success) {
						showSuc('正在批量生成成本，请稍后查看结果!');
						dialog.close();
						return;
					} else if (result) {
						showError(JSON.parse(result).message);
					}
				},
				error: function () {
					showError('新增失败,请联系管理员!');
				}
			});
		}else{
			var params = fromObj.form('getData');
			params.async = async;
			var panel = new $.fas.ProgressPanel({
				title:"成本生成",url:url,onclose:function(){
					dialog.close();
				}
			}, params);
			panel.open();
		}
		
//		// 3.保存
//		var saveFn = function(returnData) {
////			var url = BasePath + "/item_cost/generate_item_cost";
//			var url = BasePath + "/item_cost/check_generate_condition";
//			fromObj.form('submit', {
//				url : url,
//				dataType : 'json',
//				success : function(result) {
//					if(result && JSON.parse(result).success){
//						  showSuc('正在批量生成成本，请稍后查看结果!');
//						  dialog.close();
//						  return;
//					 } else if(result){
//						  showError(JSON.parse(result).message);
//					 }
//				},
//				error : function() {
//					showError('新增失败,请联系管理员!');
//				}
//			});
//		};
	};
	
	this.updateSave = function(dialog){
		var fromObj = $('#updateDataForm');
		var validateForm = fromObj.form('validate');
		// 1.校验必填项
		if (validateForm == false) {
			return;
		}
		
		// 3.保存
		var saveFn = function() {
			var url =  "/fas/item_cost/update_bill_cost";
			if(!fromObj.form('validate'))
				return;
			var params = fromObj.form('getData');
			if( params.assignType == 1 ){
				params.async = "true";
				var panel = new $.fas.ProgressPanel({
					title:"成本分配",url:url,onclose:function(){
						dialog.close();
					}
				}, params);
				panel.open();
			}
			else{
				$.messager.progress({
					title:'请稍后',
					msg:'正在处理...'
				});
				$.post( url,params).then(function(data){
					$.messager.progress('close');
					if(data.status == 1){
						showSuc('分配成本成功!');
					}
					else{
						showWarn('分配成本失败，请联系管理员!');
					}
				}).fail(function(){
					$.messager.progress('close');
					showError('分配成本失败,请联系管理员!');
				});
			}
		};
		
		saveFn();
	};

	this.checkItemBrandData = function(){
		var conflictFlag=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: "/fas/item_cost/conflict_item_brand.json",
			  data: $('#genarateDataForm').form('getData'),
			  cache: true,
			  async:false, // 一定要
			  dataType: 'json', 
			  success: function(result){
				  if(result && result.success){
					  conflictFlag = true;
				  }
			  }
	   });
	 	return conflictFlag;
	};

	this.checkCompanySettleDate = function(){
		var canDo=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/item_cost/check_settle_date.json",
			  data: $('#genarateDataForm').form('getData'),
			  cache: true,
			  async:false, // 一定要
			  dataType: 'json', 
			  success: function(result){
				  if(result && result.success){
					  canDo = true;
				  }
			  }
	   });
	 	return canDo;
	};
	
	this.checkCompanyBrandUnitSettleDate = function (){
		var canDo=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/item_cost/check_brand_unit_settle_date.json",
			  data: $('#genarateDataForm').form('getData'),
			  cache: true,
			  async:false, // 一定要
			  dataType: 'json', 
			  success: function(result){
				  if(result && result.success){
					  canDo = true;
				  }
			  }
	   });
	 	return canDo;
	};
	
	this.checkGMSOrganClosingDate = function(){
		var canDo=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/item_cost/check_can_generate.json",
			  data: $('#genarateDataForm').form('getData'),
			  cache: true,
			  async:false, // 一定要
			  dataType: 'json', 
			  success: function(result){
				  if(result && result.success){
					  canDo = true;
				  }
			  }
	   });
	 	return canDo;
	};
	
	this.monthData =  monthData;

	this.showAudit = function(){
		var auditListPanel = $('#auditListPanel');
		$(':input','#auditListPanel').not(
		':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
		
		var auditGrid = $('#auditDataGrid');
		auditGrid.datagrid('loadData',{total:0,rows:[]});
		auditGrid.datagrid({'url':BasePath+'/item_cost/audit_list.json','pageNumber':1 });
		
		ygDialog({
			title : '生成成本结果查询',
			target : $('#auditListPanel'),
			width : 800,
			height : 280,
			buttons : [ ]
		});
	};
	
	this.genearateZeroItemCost = function(){
		var dataRow = $("#dataGridDiv").datagrid('getRows');
		if($("#zeroAmount").attr("checked") && dataRow.length > 0){
			$.messager.confirm("确认", "是否以当前生效地区价重新生成单位成本？", function (r) {  
				if (r) {  
					$(':input','#generateZeroItemCost').not(
					':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
					
					var params = $('#searchForm').form('getData');
					var url = BasePath+'/item_cost/item_cost_list.json';
					
					$('#zeroItemCostDatagrid').datagrid('options').queryParams = params;
					$('#zeroItemCostDatagrid').datagrid('options').url = url;
					$('#zeroItemCostDatagrid').datagrid('load');
					
					ygDialog({
						title : '零单位成本',
						target : $('#generateZeroItemCost'),
						width : 800,
						height : 280,
						buttons : [ {
							text : '生成成本',
							iconCls : 'icon-save',
							handler : function(dialog) {
								$this.genearateZeroSave(dialog);
							}
						}, {
							text : '取消',
							iconCls : 'icon-cancel',
							handler : function(dialog) {
								dialog.close();
							}
						} ]
					});
				}else{
					return false;
				}
			});
			
		}
	};

	this.genearateZeroSave = function(dialog){
		var checkedRows = $('#zeroItemCostDatagrid').datagrid('getChecked');

		if (checkedRows.length == 0) {
			showWarn('请选择要生成成本的行..');
			return;
		}
		var conflictFlag = false;
		$.each(checkedRows, function(index, row) {
			row.itemNos = row.itemNo;
			if(row.brandNo != undefined){
				row.brandUnitNo = row.brandNo.substring(0,2);//品牌部
			}
			
			//校验商品和品牌是否匹配
			$.ajax({
				  type: 'POST',
				  url: BasePath + "/item_cost/conflict_item_brand.json",
				  data: row,
				  cache: true,
				  async:false, // 一定要
				  dataType: 'json', 
				  success: function(result){
					  if(result && result.success){
						  conflictFlag = true;
					  }
				  }
			});
			if(!conflictFlag){
				showError("["+row.itemCode + ']所选的商品与品牌不匹配!');
				return false;
			}
			//校验公司结账日是否设置或是否已关账
			$.ajax({
				  type: 'POST',
				  url: BasePath + "/item_cost/check_settle_date.json",
				  data: row,
				  cache: true,
				  async:false, // 一定要
				  dataType: 'json', 
				  success: function(result){
					  if(result && result.success){
						  conflictFlag = true;
					  }
				  }
			});
			if(!conflictFlag){
				showError(row.companyNo + '公司结账日未设置或已关账!');
				return false;
			}
			//校验公司品牌部结账日是否设置或是否已关账
			$.ajax({
				  type: 'POST',
				  url: BasePath + "/item_cost/check_brand_unit_settle_date.json",
				  data: row,
				  cache: true,
				  async:false, // 一定要
				  dataType: 'json', 
				  success: function(result){
					  if(result && result.success){
						  conflictFlag = true;
					  }
				  }
			});
			if(!conflictFlag){
				showError(row.companyNo + '公司品牌部'+row.brandUnitNo+'结账日未设置或已关账!');
				return false;
			}
		});
		
		if(conflictFlag==false){
			return;
		}
		
		$.messager.confirm("确认", "成本核算前需要进行FAS异常价格检查，是否继续生成公司结存?", function (r) {  
			if (r) {
				var changeRows = {checked : JSON.stringify(checkedRows)};
				$.ajax({
					  type: 'POST',
					  url: BasePath + "/item_cost/genearate_zero_item_cost",
					  data: changeRows,
					  cache: true,
					  async:false, // 一定要
					  dataType: 'json', 
					  success : function(result) {
						  if(result && result.success){
							  showSuc('正在批量生成成本，请稍后查看结果!');
							  dialog.close();
							  return;
						  } else if(result){
							  showError(JSON.parse(result).message);
						  }
					  },
					  error : function() {
						  showError('新增失败,请联系管理员!');
					  }
				});
			}else{
				return false;
			}
		});
		
	};

}

var auditStatusFormat =[{'value':'0', 'text': '运行中'},{'value':'1', 'text':'生成成功'},{'value':'2', 'text':'生成失败'}];
auditStatusformatter= function(value, rowData, rowIndex) {
    for (var i = 0; i < auditStatusFormat.length; i++) {
        if (auditStatusFormat[i].value == value) {
            return auditStatusFormat[i].text;
        }
    }
    return "生成失败";
};

var dialog = null;
$(function() {
	$.fas.extend(ItemCostDialog, FasDialogController);
	dialog = new ItemCostDialog();
	dialog.init("/item_cost", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/item_cost_list.json",
		searchFormId : "searchForm",
		exportTitle : "加权成本导出",
		exportUrl : "/do_fas_export",
		exportType : "common"
	});
	
	var currentDate = new Date();
	var currentYear = currentDate.getFullYear();
	var currentMonth = currentDate.getMonth() + 1;
	$("#monthCondition").initCombox({
		data:dialog.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentMonth
	});
	$("#genarateMonth").initCombox({
		data:dialog.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentMonth
	});
	$('#yearCondition').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentYear
	});
	$('#genarateYear').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentYear
	});
	$('#assignMonth').combobox({
		data:dialog.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentMonth
	});

	$('#assignYear').combobox({
		//url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		data:yearData,
		valueField : 'value',
		textField : 'text',
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentYear
	});
	
});
