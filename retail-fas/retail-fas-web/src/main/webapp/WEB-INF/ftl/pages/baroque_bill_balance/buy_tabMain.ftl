<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"baroqueBuyBalance.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"baroqueBuyBalance.clear()","type":0},
			{"id":"top_btn_delete","title":"删除","iconCls":"icon-del","action":"baroqueBuyBalance.batchDel()","type":3},
			{"id":"top_btn_1","title":"审核","iconCls":"icon-aduit","action":"baroqueBuyBalance.batchVerify(1)","type":31} ,
			{"id":"top_btn_2","title":"反审核","iconCls":"icon-aduit","action":"baroqueBuyBalance.batchVerify(0)","type":32},
			{"id":"top_btn_next","title":"生成请款单","iconCls":"icon-build-some","action":"baroqueBuyBalance.generateAskPaymentBill()","type":0}
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
						    <td><input class="ipt"  name="status" combobox="balanceStatus" fliterType="SEND_FINANCE_CONFIRM,RECEIVE_BUSSINESS_CONFIRM,RECEIVE_FINANCE_CONFIRM,MAKE_INVOICE,MAKE_INVOICE,INVALID"/></td>
							<th>制单人：</th>
						    <td><input class="ipt" name="createUser"/></td>   
						    <th>审核人：</th>
						    <td><input class="ipt" name="auditor"/></td>
						</tr>
						<tr>
							<th>公司：</th>
                            <td><input class="ipt"  multiSearch="company"  notGroupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
                            <th>供应商：</th>
                            <td><input class="ipt"  multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
							<th>品牌部：</th>
                            <td><input class="ipt"  multiSearch="brandUnit"   /><input type="hidden" name="multiBrandUnitNo"></td>
                            <th>大类：</th>
                            <td><input class="ipt"  multiSearch="category"  /><input type="hidden" name="multiCategoryNo"></td>
						</tr>
						<tr>
							<th>结算期：</th>
						    <td><input class="easyui-datebox ipt" defaultValue="startDate" name="balanceStartDate" id="startDate" data-options="maxDate:'endDate'" /></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox ipt" defaultValue="endDate"  name="balanceEndDate" id="endDate" data-options="minDate:'startDate'" /></td>
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
			frozenColumns="[
					{field:'ck',title:'',checkbox:true,notexport:true},
					{field : 'buyerName',title : '公司',width : 200, align:'left'},
					{field : 'salerName',title : '供应商',width : 200, align:'left'},
					{field : 'brandUnitName',title : '品牌部',width : 100, align:'left'},
					{field : 'categoryName',title : '大类',width : 220, align:'left'},
					{field : 'currencyCode',hidden:'true'},
					{field : 'standardCurrencyCode',hidden:'true'},
					{field : 'standardCurrencyName',hidden:'true'},
					{field : 'currencyName',hidden:'true'},
					{field : 'standardAmount',hidden:'true'}]"
			columnsJsonList="[	
				{field : 'balanceDate',title : '结算日',width : 100},	
				{field : 'balanceStartDate',title : '结算开始日期',width : 100},
				{field : 'balanceEndDate',title : '结算结束日期',width : 100},
				{field : 'outQty',title : '进货数量',width : 100},
				{field : 'outAmount',title : '进货金额',width : 100},
				{field : 'returnQty',title : '退货数量',width : 100},
				{field : 'returnAmount',title : '退货金额',width : 100},
				{field : 'customReturnQty',title : '客残数量',width : 100},
				{field : 'customReturnAmount',title : '客残金额',width : 100},
				{field : 'deductionAmount',title : '其他扣项',width : 100},
				{field : 'balanceQty',title : '应付数量',width : 100},
				{field : 'balanceAmount',title : '应付金额',width : 100},
				{field : 'billNo',title : '单据编号',width : 150},
				{field : 'statusName',title : '单据状态',width : 100},
				{field : 'askPaymentNo',title : '请款单编号',width : 150, formatter: function(value,row,index){
					return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HI-请款单');
				}},
				{field : 'createUser',title : '制单人',width : 100},
				{field : 'createTime',title : '制单日期',width : 150},
				{field : 'auditor',title : '审核人',width : 100},
				{field : 'auditTime',title : '审核日期',width : 150}]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				baroqueBuyBalance.loadDetail(rowIndex,rowData);
		}}'/>
	</div>
</div>
