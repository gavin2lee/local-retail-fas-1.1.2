var salesSumReport = new Object();
var $this = this;
//查询
salesSumReport.search = function() {
	
	if(!$('#searchForm').form('validate')) {
		return;
	}
	
	var params = $('#searchForm').form('getData');
	//combobox多选参数处理
	var businessType = $("#businessType").combobox('getValues');
	var businessTypes = '';
	$.each(businessType, function(index, row) {
		businessTypes = businessTypes + row + ",";
	});
	params.businessType = businessTypes;
	
	/*var provinceNo = $("#provinceNo").combobox('getValues');
	var provinceNos = '';
	$.each(provinceNo, function(index, row) {
		provinceNos = provinceNos + row + ",";
	});
	params.provinceNo = provinceNos;*/
	
	/*var saleMode = $("#saleMode").combobox('getValues');
	var saleModes = '';
	$.each(saleMode, function(index, row) {
		saleModes = saleModes + row + ",";
	});
	params.saleMode = saleModes;
	
	var retailType = $("#retailType").combobox('getValues');
	var retailTypes = '';
	$.each(retailType, function(index, row) {
		retailTypes = retailTypes + row + ",";
	});
	params.retailType = retailTypes;
	
	var shopMulti = $("#shopMulti").combobox('getValues');
	var shopMultis = '';
	$.each(shopMulti, function(index, row) {
		shopMultis = shopMultis + row + ",";
	});
	params.shopMulti = shopMultis;
	
	var shopLevel = $("#shopLevel").combobox('getValues');
	var shopLevels = '';
	$.each(shopLevel, function(index, row) {
		shopLevels = shopLevels + row + ",";
	});
	params.shopLevel = shopLevels;*/
	
	var categoryNo = $("#categoryNo").combobox('getValues');
	var categoryNos = '';
	$.each(categoryNo, function(index, row) {
		categoryNos = categoryNos + row + ",";
	});
	params.categoryNo = categoryNos;
	
	var url = BasePath + '/bill_sales_sum/list.json';
    $('#salesSumDataGrid').datagrid('options').queryParams= params;
    $('#salesSumDataGrid').datagrid('options').url= url;
    
    $('#salesSumDataGrid').datagrid({
    	rowStyler:function(index,row){
    		if(row.zoneName == '小计：') {
    			return 'background-color:#FFFFE0;';
    		}
    		else if(row.zoneName == '合计：') {
    			return 'background-color:#FFF0F5';
    		}
    	}
    });
    
};

//清空
salesSumReport.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
	salesSumReport.initDate(); 
};

salesSumReport.initCombobox = function(){
	var balanceStateArray = 
		[
		 {'value' : '1' , 'text' : '已结算'},
		 {'value' : '0' , 'text' : '未结算'}
       ];
	var invoiceStateArray = 
		[
		 {'value' : '1' , 'text' : '已开票'},
		 {'value' : '0' , 'text' : '未开票'}
       ];
	var countTypeArray = 
		[
		 {'value' : '1' , 'text' : '业务类型'},
		 {'value' : '2' , 'text' : '公司'},
		 {'value' : '3' , 'text' : '管理城市'},
		 {'value' : '4' , 'text' : '店铺/客户'},
		 {'value' : '5' , 'text' : '年月'},
		 {'value' : '6' , 'text' : '结算期'},
		 {'value' : '7' , 'text' : '品牌'}
       ];
	
	$('#balanceState').combobox({
		data : balanceStateArray,
		valueField : 'value',
		textField : 'text'
	});
	
	$('#invoiceState').combobox({
		data : invoiceStateArray,
		valueField : 'value',
		textField : 'text'
	});

	$('#countType').combobox({
		data : countTypeArray,
		valueField : 'value',
		textField : 'text'
	});
};

//导出
salesSumReport.exportExcel = function() {
	exportSalesSumReport();
};

//店铺结算期检查
salesSumReport.checkShopBalanceDate = function() {
	ygDialog({
		title : '结算期检查',
		target : $('#checkShopBalanceDatePanel'),
		width : 520,
		height : 220,
		buttons : [ {
			text : '检查店铺结算月',
			iconCls : 'icon-save',
			handler : function(dialog) {
				
				var fromObj = $('#checkShopBalanceDateForm');
				if(!fromObj.form('validate')) {
					return;
				}
				
				var url = BasePath + '/bill_sales_sum/checkShopBalanceDate';
				fromObj.form('submit', {
					url : url,
					onSubmit : function(param) {
						$.messager.progress({
			    			title:'请稍后',
			    			msg:'正在处理中...'
			    		});
					},
					success : function(result) {
						$.messager.progress('close');
						var resultDate = JSON.parse(result);
						if(resultDate.rows == null || resultDate.rows == '') {
							showInfo("恭喜！该日期范围内店铺都已设置结算期");
							return;
						}
						$('#shopBalanceDateResultDataGrid').datagrid( 'loadData', resultDate.rows );
						ygDialog({
							title : '未设置结算期的店铺',
							target : $('#shopBalanceDateResultPanel'),
							width : 400,
							height : 320
						});
					}
				});
				
			}
		},{
			text : '检查内购结算期',
			iconCls : 'icon-save',
			handler : function(dialog) {
				
				var fromObj = $('#checkShopBalanceDateForm');
				if(!fromObj.form('validate')) {
					return;
				}
				
				var url = BasePath + '/bill_sales_sum/checkInnerBuyBalanceDate';
				fromObj.form('submit', {
					url : url,
					onSubmit : function(param) {
						$.messager.progress({
			    			title:'请稍后',
			    			msg:'正在处理中...'
			    		});
					},
					success : function(result) {
						$.messager.progress('close');
						var resultDate = JSON.parse(result);
						if(resultDate.rows == null || resultDate.rows == '') {
							showInfo("恭喜！该日期范围内的公司都已设置内购结算期");
							return;
						}
						$('#innerBuyBalanceDateResultDataGrid').datagrid( 'loadData', resultDate.rows );
						ygDialog({
							title : '未设置结算期的公司',
							target : $('#innerBuyBalanceDateResultPanel'),
							width : 400,
							height : 320
						});
					}
				});
				
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		} ]
	});
}

// 初始化
$(function(){
	salesSumReport.initCombobox();
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	if(month == 0) {
		year = year - 1;
		month = 12;
	}
	var month = month < 10 ? '0' + month : month;
	$('#saleStartDate').datebox('setValue', year + '-' + month + '-01');
	var  day = new Date(year,month,0);
	$('#saleEndDate').datebox('setValue', year + '-' + month + '-' + day.getDate());
	
	// 单据明细增加折叠功能
    toolSearch({
        appendTo:$('#toolbar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true 		//是否显示下拉箭头
    });
    
    //仅显示小计勾选前需选择小计维度
    $('#onlyCount').bind("click", function() {
    	if($(this).is(":checked")) {
    		var countType = $('#countType').combobox("getValue");
    		if(countType == null || countType == '') {
    			showWarn("请先选择小计维度！");
    			$(this).attr("checked", false);
    		}
    	}
    });
	
	//选择业务类型后才可选择店铺查询条件
	$('#businessType').combo({
		onChange : function(newValue, oldValue) {
	    	checkedValue = $(this).combotree('tree').tree('getChecked');
    		var isShop = false;
    		$.each(checkedValue, function(index, row) {
    			if(row.id == 11 || row.id == 12 || row.id == 51 || row.id == 52) isShop = true;
    		});
    		if(isShop) {
    			$("#shopName").removeAttr("disabled");
    		} else {
    			$("#shopName").attr('disabled','disabled');
    			$("#shopName").val('');
    			$("#shopNo").val('');
    		}
	    	
		}
    });
    
});
