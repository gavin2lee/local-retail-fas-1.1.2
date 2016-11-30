function BillDtlDialog() {
	var $this = this;
	this.unfrozenStatusData = [{"value":"9","text":"冻结"},{"value":"11","text":"解冻"}];
};

var dialog = null;

function extendClear() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
};

function search() {
	$('#dataGridDiv').datagrid('acceptChanges');
	var params = $('#searchForm').form('getData');
	var url = BasePath + '/unfrozen_amount/enter_list.json?isHq=' +  $('#isHq').val();
    $('#dataGridDiv').datagrid('options').queryParams= params;
    $('#dataGridDiv').datagrid('options').url= url;
    $('#dataGridDiv').datagrid('load');
};


function unfrozenAmount() {
	var updatedRows = $('#dataGridDiv').datagrid('getChecked');
	var errMessage = '';
	if (updatedRows.length > 0) {
		$.each(updatedRows, function(index, row) {
			if (row.status == 11) {
				errMessage = '不能操作已解冻的退货单!';
				return false;
			}
		});
		if (errMessage != '') {
			showInfo(errMessage);
			return;
		}
		$.messager.confirm("确认","你确定要解冻吗？",function(r) {
			if (r) {
				var changeRows = {
						updated : JSON.stringify(updatedRows)
					};
				var url = BasePath + '/unfrozen_amount/unfrozen?isHq=' +  $('#isHq').val() ;
				ajaxRequestAsync(url, changeRows, function(result) {
					if (result) {
						showSuc('解冻成功');
					} else {
						showSuc('解冻失败');
					}
					search();
				});
			}
		});
		
	} else {
		showInfo('请选中需要分配的记录!');
	}	
};


function importCallBack(result) {
	var resultJson = JSON.parse(result);
	if(resultJson.success){
		$('#importDataGrid').datagrid({
			data: resultJson.data,
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
		showSuc("操作成功!");
		search();
	}else{
		showError("操作失败,请检查数据有效性!");
	}
	
};

$(function() {
	var isHq = $('#isHq').val();
	$.fas.extend(BillDtlDialog, FasDialogController);
	dialog = new BillDtlDialog();
	dialog.init("/unfrozen_amount", {
		dataGridId : "dataGridDiv",
		searchFormId : "searchForm",
		searchUrl : "/enter_list.json?isHq=" + isHq,
		exportUrl : "/export_sale_dtl?isHq=" + isHq,
		exportUrl : "/do_import?isHq=" + isHq,
		exportTitle : "地区批发销售明细"
	});
	$("#unfrozenStatus").initCombox({
		data:dialog.unfrozenStatusData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 130,
		editable:true,
		required:false
	});
});