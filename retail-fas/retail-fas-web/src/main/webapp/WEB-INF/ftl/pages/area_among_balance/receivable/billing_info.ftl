<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
		  <@p.datagrid id="invoiceInfoDtl" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  rownumbers="true"
			columnsJsonList="[
				{field : 'billNo',title : '申请单号',align:'left',width : 150,halign:'center'},
				{field : 'statusName',title : '单据状态',align:'center',width : 100,halign:'center'},
				{field : 'invoiceApplyDate',title : '申请日期',align:'center',width : 100,halign:'center'},
				{field : 'amount',title : '金额',align:'right',width : 100,halign:'center'},
				{field : 'remark',title : '备注',align:'center',width : 200,halign:'center'}
		    ]"	
			/>
 </div>
</div>
			