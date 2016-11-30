<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<title>库存单位成本</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/itemCost.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"dialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()", "type":4},
             {"id":"btn-ok","title":"生成成本","iconCls":"icon-ok","action":"dialog.generateCost()", "type":87},
             {"id":"btn-ok","title":"生成成本结果查询","iconCls":"icon-ok","action":"dialog.showAudit()", "type":87},
             {"id":"btn-ok","title":"分配成本","iconCls":"icon-ok","action":"dialog.updateCost()", "type":87}
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
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr height="40">
							 		<th align='right'><span class="ui-color-red">*</span>公司名称：</th>
							 		<td>
								 		<input class="ipt easyui-company" name="companyName" id="companyNameCondition" 
								 		data-options="required:'true',inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
				      					<input type="hidden" name="companyNo" id="companyNoCondition"/>
			      					</td>
							 		<th align='right'>商品编码：</th>
							 		<td>
							 			<input class="ipt easyui-item" name="itemCode" id="itemCodeCondition"
							 			 data-options="inputWidth:160,inputNoField:'itemNoCondition', inputCodeField:'itemCodeCondition'"/>
							  			<input type="hidden" name="itemNo" id="itemNoCondition"/>
							  		</td>
							 		<th align='right'><span class="ui-color-red">*</span>年份：</th>
							 		<td><input class="ipt" name="year" id="yearCondition" data-options="required:true" /></td>
							 		<th align='right'><span class="ui-color-red">*</span>月份：</th>
							 		<td><input class="ipt" name="month" id="monthCondition" data-options="required:true" /></td>
							 	</tr>
							 	<tr height="40">
							 		<th align='right'>品牌：</th>
							 		<td>
							 			<input class="ipt easyui-brand" name="brandNames" id="brandNameCondition"
							 			 data-options="inputWidth:160,inputNoField:'brandNoCondition', inputCodeField:'brandNameCondition'"/>
							  			<input type="hidden" name="brandNos" id="brandNoCondition"/>
							  		</td>
							 		<th>创建时间：</th>
								 	<td><input class="easyui-datebox ipt" style="width:145px" name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt" style="width:145px" name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'"/></td>
									<th align='right'>零金额成本： </th>
							 		<td><input type="checkbox" name="zeroAmount" id="zeroAmount"/></td>
							 		<td align='right'>与地区价不一致： </td>
							 		<td><input type="checkbox" name="regionCostUnmatch" id="regionCostUnmatch"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
			                  	  {field : 'ck',checkbox:true,notexport:true},
			                  	  {field : 'id',hidden:true,notexport:true},
				                  {field : 'companyNo',title : '公司编码',width: 80,align:'left'},
				                  {field : 'companyName',title : '公司名称',width: 250,align:'left'},
				                  {field : 'itemCode',title : '商品编码',width: 120,align:'left'},
				                  {field : 'itemName',title : '商品名称',width: 150,align:'left'},
				                  {field : 'brandName',title : '品牌',width: 80,align:'left'},
				                  {field : 'year',title : '年份',width: 50,align:'left', exportType:'number'},
				                  {field : 'month',title : '月份',width: 50,align:'left', exportType:'number'},
				                  {field : 'unitCost',title : '单位成本',width: 80,align:'right', exportType:'number'},
				                  {field : 'regionCost',title : '地区价',width: 80,align:'right', exportType:'number'},
				                  {field : 'createUser',title : '创建人',width: 100,align:'left'},
				                  {field : 'createTime',title : '创建时间',width: 140,align:'left',sortField:'create_time',sortable:true},
				                  {field : 'updateUser',title : '修改人',width: 100,align:'left'},
				                  {field : 'updateTime',title : '修改时间',width: 140,align:'left',sortField:'update_time',sortable:true}
			              ]" 
				          jsonExtend='{
				         	onDblClickRow:function(rowIndex, rowData){
			             	},
			             	onLoadSuccess:function(rowData){
			             		dialog.genearateZeroItemCost();
							}
			             }'
                 />
			</div>
	 	</div>
	</div>
	
   <div id="genarateFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="genarateDataForm" id="genarateDataForm" method="post"  class="pd10">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:650px;"  title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<col width="120"/>
						<col/>
						<col width="120"/>
						<col/>
					   <tr height="40">
				        <th><span class="ui-color-red">*</span>公司名称：</th>
			        	<td>
				        	<input class="ipt easyui-company" name="companyName" id="companyName_"
				        	 data-options="required:true,inputWidth:160,inputNoField:'companyNo_',inputNameField:'companyName_'" />
	      					<input type="hidden" name="companyNo" id="companyNo_"/>
			        	</td>
				        <th><span class="ui-color-red">*</span>年份：</th>
			        	<td><input class="ipt" name="year" id="genarateYear" data-options="required:true" /></td>
			           </tr>
			           <tr height="40">
			        	<th><span class="ui-color-red">*</span>月份：</th>
						<td><input class="ipt" name="month" id="genarateMonth" data-options="required:true" /></td>
			        	<th><span class="ui-color-red">*</span>品牌部：</th>
						<td>
			        		<input class="ipt easyui-brandunit" name="brandUnitName" id="brandUnitName_" 
			  					data-options="required:true,inputWidth:160, inputNoField:'brandUnitNo_', inputNameField:'brandUnitName_'"/>
			  				<input type="hidden" name="brandUnitNo" id="brandUnitNo_"/>
						</td>
					   </tr>
					   <tr height="40">
				        <th>商品编码：</th>
			        	<td>
			        		<input class="ipt easyui-item" name="itemCodes" id="itemCode_" 
			  					data-options="inputWidth:160, multiple: true, inputNoField:'itemNo_',inputCodeField:'itemCode_', inputNameField:'itemName_'"/>
			  				<input type="hidden" name="itemNos" id="itemNo_"/>
			  				<input type="hidden" name="itemNames" id="itemName_"/>
			  			</td>
					   </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
   
   <div id="updateFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="updateDataForm" id="updateDataForm" method="post"  class="pd10">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:480px;"  title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<col width="80"/>
						<col/>
						<col width="80"/>
						<col/>
					   <tr height="40">
					    <th><span class="ui-color-red">*</span>公司名称：</th>
			        	<td>
				        	<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName_1"
				        	 data-options="required:true,inputWidth:160,inputNoField:'companyNo_1',inputNameField:'companyName_1'" />
	      					<input type="hidden" name="companyNo" id="companyNo_1"/>
			        	</td>
			           </tr>
			           <tr height="40">
				        <th><span class="ui-color-red">*</span>品牌部：</th>
						 	<td>
				        		<input class="ipt easyui-brandunit" name="brandUnitNames" id="brandUnitNames_" 
				        			data-options="inputWidth:160, multiple: true, inputNoField:'brandUnitNos_', inputNameField:'brandUnitNames_'"/>
							  	<input type="hidden" name="brandUnitNos" id="brandUnitNos_"/>
							</td>
			           </tr>
			           <tr height="40">
				        <th><span class="ui-color-red">*</span>年份：</th>
						 	<td><input class="ipt" name="year" id="assignYear" data-options="required:true" /></td>
			           </tr>
			           <tr height="40">
				        <th><span class="ui-color-red">*</span>月份：</th>
						 	<td><input class="ipt" name="month" id="assignMonth" data-options="required:true" /></td>
			           </tr>
			           <tr height="40">
				        <th><span class="ui-color-red">*</span>分配类型：</th>
				        <td><input type="radio" name="assignType" value="1" id="radio3" />成本价
							&nbsp;&nbsp;
							<input type="radio" name="assignType" value="2" id="radio4" />地区、总部价
						<td>
			           </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
   
   <div id="auditListPanel" class="easyui-dialog" data-options="closed:true">
	   	<@p.datagrid id="auditDataGrid" loadUrl="" saveUrl="" defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
				           rownumbers="true" singleSelect="true"   fitColumns="false"
				           columnsJsonList="[
					                  {field : 'companyNo',title : '公司编码',width: 80,align:'left'},
					                  {field : 'companyName',title : '公司名称',width: 250,align:'left'},
					                  {field : 'year',title : '年份',width: 50,align:'left'},
					                  {field : 'month',title : '月份',width: 50,align:'left'},
					                  {field : 'brandUnitName',title : '品牌部',width: 100,align:'left'},
					                  {field : 'status', title : '状态',width: 100,align:'left', formatter:auditStatusformatter},
					                  {field : 'createUser',title : '创建人',width: 100,align:'left'},
					                  {field : 'createTime',title : '创建时间',width: 140,align:'left',sortField:'create_time',sortable:true},
					                  {field : 'updateUser',title : '修改人',width: 100,align:'left'},
					                  {field : 'updateTime',title : '修改时间',width: 140,align:'left',sortField:'update_time',sortable:true}
				              ]" 
					          jsonExtend='{onDblClickRow:function(rowIndex, rowData){
				             }}'
	                 />
   </div>
   
   <div id="generateZeroItemCost" class="easyui-dialog" data-options="closed:true">
   		<@p.datagrid id="zeroItemCostDatagrid" loadUrl="" saveUrl="" defaultColumn="" 
              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
	           rownumbers="true" singleSelect="true"   fitColumns="false"
	           columnsJsonList="[
	           		{field : 'ck',checkbox:true,notexport:true},
                  	{field : 'companyName',title : '公司名称',width: 250,align:'left'},
                  	{field : 'year',title : '年份',width: 50,align:'left'},
                  	{field : 'month',title : '月份',width: 50,align:'left'},
                  	{field : 'itemNo',title : '商品编号',width: 120,align:'left'},
                  	{field : 'itemCode',title : '商品编码',width: 120,align:'left'},
                  	{field : 'itemName',title : '商品名称',width: 150,align:'left'},
                  	{field : 'brandName',title : '品牌',width: 80,align:'left'},
                  	{field : 'unitCost',title : '单位成本',width: 80,align:'right', exportType:'number'},
                  	{field : 'regionCost',title : '地区成本',width: 80,align:'right', exportType:'number'}
		                  
	              ]" 
		          jsonExtend='{onDblClickRow:function(rowIndex, rowData){
	             }}'
         />
   </div>
   
   
</body>
</html>