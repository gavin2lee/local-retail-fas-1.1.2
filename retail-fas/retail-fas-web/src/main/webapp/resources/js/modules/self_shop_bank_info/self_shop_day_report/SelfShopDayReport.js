var selfShopDayReport = new Object();

var sales = 0;//显示销售数目
var paidins = 0;;//显示实收数字
var diffs = 0;//显示差异数字
var sum = 0;
var common_setting = {
	
	searchBtn : "btn-search",
	searchUrl : "/self_shop_day_report/list.json",
	dataGridId : "dataGridDiv",
	//export_title
	title : "店铺日报表导出",
	//export_url
	url : "/self_shop_day_report/do_fas_export",
	searchFormId : "searchForm"
	
};

selfShopDayReport.searchEventBtn = function(){
	var tab = $('#mainTab').tabs("getSelected");
	var tabTitle = tab.panel('options').title;
	var fromObj = $('#searchForm');
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	var fromObjStr = convertArray($("#searchForm").serializeArray());
	if(tabTitle == '所有品牌统计') {
		$("#dataGridDiv").datagrid({
			url : BasePath + '/self_shop_day_report/list.json',
			queryParams : eval("(" + fromObjStr + ")"),
			onLoadSuccess : function(data){
				showColumn(data,'dataGridDiv');
			}
		});
		$(":checkbox:eq(0)").attr("checked", false);
	}else if(tabTitle == '分品牌统计'){
		$("#dataGridDivForBrand").datagrid({
			url : BasePath + '/self_shop_day_report/list_brand',
			queryParams : eval("(" + fromObjStr + ")"),
			onLoadSuccess : function(data){
				showColumn(data,'dataGridDivForBrand');
			}
		});
		$(":checkbox:eq(0)").attr("checked", false);
	}
	
};

function showColumn(data,id){
	//隐藏要判断的列
	$('#'+id).datagrid('hideColumn','p01');
	$('#'+id).datagrid('hideColumn', 'p03');
	$('#'+id).datagrid('hideColumn', 'p04');
	$('#'+id).datagrid('hideColumn', 'p05');
	$('#'+id).datagrid('hideColumn', 'p06');
	$('#'+id).datagrid('hideColumn', 'p08');
	$('#'+id).datagrid('hideColumn', 'p09');
	$('#'+id).datagrid('hideColumn', 'p10');
	$('#'+id).datagrid('hideColumn', 'p11');
	$('#'+id).datagrid('hideColumn', 'p12');
	$('#'+id).datagrid('hideColumn', 'p13');
	$('#'+id).datagrid('hideColumn', 'p14');
	$('#'+id).datagrid('hideColumn', 'p15');
	$('#'+id).datagrid('hideColumn', 'p16');
	$('#'+id).datagrid('hideColumn', 'p17');
	$('#'+id).datagrid('hideColumn', 'p18');
	$('#'+id).datagrid('hideColumn', 'p19');
	$('#'+id).datagrid('hideColumn', 'p20');
	$('#'+id).datagrid('hideColumn', 'p20151010');
	$('#'+id).datagrid('hideColumn', 'p21');
	$('#'+id).datagrid('hideColumn', 'p22');
	$('#'+id).datagrid('hideColumn', 'p23');
	$('#'+id).datagrid('hideColumn', 'p24');
	$('#'+id).datagrid('hideColumn', 'p25');
	$('#'+id).datagrid('hideColumn', 'p26');
	$('#'+id).datagrid('hideColumn', 'p27');
	$('#'+id).datagrid('hideColumn', 'p28');
	$('#'+id).datagrid('hideColumn', 'p29');
	$('#'+id).datagrid('hideColumn', 'p30');
	$('#'+id).datagrid('hideColumn', 'p31');
	$('#'+id).datagrid('hideColumn', 'p32');
	$('#'+id).datagrid('hideColumn', 'p33');
	$('#'+id).datagrid('hideColumn', 'p35');
	$('#'+id).datagrid('hideColumn', 'p36');
	$('#'+id).datagrid('hideColumn', 'p37');
	$('#'+id).datagrid('hideColumn', 'p38');
	$('#'+id).datagrid('hideColumn', 'p999');
	$('#'+id).datagrid('hideColumn', 'totalAmount');
	$('#'+id).datagrid('hideColumn','s01');
	$('#'+id).datagrid('hideColumn', 's03');
	$('#'+id).datagrid('hideColumn', 's04');
	$('#'+id).datagrid('hideColumn', 's05');
	$('#'+id).datagrid('hideColumn', 's06');
	$('#'+id).datagrid('hideColumn', 's08');
	$('#'+id).datagrid('hideColumn', 's09');
	$('#'+id).datagrid('hideColumn', 's10');
	$('#'+id).datagrid('hideColumn', 's11');
	$('#'+id).datagrid('hideColumn', 's12');
	$('#'+id).datagrid('hideColumn', 's13');
	$('#'+id).datagrid('hideColumn', 's14');
	$('#'+id).datagrid('hideColumn', 's15');
	$('#'+id).datagrid('hideColumn', 's16');
	$('#'+id).datagrid('hideColumn', 's17');
	$('#'+id).datagrid('hideColumn', 's18');
	$('#'+id).datagrid('hideColumn', 's19');
	$('#'+id).datagrid('hideColumn', 's20');
	$('#'+id).datagrid('hideColumn', 's20151010');
	$('#'+id).datagrid('hideColumn', 's21');
	$('#'+id).datagrid('hideColumn', 's22');
	$('#'+id).datagrid('hideColumn', 's23');
	$('#'+id).datagrid('hideColumn', 's24');
	$('#'+id).datagrid('hideColumn', 's25');
	$('#'+id).datagrid('hideColumn', 's26');
	$('#'+id).datagrid('hideColumn', 's27');
	$('#'+id).datagrid('hideColumn', 's28');
	$('#'+id).datagrid('hideColumn', 's29');
	$('#'+id).datagrid('hideColumn', 's30');
	$('#'+id).datagrid('hideColumn', 's31');
	$('#'+id).datagrid('hideColumn', 's32');
	$('#'+id).datagrid('hideColumn', 's33');
	$('#'+id).datagrid('hideColumn', 's35');
	$('#'+id).datagrid('hideColumn', 's36');
	$('#'+id).datagrid('hideColumn', 's37');
	$('#'+id).datagrid('hideColumn', 's38');
	$('#'+id).datagrid('hideColumn', 's999');
	$('#'+id).datagrid('hideColumn', 'amount');
	$('#'+id).datagrid('hideColumn', 'd01');
	$('#'+id).datagrid('hideColumn', 'd03');
	$('#'+id).datagrid('hideColumn', 'd04');
	$('#'+id).datagrid('hideColumn', 'd05');
	$('#'+id).datagrid('hideColumn', 'd06');
	$('#'+id).datagrid('hideColumn', 'd08');
	$('#'+id).datagrid('hideColumn', 'd09');
	$('#'+id).datagrid('hideColumn', 'd10');
	$('#'+id).datagrid('hideColumn', 'd11');
	$('#'+id).datagrid('hideColumn', 'd12');
	$('#'+id).datagrid('hideColumn', 'd13');
	$('#'+id).datagrid('hideColumn', 'd14');
	$('#'+id).datagrid('hideColumn', 'd15');
	$('#'+id).datagrid('hideColumn', 'd16');
	$('#'+id).datagrid('hideColumn', 'd17');
	$('#'+id).datagrid('hideColumn', 'd18');
	$('#'+id).datagrid('hideColumn', 'd19');
	$('#'+id).datagrid('hideColumn', 'd20');
	$('#'+id).datagrid('hideColumn', 'd20151010');
	$('#'+id).datagrid('hideColumn', 'd21');
	$('#'+id).datagrid('hideColumn', 'd22');
	$('#'+id).datagrid('hideColumn', 'd23');
	$('#'+id).datagrid('hideColumn', 'd24');
	$('#'+id).datagrid('hideColumn', 'd25');
	$('#'+id).datagrid('hideColumn', 'd26');
	$('#'+id).datagrid('hideColumn', 'd27');
	$('#'+id).datagrid('hideColumn', 'd28');
	$('#'+id).datagrid('hideColumn', 'd29');
	$('#'+id).datagrid('hideColumn', 'd30');
	$('#'+id).datagrid('hideColumn', 'd31');
	$('#'+id).datagrid('hideColumn', 'd32');
	$('#'+id).datagrid('hideColumn', 'd33');
	$('#'+id).datagrid('hideColumn', 'd35');
	$('#'+id).datagrid('hideColumn', 'd36');
	$('#'+id).datagrid('hideColumn', 'd37');
	$('#'+id).datagrid('hideColumn', 'd38');
	$('#'+id).datagrid('hideColumn', 'd999');
	$('#'+id).datagrid('hideColumn', 'returnAmount');
	$('#'+id).datagrid('hideColumn', 'actualReturnAmount');
	$('#'+id).datagrid('hideColumn', 'diffAmount');
	
	$('#'+id).datagrid('hideColumn', 'poundage');
	$('#'+id).datagrid('hideColumn', 'sum');
	//查处合计对象
	var all = data.all;
	if(all != undefined){
		if (all.p01 != 0) {
			$('#'+id).datagrid('showColumn', 'p01');
		}
		if (all.p03 != 0) {
			$('#'+id).datagrid('showColumn', 'p03');
		}
		if (all.p04 != 0) {
			$('#'+id).datagrid('showColumn', 'p04');
		}
		if (all.p05 != 0) {
			$('#'+id).datagrid('showColumn', 'p05');
		}
		if (all.p06 != 0) {
			$('#'+id).datagrid('showColumn', 'p06');
		}
		if (all.p08 != 0) {
			$('#'+id).datagrid('showColumn', 'p08');
		}
		if (all.p09 != 0) {
			$('#'+id).datagrid('showColumn', 'p09');
		}
		if (all.p10 != 0) {
			$('#'+id).datagrid('showColumn', 'p10');
		}
		if (all.p11 != 0) {
			$('#'+id).datagrid('showColumn', 'p11');
		}
		if (all.p12 != 0) {
			$('#'+id).datagrid('showColumn', 'p12');
		}
		if (all.p13 != 0) {
			$('#'+id).datagrid('showColumn', 'p13');
		}
		if (all.p14 != 0) {
			$('#'+id).datagrid('showColumn', 'p14');
		}
		if (all.p15 != 0) {
			$('#'+id).datagrid('showColumn', 'p15');
		}
		if (all.p16 != 0) {
			$('#'+id).datagrid('showColumn', 'p16');
		}
		if (all.p17 != 0) {
			$('#'+id).datagrid('showColumn', 'p17');
		}
		if (all.p18 != 0) {
			$('#'+id).datagrid('showColumn', 'p18');
		}
		if (all.p19 != 0) {
			$('#'+id).datagrid('showColumn', 'p19');
		}
		if (all.p20 != 0) {
			$('#'+id).datagrid('showColumn', 'p20');
		}
		if (all.p20151010 != 0) {
			$('#'+id).datagrid('showColumn', 'p20151010');
		}
		if (all.p21 != 0) {
			$('#'+id).datagrid('showColumn', 'p21');
		}
		if (all.p22 != 0) {
			$('#'+id).datagrid('showColumn', 'p22');
		}
		if (all.p23 != 0) {
			$('#'+id).datagrid('showColumn', 'p23');
		}
		if (all.p24 != 0) {
			$('#'+id).datagrid('showColumn', 'p24');
		}
		if (all.p25 != 0) {
			$('#'+id).datagrid('showColumn', 'p25');
		}
		if (all.p26 != 0) {
			$('#'+id).datagrid('showColumn', 'p26');
		}
		if (all.p27 != 0) {
			$('#'+id).datagrid('showColumn', 'p27');
		}
		if (all.p28 != 0) {
			$('#'+id).datagrid('showColumn', 'p28');
		}
		if (all.p29 != 0) {
			$('#'+id).datagrid('showColumn', 'p29');
		}
		if (all.p30 != 0) {
			$('#'+id).datagrid('showColumn', 'p30');
		}
		if (all.p31 != 0) {
			$('#'+id).datagrid('showColumn', 'p31');
		}
		if (all.p32 != 0) {
			$('#'+id).datagrid('showColumn', 'p32');
		}
		if (all.p33 != 0) {
			$('#'+id).datagrid('showColumn', 'p33');
		}
		if (all.p35 != 0) {
			$('#'+id).datagrid('showColumn', 'p35');
		}
		if (all.p36 != 0) {
			$('#'+id).datagrid('showColumn', 'p36');
		}
		if (all.p37 != 0) {
			$('#'+id).datagrid('showColumn', 'p37');
		}
		if (all.p38 != 0) {
			$('#'+id).datagrid('showColumn', 'p38');
		}
		if (all.p999 != 0) {
			$('#'+id).datagrid('showColumn', 'p999');
		}
		if (all.totalAmount !=0) {
			$('#'+id).datagrid('showColumn', 'totalAmount');
		}
		if (all.s01 != 0) {
			$('#'+id).datagrid('showColumn', 's01');
		}
		if (all.s04 != 0) {
			$('#'+id).datagrid('showColumn', 's04');
		}
		if (all.s03 != 0) {
			$('#'+id).datagrid('showColumn', 's03');
		}
		if (all.s05 != 0) {
			$('#'+id).datagrid('showColumn', 's05');
		}
		if (all.s06 != 0) {
			$('#'+id).datagrid('showColumn', 's06');
		}
		if (all.s08 != 0) {
			$('#'+id).datagrid('showColumn', 's08');
		}
		if (all.s09 != 0) {
			$('#'+id).datagrid('showColumn', 's09');
		}
		if (all.s10 != 0) {
			$('#'+id).datagrid('showColumn', 's10');
		}
		if (all.s11 != 0) {
			$('#'+id).datagrid('showColumn', 's11');
		}
		if (all.s12 != 0) {
			$('#'+id).datagrid('showColumn', 's12');
		}
		if (all.s13 != 0) {
			$('#'+id).datagrid('showColumn', 's13');
		}
		if (all.s14 != 0) {
			$('#'+id).datagrid('showColumn', 's14');
		}
		if (all.s15 != 0) {
			$('#'+id).datagrid('showColumn', 's15');
		}
		if (all.s16 != 0) {
			$('#'+id).datagrid('showColumn', 's16');
		}
		if (all.s17 != 0) {
			$('#'+id).datagrid('showColumn', 's17');
		}
		if (all.s18 != 0) {
			$('#'+id).datagrid('showColumn', 's18');
		}
		if (all.s19 != 0) {
			$('#'+id).datagrid('showColumn', 's19');
		}
		if (all.s20 != 0) {
			$('#'+id).datagrid('showColumn', 's20');
		}
		if (all.s20151010 != 0) {
			$('#'+id).datagrid('showColumn', 's20151010');
		}
		if (all.s21 != 0) {
			$('#'+id).datagrid('showColumn', 's21');
		}
		if (all.s22 != 0) {
			$('#'+id).datagrid('showColumn', 's22');
		}
		if (all.s23 != 0) {
			$('#'+id).datagrid('showColumn', 's23');
		}
		if (all.s24 != 0) {
			$('#'+id).datagrid('showColumn', 's24');
		}
		if (all.s25 != 0) {
			$('#'+id).datagrid('showColumn', 's25');
		}
		if (all.s26 != 0) {
			$('#'+id).datagrid('showColumn', 's26');
		}
		if (all.s27 != 0) {
			$('#'+id).datagrid('showColumn', 's27');
		}
		if (all.s28 != 0) {
			$('#'+id).datagrid('showColumn', 's28');
		}
		if (all.s29 != 0) {
			$('#'+id).datagrid('showColumn', 's29');
		}
		if (all.s30 != 0) {
			$('#'+id).datagrid('showColumn', 's30');
		}
		if (all.s31 != 0) {
			$('#'+id).datagrid('showColumn', 's31');
		}
		if (all.s32 != 0) {
			$('#'+id).datagrid('showColumn', 's32');
		}
		if (all.s33 != 0) {
			$('#'+id).datagrid('showColumn', 's33');
		}
		if (all.s35 != 0) {
			$('#'+id).datagrid('showColumn', 's35');
		}
		if (all.s36 != 0) {
			$('#'+id).datagrid('showColumn', 's36');
		}
		if (all.s37 != 0) {
			$('#'+id).datagrid('showColumn', 's38');
		}
		if (all.s999 != 0) {
			$('#'+id).datagrid('showColumn', 's999');
		}
		if (all.amount !=0) {
			$('#'+id).datagrid('showColumn', 'amount');
		}
		if (all.d01 != 0) {
			$('#'+id).datagrid('showColumn', 'd01');
		}
		if (all.d04 != 0) {
			$('#'+id).datagrid('showColumn', 'd04');
		}
		if (all.d03 != 0) {
			$('#'+id).datagrid('showColumn', 'd03');
		}
		if (all.d05 != 0) {
			$('#'+id).datagrid('showColumn', 'd05');
		}
		if (all.d06 != 0) {
			$('#'+id).datagrid('showColumn', 'd06');
		}
		if (all.d08 != 0) {
			$('#'+id).datagrid('showColumn', 'd08');
		}
		if (all.d09 != 0) {
			$('#'+id).datagrid('showColumn', 'd09');
		}
		if (all.d10 != 0) {
			$('#'+id).datagrid('showColumn', 'd10');
		}
		if (all.d11 != 0) {
			$('#'+id).datagrid('showColumn', 'd11');
		}
		if (all.d12 != 0) {
			$('#'+id).datagrid('showColumn', 'd12');
		}
		if (all.d13 != 0) {
			$('#'+id).datagrid('showColumn', 'd13');
		}
		if (all.d14 != 0) {
			$('#'+id).datagrid('showColumn', 'd14');
		}
		if (all.d15 != 0) {
			$('#'+id).datagrid('showColumn', 'd15');
		}
		if (all.d16 != 0) {
			$('#'+id).datagrid('showColumn', 'd16');
		}
		if (all.d17 != 0) {
			$('#'+id).datagrid('showColumn', 'd17');
		}
		if (all.d18 != 0) {
			$('#'+id).datagrid('showColumn', 'd18');
		}
		if (all.d19 != 0) {
			$('#'+id).datagrid('showColumn', 'd19');
		}
		if (all.d20 != 0) {
			$('#'+id).datagrid('showColumn', 'd20');
		}
		if (all.d20151010 != 0) {
			$('#'+id).datagrid('showColumn', 'd20151010');
		}
		if (all.d21 != 0) {
			$('#'+id).datagrid('showColumn', 'd21');
		}
		if (all.d22 != 0) {
			$('#'+id).datagrid('showColumn', 'd22');
		}
		if (all.d23 != 0) {
			$('#'+id).datagrid('showColumn', 'd23');
		}
		if (all.d24 != 0) {
			$('#'+id).datagrid('showColumn', 'd24');
		}
		if (all.d25 != 0) {
			$('#'+id).datagrid('showColumn', 'd25');
		}
		if (all.d26 != 0) {
			$('#'+id).datagrid('showColumn', 'd26');
		}
		if (all.d27 != 0) {
			$('#'+id).datagrid('showColumn', 'd27');
		}
		if (all.d28 != 0) {
			$('#'+id).datagrid('showColumn', 'd28');
		}
		if (all.d29 != 0) {
			$('#'+id).datagrid('showColumn', 'd29');
		}
		if (all.d30 != 0) {
			$('#'+id).datagrid('showColumn', 'd30');
		}
		if (all.d31 != 0) {
			$('#'+id).datagrid('showColumn', 'd31');
		}
		if (all.d32 != 0) {
			$('#'+id).datagrid('showColumn', 'd32');
		}
		if (all.d33 != 0) {
			$('#'+id).datagrid('showColumn', 'd33');
		}
		if (all.d35 != 0) {
			$('#'+id).datagrid('showColumn', 'd35');
		}
		if (all.d36 != 0) {
			$('#'+id).datagrid('showColumn', 'd36');
		}
		if (all.d37 != 0) {
			$('#'+id).datagrid('showColumn', 'd37');
		}
		if (all.d38 != 0) {
			$('#'+id).datagrid('showColumn', 'd38');
		}
		if (all.d999 != 0) {
			$('#'+id).datagrid('showColumn', 'd999');
		}
		if (all.returnAmount != 0) {
			$('#'+id).datagrid('showColumn', 'returnAmount');
		}
		if (all.actualReturnAmount != 0) {
			$('#'+id).datagrid('showColumn', 'actualReturnAmount');
		}
		if (all.diffAmount !=0) {
			$('#'+id).datagrid('showColumn', 'diffAmount');
		}
		if (all.poundage != 0) {
			$('#'+id).datagrid('showColumn', 'poundage');
		}
		if (all.sum !=0) {
			$('#'+id).datagrid('showColumn', 'sum');
		}
	}
	var showColumns = data.show;
	if(showColumns != undefined){
		sales = showColumns.sales;
		paidins = showColumns.paidins;
		diffs = showColumns.diffs;
		sum = showColumns.sum;
		
		var head = $("table.datagrid-htable.datagrid-htable-sm tr:eq(0) td:eq(7)").text;
		var sale = $("div.datagrid-cell-group");
		
		for(var i=0;i<sale.length;i++){
			if(sale[i].firstChild.data=="销售"){
				var td = sale[i].firstChild.parentNode.parentNode;
				if(showColumns.sales<1){
					td.hidden = true;
				}else{
					td.colSpan = showColumns.sales;
				}
			}else if(sale[i].firstChild.data=="实收"){
				var td = sale[i].firstChild.parentNode.parentNode;
				if(showColumns.paidins<1){
					td.hidden = true;
				}else{
					td.colSpan = showColumns.paidins;
				}
			}else if(sale[i].firstChild.data=="差异"){
				var td = sale[i].firstChild.parentNode.parentNode;
				if(showColumns.diffs<1){
					td.hidden = true;
				}else{
					td.colSpan = showColumns.diffs;
				}
			}else if(sale[i].firstChild.data=="进账"){
				var td = sale[i].firstChild.parentNode.parentNode;
				if(showColumns.sum<1){
					td.hidden = true;
				}else{
					td.colSpan = showColumns.sum;
				}
				
			}
		}
	}
}

selfShopDayReport.removeEventBtn = function(){
	
	$('#' + common_setting.searchFormId).form("clear");
	$(':input','#' + common_setting.searchFormId).not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
	
};

selfShopDayReport.exportEventBtn = function(){
	var tab = $('#mainTab').tabs("getSelected");
	var tabTitle = tab.panel('options').title;
	var setting = null;
	if(tabTitle == '所有品牌统计') {
		setting = {
				id : "dataGridDiv",
				title : "店铺日报表所有品牌导出",
				url : "/self_shop_day_report/do_fas_export",
				i : 0
		};
		exportData(setting);
	}else if(tabTitle == '分品牌统计'){
		setting = {
				id : "dataGridDivForBrand",
				title : "店铺日报表分品牌导出",
				url : "/self_shop_day_report/brand_export",
				i : 1
		};
		exportData(setting);
	}
};

function exportData(setting){
	var $dg = $("#" + setting.id);
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	var columns = [],firstHeaderColumns = [];
	
	if(grepColumns && grepColumns.length>1){
		columns = $.grep(grepColumns[1],function(o,i) {
			if($(o).attr("notexport") == true){
				return true;
			}
			if($(o).attr("hidden") == true) {
				return true;
			}
			return false;
		},true);
		firstHeaderColumns = $.grep(grepColumns[0],function(o,i) {
			if($(o).attr("notexport") == true){
				return true;
			}
			if($(o).attr("hidden") == true) {
				return true;
			}
			return false;
			
		},true);
	}else{
		columns = $.grep(grepColumns[0],function(o,i) {
			if($(o).attr("notexport") == true) {
				return true;
			}
			if($(o).attr("hidden") == true) {
				return true;
			}
			return false;
		},true);
	}
	firstHeaderColumns[5+setting.i].colspan = sales;
	firstHeaderColumns[6+setting.i].colspan = paidins;
	firstHeaderColumns[7+setting.i].colspan = diffs;
	firstHeaderColumns[8+setting.i].colspan = sum;
	if(sum == 0){
		firstHeaderColumns.splice(8+setting.i,1);
	}
	if(diffs == 0){
		firstHeaderColumns.splice(7+setting.i,1);
	}
	if(paidins == 0){
		firstHeaderColumns.splice(6+setting.i,1);
	}
	if(sales == 0){
		firstHeaderColumns.splice(5+setting.i,1);
	}
	
	
	var exportColumns = JSON.stringify(columns);
	var url = BasePath + setting.url;
	var dataRow = $dg.datagrid('getRows');

	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.exportColumns = exportColumns;
				param.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
				param.fileName = setting.title;
				param.exportType = 'common' || '';
				if(params != null && params != {}) {
					$.each(params, function(i) {
						param[i] = params[i];
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

$(function(){
	// 初始化页签
	$('#mainTab').addTab({
		title : "所有品牌统计",
		href : BasePath + "/self_shop_day_report/self_shop_day_report_all",
		selected : true,
		onLoad : function(panel) {
			
		}
	});
	$('#mainTab').addTab({
		title : "分品牌统计",
		href : BasePath + "/self_shop_day_report/self_shop_day_report_brand",
		onLoad : function(panel) {
			
		}
	});
	
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
	
	$("#startOutDate").val(firstDay.format("yyyy-MM-dd"));
	$("#endOutDate").val(lastDay.format("yyyy-MM-dd"));
});



Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 

var changeColor = function(value,row,index){
	if(value != '' && value != 0){
		return 'color:red;';
	}
}