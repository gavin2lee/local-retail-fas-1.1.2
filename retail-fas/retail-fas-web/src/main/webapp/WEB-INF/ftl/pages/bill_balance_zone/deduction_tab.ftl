<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="deductionTab" data-options="region:'center',border:false">
		<@p.datagrid id="deductionDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
				{field : 'brandName',title : '品牌',width : 100, formatter: function(value,row,index){
						if(row.buyerName == '合计'){
							return '合计';
						}
						return value;
				}},		
				{field : 'categoryName',title : '大类',width : 100},	
				{field : 'type',title : '扣项类型',width : 100,formatter: function(value,row,index){
	                  		if(row.type == '0') {
	                  			return '其他扣项';
	                  		}else if (row.type == '1') {
	                  			return '返利';
	                  		}else if (row.type == '2') {
	                  			return '其他费用';
	                  		}
				}},	
				{field : 'deductionDate',title : '日期',width : 120},
				{field : 'sendAmount',title : '扣款金额',width : 120, align : 'right', formatter: function(value,row,index){
					return (row.deductionAmount).toFixed(2);
				}},							
				{field : 'remark',title : '备注',width : 200}]" 
			/>
	</div>
</div>