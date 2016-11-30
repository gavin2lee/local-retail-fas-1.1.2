<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>应付账款表</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/top_sports/payableAccount.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "payableAccount.search()", "type":0},
	         {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "payableAccount.clear()","type":0},
	         {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "payableAccount.doExport('/payable_account/export')","type":4}
	       ]
		/>
	</div>
   
	<div data-options="region:'center',border:false" >
	  <div class="easyui-layout" data-options="fit:true" id="subLayout">
	    <div data-options="region:'north',border:false" >
		  <div class="search-div">
            <form name="searchForm" id="searchForm" method="post">
				 <table class="form-tb">
					<col width="100" />
					<col />
					<col width="100" />
					<col />
					<col width="100" />
					<col />	
					<col width="100" />
					<col />							
					<tbody>
						<tr>
							<th>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
							<td>
								<input class="easyui-company ipt"   name="buyerName" id="buyerNameId" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:160,isDefaultData : false"/>
								<input type="hidden" name="buyerNo" id="buyerNoId"/>
							</td>
						    <th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
							<td>
								<input class="easyui-supplier ipt"  name="salerName" id="salerNameId" data-options="inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160"/>
								<input type="hidden" name="salerNo" id="salerNoId" />
							</td>
							<th>日期： </th>
							<td>
								<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'" style="width: 150px;" readonly="true"/>
							</td>
							<th>至： </th>
							<td>
								<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" style="width: 150px;" readonly="true"/>
							</td>
						</tr>					
					</tbody>
				</table>
			</form>
		 </div>
	  </div>
	  
      <div data-options="region:'center',border:false">
    	 <div class="easyui-tabs" id="mainTab" data-options="fit:true,plain:true,border:false" >
			<div title="明细" >
			     <@p.datagrid id="dataDGridJG"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
			      onClickRowEdit="false" pagination="true"  rownumbers="true"   pageSize="20" showFooter="true"
		          	 columnsJsonList="[
	                  {field : 'buyerNo', title : '公司编码', width : 80,align:'center'},	
	                  {field : 'buyerName',title : '公司名称',width : 180,align:'left',halign:'center'},
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
	                  {field : 'billDate', title : '日期', width : 90,align:'center'},
	                  {field : 'earlyPayableAmount', title : '期初应付账款', width : 100,align:'right',halign:'center'},	
	                  {field : 'currentPayableAmount', title : '本期应付金额', width : 100,align:'right',halign:'center'},	
					  {field : 'currentPayment', title : '本期付款', width : 100,align:'right',halign:'center'},
	                  {field : 'balance', title : '余额', width : 100,align:'center',halign:'center'},
	                  {field : 'billNo', title : '单据编码', width : 150,align:'left',halign:'center'},	
	                  {field : 'billTypeName', title : '单据类型', width : 100,halign:'center'}
	                ]" 
            	 />
			</div>
			
			<div title="汇总" >
				<@p.datagrid id="sumDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
				 onClickRowEdit="false" pagination="true" rownumbers="true"   pageSize="20" showFooter="true"
		           columnsJsonList="[
	                  {field : 'buyerNo', title : '公司编码', width : 80,align:'center'},	
	                  {field : 'buyerName',title : '公司名称',width : 180,align:'left',halign:'center'},
	                  {field : 'supplierName', title : '供应商', width : 180,align:'left',halign:'center'},
	                  {field : 'earlyPayableAmount', title : '期初应付账款', width : 100,align:'right',halign:'center'},	
	                  {field : 'currentPayableAmount', title : '本期应付金额', width : 100,align:'right',halign:'center'},	
					  {field : 'currentPayment', title : '本期付款', width : 100,align:'right',halign:'center'},
	                  {field : 'balance', title : '余额', width : 100,align:'right',halign:'center'}
                  ]" 
	             />
			</div>
	   	</div>
	  </div>
	  
	</div>
  </div>
</body>
</html>