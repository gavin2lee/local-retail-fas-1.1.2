<!DOCTYPE html>
<head>
    <title>收款信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/area_among/receivableInfo.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<input type="hidden" name="billNoMenu" value="${billNoMenu}" id="billNoMenu" />
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "dialog.search()", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action" : "dialog.clear()","type":0}
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
	       		 		<col width="100" />
						<col  />
	       		 		<col width="100" />
						<col  />
						 <tbody>
	       		 			  <tr>
								<th>收款单号：</th>
								<td>
									<input class="ipt" name="billNo" id="billNo"/>
								</td>									
								<th>结算单号：</th>
								<td>		
									<input class="ipt" name="balanceNo" id="balanceNo"/>
								</td>		
								<th></th>
								<td></td>		
								<th></th>
								<td></td>		
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":0}
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false" id="dataGridDiv">
			      <@p.datagrid id="dataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true" rownumbers="true" singleSelect="false"  showFooter="true"
				           columnsJsonList="[
			                    {field:'ck',checkbox:true,notexport:true},
								{field : 'billNo',title : '收款单号',width : 125,align:'left',halign:'center',
									editor:{
									 type:'readonlybox',
									  options:{
									   }
									}
								},
								{field : 'balanceNo',title : '结算单号',width : 150,align:'left',halign:'center',
									formatter: function(value,row,index){
										return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AS-结算单');
									},
									editor:{
										 type:'combogrid',
										 options:{
										    required:true,
				                  			id:'balanceNoId',
				                  			name:'balanceNo',
				                  			inputWidth:100,
				                  			panelWidth : 900,
											panelHeight : 200,
				                  			noField:'billNo',
				                  			inputNoField:'balanceAmount',
				                  			url:BasePath+'/area_among_balance/list.json?balanceType=5&aToA=ys',
				                  			columns:[[ 
		        	     						{field : 'billNo',title : '结算单号',width : 180, halign : 'center', align : 'left'},
		        	     						{field : 'balanceAmount',title : '应收金额',width : 100, halign : 'center', align : 'right'},
		        	     						{field : 'salerName',title : '调出公司',width : 200,align:'left',halign:'center'},
												{field : 'buyerName',title : '调入公司',width : 200,align:'left',halign:'center'},
												{field : 'brandUnitName',title : '品牌部',width : 80,align:'center',halign:'center'},
												{field : 'balanceStartDate',title : '结算起始日期',width : 100,align:'center',halign:'center'},
												{field : 'balanceEndDate',title : '结算结束日期',width : 100,align:'center',halign:'center'}
				                  			]],
				                  			callback:editor.chooseCallBack
										 }
									}
								},
								{field : 'balanceAmount',title : '应收金额',width : 100,align:'right',halign:'center',
									editor:{
									  type:'readonlybox',
									  options:{
								  			id:'balanceAmountId',
			                  				name:'balanceAmount',
			                  				readonly:true
									  }
									}
								},
								{field : 'receivableAmount',title : '收款金额',width : 130,align:'right',halign:'center',
									editor:{
									 type:'fastextbox',
									 options:{
									     required:true,
										 id:'receivableAmountId',
			                  			 name:'receivableAmount'
									 }
									}
								},
								{field : 'overageAmount',title : '余额',width : 100,align:'right',halign:'center',
									editor:{
									  type:'readonlybox',
									  options:{
									  	 id:'overageAmountId',
			                  			 name:'overageAmount'
									  }
									}
								},
								{field : 'billDate',title : '日期',width : 100,align:'center',halign:'center',
									editor:{
									 type:'datebox',
									   options:{
									     required:true
									   }
									}
								},	
								{field : 'remark',title : '备注',width : 150,align:'left',halign:'center',
									editor:{
									 type:'validatebox'
									}
								},
							    {field : 'createUser',title : '创建人',width : 100,align:'center'},
			                    {field : 'createTime',title : '创建时间',width : 150,align:'center'},
			                    {field : 'updateUser',title : '修改人',width : 100,align:'center'},
			                    {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
							 ]" 
					    jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                   	   editor.editRow(rowIndex, rowData);
			                   }
			         }'/>
			</div>
		 </div>
	</div>
</body>
</html>