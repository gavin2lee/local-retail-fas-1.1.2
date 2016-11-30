<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="main_bar"	listData=[
			{"id":"main_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"applyForPayment.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"applyForPayment.clear()","type":0},
			{"id":"main_btn_del","title":"删除","iconCls":"icon-del","action":"applyForPayment.batchDel()","type":3},		
			{"id":"main_btn_sure","title":"审核","iconCls":"icon-aduit","action":"applyForPayment.batchAudit(1)","type":31},
			{"id":"main_btn_unsure","title":"反审核","iconCls":"icon-aduit","action":"applyForPayment.batchAudit(0)","type":32} 							
		]/>
	</div>
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" >
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
                        		<input class="easyui-validatebox ipt"  name="billNo" id="billNoCond"  style="width: 150px;"/>
                        	</td>
                          	<th>单据状态：</th>
						    <td>
						    	<select class="easyui-combobox"  name="status" id="status" style="width:160px;"
						        data-options="editable:false,valueField:'statusNo',textField:'statusName',url:BasePath + '/apply_for_payment/get_bill_status'"/>
						    </td>
						    <th>制单人：</th>
						    <td>
						    	<input class="easyui-validatebox ipt" name="createUser" id="createUserCondition" />
						    </td>
							<th>审核人：</th>
						    <td>
						    	<input class="easyui-validatebox ipt" name="auditor" id="auditorCondition" />
						    </td>
						</tr>
						<tr>
							<th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</th>
                            <td>
                            	<input class="easyui-company ipt"  name="buyerName" id="buyerNameCond" 
                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'buyerNoCond',inputNameField:'buyerNameCond',inputWidth:160,isDefaultData : false"/>
                           		<input type="hidden" name="buyerNo" id="buyerNoCond"/>
                           	</td>
                           	<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</th>
                            <td>
                            	<input class="easyui-supplier ipt"  name="salerName" id="salerNameCond" 
                            	data-options="inputNoField:'salerNoCond',inputNameField:'salerNameCond',inputWidth:160"/>
								<input type="hidden" name="salerNo" id="salerNoCond" />
                            </td>
						    <th>制单日期：</th>
						    <td>
						    	<input class="easyui-datebox"  name="createTimeStart"  readonly="true" id="createTimeStart" data-options="maxDate:'createTimeEnd'" />
						    </td>
							<th>至：</th>
						    <td>
						    	<input class="easyui-datebox"  name="createTimeEnd"  readonly="true" id="createTimeEnd" data-options="minDate:'createTimeStart'" />
						    </td>
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
								{field : 'id',hidden:true},
								{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
								{field : 'status',title : '单据状态',width : 115,align:'center',halign:'center',formatter:applyForPayment.statusFormat},
								{field : 'salerName',title : '供应商',width : 200,align:'left',halign:'center'},
								{field : 'buyerName',title : '公司',width : 200,align:'left',halign:'center'},
								{field : 'billDate',title : '单据日期',width : 100,align:'center',halign:'center'},
								{field : 'balanceNo',title : '结算单号',width : 150,align:'left',halign:'center'},
								{field : 'allAmount',title : '请款金额',width : 100,align:'right',halign:'center'},
								{field : 'balanceAmount',title : '结算金额',width : 100,align:'right',halign:'center'},
								{field : 'createUser',title : '制单人',width : 100,align:'center',halign:'center'},
								{field : 'createTime',title : '制单日期',width : 150,align:'center',halign:'center',sortField:'create_time',sortable:true},
								{field : 'auditor',title : '审核人',width : 100,align:'center',halign:'center'},
								{field : 'auditTime',title : '审核日期',width : 150,align:'center',halign:'center'},
								{field : 'remark',title : '备注',width : 200,align:'left',halign:'center'}
				                ]" 
					          jsonExtend='{
		                           onDblClickRow:function(rowIndex, rowData){
				                   	  applyForPayment.loadDetail(rowIndex,rowData);
			                  }
			         }'/>
			</div>
		 </div>
	</div>
</div>