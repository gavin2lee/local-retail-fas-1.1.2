<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算日设置-应收</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/initial_amount/initialAmount.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"initialAmount.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"initialAmount.clear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"initialAmount.add()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"initialAmount.del()","type":3}, 
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"initialAmount.save()","type":7},                
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/initial_amount/export','初始应收余额信息导出')","type":4}
           	 {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"common_util.doImport('初始应收余额.xlsx','/initial_amount/do_import',2,initialAmount.importCallBack)","type":6}
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
									<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiCustomerNo"></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""  showFooter="true" defaultColumn=""  pageSize="20" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = "" rownumbers="true"
			           columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true,},
			                 {field : 'companyName', title : '公司', width : 300, editor:{type:'test_combogrid',options:{type:'company',notGroupLeadRole:true,required:true,datagridId:'dtlDataGrid',valueField:'companyNo'}}, align:'left'},
			                 {field : 'companyNo', title : '公司编号', hidden:'true',  editor:{type:'readonlytext'}},
			                 {field : 'customerName', title : '客户', width : 300, editor:{type:'test_combogrid',options:{type:'company',required:true,datagridId:'dtlDataGrid',valueField:'customerNo'}}, align:'left'},
			                 {field : 'customerNo', title : '客户编号', hidden:'true', editor:{type:'readonlytext'}},
			                 {field : 'itemName', title : '商品', width : 300, editor:{type:'test_combogrid',options:{type:'item',datagridId:'dtlDataGrid',valueField:'itemCode'}}, align:'left'},
			                 {field : 'itemCode', title : '商品编码', hidden:'true', editor:{type:'readonlytext'}},
			 				 {field:'initialDate',title:'日期',width:100,editor:{type:'datebox',options:{required:true}}},
			 				 {field:'currency',title:'币别',width:100,editor:{type:'combobox',options:{required:true, editable:false, valueField:'code',textField:'name',data:common_util.currencyJSONData}}
			 				 	,formatter:common_util.currencyFormat},
			 				 {field:'qty',title:'数量',width:100,editor:{type:'numberbox',options:{precision:0,validType:'maxLength[8]'}},align:'right',halign:'center'},
			 				 {field:'cost',title:'单价',width:100,editor:{type:'numberbox',options:{precision:2,min:0,validType:'maxLength[12]'}},align:'right',halign:'center'},
							 {field:'amount',title:'金额',width:100,editor:{type:'numberbox',options:{precision:2, validType:'maxLength[12]'}},align:'right',halign:'center'},
			 				 {field:'remark',title:'备注',width:180,editor:{type:'validatebox',options:{validType:'maxLength[200]'}},align:'left',halign:'center'}
			 				]"	
				          jsonExtend='{
				          
	                           onDblClickRow:function(rowIndex){
			                	  //双击方法
			                   	   initialAmount.edit(rowIndex);
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
                     {field : 'validateObj.customerNo', title : '客户编码', width : 100},
                     {field : 'validateObj.itemCode', title : '商品编码', width : 150},	
	 				 {field : 'validateObj.initialDate',title:'日期',width:100},
	 				 {field : 'validateObj.qty',title:'数量',width:100,align:'right'},
	 				 {field : 'validateObj.cost',title:'单价',width:100,align:'right'},
					 {field : 'validateObj.amount',title:'金额',width:100,align:'right'},
	 				 {field : 'validateObj.remark',title:'备注',width:180,align:'left'}]"	
         />
     </div>		
</body>
</html>