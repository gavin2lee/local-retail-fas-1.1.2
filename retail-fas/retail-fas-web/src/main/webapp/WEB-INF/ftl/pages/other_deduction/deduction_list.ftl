<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>地区间其他扣项</title>
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
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"otherDeduction.save()","type":7},                
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"otherDeduction.del()","type":3}, 
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/other_deduction/export','其他扣项导出')","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" id="balanceType" name="balanceType" value="${balanceType}">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
									<th>调出公司： </th>
									<td>
										<input class="ipt" multiSearch="company"/>
										<input type="hidden" name="multiSalerNo">
									</td>
									<th>调入公司： </th>
									<td>
										<input class="ipt" multiSearch="dataAccess_company"/>
										<input type="hidden" name="multiBuyerNo">
									</td>
									<th>日期：</th>
								    <td>
								    	<input class="easyui-datebox ipt"  name="deductionDateStart" id="deductionDateStart" 
								    	data-options="maxDate:'deductionDateEnd'" />
								    </td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input class="easyui-datebox ipt"  name="deductionDateEnd" id="deductionDateEnd" data-options="minDate:'deductionDateStart'" />
									</td>	
								</tr>
								<tr>
									<th>结算单号： </th>
									<td>
										<input class="ipt"  name="balanceNo" />
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
			<#--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""  pageSize="20" showFooter="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = "" rownumbers="true"
			           columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true,},
			                 {field : 'salerName', title : '调出公司', width : 200, 
			                 	editor:{
			                 			type:'test_combogrid',
			                 			options:{type:'company',required:true,datagridId:'dtlDataGrid',valueField:'salerNo'}
			                 	}, 
			                 align:'left',halign:'center'},
			                 {field : 'salerNo', title : '调出公司编号', hidden:'true',notexport:true, editor:{type:'readonlytext'}},
			                 {field : 'buyerName', title : '调入公司', width : 200, 
			                 	editor:{
			                 			type:'test_combogrid',
			                 			options:{type:'dataAccess_company',required:true,datagridId:'dtlDataGrid',valueField:'buyerNo'}
			                 	},
			                 align:'left',halign:'center'},
			                 {field : 'buyerNo', title : '调入公司编号', hidden:'true', notexport:true, editor:{type:'readonlytext'}},
			 				 {field : 'deductionDate',title:'日期',width:100,editor:{type:'datebox',options:{required:true}}},
			                 {field : 'brandName', title : '品牌', width : 100, 
			                 	editor:{type:'test_combogrid',
			                 			options:{type:'brand',required:true,datagridId:'dtlDataGrid',valueField:'brandNo'}}, align:'center'},
			                 {field : 'brandNo', title : '品牌编号', hidden:'true',notexport:true, editor:{type:'readonlytext'}},
			                 {field : 'categoryName', title : '一级大类', width : 80, 
			                 	editor:{type:'test_combogrid',
			                 			options:{type:'category',datagridId:'dtlDataGrid',valueField:'categoryNo'}}, align:'center'},
			                 {field : 'categoryNo', title : '大类编号', hidden:'true',notexport:true, editor:{type:'readonlytext'}},
			 				 {field : 'currencyNo',title:'币别',width:80,
			 				 	editor:{type:'combobox',
			 				 			options:{editable:false, required:true, valueField:'code',textField:'name',data:common_util.currencyJSONData}}
			 				 	,formatter:common_util.currencyFormat},
			 				 {field : 'deductionQty',title:'数量',width:80,align:'right',halign:'center', 
			 				 	editor:{
			 				 			type:'numberbox',
					 				 	options:{
							 				 	precision:0,
							 				 	min:0,
							 				 	validType:'maxLength[9]'
							 			}
			 					   }
			 				 },
							 {field : 'fineAmount',title:'扣项金额',width:125,align:'right',halign:'center', 
								 editor:{
								 		type:'numberbox',
										 options:{
											 precision:0,
											 validType:'maxLength[12]'
									     }
								   }
							 },
			 				 {field : 'deductionAmount',hidden:true,notexport:true,width:100},
			 				 {field : 'remark',title:'备注',width:150,align:'left',halign:'center',editor:'validatebox'},
			 				 {field : 'createUser',title:'创建人',width:100},
			 				 {field : 'createTime',title:'创建时间',width:150},
			 				 {field : 'balanceNo',title:'结算单号',width:180,align:'left',halign:'center', formatter: function(value,row,index){
								return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AS-结算单');
							}}
			 				]"	
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                   	   otherDeduction.edit(rowIndex, rowData);
			              }}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>