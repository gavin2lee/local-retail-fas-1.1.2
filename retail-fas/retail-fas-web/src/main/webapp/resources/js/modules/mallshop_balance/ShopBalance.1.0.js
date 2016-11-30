// 是否点击过tab页的标志
var hasClickTabs = {
	diffTab : false,
	deductTab : false,
	invoiceTab : false,
	balanceAccountTab : false,
	brandDeductTab : false,
	deductafterTab : false
};

//1.结算单单据对象
function ShopBalanceBill() {
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	var  organTypeNo =  currentUser.organTypeNo;
	
	var $this = this;
	// 点击新增按钮
	this.toAddBill = function() {	
		$this.super.toAddBill.call(this);
		// 设置默认的单据状态
		$("#status").val("1");
		// 清空销售单查询页签中的下拉框值
		$("#showType").combobox("clear");
		
		$("#mallNumberAmount").numberbox("clear");
		$("#mallBillingAmount").numberbox("clear");
		
		// 重置是否点击tab页的标志
		for(var tab in hasClickTabs) {
			hasClickTabs[tab] = false;
		}
		// 清空"销售单查询"页签的页脚
		$("#categoryDataGrid").datagrid('reloadFooter',[]);
		$("#balanceDtlDataGrid").datagrid('reloadFooter',[]);
		$("#promotionsDataGrid").datagrid('reloadFooter',[]);
		$("#paymentMethodDataGrid").datagrid('reloadFooter',[]);
		$("#billingCodeDataGrid").datagrid('reloadFooter',[]);
		$("#balanceDiffDataGrid").datagrid('reloadFooter',[]);
		$("#balanceDeductDataGrid").datagrid('reloadFooter',[]);
		
		$("#showWarnMsgTr").html("");
		$("#showRateExpenseRemindTr").html("");
	};
	// 新增数据
	this.addBill = function() {
	    if(!$("#" + $this.options.dataFormId).form('validate')) {
	        return;
	    }
		var companyNo = $('#companyNo').val();
		var companyName = $('#companyName').combogrid("getValue");
		var shortName = $('#shortName').shop("getValue");
		var shopNo = $('#shopNo').val();
		var month = $('#month').val();
		var balanceType = $('#balanceType').combobox('getValue'); 
		// 生成结算单的url
		var generateBillUrl = BasePath + $this.modulePath + $this.options.addUrl;
		// 生成结算单传递的参数
		var generateBillParams = companyNo+";"+companyName+";"+shopNo+";"+shortName+";"+month+";"+balanceType+";";
		// 复制结算单的url
		var copyBillUrl = BasePath + $this.modulePath + "/copyBill";
		// 校验及获取预估结算单
		var validateParams = {companyNo:companyNo, shopNo:shopNo,month:month,balanceType:balanceType};
		var flag = $.fas.ajaxRequestAsync(BasePath+"/mall_shopbalance/validateAndGetEstimateBill", validateParams, function(validateData){
            // 如果存在validateData
        	if(validateData.flag == '1') { //表示校验不通过
        		showError(validateData.msg);
        		return false;
        	} else if(validateData.flag == '2') {// 校验通过，且存在预估结算单（controller已判断是生成正式结算单时才会查询是否有预估结算单）
        		ygDialog({
    		        title : '结算单生成方式',
    		        target : $('#confirmBillGeneratePanel'),
    		        width : 400,
    		        height : 100,
    		        enableCloseButton : false,
    		        buttons : [{
    		            text : '复制',
    		            iconCls : 'icon-copy',
    		            handler : function(dialog) {
    		            	dialog.close();
            				return $this.copyBill(copyBillUrl, {id:validateData.bill.id, balanceNo:validateData.bill.balanceNo});
    		            }
    		        }, {
    		            text : '重新生成',
    		            iconCls : 'icon-redo',
    		            handler : function(dialog) {
    		            	dialog.close();
    		            	return $this.generateBill(generateBillUrl, generateBillParams);
    		            }
    		        }]
    		    });
        	} else if(validateData.flag == '0') {
//        		if($this.generateBill(generateBillUrl, generateBillParams)){
//        			showInfo('正在处理中....请等待...');
//        		}
        		return $this.generateBill(generateBillUrl, generateBillParams);
        	}
		});
		return flag;
	};
	// 复制结算单
	this.copyBill = function(url, params) {
		var flag = $.fas.ajaxRequestAsync(url, params, function(data){
			return $this.copyOrGenerateBillFn(data);
	    });
		return flag;
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
	    		var deductAfterEffectRow = getChangeTableDataCommon("balanceDeductAfterDataGrid");
	    		params.diffInserted = diffEffectRow.inserted;
				params.dudectInserted = deductEffectRow.inserted;
				params.brandInserted = brandEffectRow.inserted;
				params.deductAfterInserted = deductAfterEffectRow.inserted;
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
	// 生成或复制结算单后的回调函数
	this.copyOrGenerateBillFn = function(data) {
		if(data && data.errorType == 0) {
			showSuc('操作成功');
			if(data.errorInfo) {
				$("#showWarnMsgTr").html("<th><font color='red'>温馨提示：</font></th><td colspan='7'>结算单数据不准确，请将鼠标放置图标处查看消息详情<a href='javascript:void();'><span class='l-btn-text icon-tips l-btn-icon-left' id='billMsgTip'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				$.fas.tooltip({
					id : 'billMsgTip',
					content : data.errorInfo
				});
			} else {
				$("#showWarnMsgTr").html("");
			}
			if(data.showRateExpenseRemind) {
				$("#showRateExpenseRemindTr").html("<th><font color='red'>保底提示：</font></th><td colspan='7'><a href='javascript:void();'><span class='l-btn-text icon-tips l-btn-icon-left' id='showRateExpenseRemindip'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				$.fas.tooltip({
					id : 'showRateExpenseRemindip',
					content : data.showRateExpenseRemind
				});
			} else {
				$("#showRateExpenseRemindTr").html("");
			}
			// 填充表单数据
			$('#mainDataForm').form('load', data);
			//单据类型、设置店铺、公司、结算月只可读
			$this.setReadOnly({
				combobox : ['balanceType'],
				combogrid : ['companyName', "shortName"],
				dgSelector : ["shortName"],
				date : ['month']
			});
			// 重置是否点击tab页的标志
			for(var tab in hasClickTabs) {
				hasClickTabs[tab] = false;
			}
//			$("#dtlTab").tabs("select", 0);
			
			var tab = $('#dtlTab').tabs('getSelected');
			var index = $('#dtlTab').tabs('getTabIndex',tab);
			$("#dtlTab").tabs("select", index);
			if(index == 0) {
				// 默认选中"销售单查询"页签，且查询方式是按大类显示
				$("#showType").combobox("select", "1");
			} else {
				loadTabData(index);
			}
			// 设置店铺名称：因为店铺可多选，需手动设置店铺名称的值
			$("#shortName").shop("setValue", data.shortName);
			// 判断商场开票金额和系统开票金额是否等于差异金额
			
//			$("#mallNumberAmount").numberbox("clear");
//			$("#mallBillingAmount").numberbox("clear");
			
			// 刷新列表页面的数据
			$("#btn-search1").click();
			return true;
		} else {
			if( data.errorInfo && data.errorType == 1) {
				$.messager.progress('close');
				showError(data.errorInfo);
			} else {
				$.messager.progress('close');
				showError("操作失败，请检查数据是否有效，结算期设置是否正确！");
			}
			return false;
		}
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
					bill.saveBills();
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
	this.saveBills = function() {
	    var fromObj = $('#dataForm');
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		$.messager.progress({
			title:'请稍后',
			msg:'正在处理中...'
		});
		$("#dataForm").form('submit', {
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
	
	// 批量生成结算单后的回调函数  \ftl\pages\mallshop_balance\shopBalance_BatchAdd_ResultList.ftl
	this.generateBillBatchAdd = function(result){
//		   showWarn("<font color='red'>温馨提示：</font>结算单数据不准确："+result);//.get("errorMsg")+result.get("shopNo"));
			var url = BasePath + '/mall_shopbalance/shopbalance_batchadd_resultlist';
			ygDialog({
				title : '批量生成门店结算单处理结果',
				href : url,
				width : 1000,
				height : 'auto',
				isFrame : true,
				modal : true,
				showMask : true,
				buttons: [
				   {
				    text: '取消',
				    handler: function(dialog) {
				    dialog.close();
				   }
				  }],
				  onLoad : function(win, content) {
				  content.loadData(result);
//				  $('#searchForm').form('load', data);
				  $("#btn-search1").click();
				 }
			});
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
		
		// 检查是否可进行保存操作
		this.checkUpdate = function(options) {
			var flag = $this.super.checkUpdate.call(this, options);
			if(!flag) {
				return false;
			}
			flag = checkHasNextBillBalance();
			if(!flag) {
				showWarn("请检查系统参数设置,结算单控制处理策略不可修改.");
				return false;
			}
			return true;
		};
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
		var balanceDeductData = {}, balanceDiffData = {},balanceBrandData={},balanceDeductAfterData = {},balanceCategoryData = {};
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
		if($.fas.endEditing("balanceDeductAfterDataGrid")) {
			balanceDeductAfterData = getChangeTableDataCommon("balanceDeductAfterDataGrid");
		} else {
			return;
		}
		if($.fas.endEditing("categoryDataGrid")) {
			balanceCategoryData = getChangeTableDataCommon("categoryDataGrid");
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
				
				param.deduceAfterDataInserted = balanceDeductAfterData.inserted;
				param.deduceAfterDataUpdated = balanceDeductAfterData.updated;
				param.deduceAfterDataDeleted = balanceDeductAfterData.deleted;
				
				param.balanceCategoryDataInserted = balanceCategoryData.inserted;
				param.balanceCategoryDataUpdated = balanceCategoryData.updated;
				param.balanceCategoryDataDeleted = balanceCategoryData.deleted;
			},
			success : function(result) {
				$.messager.progress('close');
				$this.updateBillFn(result, $this.options);
				$('#balanceDiffDataGrid').datagrid('acceptChanges');
				$('#balanceDeductDataGrid').datagrid('acceptChanges');
				$('#balanceDeductAfterDataGrid').datagrid('acceptChanges');
				$('#categoryDataGrid').datagrid('acceptChanges');
			}
		});	
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
				var id = $("#id").val();
				var shopNo = $("#shopNo").val();
				var balanceEndDate = $("#balanceEndDate").val();
				var month = $("#month").val();
				
				var idList = id+','+balanceNo+','+shopNo+','+month+','+balanceEndDate;
				var params = {idList : idList};
				ajaxRequestAsync(url,params,function(count){
					if(count=='9999'){
						showError('删除失败,已存在结算期开始时间大于当前选择的结束时间');	
					}else if(count !='9999') {
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
					idList+=row.id+','+row.balanceNo+","+row.shopNo+','+row.month+','+row.balanceEndDate+';';
				});
				var params = {idList : idList.substring(0, idList.length-1)}; //{id:idList};//
				ajaxRequestAsync(url,params,function(count){
					if(count == '9999'){
						showError('删除失败,已存在结算期开始时间大于当前选择的结束时间');	
					}else if(count !='9999') {
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
	this.saveConfirm = function(type, status) {
		var $this = this;
		var message = status == '1' ? '反确认' : '确认';
		// 新增页面审核操作
		if(type == '0') {
			var id = $("#id").val();
			if(!id || $.trim(id) == '') {
				showWarn("请选择您要"+message+"的单据！");
				return;
			}
			var billStatus = $("#status").val();
			if(status == '1'  && billStatus == '1') {
				showWarn("制单状态的单据不允许进行反确认操作！");
				return;
			}
			if(status == '1'  && billStatus != '5') {
				showWarn("只能操作业务确认状态的单据！");
				return;
			}
			if(status == '1' && billStatus == '4') {
				showWarn("已开票申请状态的单据不允许进行反确认操作！");
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
			if( status == '5' && billStatus != '1') {
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
//			if(!bill.saveBill()){
//				return;
//			} 
			$.messager.confirm("确认", "你确定要"+message+"该条单据?", function(r) {
				if(r) {
					$('#mainDataForm').form('submit', {
						url : BasePath + '/mall_shopbalance/confirm',
						onSubmit:function(param) {
							$("#status").val(status);
							param.updateUser = currentUser.loginName;
							param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
							if(status == '1') {
								param.updateUser = '';
								param.updateTime = '';
							}
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
					if(item.status != '5') {
						showWarn("只允许操作审核状态的单据！");
						confirmFlag = false;
						return false;
					}
					if(item.status == '1' && status == '1') {
						showWarn("制单状态的单据不允许进行反确认操作！");
						return;
					}
					if(item.status == '1' && status == '4') {
						showWarn("已开票申请状态的单据不允许进行反确认操作！");
						return;
					}
					if(item.status && billStatus && item.status == status) {
						showWarn("单据已"+message+"，请勿重复操作！");
						return;
					}
					if(item.status == '2' && status != '5') {
						showWarn("只允许操作制单状态的单据！");
						return;
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
			if(status == '5') {
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
						row.updateUser = currentUser.loginName;
						row.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
						if(status == '1') {
							row.updateUser = '';
							row.updateTime = '';
						}
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
	
	// 检查是否可进行确认操作  审核操作
	this.confirm = function(type, status) {
		var $this = this;
		var message = status == '2' ? '审核' : '反审核';
		// 新增页面审核操作
		if(type == '0') {
			var id = $("#id").val();
			if(!id || $.trim(id) == '') {
				showWarn("请选择您要"+message+"的单据！");
				return;
			}
			var billStatus = $("#status").val();
			
			var companyNo = $("#companyNo").val();
			var dtlValue = checkSystemDeduAmountEdit(companyNo,'msBalanceAudit');  //门店结算单审核流程
			if(dtlValue != null && dtlValue != '' && dtlValue == '1') {  //控制业务-财务审核流程
				if(status == '2' && billStatus != '5') {
					showWarn("只允许操作业务确认状态的单据！");
					return;
				}
			}

			if(status == '5') {
				if(dtlValue != null && dtlValue != '' && dtlValue == '1') {  //控制业务-财务审核流程
					  status ='5';
					}  else 
					  status ='1';	
			}
			
			if(status == '1' && billStatus == '1') {
				showWarn("制单状态的单据不允许进行反审核操作！");
				return;
			}
			if(status == '1' && billStatus == '4') {
				showWarn("已开票申请状态的单据不允许进行反审核操作！");
				return;
			}
//			if(status && billStatus && status == billStatus) {
//				showWarn("单据已"+message+"，请勿重复操作！");
//				return;
//			}
			if(status == '2' && billStatus != '1' && billStatus != '5') {
				showWarn("只允许操作制单状态的单据！");
				return;
			}
			
			if(status == '5' && billStatus != '2' ) {
				showWarn("只允许操作制单状态的单据！");
				return;
			}
//			if(status == '5' && billStatus != '5' ) {
//				showWarn("只允许操作财务确认状态的单据！");
//				return;
//			}
//			if( status == '5' && billStatus != '1') {
//				showWarn("只允许操作制单状态的单据！");
//				return;
//			}
//			if( status == '5' && billStatus == '1') {
//				showWarn("只允许操作审核状态的单据！");
//				return;
//			}
			
//			if(status == '5') {
//				var confirmFlag = true;
//				$.each(checkedRows, function(index, item){
//					if(item.status != '5') {
//						showWarn("只允许操作审核状态的单据！");
//						confirmFlag = false;
//						return false;
//					}
//					if(item.status == '1' && status == '1') {
//						showWarn("制单状态的单据不允许进行反确认操作！");
//						return;
//					}
//					if(item.status == '1' && status == '4') {
//						showWarn("已开票申请状态的单据不允许进行反确认操作！");
//						return;
//					}
//					if(item.status && billStatus && item.status == status) {
//						showWarn("单据已"+message+"，请勿重复操作！");
//						return;
//					}
//					if(item.status == '2' && status != '5') {
//						showWarn("只允许操作制单状态的单据！");
//						return;
//					}
//				});
//				if(!confirmFlag) {
//					return;
//				}
//			}
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
//			if(!bill.saveBill()){
//				return;
//			} 
			$.messager.confirm("确认", "你确定要"+message+"该条单据?", function(r) {
				if(r) {
					$('#mainDataForm').form('submit', {
						url : BasePath + '/mall_shopbalance/confirm',
						onSubmit:function(param) {
							$("#status").val(status);
							param.auditor = currentUser.loginName;
							param.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
							if(status == '1') {
								param.auditor = '';
								param.auditTime = '';
							}
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
			};
			// 检查是否可进行保存操作
			this.checkUpdate = function(options) {
				var flag = $this.super.checkUpdate.call(this, options);
				if(!flag) {
					return false;
				}
				flag = checkHasNextBillBalance();
				if(!flag) {
					showWarn("请检查系统参数设置,结算单控制处理策略不可修改.");
					return false;
				}
				return true;
			};
			this.checkOperate = function() {
				var flag = checkHasNextBillBalance();
				if(!flag) {
					showWarn("请检查系统参数设置,结算单控制处理策略不可修改..");
					return false;
				}
				return true;
			};
			if(status == '1') {
				var confirmFlag = true;
				$.each(checkedRows, function(index, item){
					if(item.status != '2' && item.status != '5' ) {
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
					var companyNo = item.companyNo;
					var dtlValue = checkSystemDeduAmountEdit(companyNo,'msBalanceAudit');  //门店结算单审核流程
					if(dtlValue != null && dtlValue != '' && dtlValue == '1') {  //控制业务-财务审核流程
						if(status == '2' && item.status != '5') {
							showWarn("只允许操作业务确认状态的单据！");
							confirmFlag = false;
							return false;
						}
					}
					
					if(item.status != '1' && item.status != '5') {
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
			var checkedRows = $("#" + $this.options.listDataGridId).datagrid("getChecked");
			if(checkedRows.length < 1) {
				showWarn("请选择您要操作的单据！");
				return;
			}
			if(status == '5') {
				var confirmFlag = true;
				$.each(checkedRows, function(index, item){
					if(item.status != '2') {
						showWarn("只允许操作审核状态的单据！");
						confirmFlag = false;
						return false;
					}
					if(item.status == '1' && status == '1') {
						showWarn("制单状态的单据不允许进行反确认操作！");
						return;
					}
					if(item.status == '1' && status == '4') {
						showWarn("已开票申请状态的单据不允许进行反确认操作！");
						return;
					}
					if(item.status && billStatus && item.status == status) {
						showWarn("单据已"+message+"，请勿重复操作！");
						return;
					}
					if(item.status == '2' && status != '5') {
						showWarn("只允许操作制单状态的单据！");
						return;
					}
					
					var billStatus = item.status;					
					var companyNo = item.companyNo;
					var dtlValue = checkSystemDeduAmountEdit(companyNo,'msBalanceAudit');  //门店结算单审核流程
					if(dtlValue != null && dtlValue != '' && dtlValue == '1') {  //控制业务-财务审核流程
						if(status == '2' && item.status != '5') {
							showWarn("只允许操作业务确认状态的单据！");
							confirmFlag = false;
							return false;
						}
					}
					if(status == '5') {
						if(dtlValue != null && dtlValue != '' && dtlValue == '1') {  //控制业务-财务审核流程
							  status ='5';
							}  else 
							  status ='1';	
					}
				});
				if(!confirmFlag) {
					return;
				}
			}
//			if(status == '5') {
//				var confirmFlag = true;
//				$.each(checkedRows, function(index, item){
//										
//					if(item.status != '1' && item.status != '5') {
//						showWarn("只允许操作未审核状态的单据！");
//						confirmFlag = false;
//						return false;
//					}
//					
//				});
//				if(!confirmFlag) {
//					return;
//				}
//			}
			$.messager.confirm("确认", "你确定要"+message+"该条单据?", function(r) {
				if(r) {
					$.each(checkedRows, function(index, row) {
						row.status = status;
						row.auditor = currentUser.loginName;
						row.auditTime = new Date().format("yyyy-MM-dd hh:mm:ss");
						if(status == '1') {
							row.auditor = '';
							row.auditTime = '';
						}
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
		
		//华北调整为    系统收入 - 商场报数	
		var companyNo =rowData.companyNo;
		if($.trim(companyNo.substring(0,1)) != 'D'){
			$('#balanceDiffDataGrid').datagrid('hideColumn', 'balanceDiffAmount'); 
		} else
			$('#balanceDiffDataGrid').datagrid('showColumn', 'balanceDiffAmount'); 
		
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
			 if(result && result.differenceAmount) {
				 $("#differenceAmount").val(result.differenceAmount);
				 $("#differenceAmount").html("<th><span class='l-btn-text icon-tips l-btn-icon-left' id='differenceAmount'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				 $.fas.tooltip({
						id : 'differenceAmount',
						content : result.differenceAmount
					});
			 } else {
					$("#differenceAmount").html("");
			}
			 if(result && result.returnedAmount) {
				 $("#returnedAmount").val(result.returnedAmount);
				 $("#returnedAmount").html("<th><span class='l-btn-text icon-tips l-btn-icon-left' id='returnedAmount'>&nbsp;&nbsp;&nbsp;&nbsp;</span></a></td>");
				 $.fas.tooltip({
						id : 'returnedAmount',
						content : result.returnedAmount
					});
			 } else {
					$("#returnedAmount").html("");
			}
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
	this.exportDetailExcel = function() {
		if($("#balanceNo").val() == '') {
			showWarn("请选择您要导出的数据！");
			return;
		}
		var headerFormData = $.fas.serializableForm("mainDataForm", 4);
		var dtlShowType = $("#showType").combobox("getValue");
		var dtlDataGridName = "";
		var saleDtlExportColumns = [];
		if(dtlShowType == '1') {
			var categoryDataGridColumns = $("#categoryDataGrid").datagrid('options').columns;
			saleDtlExportColumns = $.grep(categoryDataGridColumns[0], function(o, i) {
				if ($(o).attr("notexport") == true) {
					return true;
				}
				return false;
			}, true);
			dtlDataGridName = "按大类显示";
		} else if(dtlShowType == '2') {
			var balanceDtlDataGridColumns = $("#balanceDtlDataGrid").datagrid('options').columns;
			saleDtlExportColumns = $.grep(balanceDtlDataGridColumns[0], function(o, i) {
				if ($(o).attr("notexport") == true) {
					return true;
				}
				return false;
			}, true);
			dtlDataGridName = "按明细显示";
		} else if(dtlShowType == '3') {
			var promotionsDataGridColumns = $("#promotionsDataGrid").datagrid('options').columns;
			saleDtlExportColumns = $.grep(promotionsDataGridColumns[0], function(o, i) {
				if ($(o).attr("notexport") == true) {
					return true;
				}
				return false;
			}, true);
			dtlDataGridName = "按活动显示";
		} else if(dtlShowType == '4') {
			var paymentMethodDataGridColumns = $("#paymentMethodDataGrid").datagrid('options').columns;
			saleDtlExportColumns = $.grep(paymentMethodDataGridColumns[0], function(o, i) {
				if ($(o).attr("notexport") == true) {
					return true;
				}
				return false;
			}, true);
			dtlDataGridName = "按收款方式显示";
		} else if(dtlShowType == '5') {
			var billingCodeDataGridColumns = $("#billingCodeDataGrid").datagrid('options').columns;
			saleDtlExportColumns = $.grep(billingCodeDataGridColumns[0], function(o, i) {
				if ($(o).attr("notexport") == true) {
					return true;
				}
				return false;
			}, true);
			dtlDataGridName = "按商场结算码显示";
		}
		
	    var diffExportColumns = [{field:'diffTypeName',title : '差异类型'},
	                             {field:'diffDate',title : '差异结算日'},
	                             {field:'brandName',title : '品牌部名称'},
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
	    var deductExportColumns = [{field:'brandName',title : '品牌部名称'},
	                               {field:'month',title : '结算月'},
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
            url : BasePath + "/mall_shopbalance/do_dtl_export",
            onSubmit : function(params) {
            	params.balanceNo = $("#balanceNo").val();
            	params.shopNo = $("#shopNo").val();
            	params.month = $("#month").val();
            	params.startDate = $("#balanceStartDate").val();
            	params.endDate = $("#balanceEndDate").val();
            	params.headerFormData = JSON.stringify(headerFormData);
            	params.dtlShowType = dtlShowType;
            	params.saleDtlExportColumns = JSON.stringify(saleDtlExportColumns);
            	params.dtlDataGridName = dtlDataGridName;
            	params.diffExportColumns = JSON.stringify(diffExportColumns);
            	params.diffFileName = "结算差异";
            	params.deductExportColumns = JSON.stringify(deductExportColumns);
            	params.deductFileName = "票前费用";
            	params.fileName = $("#balanceNo").val()+"结算单信息导出";
            },
            success : function() {

            }
        });
	};
	
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
		                             {field:'brandName',title : '品牌部名称'},
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
		    var deductExportColumns = [{field:'brandName',title : '品牌部名称'},
		                               {field:'month',title : '结算月'},
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

	
	// 改变商场报数数据时，触发的函数   报数差异 {报数差异：系统收入-商场报数}
	this.changeMallNumberAmount = function() {
		var mallNumberAmount = $("#mallNumberAmount").val();	
		var systemSalesAmount = $("#systemSalesAmount").val();
		if(!mallNumberAmount) {
			mallNumberAmount = 0;
		}
		if(!systemSalesAmount) {
			systemSalesAmount = 0;
		}
		$("#salesDiffamount").val($.fas.floatFixed(parseFloat(systemSalesAmount - mallNumberAmount)));
	};
	
	// 改变商场开票金额时，触发的函数，改变扣费总额、结算差异总额
	this.changeMallBillingAmount = function() {
		var mallBillingAmount = $("#mallBillingAmount").val();	
		var systemSalesAmount = $("#systemSalesAmount").val();
		if(!mallBillingAmount) {
			mallBillingAmount = 0;
		}
		if(!systemSalesAmount) {
			systemSalesAmount = 0;
		}
		mallBillingAmount=$.fas.floatFixed(parseFloat(mallBillingAmount));
		systemSalesAmount=$.fas.floatFixed(parseFloat(systemSalesAmount));
		// 设置扣费总额
		$("#mallDeductAmount").val($.fas.floatFixed(parseFloat(systemSalesAmount - mallBillingAmount)));
		// 系统开票金额
		var systemBillingAmount = $("#systemBillingAmount").val();
		if(!systemBillingAmount) {
			systemBillingAmount = 0;
		}
		systemBillingAmount=$.fas.floatFixed(parseFloat(systemBillingAmount));
		// 设置结算差异总额
		$("#balanceDiffAmount").val($.fas.floatFixed(parseFloat(systemBillingAmount - mallBillingAmount)));
	};
	
	// 系统校验：系统开票金额-商场开票金额=结算差异，提示差异额有多少
	this.changeDiffAmount = function() {
		var systemBillingAmount = $("#systemBillingAmount").val();	
		var mallBillingAmount = $("#mallBillingAmount").val();
		var balanceDiffAmount = $("#balanceDiffAmount").val();
		
		if(!systemBillingAmount) {
			systemBillingAmount = 0;
		}
		if(!mallBillingAmount) {
			mallBillingAmount = 0;
		}
		if(!balanceDiffAmount) {
			balanceDiffAmount = 0;
		}
		var diffAmount=parseFloat(systemBillingAmount - mallBillingAmount).toFixed(2);
		if((diffAmount-balanceDiffAmount) > 0.02 || (diffAmount-balanceDiffAmount) < -0.02) {
			alert('系统开票金额-商场开票金不等于结算差异，差异额为'+diffAmount);
		}
	};
};

//1.1单据列表对象
function ShopBalanceDialog() {
	var $this = this;
	this.toAdd = function() {
		bill.toAddBill();
		returnTab('mainTab', '单据明细');
	}
}

//2.结算差异页签对象
function BillShopBalanceDiffEditor() {
	
	// 转换状态值
	this.formatGenerateType = function(value) {
		if(value == '0') {
			return "系统生成";
		}
		if(value == '1') {
			return "手工新增";
		}
	};
	
	this.generateType = function(value, rowData, rowIndex) {
	    var generateType = [{'value':'0', 'text': '系统生成'}, {'value':'1', 'text':'手工新增'}];
	    for(var i = 0; i < generateType.length; i++) {
	        if(generateType[i].value == value) {
	            return generateType[i].text;
	        }
	    }
	    return "";
	};
	
	var $this = this;
	//验证当前是否选中活动
	this.isCheckedPro=function (){
		var proName= $("#proName").combogrid('getValue');
		if(proName == null || proName =="" || proName == undefined){
			return false;
		}
		return true;
	}
	// 新增操作时，组装的数据
	this.buildAddData = function(rowIndex) {
		return {
			balanceNo : $("#balanceNo").val(),
			companyNo : $("#companyNo").val(),
			companyName : $("#companyName").combogrid("getValue"),
			shopNo : $("#shopNo").val(),
			shortName : $("#shortName").shop("getValue"),
			month : $("#month").datebox("getValue"),
			brandNo : $("#brandNo1").val(),
			brandName :  $("#brandName1").val(),
		};
		
		//新增行时复制上一行部分字段
		var copyData = {};
		if(rowIndex > 0) {
			var copyRow = $("#balanceDiffDataGrid").datagrid('getRows')[rowIndex-1];
			copyData = {
				shortName:copyRow.shortName,
				shopNo:copyRow.shopNo,
//				month:copyRow.month,
//				balanceStartDate:copyRow.balanceStartDate,
//				balanceEndDate:copyRow.balanceEndDate,
				companyNo:copyRow.companyNo,
				companyName:copyRow.companyName,
//				bsgroupsNo:copyRow.bsgroupsNo,
//				bsgroupsName:copyRow.bsgroupsName,
//				mallNo:copyRow.mallNo,
//				mallName:copyRow.mallName,
				organNo:copyRow.organNo,
				organName:copyRow.organName,
				brandNo:copyRow.brandNo,
				brandName:copyRow.brandName
			}
		}
		
		var row = $.extend({}, copyData);
		
		return row;
	};
	// 新增之前设置差异回款不能点击
	this.afterAdd = function() {
		$("#diffBackAmount").iptSearch("disable");
		$("#proName").combogrid("enable");
		// 本期差异余额字段锁定
		$.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			field : "diffBalance"
		}).numberbox("disable");
		// 上期差异余额字段锁定
		$.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			field : "preDiffBalance"
		}).numberbox("disable");
		// 设置差异结算日的值
		$.fas.setEditorVal({
			dataGridId : "balanceDiffDataGrid",
			field : "diffDate",
			dataType : "datebox",
			value : $("#balanceEndDate").val()
		});
		var generateType = $('#balanceDiffDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'generateType'});
		generateType.target.val(1);
		// 改变金额时，重新计算差异余额的值
		$this.changeAmountEnvent();
	};
	// 修改行数据之前调用的函数
	this.afterUpdate = function(rowIndex, rowData) {
		$.fas.assistEditIndex = rowIndex;
		//表示上期的差异,可选择差异回款
		//如果是系统生成的   generateType = 0 的则 系统自动生成的差异行，扣率、系统扣费、系统收入能任意修改
		if(rowData && rowData.generateType != null && $.trim(rowData.generateType) != "" && rowData.generateType == 0) {
			$("#diffBackAmount").iptSearch("enable");
			$("#proName").combogrid("disable");
			$.fas.getFieldEditor({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "deductDiffAmount"
			}).numberbox("disable");
			$.fas.getFieldEditor({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "discount"
			}).numberbox("disable");
			$.fas.getFieldEditor({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "salesAmount"
			}).numberbox("disable");
		} else {
			$("#diffTypeName").combogrid("enable");
			$("#diffBackAmount").iptSearch("disable");
			$("#proName").combogrid("enable");
		}
		if(rowData && rowData.parBalanceNo != null && $.trim(rowData.parBalanceNo) != "") {
//			$("#diffTypeName").combogrid("disable"); // 暂时屏蔽差异类型不可编辑的功能，因为如果设置不可编辑，则修改时，光标会自动移到该字段中
			$("#diffBackAmount").iptSearch("enable");
			$("#proName").combogrid("disable");
			$.fas.getFieldEditor({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "deductDiffAmount"
			}).numberbox("disable");
			$.fas.getFieldEditor({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "mallNumber"
			}).numberbox("disable");
			$.fas.getFieldEditor({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "diffAmount"
			}).numberbox("disable");
		} else {
			$("#diffTypeName").combogrid("enable");
			$("#diffBackAmount").iptSearch("disable");
			$("#proName").combogrid("enable");
		}
		// 本期差异余额字段锁定
		$.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffBalance"
		}).numberbox("disable");
		// 改变金额时，重新计算差异余额的值
		$this.changeAmountEnvent(rowIndex);
	};
	// 改变金额时，触发的事件
	this.changeAmountEnvent = function(rowIndex) {
		// 1.商场报数
		var mallNumberEditor = $.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "mallNumber",
			dataType : "numberbox"
		});
		mallNumberEditor.bind('keyup',function(){
			$this.changeMallNumber(mallNumberEditor, rowIndex);
		});
		// 2.系统收入
		var salesAmountEditor = $.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "salesAmount",
			dataType : "numberbox"
		});
		salesAmountEditor.bind('keyup',function(){
			$this.changeSalesAmount(salesAmountEditor, rowIndex);
		});
		//3.扣费差异
		var diffAmountEditor = $.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffAmount",
			dataType : "numberbox"
		});
		diffAmountEditor.bind('keyup',function(){
			$this.changeDiffAmountValue(diffAmountEditor, rowIndex);
		});
		// 4.调整金额
		var amountEditor = $.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "changeAmount",
			dataType : "numberbox"
		});
		amountEditor.bind('keyup',function(){
			$this.changeAmountField(amountEditor,rowIndex);
		});
	};
	//1.改变"商场报数"字段值,触发的函数
	this.changeMallNumber = function(mallNumberEditor, rowIndex) {
		// 系统收入
		var salesAmount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "salesAmount",
			dataType : "numberbox"
		});	
		salesAmount = salesAmount ? salesAmount : 0;
		// 商场报数
		var mallNumber = $(mallNumberEditor).val();
		if(!mallNumber) {
			$(mallNumberEditor).val("0");
		}
		if(mallNumber == '-') {
			return;
		}
		mallNumber = mallNumber ? mallNumber : 0;
		var diffAmountjs = 0;
		if(mallNumber != null) {
			diffAmountjs = parseFloat(mallNumber) - parseFloat(salesAmount);
		}
		diffAmountjs = $.fas.floatFixed(diffAmountjs);
		// 1.设置差异金额(A-B) 差异金额：商场报数-系统收入）		
		$.fas.setEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "salesDiffamount",
			value : diffAmountjs,
			dataType : "numberbox"
		});
		
		//华北调整为    系统收入 - 商场报数	
		var companyNo = $("#companyNo").val();
		if( $.trim(companyNo.substring(0,1)) == 'D'){	 
			if(mallNumber != null) {
				diffAmountjs = parseFloat(salesAmount) - parseFloat(mallNumber);
			}
			diffAmountjs = $.fas.floatFixed(diffAmountjs);
			// 1.设置差异金额(A-B) 差异金额：商场报数-系统收入）		
			$.fas.setEditorVal({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "salesDiffamount",
				value : diffAmountjs,
				dataType : "numberbox"
			}); 
		}
		
		//扣率
		var disCount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "discount",
			dataType : "numberbox"
		});	
		if(!this.isCheckedPro()){
			disCount = 100;
		}
		//扣费差异
		var diffAmount = 0.00;
		// 如果扣率不为0，则扣费差异=(商场报数-系统输入) * 扣率
		if(disCount > 0) {
			diffAmount = (diffAmountjs * disCount) / 100;
		}else{
			diffAmount = diffAmountjs * disCount;
		}
		// 扣费差异字段值保留2位小数
		diffAmount = $.fas.floatFixed(diffAmount);
		//当不选择活动时，设置报数差异
		if(!this.isCheckedPro()){
			diffAmount = diffAmount * -1;
			$.fas.setEditorVal({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "reportDiffAmount",
				value : diffAmount,
				dataType : "numberbox"
			});
		}
		//2.设置扣费差异
		$.fas.setEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffAmount",
			value : diffAmount,
			dataType : "numberbox"
		});
		// 扣费差异
		var diffAmountEditor = $.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffAmount",
			dataType : "numberbox"
		});
		$this.changeDiffAmountValue(diffAmountEditor,rowIndex);
	};
	//2.改变"系统收入"字段值,触发的函数
	this.changeSalesAmount =  function(salesAmountEditor, rowIndex) {
		// 系统收入
		var salesAmount = $(salesAmountEditor).val();
		salesAmount = salesAmount ? salesAmount : 0;
		// 商场报数
		var mallNumber = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "mallNumber",
			dataType : "numberbox"
		});
		if(mallNumber == '-' || mallNumber=="" || mallNumber==undefined) {
			return;
		}
		mallNumber = mallNumber ? mallNumber : 0;
		var diffAmountjs = 0;
		if(mallNumber != null) {
			diffAmountjs = parseFloat(mallNumber) - parseFloat(salesAmount);
		}
		diffAmountjs = $.fas.floatFixed(diffAmountjs);
		// 1.设置差异金额(A-B) 差异金额：商场报数-系统收入）
		$.fas.setEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "salesDiffamount",
			value : diffAmountjs,
			dataType : "numberbox"
		});
		//华北调整为    系统收入 - 商场报数	
		var companyNo = $("#companyNo").val();
		if( $.trim(companyNo.substring(0,1)) == 'D'){	
			if(mallNumber != null) {
				diffAmountjs = parseFloat(salesAmount) - parseFloat(mallNumber);
			}
			diffAmountjs = $.fas.floatFixed(diffAmountjs);
			// 1.设置差异金额(A-B) 差异金额：商场报数-系统收入）		
			$.fas.setEditorVal({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "salesDiffamount",
				value : diffAmountjs,
				dataType : "numberbox"
			}); 
		}
		//扣率
		var disCount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "discount",
			dataType : "numberbox"
		});	
		if(!this.isCheckedPro()){
			disCount = 100;
		}
		//扣费差异
		var diffAmount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffAmount",
			dataType : "numberbox"
		});
		// 如果扣率不为0，则扣费差异=(商场报数-系统输入) * 扣率
		if(disCount > 0) {
			diffAmount = (diffAmountjs * disCount) / 100;
		}
		// 扣费差异字段值保留2位小数
		diffAmount = $.fas.floatFixed(diffAmount);
		//当不选择活动时，设置设置报数差异
		if(!this.isCheckedPro()){
			diffAmount= diffAmount * -1;
			$.fas.setEditorVal({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "reportDiffAmount",
				value : diffAmount,
				dataType : "numberbox"
			});
		}
		//2.设置扣费差异
		$.fas.setEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffAmount",
			value : diffAmount,
			dataType : "numberbox"
		});
		// 扣费差异
		var diffAmountEditor = $.fas.getFieldEditor({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffAmount",
			dataType : "numberbox"
		});
		$this.changeDiffAmountValue(diffAmountEditor,rowIndex);
	}
	//3. 改变"扣费差异"字段值,触发的函数
	this.changeDiffAmountValue = function(diffAmountEditor, rowIndex) {
		// 差异回款
		var diffBackAmount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffBackAmount",
			dataType : "numberbox"
		});	
		diffBackAmount = diffBackAmount ? diffBackAmount : 0;
		// 调整金额
		var changeAmount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "changeAmount",
			dataType : "numberbox"
		});	
		changeAmount = changeAmount ? changeAmount : 0;
		// 上期差异余额
		var preDiffBalance = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "preDiffBalance",
			dataType : "numberbox"
		});
		preDiffBalance = preDiffBalance ? preDiffBalance : 0;
		// 扣费差异金额
		var diffAmount = $(diffAmountEditor).val();
		diffAmount = diffAmount ? diffAmount : 0;
		//设置本期差异余额=上期差异余额+扣费差异+调整金额
		var diffBalance = parseFloat(preDiffBalance) + parseFloat(diffAmount) + parseFloat(changeAmount);
		$.fas.setEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffBalance",
			value : diffBalance,
			dataType : "numberbox"
		});
		//本期差异余额为0，状态自动完成
		if(diffBalance != 0.00) {
			$("#diffStatus").combobox("select", "0");
		}else{
			$("#diffStatus").combobox("select", "1");
		}
		if(rowIndex==undefined){
			rowIndex = $.fas.editIndex;
		}
		var discount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "discount",
			dataType : "numberbox"
		});
		var diffReason =  $('#balanceDiffDataGrid').datagrid('getEditor', {index:rowIndex,field:'diffReason'});
		var diffReasonVal =diffReason.target.val();
		if(diffReasonVal!=''){
			return;
		}else{
			//扣费差异不为空   差异原因必填项 控制 
			if(diffAmount != 0){
				$(diffReason.target).validatebox({"required":true});
				$.fas.endEditing("balanceDiffDataGrid");
			}else{
				if(discount!=0){
					$(diffReason.target).validatebox({"required":false});
					$.fas.endEditing("balanceDiffDataGrid");
					$('#balanceDiffDataGrid').datagrid('beginEdit', rowIndex);
					focusEditor('#balanceDiffDataGrid','mallNumber',rowIndex);
					this.changeAmountEnvent(rowIndex);
				}
			}
		}
	};
	//4.改变"调整金额"字段值,触发的函数
	this.changeAmountField =  function(amountEditor,rowIndex) {
		// 扣费差异
		var diffAmount = $.fas.getEditorVal({
			dataGridId : "balanceDiffDataGrid",
			rowIndex : rowIndex,
			field : "diffAmount",
			dataType : "numberbox"
		});	
		diffAmount = diffAmount ? diffAmount : 0;
		// 调整金额
		var changeAmount = $(amountEditor).val();
		if(changeAmount) {
			changeAmount = (changeAmount == '' || changeAmount == '-') ? 0 : changeAmount;
			// 设置差异余额字段的值
    		var diffBalance = $.fas.getEditorVal({
    			dataGridId : "balanceDiffDataGrid",
    			rowIndex : rowIndex,
    			field : "preDiffBalance",
    			dataType : "numberbox"
    		});
    		diffBalance = diffBalance ? diffBalance : 0;
    		//本期差异余额=上期差异余额+扣费差异+调整金额
    		diffBalance = parseFloat(diffBalance) + parseFloat(diffAmount) + parseFloat(changeAmount);
			$.fas.setEditorVal({
				dataGridId : "balanceDiffDataGrid",
				rowIndex : rowIndex,
				field : "diffBalance",
				value : diffBalance,
				dataType : "numberbox"
			});
			//本期差异余额为0，状态自动完成
			if(diffBalance == 0.00) {
				$("#diffStatus").combobox("select", "1");
			} else {
				$("#diffStatus").combobox("select", "0");
			}
		}
	}
	
	// 校验是否可新增行
	this.checkInsert = function() {
		var flag = $("#mainDataForm").form('validate');
	    if(flag == false) {
	        return;
	    }
		flag = $this.super.checkInsert.call(this, $this.options);
		if(!flag) {
			return false;
		}
		return $this.checkOperate();
	};
	// 校验是否可修改行
	this.checkUpdate = function(options, rowIndex, rowData) {
		return $this.checkOperate();
	};
	// 校验是否可进行删除行
	this.checkDel = function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
		if(!checkedRows || checkedRows.length == 0) {
			return false;
		}
		var flag = true;
		$.each(checkedRows, function(index, item) {
			if(item.parBalanceNo && $.trim(item.parBalanceNo) != '') {				
				flag = false;
				return;
			}
		});
		if(!flag) {
			showWarn("上期的差异和票前费用不允许删除！");
			return false;
		}
		return $this.checkOperate();
	};
	// 检查是否可新增、删除、编辑行
	this.checkOperate = function() {
		var flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可修改..");
			return false;
		}
		return true;
	};
	// 转换状态值
	this.formatStatus = function(value) {
		if(value == '0') {
			return "未完成";
		}
		if(value == '1') {
			return "已完成";
		}
	};

};

//2.1清空结算差异grid文本框
function clearGridText(){
	var clearTextArray=[
			{type: 'fastextbox',text: 'proName'},
			{type: 'numberbox',text: 'reportDiffAmount'},
			{type: 'numberbox',text: 'deductDiffAmount'},
			{type: 'numberbox',text: 'salesDiffamount'},
			{type: 'numberbox',text: 'discount'},
			{type: 'readonlybox',text: 'discountN'},
			{type: 'numberbox',text: 'mallNumber'},
			{type: 'numberbox',text: 'salesAmount'},
			{type: 'numberbox',text: 'diffAmount'},
			{type: 'numberbox',text: 'preDiffBalance'},
			{type: 'numberbox',text: 'diffBalance'},
			{type: 'numberbox',text: 'changeAmount'}
	];
	clearArrayText(clearTextArray,$.fas.editIndex,'balanceDiffDataGrid');
}

//2.2结算差异活动选中事件
function selectProNameEvent(newValue, oldValue){
	clearGridText();
    //选择活动时，扣率，系统扣费，系统收入，差异金额不可编辑
	$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'preDiffBalance'}).numberbox('disable');
	$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'diffBalance'}).numberbox('disable');
	if(newValue && newValue != '') {
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'proStartDate'}).datebox('disable');
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'proEndDate'}).datebox('disable');
//		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'deductDiffAmount'}).numberbox('disable');
//		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'salesDiffamount'}).numberbox('disable');
//		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'salesAmount'}).numberbox('disable');
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'discountCode'}).datebox('disable');
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'proNo'}).datebox('disable');
	}else {
		var clearTextArray=[
			{type: 'fastextbox',text: 'discountCode'},
			{type: 'fastextbox',text: 'proNo'},
			{type: 'datebox',text: 'proStartDate'},
			{type: 'fastextbox',text: 'proEndDate'}
		];
		clearArrayText(clearTextArray,$.fas.editIndex,'balanceDiffDataGrid')
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'proNo'}).datebox('enable');
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'discountCode'}).datebox('enable');
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'proStartDate'}).datebox('enable');
		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'proEndDate'}).datebox('enable');
//		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'salesDiffamount'}).numberbox('enable');
//		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'deductDiffAmount'}).numberbox('enable');
//		$.fas.getFieldEditor({dataGridId : 'balanceDiffDataGrid',field : 'salesAmount'}).numberbox('enable');
	}
}

// 检查是否已生成下期结算单
function checkHasNextBillBalance1() {
	var url = BasePath + '/mall_shopbalance/hasNextBillBalance';
	var shopNo = $('#shopNo').val();
	var balanceEndDate = $('#balanceEndDate').val();
	if(!shopNo || $.trim(shopNo) == '') {
		return false;
	} 
	var month = $("#month").datebox("getValue");
	var params = {
		shopNo : shopNo,
		balanceEndDate : balanceEndDate,
		month : month
	};
	return $.fas.ajaxRequestAsync(url, params ,function(result) {
		if(result && result == 'success') {
			return true;
		} else {
			return false;
		}
	});
	return true;
};

//根据系统参数设置检查是否可以修改结算单  checkSystemUpdateSet
function checkHasNextBillBalance() {
	var url = BasePath + '/mall_shopbalance/hasNextBillBalance';
	var shopNo = $('#shopNo').val();
	var balanceEndDate = $('#balanceEndDate').val();
	var companyNo = $('#companyNo').val();
	var status = $('#status').val();
	if(!shopNo || $.trim(shopNo) == '') {
		return false;
	} 
	var month = $("#month").datebox("getValue");
	var params = {
		shopNo : shopNo,
		balanceEndDate : balanceEndDate,
		companyNo :companyNo,
		status : status,
		month : month
	};
	return $.fas.ajaxRequestAsync(url, params ,function(result) {;
		if(result && result == 'success') {
			return true;
		} else {
			return false;
		}
	});
	return true;
};

//检查费用管理系统扣费金额是否可以编辑
function checkSystemDeduAmountEdit(businessOrganNo,paramCode) {     
	var url = BasePath + '/base_setting/system_param_set/findSystemParamByParma';
	if(!businessOrganNo || $.trim(businessOrganNo) == '') {
		return false;
	}     
	var params = {
		 businessOrganNo : businessOrganNo,
		 paramCode : paramCode
	};
	return $.fas.ajaxRequestAsync(url, params ,function(data) {
 		return data; 
	});   
};

//3.票前费用页签对象
function BillBalanceDeductEditor() {
	var $this = this;
	// 新增操作时，组装的数据
	this.buildAddData = function() {
		return {month:$("#month").val()};
	};
	//新增之前设置
	this.afterAdd = function() {
		var deductAmountEditor = $("#balanceDeductDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
		var companyNo = $("#companyNo").val();
//		if( $.trim(companyNo.substring(0,1)) == 'K'){	
//		  $(deductAmountEditor.target).numberbox("disable");
//		}
		var dtlValue = checkSystemDeduAmountEdit(companyNo,'systemDeduAmountEdit');
		if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
			$(deductAmountEditor.target).numberbox("disable");
		}
		$(deductAmountEditor.target).bind('change',function(){
			balanceDeductEditor.changeDeductAmount(deductAmountEditor);
		});
		var actualAmountEditor = $("#balanceDeductDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'actualAmount'});
		$(actualAmountEditor.target).bind('change',function(){
			balanceDeductEditor.changeActualAmount(actualAmountEditor);
		});
		var diffAmountEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffAmount'});
		var diffReason =  $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var processStatusEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffAmount = diffAmountEditor.target.val();	
		var diffReasonVal = $("#diffReason").val();
		var generateType = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'generateType'});
		$(generateType.target).combobox("setValue", "1");
		if(diffAmount == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');
			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因..！");
				return;
			}
		}
	};
	// 修改行数据之前调用的函数
	this.afterUpdate = function(rowIndex, rowData) {
//		$("#deductAmount").numberbox("disable");

//		$("#deductAmount").attr('readonly',true); 
		if(rowData.generateType == '0') {
			var deductionNameEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'deductionName'});
			var costTypeEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'costType'});
//////			$("#deductionName").combogrid("disable");
//////			$("#costType").combobox("disable");
			$(deductionNameEditor.target).combobox('disable');
			$(costTypeEditor.target).combobox('disable');
		}
//		}
		var deductAmountEditor = $("#balanceDeductDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
		var companyNo = $("#companyNo").val();
//		if( $.trim(companyNo.substring(0,1)) == 'K'){
//		   $(deductAmountEditor.target).numberbox("disable");
//		}
		var dtlValue = checkSystemDeduAmountEdit(companyNo,'systemDeduAmountEdit');
		if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
			$(deductAmountEditor.target).numberbox("disable");
		}
		$(deductAmountEditor.target).bind('change',function(){
			balanceDeductEditor.changeDeductAmount(deductAmountEditor);
		});
		var actualAmountEditor = $("#balanceDeductDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'actualAmount'});
		$(actualAmountEditor.target).bind('change',function(){
			balanceDeductEditor.changeActualAmount(actualAmountEditor);
		});
		var diffAmountEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffAmount'});
		var diffReason =  $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var processStatusEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffAmount = diffAmountEditor.target.val();	
		var diffReasonVal =diffReason.target.val();// $("#diffReason").val();
		if(diffAmount == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');				
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');
			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因...！");
				return;
			}
		}
	};
	// 检查是否可删除
	this.checkDel = function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
		if(!checkedRows || checkedRows.length == 0) {
			return false;
		}
		var flag = true;
		$.each(checkedRows, function(index, item){
			if(item.id && item.generateType != '1') {
				flag = false;
				return;
			}
		});
		if(!flag) {
			showWarn("不能删除系统自动生成的数据，请重新选择！");
			return false;
		}
		return flag;
	};
	// 校验是否可新增行
	this.checkInsert = function() {
		var flag = $("#mainDataForm").form('validate');
	    if(flag == false) {
	        return;
	    }
		flag = $this.super.checkInsert.call(this, $this.options);
		if(!flag) {
			return false;
		}
		flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可新增...");
			return false;
		}
		return true;
	};
	// 检查行数据是否可修改
	this.checkUpdate = function(options, rowIndex, rowData) {
		var flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可修改....");
			return false;
		}
		return true;
	};
	// 改变"实际扣费金额"列的数据时，触发的函数
	this.changeActualAmount = function(actualAmountEditor) {
		var actualAmount = actualAmountEditor.target.val();	
		var deductAmount =  $("#deductAmount").val();
		if(!deductAmount) {
			deductAmount = 0;
		}
		$("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));
		var processStatusEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffReason =  $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var diffAmount = $("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));	
		var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
		
		var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
		if(diffAmount1 == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');

			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因 .！");
				return;
			}
//			$.fas.setEditorVal({
//				dataGridId : "balanceDeductDataGrid",
//				rowIndex : rowIndex,
//				field : "diffReason",
//				value : diffReason,
//				dataType : "validatebox",
//				required:true
//			});
		}
	};
	// 改变"扣费金额"列的数据时，触发的函数
	this.changeDeductAmount = function(deductAmountEditor) {
		var deductAmount = deductAmountEditor.target.val();	
		var actualAmount = $("#actualAmount").val();
		if(!actualAmount) {
			actualAmount = 0;
		}
		$("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));
		var processStatusEditor = $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffReason =  $('#balanceDeductDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var diffAmount = $("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));	
//		var diffReasonVal = $("#diffReason").val();
		
        var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
		
		var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
		if(diffAmount1 == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');
			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因.！");
				return;
			}
		}
	};
};

//3.票后费用页签对象
function BillBalanceDeductAfterEditor() {
	var $this = this;
	// 新增操作时，组装的数据
	this.buildAddData = function() {
		return {month:$("#month").val()};
	};
	//新增之前设置
	this.afterAdd = function() {
		var deductAmountEditor = $("#balanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
		var companyNo = $("#companyNo").val();
//		if( $.trim(companyNo.substring(0,1)) == 'K'){	
//		     $(deductAmountEditor.target).numberbox("disable");
//		}
		var dtlValue = checkSystemDeduAmountEdit(companyNo,'systemDeduAmountEdit');
		if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
			$(deductAmountEditor.target).numberbox("disable");
		}
		$(deductAmountEditor.target).bind('change',function(){
			balanceDeductAfterEditor.changeDeductAmount(deductAmountEditor);
		});
		var actualAmountEditor = $("#balanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'actualAmount'});
		$(actualAmountEditor.target).bind('change',function(){
			balanceDeductAfterEditor.changeActualAmount(actualAmountEditor);
		});
		var diffAmountEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffAmount'});
		var diffReason =  $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var processStatusEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffAmount = diffAmountEditor.target.val();	
		var diffReasonVal = $("#diffReason").val();
		var generateType = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'generateType'});
		$(generateType.target).combobox("setValue", "1");
		if(diffAmount == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');
			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因..！");
				return;
			}
		}
	};
	// 修改行数据之前调用的函数
	this.afterUpdate = function(rowIndex, rowData) {
//		$("#deductAmount").numberbox("disable");

//		$("#deductAmount").attr('readonly',true); 
//		if(rowData.generateType == '0') {
//			$("#deductionName").combogrid("disable");
//			$("#costType").combobox("disable");
//		}
		if(rowData.generateType == '0') {
			var deductionNameEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'deductionName'});
			var costTypeEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'costType'});
	//////		$("#deductionName").combogrid("disable");
	//////		$("#costType").combobox("disable");
			$(deductionNameEditor.target).combobox('disable');
			$(costTypeEditor.target).combobox('disable');
		}
		var deductAmountEditor = $("#balanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductAmount'});
		var companyNo = $("#companyNo").val();
//		if( $.trim(companyNo.substring(0,1)) == 'K'){
//		    $(deductAmountEditor.target).numberbox("disable");
//		}
		var dtlValue = checkSystemDeduAmountEdit(companyNo,'systemDeduAmountEdit');
		if(dtlValue != null && dtlValue != '' && dtlValue == '1') {
			 $(deductAmountEditor.target).numberbox("disable");
		}
		$(deductAmountEditor.target).bind('change',function(){
			balanceDeductAfterEditor.changeDeductAmount(deductAmountEditor);
		});
		var actualAmountEditor = $("#balanceDeductAfterDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'actualAmount'});
		$(actualAmountEditor.target).bind('change',function(){
			balanceDeductAfterEditor.changeActualAmount(actualAmountEditor);
		});
		var diffAmountEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffAmount'});
		var diffReason =  $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var processStatusEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffAmount = diffAmountEditor.target.val();	
		var diffReasonVal =diffReason.target.val();// $("#diffReason").val();
		
		if(diffAmount == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');				
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');
			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因...！");
				return;
			}
		}
	};
	// 检查是否可删除
	this.checkDel = function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
		if(!checkedRows || checkedRows.length == 0) {
			return false;
		}
		var flag = true;
		$.each(checkedRows, function(index, item){
			if(item.id && item.generateType != '1') {
				flag = false;
				return;
			}
		});
		if(!flag) {
			showWarn("不能删除系统自动生成的数据，请重新选择！");
			return false;
		}
		return flag;
	};
	// 校验是否可新增行
	this.checkInsert = function() {
		var flag = $("#mainDataForm").form('validate');
	    if(flag == false) {
	        return;
	    }
		flag = $this.super.checkInsert.call(this, $this.options);
		if(!flag) {
			return false;
		}
		flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可新增......");
			return false;
		}
		return true;
	};
	// 检查行数据是否可修改
	this.checkUpdate = function(options, rowIndex, rowData) {
		var flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可修改.......");
			return false;
		}
		return true;
	};
	// 改变"实际扣费金额"列的数据时，触发的函数
	this.changeActualAmount = function(actualAmountEditor) {
		var actualAmount = actualAmountEditor.target.val();	
		var deductAmount =  $("#deductAmount").val();
		if(!deductAmount) {
			deductAmount = 0;
		}
		$("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));
		var processStatusEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffReason =  $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var diffAmount = $("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));	
		var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
		
		var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
		if(diffAmount1 == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');

			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因 .！");
				return;
			}
//			$.fas.setEditorVal({
//				dataGridId : "balanceDeductDataGrid",
//				rowIndex : rowIndex,
//				field : "diffReason",
//				value : diffReason,
//				dataType : "validatebox",
//				required:true
//			});
		}
	};
	// 改变"扣费金额"列的数据时，触发的函数
	this.changeDeductAmount = function(deductAmountEditor) {
		var deductAmount = deductAmountEditor.target.val();	
		var actualAmount = $("#actualAmount").val();
		if(!actualAmount) {
			actualAmount = 0;
		}
		$("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));
		var processStatusEditor = $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'processStatus'});
		var diffReason =  $('#balanceDeductAfterDataGrid').datagrid('getEditor', {index:$.fas.editIndex,field:'diffReason'});
		var diffAmount = $("#diffAmount").val(parseFloat(actualAmount- deductAmount).toFixed(2));	
//		var diffReasonVal = $("#diffReason").val();
		
        var diffReasonVal = diffReason.target.val();//$("#diffReason").val();
		
		var  diffAmount1 =parseFloat(actualAmount- deductAmount).toFixed(2);
		if(diffAmount1 == 0) {
			$(processStatusEditor.target).combobox("setValue", "2");
			$(processStatusEditor.target).combobox('disable');
		} else {
			$(processStatusEditor.target).combobox("setValue", "1");
			$(processStatusEditor.target).combobox('enable');
			if(diffReasonVal == null || $.trim(diffReasonVal) == "") {
				showWarn("请填写差异原因.！");
				return;
			}
		}
	};
};

//4.综合店页签对象
function BillBalanceBrandEditor() { 
	var $this = this;
	// 新增操作时，组装的数据
	this.buildAddData = function() {
		return {month:$("#month").val()};
	};
	//新增之前设置
	this.afterAdd = function() {
		
	};
	// 修改行数据之前调用的函数  
	this.afterUpdate = function(rowIndex, rowData) {
//		var systemBillingAmountEditor = $("#balanceBrandDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'systemBillingAmount'});
//		$(systemBillingAmountEditor.target).bind('change',function(){
//			balanceBrandEditor.changeBalanceDiffAmount(systemBillingAmountEditor);
//		});
//		var mallBillingAmountEditor = $("#balanceBrandDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'mallBillingAmount'});
//		$(mallBillingAmountEditor.target).bind('change',function(){
//			balanceBrandEditor.changeBalanceDiffAmountDc(mallBillingAmountEditor);
//		});
	};
	// 改变"系统开票金额"列的数据时，触发的函数
	this.changeBalanceDiffAmount = function(systemBillingAmountEditor) {
		var systemBillingAmount = systemBillingAmountEditor.target.val();	
		var mallBillingAmount =  $("#mallBillingAmountBrand").val();
		if(!mallBillingAmount) {
			mallBillingAmount = 0;
		}
		$("#balanceDiffAmountBrand").val(parseFloat(systemBillingAmount- mallBillingAmount).toFixed(2));
	};
	// 改变"商场开票金额"列的数据时，触发的函数
	this.changeBalanceDiffAmountDc = function(mallBillingAmountEditor) {
		var mallBillingAmount = mallBillingAmountEditor.target.val();	
		var systemBillingAmount = $("#systemBillingAmountBrand").val();
		if(!systemBillingAmount) {
			systemBillingAmount = 0;
		}
		$("#balanceDiffAmountBrand").val(parseFloat(systemBillingAmount- mallBillingAmount).toFixed(2));
		
		var  diffAmount1 =parseFloat(systemBillingAmount- mallBillingAmount).toFixed(2);
		var deductDiffAmount = $("#deductDiffAmountBrand").val();
		if(!deductDiffAmount) {
			deductDiffAmount = 0;
		}
		var balanceDeductAmount = $("#balanceDeductAmountBrand").val();
		if(!balanceDeductAmount) {
			balanceDeductAmount = 0;
		}
//		var deductDiffAmount = $("#balanceBrandDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'deductDiffAmountBrand'});
//		var balanceDeductAmount = $("#balanceBrandDataGrid").datagrid('getEditor',{index:$.fas.editIndex,field:'balanceDeductAmountBrand'});			
		$("#deductAllamount").val((parseFloat(diffAmount1) +  parseFloat(deductDiffAmount) + parseFloat(balanceDeductAmount)).toFixed(2));
	};
	// 检查是否可删除
	this.checkDel = function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
		if(!checkedRows || checkedRows.length == 0) {
			return false;
		}
		var flag = true;
		$.each(checkedRows, function(index, item){
			if(item.id && item.generateType != '1') {
				flag = false;
				return;
			}
		});
		if(!flag) {
			showWarn("不能删除系统自动生成的数据，请重新选择！");
			return false;
		}
		return flag;
	};
	// 校验是否可新增行
	this.checkInsert = function() {
		var flag = $("#mainDataForm").form('validate');
	    if(flag == false) {
	        return;
	    }
		flag = $this.super.checkInsert.call(this, $this.options);
		if(!flag) {
			return false;
		}
		flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可新增_.");
			return false;
		}
		return true;
	};
	// 检查行数据是否可修改
	this.checkUpdate = function(options, rowIndex, rowData) {
		var flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可修改_..");
			return false;
		}
		return true;
	};
	
	// 转换状态值
	this.formatStatus = function(value) {
		if(value == '1') {
			return "未确认";
		}
		if(value == '2') {
			return "已确认";
		}
		if(value == '3') {
			return "已开票";
		}
	};
	
	this.checkEvent = function(value) {
//		alert("~~~~"+value);
	};
	
	//根据 核对状态显示操作信息，实现处理
	this.operate = function(value,row,index) {
		var result='';
		if(row.id != undefined){
			if(row.status == '1'){
				result = result + '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>确认</a>';
			}else{
				result = result + '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>反确认</a>';
			}
		}
		
		return result;
	}
};

function CategoryDataGridEditor() { 
	var $this = this;
	// 新增操作时，组装的数据
	this.buildAddData = function() {
	};
	//新增之前设置
	this.afterAdd = function() {
		
	};
	// 修改行数据之前调用的函数  
	this.afterUpdate = function(rowIndex, rowData) {
		var flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可修改_...");
			return false;
		}
		return true;
	};
	
	// 校验是否可新增行
	this.checkInsert = function() {
		var flag = $("#mainDataForm").form('validate');
	    if(flag == false) {
	        return;
	    }
		flag = $this.super.checkInsert.call(this, $this.options);
		if(!flag) {
			return false;
		}
		return $this.checkOperate();
	};
	// 校验是否可修改行
	this.checkUpdate = function(options, rowIndex, rowData) {
		return $this.checkOperate();
	};
	// 校验是否可进行删除行
	this.checkDel = function(options) {
		var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
		if(!checkedRows || checkedRows.length == 0) {
			return false;
		}
		var flag = true;
		$.each(checkedRows, function(index, item) {
			if(item.parBalanceNo && $.trim(item.parBalanceNo) != '') {				
				flag = false;
				return;
			}
		});
		if(!flag) {
			showWarn("不允许删除！");
			return false;
		}
		return $this.checkOperate();
	};
	// 检查是否可新增、删除、编辑行
	this.checkOperate = function() {
		var flag = checkHasNextBillBalance();
		if(!flag) {
			showWarn("请检查系统参数设置,结算单控制处理策略不可修改_....");
			return false;
		}
		return true;
	};
};

//选择店铺后的回调函数
var selectShopClickFn = function(row) {
	$("#shopNo").val(row.shopNo);
	$("#shortName").shop("setValue", row.shortName);
	$.ajax({
        url: BasePath + "/shop/initSubInfo?"+jQuery.param({shopNo:row.shopNo}),
        async:false,
        cache: true
    }).then(function(result) {
        $("#companyNo").val(result.companyNo);
		$("#companyName").combogrid("setValue", result.companyName);
    });
};

function selectMonthClickFn22(){
	alert("-------");
}



//选择店铺后的回调函数
this.selectMonthClickFn = function(row) {
	var balanceType = $('#balanceType').combobox('getValue'); 
	var month = $("#month").val();
	$("#shopNo").val(row.shopNo);
	$("#shortName").shop("setValue", row.shortName);
	$.ajax({
        url: BasePath + "/shop_balance_date/initSubInfo?"+jQuery.param({balanceType:balanceType,shopNo:row.shopNo,month:month}),
        async:false,
        cache: true
    }).then(function(result) {
        $("#companyNo").val(result.companyNo);
		$("#companyName").combogrid("setValue", result.companyName);
    });
};

var bill = null, balanceDiffEditor = null, balanceDeductEditor = null,balanceBrandEditor=null,balanceDeductAfterEditor=null,categoryEditor=null;
$(function() {

	//绑定店铺通用查询
	$("#shortName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
	
	$("#shopName_").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo_").val(value);
        }
    });
	
	// 初始化单据明细页签
	initDtlTab();
	// 初始化下拉框组件,提示信息
	loadCombobox();
	//加载单据列表页签，tab页签点击时触发的函数
	initBillChangeEvent();
	
	$.fas.extend(ShopBalanceDialog, FasDialogController);
	$.fas.extend(ShopBalanceBill, FasBillController);
	$.fas.extend(BillShopBalanceDiffEditor, FasEditorController);
	$.fas.extend(BillBalanceDeductEditor, FasEditorController);
	$.fas.extend(BillBalanceDeductAfterEditor, FasEditorController);
	$.fas.extend(BillBalanceBrandEditor, FasEditorController);
	$.fas.extend(CategoryDataGridEditor, FasEditorController);
	// 单据列表对象
	dialog = new ShopBalanceDialog();
	dialog.init("/mall_shopbalance", {
		dataGridId : "shopBalanceDataGrid",
		searchFormId : "searchForm",
		searchUrl : "/shopbalance_list_main.json",
		delUrl :  "/deleteShopBalance",
		exportUrl : "/do_exports",
		exportTitle : "商场门店结算单列表导出",
		searchBtn : "btn-search1"
	});
	// 单据头对象
	bill = new ShopBalanceBill();
	bill.init("/mall_shopbalance", {
		listDataGridId : "shopBalanceDataGrid",
		dataFormId : 'mainDataForm',
		dtlDataGridIds : ["balanceDtlDataGrid", "categoryDataGrid", "promotionsDataGrid", "paymentMethodDataGrid", "billingCodeDataGrid",
		                  "balanceDiffDataGrid", "balanceDeductDataGrid","balanceDeductAfterDataGrid","invoiceDataGrid"],
		addUrl : "/saveShopBalanceBill",
		updateUrl : "/updateBill",
		delUrl : "/delBill",
		searchBtn : "btn-search1",
		dataFormFields : {
			input : ["billDate", "billStatusName", "balanceNo", "actualRateName", "contractRateName", "mallDeductAmount", 
			         "mallNumberAmount", "systemSalesAmount", "salesDiffamount", "mallBillingAmount", "systemBillingAmount",  "prepaymentAmount","usedPrepaymentAmount",
			         "balanceDiffAmount", "balanceDeductAmount", "differenceAmount", "estimateAmount","returnedAmount"],
			date : ["month", "startEndDate"],
			combogrid : ["companyName","shortName"],
			combobox : ["balanceType"],
			dgSelector : ["shortName"]
		},
		readOnlyFields : {
			input : ["billDate", "billStatusName", "balanceNo", "actualRateName", "contractRateName", "mallDeductAmount", 
			         "systemSalesAmount", "salesDiffamount", "systemBillingAmount", "prepaymentAmount","usedPrepaymentAmount",
			         "balanceDiffAmount", "balanceDeductAmount", "differenceAmount", "estimateAmount","returnedAmount"],
			date : ["startEndDate"],
			combogrid : ["companyName"]
		}
	});
	// 点击明细区域时，自动触发保存事件
	$("#dtlTabDiv").bind("click", function() {
		var id = $("#id").val();
		if(id != null && $.trim(id) == '') {
			bill.addBill();
		}
	});
	// 绑定商场报数字段的函数事件
	$("#mallNumberAmount").bind("keyup", bill.changeMallNumberAmount);
	//绑定商场开票金额的函数事件
	$("#mallBillingAmount").bind("keyup", bill.changeMallBillingAmount);
	// 结算差异页签
	balanceDiffEditor = new BillShopBalanceDiffEditor();
	balanceDiffEditor.init("/bill_shop_balance_diff", {
		dataGridDivId : 'balanceDiffDataGridDiv',
		dataGridId : 'balanceDiffDataGrid',
		saveUrl : "/bill_shop_balance_diff/save",
		keyboard : true,
		afterAdd : balanceDiffEditor.afterAdd,
		afterUpdate : balanceDiffEditor.afterUpdate,
		buildAddData : balanceDiffEditor.buildAddData
	});
	// 票前费用页签
	balanceDeductEditor = new BillBalanceDeductEditor();
	balanceDeductEditor.init("/bill_shop_balance_deduct", {
		dataGridDivId : 'balanceDeductDataGridDiv',
		dataGridId : 'balanceDeductDataGrid',
		afterAdd : balanceDeductEditor.afterAdd,
		afterUpdate : balanceDeductEditor.afterUpdate,
		buildAddData : balanceDeductEditor.buildAddData
	});
	
	balanceDeductAfterEditor = new BillBalanceDeductAfterEditor();
	balanceDeductAfterEditor.init("/bill_shop_balance_deduct", {
		dataGridDivId : 'balanceDeductAfterDataGridDiv',
		dataGridId : 'balanceDeductAfterDataGrid',
		afterAdd : balanceDeductAfterEditor.afterAdd,
		afterUpdate : balanceDeductAfterEditor.afterUpdate,
		buildAddData : balanceDeductAfterEditor.buildAddData
	});
	
	// 票前费用页签
	balanceBrandEditor = new BillBalanceBrandEditor();
	balanceBrandEditor.init("/bill_shop_balance_brand", {
		dataGridDivId : 'balanceBrandDataGridDiv',
		dataGridId : 'balanceBrandDataGrid',
		afterAdd : balanceBrandEditor.afterAdd,
		afterUpdate : balanceBrandEditor.afterUpdate,
		buildAddData : balanceBrandEditor.buildAddData
	});
	
	//大类页签
	categoryEditor = new CategoryDataGridEditor();
	categoryEditor.init("/bill_shop_balance_cate_sum", {
		dataGridDivId : 'categoryDataGridDiv',
		dataGridId : 'categoryDataGrid',
		afterAdd : categoryEditor.afterAdd,
		afterUpdate : categoryEditor.afterUpdate,
		buildAddData : categoryEditor.buildAddData
	});
	//加载预警列表
	loadWarnMessageList();
	
//	var date = new Date();
//	var year = date.getFullYear();
//	var month = date.getMonth();
//	if(month == 0) {
//		year = year - 1;
//		month = 12;
//	}
//	var month = month < 10 ? '0' + month : month;
//	$('#startMonth').datebox('setValue',year + '' + month);
//	
//	$('#saleStartDate').datebox('setValue', year + '-' + month + '-01');
//	var  startMonth = year + '' + month;
//	alert(startMonth);
//	$("#startMonth").val(startMonth);	
//	var  day = new Date(year,month,0);
//	$('#saleEndDate').datebox('setValue', year + '-' + month + '-' + day.getDate()); 
	
});

function loadWarnMessageList(){
	var warnPostUrl = $("#warnPostUrl").val();
	if(warnPostUrl != null && warnPostUrl != ''){
		warnPostUrl = warnPostUrl.replaceAll(":", "&");
		if($('#shopBalanceDataGrid').length>0){
			$('#shopBalanceDataGrid').datagrid( 'options' ).url= BasePath + warnPostUrl;
			$('#shopBalanceDataGrid').datagrid( 'load' );
		}else{
			setTimeout("loadWarnMessageList()", 3000);
		}
	}
}

//加载单据列表页签，tab页签点击时触发的函数
function initBillChangeEvent(){
	// 加载单据列表页签
	$('#mainTab').tabs('add', {
		title : '单据查询',
		selected : true,
		closable : false,
		href : BasePath + "/mall_shopbalance/shopbalance_tbMain",
		onLoad : function(panel) {
			// 这里需要在重写在加载完后做对应的事件
		}
	});
	// 单据明细tab页签点击时触发的函数
	$('#dtlTab').tabs({
		onSelect : function(title) {
			var index = 0;
			if('结算差异' == title) {
				index = 1;
			} else if('票前费用' == title) {
				index = 2;
			} else if('票后费用' == title) {
				index = 6;
			} 	else if('发票信息' == title) {
				index = 3;
			} else if('收款信息' == title) {
				index = 4;
			} else if('综合店结算' == title) {
				index = 5;
			}
			loadTabData(index);
		}
	});
	$('#mainTab').tabs('hideHeader');
}

// 初始化子tab页
function initDtlTab() {
	$('#dtlTab').addTab({
		title : "发票信息",
		href : BasePath + "/mall_shopbalance/shopbalance_invoicebill"
	});
	$('#dtlTab').addTab({
		title : "收款信息",
		href : BasePath + "/mall_shopbalance/shopbalance_accountbill"
	});
	returnTab('dtlTab', '销售单查询');
}

// 加载tab页签的数据
function loadTabData(index) {
	var balanceNo = $("#balanceNo").val();
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
	var startDate = $('#balanceStartDate').val();
	var endDate = $('#balanceEndDate').val();
	// 如果点击了"新增"按钮，则清空所有tab明细的数据
	if(typeof balanceNo == 'undefined' || $.trim(balanceNo) == '') {
		if(bill.options.dtlDataGridIds) {
			$.each(bill.options.dtlDataGridIds, function(index, item) {
				$("#" + item).clearDataGrid();
			});
		}
	} else {
		if(index == 0) { // 销售单查询
			var showType = $("#showType").combobox("getValue");
			if(showType == "") {
				$("#showType").combobox("select", "1");
			}
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
		} else if(index == 1) { //结算差异
			if(hasClickTabs.diffTab) {
				return;
			}
			hasClickTabs.diffTab = true;
//			var params = "?shopNo="+shopNo+"&month="+month+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo+"&balaceDiffType=1";
			var params = "?balanceNo="+balanceNo;
			setTimeout(function(){
				$.fas.search({
					dataGridId : "balanceDiffDataGrid",
					searchUrl : "/bill_shop_balance_diff/list.json" + params,
					hasSearchForm : false,
					loadType : "load"
				});
			},300);
		} else if(index == 2) { //  票前费用
			if(hasClickTabs.deductTab) {
				return;
			}
			hasClickTabs.deductTab = true; 
//			var params = "?balanceNo=&shopNo="+shopNo+"&month="+month+"&befMonth="+month+"&startDate="+startDate+"&endDate="+endDate+"&costDeductType=1&balaceDeduType=1";
			var params = "?shopNo="+shopNo+"&month="+month+"&balanceNo="+balanceNo+"&costDeductType=1";
			setTimeout(function(){
				$.fas.search({
					dataGridId : "balanceDeductDataGrid",
					searchUrl : "/bill_shop_balance_deduct/list.json" + params,
					hasSearchForm : false,
					loadType : "load"
				});
			},300);
		} else if(index == 3) { // 发票信息
			var billApplyNo = $('#invoiceApplyNo').val();
			if(billApplyNo) {
				setTimeout(function(){
					$.fas.search({
						dataGridId : "invoiceDataGrid",
						searchUrl : "/bill_balance_invoice_info/getBillShopBanancleInfo?billNo=" + billApplyNo,
						hasSearchForm : false,
						loadType : "load"
					});
				},300);
			}
		} else if(index == 4) { // 收款信息
			var shopNo = $('#shopNo').val();
			var month = $('#month').val();
			var startDate = $('#balanceStartDate').val();
			var endDate = $('#balanceEndDate').val();
			var balanceNo = $('#balanceNo').val();	
			var url = '/bill_backsection_split_dtl/shopblanacelist.json';
			var params = "shopNo="+shopNo+"&month="+month+"&balanceNo="+balanceNo;
			setTimeout(function(){
				$.fas.search({
					dataGridId : "balanceAccountDataGrid",
					searchUrl : url + "?" + params,
					hasSearchForm : false,
					loadType : "load"
				});
			},300);
		}  else if(index == 5) { //综合店结算
			if(hasClickTabs.brandDeductTab) {
				return;
			}
			hasClickTabs.brandDeductTab = true;
//			var params = "?shopNo="+shopNo+"&month="+month+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo+"&balaceDiffType=1";
			var params = "?balanceNo="+balanceNo;
			setTimeout(function(){
				$.fas.search({
					dataGridId : "balanceBrandDataGrid",
					searchUrl : "/bill_shop_balance_brand/list.json" + params,
					hasSearchForm : false,
					loadType : "load"
				});
			},300);
		} else if(index == 6) { //  票后费用			
			if(hasClickTabs.deductafterTab) {
				return;
			}
			hasClickTabs.deductafterTab = true; 
//			var params = "?balanceNo=&shopNo="+shopNo+"&month="+month+"&befMonth="+month+"&startDate="+startDate+"&endDate="+endDate+"&costDeductType=1&balaceDeduType=1";
			var params = "?shopNo="+shopNo+"&month="+month+"&startDate="+startDate+"&endDate="+endDate+"&costDeductType=2&balanceNo=br";
			setTimeout(function(){
				$.fas.search({
					dataGridId : "balanceDeductAfterDataGrid",
					searchUrl : "/bill_shop_balance_deduct/list.json" + params,
					hasSearchForm : false,
					loadType : "load"
				});
			},300);
		} 
	}
}

// 加载结算单类型等下拉框的数据,提示信息
function loadCombobox() {
	$.fas.tooltip({
		id : 'mallDeductAmountTooltip',
		content : '扣费总额=系统收入-商场开票金额'
	});
	$.fas.tooltip({
		id : 'balanceDiffAmountTooltip',
		content : '结算差异总额=系统开票金额-商场开票'
	});
	$.fas.tooltip({
		id : 'salesDiffamountTooltip',
		content : '报数差异=系统收入-商场报数'
	});
	$.fas.tooltip({
		id : 'systemBillingAmountTooltip',
		content : '系统开票金额=系统收入-按结算差异汇总的系统扣费合计-票前费用扣费合计'
	});
	
	$.fas.tooltip({
		id : 'differenceAmountTooltip',
		content : '收款差异=商场开票金额-票后账扣实际金额-回款金额'
	});
	$.fas.tooltip({
		id : 'returnedAmountTooltip',
		content : '应返款金额 =商场开票金额-票后账扣实际金额'
	});
	
	$("#mallNumberAmount").numberbox("clear");
	$("#mallBillingAmount").numberbox("clear");
	
	$("#balanceType").initCombox({
		required : true, 
		width : 130,
		data : [{value: '1', text: '正式'},{value: '2', text: '预估'}]
	});
	
//	$("#status").initCombox({
//		required : true, 
//		width : 130,
//		data : [{value: '1', text: '制单'},{value: '2', text: '确认'},{value: '3', text: '作废'},{value: '4', text: '已开票申请'}]
//	});
	
	$("#payType").initCombox({
		required : false, 
		width : 130,
		data : [{value: 'U030301', text: '扣费店'},{value: 'U030302', text: '租金店'},{value: 'U030303', text: '不结算'}]
	});
	
	$("#showType").initCombox({
		data: [{
			value: '1',
			text: '按大类显示'
		},{
			value: '2',
			text: '按明细显示'
		},{
			value: '3',
			text: '按活动显示'
		},{
			value: '4',
			text: '按收款方式显示'
		},{
			value: '5',
			text: '按商场结算码显示'
		},{
			value: '6',
			text: '按小类开票显示'
		}],
		onChange : function(newValue) {
			var balanceNo = $("#balanceNo").val();
			if(newValue == '1') {
				$('#balancedtl_panel').panel('close');
				$('#promotions_panel').panel('close');
				$('#paymentmethod_panel').panel('close');
				$('#category_panel').panel('open');
				if(balanceNo && $.trim(balanceNo) != '') {
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
			} else if(newValue == '2') {
				$('#category_panel').panel('close');
				$('#promotions_panel').panel('close');
				$('#paymentmethod_panel').panel('close');
				$('#balancedtl_panel').panel('open');
				// 需修改
				$('#balancedtl_panel').panel('resize',{
					width: function () {
	                    return document.body.clientWidth;
	                },
					height: function () {
	                    return document.body.clientHeight;
	                }
				});
				if(balanceNo && $.trim(balanceNo) != '') {
					var shopNo = $('#shopNo').val();
					var startDate = $('#balanceStartDate').val();
					var endDate = $('#balanceEndDate').val();
					var balanceNo = $('#balanceNo').val();
					var params = "?shopNos="+shopNo+"&startOutDate="+startDate+"&endOutDate="+endDate+"&balanceNo="+balanceNo;	
					var url = '/mall_shopsorderd/list.json' + params;
					$.fas.search({
						dataGridId : "balanceDtlDataGrid",
						searchUrl : url,
						hasSearchForm : false,
						loadType : "load"
					});
				}
			} else if(newValue == '3') {
				$('#category_panel').panel('close');
				$('#balancedtl_panel').panel('close');
				$('#paymentmethod_panel').panel('close');
				$('#promotions_panel').panel('open');
				// 需修改
				$('#promotions_panel').panel('resize',{
					width: function () {
	                    return document.body.clientWidth;
	                },
					height: function () {
	                    return document.body.clientHeight;
	                }
				});
				if(balanceNo && $.trim(balanceNo) != '') {
					var shopNo = $('#shopNo').val();
					var startDate = $('#balanceStartDate').val();
					var endDate = $('#balanceEndDate').val();
					var balanceNo = $('#balanceNo').val();	
					var params = "?shopNo="+shopNo+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo;	
					var url = '/bill_shop_balance_pro_sum/list.json' + params;
					$.fas.search({
						dataGridId : "promotionsDataGrid",
						searchUrl : url,
						hasSearchForm : false,
						loadType : "load"
					});
				}
			} else if(newValue == '4') {
				$('#category_panel').panel('close');
				$('#balancedtl_panel').panel('close');
				$('#promotions_panel').panel('close');
				$('#paymentmethod_panel').panel('open');
				// 需修改
				$('#paymentmethod_panel').panel('resize',{
					width: function () {
	                    return document.body.clientWidth;
	                },
					height: function () {
	                    return document.body.clientHeight;
	                }
				});
				if(balanceNo && $.trim(balanceNo) != '') {
					var shopNo = $('#shopNo').val();
					var startDate = $('#balanceStartDate').val();
					var endDate = $('#balanceEndDate').val();
					var balanceNo = $('#balanceNo').val();	
					var params = "?shopNo="+shopNo+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo;	
					var url = '/mall_shopbalance/getPaymentMethodSum' + params;
					$.fas.search({
						dataGridId : "paymentMethodDataGrid",
						searchUrl : url,
						hasSearchForm : false,
						loadType : "load"
					});
				}
			} else if(newValue == '5') {
				$('#category_panel').panel('close');
				$('#balancedtl_panel').panel('close');
				$('#promotions_panel').panel('close');
				$('#paymentmethod_panel').panel('close');
				$('#billingcode_panel').panel('open');
				// 需修改
				$('#billingcode_panel').panel('resize',{
					width: function () {
	                    return document.body.clientWidth;
	                },
					height: function () {
	                    return document.body.clientHeight;
	                }
				});
				if(balanceNo && $.trim(balanceNo) != '') {
					var shopNo = $('#shopNo').val();
					var startDate = $('#balanceStartDate').val();
					var endDate = $('#balanceEndDate').val();
					var balanceNo = $('#balanceNo').val();	
					var params = "?shopNo="+shopNo+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo;	
					var url = '/bill_shop_balance_code_sum/list.json' + params;
					$.fas.search({
						dataGridId : "billingCodeDataGrid",
						searchUrl : url,
						hasSearchForm : false,
						loadType : "load"
					});
				}
			}else if(newValue == '6') {
				$('#category_panel').panel('close');
				$('#promotions_panel').panel('close');
				$('#paymentmethod_panel').panel('close');
				$('#balancedtl_panel').panel('close'); 
				$('#billingcode_panel').panel('close');
				$('#category3_panel').panel('open');
				// 需修改
				$('#category3_panel').panel('resize',{
					width: function () {
	                    return document.body.clientWidth;
	                },
					height: function () {
	                    return document.body.clientHeight;
	                }
				});
				if(balanceNo && $.trim(balanceNo) != '') {
					var shopNo = $('#shopNo').val();
					var startDate = $('#balanceStartDate').val();
					var endDate = $('#balanceEndDate').val();
					var balanceNo = $('#balanceNo').val();
					var params = "?shopNos="+shopNo+"&startOutDate="+startDate+"&endOutDate="+endDate+"&balanceNo="+balanceNo;	
					var url = '/bill_shop_balance_cate_sum/salesCategory3list.json' + params;
					$.fas.search({
						dataGridId : "category3DataGrid",
						searchUrl : url,
						hasSearchForm : false,
						loadType : "load"
					});
				}
			}
		}
	});
}

//门店结算单打印
function printBalanceOrder() {
	var shopNo = $('#shopNo').val();
	var startDate = $('#balanceStartDate').val();
	var endDate = $('#balanceEndDate').val();
	var balanceNo = $('#balanceNo').val();	
	if(balanceNo == ""){
		return;
	}
	var dgt=$("#categoryDataGrid").datagrid("getRows");
	if(dgt.length<=0){
		alert("列表数据为空!");
		return;
	}
	var printCol = [{
		text : "默认_A4纸",
		value : "Default-A4",
		label : 1
	},{
		text : "默认_A5纸",
		value : "Default-A5",
		label : 2
	},{
		text : "华北模板",
		value : "HB",
		label : 3
	}];
	 //初始化框架数据
	$('#printHBSelect').empty();
	$.each(printCol,function(index,item){
		$('#printHBForm #printHBSelect').append("<option value="+item.value+">"+item.text+"</option>");
	});
	ygDialog({
		title : '门店结算单打印',
		target : $('#printHBContrPanel'),
		width : 225,
		height : 300,
		buttons : [{
					text : '打印',
					iconCls : 'icon-print',
					handler : function(dialog) {
						var selected = $('#printHBSelect').val();
						if(selected =="Default-A4"){
							var params = "?shopNo="+shopNo+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo+"&costDeductType=1";	
							var winOpts = {
									'dataGridId' : "categoryDataGrid",
									'sencondDateGrid' :"promotionsDataGrid",
									'downDateGrid' :"balanceBrandDataGrid",
									'intOrient' : 0, 	// 0：打印机缺省设置,1：纵向，2：横向
									'type' : 0, 		// 0：简单打印，1：动态打印
									'needParams' : 0,	// 0需要查询参数，1不需要带查询参数
									'queryDataUrl' : '/bill_shop_balance_cate_sum/list.json'+"?rows=50",
									'selectProSum' : '/bill_shop_balance_pro_sum/list.json'+"?rows=100",
									'findDiffUrl' : '/bill_shop_balance_deduct/list.json'+"?rows=100",
									'title' : "门店结算单",
									'viewName' : "shop_balance_print",
									'params' : params
							};
							printDialog(winOpts);
						}
						if(selected =="Default-A5"){
							var params = "?shopNo="+shopNo+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo+"&costDeductType=1";	
							var winOpts = {
									'dataGridId' : "categoryDataGrid",
									'sencondDateGrid' :"promotionsDataGrid",
									'downDateGrid' :"balanceBrandDataGrid",
									'intOrient' : 0, 	// 0：打印机缺省设置,1：纵向，2：横向
									'type' : 0, 		// 0：简单打印，1：动态打印
									'needParams' : 0,	// 0需要查询参数，1不需要带查询参数
									'queryDataUrl' : '/bill_shop_balance_cate_sum/list.json'+"?rows=50",
									'selectProSum' : '/bill_shop_balance_pro_sum/list.json'+"?rows=100",
									'findDiffUrl' : '/bill_shop_balance_deduct/list.json'+"?rows=100",
									'title' : "门店结算单",
									'viewName' : "shop_balance_print_A5",
									'params' : params
							};
							printDialog(winOpts);
						}
						//华北模板导出
						if(selected =="HB"){
							var params = "?shopNo="+shopNo+"&startDate="+startDate+"&endDate="+endDate+"&balanceNo="+balanceNo;	
							var winOpts = {
									'dataGridId' : "balanceDiffDataGrid",
									'sencondDateGrid' :"balanceDeductDataGrid",
									'downDateGrid' :"balanceDeductAfterDataGrid",
									'intOrient' : 0, 	// 0：打印机缺省设置,1：纵向，2：横向
									'type' : 0, 		// 0：简单打印，1：动态打印
									'needParams' : 0,	// 0需要查询参数，1不需要带查询参数
									'queryDataUrl' : '/bill_shop_balance_diff/list.json'+"?rows=50",
									'selectProSum' : '/bill_shop_balance_deduct/list.json'+"?costDeductType=1&rows=100",
									'findDiffUrl' : '/bill_shop_balance_deduct/list.json'+"?costDeductType=2&rows=100",
									'title' : "门店结算单",
									'viewName' : "shop_balance_print_hb",
									'params' : params
							};
							printDSDialog(winOpts); 
						}
					}
		}]
		});
}

//清空文本方法
function clearArrayText(clearTextArray,editIndex,datagridName){
	for(var i=0;i<clearTextArray.length;i++){
		var fieldText=clearTextArray[i].text;
		var type=clearTextArray[i].type;
		$.fas.setEditorVal({
			dataGridId : datagridName,
			rowIndex : editIndex,
			field : fieldText,
			value : '',
			dataType : type
		});
	}
}

//光标锁定到指定文本框
function focusEditor(target,field,editIndex){
	var editor = $(target).datagrid('getEditor', {index:editIndex,field:field});
	if (editor){
		editor.target.focus();
	} else {
		var editors = $(target).datagrid('getEditors', editIndex);
		if (editors.length){
			editors[0].target.focus();
		}
	}
}