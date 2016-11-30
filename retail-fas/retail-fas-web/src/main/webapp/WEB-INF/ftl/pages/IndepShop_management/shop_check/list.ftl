<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>店铺检查登记</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/shop_check/shop_check.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-empty","type":0},
	             {"id":"btn-save","title":"保存","iconCls":"icon-save","type":0},
	             {"id":"btn-import","title":"导入","iconCls":"icon-import","type":0},
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
								<td align="right" width="80">公司名称：</td>
								<td align="left">
									<input class="ipt easyui-company"  name="companyName" id="companyName" data-options="inputWidth:170,multiple:false,required:true"/>
									<input type="hidden"  name="companyNo" id="companyNo" 	/>	
								</td>
								<td align="right" width="110">店铺名称：</td>
								<td align="left" width="140">
									<input id="shopName" class="easyui-shopCommon" disabled="disabled"/>
									<input type="hidden" name="shopNo" id="shopNo"/>
								</td>
								<th align="right" width="80">管理城市： </th>
		                   		<td>
		                   			<input class="easyui-organ ipt"  name="organName" id="organName" data-options="inputWidth:170,multiple:true"/>
									<input type="hidden" name="organNo" id="organNo"/>
		                   		</td>
								<th>年：</th>
					       		<td>
					       		 	<input class="easyui-datebox ipt"  name="year" id="year" data-options="dateFmt:'yyyy',required:true" /> 
					       		</td>				
							 	<th>月：</th>
					       		<td>
					       		 	<input class="easyui-datebox ipt"  name="month" id="month" data-options="dateFmt:'MM',required:true" /> 
					       		</td>
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
			           rownumbers="false" singleSelect="false"  showFooter="true"
			           columnsJsonList="[
			           		{field : 't',checkbox:true,width : 30,notexport:true},
			                {field : 'id',hidden : 'true',align:'center',notexport:true},
			                {field : 'shopNo',title : '店铺编码',width : 80,align:'left'},
							{field : 'shopName',title : '店铺名称',width : 120,align:'left'},
							{field : 'year',title : '年',width : 80,align:'left',exportType:'number'},
							{field : 'month',title : '月',width : 80,align:'left',exportType:'number'},
							{field : 'createUser',title : '建档人',width : 80,align:'left'}, 
			                {field : 'createTime',title : '建档时间',width : 130,align:'center'},
			                {field : 'updateUser',title : '修改人',width : 80,align:'left'}, 
			                {field : 'updateTime',title : '修改时间',width : 130,align:'center'}
			           ]" jsonExtend='{
				          	onDblClickRow:function(rowIndex, rowData){
				          		shopCheck.edit(rowIndex, rowData);
		          			}
			          	}'
				/>
			</div>
    	</div>
    </div>
</body>