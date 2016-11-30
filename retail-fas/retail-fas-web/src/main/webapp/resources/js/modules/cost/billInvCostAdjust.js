///////////////////库存成本调整单///////////////////////
billInvCostAdjust = {};

function BillInvCostAdjustDialog() { 
	var $this = this;
	this.toAdd = function() {
		bill.toAddBill();
		returnTab('mainTab', '单据明细');
	}
}

function BillInvCostAdjustEditor() {
	var $this = this;
	
}

//1.结算单单据对象
function BillInvCostAdjustBill() {
	var $this = this;
	// 点击新增按钮
	this.toAddBill = function() {	
		$this.super.toAddBill.call(this);
		// 设置默认的单据状态
		$("#status").val("0");
		// 清空销售单查询页签中的下拉框值
		$("#showType").combobox("clear");
	};
	// 新增数据
	this.addBill = function() {
	    if(!$("#" + $this.options.dataFormId).form('validate')) {
	        return;
	    }
	};
	// 生成结算单
	this.generateBill = function(url, shopBalanceObj) {
		$.messager.progress({
			title:'请稍后',
			msg:'正在处理中...'
		}); 
		$("#mainDataForm").form('submit', {
	        url : url,
	        dataType : 'json',
	        onSubmit : function(params) {
	        	var diffEffectRow = getChangeTableDataCommon("balanceDiffDataGrid");
	    		var deductEffectRow = getChangeTableDataCommon("balanceDeductDataGrid");
	    		var brandEffectRow = getChangeTableDataCommon("balanceBrandDataGrid");
	    		params.diffInserted = diffEffectRow.inserted;
				params.dudectInserted = deductEffectRow.inserted;
				params.brandInserted = brandEffectRow.inserted;
	        },
	        success : function(data) {
	        	var obj = eval("("+data+")");	
	        	$.messager.progress('close');
	        	return $this.copyOrGenerateBillFn(obj);
	        },
	        error : function() {
	        	$.messager.progress('close');
	            showError('新增失败,请联系管理员!');
	        }
	    });
		return true;
	};
	// 批量新增
	this.insertRows = function() {
		ygDialog({
			title : '批量新增',
			target : $('#myFormPanel'),
			width : 330,
			height : 350,
			buttons : [ {
				text : '确定',
				iconCls : 'icon-save',
				handler : function(dialog) {
					bill.saveBill();
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
					$('#dataForm').form('clear');
				}
			} ]
		});
	};
			
	// 新增数据
	this.saveBill = function() {
		var fromObj = $('#mainDataForm');
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		$.messager.progress({
			title:'请稍后',
			msg:'正在处理中...'
		});
		$("#mainDataForm").form('submit', {
	        url : BasePath + $this.modulePath +"/batchAdd",
	        dataType : 'json',
	        onSubmit : function(params) {
//	        	var diffEffectRow = getChangeTableDataCommon("balanceDiffDataGrid");
//	    		var deductEffectRow = getChangeTableDataCommon("balanceDeductDataGrid");
//	    		params.diffInserted = diffEffectRow.inserted;
//				params.dudectInserted = deductEffectRow.inserted;
	        },
	        success : function(data) {
	        	var obj = eval("("+data+")"); 
	        	$.messager.progress('close');
	        	return $this.generateBillBatchAdd(obj);
	        },
	        error : function() {
	        	$.messager.progress('close');
	            showError('新增失败,请联系管理员!');
	        }
	    });
		return true;
	};
	
	this.doSaveRows = function() {
		var fromObj = $('#dataForm');
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		var companyNo = $('#companyNo_adds').val();
		var organNo = $('#organNo_adds').val();
		var mallNo = $('#mallNo_adds').val();
		var bsgroupsNo = $('#bsgroupsNo_adds').val();
		var payType = $('#payType').val();
		var month = $('#month').val();
		fromObj.form('submit', {				
			url : BasePath + '/mall_shopbalance/save',
			onSubmit : function(param) {
				param.companyNo = companyNo;
				param.organNo = organNo;
				param.mallNo = mallNo;
				param.bsgroupsNo=bsgroupsNo;
				param.payType=payType;
				param.month=month;
			},
			success : function(data) {
				if (data > 0) {
					showInfo('批量操作成功！');
					dialog.search();
//							billBacksectionSplit.loadBacksectionSplitDtl(data.backsectionNo);
					$('#myFormPanel').dialog('close');
				} else {
					showWarn('保存失败，没有对应的业务数据');
				}
			}
		});
	};
			
	// 修改单据
	this.updateBill = function() {		
		if($('#difffDiv').length >0){
			var editTr = $('#difffDiv').find("tr[class*=datagrid-row-editing]");
			if(editTr.length > 0){
    			var editRowIndex = editTr.attr("datagrid-row-index");
    			var edDiffAmount = $('#balanceDiffDataGrid').datagrid('getEditor',{index:editRowIndex,field:'diffAmount'});
    			var edMallNumber = $('#balanceDiffDataGrid').datagrid('getEditor',{index:editRowIndex,field:'mallNumber'});
    			//$(edDiffAmount.target).unbind();
    			//$(edMallNumber.target).unbind();
			}
		}
		if(!$this.checkUpdate($this.options)) {
			return;
		}		
		var balanceDeductData = {}, balanceDiffData = {};
		if($.fas.endEditing("balanceDeductDataGrid")) {
			balanceDeductData = getChangeTableDataCommon("balanceDeductDataGrid");
		} else {
			return;
		}
		if($.fas.endEditing("balanceDiffDataGrid")) {
			balanceDiffData = getChangeTableDataCommon("balanceDiffDataGrid");
		} else {
			return;
		}
		if($.fas.endEditing("balanceBrandDataGrid")) {
			balanceBrandData = getChangeTableDataCommon("balanceBrandDataGrid");
		} else {
			return;
		}
		$.messager.progress({
			title:'请稍后',
			msg:'正在处理中...'
		});
		$("#" + $this.options.dataFormId).form('submit', {
			url : BasePath + $this.modulePath + $this.options.updateUrl,
			onSubmit : function(param) {
				param.deduceDataInserted = balanceDeductData.inserted;
				param.deduceDataUpdated = balanceDeductData.updated;
				param.deduceDataDeleted = balanceDeductData.deleted;
				
				param.balanceDiffDataInserted = balanceDiffData.inserted;
				param.balanceDiffDataUpdated = balanceDiffData.updated;
				param.balanceDiffDataDeleted = balanceDiffData.deleted;
				
				param.brandDataInserted = balanceBrandData.inserted;
				param.brandDataUpdated = balanceBrandData.updated;
				param.brandDataDeleted = balanceBrandData.deleted;
			},
			success : function(result) {
				$.messager.progress('close');
				$this.updateBillFn(result, $this.options);
			}
		});	
		return true;
	};
	// 检查是否可进行保存操作
	this.checkUpdate = function(options) {
		var flag = $this.super.checkUpdate.call(this, options);
		if(!flag) {
			return false;
		}
		flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("已经生成下期结算单不可修改");
			return false;
		}
		return true;
	};
	// 修改操作的回调方法
	this.updateBillFn = function(result, options) {
		if(result) {
			var resultData = JSON.parse(result);
			if(resultData && resultData.errorType == '0' && resultData.errorInfo) {
				$("#showWarnMsgTr").html("<th><font color='red'>温馨提示：</font></th><td colspan='7'>结算单数据不准确，请将鼠标放置图标处查看消息详情<a href='javascript:void();'><span class='l-btn-text icon-tips l-btn-icon-left' id='billMsgTip'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				$.fas.tooltip({
					id : 'billMsgTip',
					content : resultData.errorInfo
				});
			} else {
				$("#showWarnMsgTr").html("");
			}
			
			if(resultData && resultData.showRateExpenseRemind) { 
				$("#showRateExpenseRemindTr").html("<th><font color='red'>保底提示：</font></th><td colspan='7'><a href='javascript:void();'><span class='l-btn-text icon-tips l-btn-icon-left' id='showRateExpenseRemindip'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				$.fas.tooltip({
					id : 'showRateExpenseRemindip',
					content : resultData.showRateExpenseRemind
				});
			} else {
				$("#showRateExpenseRemindTr").html("");
			}
			showSuc('修改成功');
			$("#" + options.dataFormId).form('load', resultData);
			//单据类型、设置店铺、公司、结算月只可读
			$this.setReadOnly({
				combobox : ['balanceType'],
				combogrid : ['companyName'],
				dgSelector : ["shortName"],
				date : ['month']
			});
//			// 重置是否点击tab页的标志
			for(var tab in hasClickTabs) {
				hasClickTabs[tab] = false;
			}
			var tab = $('#dtlTab').tabs('getSelected');
			var index = $('#dtlTab').tabs('getTabIndex',tab);
			$("#dtlTab").tabs("select", index);
			if(index == 0) {
				// 处理"销售单查询"页签，查询方式是按大类显示的开票金额的数据
				if($("#showType").combobox("getValue") == "1") {
					var shopNo = $('#shopNo').val();
					var startDate = $('#balanceStartDate').val();
					var endDate = $('#balanceEndDate').val();
					var balanceNo = $('#balanceNo').val();	
					var params = "?shopNo="+shopNo+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo;	
					var url = '/bill_shop_balance_cate_sum/list.json' + params;
					$.fas.search({
						dataGridId : "categoryDataGrid",
						searchUrl : url,
						hasSearchForm : false,
						loadType : "load"
					});
				}
			} else {
				loadTabData(index);
			}
			
			// 设置店铺名称：因为店铺可多选，需手动设置店铺名称的值
			$("#shortName").shop("setValue", resultData.shortName);
			// 判断商场开票金额和系统开票金额是否等于差异金额
			$this.changeDiffAmount();
			
//			$("#mallNumberAmount").numberbox("clear");
//			$("#mallBillingAmount").numberbox("clear");
			
			// 刷新列表页面的数据
			$("#"+dialog.options.searchBtn).click();
		} else {
			showError('修改失败');
		}
	};
	// 明细页面点击删除按钮
	this.delBill = function() {
		var id = $("#id").val();
	    if(!id || $.trim(id) == ''){
	        showWarn("请选择要删除的数据!");
	        return;
	    }
	    var status = $("#status").val();
		if(status && status != "1"){
			showWarn("单据已审核确认不能删除！" );
			return;
		 }
        
		var createUser = $("#createUser").val();
		var companyNo = $("#companyNo").val();
		if( $.trim(companyNo.substring(0,1)) == 'K'){
			if(createUser && createUser != currentUser.username){
				showWarn("该单据非本人创建，不可删除！" );
		    	return;
			}
		}
		
		$.messager.confirm("确认","你确定要删除该单据?",function(r) {
			if (r) {
				var url = BasePath + '/mall_shopbalance/deleteShopBalance';
				var balanceNo = $("#balanceNo").val();
				var idList = id+','+balanceNo;
				var params = {idList : idList};
				ajaxRequestAsync(url,params,function(count){
					if(count) {
						showSuc('删除成功');
						$this.toAddBill();
						$("#" + $this.options.searchBtn).click();
					} else {
						showError('删除失败');
					}
				});
			}
		});
	}
	// 列表页面点击删除按钮
	this.batchDelBill = function() {
		var checkedRows = $('#shopBalanceDataGrid').datagrid('getChecked');
	    if(checkedRows.length < 1){
	        showWarn("请选择要删除的记录!");
	        return;
	    }
		for(var i =0; i < checkedRows.length; i++){
			var item = checkedRows[i];
			if(item.status != "1"){
				showWarn("单据："+item.balanceNo+"，已审核确认不可删除！" );
				return false;
			 }
//			alert("item.createUser: "+item.createUser);
//		    alert("currentUser.username: "+currentUser.username);
			if((item.companyNo).substring(0,1) == 'K'){
			if(item.createUser && item.createUser != currentUser.username){
				showWarn("该单据非本人创建，不可删除！" );
		    	return;
			}
			}
		}
		$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
			if (r) {
				var url = BasePath + '/mall_shopbalance/deleteShopBalance';
				var idList = "";
				$.each(checkedRows, function(index, row) {					
					idList+=row.id+','+row.balanceNo+";";
				});
				var params = {idList : idList.substring(0, idList.length-1)}; //{id:idList};//
				ajaxRequestAsync(url,params,function(count){
					if(count) {
						showSuc('删除成功');
						$("#" + $this.options.searchBtn).click();
					} else {
						showError('删除失败');
					}
				});
			}
		});
	};
	// 检查是否可进行确认操作
	this.confirm = function(type, status) {
		var $this = this;
		var message = status == '1' ? '反审核' : '审核';
		// 新增页面审核操作
		if(type == '0') {
			var id = $("#id").val();
			if(!id || $.trim(id) == '') {
				showWarn("请选择您要"+message+"的单据！");
				return;
			}
			var billStatus = $("#status").val();
			if(status == '1' && billStatus == '1') {
				showWarn("制单状态的单据不允许进行反审核操作！");
				return;
			}
			if(status == '1' && billStatus == '4') {
				showWarn("已开票申请状态的单据不允许进行反审核操作！");
				return;
			}
			if(status && billStatus && status == billStatus) {
				showWarn("单据已"+message+"，请勿重复操作！");
				return;
			}
			if(status == '2' && billStatus != '1') {
				showWarn("只允许操作制单状态的单据！");
				return;
			}
			var mallNumberAmount = $("#mallNumberAmount").numberbox("getValue");
			var mallBillingAmount = $("#mallBillingAmount").numberbox("getValue");
			if(!mallNumberAmount || !mallBillingAmount) {
				showWarn("请填写商场报数和商场开票金额后再操作！");
				return;
			}
			
			var balanceNo = $("#balanceNo").val();
			// 校验数据是否已平
			var checkFlag = $.fas.ajaxRequestAsync(BasePath+"/mall_shopbalance/validateDataBalance", {balanceNo:balanceNo}, function(result){
				if(result && result.errorMsg) {	
					showWarn("<font color='red'>温馨提示：</font>结算单数据不准确："+result.errorMsg);
					return false;
				}
				return true;
			});
			if(!checkFlag){
				return;
			} 
			bill.saveBill();
			$.messager.confirm("确认", "你确定要"+message+"该条单据?", function(r) {
				if(r) {
					$('#mainDataForm').form('submit', {
						url : BasePath + '/mall_shopbalance/confirm',
						onSubmit:function(param) {
							$("#status").val(status);
							param.auditor = currentUser.loginName;
							param.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
						},
						success:function(data) {
							if(data) {
								showSuc('操作成功');
								$("#" + $this.options.dataFormId).form('load', JSON.parse(data));
								// 设置表单样式
								if(status == '1') {
									//单据类型、设置店铺、公司、结算月只可读
									$this.clearReadOnly({
										input : ['mallNumberAmount', 'mallBillingAmount']
									});
								} else if(status == '2') {
									if($this.options.dataFormFields) {
										$this.setReadOnly($this.options.dataFormFields);
									}
								}
								
								$("#dtlTab").tabs("select", 0);
								$this.setBillBottom(JSON.parse(data));
								// 重置是否点击tab页的标志
								for(var tab in hasClickTabs) {
									hasClickTabs[tab] = false;
								}
								// 设置店铺名称：因为店铺可多选，需手动设置店铺名称的值
								$("#shortName").shop("setValue", JSON.parse(data).shortName);
								// 刷新列表页面的数据
								$("#"+$this.options.searchBtn).click();
							} else {
								showError('操作失败');
							}
						}
					});
				}
			});
		} else { // 列表页面审核操作
			var checkedRows = $("#" + $this.options.listDataGridId).datagrid("getChecked");
			if(checkedRows.length < 1) {
				showWarn("请选择您要操作的单据！");
				return;
			}
			if(status == '1') {
				var confirmFlag = true;
				$.each(checkedRows, function(index, item){
					if(item.status != '2') {
						showWarn("只允许操作审核状态的单据！");
						confirmFlag = false;
						return false;
					}
				});
				if(!confirmFlag) {
					return;
				}
			}
			if(status == '2') {
				var confirmFlag = true;
				$.each(checkedRows, function(index, item){
					if(item.status != '1') {
						showWarn("只允许操作未审核状态的单据！");
						confirmFlag = false;
						return false;
					}
					if(!item.mallNumberAmount || !item.mallBillingAmount) {
						showWarn("请填写商场报数和商场开票金额后再操作！");
						confirmFlag = false;
						return false;
					}
					// 校验数据是否已平
					var checkFlag = $.fas.ajaxRequestAsync(BasePath+"/mall_shopbalance/validateDataBalance", {balanceNo:item.balanceNo}, function(result){
						if(result && result.errorMsg) {	
							showWarn("您操作的数据中，结算单数据不准确，请重新选择！");
							return false;
						}
						return true;
				    });
					if(!checkFlag) {
						confirmFlag = false;
						return false;
					}
				});
				if(!confirmFlag) {
					return;
				}
			}
			$.messager.confirm("确认", "你确定要"+message+"该条单据?", function(r) {
				if(r) {
					$.each(checkedRows, function(index, row) {
						row.status = status;
						row.auditor = currentUser.loginName;
						row.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					});
					var data = {
						confirmList : JSON.stringify(checkedRows),
					};
					ajaxRequestAsync(BasePath + '/mall_shopbalance/batchConfirm', data, function(result){
						if(result){
							showSuc('操作成功');
							$("#" + $this.options.searchBtn).click();
						} else {
							showError('操作失败');
						}
					});
				}
			});
		}
	};
	// 双击明细行
	this.loadDetail = function(rowIndex, rowData) {
		// 重置是否点击tab页的标志
		for(var tab in hasClickTabs) {
			hasClickTabs[tab] = false;
		}
		$this.super.loadDetail.call(this, rowIndex, rowData);
		// 设置店铺名称：因为店铺可多选，需手动设置店铺名称的值
		$("#shortName").shop("setValue", rowData.shortName);
		$("#showType").combobox("select", "1");
		var url = "/bill_shop_balance_cate_sum/list.json?balanceNo="+rowData.balanceNo+"&shopNo="+rowData.shopNo
				+"&startDate="+rowData.balanceStartDate+"&endDate="+rowData.balanceEndDate;
		$.fas.search({
			dataGridId : "categoryDataGrid",
			searchUrl : url,
			hasSearchForm : false,
			loadType : "load"
		});
		// 检查单据状态是否已审核
		if(rowData.status == '1') {
			//单据类型、设置店铺、公司、结算月只可读
			$this.clearReadOnly({
				input : ['mallNumberAmount', 'mallBillingAmount']
			});
		} else if(rowData.status != '1') {
			if($this.options.dataFormFields) {
				$this.setReadOnly($this.options.dataFormFields);
			}
		}
		// 校验数据是否已平
		 ajaxRequest(BasePath+"/mall_shopbalance/validateDataBalance", {balanceNo:rowData.balanceNo}, function(result){
			if(result && result.errorMsg) {
				$("#showWarnMsgTr").html("<th><font color='red'>温馨提示：</font></th><td colspan='7'>结算单数据不准确，请将鼠标放置图标处查看消息详情<a href='javascript:void();'><span class='l-btn-text icon-tips l-btn-icon-left' id='billMsgTip'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				$.fas.tooltip({
					id : 'billMsgTip',
					content : result.errorMsg
				});
			} else {
				$("#showWarnMsgTr").html("");
			}
			if(result && result.showRateExpenseRemind) { 
				$("#showRateExpenseRemindTr").html("<th><font color='red'>保底提示：</font></th><td colspan='7'><a href='javascript:void();'><span class='l-btn-text icon-tips l-btn-icon-left' id='showRateExpenseRemindip'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				$.fas.tooltip({
					id : 'showRateExpenseRemindip',
					content : result.showRateExpenseRemind
				});
			} else {
				$("#showRateExpenseRemindTr").html("");
			}
		 });
	};
	
	// 导出明细数据
	
	/*
	 * 批量导出明细
	 */
	this.batchExportDetailExcel = function() {
		var shopBalanceDataGrid = $("#shopBalanceDataGrid").datagrid('getSelections');
		if(shopBalanceDataGrid.length==0){
			alert("请选择您要导出的数据！");
		}else{
			var headerFormData = $.fas.serializableForm("mainDataForm", 4);
			var saleDtlExportColumns = [];
			var promotionsDataGrid = $("#promotionsDataGrid").datagrid('options').columns;
			var dtlDataGridName = "按活动显示";
			saleDtlExportColumns = $.grep(promotionsDataGrid[0], function(o, i) {
				if ($(o).attr("notexport") == true) {
					return true;
				}
				return false;
			}, true);
			var diffExportColumns = [{field:'diffTypeName',title : '差异类型'},
		                             {field:'diffDate',title : '差异结算日'},
		                             {field:'proStartDate',title : '活动起始日'},
		                             {field:'proEndDate',title : '活动终止日'},
		                             {field:'proNo',title : '活动编码'},
		                             {field:'proName',title : '活动名称'},
		                             {field:'discountN',title : '扣率%'},
		                             {field:'deductDiffAmount',title : '系统扣费',exportType:'number'},
		                             {field:'reportDiffAmount',title : '报数差异',exportType:'number'},
		                             {field:'mallNumber',title : '商场报数(A)',exportType:'number'},
		                             {field:'salesAmount',title : '系统收入(B)',exportType:'number'},
		                             {field:'salesDiffamount',title : '差异金额(A-B)',exportType:'number'},
		                             {field:'diffAmount',title : '扣费差异',exportType:'number'},
		                             {field:'diffReason',title : '差异原因'},
		                             {field:'preDiffBalance',title : '上期差异余额',exportType:'number'},
		                             {field:'diffBalance',title : '本期差异余额',exportType:'number'},
		                             {field:'diffBalanceSum',title : '累计差异余额',exportType:'number'},
		                             {field:'diffBackAmount',title : '差异回款',exportType:'number'},
		                             {field:'reason',title : '原因'},
		                             {field:'changeAmount',title : '调整金额',exportType:'number'},
		                             {field:'changeReason',title : '调整原因'},
		                             {field:'changeDate',title : '调整日期'},
		                             {field:'status',title : '状态'},
		                             {field:'remark',title : '备注'}
		    ];
		    var deductExportColumns = [{field:'month',title : '结算月'},
		                               {field:'deductionCode',title : '扣费编码'},
		                               {field:'deductionName',title : '扣费类别'},
		                               {field:'costType',title : '费用性质'},
		                               {field:'deductAmount',title : '扣费金额',exportType:'number'},
		                               {field:'actualAmount',title : '实际扣费金额',exportType:'number'},
		                               {field:'diffAmount',title : '扣费差异金额',exportType:'number'},
		                               {field:'diffReason',title : '差异原因'},
		                               {field:'deductDate',title : '扣费日期'},
		                               {field:'processStatus',title : '处理状态'}
		    ];
		    $("#exportExcelForm").remove();
		    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	    	$('#exportExcelForm').form('submit', {
	            url : BasePath + "/mall_shopbalance/do_dtl_batchexport",
	            onSubmit : function(params) {
	            	params.shopBalanceDataGrid=JSON.stringify(shopBalanceDataGrid);
	            	params.headerFormData = JSON.stringify(headerFormData);
	            	params.dtlShowType = '3';
	            	params.saleDtlExportColumns = JSON.stringify(saleDtlExportColumns);
	            	params.diffExportColumns = JSON.stringify(diffExportColumns);
	            	params.diffFileName = "结算差异";
	            	params.dtlDataGridName = dtlDataGridName;
	            	params.deductExportColumns = JSON.stringify(deductExportColumns);
	            	params.deductFileName = "票前费用";
	            	params.fileName = "结算单明细信息导出";
	            },
	            success : function() {

	            }
	        });
		}
	}
	
};


var dialog = null, editor = null, bill = null;
$(function() {
	
//	returnTab('dtlTab', '销售单查询');
	
	$.fas.extend(BillInvCostAdjustDialog, FasDialogController);
	$.fas.extend(BillInvCostAdjustEditor, FasEditorController);
	$.fas.extend(BillInvCostAdjustBill, FasBillController);
	
	adjustDialog = new BillInvCostAdjustDialog();
	adjustDialog.init("/bill_inv_cost_adjust", {
		dataGridId : "mainDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "成本调整单导出",
		exportUrl : "/do_fas_export",
		statusType : "-1|0|5"
	});
	
	editor = new BillInvCostAdjustEditor();
	editor.init("/bill_inv_cost_adjust", {
		dataGridDivId : 'dataGridDiv',
		dataGridId : 'dtlDataGrid',
		saveUrl : "/bill_inv_cost_adjust/save",
		searchBtn : "btn-search",
		afterAdd : function(rowIndex) {
//			var edZoneNo = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'zoneNo'});
//			$(edZoneNo.target).on("blur",function(){
//				var edZoneCode = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'zoneCode'});
//				$(edZoneCode.target).val($(edZoneNo.target).val());
//			});
		},
		afterUpdate : function(rowIndex, rowData) {
//			var edZoneNo = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'zoneNo'});
//			var edZoneName = $('#specialZoneInfoDataGrid').datagrid('getEditor', {index:rowIndex,field:'name'});
//			$(edZoneNo.target).attr("readonly", true).addClass("disabled");
//			$(edZoneName.target).attr("readonly", true).addClass("disabled");
		},
		keyboard : true
	});
	
	// 单据头对象
	bill = new BillInvCostAdjustBill();
	bill.init("/bill_inv_cost_adjust", {
		dataFormId : 'mainDataForm',
		addUrl : "/saveShopBalanceBill",
		updateUrl : "/updateBill",
		delUrl : "/delBill"
	});
	
	returnTab('mainTab', '单据查询');
	billInvCostAdjust.initMonthCombox("month");
	billInvCostAdjust.initYearCombox("year");
});

//月份数据
billInvCostAdjust.monthData = [
	{"lable":"1","value":"1","text":"1"},{"lable":"2","value":"2","text":"2"},
	{"lable":"3","value":"3","text":"3"},{"lable":"4","value":"4","text":"4"},
	{"lable":"5","value":"5","text":"5"},{"lable":"6","value":"6","text":"6"},
	{"lable":"7","value":"7","text":"7"},{"lable":"8","value":"8","text":"8"},
	{"lable":"9","value":"9","text":"9"},{"lable":"10","value":"10","text":"10"},
	{"lable":"11","value":"11","text":"11"},{"lable":"12","value":"12","text":"12"}
];

//年份数据
billInvCostAdjust.yearData = function() {
	var yearDatas = [];
	var year = new Date().getFullYear();
	for(var i = 5; i >= 1; i--) {
		yearDatas.push({lable:(year-i),value : (year-i), text : (year-i)});
	}
	for(var j = 0; j <= 5; j++) {
		yearDatas.push({lable:(year+j),value : (year+j), text : (year+j)});
	}
	return yearDatas;
};

//初始化年份下拉框
billInvCostAdjust.initYearCombox = function(id){
	var data = billInvCostAdjust.yearData();
	$("#" + id).combobox({
		data :data,
		valueField : "lable",
		textField : "text",
		panelHeight : "auto",
		width : 130
	});
};

//初始化月份下拉框
billInvCostAdjust.initMonthCombox = function(id){
	var data = billInvCostAdjust.monthData;
	$("#" + id).combobox({
		data : data,
		valueField : "lable",
		textField : "text",
		panelHeight : "auto",
		width : 130
	});
};
