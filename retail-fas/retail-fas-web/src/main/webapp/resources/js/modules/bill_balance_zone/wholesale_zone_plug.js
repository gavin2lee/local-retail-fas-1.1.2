/** 地区批发客户弹出框插件 */
(function($) {
	var _name = 'wholesale_zone_customer';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		$(jq).combogrid({
			width : options.inputWidth,
			required : options.required,
			delay: options.delay,
			panelWidth : options.panelWidth,
			panelHeight : options.panelHeight,
			url : "",
			idField : options.valueField,
			textField : options.textField,
			mode : options.mode,
			pagination : options.pagination,
			multiple : options.multiple,
			fitColumns : options.fitColumns,
			selectOnCheck : true,
	        checkOnSelect : true,
	        frozenColumns:[[{field:'ck',checkbox:true,width:30}]],
			columns:[[
               {field : 'clientNo',title : '客户编码',width : 80,align:'left'},
          	   {field : 'clientName',title : '客户全称',width : 125,align:'left'},
          	   {field : 'clientShortName',title : '客户简称',width : 100,align:'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
            	// 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections'); 
                if(rows && rows.length > 0) {
                	var dataNames = "", dataNos = "";
                	$.each(rows, function(index, item) {
                		dataNos += item[options.noField];
			    		dataNames += item[options.textField];
			    		if(index < rows.length - 1) {
			    			dataNos += ",";
			    			dataNames += ",";
			    		}
                	});
                	$("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
//                	validChange();	// 根据公司和客户控制预收款类型 	BillPrePaymentNt.js
                    if(options.callback) {
                		options.callback(rows);
                	}
                } else { //没选择数据则清空编辑框
                	_clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
            	var dg = $(jq).combogrid('grid');
            	var queryParams = $(jq).combo('options').queryParams;
            	var companyCtrl = $('#companyNoCondition');
            	queryParams.companyNo =  null;
            	if( companyCtrl.length >0  ){
            		var val = companyCtrl.val();
            		if( val )
            			queryParams.companyNo = val; 
            	}
            	
            	dg.datagrid( 'options' ).queryParams = queryParams;
            	dg.datagrid( 'options' ).url = options.queryUrl;
            	dg.datagrid( 'load' );
            }
		});
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     inputWidth : 140,
	     panelWidth: 420,
	     panelHeight : 200,
	     valueField: 'clientName',//
	     textField: 'clientName',// store表中的name
	     noField:'clientNo',
	     inputNoField: 'customerNo',// 输入字段编码no 默认‘’
	     inputNameField:'customerName',
	     company:'companyNoCondition', //company控件
	     queryUrl: BasePath + '/base_setting/invoice_info_set/getInvoiceInfoByClientType.json?clientType=2',
	     delay: 700,
	     mode : 'remote',
		 fitColumns : true,
		 pagination : true,
	     multiple : false,
	     required : false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   if(typeof data === 'string') {
		   $(jq).combogrid('setValue', data);
	   } else {
		   $(jq).combogrid('setValue', data[op.valueField]);
		   $("#" + op.inputNoField).val(data[op.noField]);
	   }
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $(jq).combogrid('getValue');
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).combogrid("disable");
   }
   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).combogrid("enable");
   }
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $(jq).combogrid('clear');
   	   $("#" + options.inputNoField).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);


//--------------选择客户模板--------------
(function($) {
	var _name = 'wholesale_zone_select_customer';
	// 自定义插件
	$.fn[_name] = function(options, param) {
	    if (typeof options == "string") {
	        return $.fn[_name].methods[options](this, param);
	    };
	    options = options || {};
	    return this.each(function(){
	        var opts = {};
	        var data = $.data(this, _name);
	        if (data) {
	            opts = $.extend(data.options, options);
	        } else {
	            opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
	            $.data(this, _name, {
	                options : opts
	            });
	        }
	        //插件渲染方法
	        render(this, opts);
	    });
	};
	
	// 页面渲染及回调设值
	function render(jq, options) {
		$(jq).combogrid({
			width : options.inputWidth,
			required : options.required,
			delay: options.delay,
			panelWidth : options.panelWidth,
			panelHeight : options.panelHeight,
			url : "",
			idField : options.valueField,
			textField : options.textField,
			mode : options.mode,
			pagination : options.pagination,
			multiple : options.multiple,
			fitColumns : options.fitColumns,
			selectOnCheck : true,
	        checkOnSelect : true,
	        frozenColumns:[[{field:'ck',checkbox:true,width:30}]],
			columns:[[
               {field : 'clientNo',title : '客户编码',width : 150,align:'left'},
          	   {field : 'clientName',title : '客户简称',width : 150,align:'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
            	// 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections'); 
                if(rows && rows.length > 0) {
                	var dataNames = "", dataNos = "";
                	$.each(rows, function(index, item) {
                		dataNos += item[options.noField]+","+item[options.textField];
			    		dataNames += item[options.textField];
			    		if(index < rows.length - 1) {
			    			dataNos += "|";
			    			dataNames += ",";
			    		}
                	});
                	$("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if(options.callback) {
                		options.callback(rows);
                	}
                } else { //没选择数据则清空编辑框
                	_clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
            	var dg = $(jq).combogrid('grid');
            	var queryParams = $(jq).combo('options').queryParams;
            	var companyCtrl = $('#companyNoCondition');
            	if( companyCtrl.length >0  ){
            		var val = companyCtrl.val();
            		if( val )
            			queryParams.companyNo = val;
            	}
            	dg.datagrid( 'options' ).queryParams = queryParams;
            	dg.datagrid( 'options' ).url = options.queryUrl;
            	dg.datagrid( 'load' );
            }
		});
    };
    
    // 默认的初始化参数
	$.fn[_name].defaults = {
	     inputWidth : 140,
	     panelWidth: 420,
	     panelHeight : 200,
	     valueField: 'clientName',//
	     textField: 'clientName',// store表中的name
	     noField:'clientNo',
	     inputNoField: 'customerNo',// 输入字段编码no 默认‘’
	     inputNameField:'customerName',
	     queryUrl: BasePath + '/base_setting/invoice_info_set/getInvoiceInfoByClientType.json?clientType=2',
	     delay: 700,
	     mode : 'remote',
		 fitColumns : true,
		 pagination : true,
	     multiple : false,
	     required : false
	 };

   // 设置值的方法
   function _setValue(jq, data) {
	   if (typeof data == 'undefined' || data == null) {
		   return;
	   }
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   if(typeof data === 'string') {
		   $(jq).combogrid('setValue', data);
	   } else {
		   $(jq).combogrid('setValue', data[op.valueField]);
		   $("#" + op.inputNoField).val(data[op.noField]);
	   }
   }
   
   // 获取插件的值
   function _getValue(jq, fieldName) {
	   var op = $.data(jq, _name).options;
	   if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
		   return $("#"+fieldName).val();
   	   }
       return $(jq).combogrid('getValue');
   };
   
   // 插件不可用的方法
   function _disable(jq) {
       $(jq).attr("readonly", true).combogrid("disable");
   }
   // 插件可用的方法
   function _enable(jq) {
       $(jq).attr("readonly", true).combogrid("enable");
   }
   
   // 清空查询精灵的方法
   function _clear(jq) {
	   var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
	   $(jq).combogrid('clear');
   	   $("#" + options.inputNoField).val("");
   };
   
   // 对外暴露的方法
   $.fn[_name].methods = {
	   options: function (jq) {
		   //return $.data(jq[0], _name).options;
		   return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
	   },
	   setValue: function (jq, data) {
		   _setValue(jq[0], data);
	   },
	   getValue: function (jq, fieldName) {
		   return _getValue(jq[0], fieldName);
	   },
	   clear: function (jq, param) {
		   $.each(jq, function (index, item) {
			   _clear(item);
		   });
	   },
	   disable: function (jq) {
		   $.each(jq, function (index, item) {
			   _disable(item);
		   });

	   },
	   enable: function (jq) {
		   $.each(jq, function (index, item) {
			   _enable(item);
		   });
	   }
   };
 
   // 将声明式定义属性data-options转化为options
   $.fn[_name].parseOptions = function(target) {
	   return $.parser.parseOptions(target, ['data']);
   };
	
   //将自定义的插件加入 easyui 的插件组
   $.parser.plugins.push(_name);
})(jQuery);

