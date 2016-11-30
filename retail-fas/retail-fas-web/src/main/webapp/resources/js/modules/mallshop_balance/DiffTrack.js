function DiffTrackDialog() {
	// 转换状态值
	this.formatStatus = function(value) {
		if(value == '0') {
			return "未完成";
		}
		if(value == '1') {
			return "已完成";
		}
	};
	
	this.generateType = function(value, rowData, rowIndex) {
	    var generateType = [{'value':'0', 'text': '系统生成'}, {'value':'1', 'text':'手工新增'}];
	    for(var i = 0; i < generateType.length; i++) {
	        if(generateType[i].value == value) {
	            return generateType[i].text;
	        }
	    }
	    return "";
	};
	
};

//结算大类列表页面，组装查询subgrid数据的参数
var buildSubgridUrl = function(rowData, hasParam) {
	var params = "?";
	if(hasParam) {
		params += "&";
	}
	return params + "rootDiffId=" + rowData.id;
};

//导出excel
function  exportExcelDetails() {
	var $dg = $("#dataGridDiv");
    var queryParams = $dg.datagrid('options').queryParams;
    var dgtColumn=[
        			{field : 'shopNo',title : '店铺编码',width : 80,align:'left'},
	                  {field : 'shortName',title : '店铺名称',width : 100,align:'left'},
	                  {field : 'month',title : '结算月',width : 80,align:'center'},
	                  {field : 'balanceNo',title : '结算单编号',width : 160,align:'left'},	
	                  {field : 'brandName',title : '品牌',width : 80,align:'left'},
	                  {field : 'diffTypeCode',title : '差异编码',width : 80,align:'left',notexport:true},
	                  {field : 'diffTypeName',title : '差异类型',width : 80,align:'left'}, 
	                  {field : 'diffDate',title : '差异结算日',width : 100,align:'center'},
	                  {field : 'proNo',title : '活动编码',width : 80,align:'left'},
	                  {field : 'proName',title : '活动名称',width : 100,align:'left'},
	                  {field : 'discountN',title : '扣率',width : 80,align:'right'},
	                  {field : 'discountCode',title : '扣率代码',width : 80,align:'center',halign:'center'},
	                  {field : 'mallNumber',title : '商场报数',width : 80,align:'right',exportType:'number'},
	                  {field : 'salesAmount',title : '系统收入',width : 80,align:'right',exportType:'number'},
	                  {field : 'diffAmount',title : '扣费差异',width : 80,align:'right',exportType:'number'},
	                  {field : 'salesDiffamount',title : '报数差异',width : 80,align:'right',halign:'center',exportType:'number'},	                  
	                  {field : 'diffReason',title : '差异原因',width : 100,align:'left'},
	                  {field : 'diffBalance',title : '差异余额',width : 80,align:'right',exportType:'number'},
	                  {field : 'statusName',title : '状态',width : 90,align:'center',formatter : dialog.formatStatus},
	                  {field : 'monthDiff',title : '结算月',width : 20,align:'center'},
	                  {field : 'balanceNoDiff', title : '结算单编码', width : 40, align : 'left'},
	             	  {field : 'diffBillNoDiff', title : '差异单据编码', width : 40, align : 'left'},
	             	  {field : 'adjustTypeName', title : '调整类型', width : 30, align : 'center'},
	             	  {field : 'adjustAmount', title : '调整金额', width : 20, align : 'right',exportType:'number'},
				      {field : 'adjustDate', title : '调整日期', width : 20, align : 'center'},
		              {field : 'adjustReason', title : '备注/调整原因', width : 30, align : 'left'},
		              {field : 'statusDiffName',title : '状态',width : 30,align:'center'}
	 			];
    // 获取排序字段，由于sortName只能获取field字段，所以需要转换
    var sortName = $dg.datagrid('options').sortName;
    var sortField = "", sortOrder = $dg.datagrid('options').sortOrder;
    var exportColumns = JSON.stringify(dgtColumn);
    var dataRow = $dg.datagrid('getRows');
    $("#exportExcelForm").remove();
    $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
    if(dataRow.length > 0) {
    	$('#exportExcelForm').form('submit', {
            url : BasePath + "/bill_shop_balance_diff/export?noRootDiffId=true",
            onSubmit : function(params) {
            	params.exportColumns = exportColumns;
            	params.fileName = "结算差异跟踪明细信息导出";
            	params.exportType = "common";
            	params.orderByField = sortField;
            	params.orderBy = sortOrder;
                if(queryParams != null && queryParams != {}) {
                    $.each(queryParams, function(i) {
                    	params[i] = queryParams[i];
                    });
                }
            },
            success : function() {

            }
        });
    } else {
        showWarn('查询记录为空，不能导出!');
    }
}

var dialog = null;
$(function() {
	$.fas.extend(DiffTrackDialog, FasDialogController);
	dialog = new DiffTrackDialog();
	dialog.init("/bill_shop_balance_diff", {
		dataGridId : "dataGridDiv",
		searchFormId : "searchForm",
		searchUrl : "/list.json?noRootDiffId=true",
		exportTitle : "结算差异跟踪列表信息导出",
		exportUrl : "/do_fas_export?noRootDiffId=true&billStatus=overAll"
	});
});