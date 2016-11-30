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
                    		{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"saleBalance.initAdd()","type":1},
							{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"saleBalance.save()","type":7} ,
							{"id":"top_btn_delete","title":"删除","iconCls":"icon-del","action":"saleBalance.del()","type":3} ,
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"saleBalance.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"saleBalance.downBill()","type":0},
							{"id":"top_btn_0","title":"确认","iconCls":"icon-aduit","action":"saleBalance.operate(2)","type":18} ,
							{"id":"top_btn_2","title":"反确认","iconCls":"icon-cancel","action":"saleBalance.operate(0)","type":10},
							{"id":"top_btn_3","title":"选单","iconCls":"icon-add","action":"saleBalance.toCreateBalance()","type":77},
							{"id":"top_btn_5","title":"日志","iconCls":"icon-info","action":"saleBalance.toOperateLog()","type":81},
							{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":20}	
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
				                                <td><input class="ipt disableEdit"  name="statusName" /><input type="hidden" name="status" id="status"/></td>
		                                        <th>单据名称：</th>
		                                        <td><input class="easyui-validatebox  ipt"  id="billName" name="billName"/></td>
		                                    	<th>采购调整额：</th>
		                                        <td><input class=" ipt disableEdit" name="customReturnAmount" /></td>
		                                    </tr>
		                                    <tr>
		                                   		<th><span class="ui-color-red">*</span>公司：</th>
		                                        <td><input class="ipt" singleSearch="company"  name="salerName" data-options="required:true" notGroupLeadRole/><input type="hidden" name="salerNo" /></td>
		                                        <th><span class="ui-color-red">*</span>客户：</th>
		                                        <td><input class="ipt" singleSearch="dataAccess_company"  name="buyerName" data-options="required:true"/><input type="hidden" name="buyerNo" /></td>
		                                    	<th>品牌部：</th>
		                                        <td><input class="ipt" singleSearch="brandUnit"  name="brandUnitName"/><input type="hidden" name="brandUnitNo"/></td>
		                                    	<th>大类：</th>
		                                        <td><input class="ipt" singleSearch="category"  name="categoryName"  /><input type="hidden" name="categoryNo"/></td>
		                                    </tr>
		                                    <tr>
		                                    	<th><span class="ui-color-red">*</span>结算期：</th>
											    <td><input class="easyui-datebox easyui-validatebox  ipt"  id= "balanceStartDate" defaultValue="startDate" name="balanceStartDate" data-options="required:true, maxDate:'balanceEndDate'" /></td>
												<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td><input class="easyui-datebox easyui-validatebox  ipt"  id= "balanceEndDate" defaultValue="endDate" name="balanceEndDate" data-options="required:true, minDate:'balanceStartDate'"/></td>	
		                                    	<th><span class="ui-color-red">*</span>结算日：</th>
											    <td><input class="easyui-datebox easyui-validatebox ipt" id="balanceDate"  defaultValue="currentDate" name="balanceDate" data-options="required:true" /></td>
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
		                                        <input class=" ipt" readonly="readonly" id ="extendCategoryName" name="extendCategoryName" style="width:336px"/>
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
								  		<th><span class="ui-color-red">*</span>品牌部：</th>
									    <td><input  class="ipt"  multiSearch="brandUnit"  data-options="required:true" /><input type="hidden" name="multiBrandUnitNo"/></td>
								   </tr>
								   <th>品牌：</th>
									   <td><input  class="ipt"  multiSearch="brand"  /><input type="hidden" name="multiBrandNo"/></td>
								   </tr>
								   <tr>
								        <th>公司：</th>
		                                <td><input class="ipt" notGroupLeadRole  multiSearch="company"  /><input type="hidden" name="multiSalerNo" /></td>
								   </tr>
								   <tr>
								        <th>客户：</th>
		                                <td><input class="ipt"  multiSearch="dataAccess_company"  /><input type="hidden" name="multiBuyerNo" /></td>
								   </tr>
								   <tr>
									    <th>大类：</th>
										<td><input  class="ipt"  multiSearch="category"  /><input type="hidden" name="multiCategoryNo"/></td>
								   </tr>
								   <tr>
									   <th>供应商类型：</th>
									   <td><input class="ipt"   multiSearch="supplierGroup"/><input type="hidden" name="supplierGroupNo" /></td>
								   </tr>
								   <tr>
									    <th>供应商：</th>
										<td><input class="ipt"  multiSearch="supplier"/><input type="hidden" name="supplierNo" /></td>
								   </tr>
								   <tr>
									    <th>二级大类：</th>
										<td><input  class="ipt"  multiSearch="twoLevelCategory"  /><input type="hidden" name="twoLevelCategoryNo"/></td>
								   </tr>
								   <tr>
									    <th>年份：</th>
										<td><input class="ipt"  multiSearch="years"/><input type="hidden" name="years" /></td>
								   </tr>
								   <tr>
                                    	<th>结算日：</th>
									    <td><input class="easyui-datebox easyui-validatebox  ipt"  name="balanceDate" defaultValue="currentDate"  data-options="required:true" /></td>
		                           </tr>
								   <tr>
                                    	<th>结算开始时间：</th>
									    <td><input class="easyui-datebox easyui-validatebox  ipt"  name="balanceStartDate" defaultValue="startDate"  id= "batchBalanceStartDate"  data-options="required:true, maxDate:'batchBalanceEndDate'"  /></td>
		                           </tr>
		                           <tr>
										<th>结算结束时间：</th>
										<td><input class="easyui-datebox easyui-validatebox  ipt"  name="balanceEndDate" defaultValue="endDate"  id= "batchBalanceEndDate"  data-options="required:true, minDate:'batchBalanceStartDate'"  /></td>		 
		                           </tr>
		                           <tr>
			                           <th>拆分品牌部：</th>
									   <td><input type="checkbox"  name ="splitBrandUnit" value="true"/></td>
								   </tr>	
								    <tr>
			                           <th>拆分大类：</th>
									   <td><input type="checkbox"  name ="splitCategory" value="true"/></td>
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
					 {field : 'brandName', title : '品牌', width : 80},
		           	  {field : 'organName', title : '管理城市', width : 100},
		           	  {field : 'itemCode', title : '商品编码', width : 150,align:'left'},
					  {field : 'itemName', title : '商品名称', width : 150,align:'left'},
	                  {field : 'cost', title : '地区价', width : 80,align:'rigth'},	
	                  {field : 'sendQty', title : '数量', width : 80},
					  {field : 'sendAmount', title : '金额', width : 80,align:'rigth'},
					  {field : 'oneLevelCategoryName', title : '大类', width : 80},
					  {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
					  {field : 'categoryName', title : '三级大类', width : 80}]" 
         />
</div>
</body>
</html>