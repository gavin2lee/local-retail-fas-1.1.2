<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" >
    	<@p.toolbar id="dtl_toor_bar" listData=[
			{"id":"foot_btn_pre","title":"新增明细","iconCls":"icon-add-dtl","action":"billController.addDtl()","type":40} ,
			{"id":"foot_btn_next","title":"删除明细","iconCls":"icon-del-dtl","action":"billController.delDtl()","type":43},
			{"id":"foot_btn_import","title":"导入明细","iconCls":"icon-import","action":"billController.doImport('采购发票.xlsx','/bill_invoice/do_import',importCallBack)","type":47}
		 ]/>
    </div>
    <div data-options="region:'center',border:false" id="dtlDGTab">
    <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
		isHasToolBar="false" divToolbar=""    pageSize="500" 
		checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
		onClickRowAuto="false" 
		columnsJsonList="[
		{field:'ck',checkbox:true,notexport:true},
		{field:'invoiceCode',title:'发票代码',width:110,editor:{type:'validatebox',options:{required:true, validType:['unNormalData','maxLength[18]']}}},
		{field:'invoiceNo',title:'发票号码',width:110,editor:{type:'validatebox',options:{required:true, validType:['unNormalData','maxLength[30]']}}},
		{field:'invoiceDate',title:'发票日期',width:100,editor:{type:'datebox',options:{required:true}}},
		{field:'invoiceAmount',title:'发票金额',width:100,editor:{type:'numberbox',options:{required:true, precision:2,validType:['maxLength[12]']}}},
		{field:'taxRate',title:'税率',width:100,editor:{type:'numberbox',options:{required:true, precision:2,min:0,max:1}}},
		{field:'noTaxAmount',title:'不含税金额',width:120,editor:'readonlytext'},
		{field:'taxAmount',title:'税额',width:120,editor:'readonlytext'},
		{field:'qty',title:'数量',width:100,editor:{type:'numberbox', options:{validType:['maxLength[9]']}}},
		{field:'price',title:'单价',width:100,editor:'readonlytext'},
		{field : 'categoryName', title : '财务大类', width : 80, editor:{type:'test_combogrid',options:{type:'financialCategory',datagridId:'dtlDataGrid',valueField:'categoryNo',queryUrl:''}}},
        {field : 'categoryNo', title : '财务大类编码', hidden:'true', editor:{type:'readonlytext'}}]"		
		 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
		 		billController.editDtl(rowIndex);
		 }}"/>
    </div>
</div>