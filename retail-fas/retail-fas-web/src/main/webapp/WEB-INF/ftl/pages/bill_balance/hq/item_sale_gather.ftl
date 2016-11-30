<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="gatherTab" data-options="region:'center',border:false">
	      <@p.datagrid id="saleGatherDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""  showFooter="true"
	              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
		           rownumbers="true"   emptyMsg = "" 
		           columnsJsonList="[
		           	  {field : 'brandName', title : '品牌', width : 80},
		           	  {field : 'organName', title : '管理城市', width : 100},
		           	  {field : 'itemCode', title : '商品编码', width : 150,align:'left'},
					  {field : 'itemName', title : '商品名称', width : 150,align:'left'},
	                  {field : 'cost', title : '地区价', width : 80,align:'rigth'},	
	                  {field : 'sendQty', title : '数量', width : 80},
					  {field : 'sendAmount', title : '金额', width : 80,align:'rigth'},
					  {field : 'oneLevelCategoryName', title : '大类', width : 80},
					  {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
					  {field : 'categoryName', title : '三级大类', width : 80},
					  {field : 'years', title : '年份', width : 80},
					  {field : 'genderName', title : '性别', width : 80},
					  {field : 'remark', title : '备注', width : 150}]" 
             />
	 </div>
</div>