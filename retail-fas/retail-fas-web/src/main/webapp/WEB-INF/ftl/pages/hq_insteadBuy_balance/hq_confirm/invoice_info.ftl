<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
		    <@p.subdatagrid id="invoiceInfoDtl" 
			isHasToolBar="false" height="502"  onClickRowEdit="false" 
			columnsJsonList="[
		    {field : 'billNo', title : '单据编码', width : 150,align:'left',halign:'center'},
		    {field : 'billDate', title : '单据日期', width : 100},
		    {field : 'statusName', title : '单据状态', width : 100},
		    {field : 'amount', title : '发票金额', width : 100,align:'right',halign:'center'},
		    {field : 'paymentNo',title : '付款单号',width : 150,align:'left',halign:'center'},
			{field : 'paymentAmount',title : '付款金额',width : 100,align:'right',halign:'center'},
	    	{field : 'createUser',title : '创建人',width : 100},
			{field : 'createTime',title : '创建日期',width : 150},
			{field : 'auditor',title : '审核人',width : 100},
			{field : 'auditTime',title : '审核日期',width : 150},
		    {field : 'remark', title : '备注', width : 180,align:'left',halign:'center'}]"	
		    loadSubGridUrl="/bill_invoice_dtl/list.json"
            subPagination="false"
            subGridColumnsJsonList="[
				{field:'invoiceCode',title:'发票代码',width:100,align:'left',halign:'center'},
				{field:'invoiceNo',title:'发票号码',width:100,align:'left',halign:'center'},
				{field:'invoiceDate',title:'发票日期',width:100},
				{field:'invoiceAmount',title:'发票金额',width:100,align:'right',halign:'center'},
				{field:'taxRate',title:'税率',width:80,align:'right',halign:'center'},
				{field:'noTaxAmount',title:'不含税金额',width:100,align:'right',halign:'center'},
				{field:'taxAmount',title:'税额',width:100,align:'right',halign:'center'},
				{field:'qty',title:'数量',width:80,align:'right',halign:'center'},
				{field:'price',title:'单价',width:100,align:'right',halign:'center'},
				{field : 'categoryName',title:'财务大类',width:100}]"
			/>
 </div>
</div>
			