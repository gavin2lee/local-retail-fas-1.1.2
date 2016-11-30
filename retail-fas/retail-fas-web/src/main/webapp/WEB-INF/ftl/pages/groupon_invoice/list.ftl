<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/buyBalance.js?version=${version}"></script>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,cls:'pd10',border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
							{"id":"top_btn_add","title":"生成结算单","iconCls":"icon-add","action":"buyBalance.showDialog()","type":0},
							{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"buyBalance.save()","type":0} ,
							{"id":"top_btn_delete","title":"删除","iconCls":"icon-del","action":"buyBalance.del()","type":0} ,
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"buyBalance.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"buyBalance.downBill()","type":0},
							{"id":"top_btn_back","title":"业务确认","iconCls":"icon-prev","action":"buyBalance.operate(2)","type":0} ,
							{"id":"top_btn_next","title":"财务确认","iconCls":"icon-next","action":"buyBalance.operate(3)","type":0}
						 ]/>	
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="mainDataForm"  method="post">
		                        	 <input type="hidden" name="id" id="id">
		                        	 <table class="form-tb">
		                        	    <col width="120px"/>
		                        	 	<col />
		                        	 	<col width="120px"/>
		                        	 	<col />
		                        	 	<col width="120px"/>
		                        	 	<col />
		                                <tbody>
		                                    <tr>
		                                    	<th>单据编号：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="billNo" id="billNo"/></td>
		                                        <th>单据名称：</th>
		                                        <td colspan ="3"><input class="easyui-validatebox  ipt"  id="billName" name="billName" style="width:375px"/></td>
		                                    </tr>
		                                    <tr>
		                                   		<th>结算主体：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="buyerName" /><input type="hidden" name="buyerNo"/></td>
		                                        <th>供应商：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="salerName" /><input type="hidden" name="salerNo"/></td>
		                                        <th>品牌：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="brandName" /><input type="hidden" name="brandNo"/></td>
		                                    </tr>
		                                    <tr>
		                                        <th>入库金额：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="entryAmount"  /></td>
		                                        <th>残鞋金额：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="returnAmount" /></td>
		                                        <th>扣项金额：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="deductionAmount" /></td>
		                                    </tr>
		                                    <tr>
		                                    	<th>应付金额：</th>
		                                        <td><input class="easyui-validatebox  ipt"  name="payableAmount" /></td>
		                                    	<th>结算期：</th>
											    <td><input class="easyui-validatebox  ipt"  name="balanceEndDate"  /></td>
												<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td><input class="easyui-validatebox  ipt"  name="balanceEndDate"  /></td>		 
		                                    </tr>
		                                    <tr>
		                                    	<th>备注：</th>
		                                        <td colspan ="5"><input class="easyui-validatebox  ipt"  style="width:625px" id="remark" name="remark" /></td>	 
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
             	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
				     <form name="dataForm" id="dataForm" method="post"  >
				     	<div>
							 <table class="form-tb">
	                    	    <col width="100px"/>
	                    	 	<col />
	                            <tbody>
								   <tr>
								        <th>结算主体：</th>
		                                <td><input class="easyui-validatebox  ipt"  iptSearch="company"  name="buyerName" id="companyName" /><input type="hidden" name="buyerNo" id="companyNo" /></td>
								   </tr>
								   <tr>
								        <th>供应商：</th>
		                                <td><input class="easyui-validatebox  ipt"  iptSearch="supplier"  name="salerName" id="supplierName"/><input type="hidden" name="salerNo" id="supplierNo" /></td>
								   </tr>
								   <tr>
                                    	<th>结算开始时间：</th>
									    <td><input class="easyui-datebox  ipt"  name="balanceStartDate" id="balanceStartDate" /></td>
		                           </tr>
		                           <tr>
										<th>结算结束时间：</th>
										<td><input class="easyui-datebox  ipt"  name="balanceEndDate" id="balanceEndDate" /></td>		 
		                           </tr>
		                           <tr>
									   	<th>品牌：</th>
									    <td><input class="easyui-combobox ipt" name="brandNo" id="brandNo" data-options="valueField:'brandNo',textField:'name',url:BasePath + '/brand/getJsonData'"/><input type="hidden" name="brandName" id="brandName"></td>
								   </tr>
								 </tbody>
							 </table>
						</div>
					 </form>	
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