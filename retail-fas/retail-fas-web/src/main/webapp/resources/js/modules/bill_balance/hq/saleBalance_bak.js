var saleBalance ;

var balanceParams = {
		mainTabUrl : BasePath + '/bill_balance/type2/sale_tabMain.htm',
		listUrl : BasePath + '/bill_balance/query/balance_list.json' ,
		addUrl : BasePath + '/bill_balance/update/add_balance',	
		updateUrl : BasePath + '/bill_balance/update/update_balance',	
		deleteUrl : BasePath + '/bill_balance/update/delete_balance',
		verifyUrl : BasePath + '/bill_balance/verify',
		toCreateBalanceUrl : BasePath + '/bill_balance/to_page/to_create_balance',
		createBalanceUrl : BasePath + '/bill_balance/update/create_balance',
		saveDeductionUrl : BasePath + '/other_deduction/save',
		listDeductionUrl : BasePath + '/other_deduction/list.json',
		balanceExportUrl : BasePath + '/bill_balance/query/balance_export',
		balanceType : 2,
		dtlDataGrids : [
		    {id:'saleOutDataGrid',
			 title:'出库明细', 
			 tabUrl:BasePath + '/bill_balance/to_page/sale_tab_out.htm',
			 listUrl:BasePath + '/bill_balance/query/detail_list.json',
			 queryParams:{multiBillType:'(1301,1371)'}
		    },
		    {id:'saleReturnDataGrid',
			 title:'退残明细', 
			 tabUrl:BasePath + '/bill_balance/to_page/sale_tab_return.htm',
			 listUrl:BasePath + '/bill_balance/query/detail_list.json',
			 queryParams:{multiBillType:'(1333)'}
			},
		    {id:'deductionDataGrid',
			 title:'其他扣项', 
			 tabUrl:BasePath + '/bill_balance/to_page/sale_tab_deduction.htm',
			 listUrl:BasePath + '/other_deduction/list.json'
			},
			{id:'saleInvoiceDataGrid',
			 title:'发票明细', 
			 tabUrl:BasePath + '/bill_balance/to_page/sale_tab_invoice.htm',
			 listUrl:BasePath + '/bill_balance_invoice_apply/getByBalanceNo.json',
		    },
			{id:'saleGatherDataGrid',
			 title:'明细汇总', 
			 tabUrl:BasePath + '/bill_balance/to_page/to_item_sale_gather.htm',
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
		if(!((row.status == 4 || row.status == 6) && (row.askPaymentNo =='' || row.askPaymentNo==null))){
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
					saleBalance.search();
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
	saleBalance = new BillBalanceController();
	if($("#isArea").val() =="true"){// 地区
		balanceParams.mainTabUrl = BasePath + '/bill_balance/type4/sale_tabMain.htm';
		saleBalance.init(balanceParams);
		saleBalance.initPage();
		$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
		$("#mainDataForm").find("input[combobox]").combobox("disable");
		$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
	}else{
		saleBalance.init(balanceParams);
		saleBalance.initPage();
	}
	
	//以超链接方式直接访问详细页面
	var isArea = $('#isArea').val();
	var billNoMenu = $('#billNoMenu').val();
	if(billNoMenu != null && billNoMenu != ''){
		var params = {billNo:billNoMenu};
		if(isArea == 'true'){
			params = {billNo:billNoMenu,isArea:true};
		}
		ajaxRequestAsync(BasePath + '/bill_balance/query/balance_list.json',params,function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				saleBalance.loadMainData(obj);
				saleBalance.loadDtlDataGrid(balanceParams.dtlDataGrids[0]);
	    		returnTab('dtlTab', '出库明细');
	    		saleBalance.resetEditClass();
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
});
