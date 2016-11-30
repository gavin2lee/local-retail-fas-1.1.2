var billNoMenuRedirect = {}

//以单号生成超链接，实现跳转
billNoMenuRedirect.billNoMenuFormat = function(value,row,index,title) {
	var result ='';
	if(value && value != null && value!='') {
		var arr=new Array();
		if(value.indexOf(',') != -1) {
			arr = value.split(',');
			for(i = 0;i<arr.length;i++) {
				result = result + ', <a href=javascript:void(0) onclick=page_jump(&quot;'+arr[i]+'&quot;,&quot;' + title + '&quot;) style=cursor:pointer;text-decoration:underline;color:blue;>'+arr[i]+'</a>';
			}
			result = result.substring(1, result.length - 1);
		}else {
			result = '<a href=javascript:void(0) onclick=page_jump(&quot;'+value+'&quot;,&quot;' + title + '&quot;) style=cursor:pointer;text-decoration:underline;color:blue;>'+value+'</a>';
		}
	}else {
		result = value;
	}
	return result;
}

//跳转方法 value为单号 ，title为节点名称
function page_jump(value, title) {
	var url;
	if(title != '') {
		if(title == 'AI-请款单') {
			url = "bill_ask_payment/area/list.htm?billNoMenu=";
		}else if (title == 'AI-付款单') {
			url = "bill_payment/area/list.htm?billNoMenu=";
		}else if (title == 'AI-开票申请') {
			url = "invoice_apply/area/bill_balance_invoice_apply?billNoMenu=";
		}else if (title == 'AI-采购发票') {
			url = "bill_invoice/area/list.htm?billNoMenu=";
		}else if (title == 'AI-发票登记') {
			url = "invoice_register/area/listInvoiceRegister?billNoMenu=";
		}else if (title == 'HP-结算单') {
			url = "hq_insteadOf_buy_balance/balance_bill?billNoMenu=";
		}else if (title == 'AP-结算单查询') {
			url = "area_insteadOf_buy_balance/balance_bill?atoa=yf&billNoMenu=";
		}else if (title == 'HS-结算单') {
			url = "bill_balance/type1/buy_balance.htm?billNoMenu=";
		}else if (title == 'HU-结算单') {
			url = "bill_balance/type2/sale_balance.htm?billNoMenu=";
		}else if (title == 'AU-结算单确认') {
			url = "bill_balance/type4/sale_balance.htm?isArea=true&billNoMenu=";
		}else if (title == 'HI-开票申请') {
			url = "invoice_apply/hq/bill_balance_invoice_apply?isHQ=true&billNoMenu=";
		}else if (title == 'HI-请款单') {
			url = "bill_ask_payment/hq/list.htm?isHQ=true&billNoMenu=";
		}else if (title == 'HI-付款单') {
			url = "bill_payment/hq/list.htm?isHQ=true&billNoMenu=";
		}else if (title == 'HI-采购发票') {
			url = "bill_invoice/hq/list.htm?isHQ=true&billNoMenu=";
		}else if (title == 'HI-发票登记') {
			url = "invoice_register/hq/listInvoiceRegister?isHQ=true&billNoMenu=";
		}else if (title == 'AO-结算单'){
			url = "area_other_stock_out_balance/balance_bill.htm?billNoMenu=";
		}else if (title == 'HE-结算单确认') {
			url = "hq_other_stock_in_balance/balance_bill?billNoMenu=";
		}else if (title == 'AM-结算单') {
			url = "area_private_purchase_balance/balance_bill?billNoMenu=";
		}else if (title == 'AS-结算单'){
			url = "area_among_balance/balance_bill?billNoMenu=";
		}else if (title == 'AB-结算单确认'){
			url = "area_among_pay/payment_bill?billNoMenu=";
		}else if (title == 'AW-结算单') {
			url = "bill_balance_zone/list?billNoMenu=";
		}else if (title == 'HO-结算单') {
			url = "hq_other_stock_out_balance/balance_bill.htm?billNoMenu=";
		}else if (title == 'AE-结算单确认') {
			url = "area_other_stock_in_balance/balance_bill?billNoMenu=";
		}else if (title == 'HW-结算单') {
			url = "bill_balance_zone/list?isHq=true&billNoMenu=";
		}
		//打开新Tab页
		openNewPane(url+value, null, title);
	}
	
}