<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>体总厂商-结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/pe_balance/peBalance.js?version=${version}"></script>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
                    	    {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0},
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"peBalance.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"peBalance.downBill()","type":0},
							{"id":"top_btn_delete","title":"删除","iconCls":"icon-del","action":"peBalance.del()","type":0} ,
							{"id":"top_btn_1","title":"审核","iconCls":"icon-aduit","action":"peBalance.doAudit(1)","type":0} ,
							{"id":"top_btn_2","title":"反审核","iconCls":"icon-aduit","action":"peBalance.doAudit(0)","type":0}
						 ]/>
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="mainDataForm"  method="post">
		                        	 <input type="hidden" name="id" id="id">
		                        	 <input type="hidden" name="askPaymentNo" id="askPaymentNo">
		                        	 <input type="hidden" name="invoiceNo" id="invoiceNo">
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
		                                        <td ><input class="ipt" name="billNo" id="billNo"/></td>
		                                   		<th>公司：</th>
		                                        <td><input class="ipt"  name="buyerName" /></td>
		                                        <th>供应商：</th>
		                                        <td><input class="ipt"  name="salerName" /></td>
		                                        <th>品牌：</th>
		                                        <td><input class="ipt"  name="brandName" /></td>
		                                    </tr>
		                                    <tr>
		                                    	<th>单据状态：</th>
				                                <td><input class="ipt " name="peStatusName"/> <input type="hidden" id="status" name="status"/> </td>
		                                    	<th>结算期：</th>
											    <td><input class="ipt"  id= "balanceStartDate" name="balanceStartDate"/></td>
												<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td><input class="ipt"  id= "balanceEndDate" name="balanceEndDate"/></td>	
		                                        <th>结算数量：</th>
		                                        <td><input class="ipt"  name="balanceQty" /></td>
		                                    </tr>
		                                    <tr>
		                                        <th>厂商金额：</th>
		                                        <td><input class=" ipt " name="supplierAmount" /></td>
		                                        <th>结算金额：</th>
		                                        <td><input class=" ipt " name="outAmount" /></td>
		                                        <th>采购调整额：</th>
		                                        <td><input class=" ipt " name="customReturnAmount" /></td>
		                                        <th>扣项金额：</th>
		                                        <td><input class=" ipt " name="deductionAmount"/></td>
		                                    </tr>
		                                    <tr>
		                                    	<th>应付金额：</th>
		                                        <td><input class=" ipt " name="balanceAmount"/></td>
		                                    </tr>
		                                </tbody>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                <div id="dtlDiv" data-options="region:'center',border:false" style="height:350px;">
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