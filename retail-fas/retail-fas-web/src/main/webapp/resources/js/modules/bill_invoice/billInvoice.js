var billController ;

function Bill(){}

Bill.prototype = new BillController();

BillController.prototype.addDtl = function(row){// 新增明细
	if(billController.enableEdit){
		if(billController.endEdit()){
			if(row){
				$('#dtlDataGrid').datagrid('appendRow',{
					invoiceAmount:row.balanceAmount,
					taxRate:'0.17',
					noTaxAmount:(row.balanceAmount/1.17).toFixed(8),
					taxAmount:row.balanceAmount - (row.balanceAmount/1.17).toFixed(8),
					qty:row.balanceQty,
					price:row.balanceQty == 0?0:(row.balanceAmount/row.balanceQty).toFixed(2)
				});
			}else{
				$('#dtlDataGrid').datagrid('appendRow',{taxRate:'0.17'});
			}
		    var length = $('#dtlDataGrid').datagrid('getRows').length;
		    billController.beginEdit(length-1);
		    return true;
		}
		return false;
	}
};

Bill.prototype.beginEdit = function(editIndex){// 开始明细行编辑
	billController.initFasCategory();
	BillController.prototype.beginEdit.call(this, editIndex);
	var edAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'invoiceAmount'});
	$(edAmount.target).bind('blur',function(){
		Bill.prototype.setAllAmount();
	});
	var edTaxRate = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'taxRate'});
	$(edTaxRate.target).bind('blur',function(){
		Bill.prototype.setAllAmount();
	});
	var edQty = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'qty'});
	$(edQty.target).bind('blur',function(){
		Bill.prototype.setAllAmount();
	});
};


Bill.prototype.delDtl = function(){// 删除明细
	BillController.prototype.delDtl.call(this);
	Bill.prototype.setAllAmount();
};


Bill.prototype.setAllAmount = function(){// 设置总金额
	var rows = $("#dtlDataGrid").datagrid('getRows');
	var allQty = 0;
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editRowIndex = -1;
	var allAmount = 0;
	if(editTr.length > 0){
		editRowIndex = editTr.attr("datagrid-row-index");
		var edAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'invoiceAmount'});
		var edTaxRate = $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'taxRate'});
		var edTaxAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'taxAmount'});
		var edNoTaxAmount = $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'noTaxAmount'});
		var edQty =  $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'qty'});
		var edPrice =  $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'price'});
		var amount = $(edAmount.target).numberbox('getValue');
		var taxRate = $(edTaxRate.target).numberbox('getValue');
		var qty = $(edQty.target).numberbox('getValue');
		if(''!=amount && ''!=taxRate ){
			 edNoTaxAmount.target.val((parseFloat(amount) / (1 + parseFloat(taxRate))).toFixed(8));
			 edTaxAmount.target.val((parseFloat(amount) - parseFloat(amount) / (1 + parseFloat(taxRate))).toFixed(8));
			 allAmount += parseFloat(amount);
		}
		if(''!=amount && ''!=qty && 0!=qty){
			 edPrice.target.val((parseFloat(amount)/qty).toFixed(2));
		}
		if(''!=qty){
			allQty += parseFloat(qty);
		}
	} 
	for(var i =0,iLength = rows.length; i<iLength; i++){
		var rowIndex = $("#dtlDataGrid").datagrid('getRowIndex',rows[i]);
		var amount = rows[i].invoiceAmount;
		var qty = rows[i].qty;
		if(editRowIndex != rowIndex){
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

Bill.prototype.endEdit = function(){// 结束明细行编辑
	var editTr = $("#dtlDGTab").find("tr[class*=datagrid-row-editing]");
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

Bill.prototype.initMessage = function(){
	auditMessage = "只允许确认制单状态的单据";
	backMessage = "只允许反确认确认状态的单据!";
	deleteMessage = "只允许删除制单状态的单据!";
	submitMessageReq = "你确定要确认该条单据?";
	backMessageReq = "你确定要反确认该条单据?";
	batchSubmitMessageReq = "你确定要确认选中的单据?";
	batchBackMessageReq = "你确定要反确认选中的单据?";
}

Bill.prototype.initDtlTab = function() {// 初始化明细Tab
	var dtl_url = billController.modulePath + '/area/to_dtl';
	var ref_url = billController.modulePath + '/area/to_ref';
	if($('#isHQ').val() == 'true'){
		dtl_url = billController.modulePath + '/hq/to_dtl';
		ref_url = billController.modulePath + '/hq/to_ref';
	}
	billController.addDtlTab('单据明细',dtl_url);
	billController.addDtlTab('结算单信息',ref_url);
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

Bill.prototype.loadRefInfo = function(){// 加载源单信息
	var billNo =  $('#refBillNoId').val();
	if(billNo !=""){
		setTimeout(function(){
			$( '#refDataGrid').datagrid( 'options' ).queryParams= {billNo:billNo};
		    $( '#refDataGrid').datagrid( 'options' ).url = BasePath + '/bill_invoice/ref_list.json';
		    $( '#refDataGrid').datagrid( 'load' );
		},300);
	}else{
		$( '#refDataGrid').datagrid('loadData',{total:0,rows:[]}); 
	}
};

BillController.prototype.loadMainData = function(rowData){// 加载表头数据
	billController.changeFlag = false;
	$('#mainDataForm').form('load', rowData);
	// 底部单据状态显示栏
	$('#createUser').html(rowData.createUser);
	$('#createTime').html(rowData.createTime);
	$('#auditor').html(rowData.auditor);
	$('#auditTime').html(rowData.auditTime);
	$('#refBillNo').combogrid('setText',rowData.refBillNo);
	$('#remark').change(function(){
		billController.changeFlag = true;
	});
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

function importCallBack(data){
	if(data.indexOf('"success":true')!=-1){
		var jsonData = JSON.parse(data);
		if(jsonData.success){
			var lstData = jsonData.rows;
			if(lstData.length > 0){
				$.each(lstData,function(index, item){
					if(item.pass == 1){
						$('#dtlDataGrid').datagrid('insertRow',{
							index : 0 ,
							row : item.validateObj
						});
					}
				});
				$('#importDataGrid').datagrid({
					data: lstData,
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
				showSuc("操作成功!");
				billController.setAllAmount();
				return ;
			}
		}
	}
	showError("数据不合法,导入失败!");
}

BillController.prototype.save = function(){// 保存
	var rows = $('#dtlDataGrid').datagrid('getRows');
	if(billController.doValidate()){
		if(rows.length == 0){
			showInfo('请添加明细后在进行保存!');
			return ;
		}
		if($('#mainDataForm').find("input[name=refBillNo]").val()  != ''){
			var refAmount = $('#refAmount').val();
			var amount = $('#amount').val();
			if(parseFloat(refAmount) != parseFloat(amount)){
				showError("结算单金额与发票金额不一致,不允许保存!");
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

function generatePaymentBill(){
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	if(checkRows.length == 0){
		showInfo("请选中需要生成付款单的发票!");
		return ; 
	}
	var flag = false;
	$.each(checkRows,function(index, row){
		if(row.status != 1 && row.status != 2){
			flag = true;
			return false;
		}
	});
	if(flag){
		showInfo("请选择确认或部分付款的发票!");
		return ;
	}
	$.messager.confirm("确认","确认根据选中的发票生成付款单?",function(r) {
		if (r) {
			var url = BasePath +  "/bill_payment/generate_by_invoice";
			var params = {checkRows : JSON.stringify(checkRows),balanceType : $('#balanceType').val()};
			ajaxRequestAsync(url, params, function(data){
				if(data > 0){
					billController.search();
					showSuc('成功生成'+data+'条付款单');
				}else{
					showError('生成失败');
				}
			});
		}
	});
};

function callback(rowData, _this, _next){
	if(rowData){
		if(_next.val()!=rowData.code){
	        _next.val(rowData.code);
	        billController.clearRef();
		}
		var buyerNo = $('#mainDataForm').find("input[name=buyerNo]").val();
		_fasCategoryUrl =  BasePath + '/common_util/getPageFasCategory?status=1&companyNo='+buyerNo;
	}else{
		_next.val('');
		_this.combogrid('clear');
		billController.clearRef();
		_fasCategoryUrl =  '';
	}
}

Bill.prototype.initRef = function(){// 初始化关联单据信息
	var _this = $('#refBillNo');
	_this.combogrid({
        panelWidth:560,
	    panelHeight : 250,
	    width : 140,
        mode:'remote',
        idField:'billNo',
        textField:'billNo',
        multiple:true,
        frozenColumns:[[{field:'ck',checkbox:true,width:30}]],
        url : '',
        columns:[[
	        {field:'billNo',title:'结算单号',width:150},
	        {field:'balanceTypeName',title:'结算类型',width:100},
	        {field:'balanceDate',title:'结算日期',width:80},
	        {field:'balanceAmount',title:'结算金额',width:80,align:'right'},
	        {field:'createUser',title:'制单人',width:80},
	        {field:'createTime',title:'制单时间',width:150},
	        {field:'auditor',title:'审批人',width:80},
	        {field:'auditTime',title:'审批时间',width:150}
        ]],
        onHidePanel:function(){
        	var rows = _this.combogrid('grid').datagrid('getSelections');
        	billController.setRef(rows);
        },
        onShowPanel:function(){
        	billController.loadRef();
        }
    });
};

Bill.prototype.setRef = function(rows){// 设置关联单据信息
	if(rows.length >0){
		var row = rows[0];
		var balanceAmount = 0;
		var balanceQty = 0;
		var refBillNo = '';
		$.each(rows,function(index,item){
			balanceAmount += parseFloat(item.balanceAmount);
			balanceQty += parseFloat(item.balanceQty);
			refBillNo += item.billNo+',';
		});
		balanceAmount = balanceAmount.toFixed(2);
		$('#refAmount').val(balanceAmount);
		$('#refBillNoId').val(refBillNo.substring(0,refBillNo.length - 1));
		$('#dtlDataGrid').datagrid('loadData',{total:0,rows:[]}); 
		row.balanceAmount = balanceAmount;
		row.balanceQty = balanceQty;
		billController.addDtl(row);
		$('#amount').val(balanceAmount);
	}else{
		billController.clearRef();
	}
};

Bill.prototype.clearRef = function(){// 清空关联单据信息
	$('#refBillNo').combogrid('clear');
	$('#refAmount').val('');
	$('#refBillNoId').val('');
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


var _fasCategoryUrl = '';

Bill.prototype.initFasCategory = function(){// 初始化财务大类
	$("#dtlDataGrid").datagrid("removeEditor", "categoryName");
	$("#dtlDataGrid").datagrid("addEditor", {field : "categoryName", 
		editor : {type:'test_combogrid',
			options:{
				type:'financialCategory',
				datagridId:'dtlDataGrid',
				valueField:'categoryNo',
      			queryUrl:'',
      			onShowPanelFunction:function(input){
      				var dg = input.combogrid('grid');
      				var queryParams = input.combo('options').queryParams;
      				dg.datagrid( 'options' ).queryParams = queryParams;
      				dg.datagrid( 'options' ).url = _fasCategoryUrl;
      				dg.datagrid( 'load' );
      			}
			}
		}
	});
};

// 初始化
$(function(){
	billController = new Bill();
	billController.init(BasePath + '/bill_invoice', BasePath + '/bill_invoice_dtl');
	billController.initPage();
	billController.initMessage();
	setTimeout(function(){
		returnTab('mainTab', '单据列表');
	},300);
	//以超链接方式直接访问详细页面
	var isHQ = $('#isHQ').val();
	var billNoMenu = $("#billNoMenu").val();
	if(billNoMenu != null && billNoMenu != ''){
		var params = {billNo:billNoMenu};
		if(isHQ == 'true'){
			params = {billNo:billNoMenu,isHQ:true};
		}
		ajaxRequestAsync( BasePath + '/bill_invoice/list.json', params, function(data){
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
});

//显示巴洛克的列
function showBaroqueColumns(){
	if(currentUser.organTypeNo.indexOf("U010105")!= -1){
		$('#mainDataGrid').datagrid('showColumn','targetCurrencyName');
		$('#mainDataGrid').datagrid('showColumn','conversionFactor');
		$('#mainDataGrid').datagrid('showColumn','targetCurrencyAmount');
	}
}
