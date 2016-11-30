<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
		    {"id":"top_btn_add","title":"查看明细","iconCls":"icon-xq","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"saleBalance.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"saleBalance.clear()","type":0},
			{"id":"top_btn_3","title":"确认","iconCls":"icon-aduit","action":"saleBalance.batchOperate(4)","type":10} ,	
			{"id":"top_btn_4","title":"反确认","iconCls":"icon-cancel","action":"saleBalance.batchOperate(2)","type":18},	
		    {"id":"top_btn_4","title":"打回","iconCls":"icon-cancel","action":"saleBalance.batchOperate(99)","type":73},
		    {"id":"top_btn_add","title":"生成请款单","iconCls":"icon-build-some","action":"generateAskPaymentBill()","type":82},
			{"id":"mian_btn_export","title":"结算列表导出","iconCls":"icon-export","action":"saleBalance.doExport('mainDataGrid','/bill_balance/hq/export','结算单列表导出',{type:'balance'})","type":4},
		    {"id":"top_btn_5","title":"结算单导出","iconCls":"icon-export","action":"saleBalance.batchExportBalance()","type":4}	
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
	            <table class="form-tb" >
				    <col width="80" />
				    <col  />
				    <col width="100" />
				    <col />
				    <col width="100" />
				    <col />
				    <col width="100"/>
				    <col />
				    <tbody>
				    	<tr>
				    		<th>单据编号：</th>
						    <td><input class="ipt" name="billNo"/></td>
						    <th>单据状态：</th>
						    <td><input class="ipt" name="extendStatus" combobox="balanceStatus" fliterType="SEND_BUSSINESS_CONFIRM,RECEIVE_BUSSINESS_CONFIRM,MAKE_APPLY_INVOICE,MAKE_INVOICE"/></td>
							<th>制单人：</th>
						    <td><input class="ipt" name="createUser"/></td>
						    <th>审核人：</th>
						    <td><input class="ipt" name="auditor"/></td>
						</tr>
						<tr>
						    <th>公司：</th>
                            <td><input class="ipt"  multiSearch="company"  groupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
                            <th>内部供应商：</th>
                            <td><input class="ipt"  multiSearch="dataAccess_company"  notGroupLeadRole/><input type="hidden" name="multiSalerNo"></td>
							<th>品牌部：</th>
                            <td><input class="ipt"  multiSearch="brandUnit"   /><input type="hidden" name="multiBrandUnitNo"></td>
                            <th>大类：</th>
                            <td><input class="ipt"  multiSearch="category"  /><input type="hidden" name="multiCategoryNo"></td>
						</tr>
						<tr>
							<th>结算期间：</th>
						    <td><input class="easyui-datebox ipt"  name="balanceStartDate" defaultValue="startDate"  id="startDate" data-options="maxDate:'endDate'" /></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox ipt"  name="balanceEndDate" defaultValue="endDate"  id="endDate" data-options="minDate:'startDate'" /></td>
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
				{field:'ck',checkbox:true,notexport:true},
				{field : 'billNo',title : '单据编号',width : 150},
				{field : 'askPaymentNo',title : '请款单编号',width : 150, formatter: function(value,row,index){
					return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AI-请款单');
				}},
				{field : 'extendStatusName',title : '单据状态',width : 100},
				{field : 'buyerName',title : '公司',width : 250, align:'left'},
				{field : 'salerName',title : '内部供应商',width : 250, align:'left'},
				{field : 'brandUnitName',title : '品牌部',width : 80},	
				{field : 'categoryName',title : '大类',width : 80},	
				{field : 'balanceStartDate',title : '结算开始日期',width : 100,align:'center',halign:'center'},
				{field : 'balanceEndDate',title : '结算结束日期',width : 100,align:'center',halign:'center'},
				{field : 'outQty',title : '入库数量',width : 100},
				{field : 'outAmount',title : '入库金额',width : 100,align:'right',halign:'center'},
				{field : 'returnQty',title : '原残数量',width : 100},
				{field : 'returnAmount',title : '原残金额',width : 100,align:'right',halign:'center'},
				{field : 'deductionQty',title : '扣项数量',width : 100},
				{field : 'deductionAmount',title : '扣项金额',width : 100,align:'right',halign:'center'},
				{field : 'customReturnAmount',title : '采购调整额',width : 100,align:'right',halign:'center'},
				{field : 'balanceQty',title : '应付数量',width : 100},
				{field : 'balanceAmount',title : '应付金额',width : 100,align:'right',halign:'center'},
				{field : 'createUser',title : '创建人',width : 100},
				{field : 'createTime',title : '创建日期',width : 150},
				{field : 'auditor',title : '审核人',width : 100},
				{field : 'auditTime',title : '审核日期',width : 150}]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				saleBalance.loadDetail(rowIndex,rowData);
		}}'/>
	</div>
</div>
