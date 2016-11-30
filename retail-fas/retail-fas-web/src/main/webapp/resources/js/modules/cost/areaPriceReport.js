
var regionCostMaintain = {};

regionCostMaintain.initAddRuleData = [
                                        {"value":"2","text":"牌价"},
                                        {"value":"3","text":"总部成本"}
                                 ];

regionCostMaintain.addBasisFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < regionCostMaintain.initAddRuleData.length; i++) {
		if (regionCostMaintain.initAddRuleData[i].value == value) {
			return regionCostMaintain.initAddRuleData[i].text;
		}
	}
};

regionCostMaintain.ruleChangeData = {};


regionCostMaintain.initRegionRuleNoCombobox = function(){
	 $("#addRuleNo").combobox({
			data:regionCostMaintain.ruleChangeData,
			valueField:"addRuleNo",
			textField:"addRuleNo",
			panelHeight:"auto",
			width : 160,
			editable:false,
			onSelect : function(param) {
				var choosedData = {"itemNo" : $("#itemNo_").val(),"zoneNo" : $("#zoneNo").combobox("getValue"),
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
							  showWarn(record.failMessage);
							  return false;
						  }
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
	
	ygDialog({
		title : '批量生成地区价',
		target : $('#genarateFormPanel'),
		width : 480,
		height : 300,
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

$(document).ready(function(){
	//初始化大区
	regionCostMaintain.initQueryZoneNo("zoneNoCondition");

});
