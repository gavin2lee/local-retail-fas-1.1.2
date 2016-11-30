var hqShipmentCollet = new Object();
var $this = this;
//查询
hqShipmentCollet.search = function() {
	if($('#searchForm').form('validate')) {
		var params = $('#searchForm').form('getData');
		
		var url = BasePath + '/hq_shipment_collet/find_shipment_list';
		$('#hqShipmentColletDataGrid').datagrid('options').queryParams= params;
	    $('#hqShipmentColletDataGrid').datagrid('options').url= url;
	    $('#hqShipmentColletDataGrid').datagrid('load');
	}
};

//清空
hqShipmentCollet.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=type]").val('');
};

//导出
hqShipmentCollet.exportExcel = function() {
	$.fas.exportExcel({
		dataGridId : "hqShipmentColletDataGrid",
		exportUrl : "/hq_shipment_collet/do_fas_export",
		exportTitle : "出货统计表"
	});
};


// 初始化
$(function(){
	
});

