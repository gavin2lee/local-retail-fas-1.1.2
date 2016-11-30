/**
 * 总部代采购明细表
 */
function insteadOfBuyEntryDtl() {
};
insteadOfBuyEntryDtl.prototype = new areaDetail();
var entryDtl = new insteadOfBuyEntryDtl();

$(function() {
	entryDtl.init({
		formId : 'searchForm',
		dataGridId : 'dataGridJG',
		queryUrl : '/hq_insteadOf_buy/list.json',
		exportUrl : '/hq_insteadOf_buy/export',
		excelTitle : '采购明细表'
	});
	toolSearch({
        appendTo:$('#toolbar'), 
        target:$('#subLayout'), 
        collapsible:true
	});
	setDefaultDate();
	$("#btn-direct").on('click',function(){
		var balanceType = '13';
		var startDate = $('#sendDateStart').val();
		var endDate = $('#sendDateEnd').val();
		if(startDate =='' || endDate==''){
			showWarn("请选择日期范围！");
			return ;
		}
		$.messager.confirm("确认","确定更新"+startDate+"至"+endDate+"日期范围异常单据价格?",function(r) {
			if (r) {
				$("#updatePriceForm").remove();
				$("<form id='updatePriceForm'  method='post'></form>").appendTo("body"); ;
				var fromObj=$('#updatePriceForm');
    			$.messager.progress({
    				title:'请稍后',
    				msg:'正在处理中...',
    				interval:5000
    			}); 
				fromObj.form('submit', {
					url: BasePath +'/bill_balance/hq/update_exception_price',
					onSubmit: function(param){
						param.balanceType = balanceType;
						param.balanceStartDate = startDate;
						param.balanceEndDate = endDate;
					},
					success: function(){
						$("#updatePriceForm").remove();
						$.messager.progress('close');
						$.fas.showMsg('更新成功！',5000);
						entryDtl.search();
				    }
			   });
			}
		});
	});
});

entryDtl.clear = function() {
	$("#searchForm").form("clear");
	$("input:hidden[name!='sign']").val("");
	setDefaultDate();
};

function setDefaultDate(){
	$("#sendDateStart").datebox('setValue',getStartDate().format("yyyy-MM-dd"));
	$("#sendDateEnd").datebox('setValue',getEndDate().format("yyyy-MM-dd"));
}