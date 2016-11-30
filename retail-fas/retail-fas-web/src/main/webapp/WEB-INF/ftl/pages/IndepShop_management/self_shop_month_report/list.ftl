<!DOCTYPE HTML>
<html>
<head>
<title>独立店铺日报表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/self_shop_month_report/selfShopMonthReport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"selfShopMonthReport.searchEventBtn()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"selfShopMonthReport.removeEventBtn()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"selfShopMonthReport.exportEventBtn()", "type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
						 <col width="120"/>
						 <col/>
		                 <tbody>
		                 	<tr height='33'>
								<th>公司	： </th>
								<td>
									<input class="easyui-company" name="companyName" id="companyName" data-options="required:true,inputWidth:200"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />	
								</td>
								<th>店铺	： </th>
								<td>
									<input id="shopName" style="width: 200px" class="easyui-shopCommon" disabled="disabled" data-options="callback:function(value){
						     			if($('#shopName').attr('disabled') == null){
						     				$('#shopNo').val(value);
						     			} else {
						     				showWarn('请先选择业务类型');
						     				$('#shopName').val('');
						     				$('#shopNo').val('');
						     			}
						     		}"/>
									<input type="hidden" name="shopNo" id="shopNo"/>
								</td>
								<th>销售月：</th>
					    		<td ><input class="easyui-datebox"  name="createTimeStart" id="createTimeStart" 	
					    			data-options="width:'80px',required:true,maxDate:'createTimeEnd',dateFmt:'yyyy-MM'" /></td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td><input class="easyui-datebox" name="createTimeEnd" id="createTimeEnd" 
									data-options="width:'80px',required:true,minDate:'createTimeStart',dateFmt:'yyyy-MM'"/></td>
							</tr>
							<tr>
								<th>销售类型	： </th>
								<td>
									<input class="easyui-combobox" name="saleType" id="saleType" style="width:100px;"/>
								</td>
							</tr>
						 </tbody>
						 </table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="店铺月报表"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shopNo',title : '店铺编码',width : 100,align:'left'},
								{field : 'shopName',title : '店铺名称',width : 100,align:'left'},
								{field : 'saleMonth',title : '销售月',width : 80,align:'center'},	
								{field : 'cashAmount',title : '现金',width : 100,align:'right',exportType:'number'},	
								{field : 'cashCouponAmount',title : '现金券',width : 100,align:'right',exportType:'number'},	
								{field : 'creditCardAmount',title : '刷卡',width : 100,align:'right',exportType:'number'},
								{field : 'mallCardAmount',title : '商场卡',width : 80,align:'right',exportType:'number'},
								{field : 'mallCouponAmount',title : '商场券',width : 80,align:'right',exportType:'number'},
								{field : 'advancePayAmount',title : '预付款',width : 100,align:'right',exportType:'number'},
								{field : 'othersAmount',title : '其它',width : 100,align:'right',exportType:'number'},
								{field : 'totalAmount',title : '总销售金额',width : 100,align:'right',exportType:'number'},
								{field : 'feeAmount',title : '总回扣费',width : 100,align:'right',exportType:'number'},
								{field : 'basicSpendAmount',title : '店铺基础费用',width : 100,align:'right',exportType:'number'},
								{field : 'actualIncomeAmount',title : '店铺月收益金额',width : 100,align:'right',exportType:'number'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
	
</body>
</html>