
var costExceptionBill = {};

costExceptionBill.exceptionFlag = [
                             {"value":"1","text":"单价为0或1"},
                             {"value":"2","text":"与地区价不一致"}
                      ];

costExceptionBill.exceptionFlagFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < costExceptionBill.exceptionFlag.length; i++) {
		if (costExceptionBill.exceptionFlag[i].value == value) {
			return costExceptionBill.exceptionFlag[i].text;
		}
	}
};

$(document).ready(function(){
});
