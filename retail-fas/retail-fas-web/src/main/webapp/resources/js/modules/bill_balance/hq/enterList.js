var enterList = new Object();

//查询
enterList.search = function() {
	if($('#searchForm').form('validate')){
		var url = BasePath + '/bill_balance/hq/enter_list.json';
		var params = $('#searchForm').form('getData');
	    $('#dtlDataGrid').datagrid('options').queryParams= params;
	    $('#dtlDataGrid').datagrid('options').url= url;
	    $('#dtlDataGrid').datagrid('load');
	}
};

//清空
enterList.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType][name!=isArea]").val("");
};

//导出
enterList.doExport = function() {
	common_util.doExport('dtlDataGrid','/bill_balance/hq/export','单据明细列表',{type:'enter'},BasePath + '/bill_balance/hq/enter_count');
};

// 初始化
$(function(){
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	$("#btn-direct").on('click',function(){
		var balanceType = $('#balanceType').val();
		var startDate = $('#balanceStartDate').val();
		var endDate = $('#balanceEndDate').val();
		if(startDate =='' || endDate==''){
			showWarn("请选择日期范围！");
			return ;
		}
		$.messager.confirm("确认","确定更新"+startDate+"至"+endDate+"日期范围异常单据价格?",function(r) {
			if (r) {
				$("#updatePriceForm").remove();
				$("<form id='updatePriceForm'  method='post'></form>").appendTo("body"); ;
				var fromObj=$('#updatePriceForm');
    			$.messager.progress({
    				title:'请稍后',
    				msg:'正在更新异常价格...',
    				interval:3000
    			}); 
    			var queryParams = $('#searchForm').form('getData');
				fromObj.form('submit', {
					url: BasePath +'/bill_balance/hq/update_exception_price',
					onSubmit: function(param){
						param.balanceType = balanceType;
						param.balanceStartDate = startDate;
						param.balanceEndDate = endDate;
						if(param!=null&&param!={}){
							$.each(queryParams,function(i){
								param[i]=queryParams[i];
							});
						}
					},
					success: function(){
						$("#updatePriceForm").remove();
						$.messager.progress('close');
						showSuc('更新成功');
						enterList.search();
				    }
			   });
			}
		});
	});
});
