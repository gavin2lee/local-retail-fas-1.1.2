<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
		    {"id":"top_btn_add","title":"查看明细","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"peBalance.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"peBalance.clear()","type":0},
			{"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"peBalance.batchDel()","type":0},		
			{"id":"top_btn_back","title":"审核","iconCls":"icon-aduit","action":"peBalance.batchDoAudit(1)","type":0} ,
			{"id":"top_btn_next","title":"反审核","iconCls":"icon-aduit","action":"peBalance.batchDoAudit(0)","type":0},
			{"id":"top_btn_add","title":"生成请款单","iconCls":"icon-build-some","action":"generateAskPaymentBill()","type":0},
		    {"id":"top_btn_3","title":"导出","iconCls":"icon-export","action":"pe_util.doExport('mainDataGrid', '/pe_balance/export_data', '结算单列表',{});","type":0}
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
							<th>公司：</th>
                            <td><input class="ipt"  multiSearch="company"  notGroupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
                            <th>供应商：</th>
                            <td><input class="ipt"  multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
                            <th>结算期：</th>
						    <td><input class="easyui-datebox ipt" defaultValue="startDate" name="balanceStartDate" id="startDate" data-options="maxDate:'endDate'" /></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox ipt" defaultValue="endDate"  name="balanceEndDate" id="endDate" data-options="minDate:'startDate'" /></td>
						</tr>
				    	<tr>
				    		<th>单据编号：</th>
						    <td><input class="easyui-validatebox ipt" name="billNo"/></td>
						    <th>单据状态：</th>
						    <td><input class="ipt" name="status" combobox="peBalanceStatus" /></td>
							<th>制单人：</th>
						    <td><input class="ipt" name="createUser"/></td>
						    <th>审核人：</th>
						    <td><input class="ipt" name="auditor"/></td>
						</tr>
						<tr>
						    <th>制单日期：</th>
						    <td><input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'" /></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox ipt"  name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'" /></td>
						</tr>
				    </tbody>
				</table>   
	        </form>
        </div>
     </div> 
	<#-- end -->
	
	<div data-options="region:'center',border:false">
		<@p.datagrid id="mainDataGrid"  showFooter="true"
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			rownumbers="true" checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			columnsJsonList="[
				{field:'ck',title:'',checkbox:true,notexport:true},
				{field : 'buyerName',title : '公司',width : 250, align:'left'},
				{field : 'salerName',title : '供应商',width : 250, align:'left'},
			    {field : 'brandName',title : '品牌',width : 100},
			   	{field : 'peStatusName',title : '单据状态',width : 100},
				{field : 'balanceDate',title : '结算日',width : 100},	
				{field : 'balanceStartDate',title : '结算开始日期',width : 100},
				{field : 'balanceEndDate',title : '结算结束日期',width : 100},
				{field : 'outQty',title : '结算数量',width : 100},
				{field : 'outAmount',title : '结算金额',width : 100},
				{field : 'supplierAmount',title : '厂商金额',width : 100},
				{field : 'deductionAmount',title : '其他扣项',width : 100},
				{field : 'balanceQty',title : '应付数量',width : 100},
				{field : 'balanceAmount',title : '应付金额',width : 100},
				{field : 'billNo',title : '单据编号',width : 150},
				{field : 'askPaymentNo',title : '请款单编号',width : 150},
				{field : 'createUser',title : '制单人',width : 100},
				{field : 'createTime',title : '制单日期',width : 150},
				{field : 'auditor',title : '审核人',width : 100},
				{field : 'auditTime',title : '审核日期',width : 150}]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				peBalance.loadDetail(rowIndex,rowData);
		}}'/>
	</div>
</div>
