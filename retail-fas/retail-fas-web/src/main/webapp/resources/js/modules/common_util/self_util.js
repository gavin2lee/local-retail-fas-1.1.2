var self_util = {
	combogrid:{
		style : {
			idField:'code',
			textField:'name',
			url:BasePath + '/base_setting/item/getStyle'
		}
	},
	combobox:{
	}
};


/********************初始化方法***********************/
self_util.initPage = function(formId){
	self_util.initInputWidth(formId);
	self_util.initSingleSearch(formId); 
};

//初始化页面input宽度
self_util.initInputWidth = function(formId){
	var input = $('input[class*=ipt]');
	var default_width = 140;
	if(formId){
		input = $('#'+formId).find('input[class*=ipt]');
	}
	input.each(function(){
		var _this = $(this);
		if(_this.width() < default_width){
			if(typeof($(this).attr('selfSingleSearch'))!='undefined'){
				_this.width(140);
			}else{
				_this.width(130);
			}
		}
	});
};

/**
 * 初始化自定义页面单选查询框
 */
self_util.initSingleSearch = function(formId){
	var input = $('input[selfSingleSearch]');
	if(formId){
		input = $('#'+formId).find('input[selfSingleSearch]');
	}
	input.each(function(){
		var _this = $(this);
		var type = _this.attr("selfSingleSearch");
		var obj = self_util.combogrid[type];
		if(obj){
			var _next = _this.next();
			$(this).combogrid({
		        panelWidth:320,
			    panelHeight : 250,
		        mode:'remote',
		        idField:obj.idField,
				textField:obj.idField,
		        url : '',
		        columns:[[
		        {field:'code',title:'商品款号',width:100,align:'left'}
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
    	        	var onShowPanelFunction = _this.combo('options').onShowPanelFunction;
    	        	if(typeof(onShowPanelFunction)=='function'){
    	        		onShowPanelFunction(_this);
    	        	}else{
        	        	var url = obj.url;
        	        	if(typeof(_this.combo('options').queryUrl)!='undefined'){
        	        		url = _this.combo('options').queryUrl;
        	        	}
        	        	self_util.loadData(_this,url);
    	        	}
    	        }
		    });
		}
	});
};

/**
 * 初始化页面多选查询框
 */
self_util.initMultiSearch = function(formId){
	
};

//初始化日期控件
self_util.initDate = function(){
};

$(function(){
	self_util.initPage();
//	self_util.initDate();
//	self_util.initEditorMethod();
//	self_util.initImportMethod();
});

//载入数据
self_util.loadData = function(obj,url,options){
    if(typeof(obj.attr("notGroupLeadRole")) != 'undefined' || (options && typeof(options.notGroupLeadRole) != 'undefined')){
    	if(url.indexOf('?')!= -1){
    		url += '&params=notGroupLeadRole';
    	}else{
    		url += '?params=notGroupLeadRole';
    	}
    }else if(typeof(obj.attr("groupLeadRole")) != 'undefined' || (options && typeof(options.groupLeadRole) != 'undefined')){
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
};