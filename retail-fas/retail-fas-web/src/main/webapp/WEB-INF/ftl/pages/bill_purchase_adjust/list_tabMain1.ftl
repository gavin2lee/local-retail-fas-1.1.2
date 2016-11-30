
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
			<@p.toolbar id="main_bar" listData=[
		    {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"ctrl.returnTabs(0)","type":0},
			{"id":"btn-search1","title":"查询","iconCls":"icon-search", "type":0,"action":"ctrl.search()"},
			{"id":"btn-remove","title":"清空","iconCls":"icon-empty","type":0,"action":"ctrl.clear()"},
			{"id":"btn-del","title":"删除","iconCls":"icon-del","action":"ctrl.batchDel()","type":3},		         
	        {"id":"mian_btn_aduit","title":"确认","iconCls":"icon-aduit","action":"ctrl.batchConfirm()","type":18},	
			{"id":"top_btn_2","title":"反确认","iconCls":"icon-cancel","action":"ctrl.batchCancel()","type":10},
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"ctrl.exportExcel(1)","type":4}
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
                        <th>总部公司：</th>
                        <td>
                        	<input class="easyui-company ipt"  name="salerName" id="salerNameCon1" 
                        	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'salerNoCon1',inputNameField:'salerNameCon1',inputWidth:160,isDefaultData : false"/>
                       		<input type="hidden" name="salerNo" id="salerNoCon1"/>
                       	</td>
                       	 <th>地区公司：</th>
                        <td>
                             <input class="easyui-company ipt"   name="buyerName" id="buyerNameCon1" 		
                             data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNoCon1',inputNameField:'buyerNameCon1',inputWidth:160,isDefaultData : false"/>
                             <input type="hidden" name="buyerNo" id="buyerNoCon1"/>
                        </td>
                         	<th>供应商： </th>
							<td>
								<input class="easyui-supplier  ipt"  name="supplierName" id="supplierNameId1" data-options="inputNoField:'salerNoId1',inputNameField:'supplierNameId1',inputWidth:150"/>
								<input type="hidden" name="supplierNo"  id="salerNoId1"/>
                        </tr>
                        <tr>
                       		<th>单据编号：</th>
                            <td ><input class="ipt disableEdit" name="billNo" id="billNo"/></td>
                            <th>单据状态：</th>
   						<td><input class="easyui-combobox" style="width:130px" name="status" id="statusBox" /></td>
                        </tr>
                </tbody>
			</table>   
        </form>
        </div>
     </div> 
	<#-- end -->
	
	<div data-options="region:'center',border:false">
		<@p.datagrid id="mainDataGrid"  
			isHasToolBar="false" height="502"  onClickRowEdit="false" singleSelect="false" pageSize="20" 
			columnsJsonList="[
						{field:'ck',checkbox:true,notexport:true},
						{field : 'salerName',title : '总部公司',width : 220, align:'left',halign:'center'},
						{field : 'buyerName',title : '地区公司',width : 220, align:'left',halign:'center'},
						{field : 'supplierName',title : '供应商',width : 220, align:'left',halign:'center'},
						{field : 'purchaseDate',title : '日期',width : 100},
						{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
						{field : 'statusStr',title : '单据状态',width : 150,align:'center'},
						{field : 'status',hidden : 'true',title : '单据状态',width : 150,align:'center'},
						{field : 'balanceNo',title : '结算单号',width : 150,align:'center'},
						{field : 'createUser',title : '审核人',width: 100,align:'center'},
						{field : 'createTime',title : '审核时间',width: 150,align:'center'},
						{field : 'createUser',title : '创建人',width: 100,align:'center'},
						{field : 'createTime',title : '创建时间',width: 150,align:'center'},
						{field : 'updateUser',title : '修改人',width: 100,align:'center'},
						{field : 'updateTime',title : '修改时间',width: 150,align:'center'}
						]" 
					jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				           	  		 editor.editRow(rowIndex, rowData);
					
						}
					}'/>
	</div>
</div>
