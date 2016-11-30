<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>成本计算</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/specifyItemCost.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/specify_item_cost/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/specify_item_cost/do_fas_export"
					  export_title="巴洛克成本核算导出"
					  primary_key="id"/>
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
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
							<tbody>
								<tr>
									<th><span class="ui-color-red">*</span>公司：</th>
				                   	<td align="left">
										<input class="ipt easyui-company"  name="buyerName" id="buyerName" data-options="inputNoField:'buyerNo',inputNameField:'buyerName',inputWidth:170,required:true"/>
										<input type="hidden"  name="buyerNo" id="buyerNo" 	/>	
									</td>
				                   	<th align="right" width="80"><span class="ui-color-red">*</span>日期：</th>
						    		<td align="left">
						    			<input class="easyui-datebox ipt" style="width: 100px;"  name="receiveDateStart" id="receiveDateStart" data-options="required:true,maxDate:'receiveDateEnd'"/>
										 - <input class="easyui-datebox ipt" style="width: 100px;" name="receiveDateEnd" id="receiveDateEnd" data-options="required:true,minDate:'receiveDateStart'"/>
						    		</td>
				        		    <th>商品：</th>
							 		<td>
							 			<input class="ipt easyui-item" name="itemCode" id="itemCode"
							 			 data-options="inputWidth:160,inputNoField:'itemNo', inputCodeField:'itemCode'"/>
							  			<input type="hidden" name="itemNo" id="itemNo"/>
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
		              isHasToolBar="false" onClickRowEdit="false" pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
		           			  {field : 't',checkbox:true,width : 30,align:'center',notexport:true},
		                  	  {field : 'id',hidden:true,notexport:true},
			                  {field : 'buyerName',title : '公司',width: 150,align:'left',align:'center'},
			                  {field : 'receiveDateStr',title : '日期范围',width: 160,align:'left',align:'center'},
			                  {field : 'itemCode',title : '商品编码',width: 135,align:'left',align:'center'},
			                  {field : 'itemName',title : '商品名称',width: 160,align:'left',align:'center'},
			                  {field : 'brandUnitName',title : '品牌部',width: 50,align:'left',align:'center'},
			                  {field : 'brandName',title : '品牌',width: 50,align:'left',align:'center'},
			                  {field : 'firstLevelCategoryName',title : '大类',width: 80,align:'left',align:'center'},
			                  {field : 'yearsName',title : '年份',width: 80,align:'left',align:'center'},
			                  {field : 'seasonName',title : '季节',width: 80,align:'left',align:'center'},
			                  {field : 'secondLevelCategoryName',title : '二类',width: 80,align:'left',align:'center'},
			                  {field : 'categoryName',title : '三类',width: 80,align:'left',align:'center'},
			                  {field : 'receiveQty',title : '采购入库数量',width: 100,align:'left',align:'center',exportType:'number'},
			                  {field : 'receiveAmount',title : '采购入库金额',width: 100,align:'left',align:'center',exportType:'number'},
			                  {field : 'averageCost',title : '平均成本',width: 100,align:'left',align:'center',exportType:'number'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>