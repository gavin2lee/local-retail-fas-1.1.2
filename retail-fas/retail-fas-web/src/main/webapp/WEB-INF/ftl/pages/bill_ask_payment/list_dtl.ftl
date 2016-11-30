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
				{field : 'amount',title:'金额',width:100,editor:{type:'numberbox',options:{required:true, precision:2, validType:['maxLength[12]']}}},
				{field : 'otherBank',title:'开户行',width:100,exportType:'string',editor:{type:'validatebox',options:{validType:['unNormalData','maxLength[30]']}}},
				{field : 'otherBankAccount',title:'对方帐号',width:100,exportType:'string',editor:{type:'validatebox',options:{validType:['unNormalData','maxLength[30]']}}},
				{field : 'qty',title:'数量',width:120,editor:{type:'numberbox', options:{required:true, validType:['maxLength[9]']}}},
				{field : 'brandName', title : '品牌部', width : 80, editor:{type:'test_combogrid',options:{type:'brandUnit',datagridId:'dtlDataGrid',valueField:'brandNo'}}},
                {field : 'brandNo', title : '品牌部编码', hidden:'true', editor:{type:'readonlytext'}},
 				{field : 'categoryName', title : '财务大类', width : 80, editor:{type:'test_combogrid',options:{type:'financialCategory',datagridId:'dtlDataGrid',valueField:'categoryNo',queryUrl:''}}},
                {field : 'categoryNo', title : '财务大类编码', hidden:'true', editor:{type:'readonlytext'}},
				{field:'remark',title:'备注',width:180,editor:{type:'validatebox',options:{validType:'maxLength[200]'}}}]"		
				 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
				 		billController.editDtl(rowIndex);
				 }}"/>
	</div>
</div>