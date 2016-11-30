<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算日设置-应收</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/balance_date/balanceDate.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"balanceDate.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"balanceDate.clear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"balanceDate.add()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"balanceDate.del()","type":3}, 
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"balanceDate.save()","type":7}              
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" id="balanceType" name="balanceType" value="2">
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
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiCompanyNo"></td>
									<th>客户： </th>
									<td><input class="ipt" multiSearch="dataAccess_company"  /><input type="hidden" name="multiCustomerNo"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" rownumbers="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  pageSize="20" 
			          emptyMsg = ""
			           columnsJsonList="[
			                  {field : 't', checkbox:true, width : 30, notexport:true,},
			                  {field : 'companyName', title : '公司', width : 300, editor:{type:'test_combogrid',options:{type:'company',notGroupLeadRole:true,required:true,datagridId:'dtlDataGrid',valueField:'companyNo'}}, align:'left'},
			                  {field : 'companyNo', title : '公司编号', hidden:'true',  editor:{type:'readonlytext'}},
			                  {field : 'customerName', title : '客户', width : 300, editor:{type:'test_combogrid',options:{type:'dataAccess_company',datagridId:'dtlDataGrid',valueField:'customerNo'}}, align:'left'},
			                  {field : 'customerNo', title : '客户编号', hidden:'true', editor:{type:'readonlytext'}},
			                  {field : 'balanceDate', title : '结算日', width : 100, editor:{type:'numberbox',options:{required:true,min:1,max:28,validType:'maxLength[2]'}}}]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex){
			                	  //双击方法
			                   	   balanceDate.edit(rowIndex);
			              }}'
                 />
			</div>
			<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
				     <form name="dataForm" id="dataForm" method="post"  >
				     	<div style="padding:12px">
							 <table class="form-tb">
	                    	    <col width="80"/>
	                    	 	<col />
	                            <tbody>
								   <tr>
								        <th>公司：</th>
		                                <td><input class="ipt" notGroupLeadRole data-options="required:true" multiSearch="company"  name="companyNo"/><input type="hidden" name="companyName" /></td>
								   </tr>
								   <tr>
								        <th>客户：</th>
		                                <td><input class="ipt" data-options="required:true" multiSearch="company"  name="customerNo"/><input type="hidden" name="customerName" /></td>
								   </tr>
		                           <tr>
									   	<th>结算日：</th>
									   <td><input  class="ipt" data-options="required:true,min:1,max:28"  name="balanceDate"/></td>
								   </tr>
								 </tbody>
							 </table>
						</div>
					 </form>	
			   </div>
	 	</div>
	</div>
</body>
</html>