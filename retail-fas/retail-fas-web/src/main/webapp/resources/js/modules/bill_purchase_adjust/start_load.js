//键盘导航
$(function(){
	$.fn.navigation = function(options,param){
        var jq = this;
        if( typeof options == "string"){
            return $.fn.navigation.methods[options](jq,param);
        }
        if(!$(jq).hasClass('.loae')) {
        	//支持datagrid中普通控件
        	if($(jq).attr('id')=='dtlDataGrid'){
        		//是非尺码横排的datagrid
        		jq=$($(jq[0]).prev().children()[1]).children();
        	}
            $('input:text', jq).each(function (i, t) {
                bindComboGrid(jq,t);
            });
            $(jq).addClass('loae');
        }
        jq.bind($.browser.mozilla?"keyup":"keydown", function (e) {
            var target = $(e.srcElement);
            if($.browser.mozilla &&target.hasClass('.combo-text')){
                return;
            }
            var key = e.which;
            if (key == 13) {
                e.preventDefault();
                var name = $(e.srcElement).attr('name');
                //支持datagrid中普通控件
                if(!name){
					name=$(e.srcElement).parent().parent().parent().parent().parent().parent().attr('field');
				}
                if(name == 'remark' && $.isFunction(options.onLast)){
                    options.onLast(jq);
                    return;
                }
                movenext(jq,$(e.srcElement));
            }
        });
    };
    
	$.fn.navigation.methods = {
        focus: function(jq,param){
            var item = $('input:text:first',jq);
            movenext(jq,item);
        },
        selectValue:function(jq,target){
        	selectValue(jq,target);
        }
    };
	
	function movenext(jq,target){
        var $inp = $('input:text',jq);
        var nxtIdx = $inp.index(target) + 1;
        var el = null;
        while(nxtIdx<$inp.length) {
            el = $($inp[nxtIdx]);
            if(el.is(":visible")
                && !el.hasClass("readonly")
                && !el.attr("readonly")
                && !el.attr('disabled')){
                el.focus();
                return true;
            }
            nxtIdx +=1;
        }
        return false;
    }

    function selectValue(jq,target) {
        setTimeout(function () {
            movenext(jq,target);
        }, 300);
    }
    
    function bindComboGrid(jq,target){
        if($(target).hasClass('combogrid-f')){
            var options = $(target).combogrid('options');
            var enter = options.keyHandler.enter;
            var onHidePanel = options.onHidePanel;
            options.idField;//: 'storeNo',
            options.textField;//: 'shortName',
            //options.keyHandler = $.extend({},options.keyHandler);
            options.keyHandler.enter = function(event){
                var input = $(this);
                var txt = input.combogrid('getText');
                var grid = input.combogrid('grid');
                var rows = grid.datagrid('getRows');
                var key = null;
                $.each(rows, function (i, row) {
                    if (txt == row[options.textField])
                        key = row[options.idField];
                });
                if (!key && rows.length>0 ) {
                    input.combogrid('setValue', rows[0][options.idField]);
                    input.combogrid('setText', rows[0][options.textField]);
                    grid.datagrid('selectRow', 0);
                }
                else if(!key && rows.length == 0 ){
                    input.combogrid('setValue', null);
                    input.combogrid('setText', "");
                    return;
                }
                this._showpanel = false;
                input.combogrid('hidePanel');
                selectValue(jq,input.combogrid('textbox'));
                enter.call(this);
            };
            options.onHidePanel = function(){
                this._showpanel = false;
                var g = $(this).combogrid('grid'); // 获取数据表格对象
                var r = g.datagrid('getSelected');
                selectValue(jq,$(this).combogrid('textbox'));
                onHidePanel.call(this);
            };
        }else if($(target).hasClass('combobox-f')){
            var options = $(target).combo('options');
            var onHidePanel = options.onHidePanel;
            options.keyHandler = $.extend({},options.keyHandler);
            options.keyHandler.enter = function(event) {
                var val = $(this).combobox('getValue');
                if(!val){
                    var op = $(this).combobox('options');
                    var field = op.valueField;
                    var data =  $(this).combobox('getData');
                    if(data.length>0 ){
                        $(this).combobox('setValue',data[0][field]);
                    }
                }
                $(this).combo('hidePanel');
                selectValue(jq,$(this).combo('textbox'));
            };
            options.onHidePanel = function(){
                this._showpanel = false;
                selectValue(jq,$(this).combo('textbox'));
                onHidePanel.call(this);
            };
//        	$(target).combo.methods.getValue=function(jq){
//        		try{return $(jq).combobox('getValue');}catch(e){}
//        	};
//        	$(target).combo.methods.setValue=function(jq,value){
//        		try{$(jq).combobox('setValue',value);}catch(e){}
//        	};
        }else if($(target).hasClass('easyui-combobox')){
            var options = $(target).combo('options');
            $(target).combo({editable:false});
            var onHidePanel = options.onHidePanel;
            options.keyHandler = $.extend({},options.keyHandler);
            options.keyHandler.enter = function(event) {
                var val = $(this).combobox('getValue');
                if(!val){
                    var op = $(this).combobox('options');
                    var field = op.valueField;
                    var data =  $(this).combobox('getData');
                    if(data.length>0 ){
                        $(this).combobox('setValue',data[0][field]);
                    }
                }
                $(this).combo('hidePanel');
                selectValue(jq,$(this).combo('textbox'));
            };
            options.onHidePanel = function(){
                this._showpanel = false;
                selectValue(jq,$(this).combo('textbox'));
                onHidePanel.call(this);
            };
        }
    }
});

//全键盘导航
$(function(){
	$.fn.keyboard.methods = {
        focusCell: function (jq, param) {
            focusCell(jq, param.cellInex, param.rowIndex);
        },
        addKeyboard:function(jq){
        	onBeginEdit(jq, $(jq).datagrid('editIndex'));
        }
    };
	
	function focusCell(grid, cellIndex, rowIndex, isleft) {
        var data = getCellEditor(grid, rowIndex, cellIndex, isleft);
        if( !data)
            return;
        return $(grid).datagrid.methods.selectCell(grid,data);
    }
	
	function onBeginEdit(grid, rowIndex) {
        var fields = getFields(grid);
        var editIndex = rowIndex;
        $.each(fields, function (i, field) {
            var editor = grid.datagrid('getEditor', {
                'index': editIndex,
                'field': field
            });

            (function (grid, editor, field, rowIndex, index) {
                if (editor && editor.target) {
                    var parent = editor.target.parent();
                    parent.bind($.browser.mozilla?"keyup":"keydown",
                        function (event) {
	                        var data = {
	                            cellIndex: index,
	                            field: field,
	                            rowIndex: rowIndex,
	                            target: editor.target,
	                            event: event
	                        };
	
	                        onKeydown.call(grid, event, data);
	                        return true;
                    });

                }
            })(grid, editor, field, rowIndex, i);
        });
    }
	
	function getFields(grid) {
        return grid.datagrid('getFields');
    }
	
	function getCellEditor(grid, rowIndex, cellIndex, isLeft) {
        var step = isLeft ? -1 : 1;
        var fields = getFields(grid);
        var field= fields[cellIndex];
        var ed = grid.datagrid('getEditor', { index: rowIndex, field: field });
        if (canEdit(grid, ed))
            return {
                ed:ed,
                rowIndex:rowIndex,
                cellIndex:cellIndex,
                field:field
            };
        cellIndex = parseInt(cellIndex);
        var i = 0;
        while (true) {
            i++;
            if (i > 50)
                return null;
            cellIndex += step;
            if (cellIndex < 0 || cellIndex >= fields.length) {
                moveToOtherRow(grid, isLeft);
                return null;
            }
            else {
                field = fields[cellIndex];
                ed = grid.datagrid('getEditor', { index: rowIndex, field:field });
                if (canEdit(grid, ed))
                    return {
                    ed:ed,
                    rowIndex:rowIndex,
                    cellIndex:cellIndex,
                    field:field};
            }
        }
        return null;
    }
	
	function canEdit(grid, editor) {
        if (!editor || !editor.target)
            return false;
        var target = $(editor.target);
        if (target.hasClass('readonly'))
            return false;
        var col = grid.datagrid('getColumnOption', editor.field);
        if (col.hidden)
            return false;
        return true;
    }

});

//替换原油easyui扩展方法
(function ($){
	
	function _getCombo(target) {
        var form = $(target);
        var cc = $.fn.form.plugins;
        var data = {};
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('getValue') && plug.methods.hasOwnProperty('getNameValue')) {
                var editors = form.find(".easyui-" + name);
                if (editors != undefined && editors.length > 0) {
                    $.each(editors, function (i, jq) {
                        var editor = $(jq);
                        var options = editor[name]('options');
                        if (!isNotBlank(options.isNormarl)) {
                            if (isNotBlank(options.inputValueFeild)) {
                                data[options.inputValueFeild] = editor[name]('getValue');// 提交时获取no
                            } else if (isNotBlank(options.valueFeild)) {
                                data[options.valueFeild] = editor[name]('getValue');// 提交时获取no
                            }
                            if (isNotBlank(options.inputNameFeild)) {
                                data[options.inputNameFeild] = editor[name]('getNameValue');// 提交时获取name
                            } else if (isNotBlank(options.valueNameFeild)) {
                                data[options.valueNameFeild] = editor[name]('getNameValue');// 提交时获取name
                            }
                            if (isNotBlank(options.attachValueFeild)) {
                                data[options.attachValueFeild] = editor[name]('getAttachValue');// 提交时获取附加参数
                            }
                        }
                    });
                }
            }
        });

        for (var i = 0; i < cc.length; i++) {
            var type = cc[i];
            var cmbs = form.find("." + type + '-f');
            $.each(cmbs, function (i, e) {
                var c = $(e);
                var name = c.attr('comboName');
                if (!data.hasOwnProperty(name)) {
                    var val = null;
                    if (c[type]('options').multiple) {
                        val = c[type]('getValues');
                    } else {
                        val = c[type]('getValue');
                    }
                    data[name] = val;
                }
            });
        }
        return data;
    }
	
	function post(target, options) {
        options = options || {};
        var param = {};
        var form = $(target);
        var url = options.url;
        var data1 = {};
        $.each($('input,textarea,select', form), function (i, input) {
            var name = $(input).attr('name');
            if (name)
                data1[name] = $(input).val();
        });
        var data2 = _getCombo(target);
        param = $.extend(param, data1, data2);
        if (options.onSubmit) {
            param = options.onSubmit.call(target, param);
        }
        var def = $.Deferred();
        $.post(url, param).success(function (data, state, xhr) {
        	if (data) {
    			if (isNotBlank(data.errorMessage)) {
    				showError(data.errorMessage + " " + data.errorDefined);
    				def.reject();
    			} else {
    				if (data.hasOwnProperty('errorCode')) {
    					def.reject();
    				}else{
    					def.resolve(data);
    				}
    			}
    		} else {
    			showError('操作失败,请联系管理员!');
    			def.reject();
    		}
        }).error(function () {
            def.reject();
        });
        return def;
    }
	
	/**
     * 清空控件QueryParams
     */
    function clearQueryParams(target) {
    	var t = $(target);
        // 清空查询精灵
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('clearQueryParams')) {
                var editor = t.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('clearQueryParams');// 清空控件QueryParams
                }
            }
        });
    }
	
	$.fn.form.methods.post = function (jq, param) {
        return post(jq[0], param);
    };

	
	$.fn.form.methods.getCombo = function (jq) {
        return _getCombo(jq[0]);
    };
    
    $.fn.form.methods.clearQueryParams = function (jq) {
        return jq.each(function () {
        	clearQueryParams(this);
        });
    };
    
    //设置默认值 取权限数据
    function _setDefaultValue(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('setDefaultValue')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('setDefaultValue');// 设置默认值 取权限数据
                }
            }
        });
    }
    
    // daixiaowei
    function _disable(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('disable')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('disable');// 禁用控件
                }
            }
        });
    }
    
    function _enable(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('enable')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('enable');// 启用控件
                }
            }
        });
    }
    
    $.fn.form.methods.setDefaultValue = function (jq, data) {
        _setDefaultValue(jq[0], data);
    };
    
    $.fn.form.methods.disable = function (jq) {
        return _disable(jq[0]);
    };
    
    $.fn.form.methods.enable = function (jq) {
        return _enable(jq[0]);
    };
	
    $.fn.form.explugins = [];
    
    $.fn.form.plugins = ['combobox', 'combotree', 'combogrid', 'datetimebox',
                         'datebox', 'combo'];
    
})(jQuery);

//扩展dategrid方法
$(function ($){
	// 获取当前的编辑行序号
    $.fn.datagrid.methods.editIndex = function (jq,param) {
        if(param){
            $.data(jq[0],'editIndex',-1);
            return param;
        }
        return $.data(jq[0], 'editIndex');
    };
    
    $.fn.datagrid.methods.selectCell = function(jq,param){
        var grid = $(jq);
        var editor = grid.datagrid('getEditor', { index: param.rowIndex, field: param.field });
        if(!editor)
            return false;
        var target = $(editor.target);
        
        //判断是combobox就禁止手工输入 只允许下拉列表选择 输入则清掉需要重新获取焦点
        if($(target).hasClass('combobox-f')){
        	$(target).combo({
        		onChange:function(newValue, oldValue){
        			$(this).combo('clear');
            	}
        	});
//            $($(target).next().children()[0]).focus();
        }
        
        var type = $.fn.datagrid.defaults.editors[editor.type];
        if (type.hasOwnProperty('focus'))
            type.focus(target);
        else if (target.find('input select textarea').focus().select().length == 0)
            target.focus().select();
        param.editor = editor;
        var args = param;
        $(jq).trigger('selectedCell', [args]);
        var data = $.data(grid[0],'datagrid');
        if( !data.options2)
            data.options2 = {};
        data.options2.selectedCell = param;
        
        $(target.next().children()[0]).focus();
        
        return true;
    };
    
  //获取所有字段
    $.fn.datagrid.methods.getFields = function (jq) {
        var grid = $(jq);
        var fields = grid.datagrid('getColumnFields', true).concat(grid.datagrid('getColumnFields'));
        return fields;
    };
    
  //获取当前编辑行(原始值)
    $.fn.datagrid.methods.getEditRow = function(jq){
        var data = $.fn.datagrid.methods.getData(jq);
        if( !data )
            return null;
        var editIndex = $.fn.datagrid.methods.editIndex(jq);
        if( editIndex< 0 || typeof editIndex == "undefined")
            return null;
        return data.rows[editIndex];
    };
    
    $.fn.datagrid.methods.bindColumnsOptions = bindColumnsOptions;
    
    //将grid绑定到没一个列的options上，便于editor查找当前grid
    function bindColumnsOptions(jq){
        var options = $(jq).datagrid('options');
        //暂时注释 重复保存获取不到grid
//        if( options.__flag)
//            return;
        var columns = $(jq).datagrid("getColumns", "all");
        for(var i=0;i<columns.length;i++){
            var col = columns[i];
            var editor = col.editor;
            if( editor && editor.type){
                if( editor.options == null)
                    editor.options ={};
                editor.options.grid = $(jq);
                editor.options.column = col;
            }
        }
        options.__flag = true;
    }
    
    /**
     * 扩展的编辑器对象 商品选择下拉网格编辑器插件
     */
    function isPersistence(target) {
    	var data = $.data($(target)[0], 'persistence');
    	return data ? true : false;
    }

    var selectEditor = {
    		destroy : function(target) {
    			if (!isPersistence(target)) {
    				$(target).combogrid('destroy');
    			}
    		},
    		getValue : function(target) {
    			if (isPersistence(target))
    				return $(target).val();
    			return $(target).combogrid('getValue');
    		},
    		setValue : function(target, value) {
    			$.data($(target)[0], 'oldValue', value);
    			if (isPersistence(target))
    				$(target).val(value);
    			else
    				$(target).combogrid('setValue', value);
    		},
    		resize : function(target, width) {
    			if (isPersistence(target)) {
    				$(target)._outerWidth(width)._outerHeight(22);
    				$(target).parent()._outerWidth(width);
    				return;
    			}
    			$(target).combogrid('resize', width);
    		},
    		focus : function(target) {
    			if (isPersistence(target))
    				return false;
    			$(target).parent().find('.combo-text').focus().select();
    		}
    	};

    var itemEditor = $.extend({
    	init : function(container, options) {
    		var parent = container.parent();
    		var input = $('<input type="text" class="datagrid-editable-input">');
    		input.appendTo(container);
    		if (options.grid.datagrid('getEditRow').isPersistence) {
    			$.data(input[0], 'persistence', true);
    			return input;
    		}
    		
    		// 过滤头挡的供应商 notGetSupplier:true 不过滤供应商 false过滤供应商 默认过滤
    		var supplierNo = "";
    		if(!options.notGetSupplier){
    			try {
    				supplierNo = $.fn["supplier"].methods.getValue($("#supplierNo"));
    			} catch (e) {
    				try {
    					supplierNo = $.fn["supplier"].methods.getValue($("#supplierName"));
    				} catch (e) {
    					try {
    						supplierNo = $.fn["supplierBox"].methods.getValue($("#supplierNo"));
    					} catch (e) {
    						try {
    							supplierNo = $.fn["supplierBox"].methods.getValue($("#supplierName"));
    						} catch (e) {
    							try{
    								supplierNo=$("#supplierNo").val();
    							}catch(e){
    								supplierNo = '';
    							}
    							
    						}
    					}
    				}
    			}
    		}
    		
    		//TASK #1347 [收购]类型的到货单 不需要根据供应商过滤 商品
    		var bizType;
    		try {
    			bizType = $('#bizType').combobox('getValue');
    		} catch (e) {
    			try{
    				bizType=$("#bizType").val();
    			}catch(e){
    				bizType = '';
    			}
    		}
    		if (typeof bizType != 'undefined' && bizType == '33') {
    			supplierNo = '';
    		}
    		if (typeof supplierNo == 'undefined' || 'undefined' == supplierNo) {
    			supplierNo = '';
    		}
    		// 过滤头挡的shopNo
    		var isNotFrom="N";
    		var shopNo = "";
    		if(!options.notGetShopNo){
    			shopNo = $("#shopNoFrom").val();
    			if (typeof shopNo == 'undefined' || 'undefined' == shopNo || shopNo=="") {
    				shopNo = $("#shopNo").val();
    				if(isNotBlank(shopNo)){
    					isNotFrom="Y";
    				}
    				if (typeof shopNo == 'undefined' || 'undefined' == shopNo) {
    					shopNo = '';
    				}
    			}
    		}
    		
    		// 过滤头挡的storeNo
    		var storeNo = "";
    		if(!options.notGetStoreNo){
    			storeNo = $("#storeNoFrom").val();
    			if(isNotFrom=="Y"){//如果前面店是from 后面的仓取非from 如果店非from 那么仓要取from的值
    				storeNo = $("#storeNo").val();
    			}
    			if (typeof storeNo == 'undefined' || 'undefined' == storeNo || storeNo=="") {
    				storeNo = $("#storeNoFrom").val();
    				if (typeof storeNo == 'undefined' || 'undefined' == storeNo || storeNo=="") {
    					storeNo = $("#storeNo").val();
    					if (typeof storeNo == 'undefined' || 'undefined' == storeNo) {
    						storeNo = $("#saleStoreNo").val();//客残销售出库单
    						if (typeof storeNo == 'undefined' || 'undefined' == storeNo) {
    							storeNo = '';
    						}
    					}
    				}
    			}
    		}
    		
    		// 过滤头挡的orderUnitNo 通过storeNo和orderUnitNo获取brandNo过滤商品
    		var orderUnitNo = "";
    		if(!options.notGetOrderUnitNo){
    			try{
    				orderUnitNo = $.fn['orderUnitBox'].methods.getValue($('#orderUnitNoFrom'));
    			}catch(e){
    				try{
    					orderUnitNo = $.fn['orderUnitBox'].methods.getValue($('#orderUnitNo'));
    				}catch(e){}
    			}
    			if (!isNotBlank(orderUnitNo)) {
    				orderUnitNo = $("#orderUnitNoFrom").val();
    				if (!isNotBlank(orderUnitNo)) {
    					orderUnitNo = $('#orderUnitNo').val();
    					if (!isNotBlank(orderUnitNo)) {
    						orderUnitNo = $('#saleOrderUnitNo').val();
    						if (!isNotBlank(orderUnitNo)) {
    							orderUnitNo = '';
    						}
    					}
    				}
    			}
    		}
    		
    		var keyHandler = {
    			up : function(event) {
    				event.originalEvent.canceled = input._showpanel;
    				$.fn.combogrid.defaults.keyHandler['up'].call(this, event);
    				event.stopPropagation();
    			},
    			down : function(event) {
    				event.originalEvent.canceled = input._showpanel;
    				$.fn.combogrid.defaults.keyHandler['down'].call(this, event);
    				event.stopPropagation();
    			},
    			enter : function(event) {
    				var val = input.combogrid('getValue');
    				var txt = input.combogrid('getText');
    				var grid = input.combogrid('grid');
    				var rows = grid.datagrid('getRows');
    				var code = null;
    				$.each(rows, function(i, row) {
    					if (row.code == val)
    						code = row.code;
    				});
    				if (!code && rows.length > 0) {
    					input.combogrid('setValue', rows[0].code);
    					input.combogrid('setText', rows[0].code);
    					grid.datagrid('selectRow', 0);
    				} else if (!code && rows.length == 0) {
    					input.combogrid('setValue', null);
    					input.combogrid('setText', "");
    					return;
    				}

    				var r = grid.datagrid('getSelected'); // 获取选择的行
    				input._showpanel = false;
    				input.combogrid('hidePanel');
    				selectedValue(r);
    			}
    		};
    		keyHandler = $.extend({}, $.fn.combogrid.defaults.keyHandler, keyHandler);

    		function selectedValue(r) {
    			var oldValue = $.data(input[0], 'oldValue');
    			if (r && r.itemNo && oldValue != r.code) {
    				var data = $.extend(true, {}, r);// 设置data
    				options.clickFn(data, input);
    			}

    			// 下拉网格没选择数据（判断文本搜索框中的数据没有被修改则不清空）则清空编辑框
    			if (!r && input.parent().find("span").children("input").val() != oldValue) {
    				input.combogrid('clear');//没选择数据则清空编辑框
    			}

    			if (!r && !input.combogrid('getValue'))
    				return;
    			var rowIndex = options.grid.datagrid('editIndex');
    			var field = options.column.field;
    			var fields = options.grid.datagrid('getFields');
    			var index = fields.indexOf(field) + 1;
    			index = Math.max(index, 1);
    			setTimeout(function() {
    				$.fn.keyboard.methods.focusCell(options.grid, {
    					rowIndex : rowIndex,
    					cellInex : index
    				});
    			}, 10);
    		}
    		
    		var urlCondition='&supplierNo=' + supplierNo + '&shopNo=' + shopNo + '&storeNo=' + storeNo + '&orderUnitNo=' + orderUnitNo;
    		//商品查询条件 大类
    		if(isNotBlank(options.rootCategoryNo)){
    			urlCondition+="&rootCategoryNo="+options.rootCategoryNo;
    		}
    		if($("#brandNo").length > 0){
    			urlCondition+="&brandNo="+$("#brandNo").val();
    		}
    		
    		var dataOp = $.extend({}, options, {
    			loadMsg:'数据加载中，请稍等......',
    			delay : 700,// 延迟
    			pageSize : 100,//在设置分页属性的时候初始化页面大小。
    			pageList : [ 100, 500, 1000 ],//在设置分页属性的时候 初始化页面大小选择列表。
    			mode : 'remote',
    			panelWidth : 370,
    			panelHeight : 200,
    			idField : 'code',
    			textField : 'code',
    			url : BasePath + '/base_setting/item/chooseItem.json?1=1'+urlCondition,
    			columns : [ [ {
    				field : 'code',
    				title : '商品编码',
    				width : 150,
    				sortable : true
    			}, {
    				field : 'name',
    				title : '商品名称',
    				width : 200,
    				align : 'left',
    				sortable : true
    			} ] ],
    			filter : function(q, row) {
    				var opts = $(this).combogrid('options');
    				return row[opts.idField].indexOf(q) == 0;
    			},
    			fitColumns : true,
    			onShowPanel : function() {
    				input._showpanel = true;
    				
    				//清除brandNo
    				delete input.combogrid("grid").datagrid('options').queryParams.brandNo;//清除
    				delete input.combogrid("options").queryParams.brandNo;//清除
    				
    			},
    			onHidePanel : function() {// 选中下拉项后关闭面板，填充选中网格行数据itemCodeitemNobrandNo....
    				input._showpanel = false;
    				var g = input.combogrid('grid'); // 获取数据表格对象
    				var r = g.datagrid('getSelected');
    				selectedValue(r);
    			},
    			pagination : false,//商品下拉控件不分页
    			hasDownArrow:false,//定义是否显示向下箭头按钮。
    			keyHandler : keyHandler
    		});
    		if (dataOp.grid.datagrid('getEditRow').isPersistence){
    			options.disabled = true;
    			input.attr("readonly", true).iptSearch("disable");
    		}else{
    			options.disabled = false;
    		}
    		input.combogrid(dataOp);
    		if (dataOp.isRequired) {
    			input.combo({
    				required : true,
    				tipPosition:'none'
    			});
    		}
    		
    		//***********************************
    		//加查询精灵
    		var iptOption = $.extend({}, options, {
    			title : '选择商品',
    			width : 900,
    			height : 500,
    			isFrame : false,
    			href : BasePath + '/search_dialog/item',
    			queryUrl : BasePath + '/base_setting/item/list.json?1=1'+urlCondition,
    			//params : params,
    			fn : function(data) {
    				//必须 否则datagrid无法获取itemCode
    				input.parent().children("span").children("input").val(data.code);
    				selectedValue(data);
    			}
    		});
    		
    		// 查询精灵
    		input.iptSearch({
    			width:93,
    			clickFn : function() {
    				dgSelector(iptOption);
    			}
    		});
    		
    		return input;
    	}
    }, selectEditor);
    $.fn.datagrid.defaults.editors.itemEditor = itemEditor;
});

//单据查询表单在普通文本编辑框中回车查询
$(function(){
    $.fn.enterSearch = function(options,param){
        var jq = this;
        if( typeof options == "string"){
            return $.fn.navigation.methods[options](jq,param);
        }
        if(!$(jq).hasClass('enterSearch')) {
        	jq.bind($.browser.mozilla?"keyup":"keydown", function (e) {
                var target = $(e.srcElement);
                if(target.hasClass('validatebox-text')){
                	var key = e.which;
                    if (key == 13) {
                        e.preventDefault();
                        if(ctrl){
                        	ctrl.search();
                        }
                    }
                }
            });
            $(jq).addClass('enterSearch');
        }
    };
    
    $.fn.enterSearch.methods = {
    };
});

var fas = {};
fas.common = {};

//非尺码横排结束行编辑并且校验（没有输入商品的跳过校验）
fas.common.endEditing = function (datagridId,index) {
    var dg = $('#' + datagridId);
    var index=index||dg.datagrid('editIndex');
    if (index == undefined ||index==-1) {
        return true;
    }
    var itemED = dg.datagrid('getEditor', {
        'index': index,
        'field': 'itemCode'
    });
    var selectEditors = dg.datagrid('getEditors', index);
    var countEDs = [];
    $.each(selectEditors, function (i, item) {
        if (item.field.indexOf('Qty') > 0) {
            countEDs.push(item);
        }
    });
    if (itemED && ( itemED.target.val() == "" ) && itemED.oldHtml == "") {
        // 如果还没用选择商品就自动删掉当前编辑的行
        dg.datagrid('deleteRow', index);
        dg.datagrid('editIndex',-1);
    } else {
        // 校验网格行中的必填项
        if (dg.datagrid('validateRow', index)) {
            dg.datagrid('endEdit', index);
            dg.datagrid('editIndex',-1);
            dg.datagrid('unselectRow',index);
            return true;
        } else {
            showSuc("单据明细数据验证没有通过！");// 验证不通过时弹出提示框
            return false;
        }
    }
    return true;
};

//判断页面表单是否被修改 给提示 校验通过返回false 不通过返回true datagridId:要检测的datagrid fromId:要检测的form
fas.common.formDirty = function (datagridId, fromId) {
	if($.browser.mozilla){
		return false;
	}
    if (!fromId) {
        fromId = "mainDataForm";
    }
    if (!datagridId) {
        datagridId = "dtlDataGrid";
    }
    if (formIsDirty(document.forms[fromId])) {
        showWarn("页面数据已修改，请保存！");
        return true;
    } else {
        var datas = $("#" + datagridId).datagrid('getChanges');
        if (datas && datas.length > 0) {
            showWarn("页面数据已修改，请保存！");
            return true;
        }
        return false;
    }
};

//非尺码横排新增行添加校验
fas.common.validateGrid = function (datagridId, rowIndex) {
    var dg = $('#' + datagridId);
    var eds = dg.datagrid('getEditors', rowIndex);
    if (eds != null && eds.length > 0) {
        var qtyEditors = [];
        $.each(eds, function (i, item) {
            if (item.field.indexOf('Qty') > 0) {
                qtyEditors.push(item);
            }
        });
        if (qtyEditors != null && qtyEditors.length > 0) {
            $.each(qtyEditors, function (j, item) {
                var ele = dg.datagrid('getEditor', {
                    'index': rowIndex,
                    'field': item.field
                });
                if (ele) {
                    $(ele.target).bind("keyup", function () {
                        var tempValue = item.target.val();
                        if (tempValue != null && tempValue != '') {
                            if (tempValue != '-') {
                                if (isNaN(tempValue)) {
                                    item.target.val("");
                                    showWarn("请输入数字！");
                                } else {
                                    var tempIntValue = parseInt(tempValue);
                                    if (tempIntValue > 2147483648 || tempIntValue < -2147483648) {
                                        item.target.val("");
                                        showWarn("请输入合法的数量！");
                                    } else {
                                        item.target.val(tempIntValue);
                                    }
                                }
                            }
                        }
                    });
                }
            });
        }
    }
};

//非尺码横排加键盘导航
fas.common.addNavigation=function(){
	var grid=$("#dtlDataGrid");
	setTimeout(function(){
		//支持通用的编辑器键盘导航
//		grid.navigation({});
		//支持datagrid中键盘导航
		grid.keyboard({
			onLast: function(){//定义datagrid编辑到一行最后一个编辑器后调用的函数
				ctrl.addDetail();
				var field = grid.datagrid('getFields')[0];
		        setTimeout(function () {
		            grid.datagrid('selectCell', {
		                rowIndex: grid.datagrid('editIndex'),
		                field: field
		            });
		        }, 1000);
			},
            keys: {
                'Ctrl+C': function (event) {
                    var rows = grid.datagrid('getSelections');
                    gms.env.setData('copiedRows',rows);
                }
            }
		});
		//支持datagrid中键盘导航 调用该函数初始化
		grid.keyboard('addKeyboard');
		
	},500);
};

/*
 * 检查单据明细是否有数据 @params pkValue 单据编码 @params dtlModulePath 明细Controller对应的Url
 */
fas.common.checkHasDetails = function (pkValue, dtlModulePath,shardingFlag) {
    var result = fas.common.getDetailDatas(pkValue, dtlModulePath,shardingFlag);
    if (!result) {
        showWarn("单据" + pkValue + "没有添加明细，不能操作！");
    }
    return result;
};

//检查单据是否有明细
fas.common.getDetailDatas = function (pkValue, dtlModulePath,shardingFlag) {
    if (!pkValue) {
        return false;
    }
    var url = dtlModulePath + "get_count.json";
    var params = new Object();
    params['billNo'] = pkValue;
    params['shardingFlag'] = shardingFlag;
    var status = true;
    ajaxRequestAsync(url, params, function (totalData) {
        total = parseInt(totalData, 10);
        if (total <= 0) {
            status = false;
        }
    });
    return status;
};

//检查单据是否有明细
fas.common.getDetailDatas = function (pkValue, dtlModulePath,shardingFlag) {
    if (!pkValue) {
        return false;
    }
    var url = dtlModulePath + "get_count.json";
    var params = new Object();
    params['billNo'] = pkValue;
    params['shardingFlag'] = shardingFlag;
    var status = true;
    ajaxRequestAsync(url, params, function (totalData) {
        total = parseInt(totalData, 10);
        if (total <= 0) {
            status = false;
        }
    });
    return status;
};


/*
 * 检查单据明细是否有数据 @params pkValue 单据编码 @params dtlModulePath 明细Controller对应的Url
 */
fas.common.checkHasDetails = function (pkValue, dtlModulePath,shardingFlag) {
    var result = fas.common.getDetailDatas(pkValue, dtlModulePath,shardingFlag);
    if (!result) {
        showWarn("单据" + pkValue + "没有添加明细，不能操作！");
    }
    return result;
};

fas.common.formDirtyNoAlert = function (datagridId, fromId) {
    if (!fromId) {
        fromId = "mainDataForm";
    }
    if (!datagridId) {
        datagridId = "dtlDataGrid";
    }
    if (formIsDirty(document.forms[fromId])) {
        return true;
    } else {
    	var grid=$("#" + datagridId);
    	if(grid){
    		try{
    			var datas = grid.datagrid('getChanges');
                if (datas && datas.length > 0) {
                    return true;
                }
    		}catch(e){}
    	}
        return false;
    }
};



fas.data = {};

//商品查询也弹出框，商品状态数据
fas.data.itemStatusData = [ {
	"value" : "0",
	"text" : "禁用"
}, {
	"value" : "1",
	"text" : "正常"
}, {
	"value" : "2",
	"text" : "临时"
} ];

var billStatusEnums = {
		MAKEBILL : 0,//制单
		CONFIRM : 1//确认
};
