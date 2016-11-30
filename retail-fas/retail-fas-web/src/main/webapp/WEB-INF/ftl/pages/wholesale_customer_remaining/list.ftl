<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客户余额</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_customer_remaining/WholesaleCustomerRemaining.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_balance_zone/wholesale_zone_plug.js?version=${version}"></script>

</head>
<body>
<input type="hidden" id="isHq" name="isHq" value="${isHq}"/>
<input type="hidden" name="queryCondition" id="queryCondition" value="${queryCondition}">
<input type="hidden" id ="warnPostUrl" name="warnPostUrl" value="${warnPostUrl}" />
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,border:false,plain:true" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="toor_bar" listData=[
                    	    {"id":"top_btn_add","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0}
                    	    {"id":"top_btn_export","title":"导出","iconCls":"icon-export","action":"wholesaleController.doExport('dtlDataGrid','/wholesale_customer_remaining_dtl/export','客户余额表流水导出')","type":0}			
                    	    
						 ]/>	
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			                <div data-options="region:'north',border:false">
		                	 	 <div class="search-div" data-options="fit:true">
			                        <#-- 主档信息  -->
			                        <form id="mainDataForm"  method="post">
			                        	 <input type="hidden" name="id" id="id">
			                        	 <input type="hidden" name="balanceType" id="balanceType">	
			                        	 <table class="form-tb">
			                        	    <col width="100px"/>
			                        	 	<col />
			                        	 	<col width="100px"/>
			                        	 	<col />
			                        	 	<col width="100px"/>
			                        	 	<col />
			                        	 	<col width="100px"/>
			                        	 	<col />
			                               <tbody>
		                                    	<tr>
		                                    		<input type="hidden" name="id" id="id"/>
						                       		<th><span class="ui-color-red">*</span>公司名称：</th>
						                            <td>
						                                <input class="ipt disableEdit" id="companyName" name="companyName" data-options="required:true,inputWidth:160"/>
						                            	<input type="hidden" name="companyNo" id="companyNo"/>
						                            </td>
						                            <th><span class="ui-color-red">*</span>客户名称：</th>
						                            <td>
						                            	<input class="ipt disableEdit" name="customerName" id="customerName" data-options="required:true,inputWidth:160"/>
						                            	<input type="hidden" name="customerNo" id="customerNo"/>
						                            </td>
						                            <th><span class="ui-color-red">*</span>客户可用余额：</th>
						                            <td>
						                            	<input class="ipt disableEdit" name="remainingAmount" id="remainingAmount" data-options="required:true,inputWidth:100"/>
						                            </td>
						                        </tr>
						                        <tr>
						                        	<th><span class="ui-color-red">*</span>合同保证金：</th>
						                            <td>
						                            	<input class="ipt disableEdit" name="marginAmount" id="marginAmount" data-options="required:true,inputWidth:100"/>
						                            </td>
						                            <th><span class="ui-color-red">*</span>保证金余额：</th>
						                            <td>
						                            	<input class="ipt disableEdit" name="marginRemainderAmount" id="marginRemainderAmount" data-options="required:true,inputWidth:100"/>
						                            </td>
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
			              <div class="easyui-layout" data-options="fit:true,border:false">
	             	</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>