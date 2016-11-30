<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>银行卡退款核对</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/card_return_check/card_return_check.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/card_return_check/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/card_return_check/do_fas_export"
					  export_title="银行卡退款核对导出"
					  primary_key="id"/>

	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
	             {"id":"btn-save","title":"保存","iconCls":"icon-save","type":7},
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
		                 <tbody>
							<tr>
								<td align="right" width="50">公司名称：</td>
								<td align="left">
									<input class="ipt easyui-company"  name="companyName" id="companyName" data-options="inputWidth:180,required:true"/>
									<input type="hidden"  name="companyNo" id="companyNo" 	/>	
								</td>
						     	<td align="right" width="80">店铺名称：</td>
								<td >
									<input id="shopName" style="width: 180px" class="easyui-shopCommon" disabled="disabled" data-options="callback:function(value){
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
						     	<td align="right" width="60">终端号: </td>
						     	<td align="left" width="140">
						     		<input class="ipt"  iptSearch="card"  name="terminalNumber" id="terminalNumber"/>
						     	</td>
						     	<th align="right" width="100">销售订单编码： </th>
								<td align="left" width="140">
									<input class="ipt" style="width: 120px;" iptSearch="card"  name="businessNo" id="businessNo" />
								</td>
								<input type="checkbox" name="s" id="s"/>
								<td align="right" width="100">仅显示财务确认:</td>
								<td>
									<input type="checkbox" value='1' name="status" id="status"/>
								</td>
							</tr>
							<tr>
								<th align="right" width="50">商场名称：</th>
							  	<td align="left" width="140">
									<input class="easyui-mall" name="mallName" id="mallName" data-options="inputWidth:180"/>
									<input type="hidden" name="mallNo" id="mallNo"/>
								</td>
								<td align="right" width="50">刷卡账号：</td>
						     	<td align="left" width="140">
						     		<input class="ipt"  iptSearch="card"  name="creditCardAccount" id="creditCardAccount" style="width:170px" />
						     	</td>
						     	<td align="right" width="80">商户编码：</td>
						     	<td align="left" width="140">
						     		<input class="ipt"  iptSearch="card"  name="merchantsNo" id="merchantsNo" style="width:120px" />
						     	</td>
						     	<td align="right" width="80">退换货日期：</td>
						     	<td >
						     		<input class="easyui-datebox"  name="startOutDate" id="startOutDate" data-options="maxDate:'endOutDate',required:true"/>-
						     	</td>
								<td><input class="easyui-validatebox easyui-datebox ipt" name="endOutDate" id="endOutDate" data-options="minDate:'startOutDate',required:true"/></td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
	    	
	    	<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  showFooter="true"
			           columnsJsonList="[
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'operate',title : '操作',width : 100,malign:'center',formatter: function(value,row,index){
				                	return cardReturnCheck.operate(value,row,index);
				                },notexport:true},
								{field : 'shopName',title : '店铺名称',width : 100,align:'left'},
				                {field : 'shopNo',title : '店铺编码',width : 100,align:'left'},
				                {field : 'companyName',title : '公司名称',width : 100,align:'left'},
				                {field : 'companyNo',title : '公司编码',width : 100,align:'left'},
				                {field : 'mallName',title : '商场名称',width : 100,align:'left'},
				                {field : 'mallNo',title : '商场编码',width : 100,align:'left',exportType:'text'},
				                {field : 'terminalNumber',title : '终端号',width : 100,align:'left',exportType:'text'},
				                {field : 'oldOutDate',title : '原单日期',width : 100,align:'left'},
				                {field : 'outDate',title : '退换货日期',width : 100,align:'left'},
				                {field : 'payName',title : '支付方式',width : 100,align:'left'},
				                {field : 'amount',title : '退款金额',width : 100,align:'left',exportType:'number'},
				                {field : 'creditCardRate',title : '费率',width : 100,align:'left',exportType:'number'},
				                {field : 'poundage',title : '手续费',width : 100,align:'left',exportType:'number'
								,editor:{
				                  		type:'validatebox',
				                  		options:{}
				                }},
				                {field : 'paidinAmount',title : '实收金额',width : 100,align:'left',exportType:'number'},
				                {field : 'businessNo',title : '销售订单编码',width : 120,align:'left'},
				                {field : 'status',title : '状态',width : 100,align:'left',formatter: function(value,row,index){if(row.id){if(value == '1'){return '退款成功'}else if(value == '0'){ return '退款处理中'}else{return '尚未处理'}}}},
				                {field : 'auditor',title : '处理人',width : 100,align:'left'}, 
				                {field : 'actualReturnTime',title : '处理日期',width : 100,align:'left'}, 
				                {field : 'merchantsNo',title : '商户编码',width : 100,align:'left',exportType:'text'},
				                {field : 'creditCardAccount',title : '刷卡账号',width : 100,align:'left',exportType:'text'},
				                {field : 'createUser',title : '建档人',width : 100,align:'left'},
				                {field : 'createTime',title : '建档时间',width : 100,align:'left'}	
			              ]" 
				          jsonExtend='{
				          	onDblClickRow:function(rowIndex,rowData){
				          		if(rowData.id != undefined){
				          			if(rowData.status!=1){
										cardReturnCheck.edit(rowIndex,rowData);
										return;
									}
									showWarn("已确认单据,系统不允许修改手续费!");
				          		}
				          		
				          	}
				          }'
                 />
			</div>
	    	
    	</div>
    </div>	
</body>