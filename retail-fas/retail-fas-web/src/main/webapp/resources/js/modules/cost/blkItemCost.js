var blkItemCost = new Object();

//查询
blkItemCost.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/blk_item_cost/item_cost_list.json';
		$('#dataGridDiv').datagrid('options').queryParams= params;
		$('#dataGridDiv').datagrid('options').url= url;
		$('#dataGridDiv').datagrid('load');
	}
};

//清空
blkItemCost.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("tbody").find("input").val("");
};

//导出
blkItemCost.exportExcel = function(){
	$.fas.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/blk_item_cost/do_fas_export",
		exportTitle : "加权成本导出"
	});
};

//生成成本
blkItemCost.generateCost = function(){
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
		data:blkItemCost.monthData,
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
				blkItemCost.generateSave(dialog);
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

//生成成本结果查询
blkItemCost.showAudit = function(){
	$(':input','#auditListPanel').not(
	':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
	
	var auditGrid = $('#auditDataGrid');
	auditGrid.datagrid('loadData',{total:0,rows:[]});
	auditGrid.datagrid({'url':BasePath+'/blk_item_cost/audit_list.json','pageNumber':1 });
	
	ygDialog({
		title : '生成成本结果查询',
		target : $('#auditListPanel'),
		width : 800,
		height : 280,
		buttons : [ ]
	});
};

//分配成本
blkItemCost.updateCost = function(){
	$('#updateDataForm').form('clear');
	$("#radio3").attr("checked","checked");
	$("input[type='hidden']", '#updateDataForm').val('');
	
	ygDialog({
		title : '分配单据成本',
		target : $('#updateFormPanel'),
		width : 360,
		height : 350,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				blkItemCost.updateSave(dialog);
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

blkItemCost.generateSave = function(dialog){
	var fromObj = $('#genarateDataForm');
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	//判断商品与品牌是否匹配
	var checkItemBrandData = function(){
		var conflictFlag=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/blk_item_cost/conflict_item_brand.json",
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
	
	//判断公司是否关账
	var checkCompanySettleDate = function(){
		var canDo=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/blk_item_cost/check_settle_date.json",
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
	
	//判断公司品牌是否关账
	var checkCompanyBrandUnitSettleDate = function (){
		var canDo=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/blk_item_cost/check_brand_unit_settle_date.json",
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

	
	if (!checkItemBrandData()) {
		showError('所选的商品与品牌不匹配!');
		return false;
	}
	if (!checkCompanySettleDate()) {
		showError('公司结账日未设置或已关账!');
		return false;
	}
	if(!checkCompanyBrandUnitSettleDate()){
		showError('公司品牌部结账日未设置或已关账!');
		return false;
	}
	
	var url = BasePath + "/blk_item_cost/check_generate_condition";
	fromObj.form('post', {
		url : url,
		dataType : 'json',
		success : function(result) {
			if(result && JSON.parse(result).success){
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
};

blkItemCost.updateSave = function(dialog){
	var fromObj = $('#updateDataForm');
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	
	// 3.保存
	var saveFn = function(returnData) {
		var url =  "/fas/blk_item_cost/update_bill_cost";
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
			$.post(url,params).then(function(data){
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

blkItemCost.genearateZeroItemCost = function(){
	var dataRow = $("#dataGridDiv").datagrid('getRows');
	if($("#zeroAmount").attr("checked") && dataRow.length > 0){
		$.messager.confirm("确认", "是否以当前生效地区价重新生成单位成本？", function (r) {  
			if (r) {  
				$(':input','#generateZeroItemCost').not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
				
				var params = $('#searchForm').form('getData');
				var url = BasePath+'/blk_item_cost/item_cost_list.json';
				
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
							blkItemCost.genearateZeroSave(dialog);
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

blkItemCost.genearateZeroSave = function(dialog){
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
			  url: BasePath + "/blk_item_cost/conflict_item_brand.json",
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
			showError("["+row.styleNo + ']所选的款号与品牌不匹配!');
			return false;
		}
		//校验公司结账日是否设置或是否已关账
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/blk_item_cost/check_settle_date.json",
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
			showError(row.comapnyNo + '公司结账日未设置或已关账!');
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
	
	var changeRows = {checked : JSON.stringify(checkedRows)};
	$.ajax({
		  type: 'POST',
		  url: BasePath + "/blk_item_cost/genearate_zero_item_cost",
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
	
};


blkItemCost.monthData = [{"value":"1","text":"1"},{"value":"2","text":"2"},
                  {"value":"3","text":"3"},{"value":"4","text":"4"},
                  {"value":"5","text":"5"},{"value":"6","text":"6"},
                  {"value":"7","text":"7"},{"value":"8","text":"8"},
                  {"value":"9","text":"9"},{"value":"10","text":"10"},
                  {"value":"11","text":"11"},{"value":"12","text":"12"}];

var auditStatusFormat =[{'value':'0', 'text': '运行中'},{'value':'1', 'text':'生成成功'},{'value':'2', 'text':'生成失败'}];
auditStatusformatter= function(value, rowData, rowIndex) {
    for (var i = 0; i < auditStatusFormat.length; i++) {
        if (auditStatusFormat[i].value == value) {
            return auditStatusFormat[i].text;
        }
    }
    return "生成失败";
};

$(function() {
	var currentDate = new Date();
	var currentYear = currentDate.getFullYear();
	var currentMonth = currentDate.getMonth() + 1;
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
	$("#monthCondition").initCombox({
		data:blkItemCost.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentMonth
	});
	$("#genarateMonth").initCombox({
		data:blkItemCost.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentMonth
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
		data:blkItemCost.monthData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentMonth
	});
	$('#assignYear').combobox({
		url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueField : 'itemname',    
		textField : 'itemname',
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false,
		value:currentYear
	});
});