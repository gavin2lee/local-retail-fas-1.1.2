<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>客户保证金及预收款初始化</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<#-- 
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_ajax.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_prepay_warn/wholesale_prepay_warn.js?version=${version}"></script>
-->
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasDialogController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasEditorController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/list.json?isHq=${isHq}" 
					  export_url="/do_fas_export"
					  export_title="客户保证金预收款预警信息"
					  export_type="common"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="700" 
					  dialog_height="600"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
	     	 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_fasDialogController.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"_wholesalePrepayWarn.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"_fasDialogController.exportExcel()","type":4}
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
							<col width="80" />
							<col />
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
									<th>公司：</th>
		                            <td>
	                                	<input class="easyui-company ipt" name="companyName" id="companyNameCondition" data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
	                                	<input type="hidden" name="companyNo" id="companyNoCondition"/>
                                	</td>
									</td>
		                            <th>客户：</th>
		                            <td>
	                                	<input class="easyui-wholesale_zone_customer ipt" name="customerName" id="customerNameCondition" data-options="inputWidth:160,inputNoField:'customerNoCondition',inputNameField:'customerNameCondition'"/>
	                                	<input type="hidden" name="customerNo" id="customerNoCondition"/>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
			           rownumbers="true" singleSelect="true"
			           columnsJsonList="[
			                  {field : 'companyName', title : '公司名称', width : 200, align : 'left'},
			                  {field : 'customerNo', title : '客户编码', width : 100, align : 'left'},
			                  {field : 'customerName', title : '客户名称', width : 200, align : 'left'},
			                  {field:'marginAmount',title : '合同保证金',width : 90, align : 'right'},
		  			  		  {field:'recedMarginAmount',title : '保证金余额',width : 90, align : 'right'},
				  			  {field:'marginFullText',title : '保证金是否足额',width : 100, align : 'center'},
				  			  {field:'prePayment',title : '预收款',width : 90, align : 'right'},
				  			  {field:'preOrderNo',title : '预收订单号',width : 100, align : 'left'},
				  			  {field:'orderAmount',title : '订单金额',width : 100, align : 'right'},
				  			  {field:'sendOutAmount',title : '出库金额',width : 100, align : 'right'},
				  			  {field:'preOrderFullText',title : ' 订货预收是否足额',width : 120, align : 'center'},
				  			  {field:'preSendOutFullText',title : '发货预收是否足额',width : 120, align : 'center'},
				  			  {field:'prePaymentOver',title : '预收款差额',width : 90, align : 'right'}
			           ]" 
                 />
			</div>
	 	</div>
	</div>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_prepay_warn/WholesalePrepayWarn.js?version=${version}"></script>
</body>
</html>