function ShopBalanceOperateDialog() {
	// 转换状态值
	this.formatOperate = function(value) {
		if(value == '1') {
			return "新增";
		}
		if(value == '2') {
			return "修改";
		}
		if(value == '3') {
			return "删除";
		}
	};
};

var dialog = null;
$(function() {
	$.fas.extend(ShopBalanceOperateDialog, FasDialogController);
	dialog = new ShopBalanceOperateDialog();   
	dialog.init("/bill_shop_balance_operatelog", {
		dataGridId : "operateLogDataGridDiv",
		searchFormId : "searchForm",
		searchUrl : "/list.json"
	});
});