<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>收款单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasDialogController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>
</head>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<body class="easyui-layout">
	<@p.commonSetting search_url="/list.json?isHq=${isHq}" 
					  save_url="/insert" 
					  update_url="/update" 
					  del_url="/save"
					  export_url="/do_fas_export?isHq=${isHq}"
					  export_title="收款单"
					  export_type="common"
					  audit_url="/do_audit?auditVal=1"
					  anti_audit_url="/do_audit?auditVal=0"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="750" 
					  dialog_height="320"
					  primary_key="id"/>
	<div data-options="region:'north',border:false">
    	 <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_fasDialogController.search()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"_billPrePaymentNt.clear()","type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"_billPrePaymentNt.toAdd()", "type":1},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"_billPrePaymentNt.toUpdate()","type":2},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"_billPrePaymentNt.doDel()","type":3},
             {"id":"btn-audit","title":"审核","iconCls":"icon-aduit","action":"_billPrePaymentNt.doAudit()","type":31},
			 {"id":"btn-antiAudit","title":"导入","iconCls":"icon-import","action":"_billPrePaymentNt.doImport('收款单.xlsx','/bill_pre_payment_nt/do_import?isHq=${isHq}',importCallBack)","type":6},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"_fasDialogController.exportExcel()","type":4}
           ]
		/>
    </div>
	    <div data-options="region:'center',border:false">
	    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
	           <div data-options="region:'north',border:false">
	            <div class="search-div">
	                <#-- 主档信息  -->
	                <form id="searchForm" name="searchForm"  method="post">
	                	<input type="hidden" name="balanceType" value="7">
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
									<th>公司：</th>
		                            <td>
		                              <#if isHq==true>
								      	<input class="easyui-company ipt"  name="companyName" id="companyNameCondition" 
		                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
		                           		<input type="hidden" name="companyNo" id="companyNoCondition"/>
								      </#if>
								      <#if isHq==null || isHq==''>
								      	<input class="easyui-company ipt"  name="companyName" id="companyNameCondition" 
		                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160"/>
		                           		<input type="hidden" name="companyNo" id="companyNoCondition"/>
								      </#if>
								    </td>
		                            <th>客户 ：</th>
		                            <td>
		                            	<input class="easyui-wholesale_zone_customer ipt" id="multiCustomerName" data-options="multiple:'true',inputWidth:160,inputNoField:'multiCustomerNo',inputNameField:'multiCustomerName'"/> 
	                                	<input type="hidden" name="multiCustomerNo" id="multiCustomerNo"/>
									</td>
									<th>收款日期：</th>
	                                <td>
								    	<input style='width:100px' class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="paidDateStart" id="paidDateStart" defaultValue="currentMonthFirstDayDate" data-options="{maxDate:'paidDateEnd'}"/>
								    </td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input style='width:100px' class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="paidDateEnd" id="paidDateEnd" defaultValue="currentMonthLastDayDate" data-options="{minDate:'paidDateStart'}"/>
									</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;审核状态：</th>
		                            <td>
		                            	<input class="easyui-statusbox ipt"  name="auditStatus" id="auditStatus" style='width:100px'
		                            		data-options="data:[{'value':'0','text':'未审核'},{'value':'1','text':'已审核'}]"/>
		                            </td>
	                            </tr>
	                            <tr>
	                            	<th>收款单号：</th>
								    <td><input style='width:150px' class="ipt" name="billNo"/></td>
									<th>批发订单：</th>
								    <td><input style='width:150px' class="ipt" name="saleOrderNo"/></td>
								    <th>审核时间：</th>
									<td>
								    	<input style='width:100px' class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="auditDateStart" id="auditDateStart"  data-options="{maxDate:'auditDateEnd'}"/>
								    </td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td>
										<input style='width:100px' class="easyui-validatebox easyui-datebox readonly ipt" readonly="true" name="auditDateEnd" id="auditDateEnd"  data-options="{minDate:'auditDateStart'}"/>
									</td>
	                            </tr>
	                        </tbody>
	                    </table>
					 </form>
	            </div>
	        </div>
	        <div data-options="region:'center',border:false" style="height:350px;">
	        	 <@p.datagrid id="dataGridDiv"  pagination="true" rownumbers="true"
					isHasToolBar="false" height="502"  onClickRowEdit="false"  pageSize="10"
					columnsJsonList="[
						{field : 'ck',checkbox:true,notexport:true},
						{field : 'billNo',title : '单据编号',width : 150,align : 'left'},
						{field : 'companyName',title : '公司名称',width : 220,align : 'left'},
						{field : 'companyNo',title : '公司编码',width : 80,align : 'left'},
						{field : 'customerName',title : '客户名称',width : 220,align : 'left'},
						{field : 'customerNo',title : '客户编码',width : 80,align : 'left'},
						{field : 'paidTypeName',title : '收款类型',width : 100,align : 'right'},
						{field : 'saleOrderNo',title : '批发订单',width : 150,align : 'left'},
						{field : 'termName',title : '收款条款',width : 140,align : 'right'},
						{field : 'orderAmount',title : '订单金额',width : 100,align : 'right',exportType:'number'},
						{field : 'paidAmount',title : '实收金额',width : 80,align : 'right',exportType:'number'},
						{field : 'paidDate',title : '收款日期',width : 100,align : 'center'},
						{field : 'auditStatusName',title : '审核状态',width : 80,align : 'center'},
						{field : 'createUser',title : '制单人',width : 80,align : 'center'},
						{field : 'createTime',title : '制单时间',width : 140,align : 'center'},
						{field : 'auditTime',title : '审核时间',width : 140,align : 'center'},
						{field : 'remark', hidden:true, title : '备注信息',width : 140,align : 'center'}
					]" 
					jsonExtend='{onDblClickRow:function(rowIndex, rowData){
						_billPrePaymentNt.toUpdate(rowData);
				}}'/>
	        </div>
	      </div>
	</div>
	
 	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
	    <div class="easyui-layout" data-options="fit:true,border:false" >
		     <form name="dataForm" id="dataForm" method="post"  class="pd10">
		     	 <div data-options="region:'north',border:false,height:320" class="pd15">
					<div class="easyui-panel" data-options="title:'收款单',fieldset:true,fit:true,collapsible:false">
						<table cellpadding="1" cellspacing="1" class="form-tb">
						   <input type="hidden" name="id" id="id">
						   <tr>
                            	<th width="140" align='right'>单据编号 ：</th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="readonly ipt" readonly="readonly" name="billNo" id="billNo"/>
                                </td>
                                <th width="140" align='right'>单据状态 ：</th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="readonly ipt" readonly="readonly" name="auditStatusName" id="auditStatusName"/>
                                </td>
                           </tr>
						   <tr>
				   				<th width="110" align='right'>
                           			<span class="ui-color-red">*</span>公司：
                           		</th>
                            	<td width="140" align='left'>
                            	  <#if isHq==true>
							      	<input class="easyui-company ipt"  name="companyName" id="companyName" 
	                            	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160"/>
	                           		<input type="hidden" name="companyNo" id="companyNo"/>
							      </#if>
							      <#if isHq==null || isHq==''>
							      	<input class="easyui-company ipt"  name="companyName" id="companyName" 
	                            	data-options="required:true,queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNo',inputNameField:'companyName',inputWidth:160"/>
	                           		<input type="hidden" name="companyNo" id="companyNo"/>
							      </#if>
                            		<!-- <input class="easyui-company ipt" name="companyName" id="companyName" data-options="required:true,inputWidth:200,inputNoField:'companyNo',inputNameField:'companyName'" />
                                	<input type="hidden" name="companyNo" id="companyNo"/>
                                	-->
                                </td>
                                <th width="110" align='right'>
                                	<span class="ui-color-red">*</span>客户：
                                </th>
                            	<td width="140" align='left'>
                            		<input class="easyui-wholesale_zone_customer ipt" name="customerName" id="customerName" data-options="required:true,inputWidth:200,inputNoField:'customerNo',inputNameField:'customerName'"/>
                                	<input type="hidden"  name="customerNo" id="customerNo"/>
                                </td>
                           </tr>
                           <tr>
                           		<th width="140" align='right'>
                                	<span class="ui-color-red">*</span>收款类型：
                                </th>
                                <td width="140" align='left'>
                                	<input style='width:200px' class="easyui-combobox ipt" name="paidType" id="paidType" data-options="required:true,
                                	valueField: 'code',
									textField: 'name'
									"/>
                                </td>
                            	<th width="140" align='right'>
                                	<span class="ui-color-red">*</span>实收金额：
                                </th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="easyui-numberbox ipt" name="paidAmount" id="paidAmount" data-options="{precision:2}"/>
                                </td>
                            </tr>
                           <tr>
	                            <th width="140" align='right'>批发订单 ：</th>
	                            <td width="140" align='left'>
	                            	<input class="ipt"  name="saleOrderNo" id="saleOrderNo" data-options="required:true"/>
	                            	<input type="hidden" name="preOrderAmount" id="preOrderAmount"/>
	                            	<input type="hidden" name="preSendAmount" id="preSendAmount"/>
	                            </td>
	                            <th width="140" align='right'>
	                            	<span class="ui-color-red">*</span>收款日期：
	                            </th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="easyui-datebox easyui-validatebox  ipt"  id="paidDate" name="paidDate" defaultValue="currentDate" data-options="required:true"/>
                                </td>
                            </tr>
                            <tr>
                            	<th width="140" align='right'>订单金额 ：</th>
	                            <td width="140" align='left'>
	                            	<input style='width:190px' class="readonly ipt" readonly="readonly" name="orderAmount" id="orderAmount"/>
	                            </td>
	                            <th width="140" align='right'>收款条款：</th>
	                            <td width="140" align='left'>
	                            	<input style='width:190px' class="readonly ipt" readonly="readonly" name="termName" id="termName"/>
	                            	<input type="hidden" name="termNo" id="termNo"/>
	                            </td>
                            </tr>
                            <tr>
                            	<th width="140" align='right'>备注 ：</th>
                                <td colspan='3'><input style='width:550px' class="easyui-validatebox" data-options="validType:'maxLength[200]'" name="remark" id="remark" style="width:99%"/></td>
                            </tr>
						</table>
					</div>
				</div>
			 </form>	
	   </div>
   </div>
   	<div id="importPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="importDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'index', title : '行号', width : 30},
	           		 {field : 'pass',title:'是否导入成功',width:100,formatter: function(value,row,index){
							if (value == 0){
								return '否';
							}
							return '是'
						}
			         },
				         {field : 'errorInfo',title:'错误信息',width:200},
                     {field : 'validateObj.companyNo', title : '公司编码', width : 100},
                     {field : 'validateObj.customerNo', title : '客户编码', width : 100},
                     {field : 'validateObj.paidType', title : '收款类型', width : 100},	
	 				 {field : 'validateObj.saleOrderNo',title:'批发订单号',width:150},
	 				 {field : 'validateObj.paidAmount',title:'实收金额',width:100,align:'right',exportType:'number'},
	 				 {field : 'validateObj.paidDate',title:'收款时间',width:100,align:'right'}]"	
         />
     </div>		
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_pre_payment_nt/BillPrePaymentNt.js?version=${version}"></script>
</body>
</html>