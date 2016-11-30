<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>体育收发货对账报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/top_sports/receiveSendCheck.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<#--最外层框-->
<div data-options="region:'center',border:false">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'明细核对'">
		<div id="subLayoutId" class="easyui-layout" data-options="fit:true,border:false">
			<#--按钮-->
			<div data-options="region:'north',border:false" class="toolbar-region">
			   	<@p.toolbar id="toolbar"  listData=[
			    	{"title":"查询","iconCls":"icon-search","action":"tsReportCheck.searchInfo()", "type":0},
			        {"title":"清空","iconCls":"icon-empty","action":"tsReportCheck.searchClear()", "type":0},
			        {"title":"导出","iconCls":"icon-export","action":"tsReportCheck.exportDtl()", "type":4}
			     ]/>
			</div>
				
			<div data-options="region:'center',border:false" style="height:200px;">		
				<div class="easyui-layout" data-options="fit:true" id="subLayout">
					<div data-options="region:'north',border:false">
					<div class="search-div">
					   <#-- 主档信息  -->
		                <form id="searchForm"  method="post">
				    	<table class="form-tb">
		            	    <col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		                    <tbody>
		           				<tr>	
		           					<th><span class="ui-color-red">*</span>前月起始：</th>
									<td>
										<input class="easyui-datebox ipt"  name="lastDateStart" id="lastDateStart" 
										data-options="required:true,maxDate:'currentDateStart',width:130" readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>本月区间：</th>
									<td>
										<input class="easyui-datebox ipt"  name="currentDateStart" id="currentDateStart" 
										data-options="required:true,maxDate:'currentDateEnd',width:130" readonly="true"/>
									</td>
									<th>— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input class="easyui-datebox ipt" name="currentDateEnd" id="currentDateEnd" 
										data-options="required:true,minDate:'currentDateStart',width:130" readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>下月终止：</th>
									<td>
										<input class="easyui-datebox ipt" name="nextDateEnd" id="nextDateEnd" 
										data-options="required:true,minDate:'currentDateEnd',width:130" readonly="true"/>
									</td>
								</tr>
									<th>收方公司：</th>
									<td>
										<input class="easyui-company ipt"  name="buyerName" id="buyerNameId" 
										data-options="inputNoField:'buyerNoId',inputNameField:'buyerNameId',isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
								    <th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
		                    		<td>
		                    			<input class="easyui-brand ipt" name="brandName" id="brandName" 
		                    			data-options="multiple:true,inputNoField:'brandNo',inputNameField:'brandName'"/>
		                    			<input type="hidden" name="brandNo" id="brandNo"/>
		                    		</td>
									<th>收方管理城市： </th>
									<td>
										<input class="easyui-organ" name="organName" id="organName"
									    data-options="inputNoField:'organNo',inputNameField:'organName',multiple:true"/>
								        <input type="hidden" name="organNo" id="organNo"/>
								    </td>
								    <th>收方货管单位：</th>
									<td>
										<input class="easyui-orderUnit  ipt" name="orderUnitName" id="orderUnitName"
										data-options="inputNoField:'orderUnitNo',inputNameField:'orderUnitName',multiple:true" />
										<input type="hidden" name="orderUnitNo" id="orderUnitNo"/>
									</td>
								</tr>
								<tr>
									<th>发方公司：</th>
									<td>
										<input class="easyui-salerCompany ipt"  name="salerName" id="salerNameId" 
										data-options="inputNoField:'salerNoId',inputNameField:'salerNameId'"/>
										<input type="hidden" name="salerNo" id="salerNoId"/>
									</td>
								    <th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th> 
									<td>
										<input class="easyui-categorybox ipt " name="categoryNo" id="categoryNo" data-options="multiple:true,width:140"/>
									</td>
									<th>发方管理城市： </th>
									<td>
									    <input class="easyui-organ" name="organNameFrom" id="organNameFormId"
									   	data-options="inputNoField:'organNoFromId',inputNameField:'organNameFormId',multiple:true"/>
								        <input type="hidden" name="organNoFrom" id="organNoFromId"/>
								    </td>
								    <th>发方货管单位：</th>
									<td>
										<input class="easyui-orderUnit" id="orderUnitFromNameId" name="orderUnitNameFrom"   
										data-options="inputNoField:'orderUnitNoFromId',inputNameField:'orderUnitFromNameId',multiple:true"/> 
										<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromId"/>
									</td>
								</tr>
								<tr>
								    <th>结算类型：</th> 
									<td>
										<input class="easyui-validatebox ipt" name="balanceTypes" id="balanceType" data-options="multiple:true,width:140"/>
									</td>
									<th>商品编码：</th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameId" data-options="inputCodeField:'itemCodeCondition',inputNameField:'itemNameId',inputWidth:140"/>
										<input type="hidden" name="itemCode" id="itemCodeCondition" />
									</td>
									<th>显示小计：</th> 
									<td>
										<input type="checkbox" style="width: 30px; height: 15px; padding: 0 5px 0 0;" id="countOnly"/>
									</td>
									<th></th>
									<td></td>
								</tr>
								<tr>
									<th><span class="l-btn-text icon-xq l-btn-icon-left" id="columnDsc">tip 1</span></th> 
									<td align="center"><span class="l-btn-text icon-xq l-btn-icon-left" id="billTypeNameId">tip 2</span></td>
									<th></th>
									<td></td>
									<th></th> 
									<td></td>
									<th></th>
									<td></td>
								</tr>
		           				</tbody>
		                 	</table> 	 
						</form>
						</div>
					</div>
						
					<#--列表-->
			        <div data-options="region:'center',border:false">
						<@p.datagrid id="dtlDataGrid" loadUrl="" saveUrl=""  defaultColumn=""  
			      			 isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"  pagination="true" 
			       			 rownumbers="true" singleSelect="false"  showFooter="true"  pageSize="20"
		            		 columnsJsonList="[
		            		    {field : 'balanceTypeName',title : '结算类型',width: 80,rowspan:'2'},
							    {field : 'billNo',title : '单据编号',width: 150,align:'left',halign:'center',rowspan:'2'},
   							    {field : 'billTypeName',title : '单据类型',width: 80,align:'center',halign:'center',rowspan:'2'},
				                {field : 'zoneName',title : '收方大区',width: 60,align:'center',halign:'center',rowspan:'2'},
								{field : 'buyerName',title : '收方公司',width: 200,align:'left',halign:'center',rowspan:'2'},
								{field : 'organName',title : '收方管理城市',width: 90,align:'center',halign:'center',rowspan:'2'},
								{field : 'orderUnitName',title : '收方货管单位',width: 90,align:'center',halign:'center',rowspan:'2'},
								
								{field : 'zoneNameFrom',title : '发方大区',width: 60,align:'center',halign:'center',rowspan:'2'},
								{field : 'salerName',title : '发方公司',width: 200,align:'left',halign:'center',rowspan:'2'},
								{field : 'organNameFrom',title : '发方管理城市',width: 90,align:'center',halign:'center',rowspan:'2'},
								{field : 'orderUnitNameFrom',title : '发方货管单位',width: 90,align:'center',halign:'center',rowspan:'2'},
								
								{field : 'itemCode',title : '商品编码',width: 150,align:'left',halign:'center',rowspan:'2'},
								{field : 'itemName',title : '商品名称',width: 180,align:'left',halign:'center',rowspan:'2'},
							    {field : 'brandUnitName',title : '品牌部',width: 100,align:'center',rowspan:'2'},
								{field : 'brandName',title : '品牌',width: 100,align:'center',rowspan:'2'},
								{field : 'oneLevelCategoryName',title : '大类',width: 80,align:'center',rowspan:'2'},
								{field : 'twoLevelCategoryName',title : '中类',width: 80,align:'center',rowspan:'2'},
								{field : 'categoryName',title : '小类',width: 80,align:'center',rowspan:'2'},
								{field : 'seasonName',title : '季节',width: 60,align:'center',rowspan:'2'},
								{field : 'yearsName',title : '年份',width: 60,align:'center','exportType':'number',rowspan:'2'},
								{field : 'genderName',title : '性别',width: 60,align:'center',rowspan:'2'},
								
								{field : 'sendDate',title : '发出日期',width: 90,align:'center',rowspan:'2'},
								{field : 'receiveDate',title : '收货日期',width: 90,align:'center',rowspan:'2'},
								
								{title : '本月发出',colspan:'2'},
								{title : '本月发本月收',colspan:'2'},
								{title : '本月发下月收',colspan:'2'},
								{title : '本月发未收',colspan:'2'},
								{title : '本月差异',colspan:'2'},
								{title : '前月发出',colspan:'2'},
								{title : '前月发前月收',colspan:'2'},
								{title : '前月发本月收',colspan:'2'},
								{title : '前月发未收',colspan:'2'},
								{title : '前月差异',colspan:'2'}],
							   
								[{field : 'currMonSenNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'currMonSenRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'currMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'currMonRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'nextMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'nextMonRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'currMonYetRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'currMonYetRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'currMonDiffNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'currMonDiffRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'lastMonSenNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'lastMonSenRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'lastMonLastRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'lastMonLastRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'lastMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'lastMonRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'lastMonYetRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'lastMonYetRecRMB',title : '金额',width: 100,align:'right','exportType':'number'},
								
								{field : 'lastMonDiffNum',title : '数量',width: 80,align:'right','exportType':'number'},
								{field : 'lastMonDiffRMB',title : '金额',width: 100,align:'right','exportType':'number'}
			                 ]"
		 				jsonExtend='{onLoadSuccess:function(rowData){
		 							tsReportCheck.compute(rowData);
							}
					}'
			 		/>
				</div>
			  </div>
			</div>
		</div>
	</div>
</div>

</body>
</html>