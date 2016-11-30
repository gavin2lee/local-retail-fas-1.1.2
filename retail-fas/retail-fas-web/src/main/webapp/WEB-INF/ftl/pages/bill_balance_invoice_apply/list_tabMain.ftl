<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
		     {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细');","type":0},
		     {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0,"action":"billBalanceInvoiceApply.search()"},
		     {"id":"btn-remove","title":"清空","iconCls":"icon-empty","type":0,"action":"billBalanceInvoiceApply.clear()"},		   
	         {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"billBalanceInvoiceApply.batchDel()","type":3},		         
	         {"id":"mian_btn_aduit","title":"审核","iconCls":"icon-aduit","action":"billBalanceInvoiceApply.batchOperate(2)","type":31},	
			 {"id":"top_btn_antiAudit","title":"反审核","iconCls":"icon-aduit","action":"billBalanceInvoiceApply.batchOperate(1)","type":32},
			 {"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"billBalanceInvoiceApply.exportExcel()","type":1},
			 {"id":"mian_btn_export","title":"导出税控模板","iconCls":"icon-export","action":"billBalanceInvoiceApply.exportTaxModelExcel()","type":1},
			 {"id":"btn-import","title":"批量登记发票","iconCls":"icon-import","action":"iptSearch.doImport('批量登记发票.xlsx','/bill_balance_invoice_apply/do_import',0,billBalanceInvoiceApply.importCallBack)","type":105}
			 {"id":"btn_add","title":"生成发票","iconCls":"icon-import","action":"invoiceApplyBalance.invoiceRegister()","type":1}
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post"> 
	        	<input type="hidden" name="isHQ" id="searchIsHQ" />
	            <table class="form-tb" >
				    <col width="100" />
				    <col  />				   
				    <col width="100" />
				    <col />
				    <col width="100"/>
				    <col />
				    <col width="100"/>
				    <col />
				   	<tbody>	  
						<tr>												
						 <input type="hidden" name="balanceType" id="balanceType" value="${balanceType}">
						 <th>管理城市： </th>
	                   		<td>
	                   			<input class="easyui-organ ipt"  name="organName" id="organName" data-options="inputWidth:130,multiple:true"/>
								<input type="hidden" name="organNo" id="organNo"/>
	                   		</td>
						 <th>开票方名称：</th>
							<td>
								<input class="easyui-company ipt" name="salerName" id="salerName" data-options="inputNoField:'salerNo_', 
										inputNameField:'salerName',inputWidth:130" />
									<input type="hidden"  name="salerNo" id="salerNo_"/>
							</td>
				       		<th>客户名称：</th>
							<td>
								<input class="easyui-customerInvoiceInfo" name="buyerName" id="buyerNameId" 
								data-options="inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:130" />
								<input type="hidden" name="buyerNo" id="buyerNoId"/>
							</td> 
							 <th>开票名称：</th>
								<td>
									<input class="easyui-validate ipt" name="invoiceName" id="invoiceNameCond" />
								</td>	
			       		</tr>
			       		<tr>
			       		<th>结算单类型： </th>
	                   		<td>
	                   			<input name="balanceType" id="searchbalanceTypes" readonly/>
	                   		</td>
	                   		<th>结算月：</th>
				       		<td>
				       		 	<input class="easyui-datebox ipt"  name="month" id="month" data-options="dateFmt:'yyyyMM'" /> 
				       		</td>
				       		 <th>品牌部：</th>
								<td>
					        		<input class="ipt easyui-brandunit" name="brandNames" id="brandNames_" 
					        			data-options="multiple: true, inputNoField:'brandNos_', inputNameField:'brandNames_'"/>
								  	<input type="hidden" name="brandNos" id="brandNos_"/>
								</td>
							 <th>单据状态：</th>
							<td>
								<input class="easyui-combobox ipt"   name="status" id="statusId"  />
							</td> 	
	                       
			       		</tr>
			       		<tr>
			       		    <th>开票申请号：</th>
				       		 <td>
				       		 	<input class="easyui-validatebox ipt" name="billNo" id="billNo"/>
				       		 </td>													
						 	<th>结算单号：</th>
				       		<td>
				       		 	<input class="easyui-validatebox ipt" name="balanceNo" id="billBalanceNo"/>
				       		</td>
			       			<th>申请开票日期：</th>
				       		<td>
				       		 	<input class="easyui-datebox ipt"  name="invoiceApplyDateStart" id="invoiceApplyDateStart" data-options="maxDate:'invoiceApplyDateEnd'"/>
				       	   	</td>
				       	   	<th> &nbsp;&nbsp;至：&nbsp;&nbsp; </th>
				       		<td>
				       		 	<input class="easyui-datebox ipt"  name="invoiceApplyDateEnd" id="invoiceApplyDateEnd" data-options="minDate:'invoiceApplyDateStart'"/>
				       	    </td>
				       	    <!--
				       		<th>制单人：</th>
				       		 <td>
				       		 	<input class="easyui-validatebox ipt" name="createUser" id="createUser"/>
				       		 </td>
				       		 -->
			       		</tr>
					</tbody>
				</table>   
	        </form>
    	</div>
    </div>
				           
    <div data-options="region:'center',border:false">
	      <@p.datagrid id="invoiceApplyDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
	              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
	               checkOnSelect="true" 
		           rownumbers="true" singleSelect="false"
		              frozenColumns="[
		                 {field : 't', checkbox:true, width : 40, notexport:true},		                
		                 {field : 'organName',title : '管理城市',width : 60,align:'center',halign:'center'}, 
						 {field : 'buyerName', title : '客户名称', width : 200, align : 'left',halign:'center'},
					 ]"
		             columnsJsonList="[			            
			              {field : 'balanceType',hidden : 'true',align:'center', notexport:true},
			              {field : 'invoiceName', title : '开票名称', width : 150, align : 'left',halign:'center'},		
			              {field : 'brandName', title : '品牌', width : 100, align : 'center',halign:'center'},
			              {field : 'month',title : '结算月',width : 80,align:'center',halign:'center'},
		                  {field : 'amount', title : '金额', width : 100, align : 'right',halign:'center'},	                  
			              {field : 'statusName',title : '单据状态',width : 80,align:'center',halign:'center'},		                  		                 
		                  {field : 'billNo', title : '开票申请号', width : 150, align : 'left',halign:'center',sortable:true,sortField:'bill_no'},
		                  {field : 'balanceNo', title : '结算单号', width : 150, align : 'left',halign:'center'},
		                  {field : 'balanceTypeStr', title : '结算单类型', width : 150, align : 'center',halign:'center'},
		                  {field : 'salerName', title : '开票方名称', width : 200, align : 'left',halign:'center'},
		                  {field : 'invoiceApplyDate', title : '申请开票日期', width : 100, align : 'center',halign:'center'},		                 
		                  {field : 'currency', title : '币别', width : 70, align : 'center',halign:'center',formatter:currencyFormat},
		                  {field : 'taxRegistryNo', title : '纳税人识别号', width : 100, align : 'left',halign:'center'},
		                  {field : 'postPayDate', title : '交票日期', width : 100, align : 'center',halign:'center'}, 		                 
		                  {field : 'shopName', title : '店铺名称', width : 120, align : 'left',halign:'center'},		
		                  {field : 'shopNo',title : '店铺编码',width : 60,align:'left'},	
		                  {field : 'buyerNo', title : '客户编码', width : 120, align : 'left',halign:'center'},
		                  {field : 'salerNo', title : '开票方编码', width : 80, align : 'left',halign:'center'},
						  {field : 'createUser',title : '制单人',width : 100,align:'center',halign:'center'}, 
			              {field : 'createTime',title : '制单日期',width : 150,align:'center',halign:'center'},
			              {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'}, 
			              {field : 'updateTime',title : '修改日期',width : 150,align:'center',halign:'center'},	
		                  {field : 'auditor',title : '审核人',width : 100,align:'center',halign:'center'}, 
			              {field : 'auditTime',title : '审核日期',width : 150,align:'center',halign:'center'},		              	                 
			              {field : 'remark',title : '备注',width : 200,align:'left',halign:'center'}
	                  ]" 				          
			          jsonExtend='{
                           onDblClickRow:function(rowIndex, rowData){
                               //双击方法
		                   	 	billBalanceInvoiceApply.loadDetail(rowIndex,rowData);
		                   }
	                 }'/>
     </div>
     
 </div>
 
 <div id="printContrPanel" class="easyui-dialog" title="My Dialog" style="width:200px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
     <div align='center'>
     	<form id="printForm">
	     		<select multiple id="printSelect" align="left" style="height:200px;width:100%">
				</select>
     	</form>
     </div>
</div>  
 
  <div id="printTaxModeltContrPanel" class="easyui-dialog" title="My Dialog" style="width:200px;height:200px;"   
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
     <div align='center'>
     	<form id="printTaxModelForm">
	     		<select multiple id="printTaxModelSelect" align="left" style="height:200px;width:100%">
				</select>
     	</form>
     </div>
</div>  
 
 <div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="importDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'index', title : '行号', width : 30},
	           		 {field : 'pass',title:'记录是否合法',width:90,formatter: function(value,row,index){
							if (value == 0){
								return '否';
							}
							return '是'
						}
			         },
			         {field : 'errorInfo',title:'错误信息',width:250,align:'left',halign:'center'},
                     {field : 'validateObj.sourceNo', title : '开票申请号', width : 150},
	 				 {field : 'validateObj.invoiceCode',title:'发票编码',width:100},
	 				 {field : 'validateObj.invoiceNo',title:'发票号',width:100,align:'right'},
	 				 {field : 'validateObj.invoiceDate',title:'发票日期',width:100,align:'right'},
					 {field : 'validateObj.express',title:'快递公司',width:150,align:'right'},
					 {field : 'validateObj.deliveryNo',title:'快递编号',width:100,align:'right'},
	 				 {field : 'validateObj.deliveryDate',title:'快递日期',width:100,align:'right'},
					 {field : 'validateObj.auditDate',title:'确认日期',width:100,align:'right'}]"	
         />
     </div>	
