// 弹出框
function SettleBrandGroupDialog() {
	var $this = this;
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	
	var  organTypeNo =  currentUser.organTypeNo;
	$("#organTypeNo").val(organTypeNo);	
	
	this.initAdd = function() {
		$("#groupId").val('');
	};
	//检查新增的数据是否正确
	this.checkAdd = function() {
		return $this.validateData();
	};
	this.loadedAdd = function() {
		$("#dtlDataGridDiv").datagrid("unselectAll");
		$("#brandUnitNoCondition").val("");
		$("#brandUnitNameCondition").val("");
		$this.searchDtl();
	};
	this.checkInitUpdate = function(rowData) {
//		if(rowData.status == '1') {
//			showWarn("数据已启用，不允许修改!");
//			return false;
//		}
		return true;
	};
	this.checkUpdate = function() {
		return $this.validateData();
	}
	this.loadedUpdate = function(rowData) {
		// 查看
		if(rowData.status == '1') {
			$.fas.search({
				dataGridId : "dtlDataGridDivView",
				searchUrl : "/settle_brand_group_rel/query?groupNo="+rowData.groupNo,
				hasSearchForm : false
			});
		} else {
			$("#brandUnitNoCondition").val("");
			$("#brandUnitNameCondition").val("");
			$this.searchDtl();
			$this.loadSuccess();
		}
	};
	//查询明细页面中的数据
	this.searchDtl = function() {
		$.fas.search({
			searchFormId : "dtlSearchForm",
			dataGridId : "dtlDataGridDiv",
			searchUrl : "/brand_unit/get_biz",
		});
	};
	//查询明细页面中的数据
	this.clearDtl = function() {
		$("#dtlSearchForm").form("clear");
	};
	// 子页面查询加载成功后的操作
	this.loadSuccess = function() {
		$("#dtlDataGridDiv").datagrid("unselectAll");
		var gridRows = $("#dtlDataGridDiv").datagrid('getRows');
		if(gridRows) {
			var url = BasePath + "/settle_brand_group_rel/query?groupNo="+$("#groupNo").val();
			ajaxRequest(url, {}, function(result){
				if(result) {
					$.each(gridRows, function(gridDataIndex, gridDataItem){
						$.each(result, function(index, item){
							if(item && (item.brandUnitNo == gridDataItem.brandUnitNo)) {
								$('#dtlDataGridDiv').datagrid("selectRow", gridDataIndex);
							}
						});
					});   
				}
			});
		}
	};
	//组装新增、修改数据
	this.buildSaveSubmitParams = function() {
		var checkedRows = $("#dtlDataGridDiv").datagrid("getChecked");// 获取所有勾选checkbox的行
		var params = [];
		var insertData = [];
		$.each(checkedRows, function(index, item){
			var obj = new Object();
			obj.groupNo = $("#groupNo").val();
			obj.brandUnitNo = item.brandUnitNo;
			insertData.push(obj);
		});     
		params.push({name : 'inserted', value : JSON.stringify(insertData)});
		return params;
	};
	this.checkDel = function(checkedRows) {
		for(var i = 0; i < checkedRows.length; i++) {
			if(checkedRows[i].status == '1') {
				showWarn("数据已启用，不允许删除!", 1);
				return false;
			}
		}
		return true;
	};
	//校验
	this.validateData = function() {
		var url = BasePath + "/settle_brand_group/validate_data";
		var check_data = {id : $("#groupId").val(), groupNo : $("#groupNo").val(), name : $("#name").val()};
		var validate_data = $.fas.ajaxRequestAsync(url, check_data, function(result){
			if(result && !result.success) {
				showError(result.message);
				return false;
			}
			return true;
		});
		return validate_data;
	};
	// tab查询页面中的查询方法
	this.querySearch = function() {
		$.fas.search({
	        searchFormId : "querySearchForm",
	        dataGridId : "queryDataGridDiv",
	        searchUrl : "/settle_brand_group_query/query.json"
	    });
	};
	// tab查询页面进行清空操作
	this.queryClear = function() {
		$.fas.clear("querySearchForm");
	};
	this.queryExport = function() {
		 $.fas.exportExcel({
	        dataGridId : "queryDataGridDiv",
	        exportUrl : "/settle_brand_group_query/do_fas_export",
	        exportTitle : "品牌组明细信息导出",
	        exportType : "common"
	    });
	}
};

//品牌组明细列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	return "?groupNo=" + rowData.groupNo;
};

var dialog = null;
$(function() {
	$.fas.extend(SettleBrandGroupDialog, FasDialogController);
	dialog = new SettleBrandGroupDialog();
	dialog.init("/settle_brand_group", fas_common_setting);
	
	$('#mainTab').tabs('add', {
		title : '品牌组查询',
		selected : false,
		closable : false,
		href : BasePath + "/settle_brand_group_query/list"
	});
});