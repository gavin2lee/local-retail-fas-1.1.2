var invoiceConfirm={};

$(function(){
});

var comfirmData = [ {
	"id" : "1",
	"text" : "已确认"
}, {
	"id" : "0",
	"text" : "未确认"
} ];

// 查询
invoiceConfirm.search=function(){
	var params = $('#searchForm').form('getData');
	var url = BasePath+ '/invoice_confirm/list.json';
	$('#dataGridJG').datagrid('options').queryParams = params;
	$('#dataGridJG').datagrid('options').url = url;
	$('#dataGridJG').datagrid('load');
};

//清空
invoiceConfirm.clear=function(){
	$('#searchForm').form('clear');
	$('#salerNoCond').val('');
	$('#buyerNoCond').val('');
};

//格式化单据查询单据状态
invoiceConfirm.typeFormat = function(v) {
	var combobox = $("#balanceTypeCondition");
	var value = combobox.combobox("options").valueField;
	var text = combobox.combobox("options").textField;
	var datas = combobox.combobox("getData");
	for ( var i = 0; i < datas.length; i++) {
		if (datas[i][value] == v) {
			return datas[i][text];
		}
	}
	return "";
};

/**
 * 到票确认
 */
invoiceConfirm.doConfirm = function() {
	var checkedRows = $('#dataGridJG').datagrid('getChecked');
	if(checkedRows.length<1){
		showInfo("请选择要操作的记录!");
		return;
	}
	var errMessage = "";
	$.each(checkedRows, function(index, item) {
		if (item.useFlag == 1) {
			errMessage = '存在已经确认的发票,请重新选择!';
			return false;
		}
	});
	if (errMessage != "") {
		showInfo(errMessage);
		return;
	}
	//设置日期
	$("#confirmDateId").datebox('setValue',new Date().format("yyyy-MM-dd"));

	ygDialog({
		title : '到票确认',
		target : $('#myFormPanel'),
		width : 350,
		height : 130,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				invoiceConfirm.doUpdate();
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

/**
 * 到票确认
 */
invoiceConfirm.doUpdate=function(){
	var checkedRows = $('#dataGridJG').datagrid('getChecked');
	var fromObj = $('#dataForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
	var $confirmDate = $("#confirmDateId").val();
	var ids = "";
	$.each(checkedRows, function(index, item) {
		ids += item.id + ",";
	});
	var params = {
		idList : ids.substring(0, ids.length - 1),
		confirmDate : $confirmDate
	};
	var url = BasePath + '/invoice_confirm/updateInvoice';
	ajaxRequestAsync(url, params, function(data) {
		if(data>0){
			$.fas.showMsg("确认成功！");
			$("#myFormPanel").window("close");
			invoiceConfirm.search();
		}else{
			showError("操作失败！");
		}
	});
};

/**
 * 生成采购发票
 */
invoiceConfirm.createInvoice=function(){
	var checkedRows = $('#dataGridJG').datagrid('getChecked');
	if(checkedRows.length<1){
		showInfo("请选择要操作的记录!");
		return;
	}
	
	var errMessage = "";
	$.each(checkedRows, function(index, item) {
		if (item.invoiceNoFlag != null && item.invoiceNoFlag != '') {
			errMessage = '存在已经生成采购发票的记录,请重新选择!';
			return false;
		}
	});
	if (errMessage != "") {
		showInfo(errMessage);
		return;
	}
	
	var tipMsg = "";
	$.each(checkedRows, function(index, item) {
		if (item.useFlag == 0) {
			tipMsg = '存在未确认的发票,请重新选择!';
			return false;
		}
	});
	if (tipMsg != "") {
		showInfo(tipMsg);
		return;
	}
	
	ajaxRequestAsync(BasePath + '/invoice_confirm/generate_bill_by_invoice', {checkedRows:JSON.stringify(checkedRows)}, function(data) {
		if(data.flag==true){
			$.fas.showMsg('生成成功.单号为：'+data.billNos,5000);
			invoiceConfirm.search();
		}else{
			showError("生成采购发票失败!");
		}
	});
};

/**
 * 反确认
 */
invoiceConfirm.doReConfirm=function(){
	var checkedRows = $('#dataGridJG').datagrid('getChecked');
	if(checkedRows.length<1){
		showInfo("请选择要操作的记录!");
		return;
	}
	
	var errMessage = "";
	$.each(checkedRows, function(index, item) {
		if (item.useFlag == 0) {
			errMessage = '存在未确认的发票,请重新选择!';
			return false;
		}
	});
	if (errMessage != "") {
		showInfo(errMessage);
		return;
	}
	
	var tipsMsg = "";
	$.each(checkedRows, function(index, item) {
		if (item.invoiceNoFlag != null && item.invoiceNoFlag != '') {
			tipsMsg = '存在已经生成采购发票的记录,请重新选择!';
			return false;
		}
	});
	if (tipsMsg != "") {
		showInfo(tipsMsg);
		return;
	}
	
	var ids = "";
	$.each(checkedRows, function(index, item) {
		ids += item.id + ",";
	});
	var params = {
		idList : ids.substring(0, ids.length - 1),
	};
	var url = BasePath + '/invoice_confirm/invoice_reConfirm';
	ajaxRequestAsync(url, params, function(data) {
		if(data>0){
			$.fas.showMsg("反确认成功！");
			invoiceConfirm.search();
		}else{
			showError("操作失败！");
		}
	});
};

/**
 * 导出
 */
invoiceConfirm.doExport = function (dataGridId, exportUrl, excelTitle, otherParams){
	var $dg = $('#'+dataGridId);
	var params=$dg.datagrid('options').queryParams;
	var columns=$dg.datagrid('options').columns;
	var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
	var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录
	
	var columnsNew = [];
	$.each(columns,function(index,item){
		var dataArray = [];
		var i = 0;
		$.each(item,function(rowIndex,rowData){
			if(rowData.hidden){
				return ;
			}
			var v_object = {};
			v_object.field = rowData.field;
			v_object.title = rowData.title;
			v_object.width = rowData.width;
			if(rowData.hidden == 'true' || rowData.notexport){
				return ;
			}
			dataArray[i] = v_object;
			i++;
		});
		columnsNew[index] = dataArray;
	});
	
	var exportColumns=JSON.stringify(columnsNew);
	
	var url=BasePath+exportUrl;
	
	var dataRow=$dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	
	$("<form id='exportExcelForm'  method='post'></form>").appendTo("body"); ;
	
	var fromObj=$('#exportExcelForm');
	
	if(dataRow.length>0){
	    fromObj.form('submit', {
			url: url,
			onSubmit: function(param){
				if(params!=null&&params!={}){
					$.each(params,function(i){
						param[i]=params[i];
					});
				}
				if(otherParams!=null&&otherParams!={}){
					$.each(otherParams,function(i){
						param[i]=otherParams[i];
					});
				}
				param.exportColumns=exportColumns;
				param.fileName=excelTitle;
				param.pageNumber = v_pageNumber;
				param.pageSize = v_pageSize;
				
			},
			success: function(){
				
		    }
	   });
	}else{
		alert('查询记录为空，不能导出!',1);
	}
};