
var billInvCostAdjust = {};
var ctr = null;

$(function() {
	ctrl = new BillInvCostAdjustBLKController();
	ctrl.init(new BillInvCostAdjustBLKHeaderController(),new BillInvCostAdjustBLKDetailController(),new BillInvCostAdjustBLKBillListController());
	
	billInvCostAdjust.initCombox("year","month","status");
	
});

function BillInvCostAdjustBLKController() {
	// 删除单据(批量)
	this.batchDel = function() {
		var rowObj = $('#dataGridDiv').datagrid('getChecked');
		if (rowObj.length < 1) {
			showWarn("请选择一条数据操作！");
			return;
		}
		var flag = false;
		$.each(rowObj, function(index, item) {
			if (item.status != '0') {
				showWarn("其中有非制单状态的单据，请选择制单状态的单据进行操作！");
				flag = true;
				return false;
			}
		});
		if (flag) {
			return;
		}
		$.messager.confirm("确认", "确定要删除这" + rowObj.length + "条单据?", function(r) {
			if (r) {
				$.messager.progress({msg:'处理中',interval:1000});
				//如果需要自己组装删除数据，则可自定义
	            var deleteList = [];
	            $.each(rowObj, function(index, item) {
	                var obj = new Object();
	                obj.id = item.id;
	                obj.billNo = item.billNo;
	                deleteList.push(obj);
	            });
	            if(deleteList.length > 0) {
	                var params = new Object();
	                params["deleted"] = JSON.stringify(deleteList);
					$.ajax({
						url : BasePath + "/bill_inv_cost_adjust_blk/batch_delete",
						data : params,
						async : false
					}).then(function(data) {
						if (data && data.success) {
							showSuc('删除成功!');
							fas_common.search({
								formId : "searchForm",
								url : "/bill_inv_cost_adjust_blk/list.json",
								dataGridId : "dataGridDiv"
							});
						} else {
							showInfo('删除失败,请联系管理员!');
						}
					});
	            }
	            $.messager.progress('close');
			}
		});
		
	};

	this.batchConfirm = function(){
		var myUrl = "confirm";
		var rowObj = $('#dataGridDiv').datagrid('getChecked');
		if (rowObj && rowObj.length < 1) {
			showWarn("请至少选择一条数据操作！");
			return;
		}
		var flag = false;
		var isNotMakeBillNos = "";
		var hasNoBillNos = "";
		var hasDetails = "";
		for(var i=0;i<rowObj.length;i++){
			var item=rowObj[i];
			var pkValue = item.billNo;
			if (!pkValue) {
				hasNoBillNos = "请选择相应的单据再确认！";
				flag = true;
				continue;
			}
		
			var billStatusTemp=billInvCostAdjust.getBillStatus(pkValue,item.shardingFlag);
			if(billStatusTemp!=0){//单据不等于制单状态
				isNotMakeBillNos = isNotMakeBillNos + item.billNo + "，";
				flag = true;
			}
			if (isNotBlank(hasNoBillNos)) {
				showWarn(hasNoBillNos);
			}
			if (!billInvCostAdjust.checkHasDetails(pkValue, ctrl.billDetail.dtlModulePath,item.shardingFlag)) {
				flag = true;
				hasDetails = hasDetails + item.billNo + "，";
			}
		}
		
		if (flag) {
			if (isNotBlank(isNotMakeBillNos)) {
				showWarn("其中" + isNotMakeBillNos + "为非制单状态的单据，请选择制单状态的单据进行操作！");
			}
			if (isNotBlank(hasDetails)) {
				showWarn("其中" + isNotMakeBillNos + "单据没有添加明细，请添加明细款号！");
			}
			return;
		}
		
		$.messager.confirm("确认", "确定要确认这" + rowObj.length + "条单据?", function(r) {
			if (r) {
				var continueFlag=true;//是否继续循环
				var rfeshFlag=false;//是否需要刷新页面
				$(rowObj).each(function(index, item) {
					var url = myUrl + "?prettyConfirm=Y";
					var params = {
						billNo : item.billNo,
						shardingFlag:item.shardingFlag
					};
					$.ajax({
						url : url,
						data : params,
						async : false
					}).then(function(data) {
						if (data.status == 1) {
							rfeshFlag = true;
						} else {
							showInfo('确认失败,请联系管理员!');
						}
					});
					return continueFlag;
				});
				
				if(rfeshFlag){
					fas_common.search({
						formId : "searchForm",
						url : "/bill_inv_cost_adjust_blk/list.json",
						dataGridId : "dataGridDiv"
					});
				}
			}
		});
	};
};

//初始化
BillInvCostAdjustBLKController.prototype.init = function(header, detail,list){
	this._tab = $('#mainTab');
	var self = this;
	this.billHeader = header;
	this.billDetail = detail;
	this.billList = list;
	this.billHeader.ctrl = this;
	this.billDetail.ctrl = this;
	this.billList.ctrl = this;
	this.tabLoad = false;
	
	this._tab.tabs('add', {
		title : '明细单据',
		selected : false,
		closable : false,
		href : 'list_dtl',
		onLoad : function() {
			self.billList.init();
			self.billHeader.init();
			self.billDetail.init();
			
			if(self.billHeader.bill){
				self.setBill(self.billHeader.bill);
			}
		}
	});
	
	this._tab.bind('rowChange', function() {
		if(self.billHeader.bill){
			self.setBill(self.billHeader.bill);
		}	
	});
	
	
	this._tab.tabs('hideHeader');
};

BillInvCostAdjustBLKController.prototype.returnTabs = function(type) {
	returnTab('mainTab', type);
	if (type == 0) // 浏览tab
		return;
	if (!isNotBlank(this.bill)) {
		this.add();
	}
	setTimeout(function() {
		$("#dtlDataForm").navigation('focus');
	}, 100);
};

//切换tab
BillInvCostAdjustBLKController.prototype.returnTab = function(type) {
	this.returnTabs(type);
};

//新增单据
BillInvCostAdjustBLKController.prototype.add = function() {
	var self=this;
	if(formDirtyNoAlert()){
		$.messager.confirm("确认", "当前表单未保存，是否直接新增？", function(r) {
			if (r) {
				add(self);
			}
		});
	}else{
		add(self);
	}
	
	function add(self){
		$("#dtlDataForm").form('clearQueryParams');
		self.setBill({status : 0});
		$("#dtlDataForm").form('setDefaultValue');
		setTimeout(function() {
			$("#dtlDataForm").navigation('focus');
		}, 100);
	}
};

BillInvCostAdjustBLKController.prototype.save = function(){
	var self = this;
	$("#billNo").focus();//获取单据头的焦点
	if (!self.validate()) {
		return false;
	}
	if(!self.billDetail.validate1()){
		return false;
	}
	var statusFlag = "addSave";
	self.checkStatus(statusFlag).then(function(data){
		var def = self.billHeader.save().then(function(data) {
			self.setBill(data, 1);
			return data;
		}).fail(function(data) {
			return false;
		});
		def.then(function(data) {
			return self.billDetail.save(data);
		}).fail(function(data) {
			return false;
		});
	});
	
};

BillInvCostAdjustBLKController.prototype.checkStatus = function(flag) {
	var self = this;
	var def = $.Deferred();
	var fo = false;
	if (self.bill.billNo){
		fo = true;
	}else{
		def.resolve();
	}
	if(fo){
		if (!self.bill) {
			return;
		}
		$.get('get', {
			billNo : self.bill.billNo,
			shardingFlag : self.bill.shardingFlag
		}).then(function(data) {
			if (!isNotBlank(flag) || flag == 'addSave') {
				flag = "put";// 默认要校验建单状态
			}
			
			switch (flag) {
			case "put":// 判断是否是制单状态
				if (data.status != 0) {
					def.reject({
						status : data.status
					});
					showInfo("该单据不是制单状态,请重新选择!");
					
				}
				break;
			}
			setTimeout(function() {
				def.resolve(data);
			}, 5);
			
		});
	}
	return def.promise();
};

//确认单据
BillInvCostAdjustBLKController.prototype.confirm = function() {
	var statusFlag = 'addSave';
	var myUrl = "confirm";
	var self = this;

	$.messager.confirm("确认", "确定要确认该条单据?", function(r) {
		if (r) {
//			if (!self.billDetail.endEdit()) {// 先校验并结束编辑
//				return;
//			}
			if (formDirtyNoAlert()) {
				showWarn("表单已被修改，请先保存！");
			}else{
				if (!self.validate()) {
					return;
				}
				self.checkStatus(statusFlag).then(function(data1) {
					if (billInvCostAdjust.checkHasDetails(self.bill.billNo, self.billDetail.dtlModulePath,self.bill.shardingFlag)) {
						self.billConfirm(myUrl);
					}
				});
			}
		}

	});
};

BillInvCostAdjustBLKController.prototype.billConfirm = function(myUrl){
	var self=this;
	var url = myUrl;
	var params = {
		billNo : ctrl.bill.billNo,
		shardingFlag:ctrl.bill.shardingFlag
	};
	
	$.post(url, params).then(function(data) {
		if (data) {
			if (isNotBlank(data.errorMessage)) {
				showError(data.errorMessage + " " + data.errorDefined);
			} else {
				showSuc('确认成功!');
				self.setBill(data, 1);
			}
		} else {
			showInfo('确认失败,请联系管理员!');
		}
		
	});
};

//新增明细
BillInvCostAdjustBLKController.prototype.addDetail = function(){
	var self = this;
	if (self.bill.billNo) {
		self.billDetail.add();// 后新增一行明细
	} else{
		showWarn('请先保存单据头!');
	}
};

//删除明细
BillInvCostAdjustBLKController.prototype.deleteDtl = function() {
	var statusFlag = 'addSave';
	var self = this;
	this.checkStatus(statusFlag).then(function(data) {
		self.billDetail.del(statusFlag);
	});
};

//导入
BillInvCostAdjustBLKController.prototype.importDetail = function(data) {
	this.billDetail.importDetail(data);
};

//导出明细
BillInvCostAdjustBLKController.prototype.exportExcel = function() {
	this.billDetail.exportExcel();
};

/**
 * 刷新类型 1：刷新头挡和工具栏 2：刷新明细
 * @param val
 * @param refreshType
 */

BillInvCostAdjustBLKController.prototype.setBill = function(val, refreshType){
	this.bill = val;
	this.billDetail.bill = val;
	this.billHeader.bill = val;
	if(refreshType!=null&&refreshType==0){
		return;
	}
	this.refresh();
};


BillInvCostAdjustBLKController.prototype.refresh = function(){
	this.billHeader.refresh();
	this.billDetail.refresh();
	
	setTimeout(function() {
		changeFormDefaultValue();
	}, 2000);// 设置表单默认值 好监听表单是否被修改过
};

//单据校验
BillInvCostAdjustBLKController.prototype.validate = function() {
	var result = false;
	if ($("#dtlDataForm").form('validate') == true) {
		if (this.billDetail.validate()) {
			result = true;
		} else {
			result = false;
		}
	} else {
		result = false;
	}
	return result;
};


//=============================

//改变调整成本文本框时触发的事件
billInvCostAdjust.invCostAdjustChange = function(adjustCost) {
	$("#dtlDataGrid").datagrid('selectRow',0);//选中第一行
    var closingQty = billInvCostAdjust.getEditorVal("dtlDataGrid", "closingQty");
    var closingAmount = billInvCostAdjust.getEditorVal("dtlDataGrid", "closingAmount");
    
    billInvCostAdjust.setEditorVal("dtlDataGrid", "diverAmount", 
    		parseFloat((closingQty * adjustCost) - closingAmount).toFixed(2));
    $("#dtlDataGrid").datagrid('unselectRow',0);//取消选中第一行
};

//获取editor的值
billInvCostAdjust.getEditorVal = function(dataGrid, field) {
    var row = $("#" + dataGrid).datagrid('getSelected');//读取第一个选定的行
	var editIndex = $("#" + dataGrid).datagrid('getRowIndex', row); 
	var editor = $("#dtlDataGrid").datagrid('getEditor', {
        'index': editIndex,
        'field': field
    });
	var editorVal = "";
    var target = editor.target;
    var ed = $.fn.datagrid.defaults.editors[editor.type];
    if (ed) {
    	editorVal = ed.getValue(target, field);
    }
    return editorVal;
};

//设置editor的值
billInvCostAdjust.setEditorVal = function(dataGrid, field, value) {
	var row = $("#" + dataGrid).datagrid('getSelected');  
	var editIndex = $("#" + dataGrid).datagrid('getRowIndex', row); 
	var editor = $("#dtlDataGrid").datagrid('getEditor', {
	     'index': editIndex,
	     'field': field
    });
    var target = editor.target;
    var ed = $.fn.datagrid.defaults.editors[editor.type];
    if (ed) {
    	ed.setValue(target, value);
    }
};

billInvCostAdjust.getBillStatus = function(billNo,shardingFlag) {
	var status='';
	ajaxRequestWithAsync('get', {billNo : billNo,shardingFlag : shardingFlag}, false, function(data){
		if (isNotBlank(data.errorMessage)) {
			showError(data.errorMessage + " " + data.errorDefined);
			status='-1';
		} else {
			status=data.status;
		}
	});
	return status;
};

//月份数据
billInvCostAdjust.monthData = [
	{"lable":"1","value":"1","text":"1"},{"lable":"2","value":"2","text":"2"},
	{"lable":"3","value":"3","text":"3"},{"lable":"4","value":"4","text":"4"},
	{"lable":"5","value":"5","text":"5"},{"lable":"6","value":"6","text":"6"},
	{"lable":"7","value":"7","text":"7"},{"lable":"8","value":"8","text":"8"},
	{"lable":"9","value":"9","text":"9"},{"lable":"10","value":"10","text":"10"},
	{"lable":"11","value":"11","text":"11"},{"lable":"12","value":"12","text":"12"}
];

//年份数据
billInvCostAdjust.yearData = function() {
	var yearDatas = [];
	var year = new Date().getFullYear();
	for(var i = 5; i >= 1; i--) {
		yearDatas.push({lable:(year-i),value : (year-i), text : (year-i)});
	}
	for(var j = 0; j <= 5; j++) {
		yearDatas.push({lable:(year+j),value : (year+j), text : (year+j)});
	}
	return yearDatas;
};

//初始化下拉框
billInvCostAdjust.initCombox = function(year,month,status){

	$("#" + year).combobox({
		data :billInvCostAdjust.yearData(),
		valueField : "lable",
		textField : "text",
		panelHeight : "auto",
		width : 130
	});
	
	$("#" + month).combobox({
		data : billInvCostAdjust.monthData,
		valueField : "lable",
		textField : "text",
		panelHeight : "auto",
		width : 130
	});
	$('#'+status).combobox({
		valueField : "label",
		textField : "text",
		editable : false,
		data : [
        	{ 
	      		"label" : "0", "text" : "制单", "value" : "0"
	      	}, 
	      	{
				"label" : "1", "text" : "确认", "value" : "1"
			}
		]
	});
};

/*
 * 检查单据明细是否有数据 @params pkValue 单据编码 @params dtlModulePath 明细Controller对应的Url
 */
billInvCostAdjust.checkHasDetails = function (pkValue, dtlModulePath,shardingFlag) {
    var result = billInvCostAdjust.getDetailDatas(pkValue, dtlModulePath,shardingFlag);
    if (!result) {
        showWarn("单据" + pkValue + "没有添加明细，不能操作！");
    }
    return result;
};

//检查单据是否有明细
billInvCostAdjust.getDetailDatas = function (pkValue, dtlModulePath,shardingFlag) {
    if (!pkValue) {
        return false;
    }
    var url = dtlModulePath + "get_count.json";
    var params = new Object();
    params['billNo'] = pkValue;
    params['shardingFlag'] = shardingFlag;
    var status = true;
    ajaxRequestAsync(url, params, function (totalData) {
        total = parseInt(totalData, 10);
        if (total <= 0) {
            status = false;
        }
    });
    return status;
};

//=====================公用函数

//重载ajaxRequest 加入同步异步参数
function ajaxRequestWithAsync(url, reqParam, async, callback) {
	$.ajax({
		async : async,
		type : 'POST',
		url : url,
		data : reqParam,
		cache : async,
		success : callback
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
}

//设置表单控件的defaultValue值
function changeFormDefaultValue(form) {
	if(!form){
		form=document.forms["mainDataForm"];//这里默认是单据明细的表单 暂时写死
	}
	if(form){
		for (var i = 0; i < form.elements.length; i++) {
	        var element = form.elements[i];
	        var type = element.type;
	        if (type == "checkbox" || type == "radio") {
	        	element.defaultChecked=element.checked;
	        }else if (type == "hidden" || type == "password" || type == "text" || type == "textarea") {
	        	element.defaultValue=element.value;
	        }else if (type == "select-one" || type == "select-multiple") {
	            for (var j = 0; j < element.options.length; j++) {
	            	element.options[j].defaultSelected=element.options[j].selected;
	            }
	        }
	    }
	}
}

function formDirtyNoAlert(datagridId, fromId){
	if (!fromId) {
        fromId = "dtlDataForm";
    }
    if (!datagridId) {
        datagridId = "dtlDataGrid";
    }
    var grid=$("#" + datagridId);
	if(grid){
		try{
			var datas = grid.datagrid('getChanges');
            if (datas && datas.length > 0) {
                return true;
            }
		}catch(e){}
	}
    return false;
};

//判断页面表单是否被修改 给提示 校验通过返回false 不通过返回true datagridId:要检测的datagrid fromId:要检测的form
function formDirty(datagridId, fromId) {
	if($.browser.mozilla){
		return false;
	}
    if (!fromId) {
        fromId = "dtlDataForm";
    }
    if (!datagridId) {
        datagridId = "dtlDataGrid";
    }
    var datas = $("#" + datagridId).datagrid('getChanges');
    if (datas && datas.length > 0) {
        showWarn("页面数据已修改，请保存！");
        return true;
    }
    return false;
};


