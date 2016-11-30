<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>收发货对账报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/report/transferingCheck.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<@p.toolbar id="toolbar"  listData=[
	    	{"title":"查询","iconCls":"icon-search","action":"transferingCheck.searchInfo()", "type":0},
	        {"title":"清空","iconCls":"icon-empty","action":"transferingCheck.searchClear()", "type":0},
	        {"title":"导出汇总","iconCls":"icon-export","action":"transferingCheck.exportTotal()", "type":4},
	        {"title":"导出明细","iconCls":"icon-export","action":"transferingCheck.exportDtl()", "type":4}
	     ]/>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true" id="subLayout">
				<div data-options="region:'north',border:false">
					<div class="search-div">
					   <form id="searchForm"  method="post">
				    	<table class="form-tb">
		            	    <col width="100px"/>
		            	 	<col />
		            	 	<col width="100px"/>
		            	 	<col />
		            	 	<col width="100px"/>
		            	 	<col />
		            	 	<col width="100px"/>
		            	 	<col />
		                    <tbody>
		           				<tr>	
		           					<th>发方公司：</th>
									<td>
										<input class="easyui-salerCompany ipt"  name="salerName" id="salerNameId" 
											data-options="inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:200"/>
										<input type="hidden" name="salerNo" id="salerNoId"/>
										<input type="hidden" name="type" id="type" value="${type}"/>
									</td>
		           					<th>发方大区：</th>
									<td>
										<input class="easyui-zonebox "   name="zoneNoFrom"  id="zoneNoFrom"  />
									</td>
									<th>发方货管单位：</th>
									<td>
										<input class="easyui-validatebox ipt easyui-orderUnit" id="orderUnitFromNameId" name="orderUnitNameFrom"  style="width:130px"  
											data-options="inputNoField:'orderUnitNoFromId',inputNameField:'orderUnitFromNameId',multiple:true"/> 
										<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromId"/>
									</td>
									<th>发方管理城市： </th>
									<td>
									    <input class="easyui-validatebox ipt easyui-organ"  style="width:60px;"  name="organNameFrom" id="organNameFormId"
									   		 data-options="inputNoField:'organNoFromId',inputNameField:'organNameFormId',multiple:true"/>
								        <input type="hidden" name="organNoFrom" id="organNoFromId"/>
								    </td>
								</tr>
								   	<th>收方公司：</th>
									<td>
										<input class="easyui-company ipt"  name="buyerName" id="buyerNameId" 
										data-options="inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:200"/>
										<input type="hidden" name="buyerNo" id="buyerNoId"/>
									</td>
		           					<th>收方大区：</th>
									<td>
										<input class="easyui-zonebox ipt"   name="zoneNo"  id="zoneNo"  />
									</td>
									<th>收方货管单位：</th>
									<td>
										<input class="easyui-orderUnit  ipt" name="orderUnitName"  style="width:130px"   data-options="multiple:true" />
										<input type="hidden" name="orderUnitNo" id="orderUnitNo"/>
									</td>
									<th>收方管理城市： </th>
									<td>
										<input class="easyui-validatebox ipt easyui-organ"  style="width:60px;"  name="organName" id="organName"
									    data-options="inputNoField:'organNo',inputNameField:'organName',multiple:true"/>
								        <input type="hidden" name="organNo" id="organNo"/>
								    </td>
								</tr>
								<tr>
									<th>结算类型：</th> 
									<td>
										<input class="easyui-validatebox ipt" style="width:200px" name="balanceTypes" id="balanceType" data-options="multiple:true"/>
									</td>
									<th>大类：</th> 
									<td>
										<input class="easyui-categorybox ipt " name="categoryNo" id="categoryNo" data-options="multiple:true"/>
									</td>
									<th>单价来源：</th> 
									<td><input class="easyui-validatebox ipt" name="priceFrom" id="priceFrom" style="width:140px"/></td>
									<th>品牌：</th>
		                    		<td>
		                    			<input class="easyui-brand ipt" style="width:130px" name="brandName" id="brandName" data-options="multiple:true"/>
		                    			<input type="hidden" name="brandNo" id="brandNo"/>
		                    		</td>
								</tr>
								<tr>
									<th><span class="ui-color-red">*</span>通用查询日：</th>
									<td>
										<input class="easyui-validatebox easyui-datebox ipt"  name="currentDateStart" id="currentDateStart" 
											data-options="required:true,maxDate:'currentDateEnd',width:85"/>
							     		&nbsp;-&nbsp;
										<input class="easyui-validatebox easyui-datebox ipt" name="currentDateEnd" id="currentDateEnd" 
											data-options="required:true,minDate:'currentDateStart',width:85"/>
									</td>
									<th><span class="ui-color-red">*</span>查询日：</th>
									<td>
										<input class="easyui-validatebox easyui-datebox ipt" name="selectDateTime" id="selectDateTime" 
											data-options="required:true,minDate:'currentDateStart'"/>
									</td>
									<th>订货形式：</th>
									<td>
										<input class="easyui-orderFormBox" name='orderForm' style="width:140px;"/>
									</td>
									<th>显示小计：</th>
									<td>
										<input type="checkbox" style="width: 30px; height: 15px; padding: 0 5px 0 0;" id="countOnly"/>
									</td>
								</tr>
		           				</tbody>
		                 	</table> 	 
						</form>
			        </div>
			    </div>
				<div data-options="region:'center',border:false" ">		
					<div id="mainTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
    					<div data-options="title:'汇总核对'">
    						<@p.datagrid id="dtlDataGridTotal" loadUrl="" saveUrl=""  defaultColumn=""  
					      			 isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"  
					      			 pagination="true" rownumbers="true" singleSelect="false"  showFooter="true" pageSize="20"
						             columnsJsonList="[
						             	{title : '收货方',colspan:'4'},
					                  	{title : '发货方',colspan:'4'},
										{title : '本月发出',colspan:'2'},
										{title : '本月发本月收',colspan:'2'},
										{title : '本月发下月收',colspan:'2'},
										{title : '本月发未收',colspan:'2'},
										{title : '本月差异',colspan:'2'},
										{title : '前月发本月收',colspan:'2'},
										{title : '前月发未收',colspan:'2'},
										{title : '前月差异',colspan:'2'}
										],[
										
										{field : 'zoneName',title : '大区',width: 60},
										{field : 'buyerName',title : '结算公司',width: 200,align:'left',halign:'center'},
										{field : 'organName',title : '管理城市',width: 80},
										{field : 'orderUnitName',title : '货管单位',width: 80,align:'center',halign:'center'},
										
										{field : 'zoneNameFrom',title : '大区',width: 60},
										{field : 'salerName',title : '结算公司',width: 200,align:'left',halign:'center'},
										{field : 'organNameFrom',title : '管理城市',width: 80},
										{field : 'orderUnitNameFrom',title : '货管单位',width: 80,align:'center',halign:'center'},
										
										{field : 'currMonSenNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonSenRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'currMonRecNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonRecRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'nextMonRecNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'nextMonRecRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'currMonYetRecNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonYetRecRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'currMonDiffNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonDiffRMB',title : '金额',width: 80,align:'right','exportType':'number'},
										
										{field : 'lastMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
										{field : 'lastMonRecRMB',title : '金额',width: 80,align:'right','exportType':'number'},
										
										{field : 'lastMonYetRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
										{field : 'lastMonYetRecRMB',title : '金额',width: 80,align:'right','exportType':'number'},
										
										{field : 'lastMonDiffNum',title : '数量',width: 80,align:'right','exportType':'number'},
										{field : 'lastMonDiffRMB',title : '金额',width: 80,align:'right','exportType':'number'}
					                 ]"
						 			jsonExtend='{}'
							/>
    					</div>
    					<div data-options="title:'明细核对'">
    						<@p.datagrid id="dtlDataGrid" loadUrl="" saveUrl=""  defaultColumn=""  
					      			 isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"  pagination="true" 
					       			 rownumbers="true" singleSelect="false"  showFooter="true"  pageSize="20"
				            		 columnsJsonList="[
						            	{title : '收货方',colspan:'6'},
					                  	{title : '发货方',colspan:'4'},
										{title : '商品信息',colspan:'10'},
										{title : '',colspan:'1'},
										{title : '本月发出',colspan:'2'},
										{title : '本月发本月收',colspan:'2'},
										{title : '本月发下月收',colspan:'2'},
										{title : '本月发未收',colspan:'2'},
										{title : '本月差异',colspan:'2'},
										{title : '前月发本月收',colspan:'2'},
										{title : '前月发未收',colspan:'2'},
										{title : '前月差异',colspan:'2'}
										],[
										{field : 'balanceTypeName',title : '结算类型',width: 80},
									    {field : 'billNo',title : '单据编号',width: 150,align:'left',halign:'center'},
										{field : 'zoneName',title : '大区',width: 60},
										{field : 'buyerName',title : '结算公司',width: 200,align:'left',halign:'center'},
										{field : 'organName',title : '管理城市',width: 80},
										{field : 'orderUnitName',title : '货管单位',width: 80,align:'center',halign:'center'},
										
										{field : 'zoneNameFrom',title : '大区',width: 60},
										{field : 'salerName',title : '结算公司',width: 200,align:'left',halign:'center'},
										{field : 'organNameFrom',title : '管理城市',width: 80},
										{field : 'orderUnitNameFrom',title : '货管单位',width: 80,align:'center',halign:'center'},
										
										{field : 'itemCode',title : '商品编码',width: 150,align:'left',halign:'center'},
										{field : 'itemName',title : '商品名称',width: 180,align:'left',halign:'center'},
										{field : 'brandName',title : '品牌',width: 80,align:'center'},
										{field : 'oneLevelCategoryName',title : '大类',width: 80,align:'center'},
										{field : 'twoLevelCategoryName',title : '中类',width: 80,align:'center'},
										{field : 'categoryName',title : '小类',width: 80,align:'center'},
										{field : 'seasonName',title : '季节',width: 60,align:'center'},
										{field : 'yearsName',title : '年份',width: 60,align:'center','exportType':'number'},
										{field : 'orderfromName',title : '订货形式',width: 80,align:'center'},	
										{field : 'genderName',title : '性别',width: 60,align:'center'},
										
										{field : 'sendDate',title : '发送日',width: 80,align:'center'},
										
										{field : 'currMonSenNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonSenRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'currMonRecNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonRecRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'nextMonRecNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'nextMonRecRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'currMonYetRecNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonYetRecRMB',title : '金额',width: 70,align:'right','exportType':'number'},
										
										{field : 'currMonDiffNum',title : '数量',width: 70,align:'right','exportType':'number'},
										{field : 'currMonDiffRMB',title : '金额',width: 80,align:'right','exportType':'number'},
										
										{field : 'lastMonRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
										{field : 'lastMonRecRMB',title : '金额',width: 80,align:'right','exportType':'number'},
										
										{field : 'lastMonYetRecNum',title : '数量',width: 80,align:'right','exportType':'number'},
										{field : 'lastMonYetRecRMB',title : '金额',width: 80,align:'right','exportType':'number'},
										
										{field : 'lastMonDiffNum',title : '数量',width: 80,align:'right','exportType':'number'},
										{field : 'lastMonDiffRMB',title : '金额',width: 80,align:'right','exportType':'number'}
					                 ]"
				 			jsonExtend='{onLoadSuccess:function(rowData){
				 							transferingCheck.compute(rowData);
										}
							}'
				 		/>
    					</div>
					</div>
				</div>	
		 </div>
 	 </div>
</body>
</html>