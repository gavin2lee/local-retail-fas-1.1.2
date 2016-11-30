var report_detail = new Object();

//查询
report_detail.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		if(!report_detail.isTotal){
			report_detail.loadColumn(params);
		}
		report_detail.loadResult(params);
	}
};

report_detail.loadColumn = function(params) {
	params.rows = report_detail.selectDg.datagrid('options').pageSize;
	params.page = report_detail.selectDg.datagrid('options').pageNumber;
	ajaxRequestAsync( BasePath + '/report/column_list.json',params,function(data){
		if(data.orderUnitColumn){
			var orderUnit = data.orderUnitColumn;
			var columnsNew = [];
			var dataArrayZone=[];
			var dataArrayOrgan=[];
			if(report_detail.isShowByDay){
				dataArrayZone.push({
					title:'',
					colspan:'17'
				});
				dataArrayOrgan.push({
					title:'',
					colspan:'17'
				});
			}else{
				dataArrayZone.push({
					title:'',
					colspan:'9'
				});
				dataArrayOrgan.push({
					title:'',
					colspan:'9'
				});
			}
			$.each(data.zoneColumn,function(index,item){
				dataArrayZone.push({
					title:item.zoneName,
					colspan:item.index
				});
			});
			$.each(data.organColumn,function(index,item){
				dataArrayOrgan.push({
					title:item.organName,
					colspan:item.index
				});
			});
			var dataArrayOrderUnit = [];
			dataArrayOrderUnit.push({
				field:'salerName',
				title:'供应商',
				width:220,
				align:'left'
			});
			dataArrayOrderUnit.push({
				field:'brandName',
				title:'品牌',
				width:80
			});
			dataArrayOrderUnit.push({
				field:'oneLevelCategoryName',
				title:'商品大类',
				width:80
			});
			dataArrayOrderUnit.push({
				field:'itemCode',
				title:'商品编码',
				width:150
			});
			dataArrayOrderUnit.push({
				field:'itemName',
				title:'商品名称',
				width:150,
				align:'left'
			});
			if(report_detail.isShowByDay){
				dataArrayOrderUnit.push({
					field:'orderfromName',
					title:'订货形式',
					width:80
				});
				dataArrayOrderUnit.push({
					field:'yeasName',
					title:'年份',
					width:80
				});
				dataArrayOrderUnit.push({
					field:'purchaseSeasonName',
					title:'季节',
					width:80
				});
				dataArrayOrderUnit.push({
					field:'seasonName',
					title:'产品季节',
					width:80
				});
				dataArrayOrderUnit.push({
					field:'genderName',
					title:'性别',
					width:80
				});
				dataArrayOrderUnit.push({
					field:'twoLevelCategoryName',
					title:'鞋大类',
					width:80
				});
				dataArrayOrderUnit.push({
					field:'threeLevelCategoryName',
					title:'鞋小类',
					width:80
				});
				dataArrayOrderUnit.push({
					field:'sendDate',
					title:'发出日',
					width:120
				});
			}
			dataArrayOrderUnit.push({
				field:'purchasePrice',
				title:'采购价',
				width:80,
				align:'right'
			});
			dataArrayOrderUnit.push({
				field:'factoryPrice',
				title:'厂进价',
				width:80,
				align:'right'
			});
			dataArrayOrderUnit.push({
				field:'totalQty',
				title:'数量',
				width:100
			});
			dataArrayOrderUnit.push({
				field:'sendAmount',
				title:'金额',
				width:100,
				align:'right'
			});
			$.each(orderUnit,function(index,item){
				var v_object = new Object();
				v_object.field = item.orderUnitNo;
				v_object.title = item.orderUnitName;
				v_object.width = 100;
				dataArrayOrderUnit.push(v_object);
			});
			columnsNew[0] = dataArrayZone;
			columnsNew[1] = dataArrayOrgan;
			columnsNew[2] = dataArrayOrderUnit;
			report_detail.selectDg.datagrid({
				url:'',
				columns : columnsNew
			});
		}
	});
};


report_detail.loadResult = function(params) {
	setTimeout(function(){
		params.isTotal = report_detail.isTotal;
		params.isShowByDay = report_detail.isShowByDay;
		report_detail.selectDg.datagrid('options').queryParams= params;
	    report_detail.selectDg.datagrid('options').url= BasePath + '/report/report_detail.json';
	    report_detail.selectDg.datagrid('load');
	},300);
};

//清空
report_detail.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("input[name!=balanceType]").val("");
	common_util.initDate(); 
};

//导出
report_detail.doExport = function(){
	if(report_detail.isTotal){
		common_util.doExport('dtlDataGrid1','/report/export','总部厂商明细汇总对账表',{type:'detail'});
	}else if(report_detail.isShowByDay){
		common_util.doExport('dtlDataGrid2','/report/export','总部厂商明细对账表',{type:'detail'});
	}else{
		common_util.doExport('dtlDataGrid','/report/export','总部厂商明细对账表',{type:'detail'});
	}
}



report_detail.selectDg;
report_detail.isTotal;
report_detail.isShowByDay;

// 初始化
$(function(){
	$("#itemNo").filterbuilder({
	    type:'item',  //新增参数，可以先不调整，使用原url
	    sqlFlag:1,
	    maxRowNum:50,
	    onSave : function(result) { 
	    	var itemObj = $("#itemNo").filterbuilder('getValue');
	    	if (itemObj != null && itemObj.resultType != null && itemObj.resultType == 1){
	    		if(itemObj.codeList.length >0){
	    			$("#itemSql").val(itemObj.codeList);
	    		}else{
	    			$("#itemSql").val("item_no = '00000'");
	    		}
        	}else if(itemObj != null && itemObj.resultType != null && itemObj.resultType == 2){
        		if(itemObj.codeList.length >0){
        			$("#itemSql").val('item_no IN (' + '\'' + itemObj.codeList.split(',').join('\',\'') + '\'' + ')');
	    		}else{
	    			$("#itemSql").val("item_no = '00000'");
	    		}
        	    
        	}else{
        		showWarn("错误：resultType未知");
        	}
        }
    });
	$("#organNo").filterbuilder({
	    type:'organ', //新增参数，可以先不调整，使用原url
	    organFlag: 1,
	    onSave : function(result) { 
	    	var organObj = $("#organNo").filterbuilder('getValue');
	    	$('#organSql').val(common_util.multiFormat(organObj));
	    }
	});
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	report_detail.selectDg = $('#dtlDataGrid');
	report_detail.isTotal = false;
	report_detail.isShowByDay = false;
	$('#dtlTab').tabs({    
	    onSelect:function(title){
	    	if(title == '明细核对'){
	    		report_detail.selectDg = $('#dtlDataGrid');
	    		report_detail.isTotal = false;
	    		report_detail.isShowByDay = false;
	    	}else if(title == '汇总核对'){
	    		report_detail.selectDg = $('#dtlDataGrid1');
	    		report_detail.isTotal = true;
	    		report_detail.isShowByDay = false;
	    	}else{
	    		report_detail.isTotal = false;
	    		report_detail.isShowByDay = true;
	    		report_detail.selectDg = $('#dtlDataGrid2');
	    	}
	    }    
	}); 
	

});
