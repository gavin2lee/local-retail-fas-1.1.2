var billBalanceInvoiceApply = new Object();

billBalanceInvoiceApply.modulePath = BasePath + '/bill_balance_invoice_apply';
// 登陆用户
var currentUser;
var isEnableEdit = false;

var nullMessage = "不存在当前单据";
var nullCheckMessage = "请选中需要操作的单据!";
var auditMessage = "只允许审核制单状态的单据";
var backMessage = "只允许反审核已确认状态的单据!";
var invalidMessage = "只允许作废已审核确认状态的单据!";// 只允许操作作废确认状态的单据
var deleteMessage = "只允许删除制单状态的单据!";
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

billBalanceInvoiceApply.addMainTab = function() {
	if ($('#mainTab') != 'undefined' && $('#mainTab') !== null) {
		var url =  BasePath + '/invoice_apply/area/bill_balance_invoice_list';
		if($('#isHQ').val() == 'true'){
			url =  BasePath + '/invoice_apply/hq/bill_balance_invoice_list';
			balanceTypeStatus = [ {'value' : '2','text' : '总部地区结算'}];
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
			}
		});
	}
};
var editor = null;
//初始化
$(function() {
	billBalanceInvoiceApply.addMainTab();
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	billBalanceInvoiceApply.initDtlTab();
	$('#mainTab').tabs('hideHeader');
	setTimeout(function(){
		billBalanceInvoiceApply.initbalanceStatus();
		billBalanceInvoiceApply.initbalanceTypeStatus();
		billBalanceInvoiceApply.initpreInvoice();
		billBalanceInvoiceApply.initinvoiceType();
		billBalanceInvoiceApply.initAddClass();
		isEnableEdit = true;
		returnTab('mainTab', '单据查询');
		
		//以超链接方式直接访问详细页面
		var isHQ = $('#isHQ').val();
		var billNoMenu = $('#billNoMenu').val();
		if(billNoMenu != null && billNoMenu != ''){
			ajaxRequestAsync( BasePath + '/bill_balance_invoice_apply/apply_list.json',{billNo:billNoMenu,isHQ:isHQ},function(data){
				var obj = data.rows[0];
				if(obj != null && obj != ''){
					setTimeout(function(){
						billBalanceInvoiceApply.loadDetail(0,obj);
					}, 500);
				}else {
					alert("单号："+ billNoMenu +"有误！", 1);
				}
			});
		} else{
//			setTimeout(function() {
//				returnTab('mainTab', '单据查询');
//			}, 500);
//			$('#balanceNoStr').iptSearch('disable');
//			$("#searchbalanceType").combobox("disable");
//			disableAll();
//			billBalanceInvoiceApply.add();
//			$("#balanceNoStr").attr("readonly", true).addClass("disabled");
		}
//		beforeAddAndUpdate();
	},300);
	billBalanceInvoiceApply.curRowIndex = -1;
	billBalanceInvoiceApply.loadBillBalance();
	billBalanceInvoiceApply.loadShowType();
	
	$.fas.extend(InvoiceApplyBaseEditor, FasEditorController);
	editor = new InvoiceApplyBaseEditor();
	editor.init("/bill_balance_invoice_apply", {
		dataGridDivId : 'invoiceCateDataGridDiv',
		dataGridId : 'invoiceCateDataGrid',
		beforeAdd : beforeAddAndUpdate,
		beforeUpdate : beforeAddAndUpdate,
		buildAddData : function() {
			return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val(), taxRate : '0.17'};
		},
		buildUpdateData : function() {
			return {billNo : $("#billNo").val(), balanceNo : $("#balanceNoStr").val(),  taxRate : '0.17'};
		}
	});
});


billBalanceInvoiceApply.search = function() {
	var params = $('#searchForm').form('getData');
	var url = billBalanceInvoiceApply.modulePath + '/apply_list.json';
	$("#invoiceApplyDataGrid").datagrid('options').queryParams = params;
	$("#invoiceApplyDataGrid").datagrid('options').url = url;
	$("#invoiceApplyDataGrid").datagrid('load');
};

//清空
billBalanceInvoiceApply.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
	$("#searchIsHQ").val($('#isHQ').val());
};

billBalanceInvoiceApply.batchDel = function(){// 批量删除   删除主明细
	var checkedRows = $('#invoiceApplyDataGrid').datagrid('getChecked');
	var errorMessage = getBatchErrorMessage(checkedRows);
	if(errorMessage!=""){
		showWarn(errorMessage);
		return ;
	}
	if(checkedRows.length < 1){
		showWarn("请选择要删除选中的单据?");
		return;
	}
	
	for(var i =0; i < checkedRows.length; i++){
		var item = checkedRows[i];
		if(item.status != "1") {
			showWarn("单据：" + item.billNo + "不是制单状态不可删除！");
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
	$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
		if (r) {
			var url =  billBalanceInvoiceApply.modulePath + '/deleteInvoiceApply';
			var idList ="";
			$.each(checkedRows, function(index, row) {
				idList += row.id + ',' + row.billNo + ',' + row.balanceType + ";";
			});
			var params = {idList : idList.substring(0, idList.length-1)}; //{id:idList};//
			ajaxRequestAsync(url,params,function(count){
				if(count){
					showSuc('删除成功');
					billBalanceInvoiceApply.search();
				}else{
					showError('删除失败');
				}
			});
		}
	});
};

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
};

billBalanceInvoiceApply.batchOperate = function(status){// 批量 审核
	var checkedRows = $('#invoiceApplyDataGrid').datagrid('getChecked');
	var errorMessage = getBatchErrorMessage(checkedRows,status);
	if(errorMessage!=""){
		showWarn(errorMessage);
		return ;
	}	
	var message = "";
	var auditor = null;
	var auditTime = null;
	if(status == 1){
		message = "你确定要反审核该条单据?";
	}else if(status == 2){
		 message = "你确定要审核该条单据?";
		 auditor = currentUser.username;
		 auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	}else if(status == 3){
		 message = "你确定要作废该条单据?";
	}
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			$.each(checkedRows, function(index, row) {
				row.status = status;
				row.auditor = auditor;
				row.auditTime = auditTime;
				row.amount = null;//金额不修改
			});
			var data = {
					updated : JSON.stringify(checkedRows),
			};
			ajaxRequestAsync(billBalanceInvoiceApply.modulePath  + '/save',data,function(result){
				if(result){
					showSuc('操作成功');
					billBalanceInvoiceApply.search();
					$("#invoiceApplyDataGrid").datagrid('load');
				    $('#dataGridCommonInvoiceDtl').datagrid( 'load' );
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

//点击切换到明细
billBalanceInvoiceApply.loadDetail = function(rowIndex, rowData) {
	billBalanceInvoiceApply.curRowIndex = rowIndex;
	var url = billBalanceInvoiceApply.modulePath + '/get';
	var params = {id : rowData.id};
	// 填充主表
	$.getJSON(url, params, function(resultData) {// resultData-服务器回传的数据
		billBalanceInvoiceApply.loadData(resultData); 
	});
	$('#remark').removeAttr("readonly").removeClass("readonly");
	//明细加载
	billBalanceInvoiceApply.loadCommonInvoiceApplyDtl(rowData);
	$("#searchbalanceType,#preInvoice,#invoiceType").combobox({disabled: true}); 
	$("#salerName").combogrid("disable");
	$("#buyerName").customerInvoiceInfo('disable');
	billBalanceInvoiceApply.initbalanceStatus();
	billBalanceInvoiceApply.initbalanceTypeStatus();
	billBalanceInvoiceApply.initpreInvoice();
	billBalanceInvoiceApply.initinvoiceType();
	var status = rowData.status;
//	billBalanceInvoiceApply.initClass();
	if(rowData.preInvoice == '1'){
		$("#invoiceCateDataGrid").datagrid('hideColumn',"estimatedAmount");
	}
	
	if(status == 1){
		isEnableEdit = true;
		$('#invoice_cateinfo_bar').toolbar('enableAll');
		enableAll();
	}else{
		isEnableEdit = false;//状态为1时，为确认状态，不能删除或新增明细
		disableAll();
		$('#invoiceApplyDate').datebox('enable');
		$("#invoiceApplyDate").removeAttr("readonly").removeClass("readonly");
		$('#invoice_cateinfo_bar').toolbar('disabledALl');
	}
	returnTab('mainTab', '单据明细');
};

//加载单据信息
billBalanceInvoiceApply.loadData = function(resultData){
	$('#mainDataForm').form('load', resultData);
	// 底部单据状态显示栏
	$('#statusName').html(resultData.status);
	$('#createUser').html(resultData.createUser);
	$('#createTime').html(resultData.createTime);
	$('#auditor').html(resultData.auditor);
	$('#auditTime').html(resultData.auditTime);
};

//加载按大类分明细行
billBalanceInvoiceApply.loadCommonInvoiceApplyDtl = function(rowData){
	var params = {billNo : rowData.billNo,
			companyNo:rowData.salerNo,
			balanceType:rowData.balanceType,
			balanceNos:rowData.balanceNo};
	var url = BasePath + '/bill_balance_invoice_dtl/query_by_billNo';
    $( '#invoiceCateDataGrid').datagrid( 'options').queryParams= params;
    $( '#invoiceCateDataGrid').datagrid( 'options').url=url;
    $( '#invoiceCateDataGrid').datagrid( 'load' );
};

// 选择源单据时,根据结算类型不同,取相应的单据的开票方及收票方
billBalanceInvoiceApply.loadBillBalance=function(){
	$("#balanceNoStr").iptSearch({
		disabled : false,
		width : 123,
		clickFn : function() {
			var hrefUrl = "";
			var searchbalanceType = $("#searchbalanceType").combobox("getValue");
			if(searchbalanceType == ""){
				showWarn("请先选择源单类型!");
				return ;
			}
			if("10" == searchbalanceType){
				hrefUrl = BasePath + '/bill_balance_invoice_apply/toSearchBillShopBalance';
			}else if("8" == searchbalanceType){
				hrefUrl = BasePath + '/bill_balance_invoice_apply/toSearchOrderBillBalance?businessTypeStr=3';
			}else{
				hrefUrl = BasePath + '/bill_balance_invoice_apply/toSearchBillBalance?invoiceType='+searchbalanceType;
			}
			if("8" == searchbalanceType){
				ygDialog({
					title : "选择原订单",
					href : hrefUrl,
					width : 920,
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
//								
								month = item["balanceMonth"];
								if(!month){
									var temp_year1=item["balanceDate"].substring(0,4) ;
								    var temp_month1=item["balanceDate"].substring(5,7);
								    month = temp_year1+temp_month1;
								}
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
							$("#balanceAmountStr").val(allAmount);
							$("#amountStr").numberbox("setValue",allAmount);
							
							$("#organNoId").val(organNo);
							$("#organNameId").val(organName);
							$("#month").val(month);
							$("#brandName").val(brandName);
							$("#brandNo").val(brandNo);
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
					width : 920,
					height : 500,
					fn : function(data) {
						$("#balanceNoStr").val(data.billNo);
						$("#balanceAmountStr").val(data.balanceAmount);
						$("#amountStr").numberbox("setValue",data.balanceAmount);
						$("#salerNo").val(data.salerNo);
						$("#salerName").combogrid('setValue', data.salerName);
						$("#buyerName").customerInvoiceInfo('setValue',data.buyerName);
						$("#buyerNo").val(data.buyerNo);
						if("7" == searchbalanceType){
							$("#organNoId").val(data.organNoFrom);
							$("#organNameId").val(data.organNameFrom);
							var temp_year1=data.balanceEndDate.substring(0,4) ;
						     var temp_month1=data.balanceEndDate.substring(5,7);
							$("#month").val(temp_year1+temp_month1);
							billBalanceInvoiceApply.setBillBalanceInvoiceName(data.salerNo, data.buyerNo);
						}else if("10" == searchbalanceType){
							$("#balanceNoStr").val(data.balanceNo);
							$("#salerName").company("setValue",data.companyName);
							$("#salerNo").val(data.companyNo);
							$("#balanceAmountStr").val(data.mallBillingAmount);
							$("#amountStr").numberbox("setValue",data.mallBillingAmount);
							$("#organNoId").val(data.organNo);
							$("#organNameId").val(data.organName);
							$("#month").val(data.month);
							$("#brandName").val(data.brandName);
							$("#brandNo").val(data.brandNo);
							var buyerNo = "";
							//先根据店铺编号，获取店铺开票规则信息
							var checkDataNo={"shopNo":data.shopNo,"compnayNo":data.companyNo};
						 	$.ajax({
								  type: 'POST',
								  url: BasePath + "/shop/findShopGroup",
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
							billBalanceInvoiceApply.setBillBalanceInvoiceName(data.salerNo, data.buyerNo);
						}
					}
				});
			}
		}
	});
};

//初始化明细Tab
billBalanceInvoiceApply.initDtlTab = function() {
	$('#dtlTab').tabs(
			{
				onSelect : function(title) {	
//					var tab = $("#dtlTab").tabs("getTab", title);
//					if ('基本信息' == title) {						
//						billCommonInvoiceRegister.loadBillBalance();
//					}else 
					if ('源单信息' == title) {
						$.fas.search({
							hasSearchForm : false,
							searchUrl : "/bill_balance_invoice_source/list.json?billNo="+$('#billNo').val(),
							dataGridId : "invoiceSourceDataGrid"
						});
					}else if('发票信息' == title){
						$.fas.search({
							hasSearchForm : false,
							searchUrl : "/bill_balance_invoice_register/getInvoiceRegister?billNo="+$('#invoiceRegisterNo').val()+"&applyBillNo="+$('#billNo').val(),//+"&status=1",
							dataGridId : "invoiceInfoDataGrid"
						});
					}
				}
			});
	returnTab('dtlTab', '基本信息'); 
	returnTab('invoiceAppDtlTab', '按大类显示');
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

billBalanceInvoiceApply.formatterDate = function(value){
	return new Date(value).format("yyyy-MM-dd");
};

//选择开票方时的处理
billBalanceInvoiceApply.selectCompany=function(data){
	if(data){
		var clientNo = $("#buyerNo").val();
		billBalanceInvoiceApply.setBillBalanceInvoiceName(data.companyNo, clientNo);
		$('#buyerName').customerInvoiceInfo({
			url : BasePath+'/base_setting/invoice_info_set/list.json?companyNo='+data.companyNo
		});
		$('#buyerName').customerInvoiceInfo("enable");
	}
};

//选择收票方时的处理
billBalanceInvoiceApply.loadCustomer=function(data){
	if(data){
		$("#buyerName").val(data.clientName);
		$("#buyerNo").val(data.clientNo);
		billBalanceInvoiceApply.setBillBalanceInvoiceName($("#salerNo").val(), data.clientNo,data.status,data.invoiceName);
		$("#invoiceInfoStatus").val(data.status);
	}
};

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
		var checkDataNo = null;
		if(status!=undefined){
			checkDataNo={"companyNo":companyNo,"clientNo":customerNo,"status":status,"invoiceName":invoiceName};
		}else{
			checkDataNo={"companyNo":companyNo,"clientNo":customerNo};
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

//根据选择的开票方或者收票方,展示开票相关信息
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
	$("#invoiceType").combobox("setValue",rowData.invoiceType);
	if($("#preInvoice").combobox("getValue") == ""){
		$("#preInvoice").combobox("setValue","1");
	}
	$("#buyerTel").val(rowData.telephoneNumber);
	$("#buyerAddress").val(rowData.address);
	$("#contactName").val(rowData.contactPerson);
	$("#mailingAddress").val(rowData.postAddress);
	$("#bankAccountName").val(rowData.accountNo);
	$("#tel").val(rowData.contactNumber);
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
};


//新增
billBalanceInvoiceApply.add = function(){
	billBalanceInvoiceApply.clearData();
	billBalanceInvoiceApply.initAddClass();
	$("#salerName").combogrid("enable");
	$('#buyerName').customerInvoiceInfo('enable');
	isEnableEdit = true;
	$('#invoice_cateinfo_bar').toolbar('enableAll');
	$('#invoiceDtlDataGrid').datagrid('loadData', { total: 0, rows: [] });
	$('#invoiceCateDataGrid').datagrid('loadData', { total: 0, rows: [] });
	$('#invoiceInfoDataGrid').datagrid('loadData', { total: 0, rows: [] });
	$('#invoiceSourceDataGrid').datagrid('loadData', { total: 0, rows: [] });
};

billBalanceInvoiceApply.save = function(){
	var status = $('#status').combobox('getValue');
	if(isEnableEdit && status == 1){
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
//				return false;
			}
			if("8" != $("#searchbalanceType").combobox("getValue")){
				if("" == $("#invoiceName").val()){
					showWarn("请完善开票信息维护，开票名称不能为空。");
					return false;
				}
			}
			$('#salerName').company('enable');
			if($('#id').val() != '') {
				if(billBalanceInvoiceApply.endEdit()){
					billBalanceInvoiceApply.doPost(1);
				}
			} else {
				// 校验方法
				if(amount != "" && $("#balanceAmountStr").val() != "" 
				&& parseFloat($("#balanceAmountStr").val()) != parseFloat(amount)){
					$.messager.confirm("确认", "开票金额与源单金额不相等,是否继续保存?", function(r) {
						if(r) {
							billBalanceInvoiceApply.doPost(0);
						}
					});
				}else{
					billBalanceInvoiceApply.doPost(0);
				}
			}
		}
	};
};

//新增/修改保存
billBalanceInvoiceApply.doPost = function(type){
	$("#mainDataForm").form('submit', {
		url : BasePath + '/bill_balance_invoice_apply/saveOrUpdate',
		onSubmit : function(param) {
			param.status = 1;
		},
		success : function(result) {
			if(result) {
				
				var resultData = JSON.parse(result);
				if(type == 1){// 修改 
					billBalanceInvoiceApply.saveDtl(resultData.billNo);
				}
				if(!resultData.errorInfo){
					showSuc('保存成功');
				}else if(resultData.errorInfo){
					showError(resultData.errorInfo);
					return ;
				}
				
				billBalanceInvoiceApply.loadData(resultData);
				//明细加载
				billBalanceInvoiceApply.loadCommonInvoiceApplyDtl(resultData);
				$('#balanceNoStr').iptSearch('disable');
				$("#searchbalanceType").combobox("disable");
				if(resultData.status == 1){
					enableAll();
				}else{
					disableAll();
				}
			} else {
				showError('保存失败');
			}
		}
	});
};

billBalanceInvoiceApply.saveDtl = function(commbillNo){// 保存明细 
	var insertedData = $('#invoiceCateDataGrid').datagrid('getChanges','inserted');
	var updatedData = $('#invoiceCateDataGrid').datagrid('getChanges','updated');
	var deletedData = $('#invoiceCateDataGrid').datagrid('getChanges','deleted');
	var billNo = $('#billNo').val();
	if(commbillNo == ""){
		$.each(insertedData, function(index, row) {
			row.billNo = billNo;
			row.salerNo = $("#salerNo").val();
		});
	}else{
		$.each(insertedData, function(index, row) {
			row.billNo = commbillNo;
			row.salerNo = $("#salerNo").val();
		});
	}
	var data = {
		inserted : JSON.stringify(insertedData),
		updated : JSON.stringify(updatedData),
		deleted : JSON.stringify(deletedData),
	};
	ajaxRequestAsync(BasePath + '/bill_balance_invoice_dtl/save_all',data,function(){
		if(data){
		  	$('#invoiceCateDataGrid').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
};

// 详细页面的删除
billBalanceInvoiceApply.delApply = function() {
	var pkValue = $('#id').val();
	if (!pkValue) {
		showInfo("请选择相应的单据再删除！");
		return;
	}
	
	var statusStr = "";
//	var useFlagStr = "";
	var invoiceRegsiter = false;
	var checkExist = false;
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/bill_balance_invoice_apply/get',
		  data: {"id" : pkValue},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  if(resultData){
				  statusStr = resultData.status;
//				  useFlagStr = resultData.rows[0].useFlag;
				  if(resultData.invoiceRegisterNo){
					  invoiceRegsiter = true;
				  }
			  }else{
				  checkExist=true;
			  }
		  }
	});
	
	
	if(checkExist){
		showWarn($("#billNo").val()+"操作失败,请检查单据是否存在!");
		return;
	}
	if(statusStr != "1"){
		showWarn($("#billNo").val()+"不是制单状态,不能删除!");
		return;
	}
	if(invoiceRegsiter){
		showWarn($("#billNo").val()+"已关联发票登记,不能删除!");
		return;
	}
//	if(useFlagStr == 1){
//		showWarn($("#billNo").val()+"已使用,无法删除!");
//		return false;
//	}
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
					$("#amountStr").removeAttr("readonly").removeClass("readonly");
					$("#invoiceName").removeAttr("readonly").removeClass("readonly");
					$('#invoiceDtlDataGrid').datagrid('loadData', { total: 0, rows: [] });
					$('#invoiceCateDataGrid').datagrid('loadData', { total: 0, rows: [] });
					billBalanceInvoiceApply.search();
					billBalanceInvoiceApply.add();
				} else {
					showError('删除失败,请联系管理员!', 2);
				}
			});
		}
	});
};


billBalanceInvoiceApply.operate = function(status) {// 明细审核 -反审核
	var errorMessage = getErrorMessage($('#status').combobox('getValue'), status);
	if (errorMessage != "") {
		showInfo(errorMessage);
		return;
	}
	
//	var checkExist=false;
//	// 填充结算单号和原单类型
//	$.ajax({
//		  type: 'POST',
//		  url: BasePath + '/bill_balance_invoice_apply/get_count.json',
//		  data: {"billNo" : $("#billNo").val()},
//		  cache: true,
//		  async:false, // 一定要
//		  success: function(resultData){
//			  totalData = parseInt(resultData,10);
//			  if(totalData == 0){
//				  checkExist=true;
//			  }
//		  }
//	});
//	
//	if(checkExist){
//		showWarn($("#billNo").val()+"操作失败,请检查单据是否存在!");
//		return false;
//	}
	
	var dtllength = $("#invoiceCateDataGrid").datagrid('getRows').length;
	if(status == 2 && dtllength <= 0){
		showWarn($("#billNo").val()+"没有明细信息,无法审核!");
		return;
	}
	
	if(status == 1 && billBalanceInvoiceApply.checkApplyBillNo($("#billNo").val())){
		showWarn($("#billNo").val()+"已关联发票登记,无法反审核!");
		return;
	}
	
//	if(status == 1 && $("#useFlag").val() == 1){
//		showWarn($("#billNo").val()+"已使用,无法反审核!");
//		return;
//	}
	
//	var statusStr = "";
	if ($('#mainDataForm').form('validate')) {
		var message = "";
		if (status == 1) {
			message = "你确定要反审核该条单据?";
			isEnableEdit = true;
			$('#invoice_cateinfo_bar').toolbar('enableAll');
		} else if (status == 2) {
			message = "你确定要审核该条单据?";
			isEnableEdit = false;
			$('#invoice_cateinfo_bar').toolbar('disabledALl');
		} else if (status == 3) {
			message = "你确定要作废该条单据?";
		}
		$.messager.confirm("确认", message, function(r) {
			if (r) {
				var user = "";
				var time = "";
				$('#status').combobox('setValue', status);
				$('#mainDataForm').form( 'submit', {
					url : BasePath + '/bill_balance_invoice_apply/put',
					onSubmit : function(param) {
						if (status == 2) {
							user = currentUser.username;
							time = new Date().format("yyyy-MM-dd hh:mm:ss");
							param.auditor = user;
							param.auditTime = time;
						}
					},
					success : function(data) {
						if (data) {
							showSuc('操作成功');
							var jsonData = JSON.parse(data);
							$('#status').combobox('setValue', jsonData.status);
							$('#auditor').html(user);
							$('#auditTime').html(time);
							if (jsonData.status != 1) {
								$('#salerName').company('disable');
								$('#buyerName').customerInvoiceInfo('disable');
								$("#invoiceType,#preInvoice,#currencyName").combobox("disable");
								$("#mainDataForm").find("input").attr("readonly", true).addClass("readonly");
								$("#mainDataForm").find("textarea").attr("readonly", true).addClass("readonly");
								$('#invoiceApplyDate').datebox('disable');
								$('#postPayDate').datebox('disable');
							} else {
								$('#auditor').html(user);
								$('#auditTime').html(time);
								enableAll();
							}
							billBalanceInvoiceApply.search();
						} else {
							showError('操作失败');
						}
					}
				});
			}
		});
	}
};

//导出明细
billBalanceInvoiceApply.exportInvoicDdtl=function(){
	$.fas.exportExcel({
	    dataGridId : "invoiceCateDataGrid",
	    exportUrl : "/bill_balance_invoice_apply/exportInvoicDdtl?billNo="+$("#billNo").val()+"&showType="+$("#showType").combobox("getValue"),
	    exportTitle : "开票申请单明细信息",
	    exportType : "fix"
	});
};

//按大类分类型切换
billBalanceInvoiceApply.loadShowType=function(){
	$('#showType').combobox({
		data : showTypeStr,
		valueField : 'value',
		textField : 'text',
		editable : false,
		onChange : function(newValue, oldValue) {
			var billNo = $("#billNo").val();
//			var companyNo = $("#salerNo").val();
//			var balanceType = $("#searchbalanceType").val();
//			var balanceNos = $("#balanceNoStr").val();
	    	if("1" == newValue){
	    		// 按大类显示
	    		$.fas.search({
	    			hasSearchForm : false,
	    			dataGridId : "invoiceCateDataGrid",
	    			searchUrl : "/bill_balance_invoice_dtl/queryInvoiceDtlGroupByParams?billNo="+billNo+"&groupBy=brand_no"
	    		});
	    	}else if("2" == newValue){
	    		// 按大类显示
	    		$.fas.search({
	    			hasSearchForm : false,
	    			dataGridId : "invoiceCateDataGrid",
	    			searchUrl : "/bill_balance_invoice_dtl/queryInvoiceDtlGroupByParams?billNo="+billNo+"&groupBy=category_no"
	    		});
	    	}else if("3" == newValue){
	    		// 按大类显示
	    		$.fas.search({
	    			hasSearchForm : false,
	    			dataGridId : "invoiceCateDataGrid",
	    			searchUrl : "/bill_balance_invoice_dtl/queryInvoiceDtlGroupByParams?billNo="+billNo+"&groupBy=brand_no,category_no"
	    		});
	    	}
		}
	}); 
};


//基本信息editor
function InvoiceApplyBaseEditor() {
	this.insertRow = function() {
		if(isEnableEdit){
			var billNo = $('#billNo').val();
			var status = $('#status').combobox('getValue');
			if(status != 1 || billNo == ''){
				showWarn('不是制单状态不能编辑.');
//				return false;
			}else{
				if(billBalanceInvoiceApply.endEdit()){
					this.super.insertRow.call(this);
					var length = $('#invoiceCateDataGrid').datagrid('getRows').length;
				    billBalanceInvoiceApply.beginEdit(length-1);
				}
			}
		}
	};
	
	this.editRow = function(rowIndex, rowData){
		if(isEnableEdit && billBalanceInvoiceApply.endEdit()){
			this.super.editRow.call(this);
			billBalanceInvoiceApply.beginEdit(rowIndex);
		}
	};
	
	this.deleteRow = function(rowIndex, rowData){
		if(isEnableEdit){
			var checkedRows = $('#invoiceCateDataGrid').datagrid('getChecked');
			var rowIndex;
			$.each(checkedRows, function(index, row) {
				rowIndex = $('#invoiceCateDataGrid').datagrid('getRowIndex',row);
				$('#invoiceCateDataGrid').datagrid('deleteRow',rowIndex);
				//删除时，重置金额
				setTatolAmount();
				billBalanceInvoiceApply.setAllAmount();
			});
			
		}
	};
}

//billBalanceInvoiceApply.addDtl = function(){// 新增明细
//	if(isEnableEdit){
//		var billNo = $('#billNo').val();
//		var status = $('#status').combobox('getValue');
//		if(status != 1 || billNo == ''){
//			showWarn('不是制单状态不能编辑.');
//			return false;
//		}
//		if(billBalanceInvoiceApply.endEdit()){
//			$('#invoiceCateDataGrid').datagrid('appendRow',{taxRate:'0.17'});
//		    var length = $('#invoiceCateDataGrid').datagrid('getRows').length;
//		    billBalanceInvoiceApply.beginEdit(length-1);
//		}
//	    return true;
//	}
//	return false;
//};
//
//billBalanceInvoiceApply.delDtl = function(){// 删除明细
//	if(isEnableEdit){
//		var checkedRows = $('#invoiceCateDataGrid').datagrid('getChecked');
//		var rowIndex;
//		$.each(checkedRows, function(index, row) {
//			rowIndex = $('#invoiceCateDataGrid').datagrid('getRowIndex',row);
//			$('#invoiceCateDataGrid').datagrid('deleteRow',rowIndex);
//			setTatolAmount();
//			billBalanceInvoiceApply.setAllAmount();
//		});
//	}
//};
//
////双击修改明细行
//billBalanceInvoiceApply.editRow = function(rowIndex, row) {
//	if(isEnableEdit){
//		if(billBalanceInvoiceApply.endEdit()){
//			//根据结算公司编号查询明细大类
//			//billCommonInvoiceRegister.financialCategory($("#salerNo").val());
//			billBalanceInvoiceApply.beginEdit(rowIndex);
//		}
//	}
//};

billBalanceInvoiceApply.beginEdit = function(editIndex){// 开始明细行编辑
	$('#invoiceCateDataGrid').datagrid('beginEdit',editIndex);
	var edAmount = $("#invoiceCateDataGrid").datagrid('getEditor',{index:editIndex,field:'sendAmount'});
	$(edAmount.target).bind('blur',function(){
		setTatolAmount();
		billBalanceInvoiceApply.setAllAmount();
	});
};

billBalanceInvoiceApply.endEdit = function(){// 结束明细行编辑
	var rows = $('#invoiceCateDataGrid').datagrid('getRows');
	var flag = true;
	$.each(rows,function(index, item){
		var rowIndex =  $('#invoiceCateDataGrid').datagrid('getRowIndex',item);
		if(!$('#invoiceCateDataGrid').datagrid('validateRow', rowIndex)){
			flag = false;
			return false;
		}	
		$('#invoiceCateDataGrid').datagrid('endEdit',rowIndex);
	});
	return flag;
};

//输入开票金额时,动态计算其他金额,并绑定到列表上
billBalanceInvoiceApply.setAllAmount = function() {
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

function setTatolAmount(){// 设置总金额
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


//修改大类行编辑列表时，触发的函数，用于判断不同的源单类型，显示不同的大类下拉框
function beforeAddAndUpdate() {
	var preInvoice = $("#preInvoice").combobox("getValue");
	var balanceType = $("#searchbalanceType").combobox("getValue");
	$("#invoiceCateDataGrid").datagrid("removeEditor", "categoryName");
//	$("#invoiceCateDataGrid").datagrid("removeEditor", "estimatedAmount");
//	$("#invoiceCateDataGrid").datagrid("removeEditor", "shortName");
	$("#invoiceCateDataGrid").datagrid("removeEditor", "organName");
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
		 		onSelect : function(rowData) {
		 			$('#organNo').val(rowData.organNo);
					$('#organName').val(rowData.name);
		 		}
		 	}
		}
	});
	if(preInvoice == '2' || preInvoice == '3' || balanceType == '10'){// 只有预开票及无票收入类型才放开编辑状态
		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "shortName", 
			editor:{
				type:'shop',
			 	options:{
			 		id:'shortName',
					name:'shortName',
			 		inputWidth:100,
			 		required:true,
			 		onSelect : function(rowIndex, rowData) {
			 			$('#retailType').val(rowData.retailType);
			 			$('#shopNo').val(rowData.shopNo);
			 			$('#fullName').val(rowData.fullName);
			 		}
			 	}
			}
		});
		
		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "brandName", 
			editor:{
				type:'brand',
			 	options:{
			 		id:'brandName1',
					name:'brandName',
			 		inputWidth:100,
			 		required:true,
			 		onSelect : function(rowIndex, rowData) {
			 			$('#brandNo1').val(rowData.brandNo);
			 		}
			 	}
			}
		});
		if(preInvoice == '2' || preInvoice == '3'){
			$("#invoiceCateDataGrid").datagrid("addEditor", {field : "estimatedAmount", 
				editor:{
					type:'numberbox',
				}
			});
		}
	}else{
		$("#invoiceCateDataGrid").datagrid("removeEditor", "shortName");
		$("#invoiceCateDataGrid").datagrid("removeEditor", "brandName");
		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "shortName"});
		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "brandName"});
	}
//	if(balanceType == '10') {
	$("#invoiceCateDataGrid").datagrid("addEditor", {field : "categoryName", 
		editor:{
			type:'categorycombobox',
			options:{
		 		id:'categoryName',
		 		name:'categoryName',
		 		inputWidth:100,
		 		required:true,
		 		onSelect : function(rowData) {
		 			$('#categoryNo').val(rowData.categoryNo);
					$('#categoryName').val(rowData.name);
					//商场结算则取店铺模板的商品开票名称
					if(balanceType == '10' && $("#shopNo").val()) {
						var urlStr = BasePath + '/invoice_template_set_dtl/categoryList.json?shopNo='
							  +$('#shopNo').val()+'&categoryNo='+rowData.categoryNo;
						//查询商品开票名称
						findInvoiceName(urlStr,10);
					}else{//其他类型则取财务大类的商品名称
						var urlStr = BasePath + '/financial_category/getCateInfoByCondition?companyNo='
							  +$("#salerNo").val()+'&categoryNo='+rowData.categoryNo;
						findInvoiceName(urlStr,0);
					}
		 		}
		 	}
		}
	});
//	} else {
//		$("#invoiceCateDataGrid").datagrid("addEditor", {field : "categoryName", 
//			editor:{
//				type:'categorycombobox',
//				options:{
//			 		id:'categoryName',
//			 		name:'categoryName',
//			 		valueField : 'financialCategoryNo',
//	       			textField : 'name',
//					url : BasePath + '/financial_category/getAllCateInfo?companyNo='+$("#salerNo").val(),
//			 		inputWidth:100,
//			 		required:true,
//			 		onSelect : function(data) {
//			 			$('#categoryNo').val(data.financialCategoryNo);
//						$('#categoryName').val(data.name);
//						$('#dtlSalerName').val(data.name);
//			 		}
//			 	}
//			}
//		});
//	}
}

//根据店铺小类编号查询小类名称
function findRetailType(retailType){
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/lookup_entry/getLookentryByCode?entryCode='+retailType,
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  if(resultData){
				  $('#retailTypeId').val(resultData);
			  }
		  }
	});
}

//根据请求URL及类型，查询商品开票名称并设置名称值
function findInvoiceName(urlStr,balanceType){
	$.ajax({
		  type: 'POST',
		  url: urlStr,
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  if(resultData.length > 0){
				  if(balanceType == 10){
					  $('#dtlSalerName').val(resultData[0].invoiceName);
				  }else{
					  $('#dtlSalerName').val(resultData[0].name);
				  }
			  }
		  }
	});
}

var balanceTypeStatus = [{'value' : '5','text' : '地区间结算'},{'value' : '7','text' : '批发结算'},{'value' : '8','text' : '内购结算'},
		                    {'value' : '10','text' : '地区商场结算'},{'value' : '11','text' : '其他出库结算'}/*{'value' : '12','text' : '独立店铺结算'},*/ ];

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

var preInvoiceformatter =[{'value':'1', 'text': '否'},{'value':'2', 'text':'预开票'},{'value':'3', 'text':'无票收入'}];

billBalanceInvoiceApply.preInvoiceformatter= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < preInvoiceformatter.length; i++) {
        if (preInvoiceformatter[i].value == value) {
            return preInvoiceformatter[i].text;
        }
    }
};

var invoiceTypeformatter =[{'value':'0', 'text': '普通发票'},{'value':'1', 'text':'增值票'},{'value':'2', 'text':'票据'}];

billBalanceInvoiceApply.invoiceTypeformatter= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < invoiceTypeformatter.length; i++) {
        if (invoiceTypeformatter[i].value == value) {
            return invoiceTypeformatter[i].text;
        }
    }
};

billBalanceInvoiceApply.initAddClass = function(){// // 新增样式
	$("#mainDataForm").find("input[class*=easyui-combobox]").combobox('enable');
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('enable');
	$("#mainDataForm").find("input").removeAttr("readonly").removeClass("readonly");
	$("#mainDataForm").find("input[iptSearch]").attr("readonly",true);
	$('#billNo').attr("readonly",true).addClass("readonly");
	$('#brandName').attr("readonly",true).addClass("readonly");
	$('.disableEdit').attr("readonly",true).addClass("readonly");
	$("#postPayDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#invoiceApplyDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#mainDataForm").find("i").show();
	$("#searchbalanceType").removeAttr("readonly").removeClass("readonly");
	$("#searchbalanceType,#preInvoice,#invoiceType").combobox({disabled: false});
	$("#status").combobox({disabled: true});
	$('#status').combobox('setValue', '1');
	$('#invoiceType').combobox('setValue', '1');
	$('#preInvoice').combobox('setValue', '1');
	$('#balanceNoStr').iptSearch('enable');
	$("#currencyName").combobox({
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

//在制单状态或者反审核后,释放可编辑控件
function enableAll(){
	$('#salerName').company('enable');
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
	$("#status").attr("readonly", true).addClass("disabled");
	$("#amountStr").attr("readonly", true).addClass("disabled");
}

// 在审核后,限制可编辑控件
function disableAll(){
	$('#salerName').company('disable');
	$('#buyerName').customerInvoiceInfo('disable');
	$('#invoiceApplyDate').datebox('disable');
	$('#postPayDate').datebox('disable');
	$("#invoiceType,#preInvoice,#currencyName").combobox("disable");
	$("#mainDataForm").find("input").attr("readonly", true).addClass("readonly");
	$("#mainDataForm").find("textarea").attr("readonly", true).addClass("readonly");
}

//清空数据
billBalanceInvoiceApply.clearData = function(){
	$('#mainDataForm').find("input").val("");
	$('#mainDataForm').find("textarea").val("");
};

//清空表单信息
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
};

billBalanceInvoiceApply.initbalanceTypeStatus=function(){
	if($('#isHQ').val() == 'true'){
		balanceTypeStatus =[{'value':'2', 'text':'总部地区结算'},{'value' : '7','text' : '批发结算'},{'value' : '14','text' : '总部其他出库结算'}];
	}
	$('#searchbalanceType').combobox({
		width:150,
		data : balanceTypeStatus,
		valueField : 'value',
		textField : 'text',
		editable : false,
		required : true,
		onChange : function(newValue, oldValue) {
	    	$('#sourceNoId').val("");
	    	hrefUrl = BasePath + '/bill_balance_invoice_register/search_invoice_apply_or_order';
		}
	});  
};

billBalanceInvoiceApply.initbalanceStatus=function(){
	$('#status').combobox({
		width:150,
		data : statusformatter,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

billBalanceInvoiceApply.initpreInvoice=function(){
	$('#preInvoice').combobox({
		width:150,
		data : preInvoiceformatter,
		valueField : 'value',
		textField : 'text',
		editable : false,
		onChange : function(newValue, oldValue) {
			if(newValue != 1){
				$('#balanceNoStr').iptSearch('disable');
			}else{
				$('#balanceNoStr').iptSearch('enable');
			}
		}
	});  
};

billBalanceInvoiceApply.initinvoiceType=function(){
	$('#invoiceType').combobox({
		width:150,
		data : invoiceTypeformatter,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

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

billBalanceInvoiceApply.exportExcel = function(){
	//调用导出方法
	printBalanceOrder();
};

//导出
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
		dialog.$('#preInvoice').append("预开票");
	}else if(perInvoice=='3'){
		dialog.$('#preInvoice').append("无票收入");
	}
	var invoiceType = data.invoiceType;
	if(invoiceType=="0"){
		dialog.$('#invoiceType').append("普通发票");
	}else if(invoiceType=='1'){
		dialog.$('#invoiceType').append("增值票");
	}else if(invoiceType=='2'){
		dialog.$('#invoiceType').append("票据");
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
 
//导出税控模板
 billBalanceInvoiceApply.exportTaxModelExcel = function() {
	 exportInvoiceApplyTaxList();
};