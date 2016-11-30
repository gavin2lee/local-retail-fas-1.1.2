var shopSaleDtl = new Object();

var commonSetting = function(){
	var setting = {
		searchFormId : "searchForm",
		searchBtn : "btn-search",
		dataGridId : "dataGridDiv",
		clearBtn : "btn-remove",
		exportBtn : "btn-export",
		export_url : "/shop_sale_detail/do_fas_export",
		export_title : "店铺销售明细导出"
	}
	return setting;
};

//search Event
shopSaleDtl.searchEvent = function(params){
	
	var fromObj = $('#' + params.formId);
	
	var validateForm = fromObj.form('validate');
	
	// 1.校验必填项
	if (validateForm) {
		
		var fromObjStr = convertArray($("#" + params.formId).serializeArray());
		var queryMxURL = BasePath + params.url;
		
		// 2.加载明细 注意请求方式 restful风格 get
		$("#" + params.dataGridId).datagrid('options').queryParams = eval("("
				+ fromObjStr + ")");
		$("#" + params.dataGridId).datagrid('options').url = queryMxURL;
		$("#" + params.dataGridId).datagrid('load');
		$(":checkbox:eq(0)").attr("checked", false);
	}
};

//clear Event 
shopSaleDtl.doSearchClear = function() {
	
	$('#' + commonSetting().searchFormId).form("clear");
	$(':input','#' + commonSetting().searchFormId).not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
};

//export Event 
shopSaleDtl.exportExcel = function(setting) {
	var $dg = $("#" + setting.dataGridId);
	var dataRow = $dg.datagrid('getRows');

	if (dataRow.length > 0) {
		var async = true;
		var url = "/shop_sale_detail/" + ( async?"async_exports":"do_fas_export");
	    $.fas.exportExcel({
			dataGridId : "dataGridDiv",
			exportUrl : url,
			exportTitle : "店铺销售明细导出",
			async:async
		});
	} else {
		showWarn('查询记录为空，不能导出!');
	}
};

//initialization 
shopSaleDtl.init = function(){
	
	//binding search button event
	$('#' + commonSetting().searchBtn).on("click",function(){
		shopSaleDtl.searchEvent(
			{
				formId : commonSetting().searchFormId,
				url : "/shop_sale_detail/list.json",
				dataGridId : commonSetting().dataGridId
			}	
		);
	});
	
	// binding clear button event
	$('#' + commonSetting().clearBtn).on("click",function(){
		shopSaleDtl.doSearchClear();	
	});
	
	$('#' + commonSetting().exportBtn).on("click",function(){
		shopSaleDtl.exportExcel({
			dataGridId : commonSetting().dataGridId,
			url : commonSetting().export_url,
			title : commonSetting().export_title,
			exportType : "common"
		});
	});
};


$(function(){
	shopSaleDtl.init();
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#createTimeStart").val(firstDay.format("yyyy-MM-dd"));
	$("#createTimeEnd").val(lastDay.format("yyyy-MM-dd"));
	
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



