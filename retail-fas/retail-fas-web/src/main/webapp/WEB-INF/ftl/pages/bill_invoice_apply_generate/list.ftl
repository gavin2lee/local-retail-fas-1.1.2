<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>生成开票申请</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/generate_invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/bill_invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/iptSearch.js?version=${version}"></script>
</head>
<body>
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'结算单开票申请'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	 <@p.toolbar id="toolbar" listData=[
							 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
				             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
				             {"id":"btn-add","title":"生成开票申请","iconCls":"icon-add","action":"dialog.generateBalanceInvoice()", "type":0}
				           ]
						/>
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="searchForm" name="searchForm"  method="post">
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
		                                    	<th>结算卖方：</th>
						       		 			<td>
						       		 				<input class="ipt easyui-company"  name="companyName" id="salerName" 
						       		 					data-options="inputNoField:'salerNo',inputNameField:'salerName',inputWidth:150,required:true"/>
													<input type="hidden"  name="companyNo" id="salerNo"/>	
						       		 			</td>
		                                    	<th>结算单类型： </th>
	                     						<td><input class="easyui-combobox ipt" name="balanceType" id="balanceType" data-options="required:true"/></td>
												<th>结算单号：</th>
			       		 						<td><input class="easyui-validatebox ipt" name="balanceNo" id="balanceNo"/></td>
		                                    	<th>结算起始日期：</th>
											    <td><input class="easyui-datebox" name="balanceStartDate" readonly="true" id="startDateCond" data-options="maxDate:'endDateCond'" /></td>
												<th>结算结束日期：</th>
											    <td ><input class="easyui-datebox" name="balanceEndDate" readonly="true" id="endDateCond" data-options="minDate:'startDateCond'" /></td>
		                                    </tr>
		                                </tbody>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                <div data-options="region:'center',border:false" style="height:350px;">
		                	 <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" title=""
						              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
							           rownumbers="true" singleSelect="false"  
							           columnsJsonList="[
							                    {field : 't', checkbox:true, width : 30, notexport:true},
							                    {field : 'billNo',title : '结算单号',width : 150,align:'left',halign:'center'},
												{field : 'balanceTypeName',title : '单据类型',width : 120,align:'left',halign:'center'},
												{field : 'statusName',title : '单据状态',width : 80,align:'left',halign:'center'},
												{field : 'balanceDate',title : '单据日期',width : 80,align:'left',halign:'center'},
												{field : 'buyerName',title : '结算买方',width : 200,align:'left',halign:'center'},
												{field : 'salerName',title : '结算卖方',width : 200,align:'left',halign:'center'},
												{field : 'balanceMonth',title : '结算月',width : 80,align:'left',halign:'center'},
												{field : 'balanceStartDate',title : '结算起始日期',width : 100,align:'left',halign:'center'},
												{field : 'balanceEndDate',title : '结算终止日期',width : 100,align:'left',halign:'center'},
												{field : 'brandName',title : '品牌',width : 80,align:'left',halign:'center'},
												{field : 'brandUnitName',title : '品牌部',width : 80,align:'left',halign:'center'},
												{field : 'amount',title : '金额',width : 80,align:'right',halign:'center'},
												{field : 'createUser',title : '创建人',width : 80,align:'left',halign:'center'},
												{field : 'createTime',title : '创建日期',width : 150,align:'left',halign:'center',sortField:'create_time',sortable:true}
							              ]" 
								          jsonExtend='{
					                           onDblClickRow:function(rowIndex, rowData){
							                   }
						                 }'
				                 />
		                </div>
		              </div>
             	</div>
            </div>
        </div>
</div>

<div id="myFormPanel" class="easyui-dialog" data-options="closed:true" > 
	<form  id="dataForm" method="post">
		<input type="hidden" id="id" name="id" />
		<input type="hidden" name="invoiceInfoStatus" id="invoiceInfoStatus" />
		<table  class="form-tb">
			<col width="100" />
		    <col />
		    <tbody>
			<tr>
				<th><span class="ui-color-red">*</span>客户名称：</th>
				<td width="200px;" height="40px;">
					<input class="easyui-customerInvoiceInfo ipt"  name="buyerName" id="buyerName" 
					data-options="required:true,inputNoField:'buyerNo', 
					inputNameField:'buyerName',inputWidth:150,callback: function(data){$('#invoiceInfoStatus').val(data.status);}"/>
			    </td>
				<th><span class="ui-color-red">*</span>客户编码：</th>
				<td width="200px;" height="40px;">
					<input name="buyerNo" id="buyerNo" />
			    </td>
			</tr>
			</tbody>
		</table>
	</form>
</div>
</body>
</html>