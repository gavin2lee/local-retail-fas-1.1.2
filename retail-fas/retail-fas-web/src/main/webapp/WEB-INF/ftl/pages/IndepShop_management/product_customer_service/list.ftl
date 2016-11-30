<!DOCTYPE HTML>
<html>
<head>
<title>退款汇总表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/self_shop_bank_info/list.json" 
					  save_url="/self_shop_bank_info/post" 
					  update_url="/self_shop_bank_info/put" 
					  del_url="/self_shop_bank_info/save" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="800" 
					  dialog_height="400"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":0},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":0},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":0}
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
			 				<tr> 
							 	<td align="right" width="110">店铺：</td>
						     	<td align="left" width="140">
						     		<input class="easyui-validatebox ipt" iptSearch="shop"  name="shopName" id="shopName"  />
						     		<input type="hidden"  name="shopNo" id="shopNo" />
						     	</td>
						     	
						     	<th>日期：</th>
					    		<td ><input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/>&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;</td>
								<td><input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="minDate:'createTimeStart'"/></td>
						     </tr>		
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title="汇总记录"
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30},
				                {field : 'id',hidden : 'true',align:'center'},
				                {field : '_operate',title : '店铺编码',width : 80,align:'center'},	
				                {field : 'shopAccount',title : '店铺名称',width : 150,align:'center'},	
								{field : 'mallName',title : '商场名称',width : 100,align:'center'},	
								{field : 'depositAccount',title : '品牌',width : 200,align:'center'},	
								{field : 'shortName',title : '消费日期',width : 150,align:'center'},	
								{field : 'shopNo',title : '日通退货日期',width : 100,align:'center'},	
								{field : 'terminalNumber',title : '交易卡号',width : 100,align:'center'},	
								{field : 'creditCardBank',title : '金额',width : 150,align:'center'},	
								{field : 'creditCardAccount',title : '刷卡金额',width : 200,align:'center'},
								{field : 'creditCardAccount',title : '退款金额',width : 200,align:'center'},
								{field : 'creditCardAccount',title : '实际退货金额',width : 200,align:'center'},
								{field : 'creditCardAccount',title : '实际退货日期',width : 200,align:'center'}			               
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
	
</body>
</html>