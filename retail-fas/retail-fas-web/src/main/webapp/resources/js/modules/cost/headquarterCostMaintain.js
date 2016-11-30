
var headquarterCostMaintain = {};

headquarterCostMaintain.initAddRuleData = [
                                      {"value":"1","text":"厂进价"},
                                      {"value":"2","text":"牌价"}
                               ];

headquarterCostMaintain.generatePriceByRule = function(){
	$('#genarateRuleDataForm').form('clear');
	ygDialog({
		title : '选择规则生成总部价',
		target : $('#genarateRuleFormPanel'),
		width : 480,
		height : 300,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				var fromObj = $('#genarateRuleDataForm');
				var validateForm = fromObj.form('validate');
				if (validateForm == false) {
					return;
				}
				$.messager.progress({
					title:'请稍后',
					msg:'正在处理中...'
				}); 
				fromObj.form('submit',{
					url : BasePath + '/headquarter_cost_maintain/generate_cost_by_rule',
					success:function(result){
						if(result){
							  showSuc('生成成功!');
							  $("#btn-search").click();
							  $("#genarateRuleFormPanel").window('close');
							  $.messager.progress('close');
						  } else {
							  showError('操作失败,请联系管理员!');
							  $.messager.progress('close');
						  }
					},
					error:function(){
						$.messager.progress('close');
						showError('操作失败');
					}
				});
				dialog.close();
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

headquarterCostMaintain.initCategory = function(){
	var _this = $('#multiCategoryNo');
	var _no = _this.next();
	_this.combogrid({
		panelWidth:280,
	    panelHeight : 250,
        mode:'remote',
        multiple:true,
        idField:'name',
        textField:'name',
        url : BasePath + '/common_util/getPageCategory?levelid=1&rows=500',
        separator:',',
        selectOnCheck : true,
        checkOnSelect : true,
        frozenColumns:[[{field:'ck',checkbox:true,width:30}]],
        columns:[[
        {field:'code',title:'编码',width:80,align:'left'},
        {field:'name',title:'名称',width:150,align:'left'}
        ]],
        pagination:false,
        onHidePanel:function(){
        	var rows = _this.combogrid('grid').datagrid('getSelections');
        	var callback = _this.combo('options').callback;
    		if(rows.length > 0){
    			var code = '';
    			$.each(rows,function(index,item){
    				code +="'"+item.code+"',";
    			});
    			_no.val("("+code.substring(0,code.length-1)+")");
        	}else{
        		_no.val('');
        		_this.combogrid('clear');
        	}
        }
	});
}

headquarterCostMaintain.addBasisFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < headquarterCostMaintain.initAddRuleData.length; i++) {
		if (headquarterCostMaintain.initAddRuleData[i].value == value) {
			return headquarterCostMaintain.initAddRuleData[i].text;
		}
	}
};

headquarterCostMaintain.ruleChangeData = {};

headquarterCostMaintain.changeItemEvent = function(){
	$("#addRuleNo").combobox('clear');
	$("#headquarterCost").val("");
	$("#effectiveTime").val("");
	$("#addBasis").val("");
	$("#addBasisName").val("");
	$("#addPrice").val("");
	$("#addDiscount").val("");
	$("#discountRate").val("");
	  
	var reqParam = {"itemNo" : $("#itemNo_").val()};
	var url = BasePath + '/headquarter_cost_maintain/generate_cost.json';
	$.ajax({
		  async:false,
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: false,
		  dataType : 'json',
		  success: function(data){
			  $("#brandNo").val(data.brandNo);
			  $("#brandName").val(data.brandName);
			  $("#supplierNo").val(data.supplierNo);
			  $("#supplierName").val(data.supplierName);
			  $("#suggestTagPrice").val(data.suggestTagPrice);
			  $("#tagPrice").val(data.tagPrice);
			  $("#factoryPrice").val(data.factoryPrice);
			  headquarterCostMaintain.ruleChangeData = data.matchedHeadquarterPriceRule;
			  headquarterCostMaintain.initHQRuleNoCombobox();
			  if(data.addRuleNo != ''){
				  $("#addRuleNo").combobox('setValue',data.addRuleNo);
			  }
			  if(data.failMessage != ''){
				  if(data.addRuleNo == ''){
					  $("#addRuleNo").combobox('loadData','');
				  }
				  showWarn(data.failMessage);
				  return false;
			  }
			  $("#addBasis").val(data.addBasis);
			  $("#addBasisName").val(data.addBasisName);
			  $("#addPrice").val(data.addPrice);
			  $("#addDiscount").val(data.addDiscount);
			  $("#discountRate").val(data.discountRate);
			  $("#headquarterCost").val(data.headquarterCost);
			  $("#effectiveTime").datebox('setValue',formatDatebox(data.effectiveTime));
		  }
	});
};

headquarterCostMaintain.initHQRuleNoCombobox = function(){
	 $("#addRuleNo").combobox({
			data:headquarterCostMaintain.ruleChangeData,
			valueField:"addRuleNo",
			textField:"addRuleNo",
			panelHeight:"auto",
			width : 160,
			editable:false,
			onSelect : function(param) {
				var choosedData = {"itemNo" : $("#itemNo_").val(),"supplierNo" : $("#supplierNo").val(),
						"suggestTagPrice" : $("#suggestTagPrice").val(),"addRuleNo" : param.addRuleNo};
				$.ajax({
					  type: 'POST',
					  url: BasePath + '/headquarter_cost_maintain/rule_choosed.json',
					  cache: false,
					  async:false, // 一定要
					  data: choosedData,
					  dataType: 'json', 
					  success: function(record){
						  if(record.failMessage != ''){
							  $("#addBasis").val("");
							  $("#addBasisName").val("");
							  $("#addPrice").val("");
							  $("#addDiscount").val("");
							  $("#discountRate").val("");
							  $("#headquarterCost").val("");
							  $("#tagPrice").val("");
							  $("#factoryPrice").val("");
							  $("#effectiveTime").datebox('setValue',"");
							  showWarn(record.failMessage);
							  return false;
						  }
						  $("#tagPrice").val(record.tagPrice);
						  $("#factoryPrice").val(record.factoryPrice);
						  $("#addBasis").val(record.addBasis);
						  $("#addBasisName").val(record.addBasisName);
						  $("#addPrice").val(record.addPrice);
						  $("#addDiscount").val(record.addDiscount);
						  $("#discountRate").val(record.discountRate);
						  $("#headquarterCost").val(record.headquarterCost);
						  $("#effectiveTime").datebox('setValue',formatDatebox(record.effectiveTime));
					  }
			     });
			}
		});
}

function formatDatebox(value) {
    if (value == null || value == '') {
      return '';
    }
    var dt;
    if (value instanceof Date) {
      dt = value;
    } else {
      dt = new Date(value);
    }
    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
};

headquarterCostMaintain.checkExistFun = function(url,checkColumnJsonData){
	var checkExist=false;
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkColumnJsonData,
		  cache: false,
		  async:false, // 一定要
		  dataType: 'text', 
		  success: function(totalData){
			  totalData = parseInt(totalData,10);
			  if(totalData>0){
				  checkExist=true;
			  }
		  }
     });
 	return checkExist;
};

//额外操作
var extra_operate = {
	//新增保存时的校验规则
	checkSave : function(){
		return true;
	},
	checkDel :  function(checkedRows){
		var checkResult = true;
		$.each(checkedRows, function(index, item) {
			//校验规则是否已经被使用
			var checkReferedRuleData = {"itemNo" : item.itemNo,"effectiveTime" : item.effectiveTime};
			var checkUrl = BasePath + "/headquarter_cost_maintain/check_region_price.json";
			if (headquarterCostMaintain.checkExistFun(checkUrl, checkReferedRuleData)) {
				checkResult = false;
			}
		});
		if(!checkResult){
			showError('该总部价格已被地区价格维护使用,不能删除!');
			return false;
		};
		return true;
	},
	// 进入新增页面初始化
	initAdd : function(){
		
	},
	// 载入页面后的样式设置
	loadedAdd: function(){
		//清空表单
		$('#dataForm').form("clear");
		$('#id').val("");
		
//		$("#itemName_").addClass("disabled").attr("readonly", true);
		$("#brandName").addClass("disabled").attr("readonly", true);
		$("#supplierName").addClass("disabled").attr("readonly", true);
		$("#addBasisName").addClass("disabled").attr("readonly", true);
		$("#addPrice").addClass("disabled").attr("readonly", true);
		$("#addDiscount").addClass("disabled").attr("readonly", true);
		$("#discountRate").addClass("disabled").attr("readonly", true);
		
		$("#addRuleNo").combobox('loadData','');
		
	},
	// 进入修改页面的初始化
	initUpdate: function(rowData){
		//校验规则是否已经被使用
		var checkReferedRuleData = {"itemNo" : rowData.itemNo};
		var checkUrl = BasePath + "/headquarter_cost_maintain/check_region_price.json";
		if (headquarterCostMaintain.checkExistFun(checkUrl, checkReferedRuleData)) {
			showError('该总部价格已被地区价格维护使用,不能修改!');
			return false;
		}
		
		var reqParam = {"itemNo" : rowData.itemNo, "addRuleNo" : rowData.addRuleNo};
		headquarterCostMaintain.initAddRuleNoCombox(reqParam);
		
		return true;
	},
	// 修改页面保存校验
	checkUpdate: function(){
		//校验是否重复
		var checkRuleNoData = {"itemNo" : $("#itemNo_").val(),"updateId" : $("#id").val(),
								"effectiveTime" : $("#effectiveTime").val()};
		var checkUrl = BasePath + "/headquarter_cost_maintain/get_count.json";
		if (headquarterCostMaintain.checkExistFun(checkUrl, checkRuleNoData)) {
			showError('当天已经存在该商品价格,不能重复!');
			return false;
		}
		return true;
	},
	// 载入修改页面后的样式设置
	loadedUpdate: function(){
//		$("#itemName_").addClass("disabled").attr("readonly", true);
		$("#brandName").addClass("disabled").attr("readonly", true);
		$("#supplierName").addClass("disabled").attr("readonly", true);
		$("#addBasisName").addClass("disabled").attr("readonly", true);
		$("#addPrice").addClass("disabled").attr("readonly", true);
		$("#addDiscount").addClass("disabled").attr("readonly", true);
		$("#discountRate").addClass("disabled").attr("readonly", true);
	}
};

headquarterCostMaintain.initAddRuleNoCombox = function(params){
 	$.ajax({
		  type: 'POST',
		  url: BasePath + "/headquarter_cost_maintain/query_priceRules.json",
		  data: params,
		  cache: true,
		  async:false, // 一定要
		  dataType: 'json', 
		  success: function(result){
			  if(result){
				  headquarterCostMaintain.ruleChangeData = result.rules;
				  headquarterCostMaintain.initHQRuleNoCombobox();
			  }
		  }
   });
};

headquarterCostMaintain.batchGenerate = function(){
	$('#genarateDataForm').form('clear');
	$("#brandUnitNosCondition").val("");
	$('#firstNew1').combobox('setValue',0);
	$("#radio1").attr("checked","checked");
	ygDialog({
		title : '批量生成总部价',
		target : $('#genarateFormPanel'),
		width : 580,
		height : 400,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				var fromObj = $('#genarateDataForm');
				var validateForm = fromObj.form('validate');
				if (validateForm == false) {
					return;
				}
				headquarterCostMaintain.saveBatchGenerate();
				dialog.close();
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

/*function selectNoFirstNew(dialog1){
	$('#noFirstNewdataForm').form('clear');
	$('#dtlDataGrid').datagrid('loadData',{total:0,rows:[]}); 
	ygDialog({
		title : '总部价生成',
		target : $('#noFirstNewFormPanel'),
		width : 700,
		height : 'auto',
		buttons: [{
			id:'icon-save',
            text: '保存',
            handler: function(dialog) {
            	headquarterCostMaintain.save(dialog,dialog1);
            	
            }
        },
        {
            text: '取消',
            iconCls : 'icon-cancel',
            handler: function(dialog) {
                dialog.close();
            }
        }]
	});
}*/

/*headquarterCostMaintain.save = function(dialog,dialog1){
	var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
	if(checkedRows.length > 0){
		var fromObj = $('#noFirstNewFormPanel');
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		
		var date=$("#effectiveTime1").val();
		$.each(checkedRows,function(index,item){
			item.effectiveTime = date;
		});
		ajaxRequestAsync( BasePath + '/headquarter_cost_maintain/add',{insertedRows:JSON.stringify(checkedRows)},function(data){
			if(data){
				showSuc('总部价维护成功!');
				dialog.close();
				dialog1.close();
			}else{
				showError('总部价维护失败!');
			}
		});
	}else{
		showInfo("请选中需要维护总部价的记录?");
	}
}*/


/*headquarterCostMaintain.searchNoFirstNew = function() {
	var istrue=$('#searchForm').form('validate');
	if(istrue == false){
		return false;
	}
	var url = BasePath + '/headquarter_cost_maintain/do_noFirstNew';
	var params = $('#genarateDataForm').form('getData');
	params.needFilter = "yes";
	$('#dtlDataGrid').datagrid( 'options' ).queryParams= params;
	$('#dtlDataGrid').datagrid( 'options' ).url= url;
	$('#dtlDataGrid').datagrid( 'load' );
}*/

headquarterCostMaintain.saveBatchGenerate = function(dialog){
	var fromObj = $('#genarateDataForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理中...'
	}); 
	$('#genarateDataForm').form('submit',{
		url : BasePath + '/headquarter_cost_maintain/batch_generate_cost_new.json',
		success:function(result){
			var resultJson = JSON.parse(result);
			if(resultJson && resultJson.success){
				  showSuc('生成成功!');
				  $("#btn-search").click();
				  $("#genarateFormPanel").window('close');
				  $.messager.progress('close');
			  } else {
				  showError('操作失败,请联系管理员!');
				  $.messager.progress('close');
			  }
		},
		error:function(){
			$.messager.progress('close');
			showError('操作失败');
		}
	});
};

headquarterCostMaintain.doImport = function() {
	$.importExcel.open({
		'submitUrl' : BasePath + '/headquarter_cost_maintain/do_import',
		'templateName' : '总部成本价导入.xlsx',
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

function initCombobox(){
	var firstNewArray = 
		[
		 {'value' : '0' , 'text' : '是'},
		 {'value' : '1' , 'text' : '否'}
       ];
	
	$('#firstNew').combobox({
		data : firstNewArray,
		valueField : 'value',
		textField : 'text'
	});
	
	$('#firstNew1').combobox({
		data : firstNewArray,
		valueField : 'value',
		textField : 'text'
	});
	initRuleNoGenarate();
}

function initRuleNoGenarate(){
	$('#addRuleNoGenarate').width(140);
	$('#addRuleNoGenarate').combobox({
		url:BasePath + '/headquarter_price_rule/get_biz?isNotUsed=true',
		valueField:'addRuleNo',
		textField:'addRuleNo',
		onShowPanel:function(){
			 $('#addRuleNoGenarate').combobox('reload', BasePath + '/headquarter_price_rule/get_biz?isNotUsed=true');  
		},
		required:true
	});
}

headquarterCostMaintain.initQueryYearCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160
	});
};

headquarterCostMaintain.initQuerySeasonCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=SELL_SEASON&addAllFlag=true',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160
	});
};

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

$(document).ready(function(){
	headquarterCostMaintain.initCategory();
	headquarterCostMaintain.initQueryYearCombo('yearCodeCondition');
	headquarterCostMaintain.initQuerySeasonCombo('seasonCondition');
	initCombobox();
});
