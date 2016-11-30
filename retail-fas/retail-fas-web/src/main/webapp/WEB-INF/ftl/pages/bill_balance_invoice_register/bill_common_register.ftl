<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>普通发票登记</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_register/bill_common_invoice_register.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/iptSearch.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div class="easyui-panel" data-options="region:'center',border:false">
	<input type="hidden" name="isHQ" id="isHQ" value="${isHQ}">
	<input type="hidden" name="billNoMenu" id="billNoMenu" value="${billNoMenu}">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'单据明细'">
           <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
                    	   {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
							{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"billCommonInvoiceRegister.add()","type":1},							
							{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"billCommonInvoiceRegister.save()","type":7},
							{"id":"mian_btn_del","title":"删除","iconCls":"icon-del","action":"billCommonInvoiceRegister.del()","type":3},						
							{"id":"mian_btn_aduit","title":"确认","iconCls":"icon-aduit","action":"billCommonInvoiceRegister.operate(1)","type":31},	
							{"id":"top_btn_cancel","title":"反确认","iconCls":"icon-aduit","action":"billCommonInvoiceRegister.operate(0)","type":32},
							<!--{"id":"mian_btn_cancel","title":"作废","iconCls":"icon-cancel","action":"billCommonInvoiceRegister.operate(2)","type":0},-->		
						    {"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"exportExcel()","type":4}, 			
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"billCommonInvoiceRegister.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"billCommonInvoiceRegister.downBill()","type":0}
						 ]/>	
                    </div>
                    <div data-options="region:'center',border:false">		
						<div id="dtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
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