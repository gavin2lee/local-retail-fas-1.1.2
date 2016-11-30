<div class="easyui-layout" data-options="fit:true,border:false">
	<#--按钮-->
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="etoolbar" listData=[
				 {"id":"search_btn","title":"查询","iconCls":"icon-search","action":"currency_management.searchExchange()","type":0},
	             {"id":"remove_btn","title":"清空","iconCls":"icon-empty","action":"currency_management.clearExchange()", "type":0},
	             {"id":"add_btn","title":"新增","iconCls":"icon-add","action":"currency_management.addExchange()","type":1},
	             {"id":"edit_btn","title":"修改","iconCls":"icon-edit","action":"currency_management.showEditDialog()","type":2},
	             {"id":"del_btn","title":"删除","iconCls":"icon-del","action":"currency_management.delExchange()","type":3}
	             {"id":"btn-enable","title":"启用","iconCls":"icon-lock","action":"currency_management.doEnable()","type":27}
	             {"id":"btn-unable","title":"停用","iconCls":"icon-unlock","action":"currency_management.doUnable()","type":100}
	           ]
			/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<div data-options="region:'north',border:false">
				<#--搜索start-->
				<div class="search-div">
					     <form name="searchForm" id="searchForm" method="post">
			       		 	<table class="form-tb">
			       		 		<col width="100" />
							    <col />
							    <col width="100" />
							    <col />
							    <col width="100" />
							    <col />
							    <col width="100" />
							    <col />
							    <tbody>
			       		 		<tr>
			       		 			<th>汇率编码：</th>
			       		 			<td>
			       		 				<input class="easyui-validatebox ipt" name="exchangeRateCode" id="exchangeRateCodeCondition"/>
			       		 			</td>
			       		 			<th>公司名称：</th>
						       		<td>
						       			<input class="easyui-company ipt" name="companyName" id="companyName"
										data-options="inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160,isDefaultData:false"/>
										<input type="hidden" name="companyNo" id="companyNo"/>
						       		</td>
			       		 		</tr>
			       		 		</tbody>
			       		 	</table>
						</form>
				</div>
			</div>
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="exchangeDG"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			               checkOnSelect="true"  rownumbers="true" 
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'exchangeRateCode',title : '汇率编码',width : 80,align:'left',halign:'center'},
				                  {field : 'sourceCurrencyName',title : '源货币',width : 80,align:'center'},
				                  {field : 'targetCurrencyName',title : '目标货币',width : 80,align:'center'},
				                  {field : 'conversionFactor',title : '转换系数',width : 80,align:'right',halign:'center',
				                  		formatter:function(v){
				                  			return v.toFixed(4);
				                  		}
				                  	},
				                  {field : 'effectiveDate',title : '生效日期',width : 100,align:'center'},
				                  {field : 'companyNo',title : '公司编码',width : 100,align:'left',halign:'center'},
				                  {field : 'companyName',title : '公司名称',width : 200,align:'left',halign:'center'},
				                  {field : 'statusName',title : '启用状态',width : 80,align:'center',halign:'center'},
				                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
				                 ]" 
					        />
			</div>
		 </div>
	</div>
	
	
	<div id="formPanel" class="easyui-dialog" data-options="closed:true,width:350,height:300" > 
		<form  id="exchangeForm" method="post">
			<input type="hidden" id="exchangeId" name="id" />
			<table cellpadding="1" cellspacing="1" class="form-tb">
				<col width="100" />
			    <col />
			    <col width="100" />
			    <col />
			    <tbody>
				<tr>
					<th><span class="ui-color-red">*</span>汇率编码：</th>
					<td>
						<input class="easyui-validatebox ipt" name="exchangeRateCode" id="exchangeRateCodeId"  data-options="required:true,validType:['unNormalData','engNum','maxLength[5]']" />  
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>公司名称：</th>
					<td colspan="3">
						<input class="easyui-validatebox ipt"  name="companyName" id="companyNameId"  style="width:400px"/>
						<input type="hidden"  name="companyNo" id="companyNoId"  />
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>源&nbsp;&nbsp;货&nbsp;&nbsp;币：</th>
					<td>
						<input class="easyui-validatebox  ipt"  name="sourceCurrencyName" id="sourceCurrencyNameId" data-options="required:true" />
						<input type="hidden"  name="sourceCurrency" id="sourceCurrencyId"  />
					</td>
					<th><span class="ui-color-red">*</span>目标货币：</th>
					<td>
						<input class="easyui-validatebox  ipt"  name="targetCurrencyName" id="targetCurrencyNameId" data-options="required:true" />
						<input type="hidden"  name="targetCurrency" id="targetCurrencyId"  />
					</td>
				</tr>
				<tr>
					<th><span class="ui-color-red">*</span>转换系数：</th>
					<td>
						<input class="easyui-numberbox ipt"  name="conversionFactor" id="conversionFactorId" data-options="required:true,min:0,precision:4,validType:'maxLength[10]'"/>
					</td>
					<th><span class="ui-color-red">*</span>生效日期：</th>
					<td>
						<input class="easyui-datebox ipt"  name="effectiveDate" id="effectiveDateId"  data-options="required:true" />
					</td>
				</tr>
			</tbody>
			</table>
		</form>
	</div>
</div>
