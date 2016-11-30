<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="invoiceTab" data-options="region:'center',border:false">
		<@p.subdatagrid id="buyInvoiceDataGrid" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
		    {field : 'billNo', title : '单据编码', width : 120},
		    {field : 'billDate', title : '单据日期', width : 120},
		    {field : 'statusName', title : '单据状态', width : 120},
		    {field : 'amount', title : '发票金额', width : 120},
		    {field : 'paymentNo',title : '付款单号',width : 120},
			{field : 'paymentAmount',title : '付款金额',width : 120},
	    	{field : 'createUser',title : '创建人',width : 100},
			{field : 'createTime',title : '创建日期',width : 150},
			{field : 'auditor',title : '审核人',width : 100},
			{field : 'auditTime',title : '审核日期',width : 150},
		    {field : 'remark', title : '备注', width : 180}]"	
		    loadSubGridUrl="/bill_invoice_dtl/list.json"
            subPagination="false"
            subGridColumnsJsonList="[
				{field:'invoiceCode',title:'发票代码',width:120},
				{field:'invoiceNo',title:'发票号码',width:120},
				{field:'invoiceDate',title:'发票日期',width:120},
				{field:'invoiceAmount',title:'发票金额',width:120},
				{field:'taxRate',title:'税率',width:100,},
				{field:'noTaxAmount',title:'不含税金额',width:120},
				{field:'taxAmount',title:'税额',width:120},
				{field:'qty',title:'数量',width:100},
				{field:'price',title:'单价',width:100},
				{field : 'brandName',title:'品牌',width:100},
				{field : 'categoryName',title:'财务大类',width:100}]"
			/>
	</div>
</div>