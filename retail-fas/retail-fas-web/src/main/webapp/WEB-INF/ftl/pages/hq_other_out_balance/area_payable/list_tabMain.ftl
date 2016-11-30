<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.billToolBar type="area_other_in_mainBar"/>
	</div>
	
	<div  data-options="region:'center',border:false">
     <div class="easyui-layout" data-options="fit:true" >
		<#--搜索start-->
		<div data-options="region:'north',border:false" >
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
	        	 <input type="hidden" name="aToA" value="yf" />
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
                            	<input class="easyui-validatebox ipt"  name="billNo" id="billNo"  style="width: 150px;"/>
                            </td>
                          	<th>单据状态：</th>
						    <td>
						    	<select class="easyui-combobox"  name="extendStatus" id="status" style="width: 130px;"
						        data-options="editable:false,valueField:'statusNo',textField:'statusName',url:BasePath + '/area_balance_common/getAreaOtherInStatus'"/>
						    </td>
						    <th>品牌： </th>
							<td>
								<input class="easyui-brand ipt"  name="brandName" id="brandNameCondId" data-options="inputNoField:'brandCondId',inputNameField:'brandNameCondId',inputWidth:130"/>
								<input type="hidden" name="brandNo" id="brandCondId"/>
							</td>
							<th>制单人：</th>
						    <td>
						    	<input class="easyui-validatebox ipt" name="createUser" id="createUserCondition" style="width:130px;"/>
						    </td>
						</tr>
						<tr>
                           	<th>地区公司：</th>
                            <td>
                             <input class="easyui-company ipt"   name="buyerName" id="buyerNameCon" 
                             data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNoCon',inputNameField:'buyerNameCon',inputWidth:160"/>
                             <input type="hidden" name="buyerNo" id="buyerNoCon"/>
                            </td>
						    <th>结算期间：</th>
						    <td>
						    	<input class="easyui-datebox"  name="balanceStartDate"  readonly="true" id="startDateCond" data-options="maxDate:'endDateCond'" />
						    </td>
							<th>至：</th>
						    <td>
						    	<input class="easyui-datebox"  name="balanceEndDate"  readonly="true" id="endDateCond" data-options="minDate:'startDateCond'" />
						    </td>
						    <th>审核人：</th>
						    <td>
						    	<input class="easyui-validatebox ipt" name="auditor" id="auditorCondition" style="width:130px;"/>
						    </td>
						</tr>
						<tr>
						    <th>总部公司：</th>
                            <td>
                            	<input class="easyui-company ipt"  name="salerName" id="salerNameCon" 
                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'salerNoCon',inputNameField:'salerNameCon',inputWidth:160"/>
                           		<input type="hidden" name="salerNo" id="salerNoCon"/>
                           	</td>
						    <th></th>
                         	<td></td>
                         	<th></th>
                         	<td></td>
                         	<th></th>
                         	<td></td>
						</tr>
				    </tbody>
				</table> 
	        </form>
        </div>
     </div> 
	
	<div data-options="region:'center',border:false">
				<@p.datagrid id="mainDataGrid"  
					loadUrl="" saveUrl=""   defaultColumn="" 
		            isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		            checkOnSelect="true"   rownumbers="true" singleSelect="false" showFooter="true" 
					columnsJsonList="[
						{field : 'ck',checkbox:true,notexport:true},
						{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
						{field : 'askPaymentNo',title : '请款单号',width : 150,align:'center',halign:'center', formatter: function(value,row,index){
							return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AI-请款单');
						}},
						{field : 'extendStatus',title : '单据状态',width : 100,align:'center',halign:'center',formatter:areaOtherStockInBalance.statusFormat},
						{field : 'buyerName',title : '地区公司',width : 200,align:'left',halign:'center'},
						{field : 'salerName',title : '总部公司',width : 200,align:'left',halign:'center'},
						{field : 'balanceStartDate',title : '结算起始日期',width : 120,align:'center',halign:'center'},
						{field : 'balanceEndDate',title : '结算结束日期',width : 120,align:'center',halign:'center'},
						{field : 'brandName',title : '品牌',width : 100,align:'center',halign:'center'},			
						{field : 'balanceDate',title : '结算日',width : 100,align:'center',halign:'center'},
						{field : 'outQty',title : '出库数量',width : 80,align:'right',halign:'center'},
						{field : 'outAmount',title : '出库金额',width : 100,align:'right',halign:'center'},
						{field : 'balanceAmount',title : '应付金额',width : 100,align:'right',halign:'center'},
						{field : 'invoiceNo',title : '采购发票号',width : 150,align:'center',halign:'center'},
						{field : 'createUser',title : '创建人',width : 100,align:'center',halign:'center'},
						{field : 'createTime',title : '创建日期',width : 150,align:'center',halign:'center',sortField:'create_time',sortable:true},
						{field : 'auditor',title : '审核人',width : 100,align:'center',halign:'center'},
						{field : 'auditTime',title : '审核日期',width : 150,align:'center',halign:'center'},
						{field : 'billName',title : '单据名称',width : 100,align:'left',halign:'center'},
						{field : 'remark',title : '备注',width : 150,align:'left',halign:'center'}
						]" 
					jsonExtend='{onDblClickRow:function(rowIndex, rowData){
						areaOtherStockInBalance.loadDetail(rowIndex,rowData);
				}}'/>
			</div>
		</div>
	</div>
</div>
