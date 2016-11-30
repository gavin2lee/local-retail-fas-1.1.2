<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="payTab" data-options="region:'center',border:false">
        <@p.subdatagrid id="buyPayDataGrid"  
       		 isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
			 {field : 'billNo', title : '供应商', width : 120},
		     {field : 'billDate', title : '单据编码', width : 120},
		     {field : 'statusName', title : '发货日期', width : 100},
		     {field : 'allAmount', title : '验收日期', width : 100},
	    	 {field : 'createUser',title : '品牌',width : 100},
			 {field : 'createTime',title : '大类',width : 150},
			 {field : 'auditor',title : '商品编码',width : 100},
			 {field : 'auditTime',title : '商品名称',width : 150},
		     {field : 'remark', title : '牌价', width : 180},
		     {field : 'statusName', title : '采购价', width : 100},
		     {field : 'allAmount', title : '数量', width : 100},
	    	 {field : 'createUser',title : '采购金额',width : 100},
			 {field : 'createTime',title : '币别',width : 150},
			 {field : 'auditor',title : '本位币',width : 100},
			 {field : 'auditTime',title : '汇率',width : 150},
		     {field : 'remark', title : '采购本位币金额', width : 180},
		     {field : 'remark', title : '关税率', width : 180},
		     {field : 'statusName', title : '增值税率', width : 100},
		     {field : 'allAmount', title : '费用', width : 100},
	    	 {field : 'createUser',title : '总金额',width : 100},
			 {field : 'createTime',title : '地区金额',width : 150},
			 {field : 'auditor',title : '地区价',width : 100},
			 {field : 'auditTime',title : '公司',width : 150},
		     {field : 'remark', title : '货管单位', width : 180},
		     {field : 'allAmount', title : '管理城市', width : 100},
	    	 {field : 'createUser',title : '地区公司',width : 100},
			 {field : 'createTime',title : '采购订单号',width : 150},
			 {field : 'auditor',title : '二级大类',width : 100},
			 {field : 'auditTime',title : '三级大类',width : 150},
		     {field : 'remark', title : '结算单号', width : 180}]"	
		    loadSubGridUrl="/bill_ask_payment_dtl/list.json"
            subPagination="false"
            subGridColumnsJsonList="[
				{field:'settleMethodNo',title:'结算方式',width:120,formatter:function(value,index,row){
					return buyBalance.settleMethodData[value];}
				},		
				{field:'amount',title:'金额',width:120},
				{field:'otherBank',title:'开户行',width:150},
				{field:'otherBankAccount',title:'对方帐号',width:150},
				{field:'remark',title:'备注',width:180}]"	
			/>
		/>
	</div>
</div>