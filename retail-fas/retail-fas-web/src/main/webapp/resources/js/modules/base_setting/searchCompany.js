var searchCompany = {};
var tbgrid = {};
/**
 * 查询结算公司
 */
searchCompany.dosearchCompany = function() {
	var reqParam = $("#subForm").form("getData");
	var queryMxURL = BasePath + "/base_setting/company/list.json";
	$("#searchCompanyDG").datagrid('options').queryParams = reqParam;
	$("#searchCompanyDG").datagrid('options').url = queryMxURL;
	$("#searchCompanyDG").datagrid('load');
};

/**
 * 清空查询条件
 */
searchCompany.clearCondition = function() {
	$("#subForm").form("clear");
};

$(function() {

	// 查询组件中必须使用这种方法定义列，要不然双击选中行，页面将无反应
	tbgrid = $('#searchCompanyDG').datagrid({
		url : '',
		singleSelect : true,
		pagination:true,
		pageSize:20,
        pageList:[10,20,50,100,200,300,400,500],
		columns : [ [ {
			title : '请选择',
			field : 'col1',
			width : 80,
			formatter : function() {
				return " <input type='radio' name='optsel' />";
			}
		}, {
			field : 'companyNo',
			title : '结算公司编码',
			align : 'left',
			halign : 'center',
			width : 100
		},{
			field : 'name',
			title : '结算公司名称',
			align : 'left',
			halign : 'center',
			width : 250
		}] ]
	});

	$('#companyNoCodeCondition').bind('blur',function(val){
		$('#companyNoCodeCondition').val(val.target.value.trim());
	});
	
	$('#nameCondition').bind('blur',function(val){
		$('#nameCondition').val(val.target.value.trim());
	});
	
});
