var report_gather = new Object();

//查询
report_gather.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/report/report_gather.json';
	    $('#dtlDataGrid').datagrid('options').queryParams= params;
	    $('#dtlDataGrid').datagrid('options').url= url;
	    $('#dtlDataGrid').datagrid('load');
	}
};

report_gather.linkFormat = function(value, row, panelId, dgId, title){
	if(row.brandName=='小计') {
		return "<a href=javascript:void(0) onclick=report_gather.showDtl('"+row.salerNo + "','" + row.buyerNo + "','"
			+ title + "','" + panelId + "','" + dgId+ "') style=cursor:pointer;text-decoration:underline;color:blue;>"+value+"</a>"
	}
}

// 显示明细
report_gather.showDtl = function(salerNo , buyerNo, title, panelId, dgId){
	ygDialog({
		title : title,
		target : $('#'+panelId),
		width :  850,
		height : 'auto',
		buttons : [{
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
	var params = $('#searchForm').form('getData');
	params.salerNo = salerNo;
	params.buyerNo = buyerNo;
	params.panelId = panelId;
	ajaxRequestAsync(BasePath + '/report/dtl_list', params, function(data){
		$('#'+dgId).datagrid('loadData',data);
	});
}

//清空
report_gather.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
	common_util.initDate();
};

// 初始化
$(function(){
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
});
