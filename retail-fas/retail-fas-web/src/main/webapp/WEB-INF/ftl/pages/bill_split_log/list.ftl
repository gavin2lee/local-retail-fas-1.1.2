<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>拆单设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_split_log/BillSplitLog.js?version=${version}"></script>
</head>
<body>
	<@p.commonSetting search_url="/list.json" 
					  datagrid_id="dataGridDiv" 
					  export_url="/do_fas_export"
					  export_title="拆单日志信息导出"
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="600" 
					  dialog_height="300"
					  primary_key="id"/>
<div id="mainTab_" class="easyui-tabs" data-options="fit:true,plain:true" >
    <div data-options="title:'拆单日志查询'">
        <div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" class="toolbar-region">
		     <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"dialog.search()", "type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.clear()", "type":0},
				 {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":0}
	           ]
			/>
		</div>
	
		<div  data-options="region:'center',border:false">
	    	<div class="easyui-layout" data-options="fit:true">
				<!--搜索start-->
				<div data-options="region:'north',border:false" >
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
										<th>单据编码： </th><td><input class="ipt"  name="refBillNo" id="refBillNo" /></td>
										<th>发货时间：</th>
								 		<td><input class="easyui-datebox ipt"  name="startDate" id="startDate" data-options="maxDate:'endDate'"/></td>
										<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<td><input class="easyui-datebox ipt" name="endDate" id="endDate" data-options="minDate:'startDate'"/></td>
										<th>状态： </th>
										<td>
											<input class="ipt easyui-statusbox"  name="processStatus" id="processStatus" data-options="data:[{'value':'0','text':'成功'},{'value':'1','text':'失败'}]"/>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
				<!--列表-->
	        	<div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
			              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
				           rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 'refBillNo', title : '单据编码', width : 140, align : 'left'},
				                  {field : 'billTypeName', title : '单据类型', width : 160, align : 'left'},
				                  {field : 'sendOutDate', title : '发货时间', width : 100, align : 'left'},
				                  {field : 'splitTime', title : '拆单时间', width : 140, align : 'left'},
				                  {field:'processStatusName',title : '状态',width : 100, align : 'left'},
				                  {field:'failureReason',title : '失败原因',width : 260, align : 'left'}
				              ]" 
					          jsonExtend='{
		                           onDblClickRow:function(rowIndex, rowData){
				                	 
				                   } 
			                 }'
	                 />
				</div>
		 	</div>
		</div>
		
		<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		     <form name="dataForm" id="dataForm" method="post"  class="pd10">
		     	<div id="dtl_detail">
					<table cellpadding="1" cellspacing="1" class="form-tb">
					   <tr height="40">
					      <td width="110" align='right'>开始日期：</td>
					      <td width="140" align='left'>
					      	 <input class="easyui-validatebox easyui-datebox ipt" style="width:140px" name="startDate" id="startDate" required="true"/>
					      </td>
					   </tr>
					   <tr height="40">
					   	  <td width="110" align='right'>结束日期：</td>
					      <td width="140" align='left'>
					      		<input class="easyui-validatebox easyui-datebox ipt" style="width:140px" name="endDate" id="endDate" required="true"/>
					      </td>
					   </tr>
					   <tr height="40">
					   	  <td width="110" align='right'>品牌编码：</td>
					      <td width="140" align='left'>
					      		<input class="ipt" style="width:140px" name="brandNo" id="brandNo_"/>
					      </td>
					   </tr>
					   <tr height="40">
					   	  <td width="110" align='right'>单据编码：</td>
					      <td width="140" align='left'>
					      		<input class="ipt" style="width:140px" name="billNo" id="billNo_"/>
					      </td>
					   </tr>
					</table>
				</div>
			 </form>
		 </div>	
		</div>
	</div>
	 <div data-options="title:'结算主体调整'">
	 	<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false" class="toolbar-region">
			     <@p.toolbar id="toolbar-query" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.querySearch()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.queryClear()", "type":0},
		             {"id":"btn-split","title":"调整","iconCls":"icon-redo", "action":"dialog.showSettlePath()", "type":4}
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
								    		<th>采购公司： </th>
											<td>
												<input class="easyui-company ipt"  name="buyerName" id="companyNameId" 
												data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0',inputNoField:'buyerNoCondition',inputNameField:'companyNameId',inputWidth:160,isDefaultData:false"/>
												<input type="hidden" name="buyerNo" id="buyerNoCondition" />
											</td>
											<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
											<td>
												<input class="easyui-supplier ipt"  name="supplierName" id="supplierNameId" data-options="inputNoField:'supplierNoCondition',inputNameField:'supplierNameId',inputWidth:160"/>
												<input type="hidden" name="supplierNo" id="supplierNoCondition" />
											</td>
											<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
											<td>
											<input class="easyui-brand ipt"  name="brandName" id="brandNameId" data-options="inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:160"/>
												<input type="hidden" name="brandNo" id="brandNoId"/>
											</td>
											<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
											<td>
												<input class="easyui-categorybox"   name="categoryNo" id="oneLevelCategoryNo" data-options="width:160"/>
											</td>
										</tr>
										<tr>
											<th>发货日期： </th>
											<td>
												<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="maxDate:'sendDateEnd'" style="width:150px;" readonly="true" />
											</td>
											<th>至： </th>
											<td>
												<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="minDate:'sendDateStart'" style="width:150px;"  readonly="true"/>
											</td>
											<th>结算类型：</th>
								    		<td>
								    			<select class="easyui-combobox"  name="balanceType" id="balanceType"/>
								    		</td>
											<th>单据类型：</th>
								    		<td>
								    			<select class="easyui-combobox"  name="billType" id="billType" data-options="editable:false,valueField:'code',textField:'name',width:'auto',url:BasePath + '/area_balance_common/getBillType?showType=ASN,RETURNOWN'"/>
								    		</td>
										</tr>
										<tr>
											<th>单据编号： </th>
											<td>
												<input class="easyui-validatebox ipt"  name="originalBillNo" id="originalBillNo" style="width: 150px;"/>
											</td>
								    		<th>商品编码： </th>
											<td>
												<input class="easyui-item ipt"  name="itemName" id="itemNameId" data-options="inputCodeField:'itemCodeCondition',inputNameField:'itemNameId',inputWidth:160"/>
												<input type="hidden" name="itemCode" id="itemCodeCondition" />
											</td>
											
										</tr>
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<!--列表-->
		        	<div data-options="region:'center',border:false">
		        		<@p.datagrid id="queryDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
			              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
				           rownumbers="true" singleSelect="false"  pageSize="20"
				           frozenColumns="[{field : 't', checkbox:true, width : 30, notexport:true}]"
				           columnsJsonList="[
			                  {field : 'originalBillNo', title : '单据编号', width : 150,align:'left'},
			                  {field : 'billTypeName', title : '单据类型', width : 100},
			                  {field : 'sendDate', title : '发货日期', width : 100},
			                  {field : 'buyerNo', title : '采购公司编码',  width : 120, hidden:true},
			                  {field : 'buyerName', title : '采购公司',  width : 180,align:'left',halign:'center'},
			                  {field : 'salerNo', title : '结算公司编码',  width : 120, hidden:true},
			                  {field : 'salerName', title : '结算公司',  width : 180,align:'left',halign:'center'},
			                  {field : 'supplierNo', title : '供应商编码',  width : 120, hidden:true},
			                  {field : 'supplierName', title : '供应商',  width : 180,align:'left',halign:'center'},
			                  {field : 'brandNo', title : '品牌编号',  width : 120, hidden:true},
			                  {field : 'brandName', title : '品牌', width : 80},
			                  {field : 'oneLevelCategoryNo', title : '大类编号', width : 100, hidden:true},
			                  {field : 'oneLevelCategoryName', title : '大类', width : 50},
			                  {field : 'itemNo', title : '商品编号', width : 100, hidden:true},
			                  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称',  width : 150,align:'left'},
			                  {field : 'cost', title : '单价', width : 80,align:'right',halign:'center'},
			                  {field : 'sendQty', title : '数量', width : 80,align:'right',halign:'center'},
			                  {field : 'balanceNo', title : '结算状态', width : 100,align:'center'}]" 
					          jsonExtend='{
		                           onDblClickRow:function(rowIndex, rowData){
				                	 
				                   } 
			                 }'
	                 />
					</div>
					
			 	</div>
			</div>
			
			<div id="settlePathPanel" class="easyui-dialog" data-options="closed:true">
			 	<div class="easyui-layout" data-options="fit:true">
			 		<div data-options="region:'center',border:false" >
			 			<@p.datagrid id="settlePathDataGrid" emptyMsg = ""
			 				selectOnCheck="true" checkOnSelect="true" singleSelect="true"
							isHasToolBar="false"  onClickRowEdit="false" pagination="false"
							frozenColumns="[
								{field : 't', checkbox:true, width : 30, notexport:true},
								{field:'pathNo',title:'路径编码',width:80},
								{field:'name',title:'路径名称',width:100}]"
							columnsJsonList="[
								{field:'supplierGroupName',title:'供应商组',width:80},
								{field:'middleCompanyName',title:'中间结算公司（H）',width:160},
								{field:'middleFinancialBasisText',title:'结算依据（H）',width:100},
								{field:'companyName',title:'采购公司（A）',width:160},
								{field:'financialBasisText',title:'结算依据（A）',width:100},
								{field:'startDate',title:'启用日期',width:80},
								{field:'endDate',title:'终止日期',width:80},
								{field:'auditStatusName',title:'审批状态',width:80}]"
						  		 jsonExtend='{onDblClickRow:function(rowIndex, rowData){
											             }}' 
						  />
			 		</div>
			 	</div>
			</div>
		</div>
	 </div>
</div>
</body>
</html>