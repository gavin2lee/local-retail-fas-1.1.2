<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部价格维护</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/headquarterCostMaintain.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util_price.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/headquarter_cost_maintain/list.json" 
					  save_url="/headquarter_cost_maintain/insert" 
					  update_url="/headquarter_cost_maintain/update" 
					  del_url="/headquarter_cost_maintain/save" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="450" 
					  export_url="/headquarter_cost_maintain/do_fas_export"
					  export_title="总部价格维护导出"
					  dialog_height="500"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":1},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":2},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":3},
             {"id":"btn-import-main","title":"导入","iconCls":"icon-import","action":"headquarterCostMaintain.doImport()","type":6},
           	{"id":"btn-export","title":"导出","iconCls":"icon-export","action":"fas_common.exportExcel","type":4},
             {"id":"btn-batch-rule","title":"根据规则初始化价格","iconCls":"icon-export","action":"headquarterCostMaintain.generatePriceByRule()","type":85},
             {"id":"btn-batch","title":"批量生成价格","iconCls":"icon-build-some","action":"headquarterCostMaintain.batchGenerate()","type":85}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
		                 <tbody>
						 	<tr>
						 		<th>商品编码：</th>
							 	<td>
								  <input class="ipt easyui-item" name="itemName" id="itemNameCondition"
								 		data-options="datinputWidth:160,inputNoField:'itemNoCondition', inputNameFeild:'itemNameCondition'"/>
								  <input type="hidden" name="itemNo" id="itemNoCondition"/>
								</td>
								<th>品牌部：</th>
							 	<td><input class="ipt" multiSearch="brandUnit"  /><input type="hidden" name="multiBrandUnitNo"></td>
							 	<th>生效时间：</th>
							 	<td>
							 		<input class="easyui-datebox ipt"  name="effectiveTimeStart" id="effectiveTimeStart" data-options="maxDate:'effectiveTimeEnd'"/>
							 	</td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox ipt" name="effectiveTimeEnd" id="effectiveTimeEnd" data-options="minDate:'effectiveTimeStart'"/>
								</td>
							</tr>
							<tr>
							 	<th>规则编码：</th>
							 	<td>
							 		<input class="easyui-validatebox ipt" name="addRuleNo" id="addRuleNoCondition" />
							 	</td>
							 	
							 	<th>是否首增：</th>
							 	<td>
							 		<input class="text" name="firstNew" id="firstNew" style="width:140px" />
							 	</td>
						 		<th>创建时间：</th>
							 	<td>
							 		<input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/>
							 	</td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'"/>
								</td>
							</tr>
							<tr>
						 		<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th>
							 		<td><input class="ipt" multiSearch="category" /><input type="hidden" name="multiCategoryNo"></td>
								<th>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</th>
						 		<td>
						 			<input class="ipt" name="years" id="yearCodeCondition" style="width:140"/>
						 		</td>
								<th>季&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;节：</th>
						 		<td>
						 			<input class="ipt" name="season" id="seasonCondition" style="width:140"/>
						 		</td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<#--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true"  onClickRowEdit="false" singleSelect="false"  
			           columnsJsonList="[
			                  	  {field : 'ck',checkbox:true,notexport:true},
				   				  {field : 'id',title : 'id',width : 10, hidden : true, notexport:true},
				                  {field : 'itemCode',title : '商品编码',width: 150,align:'left',halign:'center'},
				                  {field : 'itemName',title : '商品名称',width: 180,align:'left',halign:'center'},
				                  {field : 'brandNo',title : '品牌编码',width : 80, align:'left',halign:'center'},
				                  {field : 'brandName',title : '品牌名称',width : 80, align:'left',halign:'center'},
				                  {field : 'year',title : '年份',width: 100,halign:'center'},
				                  {field : 'season',title : '季节',width: 100,halign:'center'},
				                  {field : 'supplierNo',title : '供应商编码',width: 80,align:'left',halign:'center'},
				                  {field : 'supplierName',title : '供应商名称',width: 200,align:'left',halign:'center'},
				                  {field : 'effectiveTime',title : '生效日期',width: 100,align:'center',halign:'center'},
				                  {field : 'firstNew',title : '是否首增',width: 80,align:'center'},
				                  {field : 'addBasis',title : '加价依据',width: 80,align:'left',halign:'center',formatter:headquarterCostMaintain.addBasisFormatter},
				                  {field : 'factoryPrice',title : '厂进价',width: 80,align:'left',halign:'center',exportType:'number'},
				                  {field : 'tagPrice',title : '牌价',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'addPrice',title : '加价',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'addDiscount',title : '加折',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'discountRate',title : '折扣',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'headquarterCost',title : '总部成本',width: 80,align:'right',halign:'center',exportType:'number'},
				                  {field : 'addRuleNo',title : '规则编号',width: 100,align:'left',halign:'center'},
				                  {field : 'createUser',title : '创建人',width: 100,align:'center',halign:'center'},
				                  {field : 'createTime',title : '创建时间',width: 140,align:'left',halign:'center',sortField:'create_time',sortable:true},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'},
								  {field : 'updateTime',title : '修改时间',width : 150,align:'center',halign:'center'},
			              ]" 
				          jsonExtend='{onDblClickRow:function(rowIndex, rowData){
			           	  		 fas_common.loadDetail(rowData);
			             }}' 
                 />
			</div>
	 	</div>
	</div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="dataForm" id="dataForm" method="post"  class="pd10">
	     <input type="hidden" name="id" id="id">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:380px;"  title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
					     <col width="150"/>
						 <col/>
				         <tbody>
				         	<tr>
			        			<th><span class="ui-color-red">*</span>商品编码：</th>
			        		    <td>
			        		    	<input class="ipt easyui-item" name="itemCode" id="itemCode_" 
								 		data-options="required:true,inputWidth:160,inputNoField:'itemNo_', inputCodeField:'itemCode_',inputNameField:'itemName_',callback:headquarterCostMaintain.changeItemEvent" />
								  	<input type="hidden" name="itemNo" id="itemNo_"/>
			        		    	<input type="hidden" name="itemName" id="itemName_" />
			        		    	<input type="hidden" name="suggestTagPrice" id="suggestTagPrice" />
			        		    	<input type="hidden" name="tagPrice" id="tagPrice" />
			        		    	<input type="hidden" name="factoryPrice" id="factoryPrice" />
			        		    </td>
			        		</tr>
			        		<tr>
			        			<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
			        		    <td>
			        		    	<input class="ipt readonly" name="brandName" id="brandName" />
			        		    	<input type="hidden" name="brandNo" id="brandNo" />
			        		    </td>
			        		</tr>
			        		<tr>
			        			<th>供应商名称：</th>
			        		    <td>
			        		    	<input class="ipt readonly" name="supplierName" id="supplierName" />
			        		    	<input type="hidden" name="supplierNo" id="supplierNo" />
			        		    </td>
			        		</tr>
			        		<tr>
			        			<th>总部规则编码：</th>
			        		    <td><input class="easyui-combobox ipt" name="addRuleNo" id="addRuleNo" /></td>
			        		</tr>
			        		<tr>
			        			<th>加价依据：</th>
			        		    <td>
			        		    	<input class="ipt readonly" name="addBasisName" id="addBasisName" />
			        		    	<input type="hidden" name="addBasis" id="addBasis" />
			        		    </td>
			        		</tr>
			        		<tr>
			        			<th>加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</th>
			        		    <td>
			        		    	<input class="ipt readonly" name="addPrice" id="addPrice" />
			        		    </td>
			        		</tr>
			        		<tr>
			        			<th>加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;折：</th>
			        		    <td>
			        		    	<input class="ipt readonly" name="addDiscount" id="addDiscount" />
			        		    </td>
			        		</tr>
			        		<tr>
			        			<th>折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣：</th>
			        		    <td>
			        		    	<input class="ipt readonly" name="discountRate" id="discountRate" />
			        		    </td>
			        		</tr>
			        		<tr>
			        			<th><span class="ui-color-red">*</span>总部成本：</th>
			        		    <td>
			        		    	<input class="easyui-validatebox ipt" name="headquarterCost" id="headquarterCost" data-options="validType:'number'" />
			        		    </td>
			        		</tr>
			        		<tr>
			        		    <th><span class="ui-color-red">*</span>生效日期：</th>
			        		    <td>
			        		    	<input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="effectiveTime" id="effectiveTime" data-options="required:true" />
			        		    </td>
			        		</tr>
			        	</tbody>
					</table>
				</div>
			</div>
		 </form>	
   	</div>
   	
   <div id="genarateFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="genarateDataForm" id="genarateDataForm" method="post" class="pd10">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
				       <tr height="40">
				         <th><span class="ui-color-red">*</span>品牌部：</th>
				         <td>
							<input class="ipt easyui-brandunit" name="brandUnitNames" id="brandUnitNamesCondition" 
							 	data-options="required:true,multiple:true,inputWidth:160,inputNoField:'brandUnitNosCondition', inputNameField:'brandUnitNamesCondition'"/>
						  	<input type="hidden" name="brandUnitNos" id="brandUnitNosCondition"/>
						 <td>
					   </tr>
					   <tr height="40">
				         <th><span class="ui-color-red">*</span>大类：</th>
						 <td><input class="ipt" id="multiCategoryNo"  data-options="required:true" /> <input type="hidden" name="multiCategoryNo"/></td>
					   </tr>
					   <tr height="40">
					   		<th><span class="ui-color-red">*</span>生效日期：</th>
	        		    	<td>
	        		    	<input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="effectiveTime1" id="effectiveTime1" 
	        		    	data-options="required:true" />
	        		    	</td>
					   </tr>
					   <tr height="40">
			        		<th><span class="ui-color-red">*</span>生成类型：</th>
			        		<td>
								<input type="radio" name="HQQuotationFlag" value="1" id="radio1" />厂进价
									&nbsp;&nbsp;
								<input type="radio" name="HQQuotationFlag" value="2" id="radio0" />牌价
							<td>
					   </tr>
					   <tr height="40">
					   	<th><span class="ui-color-red">*</span>是否首增：</th>
						 <td>
							 <input class="text" name="firstNew1" id="firstNew1" style="width:160px" />
						 </td>
					   </tr>
					   
					</table>
				</div>
			</div>
		 </form>	
   </div>
   
   <div id="genarateRuleFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="genarateRuleDataForm" id="genarateRuleDataForm" method="post" class="pd10">
				<div id="div2" class="easyui-panel" title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
			      	   <tr height="40">
					     <th><span class="ui-color-red">*</span>加价规则：</th>
					     <td>
						 <input class="easyui-combobox ipt" data-options="required:true" name="addRuleNo" id="addRuleNoGenarate" />
						 </td>
					   </tr>
					   <tr height="40">
					   		<th><span class="ui-color-red">*</span>生效日期：</th>
	        		    	<td>
	        		    	<input class="easyui-validatebox easyui-datebox ipt"  name="effectiveTime" data-options="required:true" />
	        		    	</td>
					   </tr>
					</table>
			</div>
		 </form>	
   </div>
 
   <!--<div id="noFirstNewFormPanel" class="easyui-dialog" data-options="closed:true"> 
   		  <div data-options="region:'north',border:false" class="toolbar-region">
			 <@p.toolbar id="toolbarOne" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"headquarterCostMaintain.searchNoFirstNew()", "type":0}
		           ]
				/>
		</div>
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				    <form name="noFirstNewdataForm" id="dataForm" method="post"  >
						<table class="form-tb">
		            	    <col width="120px"/>
		            	 	<col />
		                    <tbody>
							   <tr>
							   		<th><span class="ui-color-red">*</span>生效日期：</th>
			        		    	<td>
			        		    	<input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="effectiveTime1" id="effectiveTime1" data-options="required:true" />
			        		    	</td>
							   </tr>
							 </tbody>
						 </table>
				 	</form>
			 	</div>
			 </div>
			 <div data-options="region:'center',border:false" >
			 	<div class="easyui-layout" data-options="fit:true">
			 		<div data-options="region:'center',border:false" >
			 			<@p.datagrid id="dtlDataGrid" emptyMsg = "" checkOnSelect="true" selectOnCheck="true" singleSelect="false"
							isHasToolBar="false"  onClickRowEdit="false" pagination="false"
							 	columnsJsonList="[
							 	{field : 't', checkbox:true, width : 30, notexport:true},
							 	{field:'itemCode',title:'商品编码',width:120},
								{field:'itemName',title:'商品名称',width:120},
								{field:'purchasePrice',title:'采购价',width:80},
								{field:'materialPrice',title:'物料价',width:80},
								{field:'headquarterCost',title:'总部价',width:80},
								{field:'effectiveTime',title:'生效日期',width:120}]"
						  		 jsonExtend='{}' 
						  />
			 		</div>
			 	</div>
			 </div>
		</div>
   </div>-->
   
</body>
</html>