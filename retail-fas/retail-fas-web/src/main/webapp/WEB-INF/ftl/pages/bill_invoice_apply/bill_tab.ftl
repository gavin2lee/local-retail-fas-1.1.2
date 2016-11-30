<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false">
		<@p.toolbar id="toolbar2" listData=[
			 {"id":"bill-btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"bill-btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             <#-- 
             {"id":"bill-btn-batchAudit","title":"审核","iconCls":"icon-aduit","type":0},
             {"id":"bill-btn-batchAntiAudit","title":"反审核","iconCls":"icon-aduit","action":"bill_invoice_apply.doAntiAudit()","type":0},
             -->
             {"id":"bill-btn-export","title":"导出","iconCls":"icon-export","type":0}
           ]
		/>
		<div class="search-div">
	      	<form name="searchForm" id="searchForm" method="post">
				<table class="form-tb">
					<col width="80" />
					<col />
					<col width="80" />
					<col />
					<col width="80" />
					<col />
					<col width="80" />
					<col />
					<col width="80" />
					<col />
					<tbody>
						<tr>
							<th>开票方： </th><td><input class="ipt"  name="companyName" id="companyName_" /></td>
							<th>客户： </th><td><input class="ipt"  name="customerName" id="customerName_" /></td>
							<th>单据状态： </th>
							<td>
								<select class="easyui-combobox" name="auditStatus" style="width:130px;" id="auditStatus">
								 	 <option value=""></option>
						      	 	 <option value="1">已审核</option>
						      	 	 <option value="0">未审核</option>
						      	 </select>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center',border:false">
	      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
	              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
		           rownumbers="false" singleSelect="true"  
		           columnsJsonList="[
		                  {field : 't', checkbox:true, width : 30, notexport:true},
		                  {field : 'companyName', title : '开票方', width : 120, align : 'left'},
		                  {field : 'billNo', title : '单据编码', width : 100, align : 'left'},
		                  {field : 'createTime', title : '申请日期', width : 90, align : 'left'},
		                  {field : 'customerName', title : '客户', width : 120, align : 'left'},
		                  {field:'totalAmount',title : '金额',width : 80, align : 'left'},
		                  {field:'taxRegistryNo',title : '纳税人识别号',width : 90, align : 'left'},
		                  {field:'payTime',title : '客户交票日期',width : 90, align : 'left'},
		                  {field:'auditStatus',title : '单据状态',width : 70, align : 'left', formatter : fas_common.formatter.audit},
		                  {field:'createUser',title : '制单人',width : 80, align : 'left'},
		                  {field:'createTime',title : '制单日期',width : 120, align : 'left'}
		              ]" 
			          jsonExtend='{
                           onDblClickRow:function(rowIndex, rowData){
		                	  //双击方法
		                   	   fas_common_bill.loadDetail(rowIndex, rowData);
		                   }
	                 }'
         />
	</div>
</div>