<div class="easyui-layout"  id ="panelDiv"  data-options="fit:true,plain:true">  
    <div data-options="region:'north',border:false">  									       
  		<input class="easyui-validate"  name="showType"  id="showType"/>	
	</div>	
				
	<div data-options="region:'center',border:false"> 
    	 <div id="category_panel" class="easyui-panel" data-options="fit:true,border:false"  id="categoryDataGridDiv"  data-options="closed:true">				 			    
	       	<@p.datagrid id="categoryDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
	              isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="true"   emptyMsg = "" singleSelect="false"  
	              selectOnCheck="true"   checkOnSelect="true"   
	              showFooter="true"	
		          columnsJsonList="[
		                {field : 't',checkbox:true,hidden : 'true',width : 30,notexport:true,printable:false}, 
		                {field : 'id',hidden : 'true',align:'center',notexport:true,printable:false},  
		                {field : 'categoryNo',hidden:'true',title : '类别',width : 100,align:'center',notexport:true,printable:false},
		                {field : 'brandNo',hidden:'true',title : '品牌编码',width : 100,align:'center',notexport:true,printable:false},
		                {field : 'brandName',title : '品牌部',width : 80,align:'left'},        
		                {field : 'categoryName',title : '大类',width : 80,align:'left'},	
		                {field : 'saleQty',title : '数量',width : 40,align:'right', exportType:'number'},	
						{field : 'saleAmount',title : '销售金额',width : 100,align:'right', exportType:'number'},
						{field : 'prepaymentAmount',title : '预收金额',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'diffAmount',title : '冲销预收款',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'systemDeductAmount',title : '系统扣费',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'balanceDeductAmount',title : '票前费用',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'balanceDiffAmount',title : '结算差异',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'deductAmount',title : '扣费总额',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'billingAmount',title : '开票金额',width : 100,align:'right', exportType:'number'},
				        {field : 'salerNo',hidden:true, title : '公司编码', align:'left',width : 80,printable:false,
				                   editor:{type:'hiddenbox',options:{id:'salerNo_',name:'salerNo'}}},
				        {field : 'salerName', title : '公司-开票方', align:'left',width : 260, halign:'center',printable:false,
				                  	editor:{
				                  		type:'company',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'salerName_',
				                  			name:'salerName',
				                  			inputNoField:'salerNo_',
				                  			required:true
				                  		}
				                  	}
				                  },
				         {field : 'buyerNo',hidden:true, title : '公司编码', align:'left',width : 80,printable:false,
				                   editor:{type:'hiddenbox',options:{id:'buyerNo_',name:'buyerNo'}}},
				        {field : 'buyerName', title : '公司-收票方', align:'left',width : 260,halign:'center',printable:false,
				                   editor:{type:'company',options:{inputWidth:70,id:'buyerName_',name:'buyerName',inputNoField:'buyerNo_',readonly:true}}}
	              ]" 
	              jsonExtend="{
	                    onDblClickRow:function(rowIndex, rowData){
		                   	   categoryEditor.editRow(rowIndex, rowData);	
		                }}"
			 />  			      
	      </div>  
			    	
		  <div id="balancedtl_panel" class="easyui-panel" data-options="fit:true,border:false" data-options="closed:true">		
	         <@p.datagrid id="balanceDtlDataGrid"  loadUrl="" saveUrl=""  defaultColumn="" title="" 
	              isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="true" 
	              showFooter="true"		           
		          columnsJsonList="[
			            {field : 'orderNo',title : '销售订单号',width : 120,align:'left'},				               		
						{field : 'outDate',title : '销售日期',width : 80,align:'left'},	
						{field : 'retailType',hidden:'true',title : '销售类型',width : 100,align:'left',notexport:true},	
						{field : 'orderType',hidden:'true',title : '单据名称',width : 100,align:'left',notexport:true},	
						{field : 'businessType',hidden:'true',title : '订单类型',width : 100,align:'left',notexport:true},	
						{field : 'sizeNo',title : '尺码',width : 40,align:'left'},	
						{field : 'skuNo',hidden:'true',title : '商品SKU',width : 120,align:'left',notexport:true},	
						{field : 'itemCode',title : '商品编码',width : 120,align:'left'},	
						{field : 'itemName',title : '商品名称',width : 130,align:'left'},	
						{field : 'qty',title : '数量',width : 40,align:'right'},
						{field : 'tagPrice',title : '牌价',width : 70,align:'right',exportType:'number'},
						{field : 'tagPriceAmount',title : '牌价额',width : 70,align:'right',exportType:'number'},
					   	{field : 'salePrice',title : '现价',width : 70,align:'right',exportType:'number'},
					   	{field : 'salePriceAmount',title : '现价额',width : 70,align:'right',exportType:'number'},
					   	{field : 'discPrice',hidden:'true',title : '折扣价',width : 70,align:'right',notexport:true},
					   	{field : 'discAmount',hidden:'true',title : '折扣额',width : 70,align:'right',notexport:true},
					   	{field : 'settlePrice',title : '结算价',width : 70,align:'right',exportType:'number'},
					   	{field : 'amount',title : '结算额',width : 70,align:'right',exportType:'number'},
					    {field : 'saleAmount',title : '销售收入',width : 80,align:'right',halign:'center',exportType:'number'},
					    {field : 'discountAmount',title : '扣费',width : 80,align:'right',halign:'center',exportType:'number'},
					  	{field : 'reducePrice',title : '减价',width : 70,align:'right',exportType:'number'},					  
					  	{field : 'prefPrice',title : '促销优惠单价',width : 70,align:'right',exportType:'number'},
					  	{field : 'proNo',title : '促销活动编号',width : 80,align:'left'},
					  	{field : 'proName',title : '促销活动名称',width : 80,align:'left'},
					  	{field : 'discountName',title : '扣率%',width : 40,align:'right'},
					  	
					  	{field : 'launchTypeName',title : '活动来源',width : 60,align:'right',halign:'center'},
						{field : 'discountCode',title : '扣率代码',width : 60,align:'right',halign:'center'},
						{field : 'proStartDate',title : '促销开始日期',width : 80,align:'right',halign:'center'},
						{field : 'proEndDate',title : '促销结束日期',width : 80,align:'right',halign:'center'},
						{field : 'proName',title : '活动描述',width : 150,align:'right',halign:'center'},
							
						{field : 'vipDiscount',title : '会员折数',width : 80,align:'right',halign:'center'},							 
						{field : 'itemDiscount',title : '商品折数',width : 80,align:'right',halign:'center'},
						{field : 'headquarterCost',hidden:'true',title : '总部成本',width : 80,align:'right',halign:'center',notexport:true},
						{field : 'regionCost',title : '地区成本',width : 80,align:'right',halign:'center'},
						{field : 'unitCost',title : '单位成本',width : 80,align:'right',halign:'center'},
								
					  	{field : 'vipDiscount',title : '会员折数',width : 50,align:'right'},
					  	{field : 'baseScore',hidden:'true',title : '基本积分',width : 50,align:'right',notexport:true},
					  	{field : 'proScore',hidden:'true',title : '整单分摊积分',width : 50,align:'right',notexport:true},
					  	{field : 'affectFlag',hidden:'true',title : '是否影响销售',width : 100,align:'center',notexport:true},
					  	{field : 'itemDiscount',title : '商品折数',width : 50,align:'right'},
					  	{field : 'remark',title : '备注',width : 100,align:'center'}			           								               
                 ]" 
	        />  
         </div> 	   
			     
       	<div id="promotions_panel" class="easyui-panel" data-options="fit:true,border:false" data-options="closed:true">			   
	      	<@p.datagrid id="promotionsDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
		              showFooter="true"	  rownumbers="true" 
			          columnsJsonList="[
		                {field : 't',checkbox:true,hidden : 'true',width : 30,notexport:true,printable:false},    
		                {field : 'seqNo',title : '序列',hidden : 'true',width : 30,notexport:true,}, 
		                {field : 'brandNo',hidden:'true',title : '品牌编码',width : 100,align:'center',notexport:true,printable:false},
		                {field : 'brandName',title : '品牌部',width : 80,align:'left'},  
		                {field : 'billingCode',title : '结算码',width : 80,align:'right'},
						{field : 'proNo',title : '活动编号',width : 70, align : 'left',printable:false,notexport:true},	        	      	
						{field : 'proName',title : '活动名称',width : 140,align:'left'},
						{field : 'discountCode',title : '活动代码',width : 70, align : 'left',printable:false},
		                {field : 'proStartDate',title : '活动起始日',width : 80,align:'left',printable:false},	
		                {field : 'proEndDate',title : '活动终止日',width : 80,align:'left',printable:false},	
						{field : 'saleAmount',title : '销售收入',width : 80,align:'right',exportType:'number'},
						{field : 'discountName',title : '扣率名称',width : 100,align:'left'},
						{field : 'discountN',title : '扣率%',width : 80,align:'right'},
						{field : 'deductAmount',title : '扣费',width : 80,align:'right',exportType:'number'},
						{field : 'systemBillingAmount',title:'系统开票金额',width : 100,align:'right',exportType:'number'},	
						{field : 'actualAmount',title : '票前费用',width : 80,hidden:'true',align:'right',notexport:true,exportType:'number'},
						{field : 'balanceDiffAmount',title : '差异总额',width : 80,hidden:'true',align:'right',notexport:true,exportType:'number'},
						{field : 'billingAmount',title:'开票金额',width : 100,align:'right',notexport:true,exportType:'number',hidden:'true',printable:false}						     
	                 ]" 
		        />  			           
	      </div> 
			      
       	  <div id="paymentmethod_panel" class="easyui-panel" data-options="fit:true,border:false" data-options="closed:true">   			    
		      <@p.datagrid id="paymentMethodDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
		              isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="true" 
	              	  showFooter="true"	
			          columnsJsonList="[
		                {field : 't',checkbox:true,hidden : 'true',width : 30,notexport:true},           
		                {field : 'payCode',hidden : 'true',title : '收款编码',width : 100,align:'left',notexport:true},	
		                {field : 'payName',title : '收款名称',width : 80,align:'left'},	
		                {field : 'amount',title : '销售收入',width : 80,align:'right',exportType:'number'},	
						{field : 'zoneNo',hidden:'true',title : '开票金额',width : 100,align:'right',notexport:true}						               
	                 ]" 				             
		        />  
	      </div> 
	      
	      <div id="billingcode_panel" class="easyui-panel" data-options="fit:true,border:false" data-options="closed:true">			   
	      	<@p.datagrid id="billingCodeDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
		              showFooter="true"	  rownumbers="true" 
			          columnsJsonList="[
		                {field : 't',checkbox:true,hidden : 'true',width : 30,notexport:true,printable:false},    
		                {field : 'billingCode',title : '结算码',width : 80,align:'right'},
						{field : 'saleAmount',title : '销售收入',width : 80,align:'right',exportType:'number'},
						{field : 'discount',title : '扣率%',width : 80,align:'right'},
						{field : 'discountName',title : '扣率名称',width : 100,align:'left'},
						{field : 'deductAmount',title : '扣费',width : 80,align:'right',exportType:'number'},
	                 ]" 
		        />  			           
	      </div> 	      
	      
	      <div id="category3_panel" class="easyui-panel" data-options="fit:true,border:false"  id="category3DataGridDiv"  data-options="closed:true">				 			    
	       	<@p.datagrid id="category3DataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
	              isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="true"   emptyMsg = "" singleSelect="false"  
	              selectOnCheck="true"   checkOnSelect="true"   
	              showFooter="true"	
		          columnsJsonList="[
		                {field : 't',checkbox:true,hidden : 'true',width : 30,notexport:true,printable:false}, 
		                {field : 'id',hidden : 'true',align:'center',notexport:true,printable:false},  
		                {field : 'categoryNo',hidden:'true',title : '类别',width : 100,align:'center',notexport:true,printable:false},
		                {field : 'brandNo',hidden:'true',title : '品牌编码',width : 100,align:'center',notexport:true,printable:false},
		                {field : 'categoryName1',title : '大类',width : 80,align:'left'},        
		                {field : 'categoryName2',title : '中类',width : 80,align:'left'},	
		                {field : 'categoryName3',title : '小类',width : 80,align:'left'},	
		                
		                {field : 'saleQty',title : '数量',width : 40,align:'right', exportType:'number'},	
						{field : 'saleAmount',title : '销售金额',width : 100,align:'right', exportType:'number'},
						{field : 'prepaymentAmount',title : '预收金额',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'diffAmount',title : '冲销预收款',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'systemDeductAmount',title : '系统扣费',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'balanceDeductAmount',title : '票前费用',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'balanceDiffAmount',title : '结算差异',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						{field : 'deductAmount',title : '扣费总额',width : 100,align:'right', exportType:'number',printable:false,notexport:true,hidden:'true'},
						
						{field : 'actualBillingAmount',title : '开票金额',width : 100,align:'right', exportType:'number'}				        
	              ]" 
	              jsonExtend="{
	                    onDblClickRow:function(rowIndex, rowData){
		                   	   categoryEditor.editRow(rowIndex, rowData);	
		                }}"
			 />  			      
	      </div>  
 	</div> 
</div>		                 