/**
 * 回单管理
 */
var receipt = {};
  
//初始化
$(document).ready(function() {
 	//selectFromDic4Combox('#statusSearch', 'DATA_STATUS');
	//$("#statusSearch").combobox('select','0');
	$('#showDialog').window("center");
	selectFromDic4Combox('#truckStatusSearchLike', 'TRUCK_STATUS');
 	var truckNo=$("#truckNo").val();
    if(truckNo!=""){
   		 setTimeout(function(){receipt.doSearch();},600);
 		//receipt.doSearch();
  	}
 	 
});
 
// 1.查询操作
receipt.doSearch = function() {
	
  	var truckNo=$("#truckNo").val();
  	 
 	if(truckNo==""){
		alert('请输入派车编号!');
		return ;
	}
  	var fromObjStr = convertArray($('#searchForm').serializeArray());
  	
	var queryMxURL = BasePath + '/receipt/list.json?quartzcenterNo='+currentQuartzcenterNo;

	// 2.加载明细 注意请求方式 restful风格 get
	$("#dataGridDiv").datagrid('options').queryParams = eval("(" + fromObjStr
			+ ")");
	$("#dataGridDiv").datagrid('options').url = queryMxURL;
	$("#dataGridDiv").datagrid('options').method = "post";
	$("#dataGridDiv").datagrid('load');
	//$("#truckNo").val("");
};

// 2.清空查询区域
receipt.doSearchClear = function() {
	$('#searchForm').form("clear");
};

 
 

// 5单击工具条的编辑操作
receipt.showEditButton = function() {
	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	if (rows.length <= 0) {
		alert('请选择需要编辑的数据!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能编辑一行数据!');
		return;
	} else if (rows.length == 1) {
		$.each(rows, function(index, item) {
			if(item.confirmFlag=='1'){
				alert('该托运单已回单，不能进行修改!');
				return;
			}
			receipt.showEdit(item);
		});
		$('#transportPointNameHidden').val($('#transportPointName').val());
	}

};

// 6.弹出修改页面,也可以双击直接进去
receipt.showEdit = function(rowData) {
	var currentBillStatus=rowData.currentBillStatus;
 	if(currentBillStatus<'30'||currentBillStatus>'31'){
		alert('只有状态为签收的托运单才能进行修改!');
		return;
	}
 	if(rowData.confirmFlag=='1'){
		alert('该托运单已回单，不能进行修改!');
		return;
	}
	$('#dataForm').form("enableValidation");
	receipt.clearAll();
 	// 设置标题
	$('#showDialog').window({
		title : "修改信息"
	});
	$("#info_update").show();
	$("#info_save").hide();
 	// 设置信息
	$('#dataForm').form('load', rowData);
	$('#transportPointNameHidden').val($('#transportPointName').val());
	// 弹窗
	$('#showDialog').show();
	var signRemarks=rowData.signRemarks;
	var confirmRemarks=rowData.confirmRemarks;
 	if(confirmRemarks==""||confirmRemarks==null||confirmRemarks=='undefined'){
		$("#confirmRemarks").val(signRemarks);
	}
  	$("#showDialog").window('open');
    $("#signTotalBoxcount").removeAttr("disabled");
	$("#signTotalCount").removeAttr("disabled");
  	if(currentBillStatus==31){
  		$("#signTotalBoxcount").attr("disabled", "disabled"); 
  		$("#signTotalCount").attr("disabled", "disabled"); 
  	}
};

// 7.修改
receipt.modifyReceipt = function() {
	var fromObj = $('#dataForm');
	// 1.校验必填项
	var validateForm = fromObj.form('validate');
	if (validateForm == false) {
		return;
	}
 	/*if (($('#storeType').combobox('getValue') == '12' || $('#storeType')
			.combobox('getValue') == '22')
			&& ($('#storeType2').combobox('getValue') != '0')) {
		alert('当机构类型为12或22时，机构类型2必须为 0', 1);
		return;
	}*/

	var url = BasePath + '/receipt';
	fromObj.form('submit', {
		url : url,
		onSubmit : function(param) {
			param._method = 'put';
		},
		success : function(data) {
			if (data == "success") {
				receipt.doSearch();
				alert('修改成功!');
				$("#showDialog").window('close');
 				return;
			} else {
				alert('修改失败,请联系管理员!', 2);
			}
		},
		error : function() {
			alert('修改失败,请联系管理员!', 2);
		}
	});

};

// 打开回单确定页面
receipt.showModitfyReceipt = function() {
	$('#dataGridChildForReceipt').datagrid('loadData',{total:0,rows:[]}); 
  	var checkedRows = $("#dataGridDiv").datagrid("getChecked");// 获取所有勾选checkbox的行
 	var checkRepeatReceipRow=false;
  	var truckNo=$("#truckNo").val();
 	if (checkedRows.length < 1) {
		alert('请选择要回单的记录!', 1);
		return;
	}
 	var billNos = [];
 	var truckNos=[];
 	var truckNo="";
 	var truckStatus="";
 	$.each(checkedRows, function(index, item) {
 		if(item.confirmFlag=='1'){
			checkRepeatReceipRow=true;
 		}
 		billNos.push(item.billNo);
		if(index==0){
			truckNos.push(item.truckNo);
			truckNo=item.truckNo;
			truckStatus=item.truckStatus;
		}
 	});
 	if(checkRepeatReceipRow){
		alert('你选择的托运单中包括已经回单的托运单,请重新选择!');
		return;
	}
 	if(truckStatus<60){
 		alert('你选择了无效的派车单,请重新选择,派车单状态必须为:【等待回单】,【回单处理中】');
		return;
	}
 	//var checkFlag=receipt.checkEeceiptStatus(truckNo,70);
	//var checkFlag2=receipt.checkEeceiptStatus(truckNo,90);
	if(truckStatus=="70" || truckStatus=="90"){
		alert('该派车单已完结,不需要重复处理!');
		return;
	}
 	$('#dataForm5').form("disableValidation");
 	$('#showDataGridDivChildDiv').show();
	$('#showDataGridDivChildDiv').window('open');
	 var checkedRows = $('#dataGridDiv').datagrid('getChecked');
 	 var allRows=checkedRows.length;
	 if(allRows>0){
		 var i=0;
			$.each(checkedRows, function(index, item) {
		 	    var rowIndex = $('#dataGridDiv').datagrid('getRowIndex', index);
		 		$('#dataGridChildForReceipt').datagrid('insertRow',{
					index: (i),	
					row: item
		 		});
		 		i++;
			  });	 		 				
 	 }
			 
};


//8.回单
receipt.confirmReceipt = function() {
  	var checkedRows = $("#dataGridDiv").datagrid("getChecked");// 获取所有勾选checkbox的行
  	var billNos = [];
 	var truckNos=[];
 	var truckNo="";
 	var truckStatus="";
 	$.each(checkedRows, function(index, item) {
  		billNos.push(item.billNo);
		if(index==0){
			truckNos.push(item.truckNo);
			truckNo=item.truckNo;
 		}
 	});
  	var data = {
		"billNos" : billNos.join(","),
		"truckNo": truckNos.join(",")
 	};
 	url = BasePath + '/receipt/put_receipt/list';
	receipt.ajaxRequest(url, data, function(result,
			returnMsg) {
		if (result == 'success') {
 			receipt.doSearch();
			alert('回单处理成功!');
			receipt.closeWindow5();
		}else{
 			printExceptionMsg(returnMsg,0,'回单处理失败,请联系管理员!');
 		}
	});
			 
};

//关闭窗体
receipt.closeWindow5 = function() {
	$('#dataGridChildForReceipt').datagrid('loadData',{total:0,rows:[]}); 
	$('#dataForm5').form("disableValidation");
	$('#showDataGridDivChildDiv').window('close');
};


receipt.checkEeceiptStatus=function(truckNo,status){
	var checkUrl = BasePath + '/receipt/count.json';
	var checkData = {
			"truckNo" : truckNo,
			"truckStatusSearchLike":status
		};
	return receipt.checkExistFun(checkUrl, checkData);
 }
 
receipt.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		async : false,
		data : reqParam,
		cache : true,
		success : callback
	});
};
 

// 清理form
receipt.clearAll = function() {
	$('#dataForm').form("clear");
};

// 关闭窗体
receipt.closeWindow = function() {
	$('#dataForm').form("disableValidation");
	$('#showDialog').window('close');
};

 
//完结单 
receipt.endTmsbillTruck =function() {
	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	if (rows.length <= 0) {
		alert('请选择需要完结的数据!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能完结一行数据!');
		return;
	} else if (rows.length == 1) {
		$.each(rows, function(index, item) {
			var checkUrl = BasePath + '/receipt/count.json';
 			var checkData = {
				"truckNo" : item.truckNo,
				"confirmFlag":0
			};
 			var status=item.truckStatus;
 			if (receipt.checkExistFun(checkUrl, checkData)) {
				alert('该派车计划单下面还有未完成回单的托运单，不能进行完结处理.');
 				return;
			}
 			//var checkFlag=receipt.checkEeceiptStatus(item.truckNo,90);
			if (status=="90") {
				alert('该派车单已完结,不需要重复处理!');
 				return;
			}
			url = BasePath + '/receipt/put_tms_bill_truck_status/list';
			receipt.ajaxRequest(url, checkData, function(result,
					returnMsg) {
				if (result == 'success') {
		 			receipt.doSearch();
					alert('完结处理成功!');
				}else{
 					printExceptionMsg(returnMsg,0,'回单处理失败,请联系管理员!');
				}
			});
			
  		});
 	}
	
};

//查看
receipt.goToPageTmsTransport =function() {
/*	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	if (rows.length <= 0) {
		alert('请选择需要查看的数据!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能查看一行数据!');
		return;
	} else if (rows.length == 1) {
		$.each(rows, function(index, item) {*/
 	 receipt.toPageTmsTransport();
  	/*	});
 	}*/
	
};


// 验证
receipt.checkExistFun = function(url, checkColumnJsonData) {
		var checkExist = false;
		$.ajax({
		type : 'get',
		url : url,
		data : checkColumnJsonData,
		cache : true,
		async : false, // 一定要
		success : function(totalData) {
			totalData = parseInt(totalData, 10);
			if (totalData > 0) {
				checkExist = true;
			}
		}
		});
		return checkExist;
};

//验证
receipt.checkExistStatus = function(url, checkColumnJsonData) {
		var status = "";
		$.ajax({
		type : 'get',
		url : url,
		data : checkColumnJsonData,
		cache : true,
		async : false, // 一定要
		success : function(result) {
			status=result;
		}
		});
		return status;
};

//打开补单子窗体
receipt.showTransportPointDtl=function(){
	 var rowAll = $('#dataGridDiv').datagrid('getRows');
	 var allRows=rowAll.length;
 	 if(allRows==0){
		alert('托运单列表中无数据,请查询出相关数据后再进行补单!');
		return;
	 }
/*	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
 	if (rows.length <= 0) {
		alert('请选择需要补单的派车单!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能选择一行数据!');
		return;
	} else if (rows.length == 1) {*/
	var truckNo;
	$.each(rowAll, function(index, item) {
 		  truckNo=item.truckNo;
    	});
 	var data = {
			"truckNo" : truckNo,
			"_method":'get'
		 	};
 	var url=BasePath+'/receipt/get_tms_bill_transportpoint_by_id/list.json';
    var checkFlag=false;
    //var status=receipt.checkExistStatus(url,data);
    if(receipt.checkExistStatus(url,data)=='40'|| receipt.checkExistStatus(url,data)=='60' || receipt.checkExistStatus(url,data)=='65'){
    	checkFlag=true;
    }
   	if(!checkFlag){
 		alert('你选择了无效的派车单,请重新选择,派车单状态必须为:【配送中】,【等待回单】,【回单处理中】');
		return;
	}
	$("#truckNoChild").val(truckNo);
  	$('#dataForm').form("disableValidation");
 	$('#showDialogChild').show();
	$('#showDialogChild').window('open');
	showDataGridDivChildDiv
};

//补单查询
receipt.doSearchChild=function(){
  	var billNoOriginal=$("#billNoOriginal").val();
  	var billNoSearch=$("#billNoSearch").val();
  	var queryMxURL = BasePath + '/tms_bill_transport/list.json?quartzcenterSearchLike='
  	+currentQuartzcenterNo+"&billNoOriginal="+billNoOriginal+"&openFlag="+0+"&currentBillstatusSearch="+10+"&billNoSearch="+billNoSearch+"&status=0";
 	$("#dataGridDivChild").datagrid('options').url=queryMxURL;
	$("#dataGridDivChild").datagrid('options').method="get";
	$("#dataGridDivChild").datagrid('load');
 	
};

 

//补单
receipt.appendToTmsBilTruck=function(){
	$.messager.confirm("确认", "你确定要添加这条数据吗？",
				function(r) {
					if (r) {
						var billNoOriginal=$("#billNoOriginal").val();
						var truckNo=$("#truckNoChild").val();
						var $dg = $("#dataGridDivChild");
						var rows = $dg.datagrid('getChecked');
					   	var checkFlag=false;
					    if (rows.length <= 0) {
							alert('请选择派车单!');
							return;
						} else if (rows.length > 1) {
							alert('每次只能选择一行数据!');
							return;
						} else if (rows.length == 1) {
							var billNo;
							$.each(rows, function(index, item) {
								billNo=item.billNo;
					 			if(item.billNoOriginal==null || item.currentBillstatus<10){
									checkFlag=true;
									}
					    		});
					  	}
					 	if(checkFlag){
					 		alert('你选择了无效的托运单,请重新选择,托运单状态必须为:【已审核】');
					 		return;
					 	}
					 	receipt.appendToTmsBilTruckCommon(truckNo,billNo);
					}
	});	
      
};

//双击选择补单
receipt.selectAppendToTmsBilTruck=function(rowData){
    $.messager.confirm("确认", "你确定要添加这条数据吗？",
				function(r) {
					if (r) {
						var truckNo=$("#truckNoChild").val();
					 	var billNo=rowData.billNo;
					  	receipt.appendToTmsBilTruckCommon(truckNo,billNo); 
					}
				});	
};

//确定按钮选择补单
receipt.appendToTmsBilTruckCommon=function(truckNo,billNo){
 	var data = {
			"truckNo" : truckNo,
			"billNo" : billNo,
			"appendType": "2",
 			"_method":'get'
		 	};
	var currentBillstatus;
 	url =BasePath+'/receipt/get_list_tms_bill_truck_by_id/list.json';
 	var checkFlag=false;
 	var currentBillstatus=receipt.checkExistStatus(url,data);
   	if(currentBillstatus=='20'){
		 alert('该托运单已在其他派车计划中,请联系管理员修改派车单!');
		 return;
	}
 	var appendUrl =BasePath+'/receipt/put_append_tms_transpoint/list'; 

   	if(currentBillstatus>=21){
  		$.messager.confirm("确认", "这条托运单已存在其他派车计划中,你确定要添加这条数据吗？",
 				function(r) {
 					if (r) {
   						receipt.ajaxRequest(appendUrl, data, function(result, returnMsg) {
 							if (result == 'success') {
  								alert('补单成功!');
 							} else {
  								printExceptionMsg(returnMsg,0,'补单失败,请联系管理员!');
 							}
 							receipt.appendCommon(truckNo);
 						});
  					}
 				});
 	}else{
 		receipt.ajaxRequest(appendUrl, data, function(result, returnMsg) {
				if (result == 'success') {
					alert('补单成功!');
				} else {
 					printExceptionMsg(returnMsg,0,'补单失败,请联系管理员!');
				}
				receipt.appendCommon(truckNo);
			});
 	}	
}

receipt.appendCommon =function(truckNo){
	receipt.closeWindow2();
	$("#truckNo").val(truckNo);
	receipt.doSearch();
	$('#dataGridDivChild').datagrid('loadData',{total:0,rows:[]}); 
}

//关闭窗体的子窗体
receipt.closeWindow2 = function(){
	$('#dataGridDivChild').datagrid('loadData',{total:0,rows:[]}); 
	$('#dataForm2').form("clear");//搜索框置空
	//$('#dataForm2').form("enableValidation");
	$('#showDialogChild').window('close'); 
 
};


//打开选择派车计划单查询窗体
receipt.showSelectTmsBillTruck=function(){
	$('#showDialogForTmsBillTruck').show();
	$('#showDialogForTmsBillTruck').window('open');
 
};


//选择派车计划单查询
receipt.doSearchForSelectTmsBillTruck=function(){
	var truckNoSearchLike=$("#truckNoSearchLike").val();
	var planBeginDateSearch=$("#planBeginDateSearch").val();
	var planEndDateSearch=$("#planEndDateSearch").val();
	var licenseNumberSearchLike=$("#licenseNumberSearchLike").val();
	var driverNameSearchLike=$("#driverNameSearchLike").val();

 	//var queryMxURL=BasePath+'/query_receipt/list.json?status=0&sort=CREATE_TIME&order=desc';
 	var queryMxURL=BasePath+'/receipt/get_truck/list.json?status=0&sort=EDIT_TIME&order=desc&quartzcenterSearchLike='+currentQuartzcenterNo;
	$("#dataGridDivTmsBillTruck").datagrid('options').queryParams= {truckNoSearchLike:truckNoSearchLike,
		planBeginDateSearch:planBeginDateSearch,planEndDateSearch:planEndDateSearch,
		licenseNumberSearchLike:licenseNumberSearchLike,driverNameSearchLike:driverNameSearchLike,searchStatus:'60,65,70'};
    $("#dataGridDivTmsBillTruck").datagrid('options').url=queryMxURL;
	$("#dataGridDivTmsBillTruck").datagrid('options').method="get";
	$("#dataGridDivTmsBillTruck").datagrid('load');
	
};

//关闭窗体的子窗体
receipt.closeWindow3 = function(){
	$('#dataGridDivTmsBillTruck').datagrid('loadData',{total:0,rows:[]}); 
	$('#dataForm3').form("clear");//搜索框置空
	//$('#dataForm33').form("enableValidation");
	$('#showDialogForTmsBillTruck').window('close'); 
 
};



//打开批量派车计划单查询窗体
receipt.showSelectTmsBillTruckBatch=function(){
	$('#showDialogForTmsBillTruckBatch').show();
	$('#showDialogForTmsBillTruckBatch').window('open');

};


//关闭窗体的子窗体
receipt.closeWindow4 = function(){
	$('#dataGridDivTmsBillTruckBatch').datagrid('loadData',{total:0,rows:[]}); 
	$('#dataForm4').form("clear");//搜索框置空
	//$('#dataForm33').form("enableValidation");
	$('#showDialogForTmsBillTruckBatch').window('close'); 

};

//查询批量回单完结派车计划单
receipt.doSearchForSelectTmsBillTruckBatch=function(){
	var truckNoSearchLike=$("#truckNoSearchLikeBatch").val();
	var planBeginDateSearch=$("#planBeginDateSearchBatch").val();
	var planEndDateSearch=$("#planEndDateSearchBatch").val();
	var licenseNumberSearchLike=$("#licenseNumberSearchLikeBatch").val();
	var driverNameSearchLike=$("#driverNameSearchLikeBatch").val();
	if($("#checkBoxAllBillTruck").is(":checked")==true){
		$("#searchStatus").val('60,65,70,90');
	}else{
		$("#searchStatus").val('60,65,70');
	}
	var searchStatus=$("#searchStatus").val();
  	//var queryMxURL=BasePath+'/query_receipt/list.json?status=0&sort=CREATE_TIME&order=desc';
 	var queryMxURL=BasePath+'/receipt/get_truck/list.json?status=0&sort=EDIT_TIME&order=desc&quartzcenterSearchLike='+currentQuartzcenterNo;
	$("#dataGridDivTmsBillTruckBatch").datagrid('options').queryParams= {truckNoSearchLike:truckNoSearchLike,
		planBeginDateSearch:planBeginDateSearch,planEndDateSearch:planEndDateSearch,
		licenseNumberSearchLike:licenseNumberSearchLike,driverNameSearchLike:driverNameSearchLike,searchStatus:searchStatus};
    $("#dataGridDivTmsBillTruckBatch").datagrid('options').url=queryMxURL;
	$("#dataGridDivTmsBillTruckBatch").datagrid('options').method="get";
	$("#dataGridDivTmsBillTruckBatch").datagrid('load');
	
};

//批理回单
receipt.receiptBatch= function() {
	var checkRepeatReceipRow=false;
   	var checkedRows = $("#dataGridDivTmsBillTruckBatch").datagrid("getChecked");// 获取所有勾选checkbox的行
   	if (checkedRows.length < 1) {
		alert('请选择要回单的记录!', 1);
		return;
	}
  	var truckNos=[];
   	$.each(checkedRows, function(index, item) {
   		truckNos.push(item.truckNo);
	    var truckStatus=item.truckStatus;
  	    if(truckStatus=='70'||truckStatus=='90'){
  	    	checkRepeatReceipRow=true;
 	    }
  	});
  	if(checkRepeatReceipRow){
 		alert('回单派车单状态必须为:【等待回单】,【回单处理中】,请重新选择.');
		return;
	}
 	var data = {
		"truckNos" : truckNos.join(",")
 	};
	var batchReceipUrl =BasePath+'/receipt/put_batch_receipt/list.json'; 

	$.messager.confirm("确认", "您确定要回单吗？",
			function(r) {
				if (r) {
					$.ajax({
					     type: "POST",
					     url: batchReceipUrl,
					     data: data,
					     async: false,
					     success: function(result, returnMsg){
 					                 if (returnMsg == 'success') {
					         			alert('批量回单成功!');
					         			receipt.doSearchForSelectTmsBillTruckBatch();
					         		} else {
					         			printExceptionMsg(returnMsg,0,'批量回单失败,请联系管理员!');
					         		}
					              }
					 });
				}
			});
};

//批理完结
receipt.endReceiptBatch= function() {
	var checkErrorReceipRow=false;
   	var checkedRows = $("#dataGridDivTmsBillTruckBatch").datagrid("getChecked");// 获取所有勾选checkbox的行
   	if (checkedRows.length < 1) {
		alert('请选择要回单的记录!', 1);
		return;
	}
  	var truckNos=[];
   	$.each(checkedRows, function(index, item) {
   		truckNos.push(item.truckNo);
	    var truckStatus=item.truckStatus;
  	    if(truckStatus!='70'){
  	    	checkErrorReceipRow=true;
 	    }
  	});
  	if(checkErrorReceipRow){
 		alert('完结派车单状态必须为:【已回单完成】,请重新选择.');
		return;
	}
 	var data = {
		"truckNos" : truckNos.join(",")
 	};
	var batchEReceipUrl =BasePath+'/receipt/put_end_batch_tms_bill_truck/list'; 

	$.messager.confirm("确认", "您确定完结派车单吗？",
			function(r) {
				if (r) {
					$.ajax({
					     type: "POST",
					     url: batchEReceipUrl,
					     data: data,
					     async: false,
					     success: function(result, returnMsg){
 					                 if (returnMsg == 'success') {
					         			alert('批量完结成功!');
					         			receipt.doSearchForSelectTmsBillTruckBatch();
					         		} else {
					         			printExceptionMsg(returnMsg,0,'批量完结失败,请联系管理员!');
					         		}
					              }
					 });
				}
			});
};


//补单确定
receipt.selectTmsBilTruckBut = function(){
  	var $dg = $("#dataGridDivTmsBillTruck");
	var rows = $dg.datagrid('getChecked');
   	var checkFlag=false;
 	if (rows.length <= 0) {
		alert('请选择派车单!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能选择一行数据!');
		return;
	} else if (rows.length == 1) {
		var truckNo;
		$.each(rows, function(index, item) {
			truckNo=item.truckNo;
			var truckStatus=item.truckStatus;
 		 	if(truckStatus=='60' || truckStatus=='65' || truckStatus=='70'){
				checkFlag=true;
				}	
    		});
  	}
 	if(!checkFlag){
		 alert('你选择了无效的派车单,请重新选择,派车单状态必须为:【等待回单】,【回单处理中】,【已回单】');
		 return;
	}
 	receipt.appendCommon2(truckNo);
}

receipt.selectTmsBilTruck = function(rowData){
 	var checkFlag=false;
 	var truckNo=rowData.truckNo;
 	var truckStatus=rowData.truckStatus;
	if(truckStatus=='60' || truckStatus=='65' || truckStatus=='70'){
		checkFlag=true;
		}	
    if(!checkFlag){
		alert('你选择了无效的派车单,请重新选择,派车单状态必须为:【等待回单】,【回单处理中】,【已回单】');
		return;
	}
	receipt.appendCommon2(truckNo);	
}
 

receipt.appendCommon2 =function(truckNo){
	receipt.closeWindow3();
	$("#truckNo").val(truckNo);
	receipt.doSearch();
	$('#showDialogForTmsBillTruck').datagrid('loadData',{total:0,rows:[]}); 
}

//1.跳转到回单界面查询操作
receipt.toPageTmsTransport = function() {
	var truckNo=jQuery.trim($("#searchForm").find('#truckNo').val());
	if(truckNo==""){
		alert("请输入派车计划单");
		return;
	}
  	var url=BasePath+'/receipt/go_page_tms_bill_transport/list?quartzcenterNo='+currentQuartzcenterNo+'&truckNo='+truckNo;
 	openNewPane(url,614,'派车计划管理');
 };
 

 //取消补单
receipt.cancelAppendBill = function() {
	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
   	var checkFlagIsEndStatus=false;
    var checkIsAppendFlag=false;
   	var truckNo;
   	var billNo;
 	if (rows.length <= 0) {
		alert('请选择托运单!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能选择一行数据!');
		return;
	} else if (rows.length == 1) {
 		$.each(rows, function(index, item) {
			truckNo=item.truckNo;
			billNo=item.billNo;
			var truckStatus=item.currentBillStatus;
 		 	if(truckStatus=='90'){
 		 		checkFlagIsEndStatus=true;
				}	
 		 	if(item.appendFlag=='1'){
 		 		checkIsAppendFlag=true;
				}	
    		});
  	}
 	var data = {
			"truckNo" : truckNo,
			"billNo" : billNo,
 		 	};
  	if(checkFlagIsEndStatus){
		 alert('您不能选择【已完结】的托运单,请重新选择!');
		 return;
	}
	if(!checkIsAppendFlag){
		 alert('您必须选择【是否补单标记为是】的托运单,请重新选择!');
		 return;
	}
  	var cancelAppendBilUrl =BasePath+'/receipt/delete_append_tms_transpoint/list.json'; 
	$.messager.confirm("确认", "您确定要取消补单吗？",
				function(r) {
					if (r) {
						receipt.ajaxRequest(cancelAppendBilUrl, data, function(result, returnMsg) {
							if (result == 'success') {
								alert('取消补单成功!');
							} else {
								printExceptionMsg(returnMsg,0,'取消补单失败,请联系管理员!');
							}
							receipt.appendCommon(truckNo);
						});
					}
				});
			 
};

 