<!DOCTYPE HTML>
<html>
<head>
<title>店铺信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/area_detail.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/self_shop_info_manage/self_shop_deposit_account.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/self_shop_info_manage/self_shop_terminal_account.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/selectObjUtil.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
	<div data-options="title:'店铺存现账号设置'">
		<div id="subLayoutId" class="easyui-layout" data-options="fit:true,border:false">
			<#--按钮-->
			<div data-options="region:'north',border:false" class="toolbar-region">
				<@p.toolbar id="toolbar" listData=[
					{"id":"btn-search","title":"查询","iconCls":"icon-search","action":"selfShopDepositAccount.search()", "type":0},
					{"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"selfShopDepositAccount.clear()", "type":0},
					{"id":"top_btn_aduit","title":"启用","iconCls":"icon-aduit","action":"selfShopDepositAccount.batchOperate(0)","type":0},	
			 		{"id":"top_btn_cancel","title":"停用","iconCls":"icon-aduit","action":"selfShopDepositAccount.batchOperate(1)","type":0},
					{"id":"btn-import-main","title":"导入","iconCls":"icon-import","type":6},
					{"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}]/>
			</div>
				
				<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
					   <#-- 主档信息  -->
		               <form name="mainForm" id="mainForm" method="post">
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />	
							<col width="80" />
							<col />							
							<tbody>
							<tr> 
						    	<td align="right" width="80">公司名称：</td>
								<td align="left" width="140">
									<input class="easyui-company"  name="companyName" id="companyName" data-options="inputWidth:160"/>
									<input type="hidden"  name="companyNo" id="companyNo" 	/>
								</td>
								<th align="right" width="110">商场名称：</th>
								<td align="left" width="140">
									<input class="easyui-mall" name="mallName" id="mallName" data-options="inputWidth:160"/>
									<input type="hidden" name="mallNo" id="mallNo"/>
								</td>	
							 	<td align="right" width="110">店铺名称：</td>
						     	<td align="left" width="140">
						     		<input id="shopName" class="easyui-shopCommon" disabled="disabled"/>
									<input type="hidden" name="shopNo" id="shopNo"/>
						     	</td>
						     	<td align="right" width="80">存现账号：</td>
						     	<td align="left" width="140">
						     		<input type="text"  name="depositAccount" id="depositAccount" style="width:160px"/>
						     	</td>
						     </tr>
						     <tr>
						     	<td align="right" width="80">分行名称：</td>
						     	<td align="left" width="140">
						     		<input type="text"  name="bank" id="bank" style="width:160px"/>
						     	</td>
						     	<th>管理城市： </th>
		                        <td>
									<input class="easyui-organ ipt" name="organNames" id="organNames" data-options="multiple:'true',inputWidth:160,inputNoField:'organNos',inputNameField:'organNames'"/> 
									<input type="hidden" name="organNos" id="organNos"/>
								</td>
						     </tr>
							</tbody>
						</table>
					</form>
						</div>
					</div>
					
				<div  data-options="region:'center',border:false">
	     		<div class="easyui-layout" data-options="fit:true" id="subLayout">	
			 	<#--工具栏   -->    
				<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "selfShopDepositAccount.add()", "type":1},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "selfShopDepositAccount.del()","type":3},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "selfShopDepositAccount.save()", "type":7}
		           ]
				/>
            	</div>
						
					<#--列表-->
			        <div data-options="region:'center',border:false">
					<@p.datagrid id="depositAccountDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"  emptyMsg = "" pageSize="20" showFooter="true"
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,align:'center',notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
								{field : 'shopNo', title : '店铺编码', width : 120,
									editor:{
										type:'searchboxname',
										options:{
											id:'shopNo_',
											name:'shopNo_',
											readonly:true,
										}
									}
								},	
								{field : 'shopName',title : '店铺名称',width : 150,align:'left',
									editor:{
										type:'shop',
										options:{
											id:'shopName_',
											name:'shopName_',
											required:true
										}
									}
								},
									
								{field : 'depositAccount',title : '存现账号',width : 120,align:'center',exportType:'text',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['onlyNumbers','length[5,25]']
				                  		}
				                	}
				                },	
				                {field : 'bank',title : '发卡行',width : 100,align:'left',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['unNormalData','length[3,30]']
				                  		}
				                  	}},	
				                  	{field : 'status',title : '状态',width : 100,align:'left',formatter: function(value,row,index){if(value == '0'){return '启用'}else if(value == '1'){ return '停用'}}},
				                  	{field : 'companyNo', title : '公司编码', width : 120, 
				                	editor:{
				                		type:'searchboxname',
				                		options:{
				                			id:'companyNo_',
				                			name:'companyNo_',
				                			readonly:true,
				                		}
				                	}
				                },
				                {field : 'companyName',title : '公司名称',width : 180,align:'left',
				                	editor:{
				                  		type:'searchboxname',
				                  		options:{
				                  			id:'companyName_',
				                  			name:'companyName_',
				                  			readonly:true,
				                  			width:'200px',
				                  			validType:['unNormalData','length[5,10]']
				                  		}
				                  	}
				                 },
				                 {field : 'mallNo', title : '商场编码', width : 120,exportType:'text',
				                 	editor:{
				                 		type:'searchboxname',
				                 		options : {
				                 			id : 'mallNo_',
				                 			name : 'mallNo_',
				                 			readonly:true,
				                 		}
				                 	}
				                 },	 	
				                {field : 'mallName',title : '商场名称',width : 150,align:'left',
				                	editor:{
				                  		type:'searchboxname',
				                  		options:{
				                  			id:'mallName_',
				                  			name:'mallName_',
				                  			readonly:true,
				                  			width:'180px',
				                  			validType:['unNormalData','length[5,10]']
				                  		}
				                  	}
				                 }, 	
				                 {field : 'createUser',title : '建档人',width : 80,align:'left'}, 
				                 {field : 'createTime',title : '建档时间',width : 130,align:'center'},
				                 {field : 'updateUser',title : '修改人',width : 80,align:'left'}, 
				                 {field : 'updateTime',title : '修改时间',width : 130,align:'center'}
			              ]"
			              jsonExtend='{
	                          onDblClickRow:function(rowIndex, rowData){
			                   	  	if(rowData.id != undefined){
					          			if(rowData.status!=0){
											selfShopDepositAccount.edit(rowIndex, rowData);
											return;
										}
										showWarn("存现账号已启用!");
		          					}
		                   		}
			         }' 
               		  />
					</div>
					</div>
					</div>
				</div>
				</div>
		</div>
	</div>
</div>

</body>
</html>