var tsReportCheck = {};

$(function() {
	rowStyle();
	tsReportCheck.initCombobox();
    toolSearch({
        appendTo:$('#toolbar'), 
        target:$('#subLayout'),
        collapsible:true 		
    });
    tsReportCheck.addMainTab();
 
    //提示
    $.fas.tooltip({
		id : 'columnDsc',
		content : '本月发未收 = 本月发出 - 本月发本月收 - 本月发下月收；'
			+ '本月差异 = 本月发出 - 本月发本月收；'
			+ '前月发未收 = 前月发出 - 前月发前月收 - 前月发本月收；'
			+ '前月差异 = 前月发出 - 前月发前月收'
	});
	$.fas.tooltip({
		id : 'billTypeNameId',
		content : '单据编号、单据类型为出库类单据的单据编号和类型'
	});
});

/**
 * 行样式
 */
function rowStyle(){
	$('#dtlDataGrid').datagrid({ 
		  rowStyler:function(index,row){ 
			  	var zoneName = row.zoneName;
	  			if(zoneName=="小计"){
	  				return 'background-color:#FFEFD5';  
		    	}
		    }   
	});
};

/**
 * 初始化明细组件
 */
tsReportCheck.initCombobox = function() {
	$("#currentDateStart").datebox("setValue",getFirstDay());  
	$("#currentDateEnd").datebox("setValue",getLastDay());  
	$("#lastDateStart").datebox("setValue",getLFirstDay().format("yyyy-MM-dd"));  
	$("#nextDateEnd").datebox("setValue",getNLastDay());  
	$("#balanceType").combobox({
		valueField:"value",
		textField:"text",
		data:tsReportCheck.areaBalanceType,
		edittable:false
	});
};

tsReportCheck.areaBalanceType = [ {
	"value" : "3",
	"text" : "地区采购"
}, {
	"value" : "2",
	"text" : "地区间"
}, {
	"value" : "1",
	"text" : "地区自购"
}, {
	"value" : "4",
	"text" : "总部收购"
} ];

/**
 * 汇总页签
 */
tsReportCheck.addMainTab = function() {
	var url =  BasePath + '/receiveAndSend_check/list_tabMain.htm?';
	$('#mainTab').tabs('add',
		{
			title : '汇总核对',
			selected : false,
			closable : false,
			href : url,
			onLoad : function(panel) {
				tsReportCheck.initSubCombobox();
				tsReportCheck.checkedInit();
				toolSearch({
			        appendTo:$('#subToolbar'), 
			        target:$('#mainLayout'),
			        collapsible:true 		
			    });
				$.fas.tooltip({
					id : 'gatherTypeId',
					content : '汇总维度没有勾选时的汇总维度：发方公司+收方公司+发方货管单位+收方货管单位'
				});
			}
		}
	);
};

/**
 * 初始化汇总组件
 */
tsReportCheck.initSubCombobox = function() {
	$("#currentDateStartCond").datebox("setValue",getFirstDay());  
	$("#currentDateEndCond").datebox("setValue",getLastDay());  
	$("#lastDateStartCond").datebox("setValue",getLFirstDay().format("yyyy-MM-dd"));  
	$("#nextDateEndCond").datebox("setValue",getNLastDay());  
	$("#balanceTypeCond").combobox({
		valueField:"value",
		textField:"text",
		data:tsReportCheck.areaBalanceType,
		edittable:false
	});
};

/**
 * 查询明细
 */
var check_status = undefined;
tsReportCheck.searchInfo = function() {
	var dtlUrl = BasePath + "/receiveAndSend_check/selectDtl.json";
	var reqParam = $("#searchForm").form("getData");
	check_status = $("#countOnly").attr("checked");
	//大类
	var categoryNo = $("#categoryNo").combobox('getValues');
	var categoryNos = '';
	$.each(categoryNo, function(index, row) {
		categoryNos += row;
		if(index < categoryNo.length - 1) {
			categoryNos += ",";
		}
	});
	reqParam.categoryNo = categoryNos;
	//结算类型
	var balanceType = $("#balanceType").combobox('getValues');
	var balanceTypes = '';
	$.each(balanceType, function(index, row) {
		balanceTypes += row;
		if(index < balanceType.length - 1) {
			balanceTypes += ",";
		}
	});
	reqParam.balanceTypes = balanceTypes;
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = dtlUrl;
	$("#dtlDataGrid").datagrid('load');
};

tsReportCheck.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
	tsReportCheck.initCombobox();
};

/**
 * 查询汇总
 */
tsReportCheck.searchSubInfo = function() {
	var sumUrl = BasePath + "/receiveAndSend_check/selectTotal.json";
	var reqParam = $("#subForm").form("getData");
	//大类
	var categoryNo = $("#categoryNoCond").combobox('getValues');
	var categoryNos = '';
	$.each(categoryNo, function(index, row) {
		categoryNos += row;
		if(index < categoryNo.length - 1) {
			categoryNos += ",";
		}
	});
	reqParam.categoryNo = categoryNos;
	//结算类型
	var balanceType = $("#balanceTypeCond").combobox('getValues');
	var balanceTypes = '';
	$.each(balanceType, function(index, row) {
		balanceTypes += row;
		if(index < balanceType.length - 1) {
			balanceTypes += ",";
		}
	});
	reqParam.balanceTypes = balanceTypes;
	$("#dtlDataGridTotal").datagrid('options').queryParams = reqParam;
	$("#dtlDataGridTotal").datagrid('options').url = sumUrl;
	$("#dtlDataGridTotal").datagrid('load');
};

tsReportCheck.clearSubForm = function() {
	$('#subForm').form("clear");
	$('#subForm').find("input[name!=type]").val('');
	tsReportCheck.initSubCombobox();
};

/**
 * 明细导出
 */
tsReportCheck.exportDtl = function() {
	fas_common.exportExcel({
		dataGridId : "dtlDataGrid",
		url : "/receiveAndSend_check/do_fas_export?exportType=2",
		title : "收发货明细报表"
	});
};

/**
 * 汇总导出
 */
tsReportCheck.exportTotal = function() {
	fas_common.exportExcel({
		dataGridId : "dtlDataGridTotal",
		url : "/receiveAndSend_check/do_fas_export?exportType=1",
		title : "收发货汇总报表"
	});
};

/**
 * 本月第一天
 */
function getFirstDay(){  
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
    var firstDay = year+"-"+month+"-01";
    return  firstDay
};  

/**
 * 本月最后一天
 */
function getLastDay(){  
	var dateTime = new Date();
	var year = dateTime.getFullYear();
    var month = dateTime.getMonth()+1;
	var myDate = new Date(year,month,0);
    if (month<10){
        month = "0"+month;
    }
    var lastDay = year+"-"+month+"-"+myDate.getDate();
    return lastDay;
};  

/**
 * 前月第一天
 */
function getLFirstDay(){  
	var d = new Date();
	var year = d.getFullYear();
	var cmonth = d.getMonth();
	var startDate = new Date();
	startDate.setDate(1);
	if (cmonth === 0 ) {
		startDate.setFullYear(d.getFullYear() - 1);
		startDate.setMonth(9);
	} else if(cmonth === 1){
		startDate.setFullYear(d.getFullYear() - 1);
		startDate.setMonth(10);
	}else if(cmonth === 2){
		startDate.setFullYear(d.getFullYear() - 1);
		startDate.setMonth(11);
	}else{
		startDate.setMonth(cmonth-3);
	}
	return startDate;
};  

/**
 * 前月最后一天
 */
function getLLastDay(){  
	var dateTime = new Date();
	var year = dateTime.getFullYear();
    var month = dateTime.getMonth();
	var myDate = new Date(year,month,0);
    if (month<10){
        month = "0"+month;
    }
    var lastDay = year+"-"+month+"-"+myDate.getDate();
    return lastDay;
};  

/**
 * 下月第一天
 */
function getNFirstDay(){  
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth()+2;
    if (month<10){
        month = "0"+month;
    }if(month>12){
    	month='01';
    	year=year+1;
    }
    var firstDay = year+"-"+month+"-01";
    return  firstDay
};  

/**
 * 下月最后一天
 */
function getNLastDay(){  
	var dateTime = new Date();
	var year = dateTime.getFullYear();
    var month = dateTime.getMonth()+2;
	var myDate = new Date(year,month,0);
    if (month<10){
        month = "0"+month;
    }if(month>12){
    	month='01';
    	year=year+1;
    }
    var lastDay = year+"-"+month+"-"+myDate.getDate();
    return lastDay;
};  

/**
 * 去重复
 */
Array.prototype.distinct = function(field) {
	var set = {}, hasField = typeof (field) != 'undefined';
	for ( var i = this.length - 1; i >= 0; i--) {
		var obj = this[i], cacheKey = hasField ? obj[field] : obj;
		if (cacheKey in set) {
			this.splice(i, 1);
			continue;
		} else {
			set[cacheKey] = null;
		}
	}
};

/**
 * 绘制DataGrid小计
 */
tsReportCheck.compute = function(data) {
	if(check_status==undefined){
		return;
	}
	var rows=data.rows;
	//复制一份
	var currMonSenNumTotal=0;	
	var	currMonSenRMBTotal = 0;
	var	currMonRecNumTotal = 0;
	var	currMonRecRMBTotal = 0;
	var	nextMonRecNumTotal = 0;
	var	nextMonRecRMBTotal = 0;
	var	currMonYetRecNumTotal = 0;
	var	currMonYetRecRMBTotal = 0;
	var	currMonDiffNumTotal = 0;
	var	currMonDiffRMBTotal = 0;
	var	lastMonSenNumTotal = 0;
	var	lastMonSenRMBTotal = 0;
	var	lastMonLastRecNumTotal = 0;
	var	lastMonLastRecRMBTotal = 0;
	var	LastMonRecNumTotal = 0;
	var	lastMonRecRMBTotal = 0;
	var	LastMonYetRecNumTotal = 0;
	var	lastMonYetRecRMBTotal = 0;
	var	LastMonDiffNumTotal = 0;
	var	lastMonDiffRMBTotal = 0;
	var tempDisticnctRows = rows.slice(0);
	var rowNumber=rows.length;
	var rowArray=[];
	var currentIndex=0;
	var tempRow;
	var categoryNo;
	var brandName;
    if(rowNumber>0){
		    tempDisticnctRows.distinct("oneLevelCategoryNo");
		    if(tempDisticnctRows.length<=1){
		    	return;
		    }
    	    //2.小计
    	    for (var i = 0; i < tempDisticnctRows.length; i++) {
				 currMonSenNumTotal=0;
				 currMonSenRMBTotal = 0;
				 currMonRecNumTotal = 0;
				 currMonRecRMBTotal = 0;
				 nextMonRecNumTotal = 0;
				 nextMonRecRMBTotal = 0;
				 currMonYetRecNumTotal = 0;
				 currMonYetRecRMBTotal = 0;
				 currMonDiffNumTotal = 0;
				 currMonDiffRMBTotal = 0;
				 lastMonSenNumTotal = 0;
				 lastMonSenRMBTotal = 0;
				 lastMonLastRecNumTotal = 0;
				 lastMonLastRecRMBTotal = 0;
				 LastMonRecNumTotal = 0;
				 lastMonRecRMBTotal = 0;
				 LastMonYetRecNumTotal = 0;
				 lastMonYetRecRMBTotal = 0;
				 LastMonDiffNumTotal = 0;
				 lastMonDiffRMBTotal = 0;
    	    	 tempRow = new Object();
    	    	 confirmTotal = 0 ;
    	    	 categoryNo = tempDisticnctRows[i]['oneLevelCategoryNo'];
    	    	 brandName = tempDisticnctRows[i]['brandName'];
    	    	 //2.1汇总符合条件的数据,并记录累加值
    	    	 for(var j=0;j<rowNumber;j++){
    	    		 if(rows[j]['oneLevelCategoryNo']==categoryNo && rows[j]['brandName'] == brandName){
	    				currMonSenNumTotal+=rows[j]['currMonSenNum'];
	    				currMonSenRMBTotal+=rows[j]['currMonSenRMB'];
	    				currMonRecNumTotal+=rows[j]['currMonRecNum'];
	    				currMonRecRMBTotal+=rows[j]['currMonRecRMB'];
	    				nextMonRecNumTotal+=rows[j]['nextMonRecNum'];
	    				nextMonRecRMBTotal+=rows[j]['nextMonRecRMB'];
	    				currMonYetRecNumTotal+=rows[j]['currMonYetRecNum'];
	    				currMonYetRecRMBTotal+=rows[j]['currMonYetRecRMB'];
	    				currMonDiffNumTotal+=rows[j]['currMonDiffNum'];
	    				currMonDiffRMBTotal+=rows[j]['currMonDiffRMB'];
	    				lastMonSenNumTotal+=rows[j]['lastMonSenNum'];
	    				lastMonSenRMBTotal+=rows[j]['lastMonSenRMB'];
	    				lastMonLastRecNumTotal+=rows[j]['lastMonLastRecNum'];
	    				lastMonLastRecRMBTotal+=rows[j]['lastMonLastRecRMB'];
	    				LastMonRecNumTotal+=rows[j]['lastMonRecNum'];
	    				lastMonRecRMBTotal+=rows[j]['lastMonRecRMB'];
	    				LastMonYetRecNumTotal+=rows[j]['lastMonYetRecNum'];
	    				lastMonYetRecRMBTotal+=rows[j]['lastMonYetRecRMB'];
	    				LastMonDiffNumTotal+=rows[j]['lastMonDiffNum'];
	    				lastMonDiffRMBTotal+=rows[j]['lastMonDiffRMB'];
    	    			currentIndex=j;
    	    		 }
    	    	 }
    	    	 tempRow.currMonSenNum=currMonSenNumTotal;
				 tempRow.currMonSenRMB= currMonSenRMBTotal;
				 tempRow.currMonRecNum=currMonRecNumTotal;
				 tempRow.currMonRecRMB=currMonRecRMBTotal;
				 tempRow.nextMonRecNum=nextMonRecNumTotal;
				 tempRow.nextMonRecRMB=nextMonRecRMBTotal;
				 tempRow.currMonYetRecNum=currMonYetRecNumTotal;
				 tempRow.currMonYetRecRMB=currMonYetRecRMBTotal;
				 tempRow.currMonDiffNum=currMonDiffNumTotal;
				 tempRow.currMonDiffRMB=currMonDiffRMBTotal;
				 tempRow.lastMonSenNum=lastMonSenNumTotal;
				 tempRow.lastMonSenRMB=lastMonSenRMBTotal;
				 tempRow.lastMonLastRecNum=lastMonLastRecNumTotal;
				 tempRow.lastMonLastRecRMB=lastMonLastRecRMBTotal;
				 tempRow.lastMonRecNum=LastMonRecNumTotal;
				 tempRow.lastMonRecRMB=lastMonRecRMBTotal;
				 tempRow.lastMonYetRecNum=LastMonYetRecNumTotal;
				 tempRow.lastMonYetRecRMB=lastMonYetRecRMBTotal;
				 tempRow.lastMonDiffNum=LastMonDiffNumTotal;
				 tempRow.lastMonDiffRMB=lastMonDiffRMBTotal;
    	    	 tempRow.currentIndex=currentIndex;
    	    	 rowArray.push(tempRow);
    	    }
    	    //3.绘制
    	    //新增一行显示统计信息
    	    for(var a=0;a<rowArray.length;a++){
    	    	  var currentIndex = rowArray[a].currentIndex+1+a;
    	    	  $('#dtlDataGrid').datagrid('insertRow', {
						    	    			  	index:currentIndex,
						    	    			    row:{
						    	    			    	zoneName: '小计', 
						    	    			    	currMonSenNum:rowArray[a].currMonSenNum,
						    	    					currMonSenRMB:rowArray[a].currMonSenRMB,
						    	    					currMonRecNum:rowArray[a].currMonRecNum,
						    	    					currMonRecRMB:rowArray[a].currMonRecRMB,
						    	    					nextMonRecNum:rowArray[a].nextMonRecNum,
						    	    					nextMonRecRMB:rowArray[a].nextMonRecRMB,
						    	    					currMonYetRecNum:rowArray[a].currMonYetRecNum,
						    	    					currMonYetRecRMB:rowArray[a].currMonYetRecRMB,
						    	    					currMonDiffNum:rowArray[a].currMonDiffNum,
						    	    					currMonDiffRMB:rowArray[a].currMonDiffRMB,
						    	    					lastMonSenNum:rowArray[a].lastMonSenNum,
						    	    					lastMonSenRMB:rowArray[a].lastMonSenRMB,
						    	    					lastMonLastRecNum:rowArray[a].lastMonLastRecNum,
						    	    					lastMonLastRecRMB:rowArray[a].lastMonLastRecRMB,
						    	    					lastMonRecNum:rowArray[a].lastMonRecNum,
						    	    					lastMonRecRMB:rowArray[a].lastMonRecRMB,
						    	    					lastMonYetRecNum:rowArray[a].lastMonYetRecNum,
						    	    					lastMonYetRecRMB:rowArray[a].lastMonYetRecRMB,
						    	    					lastMonDiffNum:rowArray[a].lastMonDiffNum,
						    	    					lastMonDiffRMB:rowArray[a].lastMonDiffRMB
						    	    			    }
			    	    			   		}
    	    	  	);
    	    }
    }
};

tsReportCheck.checkedInit = function (){
	$('#brandUnitChecked').bind("click", function() {
		if($('#brandUnitChecked').is(':checked')){
			$("#dtlDataGridTotal").datagrid('showColumn',"brandUnitName");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'brandUnitName')).attr('notexport',false);
		}else{
			$("#dtlDataGridTotal").datagrid('hideColumn',"brandUnitName");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'brandUnitName')).attr('notexport',true);
		}
    });
	$('#categoryChecked').bind("click", function() {
		if($('#categoryChecked').is(':checked')){
			$("#dtlDataGridTotal").datagrid('showColumn',"oneLevelCategoryName");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'oneLevelCategoryName')).attr('notexport',false);
		}else{
			$("#dtlDataGridTotal").datagrid('hideColumn',"oneLevelCategoryName");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'oneLevelCategoryName')).attr('notexport',true);
		}
    });
	$('#salerNoChecked').bind("click", function() {
		if($('#salerNoChecked').is(':checked')){
			$("#dtlDataGridTotal").datagrid('showColumn',"salerName");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'salerName')).attr('notexport',false);
			$("#dtlDataGridTotal").datagrid('showColumn',"zoneNameFrom");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'zoneNameFrom')).attr('notexport',false);
		}else{
			$("#dtlDataGridTotal").datagrid('hideColumn',"salerName");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'salerName')).attr('notexport',true);
			$("#dtlDataGridTotal").datagrid('hideColumn',"zoneNameFrom");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'zoneNameFrom')).attr('notexport',true);
		}
    });
	$('#orderUnitFromChecked').bind("click", function() {
		if($('#orderUnitFromChecked').is(':checked')){
			$("#dtlDataGridTotal").datagrid('showColumn',"orderUnitNameFrom");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'orderUnitNameFrom')).attr('notexport',false);
			$("#dtlDataGridTotal").datagrid('showColumn',"organNameFrom");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'organNameFrom')).attr('notexport',false);
		}else{
			$("#dtlDataGridTotal").datagrid('hideColumn',"orderUnitNameFrom");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'orderUnitNameFrom')).attr('notexport',true);
			$("#dtlDataGridTotal").datagrid('hideColumn',"organNameFrom");
			$($('#dtlDataGridTotal').datagrid('getColumnOption', 'organNameFrom')).attr('notexport',true);
		}
    });
}
