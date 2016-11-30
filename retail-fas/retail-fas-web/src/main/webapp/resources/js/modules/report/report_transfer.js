var report_transfer = new Object();

//查询
report_transfer.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/report/report_transfer.json';
	    $('#dtlDataGrid').datagrid('options').queryParams= params;
	    $('#dtlDataGrid').datagrid('options').url= url;
	    $('#dtlDataGrid').datagrid('load');
	}
};

//清空
report_transfer.clear = function() {
	$('#searchForm').form("clear");
	common_util.initDate(); 
};

common_util.doExport = function (dataGridId, exportUrl, excelTitle, otherParams, countUrl){
	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm'  method='post'></form>").appendTo("body"); 
	var fromObj=$('#exportExcelForm');
	var $dg = $('#'+dataGridId);
	var params=$dg.datagrid('options').queryParams;
	var columns=$dg.datagrid('options').columns;
	var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
	var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录
	var headColumns = new Array();
	var bodyColumns = new Array();
	var columnsNew = [];
	var columnsNew1 = [];
	headColumns.push(columns[0]);
	bodyColumns.push(columns[1]);
	$.each(headColumns,function(index,item){
		var dataArray = [];
		var i = 0;
		$.each(item,function(rowIndex,rowData){
			var v_object = {};
			v_object.field = rowData.field;
			v_object.title = rowData.title;
			v_object.colspan = rowData.colspan;
			if(rowData.notexport || rowData.hidden){
				return ;
			}
			dataArray[i] = v_object;
			i++;
		});
		columnsNew[index] = dataArray;
	});
	$.each(bodyColumns,function(index,item){
		var dataArray = [];
		var i = 0;
		$.each(item,function(rowIndex,rowData){
			var v_object = {};
			v_object.field = rowData.field;
			v_object.title = rowData.title;
			v_object.width = rowData.width;
			if(rowData.notexport || rowData.hidden){
				return ;
			}
			dataArray[i] = v_object;
			i++;
		});
		columnsNew1[index] = dataArray;
	});
	var exportHeadColumns=JSON.stringify(columnsNew);
	var exportColumns=JSON.stringify(columnsNew1);
	var url=BasePath+exportUrl;
	var dataRow=$dg.datagrid('getRows');
	if(dataRow.length>0){
		if(typeof countUrl != 'undefined'){// 数量验证
			var flag = true;
			var queryParams = fromObj.form('getData');
			if(params!=null&&params!={}){
				$.each(params,function(i){
					queryParams[i]=params[i];
				});
			}
			if(otherParams!=null&&otherParams!={}){
				$.each(otherParams,function(i){
					queryParams[i]=otherParams[i];
				});
			}
			ajaxRequestAsync(countUrl,queryParams,function(count){
				if(count > 100000){
					flag = false;
				}
			});
			if(!flag){
				showError("数据量大于10万,请选择分批导出");
				return ;
			}
		}
	    fromObj.form('submit', {
			url: url,
			onSubmit: function(param){
				if(params!=null&&params!={}){
					$.each(params,function(i){
						param[i]=params[i];
					});
				}
				if(otherParams!=null&&otherParams!={}){
					$.each(otherParams,function(i){
						param[i]=otherParams[i];
					});
				}
				param.exportHeadColumns=exportHeadColumns;
				param.exportColumns=exportColumns;
				param.fileName=excelTitle;
				param.pageNumber = v_pageNumber;
				param.pageSize = v_pageSize;
				param.exportFlag = true;
				
			},
			success: function(){
				
		    }
	   });
	}else{
		alert('查询记录为空，不能导出!',1);
	}
}

//初始化
$(function(){
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
});