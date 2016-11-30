var returnProfitDtl = new Object();

datasource ={};
datasource.orderTypes = [{"value":"1","text":"现货"},{"value":"2","text":"期货"}];

datasource.returnProfitTypes = [{"value":"0","text":"结算金额"},{"value":"1","text":"厂商金额"},{"value":"2","text":"牌价额"}];

datasource.monthData = [{"value":"1","text":"1"},{"value":"2","text":"2"},
	                  {"value":"3","text":"3"},{"value":"4","text":"4"},
	                  {"value":"5","text":"5"},{"value":"6","text":"6"},
	                  {"value":"7","text":"7"},{"value":"8","text":"8"},
	                  {"value":"9","text":"9"},{"value":"10","text":"10"},
	                  {"value":"11","text":"11"},{"value":"12","text":"12"}];

function ReturnProfitDialog(){
	var $this = this;
	this.generateReturnProfit = function(){
		var checkedRows = $('#dataGridDiv').datagrid('getChecked');
		if (checkedRows.length == 0) {
			showWarn('请选择要生成返利的明细..');
			return;
		}
		
		$('#genarateDataForm').form('clear');
		$("input[type='hidden']", '#genarateFormPanel').val('');
		ygDialog({
			title : '生成返利',
			target : $('#genarateFormPanel'),
			width : 510,
			height : 200,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					$this.save(dialog,checkedRows);
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
	
	this.save = function(dialog,checkedRows){
		if($("#genarateDataForm").form('validate') == false){
			return;
		}
		var url = BasePath + "/return_profit/gennerateReturnProfit";
		var p = $('#genarateDataForm').form('getData');
		var params = {
				checkedRows:JSON.stringify(checkedRows),
				rate : p.returnProfitRate,
				type : p.returnProfitType
		};
		returnProfitDtl.commonAjaxRequest(url, params,function(result){
			if(result && result.success){
				showSuc('返利生成成功!');
				dialog.close();
				return;
			} else if(result){
				showError(result.message);
			}
		});
	};
};


returnProfitDtl.init = function(){
//	returnProfitDtl.commonAjaxRequest(BasePath + "/common_util/getLookup?lookupId=29", null, function(result){
//		$.each(result, function(index, obj) {
//			var orderType = {"label":obj.code,"text":obj.name,"value":obj.orderNo};
//			datasource.orderTypes.push(orderType);
//		});
//	});
//	$("#orderType").combobox("loadData", datasource.orderTypes);//初始化订货类型
};

//ajax请求
returnProfitDtl.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async : false,
		dataType : 'json',
		success : callback
	});
};

var dialog = null;
$(function() {
	$.fas.extend(ReturnProfitDialog, FasDialogController);
	dialog = new ReturnProfitDialog();
	
	returnProfitDtl.init();
});