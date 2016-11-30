//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if ('0' == obj) {
			return true;
		}
		return false;
	}
	return true;
};

function SettlementBodyChangeRecordDialog() { 
	var $this = this;
	
	$this.doImport = function() {
		$.importExcel.open({
			'submitUrl' : BasePath + '/settlement_body_change_record/do_import',
			'templateName' : '存货所属(主体变更)买卖跟踪表导入模板.xlsx',
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if (isNotBlank(data.error)) {
						showError(data.error);
					} else {
						$.importExcel.colse();
						showInfo('数据导入成功');
						$this.search();
					}
				} else {
					showWarn('导入失败,请联系管理员!');
				}
			},
			error : function() {
				$.messager.progress('close');
				showWarn('数据导入失败，请联系管理员');
			}
		});
	};
}


var dialog1 = null;
$(function() {
	$.fas.extend(SettlementBodyChangeRecordDialog, FasDialogController);
	dialog1 = new SettlementBodyChangeRecordDialog();
	dialog1.init("/settlement_body_change_record", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle:'存货所属（主体变更）买卖跟踪导出',
		exportUrl:"/do_exports"
	});
	
	var StockTypeArray = 
		[
		 {'value' : '0' , 'text' : '正品'},
		 {'value' : '1' , 'text' : '次品'},
		 {'value' : '2' , 'text' : '原残'},
		 {'value' : '3' , 'text' : '客残'}
       ];
	$('#stockType').combobox({
		data : StockTypeArray,
		valueField : 'value',
		textField : 'text'
	});
});
