
function ExceptionPriceCheck(){
	
	var $this = this;
	
	ExceptionPriceCheck.prototype.startLookingExceptionPrice = function(){
		var look_url = '/exception_price_check/find_exceptionPrice';
		var validate = $("#searchForm").form('validate');
		if(!validate){
			return;
		}
		
		var url = BasePath + look_url;
		var fromObjStr = convertArray($("#searchForm").serializeArray());
		var params = eval("(" + fromObjStr + ")");
		
		ajaxRequestAsync(url, params, function(result){
			if(result.success){
				showSuc('后台正在执行，稍后查看结果');
				$("#btn-search-gms").click();
			}else{
				showError('执行失败');
			}
		});
	};
	
	ExceptionPriceCheck.prototype.initCombobox = function(){
		
		var data = [ {'value' : '1301' , 'text' : '到货单'}, {'value' : '1304' , 'text' : '验收单'},
			 {'value' : '1333' , 'text' : '原残退厂发货单'},{'value' : '1372' , 'text' : '调货入库单'},
			 {'value' : '1371' , 'text' : '调货出库单'} ];
		
		//地区间结算
		var balanceType = [{'value' : '1' , 'text' : '地区价异常'}, {'value' : '2' , 'text' : '采购价异常'},{'value' : '3' , 'text' : '牌价异常'}];
		
		$('#billBalanceType').combobox({
			data : balanceType,
			valueField : 'value',
			textField : 'text'
		});
		
		$('#billType').combobox({
			data : data,
			valueField : 'value',
			textField : 'text'
		});
		
	};
	
	ExceptionPriceCheck.prototype.searchExceptionPrice = function(){
		var search_url = BasePath + "/exception_price_check/query_exception_price";
		var validate = $("#searchForm").form('validate');
		if(validate){
			var fromObjStr = convertArray($("#searchForm").serializeArray());
			// 2.加载明细 注意请求方式 restful风格 get
			$("#dataGridDiv").datagrid('options').queryParams = eval("("
					+ fromObjStr + ")");
			$("#dataGridDiv").datagrid('options').url = search_url;
			$("#dataGridDiv").datagrid('load');
		}
	};
	
	ExceptionPriceCheck.prototype.updateExceptionPrice = function(){
		
		var url = BasePath + '/exception_price_check/update_exception_price';
		
		var checkedRowsTemp = $("#dataGridDiv").datagrid('getChecked');
		if(checkedRowsTemp.length < 1){
			alert('请选择更新的行');
			return;
		}
		
		var data = '';
		for(var i = 0;i < checkedRowsTemp.length;i++){
			var row = checkedRowsTemp[i];
			if(i == checkedRowsTemp.length-1){
				data = data + JSON.stringify(row);
			}else{
				data = data + JSON.stringify(row)+'&';
			}
		}
		
		var changedRows = {
			inserted : data
		};
		
		ajaxRequestAsync(url, changedRows, function(result){
			if(result.success){
				showSuc("后台正在执行，稍后查看结果");
				$this.searchExceptionPrice();
			}else{
				showError("更新失败");
			}
		});
	};
	
};

$(function() {
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#searchForm").find("#createTimeStart").val(firstDay.format("yyyy-MM-dd"));
	$("#searchForm").find("#createTimeEnd").val(lastDay.format("yyyy-MM-dd"));
	var exceptionPriceCheck = new ExceptionPriceCheck();
	exceptionPriceCheck.initCombobox();
	
	$('#mainTab').tabs('add', {
		title : 'FAS价格异常检查',
		selected : true,
		closable : false,
		href : BasePath + "/exception_price_check/fas_bill_check",
		onLoad : function(panel) {
			exceptionPriceCheck.initCombobox();
			
			$("#billSearchForm").find("#createTimeStart_").val(firstDay.format("yyyy-MM-dd"));
			$("#billSearchForm").find("#createTimeEnd_").val(lastDay.format("yyyy-MM-dd"));
			
			/**
			 * btn-search-fas
			 * btn-remove-fas
			 * btn-add-fas
			 */
			$("#btn-search-fas").on('click',function(){
				var ret = $("#billSearchForm").form('validate');
				if(ret == false){
					return;
				}
				var search_url = BasePath + "/exception_price_check/query_exceptionPrice_local";
				var fromObjStr = convertArray($("#billSearchForm").serializeArray());
				
				// 2.加载明细 注意请求方式 restful风格 get
				$("#billDataGridDiv").datagrid('options').queryParams = eval("("
						+ fromObjStr + ")");
				$("#billDataGridDiv").datagrid('options').url = search_url;
				$("#billDataGridDiv").datagrid('load');
			});
			
			$("#btn-remove-fas").on('click',function(){
				$("#billSearchForm").form("clear");
				$("#billSearchForm").find("input").val("");
				$("#billSearchForm").find("textarea").val("");
			});
			
			$("#btn-update-fas").on('click',function(){
				var ret = $("#billSearchForm").form('validate');
				if(ret == false){
					return;
				}
				var comNo = $('#companyNo_').val();
				var brandNo = $('#brandNos_').val();
				if(comNo == ''){
					showWarn('请选择公司!');
					return;
				}
				if(brandNo == ''){
					showWarn('请选择品牌!');
					return;
				}
				var dataParam = $('#billSearchForm').serialize();
				//更新异常单据
				$.messager.confirm("确认","确定更新异常价格?", function(r) {
					if(r){
						//异常单据 更新
						var look_url = BasePath + '/exception_price_check/doBatchUpdate_exceptionPrice_local';
						var fromObj=$('#billSearchForm');
						$.messager.progress({
							title:'请稍后',
							msg:'正在处理中...'
						});
						fromObj.form('submit', {
							url: look_url,
							onSubmit: function(param){
							},
							success: function(){
								$.messager.progress('close');
								showSuc('执行成功');
						    },
						    error : function() {
						    	$.messager.progress('close');
								showError('更新异常价格失败,请联系管理员!');
							}
					   });
						/*
						ajaxRequestAsync(look_url,dataParam, function(result){
							if(result.success){
								$.messager.progress('close');
								showSuc('执行成功');
								$("#btn-search-fas").click();
							}else{
								$.messager.progress('close');
								showError('执行失败');
							}
						});*/
					}
				});
			});
			
			//add by Ning.ly 添加导出 2015-4-17
			$("#btn-export-fas").on('click',function(){
				fas_util.exportExcelBaseInfo(
			        "billDataGridDiv",
			        "/exception_price_check/do_fas_export",
			        "FAS价格异常检查导出"
			    );
			});
			//加载预警列表
			setTimeout(loadWarnMessageList(),3000);
		}
	});
	
	function loadWarnMessageList(){
		var warnPostUrl = $("#warnPostUrl").val();
		var checkType = $("#checkType").val();
		var dataGridId;
		if(warnPostUrl != null && warnPostUrl != ''){
			warnPostUrl = warnPostUrl.replaceAll(":", "&");
			if (0 == checkType) {
				dataGridId = "dataGridDiv";
				
			} else if (1 == checkType) {
				dataGridId = "billDataGridDiv";
			}
			if($("#"+dataGridId).length>0){
				$('#mainTab').tabs('select', parseInt(checkType));
				var tempDataGrid = $("#"+dataGridId);
			    $("#"+dataGridId).datagrid('options').url = BasePath + warnPostUrl;
			    $("#"+dataGridId).datagrid('load');
			}else{
				setTimeout("loadWarnMessageList()", 3000);
			}
		}
	}
	
	//add by Ning.ly 添加导出 2015-4-17
	$("#btn-export-gms").on('click',function(){
		fas_util.exportExcelBaseInfo(
	         "dataGridDiv",
	         "/exception_price_check/do_gms_export",
	         "GMS价格异常检查导出"
	    );
	});
	
	$("#btn-check-gms").on('click',function(){
		exceptionPriceCheck.startLookingExceptionPrice();
	});
	
	$("#btn-remove-gms").on('click',function(){
		$("#searchForm").form("clear");
		$("#searchForm").find("input").val("");
	});
	
	$("#btn-update-gms").on('click',function(){
		exceptionPriceCheck.updateExceptionPrice();
	});
	
	$("#btn-search-gms").on('click',function(){
		exceptionPriceCheck.searchExceptionPrice();
	});
	setTimeout(function(){
		initDate();
	},500);
});

function initDate(){
	var currDate = new Date();
	var year = currDate.getFullYear();
	var month = currDate.getMonth() + 1;
	var day = currDate.getDate();
	var startYear = year;
	var endYear = year;
	var startMonth = month-1;
	var endMonth = month;
	if(day <= 10){
		if(month == 1){
			startYear = year - 1;
			endYear = year - 1;
			startMonth = 11;
			endMonth = 12;
		}else if(month == 2){
			startYear = year - 1;
			endYear = year;
			startMonth = 12;
			endMonth = 1;
		}else{
			startMonth = month - 2;
			endMonth = month - 1;
		}
	}else{
		if(month == 1){
			startYear = year - 1;
			endYear = year;
			startMonth = 12;
			endMonth = 1;
		}
	}
	if(startMonth < 10){
		startMonth = '0' + startMonth;
	}
	if(endMonth < 10){
		endMonth = '0' + endMonth;
	}
	$('#sendDateStart').val(startYear +'-'+ startMonth +'-'+ 26);
	$('#sendDateEnd').val(endYear +'-'+ endMonth +'-'+ 25);
	
	$('#mainTab').tabs('hideHeader');//隐藏GMS价格检查异常
}

var changeColor = function(value,row,index){
	if(value != '' && value != 0){
		return 'color:red;';
	}
}
