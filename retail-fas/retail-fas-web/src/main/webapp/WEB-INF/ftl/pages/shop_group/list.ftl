 <!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>店铺分组</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/shop_group/shopGroup.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"shopGroup.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"shopGroup.clear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"shopGroup.showDialog()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"shopGroup.del()","type":3},
             {"id":"btn-start","title":"启用","iconCls":"icon-aduit","action":"shopGroup.operate(1)", "type":27},
             {"id":"btn-stop","title":"停用","iconCls":"icon-aduit","action":"shopGroup.operate(0)", "type":100},
             {"id":"btn-updateDate","title":"修改生效日期","iconCls":"icon-aduit","action":"shopGroup.updateDate()", "type":0},
           	 {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"shopGroup.exportExcel()","type":4},
           	 {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"shopGroup.importExcel()","type":6}
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
							<col width="120" />
							<col />
							<col width="120" />
							<col />
							<col width="120" />
							<col />
							<col width="120" />
							<col />
							<tbody>
								<tr>
									<th><span class="ui-color-red">*</span>公司：</th>
									<td>
								 		<input class="ipt easyui-company" name="companyName" id="companyNameCondition" 
								 		data-options="inputWidth:160,required:true,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
				      					<input type="hidden" name="companyNo" id="companyNoCondition"/>
			      					</td>
									<th>店铺分组编号： </th><td><input class="easyui-validatebox  ipt" name="shopGroupNo" id="shopGroupNo"/></td>
									<th>店铺分组名称： </th><td><input class="easyui-validatebox  ipt" name="shopGroupName" id="shopGroupName"/></td>
									<th>状态： </th><td><input class="easyui-combobox  ipt" name="status" id="status" data-options="valueField: 'value',textField: 'label',data: [{label: '启用',value: 1},{label: '停用',value: 0}]"/></td>
								</tr>
								<tr>
									<th>店铺： </th>
									<td>
										<!--<input class="easyui-validatebox ipt easyui-shop" name="shopName" id="shopName"
											data-options="required:false,multiple:true,inputWidth:160"  />-->
										<input id="shopName"/>
										<input type="hidden" name="shopNo" id="shopNo"/>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.subdatagrid id="mainDataGrid" loadUrl="" saveUrl="" defaultColumn="" pageSize="20" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" emptyMsg = "" 
		              checkOnSelect="true" selectOnCheck="true" singleSelect="false"
			           columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true},
			                 {field : 'shopGroupNo', title : '店铺分组编号', width : 100,align:'left'},
		                     {field : 'shopGroupName', title : '店铺分组名称', width : 150,align:'left'},
		                     {field : 'companyName', title : '公司名称', width : 200, align:'left'},
		                     {field : 'clientName', title : '客户名称', width : 150, align:'left'},
		                     {field : 'invoiceName', title : '开票名称', width : 150, align:'left'},
		                     {field : 'templateName', title : '发票模板名称', width : 150, align:'left'},
			 				 {field : 'valueDate',title:'生效日期',width:80},
			 				 {field : 'status',title:'状态',width:80,formatter:function(value,row,index){
			 				 		if(value == 1){
			 				 			return '启用';
			 				 		}
			 				 		return '停用';
			 				 }}]"	
			 			jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                   	   shopGroup.showDialog(rowData);
			            }}'
			 			loadSubGridUrl="/shop_group_dtl/list.json"
			            subPagination="false"
			            subGridColumnsJsonList="[
							{field:'shopNo',title:'店铺编号',width:120},
							{field:'shopName',title:'店铺名称',width:300}]"

                 />
			</div>
	 	</div>
	</div>
	
	<div id="genarateFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="genarateDataForm" id="genarateDataForm" method="post" class="pd10">
	     	<div id="dtl_detail">
				<table cellpadding="1" cellspacing="1" class="form-tb">
			       <tr height="40">
			         <th><span class="ui-color-red">*</span>生效日期：</th>
			         <td>
						<td><input class="easyui-datebox easyui-validatebox ipt" name="valueDate_" id="valueDate_" data-options="required:true" /></td>
					 <td>
				</table>
			</div>
		 </form>	
   </div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="dataForm" id="dataForm" method="post"  >
				     	 <input type="hidden" name="id" id="id"/>
						 <table class="form-tb">
		            	    <col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		                    <tbody>
							   <tr>
							   		<th>店铺分组编号：</th>
		                            <td><input class="ipt readonly" readonly="readonly"	name="shopGroupNo" id="shopGroupNo" /></td>
							        <th>店铺分组名称：</th>
		                            <td><input class="easyui-validatebox ipt"  name="shopGroupName" id="shopGroupName" data-options="required:true,validType:['unNormalData',length[0,200]]" /></td>
							   </tr>
							   <tr>
							   	 <th>公司名称：</th>
							   	   <td>
								 		<input class="easyui-company ipt" name="companyName" id="companyName_" 
								 			data-options="required:true,inputWidth:130,inputNoField:'companyNo_',inputNameField:'companyName_',
								 			callback:shopGroup.callbackCompany"/>
				      					<input type="hidden" name="companyNo" id="companyNo_"/>
			      					</td>
			      				  <th>客户：</th>
								   <td>
								   	<input class="easyui-validatebox ipt" name="clientName" id="clientName" data-options="required:true"/>
								   	<input type="hidden" name="clientNo" id="clientNo" />
								   </td>
							   </tr>
							  <tr>
							   		<th>开票名称：</th>
								   <td><input  class="ipt readonly" name="invoiceName" id="invoiceName" readonly="readonly" /></td>
								   <th>发票模板名称：</th>
								   <td>
								   	<input class="ipt" name="templateName" id="templateName" />
								   	<input type="hidden" name="templateNo" id="templateNo" />
								   </td>
							   </tr>
							   <tr>
								   <th>生效日：</th>
								   <td><input class="easyui-datebox easyui-validatebox ipt" name="valueDate" id="valueDate" data-options="required:true" /></td>
		                           <th>备注：</th>
								   <td><input class="easyui-validatebox  ipt" name="remark" data-options="validType:['unNormalData',length[0,200]]"/></td>
							   </tr>
							 </tbody>
						 </table>
				 	</form>	
			 	</div>
			 </div>
			 <div data-options="region:'center',border:false" >
			 	<div class="easyui-layout" data-options="fit:true">
			 		<div data-options="region:'north',border:false" >
			 			<@p.toolbar id="dtl_toorbar" listData=[
				             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"shopGroup.selectShop.clickFn()", "type":0},
				             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"shopGroup.delDtl()","type":0}
				           ]
						/>
			 		</div>
			 		<div data-options="region:'center',border:false" >
			 			<@p.datagrid id="dtlDataGrid" emptyMsg = ""
							isHasToolBar="false"  onClickRowEdit="false"  pageSize="20" 
							columnsJsonList="[
							 	{field : 't', checkbox:true, width : 30, notexport:true},
								{field:'shopNo',title:'店铺编码',width:120, editor:{type:'textbutton',options:shopGroup.selectShop}},
								{field:'shopName',title:'店铺名称',width:300, editor:{type:'readonlytext'}}]"
						  		 jsonExtend='{onDblClickRow:function(rowIndex, rowData){
															editor.editRow(rowIndex, rowData);
											             }}' 
						  />
			 		</div>
			 	</div>
			 </div>
		</div>
   </div>
      <#include  "/WEB-INF/ftl/pages/shop_group/list_view.ftl" >
</body>
</html>