<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>本期结存查询</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/storeSdBalanceReport.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"dialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()", "type":4}
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
								<tr>
							 		<th><span class="ui-color-red">*</span>公司名称：</th>
							 		<td>
								 		<input class="ipt easyui-company" name="companyName" id="companyNameCondition" 
								 		data-options="required:'true',inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
				      					<input type="hidden" name="companyNo" id="companyNoCondition"/>
			      					</td>
							 		<th>品牌名称：</th>
									<td>
						        		<input class="ipt easyui-brand" name="brandNames" id="brandName_" 
									  	data-options="inputWidth:160, multiple: true, inputNoField:'brandNo_', inputNameField:'brandName_'"/>
									  	<input type="hidden" name="brandNos" id="brandNo_"/>
									</td>
							 		<th>一级大类：</th>
							 		<td>
							 			<input class="ipt easyui-combobox" name="categoryNames" id="categoryName_" 
									  				data-options="inputWidth:160, multiple:true,valueField:'categoryNo',textField:'name', url:BasePath + '/category/get_biz?levelid=1&status=1',
									  				onSelect: function(rowDate){
									  					var categoryNoStr = $('#categoryNos_').val();
									  					$('#categoryNos_').val(categoryNoStr + ',' + rowDate['categoryNo']);
									  				},
									  				onUnselect: function(rowDate){
									  					var categoryNoStr =  $('#categoryNos_').val();
									  					if(categoryNoStr.indexOf(',' + rowDate['categoryNo']) != -1) {
									  						categoryNoStr = categoryNoStr.replaceAll(',' + rowDate['categoryNo'], '');
									  						$('#categoryNos_').val(categoryNoStr);
									  					}
									  				}"/>
									  	<input type="hidden" name="categoryNos" id="categoryNos_"/>
									</td>
									<th>货管单位：</th>
							 		<td>
								 		<input class="ipt easyui-orderUnit" name="orderUnitName" id="orderUnitNameCondition" 
								 		data-options="inputWidth:160,inputNoField:'orderUnitNoCondition',inputNameField:'orderUnitNameCondition'"/>
				      					<input type="hidden" name="orderUnitNo" id="orderUnitNoCondition"/>
			      					</td>
								</tr>
								<tr>
							 		<th><span class="ui-color-red">*</span>开始月份：</th>
								   	<td>
							       		<input class="easyui-datebox easyui-validatebox ipt"  name="startYearMonth" id="startYearMonth" data-options="required:true,dateFmt:'yyyyMM',maxDate:'endYearMonth'" />    							
									</td>
									<th><span class="ui-color-red">*</span>结束月份：</th>
								   	<td>
							       		<input class="easyui-datebox easyui-validatebox ipt"  name="endYearMonth" id="endYearMonth" data-options="required:true,dateFmt:'yyyyMM',minDate:'startYearMonth'" />    							
									</td>
			      					<th>机构：</th>
							 		<td>
								 		<input class="ipt easyui-store" name="storeName" id="storeNameCondition" 
								 		data-options="inputWidth:130,inputNoField:'storeNoCondition',inputNameField:'storeNameCondition'"/>
				      					<input type="hidden" name="storeNo" id="storeNoCondition"/>
			      					</td>
			      					<th>管理城市： </th>
	                                <td>
										<input class="easyui-organ ipt" name="organNames" id="organNames" data-options="multiple:'true',inputNoField:'organNos',inputNameField:'organNames'"/> 
										<input type="hidden" name="organNos" id="organNos"/>
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
			              rowStyler="function(index,row){
								     if (row.companyNo =='小计：'){
								         return 'background-color:#6293BB;color:#fff;font-weight:bold;';
								     }
								  }" 
						 
					   frozenColumns="[
					   			  {title : '冻结属性',width: 150,align:'left',halign:'center',colspan:'6'},
					   		],[
					   			  {field : 'companyNo',title : '公司编码',width: 80,align:'left',halign:'center'},
				                  {field : 'companyName',title : '公司名称',width: 160,align:'left',halign:'center'},
				                  {field : 'storeNo',title : '机构编码',width: 120,align:'left',halign:'center'},
				                  {field : 'storeName',title : '机构名称',width: 120,align:'left',halign:'center'},
				                  {field : 'brandName',title : '品牌',width: 80,align:'center'},
				                  {field : 'firstLevelCategoryName',title : '一级大类',width: 80,align:'center'},
					   		]"
			           columnsJsonList="[
			                  	  {field : 'id',hidden:true,notexport:true},
			                  	  {title : '基础属性',colspan:'4'},
			                  	  {title : '期初',colspan:'2'},
			                  	  {title : '期间入',colspan:'20'},
			                  	  {title : '期间出',colspan:'10'},
			                 	  {title : '期间',colspan:'2'},
			                  	  {title : '期末',colspan:'4'}],
				               [
				                  {field : 'orderUnitName',title : '货管单位',width: 80,align:'left',halign:'center'},
				                  {field : 'organName',title : '管理城市',width: 80,align:'center'},
				                  {field : 'year',title : '年份',width: 80,align:'center', exportType:'number'},
				                  {field : 'month',title : '月份',width: 60,align:'center', exportType:'number'},
				                  {field : 'openingQty',title : '期初数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'openingBalance',title : '期初金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'purchaseInQty',title : '采购入库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'purchaseInAmount',title : '采购入库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'purchaseReturnQty',title : '采购退回数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'purchaseReturnAmount',title : '采购退回金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferInQty',title : '外区调入数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferInAmount',title : '外区调入金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerTransferInQty',title : '区内调入数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerTransferInAmount',title : '区内调入金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'invSurplusQty',title : '盘盈数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'invSurplusAmount',title : '盘盈金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'othersInQty',title : '其他入库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'othersInAmount',title : '其他入库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerWayQty',title : '外区入在途数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerWayAmount',title : '外区入在途金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerDiffQty',title : '外区差异数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerDiffAmount',title : '外区差异金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerWayQty',title : '区内入在途数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerWayAmount',title : '区内入在途金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerDiffQty',title : '区内入在途差异数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerDiffAmount',title : '区内入在途差异金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'salesOutQty',title : '销售出库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'salesOutAmount',title : '销售出库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferOutQty',title : '跨区调出数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferOutAmount',title : '跨区调出金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerTransferOutQty',title : '区内调出数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'innerTransferOutAmount',title : '区内调出金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'inventoryLossQty',title : '盘亏数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'inventoryLossAmount',title : '盘亏金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'othersOutQty',title : '其他出库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'othersOutAmount',title : '其他出库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'duringNetQty',title : '期间净发生数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'duringNetAmount',title : '期间净发生金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'closingQty',title : '期末数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'closingBalance',title : '期末金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'unitCost',title : '单位成本',width: 80,align:'right', exportType:'number'},
				                  {field : 'closingUnitCostAmount',title : '期末成本',width: 80,align:'right', exportType:'number'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>