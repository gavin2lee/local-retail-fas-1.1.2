var payRelationship = new Object();

//查询
payRelationship.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/pay_relationship/list.json';
		payRelationship.selectDg.datagrid('options').queryParams= params;
		payRelationship.selectDg.datagrid('options').url= url;
		payRelationship.selectDg.datagrid('load');
	}
};

// 导出
payRelationship.doExport = function(){
	var searchType = $('#searchType').val();
	if(searchType == 'bill'){
		pe_util.doExport('dtlDataGrid', '/pay_relationship/export_data', '总部到货核对-单据信息',{searchType:searchType});
	}else{
		pe_util.doExport('dtlDataGrid1', '/pay_relationship/export_data', '总部到货核对-明细信息',{searchType:searchType});
	}
	
}

//导入
payRelationship.doImport = function(type) {
	if(type == 1){
		pe_util.doImport('厂商金额导入.xlsx','/pay_relationship/do_import_cost',1,payRelationship.importCallBack);
	}else if(type == 2){
		pe_util.doImport('对账折扣导入.xlsx','/pay_relationship/do_import_discount',1,payRelationship.importCallBack);
	}else if(type == 3){
		pe_util.doImport('牌价导入.xlsx','/pay_relationship/do_import_tag_price',1,payRelationship.importCallBack);
	}
	
};

//导入回调
payRelationship.importCallBack = function(data){
	if(data.indexOf('"success":true')!=-1){
		showSuc("操作完成！");
		payRelationship.search();
	}else{
		showError("数据不合法,导入失败!");
	}
}

//清空
payRelationship.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("tbody").find("input").val("");
};

//获取选中行
payRelationship.setChecked = function(params){
	var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
	if(checkedRows.length >0){
		var checkedAll = $("#dtlTab").find(".datagrid-header-check").find("input[type=checkbox]").attr("checked");
		if(checkedAll && checkedAll == 'checked'){
			if($('#checkedAll').attr("checked") == 'checked'){
				params.checkedAll = true;
				return true;
			}
		}
		params.checkedRows = JSON.stringify(checkedRows);
		return true;
	}
	showError("请选中需要操作的记录！");
	return false;
}

//表单提交
payRelationship.doSubmit = function(url,params,type){
	$("#submitForm").remove();
	$("<form id='submitForm'  method='post'></form>").appendTo("body"); 
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理...'
	}); 
	$('#submitForm').form('submit',{
		url : url,
		onSubmit:function(param){
			if(params && params!={}){
				$.each(params,function(i){
					param[i]=params[i];
				});
			}
		},
		success:function(data){
			showSuc('操作成功');
			$.messager.progress('close');
			setTimeout(function(){
				payRelationship.search();
			},500);
			if(type == "balance"){
				if(JSON.parse(data).count >0){
					showInfo("成功生成"+JSON.parse(data).count+"条结算单!");
				}else{
					showInfo("请检查单据是否已对账或已结算!");
				}
			}
		},
		error:function(){
			showError('操作失败');
			$.messager.progress('close');
		}
	});
};

//折扣更新
payRelationship.updateDiscount = function(){
	var url = BasePath + '/pay_relationship/update_discount';
	var params = $('#searchForm').form('getData');
	var dataRow=payRelationship.selectDg.datagrid('getRows');
	if(dataRow.length >0){
		$.messager.confirm("确认","你确定要更新单据的折扣?",function(r) {
			if (r) {
				payRelationship.doSubmit(url,params); 
			}
		});
	}else{
		showInfo("查询记录为空!");
	}
};

//价格更新
payRelationship.updateCost = function(){
	var url = BasePath + '/pay_relationship/update_cost';
	var params = $('#searchForm').form('getData');
	var dataRow=payRelationship.selectDg.datagrid('getRows');
	if(dataRow.length >0){
		$.messager.confirm("确认","你确定要更新单据的价格?",function(r) {
			if (r) {
				payRelationship.doSubmit(url,params,"price"); 
			}
		});
	}else{
		showInfo("查询记录为空!");
	}
};

//到期日更新
payRelationship.updateDueDate = function(){
	var url = BasePath + '/pay_relationship/update_due_date';
	var params = $('#searchForm').form('getData');
	var dataRow=payRelationship.selectDg.datagrid('getRows');
	if(dataRow.length >0){
		$.messager.confirm("确认","你确定要更新单据的到期日?",function(r) {
			if (r) {
				payRelationship.doSubmit(url,params,"date"); 
			}
		});
	}else{
		showInfo("查询记录为空!");
	}
};

//生成结算单
payRelationship.generateBalance = function(type){
	var url = BasePath + '/pay_relationship/generate_balance';
	var params = $('#searchForm').form('getData');
	var dataRow=payRelationship.selectDg.datagrid('getRows');
	params.isBalance = 0;
	if(type == 1){
		params.balanceFlag = 1;
	}
	if(dataRow.length >0){
		$.messager.confirm("确认","你确定要生成单据的结算单?",function(r) {
			if (r) {
				payRelationship.doSubmit(url,params,"balance"); 
			}
		});
	}else{
		showInfo("查询记录为空!");
	}
};

payRelationship.selectDg;

$(function(){
	payRelationship.selectDg = $('#dtlDataGrid');
	$('#searchType').val('bill');
	$('#dtlTab').tabs({    
	    onSelect:function(title){
	    	if(title == '单据信息'){
	    		payRelationship.selectDg = $('#dtlDataGrid');
	    		$('#searchType').val('bill');
	    	}else if(title == '明细信息'){
	    		payRelationship.selectDg = $('#dtlDataGrid1');
	    		$('#searchType').val('item');
	    	}
	    }    
	}); 
});
