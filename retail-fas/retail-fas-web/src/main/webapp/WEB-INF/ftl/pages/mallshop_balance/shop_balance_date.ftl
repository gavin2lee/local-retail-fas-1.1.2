<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>mallshop结算期设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/shop_balance_date.js?version=${version}"></script>

<link type="text/css" rel="stylesheet" href="${staticFileUrl}/modules/filterbuilder/css/filterbuilder.css" /> 
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<script>
</script>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	      <@p.billToolBar type="shop_balance_date_setBar"/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" id="balanceType" name="balanceType" value="2">
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
								<th>公司名称：</th>
								<td>
									<input class="ipt easyui-company" name="companyName" id="companyName" data-options="multiple:true"/>
									<input type="hidden" name="multiCompanyNo" id="companyNo"/>
								</td>	
								<th>商业集团：</th>
								<td>
									<input class="ipt easyui-bsgroups" name="bsgroupsName" id="bsgroupsName" data-options="multiple:true"/>
									<input type="hidden" name="multiBsgroupsNo" id="bsgroupsNo"/>
								</td>	
								<th>商场：</th>
								<td>
									<input class="ipt easyui-mall" name="mallName" id="mallName" data-options="multiple:true"/>
									<input type="hidden" name="multiMallNo" id="mallNo"/>
								</td>
								</tr>
								<tr>
									<th align="right" width="110">结算月：</th>
									<td align="left" width="140" colspan="5">
								       <input class="easyui-datebox easyui-validatebox ipt" name="month" id="month" data-options="dateFmt:'yyyyMM'" />    							
									</td>
									<th align="right" width="100">仅显示新开店： </th>
							 		<td>
							 			<input type="checkbox" name="isNewShop" id="isNewShop" />
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
						    <@p.billToolBar type="shop_balance_date_operaBar"/>
	            		</div>
			        	<div data-options="region:'center',border:false" id="dataGridDiv">
					      <@p.datagrid id="dtlDataGrid" loadUrl="" saveUrl="" defaultColumn=""  
					              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
						           rownumbers="true" emptyMsg = "" singleSelect="true"  
						           columnsJsonList="[
						                      {field : 't', checkbox:true, width : 30, notexport:true},
						                       {field : 'id',hidden : 'true',align:'center',notexport:true,printable:false},
						                      {field : 'shopNo', title : '店铺编码', align:'left',width : 80, 
							                  		editor:{
							                  			type:'readonlybox',
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
							                  {field : 'month',title : '结算月',width : 80, align:'center',halign:'center',
							                  	editor:{
							                  		type:'fasdatebox',
							                  		options:{
							                  			id:'fasDate',
							                  			name:'fasDate',
							                  			dateFmt:'yyyyMM',
								                  		required:true,
							                  			blur : shopBalanceDate.dateBoxOnblur
							                  		}
							                  	}
							                  },				          	
							                  {field:'balanceStartDate',title:'起始日期',width:100,align:'center',halign:'center',
							                  	editor:{
							                  		type:'datebox',
							                  		options: {
							                  		id:'balanceStartDate',
							                  			required:true,maxDate:'balanceEndDate'
							                  		}
							                  	}
							                  },			
							                  {field:'balanceEndDate',title:'终止日期',width:100,align:'center',halign:'center',
							                  	editor:{
							                  		type:'fasdatebox',
							                  		options:{
							                  			id:'balanceEndDate',
							                  			name:'balanceEndDate',
							                  			dateFmt:'yyyy-MM-dd',
								                  		required:true,minDate:'balanceStartDate',
							                  			blur : shopBalanceDate.balanceDateBoxOnBlur
							                  		}
							                  	}
							                  },
							                  {field : 'balanceFlag',title : '是否生成结算单 ',width : 100,align:'center',halign:'center',
							                 	 formatter:shopBalanceDate.balanceFlagformatter},
							                  {field : 'billalready',title : '是否已开票 ',width : 80,align:'center',halign:'center',
							                  	 formatter:shopBalanceDate.billFlagformatter},
							                  {field : 'deductStatus',hidden : 'true',title : '费用生成 ',width : 80,align:'center', notexport:true,printable:false,formatter:shopBalanceDate.deductStatus,
												editor:{
													type:'combobox',
													options:{
													valueField: 'value',  
													textField: 'text',
													required:false,
													 data: [ {'value':'1', 'text':'生成'},{'value':'0', 'text': ' 不生成'}]
													}
												}
											  }, 
							                  {field:'shouldBillDate',title:'应开票日期',width:100,align:'center',halign:'center',
							                  	editor:{
							                  		type:'fasdatebox',
							                  		options: {
							                  			id:'shouldBillDate',
							                  			name:'shouldBillDate',
							                  			dateFmt:'yyyy-MM-dd',
							                  			required:false
							                  		}
							                  	}
							                  },
							                  {field:'incomePaymentsDate',title:'应回款日期',width:100,align:'center',halign:'center',
							                  	editor:{
							                  		type:'fasdatebox',
							                  		options: {
							                  			id:'incomePaymentsDate',
							                  			name:'incomePaymentsDate',
							                  			dateFmt:'yyyy-MM-dd',
							                  			required:false
							                  		}
							                  	}
							                  },
							                  {field:'invoiceShouldSendDate',title:'发票应寄送日期',width:100,align:'center',halign:'center',
							                  	editor:{
							                  		type:'fasdatebox',
							                  		options: {
							                  			id:'invoiceShouldSendDate',
							                  			name:'invoiceShouldSendDate',
							                  			dateFmt:'yyyy-MM-dd',
							                  			required:false
							                  		}
							                  	}
							                  },
							                  {field:'invoiceShouldArraiveDate',title:'发票应送到日期',width:100,align:'center',halign:'center',
							                  	editor:{
							                  		type:'fasdatebox',
							                  		options: {
							                  			id:'invoiceShouldArraiveDate',
							                  			name:'invoiceShouldArraiveDate',
							                  			dateFmt:'yyyy-MM-dd',
							                  			required:false
							                  		}
							                  	}
							                  },
							                   {field : 'companyName', title : '公司', align:'left',width : 200,halign:'center',
							                  	  editor:{
							                  	  	type:'readonlybox',
							                  	  	options:{
							                  	  		id:'companyName_',
							                  	  		name:'companyName'
							                  	  	}
							                  	  }
							                  },
							                  {field : 'companyNo',hidden:true, title : '公司编码', align:'left',width : 80, 
							                  	 editor:{
							                  	 	type:'hiddenbox',
							                  	 	options:{
							                  	 		id:'companyNo_',
							                  	 		name:'companyNo'
							                  	 	}
							                  	 }
							                  	},
							                  {field : 'bsgroupsNo', hidden:true,title : '商场体系', width : 160, 
							                  	 editor:{
							                  	 	type:'hiddenbox',
							                  	 	options:{
							                  	 		id:'bsgroupsNo_', 
							                  	 		name:'bsgroupsNo'
							                  	 	}
							                  	 }
							                  },
							                  {field : 'bsgroupsName', title : '商业集团名称', width : 180,align:'left',halign:'center',
							                  	 editor:{
							                  	 	type:'readonlybox',
							                  	 	options:{
														id:'bsgroupsName_',
	  													name:'bsgroupsName',
	  													width:'180px' 
													}
												 }
											   },
							                  {field : 'mallNo', hidden:true,title : '商场编码', width : 160, align:'left',halign:'center',
							                  	 editor:{
							                  	 	type:'hiddenbox',
							                  	 	options:{
							                  	 		id:'mallNo_', 
							                  	 		name:'mallNo'
							                  	 	}
							                  	 }
							                  },
							                  {field : 'mallName', title : '商场名称', width : 150,align:'left',halign:'center', 
							                  	editor:{type:'searchboxname',
							                  			options:{
															readonly:true,
															id:'mallName_',
															name:'mallName',
															width:'180px'
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
						                	   if(!shopBalanceDate.check(rowIndex,data)){
									                  shopBalanceDate.edit(rowIndex,data);
									             }
						              }}'
			                 />
						</div>
				 	</div>
				</div>
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true" > 
		<form  id="dataForm" method="post" class="pd10">
			<input type="hidden" id="id" name="id" />
			<div id="div1" class="easyui-panel" style="width:680px;"  title="筛选条件" data-options="title:'筛选条件',fieldset:true,cls:'mt10'">
			<table  class="form-tb">
				<col width="80" />
			    <col />
			    <col width="80" />
			    <col />
			    <col width="80" />
			    <col />
			    <tbody>
				<!-- <tr>
					<th><span class="ui-color-red">*</span>管理城市： </th>
					<td width="100px;" height="40px;">
					    <input class="ipt easyui-organ" name="organNames" id="organNames_" 
					    data-options="multiple:true,required:true,inputWidth:140,inputNoField:'organNos_',inputNameField:'organNames_'"/>
				        <input type="hidden" name="organNos" id="organNos_"/>
				    </td>
					<th>经营城市： </th>
					<td width="100px;" height="40px;">
					   <input class="ipt easyui-organ" name="cityNames" id="cityName_" 
					   		data-options="multiple:true,queryUrl:BasePath+'/organ/list.json?status=1&organLevel=2',filterOrgan:'organNo',inputNoField:'cityNo_',inputNameField:'cityName_'"/>
				       <input type="hidden" name="cityNos" id="cityNo_"/>
				    </td>
				    <th><span class="ui-color-red">*</span>品牌： </th>
					<td width="100px;" height="40px;">
					    <input class="ipt easyui-brand" name="brandNames" id="brandNames" data-options="multiple:true,inputNoField:'brandNos',inputNameField:'brandNames',required:true"/>
				        <input type="hidden" name="brandNos" id="brandNos"/>
				    </td>
				</tr> -->
				<tr>
					<th><span class="ui-color-red">*</span>公司： </th>
					<td>
						<input class="easyui-company" name="companyName" id="companyNameTemp" 
							data-options="inputNoField:'companyNoTemp',inputNameField:'companyNameTemp',required:true"/>
					    <input type="hidden" name="companyNo" id="companyNoTemp"/>
					</td>
					<th><span class="ui-color-red">*</span>结算月：</th>
					<td>
				       <input class="easyui-datebox" name="month" id="monthTemp" data-options="dateFmt:'yyyyMM',width:130,required:true" data-options="inputNoField:'monthTemp'"/>    							
					</td>
					<th>店铺：</th>
					<td>
							<input id="shopNameTemp"/>
							<input type="hidden" name="shopNos" id="shopNoTemp"/>
					</td>
				</tr>
				</tbody>
			</table>
			</div>
		</form>
	</div>
</body>
</html>