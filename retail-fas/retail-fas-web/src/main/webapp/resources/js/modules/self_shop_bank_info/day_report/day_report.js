var dayReport = new Object();

dayReport.searchEventBtn = function(){
	var tabTitle = $('#mainTab').tabs("getSelected").panel('options').title;
	var validateForm = $('#searchForm').form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	if(tabTitle == '所有品牌统计') {
		search("dataGridDiv",BasePath + '/self_shop_day_report_new/list.json',"all");
		$(":checkbox:eq(0)").attr("checked", false);
	}else if(tabTitle == '分品牌统计'){
		search("dataGridDivBrand",BasePath + '/self_shop_day_report_new/list_brand',"brand");
		$(":checkbox:eq(0)").attr("checked", false);
	}
	
};

function search(div,url,flag){
	$('#searchForm').form('submit', {
		url:BasePath + '/self_shop_day_report_new/query_columns?flag='+flag,
		success:function(data){
			var result = jQuery.parseJSON(data);
			var columns1 = new Array();
			
			var showColumns = result.show;
			if(showColumns != undefined){
				if(showColumns.sales!=0){
					var sales={};
					sales["title"]="销售";
					sales["colspan"]=showColumns.sales;
					columns1.push(sales);
				}
				if(showColumns.paidins!=0){
					var paidins={};
					paidins["title"]="实收";
					paidins["colspan"]=showColumns.paidins;
					columns1.push(paidins);
				}
				if(showColumns.diffs!=0){
					var diffs={};
					diffs["title"]="差异";
					diffs["colspan"]=showColumns.diffs;
					columns1.push(diffs);
				}
				if(showColumns.sum!=0){
					var sum={};
					sum["title"]="进账";
					sum["colspan"]=showColumns.sum;
					columns1.push(sum);
				}
				
			}
			
			var columns = new Array();
			$.each(result.headers, function(i, field){
				var column={};
				column["title"]=field;
				column["field"]=i;
				column["width"]=80;
				column["align"]='right';
				column["halign"]='center';
				column["exportType"]='number';
				//column["formatter"]=function(value,row,index){return value.toFixed(3);};
				columns.push(column);
			});
			
			var params = $('#searchForm').form('getData');
			$('#'+div).datagrid('options').queryParams= params;
			
			$('#'+div).datagrid({
				url: url,
				rownumbers:true,
				columns:[columns1, columns]
			});
		}
	});
};

dayReport.removeEventBtn = function(){
	
	$('#searchForm').form("clear");
	$(':input','#searchForm').not(
				':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
	
};

dayReport.exportEventBtn = function(){
	var tab = $('#mainTab').tabs("getSelected");
	var tabTitle = tab.panel('options').title;
	if(tabTitle == '所有品牌统计') {
		setting = {
				id : "dataGridDiv",
				title : "店铺日报表所有品牌导出",
				url : "/self_shop_day_report_new/do_all_export"
		};
		exportData(setting);
	}else if(tabTitle == '分品牌统计'){
		setting = {
				id : "dataGridDivBrand",
				title : "店铺日报表分品牌导出",
				url : "/self_shop_day_report_new/do_brand_export"
		};
		exportData(setting);
	}
};

function exportData(setting){
	var $dg = $("#" + setting.id);
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	var frozenColumns = $dg.datagrid('options').frozenColumns;
	var columns = [],firstHeaderColumns = [];
	
	if (frozenColumns.length > 0) {
        for (var i = frozenColumns[0].length - 1; i >= 0; i--) {
            if (!frozenColumns[0][i]['expander']) {
                grepColumns[0].unshift(frozenColumns[0][i]);
            }
        }
    }
	
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
	
	// 移除冻结列
    if (frozenColumns.length > 0) {
        for (var i = frozenColumns[0].length - 1; i >= 0; i--) {
            if (!frozenColumns[0][i]['expander']) {
                grepColumns[0].splice($.inArray(frozenColumns[0][i], grepColumns[0]), 1);
            }
        }
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

function DayReport(){
	this.search = function() {
		
	};
};

var dialog = null;
$(function () {
	$('#mainTab').tabs({
	    border:false,
	    onSelect:function(title,index){
	    	if(title=="分品牌统计"){
	    		$("#searchForm").find("th").eq(4)[0].hidden=false;
				$("#searchForm").find("td").eq(4)[0].hidden=false;
	    	}else if(title=="所有品牌统计"){
	    		$("#searchForm").find("th").eq(4)[0].hidden=true;
				$("#searchForm").find("td").eq(4)[0].hidden=true;
	    	}
	    }
	});
	// 初始化页签
	$('#mainTab').addTab({
		title : "所有品牌统计",
		href : BasePath + "/self_shop_day_report_new/day_report_all",
		selected : true,
		onLoad : function(panel) {
			
		}
	});
	$('#mainTab').addTab({
		title : "分品牌统计",
		href : BasePath + "/self_shop_day_report_new/day_report_brand",
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
	
	$.fas.extend(DayReport, FasDialogController);
	dialog = new DayReport();
	dialog.init("/self_shop_day_report_new", {
		dataGridId : "dataGridDiv",
		searchBtn : "btn-search",
		searchUrl : "/list.json",
		searchFormId : "searchForm",
		exportTitle : "店铺日报表导出",
		exportUrl : "/do_fas_export",
		exportType : "common"
	});
});

//获取所有支付方式列
dayReport.showAllColumns = function(){
	var columns=new Array();
	ajaxRequestAsync( BasePath + '/self_shop_day_report_new/query_columns',null,function(result){
		var types = ["p","s","d"];
		for(var m = 0;m<types.length;m++){
			$.each(result, function(i, field){  
	            var column={};  
	            column["title"]=field.payName;  
	            column["field"]=types[m]+field.payCode;  
	            columns.push(column);//当需要formatter的时候自己添加就可以了,原理就是拼接字符串.  
	        });
			var column = {};
			if(types[m] == "p"){
				column["title"]="销售合计";  
	            column["field"]="totalAmount";  
			}else if(types[m] == "s"){
				column["title"]="实收合计";  
	            column["field"]="amount"; 
			}else if(types[m] == "s"){
				var col1 = {};
				col1["title"]="银行卡系统退款";  
	            col1["field"]="returnAmount"; 
	            columns.push(col1);
	            
	            var col2 = {};
				col2["title"]="银行卡实际退款";  
	            col2["field"]="actualReturnAmount"; 
	            columns.push(col2);
				
				column["title"]="差异合计";  
	            column["field"]="amount"; 
			}
            
            columns.push(column);
		}
        
//		$("#dataGridDiv").datagrid({
//			fitColumns:"true",//列宽自适应
//			pagination:"true",
//			rownumbers:"true",
//			pageSize:"20",
//			showFooter:"true",
//			columns:[columns]
//		});
	});
	return columns;
};