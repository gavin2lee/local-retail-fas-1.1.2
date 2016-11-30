var mallDeductionCost = new Object();

mallDeductionCost.modulePath = BasePath + '/mall_deductcost';

var setting = {
		datagridId : "deductCostdataGrid",
		primaryKey : "id",
		saveUrl : "/mall_deductcost/save",
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

var dataStatus =[{'value':'1', 'text': '停用'}, {'value':'2', 'text':'启用'}];
var datacostType =[{'value':'1', 'text': '合同内'}, {'value':'2', 'text':'合同外'}];
var datacostDeductType =[{'value':'1', 'text': '票后'}, {'value':'2', 'text':'票前'}];
var datacostPayType =[{'value':'1', 'text': '帐扣'}, {'value':'2', 'text':'现金'}];


$(function() {
	mallDeductionCost.initSelectbrand();
});


mallDeductionCost.initSelectbrand= function() {
	if($('#brandNo').length > 0){
	   fas_common.initCombox({
			id : 'brandNo',
			url : "/brand/get_biz",
			valueId : 'brandNo',
			textId : 'name',
	   });
	}
};


mallDeductionCost.statusformatter = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < dataStatus.length; i++) {
        if (dataStatus[i].value == value) {
            return dataStatus[i].text;
        }
    }
};

mallDeductionCost.dataCostType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostType.length; i++) {
        if (datacostType[i].value == value) {
            return datacostType[i].text;
        }
    }
};

mallDeductionCost.dataCostDeductType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostDeductType.length; i++) {
        if (datacostDeductType[i].value == value) {
            return datacostDeductType[i].text;
        }
    }
};

mallDeductionCost.dataCostPayType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostPayType.length; i++) {
        if (datacostPayType[i].value == value) {
            return datacostPayType[i].text;
        }
    }
};

mallDeductionCost.save = function(){
	if($('#mainDataForm').form('validate')){
		if(mallDeductionCost.doValidate()){
			mallDeductionCost.savemallDeductionCost();
//			var pkVal = $('#id').val();
//			if(pkVal !=''){
//				mallDeductionCost.doPut();
//			}else{
//				mallDeductionCost.doPost();
//			}
		}
	};
};

mallDeductionCost.doValidate = function(){ // 验证
	if($('#mainDataForm').form('validate')){
		if(mallDeductionCost.endEdit()){
			return true;
		}
	}
	return false;
};

mallDeductionCost.endEdit = function(){// 结束明细行编辑
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#deductCostdataGrid').datagrid('validateRow', editRowIndex)){
			$('#deductCostdataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

// 新增保存
mallDeductionCost.doPost = function(){
	$('#mainDataForm').form('submit',{
		url : mallDeductionCost.modulePath + '/post',
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
mallDeductionCost.doPut = function(){
	$('#mainDataForm').form('submit',{
		url : mallDeductionCost.modulePath + '/put',
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

mallDeductionCost.savemallDeductionCost = function(){// 保存明细 
	var insertedData = $('#deductCostdataGrid').datagrid('getChanges','inserted');
	var updatedData = $('#deductCostdataGrid').datagrid('getChanges','updated');
	var deletedData = $('#deductCostdataGrid').datagrid('getChanges','deleted');
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
	ajaxRequestAsync(mallDeductionCost.modulePath + '/save',data,function(){
		if(data){
		  	$('#deductCostdataGrid').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
};