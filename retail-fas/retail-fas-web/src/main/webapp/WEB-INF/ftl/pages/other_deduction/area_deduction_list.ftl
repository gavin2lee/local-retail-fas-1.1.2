<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>其他扣项</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/other_deduction/otherDeduction.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"otherDeduction.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"otherDeduction.clear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"otherDeduction.add()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"otherDeduction.del()","type":3}, 
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"otherDeduction.save()","type":7},  
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/other_deduction/export','其他扣项导出')","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" id="balanceType" name="balanceType" value="${balanceType}">
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
									<td><input class="ipt" multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiSalerNo"></td>
									<th>客户： </th>
									<td><input class="ipt" multiSearch="dataAccess_company"  /><input type="hidden" name="multiBuyerNo"></td>
									<th>日期：</th>
								    <td><input class="easyui-datebox ipt"  name="deductionDateStart" id="deductionDateStart" data-options="maxDate:'deductionDateEnd'" /></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt"  name="deductionDateEnd" id="deductionDateEnd" data-options="minDate:'deductionDateStart'" /></td>	
								</tr>
								<tr>
									<th>结算单号： </th><td><input class="ipt"  name="balanceNoLike" /></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""  pageSize="20" showFooter="true" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = "" rownumbers="true" 
			           columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true,},
			                 {field : 'salerName', title : '公司', width : 300, editor:{type:'test_combogrid',options:{type:'company',notGroupLeadRole:true,required:true,datagridId:'dtlDataGrid',valueField:'salerNo'}}, align:'left'},
			                 {field : 'salerNo', title : '客户编号', hidden:'true', editor:{type:'readonlytext'}},
			                 {field : 'buyerName', title : '公司', width : 300, editor:{type:'test_combogrid',options:{type:'company',required:true,datagridId:'dtlDataGrid',valueField:'buyerNo'}}, align:'left'},
			                 {field : 'buyerNo', title : '公司编号', hidden:'true',  editor:{type:'readonlytext'}},
			 				 {field : 'deductionDate',title:'日期',width:100,editor:{type:'datebox',options:{required:true}}},
			                 {field : 'brandName', title : '品牌', width : 120, editor:{type:'test_combogrid',options:{type:'brand',required:true,datagridId:'dtlDataGrid',valueField:'brandNo'}}, align:'left'},
			                 {field : 'brandNo', title : '品牌编号', hidden:'true', editor:{type:'readonlytext'}},
			                 {field : 'categoryName', title : '一级大类', width : 120, editor:{type:'test_combogrid',options:{type:'category',required:true,datagridId:'dtlDataGrid',valueField:'categoryNo'}}, align:'left'},
			                 {field : 'categoryNo', title : '大类编号', hidden:'true', editor:{type:'readonlytext'}},
			 				 {field : 'currencyNo',title:'币别',width:100,editor:{type:'combobox',options:{editable:false, required:true, valueField:'code',textField:'name',data:common_util.currencyJSONData}}
			 				 	,formatter:common_util.currencyFormat},
							 {field : 'fineAmount',title:'其他扣项',width:100,align:'right',halign:'center', editor:{type:'numberbox',options:{precision:2,validType:'maxLength[12]'}}},
			 				 {field : 'remark',title:'备注',width:200,align:'left',halign:'center',editor:'validatebox'},
			 				 {field : 'createUser',title:'创建人',width:100},
			 				 {field : 'createTime',title:'创建时间',width:150},
			 				 {field : 'balanceNo',title:'结算单号',width:120, formatter: function(value,row,index){
								return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HS-结算单');
							}}]"	
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   	   otherDeduction.edit(rowIndex, rowData);
			              }}'
						/>
			</div>
	 	</div>
	</div>
</body>
</html>