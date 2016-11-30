<div class="easyui-layout" data-options="fit:true,plain:true">
	<!--列表-->
	<div data-options="region:'center',border:false">
      <@p.subdatagrid id="posDtlDataGrid" loadUrl="" saveUrl=""   defaultColumn=""   title=""
	           isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
			   rownumbers="true" singleSelect="true"  showFooter="true"
	           columnsJsonList="[
                	  {field:'ck',checkbox:true,notexport:true},
               		　{field : 'companyName', title : '公司', width : 180, align : 'left',halign:'center'},
                	  {field : 'organName', title : '管理城市', width : 60, align : 'center',halign:'center'},
                	  {field : 'shopNo', title : '店铺编号', width : 80, align : 'left',halign:'center'},
                	  {field : 'shopName', title : '店铺名称', width : 110, align : 'left',halign:'center'},
                	  {field : 'financeConfirmFlagStr',title : '财务确认',width : 60, align : 'center',halign:'center'},
               		  {field : 'bizTypeName',title : '业务类型',width : 90, align : 'center',halign:'center'},
               		  {field : 'clientNo', title : '客户编号', width : 80, align : 'left',halign:'center'},
		              {field : 'clientName', title : '客户名称', width : 80, align : 'left',halign:'center'},
               		  {field : 'outDate',title : '单据日期',width : 90, align : 'center'},
               		  {field : 'assistantNo',title : '营业员编号',width : 100, align : 'left',halign:'center'},
		  			  {field : 'assistantName',title : '营业员名称',width : 100, align : 'center',halign:'center'},
               		  {field : 'orderNo', title : '单据编码', width : 150, align : 'left',halign:'center'},
	                  {field : 'itemCode',title : '商品编码',width : 150, align : 'left',halign:'center'},
  			  		  {field : 'itemName',title : '商品名称',width : 150, align : 'left',halign:'center'},
  			  		  {field : 'brandName',title : '品牌',width : 80, align : 'center'},
  			  		  {field : 'brandUnitName',title : '品牌部',width : 80, align : 'center'},
		  			  {field : 'categoryName',title : '大类',width : 80, align : 'center'},
		  			  {field : 'qty',title : '数量',width : 50, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'tagPrice',title : '牌价额',width : 80, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'settlePrice',title : '结算额',width : 80, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'unitCost',title : '单位成本',width : 80, align : 'right',exportType:'number',halign:'center'},
				  	  {field : 'regionCost',title : '地区成本',width : 80, align : 'right',exportType:'number',halign:'center'},
				  	  {field : 'headquarterCost',title : '总部成本',width : 80, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'discPrice',title : '优惠券优惠金额',width : 80, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'discAmount',title : '优惠券价值',width : 80, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'discApplyNo',title : '优惠券开票申请单号',width : 150, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'amount', title : '结算金额', width : 80,align : 'right',exportType:'number',halign:'center'},
		  			  <!-- {field : 'ticketAmount',title : '现金券面额',width : 80, align : 'left',exportType:'number'}, -->
		  			  <!-- {field : 'ticketPrice',title : '现金券价值',width : 80, align : 'left',exportType:'number'}, -->
		  			  <!-- {field : 'ticketApplyNo',title : '现金券开票申请单号',width : 150, align : 'left',exportType:'number'}, -->
		  			  <!-- {field : 'diffAmount',title : '补差金额',width : 80, align : 'left',exportType:'number'}, -->
		  			  {field : 'sellAmount',title : '实际销售金额',width : 80, align : 'right',exportType:'number',halign:'center'},
		  			  {field : 'invoiceNo',title : '开票申请号',width : 150, align : 'left',halign:'center'},
		  			  {field : 'invoiceDate',title : '开票日期',width : 140, align : 'center'},
		  			  {field : 'receivingName',title : '顾客姓名',width : 150, align : 'center'},
		  			  {field : 'receivingTel',title : '手机号码',width : 140, align : 'left',halign:'center'},
		  			  {field : 'receivingAddress',title : '收货地址',width : 150, align : 'left',halign:'center'},
		  			  {field : 'createUser',title : '制单人',width : 90, align : 'center'},
		  			  {field : 'createTime',title : '制单时间',width : 140, align : 'center'},
		  			  {field : 'updateUser',title : '财务确认人',width : 90, align : 'center'},
		  			  {field : 'updateTime',title : '确认时间',width : 140, align : 'center'},
		  			  {field : 'remark',title : '备注',width : 200, align : 'left',halign:'center'}
	            ]" 
         />
	</div>
</div>	