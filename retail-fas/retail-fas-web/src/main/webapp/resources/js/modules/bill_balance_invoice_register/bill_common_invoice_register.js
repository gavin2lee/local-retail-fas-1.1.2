var billCommonInvoiceRegister = new Object();

//是否可以对明细进行新增编辑的标识
var isEnableEdit = false;

var map = "";

var nullMessage = "不存在当前单据";
var nullCheckMessage = "请选中需要操作的单据!";
var auditMessage = "只允许审核制单状态的单据";
var backMessage = "只允许反审核确认状态的单据!";
var invalidMessage = "只允许作废确认状态的单据!";
var deleteMessage = "只允许删除制单状态的单据!";
var audMessage="确定要审核？";
var dltIsNullMsg = "发票明细为空，不能审核!";
var optBackAuditUseMsg = "你所操作的该条单据已被其他系统使用，不能进行操作！";
//单据状态(1-制单、2-确认、3-作废)
function getErrorMessage(currStatus ,operStatus,billNo,dtlTotal){
	if(currStatus && currStatus ==""){
		return nullMessage;
	}
	if(typeof operStatus == 'undefined'){
		if(currStatus != 0){
			return deleteMessage;
		}
	}
//	if(operStatus == 1){
//		if(currStatus==0){
//			return audMessage;			
//		}			
//	}
	
	if(operStatus == 1){
		if(dtlTotal <= 0){
			return dltIsNullMsg;
		}
		if(currStatus!=0){
			return auditMessage;
		}
	}
//	else if(operStatus == 0){
//		if(billCommonInvoiceRegister.checkUseFlag(billNo)){//判断明细里，是否有发票号已被mps 的购券使用过；如果被使用过则不能再反审核
//			return "发票号："+billNo+"已被使用，不能进行操作！";
//		}
//		if(currStatus!=1){
//			return backMessage;
//		}
//	}
	else if(operStatus == 0){
		if(currStatus!=1){
			return backMessage;
		}
	}
	
//	else if(operStatus == 2){
//		if(currStatus!=0){
//			return invalidMessage;
//		}
//	}
//	else if(operStatus == 1){
//		if(currStatus!=1&&currStatus!=2){
//			return backMessage;
//		}
//	}
	return "";
}


function getBatchErrorMessage(checkRows ,operStatus){
	if(checkRows.length ==0){
		return nullCheckMessage;
	}
	var errorMessage = "";
	$.each(checkRows, function(index,item){
//		//根据发票单据号查询发票明细的记录数
//		var dtlTotal = getDtlTotalByBillNo(item.billNo);
		errorMessage = getErrorMessage(item.status,operStatus,item.billNo,checkRows.length);
		if(errorMessage!=""){
			return false;
		}
	});
	return errorMessage;
}

//根据发票单据号查询发票明细的记录数并返回记录数
function getDtlTotalByBillNo(billNo){
	var params = {billNo : billNo};
	var total = 0;
	ajaxRequestAsync( BasePath + '/bill_common_register_dtl/getDtlTotalByBillNo',params,function(data){
		if(data){
			total = data;
		}
	});
	return total;
}

//当前用户
var currentUser;

//单据查询当前选中行的索引
var curRowIndex = -1;

// 模块路径 
billCommonInvoiceRegister.modulePath = BasePath + '/bill_balance_invoice_register';

billCommonInvoiceRegister.dtlmodulePath = BasePath + '/bill_common_register_dtl';

//invoiceRegisterBase  Tab路径
billCommonInvoiceRegister.invoiceApplyBaseTabUrl = billCommonInvoiceRegister.modulePath + '/bill_common_register_dtl';

//balanceSource  Tab路径
billCommonInvoiceRegister.balanceSourceTabUrl = billCommonInvoiceRegister.modulePath + '/bill_common_balance_source';
var hrefUrl = "";
//初始化
$(function() {
	billCommonInvoiceRegister.addMainTab();
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	billCommonInvoiceRegister.initDtlTab();
	$('#mainTab').tabs('hideHeader');
	setTimeout(function(){
		billCommonInvoiceRegister.initbalanceStatus();
		billCommonInvoiceRegister.initbalanceTypeStatus();
		billCommonInvoiceRegister.initpreInvoice();
		billCommonInvoiceRegister.initinvoiceType();
		billCommonInvoiceRegister.initAddClass();
		isEnableEdit = true;
		$('#toolbarDtl').toolbar('enableAll');
		returnTab('mainTab', '单据查询');
		billCommonInvoiceRegister.addDtl();
		
		//以超链接方式直接访问详细页面
		var billNoMenu = $('#billNoMenu').val();
		if(billNoMenu != null && billNoMenu != ''){
			ajaxRequestAsync( BasePath + '/bill_balance_invoice_register/list.json',{billNo:billNoMenu},function(data){
				var obj = data.rows[0];
				if(obj != null && obj != ''){
					billCommonInvoiceRegister.loadDetail(0, obj);
				} else {
					alert("单号："+ billNoMenu +"有误！", 1);
				}
			});
		}
	},300);
	billCommonInvoiceRegister.curRowIndex = -1;
	
	
	
});


//不可编辑状态
//billCommonInvoiceRegister.disableMainInfo = function() {
//};

billCommonInvoiceRegister.search = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/bill_balance_invoice_register/register_list';
	$("#dataGridCommonInvoice").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
	$("#dataGridCommonInvoice").datagrid('options').url = queryMxURL;
	$("#dataGridCommonInvoice").datagrid('load');
};


//初始化单据查询tab
billCommonInvoiceRegister.addMainTab = function() {
	if($('#mainTab') != 'undefined' && $('#mainTab') !== null) {
		var url =  BasePath + '/invoice_register/area/bill_common_invoice_register';
		if($('#isHQ').val() == 'true'){
			url =  BasePath + '/invoice_register/hq/bill_common_invoice_register';
		}
		$('#mainTab').tabs('add', {
			title : '单据查询',
			selected : false,
			closable : false,
			href : url,
			onLoad : function(panel) {
				billCommonInvoiceRegister.initbalanceTypeStatus1();
				$("#searchIsHQ").val($('#isHQ').val());
			}
		});
	};
};

billCommonInvoiceRegister.initClass=function(){
	$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
	$("#mainDataForm").find("input[class*=easyui-combobox]").combobox({disabled: true});
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
	$("#mainDataForm").find("i").hide();
//	$('#remark').removeAttr("readonly").removeClass("readonly");
};

billCommonInvoiceRegister.initAddClass = function() {  
	$("#mainDataForm").find("input[class*=easyui-combobox]").combobox('enable');
	$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('enable');
	$("#mainDataForm").find("input").removeAttr("readonly").removeClass("readonly");
	$("#mainDataForm").find("input[iptSearch]").attr("readonly",true);
	$('#billNoId').attr("readonly",true).addClass("readonly");
	$('.disableEdit').attr("readonly",true).addClass("readonly");
	$("#billDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
	$("#mainDataForm").find("i").show();
	$("#balanceTypeId").removeAttr("readonly").removeClass("readonly");
	$("#balanceTypeId,#preInvoice,#invoiceType").combobox({disabled: false});
	$("#status").combobox({disabled: true});
	$('#status').combobox('setValue', '0');
	$('#invoiceType').combobox('setValue', '1');
	$('#sourceNoId').iptSearch('enable');
};

//新增
billCommonInvoiceRegister.add = function(){
	billCommonInvoiceRegister.clearData();
	billCommonInvoiceRegister.clearDtlData();
	billCommonInvoiceRegister.initAddClass();
	$("#salerName").combogrid("enable");
	$('#buyerName').customerInvoiceInfo('enable');
	setTimeout(function(){
		billCommonInvoiceRegister.addDtl();
	},500);
	isEnableEdit = true;
	$('#toolbarDtl').toolbar('enableAll');
};

billCommonInvoiceRegister.save = function(){
	var status = $('#status').combobox('getValue');
	if(isEnableEdit && status == 0){
		if(billCommonInvoiceRegister.doValidate()){	
			var pkVal = $('#id').val();
			var salerNo = $('#salerNo').val();
			var buyerNo =$('#buyerNo').val();
			if(salerNo == buyerNo){
				showWarn('公司名称与客户名称相同，请重新选择！');
				return ;
			}
			var invoiceAmount = $("#amount").val();
			var totalAmount = $("#totalAmount").val();
//			if(parseFloat(invoiceAmount) == 0 || invoiceAmount == ''){
//				showWarn('发票总金额为0，不允许保存');
//				return ;
//			}
			//只在新增保存时弹出提示
			if(totalAmount != "" && parseFloat(invoiceAmount) != parseFloat(totalAmount) && pkVal == ''){
				$.messager.confirm("确认","发票总金额与源单据汇总金额不相等\n是否保存?",function(r) {
					if (r) {
						billCommonInvoiceRegister.doPost();
					}
				});
			}else{
				billCommonInvoiceRegister.doPost();
			}
//			}
		}
	};
};


// 新增/修改保存
billCommonInvoiceRegister.doPost = function(){
	if(billCommonInvoiceRegister.endEdit()){
		var pkVal = $('#id').val();
		var url = billCommonInvoiceRegister.modulePath + '/addBillCommonInvoiceRegister';
		if(pkVal !=''){// 修改 
			url = billCommonInvoiceRegister.modulePath + '/put';
		}
		$('#mainDataForm').form('submit',{
			url : url,
			onSubmit:function(param){
				param.status = 0;
				if(pkVal ==""){
					param.createUser = currentUser.username;
					param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					if(map != ""){
						param.checkedRows = JSON.stringify(map);
					}
				}else{
					param.updateUser = currentUser.username;
					param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
				}
			},
			success:function(data){
				if(data){
					showSuc('保存成功');
					var jsonData = JSON.parse(data);
					$("#balanceTypeId,#preInvoice,#invoiceType").combobox({disabled: true});
					$("#salerName").combogrid("disable");
					$('#buyerName').customerInvoiceInfo('disable');
					billCommonInvoiceRegister.saveCommonInvoiceRegisterDtl(jsonData.billNo);
					billCommonInvoiceRegister.loadData(jsonData);
					//明细加载
					billCommonInvoiceRegister.loadCommonInvoiceRegisterDtl(jsonData.billNo);
					billCommonInvoiceRegister.search();
					billCommonInvoiceRegister.initClass();
				}else{
					showError('保存失败');
				}
			}
		});
	}
};

// 修改保存
/*billCommonInvoiceRegister.doPut = function(){
	$('#mainDataForm').form('submit',{
		url : billCommonInvoiceRegister.modulePath + '/put',
		onSubmit:function(param){
			param.status = 0;
			param.updateUser = currentUser.loginName;
			param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
				billCommonInvoiceRegister.loadData(jsonData); 
				billCommonInvoiceRegister.saveCommonInvoiceRegisterDtl(jsonData.billNo);
//				billCommonInvoiceRegister.add();
			}else{
				showError('保存失败');
			}
		}
	});
};

billCommonInvoiceRegister.audit = function(){
	alert("!!!!!!!!!!");
};*/

//清空数据
billCommonInvoiceRegister.clearData = function(){
//	$('#mainDataForm').find("input[type!=hidden]").val("");
	$('#mainDataForm').find("input").val("");
	$('#mainDataForm').find("textarea").val("");
};

//可编辑状态
billCommonInvoiceRegister.enableMainInfo = function(){
//	$("#mainDataForm").find("input").removeAttr("readonly").removeClass("readonly");
	$('#remark').removeAttr("readonly").removeClass("readonly");
};

//点击切换到明细
billCommonInvoiceRegister.loadDetail = function(rowIndex, rowData) {
	billCommonInvoiceRegister.curRowIndex = rowIndex;
	var url = billCommonInvoiceRegister.modulePath + '/get';
	var params = {id : rowData.id};
	// 填充主表
	$.getJSON(url, params, function(resultData) {// resultData-服务器回传的数据
		billCommonInvoiceRegister.loadData(resultData); 
	});
	billCommonInvoiceRegister.enableMainInfo();
	//明细加载
	billCommonInvoiceRegister.loadCommonInvoiceRegisterDtl(rowData.billNo);
	$("#balanceTypeId,#preInvoice,#invoiceType").combobox({disabled: true}); 
	$("#salerName").combogrid("disable");
	$('#buyerName').customerInvoiceInfo('disable');
	billCommonInvoiceRegister.initbalanceStatus();
	billCommonInvoiceRegister.initbalanceTypeStatus();
	billCommonInvoiceRegister.initpreInvoice();
	billCommonInvoiceRegister.initinvoiceType();
	var status = rowData.status;
	billCommonInvoiceRegister.initClass();
	if(rowData.preInvoice == '1'){
		$("#dataGridCommonInvoiceDtl").datagrid('hideColumn',"estimatedAmount");
	}
	if(status == 0){
		isEnableEdit = true;
//		//根据结算公司编号查询明细大类
//		$('#billDate').datebox('enable');
//		$("#billDate").removeAttr("readonly").removeClass("readonly");
		$('#toolbarDtl').toolbar('enableAll');
	}else{
		isEnableEdit = false;//状态为1时，为确认状态，不能删除或新增明细
		$('#toolbarDtl').toolbar('disabledALl');
	}
	returnTab('mainTab', '单据明细');
	
};

//加载明细行
billCommonInvoiceRegister.loadCommonInvoiceRegisterDtl = function(billNo){
	var params = {billNo : billNo};
	var url = billCommonInvoiceRegister.dtlmodulePath + '/find_list';
    $( '#dataGridCommonInvoiceDtl').datagrid( 'options').queryParams= params;
    $( '#dataGridCommonInvoiceDtl').datagrid( 'options').url=url;
    $( '#dataGridCommonInvoiceDtl').datagrid( 'load' );
};

//加载单据信息
billCommonInvoiceRegister.loadData = function(resultData){
	$('#mainDataForm').form('load', resultData);
	// 底部单据状态显示栏
	$('#status').html(resultData.status);
	$('#createUser').html(resultData.createUser);
	$('#createTime').html(resultData.createTime);
	$('#auditor').html(resultData.auditor);
	$('#auditTime').html(resultData.auditTime);
};


billCommonInvoiceRegister.doValidate = function(){ // 验证
	if($('#mainDataForm').form('validate')){
		if(billCommonInvoiceRegister.endEdit()){
			return true;
		}
	}
	return false;
};

billCommonInvoiceRegister.endEdit = function(){// 结束明细行编辑
	var rows = $('#dataGridCommonInvoiceDtl').datagrid('getRows');
	var flag = true;
	$.each(rows,function(index, item){
		var rowIndex =  $('#dataGridCommonInvoiceDtl').datagrid('getRowIndex',item);
		if(!$('#dataGridCommonInvoiceDtl').datagrid('validateRow', rowIndex)){
			flag = false;
			return false;
		}	
		$('#dataGridCommonInvoiceDtl').datagrid('endEdit',rowIndex);
	});
	return flag;
	/*
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#dataGridCommonInvoiceDtl').datagrid('validateRow', editRowIndex)){
			$('#dataGridCommonInvoiceDtl').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;*/
};

billCommonInvoiceRegister.saveCommonInvoiceRegisterDtl = function(commbillNo){// 保存明细 
	var insertedData = $('#dataGridCommonInvoiceDtl').datagrid('getChanges','inserted');
	var updatedData = $('#dataGridCommonInvoiceDtl').datagrid('getChanges','updated');
	var deletedData = $('#dataGridCommonInvoiceDtl').datagrid('getChanges','deleted');
	var billNo = $('#billNoId').val();
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
	ajaxRequestAsync(billCommonInvoiceRegister.dtlmodulePath + '/save',data,function(){
		if(data){
		  	$('#dataGridCommonInvoiceDtl').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
};

billCommonInvoiceRegister.selectBrand = {
	    clickFn:function(){
	    	dgSelector({
	    		title : '选择品牌',
	    		width : 500,
	    		height : 'auto',
	    		href : BasePath + '/common_util/doSelect?multiSelect=false&selectUrl=getPageBrand&no=brandNo&name=name',
	    		fn : function(data, rowIndex) {
	    			billCommonInvoiceRegister.selectorCallback(data, 
	    					'code','name','brandNo','brandName');
	    		}
	    	});
	    }
	};


//billCommonInvoiceRegister.financialCategory = {
//    clickFn:function(){
//    	dgSelector({
//    		title : '选择财务大类',
//    		width : 500,
//    		height : 'auto',
//    		href : BasePath + '/common_util/doSelect?selectUrl=getPageFasCategory&no=financialCategoryNo&name=name',
//    		fn : function(data, rowIndex) {
//    			billCommonInvoiceRegister.selectorCallback(data, 
//    					'code','name','categoryNo','categoryName');
//    		}
//    	});
//    }
//};

billCommonInvoiceRegister.getRowSelect = function(select, noField, nameField, required){
	var selectObj = _self.rowSelect[select];
	selectObj.noField = noField;
	selectObj.nameField = nameField;
	if(required){
		selectObj.validatebox = {
	    	options:{
	    		required:true
	    	}
	    };
	}
	return  selectObj;
};
/*billCommonInvoiceRegister.selectCompany  = {// 明细行选择公司
	    clickFn:function(){
	    	dgSelector({
	    		title:"选择公司",
	    		width : 580,
	    		height : 450,
	    		href : BasePath + "/common_util/toSearchCompany",
	    		fn : function(data, rowIndex) {
	    			billCommonInvoiceRegister.selectorCallback(data,'companyNo','name','salerNo','salerName');
	    		}
	    	});
	    }
	};


billCommonInvoiceRegister.selectMall = {// 明细行选择商场
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择商场",
				href : BasePath + "/common_util/toSearchMall",
	    		fn : function(data, rowIndex) {
	    			billCommonInvoiceRegister.selectorCallback(data,'mallNo','name','buyerNo','buyerName');
	    		}
	    	});
	    }
	};

billCommonInvoiceRegister.selectCustomer = {// 明细行选择客户
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择客户",
				href : BasePath + "/common_util/toSearchCustomer",
	    		fn : function(data, rowIndex) {
	    			billCommonInvoiceRegister.selectorCallback(data,'code','shortName','buyerNo','buyerName');
	    		}
	    	});
	    }
	};

billCommonInvoiceRegister.selectAllCustomer = {// 明细行选择客户
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择客户",
				href : BasePath + "/common_util/toSearchAllCustomer",
	    		fn : function(data, rowIndex) {
	    			billCommonInvoiceRegister.selectorCallback(data,'code','name','buyerNo','buyerName');
	    		}
	    	});
	    }
};
*/
billCommonInvoiceRegister.selectorCallback = function(data,value,text,valueField,textField){// 选择后回调
	var dataGrid = $("table[id*=dataGrid]");
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	console.log("the editIndex is "+editIndex+" , the valueField is "+valueField);
	var valueEd = dataGrid.datagrid('getEditor', {
		'index' : editIndex,		
		'field' : valueField
	});
	var textEd = dataGrid.datagrid('getEditor', {
		'index' : editIndex,
		'field' : textField
	});
	
	console.log("the valueEd = "+valueEd);
	valueEd.target.val(data[value]);
	textEd.target.val(data[text]);
};

/**
总部批发结算
地区批发结算单
地区间结算
商场结算单
独立店铺销售订单
员购订单
团购订单(POS)
团购出库单(货品)
索赔单
**/
var balanceTypeStatus = [{'value':'5', 'text':'地区间结算'},{'value':'7', 'text':'批发结算'},
                        {'value':'8', 'text':'内购结算'},{'value':'10', 'text':'地区门店结算'}, {'value':'11', 'text':'地区其他出库结算'},
                        /*{'value':'12', 'text':'独立店铺结算'},{'value':'23', 'text':'GMS团购/内购结算'},*/{'value':'99', 'text':'开票申请空白单'}];

billCommonInvoiceRegister.balanceTypeformatter = function(value, rowData, rowIndex) {
	var balanceType = $("#balanceTypeId").combobox("getValue");
	if(balanceType == '10'){
		return '地区门店结算';
	}else{
	    if (value == 0) {
	        return;
	    }
	    for (var i = 0; i < balanceTypeStatus.length; i++) {
	        if (balanceTypeStatus[i].value == value) {
	            return balanceTypeStatus[i].text;
	        }
	    }
	}
};

var statusformatter =[{'value':'0', 'text': '制单'},{'value':'1', 'text':'确认'}];
billCommonInvoiceRegister.statusformatter= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < statusformatter.length; i++) {
        if (statusformatter[i].value == value) {
            return statusformatter[i].text;
        }
    }
};

var preInvoiceformatter =[{'value':'1', 'text': '否'},{'value':'2', 'text':'预开票'},{'value':'3', 'text':'无票收入'}];

billCommonInvoiceRegister.preInvoiceformatter= function(value, rowData, rowIndex) {
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

billCommonInvoiceRegister.invoiceTypeformatter= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < invoiceTypeformatter.length; i++) {
        if (invoiceTypeformatter[i].value == value) {
            return invoiceTypeformatter[i].text;
        }
    }
};

billCommonInvoiceRegister.initbalanceTypeStatus=function(){
	if($('#isHQ').val() == 'true'){
		balanceTypeStatus =[{'value':'2', 'text':'总部地区结算'},{'value' : '7','text' : '批发结算'},{'value' : '14','text' : '总部其他出库结算'},{'value':'99', 'text':'开票申请空白单'}];
	}
	$('#balanceTypeId').combobox({
		width:160,
		data : balanceTypeStatus,
		valueField : 'value',
		textField : 'text',
		editable : false,
		required : true,
		onChange : function(newValue, oldValue) {
	    	$('#sourceNoId').val("");
	    	hrefUrl = BasePath + '/bill_balance_invoice_register/search_invoice_apply_or_order';
	    	$("#salerNo").val("");
	    	$("#buyerNo").val("");
	    	$("#amount").val("");
	    	$("#salerName").combogrid('setValue',"");
			$("#buyerName").customerInvoiceInfo('setValue',"");
		}
	});  
};

billCommonInvoiceRegister.initbalanceTypeStatus1=function(){
	$('#searchbalanceType').combobox({
		data : balanceTypeStatus,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

billCommonInvoiceRegister.initbalanceStatus=function(){
	$('#status').combobox({
		width:160,
		data : statusformatter,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

billCommonInvoiceRegister.initpreInvoice=function(){
	$('#preInvoice').combobox({
		width:160,
		data : preInvoiceformatter,
		valueField : 'value',
		textField : 'text',
		editable : false,
		onChange : function(newValue, oldValue) {
			if(newValue == '1'){
//				$("#dataGridCommonInvoiceDtl").datagrid('removeEditor', 'estimatedAmount');
				$("#dataGridCommonInvoiceDtl").datagrid('hideColumn',"estimatedAmount");
			}else{
				$("#dataGridCommonInvoiceDtl").datagrid('showColumn',"estimatedAmount");
//				$("#dataGridCommonInvoiceDtl").datagrid('addEditor', 
//						{field : 'estimatedAmount', editor:{type:'numberbox',options:{required:false,precision:2}}});
			}
		}
	});  
};

billCommonInvoiceRegister.initinvoiceType=function(){
	$('#invoiceType').combobox({
		width:160,
		data : invoiceTypeformatter,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

//双击修改明细行
billCommonInvoiceRegister.editRow = function(rowIndex, row) {
	if(isEnableEdit){
		if(billCommonInvoiceRegister.endEdit()){
			//根据结算公司编号查询明细大类
			//billCommonInvoiceRegister.financialCategory($("#salerNo").val());
			billCommonInvoiceRegister.beginEdit(rowIndex);
		}
	}
};

billCommonInvoiceRegister.clear = function(){// 清空
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
	$("#searchIsHQ").val($('#isHQ').val());
};

billCommonInvoiceRegister.addDtl = function(row){// 新增明细
	if(isEnableEdit){
		if(row){
			$('#dataGridCommonInvoiceDtl').datagrid('appendRow',row);
			 var length = $('#dataGridCommonInvoiceDtl').datagrid('getRows').length;
			 billCommonInvoiceRegister.beginEdit(length-1);
		}else{
			if(billCommonInvoiceRegister.endEdit()){
				$('#dataGridCommonInvoiceDtl').datagrid('appendRow',{taxRate:'0.17'});
			    var length = $('#dataGridCommonInvoiceDtl').datagrid('getRows').length;
			    billCommonInvoiceRegister.beginEdit(length-1);
			}
		}
		
		//billCommonInvoiceRegister.financialCategory();
	    return true;
	}
	return false;
};

//billCommonInvoiceRegister.editDtl = function(rowIndex) {// 编辑明细
//	if(isEnableEdit){
//		if (billCommonInvoiceRegister.endEdit()) {
//			//根据结算公司编号查询明细大类
//			billCommonInvoiceRegister.financialCategory($("#salerNo").val());
//			billCommonInvoiceRegister.beginEdit(rowIndex);
//			return true;
//		}
//	}
//};

billCommonInvoiceRegister.delDtl = function(){// 删除明细
	if(isEnableEdit){
		var checkedRows = $('#dataGridCommonInvoiceDtl').datagrid('getChecked');
		var rowIndex;
		$.each(checkedRows, function(index, row) {
			rowIndex = $('#dataGridCommonInvoiceDtl').datagrid('getRowIndex',row);
			$('#dataGridCommonInvoiceDtl').datagrid('deleteRow',rowIndex);
			billCommonInvoiceRegister.setAllAmount();
		});
	}
};

billCommonInvoiceRegister.clearDtlData = function(){// 清空明细数据
	
	if($('#dataGridCommonInvoiceDtl').length > 0){
		deleteAllGridCommon('dataGridCommonInvoiceDtl');
		$('#dataGridCommonInvoiceDtl').datagrid('acceptChanges');
	}
};

billCommonInvoiceRegister.beginEdit = function(editIndex){// 开始明细行编辑
	$('#dataGridCommonInvoiceDtl').datagrid('beginEdit',editIndex);
	var edAmount = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editIndex,field:'invoiceAmount'});
	$(edAmount.target).bind('blur',function(){
		billCommonInvoiceRegister.setAllAmount();
	});
	var edTaxRate = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editIndex,field:'taxRate'});
	$(edTaxRate.target).bind('blur',function(){
		billCommonInvoiceRegister.setAllAmount();
	});
	var edQty = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editIndex,field:'qty'});
	$(edQty.target).bind('blur',function(){
		billCommonInvoiceRegister.setAllAmount();
	});
	var noTaxAmountEd = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editIndex,field:'noTaxAmount'});
	$(noTaxAmountEd.target).bind('blur',function(){
		$(edAmount.target).unbind();
		$(edTaxRate.target).unbind();
	});
	var taxAmountEd = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editIndex,field:'taxAmount'});
	$(taxAmountEd.target).bind('blur',function(){
		$(edAmount.target).unbind();
		$(edTaxRate.target).unbind();
	});
};

billCommonInvoiceRegister.setAllAmount = function(){// 设置总金额
	var rows = $("#dataGridCommonInvoiceDtl").datagrid('getRows');
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editRowIndex = -1;
	var allAmount = 0;
	var totalNoTaxAmount = 0;
	var totalTaxAmount = 0;
	var totalQty = 0;
	if(editTr.length > 0){
		editRowIndex = editTr.attr("datagrid-row-index");
		var edAmount = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editRowIndex,field:'invoiceAmount'});
		var edTaxRate = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editRowIndex,field:'taxRate'});
		var edTaxAmount = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editRowIndex,field:'taxAmount'});
		var edNoTaxAmount = $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editRowIndex,field:'noTaxAmount'});
		var edQty =  $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editRowIndex,field:'qty'});
//		var price =  $("#dataGridCommonInvoiceDtl").datagrid('getEditor',{index:editRowIndex,field:'price'});
		var amount = $(edAmount.target).numberbox('getValue');
		var taxRate = $(edTaxRate.target).numberbox('getValue');
		var qty = $(edQty.target).numberbox('getValue');
//		if(amount == 0){
//			showWarn("发票总金额为0，不允许登记发票！");
//			return ;
//		}
		if(''!=amount && ''!=taxRate ){
			edNoTaxAmount.target.numberbox('setValue',(parseFloat(amount) / (1 + parseFloat(taxRate))).toFixed(2));
			edTaxAmount.target.numberbox('setValue',(parseFloat(amount) - parseFloat(amount) / (1 + parseFloat(taxRate))).toFixed(2));
			allAmount += parseFloat(amount);
		}
		if('' != qty){
			totalQty += parseInt(qty);
		}
//		if(''!=amount && ''!=qty && qty > 0){
//			price.target.val((parseFloat(amount)/qty).toFixed(2));
//		}else{
//			price.target.val(parseFloat(amount));
//		}
	} 
	for(var i =0; i < rows.length; i++){
		var rowIndex = $("#dataGridCommonInvoiceDtl").datagrid('getRowIndex',rows[i]);
		var amount = rows[i].invoiceAmount;
		var taxAmount = rows[i].taxAmount;
		var noTaxAmount = rows[i].noTaxAmount;
		var qty = rows[i].qty;
		
		if(editRowIndex != rowIndex){
			if(''!=amount){
				allAmount += parseFloat(amount);
			}
			if(''!=taxAmount){
				totalTaxAmount += parseFloat(taxAmount);
			}
			if(''!=noTaxAmount){
				totalNoTaxAmount += parseFloat(noTaxAmount);
			}
			if('' !=qty){
				totalQty += parseInt(qty);
			}
		}
	}
	$('#dataGridCommonInvoiceDtl').datagrid('reloadFooter',[
	               	                              	{invoiceCode: '合计', invoiceAmount: allAmount.toFixed(2),
	               	                              	noTaxAmount: totalNoTaxAmount.toFixed(2),
	               	                              	taxAmount: totalTaxAmount.toFixed(2),qty: totalQty}
	               	                              	]);
	$('#amount').val(allAmount.toFixed(2));
};

billCommonInvoiceRegister.del = function(){// 删除
	var sta = $('#status').combobox('getValue');
	var billNo = $('#billNoId').val();
	if(sta != "0"){
		showWarn(deleteMessage);
		return ;
	}
	if(billNo == ""){
		showWarn("不存在当前单据，不能删除！");
		return ;
	}
	$.messager.confirm("确认","你确定要删除该条单据?",function(r) {
		if (r) {
			var url =  billCommonInvoiceRegister.modulePath + '/deleteInvoiceRegister';
			var id = $('#id').val();
			var balanceType = $('#balanceTypeId').combobox("getValue");
			var params = {idList : id+','+billNo+','+balanceType};
			ajaxRequestAsync(url,params,function(data){
				if(data){
					showSuc('删除成功');
					billCommonInvoiceRegister.add();
					billCommonInvoiceRegister.search();
					billCommonInvoiceRegister.setAllAmount();
				}else{
					showError('删除失败');
				}
			});
		}
	});
};

billCommonInvoiceRegister.batchDel = function(){// 批量删除   删除主明细
	var checkedRows = $('#dataGridCommonInvoice').datagrid('getChecked');
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
		if(item.status != "0"){
			showWarn("单据："+item.billNo+"，已审核确认不可删除！" );
			return false;
		 }
	}
	$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
		if (r) {
			var url =  billCommonInvoiceRegister.modulePath + '/deleteInvoiceRegister';
			var idList ="";
			$.each(checkedRows, function(index, row) {
				idList+=row.id+','+row.billNo+','+row.balanceType+";";	
//				idList+=row.id;
			});
			var params = {idList : idList.substring(0, idList.length-1)}; //{id:idList};//
			ajaxRequestAsync(url,params,function(count){
				if(count){
					showSuc('删除成功');
					billCommonInvoiceRegister.search();
				}else{
					showError('删除失败');
				}
				
			});
		}
	});
};

billCommonInvoiceRegister.batchOperate = function(status){// 批量 审核-作废
	var checkedRows = $('#dataGridCommonInvoice').datagrid('getChecked');
	var errorMessage = getBatchErrorMessage(checkedRows,status);
	if(errorMessage!=""){
		showWarn(errorMessage);
		return ;
	}	
	var message = "";
	var auditor = null;
	var auditTime = null;
	if(status == 0){
		message = "你确定要反审核该条单据?";
	}else if(status == 1){
		 message = "你确定要审核该条单据?";
		 auditor = currentUser.username;
		 auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	}else if(status == 2){
		 message = "你确定要作废该条单据?";
	}
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			var flag = false;
			
			$.each(checkedRows, function(index, row) {
				if(null == row.invoiceCode || '' == row.invoiceCode ||null == row.invoiceNo || '' == row.invoiceNo){
					flag = true;
					return;
				}
				row.status = status;
				row.auditor = auditor;
				row.auditTime = auditTime;
				row.amount = null;//金额不修改
			});
			
			if(flag){
				showWarn('发票编号或发票号不能为空。');
				return;
			}
			
			var data = {
					updated : JSON.stringify(checkedRows),
			};
			ajaxRequestAsync(billCommonInvoiceRegister.modulePath  + '/save',data,function(result){
				if(result){
					showSuc('操作成功');
					billCommonInvoiceRegister.search();
					$("#dataGridCommonInvoice").datagrid('load');
				    $( '#dataGridCommonInvoiceDtl').datagrid( 'load' );
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

//导出
exportExcel=function(){
	$.fas.exportExcel({
		dataGridId : "dataGridCommonInvoiceDtl",
		exportUrl : "/bill_common_register_dtl/do_fas_export?billNo="+$("#billNoId").val(),
		exportTitle : "发票登记明细信息导出"
	});
//	exportExcelBaseInfo('dataGridCommonInvoiceDtl','/bill_common_register_dtl/export.htm?billNo='+$("#billNoId").val(),'发票登记明细信息导出');
};


//导出列表
exportList=function(){
	$.fas.exportExcel({
		dataGridId : "dataGridCommonInvoice",
		exportUrl : "/bill_balance_invoice_register/do_fas_export",
		exportTitle : "发票登记列表导出"
	});
};

billCommonInvoiceRegister.operate = function(status){// 审核 -作废  $('#status').val()
	var billNo = $("#billNoId").val();
	
	if(billNo == '' || $("tr[class*=datagrid-row-editing]").length > 0){
		showWarn("请先保存后再继续操作!");
		return ;
	}
	var dtlLength = $("#dataGridCommonInvoiceDtl").datagrid('getRows').length;
	var errorMessage = getErrorMessage($('#status').combobox('getValue'),status,billNo,dtlLength);
	if(errorMessage!=""){
		showWarn(errorMessage);
		return ;
	}
	if($('#mainDataForm').form('validate')){
		var message = "";
		if(status == 0){
			message = "你确定要反审核该条单据?";
		}else if(status == 1){//确认后，修改删除明细标识
			 message = "你确定要审核该条单据?";
		}else if(status == 2){
			 message = "你确定要作废该条单据?";
		}
		$.messager.confirm("确认",message,function(r) {
			if (r) {
				var dtlData = $('#dataGridCommonInvoiceDtl').datagrid('getRows');
				var flag = false;
				$.each(dtlData,function(index, item){
					if(null == item.invoiceCode || '' == item.invoiceCode ||null == item.invoiceNo || '' == item.invoiceNo){
						flag = true;
					}
				});
				if(flag){
					showWarn('发票编号或发票号不能为空。');
					return;
				}
				$('#status').val(status);
				$('#status').combobox('setValue', status);
				$('#mainDataForm').form('submit',{
					url : billCommonInvoiceRegister.modulePath  + '/put',
					onSubmit:function(param){
						 param.status = $('#status').combobox('setValue', status);  //$('#statu s').val(status);  
						 param.auditor = currentUser.username;
						 param.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					},
					success:function(data){
						if(data){
							showSuc('操作成功');
							var jsonData = JSON.parse(data);
							if(jsonData.status == 0){
								isEnableEdit = true;
								$('#toolbarDtl').toolbar('enableAll');
							}else{
								isEnableEdit = false;
								$('#toolbarDtl').toolbar('disabledALl');
							}
							billCommonInvoiceRegister.loadData(jsonData); 
							//明细加载
							billCommonInvoiceRegister.loadCommonInvoiceRegisterDtl(jsonData.billNo);
//								$("#dataGridCommonInvoiceDtl").datagrid('load');
							billCommonInvoiceRegister.search();
						}else{
							showError('操作失败');
						}
					}
				});
			}
		});
	}
};


//初始化明细Tab
billCommonInvoiceRegister.initDtlTab = function() {
	billCommonInvoiceRegister.addDtlTab('基本信息',billCommonInvoiceRegister.invoiceApplyBaseTabUrl);
	billCommonInvoiceRegister.addDtlTab('源单据信息',billCommonInvoiceRegister.balanceSourceTabUrl);
	$('#dtlTab').tabs(
			{
				onSelect : function(title) {	
//					var tab = $("#dtlTab").tabs("getTab", title);
//					if ('基本信息' == title) {						
//						billCommonInvoiceRegister.loadDtlDataGrid('','',billCommonInvoiceRegister.modulePath,'');
//					}else 
						if ('源单据信息' == title) {
						var billNo = $('#billNoId').val();
						var balanceType = $("#balanceTypeId").combobox("getValue");
						if(billNo && billNo != "" && balanceType &&balanceType !="") {
							var params = {invoiceRegisterNo : billNo, balanceType:balanceType};
							var url = BasePath + '/bill_balance_invoice_apply/list.json';
//							if(balanceType== '9' ){//员购 
//								url = BasePath +  '/bill_balance_invoice_apply/getOrderBillBalanceByInvoiceNo';
//								params = {invoiceNoFlag : 1 ,billNo : billNo,businessTypeStr : 4};
//							}else if(balanceType == '12'){//独立店铺
//								url = BasePath +  '/bill_balance_invoice_register/query_alone_saler_detail';
//								params = {invoiceNoFlag : 1 ,invoiceNo : billNo};
//							}else 
//								if(balanceType== '8' ){
//								url = BasePath +  '/bill_balance_invoice_apply/getOrderBillBalanceByInvoiceNo';
//								params = {invoiceNoFlag : 1 ,billNo : billNo,invoiceNo : billNo,businessTypeStr : 3};
//							}
							setTimeout(function(){
								$( '#invoiceSourceDataGrid').datagrid( 'options').queryParams= params;
							    $( '#invoiceSourceDataGrid').datagrid( 'options').url=url;
							    $( '#invoiceSourceDataGrid').datagrid( 'load' );
							},300);
						    
						}
					}
				}
			});
	returnTab('dtlTab', '基本信息'); 
};

//添加明细Tab
billCommonInvoiceRegister.addDtlTab = function(title, href) {
	$('#dtlTab').tabs('add', {
		title : title,
		selected : false,
		closable : false,
		href : href,
		onLoad : function(panel) {
			if("基本信息" == title){
				billCommonInvoiceRegister.loadBillBalance();
			}
		}
	});
};

billCommonInvoiceRegister.loadDtlDataGrid=function(billNo,dataGrid,urlStr){
	if(billNo==0){
     	return ;
	}
	var params = {billNo : billNo};
	var url = urlStr + '/list.json';
    $( '#'+dataGrid).datagrid( 'options').queryParams= params;
    $( '#'+dataGrid).datagrid( 'options').url=url;
    $( '#'+dataGrid).datagrid( 'load' );
};

billCommonInvoiceRegister.loadBillBalance=function(){
	$("#sourceNoId").iptSearch({
		disabled : false,
		clickFn: function () {
			var searchbalanceType = $("#balanceTypeId").combobox("getValue");
			if(searchbalanceType == ""){
				showWarn("请先选择源单类型!");
				return ;
			}
			var tempType = 1;
			var title = "选择开票申请单";
			var urlStr = BasePath +"/bill_balance_invoice_apply/getByPage";
//			if(searchbalanceType == '9'){//员购
//				urlStr = BasePath +"/bill_balance_invoice_apply/getOrderBillBalance";
//				title = "选择销售订单";
//				tempType = 2;
//			}else if(searchbalanceType == '12'){//独立店铺
//				urlStr = BasePath +"/bill_balance_invoice_register/query_alone_saler_detail";
//				title = "选择销售订单";
//				tempType = 2;
//			}
			ygDialog({
				title : title,
				href : hrefUrl+"?balanceType="+searchbalanceType,
				width : 700,
				height : 500,
				onLoad : function(win, content){
					$("#salerNameId").combogrid('setValue', $("#salerName").combogrid('getValue'));
					$("#buyerNameIds").customerInvoiceInfo('setValue',$("#buyerName").customerInvoiceInfo("getValue"));
					$("#salerNoIds").val($("#salerNo").val());
					$("#buyerNoIds").val($("#buyerNo").val());
					var _this = $(this);
					$("#btn-search1-apply", _this.contents()).on("click", function() {
						billCommonInvoiceRegister.searchSourceInfo(urlStr);
					});
					$("#btn-clean1-apply", _this.contents()).on("click", function() {
						 $("#subSearchForm").form("clear");
						 $("#salerNoIds").val("");
						 $("#buyerNoIds").val("");
					});
					$("#btn-confirm1-apply", _this.contents()).on("click", function() {
						var checkedRows = $("#searchSourceDG").datagrid("getChecked");
						if(checkedRows.length > 0){
							billCommonInvoiceRegister.confirmSelectInfo(win,tempType,checkedRows,searchbalanceType);
						}
					});
				}
			});
		}
	});
};

//查询开票申请或销售订单信息
billCommonInvoiceRegister.searchSourceInfo = function(urlStr){
	var fromObj = $('#subSearchForm');
	
	var validateForm = fromObj.form('validate');
	// 校验必填项
	if (validateForm == false) {
		return;
	}
	var reqParam = $("#subSearchForm").form("getData");
	//组装请求参数
	$("#searchSourceDG").datagrid('options').queryParams = reqParam;
    $("#searchSourceDG").datagrid('options').url = urlStr;
    $("#searchSourceDG").datagrid('load');
};

// 在查询开票申请信息及销售订单信息页面点击确认按钮的处理
billCommonInvoiceRegister.confirmSelectInfo = function (win,tempType,checkedRows,searchbalanceType){
	var amount = 0;
	if(tempType == 1){//根据开票申请信息登记发票处理
		var flag = true;
		var salerNos = "", salerNames = "" ,buyerNos = "", buyerNames = "",applyNo = "";
		var preInvoice = "",organNo = "" ,organName = "",month = "";
		$('#dataGridCommonInvoiceDtl').datagrid('loadData', { total: 0, rows: [] });
		$.each(checkedRows, function(index, obj) {
			if(index == 0){
				salerNos = obj.salerNo;
				buyerNos = obj.buyerNo;
				preInvoice = obj.preInvoice;
			}else{
				if(salerNos != obj.salerNo || buyerNos != obj.buyerNo){
					showWarn("选中记录中公司及客户必须一致");
					flag = false;
					return ;
				}
				if(preInvoice != obj.preInvoice){
					showWarn("选中记录中预开票与开票申请必须一致");
					flag = false;
					return ;
				}
			}
			salerNames = obj.salerName;
			buyerNames = obj.buyerName;
			preInvoice = obj.preInvoice;
			amount = (parseFloat(amount) + parseFloat(obj.amount)).toFixed(2);
			//取得选中（多个）的开票申请单号
			applyNo += obj.billNo;
			if(index < checkedRows.length - 1) {
				applyNo += ",";
    		}
			
			var paramSetUrl = BasePath + '/base_setting/system_param_set/findSystemParamByParma?paramCode=AI_Register_Dlt_Create&businessOrganNo='+salerNos;
			$.ajax({
				type: 'POST',
				url: paramSetUrl,  
				data: null,
				cache: true, 
				async:false, 
				success: function(resultData){
					if("" == resultData || 1 == resultData){//根据系统参数判断是否生成明细
						//加载开票申请明细
						billCommonInvoiceRegister.loadApplyDtlByApplyNo(obj.billNo);
					}
				}
			});
			
			if(searchbalanceType == 10 || searchbalanceType == 7 || searchbalanceType == 8){//商场结算时或者地区批发，取管理城市
				organNo = obj.organNo;
				organName = obj.organName;
			}
			month = obj.month;
    	});
		
		if(flag){
			$("#salerNo").val(salerNos);
			$("#salerName").combogrid("disable");
			$("#salerName").combogrid('setValue', salerNames);
			$('#buyerName').customerInvoiceInfo('disable');
			$("#buyerName").customerInvoiceInfo('setValue',buyerNames);
			$("#buyerNo").val(buyerNos);
			$("#totalAmount").val(amount);
			$("#amount").val(amount);
			$('#preInvoice').combobox('setValue', preInvoice);
			$('#month').val(month);
			if(searchbalanceType == 10 || searchbalanceType == 7 || searchbalanceType == 8){//商场结算时或者地区批发，取管理城市
				$("#organNoId").val(organNo);
				$("#organNameId").val(organName);
			}
			if(preInvoice == '1'){
//				$("#dataGridCommonInvoiceDtl").datagrid('removeEditor', 'estimatedAmount');
				$("#dataGridCommonInvoiceDtl").datagrid('hideColumn',"estimatedAmount");
			}
//			$('#sourceNoId').iptSearch('disable');
//			$("#balanceTypeId").combobox({disabled: true});
//			$("#balanceTypeId").combobox('setValue', searchbalanceType);
			$("#sourceNoId").val(applyNo);
			//根据结算公司编号查询明细大类
			//billCommonInvoiceRegister.financialCategory(salerNos);
			win.close();
		}
	}else{ //根据销售订单信息登记发票处理
		var sourceNo = "";
		$.each(checkedRows, function(index, obj) {
			//取得选中（多个）的开票申请单号
			sourceNo += obj.orderNo;
			amount = parseFloat(amount) + parseFloat(obj.amount);
			if(index < checkedRows.length - 1) {
				sourceNo += ",";
    		}
    	});
		map = checkedRows;
//		$("#salerName").val($('#companyName').val());
		$("#salerNo").val($('#companyNo').val());
		$("#salerName").combogrid('setValue', $('#companyName').val());
		$("#sourceNoId").val(sourceNo);
		$("#totalAmount").val(amount);
		//根据结算公司编号查询明细大类
		//billCommonInvoiceRegister.financialCategory($('#companyNo').val());
		win.close();
	}
};

billCommonInvoiceRegister.loadApplyDtlByApplyNo = function(billNo){
	//获取开票申请明细
	var dtlUrl = BasePath + '/bill_balance_invoice_dtl/query_by_billNo?billNo='+billNo;
	$.ajax({
		type: 'POST',
		url: dtlUrl,  
		data: null,
		cache: true, 
		async:false, 
		success: function(resultData){
			var rows = resultData.rows;
			$.each(rows, function(index,applyDtl){
				var date = new Date();
				var param = new Object();
				param.invoiceAmount = applyDtl.sendAmount;
				if(applyDtl.taxRate){
					param.taxRate = applyDtl.taxRate;
				}else{
					param.taxRate = 0.17;
				}
				param.noTaxAmount = applyDtl.noTaxAmount; 
				param.taxAmount = applyDtl.taxAmount;
				param.estimatedAmount = applyDtl.estimatedAmount; 
				param.qty = applyDtl.qty; 
				//param.brandNo = applyDtl.brandNo;
				//param.brandName = applyDtl.brandName; 
				//param.categoryNo = applyDtl.categoryNo; 
				//param.categoryName = applyDtl.categoryName;
				param.invoiceName = applyDtl.salerName;
				param.invoiceDate = date;
//				if(parseInt(applyDtl.qty) > 0){
//					param.price = (applyDtl.sendAmount / applyDtl.qty);
//				}else{
//					param.price = applyDtl.sendAmount
//				}
				billCommonInvoiceRegister.addDtl(param);
			});
			billCommonInvoiceRegister.setAllAmount();
		}
	});
};

billCommonInvoiceRegister.upBill = function() {// 上单
	if (billCommonInvoiceRegister.curRowIndex < 0) {
		showWarn('不存在当前单据');
		return;
	}
	billCommonInvoiceRegister.type = 1;
	preBill('dataGridCommonInvoice', billCommonInvoiceRegister.curRowIndex,billCommonInvoiceRegister.type, 
			billCommonInvoiceRegister.callBackBill);
};

billCommonInvoiceRegister.downBill = function() {// 下单
	if (billCommonInvoiceRegister.curRowIndex < 0) {
		showWarn('不存在当前单据');
		return;
	}
	billCommonInvoiceRegister.type = 2;
	preBill('dataGridCommonInvoice', billCommonInvoiceRegister.curRowIndex,billCommonInvoiceRegister.type, 
			billCommonInvoiceRegister.callBackBill);
};

billCommonInvoiceRegister.callBackBill = function(curRowIndex, rowData) {// 上下单回调
	if (billCommonInvoiceRegister.type == 1 || billCommonInvoiceRegister.type == 2) {
		if (rowData != null && rowData != '' && rowData != []) {
			billCommonInvoiceRegister.loadDetail(curRowIndex, rowData);
			billCommonInvoiceRegister.type = 0;
		} else {
			if (billCommonInvoiceRegister.type == 1) {
				showWarn('已经是第一单');
			} else {
				showWarn('已经是最后一单');
			}
		}
	}
};

billCommonInvoiceRegister.formatterDate = function(value){
	return new Date(value).format("yyyy-MM-dd");
};


billCommonInvoiceRegister.financialCategory = function (salerNo){
	var urlStr = '/financial_category/getAllCateInfo';
	if('' != $("#salerNo").val()){
		salerNo = $("#salerNo").val();
	}
	if(salerNo){
		urlStr = urlStr + '?companyNo='+salerNo;
	}
	$("#dataGridCommonInvoiceDtl").datagrid("addEditor", {field : "categoryName", 
		editor:{
			type : 'combogrid',
			options:{
				required:true,
      			id:'categoryName',
      			name:'categoryName',
      			inputWidth:60,
      			idField:'name',
      			textField:'name',
      			noField:'categoryNo',
      			inputNoField:'categoryNo',
      			url:BasePath + urlStr,
      			columns:[[ 
      				{field : 'financialCategoryNo',title : '大类编号',width : 100, halign : 'center', align : 'left'},
						{field : 'name',title : '大类名称',width : 150, halign : 'center', align : 'left'}
      			]],
      			callback:function(data){
      				if(data){
          				$('#categoryNo').val(data.financialCategoryNo);
          				$('#categoryName').val(data.name);
      				}
      			}
			}
		}
	});
};
//
//billCommonInvoiceRegister.financialCategory = {
//    clickFn:function(){
//    	dgSelector({
//    		title : '选择财务大类',
//    		width : 500,
//    		height : 'auto',
//    		href : BasePath + '/common_util/doSelect?selectUrl=getPageFasCategory&no=financialCategoryNo&name=name',
//    		fn : function(data, rowIndex) {
//    			billCommonInvoiceRegister.selectorCallback(data, 
//    					'code','name','categoryNo','categoryName');
//    		}
//    	});
//    }
//};

billCommonInvoiceRegister.selectSalerCompany = function (row){
	if(row){
		billCommonInvoiceRegister.financialCategory(row.companyNo);
	}
};



//根据发票查询是否有使用
billCommonInvoiceRegister.checkUseFlag = function (billNo){
	var checkExist=false;
	// 填充结算单号和原单类型
	$.ajax({
		  type: 'POST',
		  url: BasePath + '/bill_balance_invoice_register/list.json',
		  data: {"billNo" : billNo},
		  cache: true,
		  async:false, // 一定要
		  success: function(resultData){
			  if(resultData){
				  if(resultData.rows[0].useFlag == 1){
					  checkExist = true;
				  }
			  }
		  }
	});
	return checkExist;
};

billCommonInvoiceRegister.importCallBack = function(result) {
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


billCommonInvoiceRegister.importDetailCallBack = function(data){
	if(data.indexOf('"success":true')!=-1){
		var jsonData = JSON.parse(data);
		if(jsonData.success){
			var lstData = jsonData.rows;
			if(lstData.length > 0){
				var lstAmount = 0;
				$.each(lstData,function(index, item){
					if(item.pass == 1){
						$('#dataGridCommonInvoiceDtl').datagrid('insertRow',{
							index : 0 ,
							row : item.validateObj
						});
						lstAmount+= item.validateObj.invoiceAmount;
					}
				});
				$('#importDetailDataGrid').datagrid({
					data: lstData,
					rowStyler: function(index,row){
						if (row.pass == 0){
							return 'background-color:#D4E6E7;';
						}
					}
				});
				ygDialog({
					title : '导入结果',
					target : $('#myDetailFormPanel'),
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
				showSuc("操作成功!");
				var amount = $('#amount').val();
				$('#amount').val(lstAmount+parseFloat(amount));
				return ;
			}
		}
	}
	showError("数据不合法,导入失败!");
};

//到票确认
billCommonInvoiceRegister.batchInvoiceOk = function(){// 批量删除   删除主明细
	var checkedRows = $('#dataGridCommonInvoice').datagrid('getChecked');
	for(var i =0; i < checkedRows.length; i++){
		var item = checkedRows[i];
		if(item.status == "0"){
			showWarn("单据："+item.billNo+"，未确认不可做到票确认！" );
			return false;
		}
		
		if(item.confirmUser || item.confirmTime){
			showWarn("单据："+item.billNo+"，不能重复做到票确认！" );
			return false;
		}
	}
	$.messager.confirm("确认","确定进行到票确认操作吗？",function(r) {
		if (r) {
			$.each(checkedRows, function(index, row) {
				
			});
			var data = {
					updated : JSON.stringify(checkedRows),
			};
			ajaxRequestAsync(billCommonInvoiceRegister.modulePath  + '/InvoiceOk',data,function(result){
				if(result){
					showSuc('操作成功');
					billCommonInvoiceRegister.search();
					$("#dataGridCommonInvoice").datagrid('load');
				    $( '#dataGridCommonInvoiceDtl').datagrid( 'load' );
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

