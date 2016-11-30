<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>付款条款</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pay_term/payTerm.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"payTerm.searchData()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"payTerm.searchClear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"payTerm.add()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"payTerm.del()", "type":3},
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"payTerm.save()", "type":7},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"pe_util.doExport('dtlDataGrid', '/pay_term/export_data', '付款条款')","type":4}
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
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>条款编码： </th><td><input class="ipt"  name="termNo"  /></td>	
									<th>条款类型： </th><td><input class="ipt" combobox="payTermType" name="termType"  /></td>	
									<th>创建时间：</th>
						    		<td ><input class="easyui-datebox ipt" name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd"  data-options="minDate:'createTimeStart'"/></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""   rownumbers="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = ""  pageSize="20" 
			           columnsJsonList="[
			           		  {field : 't', checkbox:true, width : 30, notexport:true,},
			                  {field : 'termNo', title : '条款编码', width : 100},
			                  {field : 'termName', title : '条款名称', width : 150},
			                  {field : 'termType', title : '条款类型', width : 150},
			                  {field : 'fixedDay', title : '固定日期', width : 120},
			                  {field : 'days', title : '天数', width : 120},
			                  {field : 'createUser', title : '创建人', width : 80},
			                  {field : 'createTime', title : '创建时间', width : 150},
			                  {field : 'updateUser', title : '修改人', width : 80},
			                  {field : 'updateTime', title : '修改时间', width : 150}]" 
			                  jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   	   payTerm.edit(rowIndex, rowData);
			              	}}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>