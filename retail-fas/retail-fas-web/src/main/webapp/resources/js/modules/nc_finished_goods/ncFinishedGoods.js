
function ncFinishedGoodsDialog() { 
	var $this = this;
	
	this.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/nc_finished_goods/do_import',
			'templateName' : 'NC库存商品导入.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (data.error) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showSuc('数据导入成功');
						refresh();
					}
				} else {
					showInfo('导入失败,请联系管理员!');
				}
			},
			error : function() {
				$.messager.progress('close');
				showWarn('数据导入失败，请联系管理员');
			}
		});
	};
	
	function refresh(){
		var reqParam = $('#searchForm').form('getData');
		var url = "../nc_finished_goods/list.json";
		$('#ncFinishedGoodsDataGrid').datagrid('options').queryParams = reqParam;
		$('#ncFinishedGoodsDataGrid').datagrid('options').url =url;
		$('#ncFinishedGoodsDataGrid').datagrid('load');
	}
	
	this.deleteDate = function(){
		var data = $('#ncFinishedGoodsDataGrid').datagrid('getChecked');
		if(data.length<1){
			showInfo("请先选择要删除的记录！");
			return;
		}else{
			$.messager.confirm('确认','你确认要删除选中的这'+data.length+'条记录吗?',function(r){
			    if (r){
			    	var ids = "";
					$.each(data,function(i,item){
			    		try{
			    			if(item){
			        			if(item.id){
			            			ids += item.id+",";
			            		}
			        		}
			    		}catch(e){}
			    	});
					
					$.ajax({
						url : '../nc_finished_goods/deleteData',
						data : {
							ids : ids
						},
						type : 'get',
						async : false,
						success : function(data) {
							if (data.result){
								refresh();
							}
						}
					});
			    }
			});
		}
	};
}

function ncFinishedGoodsEditor() {
	var $this = this;
	
	this.checkUpdate = function(options, rowIndex, rowData) {
		return true;
	};
}

var ncFinishedGoods = null, editor = null;
var BizTypeData=new Object();
$(function() {
	$.fas.extend(ncFinishedGoodsDialog, FasDialogController);
	$.fas.extend(ncFinishedGoodsEditor, FasEditorController);
	ncFinishedGoods = new ncFinishedGoodsDialog();
	ncFinishedEditor = new ncFinishedGoodsEditor();
	ncFinishedGoods.init("/nc_finished_goods", {
		dataGridId : "ncFinishedGoodsDataGrid",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		enableUrl : "/do_enable",
		unableUrl : "/do_unable"
	});
});

//方向（1：借，2：贷）
function rowDirFormatter(value, rowData, rowIndex) {
	return value == 1 ? "借" : "贷";
};