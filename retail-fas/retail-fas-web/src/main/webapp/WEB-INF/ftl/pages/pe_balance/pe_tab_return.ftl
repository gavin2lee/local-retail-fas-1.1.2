<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="deductionTab" data-options="region:'center',border:false">
	<@p.datagrid id="peAdjustDataGrid" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
		    {field :'billNo', title : '单据编码', width : 150},
		    {field :'purchaseDate', title : '单据日期', width : 120},
			{field:'itemCode',title:'商品编码',width:150},
			{field:'itemName',title:'商品名称',width:180},
			{field:'brandName',title:'品牌',width:120},
			{field:'supplierAmount',title:'厂商额',width:120},
			{field:'referAmount',title:'中间额',width:100,},
			{field:'amount',title:'地区额',width:120},
			{field:'remark',title:'备注',width:220},
			{field:'supplierName', title : '供应商', width : 150},
		    {field:'salerName', title : '总部公司', width : 150},
		    {field:'buyerName', title : '地区公司', width : 150},
			{field:'createUser',title : '创建人',width : 80},
			{field:'createTime', title : '创建日期', width : 150}]"
			/>
	</div>
</div>