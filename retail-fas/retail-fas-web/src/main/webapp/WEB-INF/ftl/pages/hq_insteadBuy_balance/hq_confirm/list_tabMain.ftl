<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
		  <@p.billToolBar type="hq_insteadOf_mainBar"/>
	</div>
	
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" >
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				  <form name="searchForm" id="searchForm" method="post">
	          	  <table class="form-tb" >
				    <col width="100" />
				    <col  />
				    <col width="100" />
				    <col />
				    <col width="100" />
				    <col />
				    <col width="100" />
				    <col />
				    <tbody>
				    	<tr>
				    	    <th>单据编号：</th>
                            <td>
                            	<input class="easyui-validatebox"  name="billNo" id="billNoCond" style="width: 160px;"/>
                            </td>
                            <th>单据状态：</th>
						    <td>
								<select class="easyui-combobox"  name="status" id="status" style="width:130px;"
						        data-options="editable:false,valueField:'statusNo',textField:'statusName',url:BasePath + '/area_balance_common/getHSBalanceBillStatus'"/>								    </td>
							<th>品牌部： </th>
							<td>
								<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameCond" data-options="inputNoField:'brandUnitNoCond',inputNameField:'brandUnitNameCond',inputWidth:130"/>
								<input type="hidden" name="brandUnitNo" id="brandUnitNoCond"/>
							</td>
							<th>审核人：</th>
						    <td>
						    	<input class="easyui-validatebox ipt" name="auditor" id="auditorCondition" style="width:130px;"/>
						    </td>
						</tr>
						<tr>
						    <th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</th>
                            <td>
                            	<input class="easyui-company ipt"   name="buyerName" id="buyerNameCond"  
                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'buyerNoCond',inputNameField:'buyerNameCond',inputWidth:160,isDefaultData : false"/>
                            	<input type="hidden" name="buyerNo" id="buyerNoCond"/>
                            </td>
							<th>制单日期：</th>
						    <td>
						    	<input class="easyui-datebox"  name="createTimeStart"  readonly="true" id="createTimeStart" data-options="maxDate:'createTimeEnd'" />
						    </td>
							<th>至：</th>
						    <td>
						    	<input class="easyui-datebox"  name="createTimeEnd"  readonly="true" id="createTimeEnd" data-options="minDate:'createTimeStart'" />
						    </td>
					   		<th>制单人：</th>
						    <td>
						    	<input class="easyui-validatebox ipt" name="createUser" id="createUserCondition" style="width:130px;"/>
						    </td>
						</tr>
						<tr>
							<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</th>
                            <td>
                            	<input class="easyui-supplier ipt"  name="supplierName" id="supplierNameCond" data-options="inputNoField:'salerNoCond',inputNameField:'supplierNameCond',inputWidth:160"/>
                           		<input type="hidden" name="salerNo" id="salerNoCond"/>
                           	</td>
							<th>结算期间：</th>
						    <td>
						    	<input class="easyui-datebox"  name="balanceStartDate"  readonly="true" id="startDateCond" data-options="maxDate:'endDateCond'" />
						    </td>
							<th>至：</th>
						    <td>
						    	<input class="easyui-datebox"  name="balanceEndDate"  readonly="true" id="endDateCond" data-options="minDate:'startDateCond'" />
						    </td>
                         	<th></th>
                         	<td></td>
						</tr>
				    </tbody>
				</table>   
	        </form>
				</div>
			</div>
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="mainDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"   rownumbers="true" singleSelect="false" showFooter="true" 
				           columnsJsonList="[
				                {field:'ck',checkbox:true,notexport:true},
								{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
								{field : 'status',title : '单据状态',width : 100,align:'center',formatter:hqInsteadBuy.statusFormat},
								{field : 'buyerName',title : '公司',width : 200,align:'left',halign:'center'},
								{field : 'salerName',title : '供应商',width : 200,align:'left',halign:'center'},
								{field : 'balanceStartDate',title : '结算起始日期',width : 100,align:'center'},
								{field : 'balanceEndDate',title : '结算结束日期',width : 100,align:'center'},
								{field : 'brandUnitName',title : '品牌部',width : 80,align:'center',halign:'center'},
								{field : 'balanceDate',title : '结算日',width : 100,align:'center',halign:'center'},
								{field : 'entryQty',title : '入库数量',width : 80,align:'right',halign:'center'},
								{field : 'entryAmount',title : '入库金额',width : 100,align:'right',halign:'center'},
								{field : 'returnQty',title : '退残数量',width : 80,align:'right',halign:'center'},
								{field : 'returnAmount',title : '退残金额',width : 100,align:'right',halign:'center'},
								{field : 'balanceQty',title : '应付数量',width : 80,align:'right',halign:'center'},
								{field : 'deductionAmount',title : '扣项金额',width : 100,align:'right',halign:'center'},
								{field : 'balanceAmount',title : '应付金额',width : 100,align:'right',halign:'center'},
								{field : 'createUser',title : '制单人',width : 100,align:'center'},
								{field : 'createTime',title : '制单日期',width : 150,align:'center',sortField:'create_time',sortable:true},
								{field : 'auditor',title : '审核人',width : 100,align:'center',halign:'center'},
								{field : 'auditTime',title : '审核日期',width : 150,align:'center',halign:'center'},
								{field : 'billName',title : '单据名称',width : 100,align:'left',halign:'center'},
								{field : 'remark',title : '备注',width : 150,align:'left',halign:'center'}
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
					                   	  hqInsteadBuy.loadDetail(rowIndex,rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
</div>