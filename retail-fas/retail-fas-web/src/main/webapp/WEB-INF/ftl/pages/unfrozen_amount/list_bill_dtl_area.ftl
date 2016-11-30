<div data-options="region:'center',border:false">
      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="" showFooter="true"
              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
	           rownumbers="true" singleSelect="true"
	           columnsJsonList="[
	                  {field : 't', checkbox:true, width : 30, notexport:true,},
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
	                  		}else if (row.bizType == '43') {
	                  			return '召回退货';
	                  		}else if (row.bizType == '30') {
	                  			return '客残退货';
	                  		}
	                  }},
	                  {field:'originalBillNo', title : '单据编号', width : 120, align : 'left'},
	                  {field:'returnNo', title : '退货编号', width : 120, align : 'left'},
  			  		  {field:'statusName',title : '状态',width : 120, align : 'left'},
  			  		  {field:'sendQty',title : '发货数量',width : 80, align : 'right'},
		  			  {field:'receiveQty',title : '解冻数量',width : 80, align : 'right'},
		  			  {field:'receiveAmount',title : '解冻金额',width : 80, align : 'right'},
		  			  {field:'itemCode',title : '商品编码',width : 120, align : 'left'},
		  			  {field:'itemName',title : '商品名称',width : 140, align : 'left'},
		  			  {field : 'mainColor', title : '颜色', width : 100},
	                  {field:'brandName',title : '品牌',width : 80, align : 'left'},
	                  {field:'brandUnitName',title : '品牌部',width : 80, align : 'left'},
	                  {field : 'oneLevelCategoryName', title : '一级大类', width : 100},	
	                  {field : 'twoLevelCategoryName', title : '二级大类', width : 100},	
	                  {field:'cost',title : '单价',width : 90, align : 'right', formatter: function(value,row,index){
							if(row.salerName == '合计'){
								return '';
							}
							return value;
					  }},	
		  			  {field:'sendAmount',title : '发出金额',width : 90, align : 'right'},
		  			  {field : 'tagPrice',title : '牌价',width : 100, align : 'right'},
					  {field : 'discount',title : '扣率',width : 100, align : 'right', formatter:function(value,row,index){
						  if(row.discount != null){ 
						  	return row.discountStr;
						  }
					  }},
					  {field:'unfrozenDate',title : '解冻日期',width : 100, align : 'center'},
		  			  {field:'sendDate',title : '发货日期',width : 100, align : 'center'}
	                 ]" 
		          jsonExtend='{
                       onDblClickRow:function(rowIndex, rowData){
	                	  
	              }}'
         />
	</div>