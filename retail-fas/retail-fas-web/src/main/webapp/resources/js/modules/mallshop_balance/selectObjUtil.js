var selectObjUtil = new Object();

//当前编辑行
editRowIndex = -1;

selectObjUtil.selectCompany  = {// 明细行选择公司
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		title:"选择公司",
	    		width : 580,
	    		height : 450,
	    		href : BasePath + "/common_util/toSearchCompany",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'companyNo','name','companyNo','companyName');
	    		}
	    	});
	    }
	};

selectObjUtil.selectBsgroups1  = function(dataGrid){
	alert(dataGrid);
}

selectObjUtil.selectBsgroups  = {// 明细行选择商业集团
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		title:"选择商业集团",
	    		width : 580,
	    		height : 450,
	    		href : BasePath + "/common_util/toSearchBsgroups",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'bsgroupsNo','name','bsgroupsNo','bsgroupsName');
	    		}
	    	});
	    }
	};

selectObjUtil.selectMall = {// 明细行选择商场
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择商场",
				href : BasePath + "/common_util/toSearchMall",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'mallNo','name','mallNo','mallName');
	    		}
	    	});
	    }
};

selectObjUtil.selectShopGroup = {// 明细行选择店铺分组
		validatebox:{
	    	options:{
	    		required:false
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择店铺",
				href : BasePath + "/common_util/toSearchShopGroup",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'shopNo','shortName','shopGroupNo','shopGroupName');
	    		}
	    	});
	    }
};

selectObjUtil.selectTemplate = {// 明细行选择发票模板
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择发票模板",
				href : BasePath + "/common_util/toSearchTemplate",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'invoiceTempNo','name','invoiceTempNo','invoiceTempName');
	    		}
	    	});
	    }
};

selectObjUtil.selectShop = {// 明细行选择门店
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择门店",
				href : BasePath + "/common_util/toSearchShop",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'shopGroupNo','shortName','shopNo','shopName');
	    		}
	    	});
	    }
};

selectObjUtil.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		dataType : 'json',
		success : callback
	});
};

selectObjUtil.selectShopOpt = {// 明细行选择选择门店
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择门店",
				href : BasePath + "/common_util/toSearchShop",
	    		fn : function(data, rowIndex) {
//	    			selectObjUtil.selectorCallback(data,'shopNo','shortName','shopNo','shortName');
	    			var paramTemp = {
	    				shopNo : 'shopNo',
	    				shopName : 'shopName',
	    				shortName : 'shortName',
	    				companyNo : 'companyNo',
	    				companyName : 'companyName',
	    				bsgroupsNo : 'bsgroupsNo',
	    				bsgroupsName : 'bsgroupsName',
	    				mallNo : 'mallNo',
	    				mallName : 'mallName',
	    				organNo : 'organNo',
	    				organName : 'organName'
	    			};
	    			selectObjUtil.selectorShopCallBack(data,paramTemp);
	    		}
	    	});
	    }
};

selectObjUtil.selectorShopCallBack = function(data,paramTemp){
	var dataGrid = $("table[id*=DataGrid]");
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var valueEd = dataGrid.datagrid('getEditor', {
		'index' : editIndex,
		'field' : paramTemp.shopNo
	});
	var textEd = dataGrid.datagrid('getEditor', {
		'index' : editIndex,
		'field' : paramTemp.shortName
	});
	valueEd.target.val(data[paramTemp.shopNo]);
	textEd.target.val(data[paramTemp.shortName]);
	
	var url = BasePath + "/shop/initSubInfo";
	var reqParam = {
		shopNo : data[paramTemp.shopNo],
		payType : 'U030301'
	};
	
//	selectObjUtil.commonAjaxRequest(url,reqParam,function(result){
//		$("#companyNo").val(result.companyNo);
//		$("#companyName").val(result.companyName);
//		
//		$("#mallNo").val(result.mallNo);
//		$("#mallName").val(result.mallName);
//		
//		$("#bsgroupsNo").val(result.bsgroupsNo);
//		$("#bsgroupsName").val(result.bsgroupsName);
//		
//		$("#mallNo").val(result.mallNo);
//		$("#mallName").val(result.mallName);  
//	});

	
	selectObjUtil.commonAjaxRequest(url,reqParam,function(result){
		var dataGrid = $("table[id*=DataGrid]");
		var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
		var valueEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.companyNo
		});
		var textEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.companyName
		});

//if (!valueEd || typeof(valueEd)!="undefined" || valueEd!=0){
		if (valueEd != null && valueEd != undefined && valueEd != '') { 
			valueEd.target.val(result.companyNo);
  }
  
//if (!textEd || typeof(textEd)!="undefined" || textEd!=0){
	if (textEd != null && textEd != undefined && textEd != '') { 
			textEd.target.val(result.companyName);
 }
		
		var valueEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.bsgroupsNo
		});
		var textEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.bsgroupsName
		});
		if (valueEd != null && valueEd != undefined && valueEd != '') { 
			valueEd.target.val(result.bsgroupsNo);
		}
		if (textEd != null && textEd != undefined && textEd != '') {
			textEd.target.val(result.bsgroupsName);
		}
		var valueEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.mallNo
		});
		var textEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.mallName
		});
		valueEd.target.val(result.mallNo);
		textEd.target.val(result.mallName);
		
		var valueEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.organNo
		});
		var textEd = dataGrid.datagrid('getEditor', {
			'index' : editIndex,
			'field' : paramTemp.organName
		});
		valueEd.target.val(result.organNo);
		textEd.target.val(result.organName);
	});
};

selectObjUtil.selectOrgan = {// 明细行选择管理城市 
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择管理城市 ",
				href : BasePath + "/common_util/toSearchOrgan",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'organNo','name','organNo','organName');
	    		}
	    	});
	    }
};


selectObjUtil.selectBrand = {// 明细行选择管理品牌
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择管理品牌 ",
				href : BasePath + "/common_util/toSearchBrand",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'brandNo','name','brandNo','brandName');
	    		}
	    	});
	    }
};


selectObjUtil.selectCostCate = {// 明细行选择费用类别
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择费用类别 ",
				href : BasePath + "/common_util/toSearchCostCate",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'code','name','costCateCode','costCateName');
	    		}
	    	});
	    }
};

selectObjUtil.selectDeduction = {// 明细行选择商场扣费
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择商场扣费 ",
				href : BasePath + "/common_util/toSearchDeduction",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'code','name','deductionCode','deductionName');
	    		}
	    	});
	    }
};
selectObjUtil.selectorCallback = function(data,value,text,valueField,textField){// 选择后回调
	var dataGrid = $("table[id*=DataGrid]");
//	var dataGrid=$('#balanceDiffDataGrid');
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var valueEd = dataGrid.datagrid('getEditor', {
		'index' : editIndex,		
		'field' : valueField
	});
	var textEd = dataGrid.datagrid('getEditor', {
		'index' : editIndex,
		'field' : textField
	});
	valueEd.target.val(data[value]);
	textEd.target.val(data[text]);
};