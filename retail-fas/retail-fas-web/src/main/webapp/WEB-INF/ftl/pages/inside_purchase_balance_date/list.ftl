<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>内购结算期设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/groupon/insidePurchaseBalanceDate.js?version=${version}"></script>
</head>
<script>
</script>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
	     <@p.toolbar id="toolbar" listData=[
			  	 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "action" : "dialog.clear()","type":0},                         
             	 {"id":"btn-export","title":"导出","iconCls":"icon-export","action" : "dialog.exportExcel()","type":4}
           ]
		/>
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
	       		 			 <tr>
								<th align="right" width="110">公司名称：</th>
								<td align="left" width="140"><input class="easyui-validatebox ipt easyui-company" name="companyName" id="companyName"/>
								<input type="hidden" name="companyNo" id="companyNo"/></td>									
	       		 			</tr>
	       		 	</table>
				</form>
		</div>
	</div>
			
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			 <#--工具栏   -->  
			<div data-options="region:'north',border:false">
				<@p.toolbar id="toolbar3" listData=[
				 	 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":1},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":3},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7}				 
		           ]
				/>
            </div>
				           
			<!--列表-->
        	<div data-options="region:'center',border:false" id="dataGridDiv">
		      <@p.datagrid id="balanceDateDataGrid" loadUrl="" saveUrl="" defaultColumn=""  
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			           rownumbers="true" emptyMsg = "" singleSelect="true"  
			           columnsJsonList="[
			                      {field : 't', checkbox:true, width : 30, notexport:true},
			                      {field : 'id',hidden : 'true',align:'center',notexport:true},
			                      {field : 'companyName', title : '公司名称', align:'left',width : 180, 
					                  editor:{
						                  type:'readonlybox',
						                  options:{
						                  	id:'companyName_',
						                  	name:'companyName',
						                  	required:true
						                  }
						              }
				                  },
			                  	  {field : 'companyNo', title : '公司编码', align:'left',width : 80, 
			                  	  	editor:{
				                  		type:'company',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'companyNo_',
				                  			name:'companyNo',
				                  			idField: 'companyNo',
											textField: 'companyNo',
											noField: 'name',
				                  			inputNoField:'companyName_',
				                  			required:true
				                  		}
				                  	}
				                  },
				                  {field : 'balanceMonth',title : '结算月',width : 80, align:'left',
				                  	editor:{
				                  		type:'datebox',
				                  		options:{
											required:true,
				                  			dateFmt:'yyyyMM'
										}
				                  	}
				                  },
				                  {field:'balanceStartDate',title:'起始日期',width:80,align:'left',
				                  	editor:{
				                  		type:'datebox',
										options:{
											required:true
										}
									}
								  },			
				                  {field:'balanceEndDate',title:'终止日期',width:80,align:'left',
									editor:{
				                  		type:'datebox',
										options:{
											required:true
										}
									}
								  },		
				                  {field : 'invoiceFlagName',title : '是否已开票 ',width : 80,align:'left'},
				                  {field : 'remark',title : '备注',width : 200,align:'left',
				                  	editor:{
				                  		type:'validatebox'
				                  	}
				                  }, 
				                  {field : 'createUser',title : '建档人',width : 100,align:'left'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'left'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'left'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'left'},   
			                  ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex,rowData){
			                	  //双击方法
					              editor.editRow(rowIndex, rowData);
			              }}'
                 />
			</div>
	 	</div>
	</div>
</body>
</html>