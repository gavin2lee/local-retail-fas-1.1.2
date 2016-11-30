var iptSearch = new Object();

//初始化单选
iptSearch.initSingleSearch = function(){
	iptSearch.singleSearch($("input[singleSearch=supplier]"),'选择供应商',BasePath + '/common_util/doSelect?selectUrl=getSupplier&no=supplierNo&name=fullName');
	iptSearch.singleSearch($("input[singleSearch=company]"),'选择公司',BasePath + '/common_util/doSelect?selectUrl=getCompany&no=companyNo&name=name');
	iptSearch.singleSearch($('input[singleSearch=brandUnit]'),'选择品牌部',BasePath + '/common_util/doSelect?selectUrl=getPageBrandUnit&no=brandUnitNo&name=name');
	iptSearch.singleSearch($("input[singleSearch=brand]"),'选择品牌',BasePath + '/common_util/doSelect?selectUrl=getPageBrand&no=brandNo&name=name');
	iptSearch.singleSearch($('input[singleSearch=category]'),'选择商品大类',BasePath + '/common_util/doSelect?selectUrl=getPageCategory&no=categoryNo&name=name');
	iptSearch.singleSearch($("input[singleSearch=item]"),'选择商品',BasePath + '/common_util/doSelect?selectUrl=getItem&no=code&name=name');
};

//初始化多选组件
iptSearch.initMultiSearch = function(){
	iptSearch.multiSearch($('input[multiSearch=supplier]'),'选择供应商',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getSupplier&no=supplierNo&name=fullName');
	iptSearch.multiSearch($('input[multiSearch=company]'),'选择公司',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getCompany&no=companyNo&name=name');
	iptSearch.multiSearch($('input[multiSearch=brandUnit]'),'选择品牌部',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getPageBrandUnit&no=brandUnitNo&name=name');
	iptSearch.multiSearch($('input[multiSearch=brand]'),'选择品牌',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getPageBrand&no=brandNo&name=name');
	iptSearch.multiSearch($('input[multiSearch=category]'),'选择商品大类',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getPageCategory&no=categoryNo&name=name');
	iptSearch.multiSearch($('input[multiSearch=item]'),'选择商品',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getItem&no=code&name=name');
}; 

//初始化下拉框
iptSearch.initCombobox = function(){
	iptSearch.initCombo($("input[combobox=category]"),BasePath + '/common_util/getCategory');// 一级大类
	iptSearch.initCombo($("input[combobox=billType]"),BasePath + '/common_util/getBillType');// 单据类型
	iptSearch.initCombo($("input[combobox=currency]"),BasePath + '/common_util/getCurrency');// 币别
	iptSearch.initCombo($("input[combobox=exceptionType]"),BasePath + '/common_util/getExceptionType');// 异常类型
};

// 单选
iptSearch.singleSearch = function(obj,title,url){
	obj.each(function(){
		var name_obj = $(this);
		var no_obj =  $(this).next();
		var fliterType =  $(this).attr("fliterType");
		name_obj.iptSearch({
			clickFn : function(){
				dgSelector({
					title:title,
					href : url+'&params='+fliterType,
					width : 500,
					height : 'auto',
					fn: function(data){
						no_obj.val(data.code);
						name_obj.val(data.name);
					}
				});
			}
		});
	});
};

// 多选
iptSearch.multiSearch = function(obj, title, url) {
	obj.each(function(){
		var _this = $(this);
		var _next = _this.next();
		var fliterType =  $(this).attr("fliterType");
		_this.iptSearch({
			clickFn : function() {			
				ygDialog({
					title : title,
					href : url+'&params='+fliterType,
					width : 500,
					height : 'auto',
					isFrame : true,
					modal : true,
					showMask : true,
					buttons: [{
						id:'sure',
			            text: '确认',
			            handler: function(dialog) {
		            		var no = "";
							var name ="";
			            	var checkedRows = iptSearch.getRowData();
			            	if(typeof checkedRows == 'undefined'){
			            		checkedRows = iptSearch.getCheckRows();
								$.each(checkedRows,function(index,item){
									no += item.code+",";
									name += item.name+",";
								});
								no = no.substring(0, no.length-1);
								name = name.substring(0, name.length-1);
			            	}else{
			            		no = checkedRows.code;
			            		name = checkedRows.name;
			            	}
			            	_this.val(name);
		            		_next.val(iptSearch.multiFormatStr(no));
			            	dialog.close();
			            }
			        },
			        {
			            text: '取消',
			            handler: function(dialog) {
			                dialog.close();
			            }
			        }],
					onLoad : function(win, content) {
						iptSearch.getCheckRows = content.getCheckRows;
						iptSearch.getRowData = content.getRowData;
					}
				});
			}
		});
	})
};

//调整宽度
iptSearch.initIptSearchWidth = function(){
	var form = $("form");
	form.each(function(){
		var _thisForm = $(this);
		var _width =_thisForm.find("div[class=ipt-search-box]").width();
		if(null !=_width){
			var _input = _thisForm.find("input[type!=hidden]");
			var width = _width-10;
			_input.each(function(){
				 if(typeof($(this).attr("singleSearch"))=="undefined" && typeof($(this).attr("multiSearch"))=="undefined"){
					 if($(this).width() < width){
						 $(this).width(width);
					 }
				 }
			});
		}
	})
};

// 下拉框
iptSearch.initCombo = function(obj,url,type){
	if(obj.length == 0){
		return ;
	}
	if(typeof (obj.attr("showType")) != 'undefined'){
		url += '?showType='+obj.attr("showType");
	}
	obj.combobox({
		url:url,
		valueField:'code',
		textField:'name'
	});
};

iptSearch.getRowSelect = function(select, noField, nameField, required){
	var selectObj = iptSearch.rowSelect[select];
	selectObj.noField = noField;
	selectObj.nameField = nameField;
	if(required){
		selectObj.validatebox = {
	    	options:{
	    		required:true
	    	}
	    }
	}
	return  selectObj;
}

iptSearch.rowSelect = {
	company : {
	    clickFn:function(){
	    	var _this = this;
	    	dgSelector({
	    		title : '选择公司',
	    		width : 500,
	    		height : 'auto',
	    		href : BasePath + '/common_util/doSelect?selectUrl=getCompany&no=companyNo&name=name',
	    		fn : function(data, rowIndex) {
	    			iptSearch.selectorCallback(data, iptSearch.rowSelect['company'].noField, iptSearch.rowSelect['company'].nameField);
	    		}
	    	});
	    }
	},
	supplier : {
	    clickFn:function(){
	    	dgSelector({
	    		title : '选择供应商',
	    		width : 500,
	    		height : 'auto',
	    		href : BasePath + '/common_util/doSelect?selectUrl=getSupplier&no=supplierNo&name=fullName',
	    		fn : function(data, rowIndex) {
	    			iptSearch.selectorCallback(data, iptSearch.rowSelect['supplier'].noField, iptSearch.rowSelect['supplier'].nameField);
	    		}
	    	});
	    }
	},
	brand : {
	    clickFn:function(){
	    	dgSelector({
	    		title : '选择品牌',
	    		width : 500,
	    		height : 'auto',
	    		href : BasePath + '/common_util/doSelect?selectUrl=getPageBrand&no=brandNo&name=name',
	    		fn : function(data, rowIndex) {
	    			iptSearch.selectorCallback(data, iptSearch.rowSelect['brand'].noField, iptSearch.rowSelect['brand'].nameField);
	    		}
	    	});
	    }
	},
	category : {
	    clickFn:function(){
	    	dgSelector({
	    		title : '选择大类',
	    		width : 500,
	    		height : 'auto',
	    		href : BasePath + '/common_util/doSelect?selectUrl=getPageCategory&no=categoryNo&name=name',
	    		fn : function(data, rowIndex) {
	    			iptSearch.selectorCallback(data, iptSearch.rowSelect['category'].noField, iptSearch.rowSelect['category'].nameField);
	    		}
	    	});
	    }
	},
	item : {
	    clickFn:function(){
	    	dgSelector({
	    		title : '选择商品',
	    		width : 500,
	    		height : 'auto',
	    		href : BasePath + '/common_util/doSelect?selectUrl=getItem&no=code&name=name',
	    		fn : function(data, rowIndex) {
	    			iptSearch.selectorCallback(data, iptSearch.rowSelect['item'].noField, iptSearch.rowSelect['item'].nameField);
	    		}
	    	});
	    }
	}
}

// A,B,C -> ('A','B','C')
iptSearch.multiFormatStr = function(str){
	if(str && str.indexOf("(") != 0 && str.indexOf(")") != str.length - 1){
		var reg=new RegExp(",","g"); 
		str = str.replace(reg,"','");
		str = "('" + str + "')";
	}
	return str;
};

//('A','B','C')-> A,B,C 
iptSearch.multiParseStr = function(str){
	if(str && str.indexOf("(") == 0 && str.indexOf(")") == str.length - 1){
		var reg=new RegExp("'","g"); 
		str = str.replace(reg,"");
		str = str.substring(1,str.length - 1);
	}
	return str;
};

//选择后回调
iptSearch.selectorCallback = function(data,  noField, nameField){
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : editIndex,
		'field' : noField
	});
	var textEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : editIndex,
		'field' : nameField
	});
	valueEd.target.val(data.code);
	textEd.target.val(data.name);
};

// 币别格式化
iptSearch.currencyFormat = function(value,row,index){
	if(value && value!=''){
		if(iptSearch.currencyData[value]){
			return iptSearch.currencyData[value];
		}
		return value;
	}
};

//大类格式化
iptSearch.categoryFormat = function(value,row,index){
	if(value && value!=''){
		if(iptSearch.categoryData[value]){
			return iptSearch.categoryData[value];
		}
		return value;
	}
};

iptSearch.currencyJSONData;

iptSearch.currencyData = new Object();

iptSearch.deductionJSONData;

iptSearch.deductionData = new Object();

iptSearch.categoryJSONData;

iptSearch.categoryData = new Object();

iptSearch.initCurrencyData = function() {
	ajaxRequestAsync(BasePath + '/common_util/getCurrency',null,function(data){
		if(data.length > 0){
			iptSearch.currencyJSONData = data;
			$.each(data,function(index,item){
				iptSearch.currencyData[item.code]=item.name;
			});
		}
	});
};

iptSearch.initDeductionData = function() {
	iptSearch.deductionJSONData = [{code:'1',name:'女鞋'},{code:'2',name:'男鞋'}];
	iptSearch.deductionData = {'1':'女鞋','2':'男鞋'};
};

iptSearch.initCategoryData = function() {
	ajaxRequestAsync(BasePath + '/common_util/getCategory',null,function(data){
		if(data.length > 0){
			iptSearch.categoryJSONData = data;
			$.each(data,function(index,item){
				iptSearch.categoryData[item.code]=item.name;
			});
		}
	});
};

//币别格式化
iptSearch.deductionFormat = function(value,row,index){
	if(value && value!=''){
		if(iptSearch.deductionData[value]){
			return iptSearch.deductionData[value];
		}
		return value;
	}
};

/**
 * 导出
 * @param dataGridId  导出数据的表格ID
 * @param exportUrl 导出数据的URL  
 * @param excelTitle excel文件名
 * @param otherParams 可选参数
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

/**
 * 导入
 * @param templateName  模版名称
 * @param url 导入url  
 * @param balanceType 结算类型
 * @param successFn 成功回调函数
 * @param errorFn 失败回调函数
 */
iptSearch.doImport = function(templateName,url,balanceType,successFn,errorFn) {
	$.importExcel.open({
		'submitUrl' : BasePath + url,
		'templateName' : templateName,
		'balanceType' : balanceType,
		success : successFn,
		error : errorFn
	});
};

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
								if(data){
									if(typeof o.success=="function"){
										o.success(data);
									}else{
										showInfo("导入成功!");
									}
								}else{
									if(typeof o.error=="function"){
										o.error(data);
									}else{
										showError("导入失败!");
									}
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

iptSearch.initPage = function(){
	iptSearch.initSingleSearch(); 
	iptSearch.initMultiSearch(); 
//	iptSearch.initIptSearchWidth(); 
	iptSearch.initCombobox();
}

iptSearch.initData = function(){
	iptSearch.initCurrencyData();
	iptSearch.initDeductionData();
	iptSearch.initCategoryData();
}

//初始化
$(function(){
	iptSearch.initPage(); 
	iptSearch.initData(); 
});
