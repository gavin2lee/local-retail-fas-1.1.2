var billPurchaseAdjustBak = {};


var nullMessage = "不存在当前单据";

var nullCheckMessage = "请选中需要操作的单据!";

var deleteMessage = "只允许删除制单状态的单据!";

var auditMessage = "只允许确认制单状态的单据!";

var backMessage = "只允许反确认已经确认的单据!";

billPurchaseAdjustBak.modulePath = BasePath + '/bill_purchase_adjust';

billPurchaseAdjustBak.dtlModulePath = BasePath + '/bill_purchase_adjust_dtl';

billPurchaseAdjustBak.doImport = function(){
	if(billPurchaseAdjustBak.enableEdit){
		var billNo = $('#billNo').val();
		if($('#id').val()!= ''){
			pe_util.doImport('采购入库调整单.xlsx','/bill_purchase_adjust/do_import?billNo='+billNo,1,importCallBack);
		}else{
			showError("请先保存表头信息！");
		}
	}
};

function getErrorMessage(currStatus ,operStatus){
	if(currStatus ===""){
		return nullMessage;
	}
	if(typeof operStatus == 'undefined'){
		if(currStatus != 0){
			return deleteMessage;
		}
	}else if(operStatus == 1){
		if(currStatus!=0){
			return auditMessage;
		}
	}else if(operStatus == 0){
		if(currStatus!=1){
			return backMessage;
		}
	}
	return "";
}

function getBatchErrorMessage(checkRows ,operStatus){
	if(checkRows.length ==0){
		return nullCheckMessage;
	}
	var errorMessage = "";
	$.each(checkRows, function(index,item){
		errorMessage = getErrorMessage(item.status,operStatus);
		if(errorMessage!=""){
			return false;
		}
	});
	return errorMessage;
}


billPurchaseAdjustBak.clear = function(){// 清空
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
};

billPurchaseAdjustBak.search = function(){// 查询
  if($('#searchForm').length > 0){
	  var params = $('#searchForm').form('getData');
	  var url = billPurchaseAdjustBak.modulePath + '/list.json';
	  $( '#mainDataGrid').datagrid( 'options' ).queryParams= params;
	  $( '#mainDataGrid').datagrid( 'options' ).url= url;
	  $( '#mainDataGrid').datagrid( 'load' );
  }
};

billPurchaseAdjustBak.upBill = function() {// 上单
	if (billPurchaseAdjustBak.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	billPurchaseAdjustBak.type = 1;
	preBill('mainDataGrid', billPurchaseAdjustBak.curRowIndex,
			billPurchaseAdjustBak.type, billPurchaseAdjustBak.callBackBill);
};

billPurchaseAdjustBak.downBill = function() {// 下单
	if (billPurchaseAdjustBak.curRowIndex < 0) {
		showInfo('不存在当前单据');
		return;
	}
	billPurchaseAdjustBak.type = 2;
	preBill('mainDataGrid', billPurchaseAdjustBak.curRowIndex,
			billPurchaseAdjustBak.type, billPurchaseAdjustBak.callBackBill);
};

billPurchaseAdjustBak.callBackBill = function(curRowIndex, rowData) {// 上下单回调
	if (billPurchaseAdjustBak.type == 1 || billPurchaseAdjustBak.type == 2) {
		if (rowData != null && rowData != '' && rowData != []) {
			billPurchaseAdjustBak.loadDetail(curRowIndex, rowData);
			billPurchaseAdjustBak.type = 0;
		} else {
			if (billPurchaseAdjustBak.type == 1) {
				showInfo('已经是第一单');
			} else {
				showInfo('已经是最后一单');
			}
		}
	}
};

billPurchaseAdjustBak.loadDetail = function(rowIndex, rowData) {// 点击切换到明细
	rowData.curRowIndex = rowIndex;
	billPurchaseAdjustBak.loadMainData(rowData);
	billPurchaseAdjustBak.loadDtlData();
	billPurchaseAdjustBak.resetEditClass();
	returnTab('mainTab', '单据明细');
};


billPurchaseAdjustBak.initAddClass = function(){// // 新增样式
	$("#mainDataForm").find("input[singleSearch]").combogrid('enable');
	$("#mainDataForm").find("input[combobox]").combobox('enable');
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('enable');
	$("#mainDataForm").find("input[class*=disableEdit]").attr("readonly",true).addClass("readonly");
	$("#mainDataForm").find("input:not([class*=disableEdit])").removeAttr("readonly").removeClass("readonly");
	$("#mainDataForm").find("input[name=purchaseDate]").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#mainDataForm").find("input[name=dueDate]").datebox('setValue',new Date().format("yyyy-MM-dd"));
	billPurchaseAdjustBak.enableEdit = true;
};

billPurchaseAdjustBak.resetEditClass = function(){// 重置编辑样式
	$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
	$("#mainDataForm").find("input[singleSearch]").combogrid('disable');
	$("#mainDataForm").find("input[multiSearch]").combogrid('disable');
	$("#mainDataForm").find("input[combobox]").combobox('disable');
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
	if($('#status').val()=='0'){
		billPurchaseAdjustBak.enableEdit = true;
	}else{
		billPurchaseAdjustBak.enableEdit = false;
	}
};

billPurchaseAdjustBak.initAdd = function(){
	$('#mainDataForm').form("clear");
	$('#mainDataForm').find("input").val("");
	$('#bottombarMx').find('span').text("");
	if($('#dtlDataGrid').length > 0){
		$('#dtlDataGrid').datagrid('loadData', {total : 0,rows : []});
	}
	billPurchaseAdjustBak.initAddClass();
};

billPurchaseAdjustBak.loadDtlData = function(){// 加载明细数据
	var billNo = $('#billNo').val();
	var dg = $('#dtlDataGrid');
	if(dg.length >0){
		if(billNo !=""){
			setTimeout(function(){
				dg.datagrid( 'options' ).queryParams= {billNo:billNo};
				dg.datagrid( 'options' ).url = billPurchaseAdjustBak.dtlModulePath + '/list.json';
				dg.datagrid( 'load' );
			},300);
		}else{
			dg.datagrid('loadData',{total:0,rows:[]}); 
		}
	}
};

billPurchaseAdjustBak.loadMainData = function(rowData){
	$('#mainDataForm').form('load', rowData);
	$('#createUser').html(rowData.createUser);
	$('#createTime').html(rowData.createTime);
	$('#auditor').html(rowData.auditor);
	$('#auditTime').html(rowData.auditTime);
};

billPurchaseAdjustBak.save = function(){
	if(billPurchaseAdjustBak.doValidate()){
		billPurchaseAdjustBak.doSubmit();
		billPurchaseAdjustBak.search();
	}
};

billPurchaseAdjustBak.doSubmit = function(){
	var pkId = $('#id').val();
	var url = billPurchaseAdjustBak.modulePath +'/post';
	if(pkId != ''){
		url = billPurchaseAdjustBak.modulePath +'/put';
	}
	$('#mainDataForm').form('submit',{
		url : url,
		onSubmit:function(param){
			if(pkId ==""){
				param.createUser = currentUser.username;
				param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			}else{
				param.updateUser = currentUser.username;
				param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			}
		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
				billPurchaseAdjustBak.loadMainData(jsonData);
				billPurchaseAdjustBak.saveDtl();
				billPurchaseAdjustBak.resetEditClass();
			}else{
				showError('保存失败');
			}
		}
	});
};

billPurchaseAdjustBak.saveDtl = function(){// 保存明细 
	if(billPurchaseAdjustBak.endEdit()){
		var insertedData = $('#dtlDataGrid').datagrid('getChanges','inserted');
		var updatedData = $('#dtlDataGrid').datagrid('getChanges','updated');
		var deletedData = $('#dtlDataGrid').datagrid('getChanges','deleted');
		var billNo = $('#billNo').val();
		$.each(insertedData, function(index, row) {
			row.billNo = billNo;
		});
		var data = {
			inserted : JSON.stringify(insertedData),
			updated : JSON.stringify(updatedData),
			deleted : JSON.stringify(deletedData),
		};
		ajaxRequestAsync(billPurchaseAdjustBak.dtlModulePath + '/save',data,function(result){
			if(result){
			  	$('#dtlDataGrid').datagrid('acceptChanges');
			  	$( '#dtlDataGrid').datagrid( 'options' ).queryParams= {billNo:billNo};
			    $( '#dtlDataGrid').datagrid( 'options' ).url = billPurchaseAdjustBak.dtlModulePath + '/list.json';
			    $( '#dtlDataGrid').datagrid( 'load' );
				showSuc('保存成功');
			}else{
				showError('保存失败');
			}
		});
	}
};

billPurchaseAdjustBak.endEdit = function(){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#dtlDataGrid').datagrid('validateRow', editRowIndex)){
			$('#dtlDataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

billPurchaseAdjustBak.doValidate = function(){
	if($('#status').val()!=1){
		if($('#mainDataForm').form('validate')){
			if(billPurchaseAdjustBak.endEdit()){
				return true;
			}
		}
		return false;
	}
	showInfo("该单据已确认,不允许变更!");
	return false;
};

billPurchaseAdjustBak.del = function(){// 删除
	var errorMessage = getErrorMessage($('#status').val());
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	$.messager.confirm("确认","你确定要删除该条单据?",function(r) {
		if (r) {
			var url =  billPurchaseAdjustBak.modulePath + '/delete';
			var id = $('#id').val();
			var billNo = $('#billNo').val();
			var params = {idList : id+','+billNo};
			ajaxRequestAsync(url,params,function(data){
				if(data){
					showSuc('删除成功');
					billPurchaseAdjustBak.initAdd();
					billPurchaseAdjustBak.search();
				}else{
					showError('删除失败');
				}
			});
		}
	});
};

billPurchaseAdjustBak.batchDel = function(){// 批量删除
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = getBatchErrorMessage(checkedRows);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	$.messager.confirm("确认","你选中了"+checkedRows.length+"条单据，确定要删除?",function(r) {
		if (r) {
			var url =  billPurchaseAdjustBak.modulePath + '/delete';
			var idList ="";
			$.each(checkedRows, function(index, row) {
				idList+=row.id+','+row.billNo+";";
			});
			var params = {idList : idList.substring(0, idList.length-1)};
			ajaxRequestAsync(url,params,function(count){
				if(count){
					showSuc('删除成功');
					billPurchaseAdjustBak.search();
				}else{
					showError('删除失败');
				}
				
			});
		}
	});
};

billPurchaseAdjustBak.checkIsBalance = function(billNo){
	var flag = false;
	ajaxRequestAsync( BasePath + '/bill_purchase_adjust_dtl/get_count.json',{billNo:billNo,isBalance:1},function(count){
		if(count >0){
			flag = true;
		}
	});
	return flag;
};

billPurchaseAdjustBak.operate = function(operateStatus){
	if($("tr[class*=datagrid-row-editing]").length > 0){
		showInfo("请先保存后、再继续操作!");
		return ;
	}
	var errorMessage = getErrorMessage($('#status').val(),operateStatus);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}
	var message = "你确定要确认该条单据?";
	if(operateStatus == 0){
		// 结算判断
		var billNo = $('#billNo').val();
		if(billPurchaseAdjustBak.checkIsBalance(billNo)){
			showError('该单据已结算！');
			return ;
		}
		message = "你确定要反确认该条单据?";
	}
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			var data = {
					billNo : $('#billNo').val(),
					status : operateStatus,
					auditor : currentUser.username,
					auditTime : new Date().format("yyyy-MM-dd hh:mm:ss")
			};
			ajaxRequestAsync(billPurchaseAdjustBak.modulePath + '/verify_bill',data,function(result){
				if(result){
					showSuc('操作成功');
					billPurchaseAdjustBak.loadMainData(result); 
					billPurchaseAdjustBak.search();
					billPurchaseAdjustBak.resetEditClass();
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

billPurchaseAdjustBak.batchOperate = function(status){// 批量 审核-作废
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	var errorMessage = getBatchErrorMessage(checkedRows,status);
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}	
	if(errorMessage!=""){
		showInfo(errorMessage);
		return ;
	}	
	var message = "你确定要确认选中的单据?";
	if(status == 0){
		// 结算判断
		$.each(checkedRows, function(index,item){
			if(billPurchaseAdjustBak.checkIsBalance(item.billNo)){
				errorMessage = '存在已结算的单据！';
				return false;
			}
		});
		if(errorMessage!=""){
			showInfo(errorMessage);
			return ;
		}
		message = "你确定要反确认选中的单据?";
	}
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			var billNo = "";
			$.each(checkedRows, function(index, row) {
				billNo +=row.billNo+",";
			});
			var data = {
					billNo : billNo.substring(0,billNo.length - 1),
					status : status,
					auditor : currentUser.username,
					auditTime : new Date().format("yyyy-MM-dd hh:mm:ss")
			};
			ajaxRequestAsync(billPurchaseAdjustBak.modulePath + '/verify_bill',data,function(result){
				if(result){
					showSuc('操作成功');
					billPurchaseAdjustBak.search();
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

billPurchaseAdjustBak.addDtl = function(){
	if(billPurchaseAdjustBak.enableEdit){
		if(billPurchaseAdjustBak.endEdit()){
			$('#dtlDataGrid').datagrid('appendRow',{});
		    var length = $('#dtlDataGrid').datagrid('getRows').length;
		    billPurchaseAdjustBak.beginEdit(length-1);
		}
	}
};

billPurchaseAdjustBak.editDtl = function(rowIndex){
	if(billPurchaseAdjustBak.enableEdit){
		if(billPurchaseAdjustBak.endEdit(rowIndex)){
		    billPurchaseAdjustBak.beginEdit(rowIndex);
		}
	}
};

billPurchaseAdjustBak.delDtl = function(){
	if(billPurchaseAdjustBak.enableEdit){
		var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
		var rowIndex;
		$.each(checkedRows, function(index, row) {
			rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
			$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
		});
	}
	
};

billPurchaseAdjustBak.beginEdit = function(editIndex){// 开始明细行编辑
	$('#dtlDataGrid').datagrid('beginEdit',editIndex);
};

billPurchaseAdjustBak.endEdit = function(){// 结束明细行编辑
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#dtlDataGrid').datagrid('validateRow', editRowIndex)){
			$('#dtlDataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

billPurchaseAdjustBak.initPage = function(){
	billPurchaseAdjustBak.addMainTab();
	billPurchaseAdjustBak.refreshTabs();
	
	
};

billPurchaseAdjustBak.addMainTab = function() {// 初始化单据查询tab
	$('#mainTab').tabs('add', {
		title : '单据列表',
		selected : false,
		closable : false,
		href : billPurchaseAdjustBak.modulePath + '/list_tabMain.htm' ,
		onLoad : function(panel) {
			pe_util.initPage('searchForm');
		}
	});
};

billPurchaseAdjustBak.refreshTabs = function() {// 切换选项卡刷新单据查询的dataGrid
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

//商品信息回调
billPurchaseAdjustBak.itemCallBack = function(row){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0 && row){
		var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
    	var edNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemNo'});
    	var edCode =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemCode'});
    	var edName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemName'});
    	var edBrandNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'brandNo'});
    	var edBrandName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'brandName'});
    	var edCategoryNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'categoryNo'});
    	var edCategoryName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'categoryName'});
    	ajaxRequestAsync( BasePath + '/common_util/getItemExtends',{itemNo:row.id},function(data){
    		if(data.rows.length > 0){
    			var item = data.rows[0];
    	    	edNo.target.val(item.itemNo);
    	    	edCode.target.val(item.code);
    	    	edName.target.val(item.name);
    	    	edBrandNo.target.val(item.brandNo);
    	    	edBrandName.target.val(item.brandName);
    	    	edCategoryNo.target.val(item.categoryNo);
    	    	edCategoryName.target.val(item.categoryName);
    		}
    	});
	}
}

//导入回调
function importCallBack(data){
	if(data.indexOf('"success":true')!=-1){
		showSuc("操作完成！");
		billPurchaseAdjustBak.loadDtlData();
	}else{
		showError("数据不合法,导入失败!");
	}
}

$(function(){
	$('#mainTab').tabs('hideHeader');
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	setTimeout(function(){
		returnTab('mainTab', '单据列表');
	},300);
	billPurchaseAdjustBak.initPage();
	billPurchaseAdjustBak.initAddClass();
});





