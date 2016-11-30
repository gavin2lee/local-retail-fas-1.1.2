var applyForPayment={};
var editIndex = undefined;

applyForPayment.tabParams=
[ {
	id : 'dataDGridJG',
	title : '单据明细',
	tabUrl : BasePath + '/apply_for_payment/billDtlInfo.htm',
	listUrl : BasePath + "/bill_ask_payment_dtl/list.json",
	queryParams : {}
}, {
	id : 'sumDataGrid',
	title : '结算单明细',
	tabUrl : BasePath + '/apply_for_payment/balanceInfo.htm',
	listUrl : BasePath+ "/bill_balance/list.json",
	queryParams : {}
} ];

$(function(){
	applyForPayment.addMainTab();
	$('#mainTab').tabs('hideHeader');
	returnTab('mainTab', '单据查询');
	applyForPayment.add();
	applyForPayment.setDtlTabs();
	
	applyForPayment.initBalanceNo();
	applyForPayment.initSettleMethod();
	applyForPayment.setCurrency();
});

applyForPayment.setCurrency = function() {
	$("#currencyNameId").combobox(
	{
		onLoadSuccess : function() {
			var currencies = $("#currencyNameId").combobox("getData");
			for ( var i = 0; i < currencies.length; i++) {
				if (currencies[i]["standardMoney"] == 1) {
					$("#currencyNameId").combobox("setValue",currencies[i]["currencyName"]);
					$("#currencyId").val(currencies[i]["currencyCode"]);
				}
			}
		}
	});
};

applyForPayment.setDtlTabs = function() {
	for ( var i = 0; i < applyForPayment.tabParams.length; i++) {
		applyForPayment.addDtlTab(applyForPayment.tabParams[i].title,applyForPayment.tabParams[i].tabUrl);
	}

	$('#dtlTab').tabs(
	{
		onSelect : function(title) {
			for ( var j = 0; j < applyForPayment.tabParams.length; j++) {
				if (applyForPayment.tabParams[j].title == title) {
					applyForPayment.loadTabDg(applyForPayment.tabParams[j].id,applyForPayment.tabParams[j].listUrl,applyForPayment.tabParams[j].queryParams);
				}
			}
		}
	});
	returnTab('dtlTab', applyForPayment.tabParams[0].title);
};

applyForPayment.addDtlTab = function(title, href) {
	$('#dtlTab').tabs('add', {
		title : title,
		selected : false,
		closable : false,
		href : href
	});
};

var datas=[];
applyForPayment.initSettleMethod=function(){
	var url=BasePath+'/common_util/getSettleMethod';
	ajaxRequestAsync(url, null, function(data) {
		$.each(data, function(index, item) {
			datas[item.code] = item.name;
		});
	});
};

applyForPayment.initBalanceNo=function(){
	$('#balanceNoId').combogrid({    
		panelWidth : 400,
		panelHeight : 300,
	    idField:'billNo',    
	    textField:'billNo',    
	    url:'',    
	    columns:[[    
          	{field : 'billNo',title : '结算单号',width : 180, halign : 'center', align : 'left'},
          	{field : 'balanceAmount',title : '应付金额',width : 100, halign : 'center', align : 'right'},
			{field : 'salerName',title : '供应商',width : 200,align:'left',halign:'center'},
			{field : 'buyerName',title : '公司',width : 200,align:'left',halign:'center'},
			{field : 'brandUnitName',title : '品牌部',width : 80,align:'center',halign:'center'}
	    ]],  
	    onShowPanel: function () { 
	    	var salerNo=$("#salerNoId").val();
	    	var buyerNo=$("#buyerNoId").val();
	    	var $dg= $('#balanceNoId').combogrid('grid');
	    	if(salerNo!= '' && buyerNo!=''){
	    		var queryParams = { salerNo:salerNo,buyerNo:buyerNo,balanceType:16,status:1};
	    		$dg.datagrid( 'options' ).queryParams = queryParams;
	    		$dg.datagrid( 'options' ).url = BasePath+'/bill_balance/list.json?';
	    		$dg.datagrid( 'load' );
	    	}else{
	    		$dg.datagrid('loadData',{total:0,rows:[]}); 
	    	}
        },
        onHidePanel: function () {
	     	var row = $('#balanceNoId').combogrid('grid').datagrid('getSelected');
	     	if(row){
	    		$('#balanceAmountId').val(row.balanceAmount);
	    		$("#balanceNoId").val(row.billNo);
	    		$('#dataDGridJG').datagrid('loadData',{total:0,rows:[]}); 
	    		applyForPayment.insertRow(row);
	    	}
        }
	});  
};

applyForPayment.addMainTab=function(){
	$('#mainTab').tabs('add', {
		title : '单据查询',
		selected : false,
		closable : false,
		href : BasePath + '/apply_for_payment/list_tabMain.htm',
		onLoad : function(panel) {
		}
	});
};

applyForPayment.search=function(){
	var reqParam = $("#searchForm").form("getData");
	reqParam.balanceType = '16';
	var queryMxURL = BasePath + "/apply_for_payment/list.json";
	$("#mainDataGrid").datagrid('options').queryParams = reqParam;
	$("#mainDataGrid").datagrid('options').url = queryMxURL;
	$("#mainDataGrid").datagrid('load');
};

applyForPayment.clear=function(){
	$("#searchForm").form("clear");
	$("#salerNoCond,#buyerNoCond").val("");
};

applyForPayment.statusFormat=function(v){
	var combobox = $("#status");
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

applyForPayment.add=function(){
	$("#mainDataForm").form('clear');
	$('#mainDataForm input').removeClass("readonly").removeAttr("readonly",true).removeClass("disabled");
	$('#mainDataForm').find("input[class*=easyui-combobox]").combobox("enable");
	$('#mainDataForm').find("input[class*=easyui-datebox]").datebox('enable');
	applyForPayment.initAdd();
	applyForPayment.clearTabsData();
};

applyForPayment.initAdd = function() {  
	$('#billNo,#allAmountId,#statusNameId,#balanceAmountId').attr("readonly",true).addClass("readonly");
	$('#id,#statusId,#buyerNoId,#salerNoId').val("");	
	$("#buyerNameId,#salerNameId").combogrid("enable");
	applyForPayment.initBalanceNo();
	$("#balanceNoId").removeClass("readonly").removeAttr("readonly",true)
	$("#billDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
};

applyForPayment.clearTabsData = function() {
	$('#bottombarMx').find('span').text("");
	for ( var i = 0; i < applyForPayment.tabParams.length; i++) {
		applyForPayment.clearDtlData(applyForPayment.tabParams[i].id);
	}
};

applyForPayment.clearDtlData = function(id) {
	if ($('#' + id).length > 0) {
		var rows = $('#' + id).datagrid('getRows');
		if (rows) {
			var cloneRows = rows.slice(0);
			for ( var i = 0; i < cloneRows.length; i++) {
				$('#' + id).datagrid('deleteRow', 0);
			}
			$('#' + id).datagrid('acceptChanges');
		}
	}
};

applyForPayment.save=function(){
	var $billNo=$("#billNo").val();
	var $status = $('#statusId').val();
	if ($billNo != '') {
		if ($status > 0) {
			showInfo("已经审核，不能修改！");
			return;
		}
		applyForPayment.update();
	}else{
		var fromObj = $('#mainDataForm');
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		if (endEditing()) {
			$("#mainDataForm").form('submit', {    
				url:BasePath + '/apply_for_payment/save_bill',   
				onSubmit: function(){    
				},    
				success:function(data){    
					if(data){
						var jsonObj = JSON.parse(data);
						if(jsonObj.success==true){
							$('#mainDataForm').form('load', jsonObj.bill);
							applyForPayment.addFormClass();
							applyForPayment.saveDtl();
						} 
					}
				}    
			});  
		}
	}
};

applyForPayment.update=function(){
	var fromObj = $('#mainDataForm');
	fromObj.form('submit', {
		url : BasePath + '/apply_for_payment/update_bill',
		onSubmit : function(param) {
		},
		success : function(data) {
			if (data) {
				var jsonObj = JSON.parse(data);
				if(jsonObj.success==true){
					$('#mainDataForm').form('load', jsonObj.bill);
					applyForPayment.addFormClass();
					applyForPayment.saveDtl();
				} 
			} else {
				showError('保存失败');
			}
		}
	});
};

applyForPayment.addFormClass=function(){
	$("#buyerNameId,#salerNameId,#balanceNoId").combogrid("disable");
	$("#billDate").datebox('disable');
};

applyForPayment.removeFormClass=function(){
	$("#buyerNameId,#salerNameId,#balanceNoId").combogrid("enable");
	$("#billDate").datebox('enable');
};

applyForPayment.saveDtl=function(){
	var billNo=$("#billNo").val();
	var insertedData = $('#dataDGridJG').datagrid('getChanges', 'inserted');
	var updatedData = $('#dataDGridJG').datagrid('getChanges', 'updated');
	var deletedData = $('#dataDGridJG').datagrid('getChanges', 'deleted');
	$.each(insertedData, function(index, row) {
		row.billNo = billNo;
	});
	var data = {
		inserted : JSON.stringify(insertedData),
		updated : JSON.stringify(updatedData),
		deleted : JSON.stringify(deletedData),
	};
	ajaxRequestAsync(BasePath + '/bill_ask_payment_dtl/save', data, function() {
		if (data) {
			$('#dataDGridJG').datagrid('acceptChanges');
			$('#dataDGridJG').datagrid('load');
			showSuc('保存成功');
			applyForPayment.search();
		} else {
			showError('保存失败');
		}
	});
};

applyForPayment.del=function(){
	var status=$('#statusId').val();
	if(status!=0){
		showInfo("已结审核，不能删除！");
		return;
	}else{
		var $billNo = $("#billNo").val();
		$.messager.confirm("确认", "确定要删除当前单据?", function(r) {
			if (r) {
				var url = BasePath + '/apply_for_payment/delete_bill';
				var params = {
					idList : $billNo,
				};
				ajaxRequestAsync(url, params, function(data) {
					if (data > 0) {
						$.fas.showMsg('删除成功',5000);
						applyForPayment.add();
						applyForPayment.removeFormClass();
						applyForPayment.search();
					} else {
						showError('删除失败');
					}
				});
			}
		});
	}
};

applyForPayment.upBill = function() {
	if (applyForPayment.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	type = 1;
	preBill('mainDataGrid', applyForPayment.curRowIndex, type,
			applyForPayment.callBackBill);
};

applyForPayment.downBill = function() {
	if (applyForPayment.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	type = 2;
	preBill('mainDataGrid', applyForPayment.curRowIndex, type,
			applyForPayment.callBackBill);
};

applyForPayment.callBackBill = function(curRowIndex, rowData) {
	if (type == 1 || type == 2) {
		if (rowData != null && rowData != '' && rowData != []) {
			applyForPayment.loadDetail(curRowIndex, rowData);
			type = 0;
		} else {
			if (type == 1) {
				showInfo('已经是第一单');
			} else {
				showInfo('已经是最后一单');
			}
		}
	}
};

applyForPayment.audit=function(opStatus){
	var currStatus=$("#statusId").val();
	if (currStatus == 0 && opStatus == 0) {
		showInfo('不能越级操作');
		return;
	} else if (currStatus == 1 && opStatus == 1) {
		showInfo('不能重复操作');
		return;
	}
	var msg="";
	if (opStatus > 0) {
		msg = "审核";
	} else {
		msg = "反审核";
	}
	$.messager.confirm("确认", "确定"+msg+"当前单据?", function(r) {
		if (r) {
			var url = BasePath + '/apply_for_payment/audit';
			var params = {
				billNo : $("#billNo").val(),
				status : opStatus
			};
			ajaxRequestAsync(url, params, function(data) {
				if (typeof data !="undifined") {
					$.fas.showMsg('操作成功',5000);
					$('#mainDataForm').form('load', data);
					applyForPayment.addFormClass();
					applyForPayment.search();
				} else {
					showError('操作失败');
				}
			});
		}
	});
};

applyForPayment.loadDetail = function(rowIndex, rowData) {
	applyForPayment.curRowIndex = rowIndex;
	returnTab('mainTab', '单据明细');
	returnTab('dtlTab', applyForPayment.tabParams[0].title);
	$('#mainDataForm').form('load', rowData);
	applyForPayment.addFormClass();
	applyForPayment.loadTabDg(applyForPayment.tabParams[0].id,applyForPayment.tabParams[0].listUrl,{billNo:rowData.billNo});
	$('#createUser').html(rowData.createUser);
	$('#createTime').html(rowData.createTime);
	$('#auditor').html(rowData.auditor);
	$('#auditTime').html(rowData.auditTime);
};

//加载明细
applyForPayment.loadTabDg = function(id, url, params) {
	var $billNo = $("#billNo").val();
	var tab = $('#dtlTab').tabs('getSelected');
	var index = $('#dtlTab').tabs('getTabIndex', tab);
	if ($billNo == undefined || $billNo == "") {
		return;
	} else {
		if (index == 0) {
			params.billNo = $billNo;
		} else {
			params.askPaymentNo = $billNo;
		}
		setTimeout(function() {
			$('#' + id).datagrid('options').queryParams = params;
			$('#' + id).datagrid('options').url = url;
			$('#' + id).datagrid('load');
		}, 500);
	}
},

applyForPayment.batchDel=function(){
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	if(checkedRows.length<1){
		showInfo("请先勾选要删除的记录！");
		return;
	}
	var errMessage = "";
	$.each(checkedRows, function(index, data) {
		if (data.status == 1) {
			errMessage = '存在已审核的单据,不能删除!';
			return false;
		} 
	});
	if (errMessage != "") {
		showInfo(errMessage);
		return;
	}
	if (checkedRows) {
	  $.messager.confirm("确认", "确定要删除选择的单据?", function(r) {
		if (r) {
			var url = BasePath + "/apply_for_payment/batch_del";
			var ids_data = "";
			$.each(checkedRows, function(index, row) {
				ids_data += row.billNo + ",";
			});
			var params = {
				billNos :  ids_data.substring(0, ids_data.length - 1),
			};
			ajaxRequestAsync(url, params, function(data) {
				if (typeof data !="undifined") {
					$.fas.showMsg('操作成功',5000);
					applyForPayment.search();
				} else {
					showError('操作失败');
				}
			});
		}
	});
   }
};

applyForPayment.batchAudit=function(opStatus){
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	if (checkedRows.length < 1) {
		showInfo("请先勾选要确认的记录！");
		return;
	}
	var errMessage = "";
	$.each(checkedRows, function(index, data) {
		if (data.status == 0 && opStatus == 0) {
			errMessage = '存在未审核的单据,请重新选择!';
			return false;
		} else if (data.status == 1 && opStatus == 1) {
			errMessage = '不能重复操作';
			return false;
		}
	});
	if (errMessage != "") {
		showInfo(errMessage);
		return;
	}
	var msg="";
	if (opStatus > 0) {
		msg = "审核";
	} else {
		msg = "反审核";
	}
	if (checkedRows) {
	  $.messager.confirm("确认", "确认"+msg+"选择的单据?", function(r) {
		if (r) {
			var url = BasePath + "/apply_for_payment/batch_audit";
			var ids_data = "";
			$.each(checkedRows, function(index, row) {
				ids_data += row.billNo + ",";
			});
			var params = {
				billNos :  ids_data.substring(0, ids_data.length - 1),
				status : opStatus
			};
			ajaxRequestAsync(url, params, function(data) {
				if (typeof data !="undifined") {
					$.fas.showMsg('操作成功',5000);
					applyForPayment.search();
				} else {
					showError('操作失败');
				}
			});
		}
	});
   }
};

applyForPayment.insertRow=function(row){
	var fromObj = $('#mainDataForm');
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		showInfo("主档信息未填写完整！");
		return;
	}
	if (endEditing()) {
	   if (row) {
			$(".datagrid-empty-msg").hide();
			ajaxRequestAsync(BasePath + "/apply_for_payment/getBillDtl", row, function(data) {
				if (typeof data != null) {
					for(var i=0;i<data.length;i++){
						$('#dataDGridJG').datagrid('appendRow',{
							settleMethodNo : data[i].settleMethodNo,
							amount : data[i].amount,
							qty : data[i].qty,
							brandName : data[i].brandName,
							brandNo : data[i].brandNo,
							sysExpirationDate : data[i].sysExpirationDate,
							paymentDate :  data[i].sysExpirationDate
						})
					}
					applyForPayment.setAllAmount();
				} else {
					showError('操作失败');
				}
			});
	  } else{
		  $(".datagrid-empty-msg").hide();
			var rowLen = $('#dataDGridJG').datagrid('getRows').length;
			if (typeof rowLen == 'undefined' || rowLen < 0) {
				rowLen = 0;
			}
			editIndex = rowLen;
			$('#dataDGridJG').datagrid('insertRow', {
				index : rowLen,
				row : {}
			});
			$('#dataDGridJG').datagrid('selectRow', editIndex).datagrid(
					'beginEdit', editIndex);
	  }
	}
};

applyForPayment.deleteRow = function() {
    if (editIndex == undefined) {
		return;
	}
//    if(!endEditing()){
//    	return;
//    }
	var checkedRows = $('#dataDGridJG').datagrid('getChecked');
	if(checkedRows.length<1) {
		showInfo("请先勾选要删除的行.");
		return;
	}
	$.each(checkedRows, function(index, row) {
		var rowIndex = $('#dataDGridJG').datagrid('getRowIndex', row);
		$('#dataDGridJG').datagrid('deleteRow',rowIndex);
		if(undefined != editIndex && editIndex == rowIndex){
			editIndex = undefined;
		}
	});
	
	applyForPayment.setAllAmount();
};

applyForPayment.editRow = function(index) {
	if (editIndex != index) {
        if (endEditing()) {
            $('#dataDGridJG').datagrid('selectRow', index)
                            .datagrid('beginEdit', index);
            editIndex = index;
        } else {
            $('#dataDGridJG').datagrid('selectRow', editIndex);
        }
    }
};

function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#dataDGridJG').datagrid('validateRow', editIndex)){
        $('#dataDGridJG').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
};

applyForPayment.setAllAmount = function(){
	var currAmount = 0;
	var total = 0;
	var rows = $('#dataDGridJG').datagrid('getRows');
	if (editIndex == 0 && rows.length == 1) {
		var amountEd = $('#dataDGridJG').datagrid('getEditor', {index:editIndex,field:'amount'});
		currAmount = $(amountEd.target).numberbox('getValue');
		currAmount = parseFloat(currAmount);
		$("#allAmountId").val(currAmount); 
	}else{
		if (editIndex != undefined) {
			var amountEd = $('#dataDGridJG').datagrid('getEditor', {index:editIndex,field:'amount'});
			currAmount = $(amountEd.target).numberbox('getValue');
			currAmount = parseFloat(currAmount);
		}
		for (var i = 0; i < rows.length; i++) {
			if(rows[i]['amount']){
				total += parseFloat(rows[i]['amount']);
			}
		}
		$("#allAmountId").val(total+currAmount);
	}
};