<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar-query" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"billInvoiceApplyObj.searchBillSaleBalance()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"billInvoiceApplyObj.clearForm()","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-aduit","action":"iptSearch.doExport('billDataGridDiv','/bill_invoice_apply_generate/export','单据开票明细')", "type":4}
             {"id":"btn-save","title":"生成开票申请","iconCls":"icon-add","action":"billInvoiceApplyObj.generateInvoiceApply()","type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="billSearchForm" id="billSearchForm" method="post">
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>公司名称：</th>
			       		 			<td>
			       		 				<input class="easyui-validatebox ipt easyui-company"  name="searchCompanyName" id="searchCompanyName" 
			       		 					data-options="inputNoField:'searchCompanyNo',inputNameField:'searchCompanyName',inputWidth:200,required:true"/>
										<input type="hidden"  name="searchCompanyNo" id="searchCompanyNo"/>	
			       		 			</td>
									<th>单据类型： </th>
	                     			<td>
	                     				<input class="easyui-combobox ipt" name="billBalanceType" id="billBalanceType" 
	                     					data-options="inputWidth:200,multiple:true,required:true"/>
	                     			</td>
	                     			<th>管理城市： </th>
			                   		<td>
			                   			<input class="easyui-organ ipt"  name="organName" id="organName" data-options="inputWidth:130,multiple:true"/>
										<input type="hidden" name="organNo" id="organNo"/>
			                   		</td>	
			       		 			<th>单据日期：</th>
       		 						<td width="350px;">
       		 							<input class="easyui-validatebox easyui-datebox ipt"  name="startDate" id="startDate" data-options="required:true,maxDate:'endDate'"/>
       		 							&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;
       		 							<input class="easyui-validatebox easyui-datebox ipt" name="endDate" id="endDate" data-options="required:true,minDate:'startDate'"/>
       		 						</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="billDataGridDiv" loadUrl="" saveUrl="" defaultColumn="" title=""
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"
			           columnsJsonList="[
			              {field : 't', checkbox:true, width : 30, notexport:true},
			              {field : 'billNo',title : '单据编码',width : 130,align:'left',align:'center'},
						  {field : 'balanceTypeName',title : '单据类型',width : 80,align:'left',halign:'center'},
						  {field : 'balanceDate',title : '单据日期',width : 80,align:'left',halign:'center'},
						  {field : 'balanceMonth',title : '结算月',width : 80,align:'left',halign:'center'},
						  {field : 'buyerName',title : '结算买方',width : 100,align:'left',halign:'center'},
						  {field : 'salerName',title : '结算卖方',width : 200,align:'left',halign:'center'},
						  {field : 'organName',title : '管理城市',width : 80,align:'center',halign:'center'}, 
						  {field : 'shopNo',title : '店铺编码',width : 120,align:'left',halign:'center'},
						  {field : 'shopName',title : '店铺名称',width : 170,align:'left',halign:'center'},
						  {field : 'itemCode',title : '商品编码',width : 120,align:'left',halign:'center'},
						  {field : 'itemName',title : '商品名称',width : 170,align:'left',halign:'center'},
						  {field : 'brandName',title : '品牌',width : 80,align:'left',halign:'center'},
						  {field : 'categoryName',title : '大类',width : 50,align:'left',halign:'center'},
						  {field : 'qty',title : '数量',width : 50,align:'right',halign:'center'},
						  {field : 'cost',title : '单价',width : 50,align:'right',halign:'center'},
						  {field : 'amount',title : '金额',width : 90,align:'right',halign:'center'},
						  {field : 'primeCost',title : '成本价',width : 80,align:'right',halign:'center'},
						  {field : 'areaCost',title : '地区价',width : 80,align:'right',halign:'center'},
						  {field : 'headquareCost',title : '总部价',width : 80,align:'right',halign:'center'}
			              ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                   }
		                 }'
                 />
			</div>
	 	</div>
	</div>
</div>