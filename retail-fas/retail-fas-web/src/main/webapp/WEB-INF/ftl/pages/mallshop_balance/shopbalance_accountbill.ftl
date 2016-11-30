<!DOCTYPE html>
<head>
    <title>收款信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="easyui-layout">
<div id="main_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',bordRer:false">
 		<@p.datagrid id="balanceAccountDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
      		isHasToolBar="false" onClickRowEdit="false" pagination="true"  rownumbers="false" 
       		columnsJsonList="[
           		{field : 't',checkbox:true,hidden : 'true',width : 30},	
            	{field : 'id',hidden : 'true',align:'center'},			           
            	{field : 'backsectionDtlNo',hidden : 'true',title : '预收款单号',width : 150,align:'center'},					                
            	{field : 'backsectionNo',title : '收款单号',width : 150,align:'center'},	
				{field : 'thePaymentAmount',title : '金额',width : 100,align:'center'},	
				{field : 'backDate',title : '日期',width : 100,align:'center'},	
		    	{field : 'remark',title : '备注',width : 150,align:'center'},									               
             ]" 
          	jsonExtend='{
                   onDblClickRow:function(rowIndex, rowData){}
 		}'/>
     </div>
 </div>
</body>
</html>