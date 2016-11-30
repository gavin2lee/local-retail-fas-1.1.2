<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>客户保证金催缴通知单</title>
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
					  export_title="客户保证金催缴通知单信息"
					  export_type="common"
					  audit_url="/do_audit?auditVal=1"
					  anti_audit_url="/do_audit?auditVal=0"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="650" 
					  dialog_height="380"
					  primary_key="id"/>
	<div data-options="region:'north',border:false">
    	 <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_fasDialogController.search()","type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"_fasDialogController.clear()","type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"_billCustMarginNt.toAdd()", "type":0},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"_billCustMarginNt.toUpdate()","type":0},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"_billCustMarginNt.doDel()","type":0},
             {"id":"btn-audit","title":"审核","iconCls":"icon-aduit","action":"_billCustMarginNt.doAudit()","type":0},
			 {"id":"btn-antiAudit","title":"反审核","iconCls":"icon-aduit","action":"_billCustMarginNt.doAntiAudit()","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"_fasDialogController.exportExcel()","type":0}
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
							    <td><input class="ipt" name="billNo"/></td>
								<th>公司名称：</th>
	                            <td><input class="ipt"  name="companyName"/></td>
	                            <th>客户编码：</th>
	                            <td><input class="ipt"  name="customerNo"/></td>
	                            <th>客户名称：</th>
	                            <td><input class="ipt"  name="customerName"/></td>
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
					{field : 'billNo',title : '单据编码',width : 100,align : 'left'},
					{field : 'companyName',title : '公司名称',width : 180,align : 'left'},
					{field : 'customerNo',title : '客户编码',width : 120,align : 'left'},
					{field : 'customerName',title : '客户名称',width : 180,align : 'left'},
					{field : 'marginAmount',title : '合同保证金',width : 80,align : 'right'},
					{field : 'recedMarginAmount',title : '保证金余额',width : 80,align : 'right'},
					{field : 'receMarginAmount',title : '应收金额',width : 80,align : 'right'},
					{field : 'paidMarginAmount',title : '实收金额',width : 80,align : 'right'},
					{field : 'auditStatusName',title : '审核状态',width : 80,align : 'center'},
					{field : 'createUser',title : '制单人',width : 90,align : 'center'},
					{field : 'createTime',title : '制单时间',width : 140,align : 'center'}
				]" 
				jsonExtend='{onDblClickRow:function(rowIndex, rowData){
					 _billCustMarginNt.toUpdate(rowData);
			}}'/>
        </div>
      </div>
</div>

<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
    <div class="easyui-layout" data-options="fit:true,border:false" >
	     <form name="dataForm" id="dataForm" method="post"  class="pd10">
	     	 <div data-options="region:'north',border:false,height:300" class="pd15">
				<div class="easyui-panel" data-options="title:'客户保证金催缴通知单信息',fieldset:true,fit:true,collapsible:false">
					<table cellpadding="1" cellspacing="1" class="form-tb">
					   <input type="hidden" name="id" id="id">
					   <tr>
                        	<th width="110" align='right'>
								<span class="ui-color-red">*</span>
								单据编码：
							</th>
                            <td width="140" align='left'><input class="easyui-validatebox ipt" name="billNo" id="billNo" data-options="required:true,validType:['unNormalData','engNum','maxLength[10]']"/></td>
                        </tr>
                        <tr>
                            <th width="110" align='right'>
                            	<span class="ui-color-red">*</span>客户编码：
                            </th>
                            <td width="140" align='left'>
                            	<input class="easyui-validatebox ipt" readonly="true" name="customerNo" id="customerNo" data-options="required:true"/>
                            </td>
                        	<th width="110" align='right'>客户名称 ：</th>
                            <td width="140" align='left'><input class="readonly ipt" readonly="true"  name="customerName" id="customerName"/></td>
                        </tr>
                        <tr>
                        	<th width="110" align='right'>
                        		<span class="ui-color-red">*</span>结算主体：
                        	</th>
                            <td width="140" align='left'>
                            	<input class="easyui-validatebox ipt" readonly="true" id="companyName" name="companyName" data-options="required:true"/>
                            	<input type="hidden" name="companyNo" id="companyNo"/>
                            </td>
                        	<th width="110" align='right'>合同保证金 ：</th>
                            <td width="140" align='left'><input class="readonly ipt" readonly="true"  name="marginAmount" id="marginAmount"/></td>
                        </tr>
                        <tr>
                        	<th width="110" align='right'>
                        		<span class="ui-color-red">*</span>应收保证金：
                        	</th>
                            <td width="140" align='left'><input class="easyui-numberbox ipt" name="receMarginAmount" id="receMarginAmount" data-options="required:true,min:0,precision:2"/></td>
                            <th width="110" align='right'>保证金余额：</th>
                            <td width="140" align='left'><input class="readonly ipt" readonly="true"  name="recedMarginAmount" id="recedMarginAmount" /></td>
                        </tr>
                        <tr>
                        	<th width="110" align='right'>
                        		<span class="ui-color-red">*</span>实收金额 ：
                        	</th>
                            <td width="140" align='left'><input class="easyui-numberbox ipt" name="paidMarginAmount" id="paidMarginAmount" data-options="required:true,min:0,precision:2"/></td>
                        	<th width="110" align='right'>
                        		<span class="ui-color-red">*</span>单据日期：
                        	</th>
						    <td width="140" align='left'><input class="easyui-validatebox easyui-datebox ipt" name="billDate"  id="billDate" data-options="required:true"/></td>
                        </tr>
                        <tr>
                        	<th width="110" align='right'>备注 ：</th>
                            <td colspan ="7"><input class="ipt" name="remark" id="remark" style="width:99%"/></td>
                        </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
  </div>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_cust_margin_nt/BillCustMarginNt.js?version=${version}"></script>
</body>
</html>