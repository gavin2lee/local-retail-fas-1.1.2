<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>返利生成</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/return_profit/returnProfit.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/return_profit/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/return_profit/do_fas_export"
					  export_title="返利生成导出"
					  primary_key="id"/>
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
             {"id":"btn-generateReturnProfit","title":"生成返利","iconCls":"icon-ok","action":"dialog.generateReturnProfit()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
									<th><span class="ui-color-red">*</span>供应商名称：</th>
				                    <td>
				                    	<input class="easyui-supplier ipt"  name="supplierName" id="supplierName"  data-options="inputNoField:'supplierNo',inputNameField:'supplierName',inputWidth:230,required:true"/>
				                   		<input type="hidden" name="supplierNo" id="supplierNo"/>
				                   	</td>
				        		    <th>商品：</th>
							 		<td>
							 			<input class="ipt easyui-item" name="itemCode" id="itemCode"
							 			 data-options="inputWidth:160,inputNoField:'itemNo', inputCodeField:'itemCode'"/>
							  			<input type="hidden" name="itemNo" id="itemNo"/>
							  		</td>
							 		<th>收方地区：</th>
									<td>
										<input class="ipt easyui-zonebox" name="zoneNo" id="zoneNo"  />
									</td>
							 		<th>地区公司：</th>
							 		<td>
								 		<input class="ipt easyui-company" name="buyerName" id="buyerName" 
								 		data-options="inputWidth:160,inputNoField:'buyerNo',inputNameField:'buyerName'"/>
				      					<input type="hidden" name="buyerNo" id="buyerNo"/>
			      					</td>
							 	</tr>
							 	<tr>
							 		<th align="right" width="80">发出日期：</th>
						    		<td align="left">
						    			<input class="easyui-datebox ipt" style="width: 100px;"  name="sendDateStart" id="sendDateStart" data-options="maxDate:'sendDateEnd'"/>
										 - <input class="easyui-datebox ipt" style="width: 100px;" name="sendDateEnd" id="sendDateEnd" data-options="minDate:'sendDateStart'"/>
						    		</td>
						    		<th>订货类型：</th>
						    		<td><input class="easyui-combobox" name="orderType" id="orderType" data-options="valueField:'value',textField:'text',data:datasource.orderTypes" /></td>
						    		<th>机构：</th>
							 		<td>
								 		<input class="ipt easyui-store" name="storeName" id="storeName" 
								 		data-options="inputWidth:130,inputNoField:'storeNo',inputNameField:'storeName'"/>
				      					<input type="hidden" name="storeNo" id="storeNo"/>
			      					</td>
						    		<th>货管单位：</th>
							 		<td>
								 		<input class="ipt easyui-orderUnit" name="orderUnitName" id="orderUnitName" 
								 		data-options="inputWidth:160,inputNoField:'orderUnitNo',inputNameField:'orderUnitName'"/>
				      					<input type="hidden" name="orderUnitNo" id="orderUnitNo"/>
			      					</td>
							 	</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn=""   
		              isHasToolBar="false" onClickRowEdit="false" pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
		           			  {field : 't',checkbox:true,width : 30,align:'center',notexport:true},
		                  	  {field : 'id',hidden:true,notexport:true},
			                  {field : 'supplierName',title : '供应商',width: 150,align:'left',align:'center'},
			                  {field : 'businessBillTypeName',title : '单据类型',width: 80,align:'left',align:'center'},
			                  {field : 'businessBillNo',title : '单据编码',width: 135,align:'left',align:'center'},
			                  {field : 'itemNo',title : '商品编号',hidden:true,width: 135,align:'left',align:'center'},
			                  {field : 'itemCode',title : '商品编码',width: 135,align:'left',align:'center'},
			                  {field : 'itemName',title : '商品名称',width: 135,align:'left',align:'center'},
			                  {field : 'brandName',title : '品牌',width: 50,align:'left',align:'center'},
			                  {field : 'categoryName',title : '大类',width: 80,align:'left',align:'center'},
			                  {field : 'orderTypeName',title : '订货类型',width: 135,align:'left',align:'center'},
			                  {field : 'qty',title : '发出量',width: 100,align:'left',align:'center',exportType:'number'},
			                  {field : 'tagAmount',title : '牌价额',width: 100,align:'left',align:'center',exportType:'number'},
			                  {field : 'supplierDiscount1',title : '厂商折扣1',width: 135,align:'left',align:'center',exportType:'number'},
			                  {field : 'supplierDiscount1',title : '厂商折扣2',width: 135,align:'left',align:'center',exportType:'number'},
			                  {field : 'amount',title : '结算金额',width: 100,align:'left',align:'center',exportType:'number'},
			                  {field : 'balanceStatusStr',title : '结算状态',width: 100,align:'left',align:'center'},
			                  {field : 'balanceNo',title : '结算单号',width: 150,align:'left',align:'center'},
			                  {field : 'orderNo',title : '订单号',width: 135,align:'left',align:'center'},
			                  {field : 'billDate',title : '发出日期',width: 100,align:'left',align:'center'},
			                  {field : 'dueDate',title : '接收日',width: 135,align:'left',align:'center'},
			                  {field : 'buyerName',title : '收方公司',width: 200,align:'left',align:'center'},
			                  {field : 'orderUnitName',title : '货管单位',width: 100,align:'left',align:'center'},
			                  {field : 'storeName',title : '机构',width: 135,align:'left',align:'center'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
	
	<!-- 生成返利-->
	<div id="genarateFormPanel" class="easyui-dialog" data-options="closed:true">
		<form name="genarateDataForm" id="genarateDataForm" method="post"  class="pd10">
			<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:480px;"  title="供应商" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<col width="80"/>
						<col/>
						<col width="80"/>
						<col/>
						<tr height="40">
							<th><span class="ui-color-red">*</span>返利率(%)：</th>
							<td><input class="easyui-numberbox" min="0" max="100" missingMessage="请输入0~100之间的整数" data-options="required:true" name="returnProfitRate" id="returnProfitRate" placeholder="0.00"/></td>
							<th><span class="ui-color-red">*</span>返利基数：</th>
						    <td>
						    <input class="easyui-combobox" name="returnProfitType" id="returnProfitType" data-options="valueField:'value',textField:'text',data:datasource.returnProfitTypes,required:true" />
						    <input type="hidden" name="buyerNo" id="buyerNo"/>
						    </td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>
</html>