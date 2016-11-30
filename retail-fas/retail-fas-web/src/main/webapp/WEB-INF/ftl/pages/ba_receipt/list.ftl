<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>验收单列表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/ba_receipt/baReceipt.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"baReceipt.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"baReceipt.clear()", "type":0},
             {"id":"btn-price","title":"更新地区价","iconCls":"icon-save","action":"baReceipt.updateRate()", "type":139},
             {"id":"btn-price","title":"更新牌价","iconCls":"icon-save","action":"baReceipt.updateTagPrice()", "type":153},
             {"id":"btn-receipt","title":"生成结算单","iconCls":"icon-save","action":"baReceipt.generateBalance()", "type":137},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"baReceipt.doExport()","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="searchType" id="searchType"/>
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company"  /><input type="hidden" name="multiCompanyNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier" /><input type="hidden" name="multiSupplierNo"></td>
									<th>验收日期：</th>
						    		<td ><input class="easyui-datebox ipt" name="receiveDateStart" id="receiveDateStart" defaultValue="startDate" data-options="maxDate:'receiveDateEnd',required:true"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" name="receiveDateEnd" id="receiveDateEnd" defaultValue="endDate" data-options="minDate:'receiveDateStart',required:true"/></td>
								</tr>
								<tr>
									<th>
				                                                                  款号：
				                    </th>
				                  	<td>
				                        <input class="easyui-styleNo ipt" name="styleName" id="styleNameCon"
				                               data-options="inputNoField:'styleNo',inputNameField:'styleNoCon',isDefaultData : false"/>
				                        <input type="hidden" name="styleNo" id="styleNoCon"/>
				                    </td>
									<th>单据编码： </th><td><input class="ipt"  name="originalBillNo"/></td>	
									<th>采购订单号： </th><td><input class="ipt"  name="orderNo"  /></td>	
									<th>结算单号： </th><td><input class="ipt"  name="balanceNo"  /></td>	
								</tr>
								<tr>
									<th>
									单价为零：
									</th>
									<td>
										<input type="checkbox" name="costZero" value="1"   />
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
        			<div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
        				<div title="单据汇总" data-options="selected : true,closable : false" >
            	 			<@p.datagrid id="sumDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""   rownumbers="true" showFooter="true"
				              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = ""  pageSize="20" 
					           columnsJsonList="[
					                  {field : 'salerName', title : '供应商', width : 180},
				 	                  {field : 'originalBillNo', title : '单据编码', width :180,align:'center'},	
				 	                  {field : 'brandUnitNo',hidden:'true'},
				 	                  {field : 'brandUnitName',hidden:'true'},
				 	                  {field : 'categoryNo',hidden:'true'},
				 	                  {field : 'categoryName',hidden:'true'},		
				 	                  {field : 'sendDate', title : '发货日期', width : 80,align:'center'},	
					                  {field : 'receiveDate', title : '验收日期', width : 90,align:'center'},
					                  {field : 'receiveQty', title : '数量', width : 100,align:'right',halign:'center'},	
					                  {field : 'purchaseAmount', title : '采购金额', width : 100,align:'right',halign:'center'},
									  {field : 'currencyName', title : '币别', width : 100,align:'right',halign:'center'},
					                  {field : 'standardCurrencyName', title : '本位币', width : 100,align:'right',halign:'center'},	
					                  {field : 'exchangeRate', title : '汇率', width : 150,align:'left',halign:'center'},	
					                  {field : 'totalAmount', title : '本位币金额', width : 100,halign:'center'},
					                  {field : 'buyerName',title : '公司名称',width : 180,align:'left',halign:'center'},
					                  {field : 'orderUnitName', title : '货管单位', width : 80,align:'center'},	
					                  {field : 'organName', title : '管理城市', width : 100,halign:'center'},
					                  {field : 'orderNo',title : '采购订单号',width : 120,align:'left',halign:'center'},
					                  {field : 'balanceNo', title : '结算单号', width : 150,align:'center'}
					                 ]" 
                			 />
            	 		</div>
        				<div title="单据明细" data-options="selected : false,closable : false" >
            	 			<@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					            columnsJsonList="[
					            		  {field : 't', checkbox:true, width : 30, notexport:true},
						                  {field : 'salerName', title : '供应商', width : 180},
					 	                  {field : 'originalBillNo', title : '单据编码', width : 180,align:'center'},	
					 	                  {field : 'sendDate', title : '发货日期', width : 80,align:'center'},	
						                  {field : 'receiveDate', title : '验收日期', width : 90,align:'center'},
						                  {field : 'brandName', title : '品牌', width : 80,align:'center'},	
					 	                  {field : 'oneLevelCategoryName', title : '大类', width : 80,align:'center'},
					 	                  {field : 'styleNo', title : '款号', width : 120,align:'center'},	
						                  {field : 'itemCode', title : '商品编码', width : 120,align:'center'},
					                      {field : 'itemName', title : '商品名称', width : 120,align:'center'},	
					 	                  {field : 'tagPrice', title : '牌价', width : 100,align:'center'},	
						                  {field : 'receiveQty', title : '数量', width : 100,align:'right',halign:'center'},	
						                  {field : 'cost', title : '采购价', width : 100,align:'center'},
						                  {field : 'purchaseAmount', title : '采购金额', width : 100,align:'right',halign:'center'},
										  {field : 'currencyName', title : '币别', width : 100,align:'right',halign:'center'},
						                  {field : 'standardCurrencyName', title : '本位币', width : 100,align:'right',halign:'center'},	
						                  {field : 'exchangeRate', title : '汇率', width : 150,align:'left',halign:'center'},	
						                  {field : 'totalAmount', title : '本位币金额', width : 100,halign:'center'},
						                  {field : 'buyerName',title : '公司',width : 180,align:'left',halign:'center'},
						                  {field : 'orderUnitName', title : '货管单位', width : 80,align:'center'},	
						                  {field : 'organName', title : '管理城市', width : 100,halign:'center'},
						                  {field : 'orderNo',title : '采购订单号',width : 120,align:'left',halign:'center'},
						                  {field : 'twoLevelCategoryName',title : '二级大类',width : 120,align:'left',halign:'center'},
						                  {field : 'categoryName', title : '三级大类', width : 120,align:'center'},
						                  {field : 'balanceNo', title : '结算单号', width : 150,align:'center'},
						                  {field : 'remark', title : '备注', width : 120,align:'center'},
						                  {field : 'receiveStoreName', title : '到货仓', width : 120,align:'center'},
						                  {field : 'itemNo',hidden:'true'},
						                  {field : 'brandUnitName',hidden:'true'},
						                  {field : 'brandUnitNo',hidden:'true'}
										]" 
		                 	/>	
            	 		</div>
            		</div>
			</div>
	 	</div>
	</div>
	   <div id="updateTagPricePanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="updateTagPriceForm" id="updateTagPriceForm" method="post"  class="pd10">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:650px;"  title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<col width="120"/>
						<col/>
						<col width="120"/>
						<col/>
					   <tr height="40">
				        <th>
				        	<span class="ui-color-red">*</span>公司：
				        </th>
			        	<td><input class="ipt" multiSearch="company" data-options="required:true" />
			        		<input type="hidden" name="multiCompanyNo">
			        	</td>
			           </tr>
			           <tr height="40">
			        	<th><span class="ui-color-red">*</span>验收日期：</th>
						<td>
			  				<input class="easyui-datebox ipt" name="receiveDateStart" id="receiveDateStart1" defaultValue="startDate" data-options="maxDate:'receiveDateEnd1',required:true"/>
			  			</td>
						<td> 
							<input class="easyui-datebox ipt" name="receiveDateEnd" id="receiveDateEnd1" defaultValue="endDate" data-options="minDate:'receiveDateStart1',required:true"/>
						</td>
					   </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
</body>
</html>