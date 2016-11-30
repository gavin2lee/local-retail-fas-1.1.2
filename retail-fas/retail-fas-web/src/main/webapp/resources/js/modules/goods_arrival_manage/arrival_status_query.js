/**
 * 托运单管理
 */
var arrival_status_query = {};
var columnNameJSON;

// 1.查询操作
arrival_status_query.doSearch = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/arrival_status_query/list.json?quartzcenterNo='+currentQuartzcenterNo;

	// 2.加载明细 注意请求方式 restful风格 get
	$("#dataGridDiv").datagrid('options').queryParams = eval("(" + fromObjStr
			+ ")");
	$("#dataGridDiv").datagrid('options').url = queryMxURL;
	$("#dataGridDiv").datagrid('options').method = "get";
	$("#dataGridDiv").datagrid('load');
	$('#dataForm').form("disableValidation");
};
 
// 2.清空查询区域
arrival_status_query.doSearchClear = function() {
	$('#searchForm').form("clear");
	tms_bill_transport.initUnloadPoint('#unloadPointNoSearch','');
	initTransportPoint('#consigneeNoSearch','','');
	initTransportPoint('#shiperNoSearch','','');
};
 
 

arrival_status_query.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		success : callback
	});
};

 

// 清理form
arrival_status_query.clearAll = function() {
	$('#dataForm').form("clear");
};

// 关闭窗体
arrival_status_query.closeWindow = function() {
	$('#dataForm').form("disableValidation");
	$('#showDialog').window('close');
};

  

// 初始化
$(document).ready(function() {
/*	selectFromDic4Combox('#currentBillstatusSearch', 'CURRENT_BILLSTATUS',0);
	selectFromDic4Combox('#emergencyDegreeSearch', 'EMERGENCY_DEGREE',0);
	selectFromDic4Combox('#businessTypeSearch', 'BUSINESS_TYPE','1318');
 	 
    var searchCarUrl2=BasePath+'/initCache/standard_dtl?lookupcode=PACK_UNIT_TYPE';
    ajaxRequestGet(searchCarUrl2,{},function(result){
	   columnNameJSON=result;
    });
    var url=BasePath+"/query_tmstransport_point/list.json"
    ajaxRequestGet(searchCarUrl2,{},function(result){
 	   columnNameJSON=result;
     });*/
    //查询时，配送点只能看到属于自己配送点的发货方
    // 注释开始======
//  var getTransportPointUrl=BasePath+'/arrival_status_query/queryTransportPointByStoreNo/list.json';
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
   
 	$('#showDialog').window("center");
});
 
//发达ajax请求
function ajaxRequestGet(url,reqParam,callback){
	$.ajax({
		  type: 'GET',
		  url: url,
		  data: reqParam,
		  cache: true,
		  success: callback
    });
}

//加载页面前执行
function parsePage(){
	//工具条快捷搜索
	toolSearch();
};