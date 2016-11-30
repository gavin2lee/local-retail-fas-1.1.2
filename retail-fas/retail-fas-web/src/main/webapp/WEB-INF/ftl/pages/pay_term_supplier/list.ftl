<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>供应商付款条款</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pay_term_supplier/payTermSupplier.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"payTermSupplier.searchData()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"payTermSupplier.searchClear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"payTermSupplier.add()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"payTermSupplier.del()", "type":3},
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"payTermSupplier.save()", "type":7},
             {"id":"btn-import","title":"导入","iconCls":"icon-export","action":"pe_util.doImport('供应商付款条款.xlsx','/pay_term_supplier/do_import',1,pe_util.importCallBack)","type":6},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"pe_util.doExport('dtlDataGrid', '/pay_term_supplier/export_data', '供应商付款条款')","type":4}
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
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiCompanyNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"  /><input type="hidden" name="multiSupplierNo"></td>
									<th>条款编码： </th><td><input class="ipt"  name="termNo"  /></td>	
									<th>条款类型： </th><td><input class="ipt" combobox="payTermType" name="termType"  /></td>	
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
			           		  {field : 'companyName', title : '公司', width : 220},
			                  {field : 'companyNo', title : '公司编号', hidden:'true'},
			                  {field : 'supplierName', title : '供应商', width : 220},
			                  {field : 'supplierNo', title : '供应商编号', hidden:'true'},
			                  {field : 'termNo', title : '条款编码', width : 120},
			                  {field : 'termName', title : '条款名称', width : 120},
			                  {field : 'termType', title : '条款类型', hidden:'true'},
			                  {field : 'termTypeName', title : '条款类型', width : 120},
			                  {field : 'fixedDay', title : '固定日期', width : 80},
			                  {field : 'days', title : '天数', width : 80},
			                  {field : 'isPrepay', title : '是否预付', width : 80},
			                  {field : 'forwardProportion', title : '期货预付比例', width : 120},
			                  {field : 'spotProportion', title : '现货预付比例', width : 120},
			                  {field : 'createUser', title : '创建人', width : 80},
			                  {field : 'createTime', title : '创建时间', width : 150},
			                  {field : 'updateUser', title : '修改人', width : 80},
			                  {field : 'updateTime', title : '修改时间', width : 150}]" 
			                  jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   	   payTermSupplier.edit(rowIndex, rowData);
			              	}}'
                 />
			</div>
	 	</div>
	</div>
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="importDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'index', title : '行号', width : 30},
	           		 {field : 'pass',title:'是否导入成功',width:100,formatter: function(value,row,index){
							if (value == 0){
								return '否';
							}
							return '是'
						}
			         },
			         {field : 'errorInfo',title:'错误信息',width:200},
                     {field : 'validateObj.companyNo', title : '公司编码', width : 100},
                     {field : 'validateObj.supplierNo', title : '供应商编码', width : 100},
                     {field : 'validateObj.termNo', title : '条款编码', width : 150},	
	 				 {field : 'validateObj.isPrepay',title:'是否预付',width:100},
	 				 {field : 'validateObj.forwardProportion',title:'期货预付比例',width:100,align:'right'},
	 				 {field : 'validateObj.spotProportion',title:'现货预付比例',width:100,align:'right'}]"	
         />
     </div>		
</body>
</html>