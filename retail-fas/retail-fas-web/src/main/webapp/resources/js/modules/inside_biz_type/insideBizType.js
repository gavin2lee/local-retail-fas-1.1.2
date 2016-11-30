var inside_biz_type = {};
//行编辑
function insideBizTypeEditor() {}
function insideBizTypeDialog() {}

//初始化
var dialog = null, editor = null;
$(function() {
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	$.fas.extend(insideBizTypeDialog, FasDialogController);
	$.fas.extend(insideBizTypeEditor, FasEditorController);
	dialog = new insideBizTypeDialog();
	dialog.init("/inside_biz_type", {
			  searchUrl:"/list.json?sort=create_time&&order=desc",
			  searchBtn : "btn-search",
			  enableUrl:"/do_enable",
			  unableUrl:"/do_unable",
			  dataGridId:"dataGridJG", 
			  searchFormId:"mainDataForm",
			  primaryKey:"id"
	});
	
	editor = new insideBizTypeEditor();
	editor.init("/inside_biz_type_detail", {
		dataGridDivId : 'clientDtlDataGridDiv',
		dataGridId : "clientDtlDataGrid",
		initRow : function(){
			return {dtlType : 2};
		},
	});
	
	$('#mainTab').tabs('add', {
		title : '客户明细',
		selected : true,
		closable : false,
		href : BasePath + "/inside_biz_type_detail/to_client_list",
		onLoad : function(panel) {
			//加载预警列表
			setTimeout(3000);
		}
	});
	
	$('#viewMainTab').tabs('add', {
		title : '客户明细',
		selected : true,
		closable : false,
		href : BasePath + "/inside_biz_type_detail/to_client_list_view",
		onLoad : function(panel) {
			//加载预警列表
			setTimeout(6000);
		}
	});
	$("#mainTab").tabs("select","店铺明细") ;
	$("#viewMainTab").tabs("select","店铺明细") ;
});

//弹出新增，修改，查看页面
insideBizTypeEditor.showDialog = function(rowData){
	if(!rowData){
		$("#mainTab").tabs("select","店铺明细") ;
		// 新增
		$('#dataForm').form('clear');
		$('#id,#companyNoId').val('');
		deleteAllGridCommon('dtlDataGrid');
		$('#dtlDataGrid').datagrid('acceptChanges');
		$("#companyNameId").combogrid("enable");
		$('#clientDtlDataGrid').datagrid('loadData', { total: 0, rows: [] });
		ygDialog({
			title : '新增',
			target : $('#myFormPanel'),
			width :  540,
			height : 500,
			buttons : [
			{
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					if(extra_operate.checkSave()){
						if(insideBizTypeEditor.validate()) {
							insideBizTypeEditor.save();
							dialog.close();
						}
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
		if(rowData.status == 1){//查看
			$("#viewMainTab").tabs("select","店铺明细") ;
			$('#dataFormView').form('load',rowData);
			var url1 = BasePath + '/inside_biz_type_detail/list.json';
			$('#dtlDataGridView').datagrid( 'options' ).queryParams= {typeId:rowData.id,dtlType:1};
			$('#dtlDataGridView').datagrid( 'options' ).url= url1;
			$('#dtlDataGridView').datagrid( 'load' );
			var url = '/inside_biz_type_detail/client_list?typeId='+rowData.id+'&dtlType='+2;
			$.fas.search({
				hasSearchForm : false,
				searchUrl : url,
				dataGridId : "clientDtlViewDataGrid"
			});
			$("#companyNameOne").combogrid("disable");
			$("#dataFormView input").addClass("readonly").attr("readonly", true);
			ygDialog({
				title : '新增',
				target : $('#myFormPanelView'),
				width :  540,
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
		}else{// 修改
			$("#mainTab").tabs("select","店铺明细") ;
			$("#dataForm input").addClass("readonly").attr("readonly", false);
			$("#dataForm").form('load',rowData);
			extra_operate.loadedUpdate(rowData);
			$("#companyNameId").combogrid("disable");
			var url1 = BasePath + '/inside_biz_type_detail/list.json';
			$('#dtlDataGrid').datagrid( 'options' ).queryParams= {typeId:rowData.id,dtlType:1};
			$('#dtlDataGrid').datagrid( 'options' ).url= url1;
			$('#dtlDataGrid').datagrid( 'load' );
			var url = BasePath + '/inside_biz_type_detail/client_list';
			$('#clientDtlDataGrid').datagrid( 'options' ).queryParams= {typeId:rowData.id,dtlType:2};
			$('#clientDtlDataGrid').datagrid( 'options' ).url= url;
			$('#clientDtlDataGrid').datagrid( 'load' );
			ygDialog({
				title : '新增',
				target : $('#myFormPanel'),
				width :  540,
				height : 500,
				buttons : [
				{
					text : '保存',
					iconCls : 'icon-save',
					handler : function(dialog) {
						if(extra_operate.checkUpdate()) {
							if(insideBizTypeEditor.validate()) {
								insideBizTypeEditor.update();
								dialog.close();
							}
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

insideBizTypeEditor.validate = function(){
	if($('#dataForm').form('validate')){
		insideBizTypeEditor.endEdit();
		return true;
	}
	return false;
};

insideBizTypeEditor.endEdit = function(){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#clientDtlDataGrid').datagrid('validateRow',editRowIndex)){
			  $('#clientDtlDataGrid').datagrid('endEdit',editRowIndex);
			  return true;
		}
		return false;
	}
	return true;
},

//保存表头并保存店铺、客户明细
insideBizTypeEditor.save = function(){
	var url = BasePath + '/inside_biz_type/add';
	$('#dataForm').form('submit',{
		url : url,
		dataType : 'json',
		onSubmit:function(param){
			param.createUser = currentUser.username;
			param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			param.insertedRows = JSON.stringify($('#dtlDataGrid').datagrid('getChanges','inserted'));
			param.insertedClientRows = JSON.stringify($('#clientDtlDataGrid').datagrid('getChanges','inserted'));
		},
		success:function(result){
			var data=eval('('+result+')');
			if (data.billNo=="success") {
				showSuc('保存成功');
				dialog.search();
			} else {
				showError("保存失败");
			}
		},
		error:function(){
			showError('系统异常!');
		}
	});
};

//修改表头并修改明细
insideBizTypeEditor.update = function(){
	var url = BasePath + '/inside_biz_type/updateKey';
	$('#dataForm').form('submit',{
		url : url,
		dataType : 'json',
		onSubmit:function(param){
			param.updateUser = currentUser.username;
			param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			param.insertedRows = JSON.stringify($('#dtlDataGrid').datagrid('getChanges','inserted'));
			param.updatedRows = JSON.stringify($('#dtlDataGrid').datagrid('getChanges','updated'));
			param.deletedRows = JSON.stringify($('#dtlDataGrid').datagrid('getChanges','deleted'));
			param.clientInsRows = JSON.stringify($('#clientDtlDataGrid').datagrid('getChanges','inserted'));
			param.clientUpRows = JSON.stringify($('#clientDtlDataGrid').datagrid('getChanges','updated'));
			param.clientDelRows = JSON.stringify($('#clientDtlDataGrid').datagrid('getChanges','deleted'));
		},
		success:function(result){
			var data=eval('('+result+')');
			if (data.billNo=="success") {
				showSuc('修改成功');
				dialog.search();
			} else {
				showError("修改失败");
			}
		},
		error:function(){
			showError('系统异常!');
		}
	});
};

insideBizTypeEditor.del = function(){
	var checkedRows = $('#dataGridJG').datagrid('getChecked');
	if(checkedRows.length > 0){
		var errMessage = "";
		$.each(checkedRows,function(index,item){
			if(item.status == 1){
				errMessage = "存在已启用的记录,不允许删除!";
				return false;
			}
		});
		if(errMessage){
			showInfo(errMessage);
			return ;
		}
		$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
			if (r) {
				var url =  BasePath + '/inside_biz_type/del';
				ajaxRequestAsync(url,{checkedRows:JSON.stringify(checkedRows)},function(count){
					if(count > 0){
						showSuc('删除成功');
						dialog.search();
					}else{
						showError('删除失败');
					}
					
				});
			}
		});
	}else{
		showInfo("请选中需要删除的记录?");
	}
};

function buildSubgridUrl(row){
	return "?typeId="+row.id;
}

//店铺、客户明细新增、删除操作处理
insideBizTypeDetailEditor = {
	    clickFn:function(){// 新增店铺，弹出店铺选择页面
	    	if($('#companyNoId').val()==''){
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
	    		href : BasePath + "/common_util/doSelect?multiSelect=true&selectUrl=getCompToShopAndOrgan&companyNo=" + $('#companyNoId').val() + "&queryCondition=" + queryCondition,
				isFrame : true,
				modal : true,
				showMask : true,
				buttons: [{
					id:'sure',
		            text: '确认',
		            handler: function(dialog) {
		            	var checkedRows = insideBizTypeEditor.getRowData();
		            	var rows = $('#dtlDataGrid').datagrid('getRows');
		            	if(typeof checkedRows == 'undefined'){
		            		checkedRows = insideBizTypeEditor.getCheckRows();
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
					insideBizTypeEditor.getCheckRows = content.getCheckRows;
					insideBizTypeEditor.getRowData = content.getRowData;
				}
			});
	    },
	    delDtl:function(){//删除店铺
	    	var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
	    	if(checkedRows.length > 0){
	    		$.each(checkedRows,function(index,item){
	    			var rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',item);
	    			$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
	    		});
	    	}else{
	    		showInfo("请选中需要删除的记录?");
	    	}
	    },
	    
	   //新增客户明细行
	   clientAdd: function() {
	    	if(insideBizTypeEditor.endEdit()){
	    	    $('#clientDtlDataGrid').datagrid('appendRow',{});
	    	    var length = $('#clientDtlDataGrid').datagrid('getRows').length;
	    		$('#clientDtlDataGrid').datagrid('beginEdit',length-1);
	    	}
	    },

	    //修改客户明细行
	    clientEdit: function(rowIndex) {
	    	if(insideBizTypeEditor.endEdit()){
	    		$('#clientDtlDataGrid').datagrid('beginEdit',rowIndex);
	    	}
	    },

	    //删除客户明细行
	    clientDel : function() {
	        var checkedRows = $('#clientDtlDataGrid').datagrid('getChecked');
	        var rowIndex;
	        if(checkedRows.length > 0){
	            $.each(checkedRows,function(index,row){
	            	rowIndex = $('#clientDtlDataGrid').datagrid('getRowIndex',row);
	            	$('#clientDtlDataGrid').datagrid('deleteRow',rowIndex);
	            });
	        }else{
	        	showWarn("请选中需要删除的记录!");
	        }
	    },
	};

//insideBizTypeEditor.delDtl = function(){
//	var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
//	if(checkedRows.length > 0){
//		$.each(checkedRows,function(index,item){
//			var rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',item);
//			$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
//		});
//	}else{
//		showInfo("请选中需要删除的记录?");
//	}
//};

/**
 * 验证编码是否重复
 */
//进入修改页面时公司、业务类型编号的原始值
var $companyNoPre = null;
var $bizTypeCodePre = null;

// 表头的信息初始化及验证
var extra_operate = {
	// 新增初始化
	initAdd : function() {
		$('#dataForm').form('clear');
		$('#id,#companyNo_').val('');
		deleteAllGridCommon('dtlDataGrid');
	},
	
	checkSave : function() {
		var repeatFlag = false;
		var $companyNo = $("#companyNoId").val();
		var $bizTypeCode = $("#bizTypeCodeId").val();
		$.ajax({
			type : "POST",
			url : BasePath + "/inside_biz_type/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				companyNo : $companyNo,
				bizTypeCode : $bizTypeCode
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('业务类型编号已存在，请重新输入!');
		}
		return !repeatFlag;
	},
	
	// 修改初始化
	initUpdate : function(rowData) {
		var updateFlag = false;
		var checkedRow = $("#dataGridJG").datagrid("getSelected");
		if (checkedRow.status == '1') {
			showWarn("包含启用记录，不能修改！");
			updateFlag = true;
		}
		return !updateFlag;
	},

	// 修改之前的逻辑处理
	loadedUpdate : function(rowData) {
		$bizTypeCodePre = rowData.bizTypeCode;
	},

	//修改验证
	checkUpdate:function(){
		var repeatFlag = false;
		//修改后公司、业务类型编号的值
		var $companyNo = $("#companyNoId").val();
		var $bizTypeCode = $("#bizTypeCodeId").val();
		//如果公司、业务类型编号未改变，则不进行校验
		if($bizTypeCodePre == $bizTypeCode) {
			return !repeatFlag;
		}
		$.ajax({
			type : "POST",
			url : BasePath + "/inside_biz_type/check_Repect",
			dataType : 'json',
			async : false,
			data : {
				companyNo : $companyNo,
				bizTypeCode : $bizTypeCode
			},
			success : function(msg) {
				if (msg == true) {
					repeatFlag = true;
				}
			}
		});
		// 如果存在，则提示
		if (repeatFlag) {
			showWarn('业务类型编号已存在，请重新输入!');
		}
		return !repeatFlag;
	},
	
	// 保存成功之后
	saveCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},
	
	// 修改之后的处理
	updateCallback : function(result, setting) {
		$("#id").attr("value", "");
		$("#" + setting.searchBtn).click();
	},

	// 删除之前的逻辑处理
	checkDel : function() {
		var delFlag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1'){
				 showWarn("包含启用记录，不能删除！");
				 return;
			 }
		}
		return !delFlag;
	},
	
	checkEnable:function(){
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1'){
				 showWarn("包含启用记录，不能重复操作！");
				 return;
			 }
		}
		return !Flag;
	},
	
	checkUnable:function(){
		var Flag = false;
		var checkedRows = $("#dataGridJG").datagrid("getChecked");
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '0'){
				 showWarn("包含停用记录，不能重复操作！");
				 return;
			 }
		}
		return !Flag;
	}
};
