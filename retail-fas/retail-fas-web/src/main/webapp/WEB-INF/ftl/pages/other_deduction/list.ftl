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
             {"id":"btn-export","title":"导入","iconCls":"icon-import","action":"common_util.doImport('其他扣项导入(鞋).xlsx','/other_deduction/doShoesImport',1,importCallBack)","type":0},
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
									<td><input class="ipt" multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
									<th>日期：</th>
								    <td><input class="easyui-datebox ipt" defaultValue="startDate" name="deductionDateStart" id="deductionDateStart" data-options="maxDate:'deductionDateEnd'" /></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt" defaultValue="endDate" name="deductionDateEnd" id="deductionDateEnd" data-options="minDate:'deductionDateStart'" /></td>	
								</tr>
								<tr>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"  /><input type="hidden" name="multiBrandNo"></td>
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
		              checkOnSelect="true"  selectOnCheck="true" singleSelect="false" 
			           columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true,},
			                 {field : 'buyerName', title : '公司', width : 220, editor:{type:'test_combogrid',options:{type:'company',notGroupLeadRole:true,required:true,datagridId:'dtlDataGrid',valueField:'buyerNo'}}, align:'left'},
			                 {field : 'buyerNo', title : '公司编号', hidden:'true',  editor:{type:'readonlytext'}},
			                 {field : 'salerName', title : '供应商', width : 220, editor:{type:'test_combogrid',options:{type:'supplier',required:true,datagridId:'dtlDataGrid',valueField:'salerNo'}}, align:'left'},
			                 {field : 'salerNo', title : '供应商编号', hidden:'true', editor:{type:'readonlytext'}},
			 				 {field : 'deductionDate',title:'日期',width:100,editor:{type:'datebox',options:{required:true}}},
			                 {field : 'brandName', title : '品牌', width : 120, editor:{type:'test_combogrid',options:{type:'brand',required:true,datagridId:'dtlDataGrid',valueField:'brandNo'}}, align:'left'},
			                 {field : 'brandNo', title : '品牌编号', hidden:'true', editor:{type:'readonlytext'}},
			                 {field : 'categoryName', title : '一级大类', width : 120, editor:{type:'test_combogrid',options:{type:'category',required:true,datagridId:'dtlDataGrid',valueField:'categoryNo'}}, align:'left'},
			                 {field : 'deductionCategory',title:'性别',width:100,editor:{type:'combobox',options:{editable:false, required:true,valueField:'code',textField:'name',data:common_util.genderJSONData}}
			 				 	,formatter:common_util.genderFormat},
			                 {field : 'categoryNo', title : '大类编号', hidden:'true', editor:{type:'readonlytext'}},
							 {field : 'fineAmount',title:'其他扣项',width:100,align:'right',halign:'center', editor:{type:'numberbox',options:{precision:2,required:true,validType:'maxLength[10]'}}},
			                 {field : 'targetCurrencyNo',hidden:'true',title:'本位币编码',width:100},
			                 {field : 'targetCurrencyName',hidden:'true',title:'本位币',width:100},
			                 {field : 'conversionFactor',hidden:true,title:'汇率',width:100},
			                 {field : 'targetCurrencyAmount',hidden:true,title:'本位币金额',width:100},
			 				 {field : 'deductionAmount',hidden:'true',title:'合计金额',width:100},
			 				 {field : 'remark',title:'备注',width:200,align:'left',halign:'center',editor:{type:'validatebox',options:{validType:'maxLength[200]'}}},
			 				 {field : 'createUser',title:'创建人',width:100},
			 				 {field : 'createTime',title:'创建时间',width:150},
			 				 {field : 'balanceNo',title:'结算单号',width:120, formatter: function(value,row,index){
								return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HS-结算单');
							}},{field : 'balanceStatus',title:'结算单状态',width:120,formatter:common_util.balanceStatusFormat}]"	
				          jsonExtend='{
                       			onDblClickRow:function(rowIndex, rowData){
			                	  	//双击方法
			                   	   	otherDeduction.edit(rowIndex, rowData);
	              				},
			              		onLoadSuccess:function(){
			             			otherDeduction.operate();
								}
			              }'
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
                     {field : 'validateObj.salerNo', title : '公司编码', width : 100},
                     {field : 'validateObj.buyerNo', title : '供应商编码', width : 100},
                     {field : 'validateObj.deductionDate', title : '日期', width : 150},
                     {field : 'validateObj.brandNo',title:'品牌编码',width:100},	
                     {field : 'validateObj.categoryNo',title:'大类编码',width:100},	
                     {field : 'validateObj.deductionAmount', title : '其他扣项', width : 150},
                     {field : 'validateObj.deductionCategory', title : '性别', width : 150},
                     {field : 'validateObj.remark',title:'备注',width:100}]"	
         />
     </div>	
</body>
</html>