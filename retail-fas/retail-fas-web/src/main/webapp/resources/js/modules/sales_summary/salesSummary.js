var salesSummary = new Object();

//当前用户
salesSummary.currentUser;

// 主表模块路径
//salesSummary.modulePath = BasePath + '/sales_summary';

//清空
salesSummary.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
};

//查询
salesSummary.search = function() {
	var fromObj = $('#searchForm');
	
	var validateForm = fromObj.form('validate');
	// 1.校验必填项
	if (validateForm == false) {
		return;
	}
	var params = $('#searchForm').form('getData');
	var businessType = $("#businessType").combobox("getValues");
	if(businessType){
		params.businessType = businessType.join();
    }
	var categoryNoStr = $("#categoryNo").combobox("getValues");
	if(categoryNoStr){
		params.categoryNo = categoryNoStr.join();
    }
//	var url = salesSummary.modulePath + '/sales_summary/getSalesSummary';
//    $('#dtlDataGrid').datagrid('options').queryParams= params;
//    $('#dtlDataGrid').datagrid('options').url= url;
//    $('#dtlDataGrid').datagrid('load');
	loadColumn(params);
};
//导出销售分类汇总信息
salesSummary.exportTotal = function() {
	fas_common.exportExcel({
		dataGridId : "dtlDataGrid",
		url : "/sales_summary/do_fas_export",
		title : "销售分类汇总信息"
	});
};

// 初始化
$(function(){
	var businessTypeArray = 
		[
		 {'value' : '1' , 'text' : '门店'},
		 {'value' : '2' , 'text' : '批发'},
		 {'value' : '3' , 'text' : '调货'},
		 {'value' : '4' , 'text' : '内购'},
		 {'value' : '99' , 'text' : '其他'}
       ];
	$('#businessType').combobox({
		data : businessTypeArray,
		valueField : 'value',
		textField : 'text'
	});
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1);
	$('#saleStartDate').datebox('setValue', year + '-' + month + '-01');
	var  day = new Date(year,month,0);
	$('#saleEndDate').datebox('setValue', year + '-' + month + '-' + day.getDate());
	
	//绑定店铺通用查询
//	$("#shopName").filterbuilder({
//        type:'organ',
//        organFlag: 2,
//        roleType:'bizCity', 
//        onSave : function(result) { 
//        	var value = $(this).filterbuilder('getValue');
//        	$("#shopNo").val(value);
//        }
//    });
	
	ajaxRequestAsync(BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	var  organTypeNo =  currentUser.organTypeNo;
	$("#organTypeNo").val(organTypeNo);
	var catgoryList = null;
	if("U010101" == organTypeNo){
		catgoryList = 
			[
			 {'value' : '01' , 'text' : '鞋'},
			 {'value' : '02' , 'text' : '童鞋'},
			 {'value' : '03' , 'text' : '服'},
			 {'value' : '04' , 'text' : '包饰'},
			 {'value' : '05' , 'text' : '护鞋用品'},
			 {'value' : '06' , 'text' : '物料'},
			 {'value' : '07' , 'text' : '其他'}
	       ];
	}else if("U010102" == organTypeNo){
		catgoryList = 
			[
			 {'value' : '41' , 'text' : '鞋'},
			 {'value' : '46' , 'text' : '服'},
			 {'value' : '47' , 'text' : '配'},
			 {'value' : '7' , 'text' : '其他'}
	       ];
	}else if("U010105" == organTypeNo){
		catgoryList =
			[
			 {'value' : '1' , 'text' : '鞋'},
			 {'value' : '2' , 'text' : '服'},
			 {'value' : '3' , 'text' : '包'},
			 {'value' : '4' , 'text' : '配'},
			 {'value' : '5' , 'text' : '物料'},
			 {'value' : '6' , 'text' : '其他'}
	       ];
	}
	$('#categoryNo').combobox({
		data : catgoryList,
		valueField : 'value',
		textField : 'text'
	});
//	salesSummary.checkedShowColumn();
});

function loadColumn(params) {
	ajaxRequestAsync( BasePath + '/sales_summary/column_list.json',params,function(data){
	if(data.CateGoryColumn){
		var columnsNew = [];
		var dataCateGory=[];
		var dataArrayNumber=[];
		dataCateGory.push({field : 'companyName', title : '公司名称', width :180,align:'left',rowspan:'2'});
		dataCateGory.push({field : 'organName', title : '管理城市', width :90,align:'left',rowspan:'2'});
		dataCateGory.push({field : 'shopNo1', title : '店铺/客户编码', width : 90,rowspan:'2'});
		dataCateGory.push({field : 'shopNoReplace', title : '店铺替换编码', width : 100,rowspan:'2'});
		dataCateGory.push({field : 'shopName', title : '店铺/客户名称', width : 100,align:'left',halign:'center',rowspan:'2'});
		dataCateGory.push({field : 'bizType', title : '业务类型', width : 60,rowspan:'2'});
		dataCateGory.push({field : 'brandUnitNo', title : '品牌部编码', width : 100,rowspan:'2'});
		dataCateGory.push({field : 'brandUnitName', title : '品牌部名称', width : 100,rowspan:'2'});
		dataCateGory.push({field : 'totalQty', title : '数量', width : 60, align:'right',rowspan:'2',exportType:'number'});
		dataCateGory.push({field : 'totalTagPrice', title : '牌价总金额', width : 80, align:'right',rowspan:'2',exportType:'number'});
		dataCateGory.push({field : 'totalAmount', title : '销售总金额', width : 80, align:'right',rowspan:'2',exportType:'number'});
		dataCateGory.push({field : 'totalAmountUnitCost', title : '单位成本总额',  align:'right',width : 90,rowspan:'2',exportType:'number'});
		dataCateGory.push({field : 'totalAmountRegionCost', title : '地区成本总额',  align:'right',width : 90,rowspan:'2',exportType:'number'});
		dataCateGory.push({field : 'totalAmountHeadquarterCost', title : '总部成本总和',  align:'right',width : 90,rowspan:'2',exportType:'number'});
		var count = 0;
		$.each(data.CateGoryColumn,function(index,item){
			dataCateGory.push({
				title:item,
				align:'center',
				colspan:6
			});
			if("其他" == item){
				dataArrayNumber.push({field : 'totalQty07', title : '数量', width : 60, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalTagPrice07', title : '牌价金额 ', width : 80, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmount07', title : '销售金额 ', width : 80, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmountUnitCost07', title : '单位成本', width :90, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmountRegionCost07', title : '地区成本', width : 90, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmountHeadquarterCost07', title : '总部成本', width : 90, align:'right',halign:'center',exportType:'number'});
			}else if("其他" != item){
				count ++;
				dataArrayNumber.push({field : 'totalQty0'+count, title : '数量', width : 60, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalTagPrice0'+count, title : '牌价金额 ', width : 80, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmount0'+count, title : '销售金额 ', width : 80, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmountUnitCost0'+count, title : '单位成本', width :90, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmountRegionCost0'+count, title : '地区成本', width : 90, align:'right',halign:'center',exportType:'number'});
				dataArrayNumber.push({field : 'totalAmountHeadquarterCost0'+count, title : '总部成本', width : 90, align:'right',halign:'center',exportType:'number'});
			}
			
		});
		columnsNew[0] = dataCateGory;
		columnsNew[1] = dataArrayNumber;
		$('#dtlDataGrid').datagrid({
			url:BasePath + '/sales_summary/getSalesSummary',
			queryParams:params,
			columns : columnsNew
		});
		}else{
			showSuc("暂无数据!");
			$('#dtlDataGrid').datagrid('loadData',{total:0,rows:[],footer:[]}); 
		}
	});
};
