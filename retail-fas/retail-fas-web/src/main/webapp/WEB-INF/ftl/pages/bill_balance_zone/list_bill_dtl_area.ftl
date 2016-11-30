<div data-options="region:'center',border:false">
      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="" showFooter="true"
              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
	           rownumbers="true" singleSelect="true"
	           columnsJsonList="[
	                  {field:'salerName', title : '公司名称', width : 220, align : 'left'},
	                  {field:'salerNo', title : '公司编码', width : 80, align : 'left'},
	                  {field:'buyerName', title : '客户名称', width : 220, align : 'left'},
	                  {field:'buyerNo', title : '客户编码', width : 80, align : 'left'},
	                  {field : 'organNameFrom', title : '管理城市',  width : 120},
	                  {field : 'bizTypeName', title : '业务类型',  width : 120, formatter: function(value,row,index){
	                  		if(row.bizType == '21') {
	                  			return '批发销售';
	                  		}else if (row.bizType == '22') {
	                  			return '过季退货';
	                  		}else if (row.bizType == '29') {
	                  			return '批发销售(店出)';
	                  		}else if (row.bizType == '30') {
	                  			return '客残退货';
	                  		}else if (row.bizType == '43') {
	                  			return '召回退货';
	                  		}
	                  }},
	                  {field:'originalBillNo', title : '单据编号', width : 120, align : 'left'},
  			  		  {field:'refBillNo',title : '批发订单号',width : 120, align : 'left'},
		  			  {field:'itemCode',title : '商品编码',width : 120, align : 'left'},
		  			  {field:'itemName',title : '商品名称',width : 140, align : 'left'},
		  			  {field : 'mainColor', title : '颜色', width : 100},
	                  {field:'brandName',title : '品牌',width : 80, align : 'left'},
	                  {field:'brandUnitName',title : '品牌部',width : 80, align : 'left'},
	                  {field : 'oneLevelCategoryName', title : '一级大类', width : 100},	
	                  {field : 'twoLevelCategoryName', title : '二级大类', width : 100},	
	                  {field:'headquarterCost',title : '总部成本',width : 90, align : 'right'},
		  			  {field:'regionCost',title : '地区成本',width : 90, align : 'right'},
		  			  {field:'unitCost',title : '单位成本',width : 90, align : 'right'},
	                  {field:'cost',title : '发出单价',width : 90, align : 'right', formatter: function(value,row,index){
							if(row.salerName == '合计'){
								return '';
							}
							return value;
					  }},	
					  {field:'otherDeductCost',title : '分摊金额 (票前返利+扣项)',width : 180, align : 'right'},
		  			  {field:'sendQty',title : '发货数量',width : 80, align : 'right'},
		  			  {field:'sendAmount',title : '发出金额',width : 90, align : 'right'},
		  			  {field : 'tagPrice',title : '牌价',width : 100, align : 'right'},
		  			  {field : 'purchasePrice',title : '采购价',width : 100, align : 'right'},
                      {field : 'materialPrice',title : '物料价',width : 100, align : 'right'},
                      {field : 'factoryPrice',title : '厂进价',width : 100, align : 'right'},
					  {field : 'discount',title : '扣率',width : 100, align : 'right', formatter:function(value,row,index){
						  if(row.discount != null){ 
						  	return row.discountStr;
						  }
					  }},
		  			  {field : 'billRebateDiscountStr',title : '返利后折扣',width : 100, align : 'right'},
		  			  {field:'sendDate',title : '发货日期',width : 100, align : 'center'},
		  			  {field:'unfrozenDate',title : '解冻日期',width : 100, align : 'center'},
		  			  {field:'statusName',title : '状态',width : 100, align : 'center'},
		  			  {field:'balanceNo',title : '结算单号',width : 160, align : 'center', formatter: function(value,row,index){
						  return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AW-结算单');
					  }}
	                 ]" 
		          jsonExtend='{
                       onDblClickRow:function(rowIndex, rowData){
	                	  
	              }}'
         />
	</div>