<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>x现金余额表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/cash_balance/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/cash_balance/do_exports"
					  export_title="现金余额表导出"
					  primary_key="id"/>

	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
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
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
		                 <tbody>
							<tr>
								<td align="right" width="50"><span class="ui-color-red">*</span>公司名称：</td>
								<td align="left">
									<input class="ipt easyui-company"  name="companyName" id="companyName" data-options="inputWidth:180,required:true"/>
									<input type="hidden"  name="companyNo" id="companyNo" 	/>	
								</td>
						     	<td align="right" width="80">店铺名称：</td>
								<td >
									<input id="shopName" style="width: 180px" class="easyui-shopCommon" disabled="disabled" data-options=""/>
									<input type="hidden" name="shopNo" id="shopNo"/>
								</td>
						     	<td align="right" width="80"><span class="ui-color-red">*</span>日期：</td>
						     	<td >
						     		<input class="easyui-datebox"  name="startOutDate" id="startOutDate" data-options="maxDate:'endOutDate',required:true"/>-
						     	</td>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="endOutDate" id="endOutDate" data-options="minDate:'startOutDate',required:true"/></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
	    	
	    	<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  showFooter="true"
			           columnsJsonList="[
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shopNo',title : '店铺编码',width : 100,align:'left'},
								{field : 'shopName',title : '店铺名称',width : 150,align:'left'},
				                {field : 'startAmount',title : '期初金额',width : 100,align:'left'},
				                {field : 'currentAmount',title : '本期金额',width : 100,align:'left',exportType:'text'},
				                {field : 'currentDepositAmount',title : '本期存入',width : 100,align:'left',exportType:'text'},
				                {field : 'endAmount',title : '期末余额',width : 100,align:'left'},
				                {field : 'prepareCash',title : '备用金',width : 100,align:'left'}	
			              ]" 
				          jsonExtend='{
				          	onDblClickRow:function(rowIndex,rowData){
				          		
				          	}
				          }'
                 />
			</div>
	    	
    	</div>
    </div>	
</body>