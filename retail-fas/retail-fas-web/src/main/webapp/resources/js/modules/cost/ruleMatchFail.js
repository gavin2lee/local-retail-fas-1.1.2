
var ruleMatchFail = {};

ruleMatchFail.failReasonData = [
          {"value":"1","text":"供应商组匹配失败"},{"value":"2","text":"结算大类匹配失败"},
          {"value":"3","text":"新旧款、年份季节、品牌部或生效日期匹配失败"},{"value":"4","text":"获取厂进价失败"},
          {"value":"5","text":"获取总部价失败"},{"value":"6","text":"匹配到多个加价规则"}];

ruleMatchFail.statusData = [
                            {"value":"0","text":"失败"},
                            {"value":"1","text":"成功"}
                     ];
	
ruleMatchFail.matchTypeData = [
                           {"value":"0","text":"总部匹配"},
                           {"value":"1","text":"地区匹配"}
                    ];
	
//初始化地区
ruleMatchFail.initZoneNo = function(comboboxId) {
	fas_common.initCombox ({
		id : comboboxId,
		url : '/zone_info/getPriceZone',
		valueId : 'zoneNo',    
		textId : 'name',
		width : 130
	});
};

ruleMatchFail.initStatus = function(comboboxId) {
	$('#' + comboboxId).combobox({
		data:ruleMatchFail.statusData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 130,
		editable:false
	});
};

ruleMatchFail.initFailReason = function(comboboxId) {
	$('#' + comboboxId).combobox({
		data:ruleMatchFail.failReasonData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 130,
		editable:false
	});
};

ruleMatchFail.initMatchType = function(comboboxId) {
	$('#' + comboboxId).combobox({
		data:ruleMatchFail.matchTypeData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 130,
		editable:false
	});
};

ruleMatchFail.initStatusFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < ruleMatchFail.statusData.length; i++) {
		if (ruleMatchFail.statusData[i].value == value) {
			return ruleMatchFail.statusData[i].text;
		}
	}
};

ruleMatchFail.initFailReasonFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < ruleMatchFail.failReasonData.length; i++) {
		if (ruleMatchFail.failReasonData[i].value == value) {
			return ruleMatchFail.failReasonData[i].text;
		}
	}
};

ruleMatchFail.initMatchTypeFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < ruleMatchFail.matchTypeData.length; i++) {
		if (ruleMatchFail.matchTypeData[i].value == value) {
			return ruleMatchFail.matchTypeData[i].text;
		}
	}
};

ruleMatchFail.formatterZone = function(formatValue) {
	var combobox = $("#zoneNoCondition");
	var value = combobox.combobox("options").valueField;
	var text = combobox.combobox("options").textField;
	var datas = combobox.combobox("getData");
	for(var i = 0; i < datas.length; i++) {
		if(datas[i][value] == formatValue) {
			return datas[i][text];
		}
	}
	return "";
};

//额外操作
var extra_operate = {
	//新增保存时的校验规则
	checkSave : function(){
		return true;
	},
	// 进入新增页面初始化
	initAdd : function(){
	}
};

$(document).ready(function(){
	ruleMatchFail.initZoneNo("zoneNoCondition");
	ruleMatchFail.initStatus("statusCondition");
	ruleMatchFail.initFailReason("failReasonCondition");
	ruleMatchFail.initMatchType("matchTypeCondition");
	
});
