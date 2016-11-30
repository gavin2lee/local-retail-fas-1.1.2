<div data-options="region:'center',border:false">
		<@p.datagrid id="balanceZoneDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20"  rownumbers="true" 
			columnsJsonList="[
				{field:'ck',checkbox:true,notexport:true},
				{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
				{field : 'statusName',title : '单据状态',width : 100,align:'center',halign:'center'},
				{field : 'invoiceApplyNo',title : '开票申请单号',width : 150,align:'center',halign:'center',formatter: function(value,row,index){
					  return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AI-开票申请');
				}},
				{field : 'salerName',title : '公司名称',width : 180,align:'left',halign:'center'},
				{field : 'salerNo',title : '公司编码',width : 80,align:'left',halign:'center'},
				{field : 'buyerName',title : '客户名称',width : 180,align:'left',halign:'center'},
				{field : 'buyerNo',title : '客户编码',width : 80,align:'left',halign:'center'},
				{field : 'balanceStartDate',title : '结算开始时间',width : 100,align:'center',halign:'center'},
				{field : 'balanceEndDate',title : '结算结束时间',width : 100,align:'center',halign:'center'},
				{field : 'organNameFrom',title : '管理城市',width : 80,align:'center',halign:'center'},
				{field : 'brandUnitName',title : '品牌部',width : 80,align:'center',halign:'center'},
				{field : 'outAmount',title : '出库金额',width : 100,align:'right',halign:'center'},
				{field : 'outQty',title : '出库数量',width : 100,align:'right',halign:'center'},
				{field : 'deductionAmount',title : '其他扣项',width : 100,align:'right',halign:'center'},
				{field : 'rebateAmount',title : '返利金额',width : 100,align:'right',halign:'center'},
				{field : 'balanceAmount',title : '应收金额',width : 100,align:'right',halign:'center'},
				{field : 'createUser',title : '制单人',width : 80,align:'center',halign:'center'},
				{field : 'createTime',title : '制单日期',width : 150,align:'center',halign:'center',sortField:'create_time',sortable:true},
				{field : 'auditor',title : '审核人',width : 80,align:'center',halign:'center'},
				{field : 'auditTime',title : '审核日期',width : 150,align:'center',halign:'center'},
				{field : 'billName',title : '单据名称',width : 100,align:'left',halign:'center'},
				{field : 'remark',title : '备注',width : 200,align:'left',halign:'center'}
			]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				bill.loadDetail(rowIndex, rowData);
		}}'/>
	</div>