<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" class="toolbar-region">
			  <table width="100%">
				<tr class="datagrid-toolbar">
					<td>
					 <@p.toolbar id="itemSearchTool" listData=[ 
		   				 {"id":"dgSelectorSearchBtn","title":"查询","iconCls":"icon-search","type":0},
		   				 {"id":"dgSelectorClearBtn","title":"清空","iconCls":"icon-remove","type":0}
					  ]/>
					</td>
				</tr>
			</table>	
		</div>
		<div  data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true" id="subLayout">
				<div data-options="region:'north',border:false" >
					<div class="search-div">
							 <form name="dialog_SarchForm" id="dialog_SarchForm" method="post">
							 	<table class="form-tb">
								     <col width="80" />
					                 <col />
					                 <col width="80" />
					                 <col />
					                 <col width="80" />
					                 <tbody>
								    		<tr>
						       		 			<th>客户编码：</th>
						       		 			<td><input class="easyui-validatebox ipt" name="clientNo" id="clientNoCondition"/></td>
						       		 			<th>客户名称：</th>
						       		 			<td><input class="easyui-validatebox ipt"  name="clientName" id="clientNameCondition"/></td>
						       		 			<th>状态：</th>
												<td>
													<input class="easyui-combobox ipt" name="status" id="status"
													data-options="valueField: 'label',inputWidth:'80px',textField: 'value',
														data: [{label: '1',value: '首选'},{label: '0',value: '备选'}]" />
												</td>	
						       		 		</tr>
								    </tbody>
							   </table>
							</form>
					</div>
			</div>
			<div data-options="region:'center',border:false">
				<@p.datagrid id="dialog_SearchDataGrid"  loadUrl="" saveUrl=""  defaultColumn="" 
				                   isHasToolBar="false" height="415" divToolbar="" onClickRowEdit="false" pagination="true"
				                   rownumbers="true" singleSelect = "true"
					               columnsJsonList="[
					               	  {field : 'clientNo',title : '客户编码',width : 150, halign : 'center', align : 'left'},
					        	      {field : 'clientName',title : '客户名称',width : 200, halign : 'center', align : 'left'},
					        	      {field : 'status',title : '状态',width : 80, halign : 'center', align : 'left',
					        	       formatter: shopGroup.statusformatter, 
					        	      	editor:{
				                  		type:'combobox',
				                  		options:{
				                  			id:'status',
				                  			data: [{'value':'0', 'text': '备选'}, {'value':'1', 'text':'首选'}], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  		}
					        	      },
					        	     {field : 'invoiceName',title : '开票名称',width : 150,align:'left',halign:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },
					                 ]" 
								/>
			</div>
		</div>
	</div>
</div>
   