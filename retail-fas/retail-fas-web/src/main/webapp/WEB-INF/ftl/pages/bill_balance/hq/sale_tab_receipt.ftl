<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
    	<@p.toolbar id="pay_foot_bar" listData=[
			{"id":"foot_btn_pre","title":"新增明细","iconCls":"icon-add-dtl","action":"saleBalance.addDtl(2)","type":0} ,
			{"id":"foot_btn_next","title":"删除明细","iconCls":"icon-del-dtl","action":"saleBalance.delDtl(2)","type":0},
			{"id":"foot_btn_next","title":"保存明细","iconCls":"icon-del-dtl","action":"saleBalance.saveDtl(2)","type":0}
		 ]/>
    </div>
     <div id="receiptTab" data-options="region:'center',border:false">
    <@p.datagrid id="saleReceiptDataGrid"  fit="true" fitColumns="false" emptyMsg=""
		isHasToolBar="false" divToolbar="" height="187"    pageSize="10" 
		onClickRowAuto="false" rownumbers="false" 
		columnsJsonList="[
		{field:'ck',checkbox:true,notexport:true},
        {field : 'billNo', title : '收款单号', width : 150, editor:{type:'validatebox'}},
        {field : 'billDate', title : '收款日期', width : 120, editor:{type:'datebox'}},
        {field : 'amount', title : '金额', width : 100, editor:{type:'numberbox'}},
		{field:'remark',title:'备注',width:200,editor:'validatebox'}]"		
		 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
				saleBalance.editDtl(rowIndex,2)
		 }}"/>
    </div>
</div>
