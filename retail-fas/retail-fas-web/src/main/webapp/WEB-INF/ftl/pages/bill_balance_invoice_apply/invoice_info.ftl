<div class="easyui-layout" data-options="fit:true,plain:true">
	 <!--<div data-options="region:'north',border:false">
    	<@p.toolbar id="invoice_info_bar" listData=[
			{"id":"foot_btn_pre","title":"新增行","iconCls":"icon-add-dtl","action":"invoiceEditor.insertRow()","type":0} ,
			{"id":"foot_btn_next","title":"删除行","iconCls":"icon-del-dtl","action":"invoiceEditor.deleteRow()","type":0},
		 	{"id":"foot_btn_next","title":"保存","iconCls":"icon-save-dtl","action":"invoiceEditor.save()","type":0}
		 ]/>
    </div>-->
   
	<!--<div data-options="region:'center',border:false" id="invoiceInfoDataGridDiv">-->
	<div data-options="region:'center',border:false">
	   <@p.subdatagrid id="invoiceInfoDataGrid"  
		loadUrl="" saveUrl=""   defaultColumn="" 
		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		 checkOnSelect="true"  rownumbers="false" singleSelect="false"  
		columnsJsonList="[
			{field : 'balanceNo',title : '结算单号',width : 150, hidden:true},
			{field : 'billNo',title : '发票登记号',width : 180,
				editor:{
					type:'validatebox',
					options:{
				 		required:true
				 	}
				}
			},
			{field : 'billDate',title : '发票日期',width : 180,
				editor:{
					type:'datebox',
				 	options:{
				 		required:true
				 	}
				}
			},
			{field : 'invoiceType',title : '发票类型',width : 150,
				formatter:function(rowIndex,row,value){
					if(row && row.invoiceType == '0') {
						return '普通发票';
					}
					if(row && row.invoiceType == '1') {
						return '增值票';
					}
				},
				editor:{
					type:'validatebox',
				 	options:{
				        
				    }
				}
			},
			{field : 'amount',title : '金额',width : 150,
				editor:{
					type:'numberbox',
				 	options:{
				        required:false,
				        precision:2
				    }
				}
			},
			{field : 'billDate',title : '单据日期',width : 150,
				editor:{
					type:'validatebox',
				 	options:{
				      
				    }
				}
			},
			{field : 'taxAmount',title : '含税金额',width : 80, hidden:true,
				editor:{
					type:'numberbox',
				 	options:{
				        required:false,
				        precision:2
				    }
				}
			}]" 
			loadSubGridUrl="/bill_common_register_dtl/get_biz"
         	 subPagination="false"
         	 subHeight=300
         	 subGridColumnsJsonList="[
         	  	{field : 'invoiceCode', title : '发票编码', width : 100, align : 'center'},
		      	{field : 'invoiceNo', title : '发票号', width : 100, align : 'center'},
		      	{field : 'invoiceDate', title : '发票日期', width : 100, align : 'center', halign:'center'},
                {field : 'invoiceAmount', title : '发票金额', width : 80, align : 'right', halign:'center'},
                {field : 'qty', title : '数量', width : 80, align : 'right', halign:'center'},
                {field : 'express', title : '快递公司', width : 100, align : 'left'},
                {field : 'deliveryNo', title : '快递单号', width : 100, align : 'left'},
                {field : 'deliveryDate', title : '快递日期', width : 100, align : 'center', halign:'center'},
                {field : 'auditor', title : '确认人', width : 100, align : 'center', halign:'center'},
                {field : 'auditDate', title : '确认日期', width : 100, align : 'center', halign:'center'}
         	]" 
				/>
 </div>
</div>
			