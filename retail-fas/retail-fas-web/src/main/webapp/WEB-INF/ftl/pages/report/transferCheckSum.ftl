<div class="easyui-layout" data-options="fit:true,border:false">
	<#--按钮-->
	<div data-options="region:'north',border:false" class="toolbar-region">
	  	<@p.toolbar id="subToolbar"  listData=[
	    	{"title":"查询","iconCls":"icon-search","action":"transferingCheck.searchSubInfo()", "type":0},
	        {"title":"清空","iconCls":"icon-empty","action":"transferingCheck.clearSubForm()", "type":0},
	        {"title":"导出","iconCls":"icon-export","action":"transferingCheck.exportTotal()", "type":4}
	     ]/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="mainLayout">
			<div data-options="region:'north',border:false">
				<#--搜索start-->
				<div class="search-div">
					 <form name="searchForm" id="subForm" method="post">
					 	<input type="hidden" name="type" id="typeFlag" "/>
						<table class="form-tb">
		            	    <col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		                    <tbody>
		           				<tr>	
		           				    <th><span class="ui-color-red">*</span>前月起始：</th>
									<td>
										<input class="easyui-datebox ipt"  name="lastDateStart" id="lastDateStartCond" 
										data-options="required:true,maxDate:'currentDateStartCond',width:130"/>
									</td>
									<th><span class="ui-color-red">*</span>本月区间：</th>
									<td>
										<input class="easyui-datebox ipt"  name="currentDateStart" id="currentDateStartCond" 
										data-options="required:true,maxDate:'currentDateEndCond',width:130"/>
									</td>
									<th>— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input class="easyui-datebox ipt" name="currentDateEnd" id="currentDateEndCond" 
										data-options="required:true,minDate:'currentDateStartCond',width:130"/>
									</td>
									<th><span class="ui-color-red">*</span>下月终止：</th>
									<td>
										<input class="easyui-datebox ipt" name="nextDateEnd" id="nextDateEndCond" 
										data-options="required:true,minDate:'currentDateEndCond',width:130"/>
									</td>
								</tr>
									<th>收方公司：</th>
									<td>
										<input class="easyui-company ipt"  name="buyerName" id="buyerNameCond" 
										data-options="inputNoField:'buyerNoCond',inputNameField:'buyerNameCond',isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoCond"/>
									</td>
								    <th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
		                    		<td>
		                    			<input class="easyui-brand ipt" name="brandName" id="brandNameCond" 
		                    			data-options="multiple:true,inputNoField:'brandNoCond',inputNameField:'brandNameCond'"/>
		                    			<input type="hidden" name="brandNo" id="brandNoCond"/>
		                    		</td>
									<th>收方管理城市： </th>
									<td>
										<input class="easyui-organ"    name="organName" id="organNameCond"
									    data-options="inputNoField:'organNoCond',inputNameField:'organNameCond',multiple:true"/>
								        <input type="hidden" name="organNo" id="organNoCond"/>
								    </td>
									<th>收方货管单位：</th>
									<td>
										<input class="easyui-orderUnit  ipt" name="orderUnitName" id="orderUnitNameCond"
										data-options="inputNoField:'orderUnitNoCond',inputNameField:'orderUnitNameCond',multiple:true" />
										<input type="hidden" name="orderUnitNo" id="orderUnitNoCond"/>
									</td>
								</tr>
								<tr>
								    <th>发方公司：</th>
									<td>
										<input class="easyui-salerCompany ipt"  name="salerName" id="salerNameCond" 
										data-options="inputNoField:'salerNoCond',inputNameField:'salerNameCond'"/>
										<input type="hidden" name="salerNo" id="salerNoCond"/>
									</td>
								    <th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th> 
									<td>
										<input class="easyui-categorybox ipt " name="categoryNo" id="categoryNoCond" data-options="multiple:true,width:140"/>
									</td>
									<th>发方管理城市： </th>
									<td>
									    <input class="easyui-organ"   name="organNameFrom" id="organNameFormCond"
									   	data-options="inputNoField:'organNoFromCond',inputNameField:'organNameFormCond',multiple:true"/>
								        <input type="hidden" name="organNoFrom" id="organNoFromCond"/>
								    </td>
								    <th>发方货管单位：</th>
									<td>
										<input class="easyui-orderUnit" id="orderUnitFromNameCond" name="orderUnitNameFrom"   
										data-options="inputNoField:'orderUnitNoFromCond',inputNameField:'orderUnitFromNameCond',multiple:true"/> 
										<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromCond"/>
									</td>
								</tr>
								<tr>
									<th>结算类型：</th> 
									<td>
										<input class="easyui-validatebox ipt" name="balanceTypes" id="balanceTypeCond" data-options="multiple:true,width:140"/>
									</td>
									<th>订货形式：</th>
									<td>
										<input class="easyui-orderFormBox" name='orderForm' data-options="width:140"/>
									</td>
									<th>单价来源：</th>
									<#if zoneFlag=='no'> 
									<td>
										<input class="easyui-validatebox ipt" name="priceFrom" id="priceFromCond" data-options="width:140"/>
									</td>
									<#elseif zoneFlag=='yes'>
									<td>
										<input class="easyui-combobox ipt" name="priceFrom" id="priceFromCondId" 
										data-options="valueField:'value',textField:'text',data:transferingCheck.sendaPriceFromType,
										edittable:false,width:140" />
									</td>
									</#if>
									<th><span class="l-btn-text icon-xq l-btn-icon-left" id="gatherTypeId">tip 1</span></th>
									<td></td>
								</tr>
		           				</tbody>
		                 	</table> 	 
					</form>
				</div>
			</div>
			<#--列表-->
	        <div data-options="region:'center',border:false">
			    <@p.datagrid id="dtlDataGridTotal" loadUrl="" saveUrl=""  defaultColumn=""  
	      			 isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"  
	      			 pagination="true" rownumbers="true" singleSelect="false"  showFooter="true" pageSize="20"
		             columnsJsonList="[
		             	{title : '收货方',colspan:'4'},
	                  	{title : '发货方',colspan:'4'},
	                  	{title : '',colspan:'1'},
						{title : '本月发出',colspan:'2'},
						{title : '本月发本月收',colspan:'2'},
						{title : '本月发下月收',colspan:'2'},
						{title : '本月发未收',colspan:'2'},
						{title : '本月差异',colspan:'2'},
						{title : '前月发出',colspan:'2'},
						{title : '前月发前月收',colspan:'2'},
						{title : '前月发本月收',colspan:'2'},
						{title : '前月发未收',colspan:'2'},
						{title : '前月差异',colspan:'2'}
						],[
						
						{field : 'zoneName',title : '大区',width: 60},
						{field : 'buyerName',title : '结算公司',width: 200,align:'left',halign:'center'},
						{field : 'organName',title : '管理城市',width: 80},
						{field : 'orderUnitName',title : '货管单位',width: 80,align:'center',halign:'center'},
						
						{field : 'zoneNameFrom',title : '大区',width: 60},
						{field : 'salerName',title : '结算公司',width: 200,align:'left',halign:'center'},
						{field : 'organNameFrom',title : '管理城市',width: 80},
						{field : 'orderUnitNameFrom',title : '货管单位',width: 80,align:'center',halign:'center'},
						
					    {field : 'brandUnitName',title : '品牌部',width: 80,align:'center'},
						
						{field : 'currMonSenNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'currMonSenRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'currMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'currMonRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'nextMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'nextMonRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'currMonYetRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'currMonYetRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'currMonDiffNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'currMonDiffRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'lastMonSenNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'lastMonSenRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'lastMonLastRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'lastMonLastRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'lastMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'lastMonRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'lastMonYetRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'lastMonYetRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
						
						{field : 'lastMonDiffNum',title : '数量',width: 80,align:'right','exportType':'number'},
						{field : 'lastMonDiffRMB',title : '金额',width: 100,align:'right','exportType':'number'}
	                 ]"
		 			jsonExtend='{}'
							/>
			</div>
		 </div>
	</div>
	
</div>
