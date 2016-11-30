var shopBalanceDeduct = new Object();

shopBalanceDeduct.modulePath = BasePath + '/bill_shop_balance_deduct';

var setting = {
		datagridId : "shopBalanceDeductDataGrid",
		primaryKey : "id",
//		saveUrl : "/bill_shop_balance_deduct/save",
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
var datacostDeductType =[{'value':'1', 'text': '票前'}, {'value':'2', 'text':'票后'}];
var datacostPayType =[{'value':'0', 'text': ''},{'value':'1', 'text': '帐扣'}, {'value':'2', 'text':'现金'}];
var billStatus =[{'value':'1', 'text': '未结'}, {'value':'2', 'text':'已结'}, {'value':'3', 'text':'已开票'}];

$(function() {
	shopBalanceDeduct.initSelectbrand();
	// 双击空白处，新增一行
	$("#dataGridDiv").bind("dblclick", function() {
		fas_common_editor.insertRow();
	});
});

shopBalanceDeduct.search = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/bill_shop_balance_deduct/list.json';
	$("#shopBalanceDeductDataGrid").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
	$("#shopBalanceDeductDataGrid").datagrid('options').url = queryMxURL;
	$("#shopBalanceDeductDataGrid").datagrid('load');
};

//清空数据
shopBalanceDeduct.clearData = function(){
	$('#searchForm').find("input").val("");
	$('#searchForm').find("textarea").val("");
};

shopBalanceDeduct.check = function(rowIndex,rowData){
	var getStatusValue=rowData.status;
	if(getStatusValue == 2 ){
		showWarn("费用单已结,不能修改");
		return getStatusValue == 2;
	}
	if(getStatusValue == 3){
		showWarn("费用单已开票,不能修改");
		return getStatusValue == 2;
	}
	return getStatusValue == 2;
};

shopBalanceDeduct.createBalanceDeduct = function(){ 
	if($('#searchForm').form('validate')){
	var companyName = $('#companyName').val();
	var companyNo = $('#companyNo').val();
	var shortName = $('#shopName').val();
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
	alert(shortName);	alert(shopNo);
	if(companyName < 1){
		showWarn("公司为空,请选择要生成费用的公司!");
		return;
	}
	if(shortName < 1){
		showWarn("店铺为空,请选择要生成费用的店铺!");
		return;
	}
	if(month < 1){
		showWarn("结算月为空,请选择要生成费用的结算月!");
		return;
	}
	//检查相关条件的费用是否已经生成过
	
	message = "确定要生成"+companyName+"、" +shortName+"、" +month+" 的费用？";
	$.messager.confirm("确认",message,function(r) {
		if (r) {
			var url = shopBalanceDeduct.modulePath + '/saveBalanceDeduct';
			var deductObj ="";
//			$.each(checkedRows, function(index, row) {				
//				idList+=row.id+','+row.billNo+";";
//			});
			deductObj=companyNo+";"+companyName+";"+shopNo+";"+shortName+";"+month+";";
//			var params = {deductList : deductList.substring(0, deductList.length-1)}; //{id:idList};//
			ajaxRequestAsync(url,{"deductObj" : deductObj},function(count){
				if(count>0){
					showSuc('保存成功');
					shopBalanceDeduct.search();
					$("#shopBalanceDeductDataGrid").datagrid('load');
				}else if(count==-1){
					showError('保存失败，结算期设置错误，请检查是否设置并为未结算状态；'); 
				}else if(count==-2){
					showError('保存失败，已存在已扣数据，请检查数据是否重复操作；');
				}else if(count==-3){
					showError('保存失败，请检查数据有效性，已存在重复数据；');
				}else if(count==-4){
					showError('保存费用失败，请检查数据，没有费用生成；');
				}
				else{
					showError('保存失败，检查数据是否有效，没有费用生成；');
				}
				return;
			});
		}
	});
	}
};

shopBalanceDeduct.initSelectbrand= function() {
	if($('#brandNo').length > 0){
	   fas_common.initCombox({
			id : 'brandNo',
			url : "/brand/get_biz",
			valueId : 'brandNo',
			textId : 'name',
	   });
	}
};


shopBalanceDeduct.statusformatter = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < dataStatus.length; i++) {
        if (dataStatus[i].value == value) {
            return dataStatus[i].text;
        }
    }
};


shopBalanceDeduct.billStatus = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < billStatus.length; i++) {
        if (billStatus[i].value == value) {
            return billStatus[i].text;
        }
    }
};

shopBalanceDeduct.dataCostType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostType.length; i++) {
        if (datacostType[i].value == value) {
            return datacostType[i].text;
        }
    }
};

shopBalanceDeduct.dataCostDeductType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostDeductType.length; i++) {
        if (datacostDeductType[i].value == value) {
            return datacostDeductType[i].text;
        }
    }
};

shopBalanceDeduct.dataCostPayType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostPayType.length; i++) {
        if (datacostPayType[i].value == value) {
            return datacostPayType[i].text;
        }
    }
};

shopBalanceDeduct.save = function(){
	if($('#mainDataForm').form('validate')){
		if(shopBalanceDeduct.doValidate()){
			shopBalanceDeduct.saveshopBalanceDeduct();
//			var pkVal = $('#id').val();
//			if(pkVal !=''){
//				shopBalanceDeduct.doPut();
//			}else{
//				shopBalanceDeduct.doPost();
//			}
		}
	};
};

shopBalanceDeduct.doValidate = function(){ // 验证
	if($('#mainDataForm').form('validate')){
		if(shopBalanceDeduct.endEdit()){
			return true;
		}
	}
	return false;
};

shopBalanceDeduct.edit = function(rowIndex,data) {
	
	if(shopBalanceDeduct.endEdit()){
		$('#shopBalanceDeductDataGrid').datagrid('beginEdit',rowIndex);
		shopBalanceDeduct.editRowIndex = rowIndex;
	}
	
	var valueEd = $('#shopBalanceDeductDataGrid').datagrid('getEditor', {
		'index' : rowIndex,
		'field' : 'month'
	});
	valueEd.target.val(data.month);
	
};

shopBalanceDeduct.endEdit = function(){// 结束明细行编辑
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#shopBalanceDeductDataGrid').datagrid('validateRow', editRowIndex)){
			$('#shopBalanceDeductDataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

// 新增保存
shopBalanceDeduct.doPost = function(){
	$('#mainDataForm').form('submit',{
		url : shopBalanceDeduct.modulePath + '/post',
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
shopBalanceDeduct.doPut = function(){
	$('#mainDataForm').form('submit',{
		url : shopBalanceDeduct.modulePath + '/put',
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


//删除
shopBalanceDeduct.deleteRow = function() {
	var delFlag = false;
  var checkedRows = $('#shopBalanceDeductDataGrid').datagrid('getChecked');
  var rowIndex;
  $.each(checkedRows,function(index,row){
  	rowIndex = $('#shopBalanceDeductDataGrid').datagrid('getRowIndex',row);
  	if(row.status == '2'){
 		 showWarn("已结，不能删除！");
 		 return;
 	    }
	if(row.status == '4'){
		 showWarn("已开票，不能删除！");
		 return;
	    }
  	$('#shopBalanceDeductDataGrid').datagrid('deleteRow',rowIndex);
  	if(shopBalanceDeduct.editRowIndex == rowIndex){
  		shopBalanceDeduct.editRowIndex =  -1;
  	}
  });
  return !delFlag;
};

shopBalanceDeduct.saveshopBalanceDeduct = function(){// 保存明细 
	var insertedData = $('#shopBalanceDeductDataGrid').datagrid('getChanges','inserted');
	var updatedData = $('#shopBalanceDeductDataGrid').datagrid('getChanges','updated');
	var deletedData = $('#shopBalanceDeductDataGrid').datagrid('getChanges','deleted');
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
	ajaxRequestAsync(shopBalanceDeduct.modulePath + '/save',data,function(){
		if(data){
		  	$('#shopBalanceDeductDataGrid').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
};