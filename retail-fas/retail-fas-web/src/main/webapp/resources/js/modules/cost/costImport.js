(function($) {
	var defaults = {
		submitUrl : '',
		templateName : '',
		params : {},
		success:function(result){},
		error:function(){}
	};
	var importExcel = {
		opts : {},
		dialog : null,
		open : function(o) {
			opts = o;
			var $uploadForm = null;
			dialog = ygDialog({
				isFrame: true,
				cache : false,
				title : '导入',
				modal:true,
				showMask: false,
				width:'500',
				height:'175',
				href : BasePath + "/to/import",
				buttons:[{
					text:'上传',
					iconCls:'icon-upload',
					handler:function(self){
						$.messager.progress({msg:'处理中',interval:1000}); 
						$uploadForm.submit();
					}
				},{
					text:'下载模板',
					iconCls:'icon-download',
					handler:function(){
						window.location.href=BasePath + '/download?fileName='+ o.templateName;
					}
				}],
				onLoad:function(win,dialog){
					$uploadForm = dialog.$('#uploadForm');
					$uploadForm.attr('action',o.submitUrl);
					
					dialog.$('#error-info').panel({    
						  width:488,    
						  height:200,
						  title: '提示信息',
						  collapsible : true,
						  onCollapse:function(){
							  $.importExcel.shrink(500,212);
						  },
						  onBeforeExpand:function(){
							  $.importExcel.expand(500,360);
						  }
					}); 
				}
			});
		},
		
		shrink : function(_width,_height){
			$.messager.progress('close');
			$(dialog).dialog('resize',{
				width: _width,
				height: _height
		    });
		},
		
		expand : function(_width,_height){
			$.messager.progress('close');
			$(dialog).dialog('resize',{
				width: _width,
				height: _height
		    });
		},
		
		close : function(){
			$.messager.progress('close');
			dialog.panel('close');
		},
		success : function(result){
			$.messager.progress('close');
			opts.success.call(this,result);
		},
		error : function(e){
			$.messager.progress('close');
			opts.error.call(this,e);
		}
	};
	
	$.importExcel = function(options) {
		$.fn.importExcel.open(options);
	};

	$.importExcel.open = function(options) {
		var opts = $.extend({}, defaults, options);
		importExcel.open(opts);
	};

	$.importExcel.shrink = function(_width,_height) {
		return importExcel.shrink(_width,_height);
	};
	
	$.importExcel.expand = function(_width,_height) {
		return importExcel.expand(_width,_height);
	};
	
	$.importExcel.success = function(result){
		return importExcel.success(result);
	};
	
	$.importExcel.error = function(e){
		return importExcel.error(e);
	};
	
	$.importExcel.colse = function(){
		return importExcel.close();
	};
})(jQuery);
