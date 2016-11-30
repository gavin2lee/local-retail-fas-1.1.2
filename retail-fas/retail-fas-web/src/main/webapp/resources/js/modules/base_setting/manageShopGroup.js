var manageShopGroup = {};

var setting = {
		datagridId : "manageShopGroupDG",
		primaryKey : "id",
		saveUrl : "/invoice_rule_shop_set/save",
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

manageShopGroup.modulePath = BasePath + '/invoice_rule_shop_set';

/**
 * 查询店铺
 */
manageShopGroup.domanageShopGroup = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/invoice_rule_shop_set/list.json";          //"/common_util/getShop";
	$("#manageShopGroupDG").datagrid('options').queryParams = reqParam;
	$("#manageShopGroupDG").datagrid('options').url = queryMxURL;
	$("#manageShopGroupDG").datagrid('load');
};

/**
 * 清空查询条件
 */
manageShopGroup.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {
});

manageShopGroup.save = function(){
	if($('#mainDataForm').form('validate')){
		if(manageShopGroup.doValidate()){
			manageShopGroup.savemanageShopGroup();
//			var pkVal = $('#id').val();
//			if(pkVal !=''){
//				manageShopGroup.doPut();
//			}else{
//				manageShopGroup.doPost();
//			}
		}
	};
};

manageShopGroup.doValidate = function(){ // 验证
	if($('#mainDataForm').form('validate')){
		if(manageShopGroup.endEdit()){
			return true;
		}
	}
	return false;
};

manageShopGroup.endEdit = function(){// 结束明细行编辑
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#manageShopGroupDG').datagrid('validateRow', editRowIndex)){
			$('#manageShopGroupDG').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

// 新增保存
manageShopGroup.doPost = function(){
	$('#mainDataForm').form('submit',{
		url : manageShopGroup.modulePath + '/post',
		onSubmit:function(param){
			param.status = 0;
		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
			}else{
				showError('保存失败');
			}
		}
	});
};

// 修改保存
manageShopGroup.doPut = function(){
	$('#mainDataForm').form('submit',{
		url : manageShopGroup.modulePath + '/put',
		onSubmit:function(param){
			param.status = 1;
		},
		success:function(data){
			if(data){
				showSuc('保存成功');
				var jsonData = JSON.parse(data);
			}else{
				showError('保存失败');
			}
		}
	});
};

manageShopGroup.savemanageShopGroup = function(){// 保存明细 
	var insertedData = $('#manageShopGroupDG').datagrid('getChanges','inserted');
	var updatedData = $('#manageShopGroupDG').datagrid('getChanges','updated');
	var deletedData = $('#manageShopGroupDG').datagrid('getChanges','deleted');
//	var backsectionNo = $('#backsectionNo').val();
//	$.each(insertedData, function(index, row) {
//		row.backsectionNo = backsectionNo;
//	});
	
	var data = {
		inserted : JSON.stringify(insertedData),
		updated : JSON.stringify(updatedData),
		deleted : JSON.stringify(deletedData),
	};
//	alert(JSON.stringify(data));
	ajaxRequestAsync(manageShopGroup.modulePath + '/insert',data,function(){
		if(data){
		  	$('#manageShopGroupDG').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
};