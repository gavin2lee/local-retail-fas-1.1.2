var fas_util = new Object();
 
//初始化默认查询精灵
fas_util.initIptAllSearch = function(){
	fas_util.initIptSupplier($("input[iptSearch=supplier]"));
	fas_util.initIptCompany($("input[iptSearch=company]"));
	fas_util.initIptmall($("input[iptSearch=mall]"));
	fas_util.initIptshop($("input[iptSearch=shop]"));
	fas_util.initIptorgan($("input[iptSearch=organ]"));
	fas_util.initIptbsgroups($("input[iptSearch=bsgroup]"));
	fas_util.initIptCustomer($("input[iptSearch=customer]"));
	fas_util.initIptAllCustomer($("input[iptSearch=allcustomer]"));
};

//调整宽度
fas_util.initIptAllSearchWidth = function(){
	var _width =$("div[class=ipt-search-box]").width();
	if(null !=_width){
		var _input = $("input[type!=radio]");
		var width = _width-10;
		_input.each(function(){
			 if(typeof($(this).attr("iptSearch"))=="undefined" && typeof($(this).attr("multiSearch"))=="undefined"){
				 if($(this).width() < width){
					 $(this).width(width);
				 }
			 }
		});
	}
};

//调整宽度
fas_util.initIptFormSearchWidth = function(formId){
	var _width =$('#'+formId).find("div[class=ipt-search-box]").width();
	if(null !=_width){
		var _input = $('#'+formId).find("input[type!=radio]");
		var width = _width-10;
		_input.each(function(){
			 if(typeof($(this).attr("iptSearch"))=="undefined" && typeof($(this).attr("multiSearch"))=="undefined"){
				 if($(this).width() < width){
					 $(this).width(width);
				 }
			 }
		});
	}
};

// 初始化默认Combo
fas_util.initAllCombobox = function(){
	fas_util.initCombo($("input[combobox=tsCurrency]"),BasePath + '/common_util/getTSCurrency');// 巴洛克币别
	fas_util.initCombo($("input[combobox=currency]"),BasePath + '/common_util/getCurrency');// 币别
	fas_util.initCombo($("input[combobox=category]"),BasePath + '/common_util/getCategory');// 大类
	fas_util.initCombo($("input[combobox=brand]"),BasePath + '/common_util/getBrand');// 品牌
	fas_util.initCombo($("input[combobox=status]"),BasePath + '/common_util/getStatus');// 单据状态：制单、变更、作废、完结
	fas_util.initCombo($("input[combobox=balanceStatus]"),BasePath + '/common_util/getBalanceStatus');// 结算状态: 未对账、业务确认、财务确认
	fas_util.initCombo($("input[combobox=settleMethod]"),BasePath + '/common_util/getSettleMethod');// 结算方式: 
	fas_util.initCombo($("input[combobox=billType]"),BasePath + '/common_util/getBillType');// 单据类型
	fas_util.initCombo($("input[combobox=exceptionType]"),BasePath + '/common_util/getExceptionType');// 异常类型
};


//初始化指定form查询精灵
fas_util.initIptFormSearch = function(formId){
	fas_util.initIptSupplier($("#"+formId).find("input[iptSearch=supplier]"));
	fas_util.initIptCompany($("#"+formId).find("input[iptSearch=company]"));
	fas_util.initIptshop($("#"+formId).find("input[iptSearch=shop]"));
};

//初始化指定formCombo
fas_util.initFormCombobox = function(formId){
	fas_util.initCombo($("#"+formId).find("input[combobox=currency]"),BasePath + '/common_util/getCurrency');// 币别
	fas_util.initCombo($("#"+formId).find("input[combobox=category]"),BasePath + '/common_util/getCategory');// 大类
	fas_util.initCombo($("#"+formId).find("input[combobox=brand]"),BasePath + '/common_util/getBrand');// 品牌
	fas_util.initCombo($("#"+formId).find("input[combobox=status]"),BasePath + '/common_util/getStatus');// 单据状态：制单、变更、作废、完结
	fas_util.initCombo($("#"+formId).find("input[combobox=balanceStatus]"),BasePath + '/common_util/getBalanceStatus');// 结算状态: 未对账、业务确认、财务确认
	fas_util.initCombo($("#"+formId).find("input[combobox=settleMethod]"),BasePath + '/common_util/getSettleMethod');// 结算状态: 未对账、业务确认、财务确认
};



// 初始化默认查询精灵
fas_util.initIptSearch = function(){
	fas_util.initIptSupplier($("#mainDataForm").find("input[iptSearch=supplier]"));
	fas_util.initIptCompany($("#mainDataForm").find("input[iptSearch=company]"));
};

// 初始化默认Combo
fas_util.initCombobox = function(){
	fas_util.initCombo($("#mainDataForm").find("input[combobox=currency]"),BasePath + '/common_util/getCurrency');// 币别
	fas_util.initCombo($("#mainDataForm").find("input[combobox=category]"),BasePath + '/common_util/getCategory');// 大类
	fas_util.initCombo($("#mainDataForm").find("input[combobox=brand]"),BasePath + '/common_util/getBrand');// 品牌
	fas_util.initCombo($("#mainDataForm").find("input[combobox=status]"),BasePath + '/common_util/getStatus');// 单据状态：制单、变更、作废、完结
	fas_util.initCombo($("#mainDataForm").find("input[combobox=balanceStatus]"),BasePath + '/common_util/getBalanceStatus');// 结算状态: 未对账、业务确认、财务确认
	fas_util.initCombo($("#mainDataForm").find("input[combobox=settleMethod]"),BasePath + '/common_util/getSettleMethod');// 结算状态: 未对账、业务确认、财务确认
};

// 初始化单个Combobx
fas_util.initCombo = function(obj,url){
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

fas_util.initIptSupplier = function(obj){// 初始化供应商
	obj.each(function(){
		var name_obj = $(this);
		var no_obj =  $(this).next();
		name_obj.iptSearch({
			clickFn : function(){
				dgSelector({
					title:"选择供应商",
					href : BasePath + "/base_setting/supplier/toSearchSupplier",
					width : 500,
					height : 650,
					fn: function(data){
						no_obj.val(data.supplierNo);
						name_obj.val(data.fullName);
					}
				});
			}
		});
	});
};


fas_util.initIptCustomer = function(obj){// 初始化客户
	obj.each(function(){
		var name_obj = $(this);
		var no_obj =  $(this).next();
		name_obj.iptSearch({
			clickFn : function(){
				dgSelector({
					title:"选择客户",
					href : BasePath + "/base_setting/customer/toSearchCustomer",
					width : 500,
					height : 650,
					fn: function(data){
						no_obj.val(data.customerNo);
						name_obj.val(data.fullName);
					}
				});
			}
		});
	});
};

fas_util.initIptAllCustomer = function(obj){// 初始化客户
	obj.each(function(){
		var name_obj = $(this);
		var no_obj =  $(this).next();
		name_obj.iptSearch({
			clickFn : function(){
				dgSelector({
					title:"选择客户",
					href : BasePath + "/common_util/toSearchAllCustomer",//BasePath + "/base_setting/customer/toSearchAllCustomer",
					width : 500,
					height : 650,
					fn: function(data){
						no_obj.val(data.no);
						name_obj.val(data.name);
					}
				});
			}
		});
	});
};

fas_util.initIptCompany = function(obj){// 初始化结算公司
	obj.each(function(){
		var name_obj = $(this);
		var no_obj =  $(this).next();
		var url =  BasePath + "/base_setting/company/toSearchCompany";
		name_obj.iptSearch({
			clickFn : function() {
				dgSelector({
					title:"选择公司",
					href : url,
					width : 500,
					height : 420,
					fn: function(data){
						no_obj.val(data.companyNo);
						name_obj.val(data.name);
					}
				});
			}
		});
	});
};


/** ******************************陈茂军专用Start******************************/
//选择商场
fas_util.initIptmall = function(obj){
	if(obj.length == 0){
		return ;
	}
	obj.each(function(){
    var name_obj = $(this);
    var no_obj =  $(this).next();
	name_obj.iptSearch({
		clickFn : function() {
			dgSelector({
					title:"选择商场",
					href : BasePath + "/common_util/toSearchMall",
					width : 500,
					height : 600,
					fn: function(data){
						no_obj.val(data.mallNo);
						name_obj.val(data.name);
					}
			});
		}
	});
	});
};

//选择门店
fas_util.initIptshop = function(obj){	
	if(obj.length == 0){
		return ;
	}
	obj.each(function(){
	    var name_obj = $(this);
	    var no_obj =  $(this).next();
		name_obj.iptSearch({
		clickFn : function() {
			dgSelector({			     
					title:"选择门店",
					href : BasePath + "/common_util/toSearchShop?companyNo="+ ( (typeof $('#companyNo').val() == 'undefined') ? '' : $('#companyNo').val()) +"" +
							"&shopType="+ ( (typeof $('#shopType').val() == 'undefined') ? '' : $('#shopType').val()) ,
					width : 500,
					height : 600,
					fn: function(data){
						no_obj.val(data.shopNo);
						name_obj.val(data.shortName);
					}
			});
		}
		});
	});
};

//选择组织管理城市
fas_util.initIptorgan = function(obj){
	if(obj.length == 0){
		return ;
	}
	obj.each(function(){
	    var name_obj = $(this);
	    var no_obj =  $(this).next();
		name_obj.iptSearch({
		clickFn : function() {
			dgSelector({
					title:"选择管理城市",
					href : BasePath + "/common_util/toSearchOrgan",
					width : 500,
					height : 600,
					fn: function(data){
						no_obj.val(data.organNo);
						name_obj.val(data.name);
					}
			});
		}
		});
	});
};

//选择商业集团
fas_util.initIptbsgroups = function(obj){
	if(obj.length == 0){
		return ;
	}
	obj.each(function(){
	    var name_obj = $(this);
	    var no_obj =  $(this).next();
		name_obj.iptSearch({
		clickFn : function() {
			dgSelector({
					title:"选择商业集团",
					href : BasePath + "/common_util/toSearchBsgroups",
					width : 500,
					height : 600,
					fn: function(data){
						no_obj.val(data.bsgroupsNo);
						name_obj.val(data.name);
					}
			});
		}
		});
	});
};

/** **********************陈茂军专用END***************************************/
fas_util.selectCompany = {// 明细行选择结算主体
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		title : '选择公司',
	    		width : 580,
	    		height : 450,
	    		href : BasePath + '/base_setting/company/toSearchCompany',
	    		fn : function(data, rowIndex) {
	    			fas_util.selectorCallback(data,'companyNo','name','companyNo','companyName');
	    		}
	    	});
	    }
	};

fas_util.selectSupplier = {	// 明细行选择供应商
    clickFn:function(){
    	dgSelector({
    		title : '选择供应商',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/supplier/toSearchSupplier',
    		fn : function(data, rowIndex) {
    			fas_util.selectorCallback(data,'supplierNo','fullName','supplierNo','supplierName');
    		}
    	});
    }
};

fas_util.selectCustomer = {// 明细行选择内部客户
    clickFn:function(){
    	dgSelector({
    		title : '选择客户',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/company/toSearchCompany',
    		fn : function(data, rowIndex) {
    			fas_util.selectorCallback(data,'companyNo','name','customerNo','customerName');
    		}
    	});
    }
};

fas_util.selectItem = {// 明细行选择商品
    clickFn:function(){
    	dgSelector({
    		title : '选择商品',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/item/toSearchItem',
    		fn : function(data, rowIndex) {
    			fas_util.selectorCallback(data,'code','name','itemCode','itemName');
    		}
    	});
    }
};
fas_util.selectorCallback = function(data,value,text,valueField,textField){// 选择后回调
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : editIndex,
		'field' : valueField
	});
	var textEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : editIndex,
		'field' : textField
	});
	valueEd.target.val(data[value]);
	textEd.target.val(data[text]);
};
//初始化
$(function(){
	setTimeout(function(){
		fas_util.initMultiSearch(); 
		fas_util.initIptAllSearch(); 
//		fas_util.initIptAllSearchWidth(); 
		fas_util.initAllCombobox(); 
	},500);

});

fas_util.initMultiSearch = function(){

	fas_util.multiSearch($('input[multiSearch=supplier]'),'选择供应商',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getSupplier&no=supplierNo&name=fullName');
	fas_util.multiSearchShop($('input[multiSearch=shop]'),'选择店铺',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getCompToShop&no=shopNo&name=shortName');
	fas_util.multiSearch($('input[multiSearch=company]'),'选择结算公司',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getCompany&no=companyNo&name=name');
	fas_util.multiSearch($('input[multiSearch=item]'),'选择商品',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getItem&no=itemNo&name=name');
	fas_util.multiSearch($('input[multiSearch=category]'),'选择大类',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getCategory&no=categoryNo&name=name');
	fas_util.multiSearch($('input[multiSearch=brand]'),'选择品牌',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getBrand&no=brandNo&name=name');
	fas_util.multiSearch($('input[multiSearch=addBrand]'),'选择品牌',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getPageBrand&no=brandNo&name=name', 'add');
	fas_util.multiSearch($('input[multiSearch=addCategory]'),'选择大类',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getCategory&no=categoryNo&name=name', 'add');
	fas_util.multiSearch($('input[multiSearch=addItem]'),'选择商品',BasePath + '/common_util/doSelect?multiSelect=true&selectUrl=getItem&no=itemNo&name=name', 'add');
}; 

//回调NO为 ('BLK302','BLK303','BLK304','BLK305')
fas_util.multiSearch = function(obj, title, url, operate) {// 多选组件
	obj.each(function(){
		var _this = $(this);
		var _next = _this.next();
		_this.iptSearch({
			clickFn : function() {	
				ygDialog({
					title : title,
					href : url + "&queryCondition=?companyNo="+$('#companyNo').val(),
					width : 420,
					height : 550,
					isFrame : true,
					modal : true,
					showMask : true,
					buttons: [{
						id : 'sure',
			            text: '确认',
			            handler: function(dialog) {
			            	var val = "code";
			            	if(_this.attr("multiSearch") == "addItem"){
			            		val = "id";
			            	}
			            	var checkedRows = fas_util.getRowData();
			            	if(typeof checkedRows == 'undefined'){
								checkedRows = fas_util.getCheckRows();
								var no = "";
								var name ="";
								$.each(checkedRows,function(index,item){
									no += item[val]+",";
									name += item.name+",";
								});
								if(operate && operate == 'add') {
									_this.val(name.substring(0,name.length-1));
									_next.val(no.substring(0,no.length-1));
								} else {
									_this.val(name.substring(0,name.length-1));
									var reg=new RegExp(",","g"); 
									no = no.substring(0,no.length-1);
									no = no.replace(reg,"','");
									no = "('" + no + "')";
									_next.val(no);
								}
			            	}else{
			            		if(operate && operate == 'add') {
									_this.val(checkedRows.name);
									_next.val(checkedRows[val]);
								}else{
									_this.val(checkedRows.name);
				            		_next.val("('" + checkedRows[val] + "')");				            		
								} 
			            	}
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
						fas_util.getCheckRows = content.getCheckRows;
						fas_util.getRowData = content.getRowData;
					}
				});
			}
		});
	})
};

//回调NO为  BLK302,BLK303,BLK304,BLK305,BLK308,BLK309,BLK320,BLK321,BLK323,BLK324
fas_util.multiSearchShop = function(obj, title, url, operate) {// 多选组件
	obj.each(function(){
		var _this = $(this);
		var _next = _this.next();
		_this.iptSearch({
			clickFn : function() {	
				ygDialog({
					title : title,
					href : url + "&queryCondition=?companyNo="+$('#companyNo').val(),
					width : 420,
					height : 550,
					isFrame : true,
					modal : true,
					showMask : true,
					buttons: [{
						id : 'sure',
			            text: '确认',
			            handler: function(dialog) {
			            	var val = "code";
			            	if(_this.attr("multiSearch") == "addItem"){
			            		val = "id";
			            	}
			            	var checkedRows = fas_util.getRowData();
			            	if(typeof checkedRows == 'undefined'){
								checkedRows = fas_util.getCheckRows();
								var no = "";
								var name ="";
								$.each(checkedRows,function(index,item){
									no += item[val]+",";
									name += item.name+",";
								});
								if(operate && operate == 'add') {
									_this.val(name.substring(0,name.length-1));
									_next.val(no.substring(0,no.length-1));
								} else {
									_this.val(name.substring(0,name.length-1));
									var reg=new RegExp(",","g"); 
									no = no.substring(0,no.length-1);
//									no = no.replace(reg,"','");
//									no = "('" + no + "')";
									_next.val(no);
								}
			            	}else{
			            		if(operate && operate == 'add') {
									_this.val(checkedRows.name);
									_next.val(checkedRows[val]);
								}else{
									_this.val(checkedRows.name);
//				            		_next.val("('" + checkedRows[val] + "')");	
				            		_next.val(checkedRows[val]);	
								} 
			            	}
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
						fas_util.getCheckRows = content.getCheckRows;
						fas_util.getRowData = content.getRowData;
					}
				});
			}
		});
	})
};

/**
 * 基础资料的导出
 * @param mainForm 需要导出的表头form
 * @param dataGridId  导出数据的表格ID
 * @param exportUrl 导出数据的URL   基础资料一般都是 /模块名/do_export.htm     *如机构:/store/do_export 
 * @param excelTitle excel文件名
 * @param checkFlag 导出数据的表格是否有复选框(有复选框后台自动去掉该列)  0--无  1--有
 * @param reduceColnumName 不用导出的列名
 */
fas_util.exportExcelBaseInfo = function (dataGridId, exportUrl,excelTitle,checkFlag,reduceColnumName){
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
			if(rowData.notexport){
				return ;
			}
			if(rowData.hidden){
				return ;
			}
			var v_object = {};
			v_object.field = rowData.field;
			v_object.title = rowData.title;
			if(rowData.hidden == 'true'){
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
				param.exportColumns=exportColumns;
				param.fileName=excelTitle;
				param.checkFlag=checkFlag;
				param.reduceColnumName=reduceColnumName;
				param.pageNumber = v_pageNumber;
				param.pageSize = v_pageSize;
				if(params!=null&&params!={}){
					$.each(params,function(i){
						param[i]=params[i];
					});
				}
				
			},
			success: function(){
				
		    }
	   });
	}else{
		alert('查询记录为空，不能导出!',1);
	}
}

/**
 * 打印
 * 1:简单表头打印
 *  获取datagrid的columns表头属性，通过queryDataUrl查询待打印数据，合并到一起，直接传给lodop打印
 * 2:动态表头打印
 *  通过queryHeaderUrl获取后台返回的待打印表头，然后把值放到弹出框中隐藏的datagrid中（获取datagrid原生结构，这样自己就不用合并表头）
 *  通过queryDataUrl查询待打印数据，合并表头数据，传给lodop打印
 */
fas_util.printDialog = function(opts) {
    	//合并options
        opts = opts || {};
        var winOpts = $.extend({},
        {
		   intOrient : 0,// 0：打印机缺省设置,1：纵向，2：横向
		   params : {},	// 其他需要打印的数据
		   type:0,		// 0：简单打印，1：动态打印
		   needParams:0,// 0需要查询参数，1不需要带查询参数
		   title : '标题', // 打印标题
		   dataGridId : '', // 获取要打印的datagridId
		   viewName : 'simple_print', // 打印模板布局界面
		   queryDataUrl : '', // 查询被打印数据的url
		   modulePath:'',
		   hasSizeHorizontal:false,
		   columns:'',// 要打印的datagrid列
		   billNo:''
        },opts);
        //初始化参数
        var billNoValue=winOpts.billNo;
		var previewUrl = BasePath + '/print/preview?viewName='+winOpts.viewName;
		var $dg = $('#' + winOpts.dataGridId);
		var params = null;
		if(winOpts.needParams == 0){
			params = $dg.datagrid('options').queryParams;
		}
		var columns = null;
		//简单表头获取datagrid数据，复杂表头获取远程数据
		if(winOpts.hasSizeHorizontal){
			columns = winOpts.columns;
		}else{
			columns = $dg.datagrid('options').columns;
		}
		//打开待打印数据页面
		ygDialog({
			isFrame: true,
			cache : false,
			title : winOpts.title,
			modal:true,
			showMask: false,
			fit : true,
			href : previewUrl,
			buttons:[{
				text:'打印',
				iconCls:'icon-print',
				handler:'print_page'
			}],
			onLoad:function(win,dialog){
				var tableHeader = dialog.$('#tableHeader');
				var tableBody  =  dialog.$('#tableBody');
				var columnsLength = columns.length;
				//过滤表头不需要打印的column 静态表头配置了printable=false都将不打印
				var grepColumns = $.grep(columns[columnsLength - 1], function(o, i) {
					if ($(o).attr('printable') == false) {
						return true;
					}
					return false;
				}, true);
				//过滤列头
				var filterColumns = [];
				$.each(columns, function(i, n) {
					if ($(n).attr("printable")== undefined) {
						filterColumns.push(n);
					}
				});
				filterColumns.push(grepColumns);
				//组装列表头
				if(columnsLength >= 1){
					dialog.$("#hiddenDiv").remove();
					dialog.$("<div id='hiddenDiv' style='display:none'><table id='exportExcelDG' ></table><div>").appendTo("body");
					dialog.$("#exportExcelDG").datagrid({columns : filterColumns});
					var tbody = dialog.$("#exportExcelDG").prev(0).find('.datagrid-htable').find('tbody');
					tableHeader.append(tbody.html());
				}
				//组装列表体
				ajaxRequestAsync(BasePath + winOpts.queryDataUrl, params, function(result) {
					var rows = result.rows;
					dialog.params = rows;
					dialog.params.title = winOpts.title;
					dialog.params.intOrient = winOpts.intOrient;
					if('' != winOpts.barcode){
						dialog.params.barcode = winOpts.barcode;
					}
					for ( var i = 0; i < rows.length; i++) {
						var row = rows[i];
						//拼接表体信息
						var bodyTrNode ='<tr>';
						var bodyTdNode = '';
						$(grepColumns).each(function(i,node){
							var field=$(node).attr('field');
							var value = row[field+''];
							if (typeof(value) == 'undefined' || null == value) {
								value = '';
							}	
							bodyTdNode+='<td>'+value+'</td>';
						});
						bodyTrNode+=bodyTdNode+'</tr>';
						//填充表体
						tableBody.append(bodyTrNode);
					}
				});
				dialog.print_page(this);
			}
		});
};











