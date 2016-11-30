var checkTolerItem = {};

var currentUser;

var numberOption= {type:'numberbox',
				   options:{
			  			min:-9999999,
			  			max:99999999,
			  			precision:2,
			  			required:true
					 }
			      };

checkTolerItem.modulePath = BasePath + '/check_toler';

// 页面初始化
checkTolerItem.initPage = function(){
	checkTolerItem.initEditor();
}

// 初始化编辑器
checkTolerItem.initEditor = function(){
	$("#dtlDataGrid").datagrid("addEditor", 
		[
		 {field : "companyNo", editor : {type:'readonlytext'}},
		 {field : "supplierNo", editor : {type:'readonlytext'}},
		 
		 {field : "companyName", editor : {type:'test_combogrid',
				options:{
					id:'companyName_',
					type:'company',
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'companyNo'
				}
		 }},
		 {field : "supplierName", editor : {type:'test_combogrid',
				options:{
					id:'supplierName_',
					type:'supplier',
					required:true,
					datagridId:'dtlDataGrid',
					valueField:'supplierNo'
				}
		  }},
		 
		  {field : "pretaxTolerLow", editor :  numberOption},
		  {field : "pretaxTolerUp", editor : numberOption},
		  {field : "aftertaxTolerLow", editor : numberOption},
		  {field : "aftertaxTolerUp", editor : numberOption},
		  {field : "notaxTolerLow", editor : numberOption},
		  {field : "notaxTolerUp", editor : numberOption},
		  
		  {field : "effectiveDate", editor : {type:'datebox',
				options:{
		  			required:true
				}
		  }}
		  
		]	
	);
}

//新增
checkTolerItem.add = function() {
	if(checkTolerItem.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
	}
};
 

//结束编辑
checkTolerItem.endEdit = function() {
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#dtlDataGrid').datagrid('validateRow',editRowIndex)){
			  $('#dtlDataGrid').datagrid('endEdit',editRowIndex);
			  return true;
		}
		return false;
	}
	return true;
};

// 保存
checkTolerItem.save = function(){
	if(checkTolerItem.endEdit()){
	    var url = checkTolerItem.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    $.each(insertRows,function(index, item){
	    	item.createUser = currentUser.username;
	    	item.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result) {
				if(result.message!=null&&result.message!=""){
					showWarn(result.message);
				}else{
					showInfo("操作成功!");
				}
				invoiceSet.search();
				//如果是弹出页面，则关闭页面
				if(win) {
					win.close();
				}
				return;
			} else {
				showError("操作失败,请联系管理员!");
			}
	    });
	}
};

//查询
checkTolerItem.searchData = function() {
	var reqParam = $('#searchForm').form('getData');
	$("#dtlDataGrid").datagrid('options').queryParams = reqParam;
	$("#dtlDataGrid").datagrid('options').url = 'list.json';
	$("#dtlDataGrid").datagrid('load');
};

//清空
checkTolerItem.searchClear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=checkTolerType]").val("");
};

$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
    setTimeout(checkTolerItem.initPage,500);
});

