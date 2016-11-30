///////////////////采购入库调整单///////////////////////
billInvCostAdjust = {};

//获取绝对路径
function getBasePath(url){
	if(url.indexOf("../")==0 || url.indexOf("fas/")>=0){
		return url;
	}
	return BasePath+url;
}

/* 采购入库调整单据全局控制 */
function InvCostAdjustController() {
	this.add = function() {
		this.super.add.call(this);
		$('#status').val("制单");
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
					$.post(BasePath + "/bill_purchase_adjust/batch_delete", params, function(result) {
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
	
	this.billConfirm=function(myUrl){
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
	this.modulePath = '/bill_purchase_adjust/';// 主表模块路径
	this.dtlModulePath = '../bill_purchase_adjust_dtl/'; 
	self.dtlExcelTitle="采购入库调整单明细导出";//导出excel文件名
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
			}
			return true;
		}
		return false;
	};
	this.refresh = function() {
		this.super.refresh.call(this);
	};
}

//重写BillList对象
function InvCostAdjustBillList() { 
	this.statusType="-1|0|1";
	this.listExcelTitle="采购入库调整单导出";
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
						url : BasePath + "/bill_purchase_adjust/batch_delete",
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
};

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
	
});

//单据类型数据
billInvCostAdjust.billTypeData = [ {
	"value" : "2000",
	"text" : "采购入库调整单"
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


//行编辑选择商品时触发的事件
billInvCostAdjust.clickItemFn = function (data) {
	billInvCostAdjust
	var keyArray=["code","itemNo","name", "brandNo","brandName"];
    var inputArray=["itemCode","itemNo","itemName","brandNo","brandName"];
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
billInvCostAdjust.financeAntiConfirm = function() {
	var status=$("#status").parent().find("span").find("input").last().val().trim();
	if(status == 0){
		showWarn("单据未确认,不能反确认!");
		return false;
	}
	
    if(status == 1){
    	$.messager.confirm("确认","你确定要反确认这条数据",function(r) {
			if (r) {
				var currentUser;
				var data = {
						billNo : $('#billNo').val(),
						status : 0
				};
				ajaxRequestAsync(BasePath +"/bill_purchase_adjust/confirm",data,function(result){
					if(result) {
            			showSuc("反确认成功!");
            			ctrl.search();
            			$('#mainDataForm').form('load', data);
            		} else {
            			showError("反确认失败,请联系管理员!");
            		}
				});
            };
    	});
    }
};
