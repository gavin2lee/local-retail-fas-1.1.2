<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="outTab" data-options="region:'center',border:false">
		<@p.datagrid id="outDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""  showFooter="true"
			              isHasToolBar="false"    onClickRowEdit="false" pagination="true" pageSize="20"
				          rownumbers="true"  emptyMsg = "" 
				      	  columnsJsonList="[
				      	     {field : 'buyerName', title : '公司',  width : 200,align:'left', formatter: function(value,row,index){
								if(row.salerName == '合计'){
									return row.salerName;
								}
								return value;
							  }},	
							  {field : 'salerName', title : '内部供应商', width : 200,align:'left'},	
			                  {field : 'sendDate', title : '发出日期', width : 100},
			                  {field : 'brandName', title : '品牌', width : 80},
			                  {field : 'oneLevelCategoryName', title : '大类', width : 80},	
			                  {field : 'itemCode', title : '商品编码', width : 150},
			                  {field : 'itemName', title : '商品名称', width : 150,align:'left'},
			                  {field : 'cost', title : '地区价', width : 80, formatter: function(value,row,index){
									if(row.salerName == '合计'){
										return '';
									}
									return value;
							  }},	
			                  {field : 'sendQty', title : '发出数量', width : 80},
			                  {field : 'sendAmount', title : '发出金额', width : 100},
							  {field : 'originalBillNo', title : '单据编号', width : 150},
			                  {field : 'billTypeName', title : '单据类型', width : 100},
			                  {field : 'supplierName', title : '供应商',  width : 200,align:'left'},
			                  {field : 'supplierNo', title : '供应商编码',  width : 120, hidden:true},
			                  {field : 'organName', title : '管理城市',  width : 120},
			                  {field : 'orderUnitName', title : '货管单位',  width : 120},
			                  {field : 'twoLevelCategoryName', title : '二级大类', width : 100},	
			                  {field : 'orderNo', title : '采购订单号', width : 100}]" 
				             /> 
	</div>
</div>