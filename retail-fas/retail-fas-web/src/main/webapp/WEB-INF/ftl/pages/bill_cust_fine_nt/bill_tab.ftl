<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false">
		<@p.toolbar id="toolbar2" listData=[
			{"id":"bill-btn-search","title":"查询","iconCls":"icon-search","action":"_fasBillController.search()","type":0},
			{"id":"bill-btn-clear","title":"清空","iconCls":"icon-empty","action":"_fasBillController.clear()","type":0},
			{"id":"bill-btn-del","title":"删除","iconCls":"icon-del","action":"_fasBillController.batchDel()","type":0},		
			{"id":"bill-btn-export","title":"导出","iconCls":"icon-export","action":"_fasBillController.exportExcel()","type":0}			
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
							<th>结算主体：</th>
                            <td><input class="ipt"  name="companyName"/></td>
                            <th>客户：</th>
                            <td><input class="ipt"  name="customerName"/></td>
						</tr>
				    </tbody>
				</table>   
	        </form>
        </div>
     </div> 
	<#-- end -->
	
	<div data-options="region:'center',border:false">
		<@p.datagrid id="billTabDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20"
			columnsJsonList="[
				{field:'ck',checkbox:true,notexport:true},
				{field : 'billNo',title : '单据编号',width : 120},
				{field : 'companyName',title : '结算主体',width : 120},
				{field : 'customerNo',title : '客户编码',width : 100},
				{field : 'customerName',title : '客户名称',width : 120},
				{field : 'marginAmount',title : '合同保证金',width : 90},
				{field : 'recedMarginAmount',title : '保证金余额',width : 90},
				{field : 'fineAmount',title : '罚没金额',width : 90},
				{field : 'createUser',title : '制单人',width : 90},
				{field : 'createTime',title : '制单时间',width : 120},
				{field : 'statusName',title : '单据状态',width : 90}
			]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				_billCustFineNt.loadDetail(rowIndex, rowData);
		}}'/>
	</div>
</div>
