<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar_collect" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"salesOutstandingAnalysis.searchCollect()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"salesOutstandingAnalysis.clearCollect()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"salesOutstandingAnalysis.exportExcelCollect()", "type":0}
           ]
           />
	</div>

	<div data-options="region:'center',border:false">
         <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <div data-options="region:'north',border:false">
		        <div class="search-div">
			      	<form name="searchForm1" id="searchForm1" method="post">
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
										data-options="required:true,readonly:true"  name="zoneNo" id="zoneNo_" />
									</td>
									<th><span class="ui-color-red">*</span>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
									<td>
										<input class="easyui-company ipt" name="companyName" id="companyName1" 
											data-options="inputWidth:130,inputNoField:'companyNo1', inputNameField:'companyName1',multiple:true,required:true"  />
									    <input type="hidden" name="companyNo" id="companyNo1"/>
									</td>
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="easyui-brand ipt" name="brandName" id="brandName1" 
											data-options="inputWidth:130,inputNoField:'brandNo1', inputNameField:'brandName1',multiple:true"/>
										<input type="hidden" name="brandNo" id="brandNo1"/>
									</td>
									<th>管理城市： </th>
									<td>
									    <input class="easyui-organ ipt"  name="organName" id="organName1" 
									    	data-options="inputWidth:130,inputNoField:'organNo1',inputNameField:'organName1',multiple:true"/>
								        <input type="hidden" name="organNo" id="organNo1"/>
								    </td>
								</tr>
								<tr>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td>
										<input class="easyui-categorybox ipt" name="categoryNo" id="categoryNo1" data-options="multiple:true,readonly:true" />
									</td>
									<th>业务类型： </th>
									<td>						
										<input class="easyui-combobox ipt" name="businessType1" id="businessType1" data-options="required:false,multiple:true" readonly/>		
									</td>
									<th><span class="ui-color-red">*</span>销售日期：</th>
						    		<td ><input class="easyui-datebox ipt"  name="outDateStart" id="outDateStart1" data-options="required:true,maxDate:'outDateEnd1'"/></td>
									    <th>&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt" name="outDateEnd" id="outDateEnd1" data-options="required:true,minDate:'outDateStart1'"/></td>
								</tr>
								<tr>
									<th><span class="ui-color-red"></span>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺： </th>
									<td>
										<input class="easyui-shopCommon" id="shopName1" data-options="inputNoField:'shopNo1'"/>
										<input type="hidden" name="shopNo" id="shopNo1"/>
									</td>
									<th>活动编码： </th>
									<td>
										<input class="easyui-validatebox ipt" name="proNo" id="proNo1" data-options="inputWidth:130"/>
									</td>	
									<th>结算月：</th>
						       		<td>
						       		 	<input class="Wdate" name="month" id="month1"
						       		 	onFocus="WdatePicker({dateFmt:'yyyyMM',onpicked:function(dt) {
						       		 	$('#outDateStart1').datebox('disable');
						       		 	$('#outDateEnd1').datebox('disable');
						       		 	},onclearing:function(){
						       		 	$('#outDateStart1').datebox('enable');
						       		 	$('#outDateEnd1').datebox('enable');}})"/> 
						       		</td>
								</tr>
							</tbody>
						</table>
						<table class="form-tb">
							<col width="85" />
							<col width="120" />
							<col width="70" />
							<col width="80" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
							<col width="70" />
							<col width="95" />
							<col width="70" />
							<tr>
								<th style="color:red;">汇总维度</th>
								<th>店铺/客户名称：<input type="checkbox" checked="checked" name="isCheckShopNo" id="isCheckShopNo" value="1"/></th>
								<th>性别：<input type="checkbox" checked="checked" name="isCheckGender" id="isCheckGender" value="1"/></th>
								<th>品牌部：<input type="checkbox" checked="checked" name="isCheckBrandUnit" id="isCheckBrandUnit" value="1"/></th>
								<th>大类：<input type="checkbox" checked="checked" name="isCheckCategory" id="isCheckCategory" value="1"/></th>
								<th>中类：<input type="checkbox" checked="checked" name="isCheckRootCategory" id="isCheckRootCategory" value="1"/></th>
								<th>年份：<input type="checkbox" checked="checked" name="isCheckYear" id="isCheckYear" value="1"/></th>
								<th>季节：<input type="checkbox" checked="checked" name="isCheckSellSeason" id="isCheckSellSeason" value="1"/></th>
								<th>扣率：<input type="checkbox" checked="checked" name="isCheckRate" id="isCheckRate" value="1"/></th>
								<th>扣率代码：<input type="checkbox" checked="checked" name="isCheckRateCode" id="isCheckRateCode" value="1"/></th>
								<#if organTypeNo=='U010105'>
									<th>款号：<input type="checkbox" checked="checked" name="isStyleNo" id="isStyleNo" value="1"/></th>
								</#if>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
        	<#if sendas=='true'>
		     <@p.datagrid id="salesOutstandingCollectDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
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
							{field : 'brandUnitName', title : '品牌部', width : 90},  
							{field : 'categoryName', title : '大类 ', width : 90}, 
							{field : 'rootCategoryName', title : '中类 ', width : 90}, 
							{field : 'sellSeason', title : '季节 ', width : 60},                                                                                                                                                                                   
							{field : 'year', title : '年份 ', width : 60},      							                                                                                                                                                                                                                                       
							{field : 'gender', title : '性别', width : 80}, 
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
							{field : 'rate', title : '扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                        
							{field : 'qty', title : '数量', width : 90, align:'right',exportType:'number'},   
							{field : 'tagAmount', title : ' 牌价额', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                           
							{field : 'discPrice', title : '折后价收入', width : 90, align:'right',exportType:'number'}, 
							{field : 'amount', title : '终端销售收入', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                            
							{field : 'unitCost', title : '单位成本 ', width : 90, align:'right',exportType:'number'},  
							{field : 'regionCost', title : '地区成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                 
							{field : 'cost', title : '总部成本 ', width : 90, align:'right',exportType:'number'},  
							{field : 'purchasePrice', title : '采购成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                             
							{field : 'deductAmount', title : ' 扣费 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'contractRate', title : '合同扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceDeductAmount', title : '合同正价扣费', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceLadderAmount', title : '合同阶梯加扣', width : 90, align:'right',exportType:'number'},  								  	  	  	  	  	 	                                                                                                                                                                                                                                  
							{field : 'promPlusbuckleAmount', title : '促销活动加扣', width : 90, align:'right',exportType:'number'},
							<!-- {field : 'differenceAmount', title : '差异金额 ', width : 90, align:'right',exportType:'number'}, -->
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
                <@p.datagrid id="salesOutstandingCollectDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
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
							{field : 'brandUnitName', title : '品牌部', width : 90},  
							{field : 'categoryName', title : '大类 ', width : 90}, 
							{field : 'rootCategoryName', title : '中类 ', width : 90}, 
							{field : 'sellSeason', title : '季节 ', width : 60},                                                                                                                                                                                   
							{field : 'year', title : '年份 ', width : 60},      							                                                                                                                                                                                                                                       
							{field : 'gender', title : '性别', width : 80}, 
							{field : 'styleNo',title : '款号',width: 100,align:'right',organType:'U010105'}, 
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
							{field : 'rate', title : '扣率', width : 90, align:'right',exportType:'number'}, 
							{field : 'qty', title : '数量', width : 90, align:'right',exportType:'number'},   
							{field : 'tagAmount', title : ' 牌价额', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                           
							{field : 'discPrice', title : '折后价收入', width : 90, align:'right',exportType:'number'}, 
							{field : 'amount', title : '终端销售收入', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                            
							{field : 'unitCost', title : '单位成本 ', width : 90, align:'right',exportType:'number'},  
							{field : 'regionCost', title : '地区成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                 
							{field : 'cost', title : '总部成本 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                              
							{field : 'deductAmount', title : ' 扣费 ', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'contractRate', title : '合同扣率', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceDeductAmount', title : '合同正价扣费', width : 90, align:'right',exportType:'number'},                                                                                                                                                                                                                                  
							{field : 'conpriceLadderAmount', title : '合同阶梯加扣', width : 90, align:'right',exportType:'number'},  								  	  	  	  	  	 	                                                                                                                                                                                                                                  
							{field : 'promPlusbuckleAmount', title : '促销活动加扣', width : 90, align:'right',exportType:'number'},
							<!-- {field : 'differenceAmount', title : '差异金额 ', width : 90, align:'right',exportType:'number'}, -->
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

<div id="printCollectContrPanel" class="easyui-dialog" title="My Dialog" style="width:200px;height:200px;"   
	    data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">   
	     <div align='center'>
	     	<form id="printCollectForm">
		     		<select multiple id="printCollectSelect" align="left" style="height:200px;width:100%">
					</select>
	     	</form>
	     </div>
</div>