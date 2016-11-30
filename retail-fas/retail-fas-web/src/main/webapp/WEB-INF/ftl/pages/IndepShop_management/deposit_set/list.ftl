<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>存现设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/deposit_set/deposit_set.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/deposit_set/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/deposit_set/export"
					  export_title="存现设置"
					  primary_key="id"/>
					  
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
             {"id":"btn-insert","title":"新增","iconCls":"icon-add","type":1},
             {"id":"btn-save","title":"保存","iconCls":"icon-save","type":7},
             {"id":"btn-delete","title":"删除","iconCls":"icon-remove","type":3},
             {"id":"btn-import-main","title":"导入","iconCls":"icon-import","type":6},
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
								<th>店铺名称	： </th>
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
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
				                {field : 'shopNo',title : '店铺编码',width : 80,align:'left',
				                	editor:{
										type:'searchboxname',
										options:{
											id:'shopNo_',
											name:'shopNo_',
											readonly:true,
										}
									}
				                },
								{field : 'shopName',title : '店铺名称',width : 120,align:'left'},
								{field : 'prepareCash',title : '备用金',width : 80,align:'left',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['onlyNumbers','maxLength[20]']
				                  		}
				                	}
				                },
				                {field : 'initDate',title : '初始化日期',width : 120,align:'left',
									editor:{
				                  		type:'datebox',options:{required:true}
				                	}
				                },
								{field : 'startAmount',title : '期初现金余额',width : 120,align:'left',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			validType:['onlyNumbers','maxLength[20]']
				                  		}
				                	}
				                },
				                {field : 'beyondLastDepositDate',title : '存现间隔日(<=天)',width : 120,align:'left',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			validType:['interval']
				                  		}
				                	}
				                },
				                {field : 'amount',title : '现金余额(<=)',width : 100,align:'right',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			validType:['onlyNumbers','maxLength[20]']
				                  		}
				                	}
				                },
				                {field : 'depositDiff',title : '存现差异(<=)',width : 100,align:'right',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			validType:['onlyNumbers','maxLength[20]']
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
			                   depositSet.edit(rowIndex, rowData) ;
			             }}' 
                 />
			</div>
    	</div>
    </div>
</body>