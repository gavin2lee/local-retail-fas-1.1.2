<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>巴洛克- 费用分配</title>
    <#include  "/WEB-INF/ftl/common/header.ftl" >
        <script type="text/javascript"
                src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
                					
        <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/baroque_bill_balance/baroqueCostDistribution.js?version=${version}"></script>
        <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
        <script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
        <script type="text/javascript"
                src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
        </head>
<body class="easyui-layout">
<div data-options="region:'north',border:false">
    <@p.toolbar id="region_top_bar" listData=[
    {"id":"top_btn_add","title":"查找","iconCls":"icon-search","action":"baroqueCostDistribution.search()","type":0},
    {"id":"top_btn_add","title":"清空","iconCls":"icon-empty","action":"baroqueCostDistribution.clear()","type":0},
    {"id":"top_btn_back","title":"费用分配","iconCls":"icon-prev","action":"baroqueCostDistribution.openDistribute()","type":154},
    {"id":"top_btn_1","title":"导出","iconCls":"icon-export","action":"baroqueCostDistribution.exportExcel()","type":4}
    ]/>
    <div class="search-div">
        <form id="searchForm" method="post">
            <table class="form-tb">
                <col width="80"/>
                <col/>
                <col width="80"/>
                <col/>
                <col width="80"/>
                <col/>
                <col width="80"/>
                <col/>
                <tbody>
                <tr>
                    <th>
                                                               公司：
                    </th>
                    <td>
                        <input class="easyui-company ipt" name="buyerName" id="buyerNameCon"
                               data-options="inputNoField:'buyerNoCon',inputNameField:'buyerName',inputWidth:160,isDefaultData : false"/>
                        <input type="hidden" name="buyerNo" id="buyerNoCon"/>
                    </td>
                    <th>
                       	供应商：
                    </th>
                    <td>
                        <input class="easyui-supplier  ipt"  name="salerName" id="supplierNameId" data-options="inputNoField:'salerNoId',inputNameField:'supplierNameId',inputWidth:150"/>
						<input type="hidden" name="salerNo"  id="salerNoId"/>
                    </td>
                    <th>
                		验收日期：
                    </th>
                    <td>
                        <input class="easyui-datebox easyui-validatebox  ipt" defaultValue="startDate"
                               id="receiveStartDate" name="receiveStartDate"
                               data-options="required:true, maxDate:'receiveEndDate'"/>
                    </td>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                    <td>
                        <input class="easyui-datebox easyui-validatebox  ipt" defaultValue="endDate"
                               id="receiveEndDate" name="receiveEndDate"
                               data-options="required:true, minDate:'receiveStartDate'"/></td>
                </tr>
                <tr>
                    <th>验收单编码：</th>
                    <td><input class="ipt" name="originalBillNo" id="originalBillNo"/></td>
                    <th>采购订单号：</th>
                    <td><input class="ipt" name="orderNo"/></td>
                </tbody>
            </table>
        </form>
    </div>
</div>
<div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true" id="dtlDataGrid">
        <#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="true" singleSelect="false"  
				           columnsJsonList="[
				           		  {field:'ck',title:'',checkbox:true,notexport:true},                
				                  {field : 'purchaseFee',title : '采购费用',width : 100,align:'center',halign:'center'}, 
				                  {field : 'supplierName',title : '供应商',width : 150,align:'center',halign:'center'},
				                  {field : 'originalBillNo',title : '单据编码',width : 100,align:'center',halign:'center'}, 
				                  {field : 'sendDate',title : '发货日期',width : 150,align:'center',halign:'center'},
				                  {field : 'receiveDate',title : '验收日期',width : 100,align:'center',halign:'center'}, 
				                  {field : 'receiveQty',title : '数量',width : 150,align:'center',halign:'center'},
				                  {field : 'cost',title : '采购金额',width : 100,align:'center',halign:'center'}, 
				                  {field : 'currencyName',title : '币别',width : 150,align:'center',halign:'center'},
				                  {field : 'buyerName',title : '公司',width : 100,align:'center',halign:'center'}, 
				                  {field : 'orderUnitName',title : '货管单位',width : 150,align:'center',halign:'center'},
				                  {field : 'organName',title : '管理城市',width : 100,align:'center',halign:'center'}, 
				                  {field : 'orderNo',title : '采购订单号',width : 150,align:'center',halign:'center'},
				                  {field : 'balanceNo',title : '结算单号',width : 150,align:'center',halign:'center'}
			                ]"/>
			</div>
    </div>
</div>

<div id="dlg" class="easyui-dialog" title="采购费用分配" style="width:400px;height:200px;padding:10px"
            data-options="
                iconCls: 'icon-save',
                buttons: [{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                       baroqueCostDistribution.saveDistribute();
                    }
                },{
                    text:'取消',
                    handler:function(){
                        $('#dlg').dialog('close');
                    }
                }]">
            <form id="ff" method="post">
		       <table cellpadding="10">
			        <tr>
			            <td>费用:</td>
			            <td><input class="easyui-textbox" type="text" id="inputPurchaseFee"></input></td>
			        </tr>
			   </table>
	        </form>
    </div>

<div data-options="region:'south',border:false">
    <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
</div>
</body>
</html>