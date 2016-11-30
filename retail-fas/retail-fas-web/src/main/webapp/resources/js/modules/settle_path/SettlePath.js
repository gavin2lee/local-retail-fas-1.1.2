// 弹出框
function SettlePathDialog() {
	var $this = this;
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	var  organTypeNo =  currentUser.organTypeNo;
	$("#organTypeNo").val(organTypeNo);	
	
	this.search = function() {
		var $this = this;
	    
	    var params = $("#" + $this.options.searchFormId).form('getData');
	    
	    // 公司多选
		if($("#companyNo_").val() != null && $("#companyNo_").val() != '') {
			params.companyNos = '\'' + $("#companyNo_").val().split(',').join('\',\'') + '\'';
		}
		// 供应商多选
		if($("#supplierNo_").val() != null && $("#supplierNo_").val() != '') {
			params.supplierNos = '\'' + $("#supplierNo_").val().split(',').join('\',\'') + '\'';
		}
		// 品牌多选
		if($("#brandNo_").val() != null && $("#brandNo_").val() != '') {
			params.brandNos = '\'' + $("#brandNo_").val().split(',').join('\',\'') + '\'';
		}
	    $("#" + $this.options.dataGridId).datagrid('options').queryParams= params;
	    $("#" + $this.options.dataGridId).datagrid('options').url= BasePath + $this.modulePath + $this.options.searchUrl;
	    $("#" + $this.options.dataGridId).datagrid('reload');
	}
	
	this.initAdd = function() {
		$("#pathNo").removeClass("readonly").removeAttr("readonly", true);
		$("#id").val('');
		// 清空明细数据
		$("#companyDataGridDiv").clearDataGrid(); 
		$.fas.editIndex = undefined;
		var brandTab = $("#mainTab").tabs("getTab", "品牌部");
		$("#mainTab").tabs('update', {
			tab: brandTab,
			options: {
				title : '品牌部',
				selected : false,
				closable : false,
				href : BasePath + "/settle_path/brand_rel?pathNo="+$("#pathNo").val()
			}
		});
		$("#mainTab").tabs("select","品牌部") ;
	};
	//检查新增的数据是否正确
	this.checkAdd = function() {
		return $this.validateData();
	};
	this.checkInitUpdate = function(rowData) {
//		if(rowData.auditStatus == '1') {
//			 showWarn("数据已审核,不允许修改");
//			 return false;
//		}
		return true;
	};
	this.checkUpdate = function() {
		return $this.validateData();
	};
	this.loadedUpdate = function(rowData) {
		var dataGridId = rowData.auditStatus == '1' ? "companyDataGridDivView" : "companyDataGridDiv";
		var mainTabId = rowData.auditStatus == '1' ? "mainTabView" : "mainTab";
		var brandRelUrl = rowData.auditStatus == '1' ? "brand_rel_view" : "brand_rel";
		if(rowData.auditStatus == '1') {
			// 初始化单据类型
			$("#billTypesView").initCombox({
				url : BasePath + "/settle_path/get_bill_type",
				width : 130,
				valueField : "value",
				textField : "text",
				multiple : true,
				value : rowData.billTypes
			});settleCategoryNoView
			$("#billTypesView").combobox("disable");
			$("#settleCategoryNoView").settlecategorybox("disable");
			$("#styleNoView").newstylebox("disable");
			$("#returnOwnFlagView").attr("disabled", "disabled");
		}
		$("#pathNo").addClass("readonly").attr("readonly", true);
		$.fas.search({
			dataGridId : dataGridId,
			searchUrl : "/settle_path_dtl/get_biz?sort=path_order&order=asc&pathNo="+rowData.pathNo
		});
		$.fas.editIndex = undefined;
		var brandTab = $("#" + mainTabId).tabs("getTab", "品牌部");
		$("#" + mainTabId).tabs('update', {
			tab: brandTab,
			options: {
				title : '品牌部',
				selected : false,
				closable : false,
				href : BasePath + "/settle_path/"+brandRelUrl+"?pathNo="+rowData.pathNo
			}
		});
		$("#" + mainTabId).tabs("select","品牌部") ;
	};
	//组装新增、修改数据
	this.buildSaveSubmitParams = function() {
		var params = [];
		var effectRow = getChangeTableDataCommon("companyDataGridDiv");
		var deleted = "";
		var deletedData = $("#companyDataGridDiv").datagrid('getChanges','deleted');
		var deleteList = [];
		$.each(deletedData, function(index, item){
			var obj = new Object();
			obj.id = item.id;
			deleteList.push(obj);
		});
		if(deleteList.length > 0) {
	        deleted = JSON.stringify(deleteList);
		}
		var brandList = [];
		var checkedRows = $("#brandDataGridDiv").datagrid("getChecked");// 获取所有勾选checkbox的行
		for(var i = 0; i < checkedRows.length; i++) {
			var brand = new Object();
			brand.pathNo = $("#pathNo").val();
			brand.brandUnitNo = checkedRows[i].brandUnitNo;
			brandList.push(brand);
		}
		params.push({name : 'inserted', value : effectRow.inserted});
		params.push({name : 'updated', value : effectRow.updated});
		params.push({name : 'deleted', value : deleted});
		params.push({name : 'brandList', value : JSON.stringify(brandList)});
		return params;
	};
	this.checkDel = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].auditStatus == '1') {
				showWarn("数据已审核，不允许删除!", 1);
				return false;
			}
		}
		var url = BasePath + "/settle_path/check_del";
		var check_data = new Object();
		check_data["deleted"] = JSON.stringify(checkedRows);
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	//校验
	this.validateData = function() {
		var billTypes = $("#billTypes").combobox("getValues");
		var returnOwnFlagChecked = $("#returnOwnFlag").attr("checked");
		if((billTypes == null || billTypes == "") && returnOwnFlagChecked != 'checked') {
			showWarn("单据类型或原残退厂两项都不能为空!");
			return false
		}
		var check_data = {pathNo : $("#pathNo").val(), id : $("#id").val()};
		var url = BasePath + "/settle_path/validate_data";
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		if(!validate_data) {
			return false;
		}
		return $this.checkPathOrder();
	};
	//检查结算路径次序的设置
	this.checkPathOrder = function() {
		if($.fas.endEditing("companyDataGridDiv")) {
			 var rows = $("#companyDataGridDiv").datagrid("getRows");
			 if(rows.length == 0) {
				 return true;
			 }
			 var firstPathOrder = rows[0].pathOrder;
			 if(firstPathOrder != 1) {
				 showWarn("第一个结算公司的路径次序必须为1!");
				 return false;
			 }
			 var maxOrder = rows[rows.length - 1].pathOrder;
			 if(maxOrder > rows.length) {
				 showWarn("最后结算公司的序号不能比结算公司总数大!");
				 return false;
			 }
			 var lastRowMaxOrder = true;
			 $.each(rows, function(index, row){
		    	if(row.pathOrder > maxOrder) {
		    		lastRowMaxOrder = false;
		    	}
		     });
			 
			 var oneCount = 0;
			 $.each(rows, function(index, row){
			    	if(row.pathOrder == 1) {
			    		oneCount += 1;
			    	}
			 });
			 if(oneCount > 1){
				 showWarn("路径次序1不能有多个");
				 return false;
			 }
			 
			 if(!lastRowMaxOrder) {
				 showWarn("中间结算公司的路径次序不能比最后的结算主体大!");
				 return false;
			 }
			 var middleVal = maxOrder - 1, middleRow = 0;
			 var companyNos = [];
			 $.each(rows, function(index, row){
		    	if(row.pathOrder > 1 && row.pathOrder < maxOrder) {
		    		middleRow++;
		    	}
		    	companyNos.push(row.companyNo);
		     });
			 if(middleRow == 0 && maxOrder > 2) {
				 showWarn("不能没有中间结算主体!");
				 return false;
			 }
			 if(middleRow > 1) {
				 showWarn("中间结算公司只能有一个!");
				 return false;
			 }
			 var companyLen = companyNos.length;
			 $.unique(companyNos);
			 var uniqueCompanyLen = companyNos.length;
			 if(companyLen != uniqueCompanyLen) {
				 showWarn("结算公司不能重复!");
				 return false;
			 }
		}
		return true; 
	};
	// 检验审核数据
	this.checkAudit = function(checkedRows) {
		var flag = $this.super.checkAudit.call($this, checkedRows);
		if(!flag) {
			return false;
		}
		var pathList = [];
		$.each(checkedRows, function(index, item){
			var obj = new Object();
			obj.pathNo = item.pathNo;
			pathList.push(obj);
		});     
		if(pathList.length > 0) {
    		var url = BasePath + "/settle_path/check_audit";
    		var effectRow = new Object();
    		effectRow['checkedList'] = JSON.stringify(pathList);
    		var validate_data = $.fas.ajaxRequestAsync(url, effectRow, function(result){
    			if(result && !result.success) {
    				showError(result.message);
    				return false;
    			}
    			return true;
    		});
    		if(!validate_data) {
    			return false;
    		}
    	}
		return true;
	};
	// tab查询页面中的查询方法
	this.querySearch = function() {
		$.fas.search({
	        searchFormId : "querySearchForm",
	        dataGridId : "queryDataGridDiv",
	        searchUrl : "/settle_path_query/query.json"
	    });
	};
	// tab查询页面进行清空操作
	this.queryClear = function() {
		$.fas.clear("querySearchForm");
	};
	// tab查询页面进行导出操作
	this.queryExport = function() {
		 $.fas.exportExcel({
	        dataGridId : "queryDataGridDiv",
	        exportUrl : "/settle_path_query/do_fas_export",
	        exportTitle : "结算路径明细信息导出",
	    	exportType : "complex"
	    });
	}
};

//结算路径列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "pathNo=" + rowData.pathNo;
};

// 行编辑
function SettlePathEditor() {
	//主体复制
	this.companyCopy = function() {
//		if($.fas.endEditing("companyDataGridDiv")) {
			var checkedRows = $("#companyDataGridDiv").datagrid("getSelections");
			if(checkedRows.length < 1) {
				showWarn('请选择要复制的公司!', 1);
				return;
			} else {
				if(checkedRows.length > 1) {
					showWarn('请选择一条记录!', 1);
					return;
				}
				var rowIndex = $("#companyDataGridDiv").datagrid('getRowIndex',checkedRows[0]); 
				if(rowIndex == 0) {
					showWarn('第一条不能进行复制操作!', 1);
					return;
				}
				var rowLen = $("#companyDataGridDiv").datagrid('getRows').length; 
				if(rowIndex != rowLen - 1) {
					showWarn('只能选择最后一条记录进行复制操作!', 1);
					return;
				}
				var datagridRows = $("#companyDataGridDiv").datagrid("getRows");
				if(checkedRows[0].pathOrder == '2' && datagridRows 
						&& datagridRows[datagridRows.length-1].pathOrder > 2) {
					showWarn('中间结算主题不允许进行复制操作!', 1);
					return;
				}
				
				var rows = $("#companyDataGridDiv").datagrid("getRows");
				var companyNos = "";
				$.each(rows, function(index, item) {
					if(index == rows.length - 1) {
						companyNos += item.companyNo;
					} else {
						companyNos += item.companyNo + ",";
					}
				});
				ygDialog({
					title : "结算公司复制",
					href : BasePath + "/settle_path/company_copy?existCompanyNos="+companyNos,
					width : 500,
					height : 500,
					isFrame : false,
					modal : true,
					showMask : true,
					enableCloseButton: false,
					onLoad : function(win, content) {
						var _this = $(this);
						$("#btn-copy-save", _this.contents()).on("click", function() {
							var datas = $("#dataGridDiv1").datagrid("getChecked");
							if(datas != null && datas != "") {
								var comboboxTexts = [], comboboxValues = [];
								comboboxTexts.push(checkedRows[0].financialBasisText);
								comboboxValues.push(checkedRows[0].financialBasis);
								var comboboxData = {values : comboboxValues, texts : comboboxTexts};
								$.each(datas, function(index, item){
									var rowObj = $.extend({}, {pathNo : $("#pathNo").val()});
									rowObj.pathOrder = checkedRows[0].pathOrder;
									rowObj.companyNo = item.companyNo;
									rowObj.companyName = item.name;
									rowObj.financialBasis = item.zoneNo;
									rowObj.financialBasisText = item.zoneName+"成本";
									$.fas.addEditorRow({dataGridId : "companyDataGridDiv", rowData : rowObj, comboboxData : comboboxData});
								});
							}
							win.close();
						});
						$("#dataGridDiv1").datagrid({onDblClickRow : function(index, item) {
							var comboboxTexts = [], comboboxValues = [];
							comboboxTexts.push(checkedRows[0].financialBasisText);
							comboboxValues.push(checkedRows[0].financialBasis);
							var comboboxData = {values : comboboxValues, texts : comboboxTexts};
							var rowObj = $.extend({}, {pathNo : $("#pathNo").val()});
							rowObj.pathOrder = checkedRows[0].pathOrder;
							rowObj.companyNo = item.companyNo;
							rowObj.companyName = item.name;
							rowObj.financialBasis = item.zoneNo;
							rowObj.financialBasisText = item.zoneName+"成本";
							$.fas.addEditorRow({dataGridId : "companyDataGridDiv", rowData : rowObj, comboboxData : comboboxData});
							win.close();
						}});
					}
				});
				return false;
			}
//		}
	};
	//选择品牌组
	this.brandGroupRel = function() {
		ygDialog({
			title : "品牌组",
			href : BasePath + "/settle_path/brand_group_rel",
			width : 500,
			height : 500,
			isFrame : false,
			modal : true,
			showMask : true,
			enableCloseButton: false,
			onLoad : function(win, content) {
				var _this = $(this);
				$("#btn-brand-group-save", _this.contents()).on("click", function() {
					$("#brandDataGridDiv").datagrid("unselectAll");
					var checkedRows = $("#brandGroupDataGridDiv").datagrid("getChecked");
					var datas = "";
					$.each(checkedRows, function(index, item) {
						datas += item.groupNo + ",";
			    	});  
					if(datas != null && datas != "") {
						var url = BasePath + "/settle_brand_group_rel/mulit_group_no";
						ajaxRequest(url, {mulitGroupNo: datas}, function(result){
							var rows = $("#brandDataGridDiv").datagrid("getRows");
							$.each(rows, function(gridDataIndex, gridDataItem){
								$.each(result, function(index, item){
									if(item && (item.brandUnitNo == gridDataItem.brandUnitNo)) {
										 $('#brandDataGridDiv').datagrid("selectRow", gridDataIndex);
									}
								});
							});
						});
					}
					win.close();
				});
				$("#brandGroupDataGridDiv").datagrid({onDblClickRow : function(index, row) {
					$("#brandDataGridDiv").datagrid("unselectAll");
					var url = BasePath + "/settle_brand_group_rel/mulit_group_no";
					ajaxRequest(url, {mulitGroupNo: row.groupNo+","}, function(result){
						var rows = $("#brandDataGridDiv").datagrid("getRows");
						$.each(rows, function(gridDataIndex, gridDataItem){
							$.each(result, function(index, item){
								if(item && (item.brandUnitNo == gridDataItem.brandUnitNo)) {
									 $('#brandDataGridDiv').datagrid("selectRow", gridDataIndex);
								}
							});
						});
					});
					win.close();
				}});
			}
		});
		return false;
	};
	//查询品牌组
	this.searchBrandUnit = function() {
		$.fas.search({searchFormId : "brandSearchForm", dataGridId : "brandDataGridDiv", searchUrl : "/brand_unit/get_biz"});
	};
	//当数据加载成功时触发 
	this.brandDataOnloadSuccess = function(data) {
		if(data) {
			var url = BasePath + "/settle_path_brand_rel/query_settle_path?pathNo="+$("#pathNo").val();
			ajaxRequest(url, {}, function(result){
				$.each(data.rows, function(gridDataIndex, gridDataItem){
					$.each(result, function(index, item){
						if(item && item.brandUnitNo == gridDataItem.brandUnitNo) {
							 $('#brandDataGridDiv').datagrid("selectRow", gridDataIndex);
						}
					});
				});   
			});
		}
	};
	//查询结算公司（结算主体复制页面使用）
	this.searchCompanyCopy = function() {
		$.fas.search({searchFormId : "companyCopySearchForm", dataGridId : "dataGridDiv1", searchUrl : "/settle_path/search_company"});
	};

	//清空查询条件（结算主体复制页面使用）
	this.clearCompanyCopy = function() {
		$("#companyCopySearchForm").form("clear");
	};
	//查询品牌组
	this.searchBrandGroup = function() {
		$.fas.search({searchFormId : "brandGroupSearchForm", dataGridId : "brandGroupDataGridDiv", searchUrl : "/settle_brand_group/list.json?status=1"});
	};
	//清空品牌组查询区域
	this.clearBrandGroup = function() {
		$("#brandGroupSearchForm").form("clear");
	};
};

// 在新增或修改行操作之前执行的函数
function beforeAddUpdateEditor(rowIndex, rowData) {
	var rows = $("#companyDataGridDiv").datagrid("getRows");
	var companyNos = "";
	$.each(rows, function(index, item) {
		// index=0表示供应商组
		if(index > 0 && index == rows.length - 1) {
			companyNos += item.companyNo;
		} else if(index > 0) {
			companyNos += item.companyNo + ",";
		}
	});
	var url = BasePath + '/settle_path/search_company';
	$("#companyDataGridDiv").datagrid("removeEditor", "companyName");
	if(rowIndex == 0) {
		$("#companyDataGridDiv").datagrid("addEditor", {field : "companyName", 
			editor:{type:'suppliergroup',
				  options:{
					  	id:'companyName',
					  	name:'companyName',
					  	inputNoField: 'companyNo',// 输入字段编码
					  	required:true
				  }
			}
		});
	} else if(rowIndex == 1) {
		$("#companyDataGridDiv").datagrid("addEditor", {field : "companyName", 
			editor:{type:'company',
				  options:{
					  	id:'companyName',
					  	name:'companyName',
					  	panelWidth:400,
					  	url:url,
					  	queryParams:{existCompanyNos:companyNos},
					  	required:true,
					  	callback : function(data) {
					  		$('#companyNo').val(data.companyNo);
							$('#companyName').val(data.name);
							$('#financialBasis').combobox('setValue', "CGJ");
							$('#financialBasis').val("CGJ");
							$('#financialBasisText').val('采购价');
					  	}
				  }
			}
		});
	} else {
		$("#companyDataGridDiv").datagrid("addEditor", {field : "companyName", 
			editor:{type:'company',
				  options:{
					  	id:'companyName',
					  	name:'companyName',
					  	panelWidth:400,
					  	url:url,
					  	queryParams:{existCompanyNos:companyNos},
					  	required:true,
					  	callback : function(data) {
					  		$('#companyNo').val(data.companyNo);
							$('#companyName').val(data.name);
							var url0 = BasePath+"/base_setting/financial_account/getBaseInfo";
							var params0 = {
								companyNo: data.companyNo,
								status : '1'
							};
							ajaxRequest(url0, params0, function(result) {
								if(result && result.priceZone) {
									$('#financialBasis').combobox('setValue', result.priceZone);
									$('#financialBasis').val(result.priceZone);
									if(result.priceZoneName) {
										$('#financialBasisText').val(result.priceZoneName+'成本');
									}
								} else {
									$('#financialBasis').combobox('setValue', data.zoneNo);
									$('#financialBasis').val(data.zoneNo);
									if(data.zoneName) {
										$('#financialBasisText').val(data.zoneName+'成本');
									}
								}
							});
					  	}
				  }
			}
		});
	}
	if(rowIndex === 0) {
		$("#companyDataGridDiv").datagrid("removeEditor", "pathOrder");
		$("#companyDataGridDiv").datagrid("removeEditor", "financialBasis");
	} else {
		$("#companyDataGridDiv").datagrid("addEditor", {field : "pathOrder", 
			editor:{type:'numberbox',options:{required:true,max:3}}
		});
		$("#companyDataGridDiv").datagrid("addEditor", {field : "financialBasis", 
					editor : {type:'fascombobox',
						options:{
								id:'financialBasis',
								name:'financialBasis',
								valueField:'zoneNo',
								textField:'name',
								url:BasePath+'/zone_info/list_settle_path_zone?suffix=成本',
								width:100,
								required:true,
								onSelect:function(data){
									$('#financialBasis').val(data.zoneNo);
									$('#financialBasisText').val(data.name);
								}
							}
					}
				}
		);
	}
};

//在执行新增或修改操作的时候，初始化行数据的函数
function buildAddUpdateEditorData(rowIndex, rowData) {
	if(rowIndex === 0 || rowIndex === 1) {
		if(rowData && typeof rowData != 'undefined') {
			return {pathNo : $("#pathNo").val(), pathOrder : rowData.pathOrder};
		} else {
			return {pathNo : $("#pathNo").val(), pathOrder : rowIndex+1};
		}
	} else {
		if(rowData && typeof rowData != 'undefined') {
			return {pathNo : $("#pathNo").val(), pathOrder : rowData.pathOrder};
		} else {
			if(rowIndex == 2) {
				return {pathNo : $("#pathNo").val(), pathOrder : 3};
			} else {
				var checkedRows = $("#companyDataGridDiv").datagrid("getRows");
				return {pathNo : $("#pathNo").val(), pathOrder : checkedRows[2].pathOrder};
			}
		}
	}
}

function initCombobox(){
	var auditStatusArray = 
		[
		 {'value' : '1' , 'text' : '已审核'},
		 {'value' : '0' , 'text' : '未审核'}
       ];
	$('#auditStatus').combobox({
		data : auditStatusArray,
		valueField : 'value',
		textField : 'text'
	});
};

var dialog = null, editor = null;
$(function() {
	initCombobox();
	$.fas.extend(SettlePathDialog, FasDialogController);
	$.fas.extend(SettlePathEditor, FasEditorController);
	dialog = new SettlePathDialog();
	// 判断是否可修改的字段
	fas_common_setting.checkUpdateField = "auditStatus";
	dialog.init("/settle_path", fas_common_setting);
	
	editor = new SettlePathEditor();
	editor.init("/settle_path", {
		dataGridDivId : 'companyDataGrid',
		dataGridId : "companyDataGridDiv",
		initRow : function(){
			return {pathNo : $("#pathNo").val()};
		},
		checkDel : function(checkedRows) {
			if(checkedRows) {
				for(var i = 0; i < checkedRows.length; i++) {
					if(checkedRows[i].pathOrder == '1') {
						showWarn("第一条数据不能删除，请重新选择");
						return false;
					}
				}
			}
			return true;
		},
		beforeAdd : beforeAddUpdateEditor,
		beforeUpdate : beforeAddUpdateEditor,
		buildAddData : buildAddUpdateEditorData,
		buildUpdateData : buildAddUpdateEditorData,
	});
	
	$('#mainTab_').tabs('add', {
		title : '结算路径查询',
		selected : false,
		closable : false,
		href : BasePath + "/settle_path_query/list"
	});
	// 初始化单据类型
	$("#billTypes").initCombox({
		url : BasePath + "/settle_path/get_bill_type",
		width : 130,
		valueField : "value",
		textField : "text",
		multiple : true,
		onSelect : function(newValue) {
			if(newValue) {
				$("#billBasis").val("1301");
			} else {
				$("#billBasis").val("");
			}
		}
	});
	$("#returnOwnFlag").on("click", function() {
		var billTypes = $("#billTypes").combobox("getValues");
		if(billTypes == null || billTypes == "") {
			$("#billBasis").val("");
		}
	});
	
	$('#mainTab').tabs('add', {
		title : '品牌部',
		selected : false,
		closable : false,
		href : BasePath + "/settle_path/brand_rel?pathNo="
	});
	
	$('#mainTabView').tabs('add', {
		title : '品牌部',
		selected : false,
		closable : false,
		href : BasePath + "/settle_path/brand_rel?pathNo="
	});
});