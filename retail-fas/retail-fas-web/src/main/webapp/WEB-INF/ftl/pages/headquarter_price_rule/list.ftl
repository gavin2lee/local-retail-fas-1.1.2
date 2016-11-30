<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>总部加价规则</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/headquarterPriceRule.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/headquarter_price_rule/list.json" 
					  save_url="/headquarter_price_rule/insert" 
					  update_url="/headquarter_price_rule/update" 
					  del_url="/headquarter_price_rule/save" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="680" 
					  dialog_height="420"
					  export_url="/headquarter_price_rule/do_fas_export"
					  export_title="总部加价规则导出"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":1},
             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":2},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":3},
             {"id":"btn-edit1","title":"增加年份","iconCls":"icon-edit","action":"headquarterPriceRule.addYear()","type":2},
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
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
		                 <tbody>
						 	<tr>
						 		<th>规则编码：</th>
						 		<td>
						 			<input class="easyui-validatebox ipt" style="width:150px" name="addRuleNo" id="addRuleNoCondition" />
						 		</td>
						 		<th>供应商组名称：</th>
						 		<td>
						 			<input class="easyui-combobox ipt" name="supplierGroupNoAlial" id="supplierGroupNoCondition" />
						 		</td>
						 		<th>新旧款类型：</th>
						 		<td>
						 			<input class="easyui-combobox ipt" name="styleType" id="styleTypeCondition" />
						 		</td>
						 		<th>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</th>
						 		<td>
						 			<input class="easyui-combobox ipt" name="yearCode" id="yearCodeCondition" />
						 		</td>
						 	</tr>
							<tr>
								<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部：</th>
						 		<td>
						 			<input class="easyui-combobox ipt" name="brandUnitNoAlial" id="brandUnitNoCondition" />
						 		</td>
								<th>加价依据：</th>
						 		<td>
						 			<input class="easyui-combobox ipt" name="addBasis" id="addBasisCondition" />
						 		</td>
						 		<th>生效日期：</th>
							 	<td>
							 		<input class="easyui-datebox ipt" style="width:150px" name="effectiveTimeStart" id="effectiveTimeStart" data-options="maxDate:'effectiveTimeEnd'"/>
							 	</td>
								<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-datebox ipt" style="width:150px" name="effectiveTimeEnd" id="effectiveTimeEnd" data-options="minDate:'effectiveTimeStart'"/>
								</td>
							</tr>
							<tr>
								<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th>
						 		<td>
						 			<input class="easyui-combobox ipt" name="categoryNoAlial" id="categoryNoCondition" />
						 		</td>
						 	    <th>季&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;节：</th>
						 		<td>
						 			<input class="easyui-combobox ipt" name="season" id="seasonCondition" />
						 		</td>
							</tr>
						 </tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   
		              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false"  
			           columnsJsonList="[
			                  	  {field : 'ck',checkbox:true,notexport:true},
				   				  {field : 'id',title : 'id',width : 10, hidden : true,notexport:true},
				   				  {field : 'addRuleNo',title : '规则编码',width : 80, align:'left',halign:'center'},
				                  {field : 'supplierGroupNo',title : '供应商组编码',width: 100,align:'left',halign:'center'},
				                  {field : 'supplierGroupName',title : '供应商组名称',width: 120,align:'left',halign:'center'},
				                  {field : 'categoryName',title : '大类',width: 80,align:'left',halign:'center'},
				                  {field : 'twoLevelCategoryName',title : '二级大类',width: 80,align:'left',halign:'center'},
				                  {field : 'styleTypeName',title : '新旧款类型',width : 100, align:'left',halign:'center'},
				                  {field : 'year',title : '年份',width: 80,align:'left',halign:'center'},
				                  {field : 'seasonName',title : '季节',width : 80, align:'center'},
				                  {field : 'brandUnitName',title : '品牌部',width: 100,align:'left',halign:'center'},
				                  {field : 'addBasis',title : '加价依据',width: 80,align:'center',formatter:headquarterPriceRule.addBasisFormatter},
				                  {field : 'effectiveTime',title : '生效日期',width: 100,align:'center'},
				                  {field : 'addPrice',title : '加价',width: 80,align:'right',halign:'center'},
				                  {field : 'addDiscount',title : '加折',width: 80,align:'right',halign:'center'},
				                  {field : 'discountRate',title : '折扣',width: 80,align:'right',halign:'center'},
				                  {field : 'createUser',title : '创建人',width: 100,align:'center'},
				                  {field : 'createTime',title : '创建时间',width: 140,align:'center'},
				                  {field : 'updateUser',title : '修改人',width: 100,align:'center'},
				                  {field : 'updateTime',title : '修改时间',width: 140,align:'center'}
			              ]" 
				          jsonExtend='{onDblClickRow:function(rowIndex, rowData){
			           	  		 headquarterPriceRule.loadDetail(rowData);
			              }}'
                 />
			</div>
	 	</div>
	</div>
	
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="dataForm" id="dataForm" method="post"  class="pd10">
	     <input type="hidden" name="id" id="id">
	     	<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:620px;"  title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table class="form-tb">
					     <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
				         <tbody>
				         	<tr height="40">
				         		<th>规则编码：</th>
			        		    <td><input class="easyui-validatebox ipt" name="addRuleNo" id="addRuleNo" data-options="required:true,validType:['engNum','maxLength[18]']"/></td>
				         		<th>供应商组：</th>
			        		    <td><input class="ipt" name="supplierGroupNos" id="supplierGroupNos" data-options="required:true,validType:'length[1,50]'"/><input type="hidden" name="supplierGroupName" id="supplierGroupName" /></td>
			        		</tr>
			        		<tr height="40">
			        		    <th>大类：</th>
			        		    <td><input class="ipt" name="settleCategoryNos" id="settleCategoryNos" data-options="required:true,validType:'length[1,50]'"/><input type="hidden" name="categoryName" id="categoryName" /></td>
			        			<th>是否新旧款：</th>
			        		    <td>
									<input type="radio" name="newStyleFlag" value="1" id="radio1" />是
									&nbsp;&nbsp;
									<input type="radio" name="newStyleFlag" value="0" id="radio0" />否
								<td>
							</tr>
			        		<tr height="40">
			        			<th>新旧款类型：</th>
			        		    <td><input class="easyui-combobox" name="styleType" id="styleType" /></td>
			        			<th>年份：</th>
			        		    <td>
			        		    	<input class="ipt" name="yearCodes" id="yearCode" />
			        		    	<input type="hidden" name="year" id="year" />
			        		    </td>
			        		</tr>
			        		<tr height="40">
			        			<th>季节：</th>
			        		    <td>
			        		    	<input class="easyui-combobox" name="season" id="season" />
			        		    	<input type="hidden" name="seasonName" id="seasonName" />
			        		    </td>
				         		<th>加价依据：</th>
			        		    <td><input class="easyui-combobox" name="addBasis" id="addBasis" data-options="required:true,onSelect: headquarterPriceRule.bindAddBasisEvent" /></td>
			        		</tr>
			        		<tr height="40">
			        			<th>加价：</th>
			        		    <td><input class="easyui-numberbox ipt" name="addPrice" id="addPrice" data-options="validType:'number'"/></td>
			        			<th>加折：</th>
			        		    <td><input class="easyui-numberbox ipt" name="addDiscount" id="addDiscount" data-options="min:1,max:2,precision:2"/></td>
			        		</tr>
			        		<tr height="40">
			        			<th>折扣：</th>
			        		    <td><input class="easyui-numberbox ipt" name="discountRate" id="discountRate" data-options="min:0,max:1,precision:2"/></td>
			        			<th>生效日期：</th>
			        		    <td><input class="easyui-validatebox easyui-datebox ipt" name="effectiveTime" id="effectiveTime" data-options="required:true"/></td>
			        		</tr>
			        		<tr height="40">   
			        		    <th>品牌部：</th>
			        		    <td><input class="ipt" name="brandUnitNos" id="brandUnitNos" data-options="required:true,validType:'length[1,50]'"/><input type="hidden" name="brandUnitName" id="brandUnitName" /></td>
			        			<th>二级大类：</th>
			        		    <td><input class="ipt" id="twoLevelCategory"/><input type="hidden" name="twoLevelCategoryNo" id="twoLevelCategoryNo" /><input type="hidden" name="twoLevelCategoryName" id="twoLevelCategoryName" /></td>
			        		</tr>
				        </tbody>	
					</table>
				</div>
			</div>
		 </form>	
   </div>
   <div id="addYearFormPanel" class="easyui-dialog" data-options="closed:true"> 
	     <form name="addYearForm" id="addYearForm" method="post" class="pd10">
	     	<div id="dtl_detail2">
				<div id="div2" class="easyui-panel" title="识别信息" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
					   <tr height="40">
					   		<th><span class="ui-color-red">*</span>增加年份：</th>
	        		    	<td>
		        		    	<input class="ipt" name="yearCode" id="addYearCode" />
		        		    	<input type="hidden" name="year" id="addYear" />
		        		    </td>
					   </tr>
					</table>
				</div>
			</div>
		 </form>	
   </div>
</body>
</html>