var ExportAndImport = new Object();

/**
 * 基础资料的导出
 * @param templateName  模版路径
 * @param url 导入数据的URL  
 * @param successFn 成功回调方法 (可选参数)
 * @param errorFn 失败回调方法 (可选参数)
 */
ExportAndImport.doImport = function(templateName,url,successFn,errorFn) {
	$.importExcel.open({
		'submitUrl' : BasePath + url,
		'templateName' : templateName,
		success : successFn,
		error : errorFn
	});
};

/**
 * dataGrid导出
 * @param dataGridId  导出数据的表格ID
 * @param exportUrl 导出数据的URL  
 * @param excelTitle excel文件名
 * @param otherParams 其他参数 (可选参数)
 */
iptSearch.doExport = function (dataGridId, exportUrl, excelTitle, otherParams){
	var $dg = $('#'+dataGridId);
	var params=$dg.datagrid('options').queryParams;
	var columns=$dg.datagrid('options').columns;
	var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
	var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录
	
	var columnsNew = [];
	$.each(columns,function(index,item){
		var dataArray = [];
		var i = 0;
		$.each(item,function(rowIndex,rowData){
			var v_object = {};
			v_object.field = rowData.field;
			v_object.title = rowData.title;
			v_object.width = rowData.width;
			if(rowData.notexport){
				return ;
			}
			dataArray[i] = v_object;
			i++;
		});
		columnsNew[index] = dataArray;
	});
	
	var exportColumns=JSON.stringify(columnsNew);
	
	var url=BasePath+exportUrl;
	
	var dataRow=$dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	
	$("<form id='exportExcelForm'  method='post'></form>").appendTo("body"); ;
	
	var fromObj=$('#exportExcelForm');
	
	if(dataRow.length>0){
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
				param.exportColumns=exportColumns;
				param.fileName=excelTitle;
				param.pageNumber = v_pageNumber;
				param.pageSize = v_pageSize;
				
			},
			success: function(){
				
		    }
	   });
	}else{
		alert('查询记录为空，不能导出!',1);
	}
}

(function($) {
	var defaults = {
		submitUrl : '',
		templateName : '',
		params : {},
		success:function(){
			
		},
		error:function(){
			
		}
	};

	var importExcel = {
		opts : {},
		dialog : null,
		open : function(o) {
			opts = o;
			var $uploadForm = null;
			var $errorInfo = null;
			dialog = ygDialog({
				isFrame: true,
				cache : false,
				title : '导入',
				modal:true,
				showMask: false,
				width:'400',
				height:'160',
				href : BasePath + "/common_util/toImport?balanceType="+o.balanceType,
				buttons:[{
					text:'上传',
					iconCls:'icon-upload',
					handler:function(self){
						if($errorInfo.text().trim()!=""){
							return ;
						}
						$uploadForm.form('submit',{
							url : o.submitUrl,
							success:function(data){
								if(data > 0){
									if(typeof o.success=="function"){
										o.success(data);
									}
									showSuc("导入成功!");
								}else{
									if(typeof o.error=="function"){
										o.error(data);
									}
									showError("导入失败");
								}
								self.close();
							}
						});
					}
				},{
					text:'下载模板',
					iconCls:'icon-download',
					handler:function(){
						window.location.href=BasePath + '/common_util/download?fileName='+ o.templateName;
					}
				}],
				onLoad:function(win,dialog){
					$errorInfo = dialog.$('#errorInfo');
					$uploadForm = dialog.$('#uploadForm');
				}
			});
		},
		close : function(){
			dialog.panel('close');
		},
		success : function(){
			opts.success.call();
		},
		error : function(){
			opts.error.call();
		}
	};

	$.importExcel = function(options) {
		$.fn.importExcel.open(options);
	};

	$.importExcel.open = function(options) {
		var opts = $.extend({}, defaults, options);
		importExcel.open(opts);
	};

	$.importExcel.success = function(){
		return importExcel.success();
	};
	
	$.importExcel.error = function(){
		return importExcel.error();
	};
	
	$.importExcel.colse = function(){
		return importExcel.close();
	};
})(jQuery);


//初始化
$(function(){
	
});
