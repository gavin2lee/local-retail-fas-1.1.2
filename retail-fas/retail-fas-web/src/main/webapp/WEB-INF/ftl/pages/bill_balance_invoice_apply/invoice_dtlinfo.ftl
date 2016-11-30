<div class="easyui-layout" data-options="fit:true,plain:true">   
	<div data-options="region:'center',border:false">
	   <@p.datagrid id="invoiceDtlDataGrid"  
		loadUrl=""  saveUrl=""   defaultColumn="" 
		 isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
		 checkOnSelect="true"  rownumbers="false" singleSelect="true"  
		columnsJsonList="[			
		{field : 'salerNo',title : '销售单位编码',width : 90,
			formatter: function(value,row,index){
					if(row.salerNo){
						return row.salerNo;
					}
					if(row.shopNo){
						return row.shopNo;
					}
					return '';
			}
			},
			{field : 'salerName',title : '销售单位名称',width : 150,
			formatter: function(value,row,index){
					if(row.salerName){
						return row.salerName;
					}
					if(row.shopName){
						return row.shopName;
					}
					return '';
			}
			},
			{field : 'billNo',title : '单据编号',width : 150,
			formatter: function(value,row,index){
					if(row.billNo){
						return row.billNo;
					}
					if(row.orderNo){
						return row.orderNo;
					}
					return '';
			}
			},
			{field : 'sendDate',title : '单据日期',width : 100,
			formatter: function(value,row,index){
					if(row.sendDate){
						return row.sendDate;
					}
					if(row.outDate){
						return billBalanceInvoiceApply.formatterDate(row.outDate);
					}
					return '';
			}
			},
			{field : 'itemCode',title : '商品编码',width : 200,
			formatter: function(value,row,index){
					if(row.itemCode){
						return row.itemCode;
					}
					if(row.itemCode){
						return row.itemCode;
					}
					return '';
			}
			},
			{field : 'itemName',title : '商品名称',width : 200,
			formatter: function(value,row,index){
					if(row.itemName){
						return row.itemName;
					}
					if(row.itemName){
						return row.itemName;
					}
					return '';
			}
			},
			{field : 'billingAmount',title : '牌价',width : 100,
			formatter: function(value,row,index){
					if(row.billingAmount){
						return row.billingAmount;
					}
					if(row.tagPrice){
						return row.tagPrice;
					}
			}
			},
			{field : 'billCost',title : '现价',width : 100,
			formatter: function(value,row,index){
					if(row.billCost){
						return row.billCost;
					}
					if(row.salePrice){
						return row.salePrice;
					}
					return '';
			}
			},
			{field : 'sendQty',title : '数量',width : 100,
			formatter: function(value,row,index){
					if(row.sendQty){
						return row.sendQty;
					}
					if(row.dtlQty){
						return row.dtlQty;
					}
					if(row.qty){
						return row.qty;
					}
					return '';
			}
			},
			{field : 'cost',title : '结算价',width : 100,
			formatter: function(value,row,index){
					if(row.cost){
						return row.cost;
					}
					if(row.settlePrice){
						return row.settlePrice;
					}
					return '';
			}
			},
			{field : 'sendAmount',title : '金额',width : 100,
			formatter: function(value,row,index){
					if(row.sendAmount){
						return row.sendAmount;
					}
					if(row.amount){
						return row.amount;
					}
					return '';
			}
			},
			{field : 'unitCost',title : '单位成本',width : 80},
			{field : 'regionCost',title : '地区成本',width : 80},
			{field : 'headquarterCost',title : '总部成本',width : 80},
			{field : 'proName',title : '促销活动',width : 200},
			{field : 'taxRate',title : '扣率%',width : 100,
			formatter: function(value,row,index){
					if(row.taxRate){
						return row.taxRate*100+'%';
					}
					if(row.discount){
					    return row.discount+'%';
					}
					return '';
					
			}
			}
			]" 
		 <!--jsonExtend="{onDblClickRow:function(rowIndex,rowData){
					billBalanceInvoiceBase.editInvoiceSource(rowIndex,'invoiceSource','invoiceEditIndex')
		 }}"-->/>
 </div>
</div>