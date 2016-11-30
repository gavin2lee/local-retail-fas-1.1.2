<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
		    {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"billController.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"billController.clear()","type":0},
			{"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"billController.batchDel()","type":3},		
			{"id":"mian_btn_aduit","title":"提交审批","iconCls":"icon-aduit","action":"billController.batchOperate(1)","type":92},	
			{"id":"top_btn_cancel","title":"撤销","iconCls":"icon-cancel","action":"billController.batchOperate(0)","type":13} ,
			{"id":"top_btn_cancel1","title":"业务主管审批","iconCls":"icon-aduit","action":"billController.batchOperate(2)","type":93},
			{"id":"top_btn_cancel2","title":"财务人员审批","iconCls":"icon-aduit","action":"billController.batchOperate(3)","type":94},
			{"id":"top_btn_cancel4","title":"打回","iconCls":"icon-cancel","action":"billController.batchBack(99)","type":73},
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"billController.doExport('mainDataGrid','/bill_ask_payment/export','请款单列表导出')","type":4},
			{"id":"mian_btn_print","title":"打印","iconCls":"icon-print","action":"batchPrint()","type":0}				
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
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
						    <td><input class="ipt" combobox="auditStatus" name="status" fliterType="THREE_LEVEL_AUDIT,FOUR_LEVEL_AUDIT"/></td>
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
                            <th>结算类型：</th>
						    <td><input class="ipt" combobox="balanceType" name="balanceType"/></td>
						    <th>结算单号：</th>
						    <td><input class="ipt" name="balanceNo"/></td>
						</tr>
						<tr>
							<th>单据日期：</th>
						    <td><input class="easyui-datebox ipt"  name="billDateStart" id="billDateStart" data-options="maxDate:'billDateEnd'"/></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox ipt"  name="billDateEnd"  id="billDateEnd" data-options="minDate:'billDateStart'" /></td>
							<th>审核日期：</th>
						    <td><input class="easyui-datebox ipt"  name="auditTimeStart" id="auditTimeStart"  data-options="maxDate:'auditTimeEnd'" /></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox ipt"  name="auditTimeEnd" id="auditTimeEnd" data-options="minDate:'auditTimeStart'" /></td>
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
				{field : 'currencyName',title : '币种',width : 100},
				{field : 'allAmount',title : '请款金额',width : 100},
				{field : 'targetCurrencyName',hidden:true,title : '本位币',width : 100},
				{field : 'conversionFactor',hidden:true,title : '汇率',width : 100},
				{field : 'targetCurrencyAmount',hidden:true,title : '本位币金额',width : 100},
				{field : 'allQty',title : '数量',width : 100},	
				{field : 'balanceNo',title : '结算单编号',width : 150},
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
