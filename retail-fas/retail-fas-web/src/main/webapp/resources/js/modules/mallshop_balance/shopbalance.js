var billShopBalance = new Object();

//当前用户
billShopBalance.currentUser;

//单据查询当前选中行的索引
//billShopBalance.curRowIndex = -1;

//模块路径
billShopBalance.modulePath = BasePath + '/mall_shopbalance';

billShopBalance.saleordmodulePath = BasePath + '/mall_shopsorderd';

//模块路径
billShopBalance.dtlModulePath = BasePath + '/mall_shopbalance_dtl';

//结算单明细DataGrid
billShopBalance.balanceDtlDataGrid = 'balanceDtlDataGrid';

//商场预收款DataGrid
billShopBalance.deductionDataGrid = 'expectCashDataGrid';

//商场VIP分摊DataGrid
billShopBalance.vipDataGrid = 'vipDataGrid';

//票前费用DataGrid
billShopBalance.balanceDeductDataGrid = 'balanceDeductDataGrid';

//结算差异DataGrid
//billShopBalance.shopbalanceDiffDataGrid = 'balanceDiffDataGrid';

//发票信息DataGrid
billShopBalance.invoiceDataGrid = 'invoiceDataGrid';

//收款信息DataGrid
billShopBalance.balanceAccountDataGrid = 'balanceAccountDataGrid';

//结算单明细Tab路径
billShopBalance.balanceDtlTabUrl = billShopBalance.modulePath + '/shopbalance_dtlbill';

//商场预收款Tab路径
billShopBalance.deductionTabUrl = billShopBalance.modulePath + '/shopbalance_advancebill';

//商场VIP分摊Tab路径
billShopBalance.vipInfoTabUrl = billShopBalance.modulePath + '/shopbalance_vipbill';

//票前费用Tab路径
billShopBalance.balanceDeductTabUrl = billShopBalance.modulePath + '/shopbalance_deductbill';

//结算差异Tab路径
billShopBalance.balanceDiffTabUrl = billShopBalance.modulePath + '/shopbalance_diffbill';

//发票信息Tab路径
billShopBalance.invoiceInfoTabUrl = billShopBalance.modulePath + '/shopbalance_invoicebill';

//收款信息Tab路径
billShopBalance.balanceAccountTabUrl = billShopBalance.modulePath + '/shopbalance_accountbill';

//结算单明细
billShopBalance.balanceDtlListUrl = billShopBalance.saleordmodulePath + '/list.json';

billShopBalance.invoiceInfoPath = BasePath + '/bill_balance_invoice_info';
billShopBalance.balanceDiffPath = BasePath + '/bill_shop_balance_diff';
billShopBalance.balanceDeductPath = BasePath + '/bill_shop_balance_deduct';
billShopBalance.orderSalePath = BasePath + '/mall_shopsorderd';
billShopBalance.loadCategorySumPath=BasePath + '/bill_shop_balance_cate_sum';
billShopBalance.loadPromotionSumPath=BasePath + '/bill_shop_balance_pro_sum';

billShopBalance.balanceAccountPath=BasePath + '/bill_backsection_split_dtl';//BillBacksectionSplitDtl

//商场预收款
billShopBalance.expectCashListUrl =BasePath + '/expect_cash'+ '/list.json';  //deduction

//商场VIP分摊
billShopBalance.vipInfoListUrl =  '';

billShopBalance.readonlyInfo = function() {
	$("#billDate").attr('readonly',true).addClass("readonly");
	$("#balanceNo").attr('readonly',true).addClass("readonly");
	$("#name").attr('readonly',true).addClass("readonly");
	$("#printCount").attr('readonly',true).addClass("readonly");
	$("#balanceStartDate").attr('readonly',true).addClass("readonly");
	$("#balanceEndDate").attr('readonly',true).addClass("readonly");
	
	$("#actualDiscount").attr('readonly',true).addClass("readonly");
	$("#contractDiscount").attr('readonly',true).addClass("readonly");
	$("#systemSalesnum").attr('readonly',true).addClass("readonly");
	$("#otherTotalnum").attr('readonly',true).addClass("readonly");
	$("#billingAmount").attr('readonly',true).addClass("readonly");
	
	$("#mallNumber,#balanceDesc,#remark").attr('readonly',true).addClass("readonly");   
};

//初始化
$(function() {
	billShopBalance.initDtlTab();
//	billShopBalance.disableMainInfo();
	billShopBalance.addMainTab();
//	billShopBalance.readonlyInfo();--------------------
//	billShopBalance.parsePage();
//	billShopBalance.initShowType();
	$('#mainTab').tabs('hideHeader');
	fas_util.initIptFormSearch('searchForm');
	fas_util.initIptshop($("#searchForm").find("input[iptSearch=shop]"));
	
	billShopBalance.disableMainInfo();
//	$("#zoneNo,#mallNo,#bizCityNo,#shopNo,#month").combobox({disabled: false}); 
	$("#mallNumberAmount,#estimateAmount,#differenceAmount,#otherTotalAmount,#remark").attr('readonly',true).addClass("readonly"); 
	$("#companyName,#shortName,#month,#balanceType").removeAttr("readonly").removeClass("readonly");
//	$("#balanceType").combobox("enable"); 
	setTimeout(function(){
		 returnTab('mainTab', '单据查询');
		},500);
	
	// 初始化结算单类型
//	$("#balanceType").initCombox({
//		readonly : false,
//		valueField : "label",
//		textField : "value",
//		data : [{
//			label: '1',
//			value: '正式'
//		},{
//			label: '2',
//			value: '预估'
//		}]
//	});
});

//不可编辑状态
billShopBalance.disableMainInfo = function() {
	$("#mainDataForm").find("input").attr("readonly", true)
			.addClass("readonly");
	$("#mainDataForm").find("textarea").attr("readonly", true)
			.addClass("readonly");
};

//初始化单据查询tab
billShopBalance.addMainTab = function() {
	if($('#mainTab') != 'undefined' && $('#mainTab') !== null) {
	$('#mainTab').tabs('add', {
		title : '单据查询',
		selected : false,
		closable : false,
		href : billShopBalance.modulePath + '/shopbalance_tbMain',	
	onLoad : function(panel) {
		// 这里需要在重写在加载完后做对应的事件
		setTimeout(function(){
		fas_util.initIptFormSearch('searchForm');
		fas_util.initIptshop($("#searchForm").find("input[iptSearch=shop]"));
		billShopBalance.initShowType();
		},500);
	}
	});
	};
//	billShopBalance.refreshTabs();
};

//balanceDtlTabUrl  deductionTabUrl  vipInfoTabUrl  balanceDeductTabUrl  balanceDiffTabUrl  invoiceInfoTabUrl  balanceAccountTabUrl
//balanceDtlDataGrid  deductionDataGrid  vipDataGrid  balanceDeductDataGrid balanceDiffDataGrid  invoiceDataGrid  balanceAccountDataGrid
//初始化明细Tab
billShopBalance.initDtlTab = function() {
	billShopBalance.addDtlTab('销售单查询',billShopBalance.balanceDtlTabUrl);
	billShopBalance.addDtlTab('结算差异',billShopBalance.balanceDiffTabUrl);
//	billShopBalance.addDtlTab('预收款',billShopBalance.deductionTabUrl);
//	billShopBalance.addDtlTab('商场VIP分摊',billShopBalance.vipInfoTabUrl);	
	billShopBalance.addDtlTab('票前费用',billShopBalance.balanceDeductTabUrl);	
	billShopBalance.addDtlTab('发票信息',billShopBalance.invoiceInfoTabUrl);
	billShopBalance.addDtlTab('收款信息',billShopBalance.balanceAccountTabUrl);
	$('#dtlTab').tabs(
			{
				onSelect : function(title) {
					var tab = $("#dtlTab").tabs("getTab", title);
					if ('销售单查询' == title) {
						var shopNo = $('#shopNo').val();
						var startDate = $('#balanceStartDate').val();
						var endDate = $('#balanceEndDate').val();
						billShopBalance.balanceOrderSaleDtlInfo(shopNo,startDate,endDate);
//						billShopBalance.loadDtlDataGrid(
//								billShopBalance.balanceDtlDataGrid, {
//									//billType : 1
//									shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,balanceNo:balanceNo
//								}, billShopBalance.balanceDtlListUrl,
//								'loadBalanceDtlFlag');
					} else if ('预收款' == title) {
						var shopNo = $('#shopNo').val();
						var month = $('#month').val();
						var startDate = $('#balanceStartDate').val();
						var endDate = $('#balanceEndDate').val();
						var balanceNo = $('#balanceNo').val();
						billShopBalance.loadbalanceExpectCash(shopNo,month,startDate,endDate,balanceNo);
						billShopBalance.loadDtlDataGrid(
								billShopBalance.deductionDataGrid,
								{shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,balanceNo:balanceNo},
								billShopBalance.deductionListUrl,
								'loadExpectCashFlag');
					} else if ('商场VIP分摊' == title) {
						billShopBalance.loadDtlDataGrid(
								billShopBalance.vipDataGrid, {},
								billShopBalance.vipInfoListUrl,
								'loadVipFlag');
					}else if ('票前费用' == title) {
						var shopNo = $('#shopNo').val();
						var month = $('#month').val();
						var startDate = $('#balanceStartDate').val();
						var endDate = $('#balanceEndDate').val();
						var balanceNo = $('#balanceNo').val();	
						var befMonth = $('#month').val();
						billShopBalance.loadbalanceDeduct(shopNo,month,startDate,endDate,balanceNo,befMonth);
					}else if ('结算差异' == title) {
						var shopNo = $('#shopNo').val();
						var month = $('#month').val();
						var startDate = $('#balanceStartDate').val();
						var endDate = $('#balanceEndDate').val();
						var balanceNo = $('#balanceNo').val();	
						billShopBalance.loadbalanceDiff(shopNo,month,startDate,endDate,balanceNo);
					}else if ('发票信息' == title) {
						var billNo = $('#invoiceApplyNo').val();
						if(billNo) {
							billShopBalance.loadInvoiceInfo(billNo);
						} else {
							$("#invoiceDataGrid").clearDataGrid();
						}
//						if(billNo && billNo != "") {
//							$("#dtlTab").tabs('update', {
//								tab: tab,
//								options: {
//									title : '发票信息',
//									selected : false,
//									closable : false,
//									href : billShopBalance.invoiceInfoTabUrl+"?billNo="+billNo
//								}
//							});
//						}
				}else if ('收款信息' == title) {
					if($("#balanceNo").val()) {
						var shopNo = $('#shopNo').val();
						var month = $('#month').val();
						var startDate = $('#balanceStartDate').val();
						var endDate = $('#balanceEndDate').val();
						var balanceNo = $('#balanceNo').val();						
						billShopBalance.loadbalanceAccount(shopNo,month,null,null,balanceNo);
					} else {
						$("#balanceAccountDataGrid").clearDataGrid();
					}
				}
				}
			});
	returnTab('dtlTab', '销售单查询');
};

billShopBalance.loadInvoiceInfo  = function(billNo){
//	if($('#invoiceDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
	var params = {billNo : billNo};
	var url = billShopBalance.invoiceInfoPath + '/getBillShopBanancleInfo';
    $('#invoiceDataGrid').datagrid( 'options').queryParams= params;
    $('#invoiceDataGrid').datagrid( 'options').url=url;
    $('#invoiceDataGrid').datagrid( 'load' );
	},300);
};

billShopBalance.loadbalanceDiff  = function(shopNo,month,startDate,endDate,balanceNo){
	setTimeout(function(){
		var params = {shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,balanceNo:balanceNo,balaceDiffType:1};//,status:1
		var url = billShopBalance.balanceDiffPath + '/list.json';
	    $('#balanceDiffDataGrid').datagrid( 'options').queryParams= params;
	    $('#balanceDiffDataGrid').datagrid( 'options').url=url;
	    $('#balanceDiffDataGrid').datagrid( 'load' );
	},300);
};

billShopBalance.loadbalanceAccount  = function(shopNo,month,startDate,endDate,balanceNo){
//	if($('#balanceAccountDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
	var params = {shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,balanceNo:balanceNo};
	var url = billShopBalance.balanceAccountPath + '/list.json';
    $('#balanceAccountDataGrid').datagrid( 'options').queryParams= params;
    $('#balanceAccountDataGrid').datagrid( 'options').url=url;
    $('#balanceAccountDataGrid').datagrid( 'load' );
	},300);
};

billShopBalance.loadbalanceDeduct  = function(shopNo,month,startDate,endDate,balanceNo,befMonth){
//	if($('#balanceDeductDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
	var params = {shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,costDeductType:1,befMonth:befMonth,balaceDeduType:1,balanceNo:''};//,status:1
	var url = billShopBalance.balanceDeductPath + '/list.json';
    $('#balanceDeductDataGrid').datagrid( 'options').queryParams= params;
    $('#balanceDeductDataGrid').datagrid( 'options').url=url;
    $('#balanceDeductDataGrid').datagrid( 'load' );
	},300);
};

billShopBalance.loadbalanceExpectCash  = function(shopNo,month,startDate,endDate,balanceNo){
//	if($('#expectCashDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
	var params = {shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,balanceNo:balanceNo};
	var url = billShopBalance.expectCashListUrl;
    $( '#expectCashDataGrid').datagrid( 'options').queryParams= params;
    $( '#expectCashDataGrid').datagrid( 'options').url=url;
    $( '#expectCashDataGrid').datagrid( 'load' );
	},300);
};

billShopBalance.balanceOrderSaleDtlInfo = function(shopNo,startDate,endDate){
//	if($('#balanceDtlDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
		if($("#showType").combobox('getValue')) {
			var params = {shopNo : shopNo,startDate:startDate,endDate:endDate};	
			var url = billShopBalance.orderSalePath + '/list.json';
			$('#balanceDtlDataGrid').datagrid( 'options' ).queryParams= params;
			$('#balanceDtlDataGrid').datagrid( 'options' ).url= url;
			$('#balanceDtlDataGrid').datagrid( 'load' );
		}
	},300);
};


billShopBalance.loadCategorySum = function(shopNo,startDate,endDate,balanceNo){
//	if($('#categoryDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
	var params = {shopNo:shopNo,startDate:startDate,endDate:endDate,balanceNo:balanceNo};	
	var url = billShopBalance.loadCategorySumPath + '/list.json';
    $('#categoryDataGrid').datagrid( 'options').queryParams= params;
    $('#categoryDataGrid').datagrid( 'options').url=url;
    $('#categoryDataGrid').datagrid( 'load' );
	},300);
};

billShopBalance.loadPromotions = function(shopNo,startDate,endDate,balanceNo){
//	if($('#promotionsDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
	var params = {shopNo:shopNo,startDate:startDate,endDate:endDate,balanceNo:balanceNo};	
	var url = billShopBalance.loadPromotionSumPath + '/list.json';
    $('#promotionsDataGrid').datagrid( 'options').queryParams= params;
    $('#promotionsDataGrid').datagrid( 'options').url=url;
    $('#promotionsDataGrid').datagrid( 'load' );
	},300);
};

billShopBalance.loadPaymentMethodSum = function(shopNo,startDate,endDate,balanceNo){
//	if($('#paymentMethodDataGrid').length == 0){
//		return ;
//	}
	setTimeout(function(){
	var params = {shopNo : shopNo,balanceStartDate:startDate,balanceEndDate:endDate,balanceNo:balanceNo};	
	var url = billShopBalance.modulePath + '/getPaymentMethodSum';
    $('#paymentMethodDataGrid').datagrid( 'options').queryParams= params;
    $('#paymentMethodDataGrid').datagrid( 'options').url=url;
    $('#paymentMethodDataGrid').datagrid( 'load' );
	},300);
};

//添加明细Tab
billShopBalance.addDtlTab = function(title, href) {
	$('#dtlTab').tabs('add', {
		title : title,
		selected : false,
		closable : false,
		href : href
	});
};

/**
 * 明细操作
 */
billShopBalance.invoiceEditIndex = -1;
billShopBalance.payEditRowIndex = -1;
billShopBalance.preBillNo;
// 加载明细数据
billShopBalance.loadDtlDataGrid = function(id, params, url, loadFlag) {
	var billNo = $('#billNo').val();
	if ("" != billNo) {
		if (billShopBalance.preBillNo != billNo) {
			setTimeout(function() {
				params.balanceNo = billNo;
				$('#' + id).datagrid('options').queryParams = params;
				$('#' + id).datagrid('options').url = url;
				$('#' + id).datagrid('load');
			}, 500);
			billShopBalance['loadBalanceDtlFlag'] = true;
			billShopBalance['loadDeductionFlag'] = undefined;
			billShopBalance['loadVipFlag'] = undefined;
			billShopBalance.preBillNo = billNo;
		} else {
			if (!billShopBalance[loadFlag]) {
				setTimeout(function() {
					params.balanceNo = billNo;
					$('#' + id).datagrid('options').queryParams = params;
					$('#' + id).datagrid('options').url = url;
					$('#' + id).datagrid('load');
				}, 500);
				billShopBalance[loadFlag] = true;
			}
		}
	}
};

//新增
billShopBalance.add = function(){
	billShopBalance.clearData();
//	billShopBalance.enableMainInfo();
//	billShopBalance.readonlyInfo();
	billShopBalance.disableMainInfo();
//	$("#zoneNo,#mallNo,#bizCityNo,#shopNo,#month").combobox({disabled: false}); 
	$("#mallNumberAmount,#estimateAmount,#differenceAmount,#otherTotalAmount,#remark").attr('readonly',true).addClass("readonly"); 
	$("#companyName,#shortName,#month").removeAttr("readonly").removeClass("readonly");
	
	$("#balanceType").combobox("enable");
	
	$("#balanceDtlDataGrid").clearDataGrid();
	$("#categoryDataGrid").clearDataGrid();
	$("#promotionsDataGrid").clearDataGrid();
	$("#paymentMethodDataGrid").clearDataGrid();
	$("#showType").combobox("clear");
	returnTab('dtlTab', '销售单查询');
};

billShopBalance.save = function(){
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return;
	}
	if($('#mainDataForm').form('validate')){
			var pkVal = $('#id').val();
//			alert(pkVal);
			if(pkVal !=''){
				billShopBalance.doPut();
			}else{
				billShopBalance.createShopBalanceBill();//billShopBalance.doPost();
			}
	};
};

billShopBalance.createShopBalanceBill = function(){ 
	if($('#mainDataForm').form('validate')){
	var companyName = $('#companyName').val();
	var companyNo = $('#companyNo').val();
	var shortName = $('#shortName').val();
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
	var balanceType =$('#balanceType').combobox('getValue'); 
	if(companyName < 1){
		showWarn("公司为空,请选择要生成结算单的公司!");
		return;
	}
	if(shortName < 1){
		showWarn("店铺为空,请选择要生成结算单的店铺!");
		return;
	}
	if(month < 1){
		showWarn("结算月为空,请选择要生成结算单的结算月!");
		return;
	}
	//检查相关条件的结算单是否已经生成过
	
	message = "确定要生成"+companyName+"、" +shortName+"、" +month+" 的结算单？";
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			var url = billShopBalance.modulePath + '/saveShopBalanceBill';
			var shopBalanceObj ="";
			shopBalanceObj=companyNo+";"+companyName+";"+shopNo+";"+shortName+";"+month+";"+balanceType+";";
			ajaxRequestAsync(url,{"shopBalanceObj" : shopBalanceObj},function(data){
					if(!data.errorInfo){
						showSuc('操作成功');
						billShopBalance.loadData(data); 
						billShopBalance.search();				
					}else{
						showError(data.errorInfo);
					}
			    });
		}
	});
	}
};

//billShopBalance.generationbalanceNo = function(){
//	var no = "CSJS";
//	return no + new Date().format("yyyyMMddhhmmss");
//};

// 新增保存
billShopBalance.doPost = function(){
//	var billNo = billShopBalance.generationbalanceNo();
//	$('#balanceNo').val(billNo);
	$('#mainDataForm').form('submit',{
		url : billShopBalance.modulePath + '/post',
		onSubmit:function(param){
			param.status = 0;
		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
				billShopBalance.loadData(jsonData); 
			}else{
				showError('保存失败');
			}
		}
	});
};

// 修改保存
billShopBalance.doPut = function(){
	if($('#mainDataForm').form('validate')){
		var status = $('#status').val();	
		if(status == 2){
			showWarn("单据已审核确认,保存失败!");
			return;
		}
	}
	$('#mainDataForm').form('submit',{			
		url : billShopBalance.modulePath + '/put',
		onSubmit:function(param){
			param.status = 1;
		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
				billShopBalance.loadData(jsonData); 
			}else{
				showError('保存失败');
			}
		}
	});
};

billShopBalance.audit = function(){
	alert("!!!!!!!!!!");
};

//清空数据
billShopBalance.clearData = function(){
	$('#mainDataForm').find("input").val("");
	$('#mainDataForm').find("textarea").val("");
};

//可编辑状态
billShopBalance.enableMainInfo = function(){
	$("#mainDataForm").find("input").removeAttr("readonly").removeClass("readonly");
	$("#mainDataForm").find("textarea").removeAttr("readonly").removeClass("readonly");
//	billShopBalance.readonlyInfo();	
};

//点击切换到明细
billShopBalance.loadDetail = function(rowIndex, rowData) {
//	alert(JSON.stringify(rowData));
	billShopBalance.curRowIndex = rowIndex;
	var url = billShopBalance.modulePath + '/get';
	var params = {id : rowData.id};
	// 填充主表 resultData-服务器回传的数据
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: params,
		  cache: true,
		  async : false,
		  dataType : 'json',
		  success: function(resultData) {
			  billShopBalance.loadData(resultData); 
		  }
	});
	billShopBalance.disableMainInfo();
	var status = rowData.status;
	if(status == 2 || status == 4){
		//alert($("#mainDataForm").find("input[iptSearch=company]").length);
		$("#mainDataForm").find("input[iptSearch=company]").attr('disabled',true).addClass("disabled");
		$("#balanceType").combobox({disabled: true}); 
		$("#companyName,#mallNumberAmount,#estimateAmount,#differenceAmount,#otherTotalAmount,#remark").attr('readonly',true).addClass("readonly"); 
	}else {
		$("#mallNumberAmount,#mallBillingAmount,#balanceDesc,#remark").removeAttr("readonly").removeClass("readonly");
	}
	
	
//	$("#mallNumber,#balanceDesc,#remark").attr('readonly',false).addClass("readonly");  
//	$("#zoneNo,#mallNo,#bizCityNo,#shopNo,#month").combobox({disabled: true}); 
//	billShopBalance.enableMainInfo();
//	billShopBalance.loadDataGridBalanceDtl(rowData.balanceNo);
//	$("#showType").combobox("setValue", "1");

//	billShopBalance.loadDataGridPromotions(rowData.balanceNo);
//	billShopBalance.loadDataGridPaymentMethod(rowData.balanceNo);
//	billShopBalance.loadInvoiceInfo(rowData.billNo);
//	billShopBalance.initShowType();	
	
	setTimeout(function(){
		returnTab('mainTab', '单据明细');
		},500);	
	
//	$("#balanceDtlDataGrid").clearDataGrid();
//	$("#categoryDataGrid").clearDataGrid();
//	$("#promotionsDataGrid").clearDataGrid();
//	$("#paymentMethodDataGrid").clearDataGrid();
//	$("#showType").combobox("clear");
	// 默认选中“按大类显示”下拉框的值
	$("#showType").combobox("select", "1");
//	billShopBalance.loadDataGridCategory(rowData.balanceNo);
	 billShopBalance.loadCategorySum(rowData.shopNo,rowData.startDate,rowData.endDate,rowData.balanceNo);
	returnTab('dtlTab', '销售单查询');
};

//加载单据信息
billShopBalance.loadData = function(resultData){
	$('#mainDataForm').form('load', resultData);
	// 底部单据状态显示栏
	$('#status').html(resultData.status);
	$('#createUser').html(resultData.createUser);
	$('#createTime').html(resultData.createTime);
	$('#auditor').html(resultData.auditor);
	$('#auditTime').html(resultData.auditTime);
	
	var status = resultData.status;
	if(status == 2 || status == 4){
		$("#mainDataForm").find("input[iptSearch=company]").attr('disabled',true).addClass("disabled");
		$("#balanceType").combobox({disabled: true}); 
		$("#companyName,#mallNumberAmount,#estimateAmount,#differenceAmount,#otherTotalAmount,#remark").attr('readonly',true).addClass("readonly"); 
	}else {
		$("#mallNumberAmount,#estimateAmount,#differenceAmount,#otherTotalAmount,#remark").removeAttr("readonly").removeClass("readonly");
	}
};

// 加载明细行
billShopBalance.loadDataGridBalanceDtl = function(balanceNo){
	var params = {balanceNo : balanceNo};
	var url = billShopBalance.saleordmodulePath + '/list.json';
    $( '#balanceDtlDataGrid').datagrid( 'options').queryParams= params;
    $( '#balanceDtlDataGrid').datagrid( 'options').url=url;
    $( '#balanceDtlDataGrid').datagrid( 'load' );
};

billShopBalance.loadDataGridCategory = function(balanceNo){
	var params = {balanceNo : balanceNo};
	var url = billShopBalance.saleordmodulePath + '/getSumByCategory';
    $( '#categoryDataGrid').datagrid( 'options').queryParams= params;
    $( '#categoryDataGrid').datagrid( 'options').url=url;
    $( '#categoryDataGrid').datagrid( 'load' );
};

billShopBalance.loadDataGridPromotions = function(balanceNo){
	var params = {balanceNo : balanceNo};
	var url = billShopBalance.saleordmodulePath + '/getPromotions';
    $( '#promotionsDataGrid').datagrid( 'options').queryParams= params;
    $( '#promotionsDataGrid').datagrid( 'options').url=url;
    $( '#promotionsDataGrid').datagrid( 'load' );
};

billShopBalance.loadDataGridPaymentMethod = function(balanceNo){
	var params = {balanceNo : balanceNo};
	var url = billShopBalance.saleordmodulePath + '/getPaymentMethod';
    $( '#paymentMethodDataGrid').datagrid( 'options').queryParams= params;
    $( '#paymentMethodDataGrid').datagrid( 'options').url=url;
    $( '#paymentMethodDataGrid').datagrid( 'load' );
};

//paymentmethod_panel   promotions_panel   balancedtl_panel category_panel
billShopBalance.initShowType = function(){  
//	 $('#showType').combobox({
//	});
      $('#showType').combobox({
//	$("input[name='costType1']").combobox({    	  
          onChange:function(){         	
        	  var selvalue= $('#showType').combobox('getValue');
        	  if(selvalue==1){
            	  $('#balancedtl_panel').panel('close');
            	  $('#promotions_panel').panel('close');
            	  $('#paymentmethod_panel').panel('close');
            	  $('#category_panel').panel('open');
            	  var shopNo = $('#shopNo').val();
				  var startDate = $('#balanceStartDate').val();
				  var endDate = $('#balanceEndDate').val();
				  var balanceNo = $('#balanceNo').val();	
				  billShopBalance.loadCategorySum(shopNo,startDate,endDate,balanceNo);
              }
        	  else if(selvalue==2){
            	  $('#category_panel').panel('close');
            	  $('#promotions_panel').panel('close');
            	  $('#paymentmethod_panel').panel('close');
            	  $('#balancedtl_panel').panel('open');
            	  var shopNo = $('#shopNo').val();
				  var startDate = $('#balanceStartDate').val();
				  var endDate = $('#balanceEndDate').val();
				  billShopBalance.balanceOrderSaleDtlInfo(shopNo,startDate,endDate);            	  
              }else if(selvalue==3){
            	  $('#category_panel').panel('close');
            	  $('#balancedtl_panel').panel('close');
            	  $('#paymentmethod_panel').panel('close');
            	  $('#promotions_panel').panel('open');
            	  var shopNo = $('#shopNo').val();
				  var startDate = $('#balanceStartDate').val();
				  var endDate = $('#balanceEndDate').val();
				  var balanceNo = $('#balanceNo').val();	
				  billShopBalance.loadPromotions(shopNo,startDate,endDate,balanceNo);  
              } else if(selvalue==4){
            	  $('#category_panel').panel('close');
            	  $('#balancedtl_panel').panel('close');
            	  $('#promotions_panel').panel('close');
            	  $('#paymentmethod_panel').panel('open');
            	  var shopNo = $('#shopNo').val();
				  var startDate = $('#balanceStartDate').val();
				  var endDate = $('#balanceEndDate').val();
				  var balanceNo = $('#balanceNo').val();	
				  billShopBalance.loadPaymentMethodSum(shopNo,startDate,endDate,balanceNo);
              }
          }
      
      }); 
     
      $('#balancedtl_panel').panel('close');
	  $('#promotions_panel').panel('close');
	  $('#paymentmethod_panel').panel('close');
	  $('#category_panel').panel('open');
	  if($("#balanceNo").val()!='') {
		  var shopNo = $('#shopNo').val();
		  var startDate = $('#balanceStartDate').val();
		  var endDate = $('#balanceEndDate').val();
		  var balanceNo = $('#balanceNo').val();	
	      billShopBalance.loadCategorySum(shopNo,startDate,endDate,balanceNo);
      }
	  else {
    	  $("#categoryDataGrid").clearDataGrid();
	  }
  
 };
 
 billShopBalance.searchold = function() {
		var fromObjStr = convertArray($('#searchForm').serializeArray());
		var queryMxURL = BasePath + '/mall_shopbalance/list.json';
		$("#shopBalanceDataGrid").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
		$("#shopBalanceDataGrid").datagrid('options').url = queryMxURL;
		$("#shopBalanceDataGrid").datagrid('load');
	};

	billShopBalance.search = function() {
		var params = $('#searchForm').form('getData');
		var url = billShopBalance.modulePath + '/shop_list.json';
	    $('#shopBalanceDataGrid').datagrid('options').queryParams= params;
	    $('#shopBalanceDataGrid').datagrid('options').url= url;
	    $('#shopBalanceDataGrid').datagrid('load');
	};
	
	
	var nullMessage = "不存在当前单据";
	var nullCheckMessage = "请选中需要操作的单据!";
	var auditMessage = "只允许操作审核制单状态的单据";
	var backMessage = "只允许反审核已确认状态的单据!";
	var invalidMessage = "只允许作废已审核确认状态的单据!";//只允许操作作废确认状态的单据
	var deleteMessage = "只允许操作删除制单状态的单据!";
	var billMessage = "已经开票不允许反审核操作!";
	var audMessage="确定要审核？";
	//单据状态(1-制单、2-确认、3-作废)
	function getErrorMessage(currStatus ,operStatus){
		if(currStatus && currStatus ==""){
			return nullMessage;
		}
		if(typeof operStatus == 'undefined'){
			if(currStatus != 1){
				return deleteMessage;
			}
		}
//		if(operStatus == 2){
//			if(currStatus==1){
//				return audMessage;			
//			}			
//		}
		if(operStatus == 2){
			if(currStatus!=1){
				return auditMessage;
			}
		}
		else if(operStatus == 3){
			if(currStatus!=2){
				return backMessage;
			}
		}
		else if(operStatus == 1){
			if(currStatus==4){
				return billMessage;
			}
		}
//		else if(operStatus == 3){
//			if(currStatus!=1){
//				return invalidMessage;
//			}
//		}
//		else if(operStatus == 2){
//			if(currStatus!=1&&currStatus!=3){
//				return backMessage;
//			}
//		}
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
	
	
	billShopBalance.clear = function(){// 清空
		$('#searchForm').form("clear");
		$("#searchForm").find("input").val("");
	};
	
	billShopBalance.batchOperate = function(status){// 批量 审核-作废
		var checkedRows = $('#shopBalanceDataGrid').datagrid('getChecked');
		var errorMessage = getBatchErrorMessage(checkedRows,status);
		if(errorMessage!=""){
			showInfo(errorMessage);
			return ;
		}	
		var message = "";
		var auditor = null;
		var auditTime = null;
		if(status == 1){
			message = "你确定要反审核该条单据?";
		}else if(status == 2){
			 message = "你确定要审核该条单据?";
			 auditor = currentUser.loginName;
			 auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
		}else if(status == 3){
			 message = "你确定要作废该条单据?";
		}
		$.messager.confirm("确认",message,function(r) {
			if (r) {
				$.each(checkedRows, function(index, row) {
					row.status = status;
					row.auditor = auditor;
					row.auditTime = auditTime;
				});
				var data = {
						updated : JSON.stringify(checkedRows),
				};
				ajaxRequestAsync(billShopBalance.modulePath  + '/save',data,function(result){
					if(result){
						showSuc('操作成功');
						$("#shopBalanceDataGrid").datagrid('load');
						billShopBalance.loadData(result.data); 
						billShopBalance.search();	
					}else{
						showError('操作失败');
					}
				});
			}
		});
	};

	billShopBalance.operate = function(status){// 审核 -作废
		var errorMessage = getErrorMessage($('#status').val(),status);
		if(errorMessage!=""){
			showInfo(errorMessage);
			return ;
		}
		if($('#mainDataForm').form('validate')){
				var strStatus = $('#status').val();
				var message = "";
				if(status == 1){
					message = "你确定要反审核该条单据?";
				}else if(status == 2){
					 message = "你确定要审核该条单据?";
				}else if(status == 3){
					 message = "你确定要作废该条单据?";
				}
				$.messager.confirm("确认",message,function(r) {
					if (r) {
						$('#status').val(status);
						$('#mainDataForm').form('submit',{
							url : billShopBalance.modulePath  + '/put',
							onSubmit:function(param){
								 if(status == 1){	
									 param.status=$('#status').val(status);
									 param.auditor = currentUser.loginName;
									 param.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
								 }
								
							},
							success:function(data){
								if(data){
									showSuc('操作成功');
									var jsonData = JSON.parse(data);
									_self.loadMainData(jsonData); 
									billShopBalance.loadData(data); 
									billShopBalance.search();	
									$("#shopBalanceDataGrid").datagrid('load');
									if(status == 0){
										_self.enableEditClass();
									}else{
										_self.disableEditClass();
									}
									_self.search();
								}else{
									showError('操作失败');
								}
							}
						});
					}
				});
		}
	};
	
	billShopBalance.batchDel = function(){// 批量删除   删除主明细
		var checkedRows = $('#shopBalanceDataGrid').datagrid('getChecked');
		var errorMessage = getBatchErrorMessage(checkedRows);
		if(errorMessage!=""){
			showInfo(errorMessage);
			return ;
		}
		if(checkedRows.length < 1){
			showWarn("请选择要删除选中的单据?");
			return;
		}
		
		for(var i =0; i < checkedRows.length; i++){
			var item = checkedRows[i];
			if(item.status != "1"){
				showWarn("单据："+item.billNo+"，已审核确认不可删除！" );
				return false;
			 }
		}
		
		$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
			if (r) {
				var url = billShopBalance.modulePath + '/deleteShopBalance';
				var idList ="";
				$.each(checkedRows, function(index, row) {					
//					if(row.balanceNo!=""){
//					    idList+=row.id+','+row.billNo+','+row.balanceNo+";";
//					}else{
//						var balanceNo=null;
//						idList+=row.id+','+row.billNo+','+balanceNo+";";	
//					}					
					idList+=row.id+','+row.balanceNo+";";
//					idList+=row.id;
				});
				
				var params = {idList : idList.substring(0, idList.length-1)}; //{id:idList};//
				ajaxRequestAsync(url,params,function(count){
					if(count){
						showSuc('删除成功');
						billShopBalance.search();
						$("#shopBalanceDataGrid").datagrid('load');
					}else{
						showError('删除失败');
					}
					
				});
			}
		});
	};
	
	var statusformatter =[{'value':'1', 'text': '制单'},{'value':'2', 'text':'确认'},{'value':'3', 'text':'作废'},{'value':'4', 'text':'已开票'}];
	billShopBalance.statusformatter= function(value, rowData, rowIndex) {
	    if(value == '1') {
	    	return "制单";
	    }
	    if(value == '2') {
	    	return "确认";
	    }
	    if(value == 3) {
	    	return "作废";
	    }
	    // 区分是已生成开票申请还是已生成发票登记
	    if(rowData.invoiceApplyNo && !rowData.invoiceRegisterNo) {
	    	return "已开票申请";
	    }
	    if(rowData.invoiceRegisterNo) {
	    	return "已开票";
	    }
	    return "制单";
	};
	
	billShopBalance.exportExcel = function() {
		$.fas.exportExcel({
			dataGridId : "shopBalanceDataGrid",
			exportUrl : "/mall_shopbalance/do_fas_export",
			exportTitle : "商场门店结算单列表导出"
		});
	}
// billShopBalance.initShowType = function(){ 
// $('#showType').combobox({	 
//     onChange:function()
//     {
//    	 alert("111111111111");
//     } 
// });
// };