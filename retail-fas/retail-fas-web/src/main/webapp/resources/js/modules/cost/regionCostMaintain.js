
var regionCostMaintain = {};

regionCostMaintain.initAddRuleData = [
                                        {"value":"1","text":"厂进价"},
                                        {"value":"3","text":"总部成本"}
                                 ];

regionCostMaintain.generatePriceByRule = function(){
	$('#genarateRuleDataForm').form('clear');
	$('#multiPriceZoneNo').val('');
	ygDialog({
		title : '选择规则生成地区价',
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
					url : BasePath + '/region_cost_maintain/generate_cost_by_rule',
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


regionCostMaintain.initCategory = function(){
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


regionCostMaintain.addBasisFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < regionCostMaintain.initAddRuleData.length; i++) {
		if (regionCostMaintain.initAddRuleData[i].value == value) {
			return regionCostMaintain.initAddRuleData[i].text;
		}
	}
};

regionCostMaintain.ruleChangeData = {};

regionCostMaintain.changeItemEvent = function(){
	$("#addRuleNo").combobox('clear');
	$("#regionCost").val("");
	$("#effectiveTime").val("");
	$("#addBasis").val("");
	$("#addBasisName").val("");
	$("#addPrice").val("");
	$("#addDiscount").val("");
	$("#discountRate").val("");
	
	var itemNo = $("#itemNo_").val();
	var zoneNo = $("#zoneNo").combobox("getValue");
	if(!itemNo || !zoneNo) {
		$("#itemName_").val('');
		showWarn("请选择地区和商品信息");
		return;
	}
	var reqParam = {"itemNo" : itemNo, "zoneNo" : zoneNo};
	var url = BasePath + '/region_cost_maintain/generate_cost.json';
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
			  $("#headquarterCost").val(data.headquarterCost);
			  regionCostMaintain.ruleChangeData = data.matchedRegionPriceRule;
			  regionCostMaintain.initRegionRuleNoCombobox();
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
			  $("#regionCost").val(data.regionCost);
			  $("#effectiveTime").datebox('setValue',formatDatebox(data.effectiveTime));
		  }
	});
};

regionCostMaintain.initRegionRuleNoCombobox = function(){
	 $("#addRuleNo").combobox({
			data:regionCostMaintain.ruleChangeData,
			valueField:"addRuleNo",
			textField:"addRuleNo",
			panelHeight:"auto",
			width : 160,
			editable:false,
			onSelect : function(param) {
				var choosedData = {"itemNo" : $("#itemNo_").val(),"supplierNo" : $("#supplierNo").val(),"zoneNo" : $("#zoneNo").combobox("getValue"),
						"suggestTagPrice" : $("#suggestTagPrice").val(),"addRuleNo" : param.addRuleNo};
				$.ajax({
					  type: 'POST',
					  url: BasePath + '/region_cost_maintain/rule_choosed.json',
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
							  $("#regionCost").val("");
							  $("#effectiveTime").datebox('setValue',"");
							  $("#tagPrice").val("");
							  $("#factoryPrice").val("");
							  $("#headquarterCost").val("");
							  showWarn(record.failMessage);
							  return false;
						  }
						  $("#tagPrice").val(record.tagPrice);
						  $("#factoryPrice").val(record.factoryPrice);
						  $("#headquarterCost").val(record.headquarterCost);
						  $("#addBasis").val(record.addBasis);
						  $("#addBasisName").val(record.addBasisName);
						  $("#addPrice").val(record.addPrice);
						  $("#addDiscount").val(record.addDiscount);
						  $("#discountRate").val(record.discountRate);
						  $("#regionCost").val(record.regionCost);
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
	initRuleNoGenarate();
}

function initRuleNoGenarate(){
	$('#addRuleNoGenarate').width(140);
	$('#addRuleNoGenarate').combobox({
		url:BasePath + '/region_price_rule/get_biz?isNotUsed=true',
		valueField:'addRuleNo',
		textField:'addRuleNo',
		onShowPanel:function(){
			 $('#addRuleNoGenarate').combobox('reload', BasePath + '/region_price_rule/get_biz?isNotUsed=true');  
		},
		required:true
	});
}

//初始化地区
regionCostMaintain.initQueryZoneNo = function(comboboxId) {
	$("#" + comboboxId).combobox({
		url : BasePath + '/zone_info/getPriceZone',
		valueField:"zoneNo",
		textField:"name",
		panelHeight:"auto",
		width : 160,
		required:true,
		editable:false
	});
};

regionCostMaintain.initZoneNo = function(comboboxId) {
	$("#" + comboboxId).combobox({
		url : BasePath + '/zone_info/getPriceZone',
		valueField:"zoneNo",
		textField:"name",
		panelHeight:"auto",
		width : 160,
		editable:false,
		onSelect : function(data) {
			if(data) {
				$("#zoneName").val(data.name);
			} else {
				$("#zoneName").val("");
			}
		}
	});
};

regionCostMaintain.checkExistFun = function(url,checkColumnJsonData){
	var checkExist=false;
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkColumnJsonData,
		  cache: true,
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
	// 进入新增页面初始化
	initAdd : function(){
		regionCostMaintain.initZoneNo("zoneNo");
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
		regionCostMaintain.initZoneNo("zoneNo");
		var reqParam = {"itemNo" : rowData.itemNo, "zoneNo" : rowData.zoneNo, "addRuleNo" : rowData.addRuleNo};
		regionCostMaintain.initAddRuleNoCombox(reqParam);
		
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
	},
	// 修改页面保存校验
	checkUpdate: function(){
		//校验是否重复
		var checkRuleNoData = {"itemNo" : $("#itemNo_").val(),"updateId" : $("#id").val(),
								"zoneNo" : $("#zoneNo").combobox('getValue'),
								"effectiveTime" : $("#effectiveTime").val()};
		var checkUrl = BasePath + "/region_cost_maintain/get_count.json";
		if (regionCostMaintain.checkExistFun(checkUrl, checkRuleNoData)) {
			showError('当天已经存在该商品价格,不能重复!');
			return false;
		}
		return true;
	},
};

regionCostMaintain.doImport = function() {
	$.importExcel.open({
		'submitUrl' : BasePath + '/region_cost_maintain/do_import',
		'templateName' : '地区成本价导入.xlsx',
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

regionCostMaintain.initAddRuleNoCombox = function(params){
 	$.ajax({
		  type: 'POST',
		  url: BasePath + "/region_cost_maintain/query_priceRules.json",
		  data: params,
		  cache: true,
		  async:false, // 一定要
		  dataType: 'json', 
		  success: function(result){
			  if(result){
				  regionCostMaintain.ruleChangeData = result.rules;
				  regionCostMaintain.initRegionRuleNoCombobox();
			  }
		  }
     });
};


regionCostMaintain.initMultiZoneNo = function(comboboxId){
	$("#" + comboboxId).combobox({
		url : BasePath + '/zone_info/getPriceZone',
		valueField:"zoneNo",
		textField:"name",
		panelHeight:"auto",
		width : 160,
		editable:false,
		multiple : true
	});
};

regionCostMaintain.batchGeneratePrice = function(){
	$('#genarateDataForm').form('clear');
	regionCostMaintain.initMultiZoneNo("zoneNos");
	$("#radio0").attr("checked","checked");
	ygDialog({
		title : '批量生成地区价',
		target : $('#genarateFormPanel'),
		width : 330,
		height : 350,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				regionCostMaintain.batchGenerate(dialog);
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

regionCostMaintain.batchGenerate = function(dialog){
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
		url : BasePath + '/region_cost_maintain/batch_generate_cost_new.json',
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
regionCostMaintain.initQueryYearCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160
	});
};

regionCostMaintain.initQuerySeasonCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=SELL_SEASON&addAllFlag=true',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160
	});
};


$(document).ready(function(){
	//初始化大区
	regionCostMaintain.initQueryZoneNo("zoneNoCondition");
	regionCostMaintain.initCategory();
	regionCostMaintain.initQueryYearCombo('yearCodeCondition');
	regionCostMaintain.initQuerySeasonCombo('seasonCondition');
	initCombobox();

});
