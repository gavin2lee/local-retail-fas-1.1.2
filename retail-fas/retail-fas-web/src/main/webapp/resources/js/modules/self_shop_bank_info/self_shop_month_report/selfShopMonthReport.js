

var selfShopMonthReport = new Object();

var common_setting = {
	searchBtn : "btn-search",
	searchUrl : "/self_shop_month_report/list.json",
	dataGridId : "dataGridDiv",
	url : "/self_shop_month_report/do_fas_export",
	//export_title
	title : "独立店月报表导出",
	searchFormId : "searchForm"
};

selfShopMonthReport.searchEventBtn = function(){
	
	var fromObj = $('#' + common_setting.searchFormId);
	
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	
	var fromObjStr = convertArray($("#" + common_setting.searchFormId).serializeArray());
	var queryMxURL = BasePath + common_setting.searchUrl;
	
	// 2.加载明细 注意请求方式 restful风格 get
	$("#" + common_setting.dataGridId).datagrid('options').queryParams = eval("("
			+ fromObjStr + ")");
	$("#" + common_setting.dataGridId).datagrid('options').url = queryMxURL;
	$("#" + common_setting.dataGridId).datagrid('load');
	$(":checkbox:eq(0)").attr("checked", false);
	
};


selfShopMonthReport.removeEventBtn = function(){
	
	$('#' + common_setting.searchFormId).form("clear");
	$(':input','#' + common_setting.searchFormId).not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
};


$(function(){
	selfShopMonthReport.initSaleType();
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#startOutStart").val(firstDay.format("yyyy-MM-dd"));
	$("#endOutEnd").val(lastDay.format("yyyy-MM-dd"));
	
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

selfShopMonthReport.initSaleType = function(){
	$('#saleType').combobox({
		data : payFeesFlag,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};

selfShopMonthReport.exportEventBtn = function(){
	var setting = common_setting;
	
	var $dg = $("#" + setting.dataGridId);
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	
	var subGrepColumns = $dg.datagrid('options').subColumns;
	
	var columns = $.grep(grepColumns[0], function(o, i) {
		if ($(o).attr("notexport") == true) {
			return true;
		}
		return false;
	}, true);
	
	var subColumns = [];
	if(typeof subGrepColumns != 'undefined' 
			&& subGrepColumns != null
			&& subGrepColumns != "") {
		subColumns = $.grep(subGrepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}
	
	var exportColumns = JSON.stringify(columns);
	var exportSubColumns = JSON.stringify(subColumns);
	var url = BasePath + setting.url;
	var dataRow = $dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.exportColumns = exportColumns;
				param.exportSubColumns = exportSubColumns;
				param.fileName = setting.title;
				param.exportType = setting.exportType || '';
				if(params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
					});
				}
			},
			success : function() {

			}
		});
	} else {
		showWarn('查询记录为空，不能导出!');
	}
};



var payFeesFlag = [ {
	"value" : "2",
	"text" : "全部"
}, {
	"value" : "0",
	"text" : "正常销售"
},{
	"value" : "1",
	"text" : "跨店销售"
} ];