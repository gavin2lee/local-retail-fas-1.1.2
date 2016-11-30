var nullErrorMessage = "不存在当前单据";

var nullCheckMessage = "请选中需要操作的单据!";

var deleteErrorMessage = "只允许删除制单状态的单据!";

var backErrorMessage = "只允许反审核已审核状态的单据!";

var auditErrorMessage = "只允许审核制单状态的单据!";

var peBalance = new Object();

var balanceParams = {
		mainTabUrl : BasePath + '/pe_balance/pe_balance_tabMain.htm' ,
		listUrl : BasePath + '/pe_balance/list.json' ,
		deleteUrl : BasePath + '/pe_balance/delete_pe_balance',
		verifyUrl : BasePath + '/bill_balance/verify',
		balanceType : 16,
		dtlDataGrids : [
		    {id:'peEnterDataGrid',
			 title:'到货单', 
			 tabUrl:BasePath + '/pe_balance/pe_tab_enter.htm',
			 listUrl:BasePath + '/pe_balance/pe_tab_enter.json'
		    },
		    {id:'peAdjustDataGrid',
				 title:'采购调整单', 
				 tabUrl:BasePath + '/pe_balance/pe_tab_return.htm',
				 listUrl:BasePath + '/bill_purchase_adjust/dtl_list.json'
			},
			{id:'peDeductionDataGrid',
			 title:'其他扣项', 
			 tabUrl:BasePath + '/pe_balance/pe_tab_deduction.htm',
			 listUrl:BasePath + '/pe_balance/pe_tab_deduction.json'
		    },
			{id:'peInvoiceDataGrid',
			 title:'发票信息', 
			 tabUrl:BasePath + '/pe_balance/pe_tab_invoice.htm',
			 listUrl:BasePath + '/pe_balance/pe_tab_invoice.json'
		    },
			{id:'peAskPaymentDataGrid',
			 title:'请款信息', 
			 tabUrl:BasePath + '/pe_balance/pe_tab_ask_payment.htm',
			 listUrl:BasePath + '/pe_balance/pe_tab_ask_payment.json'
		    }
		]
	};

//查询
peBalance.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		params.balanceType = 16;
		var url = BasePath + '/pe_balance/list.json';
		$('#mainDataGrid').datagrid('options').queryParams= params;
		$('#mainDataGrid').datagrid('options').url= url;
		$('#mainDataGrid').datagrid('load');
	}
};

//清空
peBalance.clear = function() {
	$('#searchForm').form("clear");
};

peBalance.loadDetail = function(rowIndex, rowData) {// 点击切换到明细
	peBalance.curRowIndex = rowIndex;
	peBalance.loadMainData(rowData);
	peBalance.loadDtlDataGrid(balanceParams.dtlDataGrids[0]);
	returnTab('dtlTab', '单据信息');
	returnTab('mainTab', '单据明细');
};

peBalance.loadMainData = function(rowData){// 加载表头数据
	peBalance.clearMainData();
	$('#mainDataForm').form('load', rowData);
	// 底部单据状态显示栏
	$('#createUser').html(rowData.createUser);
	$('#createTime').html(rowData.createTime);
	$('#auditor').html(rowData.auditor);
	$('#auditTime').html(rowData.auditTime);
};

peBalance.initMainTab = function() {// 初始化单据查询tab
  	$('#mainTab').tabs('add', {
  		title : '单据列表',
  		selected : false,
  		closable : false,
  		href : balanceParams.mainTabUrl,
  		onLoad : function(panel) {
  			pe_util.initPage('searchForm');
  			pe_util.initDate('searchForm');
  		}
  	});
  	peBalance.refreshTabs();
  	returnTab('mainTab', '单据列表');
  };
  
peBalance.refreshTabs = function() {// 切换选项卡刷新单据查询的dataGrid
  	$('#mainTab').tabs({
  		onSelect : function(title, index) {
  			$('#mainDataGrid').datagrid('resize', {
  				width : function() {
  					return document.body.clientWidth;
  				}
  			});
  			$('#easyui-panel-id').panel('resize', {
  				width : function() {
  					return document.body.clientWidth;
  				}
  			});
  			$('#queryConditionDiv').panel('resize', {
  				width : function() {
  					return document.body.clientWidth;
  				}
  			});
  		},
  		onLoad : function(panel) {
  			$('#queryConditionDiv').panel('resize', {
	  				width : function() {
	  					return document.body.clientWidth;
	  				}
	  			});
	  		}
	  	});
};

peBalance.initDtlTab = function() {// 初始化明细Tab
	var dgs = balanceParams.dtlDataGrids;
	var iLength = dgs.length;
	for(var i=0; i < iLength; i++){
		peBalance.addDtlTab(dgs[i]);
	}
	$('#dtlTab').tabs({  
	    onSelect:function(title){   
	    	for(var i=0; i < iLength; i++){
        		if(dgs[i].title == title){
        			peBalance.loadDtlDataGrid(dgs[i]);
        		}
        	}
	    }
	}); 
	returnTab('dtlTab',"单据信息");
};
    
peBalance.addDtlTab = function(dgObj){// 添加明细Tab
	var tabUrl = dgObj.tabUrl;
	$('#dtlTab').tabs('add', {
		title : dgObj.title,
		selected : false,
		closable : false,
		href : tabUrl,
		onLoad : function(panel){
			peBalance.loadDtlDataGrid(dgObj);
		}
	});
};
    
peBalance.loadDtlDataGrid = function(dgObj){// 加载明细数据
	var billNo = $('#billNo').val();
	var dg = $('#'+dgObj.id);
	var url = dgObj.listUrl;
	var params = new Object();
	if("" != billNo && dg.length > 0){
		params.balanceNo = billNo;
		setTimeout(function(){
			dg.datagrid( 'options' ).queryParams= params;
			dg.datagrid( 'options' ).url= url;
			dg.datagrid( 'load' );
		},300)
		
	}
};  

peBalance.getErrorMessage = function(currStatus ,operStatus){
	if (currStatus === ''){
		return nullMessage;
	}
	if(typeof operStatus == 'undefined'){
		if(currStatus != 0 && currStatus!=99){
			return deleteErrorMessage;
		}
	}
	if(typeof operStatus != 'undefined'){
		if(operStatus == 0 && currStatus !=1){
			return backErrorMessage;
		}
		if(operStatus == 1 && currStatus !=0){
			return auditErrorMessage;
		}
	}
	return "";
};

peBalance.getBatchErrorMessage = function(checkedRows ,operStatus){
	if(checkedRows.length ==0){
		return nullCheckMessage;
	}
	var errorMessage = "";
	$.each(checkedRows, function(index,item){
		errorMessage = peBalance.getErrorMessage(item.status,operStatus);
		if(errorMessage!=""){
			return false;
		}
		if(operStatus == 99 && ( (item.askPaymentNo&&item.askPaymentNo!= '') || 
				(item.invoiceNo&&item.invoiceNo != '') )){
			errorMessage = "已关联下游单据的结算单不允许打回!";
			return false;
		}
	});
	return errorMessage;
};

peBalance.settleMethodData = new Object();

peBalance.initSettleMethodData = function(){
	ajaxRequestAsync(BasePath + '/common_util/getSettleMethod',null,function(data){
		if(data.length > 0){
			$.each(data,function(index,item){
				peBalance.settleMethodData[item.code]=item.name;
			});
		}
	});
};

peBalance.upBill = function() {// 上单
	if (peBalance.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	peBalance.type = 1;
	preBill('mainDataGrid', peBalance.curRowIndex,
			peBalance.type, peBalance.callBackBill);
};

peBalance.downBill = function() {// 下单
	if (peBalance.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	peBalance.type = 2;
	preBill('mainDataGrid', peBalance.curRowIndex,
			peBalance.type, peBalance.callBackBill);
};
	
peBalance.callBackBill = function(curRowIndex, rowData) {// 上下单回调
	if (peBalance.type == 1 || peBalance.type == 2) {
		if (rowData != null && rowData != '' && rowData != []) {
			peBalance.loadDetail(curRowIndex, rowData);
			peBalance.type = 0;
		} else {
			if (peBalance.type == 1) {
				showInfo('已经是第一单');
			} else {
				showInfo('已经是最后一单');
			}
		}
	}
};
	
peBalance.del = function(){// 删除
	var errorMessage = peBalance.getErrorMessage($('#status').val());
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	$.messager.confirm("确认","你确定要删除该条单据?",function(r) {
		if (r) {
			var url =  balanceParams.deleteUrl;
			var params = {id : $('#id').val()+','+$('#billNo').val(), balanceType : balanceParams.balanceType};
			ajaxRequestAsync(url,params,function(data){
				if(data){
					showSuc('删除成功');
					peBalance.clearMainData();
					peBalance.clearDtlData();
					peBalance.search();
				}else{
					showError('删除失败');
				}
			});
		}
	});
	
};


peBalance.batchDel = function(){// 批量删除
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = peBalance.getBatchErrorMessage(checkedRows);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	$.messager.confirm("确认","你选中了"+checkedRows.length+"条单据，确定要删除?",function(r) {
		if (r) {
			var url =  balanceParams.deleteUrl;
			var id = "";
			$.each(checkedRows, function(index, row) {
				id += row.id+','+row.billNo+";";
			});
			var params = {id : id.substring(0, id.length-1),balanceType : balanceParams.balanceType};
			$.messager.progress({
				title:'请稍后',
				msg:'正在处理中...'
			}); 
			ajaxRequestAsync(url,params,function(data){
				if(data){
					showSuc('删除成功');
					peBalance.search();
				}else{
					showError('删除失败');
				}
				$.messager.progress('close');
			});
		}
	});
};

peBalance.doAudit = function(status){// 审核
	var errorMessage = peBalance.getErrorMessage($('#status').val(), status);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	var message = "你确定要审核这条单据?"
	if(status == 0){
		message = "你确定要反审核这条单据?"
		if(($('#askPaymentNo').val()&&$('#askPaymentNo').val() != '') || ($('#invoiceNo').val()&&$('#invoiceNo').val() != '')){
			showInfo("已关联下游单据的结算单不允许打回!");
			return ;
		}
	}
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			var data = {
					status : status,
					billNo : $('#billNo').val()
			};
			ajaxRequestAsync(balanceParams.verifyUrl,data,function(result){
				if(result){
					showSuc('操作成功');
					peBalance.loadMainData(result); 
					peBalance.search();
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

peBalance.batchDoAudit = function(status){// 批量 审核
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = peBalance.getBatchErrorMessage(checkedRows,status);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}	
	var message = "你确定要审核选中的单据?"
	if(status == 0){
		message = "你确定要反审核选中的单据?"
		$.each(checkedRows, function(index, item){
			if( (item.askPaymentNo&&item.askPaymentNo!= '') || 
					(item.invoiceNo&&item.invoiceNo != '') ){
				errorMessage = "已关联下游单据的结算单不允许反审核!";
				return false;
			}
		});
	}
	if(errorMessage !=""){
		showInfo(errorMessage)
		return ;
	}
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			$.messager.progress({
				title:'请稍后',
				msg:'正在处理中...'
			}); 
			var billNo = "";
			$.each(checkedRows, function(index, row) {
				billNo+= row.billNo+",";
			});
			var data = {
					status : status,
					billNo : billNo.substring(0,billNo.length-1)
			};
			ajaxRequestAsync(balanceParams.verifyUrl,data,function(result){
				if(result){
					showSuc('操作成功');
					peBalance.search();
				}else{
					showError('操作失败');
				}
				setTimeout(function(){
					$.messager.progress('close');
				},300);
			});
		}
	});
};

peBalance.clearMainData = function(){// 清空表头数据
	$('#mainDataForm').form("clear");
	$('#mainDataForm').find("input").val("");
	$('#bottombarMx').find('span').text("");
};

peBalance.clearDtlData = function(){// 清空明细数据
	var dgs = balanceParams.dtlDataGrids;
	var iLength = dgs.length;
	for(var i=0; i < iLength; i++){
		var dgId = dgs[i].id;
		if($('#'+dgId).length > 0){
			$('#'+dgId).datagrid('loadData', {total : 0,rows : [],footer : []});
		}
	}
};

function generateAskPaymentBill(){
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	if(checkRows.length == 0){
		showInfo("请选中需要生成请款单的结算单据(未经过请款且审核)!");
		return ; 
	}
	var flag = false;
	var errorMessage = "";
	$.each(checkRows,function(index, row){
		if(!(row.status == 1 && (row.askPaymentNo =='' || row.askPaymentNo==null))){
			errorMessage = "请选择未请款并且审核的结算单!";
			return false;
		}
	});
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	$.messager.confirm("确认","确认根据选中的结算单生成请款单?",function(r) {
		if (r) {
			var url = BasePath +  "/bill_ask_payment/generate_bill_by_balance";
			var params = {checkRows : JSON.stringify(checkRows),balanceType : 16};
			ajaxRequestAsync(url, params, function(data){
				if(data > 0){
					peBalance.search();
					showSuc('成功生成'+data+'条请款单');
				}else{
					showError('生成失败');
				}
			});
		}
	});
};

$(function(){
	peBalance.initMainTab();
	peBalance.initDtlTab();
	peBalance.initSettleMethodData();
	$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
	$('#mainTab').tabs('hideHeader');
});
