///////////////////库存成本调整单///////////////////////
billInvCostAdjust = {};

//获取绝对路径
function getBasePath(url){
	if(url.indexOf("../")==0 || url.indexOf("fas/")>=0){
		return url;
	}
	return BasePath+url;
}

/* 库存成本调整单据全局控制 */
function InvCostAdjustController() {
    this.validate = function() {
    	var flag = this.super.validate.call(this);
    	if(flag) {
    		$.ajax({
    			url: getBasePath("/bill_inv_cost_adjust/get_controller_flag"),
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
    	}
    	return flag;
    };
	this.add = function() {
		this.super.add.call(this);
		$('#statusStr').val("制单");
		$('#billType').addClass("readonly");
		billInvCostAdjust.initBillType();
		$('#billType').combobox('setValue',2000); 
	};
	this.del = function() {
		var self = this;
		if (!self.bill || !self.bill.billNo) {
			showInfo("请选择相应的单据再删除！");
			return;
		}
		if (self.bill.status != '0') {
			showWarn("单据为非制单状态，不能进行删除操作！");
			return;
		}
		$.messager.confirm("确认", "确定要删除该条单据?", function(r) {
			if (r) {
				var deleteList = [];
                var obj = new Object();
                obj.id = self.bill.id;
                obj.billNo = self.bill.billNo;
                deleteList.push(obj);
                if(deleteList.length > 0) {
	                var params = new Object();
	                params["deleted"] = JSON.stringify(deleteList);
					$.post(BasePath + "/bill_inv_cost_adjust/batch_delete", params, function(result) {
						if (result) {
							if (isNotBlank(result.errorMessage)) {
								showError(result.errorMessage + ":" + result.errorDefined);
							} else {
								self.setBill({});
								showSuc('删除成功!');
							}
						} else {
							showInfo('删除失败,请联系管理员!', 2);
						}
					});
                }
			}
		});
	};
	this.returnTabs = function(type) {
		billInvCostAdjust.initMonthCombox("monthCondition");
		billInvCostAdjust.initYearCombox("yearCondition");
		this.billHeader.returnTabs(type);
	};
	
	this.billConfirm=function(myUrl){
		if(!billInvCostAdjust.checkCompanySettleDate()){
			return;
		}
		this.super.billConfirm.call(this,myUrl);
	};

}

//重写BillHeader对象
function InvCostAdjustBillHeader() {
	this.refresh = function() {
		this.super.refresh.call(this);
		if(this.bill&&this.bill.status==0){
			$("#remark").attr("readOnly", false).removeClass("readonly");
		}
		if(this.bill&&this.bill.status!=0){
			$("#remark").attr("readOnly", true).addClass("readonly");
		}
	};
}

//重写BillDetail对象
function InvCostAdjustBillDetail() {
	var self = this;
	this.modulePath = '/bill_inv_cost_adjust/';// 主表模块路径
	this.dtlModulePath = '../bill_inv_cost_adjust_dtl/'; 
	self.templateName="库存成本调整单.xlsx";//导入下载excel文件名
	self.dtlExcelTitle="库存成本调整单明细导出";//导出excel文件名
	self.tableName ='bill_inv_cost_adjust_dtl';
	this.hasSizeHorizontal = false;// 是否是尺码横排

	this.init = function() {
		this.super.init.call(this);
		billInvCostAdjust.initBillType();
	};
	this.validate = function() {
		// 判断明细是否有行信息 没有则只保存头挡
		if (this.grid.datagrid("getRows") && this.grid.datagrid("getRows").length <= 0) {
			return true;
		}
		if (this.endEdit()) {
			// 判断明细是否有行信息 没有则只保存头挡
			var rows = self.grid.datagrid("getRows");
			var itemNos = [];
			if(rows) {
				for(var i = 0; i < rows.length; i++) {
					itemNos.push(rows[i].itemNo);
				}
				$.unique(itemNos);
				if(rows.length != itemNos.length) {
					showWarn("同一个商品不能出现多行记录，请检查后再保存!");
					return false;
				}
			}
			return true;
		}
		return false;
	};
	this.importDetail = function() {
		var billNo = $('#billNo').val();
		var companyNo = $('#companyNo').val();
		var year = $('input[name=year][type=hidden]').val();
		var month = $('input[name=month][type=hidden]').val();
		$.importExcel.open({
			'submitUrl' : "/fas/bill_inv_cost_adjust_dtl/import?billNo=" + billNo + "&companyNo=" + companyNo + "&year=" + year + "&month=" + month,
			'templateName' : '库存成本调整单.xlsx',
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
	};
	this.refresh = function() {
		this.super.refresh.call(this);
	};
}

//重写BillList对象
function InvCostAdjustBillList() { 
	this.statusType="-1|0|1";
	this.listExcelTitle="库存成本调整单导出";
	this.onInited = function() {
		billInvCostAdjust.initMonthCombox("monthCondition");
		billInvCostAdjust.initYearCombox("yearCondition");
		this.super.onInited.call(this);
	};
	// 删除单据(批量)
	this.batchDel = function() {
		var self = this;
		var grid = $('#' + self.gridId);
		var rows = grid.datagrid('getRows');
		if (rows.length == 0) {
			showInfo('请选择要操作的数据!');
			return;
		}
		var rowObj = grid.datagrid('getChecked');
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
			var pkValue = item.billNo;
			if (!pkValue) {
				showWarn("请选择相应的单据再删除！");
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
						url : BasePath + "/bill_inv_cost_adjust/batch_delete",
						data : params,
						async : false
					}).then(function(data) {
						if (data && data.success) {
							showSuc('删除成功!');
							self.search();
						} else {
							showInfo('删除失败,请联系管理员!');
						}
					});
	            }
	            $.messager.progress('close');
			}
		});
	};
}

//jquery初始化
var ctrl = null;
$(function() {
	$.fas.extend(InvCostAdjustController, BillController);
	$.fas.extend(InvCostAdjustBillHeader, BillHeader);
	$.fas.extend(InvCostAdjustBillDetail, BillDetail);
	$.fas.extend(InvCostAdjustBillList, BillList);
	ctrl = new InvCostAdjustController();
	ctrl.init(new Toolbars(), new InvCostAdjustBillHeader(), new InvCostAdjustBillDetail(), new InvCostAdjustBillList());
	returnTab('mainTab', 1);
	
	billInvCostAdjust.initMonthCombox("month");
	billInvCostAdjust.initYearCombox("year");
	billInvCostAdjust.initMonthCombox("monthCondition");
	billInvCostAdjust.initYearCombox("yearCondition");
//	billInvCostAdjust.yearData();
});

//单据类型数据
billInvCostAdjust.billTypeData = [ {
	"value" : "2000",
	"text" : "库存成本调整单"
}];

//初始化单据类型
billInvCostAdjust.initBillType = function() {
	$('#billType').combobox({
		valueField : "value",
		textField : "text",
		data : billInvCostAdjust.billTypeData,
		panelHeight : "auto"
	});
};

//行编辑选择商品时触发的事件
billInvCostAdjust.clickItemFn = function (data) {
	var unitCost=0, closingQty=0;//牌价 默认商品建议牌价
	var itemNo = data.itemNo, companyNo = $("#companyNo").val(), year = $("#year").combobox("getValue"), month = $("#month").combobox("getValue");
	$.ajax({
	   url: getBasePath("/bill_inv_cost_adjust/find_item_cost"),
	   data: {itemNo:itemNo, companyNo:companyNo, year:year, month:month},
	   async:false,
	   success: function(result){
			if (result!=null) {
				if(isNotBlank(result.unitCost)){
					unitCost=result.unitCost;
				}
			} else {
				showWarn('获取价格失败,请联系管理员!');
			}
		},
		error:function(result){
			showWarn('获取价格异常,请联系管理员!');
		}
	 });
	$.ajax({
	   url: getBasePath("/bill_inv_cost_adjust/find_period_balance"),
	   data: {itemNo:itemNo, companyNo :companyNo, year:year, month:month},
	   async:false,
	   success: function(result){
			if (result!=null) {
				//当前调整的数量=期初数量+期间入的数量
				if(isNotBlank(result.openingQty)&&isNotBlank(result.purchaseInQty)&&isNotBlank(result.outerTransferInQty)&&isNotBlank(result.purchaseReturnQty)){
					closingQty=result.openingQty + result.purchaseInQty + result.outerTransferInQty + result.purchaseReturnQty;
				}
			} else {
				showWarn('获取价格失败,请联系管理员!');
			}
		},
		error:function(result){
			showWarn('获取价格异常,请联系管理员!');
		}
	 });
	data.unitCost = unitCost;
	data.closingQty = closingQty;
	data.closingAmount = parseFloat(closingQty * unitCost).toFixed(2);
	var keyArray=["code","itemNo","name", "brandNo","unitCost", "closingQty", "closingAmount", "sizeKind"];
    var inputArray=["itemCode","itemNo","itemName","brandNo","unitCost", "closingQty", "closingAmount", "sizeKind"];
	$.each(inputArray,function(i,eachValue){
		 var eachEditor = $("#dtlDataGrid").datagrid('getEditor', {
            'index': $("#dtlDataGrid").datagrid('editIndex'),
            'field': eachValue
   		 });
   		 if(eachEditor!=null){
   		 	 eachEditor.target.val(data[keyArray[i]]);
   		 }
	 });
	
};

billInvCostAdjust.checkCompanySettleDate = function() {
	var ret = false;
	var companyNo = $("#companyNo").val(), year = $("#year").combobox("getValue"), month = $("#month").combobox("getValue");
	$.ajax({
	   url: getBasePath("/bill_inv_cost_adjust/check_company_settle_date"),
	   data: {companyNo:companyNo, year:year, month:month},
	   async:false,
	   success: function(result){
			if (result && result.success) {
				ret = true;
			} else {
				showError(result.message);
			}
		},
		error:function(result){
			showWarn('获取FAS的公司关账日期异常,请联系管理员!');
		}
	 });
	
	return ret;
};

//改变调整成本文本框时触发的事件
billInvCostAdjust.invCostAdjustChange = function(adjustCost) {
    var closingQty = billInvCostAdjust .getEditorVal("dtlDataGrid", "closingQty");
    var closingAmount = billInvCostAdjust .getEditorVal("dtlDataGrid", "closingAmount");
    
//    billInvCostAdjust.setEditorVal("dtlDataGrid", "adjustAmount", (closingQty * adjustCost));
    billInvCostAdjust.setEditorVal("dtlDataGrid", "diverAmount", 
    		parseFloat((closingQty * adjustCost) - closingAmount).toFixed(2));
};

//获取editor的值
billInvCostAdjust.getEditorVal = function(dataGrid, field) {
	var row = $("#" + dataGrid).datagrid('getSelected');  
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

//双击编辑明细事件(业务需要，重写gms.common.selectItemHandel方法)
billInvCostAdjust.selectItemHandel = function(rowIndex, rowData) {
	$.data($("#dtlDataGrid")[0], 'editIndex', rowIndex);
	var status = $('input[type=hidden][name=status]').val();
	if (status == 0) {
		$('#dtlDataGrid').datagrid('endEdit', rowIndex);
		$('#dtlDataGrid').datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
	}else{
		showWarn("非制单状态单据不允许修改!");
	}
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

//初始化年份下拉框
billInvCostAdjust.initYearCombox = function(id){
	$("#" + id).combobox({
		data :billInvCostAdjust.yearData(),
		valueField : "lable",
		textField : "text",
		panelHeight : "auto",
		width : 130
	});
};

//初始化月份下拉框
billInvCostAdjust.initMonthCombox = function(id){
	$("#" + id).combobox({
		data : billInvCostAdjust.monthData,
		valueField : "lable",
		textField : "text",
		panelHeight : "auto",
		width : 130
	});
};