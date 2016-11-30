var hrefUrl = "";
var invoiceTypeStr = "";
var shopGroupNoStr = "";
var shopNoStr = "";

// 加载单据信息
function loadData(resultData) {
	$('#invoiceRegisterNo').val('');
	$('#mainDataForm').form('load', resultData);
	// 底部单据状态显示栏
	$('#status').html(resultData.status);
	$('#createUser').html(resultData.createUser);
	$('#createTime').html(resultData.createTime);
	$('#auditor').html(resultData.auditor);
	$('#auditTime').html(resultData.auditTime);
//	billBalanceInvoiceApply.disableMainInfo();
};

function printBalanceOrder(){
	var dgt=$("#invoiceApplyDataGrid").datagrid("getRows");
	if(dgt.length<=0){
		alert("列表数据位空!");
		return;
	}
	var row = $('#invoiceApplyDataGrid').datagrid('getSelections');
	if(row.length<1){
		alert("请至少选择一行数据进行导出！");
		return;
	}
	var printCol = [ {
		text : "华南标准",
		value : "HN1",
		label : 1
	}, {
		text : "华南拓展",
		value : "HN2",
		label : 2
	}];
	
	var invoiceApplyDataGridTemp =[		
	                            {field : 'shortName', title : '系统名称',width:160},
	                            {field : 'categoryName',title : '结算期',width:130},	
								{field : 'invoiceName', title : '开票名称',width:160},
								{field : 'brandName',title : '品牌',width:80},
								{field : 'qty',title : '数量','exportType':'number',width:60},
								{field : 'amount', title : '金额',width:60}, 
	   							{field : 'remark',title : '备注',width:100},
								{field : 'posEarningAmount',title : '终端收入金额','exportType':'number',width:100},
								{field : 'estimatedAmount',title : '预估成本','exportType':'number',width:60},
								{field : 'contractRate',title : '合同扣率','exportType':'number',width:80},
								{field : 'actualRate',title : '实际扣率',width:80},
								{field : 'costTotal',title : '成本',width:60},
								/*{field : 'remark',title : '备注',width:100},   */
								{field : 'fullName', title : '发票类型',width:110},
	                            {field : 'balanceTypeStr', title : '结算单类型',width:110},
	   							{field : 'invoiceApplyDate', title : '申请日期',width:110},
	   							{field : 'postPayDate', title : '交票日期',width:160},
	   							{field : 'currencyName', title : '币别',width:60},
	   							{field : 'taxRegistryNo', title : '纳税人识别号',width:110},
	   							{field : 'balanceNo', title : '结算单号',width:110},	
	   							{field : 'balanceStartDate',title : '结算开始时间',width:110},	
	   							{field : 'balanceEndDate',title : '结算结束时间',width:110},
	   							/*{field : 'shortName', title : '店铺简称',width:160},
	   							{field : 'fullName', title : '店铺名称',width:160},*/
	   							{field : 'organName',title : '管理城市',width:160},
	   							{field : 'taxRate',title : '税率',width:60},
	   							{field : 'taxAmount',title : '税额',width:60},
	   							{field : 'noTaxAmount',title : '不含税金额'}
	   			];
	
	var invoiceApplyDataGrid =[		 
		                           	{field : 'shortName', title : '系统名称',width:160},
		   							{field : 'categoryName',title : '结算期',width:130},	
		                           	{field : 'invoiceName', title : '开票名称',width:160},
		   							{field : 'brandName',title : '品牌',width:80},
		   							{field : 'qty',title : '数量','exportType':'number',width:60},
		   							{field : 'amount', title : '金额',width:80},    
		   							{field : 'remark',title : '备注',width:100},
		   							{field : 'posEarningAmount',title : '终端收入金额','exportType':'number',width:80},
		   							{field : 'estimatedAmount',title : '预估成本','exportType':'number',width:60},
		   							{field : 'contractRate',title : '合同扣率',width:80},
		   							{field : 'actualRate',title : '实际扣率',width:80},
		   							{field : 'costTotal',title : '成本',width:60}
		   	];
	 //初始化框架数据
	$('#printSelect').empty();
	$.each(printCol,function(index,item){
		$('#printForm #printSelect').append("<option value="+item.value+">"+item.text+"</option>");
	});
	ygDialog({
		title : '开票信息导出',
		target : $('#printContrPanel'),
		width : 225,
		height : 300,
		buttons : [{
					text : '导出',
					iconCls : 'icon-print',
					handler : function(dialog) {
						var selected = $('#printSelect').val();
						if(selected =="HN1"){
							exportExcelBasic(row,invoiceApplyDataGrid);
						}
						if(selected =="HN2"){
							exportExcelBasic(row,invoiceApplyDataGridTemp);
						}
						dialog.close();
					}
				},
				{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function(dialog) {
						dialog.close();
					}
				} ]
	});
}


function exportExcelBasic(row,invoiceApplyDataGridTemp){
		$("#exportExcelForm").remove();
	    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
		$('#exportExcelForm').form('submit', {
            url : BasePath + "/bill_balance_invoice_apply/export",
            onSubmit : function(params) {
            	params.billNo=$("#billNo").val();
            	params.salerNo=$("#salerNo_").val();
            	params.status=$("#statusId").val();
            	params.balanceType=$("#searchbalanceTypes").val();
            	params.balanceNo=$("#billBalanceNo").val();
            	params.invoiceApplyDateStart = $("#invoiceApplyDateStart").val();
            	params.invoiceApplyDateEnd = $("#invoiceApplyDateEnd").val();
            	params.exportColumns = JSON.stringify(invoiceApplyDataGridTemp);
            	params.fileName = "开票申请单明细信息";
            	params.dataList = JSON.stringify(row);
            	params.pageNumber = 1;
            	params.pageSize= 20;
            },
            success : function() {

            }
        });
}

// 单据列表页面相关的对象
function InvoiceApplyDialog() {
	var $this = this;
	this.clear = function() {
		$this.super.clear.call(this);
		$("#salerNo_").val("");
		$("#searchIsHQ").val($('#isHQ').val());
	};
	
	this.exportExcel=function() {
		
		printBalanceOrder();
		
	};
	// 删除操作
	this.doDel = function() {
		var checkedRows = $('#' + $this.options.dataGridId).datagrid('getChecked');
		var errorMessage = getBatchErrorMessage(checkedRows);
		if(errorMessage != "") {
			showInfo(errorMessage);
			return;
		}
		if(checkedRows.length < 1) {
			showWarn("请选择要删除选中的单据?");
			return;
		}

		for(var i = 0; i < checkedRows.length; i++) {
			var item = checkedRows[i];
			
			var checkExist=false;
			// 填充结算单号和原单类型
			$.ajax({
				  type: 'POST',
				  url: BasePath + '/bill_balance_invoice_apply/get_count.json',
				  data: {"billNo" : item.billNo},
				  cache: true,
				  async:false, // 一定要
				  success: function(resultData){
					  totalData = parseInt(resultData,10);
					  if(totalData == 0){
						  checkExist=true;
					  }
				  }
			});
			
			if(checkExist){
				showWarn("单据：" + item.billNo + "不存在,无法操作！");
				return false;
			}
			
			if(item.status != "1") {
				showWarn("单据：" + item.billNo + ",已审核确认不可删除！");
				return false;
			}
			
			if(item.useFlag == "1"){
				showWarn("单据：" + item.billNo + ",已使用不可删除！");
				return false;
			}
			if(billBalanceInvoiceApply.checkApplyBillNo(item.billNo)){
				showWarn(item.billNo+"已关联发票登记,无法删除!");
				return false;
			}
		}
		$.messager.confirm("确认", "你确定要删除选中的单据?", function(r) {
			if(r) {
				var url = BasePath + $this.modulePath + $this.options.delUrl;
				var idList = "";
				$.each(checkedRows, function(index, row) {
					idList += row.id + ',' + row.billNo + ',' + row.balanceType + ";";
				});
				var params = {idList : idList.substring(0, idList.length - 1)}; // {id:idList};//
				ajaxRequestAsync(url, params, function(count) {
					if(count) {
						showSuc('删除成功');
						$this.search();
					} else {
						showError('删除失败');
					}
				});
			}
		});
	};
	// 审核、反审核操作
	this.batchVerify = function(status) {// 批量 审核-作废
		var checkedRows = $('#' + $this.options.dataGridId).datagrid('getChecked');
		var errorMessage = getBatchErrorMessage(checkedRows, status);
		if(errorMessage != "") {
			showInfo(errorMessage);
			return;
		}
		
		if(checkedRows.length < 1) {
			showWarn("请选择要操作的单据?");
			return;
		}
		
		for(var i = 0; i < checkedRows.length; i++) {
			var item = checkedRows[i];
			
			var checkExist=false;
			// 填充结算单号和原单类型
			$.ajax({
				  type: 'POST',
				  url: BasePath + '/bill_balance_invoice_apply/get_count.json',
				  data: {"billNo" : item.billNo},
				  cache: true,
				  async:false, // 一定要
				  success: function(resultData){
					  totalData = parseInt(resultData,10);
					  if(totalData == 0){
						  checkExist=true;
					  }
				  }
			});
			
			if(checkExist){
				showWarn("单据：" + item.billNo + "不存在,无法操作！");
				return false;
			}
			
			if(status == 2 && billBalanceInvoiceApply.checkApplyDtlByBillNo(item.billNo)){
				showWarn(item.billNo+"单据没有明细信息,无法审核!");
				return false;
			}
			
			if(status == 1 && billBalanceInvoiceApply.checkApplyBillNo(item.billNo)){
				showWarn(item.billNo+"已关联发票登记,无法反审核!");
				return false;
			}
			
			if(status == 1 && item.useFlag == "1"){
				showWarn(item.billNo + "，已使用,无法反审核！");
				return false;
			}
		}
		
		var message = "", auditor = null, auditTime = null;
		if(status == 1) {
			message = "你确定要反审核该条单据?";
		} else if(status == 2) {
			message = "你确定要审核该条单据?";
			auditor = currentUser.username;
			auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
		} else if(status == 3) {
			message = "你确定要作废该条单据?";
		}
		$.messager.confirm("确认", message, function(r) {
			if(r) {
				$.each(checkedRows, function(index, row) {
					row.status = status;
					row.auditor = auditor;
					row.auditTime = auditTime;
				});
				var data = {updated : JSON.stringify(checkedRows)};
				ajaxRequestAsync(BasePath + $this.modulePath + '/save', data, function(result) {
					if(result) {
						showSuc('操作成功');
						$this.search();
					} else {
						showError('操作失败');
					}
				});
			}
		});
	};
	// 点击切换到明细
	this.loadDetail = function(rowIndex, rowData) {
		if(!rowData) {
			return;
		}
//		billBalanceInvoiceApply.curRowIndex = rowIndex;
		// 填充主表
		$.ajax({
			  type: 'POST',
			  url: BasePath + $this.modulePath + '/get',
			  data: {id : rowData.id},
			  cache: true,
			  async:false, // 一定要
			  success: function(resultData){
				  loadData(resultData);
			  }
		});
//		if("8" == rowData.balanceType){
//			// 填充地区团购单号和地区团购类型
//			$.ajax({
//				  type: 'POST',
//				  url: BasePath + '/bill_balance_invoice_apply/getOrderNoByApplyNo',
//				  data: {"billNo" : rowData.billNo},
//				  cache: true,
//				  async:false, // 一定要
//				  success: function(resultData){
//					  $("#searchbalanceType").combobox("setValue",rowData.balanceType);
//					  if(resultData){
//						  $("#balanceNoStr").val(resultData);
//					  }
//				  }
//			});
//		}else if("23" == rowData.balanceType){
//			$("#searchbalanceType").combobox("setValue",rowData.balanceType);
//			// 填充结算单号和原单类型
//			$.ajax({
//				  type: 'POST',
//				  url: BasePath + '/bill_balance_invoice_apply/getSaleOrderNoByApplyNo',
//				  data: {"billNo" : rowData.billNo},
//				  cache: true,
//				  async:false, // 一定要
//				  success: function(resultData){
//					  if(resultData){
//						  $("#balanceNoStr").val(resultData);
//					  }
//				  }
//			});

//		}else{
			// 填充结算单号和原单类型
			$.ajax({
				  type: 'POST',
				  url: BasePath + '/bill_balance_invoice_source/get_biz',
				  data: {"billNo" : rowData.billNo},
				  cache: true,
				  async:false, // 一定要
				  success: function(resultData){
					  if(resultData.length){
						  $("#searchbalanceType").combobox("setValue",resultData[0].balanceType);
						  var balanceNoStr = "";
						  for(var i = 0; i < resultData.length; i++){
							  if(resultData[i].balanceNo){
								  balanceNoStr += resultData[i].balanceNo+",";
							  }
						  }
						  $("#balanceNoStr").val(balanceNoStr.substring(0,balanceNoStr.length-1));
					  }
				  }
			});
			
			if("10" == $("#searchbalanceType").combobox("getValue")){
				$.ajax({
					  type: 'POST',
					  url: BasePath + '/mall_shopbalance/get_biz',
					  data: {"balanceNo" : rowData.balanceNo},
					  cache: true,
					  async:false, // 一定要
					  success: function(resultData){
						  if(resultData.length){
							  if("" != resultData[0].shopNo && null != resultData[0].shopNo){
								  shopNoStr = resultData[0].shopNo;
							  }
						  }
					  }
				});
			}
//		}
		$("#searchbalanceType").combo('hidePanel');
		// 按大类显示
		$.fas.search({
			hasSearchForm : false,
			dataGridId : "invoiceCateDataGrid",
			searchUrl : "/bill_balance_invoice_dtl/query_by_billNo?billNo="+rowData.billNo
			+"&companyNo="+rowData.salerNo+"&balanceType="+rowData.balanceType+"&balanceNos="+rowData.balanceNo
		});
		$("#searchbalanceType").combobox("disable");
		if(rowData.status == 1){
			InvoiceApplyAll();
		}else{
			disableAll();
			$('#invoiceApplyDate').datebox('enable');
			$("#invoiceApplyDate").removeAttr("readonly").removeClass("readonly");
		}
/*		if(rowData.status == 2) {
			$('#salerName').company('disable');
			$('#buyerName').company('disable');
			$('#invoiceApplyDate').datebox('disable');
			$('#postPayDate').datebox('disable');
			$("#invoiceType,#preInvoice,#currency").combobox("disable");
			$("#mainDataForm").find("input").attr("readonly", true).addClass("readonly");
			$("#mainDataForm").find("textarea").attr("readonly", true).addClass("readonly");
		} else {
			$("#invoiceType,#preInvoice,#currency").combobox("enable");
			$('#invoiceApplyDate').datebox('enable');
			$('#postPayDate').datebox('enable');
			$("#mainDataForm").find("input").removeAttr("readonly").removeClass("readonly");
			$("#mainDataForm").find("textarea").removeAttr("readonly").removeClass("readonly");
			$('#salerName').company('enable');
			$('#buyerName').company('enable');
		}*/
		returnTab('mainTab', '单据明细');
		returnTab('dtlTab', '基本信息');
		returnTab('invoiceAppDtlTab', '按大类显示');
		
		$('#dtlTab').tabs({
			onSelect : function(title) {
				if ('基本信息' == title) {
				
				} else if ('源单信息' == title) {
//					var balanceType = $("#searchbalanceType").combobox("getValue");
//					if("8" == balanceType){
//						$.fas.search({
//							hasSearchForm : false,
//							searchUrl : "/bill_balance_invoice_source/orderList.json?billNo="+$('#billNo').val(),
//							dataGridId : "invoiceSourceDataGrid"
//						});
//					}else{
						$.fas.search({
							hasSearchForm : false,
							searchUrl : "/bill_balance_invoice_source/list.json?billNo="+$('#billNo').val(),
							dataGridId : "invoiceSourceDataGrid"
						});
//					}
				} else if ('发票信息' == title) {
					$.fas.search({
						hasSearchForm : false,
						searchUrl : "/bill_balance_invoice_register/getInvoiceRegister?billNo="+$('#invoiceRegisterNo').val()+"&applyBillNo="+$('#billNo').val()+"&preInvoice=1",//+"&status=1",
						dataGridId : "invoiceInfoDataGrid"
					});
				}
			}
		});
		$('#invoiceAppDtlTab').tabs({
			onSelect : function(title) {
				if('按明细显示' == title) {
					var balanceNo = $("#balanceNoStr").val();
					var balanceType = $("#searchbalanceType").combobox("getValue");
					var searchUrl = "";
					if("8" == $("#searchbalanceType").combobox("getValue")){
						searchUrl = "/bill_balance_invoice_apply/getOrderBillBalanceByInvoiceNo?billNo="+$("#billNo").val()+"&businessTypeStr=3";
					}else{
						searchUrl = "/bill_sale_balance/list_by_balanceNo?balanceNos="+balanceNo+"&balanceType="+balanceType;
					}
					$.fas.search({
						hasSearchForm : false,
						searchUrl : searchUrl,
						dataGridId : "invoiceDtlDataGrid"
					});
				}
			}
		});
	};
}

/**
 * 币种格式化
 * @param v
 * @returns
 */
function currencyFormat  (v) {
	var combobox = $("#currencyName");
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

//基本信息editor
function InvoiceApplyBaseEditor() {
	var $this = this;
	this.insertRow = function() {
		if($("#statusName").val()=="制单"){
			$this.super.insertRow.call(this);
			//在输入开票金额时，设置开票总金额
			editSetAmount();
//			return checkEditorBtn();
		}else{
			alert("不是制单状态的申请不能新增");
		}
		
	};
	
	this.editRow = function(rowIndex, rowData){
		if($("#statusName").val()=="制单"){
			$this.super.editRow.call(this);
			//在输入开票金额时，设置开票总金额
			editSetAmount();
			return checkEditorBtn();
		}else{
			alert("不是制单状态的申请不能修改");
		}
	}
	this.deleteRow = function(rowIndex, rowData){
		if($("#statusName").val()=="制单"){
			$this.super.deleteRow.call(this);
			//删除时，重置金额
			setAmount()
			billBalanceInvoiceApply.getAllAmount();
			return checkEditorBtn();
		}else{
			alert("不是制单状态的申请不能删除");
		}
	}
	this.checkInsert = function(options) {
		return checkEditorBtn();
	};
	this.checkUpdate = function(options) {
		return checkEditorBtn();
	};
	this.checkDel = function(options) {
		return checkEditorBtn();
	};
	this.checkSave = function(options) {
		return checkEditorBtn();
	};
	this.saveCallback = function() {
		
	};
}

function setAmount(){// 设置总金额
	var rows = $("#invoiceCateDataGrid").datagrid('getRows');
	var allAmount = 0;
	
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	if(editIndex){
		var amountEd = $("#invoiceCateDataGrid").datagrid('getEditor',{index:editIndex,field:'sendAmount'});
		allAmount += parseFloat($(amountEd.target).numberbox('getValue'));
	}
	$.each(rows,function(index,item){
		var rowIndex = $("#invoiceCateDataGrid").datagrid('getRowIndex',item);
		if(editIndex!=rowIndex){
			allAmount += parseFloat(item.sendAmount);
		}
	});
	$("#amountStr").numberbox("setValue",allAmount.toFixed(2));
};

function editSetAmount(){// 开始明细行编辑
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	if(editIndex){
		var edAmount = $("#invoiceCateDataGrid").datagrid('getEditor',{index:editIndex,field:'sendAmount'});
		$(edAmount.target).bind('blur',function(){
			setAmount();
			billBalanceInvoiceApply.getAllAmount();
		});
	}
};

function endEdit(){// 结束明细行编辑
	
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	if(editIndex){
		return $('#invoiceCateDataGrid').datagrid('validateRow', editIndex);
	}
	return true;
};

function disableAll(){
	$('#salerName').company('disable');
	$('#buyerName').customerInvoiceInfo('disable');
	$('#invoiceApplyDate').datebox('disable');
	$('#postPayDate').datebox('disable');
	$("#invoiceType,#preInvoice,#currencyName").combobox("disable");
	$("#mainDataForm").find("input").attr("readonly", true).addClass("readonly");
	$("#mainDataForm").find("textarea").attr("readonly", true).addClass("readonly");
}

// 修改或修改大类行编辑列表时，触发的函数，用于判断不同的源单类型，显示不同的大类下拉框
function beforeAddAndUpdate() {
	var preInvoice = $("#preInvoice").combobox("getValue");
	var balanceType = $("#searchbalanceType").combobox("getValue");
	$("#invoiceCateDataGrid").datagrid("removeEditor", "categoryName");
	//$("#invoiceCateDataGrid").datagrid("removeEditor", "cateName");
	$("#invoiceCateDataGrid").datagrid("removeEditor", "estimatedAmount");
	$("#invoiceCateDataGrid").datagrid("removeEditor", "shortName");
	$("#invoiceCateDataGrid").datagrid("removeEditor", "organName");
	
	$("#invoiceCateDataGrid").datagrid("addEditor", {field : "shortName", 
		editor:{
			type:'shopbox',
			options:{
				id:'shortName',
				name:'shortName', 
				inputWidth:'130px',
				href : BasePath + '/plugin_page/searchShop?companyNo='+$("#salerNo").val(),
				readonly:true
		 	}
		}
	});
	
	$("#invoiceCateDataGrid").datagrid("addEditor", {field : "organName", 
		editor:{
			type:'combobox',
			options:{
		 		id:'organName',
		 		name:'organName',
		 		valueField : 'name',
       			textField : 'name',
				url : BasePath + '/organ/get_biz?organLevel=1',
		 		inputWidth:100,
		 		required:true,
		 		onSelect : function(data) {
		 			$('#organNo').val(data.organNo);
					$('#organName').val(data.name);
		 		}
		 	}
		}
	});
	if(preInvoice == "2"){
		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "estimatedAmount", 
			editor:{
				type:'numberbox',
				required:true
			}
		});
	}
	if(balanceType == '10') {
		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "categoryName", 
			editor:{
				type:'categorycombobox',
				options:{
			 		id:'categoryName',
			 		name:'categoryName',
			 		valueField : 'categoryNo',
	       			textField : 'categoryName',
					url : BasePath + '/invoice_template_set_dtl/categoryList.json?shopNo='+shopNoStr,
			 		inputWidth:100,
			 		required:true,
			 		onSelect : function(data) {
			 			$('#categoryNo').val(data.categoryNo);
						$('#categoryName').val(data.categoryName);
						$('#dtlSalerName').val(data.invoiceName);
			 		}
			 	}
			}
		});
	} else {
		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "categoryName", 
			editor:{
				type:'categorycombobox',
				options:{
			 		id:'categoryName',
			 		name:'categoryName',
			 		valueField : 'financialCategoryNo',
	       			textField : 'name',
					url : BasePath + '/financial_category/getAllCateInfo?companyNo='+$("#salerNo").val(),
			 		inputWidth:100,
			 		required:true,
			 		onSelect : function(data) {
			 			$('#categoryNo').val(data.financialCategoryNo);
						$('#categoryName').val(data.name);
						$('#dtlSalerName').val(data.name);
			 		}
			 	}
			}
		});
	}
}

// 发票信息editor
function InvoiceEditor() {
	this.checkInsert = function(options) {
		return checkEditorBtn();
	};
	this.checkUpdate = function(options) {
		return checkEditorBtn();
	};
	this.checkDel = function(options) {
		return checkEditorBtn();
	};
	this.checkSave = function(options) {
		return checkEditorBtn();
	};
}

//校验行编辑的按钮是否可用
function checkEditorBtn() {
	var billNo = $("#billNo").val();
	if(!billNo || billNo == '') {
		showWarn("请先保存表头数据！");
		return false;
	}
	var status = $('#status').val();
//	if(status == '2') {
//		showWarn("单据已审核，不能进行该操作！");
//		return false;
//	}
	return true;
}

// 单据明细对象
function InvoiceAppliBill() {
	var $this = this;
	// 新增单据
	this.add = function(tag) {
		returnTab('dtlTab', '基本信息');
		// 如果单据编码不为空，则点击新增按钮时，将大类明细数据清空
		if($("#billNo").val()) {
			$.fas.search({
				hasSearchForm : false,
				dataGridId : "invoiceCateDataGrid",
				searchUrl : "/bill_balance_invoice_dtl/query_by_billNo?billNo="
			});
		}
		$this.super.add.call(this);
		if(tag != "1"){
			$('#invoiceType').combobox('setValue', '1');
			$('#preInvoice').combobox('setValue', '1');
//			$('#currency').combobox('setValue', '0');
			$("#currencyName").combobox(
					{
						onLoadSuccess : function() {
							var currencies = $("#currencyName").combobox("getData");
							for ( var i = 0; i < currencies.length; i++) {
								if (currencies[i]["standardMoney"] == 1) {
									$("#currencyName").combobox("setValue",currencies[i]["currencyName"]);
									$("#currency").val(currencies[i]["currencyCode"]);
								}
							}
						}
					});
		}
		$("#status").val("");
		$("#billNo").attr("readonly", true).addClass("disabled");
		$("#brandName").attr("readonly", true).addClass("disabled");
		$('#salerName').company('enable');
		$('#buyerName').customerInvoiceInfo('enable');
		$('#balanceNoStr').iptSearch('disable');
		$("#invoiceType,#preInvoice,#searchbalanceType,#currencyName").combobox("enable");
		$('#invoiceApplyDate').datebox('enable');
		$('#postPayDate').datebox('enable');
		
		$('#status').html("");
		$('#createUser').html("");
		$('#createTime').html("");
		$('#auditor').html("");
		$('#auditTime').html("");
	};
	var isAdd = true;
	this.save = function() {
//		if($("#status").val() != ""){
			if($('#mainDataForm').form('validate')) {
				var amount = $("#amountStr").val();
				if("" != $("#searchbalanceType").combobox("getValue") && "" == $("#balanceNoStr").val()){
					if("2" == $("#searchbalanceType").combobox("getValue")){
						showWarn("请选择对应的源单信息！");
						return false;
					}
				}
				if($("#salerNo").val() == $("#buyerNo").val()){
					showWarn("开票方和收票方不能为相同主体,请重新选择！");
					return false;
				}
				if(amount == "" || parseFloat(amount) == 0){
					showWarn("开票金额为0！");
//					return false;
				}
				if("8" != $("#searchbalanceType").combobox("getValue")){
					if("" == $("#invoiceName").val()){
						showWarn("请完善开票信息维护，开票名称不能为空。");
						return false;
					}
				}
				var $this = this;
				$('#salerName').company('enable');
				if($('#id').val() != '') {
					// 校验方法
					$("#"+$this.options.dataFormId).form('submit', {
						url : $this.options.updateUrl,
						onSubmit : function(param) {
							param.status = 1;
							param.balanceType = $("#searchbalanceType").combobox("getValue");
						},
						success : function(result) {
							isAdd = false;
							$this.successUpdateFn(result, $this.options);
						}
					});
				} else {
					// 校验方法
					if(amount != "" && $("#balanceAmountStr").val() != "" 
					&& parseFloat($("#balanceAmountStr").val()) != parseFloat(amount)){
						$.messager.confirm("确认", "开票金额与源单金额不相等,是否继续保存?", function(r) {
							if(r) {
								$("#"+$this.options.dataFormId).form('submit', {
									url : $this.options.addUrl,
									onSubmit : function(param) {
										param.status = 1;
									},
									success : function(result) {
										isAdd = true;
										$this.successUpdateFn(result, $this.options);
									}
								});
							}
						});
					}else{
						$("#"+$this.options.dataFormId).form('submit', {
							url : $this.options.addUrl,
							onSubmit : function(param) {
								param.status = 1;
							},
							success : function(result) {
								isAdd = true;
								$this.successUpdateFn(result, $this.options);
							}
						});
					}
					
		/*			$("#"+$this.options.dataFormId).form('submit', {
						url : $this.options.addUrl,
						onSubmit : function(param) {
							param.status = 1;
						},
						success : function(result) {
							$this.successUpdateFn(result, $this.options);
						}
					});*/
				}
			}
//		}
//		else{
//			showWarn("该单据不是制单状态,无法进行操作！");
//			return false;
//		}
	}
	
	// 单据新增成功后的回调方法
	this.successAddFn = function(result, options) {
		$this.successSaveFn(result, options);
	};
	// 单据修改成功后的回调方法
	this.successUpdateFn = function(result, options) {
		$this.successSaveFn(result, options);
	};
	this.successSaveFn = function(result, options) {
		if(result) {
			var resultData = JSON.parse(result);
			var balanceTypeStr = $("#searchbalanceType").combobox("getValue");
			if(!resultData.errorInfo){
				showSuc('保存成功');
			}else if(resultData.errorInfo){
				showError(resultData.errorInfo);
				return ;
			}
			
			loadData(resultData);
//			if("8" != balanceTypeStr){
				// 填充结算单号和原单类型
				$.ajax({
					  type: 'POST',
					  url: BasePath + '/bill_balance_invoice_source/get_biz',
					  data: {"billNo" : $("#billNo").val()},
					  cache: true,
					  async:false, // 一定要
					  success: function(resultData){
						  if(resultData.length){
							  $("#searchbalanceType").combobox("setValue",resultData[0].balanceType);
							  $("#searchbalanceType").combo('hidePanel');
							  var balanceNoStr = "";
							  for(var i = 0; i < resultData.length; i++){
								  if(null != resultData[i].balanceNo){
									  balanceNoStr += resultData[i].balanceNo+",";
								  }
							  }
							  if("" != balanceNoStr){
								  $("#balanceNoStr").val(balanceNoStr.substring(0,balanceNoStr.length-1));
							  }
						  }
/*						  if(resultData){
							  $("#searchbalanceType").combobox("setValue",resultData.balanceType);
							  $("#balanceNoStr").val(resultData.balanceNo);
						  }*/
					  }
				});
//			}
//			if("8" == balanceTypeStr){
//				// 填充地区团购单号和地区团购类型
//				$.ajax({
//					  type: 'POST',
//					  url: BasePath + '/bill_balance_invoice_apply/getOrderNoByApplyNo',
//					  data: {"billNo" : $("#billNo").val()},
//					  cache: true,
//					  async:false, // 一定要
//					  success: function(resultData){
//						  $("#searchbalanceType").combobox("setValue","8");
//						  $("#searchbalanceType").combo('hidePanel');
//						  if(resultData){
//							  //$("#searchbalanceType").combobox("setValue","8");
//							  $("#balanceNoStr").val(resultData);
//						  }
//					  }
//				});
//			}
			$('#balanceNoStr').iptSearch('disable');
			$("#searchbalanceType").combobox("disable");
			if(!isAdd) {
				editor.save();
			}
			if(resultData.status == 1){
				InvoiceApplyAll();
			}else{
				disableAll();
			}
			setTimeout(function() {
				$.fas.search({
					hasSearchForm : false,
					dataGridId : "invoiceCateDataGrid",
					searchUrl : "/bill_balance_invoice_dtl/query_by_billNo?billNo="+resultData.billNo
					+"&companyNo="+resultData.salerNo+"&balanceType="+$("#searchbalanceType").combobox("getValue")+"&balanceNos="+$("#balanceNoStr").val()
				});
			}, 500);
			
			$('#invoiceAppDtlTab').tabs({
				onSelect : function(title) {
					if('按明细显示' == title) {
						var balanceNo = $("#balanceNoStr").val();
						var balanceType = $("#searchbalanceType").combobox("getValue");
						var searchUrl = "";
						if("8" == $("#searchbalanceType").combobox("getValue")){
							searchUrl = "/bill_balance_invoice_apply/getOrderBillBalanceByInvoiceNo?billNo="+$("#billNo").val()+"&businessTypeStr=3";
						}else{
							searchUrl = "/bill_sale_balance/list_by_balanceNo?balanceNos="+balanceNo+"&balanceType="+balanceType;
						}
						$.fas.search({
							hasSearchForm : false,
							searchUrl : searchUrl,
							dataGridId : "invoiceDtlDataGrid"
						});
					}
				}
			});
			
			$.fas.search({
				hasSearchForm : false,
				searchUrl : "/bill_balance_invoice_source/list.json?billNo="+$('#billNo').val(),
				dataGridId : "invoiceSourceDataGrid"
			});
			$.fas.search({
				hasSearchForm : false,
				searchUrl : "/bill_balance_invoice_register/getInvoiceRegister?billNo="+$('#invoiceRegisterNo').val()+"&applyBillNo="+$('#billNo').val()+"&preInvoice=1",//+"&status=1",
				dataGridId : "invoiceInfoDataGrid"
			});
		} else {
			showError('保存失败');
		}
	}
}

var dialog = null, editor = null, bill = null, invoiceEditor = null;
$(function() {
	$.fas.extend(InvoiceApplyDialog, FasDialogController);
	$.fas.extend(InvoiceApplyBaseEditor, FasEditorController);
	$.fas.extend(InvoiceAppliBill, FasBillController);
	$.fas.extend(InvoiceEditor, FasEditorController);
	
	dialog = new InvoiceApplyDialog();
	// 初始化InvoiceApplyDialog对象
	dialog.init("/bill_balance_invoice_apply", {
		dataGridId : "invoiceApplyDataGrid",
		searchFormId : "searchForm",
		searchUrl : "/apply_list.json",
		delUrl : "/deleteInvoiceApply",
		exportTitle : "开票申请单信息",
		exportUrl : "/do_fas_export"
	});
	// 初始化基本信息页签中的editor对象
	//var invoiceTypeStr = $("#invoiceType").combobox("getValue");
	editor = new InvoiceApplyBaseEditor();
	editor.init("/bill_balance_invoice_apply", {
		dataGridDivId : 'invoiceCateDataGridDiv',
		dataGridId : 'invoiceCateDataGrid',
		saveUrl : "/bill_balance_invoice_dtl/save_all",
		beforeAdd : beforeAddAndUpdate,
		beforeUpdate : beforeAddAndUpdate,
		buildAddData : function() {
			if("0" == invoiceTypeStr){
				return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val(), balanceType : $("#searchbalanceType").combobox("getValue")}
			}else{
				return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val(), balanceType : $("#searchbalanceType").combobox("getValue"),  taxRate : '0.17'}
			}
		},
		buildUpdateData : function() {
			if("0" == invoiceTypeStr){
				return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val(), balanceType : $("#searchbalanceType").combobox("getValue")}
			}else{
				return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val(), balanceType : $("#searchbalanceType").combobox("getValue"),  taxRate : '0.17'}
			}
		}
	});
	// 初始化发票信息页签中的editor对象
	invoiceEditor = new InvoiceEditor();
	invoiceEditor.init("/bill_balance_invoice_info", {
		dataGridDivId : 'invoiceInfoDataGridDiv',
		dataGridId : 'invoiceInfoDataGrid',
		saveUrl : "/bill_balance_invoice_info/save",
		buildAddData : function() {
			return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val()}
		},
		buildUpdateData : function() {
			return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val()}
		}
	});
	// 初始化单据明细对象
	bill = new InvoiceAppliBill();
	bill.init("/bill_balance_invoice_apply", {
		dataFormId : "mainDataForm",
		addUrl : BasePath + '/bill_balance_invoice_apply/saveOrUpdate',
		updateUrl : BasePath + '/bill_balance_invoice_apply/saveOrUpdate'
	});
	
	// 初始化发票类型下拉框
	$('#invoiceType').initCombox({
		data : [{"value" : "0", "text" : "普通发票"}, {"value" : "1", "text" : "增值票"}],
		width : 150,
		onChange : function(newValue, oldValue) {
			invoiceTypeStr = newValue;
		}
	});
	//var balanceNoStrList = "";
	// 初始化是否预开票下拉框
	$('#preInvoice').initCombox({
		data : [{"value" : "1", "text" : "否"}, {"value" : "2", "text" : "是"}],
		width : 150,
		onChange : function(newValue, oldValue) {
			if(newValue == '2') {
				$('#balanceNoStr').iptSearch('disable');
				$("#balanceNoStr").val("");
				$("#balanceAmountStr").val("");
			}else{
				$('#balanceNoStr').iptSearch('enable');
			}/*else if(newValue == '1' && balanceNoStrList != '23'){
				$('#balanceNoStr').iptSearch('enable');
			}*/
	    	$('#balanceNoStr').val("");
		}
	});
	$("#currencyName").combobox(
			{
				onLoadSuccess : function() {
					var currencies = $("#currencyName").combobox("getData");
					for ( var i = 0; i < currencies.length; i++) {
						if (currencies[i]["standardMoney"] == 1) {
							$("#currencyName").combobox("setValue",currencies[i]["currencyName"]);
							$("#currency").val(currencies[i]["currencyCode"]);
						}
					}
				}
			});
	// 初始化是否预开票下拉框
	if($('#isHQ').val() == 'true'){
		balanceTypeStatus = [ {'value' : '2','text' : '总部地区结算'},{'value' : '7','text' : '批发结算'},{'value' : '14','text' : '总部其他出库结算'}];
	}
	$('#searchbalanceType').initCombox({
		data : balanceTypeStatus,
		required:true,
		width : 150,
		onChange : function(newValue, oldValue) {
			if($("#status").val() == '2') {
				$('#balanceNoStr').iptSearch('disable');
			}else if($("#preInvoice").combobox("getValue") == "2"){
				$('#balanceNoStr').iptSearch('disable');
			}else{
				$('#balanceNoStr').iptSearch('enable');
			}
	    	$('#balanceNoStr').val("");
	    	//balanceNoStrList = newValue;
	    	if("10" == newValue){
	    		hrefUrl = BasePath + '/bill_balance_invoice_apply/toSearchBillShopBalance';
	    	}else if("8" == newValue){
	    		hrefUrl = BasePath + '/bill_balance_invoice_apply/toSearchOrderBillBalance?businessTypeStr=3';
//	    	}else if("23" == newValue){
//	    		hrefUrl = BasePath + '/bill_sale_balance/tosearchSaleOrderBill';
	    	}else{
	    		hrefUrl = BasePath + '/bill_balance_invoice_apply/toSearchBillBalance?invoiceType='+newValue;
	    	}
		}
	});
	
	addMainTab();
	ajaxRequestAsync(BasePath + '/common_util/getCurrentUser', null, function(
			data) {
		currentUser = data;
	});
	$('#mainDataForm').find('input[name=currentUser]').val(currentUser);
	$('#mainTab').tabs('hideHeader');
	$('#searchForm').find('input[name=balanceType]').val(
			$('#balanceType').val());
	
	billBalanceInvoiceApply.loadBillBalance();
	billBalanceInvoiceApply.loadShowType();
//	billBalanceInvoiceApply.loadCustomer();
	
	//以超链接方式直接访问详细页面
	var isHQ = $('#isHQ').val();
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/bill_balance_invoice_apply/apply_list.json',{billNo:billNoMenu,isHQ:isHQ},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					dialog.loadDetail(0,obj);
				}, 500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	} else{
		setTimeout(function() {
			returnTab('mainTab', '单据查询');
		}, 500);
		$('#balanceNoStr').iptSearch('disable');
		$("#searchbalanceType").combobox("disable");
		disableAll();
		bill.add();
		$("#balanceNoStr").attr("readonly", true).addClass("disabled");
	}
	$("#statusName").attr("readonly", true).addClass("disabled");
//	$("#invoiceName").attr("readonly", true).addClass("disabled");
});

// 单据查询当前选中行的索引
// billBalanceInvoiceApply.curRowIndex = -1;

var currentUser;

var nullMessage = "不存在当前单据";
var nullCheckMessage = "请选中需要操作的单据!";
var auditMessage = "只允许操作审核制单状态的单据";
var backMessage = "只允许反审核已确认状态的单据!";
var invalidMessage = "只允许作废已审核确认状态的单据!";// 只允许操作作废确认状态的单据
var deleteMessage = "只允许操作删除制单状态的单据!";
var audMessage = "确定要审核？";
// 单据状态(1-制单、2-确认、3-作废)
function getErrorMessage(currStatus, operStatus) {
	if (currStatus && currStatus == "") {
		return nullMessage;
	}
	if (typeof operStatus == 'undefined') {
		if (currStatus != 1) {
			return deleteMessage;
		}
	}
	if (operStatus == 2) {
		if (currStatus != 1) {
			return auditMessage;
		}
	} else if (operStatus == 3) {
		if (currStatus != 2) {
			return backMessage;
		}
	} else if (operStatus == 1) {
		if (currStatus != 2) {
			return backMessage;
		}
	}
	return "";
}

function getBatchErrorMessage(checkRows, operStatus) {
	if (checkRows.length == 0) {
		return nullCheckMessage;
	}
	var errorMessage = "";
	$.each(checkRows, function(index, item) {
		errorMessage = getErrorMessage(item.status, operStatus);
		if (errorMessage != "") {
			return false;
		}
	});
	return errorMessage;
}

var balanceTypeStatus = [{'value' : '5','text' : '地区间结算'},{'value' : '7','text' : '批发结算'},{'value' : '8','text' : '内购结算'},
		                    {'value' : '10','text' : '地区门店结算'},{'value' : '11','text' : '地区其他出库结算'}/*,{'value' : '12','text' : '独立店铺结算'},
		                    {'value' : '23','text' : 'GMS团购/内购结算'} */];

var showTypeStr = [ {
	'value' : '1',
	'text' : '品牌'
}, {
	'value' : '2',
	'text' : '大类'
}, {
	'value' : '3',
	'text' : '品牌和大类'
}];

var billBalanceInvoiceApply = {};

var statusformatter = [{'value' : '1','text' : '制单'}, {'value' : '2','text' : '确认'}, {'value' : '3','text' : '已开票'}];
billBalanceInvoiceApply.statusformatter = function(value, rowData, rowIndex) {
	if(value == 0) {
		return;
	}
	for(var i = 0; i < statusformatter.length; i++) {
		if (statusformatter[i].value == value) {
			return statusformatter[i].text;
		}
	}
};

function addMainTab() {
	if ($('#mainTab') != 'undefined' && $('#mainTab') !== null) {
		var url =  BasePath + '/invoice_apply/area/bill_balance_invoice_list';
		if($('#isHQ').val() == 'true'){
			url =  BasePath + '/invoice_apply/hq/bill_balance_invoice_list';
			balanceTypeStatus = [ {'value' : '2','text' : '总部地区结算'},{'value' : '7','text' : '批发结算'},{'value' : '14','text' : '总部其他出库结算'}];
		}
		
		$('#mainTab').tabs('add',{
			title : '单据查询',
			selected : false,
			closable : false,
			href :  url,
			onLoad : function(panel) {
				$('#searchbalanceTypes').combobox({
					data : balanceTypeStatus,
					valueField : 'value',
					textField : 'text',
					editable : false
				}); 
				$('#statusId').combobox({
					data : statusformatter,
					valueField : 'value',
					textField : 'text',
					editable : false
				}); 
				$("#searchIsHQ").val($('#isHQ').val());
				//$('#searchForm').find('input[name=balanceType]').val($('#balanceType').val());
//						fas_util.initIptFormSearch('searchForm');
//						fas_util.initIptAllSearchWidth();
				// 这里需要在重写在加载完后做对应的事件
			}
		});
	}
};

billBalanceInvoiceApply.exportInvoicDdtl=function(){
	$.fas.exportExcel({
	    dataGridId : "invoiceCateDataGrid",
	    exportUrl : "/bill_balance_invoice_apply/exportInvoicDdtl?billNo="+$("#billNo").val()+"&showType="+$("#showType").combobox("getValue"),
	    exportTitle : "开票申请单明细信息",
	    exportType : "fix"
	});
};

billBalanceInvoiceApply.loadShowType=function(){
	$('#showType').combobox({
		data : showTypeStr,
		valueField : 'value',
		textField : 'text',
		editable : false,
		onChange : function(newValue, oldValue) {
			var billNo = $("#billNo").val();
			var companyNo = $("#salerNo").val();
			var balanceType = $("#searchbalanceType").val();
			var balanceNos = $("#balanceNoStr").val();
	    	if("1" == newValue){
	    		// 按大类显示
	    		$.fas.search({
	    			hasSearchForm : false,
	    			dataGridId : "invoiceCateDataGrid",
	    			searchUrl : "/bill_balance_invoice_dtl/queryInvoiceDtlGroupByParams?billNo="
	    				+billNo+"&groupBy=brand_no&companyNo="+companyNo+"&balanceType="+balanceType+"&balanceNos="+balanceNos
	    		});
	    	}else if("2" == newValue){
	    		// 按大类显示
	    		$.fas.search({
	    			hasSearchForm : false,
	    			dataGridId : "invoiceCateDataGrid",
	    			searchUrl : "/bill_balance_invoice_dtl/queryInvoiceDtlGroupByParams?billNo="
	    				+billNo+"&groupBy=category_no&companyNo="+companyNo+"&balanceType="+balanceType+"&balanceNos="+balanceNos
	    		});
	    	}else if("3" == newValue){
	    		// 按大类显示
	    		$.fas.search({
	    			hasSearchForm : false,
	    			dataGridId : "invoiceCateDataGrid",
	    			searchUrl : "/bill_balance_invoice_dtl/queryInvoiceDtlGroupByParams?billNo="
	    				+billNo+"&groupBy=brand_no,category_no&companyNo="+companyNo+"&balanceType="+balanceType+"&balanceNos="+balanceNos
	    		});
	    	}
		}
	}); 
};

billBalanceInvoiceApply.loadBillBalance=function(){
	$("#balanceNoStr").iptSearch({
		disabled : false,
		readonly : true,
		width : 125,
		clickFn : function() {
			if("8" == $("#searchbalanceType").combobox("getValue")){
				ygDialog({
					title : "选择原订单",
					href : hrefUrl,
					width : 750,
					height : 500,
					isFrame : true,
					modal : true,
					showMask : true,
					onLoad : function(win, content) {
						var _this = $(this);
						$("#btn-sure", _this.contents()).on("click",function() {
							var checkedRows = content.multiSearchDatas();
							var companyNo = content.searchCompanyNo;
							var companyName = content.companyName;
							var dataNos = "", dataTemps = "";
							var allAmount = 0;
							var organNo = "",organName = "";
							var month = "";
							var brandNo = "", brandName = "";
							$.each(checkedRows,function(index,item) {
								allAmount +=parseFloat(item["amount"]);
								organNo = item["organNo"];
								organName = item["organName"];
								var temp_year1=item["balanceDate"].substring(0,4) ;
							    var temp_month1=item["balanceDate"].substring(5,7);
							    month = temp_year1+temp_month1;
								if(dataTemps != item["billNo"]){
									dataTemps = item["billNo"];
									if("" != dataNos){
										dataNos += ",";
									}
									dataNos += item["billNo"];
								}
								if(brandNo.indexOf(item["brandNo"]) < 0){
									if("" != brandNo){
										brandNo += ",";
									}
									brandNo += item["brandNo"];
									if("" != brandName){
										brandName += ",";
									}
									brandName += item["brandName"];
								}
							});
							$("#checkRows").val(JSON.stringify(checkedRows));
							$("#balanceNoStr").val(dataNos);
							$("#salerName").company("setValue",companyName.value);
							$("#salerNo").val(companyNo.value);
							//$("#amountStr").val(allAmount);
							$("#balanceAmountStr").val(allAmount);
							$("#amountStr").numberbox("setValue",allAmount);
							
							$("#organNoId").val(organNo);
							$("#organNameId").val(organName);
							$("#month").val(month);
							$("#brandName").val(brandName);
							$("#brandNo").val(brandNo);
							//获取公司信息
/*							url = BasePath + "/base_setting/company/get";
							var checkDataNo={"companyNo":companyNo.value};
						 	$.ajax({
								  type: 'POST',
								  url: url,
								  data: checkDataNo,
								  cache: true,
								  async:false, // 一定要
								  success: function(totalData){
									  if(totalData){
										  billBalanceInvoiceApply.setDtlInfoDataGrid(totalData);
									  }
								  }
						   });*/
						 	billBalanceInvoiceApply.setBillBalanceInvoiceName(companyNo.value, "");
							win.close();
							$('#salerName').company('disable');
						});
						$("#btn-cancel", _this.contents()).on("click", function() {
									win.close();
						});
					}
				});
			}else{
				dgSelector({
					title : '选择结算单',
					href : hrefUrl,
					width : 500,
					height : 500,
					fn : function(data) {
						var url = "";
						var url1 = "";
						if("7" == $("#searchbalanceType").combobox("getValue")){
							$("#balanceNoStr").val(data.billNo);
							$("#balanceAmountStr").val(data.balanceAmount);
							$("#amountStr").numberbox("setValue", data.balanceAmount);
							$("#salerName").company("setValue",data.salerName);
							$("#salerNo").val(data.salerNo);
							$("#buyerName").customerInvoiceInfo('setValue',data.buyerName);
							$("#buyerNo").val(data.buyerNo);
							$("#organNoId").val(data.organNoFrom);
							$("#organNameId").val(data.organNameFrom);
							var temp_year1=data.balanceEndDate.substring(0,4) ;
						     var temp_month1=data.balanceEndDate.substring(5,7);
							$("#month").val(temp_year1+temp_month1);
							//获取客户信息
							//url = BasePath + "/base_setting/customer/get";
							//url1 = BasePath + "/bill_balance/list.json";
							//billBalanceInvoiceApply.loadDtlInfoDataGridByCustomerNo(url, url1, data);
							billBalanceInvoiceApply.setBillBalanceInvoiceName(data.salerNo, data.buyerNo);
						}else if("10" == $("#searchbalanceType").combobox("getValue")){
							$("#balanceNoStr").val(data.balanceNo);
							$("#balanceAmountStr").val(data.mallBillingAmount);
							$("#amountStr").numberbox("setValue",data.mallBillingAmount);
							$("#salerName").company("setValue",data.companyName);
							$("#salerNo").val(data.companyNo);
							$("#systemSalesAmount").val(data.systemSalesAmount);
							$("#organNoId").val(data.organNo);
							$("#organNameId").val(data.organName);
							$("#month").val(data.month);
							$("#brandName").val(data.brandName);
							$("#brandNo").val(data.brandNo);
							var buyerNo = "";
							//先根据店铺编号，获取店铺开票规则信息
						 	shopNoStr = data.shopNo;
						 	url = BasePath + "/shop/findShopGroup";
							var checkDataNo={"shopNo":data.shopNo,"compnayNo":data.companyNo};
						 	$.ajax({
								  type: 'POST',
								  url: url,
								  data: checkDataNo,
								  cache: true,
								  async:false, // 一定要
								  success: function(totalData){
									  if(totalData){
										  if(totalData.clientNo){
											  buyerNo = totalData.clientNo;
//											  $("#buyerNo").val(totalData.clientNo);
//											  $("#buyerName").customerInvoiceInfo('setValue',totalData.clientName);
										  }
									  }
								  }
						   });
							
							//如果店铺开票规则为空，则根据商场编号获取开票信息
							if(buyerNo == "" && typeof data.mallNo != "undefined" &&  data.mallNo != ""){
								buyerNo = data.mallNo ;
//								$("#buyerNo").val(data.mallNo);
//								$("#buyerName").customerInvoiceInfo('setValue',data.mallName);
							}
							// 如果没有商场，则取商业集团
							if(buyerNo == "" && typeof data.bsgroupsNo != "undefined" &&  data.bsgroupsNo != ""){
								buyerNo = data.bsgroupsNo ;
//								$("#buyerNo").val(data.bsgroupsNo);
//								$("#buyerName").customerInvoiceInfo('setValue',data.bsgroupsName);
								
							}
							// 如果商业集团不为空，则调用使用商场编号或商业集团编号查询开票信息的方法
							if(typeof data.bsgroupsNo != "undefined" &&  data.bsgroupsNo != ""){
								billBalanceInvoiceApply.setBillBalanceInvoiceNameByMallNo($("#salerNo").val(), buyerNo,data.bsgroupsNo);
							}else{// 反之，则只需要调用根据公司及客户查询开票信息的方法即可
								billBalanceInvoiceApply.setBillBalanceInvoiceName($("#salerNo").val(), buyerNo);
							}
						}else{
							$("#balanceNoStr").val(data.billNo);
							$("#balanceAmountStr").val(data.balanceAmount);
							$("#amountStr").numberbox("setValue",data.balanceAmount);
							$("#salerName").company("setValue",data.salerName);
							$("#salerNo").val(data.salerNo);
							$("#buyerName").customerInvoiceInfo('setValue',data.buyerName);
							$("#buyerNo").val(data.buyerNo);
							//获取公司信息
							//url = BasePath + "/base_setting/company/get";
							//url1 = "";
							//billBalanceInvoiceApply.loadDtlInfoDataGridByCompanyNo(url, url1, data);
							billBalanceInvoiceApply.setBillBalanceInvoiceName(data.salerNo, data.buyerNo);
						}
					}
				});
			}
		}
	});
};

//选择开票方时的处理
billBalanceInvoiceApply.selectCompany=function(data){
	if(data){
		var clientNo = $("#buyerNo").val();
		billBalanceInvoiceApply.setBillBalanceInvoiceName(data.companyNo, clientNo);
	}
}

//选择收票方时的处理
billBalanceInvoiceApply.loadCustomer=function(data){
	if(data){
		$("#buyerName").val(data.clientName);
		$("#buyerNo").val(data.clientNo);
		billBalanceInvoiceApply.setBillBalanceInvoiceName($("#salerNo").val(), data.clientNo,data.status,data.invoiceName);
		$("#invoiceInfoStatus").val(data.status);
	}
}


billBalanceInvoiceApply.loadDtlInfoDataGridByCustomerNo = function(url, url1, rowData) {
	var checkDataNo={"customerNo":rowData.buyerNo};
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkDataNo,
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  if(resultData){
				  billBalanceInvoiceApply.setDtlInfoDataGrid(resultData);
			  }
		  }
   });
};

billBalanceInvoiceApply.loadDtlInfoDataGridByCompanyNo = function(url, url1, rowData) {
	var checkDataNo={"companyNo":rowData.salerNo};
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkDataNo,
		  cache: true,
		  async:false, // 一定要
		  success: function(totalData){
			  if(totalData){
				  billBalanceInvoiceApply.setDtlInfoDataGrid(totalData);
			  }
		  }
   });
};


billBalanceInvoiceApply.loadDtlInfoDataGridByShopNo = function(url, url1, rowData) {
	var shopNo = {"shopNo":rowData.shopNo};
 	$.ajax({
		  type: 'POST',
		  url: url1,
		  data: shopNo,
		  cache: true,
		  async:false, // 一定要
		  success: function(totalData){
			  if(totalData){
				  if(null != totalData.shopGroupNo){
					  billBalanceInvoiceApply.getInvoiceRule(totalData);
				  }else{
				 	$.ajax({
						  type: 'POST',
						  url: BasePath + "/base_setting/company/getCompanyByShopNo",
						  data: shopNo,
						  cache: true,
						  async:false, // 一定要
						  success: function(totalDatas){
							  if(totalDatas){
								  if(null != totalDatas.companyNo){
									  billBalanceInvoiceApply.setDtlInfoDataGrid(totalDatas);
								  }
							  }
						  }
				   });
				  }
			  }
		  }
 	});
/*	var checkDataNo={"shopNo":rowData.shopNo};
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkDataNo,
		  cache: true,
		  async:false, // 一定要
		  success: function(totalData){
			  if(totalData){
				  billBalanceInvoiceApply.getInfo(totalData);
			  }
		  }
   });*/
};

billBalanceInvoiceApply.getInvoiceRule = function(rowData){
	var shopGroupNo = {"shopGroupNo":rowData.shopGroupNo};
	shopGroupNoStr = shopGroupNo;
 	$.ajax({
		  type: 'POST',
		  url: BasePath + "/base_setting/invoice_info_set/getInvoiceRuleByShopGroupNo",
		  data: shopGroupNo,
		  cache: true,
		  async:false, // 一定要
		  success: function(totalData){
			  if(totalData){
				  if(null != totalData.invoiceRuleNo){
					  billBalanceInvoiceApply.setDtlInfoDataGrids(totalData);
				  }
			  }
		  }
 });
}

/*billBalanceInvoiceApply.getInfo = function(rowData) {
	var checkDataNo={"invoiceRuleNo":rowData.invoiceRuleNo};
 	$.ajax({
		  type: 'POST',
		  url: BasePath + "/invoice_rule_set/get",
		  data: checkDataNo,
		  cache: true,
		  async:false, // 一定要
		  success: function(totalData){
			  if(totalData){
				  billBalanceInvoiceApply.setDtlInfoDataGrids(totalData);
			  }
		  }
   });
};*/

billBalanceInvoiceApply.formatterDate = function(value){
	return new Date(value).format("yyyy-MM-dd");
}

//商场结算时，先根据公司及客户（开票规则的客户或商场编号）获取开票名称，如果都没有维护开票信息，则根据商业集团编号获取
billBalanceInvoiceApply.setBillBalanceInvoiceNameByMallNo = function(companyNo, customerNo,bsgroupsNo) {
	billBalanceInvoiceApply.clearInvoiceInfo();
	var dataLength = 0 ;
	if("" != companyNo && "" != customerNo){
		var checkDataNo={"companyNo":companyNo,"clientNo":customerNo};
	 	$.ajax({
			  type: 'POST',
			  url: BasePath + "/base_setting/invoice_info_set/get_biz",
			  data: checkDataNo,
			  cache: true,
			  async:false, // 一定要
			  success: function(totalData){
				  if(totalData.length){
					  dataLength = totalData.length;
					  var invoiceInfo = null;
					  //返回多笔开票信息时，优先取首选的那笔记录
					  for(var i=0; i<totalData.length;i++){
						  if(totalData[i].status == 1){
							  invoiceInfo = totalData[i];
							  break;
						  }
					  }
					  if(null == invoiceInfo){
						  invoiceInfo = totalData[0];
					  }
					  billBalanceInvoiceApply.setInvoiceInfo(invoiceInfo);
				  }
			  }
	 	});
	}
	//如果返回数据长度为0，则表示根据商场编号查询开票信息，没有返回结果，需要再根据商业集团编号查询开票信息
	if(dataLength == 0){
		var checkDataNo={"companyNo":companyNo,"clientNo":bsgroupsNo};
	 	$.ajax({
			  type: 'POST',
			  url: BasePath + "/base_setting/invoice_info_set/get_biz",
			  data: checkDataNo,
			  cache: true,
			  async:false, // 一定要
			  success: function(totalData){
				  if(totalData.length){
					  var invoiceInfo = null;
					  //返回多笔开票信息时，优先取首选的那笔记录
					  for(var i=0; i<totalData.length;i++){
						  if(totalData[i].status == 1){
							  invoiceInfo = totalData[i];
							  break;
						  }
					  }
					  if(null == invoiceInfo){
						  invoiceInfo = totalData[0];
					  }
					  billBalanceInvoiceApply.setInvoiceInfo(invoiceInfo);
				  }
			  }
	 	});
	}
};


//根据公司和客户获取开票名称
billBalanceInvoiceApply.setBillBalanceInvoiceName = function(companyNo, customerNo,status,invoiceName) {
	billBalanceInvoiceApply.clearInvoiceInfo();
	if("" != companyNo && "" != customerNo){
		if(status!=undefined){
			var checkDataNo={"companyNo":companyNo,"clientNo":customerNo,"status":status,"invoiceName":invoiceName};
		}else{
			var checkDataNo={"companyNo":companyNo,"clientNo":customerNo};
		}
	 	$.ajax({
			  type: 'POST',
			  url: BasePath + "/base_setting/invoice_info_set/get_biz",
			  data: checkDataNo,
			  cache: true,
			  async:false, // 一定要
			  success: function(totalData){
				  if(totalData.length){
					  if(status == undefined){
						  var invoiceInfo = null;
						  //返回多笔开票信息时，优先取首选的那笔记录
						  for(var i=0; i<totalData.length;i++){
							  if(totalData[i].status == 1){
								  invoiceInfo = totalData[i];
								  break;
							  }
						  }
						  if(null == invoiceInfo){
							  invoiceInfo = totalData[0];
						  }
						  billBalanceInvoiceApply.setInvoiceInfo(invoiceInfo);
					  }else{//状态不为空则说明为手选，只取手选的那笔记录
						  billBalanceInvoiceApply.setInvoiceInfo(totalData[0]);
					  }
				  }
			  }
	 	});
	}
};

billBalanceInvoiceApply.setInvoiceInfo = function(rowData){
	$("#buyerNo").val(rowData.clientNo);
	$("#buyerName").customerInvoiceInfo('setValue',rowData.clientName);
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	strDate += "-" + currTime.getDate();
	$("#invoiceApplyDate").datebox("setValue", strDate);
	$("#postPayDate").datebox("setValue", strDate);
	$("#taxRegistryNo").val(rowData.taxpayerNo);
	$("#bankName").val(rowData.bankName);
	$("#bankAccount").val(rowData.accountNo);
//	if($("#invoiceType").combobox("getValue") == ""){
		$("#invoiceType").combobox("setValue",rowData.invoiceType);
//	}
	if($("#preInvoice").combobox("getValue") == ""){
		$("#preInvoice").combobox("setValue","1");
	}
/*	$("#invoiceType").combobox("setValue","1");
	$("#preInvoice").combobox("setValue","1");*/
	$("#buyerTel").val(rowData.telephoneNumber);
	$("#buyerAddress").val(rowData.address);
	$("#contactName").val(rowData.contactPerson);
	$("#mailingAddress").val(rowData.postAddress);
	$("#bankAccountName").val(rowData.accountNo);
	$("#tel").val(rowData.contactNumber);
//	$("#currency").combobox("setValue","0");
	$("#currencyName").combobox(
			{
				onLoadSuccess : function() {
					var currencies = $("#currencyName").combobox("getData");
					for ( var i = 0; i < currencies.length; i++) {
						if (currencies[i]["standardMoney"] == 1) {
							$("#currencyName").combobox("setValue",currencies[i]["currencyName"]);
							$("#currency").val(currencies[i]["currencyCode"]);
						}
					}
				}
			});
	$("#invoiceName").val(rowData.invoiceName);
}

billBalanceInvoiceApply.clearInvoiceInfo = function(){
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	strDate += "-" + currTime.getDate();
	$("#invoiceApplyDate").datebox("setValue", strDate);
	$("#postPayDate").datebox("setValue", strDate);
	$("#taxRegistryNo").val("");
	$("#bankName").val("");
	$("#bankAccount").val("");
	if($("#invoiceType").combobox("getValue") == ""){
		$("#invoiceType").combobox("setValue","1");
	}
	if($("#preInvoice").combobox("getValue") == ""){
		$("#preInvoice").combobox("setValue","1");
	}
	$("#buyerTel").val("");
	$("#buyerAddress").val("");
	$("#contactName").val("");
	$("#mailingAddress").val("");
	$("#bankAccountName").val("");
	$("#tel").val("");
//	$("#currency").combobox("setValue","0");
	$("#currencyName").combobox(
			{
				onLoadSuccess : function() {
					var currencies = $("#currencyName").combobox("getData");
					for ( var i = 0; i < currencies.length; i++) {
						if (currencies[i]["standardMoney"] == 1) {
							$("#currencyName").combobox("setValue",currencies[i]["currencyName"]);
							$("#currency").val(currencies[i]["currencyCode"]);
						}
					}
				}
			});
	$("#invoiceName").val("");
}

billBalanceInvoiceApply.setDtlInfoDataGrids = function(rowData) {
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	strDate += "-" + currTime.getDate();
	
	if("1" == rowData.billingMethod){
		$("#buyerName").customerInvoiceInfo('setValue',rowData.bsgroupsName);
		$("#buyerNo").val(rowData.bsgroupsNo);
	}else{
		$("#buyerName").customerInvoiceInfo('setValue',rowData.mallName);
		$("#buyerNo").val(rowData.mallNo);
	}
	$("#invoiceApplyDate").datebox("setValue", strDate);
	$("#postPayDate").datebox("setValue", strDate);
	$("#taxRegistryNo").val(rowData.taxRegistryNo);
	$("#bankName").val(rowData.bankName);
	$("#bankAccount").val(rowData.bankAccount);
	$("#invoiceType").combobox("setValue","1");
	$("#preInvoice").combobox("setValue","1");
	$("#buyerTel").val(rowData.buyerTel);
	$("#buyerAddress").val(rowData.buyerAddress);
	$("#contactName").val(rowData.contactName);
	$("#mailingAddress").val(rowData.mailingAddress);
	$("#bankAccountName").val(rowData.bankAccountName);
	$("#tel").val(rowData.tel);
//	$("#currency").combobox("setValue","0");
	$("#currencyName").combobox(
			{
				onLoadSuccess : function() {
					var currencies = $("#currencyName").combobox("getData");
					for ( var i = 0; i < currencies.length; i++) {
						if (currencies[i]["standardMoney"] == 1) {
							$("#currencyName").combobox("setValue",currencies[i]["currencyName"]);
							$("#currency").val(currencies[i]["currencyCode"]);
						}
					}
				}
			});
	if(null != rowData.invoiceName){
		$("#remark").val("开票规则里面默认的开票名称为:"+rowData.invoiceName)
	}
};

billBalanceInvoiceApply.setDtlInfoDataGrid = function(rowData) {
	var currTime = new Date();
	var strDate = currTime.getFullYear() + "-";
	strDate += currTime.getMonth() + 1;
	strDate += "-" + currTime.getDate();
	$("#invoiceApplyDate").datebox("setValue", strDate);
	$("#postPayDate").datebox("setValue", strDate);
	$("#taxRegistryNo").val(rowData.taxRegistryNo);
	$("#bankName").val(rowData.bankName);
	$("#bankAccount").val(rowData.bankAccount);
	$("#invoiceType").combobox("setValue","1");
	$("#preInvoice").combobox("setValue","1");
	$("#buyerTel").val(rowData.tel);
	$("#buyerAddress").val(rowData.address);
	$("#contactName").val(rowData.contactName);
	$("#mailingAddress").val(rowData.address);
	$("#bankAccountName").val(rowData.bankAccountName);
	$("#tel").val(rowData.tel);
//	$("#currency").combobox("setValue","0");
	$("#currencyName").combobox(
			{
				onLoadSuccess : function() {
					var currencies = $("#currencyName").combobox("getData");
					for ( var i = 0; i < currencies.length; i++) {
						if (currencies[i]["standardMoney"] == 1) {
							$("#currencyName").combobox("setValue",currencies[i]["currencyName"]);
							$("#currency").val(currencies[i]["currencyCode"]);
						}
					}
				}
			});
};

function getTotalFooter(){
	$('#dg').datagrid('reloadFooter',[
	                                	{name: 'name1', salary: 60000},
	                                	{name: 'name2', salary: 65000}
	                                ]);
}

billBalanceInvoiceApply.checkApplyBillNo = function (billNo){
	var checkExist=false;
	// 填充结算单号和原单类型
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/bill_balance_invoice_apply/list.json',
		  data: {"billNo" : billNo},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  if(resultData){
				  if(resultData.rows[0].invoiceRegisterNo){
					  checkExist = true;
				  }
			  }
		  }
	});
	return checkExist;
}

billBalanceInvoiceApply.checkApplyDtlByBillNo = function (billNo){
	var checkExist=false;
	// 填充结算单号和原单类型
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/bill_balance_invoice_dtl/get_count.json',
		  data: {"billNo" : billNo},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  totalData = parseInt(resultData,10);
			  if(totalData == 0){
				  checkExist=true;
			  }
		  }
	});
	return checkExist;
}

/*billBalanceInvoiceApply.delApply = function() {
	var checkedRows = $('#' + $this.options.dataGridId).datagrid('getChecked');
	var errorMessage = getBatchErrorMessage(checkedRows);
	if(errorMessage != "") {
		showInfo(errorMessage);
		return;
	}
	if(checkedRows.length < 1) {
		showWarn("请选择要删除选中的单据?");
		return;
	}

	for(var i = 0; i < checkedRows.length; i++) {
		var item = checkedRows[i];
		if(item.status != "1") {
			showWarn("单据：" + item.billNo + "，已审核确认不可删除！");
			return false;
		}
		if(billBalanceInvoiceApply.checkApplyBillNo(item.billNo)){
			showWarn(item.billNo+"已关联发票登记,无法删除!");
			return false;
		}
	}
	$.messager.confirm("确认", "你确定要删除选中的单据?", function(r) {
		if(r) {
			var url = BasePath + $this.modulePath + $this.options.delUrl;
			var idList = "";
			$.each(checkedRows, function(index, row) {
				idList += row.id + ',' + row.billNo + ',' + row.balanceType + ";";
			});
			var params = {idList : idList.substring(0, idList.length - 1)}; // {id:idList};//
			ajaxRequestAsync(url, params, function(count) {
				if(count) {
					showSuc('删除成功');
					$this.search();
				} else {
					showError('删除失败');
				}
			});
		}
	});
};*/

//删单
billBalanceInvoiceApply.delApply = function() {
	var pkValue = $('#id').val();
	if (!pkValue) {
		showInfo("请选择相应的单据再删除！");
		return;
	}
	
	var checkExist=false;
	// 填充结算单号和原单类型
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/bill_balance_invoice_apply/get_count.json',
		  data: {"billNo" : $("#billNo").val()},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  totalData = parseInt(resultData,10);
			  if(totalData == 0){
				  checkExist=true;
			  }
		  }
	});
	
	if(checkExist){
		showWarn($("#billNo").val()+"操作失败,请检查单据是否存在!");
		return false;
	}
	
	var statusStr = "";
	var useFlagStr = "";
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/bill_balance_invoice_apply/list.json',
		  data: {"billNo" : $("#billNo").val()},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  if(resultData){
				  statusStr = resultData.rows[0].status;
				  useFlagStr = resultData.rows[0].useFlag;
			  }
		  }
	});
	
	if(statusStr != "1"){
		showWarn($("#billNo").val()+"不是制单状态,无法删除!");
		return false;
	}
	if(billBalanceInvoiceApply.checkApplyBillNo($("#billNo").val())){
		showWarn($("#billNo").val()+"已关联发票登记,无法删除!");
		return false;
	}
	if(useFlagStr == 1){
		showWarn($("#billNo").val()+"已使用,无法删除!");
		return false;
	}
		$.messager.confirm("确认", "你确定要删除该条单据?", function(r) {
			if (r) {
				var url = BasePath + '/bill_balance_invoice_apply/deleteOn';
				var params = new Object();
				params["id"] = pkValue;
				params["billNo"] = $("#billNo").val();
				params["balanceType"] = $("#searchbalanceType").combobox("getValue");
				ajaxRequest(url, params, function(result) {
					if (result) {
						showSuc('删除成功');
						$('#balanceNoStr').iptSearch('disable');
						$("#searchbalanceType").combobox("disable");
						disableAll();
						bill.add();
						$("#balanceNoStr").attr("readonly", true).addClass("disabled");
						$("#statusName").attr("readonly", true).addClass("disabled");
						$("#invoiceName").attr("readonly", true).addClass("disabled");
						dialog.search();
						$('#invoiceDtlDataGrid').datagrid('loadData', { total: 0, rows: [] });
						$('#invoiceCateDataGrid').datagrid('loadData', { total: 0, rows: [] });
					} else {
						showError('删除失败,请联系管理员!', 2);
					}
				});
			}
		});
}

billBalanceInvoiceApply.setAllAmount = function(){
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editRowIndex = -1;
	if(editTr.length > 0){
		editRowIndex = editTr.attr("datagrid-row-index");
	} 
	var ed = $("#invoiceCateDataGrid").datagrid('getEditor',{index:editRowIndex,field:'sendAmount'});
	$(ed.target).bind('blur',function(){
		billBalanceInvoiceApply.getAllAmount();
	}); // 绑定编辑器事件
};

billBalanceInvoiceApply.getAllAmount = function() {
	var rows = $("#invoiceCateDataGrid").datagrid('getRows');
	var allAmount = 0;
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editRowIndex = -1;
	if(editTr.length > 0){
		editRowIndex = editTr.attr("datagrid-row-index");
		var ed = $("#invoiceCateDataGrid").datagrid('getEditor',{index:editRowIndex,field:'sendAmount'});
		var amount = $(ed.target).numberbox('getValue');
		if(''!=amount){
			allAmount += parseFloat(amount);
		}
	} 
	for(var i =0,iLength = rows.length; i<iLength; i++){
		var rowIndex = $("#invoiceCateDataGrid").datagrid('getRowIndex',rows[i]);
		var amount = rows[i].sendAmount;
		if(editRowIndex != rowIndex){
			if(''!=amount){
				allAmount += parseFloat(amount);
			}
		}
/*		if(''!=amount){
			allAmount += parseFloat(amount);
		}*/
	}// 得到合计金额
	
	$('#invoiceCateDataGrid').datagrid('reloadFooter',[
	                              	{salerName: '合计', sendAmount: allAmount.toFixed(2)}
	                              	]);
};

billBalanceInvoiceApply.operate = function(status) {// 审核 -作废
	var errorMessage = getErrorMessage($('#status').val(), status);
	if (errorMessage != "") {
		showInfo(errorMessage);
		return;
	}
	
	var pkValue = $('#id').val();
	if (!pkValue) {
		showInfo("请选择相应的单据再操作！");
		return;
	}
	
	var checkExist=false;
	// 填充结算单号和原单类型
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/bill_balance_invoice_apply/get_count.json',
		  data: {"billNo" : $("#billNo").val()},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  totalData = parseInt(resultData,10);
			  if(totalData == 0){
				  checkExist=true;
			  }
		  }
	});
	
	if(checkExist){
		showWarn($("#billNo").val()+"操作失败,请检查单据是否存在!");
		return false;
	}
	
	if(status == 2 && billBalanceInvoiceApply.checkApplyDtlByBillNo($("#billNo").val())){
		showWarn($("#billNo").val()+"没有明细信息,无法审核!");
		return;
	}
	
	if(status == 1 && billBalanceInvoiceApply.checkApplyBillNo($("#billNo").val())){
		showWarn($("#billNo").val()+"已关联发票登记,无法反审核!");
		return;
	}
	
	if(status == 1 && $("#useFlag").val() == 1){
		showWarn($("#billNo").val()+"已使用,无法反审核!");
		return;
	}
	
	var statusStr = "";
	if ($('#mainDataForm').form('validate')) {
		var strStatus = $('#status').val();
		var message = "";
		if (status == 1) {
			message = "你确定要反审核该条单据?";
			statusStr = "制单";
		} else if (status == 2) {
			message = "你确定要审核该条单据?";
			statusStr = "确认";
		} else if (status == 3) {
			message = "你确定要作废该条单据?";
		}
		$.messager.confirm("确认", message, function(r) {
			if (r) {
				var user = "";
				var time = "";
				$('#status').val(status);
				$('#mainDataForm').form(
						'submit',
						{
							url : BasePath + '/bill_balance_invoice_apply/put',
							onSubmit : function(param) {
								if (status == 2) {
									user = currentUser.username;
									time = new Date().format("yyyy-MM-dd hh:mm:ss");
									param.auditor = user;
									param.auditTime = time;
									param.balanceType = $("#searchbalanceType").combobox("getValue");
								}

							},
							success : function(data) {
								if (data) {
									showSuc('操作成功');
									var jsonData = JSON.parse(data);
									$("#status").val(jsonData.status);
									$('#auditor').html(user);
									$('#auditTime').html(time);
									//_self.loadMainData(jsonData);
									if (jsonData.status != 1) {
										$('#salerName').company('disable');
										$('#buyerName').customerInvoiceInfo('disable');
										$("#invoiceType,#preInvoice,#currencyName").combobox("disable");
										$("#mainDataForm").find("input").attr("readonly", true).addClass("readonly");
										$("#mainDataForm").find("textarea").attr("readonly", true).addClass("readonly");
										$("#statusName").val(statusStr);
										$('#invoiceApplyDate').datebox('disable');
										$('#postPayDate').datebox('disable');
									} else {
										InvoiceApplyAll();
										$('#auditor').html(user);
										$('#auditTime').html(time);
										$("#statusName").val(statusStr);
//										disableAll();
									}
									dialog.search();
								} else {
									showError('操作失败');
								}
							}
						});
			}
		});
	}
};

billBalanceInvoiceApply.importCallBack = function(result) {
	var resultJson = JSON.parse(result);
	if(resultJson.data){
		$('#importDataGrid').datagrid({
			data: resultJson.data,
			rowStyler: function(index,row){
				if (row.pass == 0){
					return 'background-color:#D4E6E7;';
				}
			}
		});
		ygDialog({
			title : '导入结果',
			target : $('#myFormPanel'),
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
		if(resultJson.success){
			showSuc("导入成功!");
		}else{
			showWarn("导入失败!");
		}
	}else{
		showError("操作失败,请检查数据有效性!");
	}
	
};
function InvoiceApplyAll(){
//	$('#salerName').company('disable');
	$('#salerName').company('enable');
//	$('#buyerName').customerInvoiceInfo('disable');
	$('#buyerName').customerInvoiceInfo('enable');
	$('#invoiceApplyDate').datebox('enable');
	$('#postPayDate').datebox('enable');
	$("#invoiceType").combobox("enable");
	$("#preInvoice,#currencyName").combobox("disable");
	$("#mainDataForm").find("input").removeAttr("readonly").removeClass("readonly");
	$("#mainDataForm").find("textarea").removeAttr("readonly").removeClass("readonly");
	$('#balanceNoStr').iptSearch('disable');
	$("#balanceNoStr").attr("readonly", true).addClass("disabled");
	$("#billNo").attr("readonly", true).addClass("disabled");
	$("#brandName").attr("readonly", true).addClass("disabled");
	$("#statusName").attr("readonly", true).addClass("disabled");
	$("#amountStr").attr("readonly", true).addClass("disabled");
}


function getCurrentNowTime() {
		var now = new Date();
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var day = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		if (minute < 10) {
			minute = '0' + minute;
	}
	var second = now.getSeconds();
	if (second < 10) {
		second = '0' + second;
	}
	var nowdate = year + "-" + month + "-" + day + " " + hour + ":" + minute
			+ ":" + second + " ";
	return nowdate;
}

function setVal(dialog,data){
	var balanceType = data.balanceType;
	if(balanceType=="10"){
		dialog.$('#balanceType').append("地区门店结算");
	}else if(balanceType=='11'){
		dialog.$('#balanceType').append("地区其他出库结算");
	}else if(balanceType=='5'){
		dialog.$('#balanceType').append("地区间结算");
	}else if(balanceType=='8'){
		dialog.$('#balanceType').append("内购结算");
	}else if(balanceType=='7'){
		dialog.$('#balanceType').append("地区批发结算");
	}
	var perInvoice = data.preInvoice;
	if(perInvoice=="1"){
		dialog.$('#preInvoice').append("否");
	}else if(perInvoice=='2'){
		dialog.$('#preInvoice').append("是");
	}
	var invoiceType = data.invoiceType;
	if(invoiceType=="0"){
		dialog.$('#invoiceType').append("普通发票");
	}else if(invoiceType=='1'){
		dialog.$('#invoiceType').append("增值票");
	}
	var strIndex= data.balanceNo.indexOf(",");
	if(strIndex > 0){
		dialog.$('#balanceNo').append(data.balanceNo.substr(0,strIndex));
	}else{
		dialog.$('#balanceNo').append(data.balanceNo);
	}
	dialog.$('#billNo').append(data.billNo);
	dialog.$('#statusName').append(data.statusName);
	dialog.$('#salerName').append(data.salerName);
	dialog.$('#invoiceApplyDate').append(data.invoiceApplyDate);
	dialog.$('#postPayDate').append(data.postPayDate);
	dialog.$('#buyerName').append(data.buyerName);
	dialog.$('#invoiceName').append(data.invoiceName);
	dialog.$('#taxRegistryNo').append(data.taxRegistryNo);
	dialog.$('#brandName').append(data.brandName);
	dialog.$('#buyerTel').append(data.buyerTel);
	dialog.$('#contactName').append(data.contactName);
	dialog.$('#tel').append(data.tel);
	dialog.$('#bankName').append(data.bankName);
	dialog.$('#bankAccount').append(data.bankAccount);
	dialog.$('#amount').append(data.amount);
	dialog.$('#currencyName').append(data.currencyName);
	dialog.$('#buyerAddress').append(data.buyerAddress);
	dialog.$('#mailingAddress').append(data.mailingAddress);
	dialog.$('#remark').append(data.remark);
	dialog.$('#createUser').append($('#createUser').text());
	dialog.$('#auditor').append($('#auditor').text());
	dialog.$('#createTime').append($('#createTime').text());
	dialog.$('#auditTime').append($('#auditTime').text());
	dialog.$('#printTime').append(getCurrentNowTime());
}
 
 function print(){
	var previewUrl = BasePath + '/print/preview?viewName=application_form_print';
	var data = $('#mainDataForm').form('getData');
	var $dg = $('#invoiceCateDataGrid');
	var columns  = $dg.datagrid('options').columns;
	ygDialog({
		isFrame: true,
		cache : false,
		title : '开票申请单',
		modal:true,
		showMask: false,
		fit : true,
		href : previewUrl,
		buttons:[{
			text:'打印',
			iconCls:'icon-print',
			handler:'print_page'
		}],
		onLoad:function(win,dialog){
			var tableHeader = dialog.$('#tableHeader');
			var tableBody  =  dialog.$('#tableBody');
			var columnsLength = columns.length;
			
			//1过滤表头不需要打印的column 静态表头配置了printable=false都将不打印
			var grepColumns = $.grep(columns[columnsLength - 1], function(o, i) {
				if ($(o).attr('printable') == false) {
					return true;
				}
				return false;
			}, true);
			//过滤大类列头
			var filterColumns = [];
			$.each(columns, function(i, n) {
				if ($(n).attr("printable")== undefined) {
					filterColumns.push(n);
				}
			});
			filterColumns.push(grepColumns);
			
			if(columnsLength >= 1){
				//品牌大类汇总
				dialog.$("#hiddenDiv").remove();
				dialog.$("<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>").appendTo("body");
				dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
				var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
				tableHeader.append(tbody.html());
				setVal(dialog,data);
			}
			//组装大类表体
			ajaxRequestAsync(BasePath + '/bill_balance_invoice_dtl/query_by_billNo?billNo='+data.billNo,null, function(result) {
				if(result.rows!=undefined){
					var rows = result.rows;
					var footerRows = result.footer;
					for ( var i = 0; i < rows.length; i++) {
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumns).each(function(i,node){
							var field = $(node).attr('field');
							var value = row[field+''];
							if(value==undefined){
								bodyTdNode+='<td align="center">'+''+'</td>';
							}else{
								bodyTdNode+='<td align="center">'+value+'</td>';
							}
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableBody.append(bodyTrNode);
					}
					
					//添加合计项 
					var bodyTrNode2 ='<tr>';
					var bodyTdNode2 = '';
					var footerRow = footerRows[0];
					if(footerRow!=undefined){
						$(grepColumns).each(function(i,node){
							var field = $(node).attr('field');
							var value = footerRow[field+''];
							if(value==undefined){
								bodyTdNode2+='<td align="center">'+''+'</td>';
							}else{
								bodyTdNode2+='<td align="center">'+value+'</td>';
							}
						});
						bodyTrNode2+=bodyTdNode2+'</tr>';
						tableBody.append(bodyTrNode2);
					}
				}
			});
			dialog.print_page(this);
			win.close();
		}
	});

 }