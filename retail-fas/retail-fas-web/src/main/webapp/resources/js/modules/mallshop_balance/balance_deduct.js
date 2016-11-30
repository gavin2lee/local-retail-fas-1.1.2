var balanceDeduct = new Object();

balanceDeduct.modulePath = BasePath + '/bill_shop_balance_deduct';

balanceDeduct.shopBalancemodulePath = BasePath + '/mall_shopbalance';

var setting = {
		datagridId : "balanceDeductDataGrid",
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
var datacostPayType =[{'value':'0', 'text': '  '},{'value':'1', 'text': '帐扣'}, {'value':'2', 'text':'现金'}];
var processStatus =[{'value':'1', 'text': '未完成'}, {'value':'2', 'text':'已完成'}];

$(function() {
	balanceDeduct.initSelectbrand();
});

balanceDeduct.search = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/bill_shop_balance_deduct/list.json';
	$("#balanceDeductDataGrid").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
	$("#balanceDeductDataGrid").datagrid('options').url = queryMxURL;
	$("#balanceDeductDataGrid").datagrid('load');
};

//清空数据
balanceDeduct.clearData = function(){
	$('#searchForm').find("input").val("");
	$('#searchForm').find("textarea").val("");
};

balanceDeduct.check = function(rowIndex,rowData){
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return true;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return true;
	}
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

balanceDeduct.createBalanceDeduct = function(){ 
	if($('#searchForm').form('validate')){
	var companyName = $('#companyName').val();
	var companyNo = $('#companyNo').val();
	var shortName = $('#shortName').val();
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
	
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
			var url = balanceDeduct.modulePath + '/saveBalanceDeduct';
			var deductObj ="";
//			$.each(checkedRows, function(index, row) {				
//				idList+=row.id+','+row.billNo+";";
//			});
			deductObj=companyNo+";"+companyName+";"+shopNo+";"+shortName+";"+month+";";
//			var params = {deductList : deductList.substring(0, deductList.length-1)}; //{id:idList};//
			ajaxRequestAsync(url,{"deductObj" : deductObj},function(count){
				if(count){
					showSuc('保存成功');
					balanceDeduct.search();
					$("#balanceDeductDataGrid").datagrid('load');
				}else{
					showError('保存失败');
				}
				
			});
		}
	});
	}
};

//balanceDeduct.check = function(rowIndex,data){
//	var getStatusValue=data.status;
//	return getStatusValue == 2;
//};

//修改
balanceDeduct.edit = function(rowIndex) {
	if(balanceDeduct.endEdit()){
		$('#balanceDeductDataGrid').datagrid('beginEdit',rowIndex);
		balanceDeduct.editRowIndex = rowIndex;
	}
	var valueEd = $('#shopBalanceDeductDataGrid').datagrid('getEditor', {
		'index' : rowIndex,
		'field' : 'month'
	});
	valueEd.target.val(data.month);
};

balanceDeduct.initSelectbrand= function() {
	if($('#brandNo').length > 0){
	   fas_common.initCombox({
			id : 'brandNo',
			url : "/brand/get_biz",
			valueId : 'brandNo',
			textId : 'name',
	   });
	}
};


balanceDeduct.statusformatter = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < dataStatus.length; i++) {
        if (dataStatus[i].value == value) {
            return dataStatus[i].text;
        }
    }
};


balanceDeduct.processStatus = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < processStatus.length; i++) {
        if (processStatus[i].value == value) {
            return processStatus[i].text;
        }
    }
};

balanceDeduct.dataCostType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostType.length; i++) {
        if (datacostType[i].value == value) {
            return datacostType[i].text;
        }
    }
};

balanceDeduct.dataCostDeductType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostDeductType.length; i++) {
        if (datacostDeductType[i].value == value) {
            return datacostDeductType[i].text;
        }
    }
};

balanceDeduct.dataCostPayType = function(value, rowData, rowIndex) {
    if (value == 0) {
        return;
    }
    for (var i = 0; i < datacostPayType.length; i++) {
        if (datacostPayType[i].value == value) {
            return datacostPayType[i].text;
        }
    }
};

balanceDeduct.save = function(){
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return;
	}
	if($('#mainDataForm').form('validate')){
		if(balanceDeduct.doValidate()){
			balanceDeduct.savebalanceDeduct();
//			var pkVal = $('#id').val();
//			if(pkVal !=''){
//				balanceDeduct.doPut();
//			}else{
//				balanceDeduct.doPost();
//			}
		}
	};
};

balanceDeduct.doValidate = function(){ // 验证
	if($('#mainDataForm').form('validate')){
		if(balanceDeduct.endEdit()){
			return true;
		}
	}
	return false;
};

balanceDeduct.endEdit = function(){// 结束明细行编辑
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#balanceDeductDataGrid').datagrid('validateRow', editRowIndex)){
			$('#balanceDeductDataGrid').datagrid('endEdit',editRowIndex);
			return true;
		}else{
			return false;
		}
	} 
	return true;
};

// 新增保存
balanceDeduct.doPost = function(){
	$('#mainDataForm').form('submit',{
		url : balanceDeduct.modulePath + '/post',
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
balanceDeduct.doPut = function(){
	$('#mainDataForm').form('submit',{
		url : balanceDeduct.modulePath + '/put',
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

balanceDeduct.savebalanceDeduct = function(){// 保存明细 
	var insertedData = $('#balanceDeductDataGrid').datagrid('getChanges','inserted');
	var updatedData = $('#balanceDeductDataGrid').datagrid('getChanges','updated');
	var deletedData = $('#balanceDeductDataGrid').datagrid('getChanges','deleted');
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
	ajaxRequestAsync(balanceDeduct.modulePath + '/save',data,function(){
		if(data){
		  	$('#balanceDeductDataGrid').datagrid('acceptChanges');
			showSuc('保存成功');
			
			var companyNo = $('#companyNo').val();
			var shopNo = $('#shopNo').val();
			var month = $('#month').val();
			var startDate = $('#balanceStartDate').val();
			var endDate = $('#balanceEndDate').val();
			var balanceNo = $('#balanceNo').val();
			
//	                  把费用生成没有结算单单号的更新
			
			var url = balanceDeduct.modulePath + '/updateBalanceDeductBalanceNo';
			var params = {companyNo:companyNo,shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,costDeductType:1,balanceNo:balanceNo};
			ajaxRequestAsync(url,params,function(result){
				
			});
			
//			更新结算单上的票前费用金额
			var balanceDeductAmount = $('#balanceDeductAmount').val();
//			alert(balanceDeductAmount);
			
			
			var url = balanceDeduct.shopBalancemodulePath + '/getBalanceDeductAmount';
			var shopBalanceObj ="";
			shopBalanceObj=companyNo+";"+shopNo+";"+month+";"+balanceNo+";"+startDate+";"+endDate+";";
			ajaxRequestAsync(url,{"shopBalanceObj" : shopBalanceObj},function(result){
				$('#balanceDeductAmount').numberbox('setValue',result.balanceDeductAmount);//页面赋值显示
				$('#billingAmount').numberbox('setValue',result.billingAmount);
				$('#otherTotalAmount').numberbox('setValue',result.otherTotalAmount);
				$('#actualRate').numberbox('setValue',result.actualRate);
//				同时 要 update bill_shop_balance 相关字段设置
			});
				var params = {shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,costDeductType:1,balanceNo:balanceNo};//,status:1
	    		var url = balanceDeduct.modulePath + '/list.json';
	    	    $( '#balanceDeductDataGrid').datagrid( 'options').queryParams= params;
	    	    $( '#balanceDeductDataGrid').datagrid( 'options').url=url;
	    	    $( '#balanceDeductDataGrid').datagrid( 'load' );
			} 
		else{
			showError('保存失败');
		}
	});
};