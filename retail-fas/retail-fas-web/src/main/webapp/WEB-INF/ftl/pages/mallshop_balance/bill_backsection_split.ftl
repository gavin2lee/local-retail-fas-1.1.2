<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>收款登记回款拆分</title>
<#include "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/bill_backsection_split.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/selectObjUtil.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body>
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false,showHeader:false" >
		<div data-options="title:'单据查询'">
			<div class="easyui-layout" data-options="fit:true">
				<div id="queryConditionDiv" data-options="region:'north',border:false">
					<@p.billToolBar type="bill_backsection_split_listBar"/>
					
					<div class="search-div">
				        <form name="searchForm" id="searchForm" method="post">
				            <table class="form-tb" >
							    <col width="100" />
							    <col  />
							    <col width="100" />
							    <col />
							    <col width="100" />
							    <col />
							    <col width="100"/>
							    <col />
							   	<tbody>	
						       		<tr>
										<td align="right" width="110">公司名称：</td>
										<td align="left" width="140">
											<input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName_"
												data-options="inputNoField:'companyNo_',inputNameField:'companyName_'"/>
											<input type="hidden" name="companyNo" id="companyNo_"/>
										</td>
										<th>回款方：</th>
										<td> 
											<input class="easyui-customerInvoiceInfo" name="backName" id="backNameId_" 
												data-options="
													queryUrl: BasePath + '/base_setting/invoice_info_set/list.json?status=1&clientTypes=3,4',
													inputNoField:'backNoId_',inputNameFeild:'backNameId_'" />
											<input type="hidden" name="backNo" id="backNoId_"/>
										</td>
										<td width="100" align="right">回款日期：</td>
						       		 	<td align="left">
							       		 	<input class="easyui-datebox ipt" name="backSectionDateStart" id="backSectionDateStart_"/>
							       		 	&nbsp;&nbsp;    至：&nbsp;&nbsp;    
							       		 	<input class="easyui-datebox ipt "  name="backSectionDateEnd" id="backSectionDateEnd_"/>
							       		</td>	
			   		 				</tr>
								</tbody>
							</table>   
				        </form>
			        </div>
			    </div>
							           
			    <div data-options="region:'center',border:false">
			    	<div id="subTab" class="easyui-tabs" data-options="fit:true,plain:true" >
						<div data-options="title:'回款单查询'">
					      <@p.subdatagrid id="backSplitDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
					              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
					              checkOnSelect="true" pageSize="20"
						          rownumbers="true" singleSelect="false"  				           
						          columnsJsonList="[
					                  {field : 't', checkbox:true, width : 30, notexport:true},
					                  {field : 'backsectionNo', title : '回款单号', width : 150, align : 'left'},
					                  {field : 'companyNo', hidden:true, title : '公司编号', width : 160, align : 'center'},
					                  {field : 'companyName', title : '公司名称', width : 200, align : 'left'},
					                  {field : 'backName', title : '回款方名称', width : 150, align : 'left'},
					                  {field : 'backDate', title : '回款日期', width : 80, align : 'center'},
					                  {field : 'backAmount', title : '回款金额', width : 80, align : 'right',exportType:'number'},
					                  {field : 'remark', title : '备注', width : 120, align : 'left'},
					                  {field : 'createUser',title : '建档人',width : 50,align:'left'}, 
						              {field : 'createTime',title : '建档时间',width : 130,align:'left'},
						              {field : 'updateUser',title : '修改人',width : 50,align:'left'}, 
						              {field : 'updateTime',title : '修改时间',width : 130,align:'left'}
				                  	]" 				          
						          	jsonExtend='{
				                       onDblClickRow:function(rowIndex, rowData){
					                   	 	billBacksectionSplit.loadDetail(rowIndex,rowData);
					                   }
					                }'
								loadSubGridUrl="/bill_backsection_split_dtl/get_biz"
								subPagination="false"
								subGridColumnsJsonList="[
									{field : 'shortName', title : '店铺名称', width : 160, align : 'left',notexport:true},
									{field : 'month', title : '结算月', width : 80, align : 'left',notexport:true},
									{field : 'balanceNo', title : '结算单号', width : 150, align : 'left',notexport:true},
									{field : 'billingAmount', title : '开票金额', width : 100, align : 'right',notexport:true},
									{field : 'ticketDeductionAmount', title : '票后帐扣费用', width : 100, align : 'right',notexport:true},
									{field : 'receiveAmount', title : '应收款', width : 100, align : 'right',notexport:true},
									{field : 'alreadyReceiveAmount', title : '前期累计回款', width : 100, align : 'right',notexport:true},
									<!-- {field : 'notReceiveAmount', title : '未回款金额', width : 100, align : 'right',notexport:true}, -->
									{field : 'thePaymentAmount', title : '本次回款 ', width : 100, align : 'right',notexport:true},
									{field : 'diffAmount', title : '应收款余额', width : 100,align : 'right',notexport:true},
									{field : 'diffReason', title : '差异原因', width : 150,align : 'left',notexport:true},
									{field : 'remark', title : '备注', width : 150,align : 'left',notexport:true}
								]" 
				         	/>
						</div>
						<div data-options="title:'回款单明细(店)'">
							<div id="tb">
								<table class="form-tb">
					      			<col width="80" />
									<col />
									<col width="80" />
									<col />
									<col width="80" />
									<col />
									<tbody>
										<tr>
											<th>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺： </th>
											<td>
												<input style="width:140px;" id="shopName"  />
												<input type="hidden" name="shopNo" id="shopNo"/>
											</td>
											<th align="right">结算月：</th>
											<td align="left"><input class="easyui-datebox" name="month" id="month" style="width:90px;"  data-options="multiple:true,dateFmt:'yyyyMM'" /></td>
											<th align="right" width="110" > 结算单号：</th>
											<td><input class="ipt" name="balanceNo" id="balanceNo"/></td>
										</tr>
									</tbody>
					      		</table>
							</div>
							<@p.datagrid id="backSplitDataDtlGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
					              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
					              checkOnSelect="true" pageSize="20" showFooter="true"
						          rownumbers="true" singleSelect="false" divToolbar="#tb"
						          columnsJsonList="[
										{field : 'backsectionNo', title : '回款单号', width : 150, align : 'left'},
										{field : 'companyNo', hidden:true, title : '公司编号', width : 160, align : 'center'},
										{field : 'companyName', title : '公司名称', width : 200, align : 'left'},
										{field : 'backNo', hidden:true, title : '回款方编号', width : 160, align : 'center'},
										{field : 'backName', title : '回款方名称', width : 150, align : 'left'},
										{field : 'backDate', title : '回款日期', width : 80, align : 'center'},
										{field : 'shopNo', hidden:true, title : '店铺编号', width : 160, align : 'center'},
										{field : 'shortName', title : '店铺名称', width : 160, align : 'left'},
										{field : 'month', title : '结算月', width : 80, align : 'left'},
										{field : 'balanceNo', title : '结算单号', width : 150, align : 'left'},
										{field : 'billingAmount', title : '开票金额', width : 100, align : 'right',exportType:'number'},
										{field : 'ticketDeductionAmount', title : '票后帐扣费用', width : 100, align : 'right',exportType:'number'},
										{field : 'receiveAmount', title : '应收款', width : 100, align : 'right',exportType:'number'},
										{field : 'alreadyReceiveAmount', title : '前期累计回款', width : 100, align : 'right',exportType:'number'},
										<!-- {field : 'notReceiveAmount', title : '未回款金额', width : 100, align : 'right',exportType:'number'}, -->
										{field : 'thePaymentAmount', title : '本次回款 ', width : 100, align : 'right',exportType:'number'},
										{field : 'diffAmount', title : '应收款余额', width : 100,align : 'right',exportType:'number'},
										{field : 'diffReason', title : '差异原因', width : 150,align : 'left'},
										{field : 'remark', title : '备注', width : 150,align : 'left'}
				                  	]" 				          
				         	/>
						</div>
						
						<div data-options="title:'回款单明细(店+品牌)'">
							<div id="shopBrandtb">
								<table class="form-tb">
					      			<col width="80" />
									<col />
									<col width="80" />
									<col />
									<col width="80" />
									<col />
									<tbody>
									<!--
										<tr>
											<th>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺： </th>
											<td>
												<input style="width:140px;" id="shopName_"  />
												<input type="hidden" name="shopNo" id="shopNo_"/>
											</td>
											<th align="right">结算月：</th>
											<td align="left"><input class="easyui-datebox" name="month" id="month_" style="width:90px;"  data-options="multiple:true,dateFmt:'yyyyMM'" /></td>
											<th align="right" width="110" > 结算单号：</th>
											<td><input class="ipt" name="balanceNo" id="balanceNo_"/></td>
										</tr>
										-->
									</tbody>
					      		</table>
							</div>
							<@p.datagrid id="backSplitShopBrandDataDtlGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
					              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
					              checkOnSelect="true" pageSize="20" showFooter="true"
						          rownumbers="true" singleSelect="false" divToolbar="#shopBrandtb"
						          columnsJsonList="[
										{field : 'backsectionNo', title : '回款单号', width : 150, align : 'left'},
										{field : 'companyNo', hidden:true, title : '公司编号', width : 160, align : 'center'},
										{field : 'companyName', title : '公司名称', width : 200, align : 'left'},
										{field : 'backNo', hidden:true, title : '回款方编号', width : 160, align : 'center'},
										{field : 'backName', title : '回款方名称', width : 150, align : 'left'},
										{field : 'backDate', title : '回款日期', width : 80, align : 'center'},
										{field : 'shopNo', hidden:true, title : '店铺编号', width : 160, align : 'center'},
										{field : 'shortName', title : '店铺名称', width : 160, align : 'left'},
										{field : 'month', title : '结算月', width : 80, align : 'left'},
										{field : 'balanceNo', title : '结算单号', width : 150, align : 'left'},
										{field : 'brandNo', hidden:true, title : '品牌编码', width : 160, align : 'center'},
										{field : 'brandName', title : '品牌部', width : 80, align : 'left'},
										{field : 'billingAmount', title : '开票金额', width : 100, align : 'right',exportType:'number'},
										{field : 'ticketDeductionAmount', title : '票后帐扣费用', width : 100, align : 'right',exportType:'number'},
										{field : 'receiveAmount', title : '应收款', width : 100, align : 'right',exportType:'number'},
										{field : 'alreadyReceiveAmount', title : '前期累计回款', width : 100, align : 'right',exportType:'number'},
										<!-- {field : 'notReceiveAmount', title : '未回款金额', width : 100, align : 'right',exportType:'number'}, -->
										{field : 'thePaymentAmount', title : '本次回款 ', width : 100, align : 'right',exportType:'number'},
										{field : 'diffAmount', title : '应收款余额', width : 100,align : 'right',exportType:'number'},
										{field : 'diffReason', title : '差异原因', width : 150,align : 'left'},
										{field : 'remark', title : '备注', width : 150,align : 'left'}
				                  	]" 				          
				         	/>
						</div>
						
						<div data-options="title:'回款单'">
						
							<div id="backSplitEditDatatb">
								<table class="form-tb">
					      			<col width="80" />
									<col />
									<col width="80" />
									<col />
									<col width="80" />
									<col />
									<tbody>
									<!--
										<tr>
											<th>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺： </th>
											<td>
												<input style="width:140px;" id="shopNameEdit"  />
												<input type="hidden" name="shopNoEdit" id="shopNoEdit"/>
											</td>
											<th align="right">结算月：</th>
											<td align="left"><input class="easyui-datebox" name="monthEdit" id="monthEdit" style="width:90px;"  data-options="multiple:true,dateFmt:'yyyyMM'" /></td>
											<th align="right" width="110" > 结算单号：</th>
											<td><input class="ipt" name="balanceNoEdit" id="balanceNoEdit"/></td>
										</tr>
										-->
									</tbody>
					      		</table>
							</div>
							</form>
					<#--工具栏   -->
							 <div class="easyui-layout" data-options="fit:true,border:false">
					          <div data-options="region:'north',border:false">
					    	  <@p.toolbar id="backSplitEditDataEditorBar" listData=[
								{"id":"addBackSplitEditBtn","title":"新增行","iconCls":"icon-add-dtl","action":"backSplitEditDataEditor.insertRow()","type":0},
								{"id":"delBackSplitEditBtn","title":"删除行","iconCls":"icon-del-dtl","action":"backSplitEditDataEditor.del()","type":0},
								{"id":"saveBackSplitEditBtn","title":"保存","iconCls":"icon-del-dtl","action":"backSplitEditDataEditor.saveCheck()","type":0},
								{"id":"top_btn_batchadd","title":"生成明细","iconCls":"icon-build-some","action":"billBacksectionSplit.insertRowEditDtl()","type":0}
							 ]/>
					      </div> 
	                      <div data-options="region:'center',border:false" id="backSplitEditDataDiv">
							<@p.datagrid id="backSplitEditDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
					              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
					              checkOnSelect="true" pageSize="20" showFooter="true"
						          rownumbers="true" singleSelect="false" divToolbar="#backSplitEditDatatb"
						         columnsJsonList="[
								{field : 't',checkbox:true,width : 30},
								{field : 'id',hidden : 'true',align:'center'},
								 {field : 'backsectionNo',hidden : 'true', title : '回款单号', width : 150, align : 'left'},
								{field : 'organNo', hidden:true,title : '管理城市', align:'left',width : 60, 
									editor:{type:'hiddenbox',options:{id:'organNo_', name:'organNo'}}
								},
								{field : 'organName', title : '管理城市', width : 60, align:'left',
									editor:{type:'searchboxname',options:{readonly:true,id:'organName_',name:'organName',width:'180px'}}
								},								
								{field : 'shortName', title : '店铺名称', align:'left',width : 150,halign:'center', 
							editor:{
								type:'shop',
								options:{
									id:'shortName_',
									name:'shortName',
									inputNoField:'shopNo_',
									required:true,
									relationData:true,
									callback : function(row) {
										if(row) {
					       					$('#shortName_').val(row.shortName);
			       					$('#shopNo_').val(row.shopNo);
		       						$.ajax({
		       			                url: BasePath + '/shop/initSubInfo?'+jQuery.param({shopNo:row.shopNo}),
		       			                async:false,
		       			                cache: true
		       			            }).then(function(result) {
		       			                $('#shopNo_').val(result.shopNo);	
		       			                $('#companyNo_').val('');
									    $('#companyName_').val('');									
										$('#bsgroupsNo_').val(result.bsgroupsNo);
										$('#bsgroupsName_').val(result.bsgroupsName);
										$('#mallNo_').val(result.mallNo);
										$('#mallName_').val(result.mallName);
										$('#organNo_').val(result.organNo);
										$('#organName_').val(result.organName);
										$('#brandNo_').val(result.brandNo);
										$('#brandName_').combogrid('setValue',result.brandName);
										$('#companyNo_1').val(result.companyNo);
								 	    $('#companyName_1').val(result.companyName);
										if(result.companyNo.substring(0,1) == 'K'){
											$(deductAmount).numberbox('disable');
											$(deductAmount).numberbox('clear');											
										}
										else {
											$(deductAmount).numberbox('enable');
										}
		       			            });
				                } else {
				                	$('#shortName_').combogrid('clear');
		                	   	   	$('#shopNo_').val('');
				                }
							}
						}
					}
				},
				{field : 'shopNo', hidden:true, title : '店铺编码', align:'left',width : 80, notexport:true,editor:{type:'hiddenbox',options:{id:'shopNo_',name:'shopNo'}}}, 
				
				{field : 'brandNo',hidden : true,title : '品牌', align:'left',width : 80, notexport:true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'brandNo_',
												name:'brandNo'
											}
										}
				                  },
				{field : 'brandName', title : '品牌部名称', width : 80,halign:'center',
					editor:{
						type:'combogrid',
						options:{
							id:'brandName_',
							name:'brandName',
							inputNoField:'brandNo_',
							idField:'brandName',
							textField:'brandName',
							noField:'brandNo',
							columns:[[
								{field : 'brandNo',title : '品牌部编码',width : 100, halign : 'center', align : 'left'},
								{field : 'brandName',title : '品牌部名称',width : 150, halign : 'center', align : 'left'}
							]],
							onShowPanel:function(){
								if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
									var url = BasePath+'/shop_brand/list.json?' 
				 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
				 	            	$(this).combogrid('grid').datagrid('options').url= url;
				 	            	$(this).combogrid('grid').datagrid('load');
								}
								else {
									showWarn('请先选择店铺！');
									return;
													}
													
												},
											}
										}
									},	
								{field : 'month', title : '结算月', align:'center',width : 80,
					   editor:{
						type:'combogrid',
						options:{
							required:true,
							id:'month_',
							name:'month_',
							inputWidth:60,
							idField:'month',
							textField:'month',
							noField:'month_',
							inputNoField:'month',
							url:'',
							paramMap:[{name:'shopNo', field:'shopNo_'}],
							columns:[[ 
							    {field : 'month',title : '结算月',width : 80, align:'center',halign:'center'},
								{field:'balanceStartDate',title:'起始日期',width:100,align:'center',halign:'center'},
								{field:'balanceEndDate',title:'终止日期',width:100,align:'center',halign:'center'},
							]],
							onShowPanel:function(){
								if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
									var url = BasePath+'/shop_balance_date/list.json?' 
				 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
				 	            	$(this).combogrid('grid').datagrid('options').url= url;
				 	            	$(this).combogrid('grid').datagrid('load');
								}
								else {
									showWarn('请先选择店铺！');
									return;
								}
								
							},
							callback:function(data){
								if(data){
									$('#month_').val(data.month);
									$('#balanceStartDate').val(data.balanceStartDate);
									$('#balanceEndDate').val(data.balanceEndDate);
									$('#companyNo_').val(data.companyNo);
									$('#companyName_').val(data.companyName);
								}
							}
						}
					}
				},
								{field : 'balanceStartDate',title : '结算起始日期', align:'center',width : 90, 
									editor:{
											type:'searchboxname',
											options:{id:'balanceStartDate',name:'balanceStartDate',readonly:true}
									}
								},
								{field : 'balanceEndDate', title : '结算终止日期', align:'center',width : 90, 
									editor:{
											type:'searchboxname',
											options:{id:'balanceEndDate',name:'balanceEndDate',readonly:true}
									}
								}, 
								{field : 'balanceNo', title : '结算单号', width : 150,
									editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			id:'balanceNo_',
				                  			name:'balanceNo',
				                  			panelWidth:550,
				                  			idField:'balanceNo',
				                  			textField:'balanceNo',
				                  			pagination:false,
				                  			columns:[[
				                  				{field : 'balanceNo',title : '结算单号',width : 150, halign : 'center', align : 'left'},
				                  				{field : 'shortName',title : '店铺名称',width : 150, halign : 'center', align : 'left'},
							                	{field : 'month',title : '结算月',width : 80, halign : 'center', align : 'center'},
							                	{field : 'billingAmount',title : '开票金额',width : 80, halign : 'center', align : 'right'},
							                	{field : 'ticketDeductionAmount',title : '票后帐扣费用',width : 80, halign : 'center', align : 'right'},
							                	{field : 'systemSalesAmount',title : '销售收入',width : 80, halign : 'center', align : 'center'},
							                	{field : 'invoiceApplyDate',title : '申请开票日期',width : 80, halign : 'center', align : 'center'}
							 	            ]],
							 	            onShowPanel:function() {
							 	            	var url = BasePath + '/mall_shopbalance/shopbalance_list_main.json?'
							 	            	+ jQuery.param({shopNo:$('#shopNo_').val(),brandNos:$('#brandNo_').val(),month:$('#month_shopNo_').val()});							 	            	
							 	            	$(this).combogrid('grid').datagrid('options').url= url;
							 	            	$(this).combogrid('grid').datagrid('load');				 	            		
							 	            },							 	        								
				                  			callback : function(rowData) {
				                  				var rows = $('#backSplitEditDataGrid').datagrid('getRows');
												var flag = true;
												$.each(rows,function(index,row){
													if(row.balanceNo == rowData.balanceNo){
														$('#balanceNo_').combogrid('clear');
														showWarn('结算单号已存在，请勿重复添加');
														flag = false;
													}
												});
												if(flag){
								 	            	$('#shopNo_').val(rowData.shopNo);
								 	            	$('#shortName_').val(rowData.shortName);
								 	            	$('#month_').val(rowData.month);
								 	            	$('#billingAmount_').val(rowData.billingAmount);
								 	            	$('#ticketDeductionAmount_').val(rowData.ticketDeductionAmount);
								 	            	$('#receiveAmount_').val(rowData.receiveAmount);
								 	            	$('#alreadyReceiveAmount_').val(rowData.alreadyReceiveAmount);
								 	            	$('#notReceiveAmount_').val(rowData.notReceiveAmount);
								 	            	$('#thePaymentAmount_').numberbox('setValue', (rowData.receiveAmount - rowData.alreadyReceiveAmount).toFixed(2));
								 	            	$('#diffAmount_').val((0).toFixed(2));
								 	            	
								 	            	$('#mallNo_').val(rowData.mallNo);
								 	            	$('#mallName_').val(rowData.mallName);
								 	            	$('#bsgroupsNo_').val(rowData.bsgroupsNo);
								 	            	$('#bsgroupsName_').val(rowData.bsgroupsName);
								 	            	$('#organNo_').val(rowData.organNo);
													$('#organName_').val(rowData.organName);
								 	            	$('#companyNo_1').val(rowData.companyNo);
								 	            	$('#companyName_1').val(rowData.companyName);
								 	            	
								 	            	$('#thePaymentAmount_').focus();
								 	            	$('#thePaymentAmount_').select();
							 	            	}
							 	            }
				                  		}
				                  	}
								},
								{field : 'systemSalesAmount',title : '销售收入',width : 80, halign : 'center', align : 'center',
								  	editor:{type:'fasnumberbox',options:{precision:2, validType:['maxLength[12]'],id:'systemSalesAmount_',name:'systemSalesAmount'}}
								},
								{field : 'billingAmount', title : '开票金额', align:'right',width : 80, 
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'billingAmount_',name:'billingAmount'}}
								},
								{field : 'invoiceApplyDate',title : '申请开票日期',width : 90,align:'left',
								     editor:{type:'datebox',options:{required:false}}},
								{field : 'ticketDeductionAmount',title : '票后帐扣费用',width : 90,align:'right',
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'ticketDeductionAmount_',name:'ticketDeductionAmount'}}
								},
								{field : 'receiveAmount',hidden:true,title : '应收款',width : 90,align:'right',
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'receiveAmount_',name:'receiveAmount'}}
								},
								{field : 'thePaymentAmount',title : '本次回款',width : 90,align:'right',
									editor:{type:'fasnumberbox',options:{required:true,precision:2, validType:['maxLength[12]'],id:'thePaymentAmount_',name:'thePaymentAmount'}}
								},								
								{field : 'bankBackAmount',title : '银行回款金额',width : 80, halign : 'center', align : 'center',
								  editor:{type:'fasnumberbox',options:{precision:2, validType:['maxLength[12]'],id:'bankBackAmount_',name:'bankBackAmount'}}
								},
								{field : 'bankBackDate',title : '银行回款日期',width : 90,align:'left',
									editor:{type:'datebox',options:{required:false}}},
									
								{field : 'alreadyReceiveAmount',title : '前期累计回款',width : 90,align:'right',hidden:true,
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'alreadyReceiveAmount_',name:'alreadyReceiveAmount'}}
								},
								{field : 'notReceiveAmount', hidden:true,title : '未回款',width : 90,align:'right',
									editor:{type:'readonlybox',options:{id:'notReceiveAmount_', name:'notReceiveAmount'}}
								},								
								{field : 'diffAmount',hidden:true,title : '应收款余额',width : 90,align:'right',
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'diffAmount_',name:'diffAmount'}}
								},								
								{field : 'remark',title : '备注',width : 150,align:'left',
									editor:{type:'validatebox'}
								},
								{field : 'diffReason',title : '差异原因',width : 150,align:'left',
									editor:{type:'validatebox'}
								},
								{field : 'mallNo', hidden:true,title : '商场编码', width : 160, align:'left',
									editor:{type:'hiddenbox',options:{id:'mallNo_', name:'mallNo'}}
								},
								{field : 'mallName', title : '商场名称', width : 150,align:'left', 
									editor:{type:'searchboxname',options:{readonly:true,id:'mallName_',name:'mallName',width:'180px'}}
								},
								{field : 'bsgroupsNo',hidden:true, title : '商业集团编码', align:'left',width : 80, 
									editor:{type:'hiddenbox',options:{id:'bsgroupsNo_',name:'bsgroupsNo'}}
								},
								{field : 'bsgroupsName', title : '商业集团', align:'left',width : 90, 
									editor:{type:'searchboxname',options:{id:'bsgroupsName_',name:'bsgroupsName',readonly:true}}
								},
								{field : 'companyNo',hidden:true, title : '公司编码', align:'left',width : 80, 
									editor:{type:'hiddenbox',options:{id:'companyNo_1',name:'companyNo'}}
								},
								{field : 'companyName', title : '公司', align:'left',width : 180, 
									editor:{type:'searchboxname',options:{id:'companyName_1',name:'companyName',readonly:true,width:'180px'}}
								}
								
							]"
							jsonExtend='{   
								onDblClickRow:function(rowIndex, rowData){
									//双击方法   
									billBacksectionSplit.editBckSplitRow(rowIndex,rowData);
								}
							}'
						/>
						<div data-options="region:'south',border:false">
							<#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
						</div>
					</div>
				   </div>			         
				     </div>			     
				</div>		        
			</div>
		</div>
		
		 <div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
				     <form name="dataForm" id="dataForm" method="post"  >
				     	<div>
							 <table class="form-tb">	                    	   
	                            <tbody>
	                               <tr >
		                             <span class="ui-color-red" align:'center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		                           </tr>
								   <th align="right" width="110"><span class="ui-color-red">*</span>公司名称：</th>
								      	<td align="left" width="140">
									  	<input class="ipt easyui-validate easyui-company" data-options="inputNoField:'companyNo_batchAdd',inputNameField:'companyName_batchAdd',required:true" name="companyName_batchAdd" id="companyName_batchAdd"/>
										<input type="hidden" name="companyNo_batchAdd" id="companyNo_batchAdd"/>
								        </td>								       
								    <tr>
								        <th><span class="ui-color-red"></span>管理城市：</th>
		                                <td>
		                                	<input class="ipt easyui-validate  easyui-organ" name="organName_batchAdd" id="organName_batchAdd"  data-options="inputNoField:'organNo_batchAdd',inputNameField:'organName_batchAdd'" />
								            <input type="hidden" name="organNo_batchAdd" id="organNo_batchAdd"/>
		                               </td>  
								   </tr>	
								   <tr>
									   	<th>商业集团：</th>
									    <td>
										 <input class="ipt easyui-bsgroups" data-options="inputNoField:'bsgroupsNo_batchAdd',inputNameField:'bsgroupsName_batchAdd',required:false"  name="bsgroupsName_batchAdd" id="bsgroupsName_batchAdd"/>
								         <input type="hidden" name="bsgroupsNo_batchAdd" id="bsgroupsNo_batchAdd"/>
									    </td>
								   </tr>
								    <tr>
								        <th>商场：</th>
		                                <td>
		                                 <input class="easyui-validatebox ipt easyui-mall" data-options="inputNoField:'mallNo_batchAdd',inputNameField:'mallName_batchAdd',required:false"  name="mallName_batchAdd" id="mallName_batchAdd"/>
								         <input type="hidden" name="mallNo_batchAdd" id="mallNo_batchAdd"/>
		                                </td>
								   </tr>							   								    
		                           <tr>
		                           <th><span class="ui-color-red">*</span>结算月：</th>
								   <td>
									    <input class="easyui-datebox ipt"  name="month_batchAdd" id="month_batchAdd" style="width:80px;" data-options="inputNoField:'month_batchAdd',inputNameField:'month_batchAdd',required:true,dateFmt:'yyyyMM'"  />
								   </td>  
								   </tr>
								    <tr>
								    <td align="right" width="110">店铺：</td>
									<td align="left" width="140"><input class="ipt easyui-shop"  data-options="inputNoField:'shopNo_batchAdd',inputNameField:'shopName_batchAdd',multiple:true,required:false"   name="shopName_batchAdd" id="shopName_batchAdd" style="width:300px;"/>
									<input type="hidden" name="shopNo_batchAdd" id="shopNo_batchAdd"/>
									</tr>								  									
								 </tbody>
							 </table>
						</div>
					 
			   </div>
			   
					 	<div data-options="region:'south',border:false">
		                	<#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
		             	</div>
		          </div>
		       </div>
		    </div>
		</div>
		</div>
	</div>
	
	</div>
</body>
</html>