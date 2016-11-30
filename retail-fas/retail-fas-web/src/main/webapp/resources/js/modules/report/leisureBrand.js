var leisureBrand=new Object();

$(function() {
	setDate();
});

//设置时间
function setDate(){
	$("#sendDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
};

//查询
leisureBrand.search=function(){
	$('#searchForm').form('submit', {
		url : BasePath + "/leisure_brand_check/organ_columns.json",
		onSubmit : function(param) {
		},
		success : function(data) {
			var result = jQuery.parseJSON(data);
			var columns = new Array();
			$.each(result.headers, function(i, field) {
				var column = {};
				column["title"] = i;
				column["field"] = field;
				column["width"] = 80;
				column["align"] = 'right';
				column["halign"] = 'center';
				column["exportType"] = 'number';
				columns.push(column);
			});
			
			columns.push(
			  {field : 'SEND_QTY', title : '调出合计', width : 80,align:'center',halign:'center'},
			  {field : 'DIFFERENCE_QTY', title : '库存(丽天仓)', width : 100,align:'center',halign:'center',
				  styler: function(value,row,index){
					  return 'color:red;font-weight:bold;';
				  }
			  },
			  {field : 'RECEIVE_QTY', title : '厂入合计', width : 80,align:'center',halign:'center'},
              {field : 'SUPPLIER_NAME', title : '供应商', width : 200,align:'left',halign:'center'},
              {field : 'BUYER_NAME', title : '总部公司', width : 200,align:'left',halign:'center'},
  			  {field : 'BRAND_UNIT_NAME', title : '品牌部', width : 80,align:'center'},
              {field : 'BRAND_NAME', title : '品牌', width : 100,align:'center',halign:'center'}
			);

			var params = $('#searchForm').form('getData');
			$('#dataGridJG').datagrid('options').queryParams = params;
			$('#dataGridJG').datagrid({
				url : BasePath + "/leisure_brand_check/list.json",
				rownumbers : true,
				columns : [columns]
			});
		}
	});

};

//清空
leisureBrand.clear=function(){
	$("#searchForm").form("clear");
	$("#supplierNoCondition,#itemCodeCondition,#orderUnitNoId,#buyerNoId,#brandNoId").val("");
	setDate();
};

leisureBrand.doExport = function() {
	$.fas.exportExcel({
		dataGridId : "dataGridJG",
		exportUrl : "/leisure_brand_check/list_export",
		exportTitle : "休闲品牌对账报表"
	});
}

//开始日期
function getStartDate() {
	var d = new Date();
	var year = d.getFullYear();
	var cmonth = d.getMonth();
	var day = d.getDate();
	var startDate = new Date();
	startDate.setDate(1);
	if (cmonth === 0 && day <= 10) {
		startDate.setFullYear(d.getFullYear() - 1);
		startDate.setMonth(10);
	} else if (cmonth === 0 && day > 10) {
		startDate.setFullYear(d.getFullYear() - 1);
		startDate.setMonth(11);
	} else {
		if (day <= 10) {
			startDate.setMonth(cmonth - 2);
		} else {
			startDate.setMonth(cmonth - 1);
		}
	}
	startDate.setDate(26);
	return startDate;
};

// 结束日期
function getEndDate(){
	var d = new Date();
	var year = d.getFullYear();
	var cmonth = d.getMonth();
	var day = d.getDate();
	var endDate = new Date();
	if (cmonth === 0 && day <= 10) {
		endDate.setFullYear(d.getFullYear() - 1);
		endDate.setMonth(11);
	} else if (cmonth === 0 && day > 10) {
		endDate.setFullYear(d.getFullYear());
		endDate.setMonth(0);
	} else {
		if (day <= 10) {
			endDate.setMonth(cmonth - 1);
		} else {
			endDate.setMonth(cmonth);
		}
	}
	endDate.setDate(25);
	return endDate;
};