
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
			<@p.toolbar id="main_bar" listData=[
		    {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"ctrl.returnTabs(0)","type":0},
			{"id":"btn-search1","title":"查询","iconCls":"icon-search", "type":0,"action":"ctrl.search()"},
			{"id":"btn-remove","title":"清空","iconCls":"icon-empty","type":0,"action":"ctrl.clear()"},
			{"id":"btn-del","title":"删除","iconCls":"icon-del","action":"ctrl.batchDel()","type":3},		         
	        {"id":"mian_btn_aduit","title":"确认","iconCls":"icon-aduit","action":"ctrl.batchConfirm()","type":18},	
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
	       		 	 <tr>
					    <th><span class="ui-color-red">*</span>公司名称：</th>
					 	<td>
					 		<input class="ipt easyui-company"  name="companyName" id="companyName1" data-options="inputWidth:170,required:true,inputNoField:'companyNo1', inputNameField:'companyName1'"/>
							<input type="hidden"  name="companyNo" id="companyNo1" 	/>
                         </td>
					    <th>单据编号：</th>
					    <td><input class="ipt" name="billNoCondition" id="billNoCondition"/></td>
					    <th>单据状态：</th>
   						<td><input class="easyui-combobox" style="width:130px" name="status" id="statusBox" /></td>
   					</tr>
   					<tr>
					    <th>调整年份：</th>
					    <td><input class="ipt" name="yearCondition" id="yearCondition"/></td>
					    <th>调整月份：</th>
   						<td><input class="ipt" name="monthCondition" id="monthCondition" /></td>
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
				{field : 'ck',notexport : true,checkbox : true},
				{field : 'billNo',title : '单据编号',width : 140,align:'left'},
				{field : 'statusStr',title : '单据状态',width : 80,align:'left'},
				{field : 'companyNo',title : '公司编码',width : 120,align:'left'},
				{field : 'companyName',title : '公司名称',width : 180,align:'left'},
				{field : 'year',title : '调整年份',width : 80,align:'right'},
				{field : 'month',title : '调整月份',width : 80,align:'right'},
				{field : 'createUser',title : '制单人',width : 100,align:'left'},
				{field : 'createTime',title : '制单时间',width : 150},
				{field : 'auditor',title : '审核人',width : 100,align:'left'},
				{field : 'auditTime',title : '审核时间',width : 150}
			]" 
			jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				//console.log(rowIndex);
				ctrl.billList.onMainDblClickRow(rowIndex,rowData);
		}}'/>
	</div>
</div>
