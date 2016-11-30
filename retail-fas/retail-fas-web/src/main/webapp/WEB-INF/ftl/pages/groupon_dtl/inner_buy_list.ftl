<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>地区团购明细</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/groupon/inner_buy_list.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
</head>
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<input type="hidden" name="detailType" value="${detailType}" id="detailType" />
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"innerBuyDetail.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"innerBuyDetail.clear()", "type":0},
             {"id":"btn-confirm","title":"财务确认","iconCls":"icon-ok", "action" : "innerBuyDetail.financeConfirm()", "type":79},
             {"id":"btn-anti-confirm","title":"财务反确认","iconCls":"icon-remove", "action" : "innerBuyDetail.financeAntiConfirm()", "type":90},
             {"id":"btn-remove","title":"导出","iconCls":"icon-aduit","action":"innerBuyDetail.exportExcel()", "type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="billFlag" value="0">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />			
							<col width="80" />
							<col />						
							<tbody>
								<tr>
									<th><span class="ui-color-red">*</span>结算公司： </th>
									<td>
										<input class="easyui-company  ipt"  name="companyName" id="companyNameId" 
										 data-options="queryUrl: BasePath + '/base_setting/company/list.json',required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:130"/>
										<input type="hidden" name="companyNo" id="companyNoId"/>
									</td>
									<th>管理城市： </th><td><input class="easyui-organ ipt"  name="organName" id="organNameId" 
										 data-options="queryUrl: BasePath + '/organ/list.json?status=1&organLevel=1',inputNoField:'organNoId',inputNameField:'organNameId',inputWidth:120,multiple:true"/>
										<input type="hidden" name="organNo" id="organNoId"/></td>
									<th>品牌： </th>
									<td>
										<input class="easyui-brand  ipt"  name="brandName" id="brandNameId" 
										 data-options="queryUrl: BasePath + '/brand/list.json?status=1',inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:130,multiple:true"/>
										<input type="hidden" name="brandNo" id="brandNoId"/>
									</td>
									<th>大类： </th>
									<td><input class="easyui-categorybox ipt" name="categoryNo" id="categoryNoId" data-options="inputWidth:140,multiple:true"/>
									</td>
								</tr>
								<tr>
									<th>单据编号： </th>
									<td><input class="ipt" name="orderNo" id="orderNo" />
									</td>
									<th>确认状态： </th>
									<td><input class="ipt" name="confirmStatus" id="confirmStatus"/>
									</td>
									
									<th><span class="ui-color-red">*</span>销售日期： </th><td><input class="easyui-datebox ipt"  name="startDate" id="startDate" data-options="required:true,maxDate:'endDate'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </th><td><input class="easyui-datebox ipt"  name="endDate" id="endDate" data-options="required:true,minDate:'startDate'"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<div data-options="region:'center',border:false">		
				<div id="mainTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
					<div title="GMS内购明细">
			<!--列表-->
			<div class="easyui-layout" data-options="fit:true,plain:true">
        	<div data-options="region:'center',border:false">
		      <@p.subdatagrid id="gmsDtlDataGrid" loadUrl="" saveUrl=""   defaultColumn=""   title=""
			           isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
					   rownumbers="true" singleSelect="true"  showFooter="true"
			           columnsJsonList="[
		                	  {field:'ck',checkbox:true,notexport:true},
		                	  {field : 'companyName', title : '公司', width : 180, align : 'left',halign:'center'},
		                	  {field : 'organName', title : '管理城市', width : 80, align : 'center'},
		                	  {field : 'shopNo', title : '货管单位', width : 80, align : 'left',halign:'center'},
		                	  {field : 'shopName', title : '发货方', width : 80, align : 'left',halign:'center'},
		                	  {field : 'financeConfirmFlagStr',title : '财务确认',width : 60, align : 'center'},
		               		  {field : 'bizTypeName',title : '业务类型',width : 90, align : 'center'},
		               		  {field : 'outDate',title : '业务日期',width : 90, align : 'center'},
		               		  {field : 'orderNo', title : '单据编码', width : 150, align : 'left',halign:'center'},
			                  {field : 'itemCode',title : '商品编码',width : 150, align : 'left',halign:'center'},
		  			  		  {field : 'itemName',title : '商品名称',width : 150, align : 'left',halign:'center'},
		  			  		  {field : 'brandName',title : '品牌',width : 80, align : 'center'},
		  			  		  {field : 'brandUnitName',title : '品牌部',width : 80, align : 'center'},
				  			  {field : 'categoryName',title : '大类',width : 80, align : 'center'},
				  			  {field : 'qty',title : '数量',width : 50, align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'tagPrice',title : '牌价额',width : 80, align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'salePrice',title : '地区价',width : 80, align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'settlePrice',title : '结算价',width : 80, align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'amount', title : '结算金额', width : 80,align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'unitCost',title : '单位成本',width : 80, align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'regionCost',title : '地区成本',width : 80, align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'headquarterCost',title : '总部成本',width : 80, align : 'right',exportType:'number',halign:'center'},
				  			  {field : 'invoiceNo',title : '开票申请号',width : 150, align : 'center',halign:'center'},
				  			  {field : 'invoiceDate',title : '开票日期',width : 140, align : 'center'},
				  			  {field : 'createUser',title : '制单人',width : 90, align : 'center'},
				  			  {field : 'createTime',title : '制单时间',width : 140, align : 'center'},
				  			  {field : 'updateUser',title : '财务确认人',width : 90, align : 'center'},
				  			  {field : 'updateTime',title : '确认时间',width : 140, align : 'center'}
			            ]" 
                 />
			</div>
			</div>
			</div>
			</div>		
   			</div>
	 	</div>
	</div>
</body>
</html>