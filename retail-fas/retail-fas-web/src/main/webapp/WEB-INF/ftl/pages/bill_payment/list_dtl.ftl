<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" >
    	<@p.toolbar id="dtl_toor_bar" listData=[
			{"id":"foot_btn_pre","title":"新增明细","iconCls":"icon-add-dtl","action":"billController.addDtl()","type":40} ,
			{"id":"foot_btn_next","title":"删除明细","iconCls":"icon-del-dtl","action":"billController.delDtl()","type":43}
		 ]/>
    </div>
    <div data-options="region:'center',border:false" id="dtlDGTab">
    <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
		isHasToolBar="false" divToolbar=""    pageSize="500" 
		checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
		onClickRowAuto="false" 
		columnsJsonList="[
			{field:'ck',checkbox:true,notexport:true},
			{field:'settleMethodNo',title:'结算方式',width:100,formatter:function(value,index,row){
				return common_util.settleMethodData[value];}
				,editor:{type:'combobox',options:{required:true, valueField:'code',textField:'name',data : common_util.settleMethodJsonData}}},	
			{field : 'payQty',title:'应付数量',width:120,editor:{type:'numberbox', options:{required:true, validType:['maxLength[9]']}}},	
			{field:'payAmount',title:'应付金额',width:100,editor:{type:'numberbox',options:{required:true,precision:2,validType:['maxLength[12]']}}},
			{field:'moneyDiscount',title:'折扣金额',width:100,editor:{type:'numberbox',options:{precision:2,validType:['maxLength[12]']}}},
			{field:'discountAmount',title:'折后金额',width:120,editor:{type:'readonlytext'}},
			{field:'fee',title:'手续费',width:100,editor:{type:'numberbox',options:{precision:2, validType:['maxLength[12]']}}},
			{field:'balanceAmount',title:'实付金额',width:100, editor:{type:'readonlytext'}},
			{field:'bankAccount',title:'我方银行帐号',width:120,editor:{type:'validatebox',options:{validType:['unNormalData','maxLength[30]']}}},
			{field:'bankBalanceNo',title:'银行结算号',width:120,editor:{type:'validatebox',options:{validType:['unNormalData','maxLength[30]']}}},
			{field:'remark',title:'备注',width:120,editor:{type:'validatebox',options:{validType:'maxLength[200]'}}}]"		
		 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
		 		billController.editDtl(rowIndex);
		 }}"/>
    </div>
</div>