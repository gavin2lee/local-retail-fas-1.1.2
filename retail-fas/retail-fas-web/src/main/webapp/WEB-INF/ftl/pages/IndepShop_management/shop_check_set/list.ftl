<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>店铺检查项设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/self_shop_bank_info/shop_check_set/shop_check_set.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/shop_check_set/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/shop_check_set/do_fas_export"
					  export_title="店铺检查项导出"
					  primary_key="id"/>

	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
				 {"id":"btn-insert","title":"新增","iconCls":"icon-add", "type":0},
				 {"id":"btn-delete","title":"删除","iconCls":"icon-remove", "type":0},
	             {"id":"btn-save","title":"保存","iconCls":"icon-save","type":0},
	             {"id":"top-btn-aduit","title":"启用","iconCls":"icon-aduit","type":0},
	             {"id":"top-btn-cancel","title":"禁用","iconCls":"icon-aduit","type":0},
	              {"id":"btn-export","title":"导出","iconCls":"icon-export","type":0}
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
								<td align="right" >公司名称：</td>
								<td align="left">
									<input class="ipt easyui-company"  name="companyName" id="companyName" data-options="inputWidth:200"/>
									<input type="hidden"  name="companyNo" id="companyNo" 	/>	
								</td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
	    	
	    	<!--列表-->
	    	<!--
	    		
	    	-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="false" singleSelect="false"  showFooter="true"
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,notexport:true},
				                {field : 'rowId',hidden : 'true',align:'center',notexport:true},
								
				                {field : 'companyName',title : '公司名称',width : 100,align:'left',
				                	editor:{
				                  		type:'company',
				                  		options:{
				                  			id:'companyName_',
				                  			name:'companyName_',
				                  			required:true
				                  		}
				                  	}
				                },
				                {field : 'companyNo',title : '公司编码',width : 100,align:'left', 
				                	editor:{
				                		type:'searchboxname',
				                		options:{
				                			id:'companyNo_',
				                			name:'companyNo_',
				                			readonly:true
				                		}
				                	}
				                },
				                {field : 'checkNo',title : '店铺检查项编码',value:'123',width : 100,align:'left',
									editor:{
										type:'searchboxname',
										options:{
											id:'checkNo_',
											name:'checkNo_',
											readonly:true,
										}
									}
								},
				                {field : 'checkName',title : '名称',width : 100,align:'left',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['unNormalData','length[3,30]']
				                  		}
				                  	}
				                },
				                {field : 'status',title : '状态',width : 100,align:'left',formatter: function(value,row,index){if(value == '0'){return '启用'}else if(value == '1'){ return '禁用'}}},
				                {field : 'createUser',title : '建档人',width : 100,align:'left'},
				                {field : 'createTime',title : '建档时间',width : 100,align:'left'},
				                {field : 'updateUser',title : '修改人',width : 100,align:'left'},
				                {field : 'updateTime',title : '修改时间',width : 100,align:'left'}
			              ]" 
				          jsonExtend='{
				          	onDblClickRow:function(rowIndex,rowData){
				          		shopCheckSet.edit(rowIndex,rowData);
				          }}'
                 />
			</div>
	    	
    	</div>
    </div>	
</body>