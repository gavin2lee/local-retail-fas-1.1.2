function BillPrePaymentNt() {
	var $this = this;
	this.paidTypeData = [{'code':'0','name':'批发订单收款'},{'code':'1','name':'保证金收款'},{'code':'2','name':'客户收款'}];
}

var _fasDialogController = new FasDialogController("/bill_pre_payment_nt", fas_common_setting);

BillPrePaymentNt.prototype.super = _fasDialogController;

var _billPrePaymentNt = new BillPrePaymentNt();

_billPrePaymentNt.clear = function(){
	$('#searchForm').form('clear');
	$('#companyNoCondition,#multiCustomerNo').val('');
}

//点击"新增"按钮时，触发的操作
BillPrePaymentNt.prototype.toAdd = function() {
	_fasDialogController.super.loadedAdd = function() {
		$("#id").val("");
		$("#reversalAmount").numberbox("setValue", '0.00');
		$('#dataForm').find('i').show();
		_billPrePaymentNt.setReadOnly();
		$('#saleOrderNo').combo('disable');
	};
	//检查新增的数据是否正确
	_fasDialogController.super.checkSave = function() {
		return _billPrePaymentNt.validateData();
	};
	_fasDialogController.toAdd();
};

//进入修改页面
BillPrePaymentNt.prototype.toUpdate = function(rowData) {
	_fasDialogController.checkInitUpdate = function(rowData) {
		if(rowData.auditStatus == '1') {
			showWarn("数据已审核，不允许修改!");
			return false;
		}
		$('#dataForm').form('clear');
		return true;
	};
	_fasDialogController.loadedUpdate = function(rowData) {
		$("#saleOrderNo").addClass("readonly");
		if(rowData.paidType != '0'){
			$('#saleOrderNo').combogrid({
				required:false
			});
		}
		$('#dataForm').find('i').hide();
		_billPrePaymentNt.setReadOnly();
		if($(":checkbox[id='reversalAmountFlag']").attr("checked")) {
			$("#reversalAmount").removeClass("readonly").removeAttr("readonly");
		}
	};
	//检查新增的数据是否正确
	_fasDialogController.super.checkUpdate = function() {
		return _billPrePaymentNt.validateData();
	};
	_fasDialogController.toUpdate(rowData);
};

//删除
BillPrePaymentNt.prototype.doDel = function() {
	_fasDialogController.super.checkDel = function(checkedRows) {
		if(checkedRows && checkedRows.length == 1 && checkedRows[0].auditStatus == '1') {
			showWarn("数据已审核，不允许删除!");
			return false;
		}
		if(checkedRows && checkedRows.length > 1) {
			for(var i = 0; i < checkedRows.length; i++) {
				if(checkedRows[i].auditStatus == '1') {
					showWarn("您选择的数据中存在已审核状态的数据，不允许删除!");
					return false;
				}
			}
		}
		return true;
	};
	_fasDialogController.doDel();
};

//校验数据
BillPrePaymentNt.prototype.validateData = function() {
	return true;
};

//设置样式
BillPrePaymentNt.prototype.setReadOnly = function() {
	var fields = _billPrePaymentNt.buildStyleFields();
	this.super.setReadOnly(fields);
};

//组装设置或清空样式的字段
BillPrePaymentNt.prototype.buildStyleFields = function() {
	return {
		input : ["customerName", "companyName", "reversalAmount", "prePaymentOver"]
	};
};

//审核
BillPrePaymentNt.prototype.doAudit = function() {
	this.super.super.buildAuditData = function() {
	    return _billPrePaymentNt.buildRequestData();
	};
	_fasDialogController.checkAudit = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].auditStatus == '1') {
				showWarn("数据已审核，无需再审核!");
				return false;
			}
		}
		return true;
	};
	this.super.doAudit();
};

//反审核
BillPrePaymentNt.prototype.doAntiAudit = function() {
	this.super.super.buildAntiAuditData = function() {
		return _billPrePaymentNt.buildRequestData();
	};
	_fasDialogController.checkAntiAudit = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].auditStatus == '0') {
				showWarn('数据未审核，请勿重复操作');
				return false;
			}
		}
		return true;
	};
	this.super.doAntiAudit();
};

//组装请求的参数(审核/反审核)
BillPrePaymentNt.prototype.buildRequestData = function() {
	var oList = [];
	var checkedRows = $("#dataGridDiv").datagrid("getChecked");
	for(var i = 0; i < checkedRows.length; i++) {
		var obj = new Object();
		obj.id = checkedRows[i].id;
		obj.billNo = checkedRows[i].billNo;
		obj.companyNo = checkedRows[i].companyNo;
		obj.companyName = checkedRows[i].companyName;
		obj.customerNo = checkedRows[i].customerNo;
		obj.customerName = checkedRows[i].customerName;
		obj.saleOrderNo = checkedRows[i].saleOrderNo;
		obj.orderAmount = checkedRows[i].orderAmount;
		obj.prePayment = checkedRows[i].prePayment;
		obj.paidAmount = checkedRows[i].paidAmount;
		obj.paidType = checkedRows[i].paidType;
		obj.reversalAmount = checkedRows[i].reversalAmount;
		obj.remark = checkedRows[i].remark;
		oList.push(obj);
	}
    return oList;
};

//可冲销金额
var reversalAmount = 0;

//初始化查询框组件
BillPrePaymentNt.prototype.initSearchBox = function() {	
	$('#saleOrderNo').combogrid({
		width :200,
        panelWidth:700,
	    panelHeight : 250,
        mode:'remote',
        idField:'billNo',
        textField:'billNo',
        url : '',
		fitColumns : true,
		pagination : true,
        columns:[[
        {field:'salerName',title:'公司',width:200},
        {field:'buyerName',title:'客户',width:150},
        {field:'billNo',title:'单据编码',width:150},
        {field:'billDate',title:'单据日期',width:100},
        {field:'amount',title:'金额',width:120},
        {field:'sendQty',title:'数量',width:100}
        ]],
        onHidePanel:function(){
        	var data = $('#saleOrderNo').combogrid('grid').datagrid('getSelected');
        	if(data){
            	$("#saleOrderNo").val(data.billNo);
    			$("#orderAmount").val(data.amount);
    			$("#preOrderAmount").val(data.preOrderAmount);
    			$("#preSendAmount").val(data.preSendAmount);
    			$("#termName").val(data.termName);
    			$("#termNo").val(data.termNo);
        	}else{
        		clearRef();
        	}
        },
        onShowPanel:function(){
        	loadRef();
        }
    });
};

function clearRef(){// 清空关联信息
	$('#saleOrderNo').combogrid('clear');
	$("#orderAmount").val('');
	$("#preOrderAmount").val('');
	$("#preSendAmount").val('');
	$("#termName").val('');
	$("#termNo").val('');
};

function loadRef(){
	var salerNo = $('#dataForm').find("input[name=companyNo]").val();
	var buyerNo = $('#dataForm').find("input[name=customerNo]").val();
	var dg = $('#saleOrderNo').combogrid('grid');
	if(salerNo!= '' && buyerNo!=''){
		dg.datagrid( 'options' ).url = BasePath + '/bill_sale_balance/select_sale_order?salerNo='+salerNo+'&buyerNo='+buyerNo;
		dg.datagrid( 'load' );
	}else{
		dg.datagrid('loadData',{total:0,rows:[]}); 
	}
}
//获取冲销金额
BillPrePaymentNt.prototype.getReversalAmount = function() {
	var checked = $("#reversalAmountFlag").attr("checked");
	if(checked && checked == 'checked') {
		$("#reversalAmount").removeClass("readonly").removeAttr("readonly");
		var customerNo = $("#customerNo").val();
		var companyNo = $("#companyNo").val();
		if(customerNo && companyNo) {
			var url = BasePath + "/wholesale_prepay_warn/select_margin_amount";
			var params = {companyNo : $("#companyNo").val(), customerNo : customerNo};
			ajaxRequest(url, params, function(result) {
				$("#reversalAmount").numberbox("clear");
				$('#reversalAmount').numberbox('setValue', (result.reversalOverAmount).toFixed(2));
			});
		}
	} else {
		$("#reversalAmount").numberbox("setValue", '0.00');
		$("#reversalAmount").addClass("readonly").addAttr("readonly",true);
	}
};


function importCallBack(data){
	if(data.indexOf('"success":true')!=-1){
		var jsonData = JSON.parse(data);
		if(jsonData.success){
			var lstData = jsonData.rows;
			if(lstData.length > 0){
				$('#importDataGrid').datagrid({
					data: lstData,
					rowStyler: function(index,row){
						if (row.pass == 0){
							return 'background-color:#D4E6E7;';
						}
					}
				});
				ygDialog({
					title : '导入结果',
					target : $('#importPanel'),
					width :  850,
					height : 'auto',
					buttons : [{
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function(dialog) {
							dialog.close();
						}
					}]
				});
				_fasDialogController.search();
				showSuc("操作成功!");
				return ;
			}
		}
	}
	showError("导入失败!");
}

/**
 * 导入
 * @param templateName  模版名称
 * @param url 导入url  
 * @param balanceType 结算类型
 * @param successFn 成功回调函数
 * @param errorFn 失败回调函数
 */
BillPrePaymentNt.prototype.doImport = function(templateName,url,successFn) {
	$.importExcel.open({
		'submitUrl' : BasePath + url,
		'templateName' : templateName,
		success : successFn
	});
};

(function($) {
	var defaults = {
		submitUrl : '',
		templateName : '',
		params : {},
		success:function(){
			
		},
		error:function(){
			
		}
	};

	var importExcel = {
		opts : {},
		dialog : null,
		open : function(o) {
			opts = o;
			var $uploadForm = null;
			var $errorInfo = null;
			dialog = ygDialog({
				isFrame: true,
				cache : false,
				title : '导入',
				modal:true,
				showMask: false,
				width:'400',
				height:'160',
				href : BasePath + "/common_util/toImport",
				buttons:[{
					text:'上传',
					iconCls:'icon-upload',
					handler:function(self){
						if($errorInfo.text().trim()!=""){
							return ;
						}
						$uploadForm.form('submit',{
							url : o.submitUrl,
							success:function(data){
								if(data){
									if(typeof o.success=="function"){
										o.success(data);
									}else{
										showInfo("导入成功!");
									}
								}else{
									if(typeof o.error=="function"){
										o.error(data);
									}else{
										showError("导入失败!");
									}
								}
								self.close();
							}
						});
					}
				},{
					text:'下载模板',
					iconCls:'icon-download',
					handler:function(){
						window.location.href=BasePath + '/common_util/download?fileName='+ o.templateName;
					}
				}],
				onLoad:function(win,dialog){
					$errorInfo = dialog.$('#errorInfo');
					$uploadForm = dialog.$('#uploadForm');
				}
			});
		},
		close : function(){
			dialog.panel('close');
		},
		success : function(){
			opts.success.call();
		},
		error : function(){
			opts.error.call();
		}
	};

	$.importExcel = function(options) {
		$.fn.importExcel.open(options);
	};

	$.importExcel.open = function(options) {
		var opts = $.extend({}, defaults, options);
		importExcel.open(opts);
	};

	$.importExcel.success = function(){
		return importExcel.success();
	};
	
	$.importExcel.error = function(){
		return importExcel.error();
	};
	
	$.importExcel.colse = function(){
		return importExcel.close();
	};
})(jQuery);

//根据公司和客户来控制 预收款类型  可选项
//function validChange(){
//	var companyNo =  $("#companyNo").val();
//	var customerNo = $("#customerNo").val();
//	if(companyNo != null &&　companyNo != "" && customerNo != null && customerNo != ""){
//		ajaxRequestAsync( BasePath + '/wholesale_rece_term/select_byCompanyNoAndCustomerNo.json',{companyNo:companyNo,customerNo:customerNo},function(data){
//			var obj = data.rows[0];
//			if(obj != null && obj != ''){
//				setTimeout(function(){
//					if(obj.controlType == 1){
//						$('#paidType').combobox('clear');
//						$('#paidType').combobox('loadData',_billPrePaymentNt.paidTypeData1);
//					}
//				},500);
//			}else {
//				$('#paidType').combobox('clear');
//				$('#paidType').combobox('loadData', _billPrePaymentNt.paidTypeData);
//			}
//		});
//	}
//}

$(function() {
	//初始化查询组件
	_billPrePaymentNt.initSearchBox();
	$('#saleOrderNo').combo('disable');
	$('#paidAmount').numberbox('disable');
	
	$('#paidType').combobox('clear');
	$('#paidType').combobox('loadData',_billPrePaymentNt.paidTypeData);
	$('#paidType').combobox({
		onSelect:function(rec){
			if(rec.code == '0'){
				$('#saleOrderNo').combogrid({
					required:true
				});
				$('#paidAmount').numberbox({
					required:true,
					precision:2,
					validType:'maxLength[12]'
				});
				$('#saleOrderNo').combo('validate');
				$('#saleOrderNo').combo('enable');
			}else{
				$('#saleOrderNo').combogrid({
					required:false
				});
				$('#paidAmount').numberbox({
					required:true,
					precision:2,
					min:-999999999999.99,
					validType:'maxLength[12]'
				});
            	$("#saleOrderNo").combogrid('setValue','');
    			$("#orderAmount").val('');
    			$("#orderAmount").val(0.00);
    			$("#termNo").val('');
    			$("#termName").val('');
				$('#saleOrderNo').combo('validate');
				$('#saleOrderNo').combo('disable');
			}
			$('#paidAmount').numberbox('setValue','');
			$('#paidAmount').numberbox('enable');
		}
	})
});