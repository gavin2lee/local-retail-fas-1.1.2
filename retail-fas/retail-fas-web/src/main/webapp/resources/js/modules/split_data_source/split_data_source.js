var split_data_source = {};

//数据转换
split_data_source.formatter = {
	billFlag : function(value) {
		if(value == "0") {
			return "应收";
		}
		if(value == "1") {
			return "应付";
		}
		return "";
	}
};

var buildSubgridUrl = function(rowData) {
	return "?billNo=" + rowData.billNo;
};