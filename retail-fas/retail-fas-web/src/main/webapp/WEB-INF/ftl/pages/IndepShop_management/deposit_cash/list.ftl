<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>POS存现核对</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/deposit_cash/depositcash.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<@p.commonSetting search_url="/deposit_cash/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/deposit_cash/do_fas_export"
					  export_title="POS存现明细导出"
					  primary_key="id"/>
	
<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
             {"id":"top_btn_aduit","title":"确认","iconCls":"icon-aduit","action":"depositCash.batchOperate(1)","type":0},	
			 {"id":"top_btn_cancel","title":"反确认","iconCls":"icon-aduit","action":"depositCash.batchOperate(0)","type":0},
             {"id":"btn-save","title":"保存","iconCls":"icon-save","type":0}
             {"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
				<div data-options="region:'north',border:false" style="overflow:auto;height:83px;">
			      	<form name="searchForm" id="searchForm" method="post" style="margin-top: 3px;">
						<table class="">
							<tr style="height: 30px;">
			       		 		<td align="right" width="80">公司名称：</td>
							  	<td align="left" width="140">
								  	<input class="ipt easyui-company" data-options="multiple:false,inputNoField:'companyNo',inputNameField:'companyName',inputWidth:180,required:true" name="companyName" id="companyName"/>
									<input type="hidden" name="companyNo" id="companyNo"/>
								</td>
								<td align="right" width="80">店铺名称：</td>
						     	<td align="left" width="140">
						     		<input id="shopName" class="easyui-shopCommon" disabled="disabled" data-options="callback:function(value){
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
						     	<td align="right" width="80">商场名称：</td>
							  	<td align="left" width="240">
									<input class="easyui-mall" name="mallName" id="mallName" data-options="inputWidth:180"/>
									<input type="hidden" name="mallNo" id="mallNo"/>
								</td>
								<input type="checkbox" name="s" id="s"/>
								<td><input type="checkbox" name="status" id="status" value="1"/></td>
								<td>
									<label for="status">仅显示财务确认</label>
								</td>
							</tr>
							<tr>
								<td align="right" width="80">存现账号： </td>
								<td align="left" width="140">
									<input class="ipt" style="width: 170px;" iptSearch="card"  name="account" id="account" data-options="required:true" />
								</td>
								<td align="right" width="80">单据编码： </td>
								<td align="left" width="140">
									<input class="ipt" style="width: 120px;" iptSearch="card"  name="billNo" id="billNo" />
								</td>
								<td align="right" width="80">存现日期：</td>
					    		<td align="left">
					    			<input class="easyui-datebox ipt" style="width: 100px;"  name="startDepositDate" id="createTimeStart" data-options="maxDate:'createTimeEnd'"/>
									 - <input class="easyui-datebox ipt" style="width: 100px;" name="endDepositDate" id="createTimeEnd" data-options="minDate:'createTimeStart'"/>
					    		</td>
					    		<td align="right" colspan="2">销售日期：</td>
					    		<td align="left">
					    			<input class="easyui-datebox ipt" style="width: 100px;" name="startOutDate" id="startOutStart" data-options="maxDate:'endOutEnd'"/>
									 - <input class="easyui-datebox ipt" style="width: 100px;" name="endOutDate" id="endOutEnd" data-options="minDate:'startOutStart'"/>
					    		</td>
							</tr>
						</table>
						<table class="form-tb">
							<col width="85" />
							<col width="90" />
							<col width="90" />
							<col width="90" />
							<tr>
								<th style="color:red;">小计汇总维度</th>
								<th>店铺：<input type="checkbox" name="isCheckShopNo" id="isCheckShopNo" value="true"/></th>
								<th>存入账号：<input type="checkbox" name="isCheckDepositAccount" id="isCheckDepositAccount" value="true"/></th>
								<th>存现日期：<input type="checkbox" name="isCheckDepositDate" id="isCheckDepositDate" value="true"/></th>
							</tr>
						</table>
					</form>
				</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"  emptyMsg = "" pageSize="20" showFooter="true"
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'operate',title : '操作',width : 80,align:'center',formatter: function(value,row,index){
				                	return depositCash.operate(value,row,index);
				                },notexport:true},
				                {field : 'billNo',title : '单据编码',width : 120,align:'center'},
								{field : 'account',title : '存入账号',width : 150,align:'right'},	
								{field : 'depositDate',title : '存入日期',width : 100,align:'right',halign:'center'},
								{field : 'transactionNo',title : '银行流水号',width : 100,align:'right',halign:'center'},
								{field : 'shopName',title : '店铺名称',width : 120,align:'left'},
				                {field : 'shopNo',title : '店铺编码',width : 80,align:'left'},
				                {field : 'mallName',title : '商场名称',width : 120,align:'left'},
				                {field : 'mallNo',title : '商场编码',width : 80,align:'left'},
								{field : 'amount',title : '存入金额',width : 100,align:'center',exportType:'number'},
								{field : 'existAmount',title : '期间已存金额',width : 100,align:'center',exportType:'number'},
				                {field : 'saleAmount',title : '期间销售额',width : 100,align:'center',exportType:'number'},
				                {field : 'depositDiff',title : '存现差异',width : 100,align:'left',exportType:'number'},
								{field : 'cashDiff',title : '差异原因',width : 120,align:'left',
									editor:{
				                  		type:'validatebox'
				                	},exportType:'text'
								},
				                {field : 'startOutDate',title : '销售起始日期',width : 100,align:'center'},
								{field : 'endOutDate',title : '销售截止日期',width : 100,align:'center'},
								{field : 'remark',title : '备注',width : 100,align:'left'}, 
								{field : 'auditor',title : '确认人',width : 100,align:'left'}, 
				                {field : 'auditTime',title : '确认时间',width : 150,align:'center'}, 
								{field : 'status',title : '财务确认',width : 100,align:'left',formatter: function(value,row,index){if(row.id){if(value == '1'){return '是'}else{return '否'}}}},
								{field : 'assistantName',title : '店员',width : 80,align:'left'},
								{field : 'currencyType',title : '币种',width : 80,align:'left',formatter: function(value,row,index){if(value == '0'){return '人民币'}else if(value=='1'){ return '美元'}}},	
				                {field : 'createUser',title : '建档人',width : 100,align:'left'}, 
				                {field : 'createTime',title : '建档时间',width : 150,align:'center'},
				                {field : 'companyName',title : '公司名称',width : 120,align:'left'},
				                {field : 'companyNo',title : '公司编码',width : 80,align:'left'}
			              ]" 
				          jsonExtend='{
				          	onDblClickRow:function(rowIndex, rowData){
			                   	
			                   	if(rowData.id != undefined){
				          			if(rowData.status!=1){
										depositCash.edit(rowIndex, rowData);
										return;
									}
				          		}
			             	},
			             	onLoadSuccess:function(rowData){
			             		depositCash.removeCheckbox(rowData);
	 							$("#dataGridDiv").datagrid("doCellTip",{"max-width":"300px","delay":500});
							}
		             	}' 
                 />
			</div>
	 	</div>
	</div>
   
</body>
</html>