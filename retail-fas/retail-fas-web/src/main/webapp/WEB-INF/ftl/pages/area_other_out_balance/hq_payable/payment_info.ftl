<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
		  <@p.subdatagrid id="receivableInfoDtl"  
       		 isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
			 {field : 'billNo', title : '单据编码', width : 150,align:'left',halign:'center'},
		     {field : 'billDate', title : '单据日期', width : 100},
		     {field : 'statusName', title : '单据状态', width : 100},
		     {field : 'allAmount', title : '请款金额', width : 100,align:'right',halign:'center'},
	    	 {field : 'createUser',title : '创建人',width : 100},
			 {field : 'createTime',title : '创建日期',width : 150},
			 {field : 'auditor',title : '审核人',width : 100},
			 {field : 'auditTime',title : '审核日期',width : 150},
		     {field : 'remark', title : '备注', width : 180,align:'left',halign:'center'}]"	
		    loadSubGridUrl="/bill_ask_payment_dtl/list.json"
            subPagination="false"
            subGridColumnsJsonList="[
				{field:'settleMethodNo',title:'结算方式',width:80,formatter:function(value,index,row){
					return  datas[value];}
				},		
				{field:'amount',title:'金额',width:100,align:'right',halign:'center'},
				{field:'otherBank',title:'开户行',width:100,align:'left',halign:'center'},
				{field:'otherBankAccount',title:'对方帐号',width:150},
				{field:'remark',title:'备注',width:180,align:'left',halign:'center'}]"	
			/>
		/>
	</div>	
</div>	