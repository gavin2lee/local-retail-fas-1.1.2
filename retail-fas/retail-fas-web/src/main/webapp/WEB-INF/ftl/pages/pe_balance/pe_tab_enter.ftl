<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="enterTab" data-options="region:'center',border:false">
		<@p.datagrid id="peEnterDataGrid"  showFooter="true" rownumbers="true" 
			isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
              {field : 'businessBillNo', title : '单据编码', width : 120},
              {field : 'settlementNumber', title : '结算号', width : 100},
              {field : 'supplierAmount', title : '含税厂商金额', width : 100},
              {field : 'amount', title : '含税结算金额', width : 100},
              {field : 'diffAmount', title : '含税差异金额', width : 100},
              {field : 'notTaxSupplierAmount', title : '不含税厂商金额', width : 100},
              {field : 'notTaxAmount', title : '不含税结算金额', width : 100},
              {field : 'notTaxDiffAmount', title : '不含税差异金额', width : 100},
              {field : 'orderTypeName', title : '订货类型', width : 100},
              {field : 'qty', title : '发出数量', width : 100},
              {field : 'tagAmount', title : '牌价额', width : 100},
              {field : 'supplierDiscount1', title : '厂商折扣1', width : 100},
              {field : 'supplierDiscount2', title : '厂商折扣2', width : 100},
              {field : 'balanceDiscount1', title : '对账折扣1', width : 100},
              {field : 'balanceDiscount2', title : '对账折扣2', width : 100},
              {field : 'orderNo', title : '订单号', width : 100},  
              {field : 'supplierSendDate', title : '供应商发货日', width : 100},    
              {field : 'billDate', title : '发出日', width : 100},   
              {field : 'buyerName', title : '收方公司', width : 180},  
              {field : 'orderUnitName', title : '货管单位', width : 150},  
              {field : 'storeName', title : '机构', width : 150},  
              {field : 'updateUser', title : '修改人', width : 150},  
              {field : 'updateTime', title : '修改时间', width : 150}]" 
			/>
	</div>
</div>