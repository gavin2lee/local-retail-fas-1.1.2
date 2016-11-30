/**
 * 托运单管理
 */
var pc_sign = {};
var columnNameJSON;

// 1.查询操作
pc_sign.doSearch = function() {
 	var fromObjStr = convertArray($('#searchForm').serializeArray());
 	var billNo=$("#billNoStrSearch").val();
	var queryMxURL = BasePath + '/receipt/list.json?quartzcenterNo='+currentQuartzcenterNo+"&billNo="+billNo;
 	// 2.加载明细 注意请求方式 restful风格 get
	$("#dataGridDiv").datagrid('options').queryParams = eval("(" + fromObjStr
			+ ")");
	$("#dataGridDiv").datagrid('options').url = queryMxURL;
	$("#dataGridDiv").datagrid('options').method = "get";
	$("#dataGridDiv").datagrid('load');
	$('#dataForm').form("disableValidation");
};
 
// 2.清空查询区域
pc_sign.doSearchClear = function() {
	$('#searchForm').form("clear");
	tms_bill_transport.initUnloadPoint('#unloadPointNoSearch','');
	initTransportPoint('#consigneeNoSearch','','');
	initTransportPoint('#shiperNoSearch','','');
};
 
//5单击工具条的编辑操作
pc_sign.showPcSign = function() {
 	var isSignOnTruckCompleted=$("#isSignOnTruckCompleted").val();
	var current_billstatus=$("#current_billstatus").val();
 	var $dg = $("#dataGridDiv");
	var rows = $dg.datagrid('getChecked');
	if (rows.length <= 0) {
		alert('请选择需要签收的数据!');
		return;
	} else if (rows.length > 1) {
		alert('每次只能签收一条数据!');
		return;
	} else if (rows.length == 1) {
		$.each(rows, function(index, item) {
  			if(isSignOnTruckCompleted!=""&&isSignOnTruckCompleted=="1"){
				if(item.currentBillStatus!=current_billstatus){
	 				alert("该托运单不能签收,签收的托运单必须是已审核状态为在途中的托运单.");
					return;
				}
			}
			pc_sign.pcSign(item);
		});
 	}

};

pc_sign.pcSign = function(item) {
	var url = BasePath + '/moblie_sign_tms_bill_transport/getTmsBillTransport/list.json';
	var data={
		"truck_no":item.truckNo,
		"bill_no":item.billNo	
	}
    var signTime=item.signTime;
	if (typeof(signTime) != "undefined" &&signTime!=null&&signTime!=''){
     	 	alert("该托运单已经签收,请不要重复签收.");
			 return;
		 }
  	pc_sign.ajaxRequest(url, data, function(result,
			returnMsg) {
  		if (typeof(result) != "undefined" &&result!=null&&result!=''){
   			$('#dataForm').form('load', result);
   			// 弹窗
  			$('#showDialog').show();
   		  	$("#showDialog").window('open');
   		  	$('#dataForm').form("enableValidation");
   		  	pc_sign.clearAll();
   		  	$("#transport_point_no").val(result.consigneeNo);
	   		$("#transport_point_name").val(result.consigneeNoName);
	   		$("#dataForm").find("#billNo").val(result.billNo);
	   		var parentBillNo=result.parentBillNo;
	   		if (typeof(parentBillNo) != "undefined" &&parentBillNo!=null&&parentBillNo!=''){
	   			$("#openFlag").val('1');
	   		}else{
	   			$("#openFlag").val('0');
	   		}
  	   		$("#shiperName").val(result.consigneeName);
	   		$("#shiperTel").val(result.consigneeTel);
	   		
	   		$("#truckNo").val(result.truckNo);
	   		$("#dtlTotalboxcount").val(result.dtlTotalboxcount);
 	   		$("#dtlTotalCount").val(result.dtlTotalCount);
 	 	   		
   		  	$("#consigneeNoNameStr").html(result.consigneeNoName);
   		  	$("#billNoStr").html(result.billNo);
	   		$("#businessTypeStr").html(result.businessTypeStr);
	   		$("#goodsDescriptionStr").html(result.goodsDescription);
	   		$("#dtlTotalboxcountStr").html(result.dtlTotalboxcount);
	   		$("#dtlTotalCountStr2").html(result.dtlTotalCount);
	   	 
	   		$("#consigneeNameStr").html(result.consigneeName);
	   		
	   		$("#consigneeTelStr").html(result.consigneeTel);
	   		$("#driverNameStr").html(result.driverName);
	   		var dtlTotalboxcountValue=result.dtlTotalboxcount;
	   		if (typeof(dtlTotalboxcountValue) != "undefined" &&dtlTotalboxcountValue!=null&&dtlTotalboxcountValue!=''){
	   			$("#totalBoxCount").val(dtlTotalboxcountValue);
	   		}else{
 	   			$("#totalBoxCount").val("0");
	   		}
	   		var dtlTotalCountValue=result.dtlTotalCount;
	   		if (typeof(dtlTotalCountValue) != "undefined" &&dtlTotalCountValue!=null&&dtlTotalCountValue!=''){
	   			$("#totalCount").val(dtlTotalCountValue);
	   		}else{
	   			$("#totalCount").val("0");
	   		}
     		 
		}
 		 
	});
}

//签收动作
pc_sign.sign=function(actionType){
	mobile_sign_tms_bill_sp_info.sign(actionType);
	pc_sign.closeWindow();
	
}
pc_sign.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		success : callback
	});
};

//10.显示明细数据
pc_sign.showAddDetails=function(){
	$('#showDetailsDialog').show();
	$('#showDialog').hide();
	$('#addOrmodifyType').val("0");
	tms_bill_transport.doSearchDetails(0);
	$("#showDetailsDialog").window('open');
};

//11.显示明细数据
pc_sign.showModifyDetails=function(){
	$('#showDetailsDialog').show();
	$('#showDialog').hide();
	$('#addOrmodifyType').val("1");
	pc_sign.doSearchDetails(0);
	$("#showDetailsDialog").window('open');
};

//12
pc_sign.doSearchDetails = function(flag) {
	
	var rows = $("#dataDetailsGridDiv").datagrid('getRows');
	if((flag==0&&rows.length<=0)||flag==1){
		var queryMxURL = BasePath + '/tms_bill_transport_dtl/list.json?billNo=';
		$('#dataForm').form("disableValidation");
		var v_value = $('#billNo').val();
		if(v_value == ''){
			v_value = '-1';
		}
		$("#dataDetailsGridDiv").datagrid('options').url = queryMxURL+v_value;
		$("#dataDetailsGridDiv").datagrid('options').method = "get";
		$("#dataDetailsGridDiv").datagrid('load');
	}
	

};


 
// 验证
pc_sign.checkExistFun = function(url, checkColumnJsonData) {
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

// 清理form
pc_sign.clearAll = function() {
	$('#dataForm').form("clear");
};

// 关闭窗体
pc_sign.closeWindow = function() {
	$('#dataForm').form("disableValidation");
	$('#showDialog').window('close');
	$("#signResult").html("");
	
};

 
//关闭详细信息窗体
pc_sign.closeDetailWindow = function(p_window) {
	$('#dataForm').form("enableValidation");
 	$('#showDialog').show();
	$('#showDialog').window("open");
	$('#showDetailsDialog').window("close");
};
 

// 初始化
$(document).ready(function() {
	selectFromDic4Combox('#currentBillstatusSearch', 'CURRENT_BILLSTATUS',23);
	selectFromDic4Combox('#emergencyDegreeSearch', 'EMERGENCY_DEGREE',0);
	selectFromDic4Combox('#businessTypeSearch', 'BUSINESS_TYPE','1318');
 	 
    var searchCarUrl2=BasePath+'/initCache/standard_dtl?lookupcode=PACK_UNIT_TYPE';
    ajaxRequestGet(searchCarUrl2,{},function(result){
	   columnNameJSON=result;
    });
    var url=BasePath+"/query_tmstransport_point/list.json"
    ajaxRequestGet(searchCarUrl2,{},function(result){
 	   columnNameJSON=result;
     });
    //查询时，配送点只能看到属于自己配送点的发货方
    // 注释开始======
//  var getTransportPointUrl=BasePath+'/pc_sign/queryTransportPointByStoreNo/list.json';
//  var transportNo='';
//  ajaxRequestGet(getTransportPointUrl,{},function(result){
//	   for(var  i=0;i<result.length;i++){
//		    transportNo=result[i].transportPointNo;
//	   }
//	   if (typeof(transportNo) != "undefined" &&transportNo!=null&&transportNo!=''){
//		   	 $('#consigneeNoSearch').combogrid('setValue',transportNo);
//	    	 $('#consigneeNoSearch').combobox('disable');
//	    }
//  });
 //注释结束========
  var transportNo=currentTransportPointNo;
  if (typeof(transportNo) != "undefined" &&transportNo!=null&&transportNo!=''){
  	
  	$('#consigneeNoSearch').combogrid({
  		panelWidth : 400,
  		panelHeight : 200,
  		delay : 1,
  		mode : 'remote',
  		url : BasePath + '/tms_transport_point/list.json?transportPointNoUC=' + transportNo,
  		idField : 'transportPointNo',
  		textField : 'transportPointName',
  		pagination : true,// 是否分页
  		rownumbers : true,// 序号
  		method : 'get',
  		columns : [ [ {
  			field : 'transportPointName',
  			title : '配送点名称',
  			width : 220,
  			sortable : true
  		},{
  			field : 'storeTypeStr',
  			title : '机构类型',
  			width : 80,
  			sortable : true
  		} ] ]
  	});
  	      
           $('#consigneeNoSearch').combogrid('setValue',transportNo); 
           $('#consigneeNoSearch').combobox('disable');
  }else{
  	initTransportPoint('#consigneeNoSearch','','');
  }
  
  //$('#consigneeNoSearch').combogrid('select','23'); 
 	$('#showDialog').window("center");
});
 
//发达ajax请求
function ajaxRequestGet(url,reqParam,callback){
	$.ajax({
		  type: 'GET',
		  url: url,
		  data: reqParam,
		  cache: true,
		  async : false,  
		  success: callback
    });
}

//加载页面前执行
function parsePage(){
	//工具条快捷搜索
	toolSearch();
};