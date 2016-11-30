
function searchData() {
	if(!$('#searchForm').form('validate')) {
		return;
	}
	
	ajaxRequest(BasePath + "/category/get_biz.json?levelid=1", null, function(result){
		var columns = [
						{field:'sale_year',title:'销售年',halign:'center',align:'center',width:70},
						{field:'sale_month',title:'销售月',halign:'center',align:'center',width:50},
						{field:'year',title:'结算年',halign:'center',align:'center',width:70},
						{field:'month',title:'结算月',halign:'center',align:'center',width:50},
						{field:'brand_unit_no',title:'品牌部编号',halign:'center',align:'center',width:80},
						{field:'brand_unit_name',title:'品牌部名称',halign:'center',align:'center',width:80},
						{field:'contract_rate',title:'合同扣率',halign:'center',align:'right',width:80},
						{field:'qty_count',title:'数量小计',halign:'center',align:'right',width:80,exportType:'number'},
						{field:'amount_count',title:'销售小计',halign:'center',align:'right',width:80,exportType:'number'},
						{field:'unit_cost_count',title:'成本小计',halign:'center',align:'right',width:80,exportType:'number'}
						];
		$.each(result, function(index, row){
			var column1={};
			column1["title"]=row.name + '数量';
			column1["field"]=row.categoryNo + '_qty';
			column1["width"]=80;
			column1["align"]='right';
			column1["halign"]='center';
			column1["exportType"]='number';
			columns.push(column1);
			
			var column2={};
			column2["title"]=row.name + '销售';
			column2["field"]=row.categoryNo + '_amount';
			column2["width"]=80;
			column2["align"]='right';
			column2["halign"]='center';
			column2["exportType"]='number';
			columns.push(column2);
			
			var column3={};
			column3["title"]=row.name + '成本';
			column3["field"]=row.categoryNo + '_unit_cost';
			column3["width"]=80;
			column3["align"]='right';
			column3["halign"]='center';
			column3["exportType"]='number';
			columns.push(column3);
		});
		columns.push({field:'deductions',title:'系统扣费',halign:'center',align:'right',width:80,exportType:'number'});
		columns.push({field:'bill_amount',title:'系统开票金额',halign:'center',align:'right',width:80,exportType:'number'});
		columns.push({field:'rel_deductions',title:'实际扣费',halign:'center',align:'right',width:80,exportType:'number'});
		columns.push({field:'rel_bill_amount',title:'实际开票金额',halign:'center',align:'right',width:80,exportType:'number'});
		
		var params = $('#searchForm').form('getData');
		$('#dataGridDiv').datagrid('options').queryParams= params;
		
		$('#dataGridDiv').datagrid({
			url:BasePath + '/invoice_base_report/list.json',
			rownumbers:true,
			columns:[
				columns
			]
		});
	});
	
};

function clear() {
	var formId = "searchForm";
	$("#" + formId).form("clear");
	$("#" + formId).find("input").val("");
	$("#" + formId).find("textarea").val("");
};

function exportExcel() {
	$.fas.exportExcel({
		dataGridId : "dataGridDiv",
		exportUrl : "/invoice_base_report/export",
		exportTitle : "开票基础表导出"
	});
};

$(function() {
	
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
	
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#createTimeStart").val(firstDay.format("yyyyMM"));
	$("#createTimeEnd").val(lastDay.format("yyyyMM"));
	
});
