<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
		    {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"billController.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"billController.clear()","type":0},
			{"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"billController.batchDel()","type":3},		
			{"id":"mian_btn_aduit","title":"确认","iconCls":"icon-aduit","action":"billController.batchOperate(1)","type":31},	
			{"id":"top_btn_cancel","title":"反确认","iconCls":"icon-aduit","action":"billController.batchOperate(0)","type":32} ,
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"billController.doExport('mainDataGrid','/bill_invoice/export','发票列表导出')","type":4},			
			 {"id":"top_btn_add","title":"生成付款单","iconCls":"icon-add","action":"generatePaymentBill()","type":95}	
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
	         <input type="hidden" name="balanceType">
	            <table class="form-tb" >
				    <col width="80" />
				    <col  />
				    <col width="80" />
				    <col />
				    <col width="80" />
				    <col />
				    <col width="80"/>
				    <col />
				    <tbody>
				    	<tr>
				    		<th>单据编号：</th>
						    <td><input class="ipt" name="billNo"/></td>
						    <th>单据状态：</th>
						    <td><input class="ipt" combobox="status" fliterType="IS_BILLAPPLY" name="status" /></td>
						    <th>制单人：</th>
						    <td><input class="ipt" name="createUser"/></td>
						    <th>审核人：</th>
						    <td><input class="ipt" name="auditor"/></td>
						</tr>
						<tr>
							<th>公司： </th>
							<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiBuyerNo"></td>
							<th>供应商： </th>
							<td><input class="ipt" multiSearch="organization"  /><input type="hidden" name="multiSalerNo"></td>
						    <th>单据日期：</th>
						    <td><input class="ipt easyui-datebox"  name="billDateStart" id="billDateStart" data-options="maxDate:'billDateEnd'"/></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td><input class="ipt easyui-datebox"  name="billDateEnd" id="billDateEnd" data-options="minDate:'billDateStart'" /></td>
						</tr>
						<tr>
							<th>审核日期：</th>
						    <td><input class="ipt easyui-datebox"  name="auditTimeStart" id="auditTimeStart"  data-options="maxDate:'auditTimeEnd'" /></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="ipt easyui-datebox"  name="auditTimeEnd" id="auditTimeEnd" data-options="minDate:'auditTimeStart'" /></td>
						</tr>
				    </tbody>
				</table>   
	        </form>
        </div>
     </div> 
	<#-- end -->
	
	<div data-options="region:'center',border:false">
		<@p.datagrid id="mainDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			rownumbers="true" showFooter="true" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			columnsJsonList="[
				{field:'ck',checkbox:true,notexport:true},
				{field : 'billNo',title : '单据编号',width : 150},
				{field : 'statusName',title : '单据状态',width : 100},
				{field : 'buyerName',title : '公司',width : 200, align:'left'},
				{field : 'salerName',title : '供应商',width : 200, align:'left'},
				{field : 'refBillNo',title : '结算单编号',width : 150},
				{field : 'qty',title : '数量',width : 80},	
				{field : 'amount',title : '金额',width : 80, align:'left'},
				{field : 'currencyName',title : '币种',width : 100},
				{field : 'targetCurrencyName',hidden:true,title : '本位币',width : 100},
				{field : 'conversionFactor',hidden:true,title : '汇率',width : 100},
				{field : 'targetCurrencyAmount',hidden:true,title : '本位币金额',width : 100},
				{field : 'paymentNo',title : '付款单号',width : 100, align:'left', formatter: function(value,row,index){
					var title;
					if($('#isHQ').val() == 'true') {
						title = 'HI-付款单';
					}else {
						title = 'AI-付款单';
					}
					return billNoMenuRedirect.billNoMenuFormat(value,row,index,title);
				}},
				{field : 'paymentQty',title : '付款数量',width : 80},
				{field : 'paymentAmount',title : '付款金额',width : 80, align:'left'},
				{field : 'billDate',title : '单据日期',width : 100},	
				{field : 'createUser',title : '制单人',width : 100},
				{field : 'createTime',title : '制单日期',width : 150},
				{field : 'auditor',title : '审核人',width : 100},
				{field : 'auditTime',title : '审核日期',width : 150}]" 
			jsonExtend='{
				onDblClickRow:function(rowIndex, rowData){
					billController.loadDetail(rowIndex,rowData);
				},
				onLoadSuccess:function(){
         			showBaroqueColumns();
				}
		}'/>
	</div>
</div>
