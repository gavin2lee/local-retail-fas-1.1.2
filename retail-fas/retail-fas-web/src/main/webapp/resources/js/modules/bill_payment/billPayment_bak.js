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
					payAmount:parseFloat(row.amount) - parseFloat(row.paymentAmount)
				});
				$('#amount').val(parseFloat(row.amount) - parseFloat(row.paymentAmount));
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
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editIndex = -1;
	if(editTr.length > 0){
		editIndex = editTr.attr("datagrid-row-index");
		var edPayAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'payAmount'});
		var edMoneyDiscount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'moneyDiscount'});
		var edFee = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'fee'});
		var edDiscountAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'discountAmount'});
		var ed = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'balanceAmount'});
		var payAmount = $(edPayAmount.target).numberbox('getValue')==''?0:$(edPayAmount.target).numberbox('getValue');
		var moneyDiscount = $(edMoneyDiscount.target).numberbox('getValue')==''?0:$(edMoneyDiscount.target).numberbox('getValue');
		var fee = $(edFee.target).numberbox('getValue')==''?0:$(edFee.target).numberbox('getValue');
		var discountAmount = parseFloat(payAmount)-parseFloat(moneyDiscount);
		var amount = parseFloat(discountAmount) + parseFloat(fee);
		edDiscountAmount.target.val(discountAmount);
		ed.target.val(amount);
		if(''!=amount){
			allAmount += parseFloat(payAmount);
		}
	} 
	for(var i =0,iLength = rows.length; i<iLength; i++){
		var rowIndex = $("#dtlDataGrid").datagrid('getRowIndex',rows[i]);
		var amount = rows[i].payAmount;
		if(editIndex != rowIndex){
			if(''!=amount){
				allAmount += parseFloat(amount);
			}
		}
	}
	$('#amount').val(allAmount.toFixed(2));
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
				if(amount > refAmount){
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
				$('#searchForm').find('input[name=buyerNo]').attr('notGroupLeadRole',true);
			}else{
				$('#searchForm').find('input[name=buyerNo]').attr('groupLeadRole',true);
			}
			common_util.initPage('searchForm');
		}
	});
	billController.refreshTabs();
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


// 初始化
$(function(){
	billController = new Bill();
	billController.init(BasePath + '/bill_payment', BasePath + '/bill_payment_dtl');
	billController.initPage();
});
