var selectExtendCategory = {};

//开始编辑
selectExtendCategory.beginEdit = function(rowIndex) {
	if(selectExtendCategory.endEdit()){
		$('#extendCategoryDataGrid').datagrid('beginEdit',rowIndex);
	}
};

//结束编辑
selectExtendCategory.endEdit = function() {
	var editTr = $('#extendCategoryDiv').find("tr[class*=datagrid-row-editing]");
	if(editTr.length > 0){
		var editRowIndex = editTr.attr("datagrid-row-index");
		if($('#extendCategoryDataGrid').datagrid('validateRow',editRowIndex)){
			  $('#extendCategoryDataGrid').datagrid('endEdit',editRowIndex);
			  return true;
		}
		return false;
	}
	return true;
};

selectExtendCategory.initEditor = function(){
	$("#extendCategoryDataGrid").datagrid("addEditor", {field : "extendCategoryName", 
		editor : {type:'test_multi_combogrid',
			options:{
				type:'brand',
				datagridId:'extendCategoryDataGrid',
				valueField:'extendCategoryCode',
      			queryUrl:'',
      			callback:selectExtendCategory.callBack,
      			onShowPanelFunction:function(input){
      				var dg = input.combogrid('grid');
      				var queryParams = input.combo('options').queryParams;
      				var _queryUrl = '';
  					var type = '品牌';
  					var editIndex = $('#extendCategoryDiv').find("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
  					if(editIndex == 0){
  						_queryUrl = BasePath + '/common_util/getPageBrand';
  					}else if(editIndex == 1){
  						_queryUrl = BasePath + '/common_util/getPageCategory?levelid=2';
  					}else if(editIndex == 2){
  						_queryUrl = BasePath + '/common_util/getPageCategory?levelid=3';
  					}else if(editIndex == 3){
  						_queryUrl = BasePath + '/common_util/getLookup?lookupId=7'
  					}
      				dg.datagrid( 'options' ).url = _queryUrl;
      				dg.datagrid( 'options' ).queryParams = queryParams;
      				dg.datagrid( 'load' );
      			}
			}
		}
	});
}

//获取所有行
function getRows(){
	if(selectExtendCategory.endEdit()){
		return $('#extendCategoryDataGrid').datagrid('getRows');
	}
	return null;
}


//回调
selectExtendCategory.callBack = function(rows){
	if(rows.length >0){
		var code = '';
		var name = '';
		var editTr = $('#extendCategoryDiv').find("tr[class*=datagrid-row-editing]");
		if(editTr.length > 0){
			var editIndex = editTr.attr("datagrid-row-index");
			$.each(rows,function(index,item){
				code +=item.code+',';
				name += item.name+',';
			});
			var edCode =$('#extendCategoryDataGrid').datagrid('getEditor',{index:editIndex, field:'extendCategoryCode'});
			var edName =$('#extendCategoryDataGrid').datagrid('getEditor',{index:editIndex, field:'extendCategoryName'});
	    	edCode.target.val(code.substring(0,code.length-1));
	    	edName.target.val(name.substring(0,name.length-1));
		}
	}
}

$(function(){
	setTimeout(function(){
		selectExtendCategory.initEditor();
		$('#extendCategoryDataGrid').datagrid('insertRow',{index: 0,row: {extendCategoryType:'品牌',extendCategoryTypeCode:'brandNo'}});
		$('#extendCategoryDataGrid').datagrid('insertRow',{index: 1,row: {extendCategoryType:'二级大类',extendCategoryTypeCode:'twoLevelCategoryNo'}});
		$('#extendCategoryDataGrid').datagrid('insertRow',{index: 2,row: {extendCategoryType:'三级大类',extendCategoryTypeCode:'threeLevelCategoryNo'}});
		$('#extendCategoryDataGrid').datagrid('insertRow',{index: 3,row: {extendCategoryType:'性别',extendCategoryTypeCode:'gender'}});
	},300);
});