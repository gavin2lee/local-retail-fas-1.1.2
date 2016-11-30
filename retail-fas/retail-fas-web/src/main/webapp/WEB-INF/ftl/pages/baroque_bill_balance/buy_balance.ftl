<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>巴洛克-结算单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.base.balanceController.1.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/baroque_bill_balance/baroquebuyBalance.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
                    	    {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0},
							{"id":"top_btn_delete","title":"删除","iconCls":"icon-del","action":"baroqueBuyBalance.del()","type":3} ,
							{"id":"top_btn_1","title":"审核","iconCls":"icon-aduit","action":"baroqueBuyBalance.doAudit(1)","type":31} ,
							{"id":"top_btn_2","title":"反审核","iconCls":"icon-aduit","action":"baroqueBuyBalance.doAudit(0)","type":32},
							{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"baroqueBuyBalance.upBill()","type":0} ,
							{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"baroqueBuyBalance.downBill()","type":0}
							
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
		                                    <th>供应商：</th>
		                                        <td><input class="ipt" singleSearch="supplier"  name="salerName"/><input type="hidden" name="salerNo" /></td>
		                                   		<th>公司：</th>
		                                         <td>
                            	<input class="easyui-company ipt"   name="buyerName" id="buyerNameCon" 
                            	data-options="inputNoField:'buyerNoCon',inputNameField:'buyerNameCon',inputWidth:160,isDefaultData : false"/>
                                <input type="hidden" name="buyerNo" id="buyerNoCon"/>
                            </td>
		                                        <th>结算期：</th>
											    <td><input class="easyui-datebox easyui-validatebox  ipt" defaultValue="startDate" id= "balanceStartDate" name="balanceStartDate" data-options="required:true, maxDate:'balanceEndDate'" /></td>
												<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td><input class="easyui-datebox easyui-validatebox  ipt" defaultValue="endDate" id= "balanceEndDate" name="balanceEndDate" data-options="required:true, minDate:'balanceStartDate'"/></td>	
												<th>结算日：</th>
												<td><input class="easyui-datebox easyui-validatebox  ipt" defaultValue="currentDate" id="balanceDate" name="balanceDate" data-options="required:true"/></td>
		                                    </tr>
		                                    <tr>
		                                    	<th>单据编码：</th>
		                                        <td ><input class="ipt disableEdit" name="billNo" id="billNo"/></td>
		                                        <th>单据状态：</th>
				                                <td><input class="ipt disableEdit" name="statusName"/><input type="hidden" name="status" id="status"/></td>
		                                        <th>结算数量：</th>
		                                        <td><input class="disableEdit"  id="outQty" name="outQty"/></td>
		                                        <th>结算金额：</th>
		                                        <td><input class="disableEdit"  id="balanceAmount" name="balanceAmount"/></td>
		                                        <th>币别：</th>
		                                        <td><input class="ipt disableEdit"  id="currencyName" name="currencyName"  data-options="editable:false"/></td> 	
		                                    </tr>
		                                    <tr>
		                                        <th>本位币：</th>
		                                        <td><input class=" ipt disableEdit" id="standardCurrencyName" name="standardCurrencyName"/></td>
		                                        <th>应付本位币金额：</th>
		                                        <td><input class=" ipt disableEdit" name="standardAmount" id="standardAmount"/></td>
		                                        <th>备注：</th>
		                                        <td><input class=" ipt disableEdit" name="remark"/></td>
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