var SearchAndCombobox = new Object();

// 单选
SearchAndCombobox.singleSearch = function(obj,title,url){
	obj.each(function(){
		var name_obj = $(this);
		var no_obj =  $(this).next();
		name_obj.iptSearch({
			clickFn : function(){
				dgSelector({
					title:title,
					href : url,
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
SearchAndCombobox.multiSearch = function(obj, title, url) {
	obj.each(function(){
		var _this = $(this);
		var _next = _this.next();
		_this.iptSearch({
			clickFn : function() {			
				ygDialog({
					title : title,
					href : url,
					width : 500,
					height : 'auto',
					isFrame : true,
					modal : true,
					showMask : true,
					buttons: [{
						id:'sure',
			            text: '确认',
			            handler: function(dialog) {
		            		var no = '';
							var name ='';
			            	var checkedRows = SearchAndCombobox.getRowData();
			            	if(typeof checkedRows == 'undefined'){
			            		checkedRows = SearchAndCombobox.getCheckRows();
								$.each(checkedRows,function(index,item){
									no += item.code+',';
									name += item.name+',';
								});
								no = no.substring(0, no.length-1);
								name = name.substring(0, name.length-1);
			            	}else{
			            		no = checkedRows.code;
			            		name = checkedRows.name;
			            	}
			            	_this.val(name);
		            		_next.val(no);
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
						SearchAndCombobox.getCheckRows = content.getCheckRows;
						SearchAndCombobox.getRowData = content.getRowData;
					}
				});
			}
		});
	})
};

//调整宽度
SearchAndCombobox.initSearchWidth = function(formId){
	var _thisForm = $('#'+formId);
	var _width =_thisForm.find('div[class=ipt-search-box]').width();
	if(null !=_width){
		var _input = _thisForm.find('input[type!=hidden]');
		var width = _width-10;
		_input.each(function(){
			 if(typeof($(this).attr('singleSearch'))=='undefined' && typeof($(this).attr('multiSearch'))=='undefined'){
				 if($(this).width() < width){
					 $(this).width(width);
				 }
			 }
		});
	}
};

// 下拉框
SearchAndCombobox.combobox = function(obj,url,type){
	if(obj.length == 0){
		return ;
	}
	if(typeof (obj.attr('showType')) != 'undefined'){
		if(url.indexOf('?') >= 0){
			url += '&showType='+obj.attr('showType');
		}else{
			url += '?showType='+obj.attr('showType');
		}
	}
	if(typeof (obj.attr('fliterType')) != 'undefined'){
		if(url.indexOf('?') >= 0){
			url += '&fliterType='+obj.attr('fliterType');
		}else{
			url += '?fliterType='+obj.attr('fliterType');
		}
	}
	obj.combobox({
		url:url,
		valueField:'code',
		textField:'name'
	});
};

//币别格式化
SearchAndCombobox.currencyFormat = function(value,row,index){
	if(value && value!=''){
		if(SearchAndCombobox.currencyData[value]){
			return SearchAndCombobox.currencyData[value];
		}
		return value;
	}
};

//大类格式化
SearchAndCombobox.categoryFormat = function(value,row,index){
	if(value && value!=''){
		if(SearchAndCombobox.categoryData[value]){
			return SearchAndCombobox.categoryData[value];
		}
		return value;
	}
};

//扣项分类格式化
SearchAndCombobox.deductionFormat = function(value,row,index){
	if(value && value!=''){
		if(SearchAndCombobox.deductionData[value]){
			return SearchAndCombobox.deductionData[value];
		}
		return value;
	}
};

SearchAndCombobox.currencyJSONData;

SearchAndCombobox.currencyData = new Object();

SearchAndCombobox.deductionJSONData;

SearchAndCombobox.deductionData = new Object();

SearchAndCombobox.categoryJSONData;

SearchAndCombobox.categoryData = new Object();


SearchAndCombobox.initDeductionData = function() {
	SearchAndCombobox.deductionJSONData = [{code:1,name:'女鞋'},{code:2,name:'男鞋'}];
	SearchAndCombobox.deductionData = {1:'女鞋',2:'男鞋'};
};

SearchAndCombobox.initCurrencyData = function() {
	ajaxRequestAsync(BasePath + '/common_util/getCurrency',null,function(data){
		if(data.length > 0){
			SearchAndCombobox.currencyJSONData = data;
			$.each(data,function(index,item){
				SearchAndCombobox.currencyData[item.code]=item.name;
			});
		}
	});
};

SearchAndCombobox.initCategoryData = function() {
	ajaxRequestAsync(BasePath + '/common_util/getCategory',null,function(data){
		if(data.length > 0){
			SearchAndCombobox.categoryJSONData = data;
			$.each(data,function(index,item){
				SearchAndCombobox.categoryData[item.code]=item.name;
			});
		}
	});
};

//初始化单选
SearchAndCombobox.initSingleSearch = function(formId){
	SearchAndCombobox.singleSearch($('#'+formId).find('input[singleSearch=supplier]'),'选择供应商',BasePath + '/common_util/doSelect?selectUrl=getSupplier');
	SearchAndCombobox.singleSearch($('#'+formId).find('input[singleSearch=company]'),'选择公司',BasePath + '/common_util/doSelect?selectUrl=getCompany');
	SearchAndCombobox.singleSearch($('#'+formId).find('input[singleSearch=item]'),'选择商品',BasePath + '/common_util/doSelect?selectUrl=getItem');
	SearchAndCombobox.singleSearch($('#'+formId).find('input[singleSearch=brand]'),'选择品牌',BasePath + '/common_util/doSelect?selectUrl=getPageBrand');
	SearchAndCombobox.singleSearch($('#'+formId).find('input[singleSearch=brandUnit]'),'选择品牌部',BasePath + '/common_util/doSelect?selectUrl=getPageBrandUnit');
	SearchAndCombobox.singleSearch($('#'+formId).find('input[singleSearch=category]'),'选择大类',BasePath + '/common_util/doSelect?selectUrl=getPageCategory');
};

//初始化多选
SearchAndCombobox.initMultiSearch = function(formId){
	SearchAndCombobox.multiSearch($('#'+formId).find('input[multiSearch=supplier]'),'选择供应商',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getSupplier');
	SearchAndCombobox.multiSearch($('#'+formId).find('input[multiSearch=company]'),'选择公司',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getCompany');
	SearchAndCombobox.multiSearch($('#'+formId).find('input[multiSearch=item]'),'选择商品',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getItem');
	SearchAndCombobox.multiSearch($('#'+formId).find('input[multiSearch=brand]'),'选择品牌',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getPageBrand');
	SearchAndCombobox.multiSearch($('#'+formId).find('input[multiSearch=brandUnit]'),'选择品牌部',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getPageBrandUnit');
	SearchAndCombobox.multiSearch($('#'+formId).find('input[multiSearch=category]'),'选择大类',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getPageCategory');
}; 

//初始化下拉框
SearchAndCombobox.initCombobox = function(formId){
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=category]'),BasePath + '/common_util/getCategory');// 一级大类
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=billType]'),BasePath + '/common_util/getBillType');// 单据类型
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=balanceType]'),BasePath + '/common_util/getBalanceType');// 结算类型
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=balanceStatus]'),BasePath + '/common_util/getBalanceStatus');// 结算状态
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=auditStatus]'),BasePath + '/common_util/getAuditStatus');// 审批状态
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=billStatus]'),BasePath + '/common_util/getBillStatus');// 单据状态
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=exceptionType]'),BasePath + '/common_util/getExceptionType');// 异常类型
	SearchAndCombobox.combobox($('#'+formId).find('input[combobox=currency]'),BasePath + '/common_util/getCurrency');// 币别
};

//初始化数据
SearchAndCombobox.initData = function(){
	SearchAndCombobox.initCurrencyData();
	SearchAndCombobox.initDeductionData();
	SearchAndCombobox.initCategoryData();
}

// 初始化页面组件
SearchAndCombobox.initPage = function(formId){
	SearchAndCombobox.initSingleSearch(formId); 
	SearchAndCombobox.initMultiSearch(formId); 
	SearchAndCombobox.initSearchWidth(formId);
	SearchAndCombobox.initCombobox(formId);
}
