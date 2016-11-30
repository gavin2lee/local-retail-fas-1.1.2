var buyBalance ;

var balanceParams = {
		balanceType : 1,
		mainTabUrl : BasePath + '/bill_balance/type1/buy_tabMain.htm' ,
		listUrl : BasePath + '/bill_balance/query/balance_list.json' ,
		addUrl : BasePath + '/bill_balance/update/add_balance',	
		updateUrl : BasePath + '/bill_balance/update/update_balance',	
		deleteUrl : BasePath + '/bill_balance/update/delete_balance',
		verifyUrl : BasePath + '/bill_balance/verify',
		toCreateBalanceUrl : BasePath + '/bill_balance/to_page/to_create_balance',
		createBalanceUrl : BasePath + '/bill_balance/update/create_balance',
		toAdjustBalanceUrl : BasePath + '/bill_balance/to_page/to_adjust_balance',
		adjustBalanceUrl : BasePath + '/bill_balance/update/adjust_balance',
		balanceExportUrl : BasePath + '/bill_balance/query/balance_export',
		dtlDataGrids : [
		    {id:'buyEnterDataGrid',
			 title:'入库明细', 
			 tabUrl:BasePath + '/bill_balance/type1/buy_tab_enter.htm',
			 listUrl:BasePath + '/bill_balance/query/detail_list.json',
			 queryParams:{billType:1301}
		    },
			{id:'buyReturnDataGrid',
			 title:'原残明细', 
			 tabUrl:BasePath + '/bill_balance/type1/buy_tab_return.htm',
			 listUrl:BasePath + '/bill_balance/query/detail_list.json',
			 queryParams:{billType:1333}
		    },
			{id:'buyDeductionDataGrid',
			 title:'扣项明细', 
			 tabUrl:BasePath + '/bill_balance/type1/buy_tab_deduction.htm',
			 listUrl:BasePath + '/other_deduction/list.json'
		    },
			{id:'buyInvoiceDataGrid',
			 title:'发票信息', 
			 tabUrl:BasePath + '/bill_balance/type1/buy_tab_invoice.htm',
			 listUrl:BasePath + '/bill_invoice/query_balance_invoice'
		    },
			{id:'buyPayDataGrid',
			 title:'请款信息', 
			 tabUrl:BasePath + '/bill_balance/type1/buy_tab_pay.htm',
			 listUrl:BasePath + '/bill_ask_payment/query_balance_ask_payment'
		    },
			{id:'buyGatherDataGrid',
			  title:'明细汇总', 
			  tabUrl:BasePath + '/bill_balance/type1/to_item_buy_gather.htm',
			  listUrl:BasePath + '/bill_balance/query/item_gather.json'
			 }
		]
	};

function generateAskPaymentBill(){
	var checkRows = $('#mainDataGrid').datagrid('getChecked');
	if(checkRows.length == 0){
		showInfo("请选中需要生成请款单的结算单据(未经过请款且财务确认)!");
		return ; 
	}
	var flag = false;
	var errorMessage = "";
	$.each(checkRows,function(index, row){
		if(!(row.status == 2 && (row.askPaymentNo =='' || row.askPaymentNo==null))){
			errorMessage = "请选择未请款并且财务确认的结算单!";
			return false;
		}
		if(row.balanceAmount <= 0){
			errorMessage = "应付金额不大于零,不允许生成请款单!";
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
			var params = {checkRows : JSON.stringify(checkRows),balanceType : 1};
			ajaxRequestAsync(url, params, function(data){
				if(data > 0){
					buyBalance.search();
					showSuc('成功生成'+data+'条请款单');
				}else{
					showError('生成失败');
				}
			});
		}
	});
};

// 初始化
$(function(){
	buyBalance = new BillBalanceController();
	buyBalance.init(balanceParams);
	buyBalance.initPage();
	
	//以超链接方式直接访问详细页面
	var billNoMenu = $('#billNoMenu').val();
	var balanceType = $('#balanceType').val();
	if(billNoMenu != null && billNoMenu != ''){
		ajaxRequestAsync(BasePath + '/bill_balance/query/balance_list.json',{billNo:billNoMenu,balanceType:balanceType},function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					buyBalance.loadDetail(0, obj);
				},500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
});
