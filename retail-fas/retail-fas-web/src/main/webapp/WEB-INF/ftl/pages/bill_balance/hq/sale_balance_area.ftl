<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/saleBalance.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/BillBalanceController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_invoice_apply/invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>

</head>
<body>
<input type="hidden" name="isArea" value="${isArea}" id="isArea" />
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
                    	    {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0},
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"saleBalance.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"saleBalance.downBill()","type":0},
							{"id":"top_btn_1","title":"确认","iconCls":"icon-aduit","action":"saleBalance.operate(4)","type":18},
							{"id":"top_btn_2","title":"反确认","iconCls":"icon-cancel","action":"saleBalance.operate(2)","type":10},
							{"id":"top_btn_2","title":"打回","iconCls":"icon-cancel","action":"saleBalance.operate(99)","type":73},
							{"id":"top_btn_5","title":"审批日志","iconCls":"icon-info","action":"saleBalance.toOperateLog()","type":81}
						 ]/>	
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="mainDataForm"  method="post">
		                        	 <input type="hidden" name="id" id="id">
		                        	 <input type="hidden" name="invoiceApplyNo" id="invoiceApplyNo">
		                        	 <table class="form-tb">
	                        	    	<col width="120px"/>
		                        	 	<col />
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
		                                        <th>单据状态：</th>
				                                <td><input class="easyui-validatebox  ipt"  name="extendStatusName" id="extendStatusName"/><input type="hidden" name="status" id="status"/><input type="hidden" name="extendStatus" id="extendStatus"/></td>
		                                        <th>单据名称：</th>
		                                        <td><input class="easyui-validatebox  ipt"  id="billName" name="billName"/></td>
		                                  		<th>采购调整额：</th>
		                                        <td><input class=" ipt disableEdit" name="customReturnAmount" /></td>
		                                    </tr>
		                                    <tr>
		                                   		<th><span class="ui-color-red">*</span>公司：</th>
		                                        <td><input class="ipt"   name="salerName"  /><input type="hidden" name="salerNo" /></td>
		                                        <th><span class="ui-color-red">*</span>客户：</th>
		                                        <td><input class="ipt"   name="buyerName" /><input type="hidden" name="buyerNo" /></td>
		                                    	<th width="75px">品牌部：</th>
		                                        <td><input class="ipt"  name="brandUnitName" /><input type="hidden" name="brandUnitNo"/></td>
		                                    	<th width="75px">一级大类：</th>
		                                        <td><input class="ipt"   name="categoryName"  /><input type="hidden" name="categoryNo"/></td>
		                                    </tr>
		                                    <tr>
		                                    	<th><span class="ui-color-red">*</span>结算期间：</th>
											    <td><input class="easyui-datebox easyui-validatebox  ipt"  id= "balanceStartDate" name="balanceStartDate" data-options="required:true, maxDate:'balanceEndDate'" /></td>
												<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td><input class="easyui-datebox easyui-validatebox  ipt"  id= "balanceEndDate" name="balanceEndDate" data-options="required:true, minDate:'balanceStartDate'"/></td>	
		                                    	<th><span class="ui-color-red">*</span>结算日：</th>
											    <td><input class="easyui-datebox easyui-validatebox ipt" id="balanceDate"  name="balanceDate" data-options="required:true" /></td>
		                                    	<th><span class="ui-color-red">*</span>币别：</th>
		                                        <td><input class="ipt" combobox="currency" name="currency"  data-options="required:true, editable:false"/></td>
		                                    </tr>
		                                    <tr>
		                                        <th>出库数量：</th>
		                                        <td><input class="ipt disableEdit"  name="outQty"  /></td>
		                                        <th>原残数量：</th>
		                                        <td><input class="ipt disableEdit"  name="returnQty"  /></td>
		                                        <th>扣项数量：</th>
		                                        <td><input class=" ipt disableEdit" id="deductionQty" name="deductionQty"/></td>
		                                        <th>应收数量：</th>
		                                        <td><input class="ipt disableEdit"  id="balanceQty" name="balanceQty"  /></td>
		                                    </tr>
		                                    <tr>
		                                        <th>出库金额：</th>
		                                        <td><input class="ipt disableEdit"  name="outAmount" /></td>
		                                        <th>原残金额：</th>
		                                        <td><input class="ipt disableEdit"  name="returnAmount" /></td>
		                                        <th>扣项金额：</th>
		                                        <td><input class=" ipt disableEdit" id="deductionAmount" name="deductionAmount"/></td>
		                                        <th>应收金额：</th>
		                                        <td><input class="ipt disableEdit"  id="balanceAmount" name="balanceAmount" /></td>
		                                    </tr>
		                                    <tr>
		                                    	<th>扩展分类：</th>
		                                        <td colspan ="3">
		                                        <input class=" ipt" readonly="readonly" id ="extendCategoryName" name="extendCategoryName" style="width:376px"/>
		                                        <input hidden="true" class="ipt" name="extendCategoryNo" id="extendCategoryNo"/>
		                                        <input hidden="true" class="ipt" name="extendCategoryCondition" id="extendCategoryCondition"/>
		                                        </td>
		                                    	<th>备注：</th>
		                                        <td colspan ="3"><input class="easyui-validatebox ipt"  id="remark" name="remark" style="width:98%"/></td>	 
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
	                    	    <col width="120px"/>
	                    	 	<col />
	                            <tbody>
								   <tr>
								        <th>公司：</th>
		                                <td><input class="easyui-validatebox  ipt"  multiSearch="company" fliterType="notGroupLeadRole" name="salerName" data-options="required:true" /><input type="hidden" name="salerNo" /></td>
								   </tr>
								   <tr>
								        <th>客户：</th>
		                                <td><input class="easyui-validatebox  ipt"  multiSearch="company"  name="buyerName" data-options="required:true" /><input type="hidden" name="buyerNo" /></td>
								   </tr>
								   <tr>
									   <th width="75px">品牌部：</th>
									   <td><input  class="ipt"  multiSearch="brandUnit"  name="multiBrandUnitName"  data-options="required:true"/><input type="hidden" name="multiBrandUnitNo"/></td>
								   </tr>
								   <tr>
									   <th width="75px">大类：</th>
									   <td><input  class="ipt"  multiSearch="category" name="multiCategoryName"  data-options="required:true"/><input type="hidden" name="multiCategoryNo"/></td>
								   </tr>
								   <tr>
                                    	<th width="75px">结算日：</th>
									    <td><input class="easyui-datebox easyui-validatebox  ipt"  name="balanceDate" defaultValue="currentDate"  data-options="required:true" /></td>
		                           </tr>
								   <tr>
                                    	<th width="75px">结算开始时间：</th>
									    <td><input class="easyui-datebox easyui-validatebox  ipt"  name="balanceStartDate" defaultValue="startDate"  id= "batchBalanceStartDate"  data-options="required:true, maxDate:'batchBalanceEndDate'"  /></td>
		                           </tr>
		                           <tr>
										<th width="75px">结算结束时间：</th>
										<td><input class="easyui-datebox easyui-validatebox  ipt"  name="balanceEndDate" defaultValue="endDate"  id= "batchBalanceEndDate"  data-options="required:true, minDate:'batchBalanceStartDate'"  /></td>		 
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
<div style="display:none">
      <@p.datagrid id="exportExcelDG" 
	           columnsJsonList="[
					  {field : 'itemCode', title : '商品编码', width : 150,align:'left'},
					  {field : 'itemName', title : '商品名称', width : 150,align:'left'},
					  {field : 'organName', title : '管理城市', width : 100},
	                  {field : 'cost', title : '地区价', width : 80,align:'rigth'},	
	                  {field : 'sendQty', title : '数量', width : 80},
					  {field : 'sendAmount', title : '金额', width : 80,align:'rigth'},
					  {field : 'brandName', title : '品牌', width : 80},
					  {field : 'oneLevelCategoryName', title : '一级大类', width : 80},
					  {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
					  {field : 'categoryName', title : '三级大类', width : 80},
					  {field : 'remark', title : '备注', width : 80}]" 
         />
</div>
</body>
</html>