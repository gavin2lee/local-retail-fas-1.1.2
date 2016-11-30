var invoice_template_set = {};

var setting = {
	datagridId : "dtlDataGridDiv",
	primaryKey : "id",
	saveUrl : "/invoice_template_set_dtl/save",
	saveCallback : function(){
		
	},
	initRow : function(){
		return {invoiceTempNo : $("#invoiceTempNo").val()};
	},
	initData : {
		combobox : {
			
		}
	},
	checkInsertOrUpdate : function(rowIndex) {
		var rows = $("#dtlDataGridDiv").datagrid("getRows");
		var categoryNos = "";
		$.each(rows, function(index, item) {
			if(index == rows.length - 1) {
				categoryNos += item.categoryNo;
			} else {
				categoryNos += item.categoryNo + ",";
			}
		});
		$("#dtlDataGridDiv").datagrid("removeEditor", "categoryNo");
		$("#dtlDataGridDiv").datagrid("addEditor", {field : "categoryNo", 
			editor:{type:'searchbox',
				  options:{
					  id:'categoryNo',
					  	name:'categoryNo',
					  	textId:'categoryName',
					  	valueField:'categoryNo',
					  	textField:'name',
					  	title:'选择大类',
					  	width:50,
					  	isFrame:false,
					  	required:true,
					  	url:'/category/select?levelid=1&existCategoryNos='+categoryNos,
					  	queryUrl:'/category/filter_exist_category.json?levelid=1&existCategoryNos='+categoryNos
				  }
			}
		});
	}
};

//数据转换
invoice_template_set.formatter = {
	operate : function(rowIndex,row,value) {
		var invoiceTempNo = row.invoiceTempNo;
		var retVal = "<a href='javascript:void(0);' onclick='invoice_template_set.setInvoiceTemplateSetDtl(\""+invoiceTempNo+"\");'><font color='green'>编辑</font></a>";
		return retVal;
	}
};

var formatStatus =[{'value':'0', 'text':'否'}, {'value':'1', 'text':'是'}];
invoice_template_set.statusformatter = function(value, rowData, rowIndex) {
    for (var i = 0; i < formatStatus.length; i++) {
        if (formatStatus[i].value == value) {
            return formatStatus[i].text;
        }
    }
};
var extra_operate = {
	//检查新增的数据是否正确
//	checkSave : function() {
//		var check_data = {invoiceTempNo : $("#invoiceTempNo").val(), id : $("#settleCategoryId").val()};
//		var url = BasePath + "/invoice_template_set/check_exist";
//		var check_exist = fas_common_ajax.ajaxRequestAsync(url, check_data, function(flag){
//			if(flag) {
//				showError('分类编号不能重复，请重新输入!');
//				return false;
//			}
//			return true;
//		});
//		return check_exist;
//	},
	initAdd : function() {
		$("#invoiceTempNo").removeClass("readonly").removeAttr("readonly", true);
		$("#invoiceTempNoId").val('');
		fas_common.search({
			dataGridId : "dtlDataGridDiv",
			url : "/invoice_template_set_dtl/list.json?invoiceTempNo="
		});
	},
	loadedUpdate : function() {
		$("#invoiceTempNo").addClass("readonly").attr("readonly", true);	
		fas_common.search({
			dataGridId : "dtlDataGridDiv",
			url : "/invoice_template_set_dtl/list.json?invoiceTempNo="+$("#invoiceTempNo").val()
		});
		fas_common_editor.editIndex = undefined;
	},
	initSubmitParams : function() {
		var params = [];
		var effectRow = getChangeTableDataCommon("dtlDataGridDiv");
		var deleted = "";
		var deletedData = $("#dtlDataGridDiv").datagrid('getChanges','deleted');
		var deleteList = [];
    	$.each(deletedData, function(index, item){
    		var obj = new Object();
    		obj.id = item.id;
    		deleteList.push(obj);
    	});
    	if(deleteList.length > 0) {
            deleted = JSON.stringify(deleteList);
    	}
		params.push({name : 'inserted', value : effectRow.inserted});
		params.push({name : 'updated', value : effectRow.updated});
		params.push({name : 'deleted', value : deleted});
		return params;
	}
};

invoice_template_set.setInvoiceTemplateSetDtl = function(invoiceTempNo) {
	ygDialog({
		title : '关联明细',
		width : 700,
		height : 550,
		enableCloseButton: false,
		href : BasePath + "/invoice_template_set/invoice_template_set_dtl/" + invoiceTempNo,
		isFrame : true,// 是否框架中打开页面
		onLoad : function(win, content) {
			var _this = $(this);
			$("#btn-save-detail", _this.contents()).on("click", function() {
				content.fas_common_editor.save(win);
			});
			$("#btn-close", _this.contents()).on("click", function() {
				win.close();
			});
		}
	});
};

invoice_template_set.batchDelBill= function(){
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');
    if(checkedRows.length < 1){
        showWarn("请选择要删除的记录!");
        return;
    }
//	for(var i =0; i < checkedRows.length; i++){
//		var item = checkedRows[i];
//		if(item.status != "1"){
//			showWarn("单据："+item.backsectionNo+"，已审核确认不可删除！" );
//			return false;
//		 }
//	}
	$.messager.confirm("确认","你确定要删除选中的单据?",function(r) {
		if (r) {
			var url = BasePath + '/invoice_template_set/deleteInvoiceTemplateSet';
			var idList = "";
			var flag = false;
			$.each(checkedRows, function(index, row) {
				if(invoice_template_set.checkUpdate(row.invoiceTempNo)){
					flag = true;
				}
				idList+=row.id+','+row.invoiceTempNo+";";
			});
			if(flag){
				showError('店铺开票规则设置引用该记录，不能删除，请重新选择！');
				return;
			}
			var params = {idList : idList.substring(1, idList.length-1)}; //{id:idList};//
			ajaxRequestAsync(url,params,function(count){
				if(count) {
					showSuc('删除成功');
					invoice_template_set.search();
				} else {
					showError('删除失败');
				}
			});
		}
	});

};

invoice_template_set.search = function() {
	var fromObjStr = convertArray($('#searchForm').serializeArray());
	var queryMxURL = BasePath + '/invoice_template_set/list.json';
	$("#dataGridDiv").datagrid('options').queryParams = eval("(" + fromObjStr + ")");
	$("#dataGridDiv").datagrid('options').url = queryMxURL;
	$("#dataGridDiv").datagrid('load');
};

/**
 * 校验数据是否可以修改、删除
 */
invoice_template_set.checkUpdate = function(invoiceTempNo){
	var check_data = {'invoiceTempNo' : invoiceTempNo};
	var url = BasePath + "/invoice_template_set/check_update";
	var isUpdate = $.fas.ajaxRequestAsync(url, check_data, function(result){
		return result.useTempSetCount;
	});
	return isUpdate;
}

//复制
invoice_template_set.copyOneLine = function(){
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');
    
	if(checkedRows.length < 1){
		showWarn("请选择要复制的模板!");
		return;
	}
	
    $.each(checkedRows,function(index,row){
    	//add one more line , and padding the data
    	appendRowMethod(row);
    	appendRowDtlMethod(row);
    });
};

function appendRowMethod(row){
	$('#dataGridDiv').datagrid('appendRow',{
		companyNo : row.companyNo,
		companyName : row.companyName,
		name : row.name	
	});
};

function appendRowDtlMethod(row){
	$('#dtlDataGridDiv').datagrid('appendRow',{
		categoryNo : row.categoryNo,
		categoryName : row.categoryName,
		typeModel : row.typeModel,
		invoiceName:invoiceName,
		qtyControlFlag:qtyControlFlag,
		remark:remark
	});
};

//结算大类列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "invoiceTempNo=" + rowData.invoiceTempNo;
};