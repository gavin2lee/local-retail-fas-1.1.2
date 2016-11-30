<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.toolbar id="diffbacktoolbar" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "shopBalanceDiffBackEditor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "shopBalanceDiffBackEditor.deleteRow()","type":0}
		           ]
				/>
	        </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="balanceDiffBackDGDiv">
		    	<@p.datagrid id="balanceDiffBackDG"  loadUrl="/bill_shop_balance_back/lstBalanceBack?billNo=${diffDtlId}&diffBillNo=${diffBillNo}&balanceNo=${balanceNo}" saveUrl=""   defaultColumn="" 
			               isHasToolBar="false" onClickRowEdit="false" pagination="false" selectOnCheck="true" 
			               checkOnSelect="true" rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'backDate',title : '回款日期',width:80,align:'center'},
				                  {field : 'backAmount',title : '回款金额',width : 80,align:'right',								
				                  		editor:{
				                  			type:'numberbox',
				                  			options:{
				                  				required:true,
				                  				precision:2,
				                  				validType:['maxLength[12]']
				                  			}
				                  		}
				                  },
				                   {field : 'remark',title : '备注',width : 160,align:'left',								
				                  		editor:{
				                  			type:'text',
				                  			options:{
				                  			
				                  			}
				                  		}
				                  },				                  
			                    {field : 'createUser',title : '建档人',width : 60,align:'left'}, 
				                {field : 'createTime',title : '建档时间',width : 130,align:'center'},
				                {field : 'updateUser',title : '修改人',width : 60,align:'left'}, 
				                {field : 'updateTime',title : '修改时间',width : 130,align:'center'}	
				                  ]" 
					          jsonExtend='{
		                           onDblClickRow:function(rowIndex, rowData){
		                               //双击方法
				                   	  shopBalanceDiffBackEditor.editRow(rowIndex, rowData);
				                   }
			         }'/>
			</div>
		 </div>
	</div>
</div>