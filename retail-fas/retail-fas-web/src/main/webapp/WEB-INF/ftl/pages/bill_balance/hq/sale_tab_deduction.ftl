<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" >
		<@p.toolbar id="dtl_toor_bar" listData=[
			{"id":"foot_btn_pre","title":"新增明细","iconCls":"icon-add-dtl","action":"saleBalance.addDtl()","type":0} ,
			{"id":"foot_btn_next","title":"删除明细","iconCls":"icon-del-dtl","action":"saleBalance.delDtl()","type":0}
		 ]/>
	</div>
	<div id="deductionTab" data-options="region:'center',border:false">
		<@p.datagrid id="deductionDataGrid"  emptyMsg="" rownumbers="true" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="500" 
			columnsJsonList="[
				 {field:'ck',checkbox:true,notexport:true},
			 	 {field : 'deductionDate',title:'日期',width:100,editor:{type:'datebox',options:{required:true}}},
                 {field : 'deductionAmount',title:'金额',width:120,editor:{type:'numberbox', options:{required:true, precision:2, validType:['maxLength[9]']}}},
                 {field : 'deductionQty',title:'数量',width:120,editor:{type:'numberbox', options:{ required:true, min:0, precision:0,  validType:['maxLength[9]']}}},
                 {field : 'brandName', title : '品牌', width : 120, editor:{type:'test_combogrid',options:{required:true,type:'brand',datagridId:'deductionDataGrid',valueField:'brandNo'}}, align:'left'},
                 {field : 'brandNo', title : '品牌编号', hidden:'true', editor:{type:'readonlytext'}},
                 {field : 'categoryName', title : '大类', width : 120, editor:{type:'test_combogrid',options:{required:true,type:'category',datagridId:'deductionDataGrid',valueField:'categoryNo'}}, align:'left'},
                 {field : 'categoryNo', title : '大类编号', hidden:'true', editor:{type:'readonlytext'}},
				 {field:'remark',title:'备注',width:180,editor:{type:'validatebox',options:{validType:'maxLength[200]'}}}]"	
				  jsonExtend="{onDblClickRow:function(rowIndex,rowData){
				 		saleBalance.editDtl(rowIndex);
				 }}"/>	
			/>
	</div>
</div>