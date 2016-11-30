<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" id="dtlDGTab">
		<@p.datagrid id="refDataGrid"  fit="true" fitColumns="false" emptyMsg=""
			isHasToolBar="false" divToolbar=""    pageSize="10" 
			onClickRowAuto="false" 
			columnsJsonList="[
				{field : 'billNo',title : '单据编号',width : 150},
				{field : 'balanceTypeName',title : '结算类型',width : 120},
				{field : 'brandUnitName',title : '品牌部',width : 100},	
				{field : 'categoryName',title : '商品大类',width : 100},			
				{field : 'balanceStartDate',title : '结算开始日期',width : 120},
				{field : 'balanceEndDate',title : '结算结束日期',width : 120},
				{field : 'outQty',title : '入库数量',width : 100,formatter: function(value,row,index){
					if(row.outQty){
						return row.outQty;
					}
					return row.entryQty;
				}},
				{field : 'outAmount',title : '入库金额',width : 100,formatter: function(value,row,index){
					if(row.outAmount && row.outAmount!=0){
						return row.outAmount;
					}
					return row.entryAmount;
				}},
				{field : 'returnQty',title : '退货数量',width : 100},
				{field : 'returnAmount',title : '退货金额',width : 100},
				{field : 'customReturnQty',title : '客残数量',width : 100},
				{field : 'customReturnAmount',title : '客残金额',width : 100},
				{field : 'deductionAmount',title : '其他扣项',width : 100},
				{field : 'balanceQty',title : '应付数量',width : 100},
				{field : 'balanceAmount',title : '应付金额',width : 100}]" 	
			 />
	</div>
</div>