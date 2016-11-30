<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
		<@p.toolbar id="main_bar"	listData=[
			{"id":"mian_btn_search","title":"查询","iconCls":"icon-search","action":"buyBalance.search()","type":0},
			{"id":"mian_btn_clear","title":"清空","iconCls":"icon-empty","action":"buyBalance.clear()","type":0},
			{"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"buyBalance.batchDel()","type":0},		
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"buyBalance.main_exportExcel()","type":0},
			{"id":"top_btn_back","title":"业务确认","iconCls":"icon-prev","action":"buyBalance.batchOperate(2)","type":0} ,
			{"id":"top_btn_next","title":"财务确认","iconCls":"icon-next","action":"buyBalance.batchOperate(3)","type":0}					
		]/>
		<div class="search-div">
	        <form name="searchForm" id="searchForm" method="post">
	         <input type="hidden" name="balanceType" id="balanceType" value="1">
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
						    <td><input class="easyui-validatebox ipt" name="billNo" id="billNoCondition"/></td>
							<th>结算主体：</th>
                            <td><input class="easyui-validatebox  ipt"  name="buyerName"  data-options="required:true"/><input type="hidden" name="buyerNo"/></td>
                            <th>供应商：</th>
                            <td><input class="easyui-validatebox  ipt"  name="salerName"  data-options="required:true"/><input type="hidden" name="salerNo"/></td>
						</tr>
						    <th>结算期间：</th>
						    <td><input class="easyui-datebox"  name="balanceStartDate"/></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox"  name="balanceEndDate"/></td>
						<tr>
							<th>单据状态：</th>
						    <td><input class="easyui-combobox  ipt"  name="status" data-options="required:true,valueField:'code',textField:'name',url:BasePath + '/lookup_entry/getLookupEntry?lookupId=36'"/></td>
						    <th>制单人：</th>
						    <td><input class="easyui-validatebox ipt" name="createUser"/></td>
						    <th>制单日期：</th>
						    <td><input class="easyui-datebox"  name="createTimeStart" id="createTimeStart" /></td>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<td> <input class="easyui-datebox"  name="createTimeEnd" id="createTimeEnd" /></td>
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
			rownumbers="true" 
			columnsJsonList="[
				{field:'ck',checkbox:true,notexport:true},
				{field : 'billNo',title : '单据编号',width : 120},
				{field : 'statusName',title : '单据状态',width : 100},
				{field : 'buyerName',title : '结算主体',width : 150},
				{field : 'salerName',title : '供应商',width : 150},
				{field : 'brandName',title : '品牌',width : 100},			
				{field : 'balanceStartDate',title : '结算开始日期',width : 120},
				{field : 'balanceEndDate',title : '结算结束日期',width : 120},
				{field : 'entryAmount',title : '入库金额',width : 100},
				{field : 'returnAmount',title : '退残金额',width : 100},
				{field : 'deductionAmount',title : '其他扣项',width : 100},
				{field : 'payableAmount',title : '应付金额',width : 100},
				{field : 'createUser',title : '创建人',width : 100},
				{field : 'createTime',title : '创建日期',width : 100}]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				buyBalance.loadDetail(rowIndex,rowData);
		}}'/>
	</div>
</div>
