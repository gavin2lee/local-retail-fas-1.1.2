<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>预收款缴款通知单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasDialogController.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/list.json" 
					  save_url="/insert" 
					  update_url="/update" 
					  del_url="/save"
					  export_url="/do_fas_export"
					  export_title="预收款缴款通知单"
					  export_type="common"
					  audit_url="/do_audit?auditVal=1"
					  anti_audit_url="/do_audit?auditVal=0"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="760" 
					  dialog_height="300"
					  primary_key="id"/>
	<div data-options="region:'north',border:false">
    	 <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_fasDialogController.search()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"_billPrePaymentNt.clear()","type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"_billPrePaymentNt.toAdd()", "type":1},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"_billPrePaymentNt.toUpdate()","type":2},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"_billPrePaymentNt.doDel()","type":3},
             {"id":"btn-audit","title":"审核","iconCls":"icon-aduit","action":"_billPrePaymentNt.doAudit()","type":31},
			 {"id":"btn-antiAudit","title":"反审核","iconCls":"icon-aduit","action":"_billPrePaymentNt.doAntiAudit()","type":32},
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
	                            	<th>单据编码：</th>
								    <td><input style="width:140px" class="ipt" name="billNo"/></td>
									<th>公司：</th>
		                            <td>
	                                	<input class="easyui-company ipt" name="companyName" id="companyNameCondition" data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
	                                	<input type="hidden" name="companyNo" id="companyNoCondition"/>
                                	</td>
									</td>
		                            <th>客户：</th>
		                            <td>
	                                	<input class="easyui-customer ipt" name="customerName" id="customerNameCondition" data-options="inputWidth:160,inputNoField:'customerNoCondition',inputNameField:'customerNameCondition'"/>
	                                	<input type="hidden" name="customerNo" id="customerNoCondition"/>
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
						{field:'ck',checkbox:true,notexport:true},
						{field : 'billNo',title : '单据编码',width : 100, align : 'left'},
						{field : 'companyName',title : '公司名称',width : 180,align : 'left'},
						{field : 'customerNo',title : '客户编码',width : 120,align : 'left'},
						{field : 'customerName',title : '客户名称',width : 180,align : 'left'},
						{field : 'saleOrderNo',title : '销售订单编码',width : 100,align : 'left'},
						{field : 'orderAmount',title : '订单金额',width : 80,align : 'right'},
						{field : 'receivableAmount',title : '应收金额',width : 80,align : 'right'},
						{field : 'paidAmount',title : '实收金额',width : 80,align : 'right'},
						{field : 'auditStatusName',title : '审核状态',width : 80,align : 'center'},
						{field : 'createUser',title : '制单人',width : 80,align : 'center'},
						{field : 'createTime',title : '制单时间',width : 140,align : 'center'}
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
					<div class="easyui-panel" data-options="title:'预收款缴款通知单信息',fieldset:true,fit:true,collapsible:false">
						<table cellpadding="1" cellspacing="1" class="form-tb">
						   <input type="hidden" name="id" id="id">
						   <input type="hidden" name="balanceType" id="balanceType" value="7">
						   <tr>
                            	<th width="140" align='right'>
                            		<span class="ui-color-red">*</span>单据编码：
                            	</th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="readonly ipt" name="billNo" id="billNo" readonly="readonly"/>
                                </td>
                                <th width="140" align='right'>
                            		<span class="ui-color-red">*</span>单据日期：
                            	</th>
                            	 <td width="140" align='left'>
							    	<input style='width:190px' class="easyui-validatebox easyui-datebox ipt" name="billDate"  id="billDate" data-options="required:true"/>
							    </td>
                            </tr>
                    	   <tr>
                           		<th width="110" align='right'>
                            	<span class="ui-color-red">*</span>公司：
                                </th>
                            	<td width="140" align='left'>
                                	<input class="easyui-company ipt" name="companyName" id="companyName" data-options="required:true,inputWidth:200,inputNoField:'companyNo',inputNameField:'companyName'"/>
                                	<input type="hidden" name="companyNo" id="companyNo"/>
                                </td>
                                <th width="110" align='right'>
                                	<span class="ui-color-red">*</span>客户：
                                </th>
                            	<td width="140" align='left'>
                                	<input class="easyui-customer ipt" name="customerName" id="customerName" data-options="required:true,inputWidth:200,inputNoField:'customerNo',inputNameField:'customerName'"/>
                                	<input type="hidden"  name="customerNo" id="customerNo"/>
                                </td>
                            </tr>
                            <tr>
                            	<th width="140" align='right'>批发订单编码 ：</th>
                                <td width="140" align='left'>
                                	<input class="ipt" name="saleOrderNo" id="saleOrderNo"/>
                                </td>
                                <th width="140" align='right'>订单金额 ：</th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="ipt readonly" readonly="true" name="orderAmount" id="orderAmount"/>
                                </td>
                            </tr>
                            <tr>
                                <th width="140" align='right'>已预收：</th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="ipt readonly" readonly="true" name="prePaymentOver" id="prePaymentOver" />
                                </td>
                                <th width="140" align='right'>应收金额：</th>
                                <td width="140" align='left'>
                                	<input style='width:190px' class="ipt readonly" readonly="true" name="receivableAmount" id="receivableAmount" />
                                </td>
                            </tr>
                            <tr>
                            	<th width="140" align='right'>
                            		<span class="ui-color-red">*</span>实收金额 ：
                            	</th>
                                <td width="140" align='left'>
                               		<input style='width:190px' class="ipt" name="paidAmount" id="paidAmount" />
                               	</td>
                            </tr>
                            <tr>
                                <th width="140" align='right'>备注 ：</th>
                                <td colspan ="7"><input class="ipt" name="remark" id="remark" style="width:99%"/></td>
                            </tr>
						</table>
					</div>
				</div>
			 </form>	
	   </div>
   </div>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_pre_payment_nt/BillPrePaymentNt.js?version=${version}"></script>
</body>
</html>