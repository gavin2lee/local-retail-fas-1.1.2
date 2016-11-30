<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>销售汇总表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/salesSumReport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/salesSumReportExportExcel.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<input type="hidden" value="${senda}" id="senda">
	<div data-options="region:'north',border:false" class="toolbar-region">
	      <@p.billToolBar type="shop_sales_sum_listBar"/>
	</div>

	<div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<#--搜索start-->
			<div data-options="region:'north',border:false">
				<div class="search-div" style="border-bottom:0px;height: 140px;">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<div id="div1" class="easyui-panel" title="查询条件" data-options="title:'查询条件',fieldset:true,cls:'mt5'">
						<table class="form-tb">
							<col width="80" />
							<col width="160" />
							<col width="80" />
							<col />
							<col width="80" />
							<col />			
							<col width="80" />
							<col />	
							<col width="90" />
							<col />						
							<tbody>
								<tr>
									<th><span class="ui-color-red">*</span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区： </th>
									<td>
										<input class="easyui-zonebox ipt" name="zoneNo" id="zoneNo" data-options="required:true,multiple:false,inputWidth:50" />
									</td>
									<th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
									<td>
										<input class="easyui-company ipt" name="companyName" id="companyName" data-options="multiple:true,inputWidth:130"/>
									    <input type="hidden" name="companyNo" id="companyNo"/>
									</td>
									<!-- <th>省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份： </th>
									<td>								
										<input class="easyui-province ipt" name="provinceNo" id="provinceNo" data-options="multiple:true"/>
									</td> -->
									<th>管理城市： </th>
									<td>
									    <input class="easyui-organ ipt" data-options="multiple:true,inputWidth:130" name="organName" id="organName"/>
								        <input type="hidden" name="organNo" id="organNo"/>
								    </td>
								    <th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="easyui-brand ipt" name="brandName" id="brandName" data-options="multiple:true,inputWidth:130"/>
										<input type="hidden" name="brandNo" id="brandNo"/>
									</td>	
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td>
										<input class="easyui-categorybox ipt" name="categoryNo" id="categoryNo" data-options="multiple:true"/>
									</td>
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>销售日期： </th>
									<td>
										<input class="easyui-datebox ipt"   name="saleStartDate" id="saleStartDate" data-options="maxDate:'saleEndDate',required:true"  />
									</td>
									<th>— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input class="easyui-datebox ipt" name="saleEndDate" id="saleEndDate" data-options="minDate:'saleStartDate',required:true"  />
									</td>
									<th>开票状态： </th>
									<td>						
										<input class="easyui-combobox ipt" name="invoiceState" id="invoiceState" />		
									</td>
									<th>开票日期： </th>
									<td>
										<input class="easyui-datebox ipt"   name="invoiceStartDate" id="invoiceStartDate" data-options="maxDate:'invoiceEndDate'"  />
									</td>
									<th>— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </th>
									<td>
										<input class="easyui-datebox ipt" name="invoiceEndDate" id="invoiceEndDate" data-options="minDate:'invoiceStartDate'"  />
									</td>
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>业务类型： </th>
									<td>
										<input class="easyui-businessType ipt" data-options="required:true" name="businessType" id="businessType" />		
									</td>
									<th>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺： </th>
									<td>
										<input class="easyui-shopCommon" id="shopName" disabled="disabled"/>
										<input type="hidden" name="shopNo" id="shopNo"/>
									</td>
									<th>商&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品： </th>
									<td>
										<input class="easyui-itemCommon" id="itemName" />
										<input type="hidden" name="itemSql" id="itemNo"/>
									</td>
									<th>结算状态： </th>
									<td>						
										<input class="easyui-combobox ipt" name="balanceState" id="balanceState" />		
									</td>
									<th>小计维度： </th>
									<td>						
										<input class="easyui-combobox ipt" name="countType" id="countType" />		
									</td>
								</tr>
								<tr>
									<th>客户名称：</th>
		                            <td>
		                            	<input class="easyui-customer ipt" name="customerName" id="customerName" data-options="multiple:true,inputWidth:130"/>
		                            	<input type="hidden" name="customerNo" id="customerNo"/>
		                            </td>
									<th>
									</th>
									<td>
									</td>
									<th>
									</th>
									<td>
									</td>
									<th>
										<input type="checkbox" checked="checked" name="isCost" id="isCost" value="1"/>&nbsp;&nbsp;&nbsp;</th>
									<td>						
										<label for="isCost">物料显示成本</label>
									</td>
									<th>
										<input type="checkbox" name="onlyCount" id="onlyCount" value="1"/>
									</th>
									<td>
										<label for="onlyCount">仅显示小计</label>
									</td>
								</tr>
							</tbody>
						</table>
						</div>
						<!-- <div id="div2" class="easyui-panel" title="门店销售" data-options="title:'门店销售',fieldset:true,cls:'mt5'">
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
								<tr >
								<div >
									
									<th>店铺大类： </th>
									<td>
										<input  class="easyui-salemode ipt"  id="saleMode" name="saleMode" data-options="multiple:true" />
									</td>									
									<th>店铺小类： </th>
									<td>
										<input  class="easyui-retailtype ipt"  id="retailType" name="retailType" data-options="multiple:true"/>
									</td>									
									<th>店铺细类： </th>
									<td>
										<input class="easyui-shopmulti ipt"  id="shopMulti" name="shopMulti" data-options="multiple:true"/>
									</td>
									<th>店铺级别： </th>
									<td>
										<input class="easyui-shoplevel ipt"  id="shopLevel" name="shopLevel" data-options="multiple:true"/>
									</td>
								</div>
								</tr>
							</tbody>
			      		</table>
			      		</div> -->
					</form>
				</div>
			</div>
			<#--列表-->
			<div data-options="region:'center'">
			<#if senda=='true'>
				<@p.datagrid id="salesSumDataGrid" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" 
					rownumbers="true"  singleSelect="true" 
					columnsJsonList="[
						{field : 'zoneName', title : '地区', width : 60,rowspan:'2'},
						{field : 'provinceName', title : '省份 ', width : 60,rowspan:'2'},
						{field : 'companyName', title : '公司', width : 180,align:'left',halign:'center',rowspan:'2'},
						{field : 'organName', title : '管理城市 ', width : 80,rowspan:'2'},
						{field : 'organName2', title : '经营城市', width : 80,rowspan:'2'},
						
						{field : 'shopNo', title : '店铺/客户编码', width : 100,rowspan:'2'},
						{field : 'shopNoReplace', title : '店铺替换编码', width : 100,rowspan:'2'},
						{field : 'shortName', title : '店铺/客户名称', width : 150,align:'left',halign:'center',rowspan:'2'},
						{field : 'saleType', hidden:true,title : '销售类别', width : 80,rowspan:'2',notexport:true},
						{field : 'bizTypeName', title : '业务类型', width : 80,rowspan:'2'},
						
						{field : 'year',hidden:true, title : '年', width : 60,rowspan:'2',notexport:true},
						{field : 'month', title : '年月', width : 60,rowspan:'2'},
						{field : 'billMonth', title : '开票月份', width : 60,rowspan:'2'},
						{field : 'saleMonth', title : '结算期 ', width : 100,rowspan:'2'},
						{field : 'contractRate', title : '合同扣率', width : 80, align:'right',halign:'center',exportType:'number',rowspan:'2'},
						
						<!-- {field : 'major', title : '品牌分类', width : 60,rowspan:'2'}, -->
						{field : 'brandUnitNo', title : '品牌部编号', width : 80,rowspan:'2'},
						{field : 'brandUnitName', title : '品牌部名称', width : 80,rowspan:'2'},
						{field : 'categoryName', title : '类别名称', width : 60,rowspan:'2'},
						
						{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center',rowspan:'2'},
						{field : 'saleMode1', title : '店铺大类', width : 80,rowspan:'2'},
						{field : 'retailType1', title : '店铺小类', width : 80,rowspan:'2'},
						{field : 'multi1', title : '店铺细类', width : 80,rowspan:'2'},
						{field : 'levelName1', title : '店铺级别', width : 80,rowspan:'2'},
						{field : 'levelName', hidden:true,title : '店铺类别', width : 70,rowspan:'2',notexport:true},
						{field : 'statusName', title : '店铺状态', width : 80,rowspan:'2'},
						{field : 'openDate', title : '成立日期', width : 80,rowspan:'2'},
						{field : 'employeAmount', title : '店员人数', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},
						{field : 'area', title : '卖场面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
						{field : 'areaLeft', title : '仓库面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
						{field : 'areaTotal', title : '总面积 ', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},  
						{field : 'channelName', title : '销售渠道', rowspan:'2', width : 80},
						
						{title : '上月结算期后',colspan:'10',},
		                {title : '本月结算期内',colspan:'10'},
		                {title : '结算期内合计',colspan:'21'},
		                {title : '本月结算期后',colspan:'10'},
		                {title : '本月合计',colspan:'22'}],
		                
			            [{field : 'lmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
			            {field : 'lmaPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
			            {field : 'lmaPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
						                                                                                                                                                                                                                     
						{field : 'tmiPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodSalesdeductions', title : '扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'biPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biMallNumberAmount', title : '商场报数', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biSalesDiffamount', title : '报数差异', width : 90, align:'right',halign:'center',exportType:'number'},
						  
						{field : 'biPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biExpenseOperateAmount', title : '保底扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'biPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'biPeriodOtherdeductions', title : '其它加扣', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodPredictiondeductions', title : '预估调整', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodMalldeductions', title : '商场扣额（含调整）', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodBalanceamount', title : '结算收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'qty', title : '开票数量 ', width : 80, align:'right',rowspan:'2',halign:'center',exportType:'number'},
						
						{field : 'biBillingAmount', title : '开票金额（含调整）', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
						{field : 'sendAmount', title : '开票金额 ', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
						{field : 'invoiceApplyDate', title : '开票日期 ', width : 80,rowspan:'2'},
						{field : 'salerName', title : '开票单位', width : 80,rowspan:'2'},
						                                                                                                                                 
						{field : 'tmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodRealamount', title : '终端销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodSalesamount', title : '销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'tmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodBalanceamount', title : '结算收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'tmSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'tmRealamount', title : '终端销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmSalesamount', title : '销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPromDeductAmount', title : '保底扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmOtherdeductions', title : '其它加扣', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmSalesdeductions', title : '扣费  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmPredictiondeductions', title : '预估调整  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
						<!-- {field : 'sumChangebalanceamount', title : '调整前期扣费', width : 90, align:'right',halign:'center',exportType:'number'}, -->
						<!-- {field : 'sumSalesdeductions', title : '扣费合计', width : 90, align:'right',halign:'center',exportType:'number'}, -->
						{field : 'actualRate', title : '实际扣率', width : 80, align:'right',halign:'center',exportType:'number'},
						
						{field : 'systemDeductAmount', title : '系统扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceDeductAmount', title : '票前费用 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceDiffAmount', title : '结算差异 ', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'nonTaxSalesamount', title : '无税收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'taxCost', title : '含税成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'noTaxCosts', title : '无税成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'regionCost', title : '地区成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'headquarterCost', title : '总部成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'purchasePrice', title : '采购成本', width : 90, align:'right',halign:'center',exportType:'number'}]"
						
						<!-- {field : 'tmNobillingSumamount', title : '累计未开票  ', width : 90, align:'right',halign:'center',exportType:'number'}, -->
						<!-- {field : 'balanceQty', title : '库存数量', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceQtyAmount', title : '含税成本库存金额', width : 120, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceQtyRegionAmount', title : '地区成本库存金额', width : 120, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceQtyHeadquarterAmount', title : '总部成本库存金额', width : 120, align:'right',halign:'center',exportType:'number'} --> 
				/>
			<#else>
				<@p.datagrid id="salesSumDataGrid" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" 
					rownumbers="true"  singleSelect="true" 
					columnsJsonList="[
						{field : 'zoneName', title : '地区', width : 60,rowspan:'2'},
						{field : 'provinceName', title : '省份 ', width : 60,rowspan:'2'},
						{field : 'companyName', title : '公司', width : 180,align:'left',halign:'center',rowspan:'2'},
						{field : 'organName', title : '管理城市 ', width : 80,rowspan:'2'},
						{field : 'organName2', title : '经营城市', width : 80,rowspan:'2'},
						
						{field : 'shopNo', title : '店铺/客户编码', width : 100,rowspan:'2'},
						{field : 'shopNoReplace', title : '店铺替换编码', width : 100,rowspan:'2'},
						{field : 'shortName', title : '店铺/客户名称', width : 150,align:'left',halign:'center',rowspan:'2'},
						{field : 'saleType', hidden:true,title : '销售类别', width : 80,rowspan:'2',notexport:true},
						{field : 'bizTypeName', title : '业务类型', width : 80,rowspan:'2'},
						
						{field : 'year',hidden:true, title : '年', width : 60,rowspan:'2',notexport:true},
						{field : 'month', title : '年月', width : 60,rowspan:'2'},
						{field : 'billMonth', title : '开票月份', width : 60,rowspan:'2'},
						{field : 'saleMonth', title : '结算期 ', width : 100,rowspan:'2'},
						{field : 'contractRate', title : '合同扣率', width : 80, align:'right',halign:'center',exportType:'number',rowspan:'2'},
						
						<!-- {field : 'major', title : '品牌分类', width : 60,rowspan:'2'}, -->
						{field : 'brandUnitNo', title : '品牌部编号', width : 80,rowspan:'2'},
						{field : 'brandUnitName', title : '品牌部名称', width : 80,rowspan:'2'},
						{field : 'categoryName', title : '类别名称', width : 60,rowspan:'2'},
						
						{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center',rowspan:'2'},
						{field : 'saleMode1', title : '店铺大类', width : 80,rowspan:'2'},
						{field : 'retailType1', title : '店铺小类', width : 80,rowspan:'2'},
						{field : 'multi1', title : '店铺细类', width : 80,rowspan:'2'},
						{field : 'levelName1', title : '店铺级别', width : 80,rowspan:'2'},
						{field : 'levelName', hidden:true,title : '店铺类别', width : 70,rowspan:'2',notexport:true},
						{field : 'statusName', title : '店铺状态', width : 80,rowspan:'2'},
						{field : 'openDate', title : '成立日期', width : 80,rowspan:'2'},
						{field : 'employeAmount', title : '店员人数', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},
						{field : 'area', title : '卖场面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
						{field : 'areaLeft', title : '仓库面积', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},                                                                                                                                                                                                                                        
						{field : 'areaTotal', title : '总面积 ', width : 60,align:'right',halign:'center',rowspan:'2',exportType:'number'},  
						{field : 'channelName', title : '销售渠道', rowspan:'2', width : 80},
						
						{title : '上月结算期后',colspan:'10',},
		                {title : '本月结算期内',colspan:'10'},
		                {title : '结算期内合计',colspan:'21'},
		                {title : '本月结算期后',colspan:'10'},
		                {title : '本月合计',colspan:'21'}],
		                
			            [{field : 'lmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
			            {field : 'lmaPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
			            {field : 'lmaPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'lmaPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
						                                                                                                                                                                                                                     
						{field : 'tmiPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodSalesdeductions', title : '扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmiPeriodBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'biPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodRealamount', title : '终端销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodSalesamount', title : '销售收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biMallNumberAmount', title : '商场报数', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biSalesDiffamount', title : '报数差异', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biExpenseOperateAmount', title : '保底扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodOtherdeductions', title : '其它加扣', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodPredictiondeductions', title : '预估调整', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodMalldeductions', title : '商场扣额（含调整）', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'biPeriodBalanceamount', title : '结算收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'qty', title : '开票数量 ', width : 80, align:'right',rowspan:'2',halign:'center',exportType:'number'},
						{field : 'biBillingAmount', title : '开票金额（含调整）', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
						{field : 'sendAmount', title : '开票金额 ', width : 90, align:'right',rowspan:'2',halign:'center',exportType:'number'},
						{field : 'invoiceApplyDate', title : '开票日期 ', width : 80,rowspan:'2'},
						{field : 'salerName', title : '开票单位', width : 80,rowspan:'2'},
						                                                                                                                                 
						{field : 'tmaPeriodSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodRealamount', title : '终端销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodSalesamount', title : '销售收入 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodSalescost', title : '销售成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'tmaPeriodSalesdeductions', title : '扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPeriodBalanceamount', title : '结算收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'tmSalesnum', title : '销售量', width : 60, align:'right',halign:'center',exportType:'number'},
						{field : 'tmRealamount', title : '终端销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmSalesamount', title : '销售收入  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmTagamount', title : '牌价额', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmSalescost', title : '销售成本 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmContractdeductions', title : '合同正价扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmCutratedeductions', title : '折价加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmPromotiondeductions', title : '促销加扣扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmaPromDeductAmount', title : '保底扣费', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmOtherdeductions', title : '其它加扣', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmPredictiondeductions', title : '预估调整  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmSalesdeductions', title : '扣费  ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'tmBalanceamount', title : '结算收入', width : 90, align:'right',halign:'center',exportType:'number'},
						<!-- {field : 'sumChangebalanceamount', title : '调整前期扣费', width : 90, align:'right',halign:'center',exportType:'number'}, -->
						<!-- {field : 'sumSalesdeductions', title : '扣费合计', width : 90, align:'right',halign:'center',exportType:'number'}, -->
						{field : 'actualRate', title : '实际扣率', width : 80, align:'right',halign:'center',exportType:'number'},
						
						{field : 'systemDeductAmount', title : '系统扣费 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceDeductAmount', title : '票前费用 ', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceDiffAmount', title : '结算差异 ', width : 90, align:'right',halign:'center',exportType:'number'},
						
						{field : 'nonTaxSalesamount', title : '无税收入', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'taxCost', title : '含税成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'noTaxCosts', title : '无税成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'regionCost', title : '地区成本', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'headquarterCost', title : '总部成本', width : 90, align:'right',halign:'center',exportType:'number'}]"
						
						<!-- {field : 'tmNobillingSumamount', title : '累计未开票  ', width : 90, align:'right',halign:'center',exportType:'number'}, -->
						<!-- {field : 'balanceQty', title : '库存数量', width : 90, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceQtyAmount', title : '含税成本库存金额', width : 120, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceQtyRegionAmount', title : '地区成本库存金额', width : 120, align:'right',halign:'center',exportType:'number'},
						{field : 'balanceQtyHeadquarterAmount', title : '总部成本库存金额', width : 120, align:'right',halign:'center',exportType:'number'} --> 
				/>
			</#if>
			</div>
		</div>
	</div>
	<div id="printContrPanel" class="easyui-dialog" title="My Dialog" style="width:200px;height:200px;"   
	    data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	     <div align='center'>
	     	<form id="printForm">
		     		<select multiple id="printSelect" align="left" style="height:200px;width:100%">
					</select>
	     	</form>
	     </div>
	</div> 
	<div id="checkShopBalanceDatePanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="checkShopBalanceDateForm" id="checkShopBalanceDateForm" method="post"  class="pd10">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:480px;"  title="筛选条件" data-options="title:'筛选条件',fieldset:true,cls:'mt10'">
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
				        <th><span class="ui-color-red">*</span>销售日期：</th>
						 	<td>
							 	<input class="easyui-datebox ipt" style="width:110px" name="startDate" id="startDate" data-options="maxDate:'endDate',required:true"/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input class="easyui-datebox ipt" style="width:110px" name="endDate" id="endDate" data-options="minDate:'startDate',required:true"/>
							</td>
			           </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
   
   <div id="shopBalanceDateResultPanel" class="easyui-dialog" data-options="closed:true">
	 	<div class="easyui-layout" data-options="fit:true">
	 		<div data-options="region:'center',border:false" >
	 			<@p.datagrid id="shopBalanceDateResultDataGrid" emptyMsg = ""
	 				selectOnCheck="true" checkOnSelect="true" singleSelect="true"
					isHasToolBar="false"  onClickRowEdit="false" pagination="false"
					columnsJsonList="[
						{field:'shopNo',title:'店铺编号',width:80},
						{field:'shortName',title:'店铺名称',width:160},
						{field:'month',title:'结算月',width:100}]"
				  		 jsonExtend='{onDblClickRow:function(rowIndex, rowData){
									             }}' 
				  />
	 		</div>
	 	</div>
	</div>
			
	<div id="innerBuyBalanceDateResultPanel" class="easyui-dialog" data-options="closed:true">
	 	<div class="easyui-layout" data-options="fit:true">
	 		<div data-options="region:'center',border:false" >
	 			<@p.datagrid id="innerBuyBalanceDateResultDataGrid" emptyMsg = ""
	 				selectOnCheck="true" checkOnSelect="true" singleSelect="true"
					isHasToolBar="false"  onClickRowEdit="false" pagination="false"
					columnsJsonList="[
						{field:'companyNo',title:'公司编号',width:80},
						{field:'companyName',title:'公司名称',width:160},
						{field:'month',title:'结算月',width:100}]"
				  		 jsonExtend='{onDblClickRow:function(rowIndex, rowData){
									             }}' 
				  />
	 		</div>
	 	</div>
	</div>
</body>
</html>