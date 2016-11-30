<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="deductionTab" data-options="region:'center',border:false">
		<@p.datagrid id="peDeductionDataGrid"  rownumbers="true" showFooter = "true"
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
				{field : 'deductionDate',title : '日期',width : 120},			
				{field : 'areaBuyerName',title : '地区公司',width : 150},
				{field : 'deductionName',title : '扣项名称',width : 150,align:'left'},
				{field : 'deductionAmount',title : '金额',width : 100,align:'right'},		
				{field : 'brandName',title : '品牌',width : 100},				
				{field : 'categoryName',title : '大类',width : 100},
				{field : 'createUser',title : '创建人',width : 80},
				{field : 'createTime', title : '创建日期', width : 150}]" 
			/>
	</div>
</div>