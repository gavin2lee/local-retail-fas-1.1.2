function WholesaleMarginInit() {}

var _fasDialogController = new FasDialogController("/wholesale_margin_init", fas_common_setting);

WholesaleMarginInit.prototype.super = _fasDialogController;

var _wholesaleMarginInit = new WholesaleMarginInit();

//点击"新增"按钮时，触发的操作
WholesaleMarginInit.prototype.toAdd = function() {
	_fasDialogController.super.loadedAdd = function() {
		$("#id").val("");
		_wholesaleMarginInit.setReadOnly();
	};
	//检查新增的数据是否正确
	_fasDialogController.super.checkSave = function() {
		return _wholesaleMarginInit.validateData();
	};
	_fasDialogController.toAdd();
};

//进入修改页面
WholesaleMarginInit.prototype.toUpdate = function(rowData) {
	_fasDialogController.super.initUpdate = function() {
		_wholesaleMarginInit.setReadOnly();
	};
	this.super.checkInitUpdate = function(rowData) {
		if(rowData.finishFlag == '1') {
			showWarn("您选择的数据已完成初始化的数据，不能修改");
			return false;
		}
		return true;
	};
	_fasDialogController.toUpdate(rowData);
};

//删除
WholesaleMarginInit.prototype.doDel = function() {
	_fasDialogController.super.checkDel = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].finishFlag == '1') {
				showWarn("您选择的数据包含已完成初始化的数据，不能删除");
				return false;
			}
		}
		return true;
	};
	_fasDialogController.doDel();
};

//校验数据
WholesaleMarginInit.prototype.validateData = function() {
	var check_data = {companyNo : $("#companyNo").val(), customerNo : $("#customerNo").val(), id : $("#id").val()};
	var url = BasePath + "/wholesale_margin_init/validate_data";
	var validate_data = _fasDialogController.super.ajaxRequestAsync(url, check_data, function(result){
		if(result && !result.success) {
			showError(result.message);
			return false;
		}
		return true;
	});
	return validate_data;
};

//设置样式
WholesaleMarginInit.prototype.setReadOnly = function() {
	var fields = _wholesaleMarginInit.buildStyleFields();
	this.super.setReadOnly(fields);
};

//组装设置或清空样式的字段
WholesaleMarginInit.prototype.buildStyleFields = function() {
	return {
		input : ["customerName","marginAmount"]
	};
};

//完成初始化
WholesaleMarginInit.prototype.finishInit = function() {
	var checkedRows = $("#dataGridDiv").datagrid("getChecked");// 获取所有勾选checkbox的行
    if(checkedRows.length < 1){
        showWarn("请选择要完结的记录!");
        return;
    }
    for(var i = 0; i < checkedRows.length; i++) {
    	if(checkedRows[i].finishFlag == '1') {
    		showWarn("您选择的单据中包含有已完成初始化的单据!");
            return;
    	}
    }
    $.messager.confirm("确认","你确定要完结这"+checkedRows.length+"条数据", function(r) {
        if(r) {
            if(checkedRows.length > 0) {
                var effectRow = new Object();
                effectRow["finished"] = JSON.stringify(checkedRows);
                ajaxRequest(BasePath+"/wholesale_margin_init/finish_bill", effectRow, function(result){
                	if(result) {
                        showSuc("完结成功!");
                        $("#btn-search").click();
                    } else {
                        showError("完结失败,请联系管理员!");
                    }
                });
            } else {
                showError("完结失败!");
            }
        }
    });
};

//初始化查询框组件
WholesaleMarginInit.prototype.initSearchBox = function() {
	/*this.super.super.initIptSearch({
		id : "companyName",
		title : "选择公司",
		url : "/base_setting/company/toSearchCompany",
		inputWidth : 130,
		callback : function(data) {
			$("#companyNo").val(data.companyNo);
			$("#companyName").val(data.name);
		}
	});
	
	this.super.super.initIptSearch({
		id : "customerName",
		title : "选择客户",
		url : "/customer_rece_rel/select_customer",
		inputWidth : 130,
		callback : function(data) {
			$("#customerNo").val(data.customerNo);
			$("#customerName").val(data.customerName);
			$("#marginAmount").val(data.marginAmount);
		}
	});	
	
	this.super.super.initIptSearch({
		id : "preOrderNo",
		title : "选择批发订单",
		url : "/common_util/toSearchOrder",
		inputWidth : 130,
		callback : function(data) {
			$("#preOrderNo").val(data.billNo);
		}
	});	*/
	
	$('#companyName').combogrid({
		width :200,
        panelWidth:420,
	    panelHeight : 250,
        mode:'remote',
        idField:'name',
        textField:'name',
        url : BasePath + '/common_util/getCompany',
		fitColumns : true,
		pagination : true,
        columns:[[
        {field:'code',title:'编码',width:120},
        {field:'name',title:'名称',width:260}
        ]],
        onHidePanel : function(){
        	var rowData = $('#companyName').combogrid('grid').datagrid('getSelected');
        	$('#companyNo').val(rowData.code);
        	var url = BasePath + "/customer_rece_rel/list.json";
        	if($('#companyNo').val() != '' && $('#customerNo').val() != ''){
    			var params = {companyNo : $("#companyNo").val(), customerNo : $('#customerNo').val(), status:1};
    			ajaxRequest(url, params, function(result) {
    				$("#marginAmount").val(data.marginAmount);
    			});
        	}
        }
    });
	
	$('#customerName').combogrid({
		width :200,
        panelWidth:420,
	    panelHeight : 250,
        mode:'remote',
        idField:'name',
        textField:'name',
        url : BasePath + '/common_util/getCustomer',
		fitColumns : true,
		pagination : true,
        columns:[[
        {field:'code',title:'编码',width:120},
        {field:'name',title:'名称',width:260}
        ]],
        onHidePanel : function(){
        	var rowData = $('#customerName').combogrid('grid').datagrid('getSelected');
        	$('#customerNo').val(rowData.code);
        	var url = BasePath + "/customer_rece_rel/list.json";
        	if($('#companyNo').val() != '' && $('#customerNo').val() != ''){
    			var params = {companyNo : $("#companyNo").val(), customerNo : $('#customerNo').val(), status:1};
    			ajaxRequest(url, params, function(result) {
    				$("#marginAmount").val(result.rows[0].marginAmount);
    			});
        	}
        }
    });
	
	$('#preOrderNo').combogrid({
		width :200,
        panelWidth:420,
	    panelHeight : 250,
        mode:'remote',
        idField:'billNo',
        textField:'billNo',
        url : BasePath + '/bill_sale_balance/select_sale_order',
		fitColumns : true,
		pagination : true,
        columns:[[
        {field:'billNo',title:'单据编码',width:150},
        {field:'billDate',title:'单据日期',width:100},
        {field:'amount',title:'金额',width:120},
        {field:'sendQty',title:'数量',width:100}
        ]]
    });
};

_wholesaleMarginInit.clear = function(){
	$('#searchForm').form('clear');
	$('#searchForm').find('input').val("");
}

$(function() {
	//初始化查询组件
	_wholesaleMarginInit.initSearchBox();
});