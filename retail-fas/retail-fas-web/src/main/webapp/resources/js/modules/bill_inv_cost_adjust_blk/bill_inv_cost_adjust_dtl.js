
//=========================BillList
function BillInvCostAdjustBLKBillListController(){
	this._tab = $('#mainTab');
	this.selectedBill = null;
	this.gridId = 'dtlDataForm';
	this.statusData = [{"label" : "0","text" : "制单", "value":"0"}, {"label" : "1","text" : "确认","value":"1"}];;//制单 确认
}

BillInvCostAdjustBLKBillListController.prototype.init = function(){
	var self = this;

};

BillInvCostAdjustBLKBillListController.prototype.onMainDblClickRow = function(index,row){
	this.ctrl.billHeader.bill = row;
	this._tab.tabs('select', '明细单据');
	this.ctrl._tab.trigger("rowChange");
};


//===============================Header
function BillInvCostAdjustBLKHeaderController(){
	this.valideType = false;
	this.dtlDataForm = null;
}

BillInvCostAdjustBLKHeaderController.prototype.init = function(){
	billInvCostAdjust.initCombox("yearCondition","monthCondition","statusCondition");
	$('#statusCondition').combobox('select','0');
	this.dtlDataForm = $('#dtlDataForm');
};

BillInvCostAdjustBLKHeaderController.prototype.refresh = function(){
	var self = this;
	var bill = self.bill;
	$('#dtlDataForm').form('clear');
	// 判断如果billNo不为空 status为空 那么加载出单据头挡
	if (isNotBlank(bill.billNo) && !isNotBlank(bill.status)) {
		
		$.post("get", {
			billNo : self.bill.billNo,
			shardingFlag : self.bill.shardingFlag
		}, function(data) {
			return data;
		}).then(function(data) {
			ctrl.bill = data;
			ctrl.billDetail.bill = data;
			ctrl.billHeader.bill = data;
			$('#dtlDataForm').form('load', data);
		});
	} else if (isNotBlank(bill.billNo)) {
		$('#dtlDataForm').form('load', bill);
	}
};

//保存头当信息
BillInvCostAdjustBLKHeaderController.prototype.save = function(bill) {
	var self = this;
	var saveUrl="post2";
	return $('#dtlDataForm').form('post', {
		url : saveUrl,
		onSubmit : function(data) {
			data.id = self.bill.id;
			data = $.extend(bill, data);
			if(self.bill&&self.bill.shardingFlag){
				data.shardingFlag=self.bill.shardingFlag;
			}
			if(!data.status){
				data.status = "0";
			}
			return data;
		}
	}).done(function(data) {
		if (data) {
			return data;
		}
	}).fail(function(){
		
	});
};

//================================Detail

function BillInvCostAdjustBLKDetailController(){
	this.dtlModulePath = '../bill_inv_cost_adjust_dtl_blk/'; 
	
	this.validate1 = function() {
		var flag = true;
		$.ajax({
			url: getBasePath("/bill_inv_cost_adjust_blk/get_controller_flag"),
			async:false,
			success: function(result){
				if(result && result.controllerFlag == 'false') {
					return true;
				} else {
					var rows = $('#dtlDataGrid').datagrid('getRows');
					if(rows && rows.length > 0) {
						for(var i = 0; i < rows.length; i++) {
							var colsingQty = rows[i].closingQty;
							if(colsingQty != 0) {
								showWarn("上期已发生业务，不允许保存，请联系管理员！");
								flag = false;
								return false;
							}
						}
					}
					return true;
				}
			},
			error:function(result){
				showInfo("系统异常，请联系管理员！");
				return false;
			}
		});
    	return flag;
    };

};

BillInvCostAdjustBLKDetailController.prototype.init = function(){
	this.grid = $('#dtlDataGrid');
	this.editRowIndex = -1;
};

BillInvCostAdjustBLKDetailController.prototype.refresh = function(){
	var self = this;
	var url = self.dtlModulePath+"list.json";
	url += '?billNo=' + self.bill.billNo;
	if (self.bill.shardingFlag) {
		url += '&shardingFlag=' + self.bill.shardingFlag;
	}
	$("#dtlDataGrid").datagrid({url : url,showFooter:true});
	if (!this.bill.billNo) {
		this.clear();
	}
};

BillInvCostAdjustBLKDetailController.prototype.add = function(row) {
	row = row || {};
	var self = this;
	var rows = self.grid.datagrid('getRows');
	if(rows.length>0){
		if (!endEditing("dtlDataGrid")) {
			return;
		}
	}
	
	if (endEditing("dtlDataGrid")) {
		$.data($("#dtlDataGrid")[0], 'editIndex', rows.length);// 设置当前编辑行的行数
		$('#dtlDataGrid').datagrid('insertRow', {
			index : 0,
			row : {}
		});
		$('#dtlDataGrid').datagrid('beginEdit', 0);
		self.editRowIndex = 0;
	}
};

//明细删除
BillInvCostAdjustBLKDetailController.prototype.del = function() {
	var self = this;
	var grid = self.grid;
	var rows = grid.datagrid('getSelections');
	var editIndex = grid.datagrid('editIndex');
	if (rows && rows.length > 0) {
		$.messager.confirm("确认", "确定要删除选中的明细么？", function(r) {
			if (r) {
				$.each(rows, function(i, row) {
					var rowIndex = grid.datagrid('getRowIndex', row);
					grid.datagrid('deleteRow', rowIndex);
					if (editIndex == rowIndex)
						grid.datagrid('editIndex', -1);
				});
			}
		});
	} else {
		showWarn("请选择一条明细删除！");
	}
};

BillInvCostAdjustBLKDetailController.prototype.save = function(data){
	var self = this;
	var shardingFlag=self.bill.shardingFlag;
	if(data){
		shardingFlag=data.shardingFlag;
	}
	
	var url = self.dtlModulePath;
	// 判断有没有自定义保存明细提交方法
	if (self.saveMethod) {
		url += self.saveMethod;
	} else {
		url += "save";
	}
	var effectRow = getChangeTableDataCommon("dtlDataGrid");
	var billNoTemp = self.bill.billNo;
	var companyNo = self.bill.companyNo;
	var companyName = self.bill.companyName;
	var year = self.bill.year;
	var month = self.bill.month;
	var fo = true;
	$.each(effectRow, function(i, itemStr) {
		// 将Sting强制转换成json格式的数据
		var itemObj = $.parseJSON(itemStr);
		$.each(itemObj, function(j, item) {
			if (item.billNo && item.billNo != billNoTemp) {
				fo = false;
			}
		});
	});
	if (fo) {
		effectRow["billNo"] = billNoTemp;
		effectRow["shardingFlag"] = shardingFlag;
		effectRow["companyNo"] = companyNo;
		effectRow["companyName"] = companyName;
		effectRow["year"] = year;
		effectRow["month"] = month;
		if (isNotBlank(self.theType)) {
			param['theType'] = self.theType;
		}
		
		ajaxRequestWithAsync(url, effectRow, false, function(result) {
			
			if (result) {
				if (isNotBlank(result.errorMessage)) {
					showError('单据明细保存失败!' + result.errorMessage + " " + result.errorDefined);
				} else {
					showSuc('单据保存成功!');
				}
//				self.refresh();
				return true;
			} else {
				showError('单据明细保存失败,请联系管理员!');
				return false;
			}

		});
	}
};

BillInvCostAdjustBLKDetailController.prototype.importDetail = function(data) {
	var self = this;
	if (self.bill == undefined || !isNotBlank(self.bill.billNo)) {
		showWarn("请选择新单！或者先保存头挡！");
		return;
	}
	if (!self.endEdit()) {
		return;
	}
	// 判断页面表单是否被修改 给提示
	if (formDirty()) {
		return;
	}
	
	ctrl.checkStatus('addSave').then(function(data1) {
		var pkValue = self.bill.billNo;
		var companyNo = $('#companyNo1').val();
		var year = $('#yearCondition').val();
		var month = $('#monthCondition').val();
		$.importExcel.open({
			'submitUrl' : "/fas/bill_inv_cost_adjust_dtl_blk/import?billNo=" + pkValue + "&companyNo=" + companyNo + "&year=" + year + "&month=" + month,
			'templateName' : '巴洛克库存成本调整单.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (isNotBlank(data.error)) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showSuc('数据导入成功');
						ctrl.billDetail.refresh();
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
	});
};

BillInvCostAdjustBLKDetailController.prototype.exportExcel = function() {
	var self = this;
	var params = self.grid.datagrid('options').queryParams;
	var grepColumns = self.grid.datagrid('options').columns;
	var columns = [];
	
	if(grepColumns && grepColumns.length > 1) {
		columns = $.grep(grepColumns[1], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
		firstHeaderColumns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	} else {
		columns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}
	
	var exportColumns = JSON.stringify(columns);
	var url = BasePath + "/bill_inv_cost_adjust_dtl_blk/do_fas_export";
	var dataRow = self.grid.datagrid('getRows');

	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.billNo = self.bill.billNo;
				param.exportColumns = exportColumns;
				param.fileName = "巴洛克库存成本调整单导出";
				param.exportType = 'common' || '';
				if(params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
					});
				}
			},
			success : function() {

			}
		});
	} else {
		showWarn('库存成本调整明细记录为空，不能导出!');
	}
};

//明细清除
BillInvCostAdjustBLKDetailController.prototype.clear = function() {
	var $dg = this.grid;
	$dg.datagrid('options').url = "";// 这里一定要设置url为空
	var rows = $dg.datagrid('getRows');
	if (rows) {
		while (rows.length > 0) {
			$dg.datagrid('deleteRow', 0);
			rows = $dg.datagrid("getRows");
		}
		$dg.datagrid('acceptChanges');
	}
	$dg.datagrid('editIndex', -1);
	$dg.datagrid('reloadFooter',[]);//清空footer
};

BillInvCostAdjustBLKDetailController.prototype.validate = function(){
	// 判断明细是否有行信息 没有则只保存头挡
	if ($('#dtlDataGrid').datagrid("getRows") && $('#dtlDataGrid').datagrid("getRows").length <= 0) {
		return true;
	}
	if (endEditing("dtlDataGrid")) {//判断是否结束编辑
		return true;
	}
	return false;
};

//选中款号之后回调事件
BillInvCostAdjustBLKDetailController.prototype.selectorStyleNoCallBack = function(row){
	var self = this;
	//根据row和bill后台查询款号成本
	$.post("find_item_cost", {
		billNo : self.bill.billNo,
		styleNo : row.styleNo,
		shardingFlag : self.bill.shardingFlag
	}, function(data) {
		return data;
	}).then(function(data) {
		data.closingAmount = parseFloat(data.closingQty * data.unitCost).toFixed(2);
		var keyArray=["brandNo","sizeKind","unitCost","closingQty","closingAmount"];
		var inputArray=["brandNo","sizeKind","unitCost","closingQty","closingAmount"];
		$.each(inputArray,function(i,eachValue){
			 var eachEditor = $("#dtlDataGrid").datagrid('getEditor', {
	            'index': $("#dtlDataGrid").datagrid('editIndex'),
	            'field': eachValue
	   		 });
	   		 if(eachEditor!=null){
	   		 	 eachEditor.target.val(data[keyArray[i]]);
	   		 }
		 });
	});
};

//确认是否结束编辑
BillInvCostAdjustBLKDetailController.prototype.endEdit = function() {
	if ($('#dtlDataGrid').datagrid('validateRow',self.editRowIndex)) {
		$('#dtlDataGrid').datagrid('endEdit',self.editRowIndex);
		return true;
	}
	return false;
};


//==========================公用函数
//判断行是否结束
function endEditing(datagridId) {
    var dg = $('#' + datagridId);
    var index= dg.datagrid('editIndex');
    if (index == undefined ||index==-1) {
        return true;
    }
    
    if (dg.datagrid('validateRow',index)) {
		$(dg.datagrid('endEdit',index));
		return true;
	}
	return false;
};

//获取绝对路径
function getBasePath(url){
	if(url.indexOf("../")==0 || url.indexOf("fas/")>=0){
		return url;
	}
	return BasePath+url;
}

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

//================================datagrid扩展方法
$(function ($){
	// 获取当前的编辑行序号
    $.fn.datagrid.methods.editIndex = function (jq,param) {
        if(param){
            $.data(jq[0],'editIndex',-1);
            return param;
        }
        return $.data(jq[0], 'editIndex');
    };
});

