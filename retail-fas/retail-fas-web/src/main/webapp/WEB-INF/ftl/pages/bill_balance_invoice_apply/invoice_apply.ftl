<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>开票申请单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/bill_balance_invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/iptSearch.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply_export.js?version=${version}"></script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div region="center" data-options="border:false">
	<input type="hidden" name="isHQ" id="isHQ" value="${isHQ}">
	<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
	<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'单据明细'">
             <div class="easyui-layout" data-options="fit:true">
                <input type="hidden" name="balanceType" id="balanceType" value="${balanceType}">
            	<div data-options="region:'north',border:false">
                	<@p.toolbar id="top_bar" listData=[
                	 {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
						{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"billBalanceInvoiceApply.add()","type":1},
						{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"billBalanceInvoiceApply.save()","type":7},  
						{"id":"btn-del","title":"删除","iconCls":"icon-del","action":"billBalanceInvoiceApply.delApply()","type":3},     		
						{"id":"mian_btn_aduit","title":"审核","iconCls":"icon-aduit","action":"billBalanceInvoiceApply.operate(2)","type":31},	
						{"id":"top_btn_antiAudit","title":"反审核","iconCls":"icon-aduit","action":"billBalanceInvoiceApply.operate(1)","type":32},
						{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":20}							
						<!--
						{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"billBalanceInvoiceApply.upBill()","type":0},
						{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"billBalanceInvoiceApply.downBill()","type":0}
						{"id":"mian_btn_cancel","title":"作废","iconCls":"icon-cancel","action":"billBalanceInvoiceApply.operate(3)","type":0}
					    -->
					 ]/>	
                </div>                    

             	<div data-options="region:'center',border:false">		
					<div id="dtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
						<div title="基本信息">
							<#include  "/WEB-INF/ftl/pages/bill_balance_invoice_apply/invoice_base.ftl">    
						</div>
						<div title="源单信息">
							<#include  "/WEB-INF/ftl/pages/bill_balance_invoice_apply/invoice_source.ftl">    
						</div>
						<div title="发票信息">
							<#include  "/WEB-INF/ftl/pages/bill_balance_invoice_apply/invoice_info.ftl">    
						</div>
					</div>		
			    </div>
			        
                <div data-options="region:'south',border:false">
                    <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">                   
                </div> 
            </div>
        </div>
    </div>
</div>
</body>
</html>