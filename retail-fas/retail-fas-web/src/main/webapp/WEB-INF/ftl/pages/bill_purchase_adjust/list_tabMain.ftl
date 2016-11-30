<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
			<@p.toolbar id="main_bar" listData=[
		    {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"btn-search1","title":"查询","iconCls":"icon-search", "type":0,"action":"billPurchaseAdjustBak.search()"},
			{"id":"btn-remove","title":"清空","iconCls":"icon-empty","type":0,"action":"billPurchaseAdjustBak.clear()"},
			{"id":"btn-del","title":"删除","iconCls":"icon-del","action":"billPurchaseAdjustBak.batchDel()","type":3},		         
	        {"id":"mian_btn_aduit","title":"确认","iconCls":"icon-aduit","action":"billPurchaseAdjustBak.batchOperate(1)","type":18},	
			{"id":"top_btn_2","title":"反确认","iconCls":"icon-cancel","action":"billPurchaseAdjustBak.batchOperate(0)","type":10},
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"pe_util.doExport('mainDataGrid','/bill_purchase_adjust/export_data','采购入库调整单导出')","type":4}
		]/>
		<div class="search-div">
        <form name="searchForm" id="searchForm" method="post">
            <table class="form-tb" >
			    <col width="100" />
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
						    <th>供应商：</th>
                            <td><input type="ipt" name="supplierName" multiSearch="supplier" /><input type="hidden" name="multiSupplierNo"/></td>	
                            <th>地区公司：</th>
                            <td><input type="ipt" name="buyerName" multiSearch="company" /><input type="hidden" name="multiBuyerNo"/></td>	
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
	
	<div data-options="region:'center',border:true">
		<@p.datagrid id="mainDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false" singleSelect="false" pageSize="20" 
			columnsJsonList="[
						{field:'ck',checkbox:true,notexport:true},
						{field : 'salerName',title : '总部公司',width : 220, align:'left',halign:'center'},
						{field : 'buyerName',title : '地区公司',width : 220, align:'left',halign:'center'},
						{field : 'supplierName',title : '供应商',width : 220, align:'left',halign:'center'},
						{field : 'billNo',title : '单据编号',width : 150},
						{field : 'status',hidden : 'true',title : '单据状态',width : 80},
						{field : 'purchaseDate',title : '日期',width : 100},
						{field : 'statusStr',title : '单据状态',width : 150,align:'center'},
						{field : 'createUser',title : '创建人',width: 100,align:'center'},
						{field : 'createTime',title : '创建时间',width: 150,align:'center'},
						{field : 'auditor',title : '审核人',width: 100,align:'center'},
						{field : 'auditTime',title : '审核时间',width: 150,align:'center'}
						]" 
					jsonExtend='{onDblClickRow:function(rowIndex, rowData){
						billPurchaseAdjustBak.loadDetail(rowIndex,rowData);
						}
					}'/>
	</div>
</div>
