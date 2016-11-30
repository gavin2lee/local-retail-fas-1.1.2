<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
		    {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"saleBalance.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"saleBalance.clear()","type":0},
			{"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"saleBalance.batchDel()","type":3},		
			{"id":"top_btn_1","title":"确认","iconCls":"icon-aduit","action":"saleBalance.batchOperate(2)","type":18} ,
			{"id":"top_btn_3","title":"反确认","iconCls":"icon-cancel","action":"saleBalance.batchOperate(0)","type":10} ,
			{"id":"top_btn_4","title":"批量生成","iconCls":"icon-build-some","action":"saleBalance.showDialog()","type":55},
			{"id":"mian_btn_build_biing","title":"生成开票申请","iconCls":"icon-build-some","type":78,"action":"invoiceApplyBalance.invoiceApply('mainDataGrid','2')"}, 		
			{"id":"mian_btn_export","title":"列表导出","iconCls":"icon-export","action":"saleBalance.doExport('mainDataGrid','/bill_balance/hq/export','结算单列表导出',{type:'balance'})","type":4},
		    {"id":"top_btn_5","title":"明细导出","iconCls":"icon-export","action":"saleBalance.batchExportBalance()","type":4}	
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
						    <td><input class="ipt" name="status" combobox="balanceStatus" fliterType="SEND_BUSSINESS_CONFIRM,RECEIVE_BUSSINESS_CONFIRM,MAKE_APPLY_INVOICE,ASK_PAYMENT"/></td>
							<th>制单人：</th>
						    <td><input class="ipt" name="createUser"/></td>
						    <th>审核人：</th>
						    <td><input class="ipt" name="auditor"/></td>
						</tr>
						<tr>
						    <th>公司：</th>
                            <td><input class="ipt"  multiSearch="company"  notGroupLeadRole /><input type="hidden" name="multiSalerNo"></td>
                            <th>客户：</th>
                            <td><input class="ipt"  multiSearch="dataAccess_company"  /><input type="hidden" name="multiBuyerNo"></td>
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
			 frozenColumns="[
				    {field:'ck',checkbox:true,notexport:true},
					{field : 'salerName',title : '公司',width : 220, align:'left',halign:'center'},
					{field : 'buyerName',title : '客户',width : 220, align:'left',halign:'center'},
					{field : 'brandUnitName',title : '品牌部',width : 80}	
			           ]"
			columnsJsonList="[
			    {field : 'categoryName',title : '大类',width : 80},
				{field : 'balanceDate',title : '结算日',width : 100},	
				{field : 'balanceStartDate',title : '结算开始日期',width : 100},
				{field : 'balanceEndDate',title : '结算结束日期',width : 100},
				{field : 'outQty',title : '出库数量',width : 100},
				{field : 'outAmount',title : '出库金额',width : 100,align:'right',halign:'center'},
				{field : 'returnQty',title : '原残数量',width : 100},
				{field : 'returnAmount',title : '原残金额',width : 100,align:'right',halign:'center'},
				{field : 'deductionQty',title : '扣项数量',width : 100},
				{field : 'deductionAmount',title : '扣项金额',width : 100,align:'right',halign:'center'},
				{field : 'customReturnAmount',title : '采购调整额',width : 100,align:'right',halign:'center'},
				{field : 'balanceQty',title : '应收数量',width : 100},
				{field : 'balanceAmount',title : '应收金额',width : 100,align:'right',halign:'center'},
				{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
				{field : 'invoiceApplyNo',title : '开票申请号',width : 150,align:'center',halign:'center', formatter: function(value,row,index){
					return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HI-开票申请');
				}},
				{field : 'statusName',title : '单据状态',width : 100,align:'center',halign:'center'},
				{field : 'createUser',title : '制单人',width : 100,align:'center',halign:'center'},
				{field : 'createTime',title : '制单日期',width : 150,align:'center',halign:'center'},
				{field : 'auditor',title : '审核人',width : 100,align:'center',halign:'center'},
				{field : 'auditTime' ,title : '审核日期',width : 150,align:'center',halign:'center'}
				]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				saleBalance.loadDetail(rowIndex,rowData);
		}}'/>
	</div>
</div>
