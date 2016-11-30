<!DOCTYPE html>
<head>
    <title>商场费用登记</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/mall_deduction_cost.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/mallshop_balance/selectObjUtil.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/mall_deductcost/list.json" 
					  save_url="/mall_deductcost/post" 
					  update_url="/mall_deductcost_set/put" 
					  del_url="/mall_deductcost/save" 
					  datagrid_id="deductCostdataGrid" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="350" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0}
	           ]
			/>
			
		<#--搜索start-->
		<div class="search-div">
			     <form name="searchForm" id="searchForm" method="post">
	       		 	<table class="form-tb">
	       		 			  <tr>
								<td align="right" width="110">公司：</td>
									<td align="left" width="140"><input class="ipt"  iptSearch="company"  name="companyName" id="companyName" data-options="required:true" />
									<input type="hidden" name="companyNo"/></td>								
								<td align="right" width="110">商业集团：</td>
									<td align="left" width="140"><input class="ipt" iptSearch="bsgroups"  name="bsgroupsName" id="bsgroupsName" data-options="required:true" />
									<input type="hidden" name="bsgroupsNo"/></td>
								</tr>				    
							    <tr>	
								<td align="right" width="110">商场：</td>
								<td align="left" width="140"><input class="easyui-validatebox ipt" iptSearch="mall"   name="mallName" id="mallName"  />
								<input type="hidden"  name="mallNo" id="mallNo" 	/>	
								<td width="110" align="right">品牌：</td>
								<td width="140" align="left"><input class="easyui-combobox ipt" name="brandNo" id="brandNo" required="true" data-options="required:true" />
								<input type="hidden"  name="brandName" id="brandName" />
								</td>	
							 	<td align="right" width="110">制单人：</td>
								<td align="left" width="140">
								<input class="ipt"   name="createUser" id="createUser" data-options="required:true" /></td>   
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
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "fas_common_editor.deleteRow()","type":0},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "mallDeductionCost.save()", "type":0}		             
		           ]
				/>
            </div>
		                   
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="deductCostdataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"
				           rownumbers="false" singleSelect="false"  
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				                  {field : 'companyNo',hidden:true, title : '公司编码', width : 160, editor:{type:'textbutton'}},
				                  {field : 'companyName', title : '公司', width : 200, editor:{type:'textbutton',options:selectObjUtil.selectCompany}},				                 
				                  {field : 'mallNo',hidden:true, title : '商场编码', width : 130, editor:{type:'textbutton'}},
				                  {field : 'mallName', title : '商场', width : 130, editor:{type:'textbutton',options:selectObjUtil.selectMall}},	
				                  {field : 'shopNo',hidden:true, title : '店铺编码', width : 160, editor:{type:'textbutton'}},
				                  {field : 'shopName', title : '店铺', width : 150, editor:{type:'textbutton',options:selectObjUtil.selectShop}}, 
				                  {field:'period',title:'结算月',width:80,editor:{type:'datebox',options:{dateFmt:'yyyyMM'}}},  
				                  {field : 'deductionCode',hidden:true, title : '商场扣费编码', width : 130, editor:{type:'textbutton'}},
				                  {field : 'deductionName', title : '扣费类别', width : 130, editor:{type:'textbutton',options:selectObjUtil.selectDeduction}},	
				                  {field : 'deductAmount',title : '扣费金额',width : 100,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },  
				                   {field : 'actualAmount',title : '实际扣费金额',width : 100,align:'center',
				                  	editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true
				                  		}
				                  	}
				                  },  
				                  {field : 'bsgroupsNo',hidden:true, title : '商场体系', width : 160, editor:{type:'textbutton'}},
				                  {field : 'bsgroupsName', title : '商业集团', width : 200, editor:{type:'textbutton',options:selectObjUtil.selectBsgroups}},  
				                  {field : 'brandNo',hidden:true, title : '品牌编码', width : 130, editor:{type:'textbutton'}},
				                  {field : 'brandName', title : '品牌', width : 130, editor:{type:'combobox'}},  
				                  {field : 'costCateCode',hidden:true, title : '费用类别编码', width : 130, editor:{type:'textbutton'}},
				                  {field : 'costCateName', title : '总账费用类别', width : 130, editor:{type:'textbutton',options:selectObjUtil.selectCostCate}},         			          	
				                  {field : 'costType',title : '费用性质 ',width : 80,align:'center',
				                    formatter: mallDeductionCost.dataCostType, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  	    	data: [{'value':'1', 'text': '合同内'},
				                  	    	 {'value':'2', 'text':'合同外'}
				                  	    	 ], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },	
				                  {field : 'costDeductType',title : '费用扣取方式 ',width : 90,align:'center',
				                    formatter: mallDeductionCost.dataCostDeductType, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  	    	data: [{'value':'1', 'text': '票前 '},
				                  	    	 {'value':'2', 'text':'票后'}
				                  	    	 ], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },	
				                  {field : 'costPayType',title : '费用交款方式 ',width : 90,align:'center',
				                   formatter: mallDeductionCost.dataCostPayType, 
				                  	editor:{
				                  		type:'combobox',
				                  		options:{
				                  	    	data: [{'value':'1', 'text': '帐扣'},
				                  	    	 {'value':'2', 'text':'现金'}
				                  	    	 ], 
				                  			valueField: 'value', textField: 'text',
				                  			required:true
				                  		}
				                  	}
				                  },            
				                   {field : 'remark',title : '备注',width : 150,align:'center',
				                  	editor:{
				                  		type:'validatebox'
				                  	}
				                  },
				                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'},  
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                               //双击方法
					                   	  fas_common_editor.editRow(rowIndex, rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
</body>
</html>