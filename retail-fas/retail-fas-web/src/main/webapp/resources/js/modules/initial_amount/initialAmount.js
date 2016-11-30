var initialAmount = new Object();

var currentUser;

// 主表模块路径
initialAmount.modulePath = BasePath + '/initial_amount';

//清空
initialAmount.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
};

//查询
initialAmount.search = function() {
	$('#dtlDataGrid').datagrid('acceptChanges');
	var params = $('#searchForm').form('getData');
	var url = initialAmount.modulePath + '/list.json';
    $('#dtlDataGrid').datagrid('options').queryParams= params;
    $('#dtlDataGrid').datagrid('options').url= url;
    $('#dtlDataGrid').datagrid('load');
};


//新增
initialAmount.add = function() {
	if(initialAmount.endEdit()){
	    $('#dtlDataGrid').datagrid('appendRow',{});
	    var length = $('#dtlDataGrid').datagrid('getRows').length;
		$('#dtlDataGrid').datagrid('beginEdit',length-1);
		var currencyEd = $("#dtlDataGrid").datagrid('getEditor',{index:length-1,field:'currency'});
		$(currencyEd.target).combobox('setValue', 0);
		initialAmount.bindBlur(); 
	}
};

//修改
initialAmount.edit = function(rowIndex) {
	if(initialAmount.endEdit()){
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
		initialAmount.bindBlur();
	}
};

// 金额事件绑定
initialAmount.bindBlur = function(){
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var qtyEd = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'qty'});
	var costEd = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'cost'});
	var amountEd = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'amount'});
	$(qtyEd.target).bind('blur',function(){
		var qty = $(qtyEd.target).numberbox('getValue');
		var cost =  $(costEd.target).numberbox('getValue');
		if(qty*cost != 0){
			$(amountEd.target).numberbox('setValue',qty*cost);
		}
	});
	$(costEd.target).bind('blur',function(){
		var qty = $(qtyEd.target).numberbox('getValue');
		var cost =  $(costEd.target).numberbox('getValue');
		if(qty*cost != 0){
			$(amountEd.target).numberbox('setValue',qty*cost);
		}
	});
	$(amountEd.target).bind('blur',function(){
		var qty = $(qtyEd.target).numberbox('getValue');
		var cost =  $(costEd.target).numberbox('getValue');
		var amount =  $(amountEd.target).numberbox('getValue');
		if(amount != qty*cost){
			$(qtyEd.target).numberbox('setValue',0);
			$(costEd.target).numberbox('setValue',0);
		}
	});
};


//删除
initialAmount.del = function() {
    var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
    var rowIndex;
    if(checkedRows.length > 0){
        $.each(checkedRows,function(index,row){
        	rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
        	$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
        });
    }else{
    	showWarn("请选中需要删除的记录!");
    }
};

//结束编辑
initialAmount.endEdit = function() {
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

//明细行选择公司
initialAmount.selectCompany = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择公司',
    		width : 500,
    		height : 'auto',
    		href : BasePath + '/common_util/doSelect?selectUrl=getCompany&no=companyNo&name=name&params=notGroupLeadRole',
    		fn : function(data, rowIndex) {
    			initialAmount.selectorCallback(data,'code','name','companyNo','companyName');
    		}
    	});
    }
};

//明细行选择供应商
initialAmount.selectSupplier = {	
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择供应商',
    		width : 500,
    		height : 'auto',
    		href : BasePath + '/common_util/doSelect?selectUrl=getSupplier&no=supplierNo&name=fullName',
    		fn : function(data, rowIndex) {
    			initialAmount.selectorCallback(data,'code','name','supplierNo','supplierName');
    		}
    	});
    }
};

//明细行选择客户
initialAmount.selectCustomer = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择客户',
    		width : 500,
    		height : 'auto',
    		href : BasePath + '/common_util/doSelect?selectUrl=getCompany&no=companyNo&name=name',
    		fn : function(data, rowIndex) {
    			initialAmount.selectorCallback(data,'code','name','customerNo','customerName');
    		}
    	});
    }
};

//明细行选择商品
initialAmount.selectItem = {
    clickFn:function(){
    	dgSelector({
    		title : '选择商品',
    		width : 500,
    		height : 'auto',
    		href : BasePath + '/common_util/doSelect?selectUrl=getItem&no=code&name=name',
    		fn : function(data, rowIndex) {
    			initialAmount.selectorCallback(data,'code','name','itemCode','itemName');
    		}
    	});
    }
};

//选择后回调
initialAmount.selectorCallback = function(data,value,text,valueField,textField){
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

//保存
initialAmount.save = function() {
	if(initialAmount.endEdit()){
	    var url = initialAmount.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var balanceType = $("#balanceType").val();
	    $.each(insertRows,function(index, item){
	    	item.balanceType = balanceType;
	    	item.createUser = currentUser.loginName;
	    	item.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    $.each(updatedRows,function(index, item){
	    	item.updateUser = currentUser.loginName;
	    	item.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');
	    		initialAmount.search(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};

initialAmount.importCallBack = function(result) {
	var resultJson = JSON.parse(result);
	if(resultJson.success){
		$('#importDataGrid').datagrid({
			data: resultJson.data,
			rowStyler: function(index,row){
				if (row.pass == 0){
					return 'background-color:#D4E6E7;';
				}
			}
		});
		ygDialog({
			title : '导入结果',
			target : $('#myFormPanel'),
			width :  850,
			height : 'auto',
			buttons : [{
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
				}
			}]
		});
		showSuc("操作成功!");
	}else{
		showError("操作失败,请检查数据有效性!");
	}
	
};

// 初始化
$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
});
