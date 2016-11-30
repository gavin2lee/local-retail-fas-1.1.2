var customImperfect = new Object();

var currentUser;

customImperfect.modulePath = BasePath + '/custom_imperfect';

//查询
customImperfect.search = function() {
	var params = $('#searchForm').form('getData');
	var url = customImperfect.modulePath + '/list.json';
	$('#dtlDataGrid').datagrid('options').queryParams= params;
	$('#dtlDataGrid').datagrid('options').url= url;
	$('#dtlDataGrid').datagrid('load');
};

//清空
customImperfect.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
};

customImperfect.setAllAmount = function(){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
    	var edPrice =$('#dtlDataGrid').datagrid('getEditor',{index:editRowIndex, field:'purchasePrice'});
    	var edQty =$('#dtlDataGrid').datagrid('getEditor',{index:editRowIndex, field:'qty'});
    	var edAmount =$('#dtlDataGrid').datagrid('getEditor',{index:editRowIndex, field:'amount'});
    	var price = edPrice.target.val();
    	var qty = $(edQty.target).numberbox('getValue');
    	if(qty && qty!='' && price && price!=''){
    		edAmount.target.val(qty * price);
    	}
	}	
}
//新增
customImperfect.add = function() {
	if(customImperfect.endEdit()){
	    $('#dtlDataGrid').datagrid('insertRow',{index: 0,row: {returnDate:new Date().format("yyyy-MM-dd")}});
		$('#dtlDataGrid').datagrid('beginEdit',0);
		var edQty = $("#dtlDataGrid").datagrid('getEditor',{index:0,field:'qty'});
		$(edQty.target).bind('blur',function(){
			customImperfect.setAllAmount();
		});
	}
};

//修改
customImperfect.edit = function(rowIndex, rowData) {
	if(rowData.balanceNo){
		showInfo('不能操作已结算的客残记录!');
		return ;
	}
	if(customImperfect.endEdit()){
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
		var edQty = $("#dtlDataGrid").datagrid('getEditor',{index:rowIndex,field:'qty'});
		$(edQty.target).bind('blur',function(){
			customImperfect.setAllAmount();
		});
	}
};

//删除
customImperfect.del = function() {
  var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
  var rowIndex;
  var errMessage = '';
  if(checkedRows.length > 0){
	  $.each(checkedRows,function(index,row){
			if(row.balanceNo){
				errMessage = '不能操作已结算的客残明细!';
				return false;
			}  
		  });
		  if(errMessage!=''){
			 showInfo(errMessage); 
			 return ;
		  }
		  $.each(checkedRows,function(index,row){
		  	rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
		  	$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
		  });
  }else{
	  showInfo('请选中需要删除的记录!');  
  }

};


//结束编辑
customImperfect.endEdit = function() {
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
customImperfect.save = function(){
	if(customImperfect.endEdit()){
	    var url = customImperfect.modulePath + '/save';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var balanceType = $('#balanceType').val();
	    $.each(insertRows,function(index, item){
	    	item.balanceType = 1;
	    	item.balanceStatus = 0;
	    	item.createUser = currentUser.username;
	    	item.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	    });
	    $.each(updatedRows,function(index, item){
	    	item.updateUser = currentUser.username;
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
	    		customImperfect.search(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
	}
};


function importCallBack(data){
	if(data.indexOf('"success":true')!=-1){
		var jsonData = JSON.parse(data);
		if(jsonData.success){
			var lstData = jsonData.rows;
			if(lstData.length > 0){
				$.each(lstData,function(index, item){
					if(item.pass == 1){
						$('#dtlDataGrid').datagrid('insertRow',{
							index : 0 ,
							row : item.validateObj
						});
					}
				});
				$('#importDataGrid').datagrid({
					data: lstData,
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
				customImperfect.search(); 
				showSuc("操作成功!");
				return ;
			}
		}
	}
	showError("数据不合法,导入失败!");
}

//商品信息回调
customImperfect.itemCallBack = function(row){
	var editTr = $("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0 && row){
		var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
    	var edNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemNo'});
    	var edCode =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemCode'});
    	var edName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'itemName'});
    	var edBrandNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'brandNo'});
    	var edBrandName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'brandName'});
    	var edCategoryNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'categoryNo'});
    	var edCategoryName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'categoryName'});
    	var edYearsNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'years'});
    	var edYearsName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'yearsName'});
    	var edSeasonNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'season'});
    	var edSeasonName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'seasonName'});
    	var edGenderNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'gender'});
    	var edGenderName =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'genderName'});
    	var edPrice =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'purchasePrice'});
    	var edAmount =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'amount'});
    	var edQty =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'qty'});
    	ajaxRequestAsync( BasePath + '/common_util/getItemExtends',{itemNo:row.id},function(data){
    		if(data.rows.length > 0){
    			var item = data.rows[0];
    	    	edNo.target.val(item.itemNo);
    	    	edCode.target.val(item.code);
    	    	edName.target.val(item.name);
    	    	edBrandNo.target.val(item.brandNo);
    	    	edBrandName.target.val(item.brandName);
    	    	edCategoryNo.target.val(item.categoryNo);
    	    	edCategoryName.target.val(item.categoryName);
    	    	edYearsNo.target.val(item.years);
    	    	edYearsName.target.val(item.yearsName);
    	    	edSeasonNo.target.val(item.sellSeason);
    	    	edSeasonName.target.val(item.seasonName);
    	    	edGenderNo.target.val(item.gender);
    	    	edGenderName.target.val(item.genderName);
    	    	var edSupplierNo =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'salerNo'});
    	    	var edBillDate =$('#dtlDataGrid').datagrid('getEditor',{index:editIndex, field:'returnDate'});
    	    	var itemNo = row.id;
    	    	var supplierNo = edSupplierNo.target.val();
    	    	var billDate = $(edBillDate.target).datebox('getValue');
    	    	if(itemNo!=''){
        	    	ajaxRequestAsync(BasePath + '/common_util/getPurchasePriceByItemNo',
        	    			{itemNo:itemNo,supplierNo:supplierNo,billDate:billDate},function(result){
        	    		if(result.purchasePrice){
        	    			edPrice.target.val(result.purchasePrice);
        	    			var qty = $(edQty.target).numberbox('getValue');
        	    			if(qty && ''!=qty){
        	    				edAmount.target.val(result.purchasePrice * qty);
        	    			}
        	    		}
        	    	});
    	    	}
    		}
    	});
	}
}
// 初始化
$(function(){
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
});
