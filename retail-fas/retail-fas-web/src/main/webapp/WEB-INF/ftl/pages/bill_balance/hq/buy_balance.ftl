<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>总部厂商-结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance/hq/buyBalance.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/BillBalanceController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body>
<input type="hidden" name="isArea" value="${isArea}" id="isArea" />
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<input type="hidden" name="warnPostUrl" value="${warnPostUrl}" id="warnPostUrl" />
<input type="hidden" name="balanceType" value="${balanceType}" id="balanceType" />
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
                    	    {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0},
                    		{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"buyBalance.initAdd()","type":1},
							{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"buyBalance.save()","type":7} ,
							{"id":"top_btn_delete","title":"删除","iconCls":"icon-del","action":"buyBalance.del()","type":3} ,
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"buyBalance.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"buyBalance.downBill()","type":0},
							{"id":"top_btn_1","title":"业务确认","iconCls":"icon-aduit","action":"buyBalance.operate(1)","type":74} ,
							{"id":"top_btn_2","title":"财务确认","iconCls":"icon-aduit","action":"buyBalance.operate(2)","type":79},
							{"id":"top_btn_3","title":"打回","iconCls":"icon-cancel","action":"buyBalance.operate(99)","type":73},
							{"id":"top_btn_4","title":"选单","iconCls":"icon-add","action":"buyBalance.toCreateBalance()","type":77},
							{"id":"top_btn_4","title":"结算调整","iconCls":"icon-edit","action":"buyBalance.toBalanceAdjust()","type":80},
							{"id":"top_btn_5","title":"日志","iconCls":"icon-info","action":"buyBalance.toOperateLog()","type":81},
							{"id":"print_btn","title":"打印","iconCls":"icon-print","action":"print()","type":20},
							{"id":"print_btn1","title":"核价范围控制","iconCls":"icon-edit","action":"pricingRange()","type":151}	
								
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
		                                        <td ><input class="ipt disableEdit" name="billNo" id="billNo"/></td>
		                                        <th>单据状态：</th>
				                                <td><input class="ipt disableEdit" name="statusName"/><input type="hidden" name="status" id="status"/></td>
		                                        <th>单据名称：</th>
		                                        <td><input class="easyui-validatebox  ipt"  id="billName" name="billName"/></td>
		                                        <th><span class="ui-color-red">*</span>币别：</th>
		                                        <td><input class="ipt" combobox="currency" name="currency"  data-options="required:true, editable:false"/></td> 	
		                                    </tr>
		                                    <tr>
		                                   		<th><span class="ui-color-red">*</span>公司：</th>
		                                        <td><input class="ipt" singleSearch="company"  name="buyerName" data-options="required:true" notGroupLeadRole/><input type="hidden" name="buyerNo" /></td>
		                                        <th><span class="ui-color-red">*</span>供应商：</th>
		                                        <td><input class="ipt" singleSearch="supplier"  name="salerName" data-options="required:true"/><input type="hidden" name="salerNo" /></td>
		                                        <th><span class="ui-color-red">*</span>品牌部：</th>
		                                        <td><input class="ipt" singleSearch="brandUnit"  name="brandUnitName" data-options="required:true"/><input type="hidden" name="brandUnitNo"/></td>
		                                    	<th><span class="ui-color-red">*</span>大类：</th>
		                                        <td><input class="ipt" singleSearch="category"  name="categoryName"  data-options="required:true"/><input type="hidden" name="categoryNo"/></td>
		                                    </tr>
		                                    <tr>
		                                    	<th><span class="ui-color-red">*</span>结算期：</th>
											    <td><input class="easyui-datebox easyui-validatebox  ipt" defaultValue="startDate" id= "balanceStartDate" name="balanceStartDate" data-options="required:true, maxDate:'balanceEndDate'" /></td>
												<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td><input class="easyui-datebox easyui-validatebox  ipt" defaultValue="endDate" id= "balanceEndDate" name="balanceEndDate" data-options="required:true, minDate:'balanceStartDate'"/></td>	
												<th><span class="ui-color-red">*</span>结算日：</th>
		                                        <td><input class="easyui-datebox easyui-validatebox  ipt" defaultValue="currentDate" id="balanceDate" name="balanceDate" data-options="required:true"/></td>
		                                    	<th>其他扣项：</th>
		                                        <td><input class=" ipt disableEdit" name="deductionAmount"/></td>
		                                    </tr>
		                                    <tr>
		                                        <th>进货数量：</th>
		                                        <td><input class=" ipt disableEdit" name="outQty"/></td>
		                                        <th>退货数量：</th>
		                                        <td><input class=" ipt disableEdit" name="returnQty"/></td>
		                                        <th>客残数量：</th>
		                                        <td><input class=" ipt disableEdit" name="customReturnQty"/></td>
		                                        <th>应付数量：</th>
		                                        <td><input class=" ipt disableEdit" name="balanceQty"/></td>
		                                    </tr>
		                                    <tr>
		                                        <th>进货金额：</th>
		                                        <td><input class=" ipt disableEdit" name="outAmount" /></td>
		                                        <th>退货金额：</th>
		                                        <td><input class=" ipt disableEdit" name="returnAmount" /></td>
		                                        <th>客残金额：</th>
		                                        <td><input class=" ipt disableEdit" name="customReturnAmount"/></td>
		                                        <th>应付金额：</th>
		                                        <td><input class=" ipt disableEdit" name="balanceAmount"/></td>
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
		                <div id="dtlDiv" data-options="region:'center',border:false" style="height:350px;">
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
									   <td><input  class="ipt"  multiSearch="brandUnit"  /><input type="hidden" name="multiBrandUnitNo"/></td>
								   </tr>
								   <tr>
								   <th>品牌：</th>
									   <td><input  class="ipt"  multiSearch="brand"  /><input type="hidden" name="multiBrandNo"/></td>
								   </tr>
								   <tr>
								        <th>公司：</th>
		                                <td><input class="ipt"  multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiBuyerNo" /></td>
								   </tr>
								   <tr>
								        <th>供应商：</th>
		                                <td><input class="ipt"  multiSearch="supplier"  /><input type="hidden" name="multiSalerNo" /></td>
								   </tr>
								   <tr>
									   <th>大类：</th>
									   <td><input  class="ipt"  multiSearch="category" /><input type="hidden" name="multiCategoryNo"/></td>
								   </tr>
								  <tr>
									   <th>供应商类型：</th>
									   <td><input class="ipt"   multiSearch="supplierGroup"/><input type="hidden" name="supplierGroupNo" /></td>
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
									    <th>性别：</th>
										<td><input  class="ipt"  multiSearch="gender"/><input type="hidden" name="gender" /></td>
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
								 </tbody>
							 </table>
						</div>
					 </form>	
			   </div>
			   <div id="pricingFormPanel" class="easyui-dialog" data-options="closed:true"> 
				     <form name="pricingForm" id="pricingForm" method="post"  >
				     	<input type="hidden" name ="id" id="pricingId"/>
				     	<div>
							 <table class="form-tb">
	                    	    <col width="120px"/>
	                    	 	<col />
	                            <tbody>
	                               <tr>
									   <th><span class="ui-color-red">*</span>品牌部：</th>
									   <td><input class="ipt" name="brandUnitNo" id="brandUnitPricing" combogrid="brandUnit"/></td>
								   </tr>
								   <tr>
									   <th><span class="ui-color-red">*</span>供应商类型：</th>
									   <td><input class="ipt"  name="supplierGroupNo" id="supplierGroupPricing" combogrid="supplierGroup"/></td>
								   </tr>
								   <tr>
									   <th><span class="ui-color-red">*</span>大类：</th>
									   <td><input class="ipt" name="categoryNo" id="categoryPricing" combotree="category" /></td>
								   </tr>
								   <tr>
									    <th>启用：</th>
										<td><input type="checkbox" id="isStart" value="1"/></td>
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
	                  {field : 'cost', title : '采购价', width : 80,align:'rigth'},	
	                  {field : 'sendQty', title : '数量', width : 80},
					  {field : 'sendAmount', title : '金额', width : 80,align:'rigth'},
					  {field : 'oneLevelCategoryName', title : '大类', width : 80},
					  {field : 'twoLevelCategoryName', title : '二级大类', width : 80},
					  {field : 'categoryName', title : '三级大类', width : 80}]" 
         />
</div>
</body>
</html>