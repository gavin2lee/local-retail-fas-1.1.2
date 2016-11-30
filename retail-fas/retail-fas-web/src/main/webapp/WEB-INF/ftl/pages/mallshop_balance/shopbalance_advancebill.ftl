<!DOCTYPE html>
<head>
    <title>预收款信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout">
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
<!--
<div data-options="region:'north',border:false">
    	<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "fas_common_editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "shopBalanceDeduct.save()", "type":0}		             
		           ]
				/>
    </div>
    -->
	<@p.commonSetting search_url="/expect_cash/list.json" 
					  save_url="/expect_cash/post" 
					  update_url="/expect_cash/put" 
					  del_url="/expect_cash/save" 
					  datagrid_id="expectCashDataGrid" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="1000" 
					  dialog_height="300"
					  primary_key="id"/>
	  			<!--搜索start-->
			<!--列表-->
 <div data-options="region:'center',border:false">
	   <@p.datagrid id="expectCashDataGrid"  
		loadUrl="" saveUrl=""   defaultColumn="" 
 		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
  		 checkOnSelect="true"  rownumbers="false" singleSelect="false"  
			columnsJsonList="[
			    {field : 't',hidden : 'true', checkbox:true, width : 30, notexport:true},
				{field : 'billNo',title : '预收款凭证编码',width : 120},
				{field : 'businessDate',title : '日期',width : 120},
				{field : 'amount',title : '预收金额',width : 120},
				{field : 'customerName',title : '客户',width : 120},
				{field : 'usedAmount',title : '本期冲销金额',width : 120},
				{field : 'businessDate',title : '冲销日期',width : 120},
				{field : 'billNo',title : '累计冲销金额',width : 120},
				{field : 'amount',title : '预收款余额',width : 120},
				{field : 'amount',title : '本次结算金额',width : 100,align:'center',
				                  	editor:{
				                  		type:'numberbox',
				                  		options:{
				                  			required:true,precision:2,min:0, validType:['number','maxLength[12]']
				                  		}
				                  	}
				                  }
				 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
			                             //  if(!shopBalanceDeduct.check(rowIndex,rowData)){  
					                   	       fas_common_editor.editRow(rowIndex, rowData);
					                   	  //}
					                   }
			         }'/>
			</div>
</div>
	</body>
</html>