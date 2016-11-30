var officialItem = {};

//查询
officialItem.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = 'list.json';
	$("#dtlDataGrid").datagrid('load');
};

//清空
officialItem.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
};

//全量同步数据
officialItem.updateAllData = function(){
	$.messager.confirm("确认","是否全量同步数据?",function(r) {
		if (r) {
			$.messager.progress({
				title:'请稍后',
				msg:'正在同步，请耐心等候...',
				text:''
			}); 
			$('#searchForm').form('submit',{
				url : BasePath + '/official_item/update_all_data',
				success:function(data){
					var jsonData = JSON.parse(data);
					$.messager.progress('close');
					showInfo("总共同步记录："+jsonData.addCount+"条");
				} 
			});
		}
	});
	
}

//同步数据
officialItem.updateData = function(){
	var updateTimeStart = $('#updateTimeStart').val();
	if(updateTimeStart ==''){
		showError("请选择同步起始时间！");
		return;
	}
	$.messager.confirm("确认","是否增量同步"+updateTimeStart+"至今的数据?",function(r) {
		if (r) {
			$.messager.progress({
				title:'请稍后',
				msg:'正在同步，请耐心等候...',
				text:''
			}); 
			$('#searchForm').form('submit',{
				url : BasePath + '/official_item/update_data',
				onSubmit:function(param){
					param.updateTimeStart = updateTimeStart;
				},
				success:function(data){
					var jsonData = JSON.parse(data);
					$.messager.progress('close');
					showInfo("总共新增记录："+jsonData.addCount+"条,更新记录："+jsonData.updateCount+"条。")
				} 
			});
		}
	});
};

$(function(){
	$('#excessStatus').combobox({
		valueField:'id',
		textField:'text',
		data:[
		      {
		    	  id:'-1',
		    	  text:'未超额'
		      },
		      {
		    	  id:'1',
		    	  text:'超额OA未处理'
		      },
		      {
		    	  id:'2',
		    	  text:'超额OA已处理'
		      }
		      ],
		editable:true
	});
	
});

