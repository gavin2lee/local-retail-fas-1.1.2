
var selfShopBankInfo = new Object();

selfShopBankInfo.editRowIndex = -1;

var setting = {
	datagridId : "shopDataGrid",
	exportId : "btn-export",
	primaryKey : "id",
	saveUrl : "/self_shop_bank_info/save",
	saveCallback : function(){
		
	},
	initRow : function(){
		return {};
	},
	initData : {
		combobox : {
			
		}
	}
};

// clear the search form
selfShopBankInfo.clear = function(){
	$('#searchForm').form("clear");
	$(':input','#searchForm').not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
};

//查询
selfShopBankInfo.search = function() {
	var params = $('#searchForm').form('getData');
	var url = BasePath + '/self_shop_bank_info/list.json';
    $('#shopDataGrid').datagrid('options').queryParams= params;
    $('#shopDataGrid').datagrid('options').url= url;
    $('#shopDataGrid').datagrid('load');
};

//新增
selfShopBankInfo.add = function() {
	
	var paramTemp = {
			shopNo : 'shopNo',
			shopName : 'shortName',
			companyNo : 'companyNo',
			companyName : 'companyName',
			bsgroupsNo : 'bsgroupsNo',
			bsgroupsName : 'bsgroupsName',
			mallNo : 'mallNo',
			mallName : 'mallName'
	};
	
	$("#shopDataGrid").datagrid("removeEditor", "shopName");
	$("#shopDataGrid").datagrid("addEditor", {field : "shopName", 
		editor:{
      		type:'shop',
      		options:{
      			id:'shopName_',
      			name:'shopName_',
      			required:true,
      			relationData:true,
      			callback:function(row){
      				selfShopBankInfo.selectorShopCallBack(row,paramTemp);
      			}
      		}
      	}
	});
	
	if(selfShopBankInfo.endEdit()){
		
		$('#shopDataGrid').datagrid('insertRow', {
			index : 0,
			row : {}
		});
		$('#shopDataGrid').datagrid('beginEdit', 0);
	    selfShopBankInfo.editRowIndex = 0;
		
		//used to use this
//	    $('#shopDataGrid').datagrid('appendRow',{});
//	    var length = $('#shopDataGrid').datagrid('getRows').length;
//		$('#shopDataGrid').datagrid('beginEdit',length-1);
//		selfShopBankInfo.editRowIndex = length-1;
	}
	
};

//删除
selfShopBankInfo.del = function() {
	var delFlag = false;
    var checkedRows = $('#shopDataGrid').datagrid('getChecked');
    
    if(checkedRows.length == 0){
    	showWarn('请选择要删除的行..');
    	return;
    }
    
    var rowIndex;
    $.each(checkedRows,function(index,row){
    	rowIndex = $('#shopDataGrid').datagrid('getRowIndex',row);
    	$('#shopDataGrid').datagrid('deleteRow',rowIndex);
    	if(selfShopBankInfo.editRowIndex == rowIndex){
    		selfShopBankInfo.editRowIndex =  -1;
    	}
    });
};

//修改
selfShopBankInfo.edit = function(rowIndex,data) {
	if(selfShopBankInfo.endEdit()){
		$('#shopDataGrid').datagrid('beginEdit',rowIndex);
		selfShopBankInfo.editRowIndex = rowIndex;
		$("#shopName_").combogrid("disable");
	}
};

selfShopBankInfo.validationBeforeSave = function(){
	
	var flag = true;
	
	var insertRows = $('#shopDataGrid').datagrid('getChanges','inserted');
	var updatedRows = $('#shopDataGrid').datagrid('getChanges','updated');
	
	
	var dataRows;
	if(insertRows.length > 0 || updatedRows.length > 0){
		if(insertRows.length > 0){
			dataRows = insertRows;
		}else{
			dataRows = updatedRows;
		}
		
		$.each(dataRows,function(i,row){
			if(row.companyNo == null || row.companyNo == ""){
				flag = false;
				showWarn('当前添加的数据中,公司数据不完整,请完善后 重试.');
			}
//			if(row.mallNo == null || row.mallNo == ""){
//				flag = false;
//				showWarn('当前添加的数据中,商场数据不完整,请完善后 重试.');
//			}
			if(!flag){
				$('#shopDataGrid').datagrid('beginEdit',selfShopBankInfo.editRowIndex);
				selfShopBankInfo.editRowIndex = $('#shopDataGrid').datagrid('getRowIndex',row);
				return false;
			}
		});
	}
	return flag;
};



//保存
selfShopBankInfo.save = function(){
	
	
	if(selfShopBankInfo.endEdit()){
		
		if(!selfShopBankInfo.validationBeforeSave()){
			return;
		}
		
		var valid_url = BasePath + '/self_shop_bank_info/validateShopUnique';
		var url = BasePath + '/self_shop_bank_info/save';
		var insertRows = $('#shopDataGrid').datagrid('getChanges','inserted');
    	var updatedRows = $('#shopDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#shopDataGrid').datagrid('getChanges','deleted');
	    var flag = true;
    	var changeRows = {
    			inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    
	    if(insertRows.length > 0){
	    	var reParam = {
		    	shopNo : insertRows[0].shopNo
		    };
	    	
	    	selfShopBankInfo.commonAjaxRequest(valid_url,reParam,function(checkResult){
	    		if(checkResult.success != null){
	    			showWarn(checkResult.success);
	    			$('#shopDataGrid').datagrid('deleteRow',selfShopBankInfo.editRowIndex);
	    			flag = false;
	    		}
	    	});
	    }
	    if(flag){
	    	selfShopBankInfo.saveToDB(url,changeRows);
	    }
	}
	
};

selfShopBankInfo.saveToDB = function(url,changeRows){
	selfShopBankInfo.commonAjaxRequest(url, changeRows, function(result){
    	if(result){
    		showSuc('保存成功');
    		selfShopBankInfo.search(); 
    	}else{
    		showSuc('保存失败');
    	}
    });
};

// common ajax request 
selfShopBankInfo.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async:false,
		dataType : 'json',
		success : callback
	});
};


/**
 * 页面加载完后执行
 */
$(function(){
//	selfShopBankInfo.init();
	
	$.extend($.fn.validatebox.defaults.rules, {
		onlyNumbers : {// 验证数字
			validator : function(value) {
				return /^[+]?[0-9]+\d*$/i.test(value);
			},
			message : '请输入数字'
		}
	});
	
	$("#" + setting.exportId).on('click',function(){
		
		var default_setting = {
			dataGridId : "shopDataGrid",
			url : "/self_shop_bank_info/do_fas_export",
			title : "店铺信息导出",
			exportType : "common"	
		}
		selfShopBankInfo.exportExcel(default_setting);
	});
	
});


//end the current edit row
selfShopBankInfo.endEdit = function() {
	if($('#shopDataGrid').datagrid('validateRow',selfShopBankInfo.editRowIndex)){
		  $('#shopDataGrid').datagrid('endEdit',selfShopBankInfo.editRowIndex);
		  return true;
	}
	return false;
};




selfShopBankInfo.selectShop = {// 明细行选择选择门店
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
//	    			shopBalanceDate.selectorCallback(data,'shopNo','shortName','shopNo','shortName');
	    			var paramTemp = {
	    				shopNo : 'shopNo',
	    				shopName : 'shortName',
	    				companyNo : 'companyNo',
	    				companyName : 'companyName',
	    				bsgroupsNo : 'bsgroupsNo',
	    				bsgroupsName : 'bsgroupsName',
	    				mallNo : 'mallNo',
	    				mallName : 'mallName'
	    			};
	    			selfShopBankInfo.selectorShopCallBack(data,paramTemp);
	    		}
	    	});
	    }
};


selfShopBankInfo.selectorShopCallBack = function(data,paramTemp){
	
	selfShopBankInfo.clearinitlalizationCompanyAndMall();
	
	var valueEd = $('#shopDataGrid').datagrid('getEditor', {
		'index' : selfShopBankInfo.editRowIndex,
		'field' : paramTemp.shopNo
	});
	
	var textEd = $('#shopDataGrid').datagrid('getEditor', {
		'index' : selfShopBankInfo.editRowIndex,
		'field' : 'shopName'
	});
	valueEd.target.val(data[paramTemp.shopNo]);
	textEd.target.val(data[paramTemp.shopName]);
	
	var url = BasePath + "/shop/initSubInfo";
	var reqParam = {
		shopNo : data[paramTemp.shopNo]
//		,payType : 'U030301'
	};
	
	selfShopBankInfo.commonAjaxRequest(url,reqParam,function(result){
		
		if(typeof result == 'undefined' || result == null){
			showWarn('店铺信息不完整,请配置..');
			return;
		}
		
		if(typeof result.companyNo == 'undefined' || result.companyNo == null || result.companyNo == ""){
			showWarn('当前店铺的公司信息不完整,请完善后重试');
			return;
		}else{
			var valueEd = $('#shopDataGrid').datagrid('getEditor', {
				'index' : selfShopBankInfo.editRowIndex,
				'field' : 'companyNo'
			});
			
			var textEd = $('#shopDataGrid').datagrid('getEditor', {
				'index' : selfShopBankInfo.editRowIndex,
				'field' : 'companyName'
			});
			valueEd.target.val(result.companyNo);
			textEd.target.val(result.companyName);
		}
		
//		if(typeof result.mallNo == 'undefined' || result.mallNo == null){
//			showWarn('当前店铺的商场信息不完整,请完善后重试');
//			return;
//		}else{
//			var valueEd = $('#shopDataGrid').datagrid('getEditor', {
//				'index' : selfShopBankInfo.editRowIndex,
//				'field' : 'mallNo'
//			});
//			
//			var textEd = $('#shopDataGrid').datagrid('getEditor', {
//				'index' : selfShopBankInfo.editRowIndex,
//				'field' : 'mallName'
//			});
//			valueEd.target.val(result.mallNo);
//			textEd.target.val(result.mallName);
//		}
		var valueEd = $('#shopDataGrid').datagrid('getEditor', {
			'index' : selfShopBankInfo.editRowIndex,
			'field' : 'mallNo'
		});
		
		var textEd = $('#shopDataGrid').datagrid('getEditor', {
			'index' : selfShopBankInfo.editRowIndex,
			'field' : 'mallName'
		});
		valueEd.target.val(result.mallNo);
		textEd.target.val(result.mallName);
		
		
	});
};

selfShopBankInfo.clearinitlalizationCompanyAndMall = function(){
	
	var valueEd = $('#shopDataGrid').datagrid('getEditor', {
		'index' : selfShopBankInfo.editRowIndex,
		'field' : 'companyNo'
	});
	
	var textEd = $('#shopDataGrid').datagrid('getEditor', {
		'index' : selfShopBankInfo.editRowIndex,
		'field' : 'companyName'
	});
	valueEd.target.val(null);
	textEd.target.val(null);
	
	
	var valueEd = $('#shopDataGrid').datagrid('getEditor', {
		'index' : selfShopBankInfo.editRowIndex,
		'field' : 'mallNo'
	});
	
	var textEd = $('#shopDataGrid').datagrid('getEditor', {
		'index' : selfShopBankInfo.editRowIndex,
		'field' : 'mallName'
	});
	valueEd.target.val(null);
	textEd.target.val(null);
};



selfShopBankInfo.importOperation = function(){
	
	$.importExcel.open({
		'submitUrl' : BasePath + '/self_shop_bank_info/do_import',
		'templateName' : '店铺信息管理导入.xlsx',
		success : function(result) {
			$.messager.progress('close');
			if (data) {
				if (isNotBlank(data.error)) {
					showError(data.error);
				} else {
					$.importExcel.colse();
					showSuc('数据导入成功');
				}
			} else {
				showInfo('导入失败,请联系管理员!');
			}
		},
		error : function() {
			$.messager.progress('close');
			showWarn('数据导入失败，请联系管理员');
		}
	});
	
};

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};


selfShopBankInfo.exportExcel = function(setting) {
	var $dg = $("#" + setting.dataGridId);
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	
	var subGrepColumns = $dg.datagrid('options').subColumns;
	
	var columns = $.grep(grepColumns[0], function(o, i) {
		if ($(o).attr("notexport") == true) {
			return true;
		}
		return false;
	}, true);
	
	var subColumns = [];
	if(typeof subGrepColumns != 'undefined' 
			&& subGrepColumns != null
			&& subGrepColumns != "") {
		subColumns = $.grep(subGrepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
	}
	
	var exportColumns = JSON.stringify(columns);
	var exportSubColumns = JSON.stringify(subColumns);
	var url = BasePath + setting.url;
	var dataRow = $dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.exportColumns = exportColumns;
				param.exportSubColumns = exportSubColumns;
				param.fileName = setting.title;
				param.exportType = setting.exportType || '';
				if(params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
					});
				}
			},
			success : function() {

			}
		});
	} else {
		showWarn('查询记录为空，不能导出!');
	}
};


