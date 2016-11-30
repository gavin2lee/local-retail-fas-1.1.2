/**
 * 跨区调拨出库明细表
 */
function detail() {
};
detail.prototype = new areaDetail();
var crossArea = new detail();

$(function() {
	crossArea.init({
		formId : 'mainForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/area_among_transfer/list.json?subTotal=yes',
		exportUrl : '/area_among_transfer/export',
		excelTitle : '调货明细表(应收)'
	});
	contrastdtl.addMainTab();
	toolSearch({
		appendTo:$('#toolbar'), //添加位置，默认为$('#toolbar')
		target:$('#subLayout'), //控制对象，默认为$('#subLayout')
		collapsible:true //是否显示下拉箭头
	});
	setDate();
});

//设置时间
function setDate() {
	$("#sendDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
}

crossArea.clear = function() {
	$("#mainForm").form("clear");
	$("#mainForm input:hidden[name!='aToA']").val('');
	setDate();
};

/**
 * 收发汇总表
 */
function contrastList() {
};
contrastList.prototype = new areaDetail();
var contrastdtl = new contrastList();

contrastdtl.addMainTab = function() {
	$('#mainTab').tabs('add',
		{
			title : '收发汇总表',
			selected : false,
			closable : false,
			href : BasePath + '/area_among_transfer/list_tabMain.htm?atoa='+$("#aToA").val(),
			onLoad : function(panel) {
				var atoa = $("#aToA").val();
				$("#RToS").val(atoa);
				//初始化属性
				contrastdtl.init({
					formId : 'searchForm',
					dataGridId : 'contrastDg',
					queryUrl : '/area_among_transfer/summary_list.json?',
					exportUrl : '/area_among_transfer/export_summary',
					excelTitle : '收发汇总表'
				});
				toolSearch({
			        appendTo:$('#mainToolbar'), 
			        target:$('#mainLayout'), 
			        collapsible:true 
				});
				setDefaultDate();
			}
		}
	);
};

//设置时间
function setDefaultDate() {
	$("#sendDateStartCond").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEndCond").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
}

contrastdtl.clearContrast = function() {
	$("#searchForm").form("clear");
	$("#searchForm input:hidden[name!='RToS']").val('');
	setDefaultDate();
};
