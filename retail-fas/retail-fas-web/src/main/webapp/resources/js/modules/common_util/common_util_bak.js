var common_util = {
		combogrid : {
		    company  : {idField:'code',
				textField:'name',
				url:BasePath + '/common_util/getCompany'
			}, 
			dataAccess_company  : {idField:'code',
				textField:'name',
				url:BasePath + "/common_util/getCompany?dataAccess=0"
			}, 
			supplier : {idField:'code',
						textField:'name',
						url:BasePath + '/common_util/getSupplier'
			}, 
			customer : {idField:'code',
				textField:'name',
				url:BasePath + '/common_util/getCustomer'
			}, 
			organization : {idField:'code',
							textField:'name',
							url:BasePath + '/common_util/getPageOrganization'
			}, 	
			item 	 : {idField:'code',
						textField:'name',
						url:BasePath + '/common_util/getItem'
			},
			brand 	 : {idField:'code',
						textField:'name',
						url:BasePath + '/common_util/getPageBrand'
			}, 
			brandUnit : {idField:'code',
						 textField:'name',
						 url:BasePath + '/common_util/getPageBrandUnit'
			},
			category : {idField:'code',
						textField:'name',
						url:BasePath + '/common_util/getPageCategory?levelid=1'
			},
			twoLevelCategory : {idField:'code',
				textField:'name',
				url:BasePath + '/common_util/getPageCategory?levelid=2'
			},
			organ : {idField:'code',
				textField:'name',
				url:BasePath + '/common_util/getPageOrgan?organLevel=1'
			},
			orderUnit : {idField:'code',
				textField:'name',
				url:BasePath + '/common_util/getPageOrderUnit'
			},
			financialCategory :{idField:'code',
							    textField:'name',
							    url:BasePath + '/common_util/getPageFasCategory?status=1'
			}
		},
		combobox : {
			billType : {idField:'code',
						textField:'name',
						url:BasePath + '/common_util/getBillType'
			},
			currency : {idField:'code',
						textField:'name',
						url:BasePath + '/common_util/getCurrency'
			},
			balanceStatus : {idField:'code',
							 textField:'name',
							 url:BasePath + '/common_util/getBalanceStatus'
			},
			status :{idField:'code',
					 textField:'name',
					 url:BasePath + '/common_util/getStatus'
			},
			auditStatus :{idField:'code',
						  textField:'name',
						  url:BasePath + '/common_util/getAuditStatus'
			},
			settleMethod :{idField:'code',
						   textField:'name',
						   url:BasePath + '/common_util/getSettleMethod'
			},
			reportBizType : {idField:'code',
							textField:'name',
							url:BasePath + '/common_util/getReportBizType'
			},
			orderType : {idField:'code',
							textField:'name',
							url:BasePath + '/common_util/getLookup?lookupId=29'
			},
			gender : {idField:'code',
							textField:'name',
							url:BasePath + '/common_util/getLookup?lookupId=7'
			},
			balanceType :{idField:'code',
						  textField:'name',
						  url:BasePath + '/common_util/getBalanceType'
			}
		}
}

$.extend($.fn.validatebox.defaults.rules, {    
	editor_effective: {    
        validator: function(value, param){ 
          return common_util.getEditorFieldValue(param[0], param[1]) !='';
        },    
        message: '无效数据!'   
    }
});  






$.extend($.fn.datagrid.defaults.editors, {
	test_combogrid: {
	        init: function(container, options){
	            var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
	            var obj = common_util.combogrid[options.type];
	            if(obj){
	        		input.combogrid({
	        	        panelWidth:400,
	        		    panelHeight : 200,
	        	        mode:'remote',
	        	        idField:obj.idField,
	        	        textField:obj.textField,
	        	        required : options.required,
	        	        url : '',
	        	        columns:[[
	        	        {field:'code',title:'编码',width:100,align:'left',halign:'center'},
	        	        {field:'name',title:'名称',width:250,align:'left',halign:'center'}
	        	        ]],
	        	        onChange:function(newVal,oldVal){
	        	        	var row = input.combogrid('grid').datagrid('getSelected');
	        	        	var callback = input.combo('options').callback;
	        	        	if(callback){
	        	        		callback(row);
	        	        	}else{
	        	        		if(row){
	        	        			common_util.setEditorValue(options.datagridId, options.valueField, row.code);
	        	        		}else{
	        	        			common_util.setEditorValue(options.datagridId, options.valueField, '');
	        	        		}
	        	        	}
	        	        
	        	        },
	        	        onShowPanel:function(){
	        	        	var url = obj.url;
	        	        	if(typeof(options.notGroupLeadRole) != 'undefined' ){
	        	            	if(url.indexOf('?')!= -1){
	        	            		url += '&params=notGroupLeadRole';
	        	            	}else{
	        	            		url += '?params=notGroupLeadRole';
	        	            	}
	        	            }else if(typeof(options.groupLeadRole) != 'undefined' ){
	        	            	if(url.indexOf('?')!= -1){
	        	            		url += '&params=groupLeadRole';
	        	            	}else{
	        	            		url += '?params=groupLeadRole';
	        	            	}
	        	            }
	        	        	//客户类型
	        	        	if(typeof(options.customerType) != 'undefined' ){
	                			if(options.customerType == 'customer'){
	                				if(url.indexOf('?')!= -1){
	                					url += '&customerType=2';
	                				}else {
	                					url += '?customerType=2';
	                				}
	                			}
	                		}
	        	        	common_util.loadData(input,url);
	        	        }
	        	    });
	            }
	            return input;
	        },
	        destroy: function(target){
	            $(target).combogrid('destroy');
	        },
	        getValue: function(target){
	            return $(target).combogrid('getText');
	        },
	        setValue: function(target, value){
	            $(target).combogrid('setValue', value);
	        },
	        resize: function(target, width){
	            $(target).combogrid('resize',width);
	        }
	    }
});

// 设置ID值
common_util.setEditorValue = function(datagridId, valueField, value){
	if(datagridId && valueField){
		var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
    	var edValue =$('#'+datagridId).datagrid('getEditor',{index:editIndex, field:valueField});
    	edValue.target.val(value);
	}
}

// 获取editor字段值
common_util.getEditorFieldValue = function(datagridId, valueField){
	if(datagridId && valueField){
		var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
    	var edValue =$('#'+datagridId).datagrid('getEditor',{index:editIndex, field:valueField});
    	return  edValue.target.val();
	}
	return '';
}

//初始化单选
common_util.initSingleSearch = function(formId){
	var input = $('input[singleSearch]');
	if(formId){
		input = $('#'+formId).find('input[singleSearch]');
	}
	input.each(function(){
		var _this = $(this);
		var obj = common_util.combogrid[_this.attr("singleSearch")];
		if(obj){
			var _next = _this.next()
			$(this).combogrid({
		        panelWidth:460,
			    panelHeight : 250,
		        mode:'remote',
		        idField:obj.textField,
		        textField:obj.textField,
		        url : '',
		        columns:[[
		        {field:'code',title:'编码',width:150},
		        {field:'name',title:'名称',width:260,align:'left'}
		        ]],
    	        onHidePanel:function(){
    	        	var row = _this.combogrid('grid').datagrid('getSelected');
		        	var callback = _this.combo('options').callback;
		        	if(callback){
		        		callback(row,_this,_next);
		        	}else{
		        		if(row){
					        _next.val(row.code);
			        	}else{
			        		_next.val('');
			        		_this.combogrid('clear');
			        	}
		        	}
    	        },
    	        onShowPanel:function(){
    	        	common_util.loadData(_this,obj.url);
    	        }
		    });
		}
	});
};

//载入数据
common_util.loadData = function(obj,url){
    if(typeof(obj.attr("notGroupLeadRole")) != 'undefined'){
    	if(url.indexOf('?')!= -1){
    		url += '&params=notGroupLeadRole';
    	}else{
    		url += '?params=notGroupLeadRole';
    	}
    }else if(typeof(obj.attr("groupLeadRole")) != 'undefined' ){
    	if(url.indexOf('?')!= -1){
    		url += '&params=groupLeadRole';
    	}else{
    		url += '?params=groupLeadRole';
    	}
    }
	var dg = obj.combogrid('grid');
	var queryParams = obj.combo('options').queryParams;
	dg.datagrid( 'options' ).queryParams = queryParams;
	dg.datagrid( 'options' ).url = url;
	dg.datagrid( 'load' );
}

//初始化多选
common_util.initMultiSearch = function(formId){
	var input = $('input[multiSearch]');
	if(formId){
		input = $('#'+formId).find('input[multiSearch]');
	}
	input.each(function(){
		var _this = $(this);
		var _next = _this.next();
		var obj = common_util.combogrid[_this.attr("multiSearch")];
		if(obj){
			$(this).combogrid({
		        panelWidth:460,
			    panelHeight : 250,
		        mode:'remote',
		        idField:obj.idField,
		        textField:obj.textField,
		        url : '',
		    	multiple:true,
		        separator:',',
		        selectOnCheck : true,
		        checkOnSelect : true,
		        frozenColumns:[[{field:'ck',checkbox:true,width:30}]],
		        columns:[[
		        {field:'code',title:'编码',width:150},
		        {field:'name',title:'名称',width:260,align:'left'}
		        ]],
    	        onHidePanel:function(){
		        	var rows = _this.combogrid('grid').datagrid('getSelections');
		        	var callback = _this.combo('options').callback;
		        	if(callback){
		        		callback(rows,_this,_next);
		        	}else{
		        		if(rows.length > 0){
		        			_next.val(common_util.multiFormat(_this.combogrid('getValues')));
			        	}else{
			        		_next.val('');
			        		_this.combogrid('clear');
			        	}
		        	}
    	        	_this.combo('validate');
    	        },
    	        onShowPanel:function(){
    	        	common_util.loadData(_this,obj.url);
    	        },
		        validType:_this.attr('validType')
		    });
		}
	});
}; 

//初始化下拉框
common_util.initCombobox = function(formId){
	var input = $('input[combobox]');
	if(formId){
		input = $('#'+formId).find('input[combobox]');
	}
	input.each(function(){
		var _this = $(this);
		var obj = common_util.combobox[_this.attr("combobox")];
		if(obj){
			var url = obj.url;
			if(_this.attr('showType')){
				url += '?showType='+_this.attr('showType');
			}
			if(_this.attr('fliterType')){
				url += '?fliterType='+_this.attr('fliterType');
			}
			$(this).combobox({
				url:url,
				valueField:obj.idField,
				textField:obj.textField,
				editable:false
			});
		}
	});
};

//初始化页面input宽度
common_util.initInputWidth = function(formId){
	var input = $('input[class*=ipt]');
	var default_width = 160;
	if(formId){
		input = $('#'+formId).find('input[class*=ipt]');
	}
	input.each(function(){
		var _this = $(this);
		if(_this.width() < default_width){
			if(typeof($(this).attr('singleSearch'))!='undefined' 
				|| typeof($(this).attr('multiSearch'))!='undefined'
					|| typeof($(this).attr('combobox'))!='undefined'){
				_this.width(160);
			}else{
				_this.width(150);
			}
		}
	});
}

//币别格式化
common_util.currencyFormat = function(value,row,index){
	if(value && value!=''){
		if(common_util.currencyData[value]){
			return common_util.currencyData[value];
		}
		return value;
	}
};

//大类格式化
common_util.categoryFormat = function(value,row,index){
	if(value && value!=''){
		if(common_util.categoryData[value]){
			return common_util.categoryData[value];
		}
		return value;
	}
};

//扣项分类格式化
common_util.deductionFormat = function(value,row,index){
	if(value && value!=''){
		if(common_util.deductionData[value]){
			return common_util.deductionData[value];
		}
		return value;
	}
};

common_util.currencyJSONData;

common_util.currencyData = new Object();

common_util.deductionJSONData;

common_util.deductionData = new Object();

common_util.settleMethodData = new Object();

common_util.settleMethodJsonData = new Object();

common_util.initCurrencyData = function() {
	ajaxRequestAsync(BasePath + '/common_util/getCurrency',null,function(data){
		if(data.length > 0){
			common_util.currencyJSONData = data;
			$.each(data,function(index,item){
				common_util.currencyData[item.code]=item.name;
			});
		}
	});
};

common_util.initSettleMethodData = function() {
	ajaxRequestAsync(BasePath + '/common_util/getSettleMethod',null,function(data){
		if(data.length > 0){
			common_util.settleMethodJsonData = data;
			$.each(data,function(index,item){
				common_util.settleMethodData[item.code]=item.name;
			});
		}
	});
};

common_util.initDeductionData = function() {
	common_util.deductionJSONData = [{code:'1',name:'女鞋'},{code:'2',name:'男鞋'},{code:'3',name:'其他'}];
	common_util.deductionData = {'1':'女鞋','2':'男鞋','3':'其他'};
};


// 初始化页面组件
common_util.initPage = function(formId){
	common_util.initInputWidth(formId);
	common_util.initSingleSearch(formId); 
	common_util.initMultiSearch(formId); 
	common_util.initCombobox(formId);
}

//初始化页面数据
common_util.initData = function(){
	common_util.initDeductionData();
	common_util.initCurrencyData();
	common_util.initSettleMethodData();
}

//A,B,C -> ('A','B','C')
common_util.multiFormat = function(str){
	str = str.toString();
	if(str && str.indexOf("(") != 0 && str.indexOf(")") != str.length - 1){
		var reg=new RegExp(",","g"); 
		str = str.replace(reg,"','");
		str = "('" + str + "')";
	}
	return str;
};

//('A','B','C')-> A,B,C 
common_util.multiParse = function(str){
	if(str && str.indexOf("(") == 0 && str.indexOf(")") == str.length - 1){
		var reg=new RegExp("'","g"); 
		str = str.replace(reg,"");
		str = str.substring(1,str.length - 1);
	}
	return str;
};


/**
 * 导出
 * @param dataGridId  导出数据的表格ID
 * @param exportUrl 导出数据的URL  
 * @param excelTitle excel文件名
 * @param otherParams 可选参数
 */
common_util.doExport = function (dataGridId, exportUrl, excelTitle, otherParams){
	var $dg = $('#'+dataGridId);
	var params=$dg.datagrid('options').queryParams;
	var columns=$dg.datagrid('options').columns;
	var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
	var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录
	var columnsNew = [];
	var columnsOld = new Array();
	columnsOld.push(columns[columns.length-1]);
	$.each(columnsOld,function(index,item){
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
				param.exportFlag = true;
				
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
common_util.doImport = function(templateName,url,balanceType,successFn,errorFn) {
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


/**
 * 导出
 * @param dataGridId  导出数据的表格ID
 * @param exportUrl 导出数据的URL  
 * @param excelTitle excel文件名
 * @param otherParams 可选参数
 */
common_util.doExportBill = function (exportFields, dataGridId, exportUrl, excelTitle, otherParams){
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
			v_object.exportType = rowData.exportType;
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
				param.exportFields=JSON.stringify(exportFields);
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

$(function(){
	common_util.initPage();
	common_util.initData();
});







