
function TransferBalanceDateDialog() { 
	var $this = this;
	
	this.batchAdd = function() {
		ygDialog({
			title : '批量生成界面',
			target : $('#myFormPanel'),
			width : 400,
			height : 250,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					$this.batchSave();
					$('#dataForm').form('clear');
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
					$('#dataForm').form('clear');
				}
			} ]
		});
	};
	
	this.batchSave = function() {
		var fromObj = $('#dataForm');
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		fromObj.form('submit', {
			url :BasePath + '/transfer_balance_date/batch_create',
			onSubmit : function(param) {
			},
			success : function(data) {
				var jsonObj=JSON.parse(data);
				if (jsonObj.flag==true) {
					$('#myFormPanel').dialog('close');
					$.fas.showMsg('生成成功!',5000);
					dialog.search();
				} else {
					showWarn('生成失败！');
				}
			}
		});
	}
}

function TransferBalanceDateEditor() {
	var $this = this;
	
	//校验数据
	this.checkSave = function(options, data) {
		var inserted = $("#balanceDateDataGrid").datagrid('getChanges','inserted');
		var updated = $("#balanceDateDataGrid").datagrid('getChanges','updated');
		$.each(inserted, function(index, item){
			if(compareTwoDate(item['balanceStartDate'],item['balanceEndDate'])){
				return true;
			}
		});
		$.each(updated, function(index, item){
			if(compareTwoDate(item['balanceStartDate'],item['balanceEndDate'])){
				return true;
			}
		});
		var url = BasePath + "/transfer_balance_date/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, data, function(result){
			if(result && result.success) {
				showInfo(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	
//	this.checkUpdate = function(options, rowIndex, rowData) {
//		if(rowData.invoiceFlag == 1){
//			showWarn("结算期已生成开票申请，不能修改！");
//			return false;
// 	    }
//		return true;
//	};
	
	this.checkDel = function(options) {
		var checkedRows = $('#' + options.dataGridId).datagrid('getChecked');
		var delFlag = true;
		$.each(checkedRows, function(index,row) {
			if(row.invoiceFlag == 1){
				showWarn("已开票，不能删除！");
				delFlag = false;
				return false;
	 	    }
		});
		return delFlag;
	};

	this.transferFlagformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':'0', 'text': '未开票'}, {'value':'1', 'text':'已开票'}];
	    for(var i = 0; i < dataStatusType.length; i++) {
	        if(dataStatusType[i].value == value) {
	            return dataStatusType[i].text;
	        }
	    }
	    return "";
	};
	
	this.balanceFlagformatter = function(value, rowData, rowIndex) {
	    var dataStatusType = [{'value':'0', 'text': '未生成'}, {'value':'1', 'text':'已生成'}];
	    for(var i = 0; i < dataStatusType.length; i++) {
	        if(dataStatusType[i].value == value) {
	            return dataStatusType[i].text;
	        }
	    }
	    return "";
	};
}
	
//比较两个日期
function compareTwoDate(startDate, endDate) {
    var arr = startDate.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = endDate.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes >= lktimes) {
        showWarn('起始日期不能大于终止日期，请检查');
        TransferBalanceDateDialog.search(); 
        return false;
    }else{    
    	return true;
    }
}
	

var dialog = null, editor = null;
$(function() {
	$.fas.extend(TransferBalanceDateDialog, FasDialogController);
	$.fas.extend(TransferBalanceDateEditor, FasEditorController);
	dialog = new TransferBalanceDateDialog();
	dialog.init("/transfer_balance_date", {
		dataGridId : "balanceDateDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "地区调货结算期导出",
		exportUrl : "/do_fas_export"
	});
	
	editor = new TransferBalanceDateEditor();
	editor.init("/transfer_balance_date", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'balanceDateDataGrid',
		saveUrl : "/transfer_balance_date/save",
		searchBtn : "btn-search",
		afterUpdate : function(rowIndex, rowData) {
			$.fas.setEditorVal({
				dataGridId : 'balanceDateDataGrid',
				rowIndex : rowIndex,
				field : 'balanceMonth',
				value : rowData.balanceMonth
			});
			var ed = $('#balanceDateDataGrid').datagrid('getEditor', {
				index : rowIndex,
				field : 'salerNo'
			});
			$(ed.target).combobox('disable');
			var ed = $('#balanceDateDataGrid').datagrid('getEditor', {
				index : rowIndex,
				field : 'buyerNo'
			});
			$(ed.target).combobox('disable');
		},
		keyboard : true
	});
	
	ajaxRequestAsync(BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	var  organTypeNo =  currentUser.organTypeNo;
});
