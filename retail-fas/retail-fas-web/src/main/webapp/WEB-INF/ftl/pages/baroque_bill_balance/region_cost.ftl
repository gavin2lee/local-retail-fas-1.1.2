<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>巴洛克-地区价计算</title>
    <#include  "/WEB-INF/ftl/common/header.ftl" >
        <script type="text/javascript"
                src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
      <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
        <script type="text/javascript"
                src="${resourcesUrl}/fas/resources/js/modules/baroque_bill_balance/baroqueRegionCost.js?version=${version}"></script>
        <script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
        <script type="text/javascript"
                src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
        </head>
<body class="easyui-layout">
<div data-options="region:'north',border:false">
    <@p.toolbar id="region_top_bar" listData=[
		{"id":"top_btn_add","title":"查找","iconCls":"icon-search","action":"baroqueRegionCost.search()","type":0},
		{"id":"top_btn_add","title":"清空","iconCls":"icon-empty","action":"baroqueRegionCost.clear()","type":0},
		{"id":"top_btn_delete","title":"更新地区价","iconCls":"icon-del","action":"baroqueRegionCost.updateRegionCost()","type":153},
      	{"id":"btn-export","title":"导出","iconCls":"icon-export","action":"baroqueRegionCost.doExport()", "type":4}
    
    ]/>
    <div class="search-div">
        <form id="searchForm" method="post">
            <input type="hidden" name="id" id="id">
            <input type="hidden" name="askPaymentNo" id="askPaymentNo">
            <input type="hidden" name="invoiceNo" id="invoiceNo">
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
                                                                总部公司：
                    </th>
                    <td>
                        <input class="easyui-company ipt" name="salerName" id="salerNameCon"
                               data-options="inputNoField:'salerNoCon',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false,queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=notGroupLeadRole'"/>
                        <input type="hidden" name="salerNo" id="salerNoCon"/>
                    </td>
                    <th>
                                                                供应商：
                    </th>
                    <td><input class="ipt" singleSearch="supplier" name="supplierName"/>
                        <input type="hidden" name="supplierNo"/>
                    </td>

                    <th>
                      	验收日期：
                    </th>
                    <td>
                        <input class="easyui-datebox easyui-validatebox  ipt" defaultValue="startDate"
                               id="receiveStartDate" name="receiveStartDate"
                               data-options="maxDate:'receiveEndDate'"/>
                    </td>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                    <td>
                        <input class="easyui-datebox easyui-validatebox  ipt" defaultValue="endDate"
                               id="receiveEndDate" name="receiveEndDate"
                               data-options="minDate:'receiveStartDate'"/></td>
                </tr>
                <tr>
                	<th>
                                                                  款号：
                    </th>
                  <td>
                        <input class="easyui-styleNo ipt" name="styleName" id="styleNameCon"
                               data-options="inputNoField:'styleNo',inputNameField:'styleNoCon',inputWidth:160,isDefaultData : false"/>
                        <input type="hidden" name="styleNo" id="styleNoCon"/>
                    </td>
                    <th>验收单编码：</th>
                    <td><input class="ipt" name="originalBillNo" id="originalBillNo"/></td>
                    <th>采购订单号：</th>
                    <td><input class="ipt" name="orderNo"  id="orderNo"/>
                    <th>
                                                                地区公司：
                    </th>
                    <td>
                        <input class="easyui-company ipt" name="buyerName" id="buyerNameCon"
                               data-options="inputNoField:'buyerNoCon',inputNameField:'buyerNo',inputWidth:160,isDefaultData : false"/>
                        <input type="hidden" name="buyerNo" id="buyerNoCon"/>
                    </td>
                </tbody>
            </table>
        </form>
    </div>
</div>

      <div data-options="region:'center',border:false">
    	 <div class="easyui-tabs" id="mainTab" data-options="fit:true,plain:true,border:false" >
			<div title="单据明细" >
			    <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
				 onClickRowEdit="false" pagination="true" rownumbers="true"   pageSize="20" showFooter="true"
		           columnsJsonList="[
		              {field:'ck',title:'',checkbox:true,notexport:true},
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
 	                  {field : 'originalBillNo', title : '单据编码', width : 150,align:'center'},	
 	                  {field : 'sendDate', title : '发货日期', width : 80,align:'center'},	
	                  {field : 'receiveDate', title : '验收日期', width : 90,align:'center'},
  	                  {field : 'brandName', title : '品牌', width : 90,align:'center'},
	                  {field : 'oneLevelCategoryName', title : '大类', width : 90,align:'center'},
	                  {field : 'styleNo', title : '款号', width : 120,align:'center'},
	                  {field : 'itemCode', title : '商品编码', width : 120,align:'center'},
  	                  {field : 'itemName', title : '商品名称', width : 90,align:'center'},
	                  {field : 'tagPrice', title : '牌价', width : 90,align:'center'},
	                  {field : 'cost', title : '采购价', width : 100,align:'right',halign:'center'},
	                  {field : 'standardPurchasePrice', title : '采购本位币单价', width : 100,align:'right',halign:'center'},
	                  {field : 'receiveQty', title : '数量', width : 100,align:'right',halign:'center'},	
	                  {field : 'purchaseAmount', title : '采购金额', width : 100,align:'right',halign:'center'},
					  {field : 'currencyName', title : '币别', width : 100,align:'right',halign:'center'},
	                  {field : 'standardCurrencyName', title : '本位币', width : 100,align:'right',halign:'center'},	
	                  {field : 'exchangeRate', title : '汇率', width : 50,align:'left',halign:'center'},	
	                  {field : 'standardPurchaseAmount', title : '采购本位币金额', width : 100,halign:'center'},
	                  {field : 'tariffRate',title : '关税率',width : 50,align:'left',halign:'center'},
	                  {field : 'vatRate', title : '增值税率', width : 50,align:'center'},
					  {field : 'totalAmount', title : '总部本位币金额', width : 80,align:'center'},
					  {field : 'baseCost', title : '总部价', width : 80,align:'center'},	
					  {field : 'regionAmount', title : '地区金额', width : 100,halign:'center'},
					  {field : 'regionCost', title : '地区价', width : 100,halign:'center'},
	                  {field : 'salerName',title : '公司',width : 180,align:'left',halign:'center'},
	                  {field : 'receiveStoreName', title : '货管单位', width : 80,align:'center'},	
	                  {field : 'organName', title : '管理城市', width : 100,halign:'center'},
	                  {field : 'buyerName',title : '地区公司',width : 180,align:'left',halign:'center'},
	                  {field : 'orderNo',title : '采购订单号',width : 100,align:'left',halign:'center'},
	                  {field : 'twoLevelCategoryName',title : '二级大类',width : 180,align:'left',halign:'center'},
	                  {field : 'categoryName', title : '三级大类', width : 180,align:'center'},
	                  {field : 'remark', title : '备注', width : 180,align:'center'},
	                  {field : 'balanceNo', title : '结算单号', width : 180,align:'center'},
	                  {field : 'aId', title : 'id', width : 180,align:'center',hidden:'true'},
	                  {field : 'adlStatus', title : '是否手工', width : 50,align:'center',formatter:baroqueRegionCost.fomrmatStatus}
                  ]"
			                    jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
					                   	  console.log(rowData);
					                   	  baroqueRegionCost.openEditor(rowData);
					                   }
			         }' 
	             />  
			</div>
			<div title="单据汇总" >
	             <@p.datagrid id="sumDataGrid"   loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="true" singleSelect="false"  
				           columnsJsonList="[               
				                  {field : 'supplierName',title : '供应商',width : 150,align:'center',halign:'center'},
				                  {field : 'originalBillNo',title : '单据编码',width : 100,align:'center',halign:'center'}, 
				                  {field : 'sendDate',title : '发货日期',width : 150,align:'center',halign:'center'},
				                  {field : 'receiveDate',title : '验收日期',width : 100,align:'center',halign:'center'}, 
				                  {field : 'receiveQty',title : '数量',width : 150,align:'center',halign:'center'},
				                  {field : 'purchaseAmount',title : '采购金额',width : 100,align:'center',halign:'center'}, 
								  {field : 'currencyName', title : '币别', width : 100,align:'right',halign:'center'},
					              {field : 'standardCurrencyName', title : '本位币', width : 100,align:'right',halign:'center'},	
					              {field : 'exchangeRate', title : '汇率', width : 150,align:'left',halign:'center'},	
					              {field : 'standardPurchaseAmount', title : '采购本位币金额', width : 100,halign:'center'},
					              {field : 'salerName',title : '公司名称',width : 180,align:'left',halign:'center'},
	                  			  {field : 'receiveStoreName', title : '货管单位', width : 80,align:'center'},	
	                              {field : 'organName', title : '管理城市', width : 100,halign:'center'},
					              {field : 'orderNo',title : '采购订单号',width : 180,align:'left',halign:'center'},
					              {field : 'balanceNo', title : '结算单号', width : 80,align:'center'}
			                ]"/>
	             
			</div>
		 </div>
	  </div>
	  <div id="updateBuyAdl" class="easyui-dialog" data-options="closed:true"> 
	     <form name="updateBuyAdlForm" id="updateBuyAdlForm" method="post"  class="pd10">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:400px;"  title="数据信息" data-options="title:'数据信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<col width="120"/>
						<col/>
					   <tr height="40">
				        <th>
				        	<span class="ui-color-red">*</span>汇率：
				        </th>
			        	<td><input name="exchangeRate" class="easyui-numberbox" data-options="required:true,min:0,precision:4" />
			        	<input name="aId" type="hidden">
			        	</td>
			           </tr>
			           <tr height="40">
				        <th>
				        	<span class="ui-color-red">*</span>关税：
				        </th>
			        	<td><input name="tariffRate" class="easyui-numberbox" data-options="required:true,min:0,precision:4" />
			        	</td>
			           </tr>
			           <tr height="40">
				        <th>
				        	<span class="ui-color-red">*</span>增值税：
				        </th>
			        	<td><input name="vatRate" class="easyui-numberbox" data-options="required:true,min:0,precision:4" />
			        	</td>
			           </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
<div data-options="region:'south',border:false">
    <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
</div>

</body>
</html>