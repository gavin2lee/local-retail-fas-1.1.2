/** fas.plugin.js辅助文件 */
// 选择店铺
var shopSupport = {
	// 选择管理城市
	organCheck : function() {
		var checkedRows = $("#pluginShopOrganDataGrid").datagrid("getChecked");
		if(checkedRows.length == 0) {
			$("#pluginShopBizCityDataGrid").datagrid("loadData", {total:0,rows:[]});
		} else {
	    	//组装请求参数
	    	var multiParentNo = "";
	    	$.each(checkedRows, function(index, item) {
	    		if(index < (checkedRows.length - 1)) {
	    			multiParentNo += item.organNo + ",";
	    		} else {
	    			multiParentNo += item.organNo;
	    		}
	    	});
	    	var queryParams = {
    			organLevel:'2',
    			status:'1',
    			multiParentNo:multiParentNo	
	    	};
	    	$("#pluginShopBizCityDataGrid").datagrid('options').queryParams = queryParams;
		    $("#pluginShopBizCityDataGrid").datagrid('options').url = BasePath + "/organ/get_mulit_biz";
			$("#pluginShopBizCityDataGrid").datagrid("load");
		}
		$("#pluginShopShopDataGrid").datagrid("loadData", {total:0,rows:[]});
	},
	// 选择经营城市
	bizCityCheck : function() {
		var checkedRows = $("#pluginShopBizCityDataGrid").datagrid("getChecked");
		if(checkedRows.length == 0) {
			$("#pluginShopShopDataGrid").datagrid("loadData", {total:0,rows:[]});
		} else {
	    	//组装请求参数
	    	var multiOrganNo = "";
	    	$.each(checkedRows, function(index, item) {
	    		if(index < (checkedRows.length - 1)) {
	    			multiOrganNo += item.organNo + ",";
	    		} else {
	    			multiOrganNo += item.organNo;
	    		}
	    	});
	    	var queryParams = {
    			status:'1',
    			multiOrganNo:multiOrganNo,
    			rows:$("#shopPagination").pagination('options').pageSize
	    	};
	    	$("#pluginShopShopDataGrid").datagrid('options').queryParams = queryParams;
		    $("#pluginShopShopDataGrid").datagrid('options').url = BasePath + "/shop/list_multi_organNo.json";
			$("#pluginShopShopDataGrid").datagrid("load");
		}
	},
	queryOrgan : function(organName) {
//		var organName = $("#pluginShopOrganName").val();
//		var zoneNo = $("#pluginShopZoneNo").combobox("getValue");
		var queryParams = {
			organLevel:'1',
			status:'1',
			nameLike:organName
//			zoneNo : zoneNo
		}
		$("#pluginShopOrganDataGrid").datagrid('options').queryParams = queryParams;
	    $("#pluginShopOrganDataGrid").datagrid('options').url = BasePath + "/organ/get_biz.json";
		$("#pluginShopOrganDataGrid").datagrid("load");
	},
	queryBizCity : function(bizCityName) {
		var checkedRows = $("#pluginShopOrganDataGrid").datagrid("getChecked");
		if(checkedRows.length == 0) {
			return;
		}
    	var multiParentNo = "";
    	$.each(checkedRows, function(index, item) {
    		if(index < (checkedRows.length - 1)) {
    			multiParentNo += item.organNo + ",";
    		} else {
    			multiParentNo += item.organNo;
    		}
    	});
		var queryParams = {
			organLevel:'2',
			status:'1',
			multiParentNo:multiParentNo,
			nameLike:bizCityName
		};
		$("#pluginShopBizCityDataGrid").datagrid('options').queryParams = queryParams;
	    $("#pluginShopBizCityDataGrid").datagrid('options').url = BasePath + "/organ/get_mulit_biz";
		$("#pluginShopBizCityDataGrid").datagrid("load");
	},
	queryShop : function(shopName, pageNo, pageSize) {
		var checkedRows = $("#pluginShopBizCityDataGrid").datagrid("getChecked");
//		if(checkedRows.length == 0) {
//			return;
//		}
		var multiOrganNo = "";
    	$.each(checkedRows, function(index, item) {
    		if(index < (checkedRows.length - 1)) {
    			multiOrganNo += item.organNo + ",";
    		} else {
    			multiOrganNo += item.organNo;
    		}
    	});
    	var queryParams = $("#queryShopPageSearchForm").form("getData");
    	queryParams.status = "1";
    	if($.trim(multiOrganNo) != '') {
    		queryParams.multiOrganNo = multiOrganNo;
    	}
    	if(shopName) {
    		queryParams.shortName = shopName;
    	} else {
    		queryParams.shortName = $("#queryShopPageShopToolbar").find("input[name='queryShopPageShopName']").val();
    	}
    	if(pageNo) {
    		queryParams.page = pageNo;
    	} else {
    		queryParams.page = 1;
    	} 
    	if(pageSize) {
    		queryParams.rows = pageSize;
    	} else {
    		queryParams.rows = pageSize;
    	}
		$("#pluginShopShopDataGrid").datagrid('options').queryParams = queryParams;
	    $("#pluginShopShopDataGrid").datagrid('options').url = BasePath + "/shop/list_multi_organNo.json";
		$("#pluginShopShopDataGrid").datagrid("load");
	},
	clearQueryShop : function() {
		$("#queryShopPageSearchForm").find("input").val("");
	},
	clickShopPagination : function(data){
		var totalCount = data.total;
		$("#shopPagination").pagination({
			total:totalCount,
			pageList:[10,20,50,100,200,300,400,500],
			onSelectPage: function(pageNumber, pageSize){
				shopSupport.queryShop(null, pageNumber, pageSize);
			}
		});
	}
};
