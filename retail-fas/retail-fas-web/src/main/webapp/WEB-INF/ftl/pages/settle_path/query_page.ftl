<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar-query" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.querySearch()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.queryClear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action":"dialog.queryExport()", "type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="querySearchForm" id="querySearchForm" method="post">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
									<th>路径名称： </th><td><input class="ipt"  name="name" id="name" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.subdatagrid id="queryDataGridDiv"
						    	loadUrl="" saveUrl=""   defaultColumn="" checkOnSelect="false"
						    	    isHasToolBar="false"  divToolbar=""  onClickRowEdit="false" singleSelect="false"
								    pagination="true" rownumbers="true" enableHeaderClickMenu="false"
						           	columnsJsonList="[
								              {field:'pathNo',title : '路径编码',width : 100, align : 'left',sortField:'path_no',sortable:true},
								              {field:'name',title : '路径名称',width : 100, align : 'left',sortField:'name',sortable:true},
								              {field:'billTypeName',title : '单据类型',width : 125, align : 'left',sortField:'bill_type',sortable:true},
								              {field:'settleCategoryName',title : '结算大类',width : 90, align : 'center',sortField:'settle_category_no',sortable:true},
								              {field:'styleName',title : '新旧款',width : 90, align : 'center',sortField:'style_no',sortable:true,
								              	formatter : function(value){
													if(value == null || value == ''){
														return '全部';
													}
													return value;
												}
								              },
								              {field:'brandUnitName',title:'品牌部',width:120, align : 'left',halign:'center'},
								              {field:'startDate',title:'启用日期',width:100, align : 'center',sortField:'start_date',sortable:true},
								  			  {field:'endDate',title : '终止日期',width : 100, align : 'center',sortField:'end_date',sortable:true},
								  			  {field:'auditStatusName',title : '审批状态',width : 70, align : 'center',sortField:'audit_status',sortable:true},
								  			  {field : 'pathOrder', title : '路径次序', width : 80, align : 'center', notexport:true},
										      {field : 'companyNo', title : '公司编码', width : 80, align : 'left', notexport:true},
										      {field : 'companyName', title : '公司名称', width : 240, align : 'left', notexport:true},
										      {field : 'financialBasisText', title : '结算依据', width : 100, align : 'center',notexport:true},
										      {field : 'updateUser', title : '修改人', width : 100, align : 'center',notexport:true},
										      {field : 'updateTime', title : '修改时间', width : 140, align : 'center',notexport:true}
						             ]"
						             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
						           	  		
						             }}'
						             subGridColumnsJsonList="[
						             	  {field : 'pathOrder', title : '路径次序', width : 80, align : 'left',exportType:'number'},
									      {field : 'companyNo', title : '公司编码', width : 80, align : 'left'},
									      {field : 'companyName', title : '公司名称', width : 240, align : 'left'},
									      {field : 'financialBasisText', title : '结算依据', width : 100, align : 'left'},
									      {field : 'updateTime', title : '修改时间', width : 140, align : 'left'}
						             ]" 
					        	/>
			</div>
	 	</div>
	</div>
</div>