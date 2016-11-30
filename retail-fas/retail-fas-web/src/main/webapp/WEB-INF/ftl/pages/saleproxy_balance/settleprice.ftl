<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>代销结算价</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/saleproxy_balance/settleprice.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<script>
</script>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	      <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"saleproxyBalanceSettlePrice.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"saleproxyBalanceSettlePrice.clear()", "type":0},
             {"id":"btn-import","title":"导入","iconCls":"icon-import","action":"saleproxyBalanceSettlePrice.importOperation()","type":6},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"saleproxyBalanceSettlePrice.exportExcel()","type":4}           
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
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
								<th align="right" width="110">店铺：</th>
								<td align="left" width="140">
									<input id="shopNames_"/>
									<input type="hidden" name="shopNos" id="shopNos_"/>
								</td>
								<th>委托代销公司：</th>
							<td>
								<input class="easyui-company ipt"  name="salerName" id="salerNameCon" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'salerNoCon',inputNameField:'salerNameCon',inputWidth:160,isDefaultData : false"/>
								<input type="hidden" name="salerNo" id="salerNoCon"/>
							</td>	
						    <th>受托代销公司： </th>
							<td>
								<input class="easyui-company ipt"   name="buyerName" id="buyerNameCon" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNoCon',inputNameField:'buyerNameCon',inputWidth:160,isDefaultData : false"/>
								<input type="hidden" name="buyerNo" id="buyerNoCon"/>
							</td>
							<th>生效日期： </th>
									<td>
										<input class="easyui-datebox ipt"   name="startEffectiveDate" id="startEffectiveDate" data-options="maxDate:'endEffectiveDate'"  />
									</td>
									<th>&nbsp;&nbsp;-&nbsp;&nbsp; </th>
									<td>
										<input class="easyui-datebox ipt" name="endEffectiveDate" id="endEffectiveDate" data-options="minDate:'startEffectiveDate'"  />
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
								 {"id":"btn-add","title":"新增行","iconCls":"icon-add","action":"saleproxyBalanceSettlePrice.add()", "type":0},
								 {"id":"btn-del","title":"删除行","iconCls":"icon-del","action":"saleproxyBalanceSettlePrice.del()","type":0}, 
								 {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"saleproxyBalanceSettlePrice.save()","type":7}				 
							    ]
							/>
	            		</div>
			        	<div data-options="region:'center',border:false" id="dataGridDiv">
					      <@p.datagrid id="dtlDataGrid" loadUrl="" saveUrl="" defaultColumn=""  
					              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
						           rownumbers="true" emptyMsg = "" singleSelect="true"  
						           columnsJsonList="[
						                      {field : 't', checkbox:true, width : 30, notexport:true},
						                       {field : 'id',hidden : 'true',align:'center',notexport:true,printable:false},
						                        {field : 'shopNo', title : '店铺编码', align:'left',width : 80, hidden:true,
							                  		editor:{
							                  			type:'hiddenbox',
							                  			options:{
							                  				id:'shopNo_',
							                  				name:'shopNo'
							                  			}
							                  		}
							                  }, 
						          {field : 'shortName', title : '店铺名称', align:'left',width : 160,halign:'center', 
							                 	editor:{
							                 		type:'shop',
							                  		options:{
							                 			id:'shortName_',
							                  			name:'shortName',
							                  			url:BasePath+'/shop/list.json',
							                 			inputNoField:'shopNo_',
							                  			required:true,
							                  			relationData:true
							                  		}
							                  	}
							       },
							       {field : 'brandUnitNo',hidden : true,title : '品牌', align:'left',width : 80, notexport:true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'brandNo_',
												name:'brandNo'
											}
										}
				                  },
							 {field : 'brandUnitName', title : '品牌部名称', width : 80,halign:'center',
								editor:{
									type:'combogrid',
									options:{
										id:'brandName_',
										name:'brandName',
										inputNoField:'brandNo_',
										required:true,
										idField:'brandName',
										textField:'brandName',
										noField:'brandNo',
										columns:[[
											{field : 'brandNo',title : '品牌部编码',width : 20, halign : 'center', align : 'left'},
											{field : 'brandName',title : '品牌部名称',width : 30, halign : 'center', align : 'left'}
										]],
										onShowPanel:function(){
									if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
										var url = BasePath+'/shop_brand/list.json?' 
					 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
					 	            	$(this).combogrid('grid').datagrid('options').url= url;
					 	            	$(this).combogrid('grid').datagrid('load');
									}
									else {
										showWarn('请先选择店铺！');
										return;
													}
											},
										}
									}
								},	
                                {field : 'salerNo', title : '委托代销公司编码', align:'left',halign:'center',width : 100, hidden:true,
			                  	  	editor:{
				                  		type:'hiddenbox',
				                  		options:{
				                  			inputWidth:70,
				                  			id:'companyNo_',
				                  			name:'salerNo',
				                  			idField: 'companyNo',
											textField: 'companyNo',
											noField: 'name',
				                  			inputNoField:'companyName_',
				                  			required:true
				                  		}
				                  	}
				                  },
			                      {field : 'salerName', title : '委托代销公司名称', align:'left',halign:'center',width : 180, 
					                  editor:{
						                  type:'readonlybox',
						                  options:{
						                  	id:'companyName_',
						                  	name:'salerName',
						                  	required:true
						                  }
						              }
				                  },
				                   {field : 'buyerNo',title : '受托代销公司编码 ',width : 80,align:'left',halign:'center',hidden:true,
					                  editor:{
					                  		type:'hiddenbox',
					                  		options:{
				                  				id:'buyerNoId',
				                  				name:'buyerNo',
				                  				readonly:true
				                  			}
					                  	}
				                  },
				                   {field : 'buyerName', title : '受托代销公司名称', width : 200,align:'left',halign:'center',
									 editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			required:true,
				                  			id:'buyerNameId',
				                  			name:'buyerName',
				                  			inputWidth:200,
				                  			panelWidth : 400,
											panelHeight : 300,
				                  			idField:'buyerName',
				                  			textField:'name',
				                  			noField:'buyerNo',
				                  			inputNoField:'buyerName',
				                  			url:BasePath+'/base_setting/company/list.json?status=1',
				                  			columns:[[ 
		        	     						{field : 'companyNo',title : '公司编码',width : 80, halign : 'center', align : 'left'},
		        	     						{field : 'name',title : '公司名称',width : 200, halign : 'center', align : 'left'}
				                  			]],
				                  			callback:function(data){
				                  				if(data){
					                  				$('#buyerNoId').val(data.companyNo);
					                  				$('#buyerNameId').combogrid('setValue',data.name);
				                  				}
				                  			}
				                  		}
				                  	}
								  },
				                 				                 
                                
								 {field : 'pricingBase',title : '定价基数 ',width : 80,align:'center', notexport:true,printable:false,formatter:saleproxyBalanceSettlePrice.pricingBaseFalg,
												editor:{
													type:'combobox',
													options:{
													valueField: 'value',  
													textField: 'text',
													required:false, 
													 data: [ {'value':'1', 'text':'牌价'},{'value':'2', 'text': '地区价'},
													 {'value':'3', 'text':'终端销售收入'},{'value':'4', 'text': '终端销售-扣费'},
													 {'value':'5', 'text':'租金'}]
													}
												}
											  }, 
								{field : 'discount',title : '折扣（%）',width : 90,align:'right',halign:'center',exportType:'number',
									editor:{
										type:'fasnumberbox',
										options:{
											required:false,precision:2,validType:['maxLength[12]']
										}
									}
								},  
								{field : 'addOnPrice',title : '加减价',width : 90,align:'right',halign:'center',exportType:'number',
									editor:{
										type:'fasnumberbox',
										options:{
											required:false,precision:2,validType:['maxLength[12]']
										}
									}
								},  
								{field : 'tariffAmount',title : '租金',width : 90,align:'right',halign:'center',exportType:'number',
									editor:{
										type:'fasnumberbox',
										options:{
											required:false,precision:2,validType:['maxLength[12]']
										}
									}
								},  
							                  {field:'effectiveDate',title:'生效日期',width:100,align:'center',halign:'center',
							                  	editor:{
							                  		type:'fasdatebox',
							                  		options: {
							                  			id:'effectiveDate',
							                  			name:'effectiveDate',
							                  			dateFmt:'yyyy-MM-dd',
							                  			required:false
							                  		}
							                  	}
							                  },
							                  
							                   {field : 'remark',title : '备注',width : 200,align:'left',halign:'center',
							                  	editor:{
							                  		type:'validatebox',
							                  		options:{
							                  		
							                  		}
							                  	}
							                  }, 
							                  {field : 'createUser',title : '建档人',width : 100,align:'center',halign:'center'}, 
							                  {field : 'createTime',title : '建档时间',width : 150,align:'left',halign:'center'},
							                  {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'}, 
							                  {field : 'updateTime',title : '修改时间',width : 150,align:'left',halign:'center'} 
						                  ]" 
							          jsonExtend='{
				                           onDblClickRow:function(rowIndex,data){
						                	  //双击方法
						                	   if(!saleproxyBalanceSettlePrice.check(rowIndex,data)){
									                  saleproxyBalanceSettlePrice.edit(rowIndex,data);
									             }
						              }}'
			                 />
						</div>
				 	</div>
				</div>
</body>
</html>