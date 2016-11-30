var invoiceRegister = new Object();

var setting = {
	datagridId : "invoiceRegisterdataGrid",
	primaryKey : "id",
	saveUrl : "/bill_balance_invoice_register/save",
	saveCallback : function(){
		
	},
	initRow : function(){
		return {};
	},
	initData : {
		combobox : {
			
		}
	}
};

invoiceRegister.modulePath = BasePath + '/bill_balance_invoice_register';


invoiceRegister.search = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/bill_balance_invoice_register/list.json';
	$("#invoiceRegisterdataGrid").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
	$("#invoiceRegisterdataGrid").datagrid('options').url = queryMxURL;
	$("#invoiceRegisterdataGrid").datagrid('load');
};

invoiceRegister.save = function(){
	if($('#mainDataForm').form('validate')){
		if(invoiceRegister.doValidate()){
			invoiceRegister.saveInvoiceRuleSet();
		}
	};
};

invoiceRegister.doValidate = function(){ // 验证
	if($('#mainDataForm').form('validate')){
		if(invoiceRegister.endEdit()){
			return true;
		}
	}
	return false;
};

invoiceRegister.endEdit = function(){// 结束明细行编辑
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#invoiceRegisterdataGrid').datagrid('validateRow', editRowIndex)){
			$('#invoiceRegisterdataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

invoiceRegister.saveInvoiceRuleSet = function(){// 保存明细 
	var insertedData = $('#invoiceRegisterdataGrid').datagrid('getChanges','inserted');
	var updatedData = $('#invoiceRegisterdataGrid').datagrid('getChanges','updated');
	var deletedData = $('#invoiceRegisterdataGrid').datagrid('getChanges','deleted');
	
	var data = {
		inserted : JSON.stringify(insertedData),
		updated : JSON.stringify(updatedData),
		deleted : JSON.stringify(deletedData),
	};
//	alert(JSON.stringify(data));
	ajaxRequestAsync(invoiceRegister.modulePath + '/save',data,function(){
		if(data){
		  	$('#invoiceRegisterdataGrid').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
};

invoiceRegister.selectCompany  = {// 明细行选择公司
    clickFn:function(){
    	dgSelector({
    		title:"选择公司",
    		width : 580,
    		height : 450,
    		href : BasePath + "/common_util/toSearchCompany",
    		fn : function(data, rowIndex) {
    			invoiceRegister.selectorCallback(data,'companyNo','name','salerNo','salerName');
    		}
    	});
    }
};


invoiceRegister.selectMall = {// 明细行选择选择商场
    clickFn:function(){
    	dgSelector({
    		width : 580,
    		height : 450,
    		title:"选择商场",
			href : BasePath + "/common_util/toSearchMall",
    		fn : function(data, rowIndex) {
    			invoiceRegister.selectorCallback(data,'mallNo','name','buyerNo','buyerName');
    		}
    	});
    }
};

invoiceRegister.selectorCallback = function(data,value,text,valueField,textField){// 选择后回调
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

var balanceTypeStatus =[{'value':'1', 'text': '总部厂商结算'},{'value':'2', 'text':'总部批发结算'},{'value':'3', 'text':'总部其他结算'},
                        {'value':'4', 'text':'地区采购结算'},{'value':'5', 'text':'地区间结算'},{'value':'6', 'text':'地区自购结算'},
                        {'value':'7', 'text':'地区批发结算'},{'value':'8', 'text':'地区团购结算'},{'value':'9', 'text':'地区员购结算'},
                        {'value':'10', 'text':'地区门店结算'},{'value':'11', 'text':'地区其他出库结算'},];

invoiceRegister.balanceTypeformatter = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < balanceTypeStatus.length; i++) {
        if (balanceTypeStatus[i].value == value) {
            return balanceTypeStatus[i].text;
        }
    }
};

var statusformatter =[{'value':'1', 'text': '创建'},{'value':'2', 'text':'审核'}];
invoiceRegister.statusformatter= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < statusformatter.length; i++) {
        if (statusformatter[i].value == value) {
            return statusformatter[i].text;
        }
    }
};

var preInvoiceformatter =[{'value':'1', 'text': '是'},{'value':'2', 'text':'否'}];
invoiceRegister.preInvoiceformatter= function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < preInvoiceformatter.length; i++) {
        if (preInvoiceformatter[i].value == value) {
            return preInvoiceformatter[i].text;
        }
    }
};