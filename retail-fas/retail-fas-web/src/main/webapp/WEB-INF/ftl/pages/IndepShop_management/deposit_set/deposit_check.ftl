<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>存现检查</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/deposit_set/deposit_check.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/deposit_check/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/deposit_check/do_fas_export"
					  export_title="自收银店铺存现检查导出"
					  primary_key="id"/>
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"检查","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
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
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
						 <col width="80"/>
						 <col/>
		                 <tbody>
		                 	<tr height='33'>
								<th>公司名称： </th>
								<td>
									<input class="easyui-company" name="companyName" id="companyName" data-options="required:true,inputWidth:170"/>
						     		<input type="hidden"  name="companyNo" id="companyNo" />	
								</td>
								<th>管理城市： </th>
		                   		<td>
		                   			<input class="easyui-organ ipt"  name="bizCityName" id="bizCityName"
		                   			 	data-options="inputWidth:170,inputNoField:'bizCityNo',inputNameField:'bizCityName',multiple:true"/>
									<input type="hidden" name="bizCityNo" id="bizCityNo"/>
		                   		</td>
								<th>店铺名称： </th>
								<td>
									<input id="shopName" style="width: 170px" class="easyui-shopCommon" disabled="disabled" data-options="callback:function(value){
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
							</tr>
						 </tbody>
						 </table>
					</form>
				</div>
			</div>
    		<!--列表-->
    		<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"  emptyMsg = "" pageSize="10" showFooter="true"
			           columnsJsonList="[
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shopNo',title : '店铺编码',width : 80,align:'left'},
								{field : 'shopName',title : '店铺名称',width : 120,align:'left'},
								{field : 'checkDate',title : '检查日期',width : 80,align:'left'},
				                {field : 'beyondLastDepositDate',title : '超出最近存现日',width : 120,align:'left',styler: changeFirstColor},
				                {field : 'amount',title : '现金余额',width : 80,align:'right'},
				                {field : 'depositDiff',title : '存现差异',width : 80,align:'right',styler: changeSecondColor,exportType:'text'}
			              ]" 
                 />
			</div>
    	</div>
    </div>
</body>