<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>客残明细表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/custom_imperfect/customImperfect.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"customImperfect.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"customImperfect.clear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"customImperfect.add()", "type":0},
             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"customImperfect.del()","type":0}, 
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"customImperfect.save()","type":0},  
             {"id":"btn-export","title":"导入","iconCls":"icon-import","action":"common_util.doImport('客残明细导入.xlsx','/custom_imperfect/doImport',1,importCallBack)","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/custom_imperfect/doExport','客残明细表导出')","type":0}
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
									<th>公司： </th>
									<td><input class="ipt" multiSearch="company" notGroupLeadRole /><input type="hidden" name="multiBuyerNo"></td>
									<th>供应商： </th>
									<td><input class="ipt" multiSearch="supplier"  /><input type="hidden" name="multiSalerNo"></td>
									<th>品牌： </th>
									<td><input class="ipt" multiSearch="brand"  /><input type="hidden" name="multiBrandNo"></td>
									<th>大类： </th>
									<td><input class="ipt" multiSearch="category" /><input type="hidden" name="multiOneLevelCategoryNo"></td>
								</tr>	
									<th>商品编码： </th><td><input class="ipt"  multiSearch="item"/><input type="hidden" name="multiItemCode"></td>
									<th>结算单号： </th><td><input class="ipt"  name="balanceNoLike" /></td>
									<th>日期：</th>
								    <td><input class="easyui-datebox ipt" defaultValue="startDate" name="returnDateStart" id="returnDateStart" data-options="maxDate:'returnDateEnd'" /></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td><input class="easyui-datebox ipt" defaultValue="endDate" name="returnDateEnd" id="returnDateEnd" data-options="minDate:'returnDateStart'" /></td>	
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""  pageSize="20" showFooter="true" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = "" rownumbers="true" 
		              checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
			           columnsJsonList="[
			                 {field : 't', checkbox:true, width : 30, notexport:true},
			                 {field : 'buyerName', title : '公司', width : 220, editor:{type:'test_combogrid',options:{type:'company',notGroupLeadRole:true,required:true,datagridId:'dtlDataGrid',valueField:'buyerNo'}}, align:'left'},
			                 {field : 'buyerNo', title : '公司编号', hidden:'true', notexport:true, editor:{type:'readonlytext'}},
			                 {field : 'salerName', title : '供应商', width : 220, editor:{type:'test_combogrid',options:{type:'supplier',required:true,datagridId:'dtlDataGrid',valueField:'salerNo'}}, align:'left'},
			                 {field : 'salerNo', title : '供应商编号', hidden:'true', notexport:true, editor:{type:'readonlytext'}},
			 				 {field : 'returnDate',title:'日期',width:100,editor:{type:'datebox',options:{required:true}}},
			                 {field : 'itemCode', title : '商品编码', width : 120, editor:{type:'test_combogrid',options:{type:'item',required:true,callback:customImperfect.itemCallBack}}},
			                 {field : 'sizeNo', title : '尺码', width : 120, editor:{type:'numberbox',options:{validType:'maxLength[10]'}}},
			                 {field : 'purchasePrice',title:'采购价',width:80,editor:{type:'readonlytext'}},
			                 {field : 'qty', title : '数量', width : 120, editor:{type:'numberbox',options:{required:true,validType:'maxLength[10]'}}},
							 {field : 'amount',title:'金额',width:80,editor:{type:'readonlytext'}},
			                 {field : 'reason', title : '质量原因', width:200,editor:{type:'validatebox',options:{validType:'maxLength[200]'}}},
							 {field : 'opinion', title : '处理意见', width:200,editor:{type:'validatebox',options:{validType:'maxLength[200]'}}},
							 {field : 'itemNo', title : '商品ID', hidden:'true', notexport:true, editor:{type:'readonlytext'}},
							 {field : 'itemName', title : '商品名称', width:120,editor:{type:'readonlytext'}},
							 {field : 'brandName',title:'品牌',width:80,editor:{type:'readonlytext'}},
							 {field : 'categoryName',title:'大类',width:80,editor:{type:'readonlytext'}},
							 {field : 'yearsName',title:'年份',width:80,editor:{type:'readonlytext'}},
							 {field : 'seasonName',title:'季节',width:80,editor:{type:'readonlytext'}},
							 {field : 'genderName',title:'性别',width:80,editor:{type:'readonlytext'}},
							 {field : 'brandNo',title:'品牌编码',hidden:'true',editor:{type:'readonlytext'}, notexport:true},
							 {field : 'categoryNo',title:'大类编码',hidden:'true',editor:{type:'readonlytext'}, notexport:true},
							 {field : 'years',title:'年份编码',hidden:'true',editor:{type:'readonlytext'}, notexport:true},
							 {field : 'season',title:'季节编码',hidden:'true',editor:{type:'readonlytext'}, notexport:true},
							 {field : 'gender',title:'性别编码',hidden:'true',editor:{type:'readonlytext'}, notexport:true},
			 				 {field : 'createUser',title:'创建人',width:100},
			 				 {field : 'createTime',title:'创建时间',width:150},
			 				 {field : 'balanceNo',title:'结算单号',width:120, formatter: function(value,row,index){
								return billNoMenuRedirect.billNoMenuFormat(value,row,index,'HS-结算单');
							}}]"	
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	  //双击方法
			                   	   customImperfect.edit(rowIndex, rowData);
			              }}'
						/>
			</div>
	 	</div>
	</div>
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="importDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'index', title : '行号', width : 30},
	           		 {field : 'pass',title:'是否导入成功',width:100,formatter: function(value,row,index){
							if (value == 0){
								return '否';
							}
							return '是'
						}
			         },
			         {field : 'errorInfo',title:'错误信息',width:200},
                     {field : 'validateObj.buyerNo', title : '公司编码', width : 100},
                     {field : 'validateObj.salerNo', title : '供应商编码', width : 100},
                     {field : 'validateObj.returnDate', title : '日期', width : 150},
                     {field : 'validateObj.brandNo',title:'品牌编码',width:100},	
	 				 {field : 'validateObj.itemCode',title:'商品编码',width:100},
	 				 {field : 'validateObj.sizeNo',title:'尺码',width:100,align:'right'},
	 				 {field : 'validateObj.qty',title:'实收数量',width:100,align:'right'},
					 {field : 'validateObj.reason',title:'质量原因',width:100},
	 				 {field : 'validateObj.opinion',title:'处理意见',width:100}]"	
         />
     </div>		
</body>
</html>