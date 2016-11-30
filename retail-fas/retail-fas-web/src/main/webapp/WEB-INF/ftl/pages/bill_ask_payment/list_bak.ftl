<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>请款单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/BillController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_ask_payment/billAskPayment.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body>
<input type="hidden" name="queryCondition" id="queryCondition" value="${queryCondition}">
<input type="hidden" name="isHQ" id="isHQ" value="${isHQ}">
<input type="hidden" name="billNoMenu" id="billNoMenu" value="${billNoMenu}">
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,border:false,plain:true" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="toor_bar" listData=[
                    	    {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0},
							{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"billController.initAdd()","type":1},
							{"id":"top_btn_delete","title":"删除","iconCls":"icon-del","action":"billController.del()","type":3} ,
							{"id":"top_btn_save","title":"保存","iconCls":"icon-del-dtl","action":"billController.save()","type":7},
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"billController.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"billController.downBill()","type":0},
							{"id":"top_btn_aduit","title":"提交审批","iconCls":"icon-aduit","action":"billController.operate(1)","type":92} ,
							{"id":"top_btn_cancel","title":"撤销","iconCls":"icon-cancel","action":"billController.operate(0)","type":13} ,
							{"id":"top_btn_cancel1","title":"业务主管审批","iconCls":"icon-aduit","action":"billController.operate(2)","type":93},
							{"id":"top_btn_cancel2","title":"财务人员审批","iconCls":"icon-aduit","action":"billController.operate(3)","type":94},
							{"id":"top_btn_cancel4","title":"打回","iconCls":"icon-cancel","action":"billController.back(99)","type":73},
							{"id":"top_btn_export","title":"导出","iconCls":"icon-next","action":"common_util.doExportBill(exportFields,'dtlDataGrid','/bill_ask_payment/export_bill','请款单',{billNo:$('#billNo').val()})","type":4},
							{"id":"top_btn_5","title":"日志","iconCls":"icon-cancel","action":"billController.toOperateLog(3)","type":81},
							{"id":"mian_btn_print","title":"打印","iconCls":"icon-print","action":"print()","type":0}	
						 ]/>	
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			                <div data-options="region:'north',border:false">
		                	 	 <div class="search-div" data-options="fit:true">
			                        <#-- 主档信息  -->
			                        <form id="mainDataForm"  method="post">
			                        	 <input type="hidden" name="id" id="id">
			                        	 <table class="form-tb">
										    <col width="80" />
										    <col  />
										    <col width="80" />
										    <col />
										    <col width="80" />
										    <col />
										    <col width="80"/>
										    <col />
			                               <tbody>
			                                    <tr>
			                                        <th>单据编号：</th>
			                                        <td><input class="ipt disableEdit"  name="billNo" id="billNo"/></td>
			                                        <th>单据状态：</th>
			                                        <td><input class="ipt disableEdit"  name="statusName"/><input type="hidden" name="status" id="status"/></td>
			                                    	<th><span class="ui-color-red">*</span>日期：</th>
			                                        <td><input class="ipt easyui-datebox easyui-validatebox" id="billDate" name="billDate" data-options="required:true"/></td>
			                                        <th>请款金额：</th>
			                                        <td><input class="ipt disableEdit"  name="allAmount" id="allAmount"/><input type="hidden" id="allQty" name="allQty"/></td>
			                                    </tr>
			                                    <tr>
			                                        <th><span class="ui-color-red">*</span>公司：</th>
			                                        <td><input class="ipt"  singleSearch="company"  name="buyerName"  data-options="required:true,callback:callback" ${fliterCompany}/><input type="hidden" name="buyerNo" /></td>
			                                        <th><span class="ui-color-red">*</span>供应商：</th>
			                                        <td><input class="ipt"  singleSearch="organization"  name="salerName"  data-options="required:true,callback:callback"/><input type="hidden" name="salerNo" /></td>
			                                    	<th><span class="ui-color-red">*</span>结算单：</th>
			                                        <td><input class="ipt" singleSearch name="balanceNo" id="refBillNo" data-options="required:true"/></td>
			                                        <th>结算金额：</th>
			                                        <td><input class="ipt disableEdit"  name="balanceAmount" id="refAmount"/><input type="hidden" name="balanceType" id="balanceType"></td>
			                                    </tr>
			                                    <tr>
			                                    	<th><span class="ui-color-red">*</span>币别：</th>
			                                        <td><input class="ipt" combobox="tsCurrency" name="currencyNo" data-options="required:true"/></td>
			                                    	<th>备注：</th>
			                                        <td colspan ="7"><input class="easyui-validatebox  ipt"  id="remark" name="remark" style="width:99%"/></td>	 
		                                   		</tr>
			                                </tbody>
			                            </table>
									 </form>
			                    </div>
			               	</div>
			               	<div data-options="region:'center',border:false" style="height:350px;">
		                	 	<div id="dtlTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
		                		</div>
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