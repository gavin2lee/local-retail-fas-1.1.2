var shopGroup = new Object();

shopGroup.clear = function(){
	$('#searchForm').form('clear');
	$('#companyNoCondition').val('');
	$('#shopNo').val('');
}

shopGroup.search = function(){
	var istrue=$('#searchForm').form('validate');
	if(istrue == false){
		return false;
	}
	var url = BasePath + '/shop_group/list.json';
	var params = $('#searchForm').form('getData');
	/*params.needFilter = "yes";*/
	$('#mainDataGrid').datagrid( 'options' ).queryParams= params;
	$('#mainDataGrid').datagrid( 'options' ).url= url;
	$('#mainDataGrid').datagrid( 'load' );
}

shopGroup.del = function(){
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	if(checkedRows.length > 0){
		var errMessage = "";
		$.each(checkedRows,function(index,item){
			if(item.status == 1){
				errMessage = "存在已启用的记录,不允许删除!"
				return false;
			}
		});
		if(errMessage){
			showInfo(errMessage);
			return ;
		}
		$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
			if (r) {
				var url =  BasePath + '/shop_group/del';;
				ajaxRequestAsync(url,{checkedRows:JSON.stringify(checkedRows)},function(count){
					if(count > 0){
						showSuc('删除成功');
						shopGroup.search();
					}else{
						showError('删除失败');
					}
					
				});
			}
		});
	}else{
		showInfo("请选中需要删除的记录?");
	}
}

shopGroup.updateDate = function(){
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	var url=BasePath + '/shop_group/operate';
	if(checkedRows.length > 0){
		var errMessage = "";
		$.each(checkedRows,function(index,item){
			if(item.status == 1){
				errMessage = "存在已启用的记录,不允许修改!"
				return false;
			}
		});
		if(errMessage){
			showInfo(errMessage);
			return ;
		}
		$('#genarateDataForm').form('clear');
		ygDialog({
			title : '生效日期信息',
			target : $('#genarateFormPanel'),
			width :  300,
			height : 200,
			buttons : [
			{
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					var istrue=$('#genarateDataForm').form('validate');
					if(istrue == false){
						return false;
					}
					var date=$("#valueDate_").val();
					$.each(checkedRows,function(index,item){
						item.valueDate = date;
					});
					ajaxRequestAsync(url,{checkedRows:JSON.stringify(checkedRows)},function(count){
						if(count > 0){
							showSuc('操作成功');
							shopGroup.search();
						}else{
							showError('操作失败');
						}
					});
					dialog.close();
				}
			},{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			}]
		});
	}else{
		showInfo("请选中需要操作的记录?");
	}
	
}

shopGroup.operate = function(status){
	var checkedRows = $('#mainDataGrid').datagrid('getChecked');
	if(checkedRows.length > 0){
		var message = "你确定要停用选中的单据?";
		if(status == 1){
			 message = "你确定要启用选中的单据?";
		}
		$.each(checkedRows,function(index,item){
			item.status = status;
		});
		$.messager.confirm("确认",message,function(r) {
			if (r) {
				var url =  BasePath + '/shop_group/operate';
				ajaxRequestAsync(url,{checkedRows:JSON.stringify(checkedRows)},function(count){
					if(count > 0){
						showSuc('操作成功');
						shopGroup.search();
					}else{
						showError('操作失败');
					}
					
				});
			}
		});
	}else{
		showInfo("请选中需要操作的记录?");
	}
}

shopGroup.showDialog = function(rowData){
	if(!rowData){
		// 新增
		$('#dataForm').form('clear');
		$('#id,#companyNo_').val('');
		$("#valueDate").datebox('setValue',new Date().format("yyyy-MM-dd"));
		deleteAllGridCommon('dtlDataGrid');
		$('#dtlDataGrid').datagrid('acceptChanges');
		//shopGroupName原始值
		var tempShopGroupName = $("#shopGroupName").val();
		ygDialog({
			title : '店铺分组信息',
			target : $('#myFormPanel'),
			width :  800,
			height : 500,
			buttons : [
			{
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					if(shopGroup.validate()){
						//店铺分组名称改变时检查数据唯一性
						var newShopGroupName = $("#shopGroupName").val();
						if(newShopGroupName != tempShopGroupName){
							var check_data = {"shopGroupNameUnique" : newShopGroupName};
							var checkUrl = BasePath + "/shop_group/get_count.json";
							if (shopGroup.checkExistFun(checkUrl, check_data)) {
								showError('店铺分组名称已存在,不能重复!');
								return false;
							}
						}
						shopGroup.save();
						dialog.close();
					}
				}
			},{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			}]
		});
	}else{
		// 修改
		if(rowData.status == 1){
//			showInfo("已启用,不允许修改!");
//			return ;
			$('#dataFormView').form('load',rowData);
			var url = BasePath + '/shop_group_dtl/list.json';
			$('#dtlDataGridView').datagrid( 'options' ).queryParams= {shopGroupNo:rowData.shopGroupNo};
			$('#dtlDataGridView').datagrid( 'options' ).url= url;
			$('#dtlDataGridView').datagrid( 'load' );
			$("#companyNameOne").combogrid("disable");
			$("#dataFormView input").addClass("readonly").attr("readonly", true);
			$("#templateName").iptSearch("disable");
			$("#clientName").iptSearch("disable");
			
		 	var linkbuttons = $("#dtltoolbarView").find("a");
	    	for(var i = 0; i < linkbuttons.length; i++) {
	    		$(linkbuttons[i]).linkbutton('disable');
	    	}
			var tempShopGroupName = $("#shopGroupName").val();
			ygDialog({
				title : '店铺分组信息',
				target : $('#myFormPanelView'),
				width :  800,
				height : 500,
				buttons : [
				{
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function(dialog) {
						dialog.close();
					}
			}]
		});
	}else{
		$("#dataForm input").addClass("readonly").attr("readonly", false);
		$('#dataForm').form('load',rowData);
		$("#companyName_").combogrid("enable");
		$("#shopGroupNo").addClass("readonly").attr("readonly", true);
//		$("#disabled").attr("disabled", "false");
		$("#invoiceName").addClass("readonly").attr("readonly", true);
		$("#templateName").iptSearch("enable");
		$("#clientName").iptSearch("enable");
		var url = BasePath + '/shop_group_dtl/list.json';
		$('#dtlDataGrid').datagrid( 'options' ).queryParams= {shopGroupNo:rowData.shopGroupNo};
		$('#dtlDataGrid').datagrid( 'options' ).url= url;
		$('#dtlDataGrid').datagrid( 'load' );
		//shopGroupName原始值
		var tempShopGroupName = $("#shopGroupName").val();
		ygDialog({
			title : '店铺分组信息',
			target : $('#myFormPanel'),
			width :  800,
			height : 500,
			buttons : [
			{
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					if(shopGroup.validate()){
						//店铺分组名称改变时检查数据唯一性
						var newShopGroupName = $("#shopGroupName").val();
						if(newShopGroupName != tempShopGroupName){
							var check_data = {"shopGroupNameUnique" : newShopGroupName};
							var checkUrl = BasePath + "/shop_group/get_count.json";
							if (shopGroup.checkExistFun(checkUrl, check_data)) {
								showError('店铺分组名称已存在,不能重复!');
								return false;
							}
						}
						shopGroup.save();
						dialog.close();
					}
				}
			},{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			}]
		});
	}
	}
	

};

shopGroup.save = function(){
	var pkValue = $('#id').val();
	var url = BasePath + '/shop_group/add';
	if(pkValue != ''){
		url = BasePath + '/shop_group/update';
	}
	$('#dataForm').form('submit',{
		url : url,
		dataType : 'json',
		onSubmit:function(param){
			param.createUser = currentUser.username;
			param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			param.insertedRows = JSON.stringify($('#dtlDataGrid').datagrid('getChanges','inserted'));
			param.updatedRows = JSON.stringify($('#dtlDataGrid').datagrid('getChanges','updated'));
			param.deletedRows = JSON.stringify($('#dtlDataGrid').datagrid('getChanges','deleted'));
		},
		success:function(result){
			var data=eval('('+result+')');
			if (!isNotBlank(data.shopName)) {
				showSuc('保存成功');
				shopGroup.search();
			} else {
				showError(data.shopName);
			}
		},
		error:function(){
			showError('系统异常!');
		}
	});
}

function buildSubgridUrl(row){
	return "?shopGroupNo="+row.shopGroupNo;
}

shopGroup.validate = function(){
	if($('#dataForm').form('validate')){
		shopGroup.endEdit();
		return true;
	}
	return false;
}

function exportExcelBasic(row,dgtColumn){
	$("#exportExcelForm").remove();
    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	$('#exportExcelForm').form('submit', {
        url : BasePath + "/shop_group/export",
        onSubmit : function(params) {
        	params.companyNo=$("#companyNoCondition").val();
        	params.shopGroupNo=$("#shopGroupNo").val();
        	params.shopGroupName=$("#shopGroupName").val();
        	params.status=$("#status").val();
        	params.shopNo=$("#shopNo").val();
        	params.exportColumns = JSON.stringify(dgtColumn);
        	params.fileName = "店铺开票规则导出";
        	params.dataList = JSON.stringify(row);
        	params.needFilter = "";
        },
        success : function() {

        }
    });
}

shopGroup.exportExcel = function(){
	var row = $('#mainDataGrid').datagrid('getRows');
	if(row.length<1){
		alert("查询记录为空，不能导出!");
		return; 
	}
	var dgtColumn=[
	                 {field : 'shopGroupNo', title : '店铺分组编号', width : 100},
                     {field : 'shopGroupName', title : '店铺分组名称', width : 150},
                     {field : 'companyName', title : '公司名称', width : 200},
                     {field : 'clientName', title : '客户名称', width : 150},
                     {field : 'invoiceName', title : '开票名称', width : 150},
                     {field : 'templateName', title : '发票模板名称', width : 150},
	 				 {field : 'valueDate',title:'生效日期',width:80},
	 				 {field : 'statusStr',title:'状态',width:80},
	 				 {field:'shopNo',title:'店铺编号',width:120},
					 {field:'shopName',title:'店铺名称',width:300}
	 			];
	exportExcelBasic(row,dgtColumn);
}

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if ('0' == obj) {
			return true;
		}
		return false;
	}
	return true;
};

shopGroup.importExcel = function(){
	$.importExcel.open({
		'submitUrl' : BasePath + '/shop_group/do_import',
		'templateName' : '店铺开票规则导入.xlsx',
		success : function(data) {
			$.messager.progress('close');
			if (data) {
				if (isNotBlank(data.error)) {
					showError(data.error);
				} else {
					$.importExcel.colse();
					showSuc('数据导入成功');
					shopGroup.search();
				}
			} else {
				showInfo('导入失败,请联系管理员!');
			}
		},
		error : function() {
			$.messager.progress('close');
			showWarn('数据导入失败，请联系管理员');
		}
	});

}

shopGroup.addDtl = function(){
	if($('#companyNo_').val()==''){
		showInfo("请先选择结算公司!");
		return ;
	}
	shopGroup.endEdit();
	$('#dtlDataGrid').datagrid('appendRow',{});
    var length = $('#dtlDataGrid').datagrid('getRows').length;
    shopGroup.beginEdit(length-1);
}

shopGroup.editDtl = function(rowIndex){
	shopGroup.endEdit();
	shopGroup.beginEdit(rowIndex);
}

shopGroup.delDtl = function(){
	var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
	if(checkedRows.length > 0){
		var rowIndex ;
		$.each(checkedRows,function(index,item){
			rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',item);
			$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
		});
	}else{
		showInfo("请选中需要删除的记录?");
	}
}

shopGroup.beginEdit = function(rowIndex){
	 $('#dtlDataGrid').datagrid('beginEdit',rowIndex);
}

shopGroup.endEdit = function(){
	var editRow = $("tr[class*=datagrid-row-editing]");
	if(editRow.length > 0){
		var editIndex = editRow.attr("datagrid-row-index");
		if($('#dtlDataGrid').datagrid('validateRow', editIndex)){
			$('#dtlDataGrid').datagrid('endEdit',editIndex);
		}
	}
	return true;
}

shopGroup.selectShop = {
    clickFn:function(){
    	if($('#companyNo_').val()==''){
    		showInfo("请先选择结算公司!");
    		return ;
    	}
    	//查询时过滤掉已增加的店铺，不显示在结果里
    	var queryCondition = '';
    	var existShops = $('#dtlDataGrid').datagrid('getRows');
    	var shopNo_set = '';
    	$.each(existShops,function(index,row){
    		shopNo_set = shopNo_set + "'" + row.shopNo + "',";
		});
    	if(shopNo_set.length > 0){
    		shopNo_set = shopNo_set.substring(0,shopNo_set.length-1);
    		queryCondition = "AND shop_no NOT IN("+ shopNo_set +")";
    	}
		ygDialog({
			title : '选择店铺',
			width : 650,
    		height : 'auto',
    		href : BasePath + "/common_util/doSelect?multiSelect=true&selectUrl=getCompToShopAndOrgan&companyNo=" + $('#companyNo_').val() + "&queryCondition=" + queryCondition,
			isFrame : true,
			modal : true,
			showMask : true,
			buttons: [{
				id:'sure',
	            text: '确认',
	            handler: function(dialog) {
	            	var checkedRows = shopGroup.getRowData();
	            	var rows = $('#dtlDataGrid').datagrid('getRows');
	            	if(typeof checkedRows == 'undefined'){
	            		checkedRows = shopGroup.getCheckRows();
	            		$.each(checkedRows,function(index, item){
	            			var flag = true;
	            			$.each(rows,function(index,row){
	            				if(row.shopNo == item.code){
	            					flag = false;
	            					return false;
	            				}
	            			});
	            			if(flag){
								$('#dtlDataGrid').datagrid('insertRow',{
									index : 0 ,
									row : {
										shopNo:item.code,
										shopName:item.name
									}
								});
	            			}
						});
	            	}else{
            			var flag = true;
            			$.each(rows,function(index,row){
            				if(row.shopNo == checkedRows.code){
            					flag = false;
            					return false;
            				}
            			});
            			if(flag){
    	            		$('#dtlDataGrid').datagrid('insertRow',{
    							index : 0 ,
    							row : {
    								shopNo:checkedRows.code,
    								shopName:checkedRows.name
    							}
    						});	
            			}
	            	}
	            	dialog.close();
	            }
	        },
	        {
	            text: '取消',
	            handler: function(dialog) {
	                dialog.close();
	            }
	        }],
			onLoad : function(win, content) {
				shopGroup.getCheckRows = content.getCheckRows;
				shopGroup.getRowData = content.getRowData;
			}
		});
    }
}

shopGroup.statusformatter = function(value, rowData, rowIndex) {
    var dataStatusType = [{'value':'0', 'text': '备选'}, {'value':'1', 'text':'首选'}];
    for(var i = 0; i < dataStatusType.length; i++) {
        if(dataStatusType[i].value == value) {
            return dataStatusType[i].text;
        }
    }
    return "";
};

/**
 * 初始化选择源货币
 */
shopGroup.initInvoiceTemplate = function() {
	$("#templateName").iptSearch({	
		disabled : false,
		clickFn : function() {
			if($('#companyNo_').val()==''){
	    		showInfo("请先选择结算公司!");
	    		return ;
	    	}
			dgSelector({
				title:"选择开票模板",
				href : BasePath + "/shop_group/toSelectTemplate",
				queryUrl : BasePath + "/invoice_template_set/list.json?companyNo=" + $('#companyNo_').val(),
				isFrame : false,
				width : 500,
				height : 'auto',
				fn: function(data){
					$('#templateName').val(data.name);
					$('#templateNo').val(data.invoiceTempNo);
				}
			});
		}
	});
};

shopGroup.callbackCompany = function(row) {
	$('#clientName').val("");
	$('#clientNo').val("");
	$('#invoiceName').val("");
	$('#templateName').val("");
	$('#templateNo').val("");
};

/**
 * 初始化选择源货币
 */
shopGroup.initShopGroupClient = function() {
	$("#clientName").iptSearch({
		disabled : false,
		clickFn : function() {
			var companyName = $("#companyName_").combobox("getValue");
			if(!companyName) {
//				$("#clientName").val('');
//				$("#clientNo").val('');
				showWarn("请先选择选择公司！");
				return;
			}
			dgSelector({
				title:"选择客户",
				href : BasePath + "/shop_group/toSelectClient",
				queryUrl : BasePath + "/base_setting/invoice_info_set/list.json?sort=client_no,status&&order=asc&&companyNo=" + $('#companyNo_').val(),
				isFrame : false,
				width : 600,
				height : 'auto',
				fn: function(data){
					$('#clientName').val(data.clientName);
					$('#clientNo').val(data.clientNo);
					$('#status').val(data.status);
					 $('#invoiceName').val(data.invoiceName);
					//根据公司和客户在开票信息维护中查找开票名称
//					var invoiceParam = {companyNo : $('#companyNo_').val(),clientNo : $('#clientNo').val(),status : $('#status').val()};
//					$.ajax({
//						  type: 'POST',
//						  url: BasePath + "/shop_group/get_invoice_name.json",
//						  data: invoiceParam,
//						  cache: true,
//						  async:false, // 一定要
//						  dataType: 'json', 
//						  success: function(result){
//							  if(result){
//								  $('#invoiceName').val(result.invoiceName);
//							  }
//						  }
//					});
				}
			});
		}
	});
};

//检查是否唯一
shopGroup.checkExistFun = function(url,checkColumnJsonData) {
	var checkExist=false;
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkColumnJsonData,
		  cache: true,
		  async:false, // 一定要
		  dataType: 'text', 
		  success: function(totalData){
			  totalData = parseInt(totalData,10);
			  if(totalData>0){
				  checkExist=true;
			  }
		  }
     });
 	return checkExist;
}

// 初始化
$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	shopGroup.clear();
	shopGroup.initInvoiceTemplate();
	shopGroup.initShopGroupClient();
	
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
	
});
