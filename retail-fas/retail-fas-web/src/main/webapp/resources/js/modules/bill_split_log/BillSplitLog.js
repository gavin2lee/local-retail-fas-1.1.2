// 弹出框
function FinancialCategoryDialog() {
	this.split = function() {
		ygDialog({
			title : '手动拆单',
			target : $('#myFormPanel'),
			width : 400,
			height : 300,
			enableCloseButton: false,
			buttons : [
//			{
//				text : '查看拆单情况',
//				iconCls : 'icon-search',
//				handler : function(dialog) {
//					bill_split_log.showSplitLog();
//				}
//			},
			{
				text : '确定',
				iconCls : 'icon-save',
				handler : function(dialog) {
					bill_split_log.manualSplit();
				}
			}, {
				text : '关闭',
				iconCls : 'icon-close',
				handler : function(dialog) {
					dialog.close();
				}
			}]
		});
	};
	//手动拆单
	this.manualSplit = function() {
		var fromObj = $('#dataForm');
		var validateForm = fromObj.form('validate');
		// 1.校验必填项
		if (validateForm == false) {
			return;
		}
		fromObj.form('submit', {
			url : BasePath + '/bill_split_log/manual_split',
			dataType : 'json',
			onSubmit : function(params) {
				showSuc('后台正在拆单!');
			},
			success : function(result) {
				if(result) {
					$("#myFormPanel").window('close');
					return;
				} else {
					showError('拆单失败,请联系管理员!');
				}
			},
			error : function() {
				showError('拆单失败,请联系管理员!');
			}
		});
	};
	//手动拆单
	this.showSplitLog = function() {
		var fromObj = $('#dataForm');
		var validateForm = fromObj.form('validate');
		// 1.校验必填项
		if (validateForm == false) {
			return;
		}
		fromObj.form('submit', {
			url : BasePath + '/bill_split_log/show_split_log',
			dataType : 'json',
			onSubmit : function(params) {
				
			},
			success : function(result) {
				showWarn(result);
				return 
			},
			error : function() {
				showError('操作失败,请联系管理员!');
			}
		});
	};
	
	// tab查询页面中的查询方法
	this.querySearch = function() {
		
		var params = $('#querySearchForm').form('getData');
		var url = BasePath + '/bill_split/selectSplitData';
	    $('#queryDataGridDiv').datagrid('options').queryParams= params;
	    $('#queryDataGridDiv').datagrid('options').url= url;
	    $('#queryDataGridDiv').datagrid('load');
	};
	// tab查询页面进行清空操作
	this.queryClear = function() {
		$.fas.clear("querySearchForm");
	};
	//结算主体调整
	this.showSettlePath = function() {
		var checkedRows = $('#queryDataGridDiv').datagrid('getChecked');
		if(checkedRows.length > 0){
			var flag = false;
			var buyerNo = checkedRows[0].buyerNo;
			var supplierNo = checkedRows[0].supplierNo;
			$.each(checkedRows,function(index,row){
				if(row.buyerNo != buyerNo || row.supplierNo != supplierNo){
					flag = true;
				}
			});
			
			if(flag) {
				showInfo("每次调整只能选择同一采购公司和同一供应商的单据！请重新选择！");
				return;
			}
			
			var url = BasePath + '/bill_split/selectSettlePath';
			$('#settlePathDataGrid').datagrid( 'options' ).queryParams= {supplierNo:supplierNo,companyNo:buyerNo};
			$('#settlePathDataGrid').datagrid( 'options' ).url= url;
			$('#settlePathDataGrid').datagrid( 'load' );
			
			ygDialog({
				title : '结算路径选择',
				width : 1000,
	    		height : 500,
	    		target : $('#settlePathPanel'),
				buttons: [{
					id:'sure',
					iconCls:"icon-save",
		            text: '确认',
		            handler: function(dialog) {
		            	$.messager.progress({
		        			title:'请稍后',
		        			msg:'正在处理中...'
		        		});
		            	var checkedSettlePath = $('#settlePathDataGrid').datagrid('getChecked');
		            	$.ajax({
		          		  type: 'POST',
		          		  url: BasePath + "/bill_split/splitData",
		          		  data: {checkedRows:JSON.stringify(checkedRows),checkedSettlePath:JSON.stringify(checkedSettlePath)},
		          		  cache: true,
		          		  //async:false,
		          		  dataType: 'json', 
		          		  success: function(data){
		          			  if(data.isSuccess) {
		          				  showInfo(data.message);
		          			  }
		          			  else {
		          				  showError(data.message);
		          			  }
		          			  $.messager.progress('close');
		          			  $('#queryDataGridDiv').datagrid('load');
		          		  }
		            	});
		            	dialog.close();
		            }
		        },
		        {
		        	iconCls:"icon-cancel",
		        	text: '取消',
		            handler: function(dialog) {
		                dialog.close();
		            }
		        }],
				onLoad : function(win, content) {
					dialog.getCheckRows = content.getCheckRows;
					dialog.getRowData = content.getRowData;
				}
			});
		}
		else{
			showInfo("请选中需要调整的记录！");
		}
	};
};

var dialog = null;
$(function() {
	$.fas.extend(FinancialCategoryDialog, FasDialogController);
	dialog = new FinancialCategoryDialog();
	dialog.init("/bill_split_log", fas_common_setting);
	
	//设置结算类型下拉框
	var balanceTypeArray = 
		[
		 {'value' : '1' , 'text' : '总部统采'},
		 {'value' : '0' , 'text' : '总部代采'}
       ];
	$('#balanceType').combobox({
		data : balanceTypeArray,
		valueField : 'value',
		textField : 'text'
	});
	
	$('#sendDateStart').datebox('setValue', getStartDate());
	$('#sendDateEnd').datebox('setValue', getEndDate());
	
});