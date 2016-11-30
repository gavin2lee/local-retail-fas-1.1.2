<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>销售回款分析</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/salesOutstandingAnalysis.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/salesOutstandingAnalysExportExcel.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>

</head>
<body>
<input type="hidden" value="${senda}" id="senda">
<input type="hidden" id="organTypeNo">
 <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
 	<div data-options="title:'销售回款明细'">
 	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" class="toolbar-region">
		      <@p.billToolBar type="sales_outstanding_analysis_listBar"/>
		</div>

		<div data-options="region:'center',border:false">
            <div class="easyui-layout" data-options="fit:true" id="subLayout">
			   <div data-options="region:'north',border:false">
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
								<th><span class="ui-color-red">*</span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区： </th>
									<td >
										<input class="easyui-zonebox ipt"  style="width:160px;"
										data-options="required:true,readonly:true" name="zoneNo" id="zoneNo"/>
									</td>
									<th><span class="ui-color-red">*</span>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
									<td>
										<input class="easyui-company ipt" name="companyName" id="companyName" data-options="inputWidth:130,multiple:true,required:true"  />
									    <input type="hidden" name="companyNo" id="companyNo"/>
									</td>
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="easyui-brand ipt" name="brandName" id="brandName" data-options="inputWidth:130,multiple:true"/>
										<input type="hidden" name="brandNo" id="brandNo"/>
									</td>
									<th>业务类型： </th>
									<td>						
										<input class="easyui-combobox ipt" name="businessType" id="businessType" data-options="required:false,multiple:true" readonly/>		
									</td>
								</tr>
								<tr>
									<th>管理城市： </th>
									<td>
									    <input class="easyui-organ ipt"  name="organName" id="organName" data-options="inputWidth:130,multiple:true"/>
								        <input type="hidden" name="organNo" id="organNo"/>
								    </td>
								
								    <th><span class="ui-color-red"></span>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺： </th>
									<td>
										<input class="easyui-shopCommon" id="shopName" />
										<input type="hidden" name="shopNo" id="shopNo"/>
									</td>
									<!--
									<th>商品名称： </th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameId" 
										data-options="inputCodeField:'itemCodeCondition',inputNameField:'itemNameId',inputWidth:130"/>
										<input type="hidden" name="itemCode" id="itemCodeCondition" />
									</td>
									-->
									<th>单据编号： </th>
									<td>
										<input class="easyui-validatebox ipt" name="orderNo" id="orderNo" />
									</td>
									<th align='right'>商品编码：</th>
							 		<td>
							  			<input class="easyui-itemCommon" id="itemName" />
										<input type="hidden" name="itemSql" id="itemNo"/>
							  			
							  		</td>
									
									<!--
									<th>活动类别： </th>
									<td>
										<input class="easyui-combobox ipt" name="actionCategory" id="actionCategory" />
								    </td> 
								    -->
								</tr>
								<tr>
									<th>活动编码： </th>
									<td>
										<input class="easyui-validatebox ipt" name="proNo" id="proNo" data-options="inputWidth:130"/>
									</td>						  
									<th>扣率代码： </th>
									<td>
										<input class="easyui-validatebox ipt" name="discountCode" id="discountCode" />
									</td>
									<th>活动名称： </th>
									<td>
										<input class="easyui-validatebox ipt" name="proName" id="proName" data-options="inputWidth:130"/>
									</td>						  
									<th>活动属性： </th>
									<td>
										<input class="easyui-combobox ipt" name="properties" id="properties" />
									</td>
								</tr>
								<tr>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td>
										<input class="easyui-categorybox ipt" name="categoryNo" id="categoryNo" data-options="multiple:true,readonly:true"/>
									</td>
									<th>结&nbsp;&nbsp;算&nbsp;&nbsp;月：</th>
						       		<td>
						       		 	<input class="Wdate ipt" name="month" id="month"
						       		 	onFocus="WdatePicker({dateFmt:'yyyyMM',onpicked:function(dt) {
						       		 	$('#outDateStart').datebox('disable');
						       		 	$('#outDateEnd').datebox('disable');
						       		 	},onclearing:function(){
						       		 	$('#outDateStart').datebox('enable');
						       		 	$('#outDateEnd').datebox('enable');}})"/> 
						       		</td>
									<th><span class="ui-color-red">*</span>销售日期：</th>
						    		<td ><input class="easyui-validatebox easyui-datebox ipt"  name="outDateStart" id="outDateStart" data-options="required:true,maxDate:'outDateEnd'"/></td>
									    <th>&nbsp;&nbsp;&nbsp;&nbsp;-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-validatebox easyui-datebox ipt" name="outDateEnd" id="outDateEnd" data-options="required:true,minDate:'outDateStart'"/></td>
								</tr>
								<tr>
									<th>客户名称：</th>
		                            <td>
		                            	<input class="easyui-customer ipt" name="customerName" id="customerName" data-options="multiple:true,inputWidth:130"/>
		                            	<input type="hidden" name="customerNo" id="customerNo"/>
		                            </td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
        	<#if senda=='true'>
		      <@p.datagrid id="salesOutstandingAnalysisDataGrid" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" 
					rownumbers="true"  singleSelect="true" 
			           columnsJsonList="[			           
							{field : 'zoneName', title : '地区', width : 60},                                                                                                                                                                                                                                       
							<!--{field : 'provinceName', title : '省份 ', width : 60},  -->   
							{field : 'companyName', title : '公司名称 ', width : 200},                                                                                                                                                                                                                             
							{field : 'organName1', title : '管理城市 ', width : 80},                                                                                                                                                                 
							{field : 'organName2', title : '经营城市', width : 80},
							{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center'},
							{field : 'mallName', title : '商场 ', width : 80,align:'left',halign:'center'}, 
							{field : 'cmcdistName', title : '商圈 ', width : 80,align:'left',halign:'center'}, 
							{field : 'regionName', title : '片区 ', width : 80}, 
							{field : 'bizType', title : '业务类型', width : 80},      
							{field : 'shopLevel', title : '店铺级别', width : 80},
							{field : 'saleMode', title : '店铺大类', width : 80},
							{field : 'retailType', title : '店铺小类', width : 80},
							{field : 'multi', title : '店铺细类', width : 80},
							{field : 'shopNo', title : '店铺编码/客户编号', width : 120,align:'left',halign:'center'},   
							{field : 'shortName', title : '店铺名称/客户名称', width : 120,align:'left',halign:'center'},
							{field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},                                                                                                                                                                                                                               
							{field : 'itemName', title : '商品名称 ', width : 180,align:'left',halign:'center'}, 
							{field : 'brandUnitName', title : '品牌部', width : 90},   
							{field : 'brandName', title : '品牌', width : 90},                                                                                                                                                                                                                           
							{field : 'categoryName', title : '大类 ', width : 90},                                                                                                                                                                                                                           
							{field : 'rootCategoryName', title : '中类 ', width : 90},                                                                                                                                                                                                                          
							{field : 'subCategoruName', title : '小类', width : 90}, 
							{field : 'year', title : '年份 ', width : 60},      							                                                                                                                                                                                                                                       
							{field : 'sellSeason', title : '货品季节 ', width : 60}, 
							<!-- {field : 'styleName', title : '新旧款 ', width : 80}, -->                                                                                                                                                                                                                              
							{field : 'gender', title : '性别', width : 80},                                                                                                                                                                                                                                        
							{field : 'orderfrom', title : '订货形式 ', width : 80},                                                                                                                                                                                                                             
							{field : 'style', title : '风格', width : 60},                                                                                                                                                                                                                                        
							{field : 'colorName', title : '颜色', width : 60}, 
							{field : 'wildcardNo', title : '外卡编号', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'wildcardName', title : '外卡名称', width : 90, align:'right'},  	
							{field : 'launchTypeStr', title : '活动来源', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'activityTypeStr', title : '活动类别', width : 90, align:'right'}, 
							{field : 'proNo', title : '活动编码', width : 90, align:'right'},                                                                                                                                                                                                                        
							{field : 'proName', title : '活动名称', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'proStartDate', title : '活动起始日期', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'proEndDate', title : '活动终止日期 ', width : 90, align:'right'},
							{field : 'strength', title : '活动力度', width : 60, align:'right'},                                                                                                                                                                                                                         
							{field : 'virtualFlagStr', title : '虚实属性', width : 90, align:'right'},
	                        {field : 'propertiesStr', title : '活动属性', width : 90, align:'right'},    
	                        {field : 'rateTypeStr', title : '扣率类型', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'rateCode', title : '扣率代码', width : 90, align:'right'},  
							{field : 'rateName', title : '扣率名称', width : 90, align:'right'},   
							{field : 'rateDiscount', title : '活动折扣', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                 
							{field : 'rate', title : '扣率', width : 90, align:'right',exportType:'number'},	
							{field : 'billingCode', title : '商场结算码 ', width : 90, align:'right'},					 	  		  	  	  	  	  	 	                                                                                                                                                                                                                                      
							{field : 'outDate', title : '销售日期', width : 90}, 
							{field : 'orderNo', title : '单据编号', width : 130},
							{field : 'balanceBaseName', title : '结算基数 ', width : 90, align:'right'},    
							{field : 'qty', title : '数量', width : 60, align:'right',exportType:'number'},   
							{field : 'tagAmount', title : ' 牌价额', width : 90, align:'right',exportType:'number'}, 
							{field : 'saleAmount', title : ' 现价额', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                           
							{field : 'discPrice', title : '折后价收入', width : 90, align:'right',exportType:'number'}, 
							{field : 'amount', title : '终端销售收入', width : 90, align:'right',exportType:'number'}, 
							{field : 'virtualAmount', title : '虚数活动实际金额', width : 110, align:'right',exportType:'number'}, 
							{field : 'unitCost', title : '单位成本 ', width : 90, align:'right',exportType:'number'},   
							{field : 'regionCost', title : '地区成本 ', width : 90, align:'right',exportType:'number'},
							{field : 'cost', title : '总部成本 ', width : 90, align:'right',exportType:'number'},   
							{field : 'purchasePrice', title : '采购成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                              
							{field : 'deductAmount', title : ' 扣费 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'contractRate', title : '合同扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceDeductAmount', title : '合同正价扣费', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceLadderAmount', title : '合同阶梯加扣', width : 90, align:'right',exportType:'number'},
							{field : 'promPlusbuckleAmount', title : '促销活动加扣', width : 90, align:'right',exportType:'number'},
							<!-- {field : 'differenceAmount', title : '差异金额 ', width : 90, align:'right',exportType:'number'},-->
							{field : 'backAmount', title : '回款额 ', width : 90, align:'right',exportType:'number'},
							{field : 'discountRate', title : ' 折扣率', width : 90, align:'right',exportType:'number'},
							{field : 'sdGrossMarginRate', title : '毛利率 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                     
							{field : 'deductionRate', title : '扣费率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                   
							{field : 'conpriceDeductRate', title : '合同正价扣费率', width : 100, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
							{field : 'conpriceLadderRate', title : '合同阶梯扣加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'promPlusbuckleRate', title : '促销活动加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'theDiscountRate', title : '净折扣率', width : 80,exportType:'number'},                                                                                                                                                                                                
							{field : 'sdTheMarginRate', title : '净毛利率 ', width : 90, align:'right',exportType:'number'},      
							{field : 'reimbursementRate', title : '回款率 ', width : 90, align:'right',exportType:'number'}]" 
                 />
             <#else>
                 <@p.datagrid id="salesOutstandingAnalysisDataGrid" loadUrl="" saveUrl="" defaultColumn="" showFooter="true" 
					isHasToolBar="false" onClickRowEdit="false" pagination="true" pageSize="20" 
					rownumbers="true"  singleSelect="true" 
			           columnsJsonList="[			           
							{field : 'zoneName', title : '地区', width : 60},                                                                                                                                                                                                                                       
							<!--{field : 'provinceName', title : '省份 ', width : 60},  -->   
							{field : 'companyName', title : '公司名称 ', width : 200},                                                                                                                                                                                                                             
							{field : 'organName1', title : '管理城市 ', width : 80},                                                                                                                                                                 
							{field : 'organName2', title : '经营城市', width : 80},
							{field : 'bsgroupsName', title : '商业集团 ', width : 150,align:'left',halign:'center'},
							{field : 'mallName', title : '商场 ', width : 80,align:'left',halign:'center'}, 
							{field : 'cmcdistName', title : '商圈 ', width : 80,align:'left',halign:'center'}, 
							{field : 'regionName', title : '片区 ', width : 80}, 
							{field : 'bizType', title : '业务类型', width : 80},      
							{field : 'shopLevel', title : '店铺级别', width : 80},
							{field : 'saleMode', title : '店铺大类', width : 80},
							{field : 'retailType', title : '店铺小类', width : 80},
							{field : 'multi', title : '店铺细类', width : 80}, 
							{field : 'shopNoReplace', title : '店铺替换编码', width : 100},                                                                                                                                                                                                                               
							{field : 'shopNo', title : '店铺编码/客户编号', width : 120,align:'left',halign:'center'},   
							{field : 'shortName', title : '店铺名称/客户名称', width : 120,align:'left',halign:'center'},
							{field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},                                                                                                                                                                                                                               
							{field : 'itemName', title : '商品名称 ', width : 180,align:'left',halign:'center'}, 
							{field : 'brandUnitName', title : '品牌部', width : 90},   
							{field : 'brandName', title : '品牌', width : 90},                                                                                                                                                                                                                           
							{field : 'categoryName', title : '大类 ', width : 90},                                                                                                                                                                                                                           
							{field : 'rootCategoryName', title : '中类 ', width : 90},                                                                                                                                                                                                                          
							{field : 'subCategoruName', title : '小类', width : 90}, 
							{field : 'year', title : '年份 ', width : 60},      							                                                                                                                                                                                                                                       
							{field : 'sellSeason', title : '货品季节 ', width : 60}, 
							{field : 'purchaseSeason', title : '季节 ', width : 60,organType:'U010105'}, 
							{field : 'itemFlag', title : '商品标志 ', width : 60,organType:'U010105'},                                                                                                                                                                                                                                            
							<!-- {field : 'styleName', title : '新旧款 ', width : 80}, -->                                                                                                                                                                                                                              
							{field : 'gender', title : '性别', width : 80},                                                                                                                                                                                                                                        
							{field : 'orderfrom', title : '订货形式 ', width : 80},                                                                                                                                                                                                                             
							{field : 'style', title : '风格', width : 60},                                                                                                                                                                                                                                        
							{field : 'colorName', title : '颜色', width : 60}, 
							{field : 'wildcardNo', title : '外卡编号', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'wildcardName', title : '外卡名称', width : 90, align:'right'},  	
							{field : 'launchTypeStr', title : '活动来源', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'activityTypeStr', title : '活动类别', width : 90, align:'right'}, 
							{field : 'proNo', title : '活动编码', width : 90, align:'right'},                                                                                                                                                                                                                        
							{field : 'proName', title : '活动名称', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'proStartDate', title : '活动起始日期', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'proEndDate', title : '活动终止日期 ', width : 90, align:'right'},
							{field : 'strength', title : '活动力度', width : 60, align:'right'},                                                                                                                                                                                                                         
							{field : 'virtualFlagStr', title : '虚实属性', width : 90, align:'right'},
	                        {field : 'propertiesStr', title : '活动属性', width : 90, align:'right'},    
	                        {field : 'rateTypeStr', title : '扣率类型', width : 90, align:'right'},                                                                                                                                                                                                                      
							{field : 'rateCode', title : '扣率代码', width : 90, align:'right'},  
							{field : 'rateName', title : '扣率名称', width : 90, align:'right'},   
							{field : 'rateDiscount', title : '活动折扣', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                 
							{field : 'rate', title : '扣率', width : 90, align:'right',exportType:'number'},	
							{field : 'billingCode', title : '商场结算码 ', width : 90, align:'right'},					 	  		  	  	  	  	  	 	                                                                                                                                                                                                                                      
							{field : 'outDate', title : '销售日期', width : 90}, 
							{field : 'orderNo', title : '单据编号', width : 130},
							{field : 'balanceBaseName', title : '结算基数 ', width : 90, align:'right'},    
							{field : 'qty', title : '数量', width : 60, align:'right',exportType:'number'},   
							{field : 'tagAmount', title : ' 牌价额', width : 90, align:'right',exportType:'number'}, 
							{field : 'saleAmount', title : ' 现价额', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                           
							{field : 'discPrice', title : '折后价收入', width : 90, align:'right',exportType:'number'}, 
							{field : 'amount', title : '终端销售收入', width : 90, align:'right',exportType:'number'}, 
							{field : 'virtualAmount', title : '虚数活动实际金额', width : 110, align:'right',exportType:'number'}, 
							{field : 'unitCost', title : '单位成本 ', width : 90, align:'right',exportType:'number'},   
							{field : 'regionCost', title : '地区成本 ', width : 90, align:'right',exportType:'number'},
							{field : 'cost', title : '总部成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                 
							{field : 'deductAmount', title : ' 扣费 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'contractRate', title : '合同扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceDeductAmount', title : '合同正价扣费', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceLadderAmount', title : '合同阶梯加扣', width : 90, align:'right',exportType:'number'},
							{field : 'promPlusbuckleAmount', title : '促销活动加扣', width : 90, align:'right',exportType:'number'},
							<!-- {field : 'differenceAmount', title : '差异金额 ', width : 90, align:'right',exportType:'number'},-->
							{field : 'backAmount', title : '回款额 ', width : 90, align:'right',exportType:'number'},
							{field : 'discountRate', title : ' 折扣率', width : 90, align:'right',exportType:'number'},
							{field : 'grossMarginRate', title : '毛利率 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                     
							{field : 'deductionRate', title : '扣费率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                   
							{field : 'conpriceDeductRate', title : '合同正价扣费率', width : 100, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
							{field : 'conpriceLadderRate', title : '合同阶梯扣加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'promPlusbuckleRate', title : '促销活动加扣率', width : 100,exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'theDiscountRate', title : '净折扣率', width : 80,exportType:'number'},                                                                                                                                                                                                
							{field : 'theMarginRate', title : '净毛利率 ', width : 90, align:'right',exportType:'number'},      
							{field : 'reimbursementRate', title : '回款率 ', width : 90, align:'right',exportType:'number'}]" 
                 />
             </#if>
			</div>
		</div>
	</div>
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
</body>
</html>