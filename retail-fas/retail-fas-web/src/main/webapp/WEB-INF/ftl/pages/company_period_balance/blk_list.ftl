<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>巴洛克-本期结存查询</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/self_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/blkPeriodBalance.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"dialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dialog.clear()", "type":0},
             {"id":"btn-ok","title":"生成本月累计欠客","iconCls":"icon-ok","action":"dialog.generateOwerGuest()", "type":0},
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
							 		<th align='right'>商品款号：</th>
							 		<td>
								 		<input class="ipt" selfSingleSearch="style"/>
								 		<input type="hidden" name="styleNo"/>
			      					</td>
							 		<th>品牌：</th>
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
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>年份：</th>
							 		<td>
							 			<input class="ipt" name="year" id="yearCondition" data-options="required:true" />
							 		</td>
							 		<th><span class="ui-color-red">*</span>月份：</th>
							 		<td>
							 			<input class="ipt" name="month" id="monthCondition" data-options="required:true" />
							 		</td>
							 		<th>显示小计： </th>
							 		<td>
							 			<input type="checkbox" name="isSubTotal" id="isSubTotal" onclick="selectChange(this.id)" />
							 			&nbsp;&nbsp;&nbsp;&nbsp;<span align='right'>仅显示小计： </span>
							 			<input type="checkbox" name="onlySubTotal" id="onlySubTotal" onclick="selectChange(this.id)" />
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
			           rownumbers="true" singleSelect="false"  showFooter="true" 
			           rowStyler="function(index,row){
								     if (row.companyNo.indexOf('小计') != -1){
								         return 'background-color:#6293BB;color:#fff;font-weight:bold;';
								     }
								  }" 
					   frozenColumns="[
									{title : '冻结属性',width: 120,align:'left',halign:'center',colspan:'4'},
					   			  ],
					   			  [
					   			  	{field : 'styleNo',title : '款号',width: 120,align:'left',halign:'center'},
				                  	{field : 'brandName',title : '品牌',width: 80,align:'center'},
				                  	{field : 'balanceDiff',title : '对账差异数量',width: 100,align:'center'},
				                  	{field : 'firstLevelCategoryName',title : '一级大类',width: 80,align:'center'},
					     		]"
			           columnsJsonList="[
			                  	  {field : 'id',hidden:true,notexport:true},
			                      {title : '基础属性',width: 80,align:'left',halign:'center',colspan:'12'},
				                  {title : '期初',colspan:'7'},
			                  	  {title : '期间入',colspan:'13'},
			                  	  {title : '期间出',colspan:'8'},
			                 	  {title : '期间',colspan:'4'},
			                  	  {title : '期末',colspan:'13'}
			                  	  ],
				                [
				                  {field : 'companyNo',title : '公司编码',width: 80,align:'left',halign:'center'},
				                  {field : 'companyName',title : '公司名称',width: 200,align:'left',halign:'center'},
				                  {field : 'year',title : '年份',width: 80,align:'center', exportType:'number'},
				                  {field : 'month',title : '月份',width: 60,align:'center', exportType:'number'},
				                  {field : 'secondLevelCategoryName',title : '二级大类',width: 80,align:'center'},
				                  {field : 'categoryName',title : '商品大类',width: 80,align:'center'},
				                  {field : 'yearsName',title : '商品年份',width: 60,align:'center'},
				                  {field : 'seasonName',title : '商品季节',width: 80,align:'center'},
				                  {field : 'orderfrom',title : '订货形式',width: 80,align:'center'},
				                  {field : 'gender',title : '性别',width: 60,align:'center'},
				                  {field : 'purchaseSeason',title : '季节',width: 80,align:'center'},
				                  {field : 'itemFlag',title : '商品标志',width: 80,align:'center'},
				                  
				                  {field : 'preHeadquarterCost',title : '期初总部加权成本',width: 80,align:'right', exportType:'number'},
				                  {field : 'preSumOweQty',title : '期初累计欠客数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'preSumOweAmount',title : '期初累计欠客金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'preAccountingQty',title : '期初财务存数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'preAccountingAmount',title : '期初财务存金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'openingQty',title : '期初数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'openingBalance',title : '期初余额',width: 80,align:'right', exportType:'number'},
				                  
				                  {field : 'purchaseInQty',title : '采购入库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'purchaseInAmount',title : '采购入库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'purchaseReturnQty',title : '采购退回数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'purchaseReturnAmount',title : '采购退回金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferInQty',title : '外区调入数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferInAmount',title : '外区调入金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'invSurplusQty',title : '盘盈数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'invSurplusAmount',title : '盘盈金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'othersInQty',title : '其他入库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'othersInAmount',title : '其他入库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerWayQty',title : '外区入在途数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerDiffQty',title : '外区差异数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'costAdjustmentAmount',title : '差异调整金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'salesOutQty',title : '销售出库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'salesOutAmount',title : '销售出库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferOutQty',title : '跨区调出数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'outerTransferOutAmount',title : '跨区调出金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'inventoryLossQty',title : '盘亏数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'inventoryLossAmount',title : '盘亏金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'othersOutQty',title : '其他出库数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'othersOutAmount',title : '其他出库金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'duringNetInventoryQty',title : '期间盘差数量',width: 100,align:'right', exportType:'number',formatter:function(value,row,index){
										return row.duringNetInventoryQty;
								  }},
				                  {field : 'duringNetInventoryAmount',title : '期间盘差金额',width: 100,align:'right', exportType:'number',formatter:function(value,row,index){
										return row.duringNetInventoryAmount;
								  }},
				                  {field : 'duringNetQty',title : '期间净发生数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'duringNetAmount',title : '期间净发生金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'closingQty',title : '期末数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'closingBalance',title : '期末金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'closingBalanceReference',title : '期末金额(参考)',width: 100,align:'right', exportType:'number'},
				                  {field : 'weightedDifference',title : '加权差异',width: 80,align:'right', exportType:'number'},
				                  {field : 'currSumOweQty',title : '累计欠客数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'currSumOweAmount',title : '累计欠客金额',width: 100,align:'right', exportType:'number'},
				                  {field : 'currAccountingQty',title : '财务存数量',width: 80,align:'right', exportType:'number'},
				                  {field : 'currAccountingAmount',title : '财务存金额',width: 80,align:'right', exportType:'number'},
				                  {field : 'salesSumQty',title : '期间销售数量',width: 100,align:'right', exportType:'number'},
				                  {field : 'salesSumAmount',title : '期间销售成本',width: 100,align:'right', exportType:'number'},
				                  {field : 'unitCost',title : '地区加权成本',width: 80,align:'right', exportType:'number'},
				                  {field : 'headquarterCost',title : '总部加权成本',width: 80,align:'right', exportType:'number'},
				                  {field : 'tagPrice',title : '期末牌价额',width: 100,align:'right', exportType:'number',organType:'U010102'}
				                  
			              ]" 
				          jsonExtend='{}' 
                 />
			</div>
	 	</div>
	</div>
</body>
</html>