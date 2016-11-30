<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<title>巴洛克总部成本</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/baroque_item_cost/baroqueItemCost.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"baroqueItemCost.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"baroqueItemCost.clear()", "type":0},
             {"id":"btn-ok","title":"生成成本","iconCls":"icon-ok","action":"baroqueItemCost.generateHeadquarterCost()", "type":87},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "baroqueItemCost.exportExcel()","type":0}
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
							 		<th align='right'>年份：</th>
							 		<td><input class="ipt" name="year" id="yearCondition" data-options="required:true" /></td>
							 		<th align='right'>月份：</th>
							 		<td><input class="ipt" name="month" id="monthCondition" data-options="required:true" /></td>
							 		<th>品牌部：</th>
									<td>
						        		<input class="ipt easyui-brandunit" name="brandUnitName" id="brandUnitName_" 
						  					data-options="inputWidth:160, inputNoField:'brandUnitNo_', inputNameField:'brandUnitName_'"/>
						  				<input type="hidden" name="brandUnitNo" id="brandUnitNo_"/>
									</td>
									<th align='right'>款号：</th>
							 		<td>
								 		  <input class="easyui-styleNo ipt" name="styleName" id="styleNameCon"
				                               data-options="inputNoField:'styleNo',inputNameField:'styleNoCon',isDefaultData : false"/>
				                        <input type="hidden" name="styleNo" id="styleNoCon"/>
			      					</td>
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
								{field : 'styleNo',title : '款号',width: 150,align:'left'},
								{field : 'itemCode',title : '货号',width: 150,align:'left'},
								{field : 'itemName',title : '商品名',width: 120,align:'left'},
								{field : 'effectiveTime',title : '生效日期',width: 120,align:'left'},
								{field : 'brandUnitName',title : '品牌部',width: 80,align:'left'},
								{field : 'brandName',title : '品牌',width: 80,align:'left'},
								{field : 'categoryName',title : '大类',width: 180,align:'left'},
								{field : 'year',title : '年份',width: 50,align:'left', exportType:'number'},
								{field : 'month',title : '月份',width: 50,align:'left', exportType:'number'},
								{field : 'oneLevelCategoryName',title : '二类',width: 150,align:'left', exportType:'number'},
								{field : 'twoLevelCategoryName',title : '三类',width: 150,align:'left'},
								{field : 'headquarterCost',title : '总部成本',width: 100,align:'left',sortField:'create_time',sortable:true}
						  ]" 
				          jsonExtend='{
				         	onDblClickRow:function(rowIndex, rowData){
			             	},
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
				        <th><span class="ui-color-red">*</span>年份：</th>
			        	<td><input class="ipt" name="year" id="genarateYear" data-options="required:true" /></td>
			        	<th><span class="ui-color-red">*</span>月份：</th>
						<td><input class="ipt" name="month" id="genarateMonth" data-options="required:true" /></td>
			           </tr>
			           <tr height="40">
			        	<th><span class="ui-color-red">*</span>品牌部：</th>
						<td>
			  				<input class="ipt easyui-brandunit" name="brandUnitName" id="brandUnitNameGene" 
			  					data-options="inputWidth:160, inputNoField:'brandUnitNoGene', inputNameField:'brandUnitNameGene'"/>
			  				<input type="hidden" name="brandUnitNo" id="brandUnitNoGene"/>
						</td>
						<th><span class="ui-color-red">*</span>公司：</th>
						<td>
			  				<input class="ipt easyui-company" name="company" id="companyNameGene" 
			  					data-options="inputWidth:160, inputNoField:'companyNoGene', inputNameField:'companyNameGene'"/>
			  				<input type="hidden" name="companyNo" id="companyNoGene"/>
						</td>
					   </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
</body>
</html>