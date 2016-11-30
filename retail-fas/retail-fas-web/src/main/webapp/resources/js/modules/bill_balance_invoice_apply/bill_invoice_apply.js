
var billInvoiceApplyObj = '';

function BillInvoiceApply(){
	var $this = this;
	
	var defaultSetting = {
		formId : 'billSearchForm',
		dataGridId : 'billDataGridDiv'
	}
	
	
	BillInvoiceApply.prototype.searchBillSaleBalance = function(){
		
		var search_url = BasePath + "/bill_invoice_apply_generate/query_bill.json";
		var flag = $("#billSearchForm").form('validate');
		if(flag){
			var fromObjStr = convertArray($("#" + defaultSetting.formId).serializeArray());
			
			// 2.加载明细 注意请求方式 restful风格 get
			$("#billDataGridDiv").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
			$("#billDataGridDiv").datagrid('options').url = search_url;
			$("#billDataGridDiv").datagrid('load');
			
		}
	};
	
	BillInvoiceApply.prototype.generateInvoiceApply = function(){
		
		var url = BasePath + "/bill_invoice_apply_generate/save_bill_invoice";
		var checkedRowsTemp = $("#billDataGridDiv").datagrid('getChecked');
		
		if(checkedRowsTemp.length < 1){
			showWarn('请勾选要操作的行');
		}else{
			
			$this.showPannl(checkedRowsTemp,url);
			
			/*var data = '';
			for(var i = 0;i < checkedRowsTemp.length;i++){
				var row = checkedRowsTemp[i];
				if(i == checkedRowsTemp.length-1){
					data = data + JSON.stringify(row);
				}else{
					data = data + JSON.stringify(row)+'&';
				}
			}
			
			var changedRow = {
				checkedRows : data	
			}
			ajaxRequestAsync(url, changedRow, function(result){
				if(result.success){
					showSuc('生成成功');
					$this.searchBillSaleBalance();
				}else{
					alert('生成失败 ,' + result.error);
				}
			});*/
		}
	};
	
	BillInvoiceApply.prototype.showPannl = function(checkedRowsTemp,url){
		var companyNo = $("#searchCompanyNo").val();
		$('#buyerName').combogrid({
			url : BasePath+'/base_setting/invoice_info_set/list.json?companyNo='+companyNo
		});
		ygDialog({
			title : '选择收票方',
			target : $('#myFormPanel'),
			width : 630,
			height : 130,
			buttons : [{
				text : '确定',
				iconCls : 'icon-save',
				handler : function(dialog) {
					
					var buyerName = $("#buyerName").customerInvoiceInfo("getValue");
					var buyerNo = $("#buyerNo").val();
					var invoiceInfoStatus = $("#invoiceInfoStatus").val();
					
					var data = '';
					for(var i = 0;i < checkedRowsTemp.length;i++){
						var row = checkedRowsTemp[i];
						if(i == checkedRowsTemp.length-1){
							data = data + JSON.stringify(row);
						}else{
							data = data + JSON.stringify(row)+'&';
						}
					}
					
					var changedRow = {
						checkedRows : data,
						buyerNo : buyerNo,
						buyerName : buyerName,
						invoiceInfoStatus : invoiceInfoStatus
					}
					ajaxRequestAsync(url, changedRow, function(result){
						if(result.success){
							showSuc('生成成功');
							dialog.close();
							$this.searchBillSaleBalance();
						}else{
							showError('生成失败 ,' + result.error);
						}
					});
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			}]
		});
	};
	
	BillInvoiceApply.prototype.clearForm = function(){
		$("#" + defaultSetting.formId).form("clear");
		$("#" + defaultSetting.formId).find("input").val("");
		$("#" + defaultSetting.formId).find("textarea").val("");
	};
};


$(function(){
	var billInvoiceApply = new BillInvoiceApply();
	billInvoiceApplyObj = billInvoiceApply;
});

