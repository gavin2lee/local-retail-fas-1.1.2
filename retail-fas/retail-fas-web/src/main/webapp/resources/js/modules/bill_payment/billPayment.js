var billController ;

function Bill(){}

Bill.prototype = new BillController();

Bill.prototype.addDtl = function(row){// 新增明细
	if(billController.enableEdit){
		if(billController.endEdit()){
			if(row){
				$('#dtlDataGrid').datagrid('appendRow',{
					settleMethodNo:0, 
					bankAccount:billController.bankAccount,
					payAmount:parseFloat(row.amount) - parseFloat(row.paymentAmount),
					payQty:parseFloat(row.qty) - parseFloat(row.paymentQty)
				});
			}else{
				$('#dtlDataGrid').datagrid('appendRow',{
					settleMethodNo:0, 
					bankAccount:billController.bankAccount
				});
			}
		    var length = $('#dtlDataGrid').datagrid('getRows').length;
		    billController.beginEdit(length-1);
		    return true;
		}
		return false;
	}
};
	
Bill.prototype.beginEdit = function(editIndex){// 开始明细行编辑
	BillController.prototype.beginEdit.call(this, editIndex);
	var edPayAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'payAmount'});
	var edMoneyDiscount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'moneyDiscount'});
	var edFee = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'fee'});
	$(edPayAmount.target).bind('blur',function(){
		Bill.prototype.setAllAmount();
	});
	$(edMoneyDiscount.target).bind('blur',function(){
		Bill.prototype.setAllAmount();
	});
	$(edFee.target).bind('blur',function(){
		Bill.prototype.setAllAmount();
	});
};

Bill.prototype.delDtl = function(){// 删除明细
	BillController.prototype.delDtl.call(this);
	Bill.prototype.setAllAmount();
};

Bill.prototype.setAllAmount = function(){// 设置总金额
	var rows = $("#dtlDataGrid").datagrid('getRows');
	var allAmount = 0;
	var allQty = 0;
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editIndex = -1;
	if(editTr.length > 0){
		editIndex = editTr.attr("datagrid-row-index");
		var edPayAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'payAmount'});
		var edMoneyDiscount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'moneyDiscount'});
		var edFee = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'fee'});
		var edDiscountAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'discountAmount'});
		var ed = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'balanceAmount'});
		var edQty =  $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'payQty'});
		var payAmount = $(edPayAmount.target).numberbox('getValue')==''?0:$(edPayAmount.target).numberbox('getValue');
		var moneyDiscount = $(edMoneyDiscount.target).numberbox('getValue')==''?0:$(edMoneyDiscount.target).numberbox('getValue');
		var fee = $(edFee.target).numberbox('getValue')==''?0:$(edFee.target).numberbox('getValue');
		var discountAmount = parseFloat(payAmount)-parseFloat(moneyDiscount);
		var amount = parseFloat(discountAmount) + parseFloat(fee);
		var qty = $(edQty.target).numberbox('getValue');
		edDiscountAmount.target.val(discountAmount);
		ed.target.val(amount);
		if(''!=amount){
			allAmount += parseFloat(payAmount);
		}
		if(''!=qty){
			allQty += parseFloat(qty);
		}
	} 
	for(var i =0,iLength = rows.length; i<iLength; i++){
		var rowIndex = $("#dtlDataGrid").datagrid('getRowIndex',rows[i]);
		var amount = rows[i].payAmount;
		var qty = rows[i].payQty;
		if(editIndex != rowIndex){
			if(''!=amount){
				allAmount += parseFloat(amount);
			}
			if(''!=qty){
				allQty += parseFloat(qty);
			}
		}
	}
	$('#amount').val(allAmount.toFixed(2));
	$('#qty').val(allQty);
};

Bill.prototype.initDtlTab = function() {// 初始化明细Tab
	var dtl_url = billController.modulePath + '/area/to_dtl';
	var ref_url = billController.modulePath + '/area/to_ref';
	if($('#isHQ').val() == 'true'){
		dtl_url = billController.modulePath + '/hq/to_dtl';
		ref_url = billController.modulePath + '/hq/to_ref';
	}
	billController.addDtlTab('单据明细',dtl_url);
	billController.addDtlTab('发票信息',ref_url);
	$('#dtlTab').tabs({    
	    onSelect:function(title){
	    	if(title == '单据明细'){
	    		billController.loadDtlListData();
	    	}else{
	    		billController.loadRefInfo();
	    	}
	    }    
	}); 
	returnTab('dtlTab','单据明细');
	
	//加载预警列表
	loadWarnMessage();
};

Bill.prototype.save = function(){// 保存
		if(billController.doValidate()){
			var rows = $('#dtlDataGrid').datagrid('getRows');
			if(rows.length == 0){
				showInfo('请添加明细后在进行保存!');
				return ;
			}
			if($('#mainDataForm').find("input[name=refBillNo]").val() != ''){
				var refAmount = $('#refAmount').val();
				var amount = $('#amount').val();
				if(parseFloat(amount) - parseFloat(refAmount) > 0){
					showError("付款总金额大于发票金额,不允许保存!");
					return ;
				}else{
	    			billController.doSubmit();
	    			billController.search();
				}
			}else{
				billController.doSubmit();
				billController.search();
			}
		}
};

Bill.prototype.addMainTab = function() {// 初始化单据查询tab
	var url = billController.modulePath + '/area/list_tabMain.htm';
	if($('#isHQ').val() == 'true'){
		url = billController.modulePath + '/hq/list_tabMain.htm';
	}
	$('#mainTab').tabs('add', {
		title : '单据列表',
		selected : false,
		closable : false,
		href : url ,
		onLoad : function(panel) {
			if($('#isHQ').val() == 'true'){
				$('#searchForm').find('input[multiSearch=company]').attr('notGroupLeadRole',true);
			}else{
				$('#searchForm').find('input[multiSearch=company]').attr('groupLeadRole',true);
			}
			common_util.initPage('searchForm');
		}
	});
	billController.refreshTabs();
};

Bill.prototype.initRef = function(){// 初始化关联单据信息
	var _this = $('#refBillNo');
	_this.combogrid({
        panelWidth:560,
	    panelHeight : 250,
	    width : 140,
        mode:'remote',
        idField:'billNo',
        textField:'billNo',
        url : '',
        columns:[[
	        {field:'billNo',title:'发票单号',width:150},
	        {field:'billDate',title:'单据日期',width:80},
	        {field:'statusName',title:'单据状态',width:80},
	        {field:'amount',title:'发票金额',width:80,align:'right'},
	        {field:'createUser',title:'制单人',width:80},
	        {field:'createTime',title:'制单时间',width:150},
	        {field:'auditor',title:'审批人',width:80},
	        {field:'auditTime',title:'审批时间',width:150}
        ]],
        onHidePanel:function(){
        	var row = _this.combogrid('grid').datagrid('getSelected');
        	billController.setRef(row);
        },
        onShowPanel:function(){
        	billController.loadRef();
        }
    });
};

Bill.prototype.setRef = function(row){// 设置关联单据信息
	if(row){
		$('#refAmount').val(row.amount);
		$('#refQty').val(row.qty);
		$('#dtlDataGrid').datagrid('loadData',{total:0,rows:[]}); 
		billController.addDtl(row);
	}else{
		billController.clearRef();
	}
};

Bill.prototype.clearRef = function(){// 清空关联单据信息
	$('#refBillNo').combogrid('clear');
	$('#refAmount').val('');
	$('#refQty').val('');
};

Bill.prototype.loadRef = function(){// 加载关联单据查询列表
	var salerNo = $('#mainDataForm').find("input[name=salerNo]").val();
	var buyerNo = $('#mainDataForm').find("input[name=buyerNo]").val();
	var dg = $('#refBillNo').combogrid('grid');
	if(salerNo!= '' && buyerNo!=''){
		var queryParams = {salerNo:salerNo,buyerNo:buyerNo};
		dg.datagrid( 'options' ).queryParams = queryParams;
		dg.datagrid( 'options' ).url = billController.modulePath + '/search_ref?isHQ='+$('#isHQ').val();
		dg.datagrid( 'load' );
	}else{
		dg.datagrid('loadData',{total:0,rows:[]}); 
	}
};

function callback(rowData, _this, _next){
	if(rowData){
		if(_next.val()!=rowData.code){
			_next.val(rowData.code);
			billController.clearRef();
		}
		if(_next.attr("name").indexOf("buyer")!= -1){
			billController.setAccountInfo(rowData.code);
		}
	}else{
		_next.val('');
		_this.combogrid('clear');
		billController.clearRef();
	}
}

//显示巴洛克的列
function showBaroqueColumns(){
	if(currentUser.organTypeNo.indexOf("U010105")!= -1){
		$('#mainDataGrid').datagrid('showColumn','targetCurrencyName');
		$('#mainDataGrid').datagrid('showColumn','conversionFactor');
		$('#mainDataGrid').datagrid('showColumn','targetCurrencyAmount');
	}
}

// 初始化
$(function(){
	billController = new Bill();
	billController.init(BasePath + '/bill_payment', BasePath + '/bill_payment_dtl');
	billController.initPage();
	setTimeout(function(){
		returnTab('mainTab', '单据列表');
	},300);
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync( BasePath + '/bill_payment/list.json',{billNo:billNoMenu},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					billController.loadDetail(0,obj);
				},500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
	$('#isPrePayment').combobox({
		onSelect: function(rec){
			if(rec.code == '0'){
				$('#refBillNo').combogrid({
					required:true
				});
				$('#refBillNo').combogrid('clear');
				$('#refAmount').val('');
				$('#refQty').val('');
			}else{
				$('#refBillNo').combogrid({
					required:false
				});
				$('#refBillNo').combogrid('clear');
				$('#refAmount').val('');
				$('#refQty').val('');
			}
		}
	});
});
