<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>预收款单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasDialogController.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/list.json" 
					  export_title="预收款单"
					  export_url="/do_fas_export"
					  export_type="common"
					  save_url="/insert" 
					  update_url="/update" 
					  del_url="/save" 
					  enable_url="/do_enable"
					  unable_url="/do_unable"
					  audit_url = "/do_audit?auditVal=1"
					  anti_audit_url="/do_audit?auditVal=0"
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  data_form_id="showDataForm"
					  dialog_width="820" 
					  dialog_height="260"
					  primary_key="id"/>
<div data-options="region:'north',border:false" class="toolbar-region">
  <#-- 工具菜单div -->
           <@p.toolbar id="toolbar" listData=[
           				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_fasBillController.search()","type":0},
           				 {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"_GrouponPrePayment.clear()","type":0},
                         {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"_GrouponPrePayment.toAdd()","type":1},
                         {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"_GrouponPrePayment.doDel()","type":3},	
                         {"id":"btn-audit","title":"审核","iconCls":"icon-aduit","action":"_fasBillController.doAudit()","type":31},
						 {"id":"bill-antiAudit","title":"反审核","iconCls":"icon-aduit","action":"_fasBillController.doAntiAudit()","type":32},
						 <!--{"id":"btn-enable","title":"激活","iconCls":"icon-ok","action":"_fasBillController.doEnable()","type":0},
						 {"id":"btn-unable","title":"注销","iconCls":"icon-del","action":"_fasBillController.doUnable()","type":0},-->
						 {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"_fasBillController.exportExcel()","type":4}
                       ]
    />
</div>
	  
	  <#-- 条件查询区域div -->
	  <div data-options="region:'center',border:false">	
	  <div class="easyui-layout" data-options="fit:true" id="subLayout">
	  <div data-options="region:'north',border:false" >
		<div class="search-div">			
	       <form name="searchForm" id="searchForm" method="post">
	       <input type="hidden" name="balanceType" value="8">
				   <table class="search-tb" >
                        <col width="100" />
                        <col/>
                        <col width="100" />
                        <col/>
                        <col width="100" />
                        <col/>
                        <col width="100" />
                        <col/>
                        <tbody>
	                  <tr>
				    		<th>单据编号：</th>
						    <td><input class="ipt" name="billNo"/></td>
							<th>结算公司：</th>
                            <td><input class="easyui-company ipt" readonly="true" name="companyName" id="companyNames"/>
		                             <input type="hidden" name="companyNo" id="companyNos"/></td>
                            <th>客户：</th>
                            <td><input class="easyui-customerInvoiceInfo" name="customerName" id="customerNames" 
								data-options="inputValueFeild:'customerNos',inputNameFeild:'customerNames'" />
								<input type="hidden" name="customerNo" id="customerNos"/>
							</td>
	                  </tr>
	                  </tbody>
	               </table>
				</form>
	  </div>
	  </div>

				<div data-options="region:'center',border:false">       			
              <#-- 数据列表div -->
          	            <@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""  defaultColumn="" 
		                   isHasToolBar="false"  height="421"  onClickRowEdit="false"    pagination="true"
			               rownumbers="true"   singleSelect="true"
			               columnsJsonList="[
			                    {field : 'ck',checkbox:true,notexport:true},
								{field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
								{field : 'auditStatusName',title : '审核状态',width : 90,align:'center',halign:'center'},
								{field : 'companyName',title : '结算公司',width : 200,align:'left',halign:'center'},
								{field : 'customerNo',title : '客户编码',width : 150,align:'left',halign:'center'},
								{field : 'customerName',title : '客户名称',width : 180,align:'left',halign:'center'},
								{field : 'saleOrderNo',title : '团购订单号',width : 120,align:'left',halign:'center'},
								{field : 'orderAmount',title : '订单金额',width : 90,align:'right',halign:'center',exportType:'number'},
								{field : 'receivableAmount',title : '应收金额',width : 90,align:'right',halign:'center',exportType:'number'},
								{field : 'paidAmount',title : '实收金额',width : 90,align:'right',halign:'center',exportType:'number'},
								{field : 'createUser',title : '制单人',width : 90,align:'center',halign:'center'},
								{field : 'createTime',title : '制单时间',width : 150,align:'center',halign:'center'},
								{field : 'auditTime',title : '审核时间',width : 150,align:'center',halign:'center'},
								{field : 'remark',title : '备注',width : 150,align:'left',halign:'center'}
			                 ]"   
			                 jsonExtend='{onDblClickRow:function(rowIndex, rowData){
		                   	//双击方法
		                   	        _GrouponPrePayment.toUpdate(rowData);
		                   }}'
				           />
	          </div>
	      </div>
</div>
	          
	           <div id="myFormPanel" class="easyui-dialog" data-options="closed:true">
	                   <form name="showDataForm" id="showDataForm" method="post" class="pd10">
	                   <input type="hidden" name="id" id="id"/>
	                   <input type="hidden" name="balanceType" value="8">
	                   <input type="hidden" name="invoiceDate" id="invoiceDate">
			                  <table class="form-tb">
					        	<col width="100">
					        	<col/>
					        	<col width="100">
					        	<col/>
					    		<col width="100">
					        	<col/>
					        	<tbody>
			                  <tr>
			                  	 <th>单据编号： </th>
			                  	 <td><input class="readonly ipt" readonly="true" name="billNo" id="billNo"/></td>
			                  	 <th><span class="ui-color-red">*</span> 结算公司： </th>
			                     <td><input class="easyui-company ipt" name="companyName" id="companyName" 
			                    	 data-options="required:true,inputWidth:160,callback:selectCompany" />
		                             <input type="hidden" name="companyNo" id="companyNo"/>
		                         </td>
			                     <th>客户名称： </th>
			                     <td><input class="easyui-customerInvoiceInfo" name="customerName" id="customerName" 
									data-options="required:true,inputNoField:'customerNo', inputNameField:'customerName',inputWidth:160,callback:selectCustomer" />
									<input type="hidden" name="customerNo" id="customerNo"/>
								 </td>
			                     
		                      </tr>
			                  <tr>
			                     <th><span class="ui-color-red">*</span> 团购订单号：</th>
			                     <td><input class="easyui-validatebox ipt" readonly="true" name="saleOrderNo" id="saleOrderNo" required="true"/></td>
			                     <th>单据日期：  </th>
			                     <td><input class="easyui-validatebox easyui-datebox ipt" readonly="true" name="billDate" id="billDate" data-options="disabled:true"/></td>
			                     <th>订单金额：</th>
			                     <td><input class="readonly ipt" readonly="true" name="orderAmount" id="orderAmount" data-options="precision:2"/></td>
			                  </tr>
			                  <tr>
			                     <th>应收金额： </th>
			                     <td><input class="easyui-numberbox ipt" name="receivableAmount" id="receivableAmount" data-options="precision:2"  maxlength="10" /></td>
			                     <th>实收金额：</th>
			                     <td><input class="easyui-numberbox ipt" name="paidAmount" id="paidAmount" data-options="precision:2"  maxlength="10" /></td>
			                     <th>开票申请号：</th>
			                     <td><input class="ipt" readonly="true" name="invoiceNo" id="invoiceNo"/></td>
			                  </tr>
			                  <tr>
			                     <th>备注：</th>
			                     <td colspan=5><input class="ipt" name="remark" id="remark" style="width:412px"/></td>
			                  </tr>
			                  </tbody>
			               </table>
				         </form>
</div>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/groupon/grouponPrePayment.js?version=${version}"></script>
</body>
</html>