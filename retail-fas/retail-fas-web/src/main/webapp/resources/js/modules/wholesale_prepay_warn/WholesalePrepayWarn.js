function WholesalePrepayWarn() {}

var _fasDialogController = new FasDialogController("/wholesale_prepay_warn", fas_common_setting);

WholesalePrepayWarn.prototype.super = _fasDialogController;

var _wholesalePrepayWarn = new WholesalePrepayWarn();

_wholesalePrepayWarn.clear = function(){
	$('#searchForm').form('clear');
	$('#searchForm').find('input').val('');
}