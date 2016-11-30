var invoiceApplyBalance = {};

var currentUser;

//开票申请
invoiceApplyBalance.invoiceApplyPath = BasePath + '/bill_balance_invoice_apply';

//普通发票登记
invoiceApplyBalance.invoiceRegisterPath = BasePath + '/bill_balance_invoice_register';

var i;
//初始化
$(function() {
	i = 1;
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	//加载预警列表
	loadWarnMessageInvoiceList();
});

function loadWarnMessageInvoiceList(){
	var warnPostUrl = $("#warnPostUrl").val();
	if(warnPostUrl != null && warnPostUrl != ''){
		warnPostUrl = warnPostUrl.replaceAll(":", "&");
		if($('#invoiceApplyDataGrid').length>0){
			$('#invoiceApplyDataGrid').datagrid( 'options' ).url= BasePath + warnPostUrl;
			$('#invoiceApplyDataGrid').datagrid( 'load' );
		}else{
			if(i>3){
				return;
			}
			i++;
			setTimeout("loadWarnMessageInvoiceList()", 3000);
		}
	}
}

invoiceApplyBalance.invoiceApply = function(dataGridDi,balanceType){ 
	var checkedRows = $("#"+dataGridDi).datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		showWarn("请选择要生成开票的数据信息!");
		return;
	}
	
	if(5 == balanceType || 11 == balanceType || 2 == balanceType || 7 == balanceType || 14 == balanceType){
		var flag = true;
		$.each(checkedRows, function(index, item) {
			if(item.status != '4') {
				flag = false;
				return false;
			}
		});
		if(!flag && 5 == balanceType) {
			showWarn("只有调入财务确认的单据才能生成开票申请，请重新选择！");
			return;
		}else if(!flag && 11 == balanceType) {
			showWarn("只有总部财务确认的单据才能生成开票申请，请重新选择！");
			return;
		}else if(!flag && (2 == balanceType || 7 == balanceType || 14 == balanceType)) {
			showWarn("只有地区财务确认的单据才能生成开票申请，请重新选择！");
			return;
		}
	}else{
		// 地区商场结算页面中，控制只有审核状态的单据才能生成开票申请
		var flag = true;
		$.each(checkedRows, function(index, item) {
			if(item.status != '2') {
				flag = false;
				return false;
			}
		});
		if(!flag) {
			showWarn("只有审核状态的单据才能生成开票申请，请重新选择！");
			return;
		}
	}
//	for(var i =0; i < checkedRows.length; i++){
//		var item = checkedRows[i];
//		if(item.billNo<1){//主要是商场结算时,把balanceNo赋值给billNo
//			item.billNo=item.balanceNo;
//		}
//		if(item.status == '6'){
//			 showWarn("单据："+item.billNo+"，已开票！" );
//			 return false;
//		}else if(item.status != "2" && item.status  != "4" ){
//			showWarn("单据："+item.billNo+"，财务确认后才能生成开票信息！" );
//			return false;
//		 }else 
//		if(item.balanceAmount == 0){
//			showWarn("单据："+item.billNo+"，开票申请金额为0，不能开票！" );
//			return false;
//		 }
//	}
	var list = new Array();
//	$.each(checkedRows, function(index, item){
		for(var i =0; i < checkedRows.length; i++){
//			var index = $("#"+dataGridDi).datagrid('getRowIndex',rows[i]);
			var item = checkedRows[i];
			if(item.billNo<1){//主要是商场结算时,把balanceNo赋值给billNo
				item.billNo=item.balanceNo;
			}
//			var status = rows[i].status;
			var obj = new Object();		
			//商场门店
			if(balanceType==10){ 
			    obj.balanceNo = item.balanceNo;
			    obj.invoiceApplyNo = item.invoiceApplyNo;
			    
			    if(item.status == '4'){
					 showWarn("单据："+item.balanceNo+"，已开票！" );
					 return false;
				}else if(item.status != "2" && item.status  != "4" ){
					showWarn("单据："+item.balanceNo+"，财务确认后才能生成开票信息！" );
					return false;
				 }else 
				if(item.mallBillingAmount == 0){
					showWarn("单据："+item.balanceNo+"，开票申请金额为0！" );
//					return false;
				 }
			    if(item.balanceType == 2){
					showWarn("单据："+item.balanceNo+"，为预估结算单，不能开票！" );
					return false;
				 }
//			    if(item.mallNo == ''  || item.mallNo == null){
//					showWarn("店铺："+item.shortName+"，编号：" + item.shopNo + "，没有商场，不能开票，请检查门店所属的商场基础设置表！" );
//					return false;
//				 }				 
				if(item.invoiceApplyNo != ''  && item.invoiceApplyNo != null){
					showWarn("单据编号为："+item.balanceNo+"，已经生成过开票信息！申请单号为："+item.invoiceApplyNo);
					return;
				}	
				
			 	obj.salerNo = item.companyNo;
				obj.salerName=item.companyName;
				obj.buyerNo = item.shopNo; 
				obj.buyerName = item.shopName;
				obj.mallNo= item.mallNo; 
				obj.mallName=item.mallName;
				obj.balanceAmount=item.mallBillingAmount;
				obj.systemSalesAmount=item.systemSalesAmount;
				obj.organNo = item.organNo;
				obj.organName = item.organName;
				obj.month = item.month;
				obj.brandNo = item.brandNo;
				obj.brandName = item.brandName;
		  } else {//其他几种
			  obj.balanceNo = item.billNo;
			  obj.invoiceApplyNo = item.invoiceApplyNo;
//			  发出方财务确认    接收方财务确认
//			  if(item.statusName != '' && item.statusName != null ){
//				  
//			  }
			  if(item.status == '6'){
				  showWarn("单据："+item.billNo+"，已开票！" );
				  return ;
			  }else if(item.status != "2" && item.status  != "4" ){
				 showWarn("单据："+item.billNo+"，财务确认后才能生成开票信息！" );
				return;
			 }
			 if(item.invoiceApplyNo != '' && item.invoiceApplyNo != null ){
				showWarn("单据编号为："+item.billNo+"，已经生成过开票信息！申请单号为："+item.invoiceApplyNo);
				return;
			 }			   		  
			 obj.salerNo= item.salerNo; 
			 obj.salerName=item.salerName;
			 obj.buyerNo= item.buyerNo; 
			 obj.buyerName=item.buyerName;
			 obj.balanceAmount=item.balanceAmount ;
			 if(balanceType==7){
				 obj.organNo = item.organNoFrom;
				 obj.organName = item.organNameFrom;
			 }
			 var temp_year1=item.balanceEndDate.substring(0,4) ;
		     var temp_month1=item.balanceEndDate.substring(5,7);
			 obj.month = temp_year1+temp_month1;
		  }
		   var param = new Object();
		    param.status = 1;
			param.balanceNo = obj.balanceNo;
			param.balanceType = balanceType;
			param.salerNo=obj.salerNo; 
			param.salerName=obj.salerName;
			param.buyerNo=obj.buyerNo; 
			param.buyerName=obj.buyerName; 
			param.mallNo= obj.mallNo; 
			param.mallName=obj.mallName;
			param.amount=obj.balanceAmount;
			param.systemSalesAmount=obj.systemSalesAmount;
			param.organNo = obj.organNo;
			param.organName = obj.organName;
			param.month = obj.month;
			param.brandNo = obj.brandNo;
			param.brandName = obj.brandName;
			param.createUser = currentUser.loginName;
			param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			param.updateUser = currentUser.loginName;
			param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			param.auditor = currentUser.loginName;
			param.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			
			param.invoiceType=1;//是否预开票：否
			
			list.push(param);
//		   ajaxRequestAsync(invoiceApplyBalance.invoiceApplyPath + '/addBillBalanceInvoiceApply',param, function(data){
//			   msg = msg + data +"";
//				if(data > 0){
//					showSuc('成功生成'+data+'条请款单');
//				}else{
//					showError('生成失败');
//				}
//			});
		}
		param.checkedRows = JSON.stringify(list);
		ajaxRequestAsync(invoiceApplyBalance.invoiceApplyPath + '/addBillBalanceInvoiceApply',param, function(data){
			if(data[0].errorInfo == ''){
				showSuc("生成成功！单据："+data[0].billNo+"。" );
			}else{
				var url = BasePath + '/bill_balance_invoice_apply/show_error_info';
				ygDialog({
					title : '信息提示：',
					href : url,
					width : 900,
					height : 'auto',
					isFrame : true,
					modal : true,
					showMask : true,
					buttons: [
			        {
			            text: '关闭',
			            handler: function(dialog) {
			                dialog.close();
			            }
			        }],
					onLoad : function(win, content) {
						content.loadData(data);
					}
				});
			}
		});
		$("#"+dataGridDi).datagrid('load');
 };
 
 invoiceApplyBalance.invoiceRegister = function(){ 
	 var checkedRows = $("#invoiceApplyDataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
		if(checkedRows.length < 1){
			showWarn("请选择要登记发票的开票申请!");
			return;
		}
//		for(var i =0; i < checkedRows.length; i++){
//			var item = checkedRows[i];
//			if(item.billNo<1){//主要是商场结算时,把balanceNo赋值给billNo
//				item.billNo=item.balanceNo;
//			}
//			if(item.status == '3'){
//				showWarn("单据："+item.billNo+"，已开票！" );
//				return ;
//			}else if(item.status == "1"){
//				showWarn("单据："+item.billNo+"，确认后才能登记发票信息！" );
//				return false;
//			}
//			if(item.balanceAmount == 0){
//				showWarn("单据："+item.billNo+"，开票申请金额为0！" );
////				return false;
//			}
//		}
		var flag = true;
		$.each(checkedRows, function(index, obj) {
//			if(index == 0){
//				salerNos = obj.salerNo;
//				buyerNos = obj.buyerNo;
//				preInvoice = obj.preInvoice;
//			}else{
//				if(salerNos != obj.salerNo || buyerNos != obj.buyerNo){
//					showWarn("选中记录中公司及客户必须一致");
//					flag = false;
//					return ;
//				}
//				if(preInvoice != obj.preInvoice){
//					showWarn("选中记录中预开票与开票申请必须一致");
//					flag = false;
//					return ;
//				}
				if(obj.status == '3'){
					showWarn("单据："+obj.billNo+"，已开票！" );
					flag = false;
					return ;
				}else if(obj.status == "1"){
					showWarn("单据："+obj.billNo+"，确认后才能登记发票信息！" );
					flag = false;
					return ;
				}
				if(obj.balanceAmount == 0){
					showWarn("单据："+obj.billNo+"，开票申请金额为0！" );
				}
//			}
    	});
		
		if(flag){
		   var param = new Object();
			param.checkedRows = JSON.stringify(checkedRows);//把选中的明细传到后台处理
			ajaxRequestAsync(invoiceApplyBalance.invoiceRegisterPath + '/addInoviceRegisters', param, function(data){
				var url = BasePath + '/bill_balance_invoice_register/show_error_info';
				ygDialog({
					title : '信息提示：',
					href : url,
					width : 850,
					height : 'auto',
					isFrame : true,
					modal : true,
					showMask : true,
					buttons: [
			        {
			            text: '关闭',
			            handler: function(dialog) {
			                dialog.close();
			            }
			        }],
					onLoad : function(win, content) {
						content.loadData(data);
					}
				});
			});
			$("#invoiceApplyDataGrid").datagrid('load');
		}
		
 };
 
 //员购，团购的普通发票登记 第一个参数为列表ID，第二个参数为结算类型，第三个参数为发票类型，第四为收票方编号、第五为收票方名称
 invoiceApplyBalance.invoiceRegisters = function(dataGridDi,param,checkedRows){
//	 var checkedRows = $("#"+dataGridDi).datagrid("getChecked");// 获取所有勾选checkbox的行
//		if(checkedRows.length < 1){
//			showWarn("请选择要登记发票的数据信息!");
//			return;
//		}
//		
//		var amountTotal = 0;
//		$.each(checkedRows, function(index, item){
//			if(item.invoiceNo != '' && item.invoiceNo != null){
//				showWarn("单据编号为："+item.billNo+"，已经登记过普通发票!发票号为："+item.invoiceNo);
//				return;
//			}
//			if(item.billTypeName == '团购出库单'){
//				showWarn("团购出库单无需普通发票登记操作!");
//				return;
//			}
//			if(param.balanceType == '8'){// 团购取其他收款方式金额作为发票金额
//				amountTotal = amountTotal + item.otherAmount;
//			}else{//员购 取结算额
//				amountTotal = amountTotal + item.settleAmount;
//			}
//		});
//		param.amount = amountTotal;
//		param.invoiceType = invoiceType;//团购时手动选择，作为参数传过来；员购默认为普通发票，参数值默认为0
 	param.status = 0;
	param.billDate = new Date().format("yyyy-MM-dd hh:mm:ss");
	param.createUser = currentUser.loginName;
	param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	param.updateUser = currentUser.loginName;
	param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	param.auditor = currentUser.loginName;
	param.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	param.preInvoice = 1;// 不是预开票标识
	param.checkedRows = JSON.stringify(checkedRows);//把选中的明细传到后台处理
	ajaxRequestAsync(invoiceApplyBalance.invoiceRegisterPath + '/addByGroupOrMemberBuyer', param, function(data){
		showSuc('保存成功');
		$("#"+dataGridDi).datagrid('load');
	});
 };
 

 