<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
    <title>货管单位</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/common/other-lib/common.js"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/order_unit/orderUnit.js"></script>   
</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false" class="toolbar-region">
    <@p.toolbar id="toolbar" listData=[
            {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"orderUnit.searchData();","type":0},
			{"id":"btn-clear","title":"清空","iconCls":"icon-empty","action":"orderUnit.searchClear()","type":0},
		 	{"id":"btn-export","title":"导出","iconCls":"icon-export","action":"orderUnit.exportExcel()","type":4}
	 		]
		/>
</div>
<#-- 条件查询区域div -->	
<div  data-options="region:'center',border:false">
 <div class="easyui-layout" data-options="fit:true" id="subLayout">
 <div data-options="region:'north',border:false" >
	   <div class="search-div">
	       <form name="searchForm" id="searchForm" method="post">
	       	 <table class="search-tb" >
		        <col width="100" />
		        <col/>
		        <col width="100" />
		        <col/>
		        <col width="100" />
		        <col/>
		        <col width="100" />
		        <col/>
	        	<tbody>
	        	<tr>
		        	<th>公司名称：</th>
		       		<td>
		       			<input class="easyui-company ipt" name="companyName" id="companyName"
						data-options="inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160,isDefaultData:false"/>
						<input type="hidden" name="companyNo" id="companyNo"/>
		       		</td>
		       		<th>货管单位编号：</th>
		       		<td>
		       			<input class="easyui-validatebox ipt" name="orderUnitNo" id="orderUnitNoCondition" />
		       		</td>
					<th>货管单位编码：</th>
					<td>
						<input class="easyui-validatebox ipt"  name="orderUnitCode" id="orderUnitCodeCondition" />
					</td>
					<th>货管单位名称：</th>
					<td>
						<input class="easyui-validatebox ipt"  name="name" id="nameCondition" />
					</td>
			   </tr>
			   <tr>
				   	 <th>管理城市：</th>
	                 <td>
						<input class="easyui-organ ipt"   name="organName" id="organNameId" 
							data-options="inputNoField:'organNoId',inputNameField:'organNameId',inputWidth:160"/>
						<input type="hidden" name="organNo" id="organNoId" />				
					 </td>
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

	<div data-options="region:'center',border:false">
		    <@p.datagrid id="dataGridLU"  loadUrl="" saveUrl=""  defaultColumn=""
		       isHasToolBar="false"  height="300"  onClickRowEdit="false"  singleSelect="true"   pagination="true"
		       rownumbers="true" 
		       columnsJsonList="[
			        {field : 'orderUnitNo',title : '货管单位编号',width : 100,align:'center',halign:'center' },
			        {field : 'orderUnitCode',title : '货管单位编码',width : 100,align:'center',halign:'center'},
			        {field : 'name',title : '货管单位名称',width : 150 ,align:'left',halign:'center'},
			        {field : 'organName',title : '管理城市',width : 80,align:'center',halign:'center'},
			        {field : 'companyNo',title : '公司编码',width : 80,align:'left',halign:'center' },
			       	{field : 'companyName',title : '公司名称',width : 250,align:'left',halign:'center' },
					{field : 'remark',title : '备注',width : 200,align:'left',halign:'center'}
		       ]" 
		     />
	</div>  
</div>
</div> 
</body>
</html>