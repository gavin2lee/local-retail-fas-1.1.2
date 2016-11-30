var shopBalanceDiff = new Object();

//当前用户
shopBalanceDiff.currentUser;

//当前编辑行
shopBalanceDiff.editRowIndex = -1;

// 主表模块路径
shopBalanceDiff.modulePath = BasePath + '/bill_shop_balance_diff';

shopBalanceDiff.shopBalancemodulePath = BasePath + '/mall_shopbalance';

//清空
shopBalanceDiff.clear = function() {
	$('#searchForm').form("clear");
};

//查询
shopBalanceDiff.search = function() {
	var params = $('#searchForm').form('getData');
	var url = shopBalanceDiff.modulePath + '/list.json';
    $('#balanceDiffDataGrid').datagrid('options').queryParams= params;
    $('#balanceDiffDataGrid').datagrid('options').url= url;
    $('#balanceDiffDataGrid').datagrid('load');
};

shopBalanceDiff.commonCheckAjaxRequestAsync = function(url,paramType,noteInfo){
	var retResult = false;
	ajaxRequestAsync(url,paramType,function(result){
		if(result == noteInfo.result){
			retResult = true;
		}else{
			showWarn(noteInfo.warnInfo);
			retResult = false;
		}
	});
	return retResult;
};

//新增
shopBalanceDiff.add = function() {
	//结算单差异页签在未生成下期结算单之前都可以修改（不受结算单状态影响）  店铺结算单最大的结算期>当前结算单的结算期
	
	var url_check = shopBalanceDiff.shopBalancemodulePath + '/checkIsExisting';
	var noteInfo = {
	    	result : 'success',
	    	warnInfo : '已经生成下期结算单不可新增'
	    };
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
    
	var params = {
			shopNo:shopNo,month:month,
		};
	var retResult = shopBalanceDiff.commonCheckAjaxRequestAsync(url_check,params,noteInfo);

	if(retResult == true){
	/**
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return;
	}
	**/
	if(shopBalanceDiff.endEdit()){
		var billNo = $('#billNo').val();
		var companyNo = $('#companyNo').val();
		var companyName = $('#companyName').val();
		var shopNo = $('#shopNo').val();
		var shortName = $('#shortName').val();
		var month = $('#month').val();
		var balanceNo = $('#balanceNo').val();
	    $('#balanceDiffDataGrid').datagrid('appendRow', {billNo:billNo,balanceNo:balanceNo,companyNo:companyNo,companyName:companyName,shopNo:shopNo,shortName:shortName,month:month});
	    var length = $('#balanceDiffDataGrid').datagrid('getRows').length;
		$('#balanceDiffDataGrid').datagrid('beginEdit',length-1);
	    shopBalanceDiff.editRowIndex = length-1;
	
	}
	}
};


//复制
shopBalanceDiff.copyOneLine = function(){
	var checkedRows = $('#balanceDiffDataGrid').datagrid('getChecked');
    
    $.each(checkedRows,function(index,row){
    	
    	//add one more line , and padding the data
    	$('#balanceDiffDataGrid').datagrid('appendRow',{
    		companyNo : row.companyNo,
    		companyName : row.companyName,
    		bsgroupsNo : row.bsgroupsNo,
    		bsgroupsName : row.bsgroupsName,
    		mallNo : row.mallNo,
    		mallName : row.mallName,
    		shopNo : row.shopNo,
    		shortName : row.shortName,
    		//default will be '未结算'
    		balanceFlag : 1
    	});
    	
    	//edit status
    	var length = $('#balanceDiffDataGrid').datagrid('getRows').length;
    	shopBalanceDiff.edit(length-1);
    	
    	//means add to db , not update to db
    	$('#balanceDiffDataGrid').datagrid('beginEdit',length-1);
	    shopBalanceDiff.editRowIndex = length-1;
    });
};

//删除
shopBalanceDiff.del = function() {
	/**
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return;
	}*/
	var url_check = shopBalanceDiff.shopBalancemodulePath + '/checkIsExisting';
	var noteInfo = {
	    	result : 'success',
	    	warnInfo : '已经生成下期结算单不可删除'
	    };
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
    
	var params = {
			shopNo:shopNo,month:month,
		};
	var retResult = shopBalanceDiff.commonCheckAjaxRequestAsync(url_check,params,noteInfo);

	if(retResult == true){
    var checkedRows = $('#balanceDiffDataGrid').datagrid('getChecked');
    var rowIndex;
    $.each(checkedRows,function(index,row){
    	rowIndex = $('#balanceDiffDataGrid').datagrid('getRowIndex',row);
    	$('#balanceDiffDataGrid').datagrid('deleteRow',rowIndex);
    	if(shopBalanceDiff.editRowIndex == rowIndex){
    		shopBalanceDiff.editRowIndex =  -1;
    	}
    });
	}
};

//保存
shopBalanceDiff.save = function() {
	/**
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return;
	}
	**/
	var url_check = shopBalanceDiff.shopBalancemodulePath + '/checkIsExisting';
	var noteInfo = {
	    	result : 'success',
	    	warnInfo : '已经生成下期结算单不可保存'
	    };
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
    
	var params = {
			shopNo:shopNo,month:month,
		};
	var retResult = shopBalanceDiff.commonCheckAjaxRequestAsync(url_check,params,noteInfo);

	if(retResult == true){
	if(shopBalanceDiff.endEdit()){
	    var url = shopBalanceDiff.modulePath + '/save';
	    var insertRows = $('#balanceDiffDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#balanceDiffDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#balanceDiffDataGrid').datagrid('getChanges','deleted');
	    
	    
//	    var billNo = $('#billNo').val();
//		$.each(insertedData, function(index, row) {
//			row.billNo = billNo;
//		});
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');	
	    		var companyNo = $('#companyNo').val();
	    		var shopNo = $('#shopNo').val();
				var month = $('#month').val();
				var startDate = $('#balanceStartDate').val();
				var endDate = $('#balanceEndDate').val();
				var balanceNo = $('#balanceNo').val();
				
				
//				更新结算单上的结算差异金额
				var balanceDiffAmount = $('#balanceDiffAmount').val();
//				alert(balanceDeductAmount);
				
				var url = shopBalanceDiff.shopBalancemodulePath + '/getBalanceDiffAmountBill';
				var shopBalanceObj ="";
				shopBalanceObj=companyNo+";"+shopNo+";"+month+";"+balanceNo+";"+startDate+";"+endDate+";";
				ajaxRequestAsync(url,{"shopBalanceObj" : shopBalanceObj},function(result){
					$('#balanceDiffAmount').numberbox('setValue',result.balanceDiffAmount);//页面赋值显示
					$('#billingAmount').numberbox('setValue',result.billingAmount);
					$('#systemBillingAmount').numberbox('setValue',result.systemBillingAmount);
					$('#otherTotalAmount').numberbox('setValue',result.otherTotalAmount);
					$('#mallDeductAmount').numberbox('setValue',result.mallDeductAmount);
					$('#actualRate').numberbox('setValue',result.actualRate);
//					同时 要 update bill_shop_balance 相关字段设置
					
				});
				
	    	    var params = {shopNo:shopNo,month:month,startDate:startDate,endDate:endDate,balanceNo:balanceNo};//,status:1
	    		var url = billShopBalance.balanceDiffPath + '/list.json';
	    	    $( '#balanceDiffDataGrid').datagrid( 'options').queryParams= params;
	    	    $( '#balanceDiffDataGrid').datagrid( 'options').url=url;
	    	    $( '#balanceDiffDataGrid').datagrid( 'load' );	    	    
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
	}
};

shopBalanceDiff.check = function(rowIndex,rowData){
	/**
	if($("#status").val() == '2') {
		showWarn("单据已审核，不能进行此操作！");
		return true;
	}
	if($("#status").val() == '4') {
		showWarn("单据已开票，不能进行此操作！");
		return true;
	}
	*/
	/**
	var url_check = shopBalanceDiff.shopBalancemodulePath + '/checkIsExisting';
	var noteInfo = {
	    	result : 'success',
	    	warnInfo : '已经生成下期结算单不可修改'
	    };
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
    
	var params = {
			shopNo:shopNo,month:month,
		};
	var retResult = shopBalanceDiff.commonCheckAjaxRequestAsync(url_check,params,noteInfo);

	if(retResult = true){
	var getStatusValue=rowData.status;
	if(getStatusValue == 1){
		showWarn("已完成,不能修改");
		return getStatusValue == 1;
	}
	return getStatusValue == 1;
	}
	**/
};

//修改
//shopBalanceDiff.edit = function(rowIndex) {
//	if(shopBalanceDiff.endEdit()){
//		$('#balanceDeductDataGrid').datagrid('beginEdit',rowIndex);
//		balanceDeduct.editRowIndex = rowIndex;
//	}
//};

//修改
shopBalanceDiff.edit = function(rowIndex) {
//	shopBalanceDiff.edit.call(this, rowIndex);
//	var edmAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:rowIndex,field:'mallNumber'});
//	$(edmAmount.target).bind('blur',function(){
//		shopBalanceDiff.setAmount();
//	});
	var url_check = shopBalanceDiff.shopBalancemodulePath + '/checkIsExisting';
	var noteInfo = {
	    	result : 'success',
	    	warnInfo : '已经生成下期结算单不可修改'
	    };
	var shopNo = $('#shopNo').val();
	var month = $('#month').val();
    
	var params = {
			shopNo:shopNo,month:month,
		};
	var retResult = shopBalanceDiff.commonCheckAjaxRequestAsync(url_check,params,noteInfo);

	if(retResult == true){
		shopBalanceDiff.beginEdit(rowIndex);
	}
//	if(shopBalanceDiff.endEdit()){
//		$('#balanceDiffDataGrid').datagrid('beginEdit',rowIndex);
//		shopBalanceDiff.editRowIndex = rowIndex;
//	}
};

shopBalanceDiff.beginEdit= function(editIndex){// 开始明细行编辑
	if(shopBalanceDiff.endEdit()) {
		$('#balanceDiffDataGrid').datagrid('beginEdit',editIndex);
		var edmAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'mallNumber'});//商场报数
		$(edmAmount.target).bind('keyup',function(){
			shopBalanceDiff.setAmount();
			
		});
		
//		var eddAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'salesDiffamount'});//销售差异
//		$(eddAmount.target).bind('blur',function(){
//			shopBalanceDiff.setAmount();
//		});
		
//		var edbAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'balanceAmount'});//本次结算金额
//		$(edbAmount.target).bind('blur',function(){
//			shopBalanceDiff.setAmount();
//		});
		
		var eddiffAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'diffAmount'});//差异金额
		$(eddiffAmount.target).bind('keyup',function(){
			shopBalanceDiff.setAmountT();
		});
		
		var eddbAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'diffBalance'});//差异余额
		$(eddbAmount.target).bind('keyup',function(){
			
		});

		
		var edchangeAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'changeAmount'});//调整金额
		$(edchangeAmount.target).bind('keyup',function(){
			shopBalanceDiff.setAmountT();
		});
		
		var eddiffBackAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'diffBackAmount'});//差异回款
		$(eddiffBackAmount.target).bind('keyup',function(){
			shopBalanceDiff.setAmountT();
		});
		
//		var eddeductDiffAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'deductDiffAmount'});//扣费差异
//		$(eddeductDiffAmount.target).bind('blur',function(){
//			shopBalanceDiff.setAmount();
//		});
		
//		var discount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editIndex,field:'discount'});//扣率
//		$(discount.target).bind('blur',function(){
//			shopBalanceDiff.setAmount();
//		});
		
		
//		if(shopBalanceDiff.endEdit()){
			shopBalanceDiff.editRowIndex = editIndex;
//		}
	}
};

shopBalanceDiff.setAmount = function(){// 设置金额
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editRowIndex = -1;
	if(editTr.length > 0){
		editRowIndex = editTr.attr("datagrid-row-index");
		var edmAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'mallNumber'});//商场报数
		var edsAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'salesAmount'});
		var eddAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'salesDiffamount'});//销售差异
//		var edbAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'balanceAmount'});//本次结算金额
		var eddiffAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'diffAmount'});//差异金额
		var eddbAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'diffBalance'});//差异余额
		var eddiffBackAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'diffBackAmount'});//差异回款
		var edchangeAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'changeAmount'});//调整金额
		var eddeductDiffAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'deductDiffAmount'});//扣费差异
		var discount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'discount'});//扣率
			
//		var damount = $(eddAmount.target).numberbox('getValue');
//		var bamount = $(eddbAmount.target).numberbox('getValue');
		
		var deductDiffamount = $(eddeductDiffAmount.target).numberbox('getValue');
//		var discountN=$('#discountN').val();
		var discount=$(discount.target).numberbox('getValue');
		
		var mamount = edmAmount.target.val();	
		var samount = edsAmount.target.val();
		if(''!=mamount && ''!=samount ){
			eddAmount.target.val((parseFloat(samount)-parseFloat(mamount)).toFixed(2));
		}	
			
//			差异金额 = (商场报数-系统收入)*扣率	
		if(discount == 0)
			{
			    $(eddiffAmount.target).numberbox('setValue',((parseFloat(mamount)-parseFloat(samount))).toFixed(2));
			}else{
				$(eddiffAmount.target).numberbox('setValue',((parseFloat(mamount)-parseFloat(samount))*discount/100).toFixed(2));
			}
			
//			var backamount = $(eddiffBackAmount.target).numberbox('getValue');// 回款金额
//			var changamount = $(edchangeAmount.target).numberbox('getValue');// 调整金额
//			var diff_amount = $(eddiffAmount.target).numberbox('getValue');// 差异金额
//			//差异余额 = 差异金额+差异回款+调整金额    V
//			$(eddbAmount.target).numberbox('setValue',(parseFloat(diff_amount)+parseFloat(backamount)+parseFloat(changamount)).toFixed(2));
			return ;
//			edbAmount.target.val(parseFloat(mamount));
//		}
		//差异余额 = 差异金额-扣费差异+调整金额    X

		
/*//		var diffamount = $(eddiffAmount.target).numberbox('getValue');
		var diffamount =eddiffAmount.target.val();
		var diffAmountF=$(eddiffAmount.target).numberbox('getValue');
		if(diffAmountF != diffamount){
			eddiffAmount.target.val(diffamount);
			eddbAmount.target.val(diffamount);
	    }

		if(''!=diffAmountF){// && ''!=backamount && ''!=changamount
//			alert("diffamount"+parseFloat(diffamount));
//			alert("backamount"+parseFloat(backamount));
//			alert("changamount"+parseFloat(changamount));
			eddbAmount.target.val((parseFloat(diffamount)+parseFloat(backamount)+parseFloat(changamount)).toFixed(2));
		}
		var eddbAmountF =eddbAmount.target.val();
		var eddbAmountV=$(eddbAmount.target).numberbox('getValue');
		if(eddbAmountF != eddbAmountV){
			eddbAmount.target.val(eddbAmountF);
		}*/
	} 
};

shopBalanceDiff.setAmountT = function(){// 设置金额
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editRowIndex = -1;
	if(editTr.length > 0){
		editRowIndex = editTr.attr("datagrid-row-index");
		var edmAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'mallNumber'});//商场报数
		var edsAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'salesAmount'});
		var eddAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'salesDiffamount'});//销售差异
//		var edbAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'balanceAmount'});//本次结算金额
		var eddiffAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'diffAmount'});//差异金额
		var eddbAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'diffBalance'});//差异余额
		var eddiffBackAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'diffBackAmount'});//差异回款
		var edchangeAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'changeAmount'});//调整金额
		var eddeductDiffAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'deductDiffAmount'});//扣费差异
		var discount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:editRowIndex,field:'discount'});//扣率
		
		var mamount = edmAmount.target.val();		
//		var damount = $(eddAmount.target).numberbox('getValue');
//		var bamount = $(eddbAmount.target).numberbox('getValue');
		
		var deductDiffamount = eddeductDiffAmount.target.val();
//		var discountN=$('#discountN').val();
		var discount=discount.target.val();
		
		var samount = edsAmount.target.val();
		if(''!=mamount && ''!=samount ){
			eddAmount.target.val((parseFloat(samount)-parseFloat(mamount)).toFixed(2));
		}	
			
//			差异金额 = (商场报数-系统收入)*扣率	
//			$(eddiffAmount.target).numberbox('setValue',((parseFloat(mamount)-parseFloat(samount))*discount/100).toFixed(2));
			
			var backamount = eddiffBackAmount.target.val();// 回款金额
			var changamount = edchangeAmount.target.val();// 调整金额
			var diff_amount = eddiffAmount.target.val();// 差异金额
			//差异余额 = 差异金额+差异回款+调整金额    V
			$(eddbAmount.target).numberbox('setValue',(parseFloat(diff_amount)+parseFloat(backamount)+parseFloat(changamount)).toFixed(2));
//			edbAmount.target.val(parseFloat(mamount));
//		}
		//差异余额 = 差异金额-扣费差异+调整金额    X

		
/*//		var diffamount = $(eddiffAmount.target).numberbox('getValue');
		var diffamount =eddiffAmount.target.val();
		var diffAmountF=$(eddiffAmount.target).numberbox('getValue');
		if(diffAmountF != diffamount){
			eddiffAmount.target.val(diffamount);
			eddbAmount.target.val(diffamount);
	    }

		if(''!=diffAmountF){// && ''!=backamount && ''!=changamount
//			alert("diffamount"+parseFloat(diffamount));
//			alert("backamount"+parseFloat(backamount));
//			alert("changamount"+parseFloat(changamount));
			eddbAmount.target.val((parseFloat(diffamount)+parseFloat(backamount)+parseFloat(changamount)).toFixed(2));
		}
		var eddbAmountF =eddbAmount.target.val();
		var eddbAmountV=$(eddbAmount.target).numberbox('getValue');
		if(eddbAmountF != eddbAmountV){
			eddbAmount.target.val(eddbAmountF);
		}*/
	} 
};

//新增明细
shopBalanceDiff.addBalanceDiff = function(id, editRowIndex) {
	if (shopBalanceDiff.endEdit(id, editRowIndex)) {
		var billNo = $('#billNo').val();
		var balanceNo = $('#balanceNo').val();
		$('#' + id).datagrid('appendRow', {billNo:billNo,balanceNo:balanceNo});
		var length = $('#' + id).datagrid('getRows').length;
		$('#' + id).datagrid('beginEdit', length - 1);
		shopBalanceDiff[editRowIndex] = length - 1;
	}
};

// 编辑明细
shopBalanceDiff.editBalanceDiff = function(rowIndex, id, editRowIndex) {
	if (shopBalanceDiff.endEdit(id, editRowIndex)) {
		$('#' + id).datagrid('beginEdit', rowIndex);
		shopBalanceDiff[editRowIndex] = rowIndex;
	}
};

// 删除明细
shopBalanceDiff.delBalanceDiff = function(id, editRowIndex) {
	var checkedRows = $('#' + id).datagrid('getChecked');
	var rowIndex;
	$.each(checkedRows, function(index, row) {
		rowIndex = $('#' + id).datagrid('getRowIndex', row);
		if(row.status == '1'){
     		 showWarn("已启用，不能删除！");
     		 return;
     	    }
		$('#' + id).datagrid('deleteRow', rowIndex);
		if (shopBalanceDiff[editRowIndex] == rowIndex) {
			shopBalanceDiff[editRowIndex] = -1;
		}
	});
};

//结束明细行编辑
shopBalanceDiff.endEdit = function(id, editRowIndex) {
	if ($('#' + id).datagrid('validateRow', shopBalanceDiff[editRowIndex])) {
		$('#' + id).datagrid('endEdit', shopBalanceDiff[editRowIndex]);
		return true;
	}
	return false;
};

shopBalanceDiff.saveBalanceDiff  = function(id, editRowIndex){// 保存明细 
	if (shopBalanceDiff.endEdit(id, editRowIndex)) {
	var insertedData = $('#balanceDiffDataGrid').datagrid('getChanges','inserted');
	var updatedData = $('#balanceDiffDataGrid').datagrid('getChanges','updated');
	var deletedData = $('#balanceDiffDataGrid').datagrid('getChanges','deleted');
	var billNo = $('#billNo').val();
	$.each(insertedData, function(index, row) {
		row.billNo = billNo;
	});
	var data = {
		inserted : JSON.stringify(insertedData),
		updated : JSON.stringify(updatedData),
		deleted : JSON.stringify(deletedData),
	};
//	alert(JSON.stringify(changeRows));
//	alert(JSON.stringify(data)); 
	ajaxRequestAsync(shopBalanceDiff.modulePath + '/save',data,function(){
		if(data){
		  	$('#balanceDiffDataGrid').datagrid('acceptChanges');
			showSuc('保存成功');
		}else{
			showError('保存失败');
		}
	});
	};
};

//导入
shopBalanceDiff.importExcel = function() {

};

// 导出
shopBalanceDiff.exportExcel = function() {
	
};

//结束编辑
shopBalanceDiff.endEdit = function() {
	if($('#balanceDiffDataGrid').datagrid('validateRow',shopBalanceDiff.editRowIndex)){
		  $('#balanceDiffDataGrid').datagrid('endEdit',shopBalanceDiff.editRowIndex);
		  return true;
	}
	return false;
};


// 选择结算主体
shopBalanceDiff.selectCompany = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择结算主体',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/company/toSearchCompany',
    		fn : function(data, rowIndex) {
    			shopBalanceDiff.selectorCallback(data,'companyNo','name','companyNo','companyName');
    		}
    	});
    }
};

// 选择供应商
shopBalanceDiff.selectSupplier = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择供应商',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/supplier/toSearchSupplier',
    		fn : function(data, rowIndex) {
    			shopBalanceDiff.selectorCallback(data,'supplierNo','fullName','supplierNo','supplierName');
    		}
    	});
    }
};

//选择内部客户
shopBalanceDiff.selectInsideStore = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择内部客户',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/company/toSearchCompany',
    		fn : function(data, rowIndex) {
    			shopBalanceDiff.selectorCallback(data,'companyNo','name','insideStoreNo','insideStoreName');
    		}
    	});
    }
};


shopBalanceDiff.selectBsgroups  = {// 明细行选择商业集团
	    clickFn:function(){
	    	dgSelector({
	    		title:"选择商业集团",
	    		width : 580,
	    		height : 450,
	    		href : BasePath + "/common_util/toSearchBsgroups",
	    		fn : function(data, rowIndex) {
	    			shopBalanceDiff.selectorCallback(data,'bsgroupsNo','name','bsgroupsNo','bsgroupsName');
	    		}
	    	});
	    }
	};

shopBalanceDiff.selectMall = {// 明细行选择选择商场
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择商场",
				href : BasePath + "/common_util/toSearchMall",
	    		fn : function(data, rowIndex) {
	    			shopBalanceDiff.selectorCallback(data,'mallNo','name','mallNo','mallName');
	    		}
	    	});
	    }
};

shopBalanceDiff.selectShop = {// 明细行选择选择门店
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择门店",
				href : BasePath + "/common_util/toSearchShop",
	    		fn : function(data, rowIndex) {
	    			shopBalanceDiff.selectorCallback(data,'shopNo','shortName','shopNo','shortName');
	    		}
	    	});
	    }
};

shopBalanceDiff.selectDiffType = {// 明细行选择差异设置 
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择差异设置 ",
				href : BasePath + "/common_util/toSearchDiffType?companyNo="+$('#companyNo').val(),
	    		fn : function(data, rowIndex) {
	    			shopBalanceDiff.selectorCallback(data,'code','name','diffTypeCode','diffTypeName');
	    		}
	    	});
	    }
};

shopBalanceDiff.getRowNo = function(field){
	var noEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : field
	});
	return noEd.target.val();
};

shopBalanceDiff.selectDiffBack = {// 明细行选择本期差异回款
		validatebox:{
	    	options:{
	    		required:false
	    	}
	    },
	   
	    clickFn:function(){
	    	var params = 'dtlId='+shopBalanceDiff.getRowNo('id');
	     	params += '&billNo='+shopBalanceDiff.getRowNo('billNo');
	     	params += '&balanceNo='+shopBalanceDiff.getRowNo('balanceNo');
	    	ygDialog({
				title : '差异回款设置',
				href : BasePath + "/common_util/toSearchDiffBack?multiSelect=true&"+params,
				width :  650,
				height : 'auto',
				isFrame : true,
				modal : true,
				showMask : true,
				buttons: [{
					id:'sure',
		            text: '确认',
		            handler: function(dialog) {
		            	var checkedRows = shopBalanceDiff.getCheckedRows();
		            	if(typeof checkedRows != 'undefined'){

		    				var balanceStartDate = $('#balanceStartDate').val();
		    				var balanceEndDate = $('#balanceEndDate').val();
		    				var balanceNo = $('#balanceNo').val();
		    				var id =shopBalanceDiff.getRowNo('id');
		    				var url = shopBalanceDiff.modulePath + '/getDiffBackAmountSum';
		    				var shopBalanceObj ="";
		    				shopBalanceObj=balanceNo+";"+balanceStartDate+";"+balanceEndDate+";"+id+";";
		    				ajaxRequestAsync(url,{"balanceObj" : shopBalanceObj},function(result){
		    					$('#diffBackAmount').numberbox('setValue',result.diffBackAmountSum);//页面赋值显示
//		            		var amount = 0;
//							$.each(checkedRows,function(index,item){
//								amount += parseFloat(item.backAmount);
//							});
		    				var amount=result.diffBackAmountSum;	
							var valueEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
								'index' : shopBalanceDiff.editRowIndex,
								'field' : 'diffBackAmount'
							});
							valueEd.target.val(result);
							
							$(valueEd.target).bind('blur',function(){
								shopBalanceDiff.setAmountT();
							});
							
							var eddiffAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:shopBalanceDiff.editRowIndex,field:'diffAmount'});//差异金额
							var eddbAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:shopBalanceDiff.editRowIndex,field:'diffBalance'});//差异余额
							var eddiffBackAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:shopBalanceDiff.editRowIndex,field:'diffBackAmount'});//差异回款
							var edchangeAmount = $("#balanceDiffDataGrid").datagrid('getEditor',{index:shopBalanceDiff.editRowIndex,field:'changeAmount'});//调整金额
							
							var backamount = eddiffBackAmount.target.val();// 回款金额
							var changamount = edchangeAmount.target.val();// 调整金额
							var diff_amount = eddiffAmount.target.val();// 差异金额
							
							$(eddbAmount.target).numberbox('setValue',(parseFloat(diff_amount)+parseFloat(backamount)+parseFloat(changamount)).toFixed(2));
							
							});	
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
		        	shopBalanceDiff.getCheckedRows = content.getCheckedRows;
		        }
			});
//	    	ygSelector({
//	    		width : 580,
//	    		height : 450,
//	    		title:"差异回款设置 ",
//				href : BasePath + "/common_util/toSearchDiffBack?billNo="+shopBalanceDiff.getRowNo('billNo')+"&balanceNo="+shopBalanceDiff.getRowNo('balanceNo')+"&dtlId="+shopBalanceDiff.getRowNo('id'),
//	    		fn : function(data, rowIndex) {
//	    			shopBalanceDiff.selectDiffBackAmount(data,'backAmount','diffBackAmount');
//	    		}
//	    	});
	    }
};

//选择后回调 
shopBalanceDiff.selectDiffBackAmount = function(data,value,valueField){
	var valueEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : valueField
	});
	valueEd.target.val(data[value]);
};

shopBalanceDiff.selectPro = {// 明细行选择差异设置 
		validatebox:{
	    	options:{
	    		required:false
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择促销活动 ",
				href : BasePath + "/common_util/toSearchPro?shopNo="+$('#shopNo').val()+"&balanceNo="+$('#balanceNo').val(),
	    		fn : function(data, rowIndex) {
	    			shopBalanceDiff.selectorProCallback(data,'proNo','proName','proStartDate','proEndDate','discount','saleAmount','proNo','proName','proStartDate','proEndDate','discount','salesAmount');
	    		}
	    	});
	    }
};

shopBalanceDiff.selectorProCallback = function(data,proNo,proName,proStartDate,proEndDate,discount,saleAmount,proNoF,proNameF,proStartDateF,proEndDateF,discountF,salesAmountF){
	var proNoEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : proNoF
	});
	var proNameEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : proNameF
	});
	var proStartDateEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : proStartDateF
	});
	var proEndDateEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : proEndDateF
	});
	var discountEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : discountF
	});
	var salesAmountEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : salesAmountF
	});
	proNoEd.target.val(data[proNo]);
	proNameEd.target.val(data[proName]);
	proStartDateEd.target.val(data[proStartDate]);
	proEndDateEd.target.val(data[proEndDate]);
	discountEd.target.val(data[discount]);
	salesAmountEd.target.val(data[saleAmount]);
};

// 选择后回调 
shopBalanceDiff.selectorCallback = function(data,value,text,valueField,textField){
	var valueEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : valueField
	});
	var textEd = $('#balanceDiffDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDiff.editRowIndex,
		'field' : textField
	});
	valueEd.target.val(data[value]);
	textEd.target.val(data[text]);
};

shopBalanceDiff.GetDate =function(){
	   var date=new Date();
	   var year=date.getYear();
	   var month=date.getMonth();
	   var str='';
	   str+='<table id="date">';
	   str+='<tr>';
	   str+='<td><a href="javascript:void(0)" onclick="DelYear()">< <</a></td>';
	   str+='<td colspan="2"><span id="year">'+year+'</span>年</td>';
	   str+='<td><a href="javascript:void(0)" onclick="AddYear()">> ></a></td>';
	   str+='</tr>';
	   for(i=0;i<3;i++){
	      str+='<tr>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(1+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(2+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(3+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(4+i*4)+'月</td>';
	      str+='</tr>';
	   }
	   str+='</table>';
	   str=str.replace('<td onclick="ChangeMonth(this)" class="td">','<td onclick="ChangeMonth(this)" class="td" style="color:red">');
	   document.write(str);
	}
	function AddYear(){
	   var year=document.getElementById("year").innerHTML;
	   year=Number(year)+1;
	   document.getElementById("year").innerHTML=year;
	}
	function DelYear(){
	   var year=document.getElementById("year").innerHTML;
	   year=Number(year)-1;
	   document.getElementById("year").innerHTML=year;
	}
	function ChangeMonth(obj){
	   var trs=document.getElementById("date").getElementsByTagName("tr");
	   for(i=1;i<trs.length;i++){
	      var tds=trs[i].getElementsByTagName("td");
	      for(j=0;j<tds.length;j++){
	          tds[j].style.color="";
	      }
	   }
	   obj.style.color="red";
	}
 
	
	function timeFormatter(date){
        return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
    }
    function timeParser(date){
        return new Date(Date.parse(date.replace(/-/g,"/")));
    }
    
    
// 初始化
$(function(){
//	$(".test").simpleCanleder(); 
//	    $('#dd').datebox({	 
//	    	
//	    	formatter:function(date){
//      			var y = date.getFullYear();
//				var m = date.getMonth()+1;
//				alert( y+'-'+m);
//				return y+'-'+m;
//      		},		
//     	 editor:{
//      			type:'datebox',
//      			options:{
//      				required:true
//      					}
//      			}
//	    })
//	        //formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1); },
//	       // parser: function(date){ return new Date(Date.parse(date.replace(/-/g,"/"))); }
////	        }); 
});


/**
 * 是否已结算状态
 */
shopBalanceDiff.balanceFlagformatter = function(value) {
	for ( var i = 0; i < balanceFlag_status.length; i++) {
		if (balanceFlag_status[i].value == value) {
			return balanceFlag_status[i].text;
		}
	}
};


/**
 * 是否已结算状态
 */
var balanceFlag_status = [ {
	"value" : "0",
	"text" : "未完成"
}, {
	"value" : "1",
	"text" : "已完成"
} ];