<#-- 单据工具条 定义宏 -->
<#macro billToolBar type>
	<#-- 跨区调拨明细查询 -->
	<#if "area_among_transferList"==type>
		 <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "crossArea.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "crossArea.clear()","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "crossArea.doExport()","type":4}
           ]
		/>
	</#if>
	
	<#-- 跨区调拨明细查询 (应付)-->
	<#if "area_among_transferList_yf"==type>
		 <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "crossArea.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "crossArea.clear()","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "crossArea.doExport()","type":4}
           ]
		/>
	</#if>
	
	<#-- 收发汇总查询 -->
	<#if "area_among_contrastList"==type>
		<@p.toolbar id="mainToolbar" listData=[
			 {"id":"btn_search","title":"查询","iconCls":"icon-search","action":"contrastdtl.search()","type":0},
             {"id":"btn_clear","title":"清空","iconCls":"icon-empty","action":"contrastdtl.clearContrast()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "contrastdtl.doExport()","type":4}
           ]
		/>
	</#if>
	
	<#-- 收发汇总查询(应付) -->
	<#if "area_among_contrastList_yf"==type>
		<@p.toolbar id="mainToolbar" listData=[
			 {"id":"btn_search","title":"查询","iconCls":"icon-search","action":"contrastdtl.search()","type":0},
             {"id":"btn_clear","title":"清空","iconCls":"icon-empty","action":"contrastdtl.clearContrast()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "contrastdtl.doExport()","type":4}
           ]
		/>
	</#if>
	
	<#-- 地区间结算(应收) -->
	<#if "area_among_balance_listMainBar"==type>
		 <@p.toolbar id="main_bar"	listData=[
			{"id":"main_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"areaBalance.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"areaBalance.clear()","type":0},
			{"id":"main_btn_del","title":"删除","iconCls":"icon-del","action":"areaBalance.batchDel()","type":3},		
			{"id":"main_btn_sure","title":"调出方财务确认","iconCls":"icon-aduit","action":"areaBalance.batchAudit(2,'调出方财务确认')","type":72} ,								
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaBalance.batchAudit(99,'打回')","type":73},
			{"id":"main_btn_billing","title":"生成开票申请","iconCls":"icon-build-some","type":78,"action":"invoiceApplyBalance.invoiceApply('mainDataGrid','5')"},  
			{"id":"main_btn_build","title":"批量生成","iconCls":"icon-build-some","type":55,"action":"areaBalance.toBatchAdd()"},	
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"areaBalance.listExport('mainDataGrid','/area_among_balance/list_export','地区间应收结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"areaBalance.dtlExport('mainDataGrid','transferOutDtl','/area_among_balance/dtl_export','地区间应收结算单明细')","type":4}
		]/>
	</#if>		
	
	<#if "area_among_balance_listBar"==type>
		 <@p.toolbar id="top_bar" listData=[
    		{"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
			{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"areaBalance.add()","type":0},
			{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"areaBalance.save()","type":7},
			{"id":"top_btn_del","title":"删除","iconCls":"icon-del","action":"areaBalance.del()","type":3},
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"areaBalance.upBill()","type":0} ,
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"areaBalance.downBill()","type":0},
			{"id":"top_btn_adjust","title":"扣项调整","iconCls":"icon-edit","action":"areaBalance.deductAdjust()","type":80},
			{"id":"top_btn_sure","title":"调出方财务确认","iconCls":"icon-aduit","action":"areaBalance.audit(2,'调出方财务确认')","type":72},								
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaBalance.audit(99,'打回')","type":73},
			{"id":"top_btn_select","title":"选单","iconCls":"icon-add","action":"areaBalance.selectSave()","type":77},
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"areaBalance.operateLog()","type":81},
			{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":20}
		 ]/>	
	</#if>	
	
	<#-- 地区间结算(应付) -->
	<#if "area_among_pay_listBar"==type>
		<@p.toolbar id="top_bar" listData=[
    		{"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"areaBalance.upBill()","type":0} ,
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"areaBalance.downBill()","type":0},
			{"id":"top_btn_sure","title":"调入方财务确认","iconCls":"icon-aduit","action":"areaBalance.audit(4,'调入方财务确认')","type":101},								
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaBalance.audit(99,'打回')","type":73},
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"areaBalance.operateLog()","type":81},
			{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":20}
		]/>
	</#if>
	
	<#if "area_among_pay_listMainBar"==type>
		 <@p.toolbar id="main_bar"	listData=[
			{"id":"main_btn_add","title":"查看明细","iconCls":"icon-xq","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"areaBalance.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"areaBalance.clear()","type":0},
			{"id":"main_btn_sure","title":"调入方财务确认","iconCls":"icon-aduit","action":"areaBalance.batchAudit(4,'调入方财务确认','1')","type":101} ,								
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaBalance.batchAudit(99,'打回')","type":73},
			{"id":"main_btn_please","title":"生成请款单","iconCls":"icon-build-some","action":"areaBalance.BatchCreatePaymentBill(5)","type":82},
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"areaBalance.listExport('mainDataGrid','/area_among_pay/list_export','地区间应付结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"areaBalance.dtlExport('mainDataGrid','transferOutDtl','/area_among_pay/dtl_export','地区间应付结算单明细')","type":4}
		 ]/>
	</#if>		
	
	<#--地区其他出库明细查询-->
	<#if "area_other_list"==type>
		  <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"otherOut.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty","action":"otherOut.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "otherOut.doExport()","type":4}
           ]
		 />
	</#if>	
	
	<#--地区其他出库结算-->
	<#if "area_other_balance"==type>
		<@p.toolbar id="top_bar" listData=[
    		{"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
    		{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"areaOtherBalance.add()","type":1},
			{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"areaOtherBalance.save()","type":7} ,
			{"id":"top_btn_del","title":"删除","iconCls":"icon-del","action":"areaOtherBalance.del()","type":3} ,
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"areaOtherBalance.upBill()","type":0} ,
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"areaOtherBalance.downBill()","type":0},
			{"id":"top_btn_sure","title":"地区财务确认","iconCls":"icon-aduit","action":"areaOtherBalance.audit(2,'地区财务确认')","type":75},
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaOtherBalance.audit(99,'打回')","type":73}
			{"id":"top_btn_select","title":"选单","iconCls":"icon-add","action":"areaOtherBalance.selectSave()","type":77},
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"areaOtherBalance.operateLog()","type":81},
			{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":20}
		 ]/>
	</#if>	
	
	<#if "area_other_mainBar"==type>
		<@p.toolbar id="main_bar" listData=[
			{"id":"main_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"areaOtherBalance.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"areaOtherBalance.clear()","type":0},
			{"id":"main_btn_del","title":"删除","iconCls":"icon-del","action":"areaOtherBalance.batchDel()","type":3},		
			{"id":"main_btn_sure","title":"地区财务确认","iconCls":"icon-aduit","action":"areaOtherBalance.batchAudit(2,'地区财务确认')","type":75} ,
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaOtherBalance.batchAudit(99,'打回')","type":73},
			{"id":"main_btn_billing","title":"生成开票申请","iconCls":"icon-build-some","type":78,"action":"invoiceApplyBalance.invoiceApply('mainDataGrid','11')"},
			{"id":"main_btn_build","title":"批量生成","iconCls":"icon-build-some","action":"areaOtherBalance.toBatchAdd()","type":55},
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"areaOtherBalance.listExport('mainDataGrid','/area_other_stock_out_balance/list_export','地区其他出库结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"areaOtherBalance.dtlExport('mainDataGrid','transferOutDtl','/area_other_stock_out_balance/dtl_export','地区其他出库结算单明细')","type":4}
		]/>
	</#if>	
	
	<#--总部其他出库结算-->
	<#if "hq_other_out_balance"==type>
		<@p.toolbar id="top_bar" listData=[
    		{"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
    		{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"hqOtherStockOutBalance.add()","type":1},
			{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"hqOtherStockOutBalance.save()","type":7} ,
			{"id":"top_btn_del","title":"删除","iconCls":"icon-del","action":"hqOtherStockOutBalance.del()","type":3} ,
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"hqOtherStockOutBalance.upBill()","type":0} ,
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"hqOtherStockOutBalance.downBill()","type":0},
			{"id":"top_btn_sure","title":"总部财务确认","iconCls":"icon-aduit","action":"hqOtherStockOutBalance.audit(2,'总部财务确认')","type":76},
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"hqOtherStockOutBalance.audit(99,'打回')","type":73}
			{"id":"top_btn_select","title":"选单","iconCls":"icon-add","action":"hqOtherStockOutBalance.selectSave()","type":77},
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"hqOtherStockOutBalance.operateLog()","type":81}
		 ]/>
	</#if>	
	
	<#if "hq_other_out_mainBar"==type>
		<@p.toolbar id="main_bar" listData=[
			{"id":"main_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"hqOtherStockOutBalance.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"hqOtherStockOutBalance.clear()","type":0},
			{"id":"main_btn_del","title":"删除","iconCls":"icon-del","action":"hqOtherStockOutBalance.batchDel()","type":3},		
			{"id":"main_btn_sure","title":"总部财务确认","iconCls":"icon-aduit","action":"hqOtherStockOutBalance.batchAudit(2,'总部财务确认')","type":76} ,
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"hqOtherStockOutBalance.batchAudit(99,'打回')","type":73},
			{"id":"main_btn_billing","title":"生成开票申请","iconCls":"icon-build-some","type":78,"action":"invoiceApplyBalance.invoiceApply('mainDataGrid','14')"},
			{"id":"main_btn_build","title":"批量生成","iconCls":"icon-build-some","action":"hqOtherStockOutBalance.toBatchAdd()","type":55},
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"hqOtherStockOutBalance.listExport('mainDataGrid','/hq_other_stock_out_balance/list_export','总部其他出库结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"hqOtherStockOutBalance.dtlExport('mainDataGrid','transferOutDtl','/hq_other_stock_out_balance/dtl_export','总部其他出库结算单明细')","type":4}
		]/>
	</#if>	
	
	
	<#--总部其他入库明细查询-->
	<#if "hq_other_list"==type>
		  <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"otherOut.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty","action":"otherOut.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "otherOut.doExport()","type":4}
           ]
		 />
	</#if>	
	
	<#--总部其他入库结算-->
	<#if "hq_other_balance"==type>
		<@p.toolbar id="top_bar" listData=[
    		{"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"areaOtherBalance.upBill()","type":0} ,
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"areaOtherBalance.downBill()","type":0},
			{"id":"top_btn_sure","title":"总部财务确认","iconCls":"icon-aduit","action":"areaOtherBalance.audit(4,'总部财务确认')","type":76} ,
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaOtherBalance.audit(99,'打回')","type":73}
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"areaOtherBalance.operateLog()","type":81}
		 ]/>
	</#if>	
	
	<#if "hq_other_mainBar"==type>
		<@p.toolbar id="main_bar" listData=[
			{"id":"main_btn_add","title":"查看明细","iconCls":"icon-xq","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"areaOtherBalance.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"areaOtherBalance.clear()","type":0},
			{"id":"main_btn_sure","title":"总部财务确认","iconCls":"icon-aduit","action":"areaOtherBalance.batchAudit(4,'总部财务确认','1')","type":76},
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaOtherBalance.batchAudit(99,'打回')","type":73},
			{"id":"main_btn_please","title":"生成请款单","iconCls":"icon-build-some","action":"areaOtherBalance.BatchCreatePaymentBill(11)","type":82},
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"areaOtherBalance.listExport('mainDataGrid','/hq_other_stock_in_balance/list_export','总部其他入库结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"areaOtherBalance.dtlExport('mainDataGrid','transferOutDtl','/hq_other_stock_in_balance/dtl_export','总部其他入库结算单明细')","type":4}
		]/>
	</#if>	
	
	<#--地区其他入库明细查询-->
	<#if "area_other_in_list"==type>
		  <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"areaOtherStockInDtl.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty","action":"areaOtherStockInDtl.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "areaOtherStockInDtl.doExport()","type":4}
           ]
		 />
	</#if>	
	
	<#--地区其他入库结算-->
	<#if "area_other_in_balance"==type>
		<@p.toolbar id="top_bar" listData=[
    		{"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"areaOtherStockInBalance.upBill()","type":0} ,
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"areaOtherStockInBalance.downBill()","type":0},
			{"id":"top_btn_sure","title":"地区财务确认","iconCls":"icon-aduit","action":"areaOtherStockInBalance.audit(4,'地区财务确认')","type":75} ,
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaOtherStockInBalance.audit(99,'打回')","type":73}
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"areaOtherStockInBalance.operateLog()","type":81}
		 ]/>
	</#if>	
	
	<#if "area_other_in_mainBar"==type>
		<@p.toolbar id="main_bar" listData=[
			{"id":"main_btn_add","title":"查看明细","iconCls":"icon-xq","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"areaOtherStockInBalance.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"areaOtherStockInBalance.clear()","type":0},
			{"id":"main_btn_sure","title":"地区财务确认","iconCls":"icon-aduit","action":"areaOtherStockInBalance.batchAudit(4,'地区财务确认','1')","type":75},
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaOtherStockInBalance.batchAudit(99,'打回')","type":73},
			{"id":"main_btn_please","title":"生成请款单","iconCls":"icon-build-some","action":"areaOtherStockInBalance.BatchCreatePaymentBill(14)","type":82},
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"areaOtherStockInBalance.listExport('mainDataGrid','/area_other_stock_in_balance/list_export','地区其他入库结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"areaOtherStockInBalance.dtlExport('mainDataGrid','transferOutDtl','/area_other_stock_in_balance/dtl_export','地区其他入库结算单明细')","type":4}
		]/>
	</#if>	
	
	<#--地区自购结算-->
	<#if "area_purchase_balance"==type>
		<@p.toolbar id="top_bar" listData=[
            {"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
			{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"areaBuyBalance.add()","type":1},
			{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"areaBuyBalance.save()","type":7},
			{"id":"top_btn_del","title":"删除","iconCls":"icon-del","action":"areaBuyBalance.del()","type":3},
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"areaBuyBalance.upBill()","type":0},
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"areaBuyBalance.downBill()","type":0},
			{"id":"top_btn_adjust","title":"扣项调整","iconCls":"icon-edit","action":"areaBuyBalance.deductAdjust()","type":80},
			{"id":"top_btn_bSure","title":"业务确认","iconCls":"icon-aduit","action":"areaBuyBalance.audit(1,'业务确认')","type":74} ,								
			{"id":"top_btn_fSure","title":"财务确认","iconCls":"icon-aduit","action":"areaBuyBalance.audit(2,'财务确认')","type":79} ,								
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaBuyBalance.audit(99,'打回')","type":73},
			{"id":"top_btn_select","title":"选单","iconCls":"icon-add","action":"areaBuyBalance.selectSave()","type":77},
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"areaBuyBalance.operateLog()","type":81}
		 ]/>	
	</#if>	
	
	<#if "area_purchase_mainBar"==type>
		 <@p.toolbar id="main_bar"	listData=[
			{"id":"main_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"areaBuyBalance.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"areaBuyBalance.clear()","type":0},
			{"id":"main_btn_del","title":"删除","iconCls":"icon-del","action":"areaBuyBalance.batchDel()","type":3},		
			{"id":"main_btn_bSure","title":"业务确认","iconCls":"icon-aduit","action":"areaBuyBalance.batchAudit(1,'业务确认')","type":74} ,								
			{"id":"main_btn_fSure","title":"财务确认","iconCls":"icon-aduit","action":"areaBuyBalance.batchAudit(2,'财务确认')","type":79} ,								
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"areaBuyBalance.batchAudit(99,'打回')","type":73},					
			{"id":"main_btn_please","title":"生成请款单","iconCls":"icon-build-some","action":"areaBuyBalance.BatchCreatePaymentBill(5)","type":82},
		    {"id":"main_btn_build","title":"批量生成","iconCls":"icon-build-some","type":55,"action":"areaBuyBalance.toBatchAdd()"},	
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"areaBuyBalance.listExport('mainDataGrid','/area_private_purchase_balance/list_export','地区自购结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"areaBuyBalance.dtlExport('mainDataGrid','transferInDtl','/area_private_purchase_balance/dtl_export','地区自购结算单明细')","type":4}
		]/>
	</#if>	
	
	<#--代采出库明细查询-->
	<#if "hq_insteadOf_list"==type>
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "entryDtl.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "entryDtl.clear()","type":0},
             {"id":"btn-direct","title":"更新异常价格","iconCls":"icon-build-some", "type":0},
           	 {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "entryDtl.doExport('dataGridJG','/hq_insteadOf_buy/export','代采入库明细表')","type":4}
           ]
		/>
	</#if>
	
	<#--代采入库明细查询-->
	<#if "hq_insteadOf_entry_list"==type>
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "entryDtl.search()", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "entryDtl.clear()","type":0},
           	 {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "entryDtl.doExport('dataGridJG','/hq_insteadOf_buy/export','代采入库明细表')","type":4}
           ]
		/>
	</#if>
	
	<#--总部代采结算-->
	<#if "hq_insteadOf_balance"==type>
		<@p.toolbar id="top_bar" listData=[
            {"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
			{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"hqInsteadBuy.add()","type":1},
			{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"hqInsteadBuy.beforeSave()","type":7},
			{"id":"top_btn_del","title":"删除","iconCls":"icon-del","action":"hqInsteadBuy.del()","type":3},
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"hqInsteadBuy.upBill()","type":0},
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"hqInsteadBuy.downBill()","type":0},
			{"id":"top_btn_select","title":"扣项调整","iconCls":"icon-edit","action":"hqInsteadBuy.deductAdjust()","type":80},
			{"id":"top_btn_bSure","title":"确认","iconCls":"icon-aduit","action":"hqInsteadBuy.audit(1,'确认')","type":18} ,								
			{"id":"top_btn_redo","title":"打回","iconCls":"icon-redo","action":"hqInsteadBuy.audit(99,'打回')","type":73},
			{"id":"top_btn_select","title":"选单","iconCls":"icon-add","action":"hqInsteadBuy.selectSave()","type":77},
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"hqInsteadBuy.operateLog()","type":81},
			{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":0}
		 ]/>	
	</#if>
	
	<#if "hq_insteadOf_mainBar"==type>
		 <@p.toolbar id="main_bar"	listData=[
			{"id":"main_btn_add","title":"新增","iconCls":"icon-add","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"hqInsteadBuy.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"hqInsteadBuy.clear()","type":0},
			{"id":"main_btn_del","title":"删除","iconCls":"icon-del","action":"hqInsteadBuy.batchDel()","type":3},		
			{"id":"main_btn_bSure","title":"确认","iconCls":"icon-aduit","action":"hqInsteadBuy.batchAudit(1,'确认')","type":18} ,								
			{"id":"main_btn_redo","title":"打回","iconCls":"icon-redo","action":"hqInsteadBuy.batchAudit(99,'打回')","type":73},
			{"id":"top_btn_build","title":"批量生成","iconCls":"icon-build-some","type":55,"action":"hqInsteadBuy.toBatchAdd()"},								
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"hqInsteadBuy.beforeListExport('mainDataGrid','/area_insteadOf_buy_balance/list_export','总部代采结算单列表')","type":4}
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"hqInsteadBuy.beforeDtlExport('mainDataGrid','transferInDtl','/hq_insteadOf_buy_balance/dtl_export','总部代采结算单明细')","type":4}
		]/>
	</#if>
	
	<#--总部代采地区确认-->
	<#if "hq_insteadOf_area_query"==type>
		<@p.toolbar id="top_bar" listData=[
            {"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"hqInsteadBuy.upBill()","type":0},
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"hqInsteadBuy.downBill()","type":0},
			{"id":"top_btn_fSure","title":"地区财务确认","iconCls":"icon-aduit","action":"hqInsteadBuy.audit(2,'地区财务确认')","type":75},	
			{"id":"top_btn_log","title":"审批日志","iconCls":"icon-xq","action":"hqInsteadBuy.operateLog()","type":81}
		 ]/>	
	</#if>
	
	<#if "hq_insteadOf_area_pay"==type>
		 <@p.toolbar id="main_bar"	listData=[
			{"id":"main_btn_add","title":"查看明细","iconCls":"icon-xq","action":"returnTab('mainTab', '单据明细')","type":0},
			{"id":"main_btn_search","title":"查询","iconCls":"icon-search","action":"hqInsteadBuy.search()","type":0},
			{"id":"main_btn_clear","title":"清空","iconCls":"icon-empty","action":"hqInsteadBuy.clear()","type":0},
			{"id":"main_btn_fSure","title":"地区财务确认","iconCls":"icon-aduit","action":"hqInsteadBuy.batchAudit(2,'地区财务确认')","type":75},	
			{"id":"main_btn_please","title":"生成请款单","iconCls":"icon-build-some","action":"hqInsteadBuy.BatchCreatePaymentBill(5)","type":82},
			{"id":"main_btn_ListExport","title":"列表导出","iconCls":"icon-export","action":"hqInsteadBuy.listExport('mainDataGrid','/area_insteadOf_buy_balance/list_export','总部代采地区应付结算单列表')","type":4},
			{"id":"main_btn_dtlExport","title":"明细导出","iconCls":"icon-export","action":"hqInsteadBuy.dtlExport('mainDataGrid','transferInDtl','/area_insteadOf_buy_balance/dtl_export','总部代采地区应付结算单明细')","type":4}
		]/>
	</#if>
	
<#-- 结算单门店结算) -->
    <#if "shop_balance_date_setBar"==type>
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"shopBalanceDate.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"shopBalanceDate.clear()", "type":0},
             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"shopBalanceDate.importOperation()","type":6},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"shopBalanceDate.exportExcel()","type":4},
             {"id":"btn-copy","title":"批量复制结算期","iconCls":"icon-copy","action":"shopBalanceDate.copyOneLine()","type":103},
             {"id":"btn-generator","title":"批量生成结算期","iconCls":"icon-build-some","action":"shopBalanceDate.showPanelToGeneratorBalanceDate()","type":55}
           ]
		/>
	</#if>
	 <#if "shop_balance_date_operaBar"==type>
		<@p.toolbar id="toolbar3" listData=[
			 {"id":"btn-add","title":"新增行","iconCls":"icon-add","action":"shopBalanceDate.add()", "type":0},
			 {"id":"btn-del","title":"删除行","iconCls":"icon-del","action":"shopBalanceDate.del()","type":0}, 
			 {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"shopBalanceDate.save()","type":7}				 
		    ]
		/>
	</#if>
	
	 <#if "cost_categoryset_setBar"==type>
		<@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action" : "dialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "action" : "dialog.clear()", "type":0},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action" : "dialog.exportExcel()", "type":4},
	             {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action" : "dialog.doEnable()", "type":27},
	             {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action" : "dialog.doUnable()", "type":100}
	           ]
			/>
	</#if>	
	<#if "cost_categoryset_operaBar"==type>
	<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		              {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"editor.save()","type":7}	
		           ]
				/>
	</#if>
	
	 <#if "mall_deduction_setBar"==type>
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"dialog.clear()", "type":0},   
	         {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action":"dialog.doEnable()", "type":27},
	         {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action":"dialog.doUnable()", "type":100},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}
           ]
		/>
	</#if>
	<#if "mall_deduction_operaBar"==type>
		<@p.toolbar id="dtlToolbar" listData=[
					 {"id":"btn-add","title":"新增行","iconCls":"icon-add","action":"editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove","action":"editor.deleteRow()","type":0}, 
		             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"editor.save()","type":7}				 
		           ]
		        />
	</#if>
	
	
	 <#if "mall_balance_difftype_setBar"==type>
		<@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action" : "dialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "action" : "dialog.clear()", "type":0},
	             {"id":"btn-enable","title":"启用","iconCls":"icon-unlock", "action" : "dialog.doEnable()", "type":27},
	             {"id":"btn-unable","title":"停用","iconCls":"icon-lock", "action" : "dialog.doUnable()", "type":100},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}
	           ]
			/>
	</#if>
	<#if "mall_balance_difftype_operaBar"==type>
	<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7}
		           ]
				/>
	</#if>
	
	<#if "bill_shop_balance_set_setBar"==type>
		<@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"billDialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"billDialog.clear()", "type":0},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"billDialog.exportExcel()","type":4},
	             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"billDialog.doImport()","type":6},
	             {"id":"btn-copy","title":"复制结算差异","iconCls":"icon-copy","action":"billDialog.copyOneLine()","type":8}
	           ]
		/>
	</#if>	
	<#if "bill_shop_balance_set_operaBar"==type>
		<@p.toolbar id="toolbar3" listData=[
			  {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		      {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		      {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7}
		    ]
		/>

	</#if>
	
	<#if "shop_balance_listMainBar"==type>
		 <@p.toolbar id="main_bar"	listData=[
		    {"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"dialog.toAdd()","type":0},
		    {"id":"top_btn_addall","title":"批量新增","iconCls":"icon-build-some","action":"bill.insertRows()","type":104},
			{"id":"btn-search1","title":"查询","iconCls":"icon-search", "type":0,"action":"dialog.search()"},
			{"id":"btn-remove","title":"清空","iconCls":"icon-empty","type":0,"action":"dialog.clear()"},
			{"id":"btn-del","title":"删除","iconCls":"icon-del","action":"bill.batchDelBill()","type":3},	
			{"id":"btn-confirm","title":"确认","iconCls":"icon-save","action":"bill.saveConfirm('1', '5')","type":18},	
			{"id":"btn-reconfirm","title":"反确认","iconCls":"icon-cancel","action":"bill.saveConfirm('1', '1')","type":10},     	         
	        {"id":"mian_btn_aduit","title":"审核","iconCls":"icon-aduit","action":"bill.confirm('1', '2')","type":31},	
			{"id":"top_btn_antiAudit","title":"反审核","iconCls":"icon-aduit","action":"bill.confirm('1', '5')","type":32},
			{"id":"mian_btn_build_biing","title":"生成开票申请","iconCls":"icon-build-some","type":78,"action":"invoiceApplyBalance.invoiceApply('shopBalanceDataGrid','10')"},	
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4},
			{"id":"mian_btn_export","title":"导出明细","iconCls":"icon-export","action":"bill.batchExportDetailExcel()","type":4},
			{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"batchPrintBalanceOrder()","type":20}
		]/>
	</#if>	
	
	<#if "shop_balance_listBar"==type>
		 <@p.toolbar id="top_bar" listData=[
                		{"id":"bill-dtl-btn-view","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
						{"id":"bill-dtl-btn-add","title":"新增","iconCls":"icon-add","action":"bill.toAddBill()","type":1},
						{"id":"bill-dtl-btn-save","title":"保存","iconCls":"icon-save","action":"bill.saveBill()","type":7},	 
						{"id":"bill-dtl-btn-del","title":"删除","iconCls":"icon-del","action":"bill.delBill()","type":3},	 
						{"id":"bill-dtl-btn-confirm","title":"确认","iconCls":"icon-save","action":"bill.saveConfirm('0', '5')","type":18},	
						{"id":"bill-dtl-btn-reconfirm","title":"反确认","iconCls":"icon-cancel","action":"bill.saveConfirm('0', '1')","type":10}, 
						{"id":"bill-dtl-btn-aduit","title":"审核","iconCls":"icon-aduit","action":"bill.confirm('0', '2')","type":31},	
						{"id":"bill-dtl-btn-antiAudit","title":"反审核","iconCls":"icon-aduit","action":"bill.confirm('0', '5')","type":32},         
						{"id":"bill-dtl-btn-export","title":"导出","iconCls":"icon-export","action":"bill.exportDetailExcel()","type":4},
						{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"printBalanceOrder()","type":20},
						{"id":"bill-dtl-btn-prev","title":"上单","iconCls":"icon-prev","action":"bill.upBill()","type":0} ,
						{"id":"bill-dtl-btn-next","title":"下单","iconCls":"icon-next","action":"bill.downBill()","type":0}
							
       ]/>
	</#if>	
	
	<#if "shop_balance_ResultListBar"==type>
	      <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"shopBalanceResult.search()", "type":0},
             {"id":"btn-remove1","title":"清空","iconCls":"icon-empty","action":"shopBalanceResult.clear()", "type":0},
             {"id":"btn-export1","title":"导出","iconCls":"icon-export","action":"shopBalanceResult.exportExcel()","type":4}
           ]
		/>
	</#if>	
	
	<#if "shop_balance_BackSectionSplitBar"==type>
	      <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"shopBalanceBackSectionSplit.search()", "type":0},
             {"id":"btn-remove1","title":"清空","iconCls":"icon-empty","action":"shopBalanceBackSectionSplit.clear()", "type":0},
             {"id":"btn-export1","title":"导出","iconCls":"icon-export","action":"shopBalanceBackSectionSplit.exportExcel()","type":4}
           ]
		/>
	</#if>	
	
	<#if "shop_balance_deduct_operaBar"==type>
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0,"action" : "deductDialog.search()"},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0,"action" : "deductDialog.clear()"},
	             {"id":"mian_btn_build_deduct","title":"生成费用","iconCls":"icon-build-some","type":98,"action":"deductDialog.createBalanceDeduct()"},
	             {"id":"btn-import-main","title":"导入","iconCls":"icon-import","action":"deductDialog.doImport()","type":6},
	             {"id":"mian_btn_beforeExport","title":"票前导出","iconCls":"icon-export","action":"deductDialog.exportExcel()","type":4},
	             {"id":"mian_btn_afterExport","title":"票后导出","iconCls":"icon-export","action":"deductDialogs.exportExcel()","type":4}
	           ]
			/>
	</#if>
	
	<#if "shop_balance_deduct_costafter_listBar"==type>
		 <@p.toolbar id="toolbar13" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "aftereditor.insertRowCheck()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "aftereditor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "aftereditor.saveCheck()", "type":7}		             
		           ]
		/>
	</#if>	
	
	<#if "shop_balance_deduct_costbefore_listBar"==type>
		 <@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "beforeeditor.insertRowCheck()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "beforeeditor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "beforeeditor.saveCheck()", "type":7}		             
		           ]
		/>
	</#if>
	
	<#if "shop_balance_balance_diff_operaBar"==type>
		 <@p.toolbar id="balance_diff_bar" listData=[
			{"id":"foot_btn_pre","title":"新增行","iconCls":"icon-add-dtl","action":"balanceDiffEditor.insertRow()","type":0},
			{"id":"foot_btn_next","title":"删除行","iconCls":"icon-del-dtl","action":"balanceDiffEditor.deleteRow()","type":0}
		 ]/>
	</#if>	
	
	<#if "shop_balance_balance_diff_listBar"==type>
	     <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0,"action":"dialog.search()"},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0,"action":"dialog.clear()"},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export", "type":4,"action":"dialog.exportExcel()"}
	           ]
			/>
	</#if>	
	
	<#if "shop_sale_order_listBar"==type>
	       <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0,"action":"dialog.search()"},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0,"action":"dialog.clear()"},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}	
	           ]
			/>
	</#if>	
	
	<#if "shop_sales_sum_listBar"==type>
	      <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search1","title":"查询","iconCls":"icon-search","action":"salesSumReport.search()", "type":0},
             {"id":"btn-remove1","title":"清空","iconCls":"icon-empty","action":"salesSumReport.clear()", "type":0},
             {"id":"btn-checkShopBalanceDate","title":"结算期检查","iconCls":"icon-aduit","action":"salesSumReport.checkShopBalanceDate()", "type":0},
             {"id":"btn-export1","title":"导出","iconCls":"icon-export","action":"salesSumReport.exportExcel()","type":4}
           ]
		/>
	</#if>	
	
	<#if "sales_outstanding_analysis_listBar"==type>
	      <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"salesOutstandingAnalysis.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"salesOutstandingAnalysis.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"salesOutstandingAnalysis.exportExcel()", "type":0}
           ]
		/>
	</#if>	
	
	<#if "invoice_template_set_listBar"==type>
		 <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":1},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":2},
             {"id":"btn-del111","title":"删除","iconCls":"icon-del","action":"invoice_template_set.batchDelBill()","type":3},
             {"id":"btn-copy","title":"批量复制","iconCls":"icon-copy","action":"invoice_template_set.copyOneLine()","type":103},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
           ]
		/>
	</#if>	
	<#if "invoice_template_set_operaBar"==type>
		<@p.toolbar id="dtltoolbar" listData=[
			{"id":"btn-add-detail","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
			{"id":"btn-edit-detail","title":"修改行","iconCls":"icon-edit","action" : "fas_common_editor.editRow()", "type":0},
			{"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","action" : "fas_common_editor.deleteRow()", "type":0}
		 ]
		/>
	</#if>

	<#if "bill_backsection_split_listBar"==type>
		 <@p.toolbar id="main_bar"	listData=[
			{"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0,"action":"billBacksectionSplit.search()"},		    	
			{"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"billBacksectionSplit.clear()", "type":0},
			{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"billBacksectionSplit.add()","type":0},
			{"id":"btn-del","title":"删除","iconCls":"icon-del","action":"billBacksectionSplit.batchDelBill()","type":0},   
			{"id":"btn-import","title":"导入","iconCls":"icon-import","action":"billBacksectionSplit.doImport()","type":0},
		    <!-- {"id":"btn-import","title":"导入(品牌)","iconCls":"icon-import","action":"billBacksectionSplit.doImportBrand()","type":0} --> 
			{"id":"mian_btn_export","title":"导出","iconCls":"icon-export","action":"billBacksectionSplit.exportExcel()","type":4}			
		]/>
	</#if>	
	<#if "bill_backsection_split_operaBar"==type>
		<@p.toolbar id="top_bar" listData=[
			{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"billBacksectionSplit.save()","type":7},
			{"id":"top_btn_save","title":"删除","iconCls":"icon-empty","action":"billBacksectionSplit.del()","type":7},
			{"id":"top_btn_retrun","title":"返回","iconCls":"icon-back","action":"billBacksectionSplit.back()","type":0}  		
			<!--{"id":"top_btn_audit","title":"审核","iconCls":"icon-aduit","action":"billBacksectionSplit.audit()","type":31} ,
			{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"billBacksectionSplit.upBill()","type":0} ,
			{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"billBacksectionSplit.downBill()","type":0}
			-->
		]/>
	</#if>
	
	<#if "bill_backsection_splitdtl_operaBar"==type>
		<@p.toolbar id="toolbar3" listData=[
			{"id":"top_btn_add","title":"新增明细","iconCls":"icon-add-dtl","action":"billBacksectionSplit.insertRow()","type":0},
		    {"id":"btn-remove","title":"删除明细","iconCls":"icon-del-dtl", "action" : "billBacksectionSplit.deleteRows()","type":0},
		    {"id":"top_btn_batchadd","title":"生成明细","iconCls":"icon-build-some","action":"billBacksectionSplit.insertRows()","type":0}
		    ]
		/>
	</#if>
	
	<#if "supplier_rate_set_setbar"==type>
		<@p.toolbar id="supplier_rate_set_setBarToolBar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"supplierRateSetDialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"supplierRateSetDialog.clear()", "type":0},
	             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"supplierRateSetDialog.exportExcel()","type":0},
	             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"supplierRateSetDialog.doImport()","type":0}
	           ]
		/>
	</#if>	
	<#if "supplier_rate_set_operaBar"==type>
		<@p.toolbar id="supplier_rate_set_operaBarToolBar" listData=[
			  {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "supplierRateSetEditor.insertRow()", "type":0},
		      {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "supplierRateSetEditor.deleteRow()","type":0},
		      {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "supplierRateSetEditor.save()", "type":0}
		    ]
		/>

	</#if>

</#macro>