<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
<@p.commonSetting search_url="/bill_balance_invoice_register/list.json" 
					  save_url="/bill_balance_invoice_register/post" 
					  update_url="/bill_balance_invoice_register/put" 
					  del_url="/bill_balance_invoice_register/save" 
					  datagrid_id="dataGridCommonInvoice" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="1000" 
					  dialog_height="300"
					  primary_key="id"/>
	
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
		        {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},	
		        {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0,"action":"billCommonInvoiceRegister.search()"},
				{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","type":0,"action":"billCommonInvoiceRegister.clear()"},				    
			    {"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"billCommonInvoiceRegister.batchDel()","type":3},									
				{"id":"mian_btn_aduit","title":"确认","iconCls":"icon-aduit","action":"billCommonInvoiceRegister.batchOperate(1)","type":31},	
				{"id":"top_btn_cancel","title":"反确认","iconCls":"icon-aduit","action":"billCommonInvoiceRegister.batchOperate(0)","type":32},
				<!--{"id":"mian_btn_cancel","title":"作废","iconCls":"icon-cancel","action":"billCommonInvoiceRegister.batchOperate(2)","type":0},-->			
				 {"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"iptSearch.doExport('dataGridCommonInvoice','/bill_balance_invoice_register/export','发票登记列表导出')","type":4}
				 {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"iptSearch.doImport('发票信息导入.xlsx','/bill_balance_invoice_register/do_import',0,billCommonInvoiceRegister.importCallBack)","type":6}		
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
	        	<input type="hidden" name="isHQ" id="searchIsHQ" />
	            <table class="form-tb" >				    
		             <col width="100"/>
		             <col />
		             <col width="100"/>
		             <col />	
		             <col width="100"/>
		             <col />
		             <col width="100"/>
		             <col />	                        	 	
				     <tbody>	  
		                <tr>
		                	<th>单据编码：</th>
							<td>
								<input class="ipt" name="billNo" id="billNo" />
							</td> 
							<th>公司：</th>
							<td> 
								<input class="easyui-company ipt"  name="salerName" id="salerName" />
								<input type="hidden" name="salerNo" id="salerNo"/>
							</td>
							<th>客户：</th>
							<td> 
								<input class="easyui-customerInvoiceInfo" name="buyerName" id="buyerNameId" 
								data-options="inputValueFeild:'buyerNoId',inputNameFeild:'buyerNameId'" />
								<input type="hidden" name="buyerNo" id="buyerNoId"/>
							</td>
							<th>结算类型：</th>
							<td> 
								<input name="balanceType" id="searchbalanceType" />
							</td>
			       		</tr>
			       		<tr>
			       			<th>单据状态：</th>
							<td>
								<input class="easyui-combobox ipt" name="status" 
									data-options="valueField: 'value',textField: 'text',data:statusformatter "/> 
							</td>
						    <th>制单人：</th>
						    <td>
						    	<input class="easyui-validatebox ipt" name="createUser" id="createUserCondition" style="width:130px;"/>
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
			       		<tr>
			       			<th>管理城市： </th>
	                   		<td>
	                   			<input class="easyui-organ ipt"  name="organName" id="organName" data-options="inputWidth:130,multiple:true"/>
								<input type="hidden" name="organNo" id="organNo"/>
	                   		</td>						
						 	<th>结算月：</th>
				       		<td>
				       		 	<input class="easyui-datebox ipt"  name="month" id="month" data-options="dateFmt:'yyyyMM'" /> 
				       		</td>
			       		</tr>						
					</tbody>
				</table>   
	        </form>
        </div>
    </div>
				           
    <div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridCommonInvoice"  loadUrl="" saveUrl=""   defaultColumn="" title=""
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		               checkOnSelect="true"
			           rownumbers="true" singleSelect="false" 				           
			             columnsJsonList="[
		                    {field : 't', checkbox:true, width : 30, notexport:true},
		                    {field : 'billNo',title : '单据编码',width : 130,align:'left',halign:'center'},		
		                    {field : 'statusName',title : '状态',width : 70,align:'center',halign:'center'}, 
			                {field : 'billDate',title : '开票日期',width : 100,align:'center',halign:'center'},	 				                       	
			                {field : 'salerName',title : '公司名称',width : 200,align:'left',halign:'center'},
			                {field : 'buyerName',title : '客户名称',width : 200,align:'left',halign:'center'},
			                {field : 'amount',title : '发票金额',width : 90,align:'right',halign:'center'},
			                {field : 'balanceTypeName',title : '结算类型',width : 130,align:'center',halign:'center'}, 
			                {field : 'sourceNo',title : '开票申请单号',width : 150,align:'left',halign:'center', formatter: function(value,row,index){
								var title;
								if($('#isHQ').val() == 'true') {
									title = 'HI-开票申请';
								}else {
									title = 'AI-开票申请';
								}
								return billNoMenuRedirect.billNoMenuFormat(value,row,index,title);
							}},
							{field : 'organName',title : '管理城市',width : 80,align:'center',halign:'center'}, 
			              	{field : 'month',title : '结算月',width : 80,align:'center',halign:'center'},
			                {field : 'createUser',title : '制单人',width : 100,align:'center',halign:'center'}, 
			                {field : 'createTime',title : '制单时间',width : 150,align:'center',halign:'center'},
			                {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'}, 
			                {field : 'updateTime',title : '修改时间',width : 150,align:'center',halign:'center'}
		              ]" 				          
				          jsonExtend='{
		                           onDblClickRow:function(rowIndex, rowData){
		                               //双击方法
				                   	 billCommonInvoiceRegister.loadDetail(rowIndex,rowData);
				                   }
		         }'/>			     
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
                     {field : 'validateObj.salerNo', title : '公司编码', width : 150},
                     {field : 'validateObj.buyerNo', title : '客户编码', width : 150},	
	 				 {field : 'validateObj.invoiceCode',title:'发票编码',width:100},
	 				 {field : 'validateObj.invoiceNo',title:'发票号',width:100,align:'right'},
	 				 {field : 'validateObj.invoiceDate',title:'发票日期',width:100,align:'right'},
					 {field : 'validateObj.invoiceAmount',title:'发票金额',width:90,align:'right'},
					 {field : 'validateObj.taxRate', title : '税率', width : 90},
                     {field : 'validateObj.brandNo', title : '品牌编码', width : 100},	
	 				 {field : 'validateObj.categoryNo',title:'大类编码',width:100},
	 				 {field : 'validateObj.qty',title:'数量',width:80,align:'right'},
	 				 {field : 'validateObj.price',title:'价格',width:90,align:'right'},
					 {field : 'validateObj.express',title:'快递公司',width:150,align:'right'},
					 {field : 'validateObj.deliveryNo',title:'快递编号',width:100,align:'right'},
	 				 {field : 'validateObj.deliveryDate',title:'快递日期',width:100,align:'right'},
					 {field : 'validateObj.auditor',title:'确认人',width:100,align:'right'},
					 {field : 'validateObj.auditDate',title:'确认日期',width:100,align:'right'},
	 				 {field : 'validateObj.remark',title:'备注',width:180,align:'left'}]"	
         />
     </div>	